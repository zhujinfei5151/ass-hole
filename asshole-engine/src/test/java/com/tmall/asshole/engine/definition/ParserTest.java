package com.tmall.asshole.engine.definition;

import java.io.InputStream;

import com.tmall.asshole.engine.exception.ParserException;


import junit.framework.Assert;
import junit.framework.TestCase;


public class ParserTest extends TestCase {

	public void testParse(){
		InputStream is = this.getClass().getResourceAsStream("/process/trade.xml");
		Assert.assertNotNull(Parser.parse(is));
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
