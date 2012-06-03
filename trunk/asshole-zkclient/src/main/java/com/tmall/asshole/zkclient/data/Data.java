package com.tmall.asshole.zkclient.data;

import java.io.Serializable;

/***
 * @author tangjinou
 */
public class Data<T> implements Serializable{
	
	private static final long serialVersionUID = -2993185775123752861L;
	
	private String key;
	
	private T t;

	public T getT() {
		return t;
	}

	public void setT(T t) {
		this.t = t;
	}
	
	public Data(String key, T t) {
		 this.t=t;
		 this.key=key;
	}

	public String getKey() {
		return key;
	}
	
	
	
	
	
//	private String str_value=null;
//	
//	private Boolean bool_value=null;
//	
//	private Long long_value=null;
//
//	public String getKey() {
//		return key;
//	}
//
//	public void setKey(String key) {
//		this.key = key;
//	}
//
//	public T getValue() {
//		if(str_value!=null){
//			return str_value;
//		}
//		else if(bool_value!=null){
//			return bool_value;
//		}
//		else if(bool_value!=null){
//			return bool_value;
//		}
//		
//		
//	}
//
//	public void setValue(String value) {
//		this.value = value;
//	}
	
	
	
	

	
}
