package com.tmall.asshole.event.annotation.mapper;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class AnnotationConfiguration {

	private static final String ANNOTATIONS_PACKAGE = "com.tmall.asshole.event.common";

	@SuppressWarnings("serial")
	private Set<String> annoPackage = new HashSet<String>() {
		{
			add(ANNOTATIONS_PACKAGE);
		}
	};

	private Mapper mapper;

	private Class<?>[] allAnnotationClasses;

	public void init() throws Exception {
		allAnnotationClasses = getClasses(annoPackage);
		Mapper mapper = new DefaultMapper();// 这个是默认被装饰类
		mapper = new IgnoreAnnoMapper(mapper);
		mapper = new ExtendsAnnoMapper(mapper);
		mapper = new AnnotationMapper(mapper);// 这个是注解的最外层的装饰类，一定要放在最后
		this.mapper = mapper;
		initAnnotationMap(getAllAnnotationClasses());
	}

	private void initAnnotationMap(Class<?>[] classes) throws Exception {
		AnnotationMapper annotationMapper = (AnnotationMapper) getMapper().lookupMapperOfType(AnnotationMapper.class);
		annotationMapper.processAnnotations(classes);
	}

	public Mapper getMapper() throws Exception {
		if (mapper == null) {
			synchronized (this) {
				if (mapper == null) {
					this.init();
				}
			}
		}
		return mapper;
	}

	public Class<?>[] getAllAnnotationClasses() {
		return allAnnotationClasses;
	}

	/**
	 * 扫描给定包及子包内的所有类
	 *
	 * @param packageName
	 *            给定的包名
	 * @return The classes
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	@SuppressWarnings("rawtypes")
	private static Class[] getClasses(Set<String> annoPackage) throws ClassNotFoundException, IOException {
		Iterator<String> it = annoPackage.iterator();
		ArrayList<Class> classes = new ArrayList<Class>();
		while (it.hasNext()) {
			String packageName = it.next();
			Class cla = AnnotationConfiguration.class;
			String path = packageName.replace('.', '/');
			Enumeration<URL> resources = cla.getClassLoader().getResources(path);
			List<File> dirs = new ArrayList<File>();

			while (resources.hasMoreElements()) {
				URL resource = resources.nextElement();
				dirs.add(new File(resource.getFile()));
			}
			for (File directory : dirs) {
				classes.addAll(findClasses(directory, packageName));
			}
		}
		return classes.toArray(new Class[classes.size()]);
	}

	/**
	 * 找到制定包内的所有class文件
	 *
	 * @param directory
	 *            目录名称
	 * @param packageName
	 *            包名称
	 * @return The classes
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings("rawtypes")
	private static List<Class> findClasses(File directory, String packageName) throws ClassNotFoundException {
		List<Class> classes = new ArrayList<Class>();
		if (!directory.exists()) {
			return classes;
		}
		File[] files = directory.listFiles();
		for (File file : files) {
			if (file.getName().endsWith(".class")) {
				classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
			}
		}
		return classes;
	}

	public void setAnnoPackage(List<String> annoPackage) {
		this.annoPackage.addAll(annoPackage);
	}

}
