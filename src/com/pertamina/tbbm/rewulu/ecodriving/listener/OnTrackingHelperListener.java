package com.pertamina.tbbm.rewulu.ecodriving.listener;

import android.location.Location;

public interface OnTrackingHelperListener {
	public void onConnected();
	
	public void onDisconnected();
	
	public void goToPositon(Location arg0, Float bearing);

	public void onDistanceChanged(double distance);

	public void onSpeedChanged(int speed);

	public void drawLine(Location loc1, Location loc2);

	public void setTextSpeedo(int speed);

	public void setTextDistance();

	public void addGraphView();

	public void logData(Location location, int speed, double distance, long time,
			boolean acceleration);
	public void timer(long milis);
}
