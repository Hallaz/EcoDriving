package com.pertamina.tbbm.rewulu.ecodriving.clients;

import java.io.IOException;
import java.io.InputStream;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

public class ImagesClient {

	private static InputStream in;

	public static InputStream download(String url) {
		in = null;
		OkHttpClient client = new OkHttpClient();
		Request request = new Request.Builder().url(url).build();
		client.newCall(request).enqueue(new Callback() {
			@Override
			public void onFailure(Request request, IOException e) {
				System.out.println("request failed: " + e.getMessage());
			}

			@Override
			public void onResponse(Response response) throws IOException {
				in = response.body().byteStream();
			}
		});
		return in;
	}
}
