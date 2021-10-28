package com.capacitor.plugins.backgroundintervalprocess;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class BackgroundIntervalProcessWorker extends Worker {
    private final Context context;

    public BackgroundIntervalProcessWorker(@NonNull Context context, @NonNull WorkerParameters params) {
        super(context, params);
        this.context = getApplicationContext();
    }

    @NonNull
    @Override
    public Result doWork() {
//        if (!BackgroundIntervalProcessService.isServiceRunning) {
//            Intent intent = new Intent(this.context, BackgroundIntervalProcessService.class);
//            ContextCompat.startForegroundService(context, intent);
//        }

        return Result.success();
    }

    @Override
    public void onStopped() {
        super.onStopped();
    }
}