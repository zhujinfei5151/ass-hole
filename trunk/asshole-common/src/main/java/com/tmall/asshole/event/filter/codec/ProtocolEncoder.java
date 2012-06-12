package com.tmall.asshole.event.filter.codec;
/***
 * @author tangjinou
 */
public interface ProtocolEncoder<T> {
	byte[] encode( T t) throws Exception;
}
