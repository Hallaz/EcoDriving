package com.pertamina.tbbm.rewulu.ecodriving.adapters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.pertamina.tbbm.rewulu.ecodriving.databases.ContentsAdapter;
import com.pertamina.tbbm.rewulu.ecodriving.fragment.SlidePagesFragment;
import com.pertamina.tbbm.rewulu.ecodriving.utils.Loggers;

public class SlidePagesAdapter extends FragmentPagerAdapter {
	private final int PAGES_NUM;
	private ArrayList<HashMap<String, String>> data;

	public SlidePagesAdapter(Context context, FragmentManager fm,
			ArrayList<HashMap<String, String>> data) {
		super(fm);
		// TODO Auto-generated constructor stub
		this.data = data;
		PAGES_NUM = data.size();
		this.sort();
		Loggers.getInstance("SlidePagesAdapter");
		Loggers.i("", "data.size()" + data.size());
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// TODO Auto-generated method stub
		super.destroyItem(container, position, object);
	}

	private void sort() {
		// TODO Auto-generated method stub
		ArrayList<Integer> index = new ArrayList<>();
		for (HashMap<String, String> val : data)
			index.add(Integer.valueOf(val
					.get(ContentsAdapter.KEY_CONTENT_ROWID)));
		Collections.sort(index);
		ArrayList<HashMap<String, String>> nData = new ArrayList<HashMap<String, String>>();
		for (Integer val : index) {
			for (HashMap<String, String> vl : data)
				if (Integer.valueOf(vl.get(ContentsAdapter.KEY_CONTENT_ROWID)) == val)
					nData.add(vl);
		}
		if (data.size() == nData.size())
			data = nData;
	}

	@Override
	public Fragment getItem(int arg0) {
		// TODO Auto-generated method stub
		SlidePagesFragment fm = new SlidePagesFragment();
		boolean req = false;
		if (arg0 == data.size() - 1)
			req = true;
		fm.setContent(data.get(arg0), req, arg0);
		return fm;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return PAGES_NUM;
	}
}
