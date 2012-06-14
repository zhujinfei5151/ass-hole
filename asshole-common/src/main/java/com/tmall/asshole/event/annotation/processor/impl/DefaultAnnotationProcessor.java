package com.tmall.asshole.event.annotation.processor.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import com.tmall.asshole.common.Event;
import com.tmall.asshole.event.annotation.mapper.AnnotationConfiguration;
import com.tmall.asshole.event.annotation.processor.AnnotationProcessor;

public class DefaultAnnotationProcessor implements AnnotationProcessor {

	@Resource
	private AnnotationConfiguration annotationConfiguration;

	public Object processEvent(String className, Event event) throws Exception {
		return null;
	}

	public <T> Map<String, Object> processAnnotation(T t) throws Exception {
		return annotationConfiguration.getMapper().processAnnotation(t, new HashMap<String, Object>());
	}

}
