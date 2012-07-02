package com.tmall.asshole.mock.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import com.tmall.asshole.common.Event;
import com.tmall.asshole.common.IEventDAO;

public class MockEventDAO implements IEventDAO {

	Stack<Event> s = new Stack<Event>();

	public Event queryEventByPrimaryKey(Long id, Integer hashNum) {
		// 鏆傛椂涓嶉渶瑕�
		return null;
	}

	public Integer batchChangeEventStatusBytime(int from, int to, int minute) {
		// 鏆傛椂涓嶉渶瑕�
		return null;
	}

	public Integer batchChangeEventStatus(int from, int to) {
		// 鏆傛椂涓嶉渶瑕�
		return null;
	}

	public List<Event> queryEvent(int start, int end, int count, int env,
			int processorNumber) {
		List<Event> l = new ArrayList<Event>();
		if (s.size() > 0) {
			if (s.peek().getProcessorNumber()== processorNumber) {
				l.add(s.pop());
			}
		}
		return l;
	}

	@Override
	public Long insertEventDO(Event dao) {
		boolean result = s.add(dao);
		return result == true ? (long)1 : 0;
	}

	@Override
	public Integer updateEventDO(Event dao) {
		return 1;
	}
}
