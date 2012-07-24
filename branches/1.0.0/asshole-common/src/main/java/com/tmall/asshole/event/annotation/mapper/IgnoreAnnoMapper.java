package com.tmall.asshole.event.annotation.mapper;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class IgnoreAnnoMapper extends MapperWrapper {

	private Set<Field> fieldSet = new HashSet<Field>();

	public IgnoreAnnoMapper(Mapper mapper) {
		super(mapper);
	}

	public void addIgnoreFields(Field field) {
		fieldSet.add(field);
	}

	@Override
	public <T> Map<String, Object> processAnnotation(T t, Map<String, Object> fieldMap) throws Exception {
		Class<?> clazz = t.getClass();
		for (Field field : clazz.getDeclaredFields()) {
			if (fieldSet.contains(field)) {
				fieldMap.remove(field.getName());
			} else {
				field.setAccessible(true);
				fieldMap.put(field.getName(), field.get(t));
			}
		}

		return super.processAnnotation(t, fieldMap);
	}
}
