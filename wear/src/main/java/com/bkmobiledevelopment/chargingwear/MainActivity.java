package com.bkmobiledevelopment.chargingwear;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.wearable.view.WearableListView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity implements WearableListView.ClickListener {

	private final String[] listItems = {"While " + Build.MODEL + " is charging...",
			"Show black screen",
			"Disable bluetooth"};
	public static boolean[] listItemValues = {false, true, true};


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		WearableListView listView = (WearableListView) findViewById(R.id.wearable_list);
		listView.setAdapter(new Adapter(this, listItems));
		listView.setClickListener(this);

		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
		final Resources resources = getResources();

		listItemValues[1] = preferences.getBoolean(resources.getString(R.string.black_screen_key),
				true);
		listItemValues[2] = preferences.getBoolean(resources.getString(R.string.bluetooth_key),
				true);
	}

	@Override
	public void onClick(WearableListView.ViewHolder v) {
		Integer position = (Integer) v.itemView.getTag();

		if (position > 0) {
			boolean newValue = !listItemValues[position];
			listItemValues[position] = newValue;
			int newImageResource = R.drawable.confirmation;
			if (!newValue) {
				newImageResource = R.drawable.deconfirmation;
			}

			((Adapter.ItemViewHolder) v).imageView.setImageResource(newImageResource);

			int preferenceId;
			switch (position) {
				case 1:
					preferenceId = R.string.black_screen_key;
					break;
				case 2:
					preferenceId = R.string.bluetooth_key;
					break;
				default:
					preferenceId = -1;
					break;
			}

			SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(this)
					.edit();
			editor.putBoolean(getResources().getString(preferenceId), newValue);
			editor.apply();
		}
	}

	@Override
	public void onTopEmptyRegionClick() {
	}

	private static final class Adapter extends WearableListView.Adapter {
		private String[] mDataset;
		private final LayoutInflater mInflater;

		public Adapter(Context context, String[] dataset) {
			mInflater = LayoutInflater.from(context);
			mDataset = dataset;
		}

		public static class ItemViewHolder extends WearableListView.ViewHolder {
			private TextView textView;
			private ImageView imageView;

			public ItemViewHolder(View itemView) {
				super(itemView);
				textView = (TextView) itemView.findViewById(R.id.name);
				imageView = (ImageView) itemView.findViewById(R.id.circle);
			}
		}

		@Override
		public WearableListView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			return new ItemViewHolder(mInflater.inflate(R.layout.wear_list_item, null));
		}

		@Override
		public void onBindViewHolder(WearableListView.ViewHolder holder, final int position) {
			final ItemViewHolder itemHolder = (ItemViewHolder) holder;

			itemHolder.textView.setText(mDataset[position]);

			if (position > 0) {
				itemHolder.imageView.setVisibility(View.VISIBLE);

				int imageResource = R.drawable.confirmation;
				if (!listItemValues[position]) {
					imageResource = R.drawable.deconfirmation;
				}
				itemHolder.imageView.setImageResource(imageResource);
			} else {
				itemHolder.imageView.setVisibility(View.GONE);
			}

			holder.itemView.setTag(position);
		}

		@Override
		public int getItemCount() {
			return mDataset.length;
		}
	}
}
