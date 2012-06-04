package com.tmall.asshole.event.common;

import java.io.Serializable;
import java.util.Date;

/****
 *
 * @author tangjinou
 *
 */
public class Event implements Serializable {

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

	/**
	 * 
	 */
	private static final long serialVersionUID = -3356840467368327481L;


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


	
	private Long identifier;
	
	private Long parent_biz_order_id;
	
	private Long biz_order_id;
	
	private Long workcard_id;
	
	
	private String type;
	
	

	
	private Integer source;
	
	private Date event_time;
	
	private Long buyer_id;
	

	public Integer getStatus() {
		return status.getCode();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public EventStatus getEventStatus() {
		return status;
	}
	/**
	 * 重载 用于ibatis数据库更新
	 * @param status
	 */
	public void setStatus(Integer status) {
		
		this.status = EventStatus.getEventStatusByCode(status);
	}
	/**
	 * 重载
	 * @param status
	 */
	public void setStatus(EventStatus status)	{
		this.status = status;
		
	}

	public Integer getEnv() {
		return env.getCode();
	}

	public void setEnv(EventEnv env) {
		this.env = env;
	}
	
	/**
	 * 重载 用于ibatis数据库更新
	 * @param env
	 */
	public void setEnv(Integer env) {
		this.env = EventEnv.getEventEnvByCode(env);
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

	

	public Long getIdentifier() {
		return identifier;
	}

	public void setIdentifier(Long identifier) {
		this.identifier = identifier;
	}

	public Long getParent_biz_order_id() {
		return parent_biz_order_id;
	}

	public void setParent_biz_order_id(Long parent_biz_order_id) {
		this.parent_biz_order_id = parent_biz_order_id;
	}

	public Long getBiz_order_id() {
		return biz_order_id;
	}

	public void setBiz_order_id(Long biz_order_id) {
		this.biz_order_id = biz_order_id;
	}

	public Long getWorkcard_id() {
		return workcard_id;
	}

	public void setWorkcard_id(Long workcard_id) {
		this.workcard_id = workcard_id;
	}


	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	

	public Integer getExec_count() {
		return exec_count;
	}

	public void setExec_count(Integer exec_count) {
		this.exec_count = exec_count;
	}

	public Integer getSource() {
		return source;
	}
	public String getContent() {
		return content;
	}

	public void setSource(Integer source) {
		this.source = source;
	}

	public Date getEvent_time() {
		return event_time;
	}

	public void setEvent_time(Date event_time) {
		this.event_time = event_time;
	}

	public Long getBuyer_id() {
		return buyer_id;
	}

	public void setBuyer_id(Long buyer_id) {
		this.buyer_id = buyer_id;
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
