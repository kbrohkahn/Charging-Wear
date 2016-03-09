package com.bkmobiledevelopment.chargingwear;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.wearable.view.WearableListView;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by kbrohkahn on 3/8/2016.
 * Class for layout appearing in list of settings
 */
public class SettingListItemLayout extends LinearLayout
		implements WearableListView.OnCenterProximityListener {

	private ImageView mCircle;
	private TextView mName;

	private final float mFadedTextAlpha;
	private final int mFadedCircleColor;
	private final int mChosenCircleColor;

	public SettingListItemLayout(Context context) {
		this(context, null);
	}

	public SettingListItemLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public SettingListItemLayout(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);

		mFadedTextAlpha = 50 / 100f;
		mFadedCircleColor = getResources().getColor(R.color.grey);
		mChosenCircleColor = getResources().getColor(R.color.blue_darkest);
	}

	// Get references to the icon and text in the item layout definition
	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		// These are defined in the layout file for list items
		// (see next section)
		mCircle = (ImageView) findViewById(R.id.circle);
		mName = (TextView) findViewById(R.id.name);
	}

	@Override
	public void onCenterPosition(boolean animate) {
		mName.setAlpha(1f);
		((GradientDrawable) mCircle.getDrawable()).setColor(mChosenCircleColor);
	}

	@Override
	public void onNonCenterPosition(boolean animate) {
		((GradientDrawable) mCircle.getDrawable()).setColor(mFadedCircleColor);
		mName.setAlpha(mFadedTextAlpha);
	}
}
