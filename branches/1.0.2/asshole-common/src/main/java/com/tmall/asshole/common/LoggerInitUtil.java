package com.tmall.asshole.common;

import java.io.File;
import java.util.Enumeration;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Appender;
import org.apache.log4j.AsyncAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

public class LoggerInitUtil {

    static private final String LOGGER_NAME = "com.tmall.asshole";

    static public final Log LOGGER = LogFactory.getLog(LOGGER_NAME);

    static {
        try { // 不能因为该类初始化失败导致其引用类初始化失败
            initAssholeLogFromBizLog();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static private void initAssholeLogFromBizLog() {
        // 使通信层的log4j配置生效(Logger, Appender)
        DOMConfigurator.configure(LoggerInitUtil.class.getClassLoader().getResource("asshole-log4j.xml"));
        Logger loggerLog4jImpl = Logger.getLogger(LOGGER_NAME);

        /*
         * 找到上层应用在Root Logger上设置的FileAppender，以及通信层配置的FileAppender。
         * 目的是为了让通信层的日志与上层应用的日志输出到同一个目录。
         */
        FileAppender bizFileAppender = getFileAppender(Logger.getRootLogger());
        if (null == bizFileAppender) {
            LOGGER.warn("上层业务层没有在ROOT LOGGER上设置FileAppender!!!");
            return;
        }
        FileAppender assholeFileAppender = getFileAppender(loggerLog4jImpl);

        // 根据业务的appender配置来初始化asshole的appender。并创建异步Appender来替代FileAppender。
        String bizLogDir = new File(bizFileAppender.getFile()).getParent();
        String assholeLogFile = new File(bizLogDir, "asshole.log").getAbsolutePath();
        assholeFileAppender.setFile(assholeLogFile);
        assholeFileAppender.activateOptions(); // 很重要，否则原有日志内容会被清空
        AsyncAppender asynAppender = new AsyncAppender();
        asynAppender.addAppender(assholeFileAppender);
        loggerLog4jImpl.addAppender(asynAppender);
        loggerLog4jImpl.removeAppender(assholeFileAppender);
        LOGGER.warn("成功为asshole LOGGER添加Appender. 输出路径:" + assholeLogFile);
    }

    static private FileAppender getFileAppender(Logger logger) {
        FileAppender fileAppender = null;
        for (Enumeration<?> appenders = logger.getAllAppenders();
                (null == fileAppender) && appenders.hasMoreElements();) {
            Appender appender = (Appender) appenders.nextElement();
            if (FileAppender.class.isInstance(appender)) {
                fileAppender = (FileAppender) appender;
            }
        }
        return fileAppender;
    }

}
