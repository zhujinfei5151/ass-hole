package com.tmall.asshole.event.annotation.processor;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.Assert;

import com.tmall.asshole.event.common.TestEvent;
import com.tmall.asshole.event.util.SpringBeanUtils;

/**
 *
 * @author hemaodong
 * @date 2012-5-30 下午08:30:43
 *
 */
public class AnnotationProcessorTest {

	private AnnotationProcessor annotationProcessor;

	@Before
	public void init() {
		new ClassPathXmlApplicationContext("classpath:spring-base.xml");
		annotationProcessor = (AnnotationProcessor) SpringBeanUtils.getBean("annotationProcessor");
	}

	@Test
	public void testAnnotationProcessor() throws Exception {
//		long start = System.currentTimeMillis();
//		for (int i = 0; i < 1000000; i++) {
			TestEvent event = new TestEvent();
			event.setId(123L);
			event.setBaomingItemID("123");
			Map<String, Object> map = annotationProcessor.processAnnotation(event);
			Assert.notEmpty(map);
//		}
//		long end = System.currentTimeMillis();
//
//		System.out.println(end - start);
		System.out.println(map);
	}
}
