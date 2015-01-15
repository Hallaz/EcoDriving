package com.pertamina.tbbm.rewulu.ecodriving.clients;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.http.GET;
import retrofit.http.Path;

import com.pertamina.tbbm.rewulu.ecodriving.pojos.Motor;
import com.pertamina.tbbm.rewulu.ecodriving.utils.Api;
import com.pertamina.tbbm.rewulu.ecodriving.utils.Loggers;

public class MotorClient {

	public static class ResponseMotor {
		public boolean error = true;
		public String message = "";
		public List<Motor> motors;
	}

	interface MotorCallback {
		@GET("/askmotor/:{email}")
		public void getMotors(@Path("email") String email,
				Callback<ResponseMotor> c);
	}

	public static void retrieveData(final String email,
			Callback<ResponseMotor> c) {
		RestAdapter restAdapter = new RestAdapter.Builder()
				.setEndpoint(Api.API_URL)
				.setLogLevel(RestAdapter.LogLevel.FULL).build();
		MotorCallback motorList = restAdapter.create(MotorCallback.class);
		try {
			motorList.getMotors(email, c);
		} catch (Exception e) {
			// TODO: handle exception
			Loggers.e("MotorClient ", e.toString());
		}
	}
}
