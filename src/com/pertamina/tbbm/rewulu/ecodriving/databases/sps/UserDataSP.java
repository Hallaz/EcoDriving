package com.pertamina.tbbm.rewulu.ecodriving.databases.sps;

import android.content.Context;
import android.content.SharedPreferences;

import com.pertamina.tbbm.rewulu.ecodriving.pojos.UserData;

public class UserDataSP {
	private static final String KEY = "user-data_sp_"
			+ UserDataSP.class.getSimpleName();

	public static void put(Context context, UserData userData) {
		SharedPreferences sp = context.getSharedPreferences(KEY,
				Context.MODE_PRIVATE);
		sp.edit().putString(UserData.KEY_USER_ADDRESS, userData.getAddrss())
				.apply();
		sp.edit().putString(UserData.KEY_USER_API_KEY, userData.getApi_key())
				.apply();
		sp.edit().putString(UserData.KEY_USER_CITY, userData.getCity()).apply();
		sp.edit().putString(UserData.KEY_USER_DOB, userData.getDob()).apply();
		sp.edit().putString(UserData.KEY_USER_EMAIL, userData.getEmail())
				.apply();
		sp.edit().putString(UserData.KEY_USER_NAME, userData.getName()).apply();
		sp.edit().putString(UserData.KEY_USER_OS, userData.getOs()).apply();
		sp.edit()
				.putString(UserData.KEY_USER_PHONE_MODEL,
						userData.getPhone_model()).apply();
		sp.edit().putInt(UserData.KEY_USER_ROWID, userData.getRow_id()).apply();
	}

	public static UserData get(Context context) {
		UserData user = null;
		SharedPreferences sp = context.getSharedPreferences(KEY,
				Context.MODE_PRIVATE);
		if (sp.getString(UserData.KEY_USER_ADDRESS, null) != null) {
			user = new UserData();
			user.setAddrss(sp.getString(UserData.KEY_USER_ADDRESS, KEY));
			user.setApi_key(sp.getString(UserData.KEY_USER_API_KEY, KEY));
			user.setCity(sp.getString(UserData.KEY_USER_CITY, KEY));
			user.setDob(sp.getString(UserData.KEY_USER_DOB, KEY));
			user.setEmail(sp.getString(UserData.KEY_USER_EMAIL, KEY));
			user.setName(sp.getString(UserData.KEY_USER_NAME, KEY));
			user.setOs(sp.getString(UserData.KEY_USER_OS, KEY));
			user.setPhone_model(sp
					.getString(UserData.KEY_USER_PHONE_MODEL, KEY));
			user.setRow_id(sp.getInt(UserData.KEY_USER_ROWID, 0));
		}
		return user;
	}

	public static boolean isEmty(Context context) {
		SharedPreferences sp = context.getSharedPreferences(KEY,
				Context.MODE_PRIVATE);
		return sp.getString(UserData.KEY_USER_ADDRESS, null) == null;
	}

}
