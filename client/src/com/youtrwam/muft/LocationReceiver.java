package com.youtrwam.muft;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class LocationReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(final Context context, final Intent intent) {
		try {
			final JSONObject msg = new JSONObject();
			final JSONArray loc = new JSONArray();
			loc.put(intent.getDoubleExtra("latitude", -1));
			loc.put(intent.getDoubleExtra("longitude", -1));
			msg.put("location", loc);
			new ApiTaskPost().execute(msg);
		} catch (final Exception e) {
			Log.e(Util.TAG, "Location post failed", e);
		}
	}

}
