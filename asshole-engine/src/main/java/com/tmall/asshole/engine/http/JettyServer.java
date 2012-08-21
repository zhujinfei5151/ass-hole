package com.tmall.asshole.engine.http;


import org.apache.commons.logging.Log;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import com.tmall.asshole.common.LoggerInitUtil;
import com.tmall.asshole.engine.http.screen.RunTimeServlet;
import com.tmall.asshole.engine.process.ProcessorMachine;
import com.tmall.asshole.schedule.Job;

public class JettyServer extends Job{
	private final static Log logger = LoggerInitUtil.LOGGER;

	private ProcessorMachine processorMachine;

	private Server server = new Server(9999);
	

	public JettyServer(ProcessorMachine processorMachine){
		this.processorMachine = processorMachine;
	}
	
	public void run(){
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/asshole");
        server.setHandler(context);

        RunTimeServlet runTimeServlet = new RunTimeServlet(processorMachine);

        context.addServlet(new ServletHolder(runTimeServlet), "/runtime");
        
        logger.info("start the jetty server,port= 9999");
		try {
			server.start();
			server.join();
		} catch (Exception e) {
			org.eclipse.jetty.util.log.Log.warn("can't start the jetty server,so exit", e);
			//²»ÄÜÆô¶¯web
			System.exit(-1);
		}
	}
	

//	public static void main(String[] args) {
//		try {
//			new JettyServer().start();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

}
