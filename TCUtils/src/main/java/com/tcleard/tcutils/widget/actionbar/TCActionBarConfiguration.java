package com.tcleard.tcutils.widget.actionbar;

import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;

/**
 * Created by geckoz on 13/03/16.
 */

public class TCActionBarConfiguration {

    private String _title;
    private com.tcleard.tcutils.widget.actionbar.TCActionBarButton _leftButton, _rightButton1, _rightButton2;
    private
    @ColorRes
    Integer _titleColor, _tintColor, _backgroundColor;
    private TCActionBar.RippleColor _rippleColor;
    private
    @DimenRes
    Integer _buttonPadding;

    public String getTitle() {
        return _title;
    }

    public TCActionBarConfiguration setTitle(String title) {
        _title = title;
        return this;
    }

    public com.tcleard.tcutils.widget.actionbar.TCActionBarButton getLeftButton() {
        return _leftButton;
    }

    public TCActionBarConfiguration setLeftButton(com.tcleard.tcutils.widget.actionbar.TCActionBarButton leftButton) {
        _leftButton = leftButton;
        return this;
    }

    public com.tcleard.tcutils.widget.actionbar.TCActionBarButton getRightButton1() {
        return _rightButton1;
    }

    public TCActionBarConfiguration setRightButton1(com.tcleard.tcutils.widget.actionbar.TCActionBarButton rightButton1) {
        _rightButton1 = rightButton1;
        return this;
    }

    public com.tcleard.tcutils.widget.actionbar.TCActionBarButton getRightButton2() {
        return _rightButton2;
    }

    public TCActionBarConfiguration setRightButton2(com.tcleard.tcutils.widget.actionbar.TCActionBarButton rightButton2) {
        _rightButton2 = rightButton2;
        return this;
    }

    public Integer getTitleColor() {
        return _titleColor;
    }

    public TCActionBarConfiguration setTitleColor(@ColorRes Integer textColor) {
        _titleColor = textColor;
        return this;
    }

    public Integer getTintColor() {
        return _tintColor;
    }

    public TCActionBarConfiguration setTintColor(@ColorRes Integer tintColor) {
        _tintColor = tintColor;
        return this;
    }

    public Integer getBackgroundColor() {
        return _backgroundColor;
    }

    public TCActionBarConfiguration setBackgroundColor(@ColorRes Integer backgroundColor) {
        _backgroundColor = backgroundColor;
        return this;
    }

    public TCActionBar.RippleColor getRippleColor() {
        return _rippleColor;
    }

    public TCActionBarConfiguration setRippleColor(TCActionBar.RippleColor rippleColor) {
        _rippleColor = rippleColor;
        return this;
    }

    public Integer getButtonPadding() {
        return _buttonPadding;
    }

    public TCActionBarConfiguration setButtonPadding(@DimenRes Integer buttonPadding) {
        _buttonPadding = buttonPadding;
        return this;
    }
}