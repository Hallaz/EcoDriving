package com.pertamina.tbbm.rewulu.ecodriving.helpers;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.CountDownTimer;

import com.pertamina.tbbm.rewulu.ecodriving.databases.DataLogAdapter;
import com.pertamina.tbbm.rewulu.ecodriving.databases.TripDataAdapter;
import com.pertamina.tbbm.rewulu.ecodriving.listener.OnMainListener;
import com.pertamina.tbbm.rewulu.ecodriving.pojos.DataLog;
import com.pertamina.tbbm.rewulu.ecodriving.pojos.Tripdata;
import com.pertamina.tbbm.rewulu.ecodriving.utils.Loggers;

public class TrackingTest {
	private OnMainListener callback;
	private Tripdata trip;
	private List<DataLog> logs;

	public TrackingTest(Context context, Tripdata tripss,
			OnMainListener listener) {
		// TODO Auto-generated constructor stub
		List<Tripdata> trips = TripDataAdapter.readAllTrip(context);
		for(Tripdata tp : trips)
			if(tp.getLocal_id() == 15)
				this.trip = tp;
		logs = DataLogAdapter.readAllLogByTrip(context, trip);
		Loggers.getInstance("TrackingTest");
		Loggers.i("", trips.size());
		//this.bulder();
		callback = listener;
		
		//timer.start();
		// callback.rqstResult(trip, logs);
	}

