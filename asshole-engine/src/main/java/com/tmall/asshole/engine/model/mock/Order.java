package com.tmall.asshole.engine.model.mock;

public class Order extends BasicStateMachinePojo{
    private int id;
    private String name;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getClassname() {
		return classname;
	}
	public Order(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	public Order(){
		super();
	}
    
}
