package com.bkmobiledevelopment.chargingwear;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;

/**
 * Created by kbrohkahn on 3/8/2016.
 * Activity displaying only a black screen
 */
public class BlackScreenActivity extends WearableActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.black_screen);
		setAmbientEnabled();

		IntentFilter intentFilter=new IntentFilter();
		intentFilter.addAction(Intent.ACTION_POWER_DISCONNECTED);

		registerReceiver(new PowerDisconnectedReceiver(), intentFilter);
	}

	private class PowerDisconnectedReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			unregisterReceiver(this);
			finish();
		}
	}
}
