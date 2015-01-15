package com.pertamina.tbbm.rewulu.ecodriving.clients;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.http.GET;
import retrofit.http.Path;

import com.pertamina.tbbm.rewulu.ecodriving.utils.Api;
import com.pertamina.tbbm.rewulu.ecodriving.utils.Error.MyErrorHandler;
import com.pertamina.tbbm.rewulu.ecodriving.utils.Loggers;

public class UpdateClient {

	public static class ResponseUpdate {
		public boolean error;
		public String message = "";
		public String created_at = "";
	}

	interface UpdateCallback {
		@GET("/check/:{email}")
		public void update(@Path("email") String email,
				Callback<ResponseUpdate> c);
	}

	public static void update(final String email,
			Callback<ResponseUpdate> c) {
		RestAdapter restAdapter = new RestAdapter.Builder()
				.setEndpoint(Api.API_URL).setErrorHandler(new MyErrorHandler())
				.setLogLevel(RestAdapter.LogLevel.FULL).build();
		UpdateCallback motorList = restAdapter.create(UpdateCallback.class);
		try {
			motorList.update(email, c);
		} catch (Exception e) {
			// TODO: handle exception
			Loggers.e("UpdateClient ", e.toString());
		}
	}
}
