package com.pertamina.tbbm.rewulu.ecodriving.listener;

import java.util.List;

import android.widget.ImageView;

import com.pertamina.tbbm.rewulu.ecodriving.pojos.DataLog;
import com.pertamina.tbbm.rewulu.ecodriving.pojos.Motor;
import com.pertamina.tbbm.rewulu.ecodriving.pojos.Tripdata;
import com.pertamina.tbbm.rewulu.ecodriving.pojos.UserData;

public interface OnMainListener {
	
	public void startTrack(Tripdata tripdata);

	public void startResult();

	public void startResult(Tripdata trip);

	public void startHelp();
	
	public void startHistory();
	
	public void startMainMenu();
	
	public void startUserSetting();

	public void startApp(UserData user);

	public void upDateMotor(String email);

	public void setDataMotor(List<Motor> motors);

	public void storeLog(DataLog log);

	public void setDataTrip(double eco_fuel, double non_eco_fuel,
			double eco_distance, double non_eco_distance);
	
	public void loadImageTo(Motor motor, ImageView view);
	
	public void onBackActionPressed();
	
}
