/*
 * @author Hallaz ~ hallaz.ibnu@gmail.com
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

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.pertamina.tbbm.rewulu.ecodriving.R;
import com.pertamina.tbbm.rewulu.ecodriving.databases.TripDataAdapter;
import com.pertamina.tbbm.rewulu.ecodriving.dialogs.UserInputDialogFragment;
import com.pertamina.tbbm.rewulu.ecodriving.listener.OnDialogListener;
import com.pertamina.tbbm.rewulu.ecodriving.listener.OnMainListener;
import com.pertamina.tbbm.rewulu.ecodriving.pojos.DataLog;
import com.pertamina.tbbm.rewulu.ecodriving.pojos.Tripdata;
import com.pertamina.tbbm.rewulu.ecodriving.utils.Api;
import com.pertamina.tbbm.rewulu.ecodriving.utils.Constant;
import com.pertamina.tbbm.rewulu.ecodriving.utils.Loggers;
import com.pertamina.tbbm.rewulu.ecodriving.utils.Utils;

public class ResultFragment extends Fragment implements OnClickListener,
		OnDialogListener {
	private static final int USER_SAVE_BACKPRESSED_INPUT_ID = 54540545;
	private static final int USER_ACTION_SAVE_ID = 92785;
	private static final int USER_ACTION_SHARE_ID = 75864;
	private static final int USER_ACTION_DELETE_ID = 81346;

	private Tripdata tripdata;
	private ArrayList<DataLog> dataLogs;
	private double totalDistance = 0d;
	private int avSpeed = 0;
	private double totalTripFuel = 0d;
	private double remainingFuel = 0d;
	private double ecoDistance = 0d;
	private double ecoFuel = 0d;
	private double vice_ecoFuel = 0d;
	private double save_ecoFuel = 0d;
	private double nonEcoDistance = 0d;
	private double nonEcoFuel = 0d;
	private double vice_nonEcoFuel = 0d;
	private double boros_nonEcoFuel = 0d;
	private LinearLayout chartContainer;
	private LinearLayout leftT, centerT, rightT;
	private LinearLayout leftB, centerB, rightB;
	// research
	private ArrayList<Integer> graphDataWaktu;
	private ArrayList<Integer> graphDataSpeed;
	private ArrayList<Double> distanceWaktu;
	private ArrayList<Double> distanceSpeed;
	private double distancePivot = 1d;
	private double timePivot = -1;
	private final String[] graphModel = new String[] {
			"waktu (detik) - jarak (km)", "kecepatan (km/h) - jarak (km)" };
	private OnMainListener callback;
	private UserInputDialogFragment userDialog = new UserInputDialogFragment(
			USER_SAVE_BACKPRESSED_INPUT_ID);

	public void setData(Tripdata tripdata, ArrayList<DataLog> dataLogs,
			OnMainListener callback) {
		Loggers.i("ResultFragment - setData",
				"dataLogs.size() " + dataLogs.size());
		this.tripdata = tripdata;
		this.dataLogs = dataLogs;
		this.graphDataWaktu = new ArrayList<>();
		this.graphDataSpeed = new ArrayList<>();
		this.distanceSpeed = new ArrayList<>();
		this.distanceWaktu = new ArrayList<>();
		this.graphDataWaktu.add(0);
		this.distanceWaktu.add(0d);
		this.callback = callback;
	}

	public void onBackPressed() {
		if (!tripdata.isSaved() && userDialog != null) {
			initDialogSaveBackPressed();
		} else
			callback.goToMainMenu();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.fragment_result_tracking,
				container, false);
		Backstack.onResult();
		initData();
		Loggers.i("fuel start",
				Utils.decimalFormater(dataLogs.get(0).getFuel()));
		Loggers.i("fuel end", Utils.decimalFormater(dataLogs.get(
				dataLogs.size() - 1).getFuel()));

		((ImageButton) rootView.findViewById(R.id.btn_share_result))
				.setOnClickListener(this);
		((ImageButton) rootView.findViewById(R.id.btn_save_result))
				.setOnClickListener(this);
		((ImageButton) rootView.findViewById(R.id.btn_delete_result))
				.setOnClickListener(this);

		((TextView) rootView.findViewById(R.id.text_jarak_tempuh_result))
				.setText(String.format("%.2f", totalDistance) + " km");
		Loggers.i("totalDistance", Utils.decimalFormater(totalDistance));
		((TextView) rootView.findViewById(R.id.text_av_speed_result))
				.setText(avSpeed + " km/h");
		((TextView) rootView.findViewById(R.id.text_konsumsi_bbm_result))
				.setText(String.format("%.2f", totalTripFuel) + " L");
		Loggers.i("totalTripFuel", Utils.decimalFormater(totalTripFuel));
		((TextView) rootView.findViewById(R.id.text_sisa_bbm_result))
				.setText(String.format("%.2f", remainingFuel) + " L");
		Loggers.i("remainingFuel", Utils.decimalFormater(remainingFuel));

		// ==============================================================
		// ==============================================================

		((TextView) rootView.findViewById(R.id.text_jarak_tempuh_eco_result))
				.setText(String.format("%.2f", ecoDistance) + " km");
		Loggers.i("ecoDistance", Utils.decimalFormater(ecoDistance));
		((TextView) rootView.findViewById(R.id.text_konsumsi_bbm_eco_result))
				.setText(String.format("%.2f", ecoFuel) + " L");
		Loggers.i("ecoFuel", Utils.decimalFormater(ecoFuel));
		((TextView) rootView.findViewById(R.id.text_hemat_bbm_result))
				.setText(String.format("%.2f", save_ecoFuel) + " L");
		Loggers.i("save_ecoFuel", Utils.decimalFormater(save_ecoFuel));
		((TextView) rootView.findViewById(R.id.text_hemat_emisi_result))
				.setText(String.format("%.2f",
						(save_ecoFuel * Constant.FAKTOR_EMISI)) + " kgCO2eq");
		Loggers.i("text_hemat_emisi_result",
				Utils.decimalFormater(save_ecoFuel * Constant.FAKTOR_EMISI));
		((TextView) rootView
				.findViewById(R.id.text_konsumsi_bbm_if_non_eco_result))
				.setText(String.format("%.2f", vice_ecoFuel) + " L");
		Loggers.i("vice_ecoFuel", Utils.decimalFormater(vice_ecoFuel));

		// ==============================================================
		// ==============================================================

		Loggers.i("nonEcoDistance", Utils.decimalFormater(nonEcoDistance));
		((TextView) rootView
				.findViewById(R.id.text_jarak_tempuh_non_eco_result))
				.setText(String.format("%.2f", nonEcoDistance) + " km");
		Loggers.i("boros_nonEcoFuel", Utils.decimalFormater(boros_nonEcoFuel));
		((TextView) rootView.findViewById(R.id.text_boros_bbm_result))
				.setText(String.format("%.2f", boros_nonEcoFuel) + " L");
		Loggers.i("text_boros_emisi_result",
				Utils.decimalFormater(Constant.FAKTOR_EMISI * boros_nonEcoFuel));
		((TextView) rootView.findViewById(R.id.text_boros_emisi_result))
				.setText(String.format("%.2f",
						(Constant.FAKTOR_EMISI * boros_nonEcoFuel))
						+ " kgCO2eq");
		Loggers.i("vice_nonEcoFuel", Utils.decimalFormater(vice_nonEcoFuel));
		((TextView) rootView
				.findViewById(R.id.text_konsumsi_bbm_if_eco_non_eco_result))
				.setText(String.format("%.2f", vice_nonEcoFuel) + " L");
		Loggers.i("nonEcoFuel", Utils.decimalFormater(nonEcoFuel));
		((TextView) rootView
				.findViewById(R.id.text_konsumsi_bbm_non_eco_result))
				.setText(String.format("%.2f", nonEcoFuel) + " L");

		// ==============================================================
		// ==============================================================

		((TextView) rootView.findViewById(R.id.text_jarak_tempuh_eco_result))
				.setOnClickListener(this);
		((TextView) rootView
				.findViewById(R.id.text_jarak_tempuh_eco_result_title))
				.setOnClickListener(this);
		((TextView) rootView
				.findViewById(R.id.text_jarak_tempuh_non_eco_result))
				.setOnClickListener(this);
		((TextView) rootView
				.findViewById(R.id.text_jarak_tempuh_non_eco_result_title))
				.setOnClickListener(this);

		chartContainer = (LinearLayout) rootView
				.findViewById(R.id.chart_container);
		leftT = (LinearLayout) rootView.findViewById(R.id.ly_eco_result_title);
		leftB = (LinearLayout) rootView
				.findViewById(R.id.ly_non_eco_result_title);
		centerT = (LinearLayout) rootView.findViewById(R.id.ly_eco_result_sym);
		centerB = (LinearLayout) rootView
				.findViewById(R.id.ly_non_eco_result_sym);
		rightT = (LinearLayout) rootView.findViewById(R.id.ly_eco_result_body);
		rightB = (LinearLayout) rootView
				.findViewById(R.id.ly_non_eco_result_body);

		Spinner spinner = (Spinner) rootView
				.findViewById(R.id.spinner_result_graph);
		ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
				android.R.layout.simple_spinner_dropdown_item, graphModel);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				drawChart(arg2);

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
		drawChart(0);
		return rootView;
	}

	private void initData() {
		// TODO Auto-generated method stub
		double ecoFuelAgeData = tripdata.getMotor().getEco_fuelage();
		double nonEcoFuelAgeData = tripdata.getMotor().getNon_eco_fuelage();
		// ==========
		for (DataLog log : dataLogs) {
			avSpeed += log.getSpeed();
			totalDistance += log.getDistance();
			totalTripFuel += (log.getFuel_age());
			if (log.getDrive_state()) {
				ecoDistance += log.getDistance();
				ecoFuel += (log.getFuel_age());
			} else {
				nonEcoDistance += log.getDistance();
				nonEcoFuel += (log.getFuel_age());
			}

			int sspeed = log.getSpeed();
			if (sspeed != 0) {
				graphDataSpeed.add(sspeed);
				distanceSpeed.add(totalDistance);
			}

			if (timePivot < 0)
				timePivot = log.getTime();
			else {
				time += (log.getTime() - timePivot) / 1000;
				timePivot = log.getTime();
			}
			if (totalDistance > distancePivot) {
				graphDataWaktu.add(time);
				distanceWaktu.add(totalDistance);
				distancePivot = totalDistance + 1;
				time = 0;
			}

		}

		graphDataWaktu.add(time);
		distanceWaktu.add(totalDistance);
		// ecoFuel = Utils.doubleDecimalFormater(ecoFuel);
		// nonEcoFuel = Utils.doubleDecimalFormater(nonEcoFuel);
		Loggers.d("ecoFuelAgeData", ecoFuelAgeData);
		Loggers.d("nonEcoFuelAgeData", nonEcoFuelAgeData);
		remainingFuel = (dataLogs.get(dataLogs.size() - 1).getFuel());
		avSpeed = avSpeed / dataLogs.size();
		vice_ecoFuel = 1 / nonEcoFuelAgeData * ecoDistance;
		save_ecoFuel = vice_ecoFuel - ecoFuel;
		vice_nonEcoFuel = 1 / ecoFuelAgeData * nonEcoDistance;
		boros_nonEcoFuel = nonEcoFuel - vice_nonEcoFuel;
		totalDistance = ecoDistance + nonEcoDistance;
		if (callback != null)
			callback.setDataTrip(ecoFuel, nonEcoFuel, ecoDistance,
					nonEcoDistance);
		Loggers.i(
				"ResultFragment",
				"ecoFuel "
						+ ecoFuel
						+ " nonEcoFuel "
						+ nonEcoFuel
						+ " ecoDistance "
						+ ecoDistance
						+ " nonEcoDistance "
						+ nonEcoDistance
						+ " time "
						+ Utils.getDurationBreakdown(dataLogs.get(
								dataLogs.size() - 1).getTime()
								- dataLogs.get(0).getTime()));
	}

	private int time = 0;

	private void drawChart(int arg0) {
		// TODO Auto-generated method stub
		GraphicalView chartView;
		XYSeries xySeries = new XYSeries("");
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
		XYSeriesRenderer xySeriesRenderer = new XYSeriesRenderer();

		xySeriesRenderer.setLineWidth(5);
		xySeriesRenderer.setColor(Color.BLUE);
		// Include low and max value
		xySeriesRenderer.setDisplayBoundingPoints(true);
		// we add point markers
		xySeriesRenderer.setPointStyle(PointStyle.POINT);
		xySeriesRenderer.setPointStrokeWidth(7);
		xySeriesRenderer.setChartValuesTextSize(18f);

		String[] tt;
		switch (arg0) {
		case 0:
			tt = graphModel[0].split("-");
			renderer.setYTitle(tt[0].trim()); // waktu
			renderer.setXTitle(tt[1]); // jarak
			for (int w = 0; w < graphDataWaktu.size(); w++) {
				xySeries.add(w, graphDataWaktu.get(w));
			}
			dataset.addSeries(xySeries);
			renderer.addSeriesRenderer(xySeriesRenderer);
			xySeriesRenderer.setDisplayChartValues(true);
			renderer.setYAxisMin(0);
			renderer.setYAxisMax(200);
			renderer.setYLabels(5);
			break;
		case 1:
			tt = graphModel[1].split("-");
			renderer.setYTitle(tt[0].trim()); // speed
			renderer.setXTitle(tt[1]); // jarak
			for (int w = 0; w < distanceSpeed.size(); w++) {
				xySeries.add(distanceSpeed.get(w), graphDataSpeed.get(w));
			}
			dataset.addSeries(xySeries);
			dataset.addSeries(getMaxEcoSpeedSeries());
			xySeriesRenderer.setDisplayChartValues(false);
			renderer.addSeriesRenderer(xySeriesRenderer);
			renderer.addSeriesRenderer(getMaxEcoSpeedSeriesRendere());
			break;
		default:
			break;
		}
		renderer.setPanEnabled(true, false);
		renderer.setMarginsColor(Color.argb(0x00, 0xff, 0x00, 0x00)); // transparent

		renderer.setXAxisMin(0);
		renderer.setXAxisMax(5);
		renderer.setXLabels(6);
		renderer.setXRoundedLabels(true);

		renderer.setShowGrid(true);
		renderer.setGridColor(Color.WHITE);
		renderer.setInScroll(true);
		renderer.setAxesColor(Color.BLACK);
		renderer.setLabelsColor(Color.BLACK);
		renderer.setXLabelsColor(Color.BLACK);
		renderer.setYLabelsColor(0, Color.BLACK);

		renderer.setFitLegend(true);
		renderer.setLabelsTextSize(18);
		renderer.setAxisTitleTextSize(24);
		renderer.setLegendTextSize(24);
		renderer.setMargins(new int[] { 20, 48, 16, 16 });
		renderer.setYLabelsAlign(Align.LEFT);
		chartView = ChartFactory.getLineChartView(getActivity(), dataset,
				renderer);
		try {
			chartContainer.removeViewAt(0);
		} catch (Exception e) {
			// TODO: handle exception
		}
		chartContainer.addView(chartView, 0);
	}

	private XYSeries getMaxEcoSpeedSeries() {
		// TODO Auto-generated method stub
		XYSeries maxEcoXySeries = new XYSeries("Max Eco Driving Speed");
		int max = tripdata.getMotor().getMax_speed_eco();
		for (double w = -Constant.SPEED_TOLERANCE; w < graphDataSpeed.size()
				+ Constant.SPEED_TOLERANCE; w += 0.2d)
			maxEcoXySeries.add(w, max);
		return maxEcoXySeries;
	}

	private XYSeriesRenderer getMaxEcoSpeedSeriesRendere() {
		// TODO Auto-generated method stub
		XYSeriesRenderer maxXySeriesRenderer = new XYSeriesRenderer();
		maxXySeriesRenderer.setLineWidth(8);
		maxXySeriesRenderer.setColor(Color.RED);
		// Include low and max value
		maxXySeriesRenderer.setDisplayBoundingPoints(true);
		// we add point markers
		maxXySeriesRenderer.setPointStyle(PointStyle.POINT);
		maxXySeriesRenderer.setPointStrokeWidth(7);
		return maxXySeriesRenderer;
	}

	private void setVisibilityLayout(int arg0) {
		// TODO Auto-generated method stub
		leftB.setVisibility(arg0);
		leftT.setVisibility(arg0);
		centerB.setVisibility(arg0);
		centerT.setVisibility(arg0);
		rightB.setVisibility(arg0);
		rightT.setVisibility(arg0);
	}

	private void initDialogShare() {
		// TODO Auto-generated method stub
		userDialog = new UserInputDialogFragment(USER_ACTION_SHARE_ID,
				"Bagikan Perjalanan", "Bagikan dengan judul", true, this);
		if (tripdata.isNamed())
			userDialog.setTextField(tripdata.getTitle());
		userDialog.setInputType(InputType.TYPE_TEXT_VARIATION_NORMAL);
		userDialog.setCustomTextButton("Batal", "Bagikan");
		userDialog.show(getFragmentManager(), null);
	}

	private void initDialogSave() {
		// TODO Auto-generated method stub
		userDialog = new UserInputDialogFragment(USER_ACTION_SAVE_ID,
				"Simpan Perjalanan", "Masukkan judul", true, this);
		userDialog.setInputType(InputType.TYPE_TEXT_VARIATION_NORMAL);
		if (tripdata.isNamed())
			userDialog.setTextField(tripdata.getTitle());
		userDialog.show(getFragmentManager(), null);
	}

	private void initDialogSaveBackPressed() {
		// TODO Auto-generated method stub
		userDialog = new UserInputDialogFragment(
				USER_SAVE_BACKPRESSED_INPUT_ID, "Simpan Perjalanan",
				"Masukkan judul", true, this);
		userDialog.setInputType(InputType.TYPE_TEXT_VARIATION_NORMAL);
		userDialog.setCustomTextButton("Hapus", "Simpan");
		userDialog.show(getFragmentManager(), null);
	}

	private void initDialogDelete() {
		// TODO Auto-generated method stub
		userDialog = new UserInputDialogFragment(USER_ACTION_DELETE_ID,
				"Hapus Perjalanan ?", false, this);
		userDialog.show(getFragmentManager(), null);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.text_jarak_tempuh_eco_result:
		case R.id.text_jarak_tempuh_eco_result_title:
		case R.id.text_jarak_tempuh_non_eco_result:
		case R.id.text_jarak_tempuh_non_eco_result_title:
			if (leftT.getVisibility() == View.VISIBLE)
				setVisibilityLayout(View.GONE);
			else
				setVisibilityLayout(View.VISIBLE);
			break;
		case R.id.btn_share_result:
			initDialogShare();
			break;
		case R.id.btn_save_result:
			initDialogSave();
			break;
		case R.id.btn_delete_result:
			initDialogDelete();
			break;
		default:
			break;
		}
	}

	@Override
	public void onSubmitDialog(int id, boolean action, String arg0) {
		// TODO Auto-generated method stub
		switch (id) {
		case USER_SAVE_BACKPRESSED_INPUT_ID:
			if (action) {
				if (arg0 == null) {
					Utils.toast(getActivity(), "Kolom masukan harus diisi !");
					return;
				}
				if (!arg0.isEmpty()) {
					Utils.toast(getActivity(), "Kolom masukan harus diisi !");
					return;
				}
				tripdata.setTitle(arg0);
				tripdata.setSaved(true);
			} else {
				tripdata.setSaved(false);
			}
			TripDataAdapter.updateTrip(getActivity(), tripdata);
			callback.goToMainMenu();
			break;

		case USER_ACTION_SAVE_ID:
			if (action) {
				if (arg0 == null) {
					Utils.toast(getActivity(), "Kolom masukan harus diisi !");
					return;
				}
				if (!arg0.isEmpty()) {
					Utils.toast(getActivity(), "Kolom masukan harus diisi !");
					return;
				}
				tripdata.setTitle(arg0);
				tripdata.setSaved(true);
				TripDataAdapter.updateTrip(getActivity(), tripdata);
			}
			break;
		case USER_ACTION_SHARE_ID:
			if (action) {
				if (arg0 == null) {
					Utils.toast(getActivity(), "Kolom masukan harus diisi !");
					return;
				}
				if (!arg0.isEmpty()) {
					Utils.toast(getActivity(), "Kolom masukan harus diisi !");
					return;
				}
				tripdata.setTitle(arg0);
				tripdata.setSaved(true);
				TripDataAdapter.updateTrip(getActivity(), tripdata);
				shareTrip();
			}
			break;
		case USER_ACTION_DELETE_ID:
			if (action) {
				tripdata.setSaved(false);
				TripDataAdapter.updateTrip(getActivity(), tripdata);
				callback.goToMainMenu();
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

	private void shareTrip() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(android.content.Intent.ACTION_SEND);
		intent.setType("text/plain");
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

		// Add data to the intent, the receiving app will decide what to do with
		// it.
		intent.putExtra(Intent.EXTRA_SUBJECT, "Bagikan Perjalanan");
		intent.putExtra(Intent.EXTRA_TEXT, Api.shareFormatter(tripdata));
		startActivity(Intent.createChooser(intent, "Bagikan via "));
	}
}
