package com.pertamina.tbbm.rewulu.ecodriving.controllers;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.widget.ImageView;

import com.pertamina.tbbm.rewulu.ecodriving.pojos.Motor;
import com.pertamina.tbbm.rewulu.ecodriving.utils.Loggers;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Picasso.LoadedFrom;
import com.squareup.picasso.Target;

public class ImagesManager {
	private final String PATH;
	private final String IMAGES_MOTOR_PATH = "/motor/images/";
	private final String IMAGES_MOTOR_ABSOLUTEPATH;
	private List<Motor> motors;
	private List<File> listFiles;
	private Context context;

	public ImagesManager(Context context, List<Motor> motors) {
		// TODO Auto-generated constructor stub
		this.PATH = Environment.getExternalStorageDirectory().getAbsolutePath()
				.toString()
				+ "/Android/data/" + context.getPackageName();
		setMotors(motors);
		this.context = context;
		checkDir();
		IMAGES_MOTOR_ABSOLUTEPATH = PATH + IMAGES_MOTOR_PATH;
	}

	public ImagesManager(Context context) {
		// TODO Auto-generated constructor stub
		this.PATH = Environment.getExternalStorageDirectory().getAbsolutePath()
				.toString()
				+ "/Android/data/" + context.getPackageName();
		this.context = context;
		checkDir();
		IMAGES_MOTOR_ABSOLUTEPATH = PATH + IMAGES_MOTOR_PATH;
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
			file = new File(PATH + IMAGES_MOTOR_PATH);
			if (!file.exists())
				file.mkdir();
		} else {
			file = new File(PATH + IMAGES_MOTOR_PATH);
			if (!file.exists()) {
				file.mkdir();
			} else {
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

	public void cleanUnsignedFiles() {
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

	public void viewInto(Motor motor, ImageView view) {
		File file = null;
		if (listFiles != null)
			if (!listFiles.isEmpty())
				for (File fl : listFiles)
					if (fl.getName().equalsIgnoreCase(
							getImageName(motor.getImg_sample()))) {
						file = fl;
						break;
					}
		ctmTarget.setParam(motor, view);
		if (file != null) {
			Picasso.with(context).load(file).into(ctmTarget);
		} else {
			Picasso.with(context).load(motor.getImg_sample()).into(ctmTarget);
		}

	}

	private CustomeTarget ctmTarget = new CustomeTarget();

	private class CustomeTarget implements Target {
		public CustomeTarget() {
			// TODO Auto-generated constructor stub
		}

		private Motor motor;
		private ImageView view;

		public void setParam(Motor motor, ImageView view) {
			this.motor = motor;
			this.view = view;
		}

		@Override
		public void onBitmapFailed(Drawable arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onBitmapLoaded(final Bitmap arg0, LoadedFrom arg1) {
			// TODO Auto-generated method stub
			Loggers.getInstance("ImagesManager");
			Loggers.i("target", "LoadedFrom " + arg1.toString());
			if (arg1.equals(Picasso.LoadedFrom.NETWORK))
				new Thread(new Runnable() {
					@Override
					public void run() {

						File file = new File(IMAGES_MOTOR_ABSOLUTEPATH
								+ getImageName(motor.getImg_sample()));
						try {
							file.createNewFile();
							FileOutputStream ostream = new FileOutputStream(
									file);
							arg0.compress(CompressFormat.JPEG, 99, ostream);
							ostream.close();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}).start();
			if (this.view != null)
				this.view.setImageBitmap(arg0);
		}

		@Override
		public void onPrepareLoad(Drawable arg0) {
			// TODO Auto-generated method stub

		}

	}

}
