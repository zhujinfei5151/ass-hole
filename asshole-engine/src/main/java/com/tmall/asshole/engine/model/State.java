package com.tmall.asshole.engine.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.tmall.asshole.engine.signal.Signal;


/***
 * 
 * 
 * @modified by jiuxian.tjo
 *
 */
public class State {

	public String name;
	public List<Path> nexts;
	public List<String> preNodes;

	// signal point
	public Signal enter;
	public Signal exec;
	public Signal exit;

	public State(String name) {
		this.name = name;
		this.nexts = new ArrayList<Path>();
		this.preNodes = new ArrayList<String>();
	}

	public Path to(State state) {
		Path path = new Path();
		path.to = state.name;
		nexts.add(path);
		return path;
	}

	public void execute(Map<String, Object> context) {
		if (enter != null)
			enter.dispatch(context);
		if (exec != null)
			exec.dispatch(context);
		if (exit != null)
			exit.dispatch(context);
	}
}
