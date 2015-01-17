package com.pertamina.tbbm.rewulu.ecodriving.fragment;

import com.pertamina.tbbm.rewulu.ecodriving.R;
import com.pertamina.tbbm.rewulu.ecodriving.databases.sps.UserDataSP;
import com.pertamina.tbbm.rewulu.ecodriving.listener.OnMainListener;
import com.pertamina.tbbm.rewulu.ecodriving.listener.OnPickedDate;
import com.pertamina.tbbm.rewulu.ecodriving.pojos.UserData;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.DatePickerDialog.OnDateSetListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class UserSettingFragment extends Fragment implements OnClickListener,
		OnPickedDate {

	private OnMainListener mMenuCallback;
	private UserData user;
	private EditText etxtName;
	private EditText etxtAddrss;
	private EditText etxtDob;
	private EditText etxtCity;
	private TextView txtErr;
	private int year = 1989;
	private int month = 1;
	private int day = 1;

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		try {
			mMenuCallback = (OnMainListener) activity;
		} catch (ClassCastException e) {
			// TODO: handle exception
			throw new ClassCastException(activity.toString()
					+ " must implement MainMenuCallback");
		}
	}

	public void setUser(UserData user) {
		this.user = user;
	}

	public void onBackPressed() {
		mMenuCallback.startMainMenu();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.fragment_user_setting,
				container, false);
		Backstack.onUserSetting();
		etxtName = (EditText) rootView.findViewById(R.id.etxt_name_dialog);
		etxtAddrss = (EditText) rootView.findViewById(R.id.etxt_addrss_dialog);
		etxtDob = (EditText) rootView.findViewById(R.id.etxt_dob_dialog);
		etxtCity = (EditText) rootView.findViewById(R.id.etxt_city_dialog);
		txtErr = (TextView) rootView.findViewById(R.id.txt_error_dialog);
		etxtDob.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new DatePick(UserSettingFragment.this, year, month, day).show(
						getFragmentManager(), null);
			}
		});
		((Button) rootView.findViewById(R.id.btn_dialog_back))
				.setOnClickListener(this);
		((Button) rootView.findViewById(R.id.btn_dialog_save))
				.setOnClickListener(this);
		((ImageView) rootView.findViewById(R.id.back_action_bar))
				.setOnClickListener(this);
		buildView();
		return rootView;
	}

	private void buildView() {
		// TODO Auto-generated method stub
		etxtName.setText(user.getName());
		etxtAddrss.setText(user.getAddrss());
		etxtDob.setText(user.getDob());
		etxtCity.setText(user.getCity());
	}

	private void saveData() {
		// TODO Auto-generated method stub
		if (etxtName.getText().toString().isEmpty()) {
			txtErr.setText("Kolom nama harus diisi");
			return;
		}
		if (etxtDob.getText().toString().isEmpty()) {
			txtErr.setText("Kolom tanggal lahir harus diisi");
			return;
		}
		if (etxtAddrss.getText().toString().isEmpty()) {
			txtErr.setText("Kolom alamat harus diisi");
			return;
		}
		if (etxtCity.getText().toString().isEmpty()) {
			txtErr.setText("Kolom kota harus diisi");
			return;
		}
		user.setName(etxtName.getText().toString());
		user.setAddrss(etxtAddrss.getText().toString());
		user.setDob(etxtDob.getText().toString());
		user.setCity(etxtCity.getText().toString());
		UserDataSP.put(getActivity(), user);
		mMenuCallback.startApp(user);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId() == R.id.btn_dialog_save)
			saveData();
		else if (v.getId() == R.id.btn_dialog_back)
			mMenuCallback.startMainMenu();
		else if (v.getId() == R.id.back_action_bar)
			mMenuCallback.onBackActionPressed();
	}

	@SuppressLint("ValidFragment")
	private class DatePick extends DialogFragment {
		// private DatePicker datePicker;
		int year, month, day;
		private OnPickedDate mCallback;

		public DatePick(OnPickedDate mcallback, int year, int month, int day) {
			// TODO Auto-generated constructor stub
			this.mCallback = mcallback;
			this.year = year;
			this.month = month;
			this.day = day;
		}

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			return new DatePickerDialog(getActivity(), callBack, year, month,
					day);
		}

		private DatePickerDialog.OnDateSetListener callBack = new OnDateSetListener() {

			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
				// TODO Auto-generated method stub
				mCallback.pickedDate(year, dayOfMonth, dayOfMonth);
			}
		};
	}

	@Override
	public void pickedDate(int year, int month, int day) {
		// TODO Auto-generated method stub
		etxtDob.setText(day + " - " + month + " - " + year);
		this.year = year;
		this.month = month;
		this.day = day;
	}

}
