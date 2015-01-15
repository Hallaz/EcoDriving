package com.pertamina.tbbm.rewulu.ecodriving.clients;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.http.Body;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Path;

import com.pertamina.tbbm.rewulu.ecodriving.adapters.LogsConverter;
import com.pertamina.tbbm.rewulu.ecodriving.pojos.DataLog;
import com.pertamina.tbbm.rewulu.ecodriving.pojos.ResponseLog;
import com.pertamina.tbbm.rewulu.ecodriving.utils.Api;
import com.pertamina.tbbm.rewulu.ecodriving.utils.Error.MyErrorHandler;
import com.pertamina.tbbm.rewulu.ecodriving.utils.Loggers;

public class LogsClient {

	public static class ResponseLogs {
		public boolean error = true;
		public String message = "";
		public List<ResponseLog> logs;
	}

	private interface LogCallback {
		@POST("/logs/:{id}")
		public void trip(@Path("id") int id,
				@Header("Authorization") String api_key,
				@Body List<DataLog> logs, Callback<ResponseLogs> c);
	}

	public static void logging(List<DataLog> logs,
			Callback<ResponseLogs> c) {
		RestAdapter restAdapter = new RestAdapter.Builder()
				.setEndpoint(Api.API_URL).setErrorHandler(new MyErrorHandler())
				.setConverter(new LogsConverter())
				.setLogLevel(RestAdapter.LogLevel.FULL).build();
		LogCallback callback = restAdapter.create(LogCallback.class);
		try {
			callback.trip(logs.get(0).getTripdata().getUser().getRow_id(), logs
					.get(0).getTripdata().getUser().getApi_key(), logs, c);
		} catch (Exception e) {
			// TODO: handle exception
			Loggers.e("LogClientss ", "" + e.toString());
		}
	}
}
