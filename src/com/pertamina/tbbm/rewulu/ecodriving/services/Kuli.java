package com.pertamina.tbbm.rewulu.ecodriving.services;

import java.util.ArrayList;
import java.util.List;

import com.pertamina.tbbm.rewulu.ecodriving.clients.LogsClient;
import com.pertamina.tbbm.rewulu.ecodriving.clients.TripClient;
import com.pertamina.tbbm.rewulu.ecodriving.clients.UserClient;
import com.pertamina.tbbm.rewulu.ecodriving.clients.LogsClient.ResponseLogs;
import com.pertamina.tbbm.rewulu.ecodriving.clients.TripClient.ResponseData;
import com.pertamina.tbbm.rewulu.ecodriving.clients.UserClient.ResponseUser;
import com.pertamina.tbbm.rewulu.ecodriving.databases.DataLogAdapter;
import com.pertamina.tbbm.rewulu.ecodriving.databases.TripDataAdapter;
import com.pertamina.tbbm.rewulu.ecodriving.databases.sps.UserDataSP;
import com.pertamina.tbbm.rewulu.ecodriving.helpers.ResultData;
import com.pertamina.tbbm.rewulu.ecodriving.locations.GeocoderEngine;
import com.pertamina.tbbm.rewulu.ecodriving.pojos.DataLog;
import com.pertamina.tbbm.rewulu.ecodriving.pojos.ResponseLog;
import com.pertamina.tbbm.rewulu.ecodriving.pojos.Tripdata;
import com.pertamina.tbbm.rewulu.ecodriving.pojos.UserData;
import com.pertamina.tbbm.rewulu.ecodriving.utils.Api;
import com.pertamina.tbbm.rewulu.ecodriving.utils.Loggers;
import com.pertamina.tbbm.rewulu.ecodriving.utils.Utils;

import android.app.IntentService;
import android.content.Intent;
import android.os.AsyncTask;

public class Kuli extends IntentService {
	private static boolean requestingUser = false;

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
		//runTask();
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
		private List<Tripdata> trips;

		public Builders() {
			// TODO Auto-generated constructor stub
		}

		public Builders(List<Tripdata> trips) {
			// TODO Auto-generated constructor stub
			this.trips = trips;
		}

		@Override
		protected Boolean doInBackground(Tripdata... params) {
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

		private List<Tripdata> trips;

		public Delete() {
			// TODO Auto-generated constructor stub
		}

		public Delete(List<Tripdata> trips) {
			// TODO Auto-generated constructor stub
			this.trips = trips;
		}

		@Override
		protected Boolean doInBackground(Tripdata... params) {
			return true;
		}
	}

}
