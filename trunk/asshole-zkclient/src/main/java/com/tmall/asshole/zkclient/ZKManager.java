package com.tmall.asshole.zkclient;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.ZooKeeper.States;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.server.auth.DigestAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
/****
 * 
 * @author jiuxian.tjo
 *
 */
public class ZKManager{
	private static transient Log log = LogFactory.getLog(ZKManager.class);
	private ZooKeeper zk;
	private List<ACL> acl = new ArrayList<ACL>();
	//private Properties properties;
	@Autowired
	private ZKConfig zkConfig;
	@Autowired
	private ZKClientVersion version;
	

	public ZKManager(Watcher watch) throws Exception{
		String authString = zkConfig.getUsername()
				+ ":"+ zkConfig.getPassword();
		zk = new ZooKeeper(zkConfig.getZkConnectString(), zkConfig.getZkSessionTimeout(),
				watch);
		zk.addAuthInfo("digest", authString.getBytes());
		acl.add(new ACL(ZooDefs.Perms.ALL, new Id("digest",
				DigestAuthenticationProvider.generateDigest(authString))));
		acl.add(new ACL(ZooDefs.Perms.READ, Ids.ANYONE_ID_UNSAFE));
	}
	public void close() throws InterruptedException{
		log.info("Zooker close");
		this.zk.close();
	}

	public ZKConfig getZkConfig() {
		return zkConfig;
	}
	public boolean checkZookeeperState(){
		return zk.getState().isAlive() && (zk.getState() == States.CONNECTED);
	}

	public void initial() throws Exception {
		if(zk.exists(zkConfig.getRootPath(), false) == null){
			ZKTools.createPath(zk, zkConfig.getRootPath(), CreateMode.PERSISTENT, acl);
			checkParent(zk,zkConfig.getRootPath());
			zk.setData(zkConfig.getRootPath(),version.getVersion().getBytes(),-1);
		}else{
			checkParent(zk,zkConfig.getRootPath());
			byte[] value = zk.getData(zkConfig.getRootPath(), false, null);
			if(value == null){
				zk.setData(zkConfig.getRootPath(),version.getVersion().getBytes(),-1);
			}else{
				String dataVersion = new String(value);
				if(version.isCompatible(dataVersion)==false){
					throw new Exception("当前版本 "+ version.getVersion() +" 和服务器版本 " + dataVersion + " 不一致");
				}
				log.info("版本:" + version.getVersion() + " 服务器版本: " + dataVersion);
			}
		}
	}
	public static void checkParent(ZooKeeper zk, String path) throws Exception {
		String[] list = path.split("/");
		String zkPath = "";
		for (int i =0;i< list.length -1;i++){
			String str = list[i];
			if (str.equals("") == false) {
				zkPath = zkPath + "/" + str;
				if (zk.exists(zkPath, false) != null) {
					byte[] value = zk.getData(zkPath, false, null);
					if(value != null){
						String tmpVersion = new String(value);
					   if(tmpVersion.indexOf("taobao-pamirs-schedule-") >=0){
						throw new Exception("\"" + zkPath +"\"  is already a schedule instance's root directory, its any subdirectory cannot as the root directory of others");
					}
				}
			}
			}
		}
	}	
	
	public List<ACL> getAcl() {
		return acl;
	}
	public ZooKeeper getZooKeeper() throws Exception {
		if(this.checkZookeeperState()==false){
			throw new Exception("Zookeeper["+ zkConfig.getZkConnectString()+"] connect error" );
		}
		return this.zk;
	}
	
	}
