package com.pertamina.tbbm.rewulu.ecodriving.controllers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;

import com.pertamina.tbbm.rewulu.ecodriving.clients.ImagesClient;
import com.pertamina.tbbm.rewulu.ecodriving.pojos.Motor;
import com.pertamina.tbbm.rewulu.ecodriving.utils.Loggers;

public class ImagesManager {
	private final String PATH;
	private final String IMAGES_MOTOR = "/motor/images/";
	private List<Motor> motors;
	private List<File> listFiles;

	public ImagesManager(Context context, List<Motor> motors) {
		// TODO Auto-generated constructor stub
		this.PATH = Environment.getExternalStorageDirectory().getAbsolutePath()
				.toString()
				+ "/Android/data/" + context.getPackageName();
		setMotors(motors);
		checkDir();
	}

	public ImagesManager(Context context) {
		// TODO Auto-generated constructor stub
		this.PATH = Environment.getExternalStorageDirectory().getAbsolutePath()
				.toString()
				+ "/Android/data/" + context.getPackageName();
		checkDir();
	}

	public void Destroy() {
		downloader.cancel(true);
	}

	public void setMotors(List<Motor> motors) {
		this.motors = motors;
		listFiles = new ArrayList<>();
		Destroy();
	}

	private void checkDir() {
		// TODO Auto-generated method stub
		File file = new File(PATH);
		if (!file.exists()) {
			file.mkdir();
			file = new File(PATH + IMAGES_MOTOR);
			if (!file.exists())
				file.mkdir();
		} else {
			file = new File(PATH + IMAGES_MOTOR);
			if (!file.exists())
				file.mkdir();
			else {
				if (file.listFiles() != null)
					listFiles = Arrays.asList(file.listFiles());
			}
		}
	}

	public static String getImageName(String img_sample) {
		if (img_sample == null) {
			Loggers.getInstance("ImagesManager");
			Loggers.w("getImageName", "img_sample == null");
			return null;
		}
		String[] samp = img_sample.split("/");
		return samp[samp.length - 1];
	}

	private boolean fileListContain(String filename) {
		if (listFiles.isEmpty())
			return false;
		for (File file : listFiles)
			if (file.getName().equalsIgnoreCase(filename)) {
				return true;
			}
		return false;
	}

	private void cleanUnsignedFiles() {
		// TODO Auto-generated method stub
		List<File> temp = new ArrayList<>();
		for (Motor motor : motors)
			for (File file : listFiles)
				if (file.getName().equalsIgnoreCase(
						getImageName(motor.getImg_sample())))
					temp.add(file);
		for (int w = 0; w < listFiles.size(); w++)
			if (!temp.contains(listFiles.get(w))) {
				listFiles.remove(w);
				w -= 1;
			}
	}

	public void syncImages() {
		if (motors == null) {
			Loggers.getInstance("ImagesManager");
			Loggers.w("syncImages", "motors == null");
			return;
		}
		List<Motor> unSyncedMotors = new ArrayList<Motor>();
		cleanUnsignedFiles();
		if (listFiles.isEmpty())
			unSyncedMotors = motors;
		else
			for (Motor mtr : motors) {
				if (!fileListContain(mtr.getImg_sample()))
					unSyncedMotors.add(mtr);
			}
		motors = unSyncedMotors;
		downloadImage();
	}

	private Downloader downloader = new Downloader();

	private class Downloader extends AsyncTask<Motor, Integer, Boolean> {
		@Override
		protected void onProgressUpdate(Integer... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
			if (values[0] != null) {
				for (int w = 0; w < motors.size(); w++)
					if (motors.get(w).getRow_id() == values[0]) {
						motors.remove(w);
						break;
					}
			}
		}

		@Override
		protected Boolean doInBackground(Motor... params) {
			// TODO Auto-generated method stub
			Motor motor = params[0];
			InputStream is = null;
			OutputStream os = null;
			Loggers.getInstance("ImagesManager");
			Loggers.i("Downloader",
					"try to download image " + motor.getImg_sample());
			try {
				is = ImagesClient.download(motor.getImg_sample());
				os = new FileOutputStream(getImageName(motor.getImg_sample()));

				byte[] b = new byte[2048];
				int length;

				while ((length = is.read(b)) != -1) {
					os.write(b, 0, length);
				}
			} catch (Exception e) {
				// TODO: handle exception
				Loggers.e("Downloader", e.toString());
				try {
					if (is != null)
						is.close();
					if (os != null)
						os.close();

				} catch (Exception e1) {
					Loggers.e("Downloader", "Failed to close ");
				}
			} finally {
				try {
					if (is != null)
						is.close();
					if (os != null)
						os.close();

				} catch (Exception e) {
					Loggers.e("Downloader - Finally", "Failed to close");
				}
			}
			publishProgress(motor.getRow_id());
			return true;
		}
	}

	private void downloadImage() {
		// TODO Auto-generated method stub

		if (!downloader.getStatus().equals(AsyncTask.Status.RUNNING)) {
			downloader = new Downloader();
			Motor[] mtrs = new Motor[motors.size()];
			mtrs = motors.toArray(mtrs);
			downloader.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, mtrs);
		}
	}
}
