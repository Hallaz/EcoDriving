package com.pertamina.tbbm.rewulu.ecodriving.pojos;

import java.util.HashMap;
import java.util.Map;

import com.pertamina.tbbm.rewulu.ecodriving.utils.Constant;
import com.pertamina.tbbm.rewulu.ecodriving.utils.Loggers;

public class Tripdata {

	public static final int STATUS_RUNNING = 1;
	public static final int STATUS_INCOMPLETE = 2;
	public static final int STATUS_COMPLETE = 3;

	private int row_id = -1;
	private int local_id = -1;
	private UserData user;
	private Motor motor;
	private String title = Constant.TITLE_UNKNOWN;;
	private double fuel = -1;
	private double end_fuel = -1;
	private long time_start = -1;
	private long total_time = -1;
	private String addrss_start = Constant.ADDRESS_UNKNOWN;
	private String addrss_end = Constant.ADDRESS_UNKNOWN;
	private double eco_fuel = -1d;
	private double non_eco_fuel = -1d;
	private double eco_distance = -1d;
	private double non_eco_distance = -1d;
	private int status;
	private boolean user_save = false;
	
	public void Log(String from) {
		if (!Loggers.LOG)
			return;
		android.util.Log.i("Tripdata", "LOGGING FROM " + from);
		android.util.Log.i("Tripdata", "row_id " + row_id);
		android.util.Log.i("Tripdata", "local_id " + local_id);
		android.util.Log.i("Tripdata", "user " + (user == null));
		android.util.Log.i("Tripdata", "motor " + (motor == null));
		android.util.Log.i("Tripdata", "title " + title);
		android.util.Log.i("Tripdata", "fuel " + fuel);
		android.util.Log.i("Tripdata", "end_fuel " + end_fuel);
		android.util.Log.i("Tripdata", "time_start " + time_start);
		android.util.Log.i("Tripdata", "total_time " + total_time);
		android.util.Log.i("Tripdata", "addrss_start " + addrss_start);
		android.util.Log.i("Tripdata", "addrss_end " + addrss_end);
		android.util.Log.i("Tripdata", "eco_fuel " + eco_fuel);
		android.util.Log.i("Tripdata", "non_eco_fuel " + non_eco_fuel);
		android.util.Log.i("Tripdata", "eco_distance " + eco_distance);
		android.util.Log.i("Tripdata", "non_eco_distance " + non_eco_distance);
		android.util.Log.i("Tripdata", "status " + status);
		android.util.Log.i("Tripdata", "user_save " + user_save);
	}

	public static final String KEY_TRIP_ROW_ID = "row_id";
	public static final String KEY_TRIP_LOCAL_ID = "local_id";
	public static final String KEY_TRIP_USER_ID = "user_id";
	public static final String KEY_TRIP_MOTOR_ID = "motor_id";
	public static final String KEY_TRIP_TITLE = "title";
	public static final String KEY_TRIP_ADDRSS_START = "addrss_start";
	public static final String KEY_TRIP_ADDRSS_END = "addrss_end";
	public static final String KEY_TRIP_TIME_START = "time_start";
	public static final String KEY_TRIP_TOTAL_TIME = "total_time";
	public static final String KEY_TRIP_ECO_FUEL = "eco_fuel";
	public static final String KEY_TRIP_NON_ECO_FUEL = "non_eco_fuel";
	public static final String KEY_TRIP_ECO_DISTANCE = "eco_distance";
	public static final String KEY_TRIP_NON_ECO_DISTANCE = "non_eco_distance";
	public static final String KEY_TRIP_STATUS = "status";
	public static final String KEY_TRIP_USER_SAVE = "user_save";

	public Tripdata() {
		// TODO Auto-generated constructor stub
	}

