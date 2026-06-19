package com.chargehotspot.automator;

import android.accessibilityservice.AccessibilityService;import android.content.*;import android.view.accessibility.*;import java.util.*;

public class SettingsAccessibilityService extends AccessibilityService {
    static final String ACTION_RUN="com.chargehotspot.automator.RUN_ACCESSIBILITY";
    private final BroadcastReceiver receiver=new BroadcastReceiver(){@Override public void onReceive(Context c,Intent i){ runAutomation(i.getBooleanExtra("target_on",AutomationPrefs.targetOn(c))); }};
    @Override protected void onServiceConnected(){ if(android.os.Build.VERSION.SDK_INT>=33) registerReceiver(receiver,new IntentFilter(ACTION_RUN),Context.RECEIVER_NOT_EXPORTED); else registerReceiver(receiver,new IntentFilter(ACTION_RUN)); if(AutomationPrefs.pending(this)) runAutomation(AutomationPrefs.targetOn(this)); }
    @Override public void onAccessibilityEvent(AccessibilityEvent event){ if(AutomationPrefs.pending(this)) runAutomation(AutomationPrefs.targetOn(this)); }
    @Override public void onInterrupt(){}
    @Override public void onDestroy(){ try{unregisterReceiver(receiver);}catch(Exception ignored){} super.onDestroy(); }
    private void runAutomation(boolean on){
        AccessibilityNodeInfo root=getRootInActiveWindow(); if(root==null) return;
        String[] labels= on ? new String[]{"hotspot","tethering","mobile hotspot","use wi-fi hotspot","wi-fi hotspot","turn on"} : new String[]{"hotspot","tethering","mobile hotspot","use wi-fi hotspot","wi-fi hotspot","turn off"};
        if(clickFirst(root,labels)) { AutomationPrefs.clearPending(this); if(AutomationPrefs.returnHome(this)) performGlobalAction(GLOBAL_ACTION_HOME); }
    }
    private boolean clickFirst(AccessibilityNodeInfo node,String[] labels){
        Queue<AccessibilityNodeInfo> q=new ArrayDeque<>(); q.add(node);
        while(!q.isEmpty()){ AccessibilityNodeInfo n=q.remove(); CharSequence t=n.getText(), d=n.getContentDescription(); String s=((t==null?"":t)+" "+(d==null?"":d)).toLowerCase(Locale.US);
            for(String label:labels) if(s.contains(label) && click(n)) return true;
            for(int i=0;i<n.getChildCount();i++){ AccessibilityNodeInfo child=n.getChild(i); if(child!=null) q.add(child); }
        } return false;
    }
    private boolean click(AccessibilityNodeInfo n){ AccessibilityNodeInfo x=n; while(x!=null){ if(x.isClickable()&&x.isEnabled()) return x.performAction(AccessibilityNodeInfo.ACTION_CLICK); x=x.getParent(); } return false; }
}
