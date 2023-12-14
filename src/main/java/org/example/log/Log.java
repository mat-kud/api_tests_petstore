package org.example.log;

import io.qameta.allure.Allure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 * Single point for Logger retrieval.
 */
public class Log {

    private static Logger loggerInstance;

    private static Logger getLogger() {
        if (Objects.isNull(loggerInstance)) {
            loggerInstance = LoggerFactory.getLogger("Test logger");
        }
        return loggerInstance;
    }

    public static void info(String message) {
        getLogger().info(message);
        attachLogToAllure("INFO: ", message);

    }

    public static void warn(String message, Throwable cause) {
        getLogger().warn(message, cause);
        attachLogToAllure("WARN: ", message);
    }

    public static void error(String message) {
        getLogger().error(message);
        attachLogToAllure("ERROR: ", message);
    }

    private static void attachLogToAllure(String type, String logMessage){
        if(logMessage.length() > 200){
            Allure.addAttachment(type, logMessage);
        } else {
            Allure.addAttachment(type + logMessage, logMessage);
        }
    }
}
