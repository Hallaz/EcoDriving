package com.pertamina.tbbm.rewulu.ecodriving.adapters;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Spinner;

public class SpinnerMenuCustom extends Spinner {
	Context context = null;

	public SpinnerMenuCustom(Context context) {
		super(context);
		this.context = context;
	}

	public SpinnerMenuCustom(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public SpinnerMenuCustom(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public void onDetachedFromWindow() {
		super.onDetachedFromWindow();
	}
}
