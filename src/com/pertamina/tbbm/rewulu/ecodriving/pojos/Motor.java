package com.pertamina.tbbm.rewulu.ecodriving.pojos;

import com.pertamina.tbbm.rewulu.ecodriving.utils.Loggers;

public class Motor {

	private int row_id = -1;
	private int local_id = -1;
	private String sample = "";
	private double max_fuel;
	private double eco_fuelage;
	private double non_eco_fuelage;
	private int max_speed_eco;
	private double psstv_acc;
	private double ngtv_acc;
	private String img_sample = "";
	private String img = "";
	private String created_at = "";

	public void Log(String from) {
		if(!Loggers.LOG)
			return;
		android.util.Log.i("Motor", " ---LOGGING FROM --- " + from);
		android.util.Log.i("Motor", "row_id " + row_id);
		android.util.Log.i("Motor", "local_id " + local_id);
		android.util.Log.i("Motor", "sample " + sample);
		android.util.Log.i("Motor", "max_fuel " + max_fuel);
		android.util.Log.i("Motor", "eco_fuelage " + eco_fuelage);
		android.util.Log.i("Motor", "non_eco_fuelage " + non_eco_fuelage);
		android.util.Log.i("Motor", "max_speed_eco " + max_speed_eco);
		android.util.Log.i("Motor", "psstv_acc " + psstv_acc);
		android.util.Log.i("Motor", "ngtv_acc " + ngtv_acc);
		android.util.Log.i("Motor", "img_sample " + img_sample);
		android.util.Log.i("Motor", "img " + img);
		android.util.Log.i("Motor", "created_at " + created_at);
	}

	public static final String KEY_MOTOR_ROW_ID = "row_id";
	public static final String KEY_MOTOR_LOCAL_ID = "local_id";
	public static final String KEY_MOTOR_SAMPLE = "sample";
	public static final String KEY_MOTOR_MAX_FUEL = "max_fuel";
	public static final String KEY_MOTOR_ECO_FUELAGE = "eco_fuelage";
	public static final String KEY_MOTOR_NON_ECO_FUELAGE = "non_eco_fuelage";
	public static final String KEY_MOTOR_MAX_SPEED_ECO = "max_speed_eco";
	public static final String KEY_MOTOR_PSSTV_ACC = "psstv_acc";
	public static final String KEY_MOTOR_NGTV_ACC = "ngtv_acc";
	public static final String KEY_MOTOR_IMG_SAMPLE = "img_sample";
	public static final String KEY_MOTOR_IMG = "img";
	public static final String KEY_MOTOR_CREATED_AT = "created_at";

	public Motor() {
		// TODO Auto-generated constructor stub
	}

	public boolean equality(Motor motor) {
		if (sample == null || img_sample == null || img == null
				|| created_at == null)
			return true;
		if (motor.getSample() == null || motor.getImg_sample() == null
				|| motor.getCreated_at() == null)
			return true;
		if (sample.trim() != motor.getSample()
				|| max_fuel != motor.getMax_fuel()
				|| eco_fuelage != motor.getEco_fuelage()
				|| non_eco_fuelage != motor.getNon_eco_fuelage()
				|| max_speed_eco != motor.getMax_speed_eco()
				|| psstv_acc != motor.getPsstv_acc()
				|| ngtv_acc != motor.getNgtv_acc()
				|| img_sample != motor.getImg_sample()
				|| created_at != motor.getCreated_at())
			return false;
		return true;
	}

	/**
	 * @return the row_id
	 */
	public int getRow_id() {
		return row_id;
	}

	/**
	 * @param row_id
	 *            the row_id to set
	 */
	public void setRow_id(int row_id) {
		this.row_id = row_id;
	}

	/**
	 * @return the sample
	 */
	public String getSample() {
		return sample.trim();
	}

	/**
	 * @param sample
	 *            the sample to set
	 */
	public void setSample(String sample) {
		this.sample = sample.trim();
	}

	/**
	 * @return the max_fuel
	 */
	public double getMax_fuel() {
		return max_fuel;
	}

	/**
	 * @param max_fuel
	 *            the max_fuel to set
	 */
	public void setMax_fuel(double max_fuel) {
		this.max_fuel = max_fuel;
	}

	/**
	 * @return the eco_fuelage
	 */
	public double getEco_fuelage() {
		return eco_fuelage;
	}

	/**
	 * @param eco_fuelage
	 *            the eco_fuelage to set
	 */
	public void setEco_fuelage(double eco_fuelage) {
		this.eco_fuelage = eco_fuelage;
	}

	/**
	 * @return the non_eco_fuelage
	 */
	public double getNon_eco_fuelage() {
		return non_eco_fuelage;
	}

	/**
	 * @param non_eco_fuelage
	 *            the non_eco_fuelage to set
	 */
	public void setNon_eco_fuelage(double non_eco_fuelage) {
		this.non_eco_fuelage = non_eco_fuelage;
	}

	/**
	 * @return the max_speed_eco
	 */
	public int getMax_speed_eco() {
		return max_speed_eco;
	}

	/**
	 * @param max_speed_eco
	 *            the max_speed_eco to set
	 */
	public void setMax_speed_eco(int max_speed_eco) {
		this.max_speed_eco = max_speed_eco;
	}

	/**
	 * @return the psstv_acc
	 */
	public double getPsstv_acc() {
		return psstv_acc;
	}

	/**
	 * @param psstv_acc
	 *            the psstv_acc to set
	 */
	public void setPsstv_acc(double psstv_acc) {
		this.psstv_acc = psstv_acc;
	}

	/**
	 * @return the ngtv_acc
	 */
	public double getNgtv_acc() {
		return ngtv_acc;
	}

	/**
	 * @param ngtv_acc
	 *            the ngtv_acc to set
	 */
	public void setNgtv_acc(double ngtv_acc) {
		this.ngtv_acc = ngtv_acc;
	}

	/**
	 * @return the img_sample
	 */
	public String getImg_sample() {
		return img_sample.trim();
	}

	/**
	 * @param img_sample
	 *            the img_sample to set
	 */
	public void setImg_sample(String img_sample) {
		this.img_sample = img_sample.trim();
	}

	/**
	 * @return the img
	 */
	public String getImg() {
		if (img != null)
			return img.trim();
		return img;
	}

	/**
	 * @param img
	 *            the img to set
	 */
	public void setImg(String img) {
		if (img != null)
			this.img = img.trim();
	}

	/**
	 * @return the created_at
	 */
	public String getCreated_at() {
		return created_at.trim();
	}

	/**
	 * @param created_at
	 *            the created_at to set
	 */
	public void setCreated_at(String created_at) {
		this.created_at = created_at.trim();
	}

	/**
	 * @return the local_id
	 */
	public int getLocal_id() {
		return local_id;
	}

	/**
	 * @param local_id
	 *            the local_id to set
	 */
	public void setLocal_id(int local_id) {
		this.local_id = local_id;
	}

}
