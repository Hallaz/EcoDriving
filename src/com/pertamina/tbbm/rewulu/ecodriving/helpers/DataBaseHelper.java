package com.pertamina.tbbm.rewulu.ecodriving.helpers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.pertamina.tbbm.rewulu.ecodriving.utils.Loggers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

@SuppressLint("SdCardPath")
public class DataBaseHelper extends SQLiteOpenHelper {
	// destination path (location) of our database on device
	private static String DB_PATH = "";
	private static final String DB_NAME = "pertamina.tbbm.rewulu.ecodriving.v1.sqlite";// Database
	// name
	private SQLiteDatabase mDataBase;
	private final Context mContext;

	public DataBaseHelper(Context context) {
		super(context, DB_NAME, null, 1);// 1? its Database Version
		if (android.os.Build.VERSION.SDK_INT >= 4.2) {
			DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
		} else {
			DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
		}
		this.mContext = context;
	}

	public void createDataBase() throws IOException {
		// If database not exists copy it from the assets
		boolean mDataBaseExist = checkDataBase();
		if (!mDataBaseExist) {
			this.getReadableDatabase();
			this.close();
			try {
				copyDataBase();
			} catch (IOException mIOException) {
				Loggers.getInstance("DataBaseHelper");
				Loggers.w("ErrorCopyingDataBase", mIOException.getMessage());
				throw new Error("ErrorCopyingDataBase" + mIOException);
			}
		}
	}

	private boolean checkDataBase() {
		File dbFile = new File(DB_PATH + DB_NAME);
		return dbFile.exists();
	}

	// Copy the database from assets
	private void copyDataBase() throws IOException {
		InputStream mInput = mContext.getAssets().open(DB_NAME);
		String outFileName = DB_PATH + DB_NAME;
		OutputStream mOutput = new FileOutputStream(outFileName);
		byte[] mBuffer = new byte[1024];
		int mLength;
		while ((mLength = mInput.read(mBuffer)) > 0) {
			mOutput.write(mBuffer, 0, mLength);
		}
		mOutput.flush();
		mOutput.close();
		mInput.close();
	}

	public boolean openDataBase() throws SQLException {
		String mPath = DB_PATH + DB_NAME;
		mDataBase = SQLiteDatabase.openDatabase(mPath, null,
				SQLiteDatabase.CREATE_IF_NECESSARY);
		if (!mDataBase.isReadOnly()) {
			mDataBase.execSQL("PRAGMA foreign_keys=ON;");
		}
		return mDataBase != null;
	}

	@Override
	public synchronized void close() {
		if (mDataBase != null)
			mDataBase.close();
		super.close();
	}

	@Override
	public void onCreate(SQLiteDatabase arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

	public static String getText(Cursor cursor, final String key) {
		if (cursor != null)
			return cursor.getString(cursor.getColumnIndex(key));
		return null;
	}

	public static byte[] getBlob(Cursor cursor, final String key) {
		if (cursor == null)
			return null;
		return cursor.getBlob(cursor.getColumnIndex(key));
	}
}
