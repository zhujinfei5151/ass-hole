package com.tmall.asshole.event.filter.codec;
/**
 * 
 * @author tangjinou (jiuxian.tjo)
 *
 */
public interface ProtocolEncoder<T> {
	byte[] encode( T t) throws Exception;
}
