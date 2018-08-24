package com.example.nandu.tieredsample;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * * TierView is a custom view that contains a outter circle, inner circle and dash with progress.
 * Inside circle contain TextView and Image which are settable.
 * Under circle also contain TextView
 * Its radius based on screen can be adjustable and paint color can be changed.
 */
public class TierView extends ConstraintLayout {

    private CircleView circleView;
    private TextView tierProgress;
    private TextView subLabel;
    private TextView tierTotalProgress;
    private int tierSize;

    public TierView(Context context) {
        super(context);
    }

    public TierView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TierView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    TextView tripText, bottomText, subBottomText;
    ImageView tierImageView;
    RelativeLayout tierContainer;
    ProgressBar connectorView;

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        tripText = findViewById(R.id.tripText);
        tierImageView = findViewById(R.id.tierIcon);
        bottomText = findViewById(R.id.discount_bottom);
        subBottomText = findViewById(R.id.discount_bottom_sub_text);
        tierContainer = findViewById(R.id.tierContainer);
        connectorView = findViewById(R.id.connector);
        subLabel = findViewById(R.id.tv_sub_label);
        tierTotalProgress = findViewById(R.id.tv_tier_total);
        tierProgress = findViewById(R.id.tv_tier_progress);
    }

    void setTripText(String trips) {
        tripText.setVisibility(View.VISIBLE);
        tripText.setText(trips);
    }


    void setTierIcon(int resourceId) {
        tierImageView.setVisibility(View.VISIBLE);
        tierImageView.setImageResource(resourceId);
    }

    void setProgress(int progress) {
        connectorView.setProgress(progress);
    }

    void setConnectorVisibility(boolean visibilty) {
        connectorView.setVisibility(visibilty ? VISIBLE : GONE);
    }

    /**
     * Get the CircleView which can be adjusted
     *
     * @param context
     * @return CircleView
     */
    CircleView getTierCircleView(Context context) {
        circleView = new CircleView(context, tierSize);
        tierContainer.addView(circleView);
        return circleView;
    }

    void setBottomText(String bottomText, int bottomTextColor) {
        this.bottomText.setVisibility(View.VISIBLE);
        this.bottomText.setText(bottomText);
        this.bottomText.setTextColor(bottomTextColor);
    }

    public void setSubBottomText(String subBottomText, int subBottomTextColor) {
        this.subBottomText.setVisibility(View.VISIBLE);
        this.subBottomText.setText(subBottomText);
        this.subBottomText.setTextColor(subBottomTextColor);
    }

    public void setTierProgress(int tierProgress) {
        this.tierProgress.setText(tierProgress + "");
    }

    public void setTierTotalProgress(int tierTotal) {
        this.tierTotalProgress.setText("/" + tierTotal);
    }

    public void setConnectorComplete() {
        connectorView.setProgress(100);
    }

    public void setTierSize(int tiersSize) {
        this.tierSize = tiersSize;
        resetConnectorSize(tiersSize);
    }

    /**
     * Adjust the the connector length.
     *
     * @param tiersSize
     */
    private void resetConnectorSize(int tiersSize) {
        LayoutParams layoutParams = (LayoutParams) connectorView.getLayoutParams();
        layoutParams.width = tiersSize / 3;
        connectorView.setLayoutParams(layoutParams);
    }

    public void setConnectorFGColor(int tierRingColor) {
        LayerDrawable progressBarDrawable = (LayerDrawable) connectorView.getProgressDrawable();
        Drawable progressDrawable = progressBarDrawable.getDrawable(1);
        progressDrawable.setColorFilter(tierRingColor, PorterDuff.Mode.SRC_IN);
    }
}
