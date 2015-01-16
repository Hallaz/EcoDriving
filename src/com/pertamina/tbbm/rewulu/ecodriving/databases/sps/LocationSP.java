package com.pertamina.tbbm.rewulu.ecodriving.databases.sps;

import java.util.Calendar;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;

public class LocationSP {
	private static final String LOCATION_SP = "location-sp_"
			+ LocationSP.class.getSimpleName();
	private static final String LOCATION_SP_LATITUDE = "location-sp-lat";
	private static final String LOCATION_SP_LONGITUTE = "location-sp-longi";
	private static final String LOCATION_SP_PROVIDER = "location-sp-provider";
	private static final String LOCATION_SP_LAST_TIME = "location-sp-last_time";
	private static Calendar calendar = Calendar.getInstance();

	public static void saveKnownLocation(Context context, Location location) {
		SharedPreferences sp = context.getSharedPreferences(LOCATION_SP,
				Context.MODE_PRIVATE);
		sp.edit().putLong(LOCATION_SP_LATITUDE, (long) location.getLatitude())
				.apply();
		sp.edit()
				.putLong(LOCATION_SP_LONGITUTE, (long) location.getLongitude())
				.apply();
		sp.edit().putString(LOCATION_SP_PROVIDER, location.getProvider())
				.apply();
		sp.edit().putLong(LOCATION_SP_LAST_TIME, location.getTime()).apply();
	}

	public static Location getLastKnownLocation(Context context) {
		SharedPreferences sp = context.getSharedPreferences(LOCATION_SP,
				Context.MODE_PRIVATE);
		long lat = sp.getLong(LOCATION_SP_LATITUDE, 0);
		long longi = sp.getLong(LOCATION_SP_LONGITUTE, 0);
		String provider = sp.getString(LOCATION_SP_PROVIDER, null);
		if (lat == 0 || longi == 0 || provider == null)
			return getInaLocation();
		Location location = new Location(provider);
		location.setLatitude((double) lat);
		location.setLongitude((double) longi);
		location.setTime(sp.getLong(LOCATION_SP_LAST_TIME, 0));
		return location;
	}

	private static Location getInaLocation() {
		Location location = new Location(LocationManager.GPS_PROVIDER);
		location.setLatitude(6.1750D);
		location.setLongitude(106.8283D);
		return location;
	}

	public static boolean isNeedKnowingLocation(Context context) {
		SharedPreferences sp = context.getSharedPreferences(LOCATION_SP,
				Context.MODE_PRIVATE);
		long time = sp.getLong(LOCATION_SP_LAST_TIME, 0);
		if (time == 0 || calendar.getTimeInMillis() - time > 600l)
			return true;
		return false;
	}

}
