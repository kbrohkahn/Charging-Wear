package com.bkmobiledevelopment.chargingwear;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Handler;
import android.preference.PreferenceManager;

/**
 * Created by kbrohkahn on 3/8/2016.
 * Receiver listening for charge status change
 */
public class PowerConnectedReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(final Context context, Intent intent) {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
		final Resources resources = context.getResources();

		boolean blackScreen = preferences.getBoolean(resources.getString(R.string.black_screen_key),
				true);
		boolean bluetooth = preferences.getBoolean(resources.getString(R.string.bluetooth_key),
				true);

		if (blackScreen) {
			final Handler handler = new Handler();
			handler.postDelayed(new Runnable() {
				@Override
				public void run() {
					Intent blackScreenIntent = new Intent(context, BlackScreenActivity.class);
					blackScreenIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					context.startActivity(blackScreenIntent);
				}
			}, 5000);
		}

		if (bluetooth) {
			BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
			if (mBluetoothAdapter.isEnabled()) {
				mBluetoothAdapter.disable();
			}
		}
	}
}
