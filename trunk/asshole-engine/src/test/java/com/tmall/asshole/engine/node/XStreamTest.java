package com.tmall.asshole.engine.node;

import java.util.ArrayList;

import junit.framework.TestCase;

import com.thoughtworks.xstream.XStream;

public class XStreamTest extends TestCase {

	public Definition definition;
	public Start start;
	public Service service;
	public Decision decision;
	public Transition transition;
	public End end;

	public XStream xstream;

	public void setUp() {
		// init xstream
		xstream = new XStream();
		@SuppressWarnings("rawtypes")
		Class[] classes = new Class[] { Definition.class, Start.class,
				End.class, Node.class, Transition.class, Decision.class,
				Service.class, Invokable.class, InvokeClass.class,
				InvokeBean.class };
		xstream.processAnnotations(classes);
		xstream.aliasSystemAttribute(null, "class");

		// start
		start = new Start("start");
		start.transition = new Transition("end", "a > 5");
		// end
		end = new End("end");
		// decision
		decision = new Decision("decision");
		decision.transitions = new ArrayList<Transition>();
		decision.transitions.add(new Transition("end", "a>3"));
		decision.transitions.add(new Transition("end", "a<=3"));
		// service
		service = new Service("service");
		service.invokables = new ArrayList<Invokable>();
		Invokable clazz = new InvokeClass("com.taobao.pamirs.Node", "parse",
				"void", "ab");
		service.invokables.add(clazz);
		service.transitions = new ArrayList<Transition>();
		service.transitions.add(new Transition("end", "a>3"));
		service.transitions.add(new Transition("end", "a<=3"));
		// definition
		definition = new Definition("definition");
		definition.nodes = new ArrayList<Node>();
		definition.ends = new ArrayList<End>();
		definition.start = start;
		definition.nodes.add(service);
		definition.nodes.add(decision);
		definition.ends.add(end);
	}

	public void testStart() {
		String xml = xstream.toXML(start);
		System.out.println(xml);
		Start start = (Start) xstream.fromXML(xml);
		System.out.println(start);
	}

	public void testEnd() {
		String xml = xstream.toXML(end);
		System.out.println(xml);
		End end = (End) xstream.fromXML(xml);
		System.out.println(end);
	}

	public void testDecision() {
		String xml = xstream.toXML(decision);
		System.out.println(xml);
		Decision decision = (Decision) xstream.fromXML(xml);
		System.out.println(decision);
	}

	public void testService() {
		String xml = xstream.toXML(service);
		System.out.println(xml);
		Service service = (Service) xstream.fromXML(xml);
		System.out.println(service);
	}

	public void testDefinition() {
		String xml = xstream.toXML(definition);
		System.out.println(xml);
		Definition definition = (Definition) xstream.fromXML(xml);
		System.out.println(definition);
	}
}
