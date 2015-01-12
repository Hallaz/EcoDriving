package com.pertamina.tbbm.rewulu.ecodriving.helpers;

import java.util.List;

import android.content.Context;
import android.os.CountDownTimer;

import com.pertamina.tbbm.rewulu.ecodriving.databases.LogDataAdapter;
import com.pertamina.tbbm.rewulu.ecodriving.databases.TripDataAdapter;
import com.pertamina.tbbm.rewulu.ecodriving.listener.OnMainListener;
import com.pertamina.tbbm.rewulu.ecodriving.pojos.DataLog;
import com.pertamina.tbbm.rewulu.ecodriving.pojos.Tripdata;
import com.pertamina.tbbm.rewulu.ecodriving.utils.Loggers;

public class TrackingTest {
	private OnMainListener callback;
	private Tripdata trip;
	List<DataLog> logs;

	public TrackingTest(Context context, Tripdata tripss,
			OnMainListener listener) {
		// TODO Auto-generated constructor stub
		List<Tripdata> trips = TripDataAdapter.readAllTrip(context);
		this.trip = trips.get(0);
		this.trip.setRow_id(-1);
		logs = LogDataAdapter.readAllLogByTrip(context, this.trip);
		for (Tripdata tp : trips) {
			List<DataLog> lg = LogDataAdapter.readAllLogByTrip(context, tp);
			if (lg.size() > logs.size())
				logs = lg;
		}
		callback = listener;
		// timer.start();
		callback.rqstResult(trip, logs);
	}

	CountDownTimer timer = new CountDownTimer(Long.MAX_VALUE, 2000) {

		@Override
		public void onTick(long millisUntilFinished) {
			// TODO Auto-generated method stub
			Loggers.i("" + millisUntilFinished, "logs.size() " + logs.size());
			if (!logs.isEmpty()) {
				logs.get(0).setRow_id(-1);
				callback.storeLog(logs.get(0));
				logs.remove(0);
			} else {
				callback.startResult(trip.getArrayGraph_time());
				callback.setDataTrip(11, 21, 50, 60);
				this.cancel();
			}
		}

		@Override
		public void onFinish() {
			// TODO Auto-generated method stub
		}
	};
}
