package com.tmall.asshole.util;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Map.Entry;
/**
 * @author tangjinou
 */
public class BeanCopyUtil {
	
	public static  void copy(Object o, Map<String,Object> map){
		
		for (Entry<String, Object> entry :map.entrySet()) {
				try{
					// 现在只拷贝一级
					Field field = o.getClass().getDeclaredField(entry.getKey());
					Object value = entry.getValue();
					//只有名字和类型一直时候才拷贝
					if(field.getType() ==value.getClass())
					{
						field.setAccessible(true);
						field.set(o, value);
					}
				}catch (Exception e) {
					
				}
		}
		
		
	}

}
