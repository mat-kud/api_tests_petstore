package org.example.utils;

import io.qameta.allure.Allure;
import io.qameta.allure.listener.TestLifecycleListener;
import io.qameta.allure.model.TestResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.UUID;

public class AllureTestListener implements TestLifecycleListener {
    public static final String LOG_PATH = "logs/";
    private static final ThreadLocal<String> TL_TEST_UUID = new ThreadLocal<>();
    private static final String LOG_FILE_PATTERN = "test-%s.log";
    private final Logger LOGGER = LogManager.getLogger();

    @Override
    public void afterTestStart(TestResult result) {
        startTestLogging();
    }

    @Override
    public void beforeTestStop(TestResult result) {
        stopTestLogging();
    }

    private void startTestLogging() {
        TL_TEST_UUID.set(UUID.randomUUID().toString());
        ThreadContext.put("uuid", TL_TEST_UUID.get());
    }

    private void stopTestLogging() {
        ThreadContext.remove("uuid");

        String logFileName = String.format(LOG_FILE_PATTERN, TL_TEST_UUID.get());
        File logFile = Paths.get(LOG_PATH, logFileName).toFile();
        addLogAttachment(logFile);
    }

    private void addLogAttachment(File logFile) {
        try {
            Allure.addAttachment("Logs", new FileInputStream(
                    logFile));
        } catch (FileNotFoundException e) {
            LOGGER.warn(String.format("There is no log file %s", logFile.getName()));
        }
    }
}
