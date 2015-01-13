package com.pertamina.tbbm.rewulu.ecodriving.databases;

import java.io.IOException;
import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.pertamina.tbbm.rewulu.ecodriving.helpers.DataBaseHelper;
import com.pertamina.tbbm.rewulu.ecodriving.pojos.DataLog;
import com.pertamina.tbbm.rewulu.ecodriving.pojos.Tripdata;
import com.pertamina.tbbm.rewulu.ecodriving.utils.Loggers;

public class LogDataAdapter {

	private DataBaseHelper mDbHelper;
	private SQLiteDatabase mDb;
	private static final String SQLITE_TABLE = "t_datalog";
	private final Context context;

	private LogDataAdapter(Context context) {
		this.context = context;
		this.mDbHelper = new DataBaseHelper(this.context);
	}

	private LogDataAdapter open() throws SQLException {
		try {
			this.mDbHelper.openDataBase();
			this.mDbHelper.close();
			this.mDb = mDbHelper.getReadableDatabase();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		return this;
	}

	private void close() {
		if (this.mDbHelper != null) {
			this.mDbHelper.close();
		}
	}

	private LogDataAdapter createDatabase() throws SQLException {
		try {
			this.mDbHelper.createDataBase();
		} catch (IOException e) {
			e.printStackTrace();
			throw new Error("UnableToCreateDatabase");
		}
		return this;
	}

	public static final String KEY_LOG_DISTANCE = "distance";
	public static final String KEY_LOG_DRIVE_STATE = "drive_state";
	public static final String KEY_LOG_FUEL = "fuel";
	public static final String KEY_LOG_FUEL_AGE = "fuel_age";
	public static final String KEY_LOG_LATITUDE = "latitude";
	public static final String KEY_LOG_LONGITUDE = "longitude";
	public static final String KEY_LOG_ROW_ID = "row_id";
	public static final String KEY_LOG_LOCAL_ID = "local_id";
	public static final String KEY_LOG_SPEED = "speed";
	public static final String KEY_LOG_TIME = "time";
	public static final String KEY_LOG_TRIPDATA_ID = "tripdata_id";

	private Cursor fetchByTripId(int trip_id) {
		Loggers.i("LogDataAdapter - fetchByTripId", trip_id);
		Cursor cursor = this.mDb.query(SQLITE_TABLE, new String[] {
				KEY_LOG_DISTANCE, KEY_LOG_DRIVE_STATE, KEY_LOG_FUEL,
				KEY_LOG_FUEL_AGE, KEY_LOG_LATITUDE, KEY_LOG_LONGITUDE,
				KEY_LOG_ROW_ID, KEY_LOG_LOCAL_ID, KEY_LOG_SPEED, KEY_LOG_TIME,
				KEY_LOG_TRIPDATA_ID, }, KEY_LOG_TRIPDATA_ID + " = '" + trip_id
				+ "'", null, null, null, "cast(" + KEY_LOG_TIME
				+ " as REAL) ASC");
		if (cursor != null) {
			cursor.moveToFirst();
		}
		return cursor;
	}

	private Cursor fetchByTripId() {
		Cursor cursor = this.mDb.query(SQLITE_TABLE, new String[] {
				KEY_LOG_DISTANCE, KEY_LOG_DRIVE_STATE, KEY_LOG_FUEL,
				KEY_LOG_FUEL_AGE, KEY_LOG_LATITUDE, KEY_LOG_LONGITUDE,
				KEY_LOG_ROW_ID, KEY_LOG_LOCAL_ID, KEY_LOG_SPEED, KEY_LOG_TIME,
				KEY_LOG_TRIPDATA_ID, }, null, null, null, null, "cast("
				+ KEY_LOG_TIME + " as REAL) ASC");
		if (cursor != null) {
			cursor.moveToFirst();
		}
		return cursor;
	}

	private long insertLog(int row_id, int tripdata_id, double distance,
			boolean drive_state, int speed, long time, double fuel,
			double fuel_age, double latitude, double longitude) {
		ContentValues cv = new ContentValues();
		cv.put(KEY_LOG_ROW_ID, row_id);
		cv.put(KEY_LOG_TRIPDATA_ID, tripdata_id);
		cv.put(KEY_LOG_DISTANCE, distance);
		cv.put(KEY_LOG_DRIVE_STATE, drive_state);
		cv.put(KEY_LOG_SPEED, speed);
		cv.put(KEY_LOG_TIME, time);
		cv.put(KEY_LOG_FUEL, fuel);
		cv.put(KEY_LOG_FUEL_AGE, fuel_age);
		cv.put(KEY_LOG_LATITUDE, latitude);
		cv.put(KEY_LOG_LONGITUDE, longitude);
		return this.mDb.insert(SQLITE_TABLE, null, cv);
	}

	private long updateLog(int local_id, int row_id, int tripdata_id,
			double distance, boolean drive_state, int speed, long time,
			double fuel, double fuel_age, double latitude, double longitude) {
		ContentValues cv = new ContentValues();
		cv.put(KEY_LOG_ROW_ID, row_id);
		cv.put(KEY_LOG_TRIPDATA_ID, tripdata_id);
		cv.put(KEY_LOG_DISTANCE, distance);
		cv.put(KEY_LOG_DRIVE_STATE, drive_state);
		cv.put(KEY_LOG_SPEED, speed);
		cv.put(KEY_LOG_TIME, time);
		cv.put(KEY_LOG_FUEL, fuel);
		cv.put(KEY_LOG_FUEL_AGE, fuel_age);
		cv.put(KEY_LOG_LATITUDE, latitude);
		cv.put(KEY_LOG_LONGITUDE, longitude);
		return this.mDb.update(SQLITE_TABLE, cv, KEY_LOG_LOCAL_ID + "="
				+ local_id, null);
	}

	private long deleteByLocal_id(int local_id) {
		return this.mDb.delete(SQLITE_TABLE, KEY_LOG_LOCAL_ID + "=" + local_id,
				null);
	}

	private long deleteByTripId(int trip_id) {
		return this.mDb.delete(SQLITE_TABLE, KEY_LOG_TRIPDATA_ID + "="
				+ trip_id, null);
	}

	public static ArrayList<DataLog> readAllLogByTrip(Context context) {
		ArrayList<DataLog> data = new ArrayList<>();
		LogDataAdapter db = new LogDataAdapter(context);
		db.createDatabase();
		db.open();
		Cursor cursor;
		cursor = db.fetchByTripId();
		if (cursor != null && cursor.moveToFirst())
			do {
				DataLog log = new DataLog();
				log.setRow_id(cursor.getInt(cursor
						.getColumnIndexOrThrow(KEY_LOG_LOCAL_ID)));
				log.setLocal_id(cursor.getInt(cursor
						.getColumnIndexOrThrow(KEY_LOG_LOCAL_ID)));
				log.setTripdata(TripDataAdapter.readTripByLocalId(context, cursor
						.getInt(cursor
								.getColumnIndexOrThrow(KEY_LOG_TRIPDATA_ID))));
				log.setDistance(cursor.getDouble(cursor
						.getColumnIndexOrThrow(KEY_LOG_DISTANCE)));
				log.setDrive_state(cursor.getInt(cursor
						.getColumnIndexOrThrow(KEY_LOG_DRIVE_STATE)) > 0);
				log.setSpeed(cursor.getInt(cursor
						.getColumnIndexOrThrow(KEY_LOG_SPEED)));
				log.setTime(cursor.getLong(cursor
						.getColumnIndexOrThrow(KEY_LOG_TIME)));
				log.setFuel(cursor.getDouble(cursor
						.getColumnIndexOrThrow(KEY_LOG_FUEL)));
				log.setFuel_age(cursor.getDouble(cursor
						.getColumnIndexOrThrow(KEY_LOG_FUEL_AGE)));
				log.setLatitude(cursor.getDouble(cursor
						.getColumnIndexOrThrow(KEY_LOG_LATITUDE)));
				log.setLongitude(cursor.getDouble(cursor
						.getColumnIndexOrThrow(KEY_LOG_LONGITUDE)));
				data.add(log);
			} while (cursor.moveToNext());
		db.close();
		return data;
	}

	public static ArrayList<DataLog> readAllLogByTrip(Context context,
			Tripdata trip) {
		ArrayList<DataLog> data = new ArrayList<>();
		LogDataAdapter db = new LogDataAdapter(context);
		db.createDatabase();
		db.open();
		Cursor cursor;
		cursor = db.fetchByTripId(trip.getLocal_id());
		if (cursor != null && cursor.moveToFirst())
			do {
				DataLog log = new DataLog();
				log.setRow_id(cursor.getInt(cursor
						.getColumnIndexOrThrow(KEY_LOG_LOCAL_ID)));
				log.setLocal_id(cursor.getInt(cursor
						.getColumnIndexOrThrow(KEY_LOG_LOCAL_ID)));
				
				log.setTripdata(TripDataAdapter.readTripByLocalId(context, cursor
						.getInt(cursor
								.getColumnIndexOrThrow(KEY_LOG_TRIPDATA_ID))));
				log.setDistance(cursor.getDouble(cursor
						.getColumnIndexOrThrow(KEY_LOG_DISTANCE)));
				log.setDrive_state(cursor.getInt(cursor
						.getColumnIndexOrThrow(KEY_LOG_DRIVE_STATE)) > 0);
				log.setSpeed(cursor.getInt(cursor
						.getColumnIndexOrThrow(KEY_LOG_SPEED)));
				log.setTime(cursor.getLong(cursor
						.getColumnIndexOrThrow(KEY_LOG_TIME)));
				log.setFuel(cursor.getDouble(cursor
						.getColumnIndexOrThrow(KEY_LOG_FUEL)));
				log.setFuel_age(cursor.getDouble(cursor
						.getColumnIndexOrThrow(KEY_LOG_FUEL_AGE)));
				log.setLatitude(cursor.getDouble(cursor
						.getColumnIndexOrThrow(KEY_LOG_LATITUDE)));
				log.setLongitude(cursor.getDouble(cursor
						.getColumnIndexOrThrow(KEY_LOG_LONGITUDE)));
				data.add(log);
			} while (cursor.moveToNext());
		db.close();
		return data;
	}

	public static long insertLog(Context context, DataLog data) {
		LogDataAdapter db = new LogDataAdapter(context);
		db.createDatabase();
		db.open();
		long l = db.insertLog(data.getRow_id(), data.getTripdata().getLocal_id(),
				data.getDistance(), data.getDrive_state(), data.getSpeed(),
				data.getTime(), data.getFuel(), data.getFuel_age(),
				data.getLatitude(), data.getLongitude());
		db.close();
		return l;
	}

	public static long updateLog(Context context, DataLog data) {
		LogDataAdapter db = new LogDataAdapter(context);
		db.createDatabase();
		db.open();
		long l = db.updateLog(data.getLocal_id(), data.getRow_id(),
				data.getTripdata().getLocal_id(), data.getDistance(),
				data.getDrive_state(), data.getSpeed(), data.getTime(),
				data.getFuel(), data.getFuel_age(), data.getLatitude(),
				data.getLongitude());
		db.close();
		return l;
	}

	public static long deleteLogById(Context context, DataLog data) {
		LogDataAdapter db = new LogDataAdapter(context);
		db.createDatabase();
		db.open();
		long l = db.deleteByLocal_id(data.getTripdata_id());
		db.close();
		return l;
	}

	public static long deleteLogByTripId(Context context, DataLog data) {
		LogDataAdapter db = new LogDataAdapter(context);
		db.createDatabase();
		db.open();
		long l = db.deleteByTripId(data.getTripdata_id());
		db.close();
		return l;
	}

	public static long deleteLogByTripId(Context context, int trip_id) {
		LogDataAdapter db = new LogDataAdapter(context);
		db.createDatabase();
		db.open();
		long l = db.deleteByTripId(trip_id);
		db.close();
		return l;
	}
}
