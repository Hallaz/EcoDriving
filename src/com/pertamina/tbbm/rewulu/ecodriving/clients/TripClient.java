package com.pertamina.tbbm.rewulu.ecodriving.clients;

import java.util.Map;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.http.DELETE;
import retrofit.http.FieldMap;
import retrofit.http.FormUrlEncoded;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;

import com.pertamina.tbbm.rewulu.ecodriving.pojos.Tripdata;
import com.pertamina.tbbm.rewulu.ecodriving.utils.Api;
import com.pertamina.tbbm.rewulu.ecodriving.utils.Error.MyErrorHandler;
import com.pertamina.tbbm.rewulu.ecodriving.utils.Loggers;

public class TripClient {

	public static class ResponseData {
		public boolean error = true;
		public String message = "";
		public int row_id = -1;
		public String link_page;
	}

	private interface deleteCallback {
		@DELETE("/trip/:{id}/:{row}/:{time}")
		public void delete(@Path("id") int id, @Path("row") int row,
				@Path("time") long time,
				@Header("Authorization") String api_key,
				Callback<ResponseData> c);
	}

	private interface TripdataCallback {
		@FormUrlEncoded
		@POST("/trip/:{id}")
		public void trip(@Path("id") int id,
				@Header("Authorization") String api_key,
				@FieldMap Map<String, String> field, Callback<ResponseData> c);
	}

	private interface UpdateCallback {
		@FormUrlEncoded
		@PUT("/trip/:{id}/:{tripdata_id}")
		public void trip(@Path("id") int id,
				@Path("tripdata_id") int tripdata_idd,
				@Header("Authorization") String api_key,
				@FieldMap Map<String, String> field, Callback<ResponseData> c);
	}

	public static void trip(Tripdata tripdata, Callback<ResponseData> c) {
		RestAdapter restAdapter = new RestAdapter.Builder()
				.setEndpoint(Api.API_URL).setErrorHandler(new MyErrorHandler())
				.setLogLevel(RestAdapter.LogLevel.FULL).build();
		TripdataCallback callback = restAdapter.create(TripdataCallback.class);
		Loggers.i("TripClient", "@POST");
		try {
			callback.trip(tripdata.getUser().getRow_id(), tripdata.getUser()
					.getApi_key(), tripdata.getParameterMap(), c);
		} catch (Exception e) {
			// TODO: handle exception
			Loggers.e("TripdataClient ", "" + e.toString());
		}
	}

	public static void update(Tripdata tripdata, Callback<ResponseData> c) {
		RestAdapter restAdapter = new RestAdapter.Builder()
				.setEndpoint(Api.API_URL).setErrorHandler(new MyErrorHandler())
				.setLogLevel(RestAdapter.LogLevel.FULL).build();
		UpdateCallback callback = restAdapter.create(UpdateCallback.class);
		Loggers.i("TripClient", "@PUT");
		try {
			callback.trip(tripdata.getUser().getRow_id(), tripdata.getRow_id(),
					tripdata.getUser().getApi_key(),
					tripdata.getParameterMap(), c);
		} catch (Exception e) {
			// TODO: handle exception
			Loggers.e("TripdataClient ", "" + e.toString());
		}
	}

	public static void delete(Tripdata tripdata, Callback<ResponseData> c) {
		RestAdapter restAdapter = new RestAdapter.Builder()
				.setEndpoint(Api.API_URL).setErrorHandler(new MyErrorHandler())
				.setLogLevel(RestAdapter.LogLevel.FULL).build();
		deleteCallback callback = restAdapter.create(deleteCallback.class);
		Loggers.i("TripClient", "@DELETE");
		try {
			callback.delete(tripdata.getUser().getRow_id(), tripdata
					.getRow_id(), tripdata.getTime_start(), tripdata.getUser()
					.getApi_key(), c);
		} catch (Exception e) {
			// TODO: handle exception
			Loggers.e("TripdataClient ", "" + e.toString());
		}
	}

}
