package com.pertamina.tbbm.rewulu.ecodriving.databases;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.pertamina.tbbm.rewulu.ecodriving.helpers.DataBaseHelper;
import com.pertamina.tbbm.rewulu.ecodriving.pojos.Motor;

public class MotorDataAdapter {

	private DataBaseHelper mDbHelper;
	private SQLiteDatabase mDb;
	private static final String SQLITE_TABLE = "t_motor";
	private final Context context;

	private MotorDataAdapter(Context context) {
		this.context = context;
		this.mDbHelper = new DataBaseHelper(this.context);
	}

	private MotorDataAdapter open() throws SQLException {
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

	private MotorDataAdapter createDatabase() throws SQLException {
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
				Motor.KEY_MOTOR_ROW_ID, Motor.KEY_MOTOR_LOCAL_ID,
				Motor.KEY_MOTOR_SAMPLE, Motor.KEY_MOTOR_MAX_FUEL,
				Motor.KEY_MOTOR_ECO_FUELAGE, Motor.KEY_MOTOR_NON_ECO_FUELAGE,
				Motor.KEY_MOTOR_MAX_SPEED_ECO, Motor.KEY_MOTOR_PSSTV_ACC,
				Motor.KEY_MOTOR_NGTV_ACC, Motor.KEY_MOTOR_IMG_SAMPLE,
				Motor.KEY_MOTOR_IMG, Motor.KEY_MOTOR_CREATED_AT, }, null, null,
				null, null, null);
		if (cursor != null) {
			cursor.moveToFirst();
		}
		return cursor;
	}

	private Cursor fetchById(int local_id) {
		Cursor cursor = this.mDb.query(SQLITE_TABLE, new String[] {
				Motor.KEY_MOTOR_ROW_ID, Motor.KEY_MOTOR_LOCAL_ID,
				Motor.KEY_MOTOR_SAMPLE, Motor.KEY_MOTOR_MAX_FUEL,
				Motor.KEY_MOTOR_ECO_FUELAGE, Motor.KEY_MOTOR_NON_ECO_FUELAGE,
				Motor.KEY_MOTOR_MAX_SPEED_ECO, Motor.KEY_MOTOR_PSSTV_ACC,
				Motor.KEY_MOTOR_NGTV_ACC, Motor.KEY_MOTOR_IMG_SAMPLE,
				Motor.KEY_MOTOR_IMG, Motor.KEY_MOTOR_CREATED_AT, },
				Motor.KEY_MOTOR_LOCAL_ID + " = '" + local_id + "'", null,
				null, null, null);
		if (cursor != null) {
			cursor.moveToFirst();
		}
		return cursor;
	}

	private Cursor fetchLast() {
		Cursor cursor = this.mDb.query(SQLITE_TABLE, new String[] {
				Motor.KEY_MOTOR_ROW_ID, Motor.KEY_MOTOR_CREATED_AT }, null,
				null, null, null, Motor.KEY_MOTOR_CREATED_AT + " DESC", "1");
		if (cursor != null)
			cursor.moveToFirst();
		return cursor;
	}

	private long insertMotor(int row_id, String sample, double max_fuel,
			double eco_fuelage, double non_eco_fuelage, int max_speed_eco,
			double psstv_acc, double ngtv_acc, String img_sample, String img,
			String created_at) {
		ContentValues cv = new ContentValues();
		cv.put(Motor.KEY_MOTOR_ROW_ID, row_id);
		cv.put(Motor.KEY_MOTOR_SAMPLE, sample);
		cv.put(Motor.KEY_MOTOR_MAX_FUEL, max_fuel);
		cv.put(Motor.KEY_MOTOR_ECO_FUELAGE, eco_fuelage);
		cv.put(Motor.KEY_MOTOR_NON_ECO_FUELAGE, non_eco_fuelage);
		cv.put(Motor.KEY_MOTOR_MAX_SPEED_ECO, max_speed_eco);
		cv.put(Motor.KEY_MOTOR_PSSTV_ACC, psstv_acc);
		cv.put(Motor.KEY_MOTOR_NGTV_ACC, ngtv_acc);
		cv.put(Motor.KEY_MOTOR_IMG_SAMPLE, img_sample);
		cv.put(Motor.KEY_MOTOR_IMG, img);
		cv.put(Motor.KEY_MOTOR_CREATED_AT, created_at);
		return this.mDb.insert(SQLITE_TABLE, null, cv);
	}

	private long updateMotor(int local_id, int row_id, String sample,
			double max_fuel, double eco_fuelage, double non_eco_fuelage,
			int max_speed_eco, double psstv_acc, double ngtv_acc,
			String img_sample, String img, String created_at) {
		ContentValues cv = new ContentValues();
		cv.put(Motor.KEY_MOTOR_ROW_ID, row_id);
		cv.put(Motor.KEY_MOTOR_SAMPLE, sample);
		cv.put(Motor.KEY_MOTOR_MAX_FUEL, max_fuel);
		cv.put(Motor.KEY_MOTOR_ECO_FUELAGE, eco_fuelage);
		cv.put(Motor.KEY_MOTOR_NON_ECO_FUELAGE, non_eco_fuelage);
		cv.put(Motor.KEY_MOTOR_MAX_SPEED_ECO, max_speed_eco);
		cv.put(Motor.KEY_MOTOR_PSSTV_ACC, psstv_acc);
		cv.put(Motor.KEY_MOTOR_NGTV_ACC, ngtv_acc);
		cv.put(Motor.KEY_MOTOR_IMG_SAMPLE, img_sample);
		cv.put(Motor.KEY_MOTOR_IMG, img);
		cv.put(Motor.KEY_MOTOR_CREATED_AT, created_at);
		return this.mDb.update(SQLITE_TABLE, cv, Motor.KEY_MOTOR_LOCAL_ID + "="
				+ local_id, null);
	}

	private long deleteMotor(int local_id) {
		return this.mDb.delete(SQLITE_TABLE, Motor.KEY_MOTOR_LOCAL_ID + "="
				+ local_id, null);
	}

	public static String getLastdStringDate(Context context) {
		String date = null;
		MotorDataAdapter db = new MotorDataAdapter(context);
		db.createDatabase();
		db.open();
		Cursor cursor = db.fetchLast();
		if (cursor != null && cursor.moveToFirst()) {
			date = cursor.getString(cursor
					.getColumnIndexOrThrow(Motor.KEY_MOTOR_CREATED_AT));
		}
		db.close();
		return date;
	}

	public static ArrayList<Motor> readAllMotor(Context context) {
		ArrayList<Motor> data = new ArrayList<>();
		MotorDataAdapter db = new MotorDataAdapter(context);
		db.createDatabase();
		db.open();
		Cursor cursor;
		cursor = db.fetchALL();
		if (cursor != null && cursor.moveToFirst())
			do {
				Motor mtr = new Motor();
				mtr.setRow_id(cursor.getInt(cursor
						.getColumnIndexOrThrow(Motor.KEY_MOTOR_ROW_ID)));
				mtr.setLocal_id(cursor.getInt(cursor
						.getColumnIndexOrThrow(Motor.KEY_MOTOR_LOCAL_ID)));
				mtr.setSample(cursor.getString(cursor
						.getColumnIndexOrThrow(Motor.KEY_MOTOR_SAMPLE)));
				mtr.setMax_fuel(cursor.getDouble(cursor
						.getColumnIndexOrThrow(Motor.KEY_MOTOR_MAX_FUEL)));
				mtr.setEco_fuelage(cursor.getDouble(cursor
						.getColumnIndexOrThrow(Motor.KEY_MOTOR_ECO_FUELAGE)));
				mtr.setNon_eco_fuelage(cursor.getDouble(cursor
						.getColumnIndexOrThrow(Motor.KEY_MOTOR_NON_ECO_FUELAGE)));
				mtr.setMax_speed_eco(cursor.getInt(cursor
						.getColumnIndexOrThrow(Motor.KEY_MOTOR_MAX_SPEED_ECO)));
				mtr.setPsstv_acc(cursor.getDouble(cursor
						.getColumnIndexOrThrow(Motor.KEY_MOTOR_PSSTV_ACC)));
				mtr.setNgtv_acc(cursor.getDouble(cursor
						.getColumnIndexOrThrow(Motor.KEY_MOTOR_NGTV_ACC)));
				mtr.setImg_sample(cursor.getString(cursor
						.getColumnIndexOrThrow(Motor.KEY_MOTOR_IMG_SAMPLE)));
				mtr.setImg(cursor.getString(cursor
						.getColumnIndexOrThrow(Motor.KEY_MOTOR_IMG)));
				mtr.setCreated_at(cursor.getString(cursor
						.getColumnIndexOrThrow(Motor.KEY_MOTOR_CREATED_AT)));
				data.add(mtr);
			} while (cursor.moveToNext());
		db.close();
		return data;
	}

	public static Motor readMotorByLocalId(Context context, int local_id) {
		Motor mtr = null;
		MotorDataAdapter db = new MotorDataAdapter(context);
		db.createDatabase();
		db.open();
		Cursor cursor;
		cursor = db.fetchById(local_id);
		if (cursor != null && cursor.moveToFirst()) {
			mtr = new Motor();
			mtr.setRow_id(cursor.getInt(cursor
					.getColumnIndexOrThrow(Motor.KEY_MOTOR_ROW_ID)));
			mtr.setLocal_id(cursor.getInt(cursor
					.getColumnIndexOrThrow(Motor.KEY_MOTOR_LOCAL_ID)));
			mtr.setSample(cursor.getString(cursor
					.getColumnIndexOrThrow(Motor.KEY_MOTOR_SAMPLE)));
			mtr.setMax_fuel(cursor.getDouble(cursor
					.getColumnIndexOrThrow(Motor.KEY_MOTOR_MAX_FUEL)));
			mtr.setEco_fuelage(cursor.getDouble(cursor
					.getColumnIndexOrThrow(Motor.KEY_MOTOR_ECO_FUELAGE)));
			mtr.setNon_eco_fuelage(cursor.getDouble(cursor
					.getColumnIndexOrThrow(Motor.KEY_MOTOR_NON_ECO_FUELAGE)));
			mtr.setMax_speed_eco(cursor.getInt(cursor
					.getColumnIndexOrThrow(Motor.KEY_MOTOR_MAX_SPEED_ECO)));
			mtr.setPsstv_acc(cursor.getDouble(cursor
					.getColumnIndexOrThrow(Motor.KEY_MOTOR_PSSTV_ACC)));
			mtr.setNgtv_acc(cursor.getDouble(cursor
					.getColumnIndexOrThrow(Motor.KEY_MOTOR_NGTV_ACC)));
			mtr.setImg_sample(cursor.getString(cursor
					.getColumnIndexOrThrow(Motor.KEY_MOTOR_IMG_SAMPLE)));
			mtr.setImg(cursor.getString(cursor
					.getColumnIndexOrThrow(Motor.KEY_MOTOR_IMG)));
			mtr.setCreated_at(cursor.getString(cursor
					.getColumnIndexOrThrow(Motor.KEY_MOTOR_CREATED_AT)));
		}
		db.close();
		return mtr;
	}

	public static long insertMotor(Context context, Motor data) {
		MotorDataAdapter db = new MotorDataAdapter(context);
		db.createDatabase();
		db.open();
		long l = db.insertMotor(data.getRow_id(), data.getSample(),
				data.getMax_fuel(), data.getEco_fuelage(),
				data.getNon_eco_fuelage(), data.getMax_speed_eco(),
				data.getPsstv_acc(), data.getNgtv_acc(), data.getImg_sample(),
				data.getImg(), data.getCreated_at());
		db.close();
		return l;
	}

	public static long updateMotor(Context context, Motor data) {
		MotorDataAdapter db = new MotorDataAdapter(context);
		db.createDatabase();
		db.open();
		long l = db.updateMotor(data.getLocal_id(), data.getRow_id(),
				data.getSample(), data.getMax_fuel(), data.getEco_fuelage(),
				data.getNon_eco_fuelage(), data.getMax_speed_eco(),
				data.getPsstv_acc(), data.getNgtv_acc(), data.getImg_sample(),
				data.getImg(), data.getCreated_at());
		db.close();
		return l;
	}

	public static void deleteMotors(Context context, List<Motor> motors) {
		MotorDataAdapter db = new MotorDataAdapter(context);
		db.createDatabase();
		db.open();
		for (Motor mtr : motors)
			db.deleteMotor(mtr.getLocal_id());
		db.close();
	}
}
