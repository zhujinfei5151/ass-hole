package com.tmall.asshole.zkclient.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/***
 * 
 * @author tangjinou
 * @since 2012-5-30
 * @version 1.0
 */
public class NodeData  implements Serializable{

	private static final long serialVersionUID = 3638092482783769350L;

	private String name;
	
	public NodeData(String name){
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private List<Data> datas=new ArrayList<Data>();
	
	public void clear(){
		datas.clear();
	}
	
	public void addData(Data data){
		datas.add(data);
	}

	public List<Data> getDatas() {
		return datas;
	}

	public void setDatas(List<Data> datas) {
		this.datas = datas;
	}
	
   	
	
	
	

}
