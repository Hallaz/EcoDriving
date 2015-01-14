package com.pertamina.tbbm.rewulu.ecodriving.databases;

import java.io.IOException;
import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.pertamina.tbbm.rewulu.ecodriving.databases.sps.UserDataSP;
import com.pertamina.tbbm.rewulu.ecodriving.helpers.DataBaseHelper;
import com.pertamina.tbbm.rewulu.ecodriving.pojos.Tripdata;
import com.pertamina.tbbm.rewulu.ecodriving.utils.Loggers;

public class TripDataAdapter {

	private DataBaseHelper mDbHelper;
	private SQLiteDatabase mDb;
	private static final String SQLITE_TABLE = "t_tripdata";
	private final Context context;

	private TripDataAdapter(Context context) {
		this.context = context;
		this.mDbHelper = new DataBaseHelper(this.context);
	}

	private TripDataAdapter open() throws SQLException {
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

	private TripDataAdapter createDatabase() throws SQLException {
		try {
			this.mDbHelper.createDataBase();
		} catch (IOException e) {
			e.printStackTrace();
			throw new Error("UnableToCreateDatabase");
		}
		return this;
	}

	private Cursor fetchALL() {
		Cursor cursor = this.mDb.query(SQLITE_TABLE, new String[] {
				Tripdata.KEY_TRIP_ROW_ID, Tripdata.KEY_TRIP_LOCAL_ID,
				Tripdata.KEY_TRIP_USER_ID, Tripdata.KEY_TRIP_MOTOR_ID,
				Tripdata.KEY_TRIP_ADDRSS_START, Tripdata.KEY_TRIP_ADDRSS_END,
				Tripdata.KEY_TRIP_TIME_START, Tripdata.KEY_TRIP_TOTAL_TIME,
				Tripdata.KEY_TRIP_ECO_FUEL, Tripdata.KEY_TRIP_NON_ECO_FUEL,
				Tripdata.KEY_TRIP_ECO_DISTANCE,
				Tripdata.KEY_TRIP_NON_ECO_DISTANCE,
				Tripdata.KEY_TRIP_GRAPH_TIME, Tripdata.KEY_TRIP_STATUS,
				Tripdata.KEY_TRIP_TITLE, Tripdata.KEY_TRIP_USER_SAVE, }, null,
				null, null, null, "cast(" + Tripdata.KEY_TRIP_TIME_START
						+ " as REAL) DESC");
		if (cursor != null) {
			cursor.moveToFirst();
		}
		return cursor;
	}

	private Cursor fetchByLocalId(int local_id) {
		Cursor cursor = this.mDb.query(SQLITE_TABLE, new String[] {
				Tripdata.KEY_TRIP_ROW_ID, Tripdata.KEY_TRIP_LOCAL_ID,
				Tripdata.KEY_TRIP_USER_ID, Tripdata.KEY_TRIP_MOTOR_ID,
				Tripdata.KEY_TRIP_ADDRSS_START, Tripdata.KEY_TRIP_ADDRSS_END,
				Tripdata.KEY_TRIP_TIME_START, Tripdata.KEY_TRIP_TOTAL_TIME,
				Tripdata.KEY_TRIP_ECO_FUEL, Tripdata.KEY_TRIP_NON_ECO_FUEL,
				Tripdata.KEY_TRIP_ECO_DISTANCE,
				Tripdata.KEY_TRIP_NON_ECO_DISTANCE,
				Tripdata.KEY_TRIP_GRAPH_TIME, Tripdata.KEY_TRIP_STATUS,
				Tripdata.KEY_TRIP_TITLE, Tripdata.KEY_TRIP_USER_SAVE, },
				Tripdata.KEY_TRIP_LOCAL_ID + " = '" + local_id + "'", null,
				null, null, null);
		if (cursor != null) {
			cursor.moveToFirst();
		}
		return cursor;
	}

	private long insertTrip(int row_id, int user_id, int motor_id,
			String title, String addrss_start, String addrss_end,
			long time_start, long total_time, double eco_fuel,
			double non_eco_fuel, double eco_distance, double non_eco_distance,
			String graph_time, int status) {
		Loggers.w("insertTrip", "motor_id" + motor_id);
		ContentValues cv = new ContentValues();
		cv.put(Tripdata.KEY_TRIP_ROW_ID, row_id);
		cv.put(Tripdata.KEY_TRIP_USER_ID, user_id);
		cv.put(Tripdata.KEY_TRIP_MOTOR_ID, motor_id);
		cv.put(Tripdata.KEY_TRIP_TITLE, title);
		cv.put(Tripdata.KEY_TRIP_ADDRSS_START, addrss_start);
		cv.put(Tripdata.KEY_TRIP_ADDRSS_END, addrss_end);
		cv.put(Tripdata.KEY_TRIP_TIME_START, time_start);
		cv.put(Tripdata.KEY_TRIP_TOTAL_TIME, total_time);
		cv.put(Tripdata.KEY_TRIP_ECO_FUEL, eco_fuel);
		cv.put(Tripdata.KEY_TRIP_NON_ECO_FUEL, non_eco_fuel);
		cv.put(Tripdata.KEY_TRIP_ECO_DISTANCE, eco_distance);
		cv.put(Tripdata.KEY_TRIP_NON_ECO_DISTANCE, non_eco_distance);
		cv.put(Tripdata.KEY_TRIP_GRAPH_TIME, graph_time);
		cv.put(Tripdata.KEY_TRIP_STATUS, status);
		return this.mDb.insert(SQLITE_TABLE, null, cv);
	}

	private long updateTrip(int local_id, int row_id, int user_id,
			int motor_id, String title, String addrss_start, String addrss_end,
			long time_start, long total_time, double eco_fuel,
			double non_eco_fuel, double eco_distance, double non_eco_distance,
			String graph_time, int status, boolean saved) {
		Loggers.w("insertTrip", "motor_id" + motor_id);
		Loggers.w("updateTrip", local_id);
		ContentValues cv = new ContentValues();
		cv.put(Tripdata.KEY_TRIP_ROW_ID, row_id);
		cv.put(Tripdata.KEY_TRIP_LOCAL_ID, local_id);
		cv.put(Tripdata.KEY_TRIP_TITLE, title);
		cv.put(Tripdata.KEY_TRIP_USER_ID, user_id);
		cv.put(Tripdata.KEY_TRIP_MOTOR_ID, motor_id);
		cv.put(Tripdata.KEY_TRIP_ADDRSS_START, addrss_start);
		cv.put(Tripdata.KEY_TRIP_ADDRSS_END, addrss_end);
		cv.put(Tripdata.KEY_TRIP_TIME_START, time_start);
		cv.put(Tripdata.KEY_TRIP_TOTAL_TIME, total_time);
		cv.put(Tripdata.KEY_TRIP_ECO_FUEL, eco_fuel);
		cv.put(Tripdata.KEY_TRIP_NON_ECO_FUEL, non_eco_fuel);
		cv.put(Tripdata.KEY_TRIP_ECO_DISTANCE, eco_distance);
		cv.put(Tripdata.KEY_TRIP_NON_ECO_DISTANCE, non_eco_distance);
		cv.put(Tripdata.KEY_TRIP_GRAPH_TIME, graph_time);
		cv.put(Tripdata.KEY_TRIP_STATUS, status);
		cv.put(Tripdata.KEY_TRIP_USER_SAVE, saved);
		return this.mDb.update(SQLITE_TABLE, cv, Tripdata.KEY_TRIP_LOCAL_ID
				+ "=" + local_id, null);
	}

	public long deleteByLocal_id(int local_id) {
		Loggers.w("deleteByLocal_id", local_id);
		return this.mDb.delete(SQLITE_TABLE, Tripdata.KEY_TRIP_LOCAL_ID + "="
				+ local_id, null);
	}

	public static ArrayList<Tripdata> readAllTrip(Context context) {
		ArrayList<Tripdata> data = new ArrayList<>();
		TripDataAdapter db = new TripDataAdapter(context);
		db.createDatabase();
		db.open();
		Cursor cursor;
		cursor = db.fetchALL();
		if (cursor != null && cursor.moveToFirst())
			do {
				Tripdata trip = new Tripdata();
				trip.setRow_id(cursor.getInt(cursor
						.getColumnIndexOrThrow(Tripdata.KEY_TRIP_ROW_ID)));
				trip.setLocal_id(cursor.getInt(cursor
						.getColumnIndexOrThrow(Tripdata.KEY_TRIP_LOCAL_ID)));
				trip.setUser(UserDataSP.get(context));
				trip.setTitle(cursor.getString(cursor
						.getColumnIndexOrThrow(Tripdata.KEY_TRIP_TITLE)));
				Loggers.w(
						"readAllTrip",
						"Tripdata.KEY_TRIP_MOTOR_ID "
								+ cursor.getInt(cursor
										.getColumnIndexOrThrow(Tripdata.KEY_TRIP_MOTOR_ID)));
				trip.setMotor(MotorDataAdapter.readMotorByLocalId(
						context,
						cursor.getInt(cursor
								.getColumnIndexOrThrow(Tripdata.KEY_TRIP_MOTOR_ID))));
				trip.setAddrss_start(cursor.getString(cursor
						.getColumnIndexOrThrow(Tripdata.KEY_TRIP_ADDRSS_START)));
				trip.setAddrss_end(cursor.getString(cursor
						.getColumnIndexOrThrow(Tripdata.KEY_TRIP_ADDRSS_END)));
				trip.setTime_start(cursor.getLong(cursor
						.getColumnIndexOrThrow(Tripdata.KEY_TRIP_TIME_START)));
				trip.setTotal_time(cursor.getLong(cursor
						.getColumnIndexOrThrow(Tripdata.KEY_TRIP_TOTAL_TIME)));
				trip.setEco_fuel(cursor.getDouble(cursor
						.getColumnIndexOrThrow(Tripdata.KEY_TRIP_ECO_FUEL)));
				trip.setNon_eco_fuel(cursor.getDouble(cursor
						.getColumnIndexOrThrow(Tripdata.KEY_TRIP_NON_ECO_FUEL)));
				trip.setEco_distance(cursor.getDouble(cursor
						.getColumnIndexOrThrow(Tripdata.KEY_TRIP_ECO_DISTANCE)));
				trip.setNon_eco_distance(cursor.getDouble(cursor
						.getColumnIndexOrThrow(Tripdata.KEY_TRIP_NON_ECO_DISTANCE)));
				trip.setGraph_time(cursor.getString(cursor
						.getColumnIndexOrThrow(Tripdata.KEY_TRIP_GRAPH_TIME)));
				trip.setStatus(cursor.getInt(cursor
						.getColumnIndexOrThrow(Tripdata.KEY_TRIP_STATUS)));
				trip.setSaved(cursor.getInt(cursor
						.getColumnIndexOrThrow(Tripdata.KEY_TRIP_USER_SAVE)) > 0);
				data.add(trip);
			} while (cursor.moveToNext());
		db.close();
		return data;
	}

	public static Tripdata readTripByLocalId(Context context, int local_id) {
		Tripdata trip = null;
		TripDataAdapter db = new TripDataAdapter(context);
		db.createDatabase();
		db.open();
		Cursor cursor;
		cursor = db.fetchByLocalId(local_id);
		if (cursor != null && cursor.moveToFirst()) {
			trip = new Tripdata();
			trip.setRow_id(cursor.getInt(cursor
					.getColumnIndexOrThrow(Tripdata.KEY_TRIP_ROW_ID)));
			trip.setLocal_id(cursor.getInt(cursor
					.getColumnIndexOrThrow(Tripdata.KEY_TRIP_LOCAL_ID)));
			trip.setUser(UserDataSP.get(context));
			trip.setTitle(cursor.getString(cursor
					.getColumnIndexOrThrow(Tripdata.KEY_TRIP_TITLE)));
			trip.setMotor(MotorDataAdapter.readMotorByLocalId(context, cursor
					.getInt(cursor
							.getColumnIndexOrThrow(Tripdata.KEY_TRIP_MOTOR_ID))));
			trip.setAddrss_start(cursor.getString(cursor
					.getColumnIndexOrThrow(Tripdata.KEY_TRIP_ADDRSS_START)));
			trip.setAddrss_end(cursor.getString(cursor
					.getColumnIndexOrThrow(Tripdata.KEY_TRIP_ADDRSS_END)));
			trip.setTime_start(cursor.getLong(cursor
					.getColumnIndexOrThrow(Tripdata.KEY_TRIP_TIME_START)));
			trip.setTotal_time(cursor.getLong(cursor
					.getColumnIndexOrThrow(Tripdata.KEY_TRIP_TOTAL_TIME)));
			trip.setEco_fuel(cursor.getDouble(cursor
					.getColumnIndexOrThrow(Tripdata.KEY_TRIP_ECO_FUEL)));
			trip.setNon_eco_fuel(cursor.getDouble(cursor
					.getColumnIndexOrThrow(Tripdata.KEY_TRIP_NON_ECO_FUEL)));
			trip.setEco_distance(cursor.getDouble(cursor
					.getColumnIndexOrThrow(Tripdata.KEY_TRIP_ECO_DISTANCE)));
			trip.setNon_eco_distance(cursor.getDouble(cursor
					.getColumnIndexOrThrow(Tripdata.KEY_TRIP_NON_ECO_DISTANCE)));
			trip.setGraph_time(cursor.getString(cursor
					.getColumnIndexOrThrow(Tripdata.KEY_TRIP_GRAPH_TIME)));
			trip.setStatus(cursor.getInt(cursor
					.getColumnIndexOrThrow(Tripdata.KEY_TRIP_STATUS)));
			trip.setSaved(cursor.getInt(cursor
					.getColumnIndexOrThrow(Tripdata.KEY_TRIP_USER_SAVE)) > 0);
		}
		db.close();
		return trip;
	}

	public static long insertTrip(Context context, Tripdata data) {
		TripDataAdapter db = new TripDataAdapter(context);
		db.createDatabase();
		db.open();
		long l = db.insertTrip(data.getRow_id(), data.getUser_id(), data
				.getMotor().getLocal_id(), data.getTitle(), data
				.getAddrss_start(), data.getAddrss_end(), data.getTime_start(),
				data.getTotal_time(), data.getEco_fuel(), data
						.getNon_eco_fuel(), data.getEco_distance(), data
						.getNon_eco_distance(), data.getGraph_time(), data
						.getStatus());
		db.close();
		return l;
	}

	public static long updateTrip(Context context, Tripdata data) {
		TripDataAdapter db = new TripDataAdapter(context);
		db.createDatabase();
		db.open();
		long l = db.updateTrip(data.getLocal_id(), data.getRow_id(),
				data.getUser_id(), data.getMotor().getLocal_id(),
				data.getTitle(), data.getAddrss_start(), data.getAddrss_end(),
				data.getTime_start(), data.getTotal_time(), data.getEco_fuel(),
				data.getNon_eco_fuel(), data.getEco_distance(),
				data.getNon_eco_distance(), data.getGraph_time(),
				data.getStatus(), data.isSaved());
		db.close();
		return l;
	}

	public static long deleteById(Context context, Tripdata data) {
		TripDataAdapter db = new TripDataAdapter(context);
		db.createDatabase();
		db.open();
		long l = db.deleteByLocal_id(data.getLocal_id());
		db.close();
		DataLogAdapter.deleteLogByTripId(context, data.getLocal_id());
		return l;
	}
}
