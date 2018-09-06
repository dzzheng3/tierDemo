package com.example.nandu.tieredsample;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Animatable;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;


/* {@link View} for TieredCardView*/
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
        checkView = findViewById(R.id.ub_card_tiered_payment_rewards_check);
        containerLayout = findViewById(R.id.ub__card_payment_rewards_progress_container);

        subTitleContainer = findViewById(R.id.ub__card_payment_rewards_progress_subtitle_container);
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
