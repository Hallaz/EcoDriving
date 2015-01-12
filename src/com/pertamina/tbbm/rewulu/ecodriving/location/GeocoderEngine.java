package com.pertamina.tbbm.rewulu.ecodriving.location;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import com.pertamina.tbbm.rewulu.ecodriving.utils.Loggers;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

public class GeocoderEngine {
	private Context context;
	public GeocoderEngine(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
	}
	public String getAddress(double latitude, double longitude) {
		Geocoder geocoder = new Geocoder(context, Locale.getDefault());
		List<Address> addresses = null;
		try {
			addresses = geocoder.getFromLocation(latitude, longitude, 1);
		} catch (IOException e1) {
			Loggers.e("LocationSampleActivity",
					"IO Exception in getFromLocation()");
			e1.printStackTrace();
			return null;
		} catch (IllegalArgumentException e2) {
			String errorString = "Illegal arguments "
					+ Double.toString(latitude) + " , "
					+ Double.toString(longitude)
					+ " passed to address service";
			Loggers.e("LocationSampleActivity", errorString);
			e2.printStackTrace();
			return null;
		}
		if (addresses != null && addresses.size() > 0) {
			Address address = addresses.get(0);
			String addressText = String.format(
					"%s, %s, %s",
					address.getMaxAddressLineIndex() > 0 ? address
							.getAddressLine(0) : "", address.getLocality(),
					address.getCountryName());
			return addressText;
		} else {
			return "No address found";
		}
	}
}
