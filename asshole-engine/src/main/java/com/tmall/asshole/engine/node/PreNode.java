package com.tmall.asshole.engine.node;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;


/***
 * Ôö¼ÓÇ°ÖÃnode ÅÐ¶Ï 
 * 
 * @author jiuxian.tjo
 *
 */
@XStreamAlias("pre-node")
public class PreNode {
   
	@XStreamAsAttribute
	public String name;
	
	public PreNode(String name){
		this.name=name;
	}
	
	@Override
	public String toString() {
		return "PreNode[name="+name+"]";
	}

} 
