package com.tmall.asshole.event.filter.codec.json;

import java.util.Map;

import com.tmall.asshole.event.filter.codec.ProtocolDecoder;

public class JSONProtocolDecoder<T> implements ProtocolDecoder<T> {

	public T decode(byte[] bytes) throws Exception {
		String json =new String(bytes);
		
		
		return null;
	}

}