	CountDownTimer timer = new CountDownTimer(Long.MAX_VALUE, 1100) {

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

	private void bulder() {
		// TODO Auto-generated method stub
		List<Lokasi> lokasi = new ArrayList<>();
		lokasi.add(new Lokasi(-7.76408, 110.38967));
		lokasi.add(new Lokasi(-7.76317, 110.3929));
		lokasi.add(new Lokasi(-7.76276, 110.39387));
		lokasi.add(new Lokasi(-7.76408, 110.38967));
		lokasi.add(new Lokasi(-7.76317, 110.3929));
		lokasi.add(new Lokasi(-7.76276, 110.39387));
		lokasi.add(new Lokasi(-7.76408, 110.38967));
		lokasi.add(new Lokasi(-7.76317, 110.3929));
		lokasi.add(new Lokasi(-7.76276, 110.39387));
		lokasi.add(new Lokasi(-7.76408, 110.38967));
		lokasi.add(new Lokasi(-7.76317, 110.3929));
		lokasi.add(new Lokasi(-7.76276, 110.39387));
		lokasi.add(new Lokasi(-7.76408, 110.38967));
		lokasi.add(new Lokasi(-7.76317, 110.3929));
		lokasi.add(new Lokasi(-7.76276, 110.39387));
		lokasi.add(new Lokasi(-7.76408, 110.38967));
		lokasi.add(new Lokasi(-7.76317, 110.3929));
		lokasi.add(new Lokasi(-7.76276, 110.39387));

		lokasi.add(new Lokasi(-7.76408, 110.38967));
		lokasi.add(new Lokasi(-7.76317, 110.3929));
		lokasi.add(new Lokasi(-7.76276, 110.39387));
		lokasi.add(new Lokasi(-7.76408, 110.38967));
		lokasi.add(new Lokasi(-7.76317, 110.3929));
		lokasi.add(new Lokasi(-7.76276, 110.39387));
		lokasi.add(new Lokasi(-7.76408, 110.38967));
		lokasi.add(new Lokasi(-7.76317, 110.3929));
		lokasi.add(new Lokasi(-7.76276, 110.39387));
		lokasi.add(new Lokasi(-7.76408, 110.38967));
		lokasi.add(new Lokasi(-7.76317, 110.3929));
		lokasi.add(new Lokasi(-7.76276, 110.39387));
		lokasi.add(new Lokasi(-7.76408, 110.38967));
		lokasi.add(new Lokasi(-7.76317, 110.3929));
		lokasi.add(new Lokasi(-7.76276, 110.39387));
		lokasi.add(new Lokasi(-7.76408, 110.38967));
		lokasi.add(new Lokasi(-7.76317, 110.3929));
		lokasi.add(new Lokasi(-7.76276, 110.39387));

		lokasi.add(new Lokasi(-7.76408, 110.38967));
		lokasi.add(new Lokasi(-7.76317, 110.3929));
		lokasi.add(new Lokasi(-7.76276, 110.39387));
		lokasi.add(new Lokasi(-7.76408, 110.38967));
		lokasi.add(new Lokasi(-7.76317, 110.3929));
		lokasi.add(new Lokasi(-7.76276, 110.39387));
		lokasi.add(new Lokasi(-7.76408, 110.38967));
		lokasi.add(new Lokasi(-7.76317, 110.3929));
		lokasi.add(new Lokasi(-7.76276, 110.39387));
		lokasi.add(new Lokasi(-7.76408, 110.38967));
		lokasi.add(new Lokasi(-7.76317, 110.3929));
		lokasi.add(new Lokasi(-7.76276, 110.39387));
		lokasi.add(new Lokasi(-7.76408, 110.38967));
		lokasi.add(new Lokasi(-7.76317, 110.3929));
		lokasi.add(new Lokasi(-7.76276, 110.39387));
		lokasi.add(new Lokasi(-7.76408, 110.38967));
		lokasi.add(new Lokasi(-7.76317, 110.3929));
		lokasi.add(new Lokasi(-7.76276, 110.39387));

		lokasi.add(new Lokasi(-7.76408, 110.38967));
		lokasi.add(new Lokasi(-7.76317, 110.3929));
		lokasi.add(new Lokasi(-7.76276, 110.39387));
		lokasi.add(new Lokasi(-7.76408, 110.38967));
		lokasi.add(new Lokasi(-7.76317, 110.3929));
		lokasi.add(new Lokasi(-7.76276, 110.39387));
		lokasi.add(new Lokasi(-7.76408, 110.38967));
		lokasi.add(new Lokasi(-7.76317, 110.3929));
		lokasi.add(new Lokasi(-7.76276, 110.39387));
		lokasi.add(new Lokasi(-7.76408, 110.38967));
		lokasi.add(new Lokasi(-7.76317, 110.3929));
		lokasi.add(new Lokasi(-7.76276, 110.39387));
		lokasi.add(new Lokasi(-7.76408, 110.38967));
		lokasi.add(new Lokasi(-7.76317, 110.3929));
		lokasi.add(new Lokasi(-7.76276, 110.39387));
		lokasi.add(new Lokasi(-7.76408, 110.38967));
		lokasi.add(new Lokasi(-7.76317, 110.3929));
		lokasi.add(new Lokasi(-7.76276, 110.39387));

		lokasi.add(new Lokasi(-7.76408, 110.38967));
		lokasi.add(new Lokasi(-7.76317, 110.3929));
		lokasi.add(new Lokasi(-7.76276, 110.39387));
		lokasi.add(new Lokasi(-7.76408, 110.38967));
		lokasi.add(new Lokasi(-7.76317, 110.3929));
		lokasi.add(new Lokasi(-7.76276, 110.39387));
		lokasi.add(new Lokasi(-7.76408, 110.38967));
		lokasi.add(new Lokasi(-7.76317, 110.3929));
		lokasi.add(new Lokasi(-7.76276, 110.39387));
		lokasi.add(new Lokasi(-7.76408, 110.38967));
		lokasi.add(new Lokasi(-7.76317, 110.3929));
		lokasi.add(new Lokasi(-7.76276, 110.39387));
		lokasi.add(new Lokasi(-7.76408, 110.38967));
		lokasi.add(new Lokasi(-7.76317, 110.3929));
		lokasi.add(new Lokasi(-7.76276, 110.39387));
		lokasi.add(new Lokasi(-7.76408, 110.38967));
		lokasi.add(new Lokasi(-7.76317, 110.3929));
		lokasi.add(new Lokasi(-7.76276, 110.39387));

		lokasi.add(new Lokasi(-7.76408, 110.38967));
		lokasi.add(new Lokasi(-7.76317, 110.3929));
		lokasi.add(new Lokasi(-7.76276, 110.39387));
		lokasi.add(new Lokasi(-7.76408, 110.38967));
		lokasi.add(new Lokasi(-7.76317, 110.3929));
		lokasi.add(new Lokasi(-7.76276, 110.39387));
		lokasi.add(new Lokasi(-7.76408, 110.38967));
		lokasi.add(new Lokasi(-7.76317, 110.3929));
		lokasi.add(new Lokasi(-7.76276, 110.39387));
		lokasi.add(new Lokasi(-7.76408, 110.38967));
		lokasi.add(new Lokasi(-7.76317, 110.3929));
		lokasi.add(new Lokasi(-7.76276, 110.39387));
		lokasi.add(new Lokasi(-7.76408, 110.38967));
		lokasi.add(new Lokasi(-7.76317, 110.3929));
		lokasi.add(new Lokasi(-7.76276, 110.39387));
		lokasi.add(new Lokasi(-7.76408, 110.38967));
		lokasi.add(new Lokasi(-7.76317, 110.3929));
		lokasi.add(new Lokasi(-7.76276, 110.39387));
		logs = new ArrayList<>();
		for (int w = 0; w < lokasi.size(); w++) { //108 data
			boolean drive_state = false;
			if (w % 2 == 0)
				drive_state = true;
			DataLog log = new DataLog(w, trip.getRow_id(), w, drive_state,
					w + 10, w + 12323, w + 5, w, lokasi.get(w).lat,
					lokasi.get(w).longi);
			logs.add(log);
		}
	}

	private class Lokasi {
		double lat;
		double longi;

		public Lokasi(double la, double lo) {
			// TODO Auto-generated constructor stub
			lat = la;
			longi = lo;
		}
	}
}
