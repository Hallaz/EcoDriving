package com.pertamina.tbbm.rewulu.ecodriving.clients;

import java.util.Map;

import retrofit.RestAdapter;
import retrofit.http.FieldMap;
import retrofit.http.FormUrlEncoded;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;

import com.pertamina.tbbm.rewulu.ecodriving.pojos.UserData;
import com.pertamina.tbbm.rewulu.ecodriving.utils.Api;
import com.pertamina.tbbm.rewulu.ecodriving.utils.Error.MyErrorHandler;
import com.pertamina.tbbm.rewulu.ecodriving.utils.Loggers;

public class UserClient {
	// $name, $email, $addrss, $city, $dob, $phone_model, $os

	public static class ResponseUser {
		public boolean error;
		public String message;
		public int row_id = -1;
		public String api_key;
	}

	private interface RegisterCallback {
		@FormUrlEncoded
		@POST("/reg")
		public ResponseUser register(@FieldMap Map<String, String> field);
	}

	private interface SessionCallback {
		@FormUrlEncoded
		@PUT("/session/:{id}")
		public ResponseUser session(@Path("id") int id,
				@Header("Authorization") String api_key,
				@FieldMap Map<String, String> field);
	}

	public static ResponseUser register(UserData userdata) {
		RestAdapter restAdapter = new RestAdapter.Builder()
				.setEndpoint(Api.API_URL).setErrorHandler(new MyErrorHandler())
				.setLogLevel(RestAdapter.LogLevel.FULL).build();
		RegisterCallback callback = restAdapter.create(RegisterCallback.class);
		try {
			return callback.register(userdata.getParameterMap());
		} catch (Exception e) {
			// TODO: handle exception
			Loggers.e("RegisterClient", "" + e.toString());
		}
		return null;
	}

	public static ResponseUser session(UserData userdata) {
		RestAdapter restAdapter = new RestAdapter.Builder()
				.setEndpoint(Api.API_URL).setErrorHandler(new MyErrorHandler())
				.setLogLevel(RestAdapter.LogLevel.FULL).build();
		SessionCallback callback = restAdapter.create(SessionCallback.class);
		try {
			return callback.session(userdata.getRow_id(),
					userdata.getApi_key(), userdata.getParameterMap());
		} catch (Exception e) {
			// TODO: handle exception
			Loggers.e("SessionClient", "" + e.toString());
		}
		return null;
	}
}
