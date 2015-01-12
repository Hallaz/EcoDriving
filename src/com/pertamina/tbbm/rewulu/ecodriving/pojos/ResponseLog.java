package com.pertamina.tbbm.rewulu.ecodriving.pojos;

public class ResponseLog {
	public boolean error;
	public String message;
	public int id;
	public int row_id;

	public ResponseLog(boolean error, String message, int id, int row_id) {
		// TODO Auto-generated constructor stub
		this.error = error;
		this.message = message;
		this.id = id;
		this.row_id = row_id;
	}
}
