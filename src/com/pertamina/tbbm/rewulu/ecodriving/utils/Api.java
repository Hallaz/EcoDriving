package com.pertamina.tbbm.rewulu.ecodriving.utils;

import com.pertamina.tbbm.rewulu.ecodriving.pojos.Tripdata;

public class Api {
	public final static String API_URL = "http://data.ecodrivingclub.com/api";
	public final static String INVALID_API_KEY = "Access Denied. Invalid Api key";
	private final static String TRIP_URL = "http://trip.ecodrivingclub.com";

	private static String generateLink(int user_id, int trip_id, long time) {
		String l = String.valueOf(time);
		String nl = String.copyValueOf(l.toCharArray(), 0, l.length() / 2);
		return TRIP_URL + user_id
				+ new String(new char[] { randChar(), randChar() }) + trip_id
				+ new String(new char[] { randChar(), randChar() }) + nl;
	}

	public static String shareFormatter(Tripdata trip) {
		return trip.getTitle()
				+ ". Perjalanan via "
				+ generateLink(trip.getUser_id(), trip.getRow_id(),
						trip.getTime_start());
	}

	private static char randChar() {
		int rnd = (int) (Math.random() * 52);
		char base = (rnd < 26) ? 'A' : 'a';
		return (char) (base + rnd % 26);
	}

}
