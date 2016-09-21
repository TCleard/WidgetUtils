package com.tcleard.tcutils.widget.actionbar;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.FloatRange;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.tcleard.tcutils.R;

/**
 * Created by geckoz on 12/03/16.
 */
public class TCActionBar extends FrameLayout {

    private View _root;
    private ImageButton _leftButton, _rightButton1, _rightButton2;
    private TextView _title;

    public TCActionBar(Context context) {
        super(context);
        init(null);
    }

    public TCActionBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public TCActionBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TCActionBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        _root = View.inflate(getContext(), R.layout.tc_action_bar, this);
        _leftButton = (ImageButton) _root.findViewById(R.id.left_button);
        _rightButton1 = (ImageButton) _root.findViewById(R.id.right_button1);
        _rightButton2 = (ImageButton) _root.findViewById(R.id.right_button2);
        _title = (TextView) _root.findViewById(R.id.title);
        setRippleColor(RippleColor.DARK);
        setButtonPadding(getDimen(R.dimen.tc_action_bar_button_padding));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setElevation(getDimen(R.dimen.tc_action_bar_button_elevation));
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, getDimen(R.dimen.tc_action_bar_height));
    }

    public void setConfiguration(TCActionBarConfiguration configuration) {
        if (configuration.getTitle() != null) {
            setTitle(configuration.getTitle());
        }
        if (configuration.getBackgroundColor() != null) {
            setBackgroundResource(configuration.getBackgroundColor());
        }
        if (configuration.getTintColor() != null) {
            setButtonTintColorResource(configuration.getTintColor());
        }
        if (configuration.getTitleColor() != null) {
            setTitleColorResource(configuration.getTitleColor());
        }
        setLeftButton(configuration.getLeftButton());
        setRightButton1(configuration.getRightButton1());
        setRightButton2(configuration.getRightButton2());
        if (configuration.getRippleColor() != null) {
            setRippleColor(configuration.getRippleColor());
        }
        if (configuration.getButtonPadding() != null) {
            setButtonPadding(getDimen(configuration.getButtonPadding()));
        }
    }

    /* Color */

    public void setTitleAlpha(@FloatRange(from = 0.0, to = 1.0) float alpha) {
        _title.setAlpha(alpha);
    }

    public void setBackgroundAlpha(@FloatRange(from = 0f, to = 1f) float alpha) {
        if (getBackground() != null && getBackground() instanceof ColorDrawable) {
            int color = ((ColorDrawable) getBackground()).getColor();
            int red = Color.red(color);
            int green = Color.green(color);
            int blue = Color.blue(color);
            int newColor = Color.argb(Math.round(alpha * 255), red, green, blue);
            setBackgroundColor(newColor);
        }
    }

    public void setButtonTintColorResource(@ColorRes int color) {
        setButtonTintColor(getColor(color));
    }

    public void setButtonTintColor(@ColorInt Integer color) {
        if (color != null) {
            _leftButton.setColorFilter(color);
            _rightButton1.setColorFilter(color);
            _rightButton2.setColorFilter(color);
        } else {
            _leftButton.clearColorFilter();
            _rightButton1.clearColorFilter();
            _rightButton2.clearColorFilter();
        }
    }

    public void setTitleColorResource(@ColorRes int color) {
        setTitleColor(getColor(color));
    }

    public void setTitleColor(@ColorInt int color) {
        _title.setTextColor(color);
    }

    public enum RippleColor {
        DARK, LIGHT
    }

    public void setRippleColor(RippleColor rippleColor) {
        _leftButton.setBackgroundResource(rippleColor == RippleColor.DARK ? R.drawable.dark_ripple_background : R.drawable.light_ripple_background);
        _rightButton1.setBackgroundResource(rippleColor == RippleColor.DARK ? R.drawable.dark_ripple_background : R.drawable.light_ripple_background);
        _rightButton2.setBackgroundResource(rippleColor == RippleColor.DARK ? R.drawable.dark_ripple_background : R.drawable.light_ripple_background);
    }

    /* Title */

    public void setTitle(String title) {
        _title.setText(title);
    }

    public void setTitle(@StringRes int title) {
        _title.setText(title);
    }

    /* Left Button */

    public void setLeftButton(com.tcleard.tcutils.widget.actionbar.TCActionBarButton actionBarButton) {
        if (actionBarButton == null) {
            setLeftButtonEnable(false);
        } else if (!actionBarButton.keepUnchanged) {
            _leftButton.setImageResource(actionBarButton.resource);
            _leftButton.setOnClickListener(actionBarButton.listener);
            setLeftButtonEnable(true);
        }
    }

    public void setLeftButton(@DrawableRes int resource, OnClickListener listener) {
        setLeftButton(new TCActionBarButton(resource, listener));
    }

    public void setLeftButtonEnable(boolean enable) {
        _leftButton.setVisibility(enable ? VISIBLE : GONE);
    }

    /* Right Button 1 */

    public void setRightButton1(com.tcleard.tcutils.widget.actionbar.TCActionBarButton actionBarButton) {
        if (actionBarButton == null) {
            setRightButton1Enable(false);
        } else if (!actionBarButton.keepUnchanged) {
            _rightButton1.setImageResource(actionBarButton.resource);
            _rightButton1.setOnClickListener(actionBarButton.listener);
            setRightButton1Enable(true);
        }
    }

    public void setRightButton1(@DrawableRes int resource, OnClickListener listener) {
        setRightButton1(new TCActionBarButton(resource, listener));
    }

    public void setRightButton1Enable(boolean enable) {
        _rightButton1.setVisibility(enable ? VISIBLE : GONE);
    }

    /* Right Button 2 */

    public void setRightButton2(com.tcleard.tcutils.widget.actionbar.TCActionBarButton actionBarButton) {
        if (actionBarButton == null) {
            setRightButton2Enable(false);
        } else if (!actionBarButton.keepUnchanged) {
            _rightButton2.setImageResource(actionBarButton.resource);
            _rightButton2.setOnClickListener(actionBarButton.listener);
            setRightButton2Enable(true);
        }
    }

    public void setRightButton2(@DrawableRes int resource, OnClickListener listener) {
        setRightButton2(new TCActionBarButton(resource, listener));
    }

    public void setRightButton2Enable(boolean enable) {
        _rightButton2.setVisibility(enable ? VISIBLE : GONE);
    }

    public void setButtonPadding(int padding) {
        _leftButton.setPadding(padding, padding, padding, padding);
        _rightButton1.setPadding(padding, padding, padding, padding);
        _rightButton2.setPadding(padding, padding, padding, padding);
    }

    /* Utils */

    private int getColor(@ColorRes int color) {
        return getResources().getColor(color);
    }

    private int getDimen(@DimenRes int dimen) {
        return getResources().getDimensionPixelSize(dimen);
    }

}
