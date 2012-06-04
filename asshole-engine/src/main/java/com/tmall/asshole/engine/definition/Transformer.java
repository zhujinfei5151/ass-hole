package com.tmall.asshole.engine.definition;

import com.tmall.asshole.engine.model.Path;
import com.tmall.asshole.engine.model.State;
import com.tmall.asshole.engine.node.Decision;
import com.tmall.asshole.engine.node.Definition;
import com.tmall.asshole.engine.node.End;
import com.tmall.asshole.engine.node.Invokable;
import com.tmall.asshole.engine.node.InvokeBean;
import com.tmall.asshole.engine.node.InvokeClass;
import com.tmall.asshole.engine.node.Node;
import com.tmall.asshole.engine.node.PreNode;
import com.tmall.asshole.engine.node.Service;
import com.tmall.asshole.engine.node.Start;
import com.tmall.asshole.engine.node.Transition;
import com.tmall.asshole.engine.signal.Signal;
import com.tmall.asshole.engine.util.SpringUtil;
import com.tmall.asshole.engine.model.Process;


public class Transformer {
	/**
	 *  ≈–∂œ «∑Ò π”√Spring»›∆˜
	 */
	public static boolean isUseSpring=false;
	

	/**
	 * @return the isUseSpring
	 */
	public static boolean isUseSpring() {
		return isUseSpring;
	}

	/**
	 * @param isUseSpring the isUseSpring to set
	 */
	public static void setUseSpring(boolean isUseSpring) {
		Transformer.isUseSpring = isUseSpring;
	}

	/**
	 * transform definition to process
	 * 
	 * @param definition
	 * @return
	 */
	public static Process transform(Definition definition) {
		Process p = new Process(definition.name);
		transfer(p, definition.start);
		for (End end : definition.ends) {
			transfer(p, end);
		}
		for (Node node : definition.nodes) {
			transfer(p, node);
		}
		return p;
	}

	private static void transfer(Process p, Start start) {
		State s = new State(start.name);
		Path path = new Path();
		path.to = start.transition.to;
		path.exp = start.transition.exp;
		s.nexts.add(path);
		p.first = s.name;
		p.states.put(s.name, s);
	}

	private static void transfer(Process p, End end) {
		State s = new State(end.name);
		p.states.put(s.name, s);
	}

	private static void transfer(Process p, Node node) {
		State s = new State(node.name);
		if (node instanceof Service) {
			Service service = (Service) node;
			// Invokable
			for (Invokable invokable : service.invokables) {
				if (invokable instanceof InvokeBean) {
					// TODO
				} else if (invokable instanceof InvokeClass) {
					Signal signal = null;
					InvokeClass invokeClass = (InvokeClass) invokable;
					try {
						if(isUseSpring){
						    signal = new Signal().add(
									SpringUtil.getBean(Class.forName(invokeClass.classPath)),
						    		invokeClass.method,
									invokeClass.args, invokeClass.callback);
						}else{
							signal = new Signal().add(
									Class.forName(invokeClass.classPath)
									.newInstance(), invokeClass.method,
									invokeClass.args, invokeClass.callback);
						}
						
					} catch (Exception e) {
						e.printStackTrace();
					}
					s.exec = signal;
				}
			}
			Path path = null;
			if (service.transitions != null) {
				for (Transition t : service.transitions) {
					path = new Path();
					path.exp = t.exp;
					path.to = t.to;
					s.nexts.add(path);
				}
			}

			if (service.preNodes != null) {
				for (PreNode n : service.preNodes) {
					s.preNodes.add(n.name);
				}
			}

		} else if (node instanceof Decision) {
			Decision decision = (Decision) node;
			Path path = null;
			for (Transition t : decision.transitions) {
				path = new Path();
				path.exp = t.exp;
				path.to = t.to;
				s.nexts.add(path);
			}
		}
		p.states.put(s.name, s);
	}
}
