package com.pertamina.tbbm.rewulu.ecodriving.utils;

public class Api {
	public final static String API_URL = "http://data.ecodrivingclub.com/api";
	public final static String INVALID_API_KEY = "401 Unauthorized";
	private final static String TRIP_URL = "http://trip.ecodrivingclub.com/";
	public final static String ERROR_UPDATE = "Oops! An error occurred while updating";

	private final String[] TABLE_URI = new String[] { "t", "v", "8", "f", "b",
			"a", "z", "7", "p", "9" };

	private String generateLink() {
		Integer[] user = getRow(user_id);
		Integer[] trip = getRow(trip_id);
		String link = new String();
		for (int w = 0; w < user.length; w++)
			link += TABLE_URI[user[w]];
		link += "e";
		for (int w = 0; w < trip.length; w++)
			link += TABLE_URI[trip[w]];
		link += "/";
		String[] t = title.split("\\s+");
		String tile = new String();
		for (int w = 0; w < t.length; w++) {
			if (w == 0)
				tile = t[w];
			else
				tile += "-" + t[w];
		}
		return TRIP_URL + link.trim() + tile.toLowerCase().trim();
	}

	private Integer[] getRow(int raw) {
		String t = Integer.toString(raw);
		Integer[] row = new Integer[t.length()];
		for (int w = 0; w < t.length(); w++)
			row[w] = t.charAt(w) - '0';
		return row;
	}

	private String user_name;
	private String title;
	private int user_id;
	private int trip_id;

	public Api(Builder builder) {
		// TODO Auto-generated constructor stub
		this.user_name = builder.user_name;
		this.title = builder.title;
		this.trip_id = builder.trip_id;
		this.user_id = builder.user_id;
	}

	public String shareFormatterBody() {
		return '"' + title + '"' + ". Perjalanan via " + generateLink();
	}

	public String shareFormatterSubject() {
		return user_name + " membagi perjalanan ";
	}

	public static class Builder {
		private String user_name = "";
		private String title = "";
		private int user_id;
		private int trip_id;

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

		public Api build() {
			return new Api(this);
		}
	}

}
