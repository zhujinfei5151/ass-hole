package com.tmall.asshole.event.filter.codec.json;


import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
/***
 * 
 * @author jiuxian.tjo
 *
 */
public class JsonUtil {
	
	public static SerializeConfig config=new SerializeConfig();
	
	/****
	 * 将json格式的map 转化存 java对象的map
	 * 
	 * @param map
	 * @return map
	 */
	public static Map<String,Object> toJava(String jsonMap){
		Map<String,Object> map = JSON.parseObject(jsonMap, HashMap.class);
		if(map==null){
			throw new NullPointerException("jsonMap's format is not collect,pls check it");
		}
		for(String key:map.keySet()){
			Object o = map.get(key);
			if(o instanceof JSONObject){
				JSONObject jsonObject=(JSONObject) o;
				try {
					map.put(key, JSON.toJavaObject(jsonObject, Class.forName((String)jsonObject.get("class"))));
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
		return map;
	} 
	
	/***
	 * 将hashmap格式的java对象转成json字符串
	 * 
	 * @param map
	 * @return
	 */
	public static String toJson(Map<String,Object> map){
		config.setAsmEnable(false);
		return JSON.toJSONString(map,config,SerializerFeature.WriteClassName);
	}
	
	
}
