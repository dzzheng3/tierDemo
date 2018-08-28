package com.example.nandu.tieredsample;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Animatable;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.List;


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

    LinearLayout containerLayout1;
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
        containerLayout1 = findViewById(R.id.container1);

//        addTiers(tiers);
        subTitleContainer = findViewById(R.id.rl_subtitle_container);
//        addCircle(tier2);
    }

    public void addCircle(Activity activity, Tier tier) {
//        CircleView tierCircleView = new CircleView(activity, this, 80);
//        switch (tier.getCircleState()) {
//            case ONPROGRESS:
//                if (tier.getTierProgress() == 0) {
//                    tierCircleView.setProgressWithAnimation(5);
//                } else {
//                    tierCircleView.setProgressWithAnimation(
//                            100.0f / tier.getTierTotal() * tier.getTierProgress());
//                }
//                break;
//            case FINISH:
//                tierCircleView.setShowInnerFill(true);
//                tierCircleView.setProgressWithAnimation(
//                        100.0f / tier.getTierTotal() * tier.getTierProgress());
//                break;
//        }
//        subTitleContainer.addView(tierCircleView);
    }


    public void addTiers(Activity activity, List<Tier> tiers) {
//        DisplayMetrics displayMetrics = new DisplayMetrics();
//        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
//        int width = displayMetrics.widthPixels;
//        for (int i = 0; i < tiers.size(); i++) {
//            Tier tier = tiers.get(i);
//            TierView tierView = getTierView(activity);
//            tierView.setConnectorFGColor(Color.parseColor("#47B274"));
//            tierView.setTierSize(width / (tiers.size() + 1));
//            containerLayout1.addView(tierView);
//            switch (tier.getCircleState()) {
//                case FINISH:
//                    tierView.setTierIcon(R.drawable.tick);
//                    tierView.setBottomText(tier.getTierPrimaryFooterText(), tier.getTierPrimaryFooterTextColor());
//                    tierView.setSubBottomText(tier.getTierSecondFooterText(), tier.getTierSencondFooterTextColor());
//                    if (i != tiers.size() - 1)
//                        tierView.setConnectorVisibility(true);
//                    else
//                        tierView.setConnectorVisibility(false);
//
//                    CircleView tierCircleView = tierView.getTierCircleView(activity, this);
//                    tierCircleView.markComplete();
//                    tierCircleView.setInnerTierRingColor(Color.parseColor("#BEE0BF"));
//                    tierCircleView.setProgress(100);
//                    tierView.setConnectorComplete();
//                    break;
//                case UNFINISH:
//                    tierView.setTierIcon(R.drawable.lock);
//                    tierView.setBottomText(tier.getTierPrimaryFooterText(), tier.getTierPrimaryFooterTextColor());
//                    tierView.setSubBottomText(tier.getTierSecondFooterText(), tier.getTierSencondFooterTextColor());
//                    if (i != tiers.size() - 1)
//                        tierView.setConnectorVisibility(true);
//                    else
//                        tierView.setConnectorVisibility(false);
//
//                    tierView.getTierCircleView(activity, this);
//                    break;
//                case ONPROGRESS:
//                    tierView.setTierProgress(tier.getTierProgress());
//                    tierView.setTierTotalProgress(tier.getTierTotal());
//                    tierView.setTripText(tier.getTierText());
//                    tierView.setBottomText(tier.getTierPrimaryFooterText(), tier.getTierPrimaryFooterTextColor());
//                    tierView.setSubBottomText(tier.getTierSecondFooterText(), tier.getTierSencondFooterTextColor());
//                    if (i != tiers.size() - 1)
//                        tierView.setConnectorVisibility(true);
//                    else
//                        tierView.setConnectorVisibility(false);
//                    if (tier.getTierProgress() == 0) {
//                        tierView.getTierCircleView(activity, this).setProgressWithAnimation(5);
//                    } else {
//                        tierView.getTierCircleView(activity, this).setProgressWithAnimation(
//                                100.0f / tier.getTierTotal() * tier.getTierProgress());
//                    }
//                    break;
//            }
//        }
    }

    public TierView getTierView(Activity activity) {
        return (TierView) activity.getLayoutInflater().inflate(R.layout.tier_view, null, false);
    }

    @Override
    public void complete() {
        ((Animatable) checkView.getDrawable()).start();
    }

    public void addTierView(TierView tierView) {
        containerLayout1.addView(tierView);
    }

    public void addSubTitleCircleView(CircleView tierCircleView) {
        subTitleContainer.addView(tierCircleView);
    }
}
