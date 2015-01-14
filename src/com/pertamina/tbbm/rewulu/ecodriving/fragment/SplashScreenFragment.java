package com.pertamina.tbbm.rewulu.ecodriving.fragment;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.pertamina.tbbm.rewulu.ecodriving.R;
import com.pertamina.tbbm.rewulu.ecodriving.clients.UpdateClient;
import com.pertamina.tbbm.rewulu.ecodriving.clients.UpdateClient.ResponseUpdate;
import com.pertamina.tbbm.rewulu.ecodriving.databases.MotorDataAdapter;
import com.pertamina.tbbm.rewulu.ecodriving.databases.sps.UserDataSP;
import com.pertamina.tbbm.rewulu.ecodriving.listener.OnMainListener;
import com.pertamina.tbbm.rewulu.ecodriving.listener.OnPickedDate;
import com.pertamina.tbbm.rewulu.ecodriving.pojos.Motor;
import com.pertamina.tbbm.rewulu.ecodriving.pojos.UserData;
import com.pertamina.tbbm.rewulu.ecodriving.utils.Loggers;
import com.pertamina.tbbm.rewulu.ecodriving.utils.Utils;

public class SplashScreenFragment extends Fragment implements OnPickedDate {
	private OnMainListener callback;
	private TextView fade;
	private Animation fadeinout;
	private LinearLayout splashContainer;

