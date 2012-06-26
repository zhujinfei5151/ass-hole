package com.tmall.asshole.zkclient;

import java.lang.reflect.Method;

import org.apache.zookeeper.server.ZooKeeperServerMain;
/***
 * 
 * @author tangjinou
 *
 */
public class ZKServerUtil {
	
	private static Thread zkServerThread;
	private static ZooKeeperServerMain  serverMain;
	public static void  start() {
		zkServerThread= new Thread(
				new Runnable() {
					public void run() {
						serverMain=new ZooKeeperServerMain();
						serverMain.main(new String[]{"2181","D:/conf"});
					}
	    });
		System.out.println("zk server is running...... pls wait 10 seconds");
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
		}
		zkServerThread.start();
	}
	
	public static void close() throws Exception{
			Method shutdown = ZooKeeperServerMain.class.getDeclaredMethod("shutdown");
			shutdown.setAccessible(true);
			shutdown.invoke(serverMain);			
	}

}
