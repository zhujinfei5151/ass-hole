package com.tmall.asshole.zkclient;

import java.util.ArrayList;
import java.util.List;

import org.apache.zookeeper.WatchedEvent;

import junit.framework.Assert;
import junit.framework.TestCase;

import com.tmall.asshole.zkclient.data.ChangeData;
import com.tmall.asshole.zkclient.data.Data;
import com.tmall.asshole.zkclient.data.NodeData;

/***
 * 
 * @author tangjinou
 *
 */
public class ZkclientTest  extends TestCase{
	
	static ZKConfig zkConfig1;
	
	static ZKConfig zkConfig2;
	
	static  ZKConfig zkConfig3;
	
	
	static{
		ZKServerUtil.start();	
	
		zkConfig1=new ZKConfig();
		zkConfig1.setRootPath("/test");
		zkConfig1.setZkConnectString("localhost:2181");
		zkConfig1.setZkSessionTimeout(2000);
		zkConfig1.setLocalIPAddressForTest("192.168.1.1");
		
		zkConfig2=new ZKConfig();
		zkConfig2.setRootPath("/test");
		zkConfig2.setZkConnectString("localhost:2181");
		zkConfig2.setZkSessionTimeout(2000);
		zkConfig2.setLocalIPAddressForTest("192.168.1.42");
		
		zkConfig3=new ZKConfig();
		zkConfig3.setRootPath("/test");
		zkConfig3.setZkConnectString("localhost:2181");
		zkConfig3.setZkSessionTimeout(2000);
		zkConfig3.setLocalIPAddressForTest("192.168.1.43");
		
		
	}

	protected List<ChangeData> changeDatas1=new ArrayList<ChangeData>();
	
	protected List<ChangeData> changeDatas2=new ArrayList<ChangeData>();
	
	protected List<ChangeData> changeDatas3=new ArrayList<ChangeData>();
	
	public void testBasicZkClientRun(){
		 ZKClient client1 = null;
    
		try {
		
			client1 = new ZKClient(new INodeChange() {
			public void onChange(List<String> machines) {
				changeDatas1.add(new ChangeData(machines));
			}
		}, zkConfig1);
		
			client1.start();
			Thread.sleep(10000);
			
			Assert.assertEquals(1, changeDatas1.size());
			
			Assert.assertNotNull(changeDatas1.get(0).getMachines());
			
			Data data = new Data("1",Boolean.TRUE);
			
			List<Data> dataL = new ArrayList<Data>();
			
			dataL.add(data);
					
			client1.reSetNodeData(dataL);
			
			Thread.sleep(2000);
			
			List<Data> newDataL = client1.getNodeDatas();
			
			Assert.assertEquals(1, newDataL.size());
			
			Assert.assertEquals(Boolean.TRUE, newDataL.get(0).getT());
			
		} catch (Exception e) {
		   fail();
		} finally{
			changeDatas1.clear();
			client1.close();
		}
	}

	public void testBasicMutiZkClientRun(){
    
		final ZKClient client1 = new ZKClient(new INodeChange() {
			public void onChange(List<String> machines) {
				changeDatas1.add(new ChangeData(machines));
			}
		}, zkConfig1);
		
		
		final ZKClient client2 = new ZKClient(new INodeChange() {
			public void onChange(List<String> machines) {
				changeDatas2.add(new ChangeData(machines));
			}
		}, zkConfig2);
		
		final ZKClient client3 = new ZKClient(new INodeChange() {
			public void onChange(List<String> machines) {
				changeDatas3.add(new ChangeData(machines));
			}
		}, zkConfig3);
		
		
		try {
			//��һ̨�����Ѿ���������
			client1.start();
			Thread.sleep(3000);
			//�ڶ�̨�����Ѿ���������
			client2.start();
			Thread.sleep(5000);
			
			Assert.assertEquals(2 , changeDatas1.size());
			
			Assert.assertEquals(1, changeDatas2.size());

			Assert.assertEquals(2, changeDatas1.get(1).getMachines().size());

			Assert.assertEquals(2, changeDatas2.get(0).getMachines().size());
			
			//�رյ�һ̨����
			client1.close();
			Thread.sleep(5000);
			//�ڶ�̨�����յ������Ϣ Ŀǰ�յ�������Ϣһ���Ǹ�ڵ㷢�͵� һ�����ӽڵ㷢�͵�
			Assert.assertEquals(2, changeDatas2.size());
			Assert.assertEquals(1, changeDatas2.get(1).getMachines().size());
			
			//���½���һ̨������
			client1.start();
			Thread.sleep(5000);
			Assert.assertEquals(3, changeDatas2.size());
			
			
			//�ٹرյ�һ̨����
			client1.close();
			Thread.sleep(5000);
			Assert.assertEquals(4, changeDatas2.size());
			
			
			//���¿�����һ̨�����͵���̨����
			client1.start();
			client3.start();
			Thread.sleep(5000);
			//Ŀǰ��������̨����
			Assert.assertEquals(3, changeDatas2.get(changeDatas2.size()-1).getMachines().size());
			Assert.assertEquals(6, changeDatas2.size());
			
			

		} catch (Exception e) {
		   fail();
		} finally{
			changeDatas1.clear();
			changeDatas2.clear();
			changeDatas2.clear();
			client1.close();
			client2.close();
			client3.close();
		}
		
	}
	
	public void testZKServerClose(){
		     ZKClient client1 = null;
		    
			try {
				client1 = new ZKClient(new INodeChange() {
				public void onChange(List<String> machines) {
					changeDatas1.add(new ChangeData(machines));
				}
			}, zkConfig1);
			
				client1.start();
				Thread.sleep(10000);
				Assert.assertEquals(1, changeDatas1.size());
				
				ZKServerUtil.close();
				
				Thread.sleep(1000000);
				
				
			} catch (Exception e) {
				   fail();
			} finally{
					changeDatas1.clear();
					changeDatas2.clear();
					changeDatas2.clear();
					client1.close();
			}
		
	}
	
	

}
