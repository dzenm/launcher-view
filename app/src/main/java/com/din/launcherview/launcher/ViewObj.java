package com.din.launcherview.launcher;

import android.widget.ImageView;

public class ViewObj {
    private final ImageView red;

    public ViewObj(ImageView red) {
        this.red = red;
    }

    public void setFabLoc(ViewPoint newLoc) {
        red.setTranslationX(newLoc.x);
        red.setTranslationY(newLoc.y);
    }
}