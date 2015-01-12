package com.pertamina.tbbm.rewulu.ecodriving.fragment;

import java.util.ArrayList;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.GoogleMap.CancelableCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;
import com.pertamina.tbbm.rewulu.ecodriving.R;
import com.pertamina.tbbm.rewulu.ecodriving.listener.OnMainListener;
import com.pertamina.tbbm.rewulu.ecodriving.pojos.DataLog;
import com.pertamina.tbbm.rewulu.ecodriving.pojos.Tripdata;
import com.pertamina.tbbm.rewulu.ecodriving.utils.Utils;

public class DrawerFragment extends Fragment {
	private GoogleMap googleMap;
	private PolylineOptions options = new PolylineOptions();
	private ArrayList<DataLog> logs;

	public void setData(Tripdata tripdata, ArrayList<DataLog> dataLogs,
			OnMainListener callback) {
		logs = dataLogs;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.fragment_drawer, container,
				false);
		initializeMap();
		draw();
		return rootView;
	}

	private void initializeMap() {
		// TODO Auto-generated method stub
		try {
			if (googleMap == null) {
				googleMap = ((MapFragment) getActivity().getFragmentManager()
						.findFragmentById(R.id.map)).getMap();
				if (googleMap == null) {
					Utils.toast(getActivity(), R.string.err_map_not_avail);
				} else {
					googleMap.setMyLocationEnabled(true);
					googleMap.getUiSettings().setZoomControlsEnabled(true);
					googleMap.getUiSettings().setCompassEnabled(true);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private void changeCamera(CameraUpdate update, CancelableCallback callback) {
		if (googleMap != null)
			googleMap.animateCamera(update, callback);

	}

	private void goToPositon(LatLng latln) {
		// TODO Auto-generated method stub
		CameraPosition cmrPosition = new CameraPosition.Builder().target(latln)
				.zoom(15).tilt(60).build();
		changeCamera(CameraUpdateFactory.newCameraPosition(cmrPosition), null);

	}

	private void draw() {
		// TODO Auto-generated method stub
		Log.i("", "jancokkk " + logs.size());
		for (int w = 1; w < logs.size(); w++)
			drawLine(logs.get(w - 1).getLatLng(), logs.get(w).getLatLng());
	}

	public void drawLine(LatLng loc1, LatLng loc2) {
		// TODO Auto-generated method stub
		options.color(Color.RED);
		options.width(14);
		options.visible(true);
		options.add(loc1).add(loc2);
		googleMap.addPolyline(options);
		goToPositon(loc2);
	}
}
