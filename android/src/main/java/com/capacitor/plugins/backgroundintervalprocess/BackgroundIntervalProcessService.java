package com.capacitor.plugins.backgroundintervalprocess;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;

public class BackgroundIntervalProcessService extends Service {
    public static boolean isServiceRunning;
    private static String title = "Service is Running";
    private static String description = "Listening for Screen Off/On events";
    private final String CHANNEL_ID = "NOTIFICATION_CHANNEL";
    private static Handler handler = new Handler();
    private static Runnable runnable;

    public BackgroundIntervalProcessService() {
        isServiceRunning = false;
    }

    public static void setInfos(String title, String description) {
        if (title != null) {
            BackgroundIntervalProcessService.title = title;
        }
        if (description != null) {
            BackgroundIntervalProcessService.description = description;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        isServiceRunning = true;
        this.createNotificationChannel();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String appName = getString(R.string.app_name);
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    appName,
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Intent notificationIntent = new Intent(this, getApplication().getClass());
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(description)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_transparent)
                .build();

        startForeground(1, notification);

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        isServiceRunning = false;
        stopForeground(true);

        Intent broadcastIntent = new Intent(getApplicationContext(), BackgroundIntervalProcessReceiver.class);
        sendBroadcast(broadcastIntent);

        super.onDestroy();
    }

    public static void startProcess(Integer interval, OnCustomEventListener ce) {
        runnable = new Runnable() {
            @Override
            public void run() {
                ce.onEvent();
                handler.postDelayed(this, interval);
            }
        };
        handler.postDelayed(runnable, interval);
    }

    public static void terminateProcess() {
        handler.removeCallbacks(runnable);
    }
}
