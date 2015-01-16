package com.pertamina.tbbm.rewulu.ecodriving.listener;

public interface OnDialogListener {
	public void onSubmitDialog(final int id, boolean action, String arg0);
	public void onSubmitSingleDialog(final int id);
	public void onDismiss(final int id);
}
