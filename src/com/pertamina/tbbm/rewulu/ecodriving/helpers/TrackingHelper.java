package com.pertamina.tbbm.rewulu.ecodriving.helpers;

import java.util.ArrayList;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.os.CountDownTimer;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.model.LatLng;
import com.pertamina.tbbm.rewulu.ecodriving.listener.OnTrackingHelperListener;
import com.pertamina.tbbm.rewulu.ecodriving.utils.Utils;

public class TrackingHelper implements ConnectionCallbacks,
		OnConnectionFailedListener, LocationListener {

	private LocationClient locationClient;
	private Context context;
	private OnTrackingHelperListener listener;
	private boolean countBoundRun;
	private boolean onBound;
	private int onLocationChangeCount = 0;
	private boolean onConnectedGPS;
	private Location location;
	private ArrayList<Location> locations;
	private final double NEGATIVE_ACCELERATION_LIMITER;
	private final double POSITIVE_ACCELERATION_LIMITER;
	// These settings are the same as the settings for the map. They will in
	// fact give you updates
	// at the maximal rates currently possible.
	private static final LocationRequest REQUEST = LocationRequest.create()
			.setInterval(500) // 0.5 seconds
			.setFastestInterval(16) // 16ms = 60fps
			.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
	/***
	 * timer untuk outbond jika kamera keluar posisi => 5detik kembali ke posisi
	 * kamere
	 * **/
	private CountDownTimer countBound = new CountDownTimer(5000, 1000) {

		@Override
		public void onTick(long millisUntilFinished) {
			// TODO Auto-generated method stub
		}

		@Override
		public void onFinish() {
			// TODO Auto-generated method stub
			onBound = false;
			countBoundRun = false;
		}
	};
	private CountDownTimer timer = new CountDownTimer(Long.MAX_VALUE, 1000) {

		@Override
		public void onTick(long millisUntilFinished) {
			// TODO Auto-generated method stub
			listener.timer(Long.MAX_VALUE - millisUntilFinished);
			if (onConnectedGPS)
				listener.onConnected();
		}

		@Override
		public void onFinish() {
			// TODO Auto-generated method stub

		}
	};

	public TrackingHelper(Context context,
			double POSITIVE_ACCELERATION_LIMITER,
			double NEGATIVE_ACCELERATION_LIMITER,
			OnTrackingHelperListener listener) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.listener = listener;
		this.POSITIVE_ACCELERATION_LIMITER = POSITIVE_ACCELERATION_LIMITER;
		this.NEGATIVE_ACCELERATION_LIMITER = NEGATIVE_ACCELERATION_LIMITER;
		locations = new ArrayList<>();
		initializeClient();
		connectClient();
		try {
			timer.cancel();
		} catch (Exception e) {
			// TODO: handle exception
		}
		timer.start();
	}

	private void initializeClient() {
		if (locationClient == null) {
			locationClient = new LocationClient(context, this, // ConnectionCallbacks
					this); // OnConnectionFailedListener
		}
	}

	public void connectClient() {
		locationClient.connect();
	}

	public void onDestroy() {
		// TODO Auto-generated method stub
		if (locationClient != null) {
			locationClient.disconnect();
		}
		countBound.cancel();
		timer.cancel();
	}

	public ArrayList<Location> getLocations() {
		return locations;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public void setBound(boolean arg0) {
		this.onBound = arg0;
	}

	public boolean getBound() {
		return onBound;
	}

	@Override
	public void onLocationChanged(Location arg0) {
		// TODO Auto-generated method stub

		if (onLocationChangeCount > 0 && arg0.getAccuracy() <= 50.100) {
			if (!onBound) {
				Float bearing = null;
				if (locations.size() > 0)
					bearing = getBearing(locations.get(locations.size() - 1),
							arg0);
				listener.goToPositon(arg0, bearing);
				location = arg0;
			} else {
				if (!countBoundRun) {
					countBound.start();
					countBoundRun = true;
				}
			}
			int speed = Utils.Distance.getKMperSec(arg0.getSpeed());
			if (speed > 0) {
				if (locations.size() == 0) {
					locations.add(arg0);
					location = arg0;
				} else {
					double dis = getDistanceInKM(
							locations.get(locations.size() - 1).getLatitude(),
							locations.get(locations.size() - 1).getLongitude(),
							arg0.getLatitude(), arg0.getLongitude());
					if (dis > 0.002D) {
						location = arg0;
						listener.onDistanceChanged(dis);
						listener.onSpeedChanged(speed);
						listener.drawLine(locations.get(locations.size() - 1),
								arg0);
						boolean isEco = getAcceleration(
								locations.get(locations.size() - 1), arg0);
						locations.add(arg0);
						listener.setTextSpeedo(speed);
						listener.setTextDistance();
						listener.addGraphView();
						listener.logData(arg0, speed, dis, arg0.getTime(),
								isEco);
					}
				}
			}
		}
		onLocationChangeCount += 1;
	}

	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onConnected(Bundle arg0) {
		// TODO Auto-generated method stub
		locationClient.requestLocationUpdates(REQUEST, this); // LocationListener
		onConnectedGPS = true;

	}

	@Override
	public void onDisconnected() {
		// TODO Auto-generated method stub
		onConnectedGPS = false;
	}

	private boolean getAcceleration(Location begin, Location end) {
		// TODO Auto-generated method stub
		double deltaV = end.getSpeed() - begin.getSpeed();
		double deltaT = (end.getTime() - begin.getTime()) / 1000;
		if (deltaT == 0.0d)
			deltaT = 0.1d;
		if (deltaV >= 0) {
			// double acc = Double.parseDouble(tripdata.getDataMotor().get(
			// EcoDataAdapter.KEY_ECO_POSITIVE_ACCELERATION));
			if ((deltaV / deltaT) > POSITIVE_ACCELERATION_LIMITER) {
				return true;
			} else {
				return false;
			}
		} else {
			if ((deltaV / deltaT) < NEGATIVE_ACCELERATION_LIMITER) {
				return true;
			} else {
				return false;
			}
		}
	}

	// ///HELPER
	private float getBearing(Location begin, Location end) {
		// TODO Auto-generated method stub
		return getBearing(
				new LatLng(begin.getLatitude(), begin.getLongitude()),
				new LatLng(end.getLatitude(), end.getLongitude()));
	}

	// ///HELPER
	private float getBearing(LatLng begin, LatLng end) {
		double lat = Math.abs(begin.latitude - end.latitude);
		double lng = Math.abs(begin.longitude - end.longitude);
		if (begin.latitude < end.latitude && begin.longitude < end.longitude)
			return (float) (Math.toDegrees(Math.atan(lng / lat)));
		else if (begin.latitude >= end.latitude
				&& begin.longitude < end.longitude)
			return (float) ((90 - Math.toDegrees(Math.atan(lng / lat))) + 90);
		else if (begin.latitude >= end.latitude
				&& begin.longitude >= end.longitude)
			return (float) (Math.toDegrees(Math.atan(lng / lat)) + 180);
		else if (begin.latitude < end.latitude
				&& begin.longitude >= end.longitude)
			return (float) ((90 - Math.toDegrees(Math.atan(lng / lat))) + 270);
		return -1;
	}

	private double getDistanceInKM(double lat1, double lon1, double lat2,
			double lon2) {
		double latA = Math.toRadians(lat1);
		double lonA = Math.toRadians(lon1);
		double latB = Math.toRadians(lat2);
		double lonB = Math.toRadians(lon2);
		double cosAng = (Math.cos(latA) * Math.cos(latB) * Math
				.cos(lonB - lonA)) + (Math.sin(latA) * Math.sin(latB));
		double ang = Math.acos(cosAng);
		double dist = ang * 6371;
		return dist;
	}
}
