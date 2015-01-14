/*
 * @author Hallaz ~ hallaz.ibnu@gmail.com
 * 
 * Please do not modify without any agreement between end user and the author.
 * 
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.pertamina.tbbm.rewulu.ecodriving;

import java.util.ArrayList;
import java.util.HashMap;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.pertamina.tbbm.rewulu.ecodriving.adapters.SlidePagesAdapter;
import com.pertamina.tbbm.rewulu.ecodriving.databases.ContentsAdapter;
import com.pertamina.tbbm.rewulu.ecodriving.fragment.SlidePagesFragment.onSlidePagesChange;
import com.pertamina.tbbm.rewulu.ecodriving.utils.Enums.Type;

public class ContentsActivity extends FragmentActivity implements
		onSlidePagesChange, OnClickListener {
	public static final String FLAG = ContentsActivity.class.getSimpleName();
	private ViewPager viewPager;
	private SlidePagesAdapter adapter;
	/* private boolean onIntro; */
	private final String PAGER_POS = "pager_pos";
	public static final String ON_INTRO = "on_intro";
	private LinearLayout panel;
	private LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
			new LayoutParams(LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT));
	private int pageSize;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().hide();
		setContentView(R.layout.activity_contents);
		panel = (LinearLayout) findViewById(R.id.panel_pages_content);
		params.setMargins(8, 8, 8, 8);
		params.weight = 1;
		viewPager = (ViewPager) findViewById(R.id.pager);
		ArrayList<HashMap<String, String>> data;
		panel.setVisibility(View.VISIBLE);
		data = ContentsAdapter
				.readContents(getApplicationContext(), Type.GUIDE);
		this.pageSize = data.size();
		adapter = new SlidePagesAdapter(getApplicationContext(),
				getSupportFragmentManager(), data);
		viewPager.setAdapter(adapter);
		initPanel();
		if (savedInstanceState != null) {
			viewPager.setCurrentItem(savedInstanceState.getInt(PAGER_POS));
			onPagePossiton(savedInstanceState.getInt(PAGER_POS));
		} else
			onPagePossiton(0);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		outState.putInt(PAGER_POS, viewPager.getCurrentItem());
	}

	@Override
	public void onBackPressed() {
		if (viewPager.getCurrentItem() == 0) {
			this.finish();
		} else {
			viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
		}
	}

	private ArrayList<ImageView> imgs;

	private void initPanel() {
		// TODO Auto-generated method stub
		imgs = new ArrayList<>();
		panel.removeAllViews();
		for (int w = 0; w < pageSize; w++) {
			ImageView img = new ImageView(getApplicationContext());
			img.setLayoutParams(params);
			if (0 == w)
				img.setImageResource(R.drawable.panel_indicator_full);
			else
				img.setImageResource(R.drawable.panel_indicator);
			img.setVisibility(View.VISIBLE);
			img.setOnClickListener(this);
			panel.addView(img);
			imgs.add(img);
		}
	}

	@Override
	public void onPagePossiton(int arg0) {
		// TODO Auto-generated method stub
		for (int w = 0; w < imgs.size(); w++) {
			if (arg0 == w) {
				imgs.get(w).setImageResource(R.drawable.panel_indicator_full);
			} else
				imgs.get(w).setImageResource(R.drawable.panel_indicator);
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		for (int w = 0; w < imgs.size(); w++) {
			if (v.equals(imgs.get(w)))
				viewPager.setCurrentItem(w);
		}
	}
}
