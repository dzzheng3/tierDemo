package com.example.nandu.tieredsample;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

public class TierCircleView extends View {

    Paint bgPaint, fgPaint, textPaint;
    float strokeWidth = 20;
    RectF rectF;
    private float progress;
    Listener listener;

    public TierCircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TierCircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public TierCircleView(Context context, Listener listener) {
        super(context);

        this.listener = listener;

        bgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bgPaint.setStyle(Paint.Style.STROKE);
        bgPaint.setStrokeWidth(strokeWidth);
        bgPaint.setColor(Color.parseColor("#D3D3D3"));

        rectF = new RectF();

        fgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        fgPaint.setStyle(Paint.Style.STROKE);
        fgPaint.setStrokeWidth(strokeWidth);
        fgPaint.setColor(Color.parseColor("#008B00"));

        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(40f);
        textPaint.setColor(Color.BLUE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int min = 350;
        rectF.set(0 + strokeWidth / 2, 0 + strokeWidth / 2, min - strokeWidth / 2, min - strokeWidth / 2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawOval(rectF, bgPaint);
        canvas.drawArc(rectF, 270, progress, false, fgPaint);
    }

    void setProgressWithAnimation(float progress) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(this, "progress", progress);
        objectAnimator.setDuration(2000);
        objectAnimator.setInterpolator(new DecelerateInterpolator());
        objectAnimator.start();
        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                listener.animationEnded();
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

    interface Listener {
        void animationEnded();
    }
}
