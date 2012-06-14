package com.tmall.asshole.schedule;


import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tmall.asshole.common.EventEnv;
import com.tmall.asshole.common.ScheduleType;
import com.tmall.asshole.config.EngineConfig;


/****
 * 
 * @author jiuxian.tjo
 *
 */
public class Schedule<T,C> extends Job {
	private static transient Log logger = LogFactory.getLog(Schedule.class);
	protected SchedulerThreadPoolExecutor threadPool;
	protected final IDataLoader<T> dataLoader;
	protected final IDataProcessor<T,C> dataProcessor;
	
	protected IDataProcessorCallBack<T,C> dataProcessorCallBack;
	
	// 考虑远程变量推送
	public boolean dealWithReceiveMsg = true;
	private int schedulingPollingTime;
	protected int maxHashNum;
	protected String envionmentGroup;
	protected String taskName;
	protected String groupingName;
	protected IScheduleFgetcPolicy scheduleFgetcPolicy;
	
	private String scheduleType;
	
	private AtomicBoolean stop=new AtomicBoolean(false);
	
//	@Autowired
//	protected ScheduleFgetcPolicyFactory scheduleFgetcPolicyFactory;
	
	
	public Schedule(IDataLoader<T> dataLoader, IDataProcessor<T,C> dataProcessor, EngineConfig config) {
		this.dataLoader = dataLoader;
		this.dataProcessor = dataProcessor;
		this.envionmentGroup = config.getEnvionmentGroup();
		this.taskName = config.getTaskName();
		this.groupingName = config.getGroupingName();
		this.schedulingPollingTime = config.getSchedulingPollingTime();
		this.scheduleType=config.getScheduleType();
		
		this.threadPool = new SchedulerThreadPoolExecutor(config.getCorePoolSize(),config.getMaxPoolSize(),config.getKeepAliveTime());
		//根据配置决定选择 scheduleFgetcPolicy
		scheduleFgetcPolicy=ScheduleFgetcPolicyFactory.create(config.getAlgorithmType());
		
	}
	
	
	public void setDataProcessorCallBack(IDataProcessorCallBack<T,C> dataProcessorCallBack) {
		this.dataProcessorCallBack = dataProcessorCallBack;
	}


	public IScheduleFgetcPolicy getScheduleFgetcPolicy() {
		return scheduleFgetcPolicy;
	}



	public synchronized void strart() {
		// 订阅
		// 发布
		this.threadPool.init(taskName);
		super.start();
	}
	
	public synchronized void stopSchedule(){
		this.stop.set(true);
	}
	
	public void run() {
		while (true) {
			try {
				if (dealWithReceiveMsg) {// 设置系统参数--是否处理收到的消息
					processQueue();
				} else {
					logger.debug("Scheduler[" + taskName + "] thread suspend ");
				}
				if(stop.get()==true){
					logger.error("Scheduler[" + taskName + "] thread suspend , stop="+stop);
					break;
				}

			} catch (Throwable e) {
				logger.error("Scheduler[" + taskName + "] thread error", e);
			}
		}
	}
	
	protected void processQueue() {
		List<T> dataList = null;
		try {
			if (threadPool.isAllThreadFree()) {
				if (scheduleFgetcPolicy.getStartIndex() < scheduleFgetcPolicy.getEndIndex()) {
					dataList = dataLoader.getDataList(scheduleFgetcPolicy.getStartIndex(), scheduleFgetcPolicy.getEndIndex(),
							scheduleFgetcPolicy.getRowNum(), EventEnv.valueOf(envionmentGroup), ScheduleType.valueOf(scheduleType), scheduleFgetcPolicy.getExecuteMachineAlias());
				} else {
					logger.warn("Scheduler[" + taskName + "]  scheduleFgetcPolicy.getStartIndex[" + scheduleFgetcPolicy.getStartIndex()
							+ "]>scheduleFgetcPolicy.getEndIndex");
				}
			} else {

				logger.warn("Scheduler[" + taskName + "] threadPool.isThreadPollFull ");
			}
		} catch (Throwable e1) {
			logger.error("Scheduler[" + taskName + "] processQueue error on dataLoader.getDataList", e1);
			return;
		}

		if (dataList != null && dataList.size()>0) {
			dealDataList(dataList);
		}else{
			logger.warn("Scheduler[" + taskName + "]  no datalist ");
		}
		
		try {
			Thread.sleep(schedulingPollingTime);
		} catch (Throwable e) {
			logger.error("Scheduler[" + taskName + "] thread sleep error", e);
		}

	}
	
	protected void dealDataList(final List<T> dataList) {
		try {
			Runnable task = new Runnable() {
				public void run() {
					try {
						for (T t : dataList) {
							   Class<C> entityClass = (Class<C>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
					           C c = entityClass.newInstance();
							try {
								dataProcessor.process(t,c);
							} catch (Throwable e) {
							   logger.error("dataProcessor, process error taskName: ["+ taskName+ "] data: [ " + t + " ]", e);
							}finally{
								try{
								if(dataProcessorCallBack!=null){
								    dataProcessorCallBack.callback(t,c);
								}
								}catch (Exception e) {
									   logger.error(" dataProcessorCallBack ,process error taskName: ["+ taskName+ "] data: [ " + t + " ] ", e);
								}
							}
						}
					} catch (Exception e) {
						logger.error("Scheduler[" + taskName + "]threadPool.execute error dataProcessor.process dealDataList:["
								+ ToStringBuilder.reflectionToString(dataList) + "]", e);
					}

				}
			};
			threadPool.execute(task);
		} catch (Throwable e) {
			logger.error("Scheduler[" + taskName + "] processQueue error on threadPool.execute ", e);
		}
	}
	
	

}
