package com.chargehotspot.automator;

import android.app.*;import android.content.*;import android.net.Uri;import android.os.*;import android.provider.Settings;

public class HotspotAutomationService extends Service {
    private static final String CHANNEL = "automation";
    @Override public void onCreate(){ super.onCreate(); createChannel(); }
    @Override public int onStartCommand(Intent intent,int flags,int startId){
        startForeground(7, notification("Applying hotspot rule…"));
        boolean on = intent != null && AutomationPrefs.ACTION_ENABLE.equals(intent.getAction());
        AutomationPrefs.setPending(this,on);
        launchFastPath(on);
        stopSelf(startId);
        return START_NOT_STICKY;
    }
    private void launchFastPath(boolean on){
        Intent panel = new Intent(Settings.Panel.ACTION_INTERNET_CONNECTIVITY).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try { startActivity(panel); } catch (Exception ignored) { startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)); }
        sendBroadcast(new Intent(SettingsAccessibilityService.ACTION_RUN).setPackage(getPackageName()).putExtra("target_on", on));
    }
    private void createChannel(){ if(Build.VERSION.SDK_INT>=26){ NotificationChannel c=new NotificationChannel(CHANNEL,"Hotspot automation",NotificationManager.IMPORTANCE_LOW); getSystemService(NotificationManager.class).createNotificationChannel(c);} }
    private Notification notification(String text){ return new Notification.Builder(this,CHANNEL).setSmallIcon(android.R.drawable.stat_sys_data_bluetooth).setContentTitle("Charge Hotspot Automator").setContentText(text).setOngoing(false).build(); }
    @Override public IBinder onBind(Intent intent){ return null; }
}
