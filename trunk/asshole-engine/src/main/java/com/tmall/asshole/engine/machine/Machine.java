package com.tmall.asshole.engine.machine;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.tmall.asshole.engine.model.Place;
import com.tmall.asshole.engine.model.State;
import com.tmall.asshole.zkclient.ZKManager;


/****
 * 
 * @modified jiuxian.tjo
 * 
 */
public class Machine {

//	public final static String MACHINE_PROCESS_ACTIVITIES = "MACHINE_PROCESS_ACTIVITIES";
//
//	
//	private static transient Log logger = LogFactory.getLog(Machine.class);
//
//	public Process process;
//
//	public ScriptEngine engine;
//
//	public ScriptEngineManager factory;
//
	public Map<String, Object> context;
//
////	@Resource
////	private ProcessInstanceDAO processInstanceDAO;
////
////	@Resource
////	private ProcessInstanceLogsDAO processInstanceLogDAO;
//
//	// @Resource
//	// private TransactionTemplate transactionTemplate;
//
//	private SerializeConfig config;
//
//	public Machine() {
//		this.factory = new ScriptEngineManager();
//		this.engine = factory.getEngineByName("javascript");
//		this.context = new HashMap<String, Object>();
//		config = new SerializeConfig();
//		config.setAsmEnable(false);
//	}
//
//	/**
//	 * start the machine
//	 * 
//	 * @param processName
//	 * @return
//	 */
//	public Map<String, Object> run(String processName,
//			ProcessInstanceDO instance) {
//		this.process = Place.getProcess(processName);
//		State s = process.states.get(process.first);
//		runAll(s, instance);
//		return this.context;
//	}
//
//	/**
//	 * start the machine
//	 * 
//	 * @param processName
//	 * @return
//	 */
//	public Map<String, Object> run(String processName) {
//		this.process = Place.getProcess(processName);
//		State s = process.states.get(process.first);
//		runAll(s, null);
//		return this.context;
//	}
//
//	private void runAll(State s, ProcessInstanceDO instance) {
//		while (s != null) {
//			try {
//				s.execute(context);
//				logger.debug("running on " + process.name + "'s state "
//						+ s.name);
//				s = process.states.get(next(s, context, instance));
//			} catch (Exception e) {
//				throw new MachineException(e + " process:" + process.name
//						+ " state:" + s.name);
//			}
//		}
//	}
//
//	/**
//	 * start the machine
//	 * 
//	 * @param processName
//	 * @return
//	 */
//	public Map<String, Object> run(String processName, String beginStatus,
//			ProcessInstanceDO instance) {
//		this.process = Place.getProcess(processName);
//		State s = process.states.get(beginStatus);
//		runAll(s, instance);
//		return this.context;
//	}
//
//	private String next(State s, Map<String, Object> context,
//			ProcessInstanceDO instance) {
//		String next = null;
//
//		for (Path path : s.nexts) {
//			// has "exp" and "to" #=> eval exp then if true to = next else
//			// continue
//			// has "to" and "exp" == null #=> then to = next
//			if (path.exp == null)
//				next = path.to;
//			else {
//				try {
//					for (Entry<String, Object> entry : context.entrySet()) {
//						engine.put(entry.getKey(), entry.getValue());
//					}
//					Boolean result = (Boolean) engine.eval(path.exp);
//					if (result)
//						next = path.to;
//					else
//						continue;
//				} catch (ScriptException e) {
//					logger.error("engine eval error on script " + path.exp
//							+ ": " + e);
//					throw new ExpressionException(e + " state:" + s.name
//							+ " expression:" + path.exp);
//				}
//			}
//		}
//		if (s.nexts.isEmpty() && next == null && instance != null) {
//			next = instance.getNextNode();
//		}
//		if (!s.nexts.isEmpty() && next == null) {
//			logger.error("next state not found on state: " + s.name);
//			throw new StateNotFoundException("abnormal end on process"
//					+ process.name);
//		}
//		return next;
//	}
//
//	// //////////////////////////////////////////////////////////////
//
//	public ProcessInstanceDO createProcessInstance(Long userid,
//			String processName, Map<String, Object> context) throws Exception {
//		ProcessInstanceDO processInstanceDO = null;
//		try {
//			this.process = Place.getProcess(processName);
//			State s = process.states.get(process.first);
//			String context_str = "";
//			if (context != null) {
//				context_str = JSON.toJSONString(context, config,
//						SerializerFeature.WriteClassName);
//			}
//			processInstanceDO = new ProcessInstanceDO(userid, processName,
//					s.name, context_str, new Date(), new Date(), "");
//			processInstanceDO.setId(processInstanceDAO
//					.insertProcessInstanceDO(processInstanceDO));
//		} catch (Exception e) {
//
//			throw new StateMachineException(
//					StateMachineExceptionConstants.STATE_MACHINE_EXCEPTION
//							.getCode(),
//					StateMachineExceptionConstants.STATE_MACHINE_EXCEPTION
//							.getDesc(), e);
//		}
//		return processInstanceDO;
//
//	}
//
//	public ProcessInstanceDO next(ProcessInstanceDO instance,
//			Map<String, Object> extContext) throws Exception {
//
//		ProcessInstanceDO processInstanceDO = null;
//		try {
//			this.process = Place.getProcess(instance.getProcessName());
//			final State s = process.states.get(instance.getNextNode());
//			processInstanceDO = this.execute(instance, s, extContext);
//		} catch (Exception e) {
//
//			throw new StateMachineException(
//					StateMachineExceptionConstants.STATE_MACHINE_EXCEPTION
//							.getCode(),
//					StateMachineExceptionConstants.STATE_MACHINE_EXCEPTION
//							.getDesc(), e);
//		}
//		return processInstanceDO;
//	}
//
//	public ProcessInstanceDO next(ProcessInstanceDO instance, String nodename,
//			Map<String, Object> extContext) throws Exception {
//		ProcessInstanceDO processInstanceDO = null;
//		try {
//			this.process = Place.getProcess(instance.getProcessName());
//			final State s = process.states.get(nodename);
//			if (s == null) {
//				throw new MachineException("can't find the nodename:"
//						+ nodename);
//			}
//			processInstanceDO = this.execute(instance, s, extContext);
//		} catch (Exception e) {
//			if (e instanceof StateMachineException) {
//				throw e;
//			} else {
//				throw new StateMachineException(StateMachineExceptionConstants.STATE_MACHINE_EXCEPTION.getCode(),
//						StateMachineExceptionConstants.STATE_MACHINE_EXCEPTION.getDesc(), e);
//			}
//		}
//		return processInstanceDO;
//	}
//
//	private ProcessInstanceDO execute(final ProcessInstanceDO instance,
//			final State s, final Map<String, Object> extContext)
//			throws Exception {
//		try {
//			Date createTime = new Date();
//
//			if (s != null) {
//				Map<String, Object> context = new HashMap<String, Object>();
//				if (!StringUtil.isBlank(instance.getContext()))
//					context.putAll(JsonUtil.toJava(instance.getContext()));
//
//				if (extContext != null)
//					context.putAll(extContext);
//
//				if (s.preNodes != null) {
//					boolean isPreNodesExecuted = true;
//					List<String> l = (List<String>) context
//							.get(MACHINE_PROCESS_ACTIVITIES);
//					for (int i = 0; i < s.preNodes.size(); i++) {
//						if (l.contains(s.preNodes.get(i))) {
//							continue;
//						}
//						isPreNodesExecuted = false;
//					}
//					if (isPreNodesExecuted == false) {
//						throw new StateMachineException(StateMachineExceptionConstants.STATE_MACHINE_PRENODE_EXCEPTION.getCode(),StateMachineExceptionConstants.STATE_MACHINE_PRENODE_EXCEPTION.getDesc());
//					}
//				}
//
//				s.execute(context);
//				Object o = context.get(MACHINE_PROCESS_ACTIVITIES);
//				if (o == null && !(o instanceof List)) {
//					o = new ArrayList<String>();
//				}
//				List<String> activitys = (List<String>) o;
//				if (activitys.contains(s.name)) {
//					activitys = deduplication(activitys);
//		        } else {
//		        	activitys.add(s.name);
//		        }
//				context.put(MACHINE_PROCESS_ACTIVITIES, activitys);
//				logger.debug("running on " + process.name + "'s state "
//						+ s.name);
//				State nextStatus = process.states
//						.get(next(s, context, instance));
//				Date endTime = new Date();
//				instance.setNextNode(nextStatus != null ? nextStatus.name
//						: "null");
//				final String jsonString = JsonUtil.toJson(context);
//				// if (jsonString.length() > 65535) {
//				// throw new OutOfLimitForContext("超出json存储长度限制");
//				// }
//				instance.setContext(jsonString);
//				// instance.setInitParam(gson.toJson(context));
//				processInstanceDAO.updateProcessInstanceDO(instance);
//				processInstanceLogDAO
//						.insertProcessInstanceLogDO(new ProcessInstanceLogsDO(
//								instance.getId(), instance.getBuyerId(),
//								s.name, instance.getContext(), createTime,
//								endTime, "", instance.getNextNode()));
//			}
//
//		} 
//		catch (StateMachineException e) {
//			throw e;
//		}
//		catch (Exception e) {
//
//			throw new StateMachineException(
//					StateMachineExceptionConstants.STATE_MACHINE_EXCEPTION
//							.getCode(),
//					StateMachineExceptionConstants.STATE_MACHINE_EXCEPTION
//							.getDesc(), e);
//		}
//		return instance;
//	}
//
//	private List<String> deduplication(List<String> activitys) {
//	      List<String> list = new ArrayList<String>();
//	      for (String value : activitys) {
//	         if (!list.contains(value)) {
//	            list.add(value);
//	         }
//	      }
//	      return list;
//	   }
//	
//	public ProcessInstanceDO next(long id, long buyerId,
//			Map<String, Object> extContext) throws Exception {
//		return next(getProcessInstance(id, buyerId), extContext);
//	}
//
//	public ProcessInstanceDO next(long id, long buyerId) throws Exception {
//		return next(getProcessInstance(id, buyerId), null);
//	}
//
//	public ProcessInstanceDO next(long id, long buyerId, String nodename,
//			Map<String, Object> extContext) throws Exception {
//		return next(getProcessInstance(id, buyerId), nodename, extContext);
//	}
//
//	public ProcessInstanceDO next(long id, long buyerId, String nodename)
//			throws Exception {
//		return next(getProcessInstance(id, buyerId), nodename, null);
//	}
//
//	private ProcessInstanceDO getProcessInstance(long id, long buyerId) {
//		return processInstanceDAO.findProcessInstanceDOByPrimaryKey((Long) id,
//				(Long) buyerId);
//	}
//
//	/**
//	 * @return the isUseSpring
//	 */
//	public boolean isUseSpring() {
//		return Transformer.isUseSpring;
//	}
//
//	/**
//	 * @param isUseSpring
//	 *            the isUseSpring to set
//	 */
//	public void setUseSpring(boolean isUseSpring) {
//		Transformer.isUseSpring = isUseSpring;
//	}
}
