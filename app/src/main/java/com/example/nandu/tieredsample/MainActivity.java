package com.example.nandu.tieredsample;

import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import java.util.ArrayList;
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
public class MainActivity extends AppCompatActivity {

    LinearLayout containerLayout;
    LinearLayout containerLayout1;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        containerLayout = findViewById(R.id.container);
        addTierWithText(false);
        //addTierWithLine();
        addTierWithImage(true);
        addTierWithImage(false);


//        CircleView circleView = new CircleView(this);
//        containerLayout.addView(circleView);
//        circleView.setProgressWithAnimation(140);

        // add tier 1 with text

//        LinearLayout testCon = findViewById(R.id.testing);
//        CircleView1 c = new CircleView1(this);
//        testCon.addView(c);
//        CircleView2 c2 = new CircleView2(this);
//        c2.setLayoutParams(new LinearLayout.LayoutParams(
//                ViewGroup.LayoutParams.WRAP_CONTENT,
//                ViewGroup.LayoutParams.WRAP_CONTENT));
//        testCon.addView(c2);
//
//        testCon.getChildAt(0).setBackgroundColor(Color.BLUE);
//        testCon.getChildAt(1).setBackgroundColor(Color.GREEN);

//        progressBar = findViewById(R.id.progressBar);
        //animate(300);

        List<Tier> tiers = new ArrayList<>();
        Tier tier1 = new Tier(CircleState.FINISH, 0, 1, 5, Color.GREEN, Color.BLACK, Color.BLUE,
                "riders", Color.BLACK, "10% off", "1 of 5",
                Color.BLACK, Color.BLACK, Color.BLACK);
        Tier tier2 = new Tier(CircleState.FINISH, 0, 0, 5, Color.GREEN, Color.BLACK, Color.BLUE,
                "riders", Color.BLACK, "20% off", "0 of 5",
                Color.BLACK, Color.BLACK, Color.BLACK);
        Tier tier3 = new Tier(CircleState.ONPROGRESS, 0, 4, 5, Color.GREEN, Color.GRAY, Color.BLUE,
                "riders", Color.GRAY, "30% off", "0 of 5",
                Color.GRAY, Color.GRAY, Color.GRAY);
        tiers.add(tier1);
        tiers.add(tier2);
        tiers.add(tier3);
        TieredProgressPayload tieredProgressPayload = new TieredProgressPayload("save 10% next week", Color.BLACK,
                "take 5 ride", Color.GRAY, 20, 1, 5, Color.GREEN, Color.GRAY,
                "save 10% next week", Color.BLACK, "Detail", Color.BLUE, Color.GRAY, "", tiers);
        containerLayout1 = findViewById(R.id.container1);

        addTiers(containerLayout1, tiers);
    }

    void animate(int progress) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofInt(this, "progressC", progress);
        objectAnimator.setDuration(2000);
        objectAnimator.setInterpolator(new DecelerateInterpolator());
        objectAnimator.start();
    }

    void setProgressC(int progress) {
        progressBar.setProgress(progress);
    }

    private void addTierWithLine() {
        View lineView = new View(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(250, 25);
        lp.gravity = Gravity.CENTER;
        lineView.setLayoutParams(lp);
        lineView.setBackgroundColor(Color.GRAY);

        containerLayout.addView(lineView);
    }

    private void addTiers(LinearLayout containerLayout1, List<Tier> tiers) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        for (int i = 0; i < tiers.size(); i++) {
            Tier tier = tiers.get(i);
            TierView tierView = getTierView();
            tierView.setConnectorFGColor(Color.parseColor("#47B274"));
            tierView.setTierSize(width/(tiers.size()+1));
            containerLayout1.addView(tierView);
            switch (tier.getCircleState()) {
                case FINISH:
                    tierView.setTierIcon(R.drawable.tick);
                    tierView.setBottomText(tier.getTierPrimaryFooterText(), tier.getTierPrimaryFooterTextColor());
                    tierView.setSubBottomText(tier.getTierSecondFooterText(),tier.getTierSencondFooterTextColor());
                    if (i != tiers.size() - 1)
                        tierView.setConnectorVisibility(true);
                    else
                        tierView.setConnectorVisibility(false);

                    CircleView tierCircleView = tierView.getTierCircleView(MainActivity.this);
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

                    tierView.getTierCircleView(MainActivity.this);
                    break;
                case ONPROGRESS:
                    tierView.setTierProgress(tier.getTierProgress());
                    tierView.setTierTotalProgress(tier.getTierTotal());
                    tierView.setTripText(tier.getTierText());
                    tierView.setBottomText(tier.getTierPrimaryFooterText(),tier.getTierPrimaryFooterTextColor());
                    tierView.setSubBottomText(tier.getTierSecondFooterText(), tier.getTierSencondFooterTextColor());
                    if (i != tiers.size() - 1)
                        tierView.setConnectorVisibility(true);
                    else
                        tierView.setConnectorVisibility(false);
                    if(tier.getTierProgress()==0){
                        tierView.getTierCircleView(MainActivity.this).setProgressWithAnimation(5);
                    }else {
                        tierView.getTierCircleView(MainActivity.this).setProgressWithAnimation(
                                100.0f/ tier.getTierTotal()*tier.getTierProgress() );
                    }
                    break;
            }
        }
    }

    private void addTierWithImage(boolean showCustomView) {
        TierView tierView = getTierView();
        containerLayout.addView(tierView);
        tierView.setTierIcon(R.drawable.lock);
        tierView.setBottomText("20% off", Color.BLACK);
        tierView.setConnectorVisibility(showCustomView);
        tierView.getTierCircleView(MainActivity.this);
    }

    private void addTierWithText(boolean isFinish) {

        TierView tierView = getTierView();
        containerLayout.addView(tierView);

        tierView.setTripText("1/5\nrides");
        tierView.setBottomText("10%off", Color.BLACK);
//        tierView.setProgress(100);
        //tierView.animate(300);

        tierView.getTierCircleView(MainActivity.this).setProgressWithAnimation(100);
        //tierView.animate(100);
    }

    TierView getTierView() {
        return (TierView) getLayoutInflater().inflate(R.layout.tier_view, null, false);
    }

    public void click(View view) {

    }
}
