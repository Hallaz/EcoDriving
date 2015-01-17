package com.pertamina.tbbm.rewulu.ecodriving.fragment;

import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.TextView;

import com.pertamina.tbbm.rewulu.ecodriving.ContentsActivity;
import com.pertamina.tbbm.rewulu.ecodriving.R;
import com.pertamina.tbbm.rewulu.ecodriving.adapters.SpinnerMenuCustom;
import com.pertamina.tbbm.rewulu.ecodriving.adapters.SpinnerMenuCustomAdapter;
import com.pertamina.tbbm.rewulu.ecodriving.adapters.SpinnerMotorCustom;
import com.pertamina.tbbm.rewulu.ecodriving.databases.sps.LocationSP;
import com.pertamina.tbbm.rewulu.ecodriving.dialogs.IndicatorSettingDialog;
import com.pertamina.tbbm.rewulu.ecodriving.listener.OnMainListener;
import com.pertamina.tbbm.rewulu.ecodriving.locations.LocationEngine;
import com.pertamina.tbbm.rewulu.ecodriving.pojos.Motor;
import com.pertamina.tbbm.rewulu.ecodriving.pojos.Tripdata;
import com.pertamina.tbbm.rewulu.ecodriving.utils.Constant;
import com.pertamina.tbbm.rewulu.ecodriving.utils.Loggers;
import com.pertamina.tbbm.rewulu.ecodriving.utils.Utils;

public class MainMenuFragment extends Fragment implements OnClickListener {
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		try {
			mMenuCallback = (OnMainListener) activity;
		} catch (ClassCastException e) {
			// TODO: handle exception
			throw new ClassCastException(activity.toString()
					+ " must implement MainMenuCallback");
		}
	}

	public void setDataMotor(List<Motor> motors) {
		this.motors = motors;
	}

	public void onBackPressed() {
		mMenuCallback.appExit();
	}

	private List<Motor> motors;
	private OnMainListener mMenuCallback;
	private Tripdata tripdata;
	private SpinnerMenuCustom menu;
	private final String[] menus = new String[] { "Panduan Eco Driving",
			"Rekaman Perjalanan", "Pengaturan Indikator", "Pengaturan Profile",
			"Tentang Aplikasi" };
	private SeekBar fuelControl;
	private LocationEngine locationEngine;
	private TextView textFuel;
	private TextView textMaxFuel;
	private ImageView imageMotor;
	private double fuel = 0f;
	private int progressSeek = 0;
	private Button btn_start;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Backstack.onMainmenu();
		View rootView = inflater.inflate(R.layout.fragment_main_menu,
				container, false);
		Loggers.getInstance("MainMenuFragment");
		if (tripdata == null)
			tripdata = new Tripdata();
		Spinner spinner = (Spinner) rootView
				.findViewById(R.id.motor_type_spinner);
		menu = (SpinnerMenuCustom) rootView.findViewById(R.id.spinner_menu);
		imageMotor = (ImageView) rootView.findViewById(R.id.img_motor);
		menu.setAdapter(new SpinnerMenuCustomAdapter(getActivity(),
				R.layout.spinner_custom, Constant.menu_item(menus),
				Constant.MENU_CONST, Gravity.LEFT,
				new SpinnerMenuCustomAdapter.onItemClickListener() {

					@Override
					public void onClick(int arg0) {
						// TODO Auto-generated method stub
						switch (arg0) {
						case 0:
							startContent();
							break;
						case 1:
							mMenuCallback.startHistory();
							break;
						case 2:
							popUpIndicatorSetting();
							break;
						case 3:
							mMenuCallback.startUserSetting();
							break;
						case 4:
							mMenuCallback.startAbout();
							break;
						default:
							break;
						}

					}
				}));

		spinner.setAdapter(new SpinnerMotorCustom(getActivity(),
				R.layout.spinner_custom, motors));
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				Loggers.d("setOnItemSelectedListener", "motors.get(arg2) "
						+ motors.get(arg2).getSample());
				mMenuCallback.loadImageTo(motors.get(arg2), imageMotor);
				fuel = 0f;
				tripdata.setMotor(motors.get(arg2));
				setFuel();
				textMaxFuel.setText("Maks " + motors.get(arg2).getMax_fuel()
						+ " L");
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
		btn_start = (Button) rootView.findViewById(R.id.start_btn);
		btn_start.setOnClickListener(this);
		((ImageButton) rootView.findViewById(R.id.action_setting_menu))
				.setOnClickListener(this);
		if (LocationSP.isNeedKnowingLocation(getActivity())) {
			locationEngine = new LocationEngine(getActivity());
			locationEngine.settLastLocation();
		}
		fuelControl = (SeekBar) rootView.findViewById(R.id.seekBar_start);
		fuelControl.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				progressSeek = progress;
				setFuel();
			}
		});
		textFuel = (TextView) rootView.findViewById(R.id.txt_fuel_start);
		textMaxFuel = (TextView) rootView.findViewById(R.id.txt_max_start);
		if (!LocationEngine.GPS_ENABLE) {
			btn_start.setClickable(false);
		}
		return rootView;
	}

	private void setFuel() {
		if (tripdata.getMotor() != null) {
			double max = tripdata.getMotor().getMax_fuel();
			fuel = (max * progressSeek) / 100;
			textFuel.setText(String.format("%.2f", fuel) + " L");
		}
	}

	private void popUpIndicatorSetting() {
		// TODO Auto-generated method stub
		menu.onDetachedFromWindow();
		IndicatorSettingDialog dialog = new IndicatorSettingDialog();
		dialog.show(getFragmentManager(), null);
	}

	private void startTrack() {
		// TODO Auto-generated method stub
		if (tripdata.getMotor() == null) {
			Utils.toast(getActivity(), R.string.err_data_motor);
		} else if (fuel == 0f) {
			Utils.toast(getActivity(), R.string.err_data_fuel);
		} else {
			if (fuel < 0.5d || fuel > tripdata.getMotor().getMax_fuel())
				Utils.toast(getActivity(), R.string.err_data_fuel_false);
			else {
				tripdata.setFuel(fuel);
				getActivity();
				tripdata.setTime_start(Utils.getNowTime());
				mMenuCallback.startTrack(tripdata);
			}
		}
	}

	private void startContent() {
		// TODO Auto-generated method stub
		getActivity().startActivity(
				new Intent().setClass(getActivity(), ContentsActivity.class));
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.start_btn:
			startTrack();
			break;
		case R.id.action_setting_menu:
			menu.performClick();
			break;
		default:
			break;
		}
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (locationEngine != null)
			locationEngine.onDestroy();
	}

	public void setAvailable(boolean available) {
		if (LocationEngine.GPS_ENABLE) {
			if (!available) {
				if (btn_start != null)
					btn_start.setClickable(false);
			} else if (btn_start != null)
				btn_start.setClickable(true);
		}
	}
}
