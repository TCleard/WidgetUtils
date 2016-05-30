package com.tcleard.tcutils.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.tcleard.tcutils.R;

/**
 * Created by geckoz on 13/03/16.
 */
public class TCSearchView extends FrameLayout {

    private View _root;
    private EditText _searchView;
    private ImageButton _clear, _go;

    public TCSearchView(Context context) {
        super(context);
        init(null);
    }

    public TCSearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public TCSearchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TCSearchView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        _root = View.inflate(getContext(), R.layout.tc_search_view, this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setElevation(getDimen(R.dimen.tc_action_bar_button_elevation));
        }
        _searchView = (EditText) _root.findViewById(R.id.query);
        _clear = (ImageButton) _root.findViewById(R.id.clear);
        _go = (ImageButton) _root.findViewById(R.id.go);
    }

    /* Utils */

    private int getColor(@ColorRes int color) {
        return getResources().getColor(color);
    }

    private int getDimen(@DimenRes int dimen) {
        return getResources().getDimensionPixelSize(dimen);
    }

}
