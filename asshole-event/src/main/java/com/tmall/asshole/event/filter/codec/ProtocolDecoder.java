package com.tmall.asshole.event.filter.codec;
/***
 * @author tangjinou
 */
public interface ProtocolDecoder<T> {
	T decode(byte[] bytes, Class<? extends T> clz) throws Exception;
}
