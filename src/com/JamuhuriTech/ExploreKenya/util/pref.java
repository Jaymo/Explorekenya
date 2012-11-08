package com.JamuhuriTech.ExploreKenya.util;

import android.content.Context;
import android.content.SharedPreferences;

public class pref {
	public static boolean AutoFetch= true ;
	
	public static boolean vibrate =  true;

    public static boolean flashLed = true;

    public static final int NOTIFICATION_ID = 1;

    public static final String PREFS_NAME = "ExploreKenyaService";

    private static SharedPreferences settings;

    private static SharedPreferences.Editor editor;
    
    public static int events = 0;

    public static void loadSettings(Context context) {
        final SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
        events=settings.getInt("Events", events);
        vibrate = settings.getBoolean("vibrate", vibrate);
        flashLed= settings.getBoolean("flashLed", flashLed);
        
    }
    

    public static void saveSettings(Context context) {
        settings = context.getSharedPreferences(PREFS_NAME, 0);
        editor = settings.edit();
        editor.putInt("Events", events);
        editor.putBoolean("vibrate", vibrate);
        editor.putBoolean("flashLed", flashLed);
        editor.commit();
    }
}
