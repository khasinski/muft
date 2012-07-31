package com.youtrwam.muft;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

public class ApiTask extends AsyncTask<ApiTask.Params, Void, ApiTask.Result> {
	static class Params {
		final MainActivity activity;

		public Params(final MainActivity activity) {
			this.activity = activity;
		}
	}

	static class Result {
		final Params params;
		final JSONArray messages;

		public Result(final Params params, final JSONArray messages) {
			this.params = params;
			this.messages = messages;
		}
	}

	@Override
	protected Result doInBackground(final Params... params) {
		return new Result(params[0], new Api().getMessages());
	}

	@Override
	protected void onPostExecute(final Result result) {
		try {
			final List<String> items = new ArrayList<String>();
			for (int i = 0; i < result.messages.length(); ++i) {
				final JSONObject m = result.messages.getJSONObject(i);
				final JSONArray loc = m.getJSONArray("location");
				items.add(String.format("%s: [%f, %f]", m.getString("client"), loc.getDouble(0), loc.getDouble(1)));
			}
			result.params.activity.update(items);
		} catch (final Exception e) {
			Log.e(Util.TAG, "rec", e);
		}
	}
}
