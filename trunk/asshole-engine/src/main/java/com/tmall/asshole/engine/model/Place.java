package com.tmall.asshole.engine.model;


import java.util.HashMap;
import java.util.Map;

public class Place {

	public static Map<String, Process> processes = new HashMap<String, Process>();

	public static Process getProcess(String name) {
		return processes.get(name);
	}

	public static Process putProcess(Process p) {
		if(p==null) return null;
		return processes.put(p.name, p);
	}
}
