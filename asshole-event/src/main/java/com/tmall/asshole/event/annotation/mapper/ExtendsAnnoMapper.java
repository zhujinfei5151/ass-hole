package com.tmall.asshole.event.annotation.mapper;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class ExtendsAnnoMapper extends MapperWrapper {

	private Map<Class<?>, Boolean> map = new HashMap<Class<?>, Boolean>();

	public ExtendsAnnoMapper(Mapper mapper) {
		super(mapper);
	}

	public void addExtendsTypes(Class<?> clazz, Boolean value) {
		map.put(clazz, value);
	}

	@Override
	public <T> Map<String, Object> processAnnotation(T t, Map<String, Object> fieldMap) throws Exception {
		Class<?> clazz = t.getClass();
		if (map.containsKey(clazz) && map.get(clazz)) {
			Class<?> parent = clazz.getSuperclass();
			while (parent != null) {
				Field[] _fields = parent.getDeclaredFields();
				for (Field f : _fields) {
					f.setAccessible(true);
					fieldMap.put(f.getName(), f.get(t));
				}
				parent = parent.getSuperclass();
			}
		}
		return super.processAnnotation(t, fieldMap);
	}
}
