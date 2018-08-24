package com.example.nandu.tieredsample;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

/**
 * * CircleView is a custom view that contains a outter circle, inner circle with progress.
 * Its radius based on screen can be adjusted and paint color can be changed.
 */
public class CircleView extends View {

    private Paint bgPaint, fgPaint, innerPaint;
    private float strokeWidth = 20;
    private RectF rectF;
    private RectF rectFInner;
    private float progress;
    private int tierSize;
    private boolean isDone;

    public CircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CircleView(Context context, int tierSize) {
        super(context);
        this.tierSize = tierSize;

        bgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bgPaint.setStyle(Paint.Style.STROKE);
        bgPaint.setStrokeWidth(strokeWidth);
        bgPaint.setColor(Color.parseColor("#D3D3D3"));

        rectF = new RectF();
        rectFInner = new RectF();

        fgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        fgPaint.setStyle(Paint.Style.STROKE);
        fgPaint.setStrokeWidth(strokeWidth);
        fgPaint.setColor(Color.parseColor("#47B274"));

        innerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        innerPaint.setStyle(Paint.Style.STROKE);
        innerPaint.setStrokeWidth(strokeWidth);
        innerPaint.setColor(Color.parseColor("#BEE0BF"));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int min = tierSize;

        setMeasuredDimension(min, min);

        rectF.set(0 + strokeWidth / 2, 0 + strokeWidth / 2, min - strokeWidth / 2, min - strokeWidth / 2);
        rectFInner.set(0 + strokeWidth * 3 / 2, 0 + strokeWidth * 3 / 2, min - strokeWidth * 3 / 2, min - strokeWidth * 3 / 2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawOval(rectF, bgPaint);
        canvas.drawArc(rectF, 270, progress * 90 / 25, false, fgPaint);
        if (isDone) {
            canvas.drawOval(rectFInner, innerPaint);
        }
    }

    void setProgressWithAnimation(float progress) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(this, "progress", progress);
        objectAnimator.setDuration(2000);
        objectAnimator.setInterpolator(new DecelerateInterpolator());
        objectAnimator.start();
    }

    float getProgress() {
        return progress;
    }

    void setProgress(float progress) {
        this.progress = progress;
        invalidate();
    }

    /**
     * Control innerCircle visibility based on event.
     */
    public void markComplete() {
        isDone = true;
        invalidate();
    }

    public void setTierRingBGColor(int tierRingColor) {
        bgPaint.setColor(tierRingColor);
        invalidate();
    }

    public void setTierRingFGColor(int tierRingColor) {
        bgPaint.setColor(tierRingColor);
        invalidate();
    }

    public void setInnerTierRingColor(int tierRingColor) {
        innerPaint.setColor(tierRingColor);
        invalidate();
    }

}
