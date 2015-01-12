/*
 * @author Hallaz ~ hallaz.ibnu@gmail.com
 * 
 * 
 * Please do not modify without any agreement between end user and the author.
 * 
 * Copyright (C) 2009 - 2013 AChartEngine - The 4ViewSoft Company
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.pertamina.tbbm.rewulu.ecodriving.fragment;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.CancelableCallback;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;
import com.pertamina.tbbm.rewulu.ecodriving.R;
import com.pertamina.tbbm.rewulu.ecodriving.dialogs.UserInputDialogFragment;
import com.pertamina.tbbm.rewulu.ecodriving.helpers.GraphHelper;
import com.pertamina.tbbm.rewulu.ecodriving.helpers.TrackingHelper;
import com.pertamina.tbbm.rewulu.ecodriving.listener.OnDialogListener;
import com.pertamina.tbbm.rewulu.ecodriving.listener.OnMainListener;
import com.pertamina.tbbm.rewulu.ecodriving.listener.OnTrackingHelperListener;
import com.pertamina.tbbm.rewulu.ecodriving.pojos.DataLog;
import com.pertamina.tbbm.rewulu.ecodriving.pojos.Tripdata;
import com.pertamina.tbbm.rewulu.ecodriving.utils.Constant;
import com.pertamina.tbbm.rewulu.ecodriving.utils.Setting;
import com.pertamina.tbbm.rewulu.ecodriving.utils.Utils;

@SuppressLint("DefaultLocale")
public class MainTrackingFragment extends Fragment implements OnDialogListener,
		OnCameraChangeListener, OnClickListener, OnTrackingHelperListener {
	/***
	 * callback to main
	 * **/
	private OnMainListener callback;
	/***
	 * dialog u/ refill n& track
	 * **/
	private UserInputDialogFragment dialog;
	/***
	 * trip data tracking
	 * **/
	private Tripdata tripdata;
	private GoogleMap googleMap;

	private TextView textEcoStats;
	private TextView textSpeedo;
	private TextView textFuel;
	private TextView textAvSpeed;
	private TextView textDistance;
	private TextView textTimer;
	private ImageView eco_stats;
	private ImageView fuel_stats;

	private double distance;
	private ArrayList<Integer> avSpeed;
	private int graphTime;
	private double warnFuelPivot;
	private double graphPivotDistance = 1d;
	private double fuel;
	private float zoom = 15;

	private ArrayList<Integer> graphData;
	private GraphHelper graph;
	private boolean onRedFuel;
	private boolean onRedEco;
	private boolean onEco;

	private TrackingHelper tracking;

	private CameraPosition cmrPosition;

	private PolylineOptions options = new PolylineOptions();
	private final int NON_ECO_COLOR = Color.argb(
			Math.round(Color.alpha(Color.RED) * 0.5f), Color.red(Color.RED),
			Color.green(Color.RED), Color.blue(Color.RED));
	private final int ECO_COLOR = Color.argb(
			Math.round(Color.alpha(Color.GREEN) * 0.5f),
			Color.red(Color.GREEN), Color.green(Color.GREEN),
			Color.blue(Color.GREEN));
	private int lineColor = ECO_COLOR;
	private long timer;

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		try {
			callback = (OnMainListener) activity;
		} catch (ClassCastException e) {
			// TODO: handle exception
			throw new ClassCastException(activity.toString()
					+ " must implement MainMenuCallback");
		}
	}

	public void onBackPressed() {
		tracking.onDestroy();
	}

	public void setData(Tripdata tripdata) {
		this.tripdata = tripdata;
		warnFuelPivot = tripdata.getMotor().getMax_fuel() * 20 / 100;
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		tracking.onDestroy();
	}

	public void destoyFragment() {
		// TODO Auto-generated method stub
		tracking.onDestroy();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Backstack.onMaintracking();
		View rootView = inflater.inflate(R.layout.fragment_main_tracking,
				container, false);
		graph = new GraphHelper(getActivity(),
				(LinearLayout) rootView.findViewById(R.id.chart));
		distance = 0;
		avSpeed = new ArrayList<>();
		graphData = new ArrayList<>();
		textSpeedo = (TextView) rootView.findViewById(R.id.textView_speedo);
		textFuel = (TextView) rootView
				.findViewById(R.id.textView_fuel_tracking);
		textDistance = (TextView) rootView
				.findViewById(R.id.textView_distance_tracking);
		textAvSpeed = (TextView) rootView
				.findViewById(R.id.textView_average_speed_tracking);
		textTimer = (TextView) rootView.findViewById(R.id.textView_timer);
		((ImageButton) rootView.findViewById(R.id.btn_refill))
				.setOnClickListener(this);
		((ImageButton) rootView.findViewById(R.id.btn_finish_tracking))
				.setOnClickListener(this);
		eco_stats = (ImageView) rootView.findViewById(R.id.ic_stats);
		fuel_stats = (ImageView) rootView.findViewById(R.id.ic_fuel);
		graphData.add(0);
		fuel = tripdata.getFuel();
		setTextFuel();
		setTextAvSpeed(0);
		setTextDistance();
		setTextSpeedo(0);
		((LinearLayout) rootView
				.findViewById(R.id.linearLayout_finish_tracking)).setAlpha(100);
		tracking = new TrackingHelper(getActivity(), tripdata.getMotor()
				.getPsstv_acc(), tripdata.getMotor().getNgtv_acc(), this);
		if (savedInstanceState == null || tracking.getLocation() == null) {
			tracking.setLocation(Utils.LocationSP
					.getLastKnownLocation(getActivity()));
		}
		onEco = true;
		initializeMap();
		rootView.setKeepScreenOn(true);
		Setting.SettingSP.loadSetting(getActivity());
		Utils.Indicator.prepare(getActivity());
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
					googleMap.setOnCameraChangeListener(this);
					googleMap.getUiSettings().setCompassEnabled(true);
					// googleMap.setPadding(left, top, right, bottom)
					if (tracking.getLocation() != null)
						googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
								new LatLng(
										tracking.getLocation().getLatitude(),
										tracking.getLocation().getLongitude()),
								10));
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	// ///HELPER
	private int getAvgSpeed() {
		// TODO Auto-generated method stub
		int val = 0;
		for (Integer vl : avSpeed)
			val += vl;
		return val / avSpeed.size();
	}

	private String gettFuelInLiter() {
		// TODO Auto-generated method stub
		if (fuel == 0.0d)
			return "0.0";
		return String.format("%.2f", (fuel / 1000d));
	}

	private void checkStats() {
		// TODO Auto-generated method stub
		if (fuel < warnFuelPivot) {
			if (onRedFuel) {
				onRedFuel = false;
				fuel_stats.setImageResource(R.drawable.ic_fuel);
			} else {
				onRedFuel = true;
				fuel_stats.setImageResource(R.drawable.ic_fuel_warn);
			}
		} else if (onRedFuel)
			fuel_stats.setImageResource(R.drawable.ic_fuel);

		if (!onEco) {
			if (onRedEco) {
				eco_stats.setImageResource(R.drawable.ic_stats);
				onRedEco = false;
			} else {
				eco_stats.setImageResource(R.drawable.ic_stats_non);
				onRedEco = true;
			}
		} else if (onRedEco)
			eco_stats.setImageResource(R.drawable.ic_stats);

	}

	private class AverageSpeedMeter extends AsyncTask<String, Integer, Integer> {

		@Override
		protected Integer doInBackground(String... params) {
			// TODO Auto-generated method stub
			return getAvgSpeed();
		}

		@Override
		protected void onPostExecute(Integer result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			textAvSpeed.setText(result + " km/h");
			setTextAvSpeed(result);
		}
	}

	private void goToPositon(LatLng latln, float bearing) {
		// TODO Auto-generated method stub
		cmrPosition = new CameraPosition.Builder().target(latln).zoom(zoom)
				.bearing(bearing).tilt(60).build();
		changeCamera(CameraUpdateFactory.newCameraPosition(cmrPosition));

	}

	private void changeCamera(CameraUpdate update) {
		changeCamera(update, null);
	}

	private void changeCamera(CameraUpdate update, CancelableCallback callback) {
		if (googleMap != null)
			googleMap.animateCamera(update, callback);

	}

	@Override
	public void onCameraChange(CameraPosition arg0) {
		// TODO Auto-generated method stub
		if (tracking.getLocations().size() > 1) {
			if (cmrPosition != null && cmrPosition != arg0)
				tracking.setBound(true);
			else
				tracking.setBound(false);
			zoom = arg0.zoom;
		}
	}

	private void setTextFuel() {
		// TODO Auto-generated method stub
		textFuel.setText(gettFuelInLiter() + " L");
		if (fuel < 0.5d)
			textFuel.setTextColor(Color.RED);
		else
			textFuel.setTextColor(Color.BLACK);
	}

	public void setTextSpeedo(int speed) {
		this.textSpeedo.setText(speed + "");
	}

	public void setTextDistance() {
		this.textDistance.setText(String.format("%.2f", distance) + "Km");
	}

	public void setTextAvSpeed(int speed) {
		textAvSpeed.setText(speed + " km/h");
	}

	@Override
	public void onSubmitDialog(int id, boolean action, String arg0) {
		// TODO Auto-generated method stub
		switch (id) {
		case Constant.DIALOG_FINISH_TRACK:
			if (action) {
				callback.startResult(graphData);
			}
			break;
		case Constant.DIALOG_FUEL_REFILL:
			if (action) {
				if (arg0 == null) {
					Utils.toast(getActivity(), "Kolom masukan harus diisi !");
					return;
				}
				if (arg0.isEmpty()) {
					Utils.toast(getActivity(), "Kolom masukan harus diisi !!");
					return;
				}
				double dFuel = 0;
				try {
					dFuel = (Double.parseDouble(arg0) * 1000) + fuel;
				} catch (Exception e) {
					// TODO: handle exception
					Utils.toast(getActivity(), "Masukkan harus angka !");
					return;
				}
				if (dFuel < 0.5d
						|| dFuel > (tripdata.getMotor().getMax_fuel() * 1000))
					Utils.toast(getActivity(), R.string.err_data_fuel_false);
				else {
					dialog = new UserInputDialogFragment(
							Constant.DIALOG_FUEL_REFILL, getActivity()
									.getString(R.string.dialog_fuel_refill),
							true, this);
					fuel = +dFuel;
					setTextFuel();
					Utils.toast(getActivity(), R.string.fuel_added);
				}
			}
			break;
		default:
			break;
		}
	}

	@Override
	public void onSubmitSingleDialog(int id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_finish_tracking:
			if (tracking.getLocations().isEmpty()
					|| tracking.getLocations().size() == 1)
				Utils.toast(getActivity(), R.string.err_no_location);
			else if (distance < 1.0d) {
				dialog = new UserInputDialogFragment(Constant.DIALOG_ERR,
						getActivity().getString(R.string.err_too_short_trip),
						false, this);
				dialog.singleButtonMode(true);
				dialog.show(getFragmentManager(), null);
			} else {
				dialog = new UserInputDialogFragment(
						Constant.DIALOG_FINISH_TRACK, getActivity().getString(
								R.string.dialog_text_finish_track), false, this);
				dialog.show(getFragmentManager(), null);
			}
			break;
		case R.id.btn_refill:
			dialog = new UserInputDialogFragment(Constant.DIALOG_FUEL_REFILL,
					getActivity().getString(R.string.dialog_fuel_refill), true,
					this);
			dialog.show(getFragmentManager(), null);
			break;
		default:
			break;
		}

	}

	@Override
	public void onConnected() {
		// TODO Auto-generated method stub
		graphTime += 1;
		checkStats();
		timer += 1L;
		if (onEco) {
			if (timer % 3 == 0)
				Utils.Indicator.led(getActivity(), Color.GREEN);
		} else {
			Utils.Indicator.led(getActivity(), Color.RED);
			if (timer % 2 == 0)
				Utils.Indicator.vib(getActivity());
		}
	}

	@Override
	public void goToPositon(Location arg0, Float bearing) {
		// TODO Auto-generated method stub
		if (bearing == null)
			bearing = googleMap.getCameraPosition().bearing;
		goToPositon(new LatLng(arg0.getLatitude(), arg0.getLongitude()),
				bearing);

	}

	@Override
	public void onDistanceChanged(double distance) {
		// TODO Auto-generated method stub
		this.distance += distance;
	}

	@Override
	public void onSpeedChanged(int speed) {
		// TODO Auto-generated method stub
		avSpeed.add(speed);
	}

	@Override
	public void drawLine(Location loc1, Location loc2) {
		// TODO Auto-generated method stub
		options.color(lineColor);
		options.width(14);
		options.visible(true);
		options.add(new LatLng(loc1.getLatitude(), loc1.getLongitude())).add(
				new LatLng(loc2.getLatitude(), loc2.getLongitude()));
		googleMap.addPolyline(options);
	}

	@Override
	public void addGraphView() {
		// TODO Auto-generated method stub
		if (distance >= graphPivotDistance) {
			graph.addSeriesChart(graphData.size(), graphTime);
			graphPivotDistance = distance + 1;
			graphData.add(graphTime);
			graphTime = 0;
		}
	}

	@Override
	public void logData(Location location, int speed, double distance,
			long time, boolean acceleration) {
		// TODO Auto-generated method stub
		DataLog logger = new DataLog();
		logger.setDistance(distance);
		logger.setSpeed(speed);
		logger.setTime(time);
		if (acceleration
				|| (speed > (Constant.SPEED_TOLERANCE + tripdata.getMotor()
						.getMax_speed_eco()))) {
			logger.setDrive_state(false);
			textEcoStats.setText("Non Eco");
			textEcoStats.setTextColor(Color.RED);
			double f = 1 / tripdata.getMotor().getNon_eco_fuelage() * distance;
			fuel -= f;
			logger.setFuel_age(f);
			if (onEco) {
				Utils.Indicator.playSound(getActivity());
				Utils.Indicator.longVib(getActivity());
			}
			onEco = false;
			lineColor = NON_ECO_COLOR;

		} else {
			logger.setDrive_state(true);
			textEcoStats.setText("Eco");
			textEcoStats.setTextColor(Color.BLACK);
			double f = 1 / tripdata.getMotor().getEco_fuelage() * distance;
			fuel -= f;
			logger.setFuel_age(f);
			lineColor = ECO_COLOR;
			onEco = true;
		}
		if (fuel < 0.0d)
			fuel = 0d;
		logger.setFuel(fuel);
		logger.setLocation(location);
		setTextFuel();
		callback.storeLog(logger);
		new AverageSpeedMeter().execute();
	}

	@Override
	public void onDisconnected() {
		// TODO Auto-generated method stub
		timer = 0L;
	}

	@Override
	public void timer(long milis) {
		// TODO Auto-generated method stub
		textTimer.setText(Utils.getDurationBreakdown(milis));
	}

}
