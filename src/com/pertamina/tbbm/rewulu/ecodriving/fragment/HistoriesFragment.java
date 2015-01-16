package com.pertamina.tbbm.rewulu.ecodriving.fragment;

import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageView;
import android.widget.ListView;

import com.pertamina.tbbm.rewulu.ecodriving.R;
import com.pertamina.tbbm.rewulu.ecodriving.adapters.CustomListHistory;
import com.pertamina.tbbm.rewulu.ecodriving.databases.MotorDataAdapter;
import com.pertamina.tbbm.rewulu.ecodriving.databases.TripDataAdapter;
import com.pertamina.tbbm.rewulu.ecodriving.dialogs.UserSingleConfrimDialog;
import com.pertamina.tbbm.rewulu.ecodriving.listener.OnDialogListener;
import com.pertamina.tbbm.rewulu.ecodriving.listener.OnMainListener;
import com.pertamina.tbbm.rewulu.ecodriving.pojos.Motor;
import com.pertamina.tbbm.rewulu.ecodriving.pojos.Tripdata;
import com.pertamina.tbbm.rewulu.ecodriving.utils.Loggers;

public class HistoriesFragment extends Fragment {
	private List<Tripdata> trips;
	private OnMainListener callback;
	private CustomListHistory adapter;
	private boolean onLongPressed;
	public void setData(List<Tripdata> trip) {
		// TODO Auto-generated method stub
		trips = trip;
	}

	public void onBackPressed() {

	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		try {
			callback = (OnMainListener) activity;
		} catch (ClassCastException e) {
			// TODO: handle exception
			throw new ClassCastException(activity.toString()
					+ " must implement MainMenuCallback");
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub.
		Backstack.onHistories();
		View rootView = inflater.inflate(R.layout.fragment_histories,
				container, false);
		List<Motor> motors = MotorDataAdapter.readAllMotor(getActivity());
		for (Motor mtr : motors)
			mtr.Log("HistoriesFragment");
		if (trips == null || trips.isEmpty()) {
			((ImageView) rootView.findViewById(R.id.img_history_err))
					.setVisibility(View.VISIBLE);
		} else {
			((ImageView) rootView.findViewById(R.id.img_history_err))
					.setVisibility(View.GONE);
			adapter = new CustomListHistory(getActivity(), trips);
			ListView list = (ListView) rootView
					.findViewById(R.id.listView_history);
			list.setAdapter(adapter);
			list.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					if(!onLongPressed) {
						trips.get(arg2).Log("HistoriesFragment");
						callback.startResult(trips.get(arg2));
					}
				}
			});
			list.setOnItemLongClickListener(new OnItemLongClickListener() {

				@Override
				public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					onLongPressed = true;
					final int position = arg2;
					UserSingleConfrimDialog dialog = new UserSingleConfrimDialog(
							8374, "Hapus", new OnDialogListener() {

								@Override
								public void onSubmitSingleDialog(int id) {
									// TODO Auto-generated method stub
									trips.get(position).setSaved(false);
									Loggers.w("onSubmitSingleDialog", "onSubmitSingleDialog");
									TripDataAdapter.updateTrip(getActivity(),
											trips.get(position));
									trips.remove(position);
									adapter.notifyDataSetChanged();
									onLongPressed = false;
								}

								@Override
								public void onSubmitDialog(int id,
										boolean action, String arg0) {
									// TODO Auto-generated method stub

								}

								@Override
								public void onDismiss(int id) {
									// TODO Auto-generated method stub
									Loggers.w("onDismiss", "onDismiss");
									onLongPressed = false;
								}
							});
					dialog.show(getChildFragmentManager(), null);
					return false;
				}
			});
		}
		return rootView;
	}

}
