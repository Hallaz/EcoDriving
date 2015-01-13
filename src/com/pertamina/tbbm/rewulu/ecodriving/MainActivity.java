/*
 * @author Hallaz ~ hallaz.ibnu@gmail.com
 * 
 * Please do not modify without any agreement between end user and the author.
 * 
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.pertamina.tbbm.rewulu.ecodriving;

import java.util.ArrayList;
import java.util.List;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.gms.maps.MapFragment;
import com.pertamina.tbbm.rewulu.ecodriving.controllers.ImagesManager;
import com.pertamina.tbbm.rewulu.ecodriving.databases.MotorDataAdapter;
import com.pertamina.tbbm.rewulu.ecodriving.databases.sps.UserDataSP;
import com.pertamina.tbbm.rewulu.ecodriving.fragment.AboutFragment;
import com.pertamina.tbbm.rewulu.ecodriving.fragment.Backstack;
import com.pertamina.tbbm.rewulu.ecodriving.fragment.HistoriesFragment;
import com.pertamina.tbbm.rewulu.ecodriving.fragment.MainMenuFragment;
import com.pertamina.tbbm.rewulu.ecodriving.fragment.MainTrackingFragment;
import com.pertamina.tbbm.rewulu.ecodriving.fragment.ResultFragment;
import com.pertamina.tbbm.rewulu.ecodriving.fragment.SplashScreenFragment;
import com.pertamina.tbbm.rewulu.ecodriving.helpers.TrackingTest;
import com.pertamina.tbbm.rewulu.ecodriving.listener.OnLayangCallback;
import com.pertamina.tbbm.rewulu.ecodriving.listener.OnMainListener;
import com.pertamina.tbbm.rewulu.ecodriving.pojos.DataLog;
import com.pertamina.tbbm.rewulu.ecodriving.pojos.Motor;
import com.pertamina.tbbm.rewulu.ecodriving.pojos.Tripdata;
import com.pertamina.tbbm.rewulu.ecodriving.pojos.UserData;
import com.pertamina.tbbm.rewulu.ecodriving.services.Kuli;
import com.pertamina.tbbm.rewulu.ecodriving.services.Layang;
import com.pertamina.tbbm.rewulu.ecodriving.services.Layang.MyBinder;
import com.pertamina.tbbm.rewulu.ecodriving.utils.Loggers;
import com.pertamina.tbbm.rewulu.ecodriving.utils.Utils;

public class MainActivity extends FragmentActivity implements OnMainListener,
		OnLayangCallback {

	private boolean cdExit;
	private boolean onPause;

	private final int NOTIFICATION_ID = 23749843;
	private List<Motor> motors;
	private NotificationCompat.Builder notificationBuilder; //
	private NotificationManager notificationManager;
	private UserData user;
	private Layang layang;
	private ImagesManager imgsManager;
	private LinearLayout err_internet;
	private Intent intentKuli;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().hide();
		setContentView(R.layout.activity_main);
		Loggers.LOG = true;
		err_internet = (LinearLayout) findViewById(R.id.connecting_error);
		Bitmap largeIcon = BitmapFactory.decodeResource(getResources(),
				R.drawable.ic_eco);
		intentKuli = new Intent(getApplicationContext(), Kuli.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
				new Intent().setClass(this, MainActivity.class), 0);
		notificationBuilder = new NotificationCompat.Builder(this)
				.setLargeIcon(largeIcon).setSmallIcon(R.drawable.ic_eco)
				.setContentTitle(this.getString(R.string.app_name))
				.setContentText("Eco Meter is Running")
				.setContentIntent(pendingIntent).setOngoing(true);
		notificationManager = (NotificationManager) this
				.getSystemService(Context.NOTIFICATION_SERVICE);
		imgsManager = new ImagesManager(getApplicationContext());
		if (Utils.TrackingSP.isRunning(getApplicationContext()))
			Utils.toast(getApplicationContext(), R.string.err_app_killed);
		if (savedInstanceState == null) {
			Intent intent = getIntent();
			if (!intent.getBooleanExtra(ContentsActivity.FLAG, false)) {
				getFragmentManager().beginTransaction()
						.replace(R.id.container, new SplashScreenFragment())
						.commit();
			} else {
				goToMainMenu();
			}
			onStoppingLayang();
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (layang == null)
			bindService(new Intent().setClass(getApplicationContext(),
					Layang.class), serviceConnection, Context.BIND_AUTO_CREATE);
		onPause = false;
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		onPause = true;
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		layang.delete();
		unbindService(serviceConnection);
		removeNotif();
	}

	@Override
	public void startApp(UserData user) {
		// TODO Auto-generated method stub
		if (user != null) {
			this.user = user;
		} else {
			this.user = UserDataSP.get(getApplicationContext());
		}
		layang.register(this.user);
		goToMainMenu();
	}

	@Override
	public void upDateMotor(String email) {
		// TODO Auto-generated method stub
		if (layang == null)
			finish();
		layang.upDateMotor(motors, email);

	}

	@Override
	public void setDataMotor(List<Motor> motors) {
		// TODO Auto-generated method stub
		this.motors = motors;
	}

	@Override
	public void storeLog(DataLog log) {
		// TODO Auto-generated method stub
		layang.logData(log);
	}

	@Override
	public void startTrack(Tripdata tripdata) {
		// TODO Auto-generated method stub
		if (onPause)
			return;
		tripdata.setUser(user);
		layang.startTrip(tripdata);
		notif();
	}

	@Override
	public void startHistory() {
		// TODO Auto-generated method stub
		layang.startHistories();
	}

	@Override
	public void startResult(ArrayList<Integer> graphDataWaktu) {
		// TODO Auto-generated method stub
		layang.setGraphTime_trip(graphDataWaktu);
		layang.startResult();
	}

	@Override
	public void startResult(Tripdata trip) {
		// TODO Auto-generated method stub
		layang.startHistoryResult(trip);
	}

	@Override
	public void goToMainMenu() {
		// TODO Auto-generated method stub
		removeNotif();
		if (motors == null)
			motors = MotorDataAdapter.readAllMotor(getApplicationContext());
		if (onPause)
			return;
		mainmenuFragment = new MainMenuFragment();
		mainmenuFragment.setDataMotor(motors);
		getFragmentManager().beginTransaction()
				.replace(R.id.container, mainmenuFragment).commit();
	}

	private MainMenuFragment mainmenuFragment = new MainMenuFragment();

	@Override
	public void startHelp() {
		// TODO Auto-generated method stub
		getFragmentManager().beginTransaction()
				.replace(R.id.container, new AboutFragment()).commit();
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		if (Backstack.isOnMaintracking()) {
			layang.delete();
		}
		if (Backstack.isOnResult()) {
			resultFragment.onBackPressed();
			return;
		}
		if (Backstack.isOnMainmenu()) {
			mainmenuFragment.onBackPressed();
			appExit();
		} else
			goToMainMenu();

	}

	private void appExit() {
		// TODO Auto-generated method stub
		if (!cdExit) {
			cdExit = true;
			new CountDownTimer(1500, 300) {

				@Override
				public void onTick(long millisUntilFinished) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onFinish() {
					// TODO Auto-generated method stub
					try {
						cdExit = false;
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
			}.start();
			Utils.toast(getApplicationContext(), R.string.exit_confirm);
		} else
			this.finish();
	}

	private void notif() {
		// TODO Auto-generated method stub
		notificationManager
				.notify(NOTIFICATION_ID, notificationBuilder.build());
		Utils.TrackingSP.setRunning(getApplicationContext(), true);
	}

	private void removeNotif() {
		// TODO Auto-generated method stub
		try {
			notificationManager.cancel(NOTIFICATION_ID);
			MapFragment fm = (MapFragment) getFragmentManager()
					.findFragmentById(R.id.map);
			if (fm != null)
				getFragmentManager().beginTransaction().remove(fm).commit();
		} catch (Exception e) {
			// TODO: handle exception

			e.printStackTrace();
		}
		Utils.TrackingSP.setRunning(getApplicationContext(), false);
	}

	@Override
	public void startMainMenu() {
		// TODO Auto-generated method stub
		goToMainMenu();
	}

	private ServiceConnection serviceConnection = new ServiceConnection() {

		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub
			layang = null;
		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			// TODO Auto-generated method stub
			layang = ((MyBinder) service).getService();
			layang.setOnCallback(MainActivity.this);
		}
	};

	@Override
	public void retrievingDataMotors(List<Motor> motors) {
		// TODO Auto-generated method stub
		this.motors = motors;
	}

	@Override
	public void registerResult(UserData userdata) {
		// TODO Auto-generated method stub
		this.user = userdata;
	}

	@Override
	public void requestedStartTrip(Tripdata trip) {
		// TODO Auto-generated method stub
		if (onPause) {
			//new TrackingTest(getApplicationContext(), trip, this);
			return;
		} else {
			mainTrackingFragment = new MainTrackingFragment();
			mainTrackingFragment.setData(trip);
			getFragmentManager().beginTransaction()
					.replace(R.id.container, mainTrackingFragment).commit();
		}
	}

	private MainTrackingFragment mainTrackingFragment = new MainTrackingFragment();

	@Override
	public void isInternetAvailable(boolean arg0) {
		// TODO Auto-generated method stub
		if (onPause)
			return;
		if (arg0)
			err_internet.setVisibility(View.GONE);
		else
			err_internet.setVisibility(View.VISIBLE);
	}

	@Override
	public void requestedStartResult(Tripdata trip, List<DataLog> logs) {
		// TODO Auto-generated method stub
		if (onPause) {
			return;
		}
		resultFragment = new ResultFragment();
		resultFragment.setData(trip, (ArrayList<DataLog>) logs, this);
		getFragmentManager().beginTransaction()
				.replace(R.id.container, resultFragment).commit();
		removeNotif();
	}

	private ResultFragment resultFragment = new ResultFragment();

	@Override
	public UserData getUser() {
		// TODO Auto-generated method stub
		return user;
	}

	@Override
	public void setDataTrip(double eco_fuel, double non_eco_fuel,
			double eco_distance, double non_eco_distance) {
		// TODO Auto-generated method stub
		layang.setDataTrip(eco_fuel, non_eco_fuel, eco_distance,
				non_eco_distance);
	}

	@Override
	public void requestedHistories(List<Tripdata> trips) {
		// TODO Auto-generated method stub
		historiesFragment = new HistoriesFragment();
		historiesFragment.setData(trips);
		getFragmentManager().beginTransaction()
				.replace(R.id.container, historiesFragment).commit();
	}

	private HistoriesFragment historiesFragment = new HistoriesFragment();

	@Override
	public void rqstResult(Tripdata trip, List<DataLog> logs) {
		// TODO Auto-generated method stub
		requestedStartResult(trip, logs);
	}

	@Override
	public void onStartingLayang() {
		// TODO Auto-generated method stub
		try {
			//stopService(intentKuli);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@Override
	public void onStoppingLayang() {
		// TODO Auto-generated method stub
		//startService(intentKuli);
	}

	@Override
	public void loadImageTo(Motor motor, ImageView view) {
		// TODO Auto-generated method stub
		imgsManager.viewInto(motor, view);
	}

}
