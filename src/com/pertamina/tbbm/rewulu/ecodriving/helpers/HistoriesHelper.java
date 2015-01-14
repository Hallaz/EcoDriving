package com.pertamina.tbbm.rewulu.ecodriving.helpers;

import java.util.ArrayList;
import java.util.List;

import com.pertamina.tbbm.rewulu.ecodriving.databases.DataLogAdapter;
import com.pertamina.tbbm.rewulu.ecodriving.databases.TripDataAdapter;
import com.pertamina.tbbm.rewulu.ecodriving.pojos.DataLog;
import com.pertamina.tbbm.rewulu.ecodriving.pojos.Tripdata;

import android.content.Context;

public class HistoriesHelper {
	private Context context;
	private List<Tripdata> trips;
	private List<DataLogTrip> logs;

	public HistoriesHelper(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
		initial();
	}

	public void load() {
		initial();
		this.trips = TripDataAdapter.readAllTrip(context);
		for (int w = 0; w < trips.size(); w++)
			if (trips.get(w).isRunning()) {
				trips.remove(w);
				w -= 1;
			}
		for (Tripdata trip : trips) {
			DataLogTrip log = new DataLogTrip(trip.getLocal_id());
			log.setLogs(DataLogAdapter.readAllLogByTrip(context, trip));
			logs.add(log);
		}
	}

	public List<Tripdata> getTrips() {
		return this.trips;
	}

	public List<DataLog> getLogs(Tripdata trip) {
		for (DataLogTrip log : logs)
			if (log.getLocal_id() == trip.getLocal_id())
				return log.getLogs();
		return null;
	}

	private void initial() {
		// TODO Auto-generated method stub
		trips = new ArrayList<>();
		logs = new ArrayList<>();
	}

	private class DataLogTrip {
		private int local_id;
		private List<DataLog> logs;

		public DataLogTrip(int local_id) {
			// TODO Auto-generated constructor stub
			this.setLocal_id(local_id);
		}

		/**
		 * @return the logs
		 */
		public List<DataLog> getLogs() {
			return logs;
		}

		/**
		 * @param logs
		 *            the logs to set
		 */
		public void setLogs(List<DataLog> logs) {
			this.logs = logs;
		}

		/**
		 * @return the local_id
		 */
		public int getLocal_id() {
			return local_id;
		}

		/**
		 * @param local_id
		 *            the local_id to set
		 */
		public void setLocal_id(int local_id) {
			this.local_id = local_id;
		}
	}
}
