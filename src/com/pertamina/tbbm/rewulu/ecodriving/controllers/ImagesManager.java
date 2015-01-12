package com.pertamina.tbbm.rewulu.ecodriving.controllers;

import java.io.File;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.widget.ImageView;

import com.pertamina.tbbm.rewulu.ecodriving.R;
import com.pertamina.tbbm.rewulu.ecodriving.pojos.Motor;
import com.pertamina.tbbm.rewulu.ecodriving.utils.Loggers;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Picasso.LoadedFrom;
import com.squareup.picasso.Target;

public class ImagesManager {
	private final String PATH;
	private final String IMAGES_MOTOR_PATH = "/motor/images/";
	private Context context;

	public ImagesManager(Context context) {
		// TODO Auto-generated constructor stub
		this.PATH = Environment.getExternalStorageDirectory().getAbsolutePath()
				.toString()
				+ "/Android/data/" + context.getPackageName();
		this.context = context;
		checkDir();
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

	public void viewInto(Motor motor, ImageView view) {
		Loggers.getInstance("ImagesManager");
		Loggers.i("viewInto",
				"motor " + motor.getSample() + " at " + motor.getImg_sample());
		Picasso.with(context).load(motor.getImg_sample())
				.error(R.drawable.default_motor).resize(72, 72)
				.centerCrop().into(new CustomeTarget(view));
	}

	private class CustomeTarget implements Target {
		public CustomeTarget(ImageView view) {
			// TODO Auto-generated constructor stub
			this.view = view;
		}

		private ImageView view;

		@Override
		public void onBitmapFailed(Drawable arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onBitmapLoaded(final Bitmap arg0, LoadedFrom arg1) {
			// TODO Auto-generated method stub
			Loggers.i("target", "LoadedFrom " + arg1.toString());
			if (this.view != null)
				this.view.setImageBitmap(arg0);
		}

		@Override
		public void onPrepareLoad(Drawable arg0) {
			// TODO Auto-generated method stub

		}

	}

}
