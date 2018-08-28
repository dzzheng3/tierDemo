package com.example.nandu.tieredsample;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;

/**
 * * CircleView is a custom view that contains a outter circle, inner circle with progress.
 * Its radius based on screen can be adjusted and paint color can be changed.
 */
public class CircleView extends View {

    private Paint bgPaint, fgPaint, innerPaint, innerFillPaint;
    private float strokeWidth = 20;
    private RectF rectF;
    private RectF rectFInner;
    private float progress, radius;
    private CompleteListener listener;
    private int tierSize;
    private boolean isDone, isShowInnerFill;
    private ObjectAnimator progressAnimator;
    private ValueAnimator innerCircleAnimator;

    public CircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CircleView(Context context, CompleteListener listener, int tierSize) {
        super(context);
        this.listener = listener;
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

        innerFillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        innerFillPaint.setStyle(Paint.Style.FILL);
        innerFillPaint.setColor(Color.parseColor("#FFFFFF"));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int min = tierSize;
        setMeasuredDimension(min, min);

        rectF.set(0 + strokeWidth / 2, 0 + strokeWidth / 2, min - strokeWidth / 2, min - strokeWidth / 2);
        rectFInner.set(0 + strokeWidth * 3 / 2, 0 + strokeWidth * 3 / 2, min - strokeWidth * 3 / 2, min - strokeWidth * 3 / 2);
        radius = rectF.width() / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawOval(rectF, bgPaint);
        canvas.drawArc(rectF, 270, progress * 90 / 25, false, fgPaint);
        if (isDone) {
            canvas.drawOval(rectFInner, innerPaint);
        }
        if (isShowInnerFill) {
            canvas.drawCircle(rectFInner.centerX(), rectFInner.centerY(), radius, innerFillPaint);
        }
    }

    void setProgressWithAnimation(final float progress) {
        progressAnimator = ObjectAnimator.ofFloat(this, "progress", progress);
        progressAnimator.setDuration(2000);
        progressAnimator.setInterpolator(new LinearInterpolator());
        progressAnimator.start();
        progressAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (progress == 100 && isShowInnerFill) {
                    fgPaint.setStyle(Paint.Style.FILL);
                    bgPaint.setStyle(Paint.Style.FILL);
                    rectF.set(0, 0, tierSize, tierSize);
                    showInnerCircleAnimation();
                }
            }
        });
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

    public void setShowInnerFill(boolean isShowInnerFill) {
        this.isShowInnerFill = isShowInnerFill;
    }

    public void showInnerCircleAnimation() {
        innerCircleAnimator = ValueAnimator.ofFloat(0, 1).setDuration(200);
        innerCircleAnimator.setInterpolator(new DecelerateInterpolator());
        innerCircleAnimator.start();
        innerCircleAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                isShowInnerFill = true;
                float value = 1 - (float) animation.getAnimatedValue(); // 1f ~ 0f
                radius *= value;
                invalidate();
            }
        });
        innerCircleAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                listener.complete();
                invalidate();
            }
        });

    }

    public interface CompleteListener {
        void complete();
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

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (progressAnimator != null){
            progressAnimator.cancel();
            progressAnimator = null;
        }
        if (innerCircleAnimator != null) {
            innerCircleAnimator.cancel();
            innerCircleAnimator = null;
        }
    }
}
