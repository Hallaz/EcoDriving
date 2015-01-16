package com.pertamina.tbbm.rewulu.ecodriving.databases.sps;

import android.content.Context;
import android.content.SharedPreferences;

public class SettingSP {
	public static boolean LED;
	public static boolean VIBRATE;
	public static boolean VOICE;
	private static final String SETTING_SP = "SETTING_SP"
			+ SettingSP.class.getSimpleName();
	private static final String LED_SP = "LED-SP";
	private static final String VIBRATE_SP = "vibrate-sp";
	private static final String VOICE_SP = "vibrate-SP";

	public static void loadSetting(Context context) {
		SharedPreferences sp = context.getSharedPreferences(SETTING_SP,
				Context.MODE_PRIVATE);
		sp.edit().putBoolean(LED_SP, LED).apply();
		sp.edit().putBoolean(VIBRATE_SP, VIBRATE).apply();
		sp.edit().putBoolean(VOICE_SP, VOICE).apply();
	}

	public static void saveSetting(Context context) {
		SharedPreferences sp = context.getSharedPreferences(SETTING_SP,
				Context.MODE_PRIVATE);
		LED = sp.getBoolean(LED_SP, true);
		VIBRATE = sp.getBoolean(VIBRATE_SP, true);
		VOICE = sp.getBoolean(VOICE_SP, true);
	}

}
