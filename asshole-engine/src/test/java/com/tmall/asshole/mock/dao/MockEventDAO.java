package com.tmall.asshole.mock.dao;

import java.util.ArrayList;
import java.util.List;

import com.tmall.asshole.common.Event;
import com.tmall.asshole.common.IEventDAO;
import com.tmall.asshole.mock.ep.TestEvent1;

public class MockEventDAO implements IEventDAO{
	@Override
	public Integer updateServiceEventDO(Event dao) {
		return 1;
	}
	
	@Override
	public Event queryEventByPrimaryKey(Long id, Integer hashNum) {
		// 暂时不需要
		return null;
	}
	
	@Override
	public Integer insertServiceEventDO(Event dao) {
		// 暂时不需要
		return null;
	}
	
	@Override
	public Integer batchChangeEventStatusBytime(int from, int to, int minute) {
		// 暂时不需要
		return null;
	}
	
	@Override
	public Integer batchChangeEventStatus(int from, int to) {
		// 暂时不需要
		return null;
	}

	@Override
	public List<Event> queryEvent(int start, int end, int count,
			int env, int scheduleType) {
		List<Event> l= new ArrayList<Event>();
		l.add(new TestEvent1());
		return l;
	}
}
