package com.example.nandu.tieredsample;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.ColorInt;
import android.support.annotation.IntRange;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;


/**
 * This circular ImageView has a transparent background and a one pixel border by default. This
 * border can be set explicitly using the public methods or xml attributes provided. It is
 * recommended to only use a {@link ImageView.ScaleType} of {@code CENTER_CROP} and
 * the {@code noFade()} option if using Picasso.
 */
@SuppressWarnings("StringFormatNoLocale")
public class CircleImageView extends android.support.v7.widget.AppCompatImageView {

  private static final ImageView.ScaleType SCALE_TYPE = ImageView.ScaleType.CENTER_CROP;
  private static final Bitmap.Config BITMAP_CONFIG = Bitmap.Config.ARGB_8888;
  private static final int COLORDRAWABLE_DIMENSION = 1;
  private static final int DEFAULT_BORDER_WIDTH = 1;
  @ColorInt private static final int DEFAULT_BORDER_COLOR = Color.WHITE;
  private static final float HALF = .5f;

  private final RectF drawableRect = new RectF();
  private final RectF borderRect = new RectF();
  private final Matrix shaderMatrix = new Matrix();
  private final Paint bitmapPaint = new Paint();
  private final Paint borderPaint = new Paint();

  @ColorInt private int borderColor = DEFAULT_BORDER_COLOR;
  private int borderWidth = DEFAULT_BORDER_WIDTH;
  private int bitmapWidth;
  private int bitmapHeight;
  private float drawableRadius;
  private float borderRadius;
  private boolean ready;
  private boolean setupPending;

  @Nullable private Bitmap bitmap;

  /**
   * Constructor.
   *
   * @param context used to construct a view.
   */
  public CircleImageView(Context context) {
    super(context);

    init();
  }

  /**
   * Constructor.
   *
   * @param context used to construct a view.
   * @param attrs attributes, may be null.
   */
  public CircleImageView(Context context, @Nullable AttributeSet attrs) {
    this(context, attrs, 0);
  }

  /**
   * Constructor.
   *
   * @param context used to construct a view.
   * @param attrs attributes, may be null.
   * @param defStyle style.
   */
  public CircleImageView(Context context, @Nullable AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);

    TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CircleImageView, defStyle, 0);

    borderColor =
        a.getColor(R.styleable.CircleImageView_circleImageBorderColor, DEFAULT_BORDER_COLOR);
    borderWidth =
        a.getDimensionPixelSize(
            R.styleable.CircleImageView_circleImageBorderWidth, DEFAULT_BORDER_WIDTH);

    a.recycle();

    init();
  }

  @Override
  public ScaleType getScaleType() {
    return SCALE_TYPE;
  }

  @Override
  public void setScaleType(ScaleType scaleType) {
    if (scaleType != SCALE_TYPE) {
      throw new IllegalArgumentException(String.format("ScaleType %s not supported.", scaleType));
    }
  }

  @Override
  protected void onDraw(Canvas canvas) {
    if (getDrawable() == null) {
      return;
    }

    canvas.drawCircle(getWidth() / 2, getHeight() / 2, drawableRadius, bitmapPaint);
    if (borderWidth != 0) {
      canvas.drawCircle(getWidth() / 2, getHeight() / 2, borderRadius, borderPaint);
    }
  }

  @Override
  protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);
    setup();
  }

  @Override
  public void setImageBitmap(Bitmap bm) {
    super.setImageBitmap(bm);
    bitmap = bm;
    setup();
  }

  @Override
  public void setImageDrawable(Drawable drawable) {
    super.setImageDrawable(drawable);
    bitmap = getBitmapFromDrawable(drawable);
    setup();
  }

  @Override
  public void setImageResource(int resId) {
    super.setImageResource(resId);
    bitmap = getBitmapFromDrawable(getDrawable());
    setup();
  }

  @Override
  public void setImageURI(Uri uri) {
    super.setImageURI(uri);
    bitmap = getBitmapFromDrawable(getDrawable());
    setup();
  }

  private void init() {
    super.setScaleType(SCALE_TYPE);
    ready = true;

    if (setupPending) {
      setup();
      setupPending = false;
    }
  }

  /**
   * Get the CircleImageView border width in pixels.
   *
   * @return The CircleImageView border width. {@code 1px} by default.
   */
  @IntRange(from = 0)
  public int getBorderWidth() {
    return borderWidth;
  }

  /**
   * Set the CircleImageView border width.
   *
   * @param borderWidth The CircleImageView border width.
   */
  public void setBorderWidth(@IntRange(from = 0) int borderWidth) {
    if (borderWidth == this.borderWidth) {
      return;
    }

    this.borderWidth = borderWidth;
    setup();
  }

  /**
   * Given a {@link Drawable}, return the corresponding {@link
   * Bitmap}.
   *
   * @param drawable The Drawable set on the AppCompatImageView.
   * @return The created Bitmap, or {@code null} if {@code OutOfMemoryError} occurs.
   */
  @Nullable
  private Bitmap getBitmapFromDrawable(@Nullable Drawable drawable) {
    if (drawable == null) {
      return null;
    }

    if (drawable instanceof BitmapDrawable) {
      return ((BitmapDrawable) drawable).getBitmap();
    }

    try {
      Bitmap bitmap;

      if (drawable instanceof ColorDrawable) {
        bitmap =
            Bitmap.createBitmap(COLORDRAWABLE_DIMENSION, COLORDRAWABLE_DIMENSION, BITMAP_CONFIG);
      } else {
        bitmap =
            Bitmap.createBitmap(
                drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), BITMAP_CONFIG);
      }

      Canvas canvas = new Canvas(bitmap);
      drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
      drawable.draw(canvas);
      return bitmap;
    } catch (OutOfMemoryError e) {
      return null;
    }
  }

  /**
   * Setup the bitmap shader, bitmap and border {@link Paint}, and calculate the
   * drawable and bitmap measurements.
   */
  private void setup() {
    if (!ready) {
      setupPending = true;
      return;
    }

    if (bitmap == null) {
      return;
    }

    BitmapShader bitmapShader =
        new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

    bitmapPaint.setAntiAlias(true);
    bitmapPaint.setShader(bitmapShader);

    borderPaint.setStyle(Paint.Style.STROKE);
    borderPaint.setAntiAlias(true);
    borderPaint.setColor(borderColor);
    borderPaint.setStrokeWidth(borderWidth);

    bitmapHeight = bitmap.getHeight();
    bitmapWidth = bitmap.getWidth();

    borderRect.set(0, 0, getWidth(), getHeight());
    borderRadius =
        Math.min((borderRect.height() - borderWidth) / 2, (borderRect.width() - borderWidth) / 2);

    drawableRect.set(
        borderWidth,
        borderWidth,
        borderRect.width() - borderWidth,
        borderRect.height() - borderWidth);
    drawableRadius = Math.min(drawableRect.height() / 2, drawableRect.width() / 2);

    updateShaderMatrix(bitmapShader);
    invalidate();
  }

  /**
   * Update the BitmapShader {@link Matrix} to scale the Bitmap to fit the
   * CircleImageView.
   *
   * @param bitmapShader Shader used to draw a bitmap.
   */
  private void updateShaderMatrix(BitmapShader bitmapShader) {
    float scale;
    float dx = 0;
    float dy = 0;

    shaderMatrix.set(null);

    if (bitmapWidth * drawableRect.height() > drawableRect.width() * bitmapHeight) {
      scale = drawableRect.height() / (float) bitmapHeight;
      dx = (drawableRect.width() - bitmapWidth * scale) * HALF;
    } else {
      scale = drawableRect.width() / (float) bitmapWidth;
      dy = (drawableRect.height() - bitmapHeight * scale) * HALF;
    }

    shaderMatrix.setScale(scale, scale);
    shaderMatrix.postTranslate((int) (dx + HALF) + borderWidth, (int) (dy + HALF) + borderWidth);

    bitmapShader.setLocalMatrix(shaderMatrix);
  }
}
