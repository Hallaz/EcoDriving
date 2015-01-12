package com.pertamina.tbbm.rewulu.ecodriving.clients;

import java.util.List;

import com.pertamina.tbbm.rewulu.ecodriving.pojos.DataLog;
import com.pertamina.tbbm.rewulu.ecodriving.pojos.ResponseLog;
import com.pertamina.tbbm.rewulu.ecodriving.utils.Api;
import com.pertamina.tbbm.rewulu.ecodriving.utils.Error.MyErrorHandler;
import com.pertamina.tbbm.rewulu.ecodriving.utils.Loggers;

import retrofit.RestAdapter;
import retrofit.http.Body;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Path;

public class LogsClient {

	public static class ResponseLogs {
		public boolean error;
		public String message;
		public List<ResponseLog> logs;
	}

	private interface LogCallback {
		@POST("/logs/:{id}")
		public ResponseLogs trip(@Path("id") int id,
				@Header("Authorization") String api_key,
				@Body List<DataLog> logs);
	}

	public static ResponseLogs logging(List<DataLog> logs) {
		RestAdapter restAdapter = new RestAdapter.Builder()
				.setEndpoint(Api.API_URL).setErrorHandler(new MyErrorHandler())
				.setConverter(new LogsConverter())
				.setLogLevel(RestAdapter.LogLevel.FULL).build();
		LogCallback callback = restAdapter.create(LogCallback.class);
		try {
			return callback.trip(logs.get(0).getTripdata().getUser()
					.getRow_id(), logs.get(0).getTripdata().getUser()
					.getApi_key(), logs);
		} catch (Exception e) {
			// TODO: handle exception
			Loggers.e("LogClientss ", "" + e.toString());
		}
		return null;
	}
}
