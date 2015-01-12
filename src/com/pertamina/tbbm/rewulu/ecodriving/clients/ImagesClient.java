package com.pertamina.tbbm.rewulu.ecodriving.clients;

import java.io.IOException;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

public class ImagesClient {

	public void download(String url) {
		OkHttpClient client = new OkHttpClient();

		Request request = new Request.Builder().url(
				"http://wwwns.akamai.com/media_resources/globe_emea.png")
				.build();

		client.newCall(request).enqueue(new Callback() {
			@Override
			public void onFailure(Request request, IOException e) {
				System.out.println("request failed: " + e.getMessage());
			}

			@Override
			public void onResponse(Response response) throws IOException {
				response.body().byteStream(); // Read the data from the stream
			}
		});
	}
}
