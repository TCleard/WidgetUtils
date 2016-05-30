package com.tcleard.tcutils.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.FloatRange;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.BaseInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.Transformation;
import android.widget.FrameLayout;

/**
 * Created by geckoz on 13/03/16.
 */
public class TCStretchyView extends FrameLayout {

    private static final String TAG = TCStretchyView.class.getName();

    private int _baseHeight;
    private float _startY;

    private float _resistance = 1.7f;
    private BaseInterpolator _interpolator = new OvershootInterpolator();

    private OnStretchingListener _onStretchingListener;

    public TCStretchyView(Context context) {
        super(context);
        init();
    }

    public TCStretchyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TCStretchyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TCStretchyView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        post(new Runnable() {
            @Override
            public void run() {
                _baseHeight = getHeight();
            }
        });
    }

    public void setResistance(@FloatRange(from = 0) float resistance) {
        if (resistance <= 0) {
            Log.e(TAG, "The resistance must be > 0");
        } else {
            _resistance = resistance;
        }
    }

    public void setInterpolator(BaseInterpolator interpolator) {
        _interpolator = interpolator;
    }

    public void setOnStretchingListener(OnStretchingListener onStretchingListener) {
        _onStretchingListener = onStretchingListener;
    }

    public int getBaseHeight() {
        return _baseHeight;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                clearAnimation();
                _startY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float range = event.getY() - _startY;
                range /= _resistance;
                resizeHeader(Math.round(_baseHeight + range));
                break;
            case MotionEvent.ACTION_UP:
                animateBack();
                break;
        }
        return true;
    }

    private void animateBack() {
        final int range = getHeight() - _baseHeight;
        Animation animation = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                int newHeight = Math.round(_baseHeight + (range * (1 - interpolatedTime)));
                if (newHeight < 0) {
                    newHeight = 0;
                }
                resizeHeader(newHeight);
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };
        animation.setInterpolator(_interpolator);
        animation.setDuration(600);
        startAnimation(animation);
    }

    private void resizeHeader(int height) {
        if (height < 1) {
            height = 1;
        }
        getLayoutParams().height = height;
        requestLayout();
        if (_onStretchingListener != null) {
            _onStretchingListener.onStretching(this, height);
        }
    }

    public interface OnStretchingListener {
        void onStretching(TCStretchyView parallaxView, int parallaxHeight);
    }
}
