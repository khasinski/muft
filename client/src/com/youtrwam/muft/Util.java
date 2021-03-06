package com.youtrwam.muft;

import java.util.Properties;

import android.app.Activity;
import android.content.res.Resources;

public class Util {
	public static String TAG = "muft";

	public static Util i;

	public final Properties prop;

	private Util(final Activity activity, final Properties prop) {
		this.prop = prop;
	}

	public static void init(final Activity activity) {
		final Resources resources = activity.getResources();
		final Properties prop = new Properties();
		try {
			prop.load(resources.openRawResource(R.raw.config));
		} catch (final Exception _) {
		}
		try {
			prop.load(resources.openRawResource(R.raw.class.getDeclaredField("local").getInt(null)));
		} catch (final Exception _) {
		}

		i = new Util(activity, prop);
	}
}
