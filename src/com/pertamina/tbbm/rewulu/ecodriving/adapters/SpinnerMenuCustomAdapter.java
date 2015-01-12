package com.pertamina.tbbm.rewulu.ecodriving.adapters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.pertamina.tbbm.rewulu.ecodriving.R;

public class SpinnerMenuCustomAdapter extends
		ArrayAdapter<HashMap<String, String>> implements OnClickListener {
	public SpinnerMenuCustomAdapter(Context context, int resource,
			ArrayList<HashMap<String, String>> objects, final String KEY,
			onItemClickListener listener) {
		super(context, resource, objects);
		// TODO Auto-generated constructor stub
		data = objects;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.KEY = KEY;
		this.listener = listener;
	}

	public SpinnerMenuCustomAdapter(Context context, int resource,
			ArrayList<HashMap<String, String>> objects, final String KEY,
			int gravity, onItemClickListener listener) {
		super(context, resource, objects);
		// TODO Auto-generated constructor stub
		data = objects;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.KEY = KEY;
		this.gravity = gravity;
		this.listener = listener;
	}

	private final onItemClickListener listener;
	private final String KEY;
	private ArrayList<HashMap<String, String>> data;
	private LayoutInflater inflater;
	public static final String ITEM_POSITION = "item_position";
	private int gravity = -1;

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

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
		tv.setText(data.get(position).get(KEY));
		data.get(position).put(ITEM_POSITION, String.valueOf(position));
		if (gravity != -1)
			tv.setGravity(gravity);
		rootViews.put(position, rootView);
		rootView.setOnClickListener(this);
		return rootView;

	}
	private HashMap<Integer, View> rootViews = new HashMap<>();
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Iterator<Entry<Integer, View>> i = rootViews.entrySet().iterator();
		while (i.hasNext()) {
			Integer key = i.next().getKey();
			if(rootViews.get(key).equals(v))
				listener.onClick(key);
		}
	}

	public interface onItemClickListener {
		public void onClick(int arg0);
	}
}
