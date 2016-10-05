package com.github.mikephil.charting.data;

import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ISplitDataSet;

public class SplitData extends ChartData<ISplitDataSet> {
    public SplitData() {
        super();
    }

    public SplitData(ISplitDataSet dataSet) {
        super(dataSet);
    }

    /**
     * Sets the SplitDataSet this data object should represent.
     *
     * @param dataSet
     */
    public void setDataSet(ISplitDataSet dataSet) {
        mDataSets.clear();
        mDataSets.add(dataSet);
        notifyDataChanged();
    }

    /**
     * Returns the DataSet this SplitData object represents. A SplitData object can
     * only contain one DataSet.
     *
     * @return
     */
    public ISplitDataSet getDataSet() {
        return mDataSets != null ? mDataSets.get(0) : null;
    }

    /**
     * The SplitData object can only have one DataSet. Use getDataSet() method instead.
     *
     * @param index
     * @return
     */
    @Override
    public ISplitDataSet getDataSetByIndex(int index) {
        return index == 0 ? getDataSet() : null;
    }

    @Override
    public ISplitDataSet getDataSetByLabel(String label, boolean ignorecase) {
        return ignorecase ? label.equalsIgnoreCase(mDataSets.get(0).getLabel()) ? mDataSets.get(0)
                : null : label.equals(mDataSets.get(0).getLabel()) ? mDataSets.get(0) : null;
    }

    @Override
    public Entry getEntryForHighlight(Highlight highlight) {
        final ISplitDataSet dataSet = getDataSet();
        if (dataSet == null) {
            return null;
        }

        return dataSet.getEntryForIndex((int) highlight.getX());
    }

    /**
     * Returns the sum of all values in this SplitData object.
     *
     * @return
     */
    public float getYValueSum() {

        float sum = 0;

        for (int i = 0; i < getDataSet().getEntryCount(); i++)
            sum += getDataSet().getEntryForIndex(i).getY();


        return sum;
    }

    public void setLabelTextSize(float size) {
        for (ISplitDataSet dataset : mDataSets) {
            dataset.setLabelTextSize(size);
        }
    }

    public void setMultiline(boolean multiline) {
        for (ISplitDataSet dataset : mDataSets) {
            dataset.setMultiline(multiline);
        }
    }
}
