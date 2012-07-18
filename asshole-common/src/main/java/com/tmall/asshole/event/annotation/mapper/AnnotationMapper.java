package com.tmall.asshole.event.annotation.mapper;

import java.lang.reflect.Field;
import java.util.Map;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.tmall.asshole.event.annotation.Extends;
import com.tmall.asshole.event.annotation.Ignore;

/**
 *
 * @author hemaodong
 * @date 2012-5-28 ÏÂÎç01:56:06
 *
 */
public class AnnotationMapper extends MapperWrapper {

	private ExtendsAnnoMapper extendsAnnoMapper;

	private IgnoreAnnoMapper ignoreAnnoMapper;

	public AnnotationMapper(Mapper mapper) {
		super(mapper);
		extendsAnnoMapper = (ExtendsAnnoMapper) lookupMapperOfType(ExtendsAnnoMapper.class);
		ignoreAnnoMapper = (IgnoreAnnoMapper) lookupMapperOfType(IgnoreAnnoMapper.class);
	}

	protected void processAnnotations(final Class<?>[] classes) {
		for (Class<?> clazz : classes) {
			processExtendsAnnotation(clazz);
			for (Field field : clazz.getDeclaredFields()) {
				processIgnoreAnnotation(field);
			}
		}
	}

	private void processIgnoreAnnotation(Field field) {
		Ignore ignore = field.getAnnotation(Ignore.class);
		if (ignore != null) {
			if (ignoreAnnoMapper != null) {
				ignoreAnnoMapper.addIgnoreFields(field);
			} else {
				throw new RuntimeException();//TODO
			}
		}
	}

	private void processExtendsAnnotation(final Class<?> clazz) {
		Extends extendsAnno = clazz.getAnnotation(Extends.class);
		if (extendsAnno != null) {
			if (extendsAnnoMapper != null) {
				extendsAnnoMapper.addExtendsTypes(clazz, extendsAnno.value());
			} else {
				throw new RuntimeException();//TODO
			}
		}
	}

	@Override
	public <T> Map<String, Object> processAnnotation(T t, Map<String, Object> fieldMap) throws Exception{
		return super.processAnnotation(t, fieldMap);
	}

	public static void main(String[] args) throws Exception {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring-base.xml");
		AnnotationConfiguration config = (AnnotationConfiguration)context.getBean("annotationConfiguration");
		config.init();
	}
}
