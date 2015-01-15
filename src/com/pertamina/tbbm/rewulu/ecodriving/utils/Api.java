package com.pertamina.tbbm.rewulu.ecodriving.utils;

public class Api {
	public final static String API_URL = "http://data.ecodrivingclub.com/api";
	public final static String INVALID_API_KEY = "Access Denied. Invalid Api key";
	private final static String TRIP_URL = "http://trip.ecodrivingclub.com/";
	public final static String ERROR_UPDATE = "Oops! An error occurred while updating";

	private String generateLink() {
		String l = String.valueOf(time);
		String nl = String.copyValueOf(l.toCharArray(), 0, l.length() / 2);
		return TRIP_URL + user_id
				+ new String(new char[] { randChar(), randChar() }) + trip_id
				+ new String(new char[] { randChar(), randChar() }) + nl;
	}

	private String user_name;
	private String title;
	private int user_id;
	private int trip_id;
	private long time;

	public Api(Builder builder) {
		// TODO Auto-generated constructor stub
		this.user_name = builder.user_name;
		this.time = builder.time;
		this.title = builder.title;
		this.trip_id = builder.trip_id;
		this.user_id = builder.user_id;
	}

	public String shareFormatterBody() {
		return title + ". Perjalanan via " + generateLink();
	}

	public String shareFormatterSubject() {
		return user_name + " membagi perjalanan ";
	}

	private static char randChar() {
		int rnd = (int) (Math.random() * 52);
		char base = (rnd < 26) ? 'A' : 'a';
		return (char) (base + rnd % 26);
	}

	public static class Builder {
		private String user_name = "";
		private String title = "";
		private int user_id;
		private int trip_id;
		private long time;

		public Builder() {
			// TODO Auto-generated constructor stub
		}

		public Builder userName(String user_name) {
			this.user_name = user_name;
			return this;
		}

		public Builder title(String title) {
			this.title = title;
			return this;
		}

		public Builder userId(int user_id) {
			this.user_id = user_id;
			return this;
		}

		public Builder tripId(int trip_id) {
			this.trip_id = trip_id;
			return this;
		}

		public Builder time(long time) {
			this.time = time;
			return this;
		}

		public Api build() {
			return new Api(this);
		}
	}

}
