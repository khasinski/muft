package com.youtrwam.muft;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {
	private Timer timer;
	private ArrayAdapter<String> data;

	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Util.init(this);

		final ListView view = (ListView) findViewById(R.id.friends);
		data = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, new ArrayList<String>());

		view.setAdapter(data);

		startService(new Intent(this, LocationService.class));
	}

	@Override
	public boolean onCreateOptionsMenu(final Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	public void update(final List<String> items) {
		data.clear();
		for (final String item : items)
			data.add(item);
		data.notifyDataSetChanged();
	}

	@Override
	protected void onPause() {
		timer.cancel();
		timer.purge();
		timer = null;

		super.onPause();
	}

	@Override
	protected void onPostResume() {
		super.onPostResume();

		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						new ApiTaskGet().execute(new ApiTaskGet.Params(MainActivity.this));
					}
				});
			}
		}, 0, TimeUnit.SECONDS.toMillis(Integer.parseInt(Util.i.prop.getProperty("api.interval"))));
	}
}
