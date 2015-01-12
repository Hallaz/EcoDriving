package com.pertamina.tbbm.rewulu.ecodriving.utils;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import retrofit.ErrorHandler;
import retrofit.RetrofitError;
import android.util.Log;

public class Error {
	public static class MyErrorHandler implements ErrorHandler {
		@SuppressWarnings("deprecation")
		@Override
		public Throwable handleError(RetrofitError cause) {
			Log.i("MyErrorHandler ", cause.getMessage());
			if (cause.isNetworkError()) {
				if (cause.getMessage().contains("authentication")) {
					// 401 errors
					Log.e("ErrorHandler",
							"Invalid credentials. Please verify login info");
					return new Exception(
							"Invalid credentials. Please verify login info.");
				} else if (cause.getCause() instanceof SocketTimeoutException) {
					// Socket Timeout
					Log.e("ErrorHandler",
							"Please verify your internet connection.");
					return new SocketTimeoutException("Connection Timeout. "
							+ "Please verify your internet connection.");
				} else {
					// No Connection
					Log.e("ErrorHandler",
							"Please verify your internet connection.");
					return new ConnectException("No Connection. "
							+ "Please verify your internet connection.");
				}
			} else {

				return cause;
			}
		}
	}
}
