package com.tmall.asshole.engine.process.template;

import java.io.InputStream;

import junit.framework.Assert;
import junit.framework.TestCase;

import com.tmall.asshole.exception.ParserException;
import com.tmall.asshole.schedule.node.ProcessTemplate;



public class ParserTest extends TestCase {

	public void testParse(){
		InputStream is = this.getClass().getResourceAsStream("/process/testProcess.xml");
		ProcessTemplate process = Parser.parse(is);
		
		Assert.assertNotNull(process);
		
		Assert.assertTrue(process.nodes.size()>0);
		
		 Assert.assertEquals(process.nodes.get(0).getName()
				 , "order_create");
		
	    Assert.assertEquals(process.nodes.get(0).getClassname(), "com.tmall.asshole.mock.ep.TestEvent1");
		
		
		
	}
	
	public void testParseWithException(){
		InputStream is = this.getClass().getResourceAsStream("/process/fake.xml");
		try{
			Parser.parse(is);
		}catch(Exception e){
			if(e instanceof ParserException){
				Assert.assertTrue(true);
				return;
			}
		}
		Assert.assertTrue(false);
	}
}
