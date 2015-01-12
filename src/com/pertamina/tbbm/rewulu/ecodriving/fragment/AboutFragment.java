package com.pertamina.tbbm.rewulu.ecodriving.fragment;

import com.pertamina.tbbm.rewulu.ecodriving.R;

import android.app.Fragment;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class AboutFragment extends Fragment implements OnClickListener {
	private RelativeLayout about_app;
	private RelativeLayout accurate_app;
	private RelativeLayout how_app;
	private RelativeLayout help_app;
	private RelativeLayout term_app;
	private RelativeLayout license_app;
	private RelativeLayout version_app;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Backstack.onAbout();
		View rootView = inflater.inflate(R.layout.fragment_about, container,
				false);
		((TextView) rootView.findViewById(R.id.text_about_app_title))
				.setOnClickListener(this);
		((TextView) rootView.findViewById(R.id.text_app_accurate))
				.setOnClickListener(this);

		((TextView) rootView.findViewById(R.id.text_how_app))
				.setOnClickListener(this);
		((TextView) rootView.findViewById(R.id.text_help_app))
				.setOnClickListener(this);

		((TextView) rootView.findViewById(R.id.text_term_app))
				.setOnClickListener(this);
		((TextView) rootView.findViewById(R.id.text_license_app))
				.setOnClickListener(this);

		((TextView) rootView.findViewById(R.id.text_version_app))
				.setOnClickListener(this);

		about_app = (RelativeLayout) rootView.findViewById(R.id.about_app_body);
		accurate_app = (RelativeLayout) rootView
				.findViewById(R.id.app_accurate_body);
		how_app = (RelativeLayout) rootView.findViewById(R.id.app_how_body);
		help_app = (RelativeLayout) rootView.findViewById(R.id.app_help_body);
		term_app = (RelativeLayout) rootView.findViewById(R.id.app_term_body);
		license_app = (RelativeLayout) rootView
				.findViewById(R.id.app_license_body);
		version_app = (RelativeLayout) rootView
				.findViewById(R.id.app_version_body);

		((TextView) rootView.findViewById(R.id.text_license_app_body))
				.setText(Html.fromHtml(getActivity().getResources().getString(
						R.string.about_app_license_body)));
		
		((TextView) rootView.findViewById(R.id.text_term_app_body))
		.setText(Html.fromHtml(getActivity().getResources().getString(
				R.string.about_app_term_body)));
		
		((TextView) rootView.findViewById(R.id.text_help_app_body))
		.setText(Html.fromHtml(getActivity().getResources().getString(
				R.string.about_app_help_body)));
		
		((TextView) rootView.findViewById(R.id.text_how_app_body))
		.setText(Html.fromHtml(getActivity().getResources().getString(
				R.string.about_app_how_body)));
		
		((TextView) rootView.findViewById(R.id.text_app_accurate_body))
		.setText(Html.fromHtml(getActivity().getResources().getString(
				R.string.about_accurate_body)));
		
		((TextView) rootView.findViewById(R.id.text_about_app_body))
		.setText(Html.fromHtml(getActivity().getResources().getString(
				R.string.about_app_body)));

		try {
			PackageInfo pInfo = getActivity().getPackageManager().getPackageInfo(
					getActivity().getPackageName(), 0);
			((TextView) rootView.findViewById(R.id.text_version_body))
					.setText(new String(pInfo.versionName));
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rootView;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.text_about_app_title:
			if (about_app.getVisibility() == View.GONE)
				about_app.setVisibility(View.VISIBLE);
			else
				about_app.setVisibility(View.GONE);
			break;
		case R.id.text_app_accurate:
			if (accurate_app.getVisibility() == View.GONE)
				accurate_app.setVisibility(View.VISIBLE);
			else
				accurate_app.setVisibility(View.GONE);
			break;
		case R.id.text_how_app:
			if (how_app.getVisibility() == View.GONE)
				how_app.setVisibility(View.VISIBLE);
			else
				how_app.setVisibility(View.GONE);
			break;
		case R.id.text_help_app:
			if (help_app.getVisibility() == View.GONE)
				help_app.setVisibility(View.VISIBLE);
			else
				help_app.setVisibility(View.GONE);
			break;
		case R.id.text_term_app:
			if (term_app.getVisibility() == View.GONE)
				term_app.setVisibility(View.VISIBLE);
			else
				term_app.setVisibility(View.GONE);
			break;
		case R.id.text_license_app:
			if (license_app.getVisibility() == View.GONE)
				license_app.setVisibility(View.VISIBLE);
			else
				license_app.setVisibility(View.GONE);
			break;
		case R.id.text_version_app:
			if (version_app.getVisibility() == View.GONE)
				version_app.setVisibility(View.VISIBLE);
			else
				version_app.setVisibility(View.GONE);
			break;
		default:
			break;
		}
	}
}
