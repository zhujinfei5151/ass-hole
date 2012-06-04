package com.tmall.asshole.engine.helper;

import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tmall.asshole.engine.definition.Parser;
import com.tmall.asshole.engine.definition.Transformer;
import com.tmall.asshole.engine.exception.DefinitionNotFoundException;
import com.tmall.asshole.engine.node.Definition;
import com.tmall.asshole.engine.model.Place;
import com.tmall.asshole.engine.model.Process;




public class DefinitionHelper {
	
	final static Log logger =  LogFactory.getLog(DefinitionHelper.class);

	/**
	 * deploy a definition by file path
	 * @param path
	 */
	public static void deploy(String path) {
		Definition definition = null;
		InputStream is = DefinitionHelper.class.getResourceAsStream(path);
		if(null == is){
			logger.error("definition not found exception on path : " + path);
			throw new DefinitionNotFoundException("process path: " + path);
		}
		definition = Parser.parse(is);
		Process p = Transformer.transform(definition);
		Place.putProcess(p);
	}
}
