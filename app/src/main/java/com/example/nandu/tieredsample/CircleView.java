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
    private int tierSize;
    private boolean isDone, isShowInnerFill;

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

    //线1的x轴
    private int line1_x = 0;
    //线1的y轴
    private int line1_y = 0;
    //线2的x轴
    private int line2_x = 0;
    //线2的y轴
    private int line2_y = 0;

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
        if (isDrawLine) {

            /**
             * 绘制圆弧
             */
            Paint paint = new Paint();
            //设置画笔颜色
            paint.setColor(Color.RED);
            //设置圆弧的宽度
            paint.setStrokeWidth(5);
            //设置圆弧为空心
            paint.setStyle(Paint.Style.STROKE);
            //消除锯齿
            paint.setAntiAlias(true);

            //获取圆心的x坐标
            int center = getWidth() / 2;
            int center1 = center - getWidth() / 5;
            //圆弧半径
            int radius = getWidth() / 2 - 5;

            if (progress >= 0) {
                if (line1_x < radius / 3) {
                    line1_x++;
                    line1_y++;
                }
                //画第一根线
                canvas.drawLine(center1, center, center1 + line1_x, center + line1_y, paint);

                if (line1_x == radius / 3) {
                    line2_x = line1_x;
                    line2_y = line1_y;
                    line1_x++;
                    line1_y++;
                }
                if (line1_x >= radius / 3 && line2_x <= radius) {
                    line2_x++;
                    line2_y--;
                }
                //画第二根线
                canvas.drawLine(center1 + line1_x - 1, center + line1_y, center1 + line2_x, center + line2_y, paint);
            }

            //每隔10毫秒界面刷新
            postInvalidateDelayed(10);
        }
    }

    void setProgressWithAnimation(final float progress) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(this, "progress", progress);
        objectAnimator.setDuration(2000);
        objectAnimator.setInterpolator(new DecelerateInterpolator());
        objectAnimator.start();
        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (progress == 100 && isShowInnerFill) {
                    fgPaint.setStyle(Paint.Style.FILL);
                    bgPaint.setStyle(Paint.Style.FILL);
                    rectF.set(0, 0, tierSize, tierSize);
                    showCheckWithAnimation();
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

    private boolean isDrawLine;

    public void showCheckWithAnimation() {
        ValueAnimator va = ValueAnimator.ofFloat(0, 1).setDuration(10000);
        va.setInterpolator(new DecelerateInterpolator());
        va.start();
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                isShowInnerFill = true;
                float value = 1 - (float) animation.getAnimatedValue(); // 1f ~ 0f
                radius *= value;
                invalidate();
            }
        });
        va.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                isDrawLine = true;
                invalidate();
            }
        });

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
