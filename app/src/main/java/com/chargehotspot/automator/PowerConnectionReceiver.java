package com.chargehotspot.automator;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

public class PowerConnectionReceiver extends BroadcastReceiver {
    @Override public void onReceive(Context context, Intent intent) {
        if (!AutomationPrefs.enabled(context)) return;
        String action = intent.getAction();
        if (Intent.ACTION_POWER_CONNECTED.equals(action)) start(context, AutomationPrefs.ACTION_ENABLE);
        if (Intent.ACTION_POWER_DISCONNECTED.equals(action)) start(context, AutomationPrefs.ACTION_DISABLE);
    }
    static void start(Context context, String action) {
        Intent service = new Intent(context, HotspotAutomationService.class).setAction(action);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) context.startForegroundService(service); else context.startService(service);
    }
}
