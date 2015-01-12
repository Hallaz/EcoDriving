package com.pertamina.tbbm.rewulu.ecodriving.utils;

import android.util.Log;

public class Loggers {
	public static boolean LOG = false;

	public static void v(String tag, String text) {
		if (LOG)
			Log.v(tag, text);
	}

	public static void v(long tag, String text) {
		if (LOG)
			Log.v("" + tag, text);
	}

	public static void v(String tag, long text) {
		if (LOG)
			Log.v(tag, "" + text);
	}

	public static void v(long tag, long text) {
		if (LOG)
			Log.v("" + tag, "" + text);
	}

	public static void v(double tag, String text) {
		if (LOG)
			Log.v("" + tag, text);
	}

	public static void v(String tag, double text) {
		if (LOG)
			Log.v(tag, "" + text);
	}

	public static void v(double tag, double text) {
		if (LOG)
			Log.v("" + tag, "" + text);
	}

	public static void d(String tag, String text) {
		if (LOG)
			Log.d(tag, text);
	}

	public static void d(long tag, String text) {
		if (LOG)
			Log.d("" + tag, text);
	}

	public static void d(String tag, long text) {
		if (LOG)
			Log.d(tag, "" + text);
	}

	public static void d(long tag, long text) {
		if (LOG)
			Log.d("" + tag, "" + text);
	}

	public static void d(double tag, String text) {
		if (LOG)
			Log.d("" + tag, text);
	}

	public static void d(String tag, double text) {
		if (LOG)
			Log.d(tag, "" + text);
	}

	public static void d(double tag, double text) {
		if (LOG)
			Log.d("" + tag, "" + text);
	}

	public static void i(String tag, String text) {
		if (LOG)
			Log.i(tag, text);
	}

	public static void i(long tag, String text) {
		if (LOG)
			Log.i("" + tag, text);
	}

	public static void i(String tag, long text) {
		if (LOG)
			Log.i(tag, "" + text);
	}

	public static void i(long tag, long text) {
		if (LOG)
			Log.i("" + tag, "" + text);
	}

	public static void i(double tag, String text) {
		if (LOG)
			Log.i("" + tag, text);
	}

	public static void i(String tag, double text) {
		if (LOG)
			Log.i(tag, "" + text);
	}

	public static void i(double tag, double text) {
		if (LOG)
			Log.i("" + tag, "" + text);
	}

	public static void w(String tag, String text) {
		if (LOG)
			Log.w(tag, text);
	}

	public static void w(long tag, String text) {
		if (LOG)
			Log.w("" + tag, text);
	}

	public static void w(String tag, long text) {
		if (LOG)
			Log.w(tag, "" + text);
	}

	public static void w(long tag, long text) {
		if (LOG)
			Log.w("" + tag, "" + text);
	}

	public static void w(double tag, String text) {
		if (LOG)
			Log.w("" + tag, text);
	}

	public static void w(String tag, double text) {
		if (LOG)
			Log.w(tag, "" + text);
	}

	public static void w(double tag, double text) {
		if (LOG)
			Log.w("" + tag, "" + text);
	}

	public static void e(String tag, String text) {
		if (LOG)
			Log.e(tag, text);
	}

	public static void e(long tag, String text) {
		if (LOG)
			Log.e("" + tag, text);
	}

	public static void e(String tag, long text) {
		if (LOG)
			Log.e(tag, "" + text);
	}

	public static void e(long tag, long text) {
		if (LOG)
			Log.e("" + tag, "" + text);
	}

	public static void e(double tag, String text) {
		if (LOG)
			Log.e("" + tag, text);
	}

	public static void e(String tag, double text) {
		if (LOG)
			Log.e(tag, "" + text);
	}

	public static void e(double tag, double text) {
		if (LOG)
			Log.e("" + tag, "" + text);
	}
}
