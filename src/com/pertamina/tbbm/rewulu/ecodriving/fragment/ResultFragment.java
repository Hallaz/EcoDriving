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
import com.pertamina.tbbm.rewulu.ecodriving.helpers.ResultData;
import com.pertamina.tbbm.rewulu.ecodriving.listener.OnDialogListener;
import com.pertamina.tbbm.rewulu.ecodriving.listener.OnMainListener;
import com.pertamina.tbbm.rewulu.ecodriving.utils.Api;
import com.pertamina.tbbm.rewulu.ecodriving.utils.Api.Builder;
import com.pertamina.tbbm.rewulu.ecodriving.utils.Constant;
import com.pertamina.tbbm.rewulu.ecodriving.utils.Loggers;
import com.pertamina.tbbm.rewulu.ecodriving.utils.Utils;

public class ResultFragment extends Fragment implements OnClickListener,
		OnDialogListener {
	private static final int USER_SAVE_BACKPRESSED_INPUT_ID = 54540545;
	private static final int USER_ACTION_SAVE_ID = 92785;
	private static final int USER_ACTION_SHARE_ID = 75864;
	private static final int USER_ACTION_DELETE_ID = 81346;

	private ResultData resultData;

	private LinearLayout chartContainer;
	private LinearLayout leftT, centerT, rightT;
	private LinearLayout leftB, centerB, rightB;

	private boolean fromTrip;

	// research
	private final String[] graphModel = new String[] {
			"waktu (detik) - jarak (km)", "kecepatan (km/h) - jarak (km)" };
	private OnMainListener callback;
	private UserInputDialogFragment userDialog = new UserInputDialogFragment(
			USER_SAVE_BACKPRESSED_INPUT_ID);

	public void setData(ResultData resultData, OnMainListener callback) {
		this.resultData = resultData;
		this.callback = callback;
		fromTrip = Backstack.isOnMaintracking();
	}

	public void onBackPressed() {
		if (!resultData.getTripdata().isSaved() && userDialog != null)
			initDialogSaveBackPressed();
		else if (fromTrip)
			callback.goToMainMenu();
		else
			callback.startHistory();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.fragment_result_tracking,
				container, false);
		Backstack.onResult();
		Loggers.i("fuel start", Utils.decimalFormater(resultData.getDataLogs()
				.get(0).getFuel()));
		Loggers.i(
				"fuel end",
				Utils.decimalFormater(resultData.getDataLogs()
						.get(resultData.getDataLogs().size() - 1).getFuel()));

		((ImageButton) rootView.findViewById(R.id.btn_share_result))
				.setOnClickListener(this);
		((ImageButton) rootView.findViewById(R.id.btn_save_result))
				.setOnClickListener(this);
		((ImageButton) rootView.findViewById(R.id.btn_delete_result))
				.setOnClickListener(this);

		((TextView) rootView.findViewById(R.id.text_jarak_tempuh_result))
				.setText(String.format("%.2f", resultData.getTotalDistance())
						+ " km");
		Loggers.i("totalDistance",
				Utils.decimalFormater(resultData.getTotalDistance()));
		((TextView) rootView.findViewById(R.id.text_av_speed_result))
				.setText(resultData.getAvSpeed() + " km/h");
		((TextView) rootView.findViewById(R.id.text_konsumsi_bbm_result))
				.setText(String.format("%.2f", resultData.getTotalTripFuel())
						+ " L");
		Loggers.i("totalTripFuel",
				Utils.decimalFormater(resultData.getTotalTripFuel()));
		((TextView) rootView.findViewById(R.id.text_sisa_bbm_result))
				.setText(String.format("%.2f", resultData.getRemainingFuel())
						+ " L");
		Loggers.i("remainingFuel",
				Utils.decimalFormater(resultData.getRemainingFuel()));

		// ==============================================================
		// ==============================================================

		((TextView) rootView.findViewById(R.id.text_jarak_tempuh_eco_result))
				.setText(String.format("%.2f", resultData.getEcoDistance())
						+ " km");
		Loggers.i("ecoDistance",
				Utils.decimalFormater(resultData.getEcoDistance()));
		((TextView) rootView.findViewById(R.id.text_konsumsi_bbm_eco_result))
				.setText(String.format("%.2f", resultData.getEcoFuel()) + " L");
		Loggers.i("ecoFuel", Utils.decimalFormater(resultData.getEcoFuel()));
		((TextView) rootView.findViewById(R.id.text_hemat_bbm_result))
				.setText(String.format("%.2f", resultData.getSave_ecoFuel())
						+ " L");
		Loggers.i("save_ecoFuel",
				Utils.decimalFormater(resultData.getSave_ecoFuel()));
		((TextView) rootView.findViewById(R.id.text_hemat_emisi_result))
				.setText(String.format("%.2f",
						(resultData.getSave_ecoFuel() * Constant.FAKTOR_EMISI))
						+ " kgCO2eq");
		Loggers.i(
				"text_hemat_emisi_result",
				Utils.decimalFormater(resultData.getSave_ecoFuel()
						* Constant.FAKTOR_EMISI));
		((TextView) rootView
				.findViewById(R.id.text_konsumsi_bbm_if_non_eco_result))
				.setText(String.format("%.2f", resultData.getVice_ecoFuel())
						+ " L");
		Loggers.i("vice_ecoFuel",
				Utils.decimalFormater(resultData.getVice_ecoFuel()));

		// ==============================================================
		// ==============================================================

		Loggers.i("nonEcoDistance",
				Utils.decimalFormater(resultData.getNonEcoDistance()));
		((TextView) rootView
				.findViewById(R.id.text_jarak_tempuh_non_eco_result))
				.setText(String.format("%.2f", resultData.getNonEcoDistance())
						+ " km");
		Loggers.i("boros_nonEcoFuel",
				Utils.decimalFormater(resultData.getBoros_nonEcoFuel()));
		((TextView) rootView.findViewById(R.id.text_boros_bbm_result))
				.setText(String.format("%.2f", resultData.getBoros_nonEcoFuel())
						+ " L");
		Loggers.i(
				"text_boros_emisi_result",
				Utils.decimalFormater(Constant.FAKTOR_EMISI
						* resultData.getBoros_nonEcoFuel()));
		((TextView) rootView.findViewById(R.id.text_boros_emisi_result))
				.setText(String.format("%.2f",
						(Constant.FAKTOR_EMISI * resultData
								.getBoros_nonEcoFuel()))
						+ " kgCO2eq");
		Loggers.i("vice_nonEcoFuel",
				Utils.decimalFormater(resultData.getVice_nonEcoFuel()));
		((TextView) rootView
				.findViewById(R.id.text_konsumsi_bbm_if_eco_non_eco_result))
				.setText(String.format("%.2f", resultData.getVice_nonEcoFuel())
						+ " L");
		Loggers.i("nonEcoFuel",
				Utils.decimalFormater(resultData.getNonEcoFuel()));
		((TextView) rootView
				.findViewById(R.id.text_konsumsi_bbm_non_eco_result))
				.setText(String.format("%.2f", resultData.getNonEcoFuel())
						+ " L");

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
		callback.setDataTrip(resultData.getEcoFuel(),
				resultData.getNonEcoFuel(), resultData.getEcoDistance(),
				resultData.getNonEcoDistance());
		return rootView;
	}

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
			for (int w = 0; w < resultData.getGraphDataWaktu().size(); w++) {
				xySeries.add(w, resultData.getGraphDataWaktu().get(w));
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
			for (int w = 0; w < resultData.getDistanceSpeed().size(); w++) {
				xySeries.add(resultData.getDistanceSpeed().get(w), resultData
						.getGraphDataSpeed().get(w));
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
		int max = resultData.getTripdata().getMotor().getMax_speed_eco();
		for (double w = -Constant.SPEED_TOLERANCE; w < resultData
				.getGraphDataSpeed().size() + Constant.SPEED_TOLERANCE; w += 0.2d)
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
		if (resultData.getTripdata().isNamed())
			userDialog.setTextField(resultData.getTripdata().getTitle());
		userDialog.setInputTypeText();
		userDialog.setCustomTextButton("Batal", "Bagikan");
		userDialog.show(getFragmentManager(), null);
	}

	private void initDialogSave() {
		// TODO Auto-generated method stub
		userDialog = new UserInputDialogFragment(USER_ACTION_SAVE_ID,
				"Simpan Perjalanan", "Masukkan judul", true, this);
		userDialog.setInputTypeText();
		if (resultData.getTripdata().isNamed())
			userDialog.setTextField(resultData.getTripdata().getTitle());
		userDialog.show(getFragmentManager(), null);
	}

	private void initDialogSaveBackPressed() {
		// TODO Auto-generated method stub
		userDialog = new UserInputDialogFragment(
				USER_SAVE_BACKPRESSED_INPUT_ID, "Simpan Perjalanan",
				"Masukkan judul", true, this);
		userDialog.setInputTypeText();
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
				if (arg0.isEmpty()) {
					Utils.toast(getActivity(), "Kolom masukan harus diisi !");
					return;
				}
				resultData.getTripdata().setTitle(arg0);
				resultData.getTripdata().setSaved(true);
			} else {
				resultData.getTripdata().setSaved(false);
			}
			TripDataAdapter.updateTrip(getActivity(), resultData.getTripdata());
			callback.goToMainMenu();
			break;

		case USER_ACTION_SAVE_ID:
			if (action) {
				if (arg0 == null) {
					Utils.toast(getActivity(), "Kolom masukan harus diisi !");
					return;
				}
				if (arg0.isEmpty()) {
					Utils.toast(getActivity(), "Kolom masukan harus diisi !");
					return;
				}
				resultData.getTripdata().setTitle(arg0);
				resultData.getTripdata().setSaved(true);
				TripDataAdapter.updateTrip(getActivity(),
						resultData.getTripdata());
			}
			break;
		case USER_ACTION_SHARE_ID:
			if (action) {
				if (arg0 == null) {
					Utils.toast(getActivity(), "Kolom masukan harus diisi !");
					return;
				}
				if (arg0.isEmpty()) {
					Utils.toast(getActivity(), "Kolom masukan harus diisi !");
					return;
				}
				resultData.getTripdata().setTitle(arg0);
				resultData.getTripdata().setSaved(true);
				TripDataAdapter.updateTrip(getActivity(),
						resultData.getTripdata());
				shareTrip();
			}
			break;
		case USER_ACTION_DELETE_ID:
			if (action) {
				resultData.getTripdata().setSaved(false);
				TripDataAdapter.updateTrip(getActivity(),
						resultData.getTripdata());
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
		Api api = new Builder().title(resultData.getTripdata().getTitle())
				.userName(resultData.getTripdata().getUser().getName())
				.userId(resultData.getTripdata().getUser_id())
				.tripId(resultData.getTripdata().getRow_id())
				.time(resultData.getTripdata().getTime_start()).build();

		intent.putExtra(Intent.EXTRA_SUBJECT, api.shareFormatterSubject());
		intent.putExtra(Intent.EXTRA_TEXT, api.shareFormatterBody());
		startActivity(Intent.createChooser(intent, "Bagikan via "));
	}

	@Override
	public void onDismiss(int id) {
		// TODO Auto-generated method stub
		
	}
}
