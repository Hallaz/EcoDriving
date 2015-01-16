package com.pertamina.tbbm.rewulu.ecodriving.dialogs;

import com.pertamina.tbbm.rewulu.ecodriving.R;
import com.pertamina.tbbm.rewulu.ecodriving.databases.sps.UserDataSP;
import com.pertamina.tbbm.rewulu.ecodriving.listener.OnPickedDate;
import com.pertamina.tbbm.rewulu.ecodriving.pojos.UserData;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.DatePickerDialog.OnDateSetListener;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

public class UserSettingDialog extends DialogFragment implements
		OnClickListener, OnPickedDate {

	private UserData user;
	private EditText etxtName;
	private EditText etxtAddrss;
	private EditText etxtDob;
	private EditText etxtCity;
	private TextView txtErr;
	private int year = 1989;
	private int month = 1;
	private int day = 1;

	public UserSettingDialog(UserData user) {
		// TODO Auto-generated constructor stub
		this.user = user;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		final Dialog dialog = new Dialog(getActivity());
		dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		dialog.getWindow().setLayout(256, ViewGroup.LayoutParams.WRAP_CONTENT);
		dialog.setContentView(R.layout.dialog_user_setting);
		etxtName = (EditText) dialog.findViewById(R.id.etxt_name_dialog);
		etxtAddrss = (EditText) dialog.findViewById(R.id.etxt_addrss_dialog);
		etxtDob = (EditText) dialog.findViewById(R.id.etxt_dob_dialog);
		etxtCity = (EditText) dialog.findViewById(R.id.etxt_city_dialog);
		txtErr = (TextView) dialog.findViewById(R.id.txt_error_dialog);
		etxtDob.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new DatePick(UserSettingDialog.this, year, month, day).show(
						getFragmentManager(), null);
			}
		});
		((Button) dialog.findViewById(R.id.btn_dialog_back))
				.setOnClickListener(this);
		((Button) dialog.findViewById(R.id.btn_dialog_save))
				.setOnClickListener(this);
		buildView();
		return dialog;
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
		this.dismiss();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId() == R.id.btn_dialog_save) {
			saveData();
		} else
			this.dismiss();
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
