package com.pertamina.tbbm.rewulu.ecodriving.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.pertamina.tbbm.rewulu.ecodriving.R;
import com.pertamina.tbbm.rewulu.ecodriving.listener.OnDialogListener;

public class UserSingleConfrimDialog extends DialogFragment {
	private final int ID;
	private OnDialogListener listener;
	private String text;

	public UserSingleConfrimDialog(final int id, String text,
			OnDialogListener arg2) {
		// TODO Auto-generated constructor stub
		this.ID = id;
		listener = arg2;
		this.text = text;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		final Dialog dialog = new Dialog(getActivity());
		dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		dialog.getWindow().setLayout(256, ViewGroup.LayoutParams.WRAP_CONTENT);
		dialog.setContentView(R.layout.user_single_dialog);
		TextView tv = (TextView) dialog
				.findViewById(R.id.textView_user_single_dialog_confirm);
		if (text != null)
			tv.setText(text);
		tv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				listener.onSubmitSingleDialog(ID);
				dialog.dismiss();
			}
		});
		dialog.show();

		return dialog;
	}
}
