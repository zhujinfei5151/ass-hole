package com.tmall.asshole.event.util;


import org.springframework.beans.factory.annotation.Autowired;

import com.tmall.asshole.common.Event;
import com.tmall.asshole.event.filter.codec.ProtocolCodecFactory;

/***
 * 使用时需要spring 注入
 * 
 * 
 * @author jiuxian.tjo
 * 
 */

public class MessageUtil {
	@Autowired
	ProtocolCodecFactory<Event> protocolCodecFactory;
	
	
	public void sendInternalMessage(Event event) throws Exception{
		byte[] encode = protocolCodecFactory.getEncoder().encode(event);
	}
	

}