	public Map<String, String> getParameterMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put(KEY_TRIP_ROW_ID, String.valueOf(row_id));
		map.put(KEY_TRIP_USER_ID, String.valueOf(user.getRow_id()));
		map.put(KEY_TRIP_MOTOR_ID, String.valueOf(motor.getRow_id()));
		map.put(KEY_TRIP_ADDRSS_START, addrss_start);
		map.put(KEY_TRIP_ADDRSS_END, addrss_end);
		map.put(KEY_TRIP_TIME_START, String.valueOf(time_start));
		map.put(KEY_TRIP_TOTAL_TIME, String.valueOf(total_time));
		map.put(KEY_TRIP_ECO_FUEL, String.valueOf(eco_fuel));
		map.put(KEY_TRIP_NON_ECO_FUEL, String.valueOf(non_eco_fuel));
		map.put(KEY_TRIP_ECO_DISTANCE, String.valueOf(eco_distance));
		map.put(KEY_TRIP_NON_ECO_DISTANCE, String.valueOf(non_eco_distance));
		map.put(UserData.KEY_USER_EMAIL, user.getEmail());
		return map;
	}

	public boolean isAddressStartSet() {
		return !addrss_start.equals(Constant.ADDRESS_UNKNOWN);
	}

	public boolean isAddressEndSet() {
		return !addrss_end.equals(Constant.ADDRESS_UNKNOWN);
	}

	public int getUser_id() {
		if (user != null)
			return user.getRow_id();
		return -1;
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
	 * @return the motor
	 */
	public Motor getMotor() {
		return motor;
	}

	/**
	 * @param motor
	 *            the motor to set
	 */
	public void setMotor(Motor motor) {
		this.motor = motor;
	}

	/**
	 * @return the end_fuel
	 */
	public double getEnd_fuel() {
		return end_fuel;
	}

	/**
	 * @param end_fuel
	 *            the end_fuel to set
	 */
	public void setEnd_fuel(double end_fuel) {
		this.end_fuel = end_fuel;
	}

	/**
	 * @return the addrss_start
	 */
	public String getAddrss_start() {
		return addrss_start;
	}

	/**
	 * @param addrss_start
	 *            the addrss_start to set
	 */
	public void setAddrss_start(String addrss_start) {
		this.addrss_start = addrss_start;
	}

	/**
	 * @return the addrss_end
	 */
	public String getAddrss_end() {
		return addrss_end;
	}

	/**
	 * @param addrss_end
	 *            the addrss_end to set
	 */
	public void setAddrss_end(String addrss_end) {
		this.addrss_end = addrss_end;
	}

	/**
	 * @return the user
	 */
	public UserData getUser() {
		return user;
	}

	/**
	 * @param user
	 *            the user to set
	 */
	public void setUser(UserData user) {
		this.user = user;
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
	 * @return the non_eco_distance
	 */
	public double getNon_eco_distance() {
		return non_eco_distance;
	}

	/**
	 * @param non_eco_distance
	 *            the non_eco_distance to set
	 */
	public void setNon_eco_distance(double non_eco_distance) {
		this.non_eco_distance = non_eco_distance;
	}

	/**
	 * @return the eco_distance
	 */
	public double getEco_distance() {
		return eco_distance;
	}

	/**
	 * @param eco_distance
	 *            the eco_distance to set
	 */
	public void setEco_distance(double eco_distance) {
		this.eco_distance = eco_distance;
	}

	/**
	 * @return the non_eco_fuel
	 */
	public double getNon_eco_fuel() {
		return non_eco_fuel;
	}

	/**
	 * @param non_eco_fuel
	 *            the non_eco_fuel to set
	 */
	public void setNon_eco_fuel(double non_eco_fuel) {
		this.non_eco_fuel = non_eco_fuel;
	}

	/**
	 * @return the eco_fuel
	 */
	public double getEco_fuel() {
		return eco_fuel;
	}

	/**
	 * @param eco_fuel
	 *            the eco_fuel to set
	 */
	public void setEco_fuel(double eco_fuel) {
		this.eco_fuel = eco_fuel;
	}

	/**
	 * @return the total_time
	 */
	public long getTotal_time() {
		return total_time;
	}

	/**
	 * @param total_time
	 *            the total_time to set
	 */
	public void setTotal_time(long total_time) {
		this.total_time = total_time;
	}

	/**
	 * @return the time_start
	 */
	public long getTime_start() {
		return time_start;
	}

	/**
	 * @param time_start
	 *            the time_start to set
	 */
	public void setTime_start(long time_start) {
		this.time_start = time_start;
	}

	/**
	 * @return the complete
	 */
	public boolean isComplete() {
		return status == STATUS_COMPLETE;
	}

	public boolean isIncomplete() {
		return status == STATUS_INCOMPLETE;
	}

	public boolean isRunning() {
		return status == STATUS_RUNNING;
	}

	/**
	 * @param complete
	 *            the complete to set
	 */
	public void setComplete() {
		this.status = STATUS_COMPLETE;
	}

	public void setIncomplete() {
		status = STATUS_INCOMPLETE;
	}

	public void setRunning() {
		status = STATUS_RUNNING;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getStatus() {
		return status;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the deleted
	 */
	public boolean isSaved() {
		return user_save;
	}

	/**
	 * @param deleted the deleted to set
	 */
	public void setSaved(boolean saved) {
		this.user_save = saved;
	}
	
	public boolean isNamed() {
		return !title.equals(Constant.TITLE_UNKNOWN);
	}
}
