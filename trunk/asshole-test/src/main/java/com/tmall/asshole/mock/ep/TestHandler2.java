package com.tmall.asshole.mock.ep;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tmall.asshole.common.EventContext;
import com.tmall.asshole.engine.AbstractHandler;

public class TestHandler2 extends AbstractHandler<TestEvent2,EventContext> {

	public boolean handle(TestEvent2 event, EventContext context)
			throws Exception {
		
		 List<Map<String,Object>> dataList = new ArrayList<Map<String,Object>>();
		
		 Map<String,Object> map1=new HashMap<String,Object>();
		 
		 map1.put("testVar3", "jiuxian.tjo 1");
		 
		 dataList.add(map1);
		 
		 Map<String,Object> map2=new HashMap<String,Object>();
		 
         map2.put("testVar3", "jiuxian.tjo 2");
		 
		 dataList.add(map2);
		
		 context.putDataList(dataList);
	
		return true;
	}


}
