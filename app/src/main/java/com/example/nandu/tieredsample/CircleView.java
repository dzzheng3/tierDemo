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

public class CircleView extends View {

    Paint bgPaint, fgPaint, textPaint, innerPaint;
    float startX = 0, startY = 0;
    float strokeWidth = 20;
    RectF rectF;
    RectF rectFInner;
    private float progress;
    Listener listener;
    private int tierSize;

    public CircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CircleView(Context context, Listener listener, int tierSize) {
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

//        textPaint = new Paint();
//        textPaint.setAntiAlias(true);
//        textPaint.setTextSize(40f);
//        textPaint.setColor(Color.parseColor(Color.BLUE));
        Log.e("TIER",
                "11111111");
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        final int height = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
        final int width = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);

        Log.e("TIER",
                "Suggested ht:" + getSuggestedMinimumHeight() + " measure spec: " + heightMeasureSpec + "default size "
                        + height);
        Log.d("TIER",
                "Suggested width:" + getSuggestedMinimumWidth() + " measure spec: " + widthMeasureSpec + "default "
                        + "size " + width);

        //final int min = Math.min(width, height) / 2;
        int min = tierSize;

        Log.d("TIER-------", String.valueOf(min));
        setMeasuredDimension(min, min);
        rectF.set(0 + strokeWidth / 2, 0 + strokeWidth / 2, min - strokeWidth / 2, min - strokeWidth / 2);
        rectFInner.set(0 + strokeWidth * 3 / 2, 0 + strokeWidth * 3 / 2, min - strokeWidth * 3 / 2, min - strokeWidth * 3 / 2);
        Log.d("TIER", "values start pt: " + strokeWidth / 2 + " end pt: " + (min - strokeWidth / 2));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //canvas.drawText("1/5\ntrips", startX - 20, startY - 20, textPaint);
        Log.d("TIER", "drawing canvas");
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

    private boolean isDone;

    public void markComplete() {
        isDone = true;
    }

    public void setTierRingBGColor(int tierRingColor) {
        bgPaint.setColor(tierRingColor);
    }
    public void setTierRingFGColor(int tierRingColor) {
        bgPaint.setColor(tierRingColor);
    }

    public void setInnerTierRingColor(int tierRingColor) {
        innerPaint.setColor(tierRingColor);
    }

    interface Listener {
        void animationEnded();
    }
}
