package com.tmall.asshole.engine.http.screen;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.continuation.Continuation;
import org.eclipse.jetty.continuation.ContinuationSupport;

import com.tmall.asshole.common.IScheduleMonitorWatcher;
import com.tmall.asshole.common.ScheduleMonitorData;



public class RunTimeServlet extends HttpServlet implements IScheduleMonitorWatcher{
	
	
	    private static final long serialVersionUID = 1L;
		private ScheduleMonitorData data;  
	  
	  
	    protected void doGet(HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {  
	    	final Continuation continuation = ContinuationSupport.getContinuation(request);
	    	response.setContentType("text/html;charset=UTF-8");
	    	response.setBufferSize(0);
	    	
	    	  new Thread(new Runnable() {
	              public void run() {
	                  System.out.println("begin");
	                  try {
	                	  OutputStream out = response.getOutputStream();
	                      for (int i = 0; i < 1000; ++i) {
	                          System.out.println("~~~~~~~~~~ok");
	                          out.write(("data=" + data.getCountOfUnExecuteEvent()+",").getBytes());
	                          out.flush();
	                          Thread.sleep(1000);
	                      }

	                  } catch (Exception e1) {
	                      e1.printStackTrace();
	                  }
	                  continuation.resume();
	                  System.out.println("end");
	              }
	          }).start();

	          continuation.setTimeout(100000000);
	          continuation.suspend();
	        
	        
	    }

	    @Override
		public void onChange(ScheduleMonitorData data) {
			this.data= data;
		}  

	    
}
