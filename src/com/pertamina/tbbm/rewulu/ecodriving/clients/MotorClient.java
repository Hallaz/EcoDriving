package com.pertamina.tbbm.rewulu.ecodriving.clients;

import java.util.List;

import retrofit.RestAdapter;
import retrofit.http.GET;
import retrofit.http.Path;

import com.pertamina.tbbm.rewulu.ecodriving.pojos.Motor;
import com.pertamina.tbbm.rewulu.ecodriving.utils.Api;
import com.pertamina.tbbm.rewulu.ecodriving.utils.Error.MyErrorHandler;
import com.pertamina.tbbm.rewulu.ecodriving.utils.Loggers;

public class MotorClient {
	
	private static class ResponseMotor{
		private boolean error;
		private String message;
		private List<Motor> motors;
	}
	
	interface MotorCallback {
		@GET("/askmotor/:{email}")
		public ResponseMotor getMotors(@Path("email") String email);
	}
	
	public static List<Motor> retrieveData(final String email) {
		RestAdapter restAdapter = new RestAdapter.Builder()
				.setEndpoint(Api.API_URL).setErrorHandler(new MyErrorHandler())
                .setLogLevel(RestAdapter.LogLevel.FULL)
				.build();
		MotorCallback motorList = restAdapter.create(MotorCallback.class);
		try {
			ResponseMotor motor = motorList.getMotors(email);
			if(!motor.error)
				return motor.motors;
			else 
				Loggers.w("MotorClient", motor.message);;
		} catch (Exception e) {
			// TODO: handle exception
			Loggers.e("MotorClient ", e.toString());
		}
		return null;
	}
}
