package com.pertamina.tbbm.rewulu.ecodriving.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pertamina.tbbm.rewulu.ecodriving.R;
import com.pertamina.tbbm.rewulu.ecodriving.listener.OnDialogListener;
import com.pertamina.tbbm.rewulu.ecodriving.utils.Constant;

public class UserInputDialogFragment extends DialogFragment implements
		OnClickListener {
	private final int ID;
	private OnDialogListener listener;
	private String text;
	private String title = null;
	private boolean actionInput;
	private EditText userInput;

	public UserInputDialogFragment(final int id) {
		// TODO Auto-generated constructor stub
		this.ID = id;
	}

	public UserInputDialogFragment(final int id, String arg0, boolean arg1,
			OnDialogListener arg2) {
		// TODO Auto-generated constructor stub
		this.ID = id;
		listener = arg2;
		text = arg0;
		actionInput = arg1;
	}

	public UserInputDialogFragment(final int id, String title, String text,
			boolean arg3, OnDialogListener arg2) {
		// TODO Auto-generated constructor stub
		this.ID = id;
		listener = arg2;
		this.text = text;
		actionInput = arg3;
		this.title = title;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		final Dialog dialog = new Dialog(getActivity());
		dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		dialog.getWindow().setLayout(256, ViewGroup.LayoutParams.WRAP_CONTENT);
		if (actionInput) {
			dialog.setContentView(R.layout.user_input_dialog);
			userInput = (EditText) dialog.findViewById(R.id.editText_dialog);
			((Button) dialog.findViewById(R.id.btn_dialog_no))
					.setOnClickListener(this);
		} else {
			if (ID == Constant.DIALOG_ERR) {
				dialog.setContentView(R.layout.user_notif_dialog);
			} else {
				dialog.setContentView(R.layout.user_dialog);
				((Button) dialog.findViewById(R.id.btn_dialog_no))
						.setOnClickListener(this);
			}
		}
		((TextView) dialog.findViewById(R.id.text_user_dialog)).setText(text);
		if (title != null)
			((TextView) dialog.findViewById(R.id.text_user_title))
					.setText(title);
		else
			((LinearLayout) dialog.findViewById(R.id.lin_user_title))
					.setVisibility(View.GONE);
		((Button) dialog.findViewById(R.id.btn_dialog_ok))
				.setOnClickListener(this);
		dialog.getWindow().setBackgroundDrawable(
				new ColorDrawable(Color.TRANSPARENT));
		dialog.show();

		return dialog;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_dialog_ok:
			if (actionInput) {
				String txt = userInput.getText().toString();
				if (text != null && !text.isEmpty())
					listener.onSubmitDialog(ID, true, txt);
				else
					listener.onSubmitDialog(ID, false, txt);
			} else {
				listener.onSubmitDialog(ID, true, null);
			}
			this.dismiss();
			break;
		case R.id.btn_dialog_no:
			listener.onSubmitDialog(ID, false, null);
			this.dismiss();
			break;

		default:
			break;
		}
	}
}
