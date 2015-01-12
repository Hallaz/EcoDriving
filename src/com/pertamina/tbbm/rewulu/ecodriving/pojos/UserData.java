package com.pertamina.tbbm.rewulu.ecodriving.pojos;

import java.util.HashMap;
import java.util.Map;

public class UserData {
	public static final String KEY_USER_ROWID = "row_id";
	public static final String KEY_USER_NAME = "name";
	public static final String KEY_USER_EMAIL = "email";
	public static final String KEY_USER_ADDRESS = "addrss";
	public static final String KEY_USER_CITY = "city";
	public static final String KEY_USER_DOB = "dob";
	public static final String KEY_USER_PHONE_MODEL = "phone_model";
	public static final String KEY_USER_OS = "os";
	public static final String KEY_USER_API_KEY = "api_key";

	private int row_id = -1;
	private String name;
	private String email;
	private String addrss;
	private String city;
	private String dob;
	private String phone_model;
	private String os;
	private String api_key;

	public UserData() {
		// TODO Auto-generated constructor stub
	}

	public Map<String, String> getParameterMap() {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<String, String>();
		map.put(UserData.KEY_USER_ROWID, String.valueOf(row_id));
		map.put(UserData.KEY_USER_NAME, name);
		map.put(UserData.KEY_USER_CITY, city);
		map.put(UserData.KEY_USER_DOB, dob);
		map.put(UserData.KEY_USER_EMAIL, email);
		map.put(UserData.KEY_USER_OS, os);
		map.put(UserData.KEY_USER_PHONE_MODEL, phone_model);
		map.put(UserData.KEY_USER_ADDRESS, addrss);
		return map;
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the addrss
	 */
	public String getAddrss() {
		return addrss;
	}

	/**
	 * @param addrss
	 *            the addrss to set
	 */
	public void setAddrss(String addrss) {
		this.addrss = addrss;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city
	 *            the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the dob
	 */
	public String getDob() {
		return dob;
	}

	/**
	 * @param dob
	 *            the dob to set
	 */
	public void setDob(String dob) {
		this.dob = dob;
	}

	/**
	 * @return the phone_model
	 */
	public String getPhone_model() {
		return phone_model;
	}

	/**
	 * @param phone_model
	 *            the phone_model to set
	 */
	public void setPhone_model(String phone_model) {
		this.phone_model = phone_model;
	}

	/**
	 * @return the os
	 */
	public String getOs() {
		return os;
	}

	/**
	 * @param os
	 *            the os to set
	 */
	public void setOs(String os) {
		this.os = os;
	}

	/**
	 * @return the api_key
	 */
	public String getApi_key() {
		return api_key;
	}

	/**
	 * @param api_key
	 *            the api_key to set
	 */
	public void setApi_key(String api_key) {
		this.api_key = api_key;
	}

}
