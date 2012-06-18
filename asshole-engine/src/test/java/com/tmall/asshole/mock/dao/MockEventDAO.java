package com.tmall.asshole.mock.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import com.tmall.asshole.common.Event;
import com.tmall.asshole.common.IEventDAO;

public class MockEventDAO implements IEventDAO {

	Stack<Event> s = new Stack<Event>();

	public Event queryEventByPrimaryKey(Long id, Integer hashNum) {
		// 暂时不需要
		return null;
	}

	public Integer batchChangeEventStatusBytime(int from, int to, int minute) {
		// 暂时不需要
		return null;
	}

	public Integer batchChangeEventStatus(int from, int to) {
		// 暂时不需要
		return null;
	}

	public List<Event> queryEvent(int start, int end, int count, int env,
			int scheduleType) {
		List<Event> l = new ArrayList<Event>();
		if (s.size() > 0) {
			if (s.peek().getSchedule_type() == scheduleType) {
				l.add(s.pop());
			}
		}
		return l;
	}

	@Override
	public Integer insertEventDO(Event dao) {
		boolean result = s.add(dao);
		return result == true ? 1 : 0;
	}

	@Override
	public Integer updateEventDO(Event dao) {
		return 1;
	}
}
