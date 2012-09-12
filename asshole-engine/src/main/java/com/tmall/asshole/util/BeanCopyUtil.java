package com.tmall.asshole.util;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Map.Entry;
/**
 * 
 * @author tangjinou (jiuxian.tjo)
 *
 */
public class BeanCopyUtil {
	
	public static  void copy(Object o, Map<String,Object> map){
		
		for (Entry<String, Object> entry :map.entrySet()) {
				try{
					// 现在只拷贝一级
					Field field = o.getClass().getDeclaredField(entry.getKey());
					
					Class<?> parent =  o.getClass();
					while(field ==null && parent.getSuperclass()!=null){
						if(isClassContainsFiled(entry.getKey(),parent.getSuperclass().getDeclaredFields())){
							   field = parent.getSuperclass().getDeclaredField(entry.getKey());
						}
						parent = parent.getSuperclass();
					}
					
					if(field==null){
						continue;
					}
					
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
	
	public static boolean isClassContainsFiled(String name,Field[] declaredFields){
		for (Field f : declaredFields) {
			if(f.toString().trim().contains(name)){
				return true;
			}
		}
		return false;
	}

}
