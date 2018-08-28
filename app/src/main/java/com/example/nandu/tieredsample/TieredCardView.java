package com.example.nandu.tieredsample;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Animatable;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;


/* Horizontal Scroller
 Based on payload, decide on number of tiers to be shown - 2 or 3
 Drawing tiers
 Based on payload, check if tier 1 is earning state
 CircleText - Add circle view - animate trips, text at bottom
 Add line view if next tier exists
 CircleImage - Add circle view with image, text at bottom if next tier exists
 Add line view if next tier exists
 CircleImage - Add circle view with image, text at bottom if next tier exists


 Based on payload, check if tier 1 is completed
 Based on payload, check if tier 2 is earning state*/
public class TieredCardView extends ConstraintLayout implements CircleView.CompleteListener {

    LinearLayout containerLayout;
    RelativeLayout subTitleContainer;
    private ImageView checkView;

    public TieredCardView(Context context) {
        super(context);
    }

    public TieredCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TieredCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        checkView = findViewById(R.id.iv_check);
        containerLayout = findViewById(R.id.container);

        subTitleContainer = findViewById(R.id.rl_subtitle_container);
    }

    public TierView getTierView(Activity activity) {
        return (TierView) activity.getLayoutInflater().inflate(R.layout.tier_view, null, false);
    }

    @Override
    public void complete() {
        ((Animatable) checkView.getDrawable()).start();
    }

    public void addTierView(TierView tierView) {
        containerLayout.addView(tierView);
    }

    public void addSubTitleCircleView(CircleView tierCircleView) {
        subTitleContainer.addView(tierCircleView);
    }
}
