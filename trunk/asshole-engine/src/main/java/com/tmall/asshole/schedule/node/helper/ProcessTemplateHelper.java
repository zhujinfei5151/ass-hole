package com.tmall.asshole.schedule.node.helper;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tmall.asshole.common.Event;
import com.tmall.asshole.engine.process.template.Parser;
import com.tmall.asshole.exception.FolderNotFountException;
import com.tmall.asshole.exception.NodeNotFountException;
import com.tmall.asshole.exception.ProcessTemplateNotFoundException;
import com.tmall.asshole.schedule.node.Node;
import com.tmall.asshole.schedule.node.ProcessTemplate;

/**
 * 
 * @author tangjinou (jiuxian.tjo)
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
			URL resource = null;
			try{
				   resource = ProcessTemplateHelper.class.getResource(folder);
			}catch (Exception e) {
				  throw new FolderNotFountException("can't find the folder, name="+folder);
			}
			if(resource == null){
				  throw new FolderNotFountException("can't find the folder, name="+folder);
			}
			
			
			if (resource.getProtocol().equals("jar")) {
			        String jarPath = resource.getPath().substring(5, resource.getPath().indexOf("!")); //strip out only the JAR file
			        JarFile jar = new JarFile(URLDecoder.decode(jarPath, "UTF-8"));
			        Enumeration<JarEntry> entries = jar.entries(); //gives ALL entries in jar
			        while(entries.hasMoreElements()) {
			          String name = entries.nextElement().getName();
			          if(name.endsWith("/")){
			        	  continue;
			          }
			          if (name.startsWith(folder.startsWith("/")?folder.substring(1):folder)) { 
			            paths.add(name.startsWith("/")?name:("/"+name));
			          }
			        }
				
			}else{
				String[] subfilenames = null;
				File f = new File(resource.getPath());
				subfilenames = f.list();
			
				if(subfilenames == null){
					throw new FolderNotFountException("can't find the subfilenames, path="+resource.getPath());
				}
				
				for (String subfilename : subfilenames) {
					if(!folder.endsWith("/")){
						folder=folder+"/";
					}
					paths.add(folder+subfilename);
				}
			
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
	/**
	 * find list<Node> by processName and className
	 * 
	 * @param className
	 * @return
	 * @throws Exception
	 */
	public static List<Node> find(String processName,Class<? extends Event> className) throws Exception{
		List<Node> l  = new ArrayList<Node>();
		ProcessTemplate processTemplate = ProcessTemplateHelper.getProcessTemplate(processName);
		for (Node node :processTemplate.nodes) {
			  if(node.getClassname().equals(className.getName())){
				   l.add(node);
			  }
		}
		return l;
	}
	
	public static Node find(String processName, Class<? extends Event> className,String nodeName) throws Exception{
		ProcessTemplate processTemplate = ProcessTemplateHelper.getProcessTemplate(processName);
		for (Node node :processTemplate.nodes) {
			  if(node.getClassname().equals(className.getName())&& node.getName().equals(nodeName)){
				   return node;
			  }
		}
		return null;
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
