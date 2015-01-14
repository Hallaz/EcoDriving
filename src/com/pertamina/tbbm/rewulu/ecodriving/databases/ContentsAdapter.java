package com.pertamina.tbbm.rewulu.ecodriving.databases;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.pertamina.tbbm.rewulu.ecodriving.helpers.DataBaseHelper;
import com.pertamina.tbbm.rewulu.ecodriving.utils.Enums.Type;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class ContentsAdapter {

	private DataBaseHelper mDbHelper;
	private SQLiteDatabase mDb;
	private static final String SQLITE_TABLE = "T_CONTENTS";
	private final Context context;

	private ContentsAdapter(Context context) {
		this.context = context;
		this.mDbHelper = new DataBaseHelper(this.context);
	}

	private ContentsAdapter open() throws SQLException {
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

	private ContentsAdapter createDatabase() throws SQLException {
		try {
			this.mDbHelper.createDataBase();
		} catch (IOException e) {
			e.printStackTrace();
			throw new Error("UnableToCreateDatabase");
		}
		return this;
	}

	public static final String KEY_CONTENT_ROWID = "row_id";
	public static final String KEY_CONTENT_JUDUL = "judul";
	public static final String KEY_CONTENT_CONTENT = "content";
	public static final String KEY_CONTENT_TYPE = "type";

	private Cursor fetchALL() {
		Cursor cursor = this.mDb.query(SQLITE_TABLE, new String[] {
				KEY_CONTENT_ROWID,KEY_CONTENT_JUDUL, KEY_CONTENT_CONTENT, KEY_CONTENT_TYPE, },
				null, null, null, null, null);
		if (cursor != null) {
			cursor.moveToFirst();
		}
		return cursor;
	}

	private Cursor fetchByType(Type type) {
		if (type == null)
			return null;
		Cursor cursor = this.mDb.query(SQLITE_TABLE, new String[] {
				KEY_CONTENT_ROWID,KEY_CONTENT_JUDUL, KEY_CONTENT_CONTENT, KEY_CONTENT_TYPE, },
				KEY_CONTENT_TYPE + " = '" + type.toString() + "'", null,
				null, null, null);
		if (cursor != null) {
			cursor.moveToFirst();
		}
		return cursor;
	}

	public static ArrayList<HashMap<String, String>> readContents(Context context,
			Type type) {
		ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String,String>>();
		ContentsAdapter db = new ContentsAdapter(context);
		db.createDatabase();
		db.open();
		Cursor cursor;
		if (type != null)
			cursor = db.fetchByType(type);
		else
			cursor = db.fetchALL();
		if (cursor.moveToFirst()) {
			do {
				HashMap<String, String> val = new HashMap<>();
				val.put(KEY_CONTENT_ROWID, cursor.getString(cursor
						.getColumnIndexOrThrow(KEY_CONTENT_ROWID)));
				val.put(KEY_CONTENT_JUDUL, cursor.getString(cursor
						.getColumnIndexOrThrow(KEY_CONTENT_JUDUL)));
				val.put(KEY_CONTENT_CONTENT, cursor.getString(cursor
						.getColumnIndexOrThrow(KEY_CONTENT_CONTENT)));
				val.put(KEY_CONTENT_TYPE, cursor.getString(cursor
						.getColumnIndexOrThrow(KEY_CONTENT_TYPE)));
				data.add(val);
			} while (cursor.moveToNext());
		}
		db.close();
		return data;
	}
	
}
