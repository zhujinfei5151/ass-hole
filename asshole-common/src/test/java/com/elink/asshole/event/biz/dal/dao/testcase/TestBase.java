package com.elink.asshole.event.biz.dal.dao.testcase;
import java.util.List;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.springframework.context.ApplicationContext;
import org.unitils.UnitilsJUnit4;
import org.unitils.spring.annotation.SpringApplicationContext;

import com.taobao.hsf.hsfunit.HSFEasyStarter;

public abstract class TestBase extends UnitilsJUnit4 {

	protected final Log log = LogFactory.getLog(getClass());

    static {
        HSFEasyStarter.startFromPath("D:\\hsf\\hsfunit\\1.4.8\\taobao-hsf.sar");
    }

	@Before
	public void before() {
		log.debug("----" + getClass().getSimpleName() + "---");
	}

	// 
	@SpringApplicationContext({ "classpath:springbeans-resource.xml", "classpath:dal-dao.xml" })
	protected static ApplicationContext ctx;

	protected String join(List<?> list) {
		StringBuilder sb = new StringBuilder();
		for (Object tmp : list) {
			sb.append(tmp).append(",");
		}
		if (sb.length() > 0) {
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();
	}

}
