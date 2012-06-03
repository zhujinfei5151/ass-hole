package com.tmall.asshole.zkclient.data;

import java.io.Serializable;

/***
 * ≤‚ ‘Data
 * 
 * @author tangjinou
 *
 */
public class TestData implements Serializable{
	
	private static final long serialVersionUID = 5869690291790613957L;
	
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}