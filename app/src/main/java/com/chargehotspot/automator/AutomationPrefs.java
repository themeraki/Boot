package com.chargehotspot.automator;

import android.content.Context;
import android.content.SharedPreferences;

final class AutomationPrefs {
    static final String ACTION_ENABLE = "com.chargehotspot.automator.ENABLE";
    static final String ACTION_DISABLE = "com.chargehotspot.automator.DISABLE";
    private static final String FILE = "automation";
    private AutomationPrefs() {}
    static SharedPreferences prefs(Context context) { return context.getSharedPreferences(FILE, Context.MODE_PRIVATE); }
    static boolean enabled(Context context) { return prefs(context).getBoolean("enabled", true); }
    static boolean returnHome(Context context) { return prefs(context).getBoolean("return_home", true); }
    static void setPending(Context context, boolean on) { prefs(context).edit().putBoolean("pending", true).putBoolean("target_on", on).apply(); }
    static void clearPending(Context context) { prefs(context).edit().putBoolean("pending", false).apply(); }
    static boolean pending(Context context) { return prefs(context).getBoolean("pending", false); }
    static boolean targetOn(Context context) { return prefs(context).getBoolean("target_on", false); }
}
