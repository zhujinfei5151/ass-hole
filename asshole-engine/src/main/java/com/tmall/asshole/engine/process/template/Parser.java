package com.tmall.asshole.engine.process.template;

import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thoughtworks.xstream.XStream;
import com.tmall.asshole.exception.ParserException;
import com.tmall.asshole.schedule.node.ProcessTemplate;
import com.tmall.asshole.schedule.node.helper.XStreamHelper;

/**
 * 
 * @author tangjinou (jiuxian.tjo)
 *
 */
public class Parser {
	
	final static Logger logger = LoggerFactory.getLogger(Parser.class);

	public static XStream xstream = XStreamHelper.getInstance();

	/**
	 * parse xml to definition
	 * @param is
	 * @return
	 */
	public static ProcessTemplate parse(InputStream is) {
		ProcessTemplate definition = null;
		try {
			definition = (ProcessTemplate) xstream.fromXML(is);
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
