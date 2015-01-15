package com.pertamina.tbbm.rewulu.ecodriving.clients;

import java.util.Map;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.http.FieldMap;
import retrofit.http.FormUrlEncoded;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;

import com.pertamina.tbbm.rewulu.ecodriving.pojos.UserData;
import com.pertamina.tbbm.rewulu.ecodriving.utils.Api;
import com.pertamina.tbbm.rewulu.ecodriving.utils.Loggers;

public class UserClient {

	public static class ResponseUser {
		public boolean error = true;
		public String message = "";
		public int row_id = -1;
		public String api_key;
	}

	private interface RegisterCallback {
		@FormUrlEncoded
		@POST("/reg")
		public void register(@FieldMap Map<String, String> field,
				Callback<ResponseUser> c);
	}

	private interface SessionCallback {
		@FormUrlEncoded
		@PUT("/session/:{id}")
		public void session(@Path("id") int id,
				@Header("Authorization") String api_key,
				@FieldMap Map<String, String> field, Callback<ResponseUser> c);
	}

	public static void register(UserData userdata, Callback<ResponseUser> c) {
		RestAdapter restAdapter = new RestAdapter.Builder()
				.setEndpoint(Api.API_URL)
				.setLogLevel(RestAdapter.LogLevel.FULL).build();
		RegisterCallback callback = restAdapter.create(RegisterCallback.class);
		try {
			callback.register(userdata.getParameterMap(),c);
		} catch (Exception e) {
			// TODO: handle exception
			Loggers.e("RegisterClient", "" + e.toString());
		}
	}

	public static void session(UserData userdata, Callback<ResponseUser> c) {
		RestAdapter restAdapter = new RestAdapter.Builder()
				.setEndpoint(Api.API_URL)
				.setLogLevel(RestAdapter.LogLevel.FULL).build();
		SessionCallback callback = restAdapter.create(SessionCallback.class);
		try {
			callback.session(userdata.getRow_id(),
					userdata.getApi_key(), userdata.getParameterMap(), c);
		} catch (Exception e) {
			// TODO: handle exception
			Loggers.e("SessionClient", "" + e.toString());
		}
	}
}
