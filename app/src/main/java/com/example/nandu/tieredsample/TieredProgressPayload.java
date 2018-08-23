package com.example.nandu.tieredsample;

import java.util.List;

public class TieredProgressPayload {
    private String lablelText;
    private int labelTextColor;
    private String labelSubText;
    private int labelSubTextColor;
    private int initialProgress;
    private int labelTierProgress;//1/5
    private int labelTierTotal;
    private int labelTierProgressColor;
    private int labelTierRingColor;
    private String headlineText;
    private int headlineTextColor;
    private String CtaText;
    private int CtaTextColor;
    private int CtaSeparatorColor;
    private String CtaURL;
    private List<Tier> tiers;

    public String getLablelText() {
        return lablelText;
    }

    public int getLabelTextColor() {
        return labelTextColor;
    }

    public String getLabelSubText() {
        return labelSubText;
    }

    public int getLabelSubTextColor() {
        return labelSubTextColor;
    }

    public int getInitialProgress() {
        return initialProgress;
    }

    public int getLabelTierProgress() {
        return labelTierProgress;
    }

    public int getLabelTierTotal() {
        return labelTierTotal;
    }

    public int getLabelTierProgressColor() {
        return labelTierProgressColor;
    }

    public int getLabelTierRingColor() {
        return labelTierRingColor;
    }

    public String getHeadlineText() {
        return headlineText;
    }

    public int getHeadlineTextColor() {
        return headlineTextColor;
    }

    public String getCtaText() {
        return CtaText;
    }

    public int getCtaTextColor() {
        return CtaTextColor;
    }

    public int getCtaSeparatorColor() {
        return CtaSeparatorColor;
    }

    public String getCtaURL() {
        return CtaURL;
    }

    public List<Tier> getTiers() {
        return tiers;
    }

    public TieredProgressPayload(String lablelText, int labelTextColor, String labelSubText, int labelSubTextColor, int initialProgress, int labelTierProgress, int labelTierTotal, int labelTierProgressColor, int labelTierRingColor, String headlineText, int headlineTextColor, String ctaText, int ctaTextColor, int ctaSeparatorColor, String ctaURL, List<Tier> tiers) {

        this.lablelText = lablelText;
        this.labelTextColor = labelTextColor;
        this.labelSubText = labelSubText;
        this.labelSubTextColor = labelSubTextColor;
        this.initialProgress = initialProgress;
        this.labelTierProgress = labelTierProgress;
        this.labelTierTotal = labelTierTotal;
        this.labelTierProgressColor = labelTierProgressColor;
        this.labelTierRingColor = labelTierRingColor;
        this.headlineText = headlineText;
        this.headlineTextColor = headlineTextColor;
        CtaText = ctaText;
        CtaTextColor = ctaTextColor;
        CtaSeparatorColor = ctaSeparatorColor;
        CtaURL = ctaURL;
        this.tiers = tiers;
    }
}
