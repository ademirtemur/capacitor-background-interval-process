package com.capacitor.plugins.backgroundintervalprocess;

import android.content.Context;
import android.content.Intent;

import androidx.core.content.ContextCompat;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

@CapacitorPlugin(name = "BackgroundIntervalProcess")
public class BackgroundIntervalProcessPlugin extends Plugin {
    private final String SMTH_WENT_WRONG = "SOME_THING_WENT_WRONG";

    @Override
    @PluginMethod(returnType = PluginMethod.RETURN_NONE)
    public void removeAllListeners(PluginCall call) {
        super.removeAllListeners(call);
    }

    @Override
    protected void handleOnDestroy() {
        unsetAppListeners();
    }

    private void unsetAppListeners() {
        bridge.getApp().setStatusChangeListener(null);
        bridge.getApp().setAppRestoredListener(null);
    }

    @PluginMethod(returnType = PluginMethod.RETURN_PROMISE)
    public void isProcessAlive(PluginCall call) {
        try {
            JSObject res = new JSObject();
            res.put("status", BackgroundIntervalProcessService.isServiceRunning);
            call.resolve(res);
        } catch (Exception ex) {
            call.reject(SMTH_WENT_WRONG);
        }
    }

    @PluginMethod(returnType = PluginMethod.RETURN_PROMISE)
    public void startProcess(PluginCall call) {
        try {
            if (BackgroundIntervalProcessService.isServiceRunning) {
                call.reject("SERVICE_IS_RUNNING_ALREADY");
                return;
            }

            Context context = getContext().getApplicationContext();

            Integer interval = call.getInt("interval");
            String title = call.getString("title");
            String description = call.getString("description");

            if (interval == null || interval < 10) {
                call.reject("INCORRECT_INTERVAL_VALUE");
                return;
            }

            BackgroundIntervalProcessService.setInfos(title, description);
            Intent serviceIntent = new Intent(context, BackgroundIntervalProcessService.class);
            ContextCompat.startForegroundService(context, serviceIntent);

            BackgroundIntervalProcessService.startProcess(
                    interval,
                    () -> {
                        notifyListeners("DOIT", new JSObject(), false);
                    }
            );

            call.resolve();
        } catch (Exception ex) {
            call.reject(SMTH_WENT_WRONG);
        }
    }

    @PluginMethod(returnType = PluginMethod.RETURN_PROMISE)
    public void terminateProcess(PluginCall call) {
        try {
            BackgroundIntervalProcessService.terminateProcess();

            unsetAppListeners();

            Context context = getContext().getApplicationContext();
            Intent serviceIntent = new Intent(context, BackgroundIntervalProcessService.class);
            context.stopService(serviceIntent);

            call.resolve();
        } catch (Exception ex) {
            call.reject(SMTH_WENT_WRONG);
        }
    }
}
