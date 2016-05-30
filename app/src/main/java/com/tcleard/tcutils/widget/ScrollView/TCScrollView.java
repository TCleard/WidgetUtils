package com.tcleard.tcutils.widget.ScrollView;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ScrollView;

import com.tcleard.tcutils.utils.ScrollUtils;

/**
 * Created by geckoz on 14/03/16.
 */
public class TCScrollView extends ScrollView {

    public TCScrollView(Context context) {
        super(context);
    }

    public TCScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TCScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TCScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setOnScrollListener(ScrollUtils.OnScrollListener scrollListener) {
        ScrollUtils.setOnScrollListener(this, scrollListener);
    }

    public void setOnScrollDirectionChangedListener(ScrollUtils.OnScrollDirectionChangedListener directionChangedListener) {
        ScrollUtils.setOnScrollDirectionChangedListener(this, directionChangedListener);
    }
}
