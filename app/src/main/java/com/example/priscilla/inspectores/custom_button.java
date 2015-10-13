package com.example.priscilla.inspectores;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;


public class custom_button extends View {

    final static OvershootInterpolator overshootInterpolator = new OvershootInterpolator();
    final static AccelerateInterpolator accelerateInterpolator = new AccelerateInterpolator();

    Context context;
    Paint mButtonPaint;
    Paint mDrawablePaint;
    Bitmap mBitmap;
    boolean mHidden = false;

    public custom_button(Context context) {
        super(context);
        this.context = context;
        init(Color.WHITE);
    }

    public void setFloatingActionButtonColor(int FloatingActionButtonColor) {
        init(FloatingActionButtonColor);
    }

    public void setFloatingActionButtonDrawable(Drawable FloatingActionButtonDrawable) {
        mBitmap = ((BitmapDrawable) FloatingActionButtonDrawable).getBitmap();
        invalidate();
    }

    public void init(int FloatingActionButtonColor) {
        setWillNotDraw(false);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        mButtonPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mButtonPaint.setColor(FloatingActionButtonColor);
        mButtonPaint.setStyle(Paint.Style.FILL);
        mButtonPaint.setShadowLayer(10.0f, 0.0f, 3.5f, Color.argb(100, 0, 0, 0));
        mDrawablePaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        setClickable(true);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, (float) (getWidth() / 2.6), mButtonPaint);
        canvas.drawBitmap(mBitmap, (getWidth() - mBitmap.getWidth()) / 2,
                (getHeight() - mBitmap.getHeight()) / 2, mDrawablePaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            setAlpha(1.0f);
        } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
            setAlpha(0.6f);
        }
        return super.onTouchEvent(event);
    }


    static public class Builder {
        private FrameLayout.LayoutParams params;
        private final Activity activity;
        int gravity = Gravity.BOTTOM | Gravity.RIGHT;
        Drawable drawable;
        int color = Color.WHITE;
        int size = 0;
        float scale = 0;

        public Builder(Activity context) {
            scale = context.getResources().getDisplayMetrics().density;
            size = convertToPixels(72, scale);
            params = new FrameLayout.LayoutParams(size, size);
            params.gravity = gravity;

            this.activity = context;
        }

        public Builder withGravity(int gravity) {
            this.gravity = gravity;
            return this;
        }

        public Builder withMargins(int left, int top, int right, int bottom) {
            params.setMargins(
                    convertToPixels(left, scale),
                    convertToPixels(top, scale),
                    convertToPixels(right, scale),
                    convertToPixels(bottom, scale));
            return this;
        }

        public Builder withDrawable(final Drawable drawable) {
            this.drawable = drawable;
            return this;
        }

        public Builder withButtonColor(final int color) {
            this.color = color;
            return this;
        }

        public Builder withButtonSize(int size) {
            size = convertToPixels(size, scale);
            params = new FrameLayout.LayoutParams(size, size);
            return this;
        }

        public custom_button create() {
            final custom_button button = new custom_button (activity);
            button.setFloatingActionButtonColor(this.color);
            button.setFloatingActionButtonDrawable(this.drawable);
            params.gravity = this.gravity;
            ViewGroup root = (ViewGroup) activity.findViewById(android.R.id.content);
            root.addView(button, params);
            return button;
        }
        private int convertToPixels(int dp, float scale){
            return (int) (dp * scale + 0.5f) ;
        }

    }
}
