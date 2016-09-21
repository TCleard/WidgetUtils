package com.tcleard.tcutils.widget.ListView;

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
import android.widget.ListAdapter;

import com.tcleard.tcutils.R;
import com.tcleard.tcutils.utils.TCScrollUtils;

/**
 * Created by geckoz on 14/03/16.
 */
public class TCParallaxListView extends FrameLayout implements View.OnTouchListener, TCScrollUtils.OnScrollListener {

    private static final String TAG = TCParallaxListView.class.getName();

    private FrameLayout _parallaxView;
    private TCListView _listView;
    private View _paddingView;
    private ListAdapter _adapter;

    private int _baseHeight;
    private boolean _isFirstOnLayout = true;
    private float _startY;
    private boolean _stretching = false;

    private static final float PARALLAX_FACTOR = 3f;

    public TCParallaxListView(Context context) {
        super(context);
        init(null);
    }

    public TCParallaxListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public TCParallaxListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TCParallaxListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        View.inflate(getContext(), R.layout.tc_parallax_list_view, this);
        _parallaxView = (FrameLayout) findViewById(R.id.parallax);
        _listView = (TCListView) findViewById(R.id.tc_list);
        _listView.setOnScrollListener(this);
        _listView.setOnTouchListener(this);
        _paddingView = new View(getContext());
        _listView.addHeaderView(_paddingView);
    }

    public ListAdapter getAdapter() {
        return _adapter;
    }

    public void setAdapter(ListAdapter adapter) {
        _adapter = adapter;
        _listView.setAdapter(_adapter);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (_isFirstOnLayout) {
            _isFirstOnLayout = false;
            if (getChildCount() == 2) {
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
            } else {
                throw new RuntimeException("TCParallaxView must have 1 child : one layout that'll be parallaxed");
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
                if (_listView.getScroll() == 0 && !_stretching) {
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
        _parallaxView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height));
        _paddingView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height));
    }
}
