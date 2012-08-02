package com.youtrwam.muft;

import java.util.concurrent.TimeUnit;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Looper;

public class LocationService extends Service {
	class ServiceLocationListener implements LocationListener {
		@Override
		public void onLocationChanged(final Location location) {
			final Intent intent = new Intent("com.youtrwam.muft.action.LOCATION");
			intent.putExtra("lat", location.getLatitude());
			intent.putExtra("lng", location.getLongitude());
			sendBroadcast(intent);
		}

		@Override
		public void onProviderDisabled(final String provider) {
		}

		@Override
		public void onProviderEnabled(final String provider) {
		}

		@Override
		public void onStatusChanged(final String provider, final int status, final Bundle extras) {
		}
	}

	@Override
	public IBinder onBind(final Intent intent) {
		return null;
	}

	@Override
	public int onStartCommand(final Intent intent, final int flags, final int startId) {

		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Looper.prepare();
					final LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

					final Criteria c = new Criteria();
					c.setAccuracy(Criteria.ACCURACY_COARSE);

					final String PROVIDER = lm.getBestProvider(c, true);

					lm.requestLocationUpdates(PROVIDER, TimeUnit.SECONDS.toMillis(60 * 60), 0, new ServiceLocationListener());
					Looper.loop();
				} catch (final Exception ex) {
					ex.printStackTrace();
				}
			}
		}, "LocationThread").start();

		return START_STICKY;
	}
}
