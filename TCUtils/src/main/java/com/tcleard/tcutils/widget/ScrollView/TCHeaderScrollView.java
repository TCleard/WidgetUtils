package com.tcleard.tcutils.widget.ScrollView;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.tcleard.tcutils.R;
import com.tcleard.tcutils.utils.TCScrollUtils;

/**
 * Created by geckoz on 25/03/16.
 */
public class TCHeaderScrollView extends FrameLayout implements TCScrollUtils.OnScrollListener, TCScrollUtils.OnScrollDirectionChangedListener {

    private FrameLayout _header;
    private TCScrollView _scrollView;

    private TCScrollUtils.ScrollDirection _scrollDirection = TCScrollUtils.ScrollDirection.UP;
    private int _baseHeight;
    private int _minHeight;
    private int _startScrollY;

    public TCHeaderScrollView(Context context) {
        super(context);
        init();
    }

    public TCHeaderScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TCHeaderScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TCHeaderScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        View.inflate(getContext(), R.layout.tc_header_scroll_view, this);
        _header = (FrameLayout) findViewById(R.id.header);
        _scrollView = (TCScrollView) findViewById(R.id.scroll);
        _scrollView.setOnScrollListener(this);
        _scrollView.setOnScrollDirectionChangedListener(this);

        // Remove after test
        _header.post(new Runnable() {
            @Override
            public void run() {
                _baseHeight = _header.getHeight();
                _minHeight = _baseHeight / 3;
            }
        });
    }

    public void setHeader(final View header, final int minSize) {
        _header.addView(header);
        header.post(new Runnable() {
            @Override
            public void run() {
                _baseHeight = header.getHeight();
                _minHeight = minSize;
                _header.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, _baseHeight));
                header.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            }
        });
    }

    private void resizeHeader(int height) {
        if (height < _minHeight) {
            height = _minHeight;
        }
        _header.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height));
    }


    @Override
    public void onScroll(View view, int scrollY) {
        int range;
        switch (_scrollDirection) {
            case DOWN:
                range = scrollY - _startScrollY;
                if (range > (_baseHeight - _minHeight)) {
                    if (_header.getHeight() > _minHeight) {
                        resizeHeader(_minHeight);
                    }
                }
                break;
            case UP:
                range = _startScrollY = scrollY;
                if (range > (_baseHeight - _minHeight)) {
                    if (_header.getHeight() > _minHeight) {
                        resizeHeader(_minHeight);
                    }
                }
                break;
        }
    }

    @Override
    public void onScrollDirectionChanged(View view, TCScrollUtils.ScrollDirection scrollDirection) {
        _scrollDirection = scrollDirection;
        _startScrollY = _scrollView.getScrollY();
    }
}
