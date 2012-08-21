package com.tmall.asshole.schedule.monitor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;

import com.tmall.asshole.common.IEventDAO;
import com.tmall.asshole.common.IScheduleMonitorWatcher;
import com.tmall.asshole.common.LoggerInitUtil;
import com.tmall.asshole.common.ScheduleMonitorData;
import com.tmall.asshole.schedule.node.ProcessTemplate;
import com.tmall.asshole.schedule.node.helper.ProcessTemplateHelper;
import com.tmall.asshole.util.ApplicationUtils;
/**
 *
 * @author tangjinou (jiuxian.tjo)
 *
 */
public class ScheduleMonitor{

	private IEventDAO eventDAO;

	private Thread monitor;

	private List<IScheduleMonitorWatcher> watchers=new ArrayList<IScheduleMonitorWatcher>();

	private final static Log logger = LoggerInitUtil.LOGGER;

	public void start() throws Exception {
		if(eventDAO==null){
			eventDAO= (IEventDAO) ApplicationUtils.getBean("eventDAO");
		}

		monitor = new Thread(){
			public void run() {
              while(true){
				try {
				   //每5秒钟一次
				   Thread.sleep(5000);
				   logger.info("schedule monitor is working");
				   ScheduleMonitorData data=new ScheduleMonitorData(eventDAO.queryCountOfUnExecuteEvent());
				   for(IScheduleMonitorWatcher watch:watchers){
					   logger.info("schedule monitor invoke watcher ,"+watch.getClass().getName());
					   watch.onChange(data);
				   }
				} catch (Exception e) {
					logger.error("schedule monitor meet error", e);
				}
			 }
			}
		};
		monitor.start();
	}

	public void addWatcher(IScheduleMonitorWatcher watcher){
		watchers.add(watcher);
	}
	
	/**
	 * 获取监控数据
	 * @return
	 */
	public ScheduleMonitorData getScheduleMonitorData(){
		ScheduleMonitorData data=new ScheduleMonitorData(eventDAO.queryCountOfUnExecuteEvent());
		//TODO:补充需要的关键数据
		//流程模板相关数据
		Map<String, ProcessTemplate> processes = ProcessTemplateHelper.processes;
		
		
		
		return data;
	}
	
	

}
