package org.example.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.example.utils.AllureTestListener.LOG_PATH;

public class LogRolloverManager {
    private static LogFilesRemover logFilesRemover = new LogFilesRemover();

    public static void executeLogRollover(){
        boolean isRollOn = Boolean.parseBoolean(System.getProperty("ROLL_ON"));
        if(!isRollOn){
            return;
        }
        long rolloverFrequency = Long.parseLong(System.getProperty("ROLL_FREQ"));
        if(isRolloverTriggered(rolloverFrequency)){
            manageLogRollover(Path.of(LOG_PATH).toFile());
        }
    }

    private static long manageLogRollover(File directory) {
        long length = 0;
        long rolloverSize = Long
                .parseLong(System.getProperty("ROLL_SIZE"))
                *1000000;
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    length += file.length();
                } else {
                    length += manageLogRollover(file);
                }
                if (length > rolloverSize){
                    logFilesRemover.deleteDirectory(Path.of(LOG_PATH).toFile());
                    return -1;
                }
            }
        }
        return length;
    }

    public static boolean isRolloverTriggered(long rolloverFrequency){
        FileTime creationTime = null;
        try {
            creationTime = Files
                    .readAttributes(Path.of(LOG_PATH), BasicFileAttributes.class)
                    .creationTime();
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert creationTime != null;
        LocalDateTime creationDateTime = LocalDateTime.ofInstant(creationTime.toInstant(), ZoneId.systemDefault());
        LocalDateTime now = LocalDateTime.now();

        return Duration.between(creationDateTime, now).toDays() >= rolloverFrequency;
    }
}
