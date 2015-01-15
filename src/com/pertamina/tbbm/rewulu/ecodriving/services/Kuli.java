package com.pertamina.tbbm.rewulu.ecodriving.services;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.RetrofitError.Kind;
import retrofit.client.Response;
import android.app.IntentService;
import android.content.Intent;
import android.os.AsyncTask;

import com.pertamina.tbbm.rewulu.ecodriving.clients.LogsClient;
import com.pertamina.tbbm.rewulu.ecodriving.clients.LogsClient.ResponseLogs;
import com.pertamina.tbbm.rewulu.ecodriving.clients.TripClient;
import com.pertamina.tbbm.rewulu.ecodriving.clients.TripClient.ResponseData;
import com.pertamina.tbbm.rewulu.ecodriving.clients.UserClient;
import com.pertamina.tbbm.rewulu.ecodriving.clients.UserClient.ResponseUser;
import com.pertamina.tbbm.rewulu.ecodriving.databases.DataLogAdapter;
import com.pertamina.tbbm.rewulu.ecodriving.databases.TripDataAdapter;
import com.pertamina.tbbm.rewulu.ecodriving.databases.sps.UserDataSP;
import com.pertamina.tbbm.rewulu.ecodriving.helpers.ResultData;
import com.pertamina.tbbm.rewulu.ecodriving.locations.GeocoderEngine;
import com.pertamina.tbbm.rewulu.ecodriving.pojos.DataLog;
import com.pertamina.tbbm.rewulu.ecodriving.pojos.Tripdata;
import com.pertamina.tbbm.rewulu.ecodriving.pojos.UserData;
import com.pertamina.tbbm.rewulu.ecodriving.utils.Api;
import com.pertamina.tbbm.rewulu.ecodriving.utils.Loggers;
import com.pertamina.tbbm.rewulu.ecodriving.utils.Utils;

public class Kuli extends IntentService {

	public Kuli() {
		super("Kuli");
		// TODO Auto-generated constructor stub
	}

	private List<Tripdata> trips;
	private List<Tripdata> inCompletes;
	private List<Tripdata> running;

