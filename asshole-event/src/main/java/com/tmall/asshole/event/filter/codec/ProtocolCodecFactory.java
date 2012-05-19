package com.tmall.asshole.event.filter.codec;
/***
 * @author tangjinou
 */
public interface ProtocolCodecFactory<T> {
	    ProtocolEncoder<T> getEncoder() throws Exception;
	    ProtocolDecoder<T> getDecoder() throws Exception;
}
