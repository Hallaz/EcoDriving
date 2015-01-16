package com.pertamina.tbbm.rewulu.ecodriving.dialogs;

import com.pertamina.tbbm.rewulu.ecodriving.R;
import com.pertamina.tbbm.rewulu.ecodriving.databases.sps.UserDataSP;
import com.pertamina.tbbm.rewulu.ecodriving.pojos.UserData;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class UserSettingDialog extends DialogFragment implements
		OnClickListener {

	private UserData user;
	private EditText etxtName;
	private EditText etxtAddrss;
	private EditText etxtDob;
	private EditText etxtCity;
	private TextView txtErr;

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
		((Button) dialog.findViewById(R.id.btn_dialog_back))
				.setOnClickListener(this);
		((Button) dialog.findViewById(R.id.btn_dialog_save))
				.setOnClickListener(this);
		return dialog ;
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
		saveData();
	}

}
