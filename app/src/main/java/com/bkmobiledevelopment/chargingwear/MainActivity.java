package com.bkmobiledevelopment.chargingwear;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.wearable.view.WearableListView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity implements WearableListView.ClickListener {

	private final String[] listItems = {"Show black screen while charging",
			"Disable bluetooth while charging"};
	public static boolean[] listItemValues = {true, true};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		WearableListView listView = (WearableListView) findViewById(R.id.wearable_list);
		listView.setAdapter(new Adapter(this, listItems));
		listView.setClickListener(this);

		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
		final Resources resources = getResources();

		listItemValues[0] = preferences.getBoolean(resources.getString(R.string.black_screen_key),
				true);
		listItemValues[1] = preferences.getBoolean(resources.getString(R.string.bluetooth_key),
				true);
	}

	@Override
	public void onClick(WearableListView.ViewHolder v) {
		Integer position = (Integer) v.itemView.getTag();
	}

	@Override
	public void onTopEmptyRegionClick() {
	}

	private static final class Adapter extends WearableListView.Adapter {
		private String[] mDataset;
		private final Context mContext;
		private final LayoutInflater mInflater;

		// Provide a suitable constructor (depends on the kind of dataset)
		public Adapter(Context context, String[] dataset) {
			mContext = context;
			mInflater = LayoutInflater.from(context);
			mDataset = dataset;
		}

		// Provide a reference to the type of views you're using
		public static class ItemViewHolder extends WearableListView.ViewHolder {
			private TextView textView;
			private ImageView imageView;

			public ItemViewHolder(View itemView) {
				super(itemView);
				// find the text view within the custom item's layout
				textView = (TextView) itemView.findViewById(R.id.name);
				imageView = (ImageView) itemView.findViewById(R.id.circle);
			}
		}

		// Create new views for list items
		// (invoked by the WearableListView's layout manager)
		@Override
		public WearableListView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			// Inflate our custom layout for list items

			return new ItemViewHolder(mInflater.inflate(R.layout.wear_list_item, parent));
		}

		// Replace the contents of a list item
		// Instead of creating new views, the list tries to recycle existing ones
		// (invoked by the WearableListView's layout manager)
		@Override
		public void onBindViewHolder(WearableListView.ViewHolder holder, final int position) {
			// retrieve the text view
			ItemViewHolder itemHolder = (ItemViewHolder) holder;
			// replace text contents
			itemHolder.textView.setText(mDataset[position]);
			// replace image contents
			int imageResource = R.drawable.confirmation;
			if (!listItemValues[position]) {
				imageResource = R.drawable.deconfirmation;
			}
			itemHolder.imageView.setImageResource(imageResource);

			// replace list item's metadata
			holder.itemView.setTag(position);
			holder.itemView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					boolean newValue = !listItemValues[position];
					int newImageResource = R.drawable.confirmation;
					if (!newValue) {
						newImageResource = R.drawable.deconfirmation;
					}
					((ImageView) v).setImageResource(newImageResource);
					changeBooleanPreference(position, newValue);
				}
			});
		}

		public void changeBooleanPreference(int position, boolean newValue) {
			int preferenceId;
			switch (position) {
				case 0:
					preferenceId = R.string.black_screen_key;
					break;
				case 1:
					preferenceId = R.string.bluetooth_key;
					break;
				default:
					preferenceId = -1;
					break;
			}

			SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(
					mContext).edit();
			editor.putBoolean(mContext.getResources().getString(preferenceId), newValue);
			editor.apply();
		}

		// Return the size of your dataset
		// (invoked by the WearableListView's layout manager)
		@Override
		public int getItemCount() {
			return mDataset.length;
		}
	}
}
