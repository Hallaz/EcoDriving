package com.pertamina.tbbm.rewulu.ecodriving.locations;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.CountDownTimer;

import com.pertamina.tbbm.rewulu.ecodriving.R;
import com.pertamina.tbbm.rewulu.ecodriving.utils.Utils;

public class LocationEngine implements LocationListener {
	private LocationManager locationManager;
	private static final int TWO_MINUTES = 1000 * 60 * 2;
	private Context context;
	private Location currLocation;
	private Location bestLocation;
	private boolean onResume;

	public LocationEngine(Context context) {
		// TODO Auto-generated constructor stub
		locationManager = ((LocationManager) context
				.getSystemService(Context.LOCATION_SERVICE));
		this.context = context;
		onResume();
	}
	
	private boolean isSameProvider(String provider1, String provider2) {
		if (provider1 == null) {
			return provider2 == null;
		}
		return provider1.equals(provider2);
	}

	protected boolean isBetterLocation(Location location,
			Location currentBestLocation) {
		if (currentBestLocation == null) {
			return true;
		}

		// Check whether the new location fix is newer or older
		long timeDelta = location.getTime() - currentBestLocation.getTime();
		boolean isSignificantlyNewer = timeDelta > TWO_MINUTES;
		boolean isSignificantlyOlder = timeDelta < -TWO_MINUTES;
		boolean isNewer = timeDelta > 0;

		// If it's been more than two minutes since the current location, use
		// the new location
		// because the user has likely moved
		if (isSignificantlyNewer) {
			return true;
			// If the new location is more than two minutes older, it must be
			// worse
		} else if (isSignificantlyOlder) {
			return false;
		}

		// Check whether the new location fix is more or less accurate
		int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation
				.getAccuracy());
		boolean isLessAccurate = accuracyDelta > 0;
		boolean isMoreAccurate = accuracyDelta < 0;
		boolean isSignificantlyLessAccurate = accuracyDelta > 200;

		// Check if the old and new location are from the same provider
		boolean isFromSameProvider = isSameProvider(location.getProvider(),
				currentBestLocation.getProvider());

		// Determine location quality using a combination of timeliness and
		// accuracy
		if (isMoreAccurate) {
			return true;
		} else if (isNewer && !isLessAccurate) {
			return true;
		} else if (isNewer && !isSignificantlyLessAccurate
				&& isFromSameProvider) {
			return true;
		}
		return false;
	}

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		try {
			currLocation = location;
			onResume = true;
		} catch (Exception e) {
			// TODO: handle exception
			onResume = false;
			onDestroy();
		}
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		Utils.toast(context, R.string.err_gps_disbled);
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

	public void settLastLocation() {
		new CountDownTimer(10000, 1000) {

			@Override
			public void onTick(long millisUntilFinished) {
				// TODO Auto-generated method stub
				if (onResume && isBetterLocation(currLocation, bestLocation))
					bestLocation = currLocation;
			}

			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				if (onResume)
					Utils.LocationSP.saveKnownLocation(context, bestLocation);
				onDestroy();
			}
		}.start();
	}

	public void onDestroy() {
		// TODO Auto-generated method stub
		locationManager.removeUpdates(this);
	}

	public void onResume() {
		locationManager.requestLocationUpdates(
				LocationManager.NETWORK_PROVIDER, 3000, 0, this);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
				3000, 0, this);
	}
}
