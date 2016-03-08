package com.bkmobiledevelopment.chargingwear;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
		final Resources resources = getResources();

		ToggleButton blackScreenToggleButton = (ToggleButton) findViewById(
				R.id.black_screen_toggle_button);
		blackScreenToggleButton.setChecked(preferences.getBoolean(resources.getString(R.string.black_screen_key), true));
		blackScreenToggleButton.setOnCheckedChangeListener(
				new CompoundButton.OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
						changeBooleanPreference(R.string.black_screen_key, isChecked);
					}
				});

		ToggleButton bluetoothToggleButton = (ToggleButton) findViewById(
				R.id.bluetooth_toggle_button);
		bluetoothToggleButton.setChecked(preferences.getBoolean(resources.getString(R.string.bluetooth_key), true));
		bluetoothToggleButton.setOnCheckedChangeListener(
				new CompoundButton.OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
						changeBooleanPreference(R.string.bluetooth_key, isChecked);
					}
				});
	}

	public void changeBooleanPreference(int stringId, boolean newValue) {
		SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(this)
				.edit();
		editor.putBoolean(getResources().getString(stringId), newValue);
		editor.apply();
	}
}
