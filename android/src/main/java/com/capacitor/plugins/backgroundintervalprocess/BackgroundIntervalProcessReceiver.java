package com.capacitor.plugins.backgroundintervalprocess;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

public class BackgroundIntervalProcessReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        WorkManager workManager = WorkManager.getInstance(context);

        OneTimeWorkRequest startServiceRequest = new OneTimeWorkRequest.Builder(BackgroundIntervalProcessWorker.class).build();

        workManager.enqueue(startServiceRequest);
    }
}