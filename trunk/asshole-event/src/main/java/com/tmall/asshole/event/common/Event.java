package com.tmall.asshole.event.common;

import java.util.Date;

/****
 *
 * @author tangjinou
 *
 */
public class Event {

	public Event(Event e) {
		super();
		this.id = e.getId();
		this.status = e.status;
		this.env = e.env;
		this.memo = e.memo;
		this.type_class = e.type_class;
		this.content = e.content;
		this.execute_machine_ip = e.execute_machine_ip;
		this.execute_machine_hash_range = e.execute_machine_hash_range;
		this.gmt_create = e.gmt_create;
		this.gmt_modify = e.gmt_modify;
		this.operator = e.operator;
		this.hash_num = e.hash_num;
		this.exec_count = e.exec_count;
	}

	public Event() {

	}

	private Long id;

	private EventStatus status;

	private EventEnv env;

	private String memo;

	private String type_class;

	private String content;

	private String execute_machine_ip;

	private String execute_machine_hash_range;

	private Date gmt_create;

	private Date gmt_modify;

	private String operator;

	private int hash_num;

	private int exec_count;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public EventStatus getStatus() {
		return status;
	}

	public void setStatus(EventStatus status) {
		this.status = status;
	}

	public EventEnv getEnv() {
		return env;
	}

	public void setEnv(EventEnv env) {
		this.env = env;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getExecute_machine_ip() {
		return execute_machine_ip;
	}

	public void setExecute_machine_ip(String execute_machine_ip) {
		this.execute_machine_ip = execute_machine_ip;
	}

	public String getExecute_machine_hash_range() {
		return execute_machine_hash_range;
	}

	public void setExecute_machine_hash_range(String execute_machine_hash_range) {
		this.execute_machine_hash_range = execute_machine_hash_range;
	}

	public Date getGmt_create() {
		return gmt_create;
	}

	public void setGmt_create(Date gmt_create) {
		this.gmt_create = gmt_create;
	}

	public Date getGmt_modify() {
		return gmt_modify;
	}

	public void setGmt_modify(Date gmt_modify) {
		this.gmt_modify = gmt_modify;
	}

	public int getHash_num() {
		return hash_num;
	}

	public void setHash_num(int hash_num) {
		this.hash_num = hash_num;
	}

	public int getExec_count() {
		return exec_count;
	}

	public void setExec_count(int exec_count) {
		this.exec_count = exec_count;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getType_class() {
		return type_class;
	}

	public void setType_class(String type_class) {
		this.type_class = type_class;
	}

}
