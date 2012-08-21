package com.tmall.asshole.engine.http;


import org.apache.commons.logging.Log;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import com.tmall.asshole.common.LoggerInitUtil;
import com.tmall.asshole.engine.http.screen.RunTimeServlet;
import com.tmall.asshole.engine.process.ProcessorMachine;

public class JettyServer{

	private final static Log logger = LoggerInitUtil.LOGGER;

	private ProcessorMachine processorMachine;

	public JettyServer(ProcessorMachine processorMachine){
		this.processorMachine = processorMachine;
	}

	public void start() throws Exception{
		Server server = new Server(9999);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/asshole");
        server.setHandler(context);

        RunTimeServlet runTimeServlet = new RunTimeServlet();
        processorMachine.getScheduleMonitor().addWatcher(runTimeServlet);

        context.addServlet(new ServletHolder(runTimeServlet), "/runtime");
        logger.info("start the jetty server,port= 9999");
		server.start();
		server.join();
	}

//	public static void main(String[] args) {
//		try {
//			new JettyServer().start();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

}
