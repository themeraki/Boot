package com.chargehotspot.automator;

import android.Manifest;import android.app.*;import android.content.*;import android.net.Uri;import android.os.*;import android.provider.Settings;import android.view.*;import android.widget.*;

public class MainActivity extends Activity {
    @Override public void onCreate(Bundle b){ super.onCreate(b); buildUi(); if(Build.VERSION.SDK_INT>=33) requestPermissions(new String[]{Manifest.permission.POST_NOTIFICATIONS},1); }
    private void buildUi(){
        LinearLayout root=new LinearLayout(this); root.setOrientation(LinearLayout.VERTICAL); root.setPadding(36,36,36,36);
        TextView title=text("Charge Hotspot Automator",24,true); root.addView(title);
        TextView desc=text("Turns hotspot on when a charger connects and turns it off when the cable disconnects. Android blocks direct hotspot control for normal apps, so enable the accessibility helper once for blink-of-eye Settings automation.",16,false); root.addView(desc);
        Switch enabled=new Switch(this); enabled.setText("Enable charge automation"); enabled.setChecked(AutomationPrefs.enabled(this)); enabled.setOnCheckedChangeListener((v,c)->AutomationPrefs.prefs(this).edit().putBoolean("enabled",c).apply()); root.addView(enabled);
        Switch home=new Switch(this); home.setText("Return to Home after switching"); home.setChecked(AutomationPrefs.returnHome(this)); home.setOnCheckedChangeListener((v,c)->AutomationPrefs.prefs(this).edit().putBoolean("return_home",c).apply()); root.addView(home);
        root.addView(button("Open Accessibility setup",v->startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS))));
        root.addView(button("Allow battery optimization exception",v->openBatterySettings()));
        root.addView(button("Test: turn hotspot ON",v->PowerConnectionReceiver.start(this,AutomationPrefs.ACTION_ENABLE)));
        root.addView(button("Test: turn hotspot OFF",v->PowerConnectionReceiver.start(this,AutomationPrefs.ACTION_DISABLE)));
        TextView tips=text("Suggested options included: manual tests, return-home behavior, boot-safe receiver, low-priority foreground service, and battery optimization exemption prompt.",14,false); root.addView(tips);
        setContentView(root);
    }
    private TextView text(String s,int sp,boolean bold){ TextView v=new TextView(this); v.setText(s); v.setTextSize(sp); v.setPadding(0,10,0,10); if(bold) v.setTypeface(android.graphics.Typeface.DEFAULT_BOLD); return v; }
    private Button button(String s,View.OnClickListener l){ Button b=new Button(this); b.setText(s); b.setAllCaps(false); b.setOnClickListener(l); return b; }
    private void openBatterySettings(){ if(Build.VERSION.SDK_INT>=23){ Intent i=new Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS, Uri.parse("package:"+getPackageName())); try{startActivity(i);}catch(Exception e){startActivity(new Intent(Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS));}} }
}
