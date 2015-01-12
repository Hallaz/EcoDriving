package com.pertamina.tbbm.rewulu.ecodriving.controllers;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.AsyncTask;

import com.pertamina.tbbm.rewulu.ecodriving.clients.LogsClient;
import com.pertamina.tbbm.rewulu.ecodriving.clients.MotorClient;
import com.pertamina.tbbm.rewulu.ecodriving.clients.TripClient;
import com.pertamina.tbbm.rewulu.ecodriving.clients.UserClient;
import com.pertamina.tbbm.rewulu.ecodriving.clients.LogsClient.ResponseLogs;
import com.pertamina.tbbm.rewulu.ecodriving.clients.TripClient.ResponseData;
import com.pertamina.tbbm.rewulu.ecodriving.clients.UserClient.ResponseUser;
import com.pertamina.tbbm.rewulu.ecodriving.databases.LogDataAdapter;
import com.pertamina.tbbm.rewulu.ecodriving.databases.TripDataAdapter;
import com.pertamina.tbbm.rewulu.ecodriving.databases.sps.UserDataSP;
import com.pertamina.tbbm.rewulu.ecodriving.listener.OnControllerCallback;
import com.pertamina.tbbm.rewulu.ecodriving.locations.GeocoderEngine;
import com.pertamina.tbbm.rewulu.ecodriving.pojos.DataLog;
import com.pertamina.tbbm.rewulu.ecodriving.pojos.Motor;
import com.pertamina.tbbm.rewulu.ecodriving.pojos.Tripdata;
import com.pertamina.tbbm.rewulu.ecodriving.pojos.UserData;
import com.pertamina.tbbm.rewulu.ecodriving.utils.Loggers;

public class ClientController {
	private boolean available;
	private Context context;
	private OnControllerCallback callback;

