package com.pertamina.tbbm.rewulu.ecodriving.clients;

import java.util.Map;

import com.pertamina.tbbm.rewulu.ecodriving.pojos.Tripdata;
import com.pertamina.tbbm.rewulu.ecodriving.utils.Api;
import com.pertamina.tbbm.rewulu.ecodriving.utils.Error.MyErrorHandler;
import com.pertamina.tbbm.rewulu.ecodriving.utils.Loggers;

import retrofit.RestAdapter;
import retrofit.http.DELETE;
import retrofit.http.FieldMap;
import retrofit.http.FormUrlEncoded;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;

public class TripClient {

	public static class ResponseData {
		public boolean error;
		public String message;
		public int row_id = -1;
		public String link_page;
	}

	private interface deleteCallback {
		@DELETE("/trip/:{id}/:{row}/:{time}")
		public ResponseData delete(@Path("id") int id, @Path("row") int row,
				@Path("time") long time, @Header("Authorization") String api_key);
	}

	private interface TripdataCallback {
		@FormUrlEncoded
		@POST("/trip/:{id}")
		public ResponseData trip(@Path("id") int id,
				@Header("Authorization") String api_key,
				@FieldMap Map<String, String> field);
	}

	private interface UpdateCallback {
		@FormUrlEncoded
		@PUT("/trip/:{id}/:{tripdata_id}")
		public ResponseData trip(@Path("id") int id,
				@Path("tripdata_id") int tripdata_idd,
				@Header("Authorization") String api_key,
				@FieldMap Map<String, String> field);
	}

	public static ResponseData trip(Tripdata tripdata) {
		RestAdapter restAdapter = new RestAdapter.Builder()
				.setEndpoint(Api.API_URL).setErrorHandler(new MyErrorHandler())
				.setLogLevel(RestAdapter.LogLevel.FULL).build();
		TripdataCallback callback = restAdapter.create(TripdataCallback.class);
		Loggers.i("TripClient", "@POST");
		try {
			return callback.trip(tripdata.getUser().getRow_id(), tripdata
					.getUser().getApi_key(), tripdata.getParameterMap());
		} catch (Exception e) {
			// TODO: handle exception
			Loggers.e("TripdataClient ", "" + e.toString());
		}
		return null;
	}

	public static ResponseData update(Tripdata tripdata) {
		RestAdapter restAdapter = new RestAdapter.Builder()
				.setEndpoint(Api.API_URL).setErrorHandler(new MyErrorHandler())
				.setLogLevel(RestAdapter.LogLevel.FULL).build();
		UpdateCallback callback = restAdapter.create(UpdateCallback.class);
		Loggers.i("TripClient", "@PUT");
		try {
			return callback.trip(tripdata.getUser().getRow_id(),
					tripdata.getRow_id(), tripdata.getUser().getApi_key(),
					tripdata.getParameterMap());
		} catch (Exception e) {
			// TODO: handle exception
			Loggers.e("TripdataClient ", "" + e.toString());
		}
		return null;
	}

	public static ResponseData delete(Tripdata tripdata) {
		RestAdapter restAdapter = new RestAdapter.Builder()
				.setEndpoint(Api.API_URL).setErrorHandler(new MyErrorHandler())
				.setLogLevel(RestAdapter.LogLevel.FULL).build();
		deleteCallback callback = restAdapter.create(deleteCallback.class);
		Loggers.i("TripClient", "@DELETE");
		try {
			return callback.delete(tripdata.getUser().getRow_id(), tripdata
					.getRow_id(), tripdata.getTime_start(), tripdata.getUser()
					.getApi_key());
		} catch (Exception e) {
			// TODO: handle exception
			Loggers.e("TripdataClient ", "" + e.toString());
		}
		return null;
	}

}
