package com.tmall.asshole.zkclient.data;


import com.tmall.asshole.zkclient.data.Data;
import com.tmall.asshole.zkclient.data.NodeData;
import com.tmall.asshole.zkclient.data.PersistenceUtil;

import junit.framework.Assert;
import junit.framework.TestCase;
/**
 * 
 * @author tangjinou (jiuxian.tjo)
 *
 */
public class PersistenceUtilTest extends TestCase{
	

	
	public void testPersistenceUtil(){
		 try{
		     
		     NodeData nodeData =new NodeData("192.168.1.1");
		     
		     TestData testData= new TestData();
		     
		     
		     testData.setName("hh");
		     
		     
		     Data<TestData> data = new Data<TestData>("testing",testData);
		     
		     nodeData.addData(data);
		     
		     byte[] bytes = PersistenceUtil.serializable(nodeData);
		     
		     NodeData newNodeData = PersistenceUtil.deSerializable(bytes);
		     
		     assertEquals(nodeData.getName(), newNodeData.getName());
		     
		     assertTrue(newNodeData.getDatas().get(0).getT() instanceof TestData);
		     
		     TestData newTestData= (TestData)(newNodeData.getDatas().get(0).getT());
		     
		     assertEquals(newTestData.getName(), testData.getName());
		     
		     
		 }catch (Exception e) {
		  
		      e.printStackTrace();
		 
		       Assert.fail();
		   
		 }
			
		
		
	}

}
