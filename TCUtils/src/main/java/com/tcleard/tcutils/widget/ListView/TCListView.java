package com.tcleard.tcutils.widget.ListView;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.tcleard.tcutils.utils.TCScrollUtils;

import java.util.ArrayList;

/**
 * Created by geckoz on 18/03/16.
 */
public class TCListView extends ListView {

    private ArrayList<View> _headers;

    public TCListView(Context context) {
        super(context);
    }

    public TCListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TCListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TCListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public int getScroll() {
        View c = getChildAt(0);
        int headerHeight = 0;
        if (_headers != null) {
            for (View header : _headers) {
                headerHeight += header.getHeight();
            }
        }
        int scroll = (c == null ? 0
                : ((-c.getTop()) + -getFirstVisiblePosition() * c.getHeight())
                + headerHeight);
        Log.e("TAG", scroll + " " + headerHeight + " " + getFirstVisiblePosition());
        return scroll;
    }

    @Override
    public void addHeaderView(View v, Object data, boolean isSelectable) {
        super.addHeaderView(v, data, isSelectable);
        if (_headers == null) {
            _headers = new ArrayList<>();
        }
        _headers.add(v);
    }

    @Override
    public void addHeaderView(View v) {
        addHeaderView(v, null, true);
    }

    @Override
    public boolean removeHeaderView(View v) {
        if (_headers != null) {
            _headers.remove(v);
        }
        return super.removeHeaderView(v);
    }

    public void setOnScrollListener(TCScrollUtils.OnScrollListener scrollListener) {
        TCScrollUtils.setOnScrollListener(this, scrollListener);
    }

    public void setOnScrollDirectionChangedListener(TCScrollUtils.OnScrollDirectionChangedListener directionChangedListener) {
        TCScrollUtils.setOnScrollDirectionChangedListener(this, directionChangedListener);
    }
}
