package com.youtrwam.muft;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;

public class Api {
	private String post(final String url, final String body) throws Exception {
		Log.d(Util.TAG, String.format("request %s [%s]", url, body));
		final HttpClient httpclient = new DefaultHttpClient();
		HttpUriRequest request;
		if (null != body) {
			final HttpPost post = new HttpPost(Util.i.prop.getProperty("api.url"));
			post.getParams().setParameter(CoreProtocolPNames.USE_EXPECT_CONTINUE, Boolean.FALSE);
			post.setEntity(new StringEntity(body, "utf-8"));
			request = post;
		} else
			request = new HttpGet(Util.i.prop.getProperty("api.url"));
		request.getParams().setParameter(CoreProtocolPNames.HTTP_ELEMENT_CHARSET, "utf-8");
		try {
			final HttpResponse httpResponse = httpclient.execute(request);
			if (httpResponse.getStatusLine().getStatusCode() != 200)
				throw new Exception(httpResponse.getStatusLine().toString());
			return httpResponseToString(httpResponse);
		} catch (final Exception e) {
			throw new Exception(e);
		}
	}

	private static String httpResponseToString(final HttpResponse response) throws IllegalStateException, IOException {
		final HttpEntity entity = response.getEntity();
		final InputStream inputStream = entity.getContent();
		final ByteArrayOutputStream content = new ByteArrayOutputStream();

		int readBytes = 0;
		final byte[] sBuffer = new byte[512];
		while ((readBytes = inputStream.read(sBuffer)) != -1)
			content.write(sBuffer, 0, readBytes);

		final String result = new String(content.toByteArray());
		Log.d(Util.TAG, result);
		return result;
	}

	public JSONArray getMessages() {
		try {
			return new JSONArray(post("/", null));
		} catch (final Exception e) {
			Log.e(Util.TAG, e.getMessage(), e);
			return new JSONArray();
		}
	}

	public void postMessage(final JSONObject message) {
		try {
			post("/", message.toString());
		} catch (final Exception e) {
			Log.e(Util.TAG, e.getMessage(), e);
		}
	}
}
