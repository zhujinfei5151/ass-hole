package com.tmall.asshole.event.annotation.mapper;

import java.util.Map;

/**
 *
 * @author hemaodong
 * @date 2012-5-28 обнГ01:56:06
 *
 */
public class DefaultMapper implements Mapper {

	public Mapper lookupMapperOfType(Class<?> type) {
		return null;
	}

	public <T> Map<String, Object> processAnnotation(T t, Map<String, Object> fieldMap) {
		return fieldMap;
	}
}