	@Override
	protected void onHandleIntent(Intent intent) {
		// TODO Auto-generated method stub
		Loggers.i("Kuli", "START");
		runTask();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Loggers.i("Kuli", "onDestroy");
		try {
			builder.cancel(true);
			deletes.cancel(true);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private void runTask() {
		// TODO Auto-generated method stub
		trips = TripDataAdapter.readAllTrip(getApplicationContext());
		inCompletes = new ArrayList<Tripdata>();
		running = new ArrayList<Tripdata>();
		if (trips == null) {
			Loggers.i("Kuli", "trips == null");
			return;
		}
		if (trips.isEmpty()) {
			Loggers.i("Kuli", "trips.isEmpty()");
			return;
		}
		for (Tripdata trip : trips) {
			if (trip.isIncomplete()) {
				inCompletes.add(trip);
			} else if (trip.isRunning() || trip.getUser() == null
					|| trip.getMotor() == null) {
				running.add(trip);
			}
		}

		if (!inCompletes.isEmpty()
				&& !builder.getStatus().equals(AsyncTask.Status.RUNNING)) {
			Loggers.i("Kuli", "Trying to build trip - inCompletes.size() "
					+ inCompletes.size());
			builder = new Builders(inCompletes);
			builder.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
		} else if (!running.isEmpty()
				&& !deletes.getStatus().equals(AsyncTask.Status.RUNNING)) {
			Loggers.i("Kuli", "Trying to delete trip - running.size() "
					+ running.size());
			deletes = new Delete(running);
			deletes.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
		}
	}

	private Builders builder = new Builders();
	private Delete deletes = new Delete();

	private class Builders extends AsyncTask<Tripdata, Boolean, Boolean> {
		private UserData user;
		private List<Tripdata> tripsToBuild;
		private int pivot;
		private boolean tripRequested;
		private boolean logsRequested;
		private boolean api_keyResquested;
		private boolean requestUpdateAPI_KEY;
		private boolean tripComplete, logsComplete;
		private List<DataLog> logs;
		private List<DataLog> unLogged;

		public Builders() {
			// TODO Auto-generated constructor stub
		}

		public Builders(List<Tripdata> trips) {
			// TODO Auto-generated constructor stub
			this.tripsToBuild = trips;
			pivot = trips.size() - 1;
			buildLogs();
		}

		private void buildLogs() {
			// TODO Auto-generated method stub
			logs = DataLogAdapter.readAllLogByTrip(getApplicationContext(),
					tripsToBuild.get(pivot));
			unLogged = new ArrayList<>();
			for (DataLog log : logs)
				if (log.getRow_id() < 0)
					unLogged.add(log);
			user = UserDataSP.get(getApplicationContext());
		}

		private Callback<ResponseData> tripCallback = new Callback<TripClient.ResponseData>() {

			@Override
			public void success(ResponseData arg0, Response arg1) {
				// TODO Auto-generated method stub
				tripsToBuild.get(pivot).setRow_id(arg0.row_id);
				TripDataAdapter.updateTrip(getApplicationContext(),
						tripsToBuild.get(pivot));
				tripComplete = true;
				report();
				tripRequested = false;
			}

			@Override
			public void failure(RetrofitError arg0) {
				// TODO Auto-generated method stub
				if (arg0.getMessage() != null)
					if (arg0.getMessage().contains(Api.INVALID_API_KEY)) {
						requestUpdateAPI_KEY = true;
						tripRequested = false;
						return;
					}
				if (arg0.getKind().equals(Kind.HTTP)) {
					tripsToBuild.get(pivot).setRunning();
					TripDataAdapter.updateTrip(getApplicationContext(),
							tripsToBuild.get(pivot));
					tripComplete = true;
					tripRequested = false;
					report();
				}
				tripRequested = false;
			}
		};

		private Callback<ResponseLogs> logsCallback = new Callback<LogsClient.ResponseLogs>() {

			@Override
			public void success(ResponseLogs arg0, Response arg1) {
				// TODO Auto-generated method stub
				if (!arg0.error) {
					if (arg0.logs != null) {
						for (int w = 0; w < unLogged.size(); w++) {
							for (int q = 0; q < arg0.logs.size(); q++) {
								if (!arg0.logs.get(q).error
										&& arg0.logs.get(q).id == unLogged.get(
												w).getId()) {
									unLogged.get(w).setRow_id(
											arg0.logs.get(q).row_id);
									DataLogAdapter.updateLog(
											getApplicationContext(),
											unLogged.get(w));
									unLogged.remove(w);
									arg0.logs.remove(q);
									w -= 1;
									q -= 1;
								}
								if (w < 0 || q < 0)
									break;
							}
						}
					}
				}
				if (unLogged.isEmpty()) {
					logsComplete = true;
					report();
				}
				logsRequested = false;
			}

			@Override
			public void failure(RetrofitError arg0) {
				// TODO Auto-generated method stub
				if (arg0.getMessage() != null)
					if (arg0.getMessage().contains(Api.INVALID_API_KEY)) {
						requestUpdateAPI_KEY = true;
						tripRequested = false;
					}
				tripRequested = false;
			}
		};

		private Callback<ResponseUser> registerCallback = new Callback<UserClient.ResponseUser>() {

			@Override
			public void failure(RetrofitError arg0) {
				// TODO Auto-generated method stub
				requestUpdateAPI_KEY = true;
				api_keyResquested = false;
			}

			@Override
			public void success(ResponseUser arg0, Response arg1) {
				// TODO Auto-generated method stub
				user.setRow_id(arg0.row_id);
				user.setApi_key(arg0.api_key);
				UserDataSP.put(getApplicationContext(), user);
				requestUpdateAPI_KEY = false;
				api_keyResquested = false;
			}
		};

		private void report() {
			// TODO Auto-generated method stub
			if (tripComplete && logsComplete) {
				pivot -= 1;
				tripComplete = false;
				logsComplete = false;
				buildLogs();
			}
		}

		@Override
		protected Boolean doInBackground(Tripdata... params) {
			boolean net = Utils.isInternetAvailable();
			while (net) {
				if (pivot < 0 || tripsToBuild.isEmpty())
					return true;
				if (requestUpdateAPI_KEY) {
					if (!api_keyResquested) {
						UserClient.register(user, registerCallback);
						api_keyResquested = true;
					}
				} else if (tripsToBuild.size() >= pivot
						&& !tripsToBuild.get(pivot).isRunning()) {
					if (!tripRequested) {
						if (!tripsToBuild.get(pivot).isAddressStartSet()) {
							Loggers.i("Kuli", "getAddress Start");
							GeocoderEngine geocoder = new GeocoderEngine(
									getApplicationContext());
							String addrss = geocoder.getAddress(logs.get(0)
									.getLatitude(), logs.get(0).getLongitude());
							if (addrss != null) {
								Loggers.i("Kuli", "result getAddress Start"
										+ addrss);
								tripsToBuild.get(pivot).setAddrss_start(addrss);
							}
						}
						if (!tripsToBuild.get(pivot).isAddressEndSet()) {
							Loggers.i("Kuli", "getAddress End");
							GeocoderEngine geocoder = new GeocoderEngine(
									getApplicationContext());
							String addrss = geocoder.getAddress(
									logs.get(logs.size() - 1).getLatitude(),
									logs.get(logs.size() - 1).getLongitude());
							if (addrss != null) {
								Loggers.i("Kuli", "result getAddress End"
										+ addrss);
								tripsToBuild.get(pivot).setAddrss_end(addrss);
							}
						}
						if (tripsToBuild.get(pivot).getEco_fuel() < 0) {
							tripsToBuild.get(pivot).setUser(user, "Builder");
							ResultData result = new ResultData(
									tripsToBuild.get(pivot), logs);
							tripsToBuild.get(pivot).setEco_fuel(
									result.getEcoFuel());
							tripsToBuild.get(pivot).setNon_eco_fuel(
									result.getNonEcoFuel());
							tripsToBuild.get(pivot).setEco_distance(
									result.getEcoDistance());
							tripsToBuild.get(pivot).setNon_eco_distance(
									result.getNonEcoDistance());
						} else if (tripsToBuild.get(pivot).isAddressEndSet()
								&& tripsToBuild.get(pivot).isAddressStartSet()) {

							Loggers.i("Kuli",
									"trying to update trip size ");
							tripsToBuild.get(pivot).Log("Kuli");
							TripDataAdapter.updateTrip(getApplicationContext(),
									tripsToBuild.get(pivot));
							TripClient.update(tripsToBuild.get(pivot),
									tripCallback);
							tripRequested = true;
						}
					}
					
					if (!logsRequested && !unLogged.isEmpty()) {
						tripsToBuild.get(pivot).setUser(user, "Builder2");
						List<DataLog> temp = new ArrayList<>();
						Loggers.i("Kuli",
								"trying to logs size " + unLogged.size());
						for (DataLog log : unLogged) {
							log.setTripdata(tripsToBuild.get(pivot));
							temp.add(log);
							if (temp.size() > 30)
								break;
						}
						LogsClient.logging(temp, logsCallback);
						logsRequested = true;
					}
				}
				try {
					Thread.sleep(5000);
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
			return true;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (result != null)
				if (result)
					runTask();
		}
	}

	private class Delete extends AsyncTask<Tripdata, Boolean, Boolean> {

		private List<Tripdata> tripsToDelete;
		private UserData user;
		private int pivot;
		private boolean requestedDelete;
		private boolean requestedUpdateAPI_KEY;
		private boolean api_keyRequested;

		private Callback<ResponseData> deleteCallback = new Callback<TripClient.ResponseData>() {

			@Override
			public void failure(RetrofitError arg0) {
				// TODO Auto-generated method stub
				if (arg0.getMessage() != null)
					if (arg0.getMessage().contains(Api.INVALID_API_KEY)) {
						requestedUpdateAPI_KEY = true;
						return;
					}
				if (arg0.getKind().equals(Kind.HTTP)) {
					TripDataAdapter.deleteById(getApplicationContext(),
							tripsToDelete.get(pivot));
					tripsToDelete.remove(pivot);
					requestedDelete = false;
					pivot -= 1;
				}
				requestedDelete = false;
			}

			@Override
			public void success(ResponseData arg0, Response arg1) {
				// TODO Auto-generated method stub
				for (int w = 0; w < tripsToDelete.size(); w++) {
					if (tripsToDelete.get(w).getRow_id() == arg0.row_id) {
						TripDataAdapter.deleteById(getApplicationContext(),
								tripsToDelete.get(w));
						tripsToDelete.remove(w);
						break;
					}
				}
				requestedDelete = false;
				pivot -= 1;
			}
		};
		private Callback<ResponseUser> registerCallback = new Callback<UserClient.ResponseUser>() {

			@Override
			public void failure(RetrofitError arg0) {
				// TODO Auto-generated method stub
				requestedUpdateAPI_KEY = true;
				api_keyRequested = false;
			}

			@Override
			public void success(ResponseUser arg0, Response arg1) {
				// TODO Auto-generated method stub
				user.setRow_id(arg0.row_id);
				user.setApi_key(arg0.api_key);
				UserDataSP.put(getApplicationContext(), user);
				requestedUpdateAPI_KEY = false;
				api_keyRequested = false;
			}
		};

		public Delete() {
			// TODO Auto-generated constructor stub
		}

		public Delete(List<Tripdata> trips) {
			// TODO Auto-generated constructor stub
			this.tripsToDelete = trips;
			user = UserDataSP.get(getApplicationContext());
			pivot = trips.size() - 1;
		}

		@Override
		protected Boolean doInBackground(Tripdata... params) {
			boolean net = Utils.isInternetAvailable();
			while (net) {
				if (pivot < 0 || tripsToDelete.isEmpty())
					return true;
				if (requestedUpdateAPI_KEY) {
					if (!api_keyRequested) {
						UserClient.register(user, registerCallback);
						api_keyRequested = true;
					}
				} else if (tripsToDelete.size() >= pivot) {
					if (!requestedDelete) {
						TripClient.delete(tripsToDelete.get(pivot),
								deleteCallback);
						requestedDelete = true;
					}
				}
			}
			return true;
		}
	}

}
