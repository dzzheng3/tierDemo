package com.example.nandu.tieredsample;

class Tier {
    private int tierInitialProgress;
    private int tierProgress;
    private int tierTotal;
    private int tierProgressColor;
    private int tierRingColor;
    private int tierInnerRingColor;
    private String tierText;
    private int tierTextColor;
    private String tierPrimaryFooterText;
    private String tierSecondFooterText;
    private int tierPrimaryFooterTextColor;
    private int tierSencondFooterTextColor;

    public int getTierInitialProgress() {
        return tierInitialProgress;
    }

    public int getTierProgress() {
        return tierProgress;
    }

    public int getTierTotal() {
        return tierTotal;
    }

    public int getTierProgressColor() {
        return tierProgressColor;
    }

    public int getTierRingColor() {
        return tierRingColor;
    }

    public int getTierInnerRingColor() {
        return tierInnerRingColor;
    }

    public String getTierText() {
        return tierText;
    }

    public int getTierTextColor() {
        return tierTextColor;
    }

    public String getTierPrimaryFooterText() {
        return tierPrimaryFooterText;
    }

    public String getTierSecondFooterText() {
        return tierSecondFooterText;
    }

    public int getTierPrimaryFooterTextColor() {
        return tierPrimaryFooterTextColor;
    }

    public int getTierSencondFooterTextColor() {
        return tierSencondFooterTextColor;
    }

    public int getTierConnectorColor() {
        return tierConnectorColor;
    }

    public Tier(int tierInitialProgress, int tierProgress, int tierTotal, int tierProgressColor, int tierRingColor, int tierInnerRingColor, String tierText, int tierTextColor, String tierPrimaryFooterText, String tierSecondFooterText, int tierPrimaryFooterTextColor, int tierSencondFooterTextColor, int tierConnectorColor) {

        this.tierInitialProgress = tierInitialProgress;
        this.tierProgress = tierProgress;
        this.tierTotal = tierTotal;
        this.tierProgressColor = tierProgressColor;
        this.tierRingColor = tierRingColor;
        this.tierInnerRingColor = tierInnerRingColor;
        this.tierText = tierText;
        this.tierTextColor = tierTextColor;
        this.tierPrimaryFooterText = tierPrimaryFooterText;
        this.tierSecondFooterText = tierSecondFooterText;
        this.tierPrimaryFooterTextColor = tierPrimaryFooterTextColor;
        this.tierSencondFooterTextColor = tierSencondFooterTextColor;
        this.tierConnectorColor = tierConnectorColor;
    }

    private int tierConnectorColor;
}
