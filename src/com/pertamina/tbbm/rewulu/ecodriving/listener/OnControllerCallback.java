package com.pertamina.tbbm.rewulu.ecodriving.listener;

import java.util.List;

import com.pertamina.tbbm.rewulu.ecodriving.clients.LogsClient.ResponseLogs;
import com.pertamina.tbbm.rewulu.ecodriving.clients.TripClient.ResponseData;
import com.pertamina.tbbm.rewulu.ecodriving.pojos.Motor;
import com.pertamina.tbbm.rewulu.ecodriving.pojos.Tripdata;
import com.pertamina.tbbm.rewulu.ecodriving.pojos.UserData;

public interface OnControllerCallback {

	void retrievingDataMotors(List<Motor> rst, List<Motor> motors);

	void registerResult(UserData result);

	void onStartAddressResult(String result);

	void onDeletedTrip(Tripdata result);

	void onLoggingResult(ResponseLogs result);

	void onUpdateTripResult(ResponseData result);

	void onTripResult(Tripdata result);

	void onEndAddressResult(String result);

	void requestSession();

}
