package com.example.nandu.tieredsample;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

import static com.example.nandu.tieredsample.CircleState.FINISH;

public class MyActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

//        CircleImageView circleImageView = findViewById(R.id.civ);
//        circleImageView.setImageDrawable(getDrawable(R.mipmap.ic_launcher));
//        circleImageView.setBorderWidth(2);



        FrameLayout main = findViewById(R.id.main);

        TieredCardView mainView = getMainView();
        main.addView(mainView);
        List<Tier> tiers = new ArrayList<>();
        Tier tier1 = new Tier(FINISH, 0, 1, 5, Color.GREEN, Color.BLACK, Color.BLUE,
                "riders", Color.BLACK, "10% off", "1 of 5",
                Color.BLACK, Color.BLACK, Color.BLACK);
        Tier tier2 = new Tier(FINISH, 0, 5, 5, Color.GREEN, Color.BLACK, Color.BLUE,
                "riders", Color.BLACK, "20% off", "0 of 5",
                Color.BLACK, Color.BLACK, Color.BLACK);
        Tier tier3 = new Tier(CircleState.UNFINISH, 0, 5, 5, Color.GREEN, Color.GRAY, Color.BLUE,
                "riders", Color.GRAY, "30% off", "0 of 5",
                Color.GRAY, Color.GRAY, Color.GRAY);
        Tier tier4 = new Tier(CircleState.UNFINISH, 0, 5, 5, Color.GREEN, Color.GRAY, Color.BLUE,
                "riders", Color.GRAY, "30% off", "0 of 5",
                Color.GRAY, Color.GRAY, Color.GRAY);
        tiers.add(tier1);
        tiers.add(tier2);
        tiers.add(tier3);
//        tiers.add(tier4);
        TieredProgressPayload tieredProgressPayload = new TieredProgressPayload("save 10% next week", Color.BLACK,
                "take 5 ride", Color.GRAY, 20, 1, 5, Color.GREEN, Color.GRAY,
                "save 10% next week", Color.BLACK, "Detail", Color.BLUE, Color.GRAY, "", tiers);

//        mainView.addTiers(this, tiers);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = (int) (displayMetrics.widthPixels+DisplayUnitUtil.convertDpToPixel(8f,this));
        for (int i = 0; i < tiers.size(); i++) {
            TierView tierView = mainView.getTierView(this);
            tierView.setConnectorFGColor(Color.parseColor("#47B274"));
            tierView.setTierSize(width / 4);
//            tierView.setTierSize(width / (tiers.size() + 1));
            mainView.addTierView(tierView);
            Tier tier = tiers.get(i);
            switch (tier.getCircleState()) {
                case FINISH:
                    tierView.setTierIcon(R.drawable.tick);
                    tierView.setBottomText(tier.getTierPrimaryFooterText(), tier.getTierPrimaryFooterTextColor());
                    tierView.setSubBottomText(tier.getTierSecondFooterText(), tier.getTierSencondFooterTextColor());
                    if (i != tiers.size() - 1)
                        tierView.setConnectorVisibility(true);
                    else
                        tierView.setConnectorVisibility(false);

                    CircleView tierCircleView = tierView.getCircleView(this, mainView);
                    tierCircleView.markComplete();
                    tierCircleView.setInnerTierRingColor(Color.parseColor("#BEE0BF"));
                    tierCircleView.setProgress(100);
                    tierView.setConnectorComplete();
                    break;
                case UNFINISH:
                    tierView.setTierIcon(R.drawable.lock);
                    tierView.setBottomText(tier.getTierPrimaryFooterText(), tier.getTierPrimaryFooterTextColor());
                    tierView.setSubBottomText(tier.getTierSecondFooterText(), tier.getTierSencondFooterTextColor());
                    if (i != tiers.size() - 1)
                        tierView.setConnectorVisibility(true);
                    else
                        tierView.setConnectorVisibility(false);

                    tierView.getCircleView(this, mainView);
                    break;
                case ONPROGRESS:
                    tierView.setTierProgress(tier.getTierProgress());
                    tierView.setTierTotalProgress(tier.getTierTotal());
                    tierView.setTripText(tier.getTierText());
                    tierView.setBottomText(tier.getTierPrimaryFooterText(), tier.getTierPrimaryFooterTextColor());
                    tierView.setSubBottomText(tier.getTierSecondFooterText(), tier.getTierSencondFooterTextColor());
                    if (i != tiers.size() - 1)
                        tierView.setConnectorVisibility(true);
                    else
                        tierView.setConnectorVisibility(false);
                    if (tier.getTierProgress() == 0) {
                        tierView.getCircleView(this, mainView).setProgressWithAnimation(5);
                    } else {
                        tierView.getCircleView(this, mainView).setProgressWithAnimation(
                                100.0f / tier.getTierTotal() * tier.getTierProgress());
                    }
                    break;
            }
        }

//        mainView.addCircle(this, tier2);
        CircleView tierCircleView = new CircleView(this, mainView, 100);
        switch (tier2.getCircleState()) {
            case ONPROGRESS:
                if (tier2.getTierProgress() == 0) {
                    tierCircleView.setProgressWithAnimation(5);
                } else {
                    tierCircleView.setProgressWithAnimation(
                            100.0f / tier2.getTierTotal() * tier2.getTierProgress());
                }
                break;
            case FINISH:
                tierCircleView.setShowInnerFill(true);
                tierCircleView.setProgressWithAnimation(
                        100.0f / tier2.getTierTotal() * tier2.getTierProgress());
                break;
        }
        mainView.addSubTitleCircleView(tierCircleView);
    }

    TieredCardView getMainView() {
        return (TieredCardView) getLayoutInflater().inflate(R.layout.tiered_car_view, null, false);
    }
}
