package com.example.nandu.tieredsample;

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

public class CircleView2 extends View {

    Paint bgPaint, fgPaint, textPaint;
    float startX = 0, startY = 0, radius = 150;
    float strokeWidth = 25;
    RectF rectF;
    private float progress;

    public CircleView2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CircleView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CircleView2(Context context) {
        super(context);

        bgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bgPaint.setStyle(Paint.Style.STROKE);
        bgPaint.setStrokeWidth(strokeWidth);
        bgPaint.setColor(Color.GRAY);

        rectF = new RectF();

        fgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        fgPaint.setStyle(Paint.Style.STROKE);
        fgPaint.setStrokeWidth(strokeWidth);
        fgPaint.setColor(Color.GREEN);

        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(40f);
        textPaint.setColor(Color.BLUE);

        rectF.set(100, 100,250, 250);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        final int height = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
        final int width = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);

        Log.d("TIER",
                "Suggested ht:" + getSuggestedMinimumHeight() + " measure spec: " + heightMeasureSpec + "default size "
                        + height);
        Log.d("TIER",
                "Suggested width:" + getSuggestedMinimumWidth() + " measure spec: " + widthMeasureSpec + "default "
                        + "size " + width);

        int min = Math.min(width, height) / 2;

        Log.d("TIER", String.valueOf(min));
        setMeasuredDimension(min, min);
        rectF.set(strokeWidth, strokeWidth,min-strokeWidth, min-strokeWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //canvas.drawText("1/5\ntrips", startX - 20, startY - 20, textPaint);
        Log.d("TIER", "drawing canvas");
        canvas.drawOval(rectF, bgPaint);
        //canvas.drawArc(rectF, 270, progress2, false, fgPaint);
    }

    void setProgressWithAnimation(float progress) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(this, "progress2", progress);
        objectAnimator.setDuration(1500);
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
}
