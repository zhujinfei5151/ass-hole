package com.tmall.asshole.schedule.node.helper;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tmall.asshole.engine.process.template.Parser;
import com.tmall.asshole.exception.NodeNotFountException;
import com.tmall.asshole.exception.ProcessTemplateNotFoundException;
import com.tmall.asshole.schedule.node.Node;
import com.tmall.asshole.schedule.node.ProcessTemplate;

/***
 * 
 * @author tangjinou
 *
 */
public class ProcessTemplateHelper {
	
	final static Logger logger = LoggerFactory.getLogger(ProcessTemplateHelper.class);
   
	public static Map<String, ProcessTemplate> processes = new HashMap<String, ProcessTemplate>();
	
	
	public static ProcessTemplate getProcessTemplate(String name) {
		return processes.get(name);
	}

	public static ProcessTemplate putProcessTemplate(ProcessTemplate p) {
		if(p==null) return null;
		return processes.put(p.name, p);
	}
	
	
	/**
	 * deploy a processTemplate by file folder
	 * @param path
	 * @throws Exception 
	 */
	public static void deploy(List<String> folders)  throws Exception {
	    List<String> paths= new ArrayList<String>();
		for (String folder : folders) {
			URL resource = ProcessTemplateHelper.class.getResource(folder);
			File f = new File(resource.getPath());
			String[] subfilenames = f.list();
			for (String subfilename : subfilenames) {
				if(!folder.endsWith("/")){
					folder=folder+"/";
				}
				paths.add(folder+subfilename);
			}
		}
		for (String path : paths) {
			ProcessTemplateHelper.deploy(path);
		}
	}
	
	
	/**
	 * deploy a processTemplate by file path
	 * @param path
	 * @throws Exception 
	 */
	public static void deploy(String path) throws Exception {
		ProcessTemplate template = null;
		InputStream is = ProcessTemplateHelper.class.getResourceAsStream(path);
		if(null == is){
			logger.error("definition not found exception on path : " + path);
			throw new ProcessTemplateNotFoundException("process path: " + path);
		}
		template = Parser.parse(is);
		ProcessTemplateHelper.putProcessTemplate(template);
	}
	/***
	 * find a node in a processTemplate
	 * 
	 * @param processName
	 * @param node
	 * @return
	 * @throws Exception
	 */
	public static Node find(String processName, String node) throws Exception{
		ProcessTemplate processTemplate = ProcessTemplateHelper.getProcessTemplate(processName);
		if(processTemplate == null){
			logger.error("cant find the processTemplate,  processName= " + processName);
			throw new ProcessTemplateNotFoundException("cant find the processTemplate,  processName= " + processName);
		}
		for(Node n : processTemplate.nodes){
			   if(n.getName().equals(node.trim())){
				    return n;
			   }
		}
		logger.error("can't find the node, name="+node+" in the processTemplate, name="+processName);
		throw new NodeNotFountException("can't find the node, name="+node+" in the processTemplate, name="+processName);
	}
	
	/****
	 * 获取流程的ID
	 * 
	 * @return
	 */
	public static Long createProcessInstanceID(){
		int random=(int)(Math.random()*100) + 100;
		String process_instance_id_str= System.currentTimeMillis() +"" + random;
		return Long.parseLong(process_instance_id_str);
	}
	
	
}
