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
			int processorNumber) {
		List<Event> l = new ArrayList<Event>();
		if (s.size() > 0) {
			//非同步商品
			if (s.peek().getProcessorNumber()== processorNumber  &&  !s.peek().getSynInvoke()) {
				l.add(s.pop());
			}
		}
		return l;
	}

	@Override
	public Long insertEventDO(Event dao) {
		boolean result = s.add(dao);
		return result == true ? 1L : 0L;
	}

	@Override
	public Integer updateEventDO(Event dao) {
		return 1;
	}

	@Override
	public Long queryCountOfUnExecuteEvent() {
		return 1L;
	}
}
