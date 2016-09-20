package com.github.mikephil.charting.data;

import android.graphics.Typeface;

import com.github.mikephil.charting.interfaces.datasets.ISplitDataSet;
import com.github.mikephil.charting.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class SplitDataSet extends DataSet<PieEntry> implements ISplitDataSet{
    private float mLineThickness = 5f;
    private boolean mMultiline;
    private Typeface mLabelTypeFace;
    private int mLabelTextColor;
    private float mLabelTextSize = 17f;

    /**
     * Creates a new DataSet object with the given values (entries) it represents. Also, a
     * label that describes the DataSet can be specified. The label can also be
     * used to retrieve the DataSet from a ChartData object.
     *
     * @param values
     * @param label
     */
    public SplitDataSet(List<PieEntry> values, String label) {
        super(values, label);
    }

    @Override
    public DataSet<PieEntry> copy() {

        List<PieEntry> yVals = new ArrayList<>();

        for (int i = 0; i < mValues.size(); i++) {
            yVals.add(mValues.get(i).copy());
        }

        PieDataSet copied = new PieDataSet(yVals, getLabel());
        copied.mColors = mColors;
        return copied;
    }



    @Override
    public boolean isMultiLine() {
        return mMultiline;
    }

    @Override
    public Typeface getLabelTypeface() {
        return mLabelTypeFace;
    }

    @Override
    public int getLabelTextColor() {
        return mLabelTextColor;
    }

    @Override
    public float getLabelTextSize() {
        return mLabelTextSize;
    }

    public void setLabelTextColor(int color) {
        this.mLabelTextColor = color;
    }

    public void setLabelTypeFace(Typeface labelTypeFace) {
        this.mLabelTypeFace = labelTypeFace;
    }

    @Override
    public void setMultiline(boolean mMultiline) {
        this.mMultiline = mMultiline;
    }

    @Override
    public void setLabelTextSize(float labelTextSize) {
        this.mLabelTextSize = Utils.convertDpToPixel(labelTextSize);
    }
}
