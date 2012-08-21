package com.tmall.asshole.engine.http.screen;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.tmall.asshole.common.LoggerInitUtil;
import com.tmall.asshole.engine.process.ProcessorMachine;



public class RunTimeServlet extends HttpServlet{
	    
	    private final static Log logger = LoggerInitUtil.LOGGER;
	
	    private static final long serialVersionUID = 1L;

		private ProcessorMachine processorMachine;
	    
	  
	    public RunTimeServlet(ProcessorMachine processorMachine) {
			this.processorMachine=processorMachine;
		}



		protected void doGet(HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {  
	  	      
			  SerializeConfig config=new SerializeConfig();
			  config.setAsmEnable(false);
			  
	    	  response.getWriter().println(JSON.toJSONString(processorMachine.getScheduleMonitor().getScheduleMonitorData(),config,SerializerFeature.WriteClassName));
	        
	        
	    }

	    
	    
}
