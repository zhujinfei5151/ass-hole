package com.tmall.asshole.event.filter.codec.json;


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tmall.asshole.event.filter.codec.ProtocolCodecFactory;
import com.tmall.asshole.event.filter.codec.ProtocolDecoder;
import com.tmall.asshole.event.filter.codec.ProtocolEncoder;
/****
 * 非标准JSON协议 为了兼容
 *  
 * @author tangjinou
 *
 * @param <T>
 */
public class JSONProtocol<T> implements ProtocolDecoder<T> ,ProtocolEncoder<T>,ProtocolCodecFactory<T>{
	public T decode(byte[] bytes,T o)throws Exception {
		
		String json =new String(bytes);
		Map<String, Object> map = JsonUtil.toJava(json);
		for (String key : map.keySet()) {
			try {
				Field field  = null;
				//需要排查 id 和 hashnum ,去除 防止被覆盖 导致 更新无法找到
				if(key.equals("id")||key.equals("hash_num")){
					continue;
				}
				if(isClassContainsFiled(key,o.getClass().getDeclaredFields())){
					field  = o.getClass().getDeclaredField(key);
				}
				Class<?> parent =  o.getClass();
				while(field ==null && parent.getSuperclass()!=null){
				   // can't find the field in the son class,son to find in the super class
					if(isClassContainsFiled(key,parent.getSuperclass().getDeclaredFields())){
					   field = parent.getSuperclass().getDeclaredField(key);
					}
					parent = parent.getSuperclass();
				}
				field.setAccessible(true);
				field.set(o, map.get(key));
			} catch (NoSuchFieldException e) {

			}
		}
		return  o;
	}
	public byte[] encode(T t,Class<? extends T> clz) throws Exception {
			//Field[] fields = clz.getDeclaredFields();
	        Class<?> parent =  clz;
			List<Field> fields = new ArrayList<Field>(); 
			
			Field[] _fields = clz.getDeclaredFields();
			for (Field f : _fields) {
				fields.add(f);
			}
			while(parent.getSuperclass()!=null){
				parent = parent.getSuperclass();
				_fields = parent.getDeclaredFields();
				for (Field f : _fields) {
					fields.add(f);
				}
			}
			
			Map<String, Object> map = new HashMap<String, Object>();
			if (fields != null && fields.size() > 0) {
				for (Field field : fields) {
					String key = field.getName();
					field.setAccessible(true);
					Object value = field.get(t);
					map.put(key, value);
				}
			}
			
			return JsonUtil.toJson(map).getBytes();
	}
	
	private boolean isClassContainsFiled(String name,Field[] declaredFields){
		for (Field f : declaredFields) {
			if(f.toString().trim().contains(name)){
				return true;
			}
		}
		return false;
	}
	public ProtocolEncoder<T> getEncoder() throws Exception {
		return this;
	}
	public ProtocolDecoder<T> getDecoder() throws Exception {
		return this;
	}

}
