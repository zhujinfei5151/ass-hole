package com.tmall.asshole.engine.helper;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JsonWriter.Node;
import com.tmall.asshole.engine.node.Decision;
import com.tmall.asshole.engine.node.Definition;
import com.tmall.asshole.engine.node.End;
import com.tmall.asshole.engine.node.Invokable;
import com.tmall.asshole.engine.node.InvokeBean;
import com.tmall.asshole.engine.node.InvokeClass;
import com.tmall.asshole.engine.node.PreNode;
import com.tmall.asshole.engine.node.Service;
import com.tmall.asshole.engine.node.Start;
import com.tmall.asshole.engine.node.Transition;


public class XStreamHelper {

	public volatile static XStream xstream;

	private XStreamHelper() {
	}

	public static XStream getInstance() {
		// 双重检查加锁
		if (xstream == null) {
			synchronized (XStream.class) {
				// 延迟实例化,需要时才创建
				if (xstream == null)
					xstream = new XStream();
				Class<?>[] classes = new Class<?>[] { Definition.class, Start.class,
						End.class, Node.class, Transition.class,PreNode.class,
						Decision.class, Service.class, Invokable.class,
						InvokeClass.class, InvokeBean.class };
				xstream.processAnnotations(classes);
				xstream.aliasSystemAttribute(null, "class");
			}
		}
		return xstream;
	}
}
