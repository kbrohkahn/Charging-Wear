package com.bkmobiledevelopment.chargingwear;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.widget.Toast;

/**
 * Created by kbrohkahn on 3/8/2016.
 * Receiver listening for charge status change
 */
public class PowerConnectedReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(final Context context, Intent intent) {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
		final Resources resources = context.getResources();

		final boolean blackScreen = preferences.getBoolean(resources.getString(R.string.black_screen_key),
				true);
		final boolean bluetooth = preferences.getBoolean(resources.getString(R.string.bluetooth_key),
				true);

		String message = "Charging source detected, Charging Wear ";
		if (blackScreen && bluetooth) {
			message += "disabling Bluetooth and showing dark screen.";
		} else if (bluetooth) {
			message += "disabling Bluetooth.";
		} else if (blackScreen) {
			message += "showing black screen.";
		} else {
			message += "taking no action.";
		}

//		AlertDialog.Builder builder = new AlertDialog.Builder(context).setTitle("Charging source detected").setMessage()

		Toast.makeText(context, message, Toast.LENGTH_LONG).show();

		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				if (blackScreen) {
					Intent blackScreenIntent = new Intent(context, BlackScreenActivity.class);
					blackScreenIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					context.startActivity(blackScreenIntent);
				}

				if (bluetooth) {
					BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
					if (mBluetoothAdapter != null && mBluetoothAdapter.isEnabled()) {
						mBluetoothAdapter.disable();
					}
				}
			}
		}, 3000);

	}
}
