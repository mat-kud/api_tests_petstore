package org.example.utils;

import java.io.File;

public class LogFilesRemover {

    public void deleteDirectory(File directory) {
        File[] files = directory.listFiles();
        try {
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        deleteDirectory(file);
                    } else {
                        file.delete();
                    }
                }
            }
            directory.delete();

        } catch (Exception e){
            System.err.println("File or directory could not be deleted");
        }
    }
}