	public ClientController(Context context, OnControllerCallback callback) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.callback = callback;
		available = true;
	}

	public void setInternetAvailable(boolean available) {
		this.available = available;
	}

	public void destroy() {
		registrar.cancel(true);
		sessions.cancel(true);
		tripping.cancel(true);
		updateTrip.cancel(true);
		logging.cancel(true);
		deleteTrip.cancel(true);
		getAddress.cancel(true);
	}

	public void askDataMotor(String email) {
		if (available)
			new RetrieveMotor().executeOnExecutor(
					AsyncTask.THREAD_POOL_EXECUTOR, email);
	}

	public void upDateMotor(List<Motor> motors, String email) {
		if (available)
			new RetrieveMotor(motors).executeOnExecutor(
					AsyncTask.THREAD_POOL_EXECUTOR, email);
	}

	private class RetrieveMotor extends AsyncTask<String, String, List<Motor>> {
		private List<Motor> motors = new ArrayList<>();

		public RetrieveMotor() {
			// TODO Auto-generated constructor stub
		}

		public RetrieveMotor(List<Motor> motors) {
			// TODO Auto-generated constructor stub
			this.motors = motors;
		}

		@Override
		protected List<Motor> doInBackground(String... params) {
			// TODO Auto-generated method stub
			return MotorClient.retrieveData(params[0]);
		}

		@Override
		protected void onPostExecute(List<Motor> result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			callback.retrievingDataMotors(result, this.motors);
		}
	}

	public void register(UserData userdata) {
		if (userdata != null) {
			if (available) {
				if (userdata.getRow_id() < 0
						&& registrar.getStatus() != AsyncTask.Status.RUNNING) {
					registrar = new Registrar();
					registrar.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,
							userdata);
				} else
					session(userdata);
			} else {
				UserDataSP.put(context, userdata);
			}
		}
	}

	private Registrar registrar = new Registrar();

	private class Registrar extends AsyncTask<UserData, String, UserData> {

		@Override
		protected UserData doInBackground(UserData... params) {
			// TODO Auto-generated method stub
			ResponseUser res = UserClient.register(params[0]);
			if (res != null) {
				params[0].setApi_key(res.api_key);
				params[0].setRow_id(res.row_id);
			}
			UserDataSP.put(context, params[0]);
			return params[0];
		}

		@Override
		protected void onPostExecute(UserData result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (callback != null) {
				callback.registerResult(result);
			}
		}
	}

	public void session(UserData userdata) {
		if (available && sessions.getStatus() != AsyncTask.Status.RUNNING) {
			sessions = new Sessions();
			sessions.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, userdata);
		}
	}

	private Sessions sessions = new Sessions();

	private class Sessions extends AsyncTask<UserData, String, UserData> {

		@Override
		protected UserData doInBackground(UserData... params) {
			// TODO Auto-generated method stub
			ResponseUser res = UserClient.session(params[0]);
			if (res != null) {
				params[0].setApi_key(res.api_key);
				params[0].setRow_id(res.row_id);
				UserDataSP.put(context, params[0]);
				return params[0];
			}
			return params[0];
		}

		@Override
		protected void onPostExecute(UserData result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (callback != null) {
				callback.registerResult(result);
			}
		}
	}

	public void trip(Tripdata trip) {
		if (available && tripping.getStatus() != AsyncTask.Status.RUNNING) {
			tripping = new Tripping();
			tripping.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, trip);
		} else if (!available) {
			if (trip.getLocal_id() == -1)
				trip.setLocal_id((int) TripDataAdapter
						.insertTrip(context, trip));
		}
	}

	private Tripping tripping = new Tripping();

	private class Tripping extends AsyncTask<Tripdata, String, Tripdata> {

		@Override
		protected Tripdata doInBackground(Tripdata... params) {
			// TODO Auto-generated method stub
			ResponseData res = TripClient.trip(params[0]);
			if (res != null) {
				params[0].setRow_id(res.row_id);
				if (params[0].getLocal_id() == -1)
					params[0].setLocal_id((int) TripDataAdapter.insertTrip(
							context, params[0]));
				return params[0];
			} else {
				if (params[0].getLocal_id() == -1)
					params[0].setLocal_id((int) TripDataAdapter.insertTrip(
							context, params[0]));
				return params[0];
			}
		}

		@Override
		protected void onPostExecute(Tripdata result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (result != null) {
				callback.onTripResult(result);
			}
		}
	}

	public void updateTrip(Tripdata trip) {
		if (available && trip.getRow_id() >= 0
				&& updateTrip.getStatus() != AsyncTask.Status.RUNNING) {
			updateTrip = new UpdateTrip();
			updateTrip.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, trip);
		} else if (!available) {
			TripDataAdapter.updateTrip(context, trip);
			updateTrip = new UpdateTrip();
			callback.onUpdateTripResult(null);
		}
	}

	private UpdateTrip updateTrip = new UpdateTrip();

	private class UpdateTrip extends AsyncTask<Tripdata, String, ResponseData> {

		@Override
		protected ResponseData doInBackground(Tripdata... params) {
			// TODO Auto-generated method stub
			TripDataAdapter.updateTrip(context, params[0]);
			return TripClient.update(params[0]);
		}

		@Override
		protected void onPostExecute(ResponseData result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			callback.onUpdateTripResult(result);
		}

	}

	public void logData(List<DataLog> logs) {
		if (available && logging.getStatus() != AsyncTask.Status.RUNNING
				&& !logs.isEmpty()) {
			Loggers.i("", "logData " + logs.size());
			logging = new Logging(logs);
			logging.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
		} else if (!available) {
			for (DataLog log : logs)
				if (log.getLocal_id() < 0)
					log.setLocal_id((int) LogDataAdapter
							.insertLog(context, log));
		}
	}

	private Logging logging = new Logging();

	private class Logging extends AsyncTask<DataLog, String, ResponseLogs> {
		private List<DataLog> lgs;

		public Logging() {
			// TODO Auto-generated constructor stub

		}

		public Logging(List<DataLog> lgs) {
			// TODO Auto-generated constructor stub
			this.lgs = lgs;
		}

		@Override
		protected ResponseLogs doInBackground(DataLog... params) {
			// TODO Auto-generated method stub
			if (this.lgs == null)
				return null;
			for (DataLog log : lgs)
				if (log.getLocal_id() < 0)
					log.setLocal_id((int) LogDataAdapter
							.insertLog(context, log));
			return LogsClient.logging(this.lgs);
		}

		@Override
		protected void onPostExecute(ResponseLogs result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (result != null) {
				callback.onLoggingResult(result);
			}
		}
	}

	public void deleteTrip(Tripdata trip) {
		if (available && trip.getRow_id() >= 0
				&& deleteTrip.getStatus() != AsyncTask.Status.RUNNING) {
			deleteTrip = new DeleteTrip();
			deleteTrip.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, trip);
		} else if (!available) {
			callback.onDeletedTrip(null);
		}
	}

	private DeleteTrip deleteTrip = new DeleteTrip();

	private class DeleteTrip extends AsyncTask<Tripdata, String, Tripdata> {

		@Override
		protected Tripdata doInBackground(Tripdata... params) {
			// TODO Auto-generated method stub
			ResponseData res = TripClient.delete(params[0]);
			TripDataAdapter.deleteById(context, params[0]);
			if (res != null) {
				params[0].setRow_id(-1);
			}
			return params[0];
		}

		@Override
		protected void onPostExecute(Tripdata result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (result != null) {
				callback.onDeletedTrip(result);
			}
		}

	}

	public void requestAddressStart(DataLog log) {
		if(log == null)
			return;
		if (!getAddress.getStatus().equals(AsyncTask.Status.RUNNING)) {
			Loggers.i("", "requestAddressStarst");
			getAddress = new GetAddressTask();
			getAddress.setParams(context, true);
			getAddress.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,
					new Double[] { log.getLatitude(), log.getLongitude() });
		}
	}

	public void requestAddressEnd(DataLog log) {
		if(log == null)
			return;
		if (!getAddress.getStatus().equals(AsyncTask.Status.RUNNING)) {
			Loggers.i("", "requestAddressEnds");
			getAddress = new GetAddressTask();
			getAddress.setParams(context, false);
			getAddress.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,
					new Double[] { log.getLatitude(), log.getLongitude() });
		}
	}

	private GetAddressTask getAddress = new GetAddressTask();

	private class GetAddressTask extends AsyncTask<Double, Void, String> {
		private Context mContext;
		private boolean start;

		public void setParams(Context context, boolean addrss_start) {
			this.start = addrss_start;
			mContext = context;
		}

		@Override
		protected String doInBackground(Double... params) {
			double latitude = params[0];
			double longitude = params[1];
			GeocoderEngine geocoder = new GeocoderEngine(mContext);
			return geocoder.getAddress(latitude, longitude);

		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (start)
				callback.onStartAddressResult(result);
			else {
				callback.onEndAddressResult(result);
			}
		}
	}

}
