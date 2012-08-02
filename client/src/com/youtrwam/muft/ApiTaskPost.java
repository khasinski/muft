package com.youtrwam.muft;

import org.json.JSONObject;

import android.os.AsyncTask;

public class ApiTaskPost extends AsyncTask<JSONObject, Void, Void> {
	@Override
	protected Void doInBackground(final JSONObject... params) {
		new Api().postMessage(params[0]);

		return null;
	}
}