	private EditText etxtName;
	private EditText etxtAddrss;
	private EditText etxtDob;
	private EditText etxtCity;
	private TextView txtErr;
	private Button btn_ok;
	private LinearLayout infoContainer;
	private int year = 1989;
	private int month = 1;
	private int day = 1;
	private String email;
	private boolean interupted;

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		try {
			callback = (OnMainListener) activity;
		} catch (ClassCastException e) {
			// TODO: handle exception
			throw new ClassCastException(activity.toString()
					+ " must implement MainMenuCallback");
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Backstack.onSplash();
		Loggers.i("SplashScreenFragment", "Backstack.onSplash()");
		View rootView = inflater.inflate(R.layout.fragment_splash_sceen,
				container, false);
		fade = (TextView) rootView.findViewById(R.id.textView1);
		fadeinout = AnimationUtils.loadAnimation(getActivity(),
				R.anim.fadeinout);
		fade.startAnimation(fadeinout);
		count.start();
		splashContainer = (LinearLayout) rootView
				.findViewById(R.id.container_splash_screen);
		infoContainer = (LinearLayout) rootView
				.findViewById(R.id.container_user_info);
		infoContainer.setVisibility(View.GONE);
		etxtName = (EditText) rootView.findViewById(R.id.etxt_name);
		etxtAddrss = (EditText) rootView.findViewById(R.id.etxt_addrss);
		etxtDob = (EditText) rootView.findViewById(R.id.etxt_dob);
		etxtCity = (EditText) rootView.findViewById(R.id.etxt_city);
		txtErr = (TextView) rootView.findViewById(R.id.txt_error_splash);
		btn_ok = (Button) rootView.findViewById(R.id.btn_prosess_infor);
		etxtDob.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new DatePick(SplashScreenFragment.this, year, month, day).show(
						getFragmentManager(), null);
			}
		});
		email = getGmail();
		new loader().execute(email);
		return rootView;
	}

	private class loader extends AsyncTask<String, List<Motor>, Boolean> {
		@SuppressWarnings("unchecked")
		@Override
		protected void onProgressUpdate(List<Motor>... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
			if (callback != null)
				callback.setDataMotor(values[0]);
		}

		@SuppressWarnings("unchecked")
		@Override
		protected Boolean doInBackground(String... params) {
			// TODO Auto-generated method stub
			ArrayList<Motor> motors = MotorDataAdapter
					.readAllMotor(getActivity());
			publishProgress(motors);
			if (!Utils.isInternetAvailable())
				return true;
			String date = MotorDataAdapter.getLastdStringDate(getActivity());
			ResponseUpdate lastUpdate = UpdateClient.update(params[0]);
			return date.trim().equals(lastUpdate.created_at.trim());
		}

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (!result && callback != null) {
				callback.upDateMotor(email);
			}
			interupted();
			interupted = true;
		}
	}

	private CountDownTimer count = new CountDownTimer(3000, 1000) {

		@Override
		public void onTick(long millisUntilFinished) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onFinish() {
			// TODO Auto-generated method stub
			interupted();
			interupted = true;
		}
	};

	private void interupted() {
		// TODO Auto-generated method stub
		if (interupted) {
			if (!UserDataSP.isEmty(getActivity())) {
				UserData user = UserDataSP.get(getActivity());
				boolean update = false;
				if (!user.getEmail().equals(email)) {
					user.setEmail(email);
					update = true;
				}
				String phone = getDeviceName().trim();
				if (!user.getPhone_model().equals(phone)) {
					user.setPhone_model(phone);
					update = true;
				}
				String os = getOs();
				if (!user.getOs().equals(os)) {
					user.setOs(os);
					update = true;
				}
				if (callback != null) {
					if (update)
						callback.startApp(user);
					else
						callback.startApp(null);
				}
			} else {
				splashContainer.setVisibility(View.GONE);
				infoContainer.setVisibility(View.VISIBLE);
				btn_ok.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						buildInfo();
					}
				});
			}
		}
	}

	private void buildInfo() {
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
		UserData user = new UserData();
		user.setName(etxtName.getText().toString());
		user.setAddrss(etxtAddrss.getText().toString());
		user.setDob(etxtDob.getText().toString());
		user.setCity(etxtCity.getText().toString());
		user.setEmail(getGmail());
		user.setOs(getOs());
		user.setPhone_model(getDeviceName().trim());
		if (callback != null)
			callback.startApp(user);
	}

	private String getOs() {
		// TODO Auto-generated method stub
		StringBuilder builder = new StringBuilder();
		builder.append("android : ").append(Build.VERSION.RELEASE);
		Field[] fields = Build.VERSION_CODES.class.getFields();
		for (Field field : fields) {
			String fieldName = field.getName();
			int fieldValue = -1;
			try {
				fieldValue = field.getInt(new Object());
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (NullPointerException e) {
				e.printStackTrace();
			}

			if (fieldValue == Build.VERSION.SDK_INT) {
				builder.append(" : ").append(fieldName).append(" : ");
				builder.append("sdk = ").append(fieldValue);
			}
		}
		return builder.toString().trim();
	}

	private String getDeviceName() {
		String manufacturer = Build.MANUFACTURER;
		String model = Build.MODEL;
		if (model.startsWith(manufacturer)) {
			return Utils.capitalize(model);
		} else {
			return Utils.capitalize(manufacturer) + " " + model;
		}
	}

	private String getGmail() {
		String gmail = null;
		Pattern gmailPattern = Patterns.EMAIL_ADDRESS; // API level 8+
		Account[] accounts = AccountManager.get(getActivity()).getAccounts();
		for (Account account : accounts) {
			if (gmailPattern.matcher(account.name).matches()) {
				gmail = account.name;
			}
		}
		return gmail.trim();
	}

	@SuppressLint("ValidFragment") private class DatePick extends DialogFragment {
		// private DatePicker datePicker;
		int year, month, day;
		private OnPickedDate mCallback;

		public DatePick(OnPickedDate mcallback, int year, int month, int day) {
			// TODO Auto-generated constructor stub
			this.mCallback = mcallback;
			this.year = year;
			this.month = month;
			this.day = day;
		}

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			return new DatePickerDialog(getActivity(), callBack, year, month,
					day);
		}

		private DatePickerDialog.OnDateSetListener callBack = new OnDateSetListener() {

			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
				// TODO Auto-generated method stub
				mCallback.pickedDate(year, dayOfMonth, dayOfMonth);
			}
		};
	}

	@Override
	public void pickedDate(int year, int month, int day) {
		// TODO Auto-generated method stub
		etxtDob.setText(year + " - " + month + " - " + day);
		this.year = year;
		this.month = month;
		this.day = day;
	}
}
