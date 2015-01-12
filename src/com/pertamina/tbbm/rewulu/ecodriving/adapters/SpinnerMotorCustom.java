package com.pertamina.tbbm.rewulu.ecodriving.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.pertamina.tbbm.rewulu.ecodriving.R;
import com.pertamina.tbbm.rewulu.ecodriving.pojos.Motor;

public class SpinnerMotorCustom extends ArrayAdapter<Motor> {
	public SpinnerMotorCustom(Context context, int resource,
			List<Motor> motors2) {
		super(context, resource, motors2);
		// TODO Auto-generated constructor stub
		motors = motors2;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public SpinnerMotorCustom(Context context, int resource,
			List<Motor> objects, int gravity) {
		super(context, resource, objects);
		// TODO Auto-generated constructor stub
		motors = objects;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.gravity = gravity;
	}

	private List<Motor> motors;;
	private LayoutInflater inflater;
	private int gravity = -1;

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		return getCustomView(position, convertView, parent);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		return getCustomView(position, convertView, parent);
	}

	private View getCustomView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View rootView = inflater
				.inflate(R.layout.spinner_custom, parent, false);
		TextView tv = (TextView) rootView.findViewById(R.id.tv_spinner);
		tv.setText(motors.get(position).getSample());
		if (gravity != -1)
			tv.setGravity(gravity);
		return rootView;
	}
}
