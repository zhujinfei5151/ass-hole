package com.tmall.asshole.engine.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TemplateHelper {

	/**
	 * extract param like "${a},{b}" to list ["a","b"]
	 * @param template
	 * @return
	 */
	public static List<String> extract(String template){
		List<String> list = new ArrayList<String>();
		Pattern p = Pattern.compile("\\{(\\w+)\\}");
		Matcher m = p.matcher(template);
		while (m.find()) {
			list.add(m.group(1));
		}
		return list;
	}
}
