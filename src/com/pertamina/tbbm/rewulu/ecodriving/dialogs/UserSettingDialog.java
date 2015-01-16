package com.pertamina.tbbm.rewulu.ecodriving.dialogs;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;

import com.pertamina.tbbm.rewulu.ecodriving.R;
import com.pertamina.tbbm.rewulu.ecodriving.databases.sps.SettingSP;

public class UserSettingDialog extends DialogFragment {
	public UserSettingDialog() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		final Dialog dialog = new Dialog(getActivity());
		dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		dialog.getWindow().setLayout(256, ViewGroup.LayoutParams.WRAP_CONTENT);
		dialog.setContentView(R.layout.user_setting_dialog);
		final CheckBox vib = (CheckBox) dialog.findViewById(R.id.checkBoxVib);
		final CheckBox led = (CheckBox) dialog.findViewById(R.id.checkBoxLed);
		final CheckBox voice = (CheckBox) dialog
				.findViewById(R.id.checkBoxVoice);
		SettingSP.saveSetting(getActivity());
		vib.setChecked(SettingSP.VIBRATE);
		led.setChecked(SettingSP.LED);
		voice.setChecked(SettingSP.VOICE);
		((Button) dialog.findViewById(R.id.btn_dialog_ok))
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						SettingSP.LED = led.isChecked();
						SettingSP.VIBRATE = vib.isChecked();
						SettingSP.VOICE = voice.isChecked();
						SettingSP.loadSetting(getActivity());
						dialog.dismiss();
					}
				});
		return dialog;
	}
}
