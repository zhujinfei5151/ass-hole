package com.tmall.asshole.zkclient;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.logging.Log;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper.States;
import org.springframework.beans.factory.annotation.Autowired;

import com.tmall.asshole.common.LoggerInitUtil;
import com.tmall.asshole.zkclient.data.Data;
import com.tmall.asshole.zkclient.data.NodeData;
import com.tmall.asshole.zkclient.data.PersistenceUtil;

/****
 * ZKClient
 *
 *
 *                   --- path1(10.12.32.12)
 *                                   |
 *                               NodeData
 *   rootPath
 *       |            ---- path2(122.23.2.32)
 *       |                            |
 *                               NodeData
 *   NodeData
 *                  ---- path3(121.234.223)
 *                                  |
 *                              NodeData
 *
 *
 *   NodeData  --      Data1
 *                            Data2
 *                            Data3
 *
 *
 *
 * @author tangjinou(jiuxian.tjo)
 *
 */
public class ZKClient  implements Watcher {
	private final static Log log = LoggerInitUtil.LOGGER;

	@Autowired
	private ZKConfig zKConfig;

	private ZKClientVersion version = new ZKClientVersion();

	private ZKManager zKManager;

	//守护ZK客户端运行的线程
	private final long protectedThreadSleepTime = 6000;

	private List<INodeChange> iNodeChanges = new ArrayList<INodeChange>();

	private boolean shutdown;

	private Thread protecteThread;


//	private boolean needRegisterWatch;

	private Lock lock = new ReentrantLock();

//	public ZKClient(INodeChange iNodeChange){
//		iNodeChanges.add(iNodeChange);
//	}

	public ZKClient(INodeChange iNodeChange, ZKConfig zKConfig){
		this.zKConfig = zKConfig;
		iNodeChanges.add(iNodeChange);
	}

	public ZKClient(List<INodeChange> iNodeChanges,ZKConfig zKConfig) {
		this.zKConfig = zKConfig;
		this.iNodeChanges.addAll(iNodeChanges);
	}


	public void start()  throws Exception{
		lock.lock();
		try{
		shutdown = false;
		if(iNodeChanges.size()==0){
			  throw new Exception("iNodeChange can't be o");
		}
		if(zKConfig==null){
			throw new Exception("zKConfig can't be null");
		}
		zKManager = new ZKManager(this, zKConfig);
		zKManager.init();
		//reRegisterWatch();
		//thread.setDaemon(true);

		protecteThread =new Thread(new Runnable() {
			public void run() {
				while (true) {
					try {
						if(shutdown){
							log.error("close the protected thread of zkClient");
							break;
						}
						//发现与ZKServer断开,重连
						if(zKManager.getZk().getState() == States.CLOSED){
							zKManager.init();
							continue;
						}

						log.info("the protected thread of zkClient is running");
						Thread.sleep(protectedThreadSleepTime);		//会有bug,可能会导致服务器端有太多本客户端的地坄1�7
//						reRegisterWatch();
					} catch (Exception e) {
						log.error("zkClient dema thread:"+e);
					}
				}
			}
		});
		protecteThread.setName("zkprotecteThread");
		protecteThread.start();
		}catch (Exception e) {
			throw e;
		}finally{
			lock.unlock();
		}
	}


	public void process(WatchedEvent event) {
		//Disconnected    SyncConnected NodeDataChanged
		List<String> children = null;

		try {
			String path = event.getPath();

			if( event.getState() ==KeeperState.Disconnected ||   event.getState() ==KeeperState.Expired){
				log.info("lost the conection with zk server");
				for (INodeChange  iNodeChange: iNodeChanges) {
					iNodeChange.onChange(children);
				}
				return;
			}

			if(path==null && event.getState() ==KeeperState.SyncConnected  && event.getType() == Event.EventType.None){
			    log.info("connected with zk server");
				return;
			}

			if(path == null){
				return;
			}

			log.info("=======================>>current path:" + path);

			if(zKConfig.getRootPath().trim().equals(path)){
				children = zKManager.getZk().getChildren(zKConfig.getRootPath(), false);

				for (INodeChange  iNodeChange: iNodeChanges) {
					iNodeChange.onChange(children);
				}

		   }

	   }
	   catch (Exception e) {
	    	log.error(e);
		}
	    finally{
				reRegisterWatch();
	    }
	 }


//	private void reConect() {
//		close();
//	 new Thread(
//		new Runnable() {
//			public void run() {
//
//
//
//
//			}
//		}).start();
//	}

	public void close(){
		try {
			zKManager.close();
		} catch (Exception e) {
			log.error(e.getCause());
		} finally{
			shutdown=true;
		}
	}


	private void reRegisterWatch(){
		try {
			List<String> children = zKManager.getZk().getChildren(zKConfig.getRootPath(), true);
			log.info("children:"+children.toArray());
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			return;
		}

	}

	public ZKClientVersion getVersion() {
		return version;
	}

	public void setVersion(ZKClientVersion version) {
		this.version = version;
	}

	public void reSetNodeData(List<Data> dataL)  throws Exception{
		NodeData nodeData = getNodeData();
		nodeData.setDatas(dataL);
		byte[] datas = PersistenceUtil.serializable(nodeData);
		zKManager.getZk().setData(zKConfig.getFullPath(), datas, -1);
	}

	private NodeData getNodeData() throws Exception{
		  return PersistenceUtil.deSerializable(zKManager.getZk().getData(zKConfig.getFullPath(), false, null));
	}

	private NodeData getParentNodeData() throws Exception{
		  return PersistenceUtil.deSerializable(zKManager.getZk().getData(zKConfig.getRootPath(), false, null));
	}

	public List<Data> getNodeDatas() throws Exception{
		  return getNodeData().getDatas();
	}

	public List<Data> getParentNodeDatas() throws Exception{
		  return getParentNodeData().getDatas();
	}

}
