package com.tmall.asshole.util;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

/**
 * @author jiuxian.tjo
 */
public class JsonUtilTest {
	
//	@Test
//	public void testToJava(){
//		
//		
//		
//	}
	
	
	@Test
	public void testToJson(){
		
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("int", new Integer(1));
		map.put("long", new Long(1));
		map.put("byte", new Byte((byte) 1));
		System.out.println(JsonUtil.toJson(map));
	}
	

}
