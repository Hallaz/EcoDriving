package com.pertamina.tbbm.rewulu.ecodriving.fragment;

import java.util.HashMap;

import junit.framework.Assert;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pertamina.tbbm.rewulu.ecodriving.R;
import com.pertamina.tbbm.rewulu.ecodriving.databases.ContentsAdapter;
import com.pertamina.tbbm.rewulu.ecodriving.listener.OnSlidePagesChange;

public class SlidePagesFragment extends Fragment {
	private HashMap<String, String> data;
	private int pageIndex;
	private OnSlidePagesChange onSlidePagesChange;

	public SlidePagesFragment() {
		setRetainInstance(true);
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		try {
			onSlidePagesChange = (OnSlidePagesChange) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnHeadlineSelectedListener");
		}
	}

	public void setContent(HashMap<String, String> data, int paggeIndex) {
		// TODO Auto-generated method stub
		this.pageIndex = paggeIndex;
		this.data = data;
	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		// TODO Auto-generated method stub
		super.setUserVisibleHint(isVisibleToUser);
		if (isVisibleToUser && onSlidePagesChange != null) {
			onSlidePagesChange.onPagePossiton(pageIndex);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_slide_pages,
				container, false);
		((TextView) rootView.findViewById(R.id.slide_judul_text)).setText(data
				.get(ContentsAdapter.KEY_CONTENT_JUDUL));
		((ImageView) rootView.findViewById(R.id.slide_context_imgview))
				.setImageResource(getDrawable(getActivity(),
						data.get(ContentsAdapter.KEY_CONTENT_CONTENT)));
		((ImageView) rootView.findViewById(R.id.back_action_bar))
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						onSlidePagesChange.onBackKeyPressed();
					}
				});
		return rootView;
	}

	private static int getDrawable(Context context, String name) {
		Assert.assertNotNull(context);
		Assert.assertNotNull(name);
		return context.getResources().getIdentifier(name, "drawable",
				context.getPackageName());
	}
}