package com.pertamina.tbbm.rewulu.ecodriving.utils;

import java.net.InetAddress;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Vibrator;

import com.pertamina.tbbm.rewulu.ecodriving.R;

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
		if (hours > 0) {
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
