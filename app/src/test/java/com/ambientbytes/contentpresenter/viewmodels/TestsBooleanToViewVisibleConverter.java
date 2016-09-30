package com.ambientbytes.contentpresenter.viewmodels;

import android.view.View;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestsBooleanToViewVisibleConverter {
    @Test
    public void convertTrue_ReturnsVISIBLE() throws Exception {
        IValueConverter converter = new BooleanToViewVisibleConverter();

        assertEquals(View.VISIBLE, (int) converter.Convert(true));
    }

    @Test
    public void convertFalse_ReturnsGONE() throws Exception {
        IValueConverter converter = new BooleanToViewVisibleConverter();

        assertEquals(View.GONE, (int) converter.Convert(false));
    }
}
