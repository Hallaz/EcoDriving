package com.pertamina.tbbm.rewulu.ecodriving.adapters;

import java.util.List;

import com.pertamina.tbbm.rewulu.ecodriving.R;
import com.pertamina.tbbm.rewulu.ecodriving.pojos.Tripdata;
import com.pertamina.tbbm.rewulu.ecodriving.utils.Utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CustomListHistory extends BaseAdapter {
	private static LayoutInflater inflater = null;
	private List<Tripdata> trips;

	public CustomListHistory(Context context, List<Tripdata> trips) {
		// TODO Auto-generated constructor stub
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.trips = trips;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return trips.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return trips.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return trips.get(position).getLocal_id();
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (view == null)
			view = inflater.inflate(R.layout.custom_history_listview, null);
		((TextView) view.findViewById(R.id.txt_time_list)).setText(Utils
				.dateFormatter(trips.get(position).getTime_start()));
		((TextView) view.findViewById(R.id.txt_title_list)).setText(trips.get(
				position).getTitle());
		((TextView) view.findViewById(R.id.txt_addrss_start_list))
				.setText(trips.get(position).getAddrss_start());
		((TextView) view.findViewById(R.id.txt_addrss_end_list)).setText(trips
				.get(position).getAddrss_end());
		((TextView) view.findViewById(R.id.txt_total_time_list)).setText(Utils
				.getDurationBreakdown(trips.get(position).getTotal_time()));

		return view;
	}

}
