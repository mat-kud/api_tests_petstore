package org.example.utils;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.example.utils.AllureTestListener.LOG_PATH;

public class LogRolloverManager {
    private static LogFilesRemover logFilesRemover = new LogFilesRemover();

    public static void executeLogRollover() {
        boolean isRollOn = Boolean.parseBoolean(System.getProperty("ROLL_ON"));
        long rolloverSize;
        long rolloverFrequency;

        if(!isRollOn){
            return;
        }

        try {
            rolloverSize = Long
                    .parseLong(System.getProperty("ROLL_SIZE"))
                    * 1000000;
        } catch (NumberFormatException e) {
            System.err.println("Value of ROLL_SIZE property cannot be parsed to long");
            e.printStackTrace();
            return;
        }

        try {
            rolloverFrequency = Long.parseLong(System.getProperty("ROLL_FREQ"));
        } catch (NumberFormatException e) {
            System.err.println("Value of ROLL_FREQ property cannot be parsed to long");
            e.printStackTrace();
            return;
        }

        try {
            if (isRolloverTriggered(rolloverFrequency)) {
                manageLogRollover(Path.of(LOG_PATH).toFile(), rolloverSize);
            }
        } catch (InvalidPathException e) {
            System.err.println("Provided path of logs directory cannot be converted to a Path");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static long manageLogRollover(File directory, long rolloverSize) {
        long length = 0;

        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    length += file.length();
                } else {
                    length += manageLogRollover(file, rolloverSize);
                }
                if (length > rolloverSize) {
                    logFilesRemover.deleteDirectory(Path.of(LOG_PATH).toFile());
                    return -1;
                }
            }
        }
        return length;
    }

    public static boolean isRolloverTriggered(long rolloverFrequency) {
        FileTime creationTime = null;
        try {
            creationTime = Files
                    .readAttributes(Path.of(LOG_PATH), BasicFileAttributes.class)
                    .creationTime();
        } catch (Exception e) {
            e.printStackTrace();
        }

        assert creationTime != null;
        LocalDateTime creationDateTime = LocalDateTime.ofInstant(creationTime.toInstant(), ZoneId.systemDefault());
        LocalDateTime now = LocalDateTime.now();

        return Duration.between(creationDateTime, now).toDays() >= rolloverFrequency;
    }
}
