package com.tcleard.tcutils.widget.actionbar;

import android.view.View;

/**
 * Created by geckoz on 13/03/16.
 */
public class TCActionBarButton {
    public int resource;
    public View.OnClickListener listener;
    public boolean keepUnchanged;

    public TCActionBarButton(int resource, View.OnClickListener listener) {
        this.resource = resource;
        this.listener = listener;
        keepUnchanged = false;
    }

    public TCActionBarButton() {
        keepUnchanged = true;
    }
}
