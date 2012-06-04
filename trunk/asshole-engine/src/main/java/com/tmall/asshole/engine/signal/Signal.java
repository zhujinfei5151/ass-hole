package com.tmall.asshole.engine.signal;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.tmall.asshole.engine.exception.SignalException;
import com.tmall.asshole.engine.helper.TemplateHelper;




public class Signal {

	protected List<Slot> slots;

	public Signal() {
		this.slots = new ArrayList<Slot>();
	}

	public Signal add(Object listener, String method, String args, String result) {
		Slot slot = new Slot(listener, true, method, args, result);
		slots.add(slot);
		return this;
	}

	public Signal add(Object listener, Boolean sync, String method,
			String args, String result) {
		Slot slot = new Slot(listener, sync, method, args, result);
		slots.add(slot);
		return this;
	}

	public void dispatch(Map<String, Object> context) {
		for (Slot slot : slots) {
			List<Object> args = new ArrayList<Object>();
			List<String> params = TemplateHelper.extract(slot.args);
			for (String argument : params) {
				args.add(context.get(argument));
			}
			String callback = TemplateHelper.extract(slot.result).get(0);
			// invoke listener
			Object o = this.invoke(slot, args);
			// insert result to context
			if (!callback.isEmpty()) {
				context.put(callback, o);
			}
		}
	}

	private Object invoke(Slot s, List<Object> args) {
		Method m = null;
		Object result = null;
		try {
			Class<?>[] paramsType = new Class<?>[args.size()];
			for(int i = 0; i < args.size(); i++){
				paramsType[i] = args.get(i).getClass();
			}
			Class<?> listenerClass = s.listener.getClass();
			m = listenerClass.getDeclaredMethod(s.method, paramsType);
			result = m.invoke(s.listener, args.toArray());
		} catch (Exception e) {
			throw new SignalException(e + " listener:" + s.method + " args:"
					+ s.args + " sync:" + s.sync);
		} 
		return result;
	}
}
