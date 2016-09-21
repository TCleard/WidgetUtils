package com.tcleard.tcutils.utils;

import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;

/**
 * Created by geckoz on 22/09/16.
 */
public enum TCStretchyInterpolator {

    LINEAR(0),
    ACCELERATE(1),
    DECELERATE(2),
    ACCELERATE_DECELERATE(3),
    ANTICIPATE(4),
    OVERSHOOT(5),
    ANTICIPATE_OVERSHOOT(6),
    BOUNCE(7);

    public int value;

    TCStretchyInterpolator(int value) {
        this.value = value;
    }

    public static Interpolator getInterpolator(int value) {
        Interpolator interpolator;
        switch (withValue(value)) {
            default:
            case LINEAR:
                interpolator = new LinearInterpolator();
                break;
            case ACCELERATE:
                interpolator = new AccelerateInterpolator();
                break;
            case DECELERATE:
                interpolator = new DecelerateInterpolator();
                break;
            case ACCELERATE_DECELERATE:
                interpolator = new AccelerateDecelerateInterpolator();
                break;
            case ANTICIPATE:
                interpolator = new AnticipateInterpolator();
                break;
            case OVERSHOOT:
                interpolator = new OvershootInterpolator();
                break;
            case ANTICIPATE_OVERSHOOT:
                interpolator = new AnticipateOvershootInterpolator();
                break;
            case BOUNCE:
                interpolator = new BounceInterpolator();
                break;
        }
        return interpolator;
    }

    public static TCStretchyInterpolator withValue(int value) {
        for (TCStretchyInterpolator strechInterpolator : TCStretchyInterpolator.values()) {
            if (strechInterpolator.value == value) {
                return strechInterpolator;
            }
        }
        return LINEAR;
    }
}
