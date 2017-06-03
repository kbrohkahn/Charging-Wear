package com.bkmobiledevelopment.chargingwear;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.preference.PreferenceManager;

/**
 * Created by kbrohkahn on 3/8/2016.
 * Receiver listening for charge status change
 */
public class PowerDisconnectedReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
		final Resources resources = context.getResources();

		boolean bluetooth = preferences.getBoolean(resources.getString(R.string.bluetooth_key),
				true);


		if (bluetooth) {
			BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
			if (mBluetoothAdapter != null && !mBluetoothAdapter.isEnabled()) {
				mBluetoothAdapter.enable();
			}
		}
	}
}
