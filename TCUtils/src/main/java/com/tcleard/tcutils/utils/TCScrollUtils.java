package com.tcleard.tcutils.utils;

import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ListView;
import android.widget.ScrollView;

import com.tcleard.tcutils.widget.ListView.TCListView;

/**
 * Created by geckoz on 13/03/16.
 */
public class TCScrollUtils {

    /* ScrollListener */

    public interface OnScrollListener {
        void onScroll(View view, int scrollY);
    }

    public static void setOnScrollListener(final ScrollView scrollview, final OnScrollListener listener) {
        scrollview.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {

            @Override
            public void onScrollChanged() {
                listener.onScroll(scrollview, scrollview.getScrollY());
            }
        });
    }

    public static void setOnScrollListener(final ListView listView, final OnScrollListener listener) {
        listView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {

            @Override
            public void onScrollChanged() {
                View c = listView.getChildAt(0);
                int scroll = -c.getTop() + listView.getFirstVisiblePosition() * c.getHeight();
                listener.onScroll(listView, scroll);
            }
        });
    }

    /* ScrollDirectionChangedListener */

    public enum ScrollDirection {
        UP, DOWN
    }

    public interface OnScrollDirectionChangedListener {
        void onScrollDirectionChanged(View view, ScrollDirection scrollDirection);
    }

    public static void setOnScrollDirectionChangedListener(final ScrollView scrollView, final OnScrollDirectionChangedListener listener) {
        setOnScrollListener(scrollView, new OnScrollListener() {

            private int _oldScrollY = scrollView.getScrollY();
            private ScrollDirection _scrollDirection = ScrollDirection.UP;

            @Override
            public void onScroll(View view, int scrollY) {
                ScrollView scrollView = (ScrollView) view;
                if (scrollY > 0 && scrollY < (scrollView.getChildAt(0).getHeight() - scrollView.getHeight())) {
                    if (scrollY > _oldScrollY && _scrollDirection == ScrollDirection.UP) {
                        _scrollDirection = ScrollDirection.DOWN;
                        listener.onScrollDirectionChanged(scrollView, _scrollDirection);
                    } else if (scrollY < _oldScrollY && _scrollDirection == ScrollDirection.DOWN) {
                        _scrollDirection = ScrollDirection.UP;
                        listener.onScrollDirectionChanged(scrollView, _scrollDirection);
                    }
                    _oldScrollY = scrollY;
                }
            }
        });
    }

    public static void setOnScrollDirectionChangedListener(final TCListView listView, final OnScrollDirectionChangedListener listener) {
        setOnScrollListener(listView, new OnScrollListener() {

            private int _oldScrollY = listView.getScroll();
            private ScrollDirection _scrollDirection = ScrollDirection.UP;

            @Override
            public void onScroll(View view, int scrollY) {
                if (scrollY > 0) {
                    if (scrollY > _oldScrollY && _scrollDirection == ScrollDirection.UP) {
                        _scrollDirection = ScrollDirection.DOWN;
                        listener.onScrollDirectionChanged(listView, _scrollDirection);
                    } else if (scrollY < _oldScrollY && _scrollDirection == ScrollDirection.DOWN) {
                        _scrollDirection = ScrollDirection.UP;
                        listener.onScrollDirectionChanged(listView, _scrollDirection);
                    }
                    _oldScrollY = scrollY;
                }
            }
        });
    }

}
