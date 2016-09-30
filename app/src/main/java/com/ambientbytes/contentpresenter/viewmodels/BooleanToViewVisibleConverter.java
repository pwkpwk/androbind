package com.ambientbytes.contentpresenter.viewmodels;

import android.view.View;

public final class BooleanToViewVisibleConverter implements IValueConverter {
    @Override
    public Object Convert(Object value) {
        boolean isVisible = (boolean) value;

        return isVisible ? View.VISIBLE : View.GONE;
    }
}
