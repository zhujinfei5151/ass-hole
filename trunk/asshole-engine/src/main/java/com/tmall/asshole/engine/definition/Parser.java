package com.tmall.asshole.engine.definition;

import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.thoughtworks.xstream.XStream;
import com.tmall.asshole.engine.exception.ParserException;
import com.tmall.asshole.engine.helper.XStreamHelper;
import com.tmall.asshole.engine.node.Definition;


public class Parser {
	
	final static Log logger =  LogFactory.getLog(Parser.class);

	public static XStream xstream = XStreamHelper.getInstance();

	/**
	 * parse xml to definition
	 * @param is
	 * @return
	 */
	public static Definition parse(InputStream is) {
		Definition definition = null;
		try {
			definition = (Definition) xstream.fromXML(is);
		} catch (Exception e) {
			logger.error("parse failed by xstream" + e);
			throw new ParserException("Parse failed by xstream");
		}
		if(definition == null){
			logger.error("definition is empty");
			throw new ParserException("definition is empty");
		}
		return definition;
	}
}
