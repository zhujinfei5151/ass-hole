package com.tmall.asshole.event.filter.codec;
/**
 * 
 * @author tangjinou (jiuxian.tjo)
 *
 */
public interface ProtocolDecoder<T> {
	T decode(byte[] bytes, T o) throws Exception;
}
