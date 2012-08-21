package com.tmall.asshole.event.annotation.mapper;

import java.util.Map;

/**
 *
 * @author hemaodong
 * @date 2012-5-28 обнГ03:49:30
 *
 */
public interface Mapper {

	Mapper lookupMapperOfType(Class<?> type);

	<T> Map<String, Object> processAnnotation(T t, Map<String, Object> fieldMap) throws Exception;
}
