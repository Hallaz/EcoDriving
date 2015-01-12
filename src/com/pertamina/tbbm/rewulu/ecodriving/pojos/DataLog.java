package com.pertamina.tbbm.rewulu.ecodriving.pojos;

import com.google.android.gms.maps.model.LatLng;
import com.pertamina.tbbm.rewulu.ecodriving.utils.Loggers;

import android.location.Location;

public class DataLog {

	public DataLog() {
		// TODO Auto-generated constructor stub
	}

	public DataLog(int id, int tripdata_id, double distance,
			boolean drive_state, int speed, long time, double fuel,
			double fuel_age, double latitude, double longitude) {
		// TODO Auto-generated constructor stub
		this.id = id;
		this.tripdata_id = tripdata_id;
		this.distance = distance;
		this.drive_state = drive_state;
		this.speed = speed;
		this.time = time;
		this.fuel = fuel;
		this.fuel_age = fuel_age;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public int id = -1;
	public int row_id = -1;
	public int local_id = -1;
	public int tripdata_id = -1;
	public Tripdata tripdata;
	public double distance;
	public boolean drive_state;
	public int speed = -1;
	public long time = -1;
	public double fuel = -1;
	public double fuel_age = -1;
	public double latitude = -1;
	public double longitude = -1;

	public void Log(String from) {
		if (!Loggers.LOG)
			return;
		android.util.Log.i("DataLog", " LOGGING FROM " + from);
		android.util.Log.i("DataLog", "id " + id);
		android.util.Log.i("DataLog", "row_id " + row_id);
		android.util.Log.i("DataLog", "local_id " + local_id);
		android.util.Log.i("DataLog", "tripdata " + (tripdata == null));
		android.util.Log.i("DataLog", "tripdata_id " + tripdata_id);
		android.util.Log.i("DataLog", "drive_state " + drive_state);
		android.util.Log.i("DataLog", "speed " + speed);
		android.util.Log.i("DataLog", "time " + time);
		android.util.Log.i("DataLog", "fuel " + fuel);
		android.util.Log.i("DataLog", "fuel_age " + fuel_age);
		android.util.Log.i("DataLog", "latitude " + latitude);
		android.util.Log.i("DataLog", "longitude " + longitude);
	}

	public boolean isComplete() {
		return row_id != -1;
	}

	public int getTripdata_id() {
		return tripdata_id;
	}

	/**
	 * @return the row_id
	 */
	public int getRow_id() {
		return row_id;
	}

	/**
	 * @param row_id
	 *            the row_id to set
	 */
	public void setRow_id(int row_id) {
		this.row_id = row_id;
	}

	/**
	 * @return the tripdata
	 */
	public Tripdata getTripdata() {
		return tripdata;
	}

	/**
	 * @param tripdata
	 *            the tripdata to set
	 */
	public void setTripdata(Tripdata tripdata) {
		this.tripdata = tripdata;
		if (tripdata != null)
			tripdata_id = tripdata.getRow_id();
	}

	/**
	 * @return the distance
	 */
	public double getDistance() {
		return distance;
	}

	/**
	 * @param distance
	 *            the distance to set
	 */
	public void setDistance(double distance) {
		this.distance = distance;
	}

	/**
	 * @return the drive_state
	 */
	public boolean getDrive_state() {
		return drive_state;
	}

	/**
	 * @param drive_state
	 *            the drive_state to set
	 */
	public void setDrive_state(boolean drive_state) {
		this.drive_state = drive_state;
	}

	/**
	 * @return the speed
	 */
	public int getSpeed() {
		return speed;
	}

	/**
	 * @param speed
	 *            the speed to set
	 */
	public void setSpeed(int speed) {
		this.speed = speed;
	}

	/**
	 * @return the time
	 */
	public long getTime() {
		return time;
	}

	/**
	 * @param time
	 *            the time to set
	 */
	public void setTime(long time) {
		this.time = time;
	}

	/**
	 * @return the fuel
	 */
	public double getFuel() {
		return fuel;
	}

	/**
	 * @param fuel
	 *            the fuel to set
	 */
	public void setFuel(double fuel) {
		this.fuel = fuel;
	}

	/**
	 * @return the fuel_age
	 */
	public double getFuel_age() {
		return fuel_age;
	}

	/**
	 * @param fuel_age
	 *            the fuel_age to set
	 */
	public void setFuel_age(double fuel_age) {
		this.fuel_age = fuel_age;
	}

	/**
	 * @return the latitude
	 */
	public double getLatitude() {
		return latitude;
	}

	/**
	 * @param latitude
	 *            the latitude to set
	 */
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	/**
	 * @return the longitude
	 */
	public double getLongitude() {
		return longitude;
	}

	/**
	 * @param longitude
	 *            the longitude to set
	 */
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public void setLocation(Location location) {
		latitude = location.getLatitude();
		longitude = location.getLongitude();
	}

	/**
	 * @return the local_id
	 */
	public int getLocal_id() {
		return local_id;
	}

	/**
	 * @param local_id
	 *            the local_id to set
	 */
	public void setLocal_id(int local_id) {
		this.local_id = local_id;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	public LatLng getLatLng() {
		// TODO Auto-generated method stub
		return new LatLng(latitude, longitude);
	}
}
