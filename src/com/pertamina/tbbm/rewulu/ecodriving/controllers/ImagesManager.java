package com.pertamina.tbbm.rewulu.ecodriving.controllers;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.pertamina.tbbm.rewulu.ecodriving.pojos.Motor;
import com.pertamina.tbbm.rewulu.ecodriving.utils.Loggers;

import android.app.Activity;
import android.os.Environment;

public class ImagesManager {
	private final String PATH;
	private final String IMAGES_MOTOR = "/motor/images/";
	private List<Motor> motors;
	private List<File> listFiles;

	public ImagesManager(Activity activity, List<Motor> motors) {
		// TODO Auto-generated constructor stub
		this.PATH = Environment.getExternalStorageDirectory().getAbsolutePath()
				.toString()
				+ "/Android/data/" + activity.getPackageName();
		setMotors(motors);
		checkDir();
	}

	public ImagesManager(Activity activity) {
		// TODO Auto-generated constructor stub
		this.PATH = Environment.getExternalStorageDirectory().getAbsolutePath()
				.toString()
				+ "/Android/data/" + activity.getPackageName();
		checkDir();
	}

	public void setMotors(List<Motor> motors) {
		this.motors = motors;
		listFiles = new ArrayList<>();
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

	public void syncImages() {
		if (motors == null) {
			Loggers.getInstance("ImagesManager");
			Loggers.w("syncImages", "motors == null");
			return;
		}
		List<Motor> unSyncedMotors = new ArrayList<Motor>();
		if (listFiles.isEmpty())
			unSyncedMotors = motors;
		else
			for (Motor mtr : motors) {
				if (!fileListContain(mtr.getImg_sample()))
					unSyncedMotors.add(mtr);
			}

	}

	private void downloadImage() {
		// TODO Auto-generated method stub

	}
}
