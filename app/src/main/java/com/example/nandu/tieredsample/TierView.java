package com.example.nandu.tieredsample;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class TierView extends ConstraintLayout implements CircleView.Listener {

    private CircleView circleView;
    private TextView tierProgress;
    private TextView subLabel;
    private TextView tierTotalProgress;

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
        tierProgress = findViewById(R.id.tv_tier_progress);
        tierTotalProgress = findViewById(R.id.tv_tier_total);

    }

    void setTripText(String trips) {
        tripText.setVisibility(View.VISIBLE);
        tripText.setText(trips);
        int location[] = new int[2];
        tripText.getLocationOnScreen(location);
        Log.d("TIER", "Trip text location: " + location[0] + " " + location[1]);
    }

    void setDiscountBottomText(String discount, int tierPrimaryFooterTextColor) {
        bottomText.setVisibility(View.VISIBLE);
        bottomText.setText(discount);
        bottomText.setTextColor(tierPrimaryFooterTextColor);
        int location[] = new int[2];
        bottomText.getLocationInWindow(location);
        Log.d("TIER", "Discount bottom text location: " + location[0] + " " + location[1]);
    }

    void setTierIcon(int resourceId) {
        tierImageView.setVisibility(View.VISIBLE);
        tierImageView.setImageResource(resourceId);
    }

    void animate(int progress) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofInt(this, "progress", progress);
        objectAnimator.setDuration(1000);
        objectAnimator.setInterpolator(new DecelerateInterpolator());
        objectAnimator.start();
        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                circleView.markComplete();
                circleView.invalidate();

            }
        });
    }

    void setProgress(int progress) {
        connectorView.setProgress(progress);
    }

    void setConnectorVisibility(boolean visibilty) {
        connectorView.setVisibility(visibilty ? VISIBLE : GONE);
    }

    CircleView getTierCircleView(Context context) {
        Log.d("TIER", "before init circle");
        circleView = new CircleView(context, this);
        Log.d("TIER", "after init circle");
        tierContainer.addView(circleView);
        Log.d("TIER", "after adding view circle");
        int location[] = new int[2];
        circleView.getLocationOnScreen(location);
        Log.d("TIER", "Circle location: " + location[0] + " " + location[1]);
        return circleView;
    }

    @Override
    public void animationEnded() {
//        setTripText("\u2713");
//        bottomText.setTypeface(null, Typeface.BOLD);
//        bottomText.setTextColor(Color.BLACK);
//        animate(100);
    }

    public void setSubDiscountBottomText(String subDiscountBottomText, int tierSencondFooterTextColor) {
        subBottomText.setVisibility(View.VISIBLE);
        subBottomText.setText(subDiscountBottomText);
        subBottomText.setTextColor(tierSencondFooterTextColor);
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
}
