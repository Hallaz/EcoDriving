package com.pertamina.tbbm.rewulu.ecodriving.utils;

import android.util.Log;

public class Loggers {
	public static boolean LOG = false;
	private static String FLAG = "";

	public static void getInstance(String FLAG) {
		Loggers.FLAG = FLAG + " - ";
	}

	public static void v(String tag, String arg0) {
		if (LOG)
			Log.v(FLAG + tag, arg0);
	}

	public static void v(long tag, String arg0) {
		if (LOG)
			Log.v(FLAG + tag, arg0);
	}

	public static void v(String tag, long arg0) {
		if (LOG)
			Log.v(FLAG + tag, "" + arg0);
	}

	public static void v(long tag, long arg0) {
		if (LOG)
			Log.v(FLAG + tag, "" + arg0);
	}

	public static void v(double tag, String arg0) {
		if (LOG)
			Log.v(FLAG + tag, arg0);
	}

	public static void v(String tag, double arg0) {
		if (LOG)
			Log.v(FLAG + tag, "" + arg0);
	}

	public static void v(double tag, double arg0) {
		if (LOG)
			Log.v(FLAG + tag, "" + arg0);
	}

	public static void d(String tag, String arg0) {
		if (LOG)
			Log.d(FLAG + tag, arg0);
	}

	public static void d(long tag, String arg0) {
		if (LOG)
			Log.d(FLAG + tag, arg0);
	}

	public static void d(String tag, long arg0) {
		if (LOG)
			Log.d(FLAG + tag, "" + arg0);
	}

	public static void d(long tag, long arg0) {
		if (LOG)
			Log.d(FLAG + tag, "" + arg0);
	}

	public static void d(double tag, String arg0) {
		if (LOG)
			Log.d(FLAG + tag, arg0);
	}

	public static void d(String tag, double arg0) {
		if (LOG)
			Log.d(FLAG + tag, "" + arg0);
	}

	public static void d(double tag, double arg0) {
		if (LOG)
			Log.d(FLAG + tag, "" + arg0);
	}

	public static void i(String tag, String arg0) {
		if (LOG)
			Log.i(FLAG + tag, arg0);
	}

	public static void i(long tag, String arg0) {
		if (LOG)
			Log.i(FLAG + tag, arg0);
	}

	public static void i(String tag, long arg0) {
		if (LOG)
			Log.i(FLAG + tag, "" + arg0);
	}

	public static void i(long tag, long arg0) {
		if (LOG)
			Log.i(FLAG + tag, "" + arg0);
	}

	public static void i(double tag, String arg0) {
		if (LOG)
			Log.i(FLAG + tag, arg0);
	}

	public static void i(String tag, double arg0) {
		if (LOG)
			Log.i(FLAG + tag, "" + arg0);
	}

	public static void i(double tag, double arg0) {
		if (LOG)
			Log.i(FLAG + tag, "" + arg0);
	}

	public static void w(String tag, String arg0) {
		if (LOG)
			Log.w(FLAG + tag, arg0);
	}

	public static void w(long tag, String arg0) {
		if (LOG)
			Log.w(FLAG + tag, arg0);
	}

	public static void w(String tag, long arg0) {
		if (LOG)
			Log.w(FLAG + tag, "" + arg0);
	}

	public static void w(long tag, long arg0) {
		if (LOG)
			Log.w(FLAG + tag, "" + arg0);
	}

	public static void w(double tag, String arg0) {
		if (LOG)
			Log.w(FLAG + tag, arg0);
	}

	public static void w(String tag, double arg0) {
		if (LOG)
			Log.w(FLAG + tag, "" + arg0);
	}

	public static void w(double tag, double arg0) {
		if (LOG)
			Log.w(FLAG + tag, "" + arg0);
	}

	public static void e(String tag, String arg0) {
		if (LOG)
			Log.e(FLAG + tag, arg0);
	}

	public static void e(long tag, String arg0) {
		if (LOG)
			Log.e(FLAG + tag, arg0);
	}

	public static void e(String tag, long arg0) {
		if (LOG)
			Log.e(FLAG + tag, "" + arg0);
	}

	public static void e(long tag, long arg0) {
		if (LOG)
			Log.e(FLAG + tag, "" + arg0);
	}

	public static void e(double tag, String arg0) {
		if (LOG)
			Log.e(FLAG + tag, arg0);
	}

	public static void e(String tag, double arg0) {
		if (LOG)
			Log.e(FLAG + tag, "" + arg0);
	}

	public static void e(double tag, double arg0) {
		if (LOG)
			Log.e(FLAG + tag, "" + arg0);
	}
}
