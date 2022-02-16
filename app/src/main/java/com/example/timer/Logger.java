package com.example.timer;

import android.content.Context;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

public class Logger {

    private Context context;

    public Logger(Context context) throws IOException {
        this.context = context;
    }

    private void collectLogs() {
        File appDirectory = context.getExternalCacheDir();
        File logDirectory = new File(appDirectory + File.separator + "logs" +
                File.separator + "logs_" +
                Calendar.getInstance().get(Calendar.YEAR) + "_" +
                Calendar.getInstance().get(Calendar.MONTH) + "_" +
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

        File logFile = new File(logDirectory, "logcat_" + System.currentTimeMillis() + ".txt");
        if (!appDirectory.exists()) {
            appDirectory.mkdirs();
        }
        if (!logDirectory.exists()) {
            logDirectory.mkdirs();
        }

        try {
            Process process = Runtime.getRuntime().exec("logcat -c");
            process = Runtime.getRuntime().exec("logcat -f" + logFile);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
