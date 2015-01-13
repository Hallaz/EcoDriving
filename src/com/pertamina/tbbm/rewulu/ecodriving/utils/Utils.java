package com.pertamina.tbbm.rewulu.ecodriving.utils;

import java.net.InetAddress;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import com.pertamina.tbbm.rewulu.ecodriving.R;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.location.Location;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Vibrator;

public class Utils {
	private static final String classname = Utils.class.getSimpleName();

	public static class FirstStartSP {
		private static final String START_SP = "START_SP_ECO" + classname;

		public static boolean onFirstStart(Context context) {
			SharedPreferences sp = context.getSharedPreferences(START_SP,
					Context.MODE_PRIVATE);
			return sp.getBoolean(START_SP, true);
		}

		public static void setFirstStart(Context context) {
			SharedPreferences sp = context.getSharedPreferences(START_SP,
					Context.MODE_PRIVATE);
			sp.edit().putBoolean(START_SP, false).apply();
		}
	}

	public static class LocationSP {
		private static final String LOCATION_SP = "location-sp" + classname;
		private static final String LOCATION_SP_LATITUDE = "location-sp-lat";
		private static final String LOCATION_SP_LONGITUTE = "location-sp-longi";
		private static final String LOCATION_SP_PROVIDER = "location-sp-provider";
		private static final String LOCATION_SP_LAST_TIME = "location-sp-last_time";
		private static Calendar calendar = Calendar.getInstance();

		public static void saveKnownLocation(Context context, Location location) {
			SharedPreferences sp = context.getSharedPreferences(LOCATION_SP,
					Context.MODE_PRIVATE);
			sp.edit()
					.putLong(LOCATION_SP_LATITUDE,
							(long) location.getLatitude()).apply();
			sp.edit()
					.putLong(LOCATION_SP_LONGITUTE,
							(long) location.getLongitude()).apply();
			sp.edit().putString(LOCATION_SP_PROVIDER, location.getProvider())
					.apply();
			sp.edit().putLong(LOCATION_SP_LAST_TIME, location.getTime())
					.apply();
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

	public static class TrackingSP {
		private static final String TRACKING_SP = "TRACKING_SP" + classname;
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

	public static class Indicator {
		private static Notification notif;
		private static NotificationManager notificationManager;
		private static Vibrator vib;
		private static MediaPlayer mediaPlayer;

		public static void prepare(Context context) {
			notif = new Notification();
			notificationManager = (NotificationManager) context
					.getSystemService(Context.NOTIFICATION_SERVICE);
			vib = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
			mediaPlayer = new MediaPlayer();
		}

		public static void led(Context context, int color) {
			if (notif == null || notificationManager == null)
				prepare(context);
			if (!Setting.LED)
				return;
			notif.ledARGB = color;
			notif.flags = Notification.FLAG_SHOW_LIGHTS;
			notif.ledOnMS = 100;
			notif.ledOffMS = 100;
			notificationManager.notify(2, notif);
		}

		public static void longVib(Context context) {
			if (vib == null)
				prepare(context);
			vib.vibrate(1000);
		}

		public static void vib(Context context) {
			if (vib == null)
				prepare(context);
			if (!Setting.VIBRATE)
				return;
			vib.vibrate(300);
		}

		public static void playSound(Context context) {
			if (mediaPlayer == null)
				prepare(context);
			if (!Setting.VOICE)
				return;
			if (mediaPlayer != null) {
				try {
					mediaPlayer.reset();
				} catch (Exception e) {
					// TODO: handle exception
					Loggers.e("err", "err1 :" + e.toString());
				}
			}
			AssetFileDescriptor afd = context.getResources().openRawResourceFd(
					R.raw.warn_non_eco_sound);
			try {
				mediaPlayer.setDataSource(afd.getFileDescriptor(),
						afd.getStartOffset(), afd.getLength());
				afd.close();
				mediaPlayer.prepareAsync();
			} catch (Exception e) {
				// TODO: handle exception
				Loggers.e("err", "err  :" + e.toString());
			}
			mediaPlayer.setOnPreparedListener(new OnPreparedListener() {
				@Override
				public void onPrepared(MediaPlayer mp) {
					mediaPlayer.start();
				}
			});
		}

	}

	public static void toast(Context context, int resource) {
		toast(context, context.getString(resource));
	}

	public static void toast(Context context, String text) {
		android.widget.Toast.makeText(context, text,
				android.widget.Toast.LENGTH_SHORT).show();
	}

	public static class Distance {

		public static int getKMperSec(double mPs) {
			// TODO Auto-generated method stub
			return (int) (mPs * 60 * 60) / 1000;
		}
	}

	public static String capitalize(String s) {
		if (s == null || s.length() == 0) {
			return "";
		}
		char first = s.charAt(0);
		if (Character.isUpperCase(first)) {
			return s;
		} else {
			return Character.toUpperCase(first) + s.substring(1);
		}
	}

	public static boolean isInternetAvailable() {
		try {
			InetAddress ipAddr = InetAddress.getByName("google.com");
			if (ipAddr.equals("")) {
				return false;
			} else {
				return true;
			}

		} catch (Exception e) {
			Loggers.e("isInternetAvailable ", e.toString());
			return false;
		}
	}

	public static long getNowTime() {
		Calendar cal = Calendar.getInstance();
		return cal.getTimeInMillis();
	}

	private static NumberFormat formatter = new DecimalFormat("###.#########");

	public static String decimalFormater(double d) {
		return formatter.format(d);
	}

	private static SimpleDateFormat dataFormatter = new SimpleDateFormat(
			"dd/MM/yyyy hh:mm");

	public static String dateFormatter(long milis) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(milis);
		return dataFormatter.format(calendar.getTime());
	}

	public static String getDurationBreakdown(long millis) {
		if (millis < 0) {
			return "-0";
		}

		long hours = TimeUnit.MILLISECONDS.toHours(millis);
		millis -= TimeUnit.HOURS.toMillis(hours);
		long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
		millis -= TimeUnit.MINUTES.toMillis(minutes);
		long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);

		StringBuilder sb = new StringBuilder(64);
		if(hours > 0) {
			sb.append(hours);
			sb.append("j ");
		}
		sb.append(minutes);
		sb.append("m ");
		sb.append(seconds);
		sb.append("s");

		return (sb.toString());
	}
}
