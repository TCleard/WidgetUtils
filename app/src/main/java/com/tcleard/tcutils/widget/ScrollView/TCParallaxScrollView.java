package com.tcleard.tcutils.widget.ScrollView;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.OvershootInterpolator;
import android.view.animation.Transformation;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.tcleard.tcutils.R;
import com.tcleard.tcutils.utils.ScrollUtils;

/**
 * Created by geckoz on 14/03/16.
 */
public class TCParallaxScrollView extends FrameLayout implements View.OnTouchListener, ScrollUtils.OnScrollListener {

    private static final String TAG = TCParallaxScrollView.class.getName();

    private FrameLayout _parallaxView;
    private TCScrollView _scrollView;
    private View _paddingView;
    private FrameLayout _scrollContainer;

    private int _baseHeight;
    private boolean _isFirstOnLayout = true;
    private float _startY;
    private boolean _stretching = false;

    private static final float PARALLAX_FACTOR = 3f;

    public TCParallaxScrollView(Context context) {
        super(context);
        init(null);
    }

    public TCParallaxScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public TCParallaxScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TCParallaxScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        View.inflate(getContext(), R.layout.tc_parallax_scroll_view, this);
        _parallaxView = (FrameLayout) findViewById(R.id.parallax);
        _scrollView = (TCScrollView) findViewById(R.id.scroll);
        _scrollView.setOnScrollListener(this);
        _scrollView.setOnTouchListener(this);
        _paddingView = findViewById(R.id.padding_view);
        _scrollContainer = (FrameLayout) findViewById(R.id.scroll_container);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (_isFirstOnLayout) {
            _isFirstOnLayout = false;
            if (getChildCount() == 3) {
                final View headerView = getChildAt(1);
                removeView(headerView);
                _parallaxView.addView(headerView);
                headerView.post(new Runnable() {
                    @Override
                    public void run() {
                        _baseHeight = _parallaxView.getHeight();
                        _parallaxView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, _baseHeight));
                        headerView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
                        _paddingView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, _baseHeight));
                    }
                });
                View scrollContent = getChildAt(1);
                removeView(scrollContent);
                scrollContent.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                _scrollContainer.addView(scrollContent);
            } else {
                throw new RuntimeException("TCParallaxView must have 2 children : one layout that'll be parallaxed and one layout with the content of the scrollview");
            }
        }
    }

    @Override
    public void onScroll(View view, int scrollY) {
        if (scrollY < _baseHeight) {
            _parallaxView.setY(-(scrollY / PARALLAX_FACTOR));
        } else if (scrollY > _baseHeight) {
            _parallaxView.setY(-(_baseHeight / PARALLAX_FACTOR));
        } else {
            _parallaxView.setY(0);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                _parallaxView.clearAnimation();
                break;
            case MotionEvent.ACTION_MOVE:
                if (_stretching && event.getY() < _startY) {
                    _stretching = false;
                    break;
                }
                if (_scrollView.getScrollY() == 0 && !_stretching) {
                    _stretching = true;
                    _startY = event.getY();
                }
                if (_stretching) {
                    resizeHeader(Math.round(_baseHeight + ((event.getY() - _startY) / PARALLAX_FACTOR)));
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                _stretching = false;
                animateBack();
                break;
        }
        return false;
    }

    private void animateBack() {
        final int range = _parallaxView.getHeight() - _baseHeight;
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
        animation.setInterpolator(new OvershootInterpolator());
        animation.setDuration(600);
        _parallaxView.startAnimation(animation);
    }

    private void resizeHeader(int height) {
        if (height < 1) {
            height = 1;
        }
        _parallaxView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height));
        _paddingView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height));
    }
}
