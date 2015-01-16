package com.pertamina.tbbm.rewulu.ecodriving.databases.sps;

import android.content.Context;
import android.content.SharedPreferences;

public class TrackingSP {
	private static final String TRACKING_SP = "TRACKING_SP" + TrackingSP.class.getSimpleName();
	private static final String ISRUNNING_SP = "ISRUNNING_SP";

	public static void setRunning(Context context, boolean arg0) {
		SharedPreferences sp = context.getSharedPreferences(TRACKING_SP,
				Context.MODE_PRIVATE);
		sp.edit().putBoolean(ISRUNNING_SP, arg0).apply();
	}

	public static boolean isRunning(Context context) {
		SharedPreferences sp = context.getSharedPreferences(TRACKING_SP,
				Context.MODE_PRIVATE);
		return sp.getBoolean(ISRUNNING_SP, false);
	}


}
