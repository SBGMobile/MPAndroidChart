package com.github.mikephil.charting.interfaces.datasets;

import android.graphics.Typeface;

import com.github.mikephil.charting.data.PieEntry;

public interface ISplitDataSet extends IDataSet<PieEntry> {

    boolean isMultiLine();

    Typeface getLabelTypeface();

    int getLabelTextColor();

    float getLabelTextSize();

    void setMultiline(boolean mMultiline);

    void setLabelTextSize(float labelTextSize);
}
