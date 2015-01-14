package com.pertamina.tbbm.rewulu.ecodriving.helpers;

import java.util.ArrayList;
import java.util.List;

import com.pertamina.tbbm.rewulu.ecodriving.pojos.DataLog;
import com.pertamina.tbbm.rewulu.ecodriving.pojos.Tripdata;
import com.pertamina.tbbm.rewulu.ecodriving.utils.Loggers;
import com.pertamina.tbbm.rewulu.ecodriving.utils.Utils;

public class ResultData {
	private Tripdata tripdata;
	private List<DataLog> dataLogs;
	private double totalDistance = 0d;
	private int avSpeed = 0;
	private double totalTripFuel = 0d;
	private double remainingFuel = 0d;
	private double ecoDistance = 0d;
	private double ecoFuel = 0d;
	private double vice_ecoFuel = 0d;
	private double save_ecoFuel = 0d;
	private double nonEcoDistance = 0d;
	private double nonEcoFuel = 0d;
	private double vice_nonEcoFuel = 0d;
	private double boros_nonEcoFuel = 0d;
	private int time = 0;

	private ArrayList<Integer> graphDataWaktu;
	private ArrayList<Integer> graphDataSpeed;
	private ArrayList<Double> distanceWaktu;
	private ArrayList<Double> distanceSpeed;
	private double distancePivot = 1d;
	private double timePivot = -1;

	public ResultData(Tripdata tripdata, List<DataLog> dataLogs) {
		// TODO Auto-generated constructor stub
		this.tripdata = tripdata;
		this.dataLogs = dataLogs;

		this.graphDataWaktu = new ArrayList<>();
		this.graphDataSpeed = new ArrayList<>();
		this.distanceSpeed = new ArrayList<>();
		this.distanceWaktu = new ArrayList<>();
		this.graphDataWaktu.add(0);
		this.distanceWaktu.add(0d);

		buildData();
	}
	
	private void buildData() {

		// TODO Auto-generated method stub
		double ecoFuelAgeData = tripdata.getMotor().getEco_fuelage();
		double nonEcoFuelAgeData = tripdata.getMotor().getNon_eco_fuelage();
		// ==========
		for (DataLog log : dataLogs) {
			avSpeed += log.getSpeed();
			totalDistance += log.getDistance();
			totalTripFuel += (log.getFuel_age());
			if (log.getDrive_state()) {
				ecoDistance += log.getDistance();
				ecoFuel += (log.getFuel_age());
			} else {
				nonEcoDistance += log.getDistance();
				nonEcoFuel += (log.getFuel_age());
			}

			int sspeed = log.getSpeed();
			if (sspeed != 0) {
				graphDataSpeed.add(sspeed);
				distanceSpeed.add(totalDistance);
			}

			if (timePivot < 0)
				timePivot = log.getTime();
			else {
				time += (log.getTime() - timePivot) / 1000;
				timePivot = log.getTime();
			}
			if (totalDistance > distancePivot) {
				graphDataWaktu.add(time);
				distanceWaktu.add(totalDistance);
				distancePivot = totalDistance + 1;
				time = 0;
			}

		}

		graphDataWaktu.add(time);
		distanceWaktu.add(totalDistance);
		// ecoFuel = Utils.doubleDecimalFormater(ecoFuel);
		// nonEcoFuel = Utils.doubleDecimalFormater(nonEcoFuel);
		Loggers.d("ecoFuelAgeData", ecoFuelAgeData);
		Loggers.d("nonEcoFuelAgeData", nonEcoFuelAgeData);
		remainingFuel = (dataLogs.get(dataLogs.size() - 1).getFuel());
		avSpeed = avSpeed / dataLogs.size();
		vice_ecoFuel = 1 / nonEcoFuelAgeData * ecoDistance;
		save_ecoFuel = vice_ecoFuel - ecoFuel;
		vice_nonEcoFuel = 1 / ecoFuelAgeData * nonEcoDistance;
		boros_nonEcoFuel = nonEcoFuel - vice_nonEcoFuel;
		totalDistance = ecoDistance + nonEcoDistance;
		Loggers.i(
				"ResultFragment",
				"ecoFuel "
						+ ecoFuel
						+ " nonEcoFuel "
						+ nonEcoFuel
						+ " ecoDistance "
						+ ecoDistance
						+ " nonEcoDistance "
						+ nonEcoDistance
						+ " time "
						+ Utils.getDurationBreakdown(dataLogs.get(
								dataLogs.size() - 1).getTime()
								- dataLogs.get(0).getTime()));

	}
	
	/**
	 * @return the totalDistance
	 */
	public double getTotalDistance() {
		return totalDistance;
	}

	/**
	 * @return the avSpeed
	 */
	public int getAvSpeed() {
		return avSpeed;
	}

	/**
	 * @return the totalTripFuel
	 */
	public double getTotalTripFuel() {
		return totalTripFuel;
	}

	/**
	 * @return the remainingFuel
	 */
	public double getRemainingFuel() {
		return remainingFuel;
	}

	/**
	 * @return the ecoDistance
	 */
	public double getEcoDistance() {
		return ecoDistance;
	}

	/**
	 * @return the ecoFuel
	 */
	public double getEcoFuel() {
		return ecoFuel;
	}

	/**
	 * @return the vice_ecoFuel
	 */
	public double getVice_ecoFuel() {
		return vice_ecoFuel;
	}

	/**
	 * @return the save_ecoFuel
	 */
	public double getSave_ecoFuel() {
		return save_ecoFuel;
	}

	/**
	 * @return the nonEcoDistance
	 */
	public double getNonEcoDistance() {
		return nonEcoDistance;
	}

	/**
	 * @return the nonEcoFuel
	 */
	public double getNonEcoFuel() {
		return nonEcoFuel;
	}

	/**
	 * @return the vice_nonEcoFuel
	 */
	public double getVice_nonEcoFuel() {
		return vice_nonEcoFuel;
	}

	/**
	 * @return the boros_nonEcoFuel
	 */
	public double getBoros_nonEcoFuel() {
		return boros_nonEcoFuel;
	}

	/**
	 * @return the time
	 */
	public int getTime() {
		return time;
	}

	/**
	 * @return the graphDataSpeed
	 */
	public ArrayList<Integer> getGraphDataSpeed() {
		return graphDataSpeed;
	}

	/**
	 * @return the distanceSpeed
	 */
	public ArrayList<Double> getDistanceSpeed() {
		return distanceSpeed;
	}

	/**
	 * @return the distancePivot
	 */
	public double getDistancePivot() {
		return distancePivot;
	}

	/**
	 * @return the timePivot
	 */
	public double getTimePivot() {
		return timePivot;
	}

	/**
	 * @return the tripdata
	 */
	public Tripdata getTripdata() {
		return tripdata;
	}

	/**
	 * @return the dataLogs
	 */
	public List<DataLog> getDataLogs() {
		return dataLogs;
	}

	/**
	 * @return the distanceWaktu
	 */
	public ArrayList<Double> getDistanceWaktu() {
		return distanceWaktu;
	}

	/**
	 * @return the graphDataWaktu
	 */
	public ArrayList<Integer> getGraphDataWaktu() {
		return graphDataWaktu;
	}

}
