/**
 *
 */
package com.tmall.asshole.event.annotation.processor;

import java.util.Map;

import com.tmall.asshole.common.Event;

/**
 * @author hemaodong
 * @date 2012-5-29 上午11:14:34
 *
 */
public interface AnnotationProcessor {

	/**
	 *
	 * @param className
	 * 		必须是全限定名
	 * @param event
	 * @return
	 * @throws Exception
	 */
	Object processEvent(String className, Event event) throws Exception;

	<T> Map<String, Object> processAnnotation(T t) throws Exception;
}
