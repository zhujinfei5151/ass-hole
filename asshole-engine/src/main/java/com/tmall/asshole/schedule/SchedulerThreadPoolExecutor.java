package com.tmall.asshole.schedule;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/****
 * 
 * 内部线程池
 * 
 * @author jiuxian.tjo
 *
 */
public class SchedulerThreadPoolExecutor {
	
	private static transient Log logger = LogFactory.getLog(SchedulerThreadPoolExecutor.class);
	
	
	/** *线程池维护线程的最少数量 */
	private int corePoolSize = 20;

	/** *线程池维护线程的最大数量 */
	private int maxPoolSize = 20;

	/** *线程池维护线程所允许的空闲时间 */
	private int keepAliveTime = 0;

	private int defaultScheduleInterval = 50;

	private ReentrantLock rejectQueuePauseLock = new ReentrantLock();

	/** * 缓冲队列 */
	private Queue<Runnable> rejectQueue = new LinkedList<Runnable>();

	/** 调度线程池 */
	private ScheduledExecutorService scheduler;

	private int rejectQueueMaxSize = 1000;

	private ThreadPoolExecutor threadPool;

	public SchedulerThreadPoolExecutor() {
		// startScheduledFuture();
		// initThreadPoolExecutor();
	}

	public SchedulerThreadPoolExecutor(int corePoolSize, int maxPoolSize, int keepAliveTime) {
		this.corePoolSize = corePoolSize;
		this.maxPoolSize = maxPoolSize;
		this.keepAliveTime = keepAliveTime;
	}

	public void init(String schedulerName) {
		startScheduledFuture(schedulerName);
		initThreadPoolExecutor(schedulerName);
	}

	public void initThreadPoolExecutor(String schedulerName) {
		RejectedExecutionHandler handler = new RejectedExecutionHandler() {
			public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
				rejectQueuePauseLock.lock();
				try {
					if (rejectQueue.size() < rejectQueueMaxSize) {
						rejectQueue.offer(r);
					}
				} finally {
					rejectQueuePauseLock.unlock();
				}
			}
		};
		threadPool = new ThreadPoolExecutor(corePoolSize, maxPoolSize, keepAliveTime, TimeUnit.SECONDS, new SynchronousQueue<Runnable>(),
				new ThreadFactoryImpl(schedulerName), handler);
	}

	public void startScheduledFuture(String schedulerName) {

		// 构建定时检查
		Timer checkTimer = new Timer("reject-" + schedulerName);
		TimerTask task = new TimerTask() {

			@Override
			public void run() {
				/** 查看是否有待定请求，如果有，则创建一个新的Thread，并添加到线程池中 */
				int activeCount = threadPool.getActiveCount();
				int maxSize = threadPool.getMaximumPoolSize();
				rejectQueuePauseLock.lock();
				try {
					int rQueueSize = rejectQueue.size();
					if (rQueueSize > 0) {
						int polSize = 0;
						int rQPolSize = maxSize - activeCount;
						polSize = (rQueueSize < rQPolSize) ? rQueueSize : rQPolSize;
						for (int i = 0; i < polSize; i++) {
							Runnable task = rejectQueue.poll();
							if (task != null) {
								threadPool.execute(task);
							}
						}
					}
				} catch (Throwable e) {
					logger.error(e.getMessage(), e);
				} finally {
					rejectQueuePauseLock.unlock();
				}
			}
		};
		checkTimer.schedule(task, new java.sql.Date(System.currentTimeMillis() + 5000), defaultScheduleInterval);

	}

	/**
	 * 实现java.util.concurrent.ThreadFactory接口
	 * 
	 */
	private static final class ThreadFactoryImpl implements ThreadFactory {
		private String name = "";

		public ThreadFactoryImpl(String name) {
			this.name = name;
		}

		public Thread newThread(Runnable r) {
			Thread thread = new Thread(r);
			thread.setDaemon(true);
			thread.setName("TEP Scheduler: [" + name + "]");
			return thread;
		}
	}

	public void execute(Runnable task) {
		this.threadPool.execute(task);
	}

	public boolean isAllThreadFree() {
		boolean result = false;
		rejectQueuePauseLock.lock();
		try {
			if (rejectQueue.size() == 0 && threadPool.getActiveCount() == 0) {
				result = true;
			}
		} finally {
			rejectQueuePauseLock.unlock();
		}
		return result;
	}

	public boolean isThreadPollFull() {
		boolean result = false;
		rejectQueuePauseLock.lock();
		try {
			if (rejectQueue.size() > 0) {
				result = true;
			}
		} finally {
			rejectQueuePauseLock.unlock();
		}
		return result;
	}

	public int getCorePoolSize() {
		return threadPool.getCorePoolSize();
	}

	public void setCorePoolSize(int corePoolSize) {
		threadPool.setCorePoolSize(corePoolSize);
	}

	public int getMaxPoolSize() {
		return threadPool.getMaximumPoolSize();
	}

	public void setMaxPoolSize(int maxPoolSize) {
		threadPool.setMaximumPoolSize(maxPoolSize);
	}

	public int getActiveCount() {
		return threadPool.getActiveCount();
	}

}
