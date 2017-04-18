package com.github.mikephil.charting.charts;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Handler;
import android.support.annotation.ColorRes;
import android.support.percent.PercentLayoutHelper;
import android.support.percent.PercentRelativeLayout;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;

public class ConcentricPieChart extends PercentRelativeLayout {

    private static final float OUTER_PERCENT = 1.f;
    private static final float INNER_PERCENT = 0.59f;
    private static final float OUTER_HOLE_RADIUS = 61.f;
    private static final float INNER_HOLE_RADIUS = 85.5f;

    private static final float SPLICE_SPACE_SIZE = 0.0084f;
    private static final float CENTER_TEXT_OFFSET = 0.037f;
    private static final float CENTER_VALUE_SIZE = 0.085f;
    private static final float CENTER_LABEL_SIZE = 0.055f;
    private static final float SELECTION_SHIFT_SIZE = 0.048f;

    private PieDataSet innerDataSet;
    private PieDataSet outerDataSet;
    private PieChart innerPieChart;
    private PieChart outerPieChart;
    private PercentLayoutHelper.PercentLayoutInfo pieChartInfo;
    private Handler resizeHandler;
    private OnSliceClickListener listener;
    private ArrayList<PieEntry> outerEntries;

    public ConcentricPieChart(Context context) {
        super(context);
        initialise(context);
    }

    public ConcentricPieChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialise(context);
    }

    public ConcentricPieChart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialise(context);
    }

    public void setOnSliceClickListener(OnSliceClickListener listener) {
        this.listener = listener;
    }

    public void setOuterColors(TypedArray outerColors) {
        setColors(outerDataSet, outerColors);
    }

    public void setInnerColors(TypedArray innerColors) {
        setColors(innerDataSet, innerColors);
    }

    public void setOuterValues(ArrayList<Float> outerValues) {
        outerEntries = getPieEntries(outerValues);
        outerDataSet = getPieDataSet(outerEntries);
        outerPieChart.setData(new PieData(outerDataSet));
    }

    public void setInnerValues(ArrayList<Float> innerValues) {
        ArrayList<PieEntry> innerEntries = getPieEntries(innerValues);
        innerDataSet = getPieDataSet(innerEntries);
        innerPieChart.setData(new PieData(innerDataSet));
    }

    public void setOuterColors(ArrayList<Integer> outerColors) {
        setColors(outerDataSet, outerColors);
    }

    public void setInnerColors(ArrayList<Integer> innerColors) {
        setColors(innerDataSet, innerColors);
    }

    public void setCenterLabel(String label) {
        innerPieChart.setCenterText(label);
    }

    public void setCenterValue(String value) {
        outerPieChart.setCenterText(value);
    }

    public void setCenterLabelColor(@ColorRes int centerLabelColor) {
        innerPieChart.setCenterTextColor(centerLabelColor);
    }

    public void setCenterValueColor(@ColorRes int centerTextColor) {
        outerPieChart.setCenterTextColor(centerTextColor);
    }

    public void setSelectionShiftEnabled(boolean selectionShiftEnabled) {
        outerDataSet.setHighlightEnabled(selectionShiftEnabled);
        outerPieChart.setHighlightPerTapEnabled(selectionShiftEnabled);
    }

    public PieChart getOuterPieChart() {
        return outerPieChart;
    }

    public PieChart getInnerPieChart() {
        return innerPieChart;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        int width = getSmallestDimension(right, bottom);
        if (changed) {
            setScaledValues(width);
        }
    }

    private int getSmallestDimension(int right, int bottom) {
        return right < bottom ? right : bottom;
    }

    private void initialise(Context context) {
        initPieCharts(context);
        initDataSets();
        addPieChartsToView();
        initPieChartParams();
        setOnChartValueSelectedListener();
        resizeHandler = new Handler();
    }

    private void initPieChartParams() {
        setPieChartParams(outerPieChart, OUTER_PERCENT);
        setPieChartParams(innerPieChart, INNER_PERCENT);
    }

    private void addPieChartsToView() {
        addView(innerPieChart);
        addView(outerPieChart);
    }

    private void initDataSets() {
        outerDataSet = getPieDataSet(null);
        innerDataSet = getPieDataSet(null);
    }

    private void initPieCharts(Context context) {
        innerPieChart = getPieChart(context, INNER_HOLE_RADIUS);
        outerPieChart = getPieChart(context, OUTER_HOLE_RADIUS);
    }

    private void setOnChartValueSelectedListener() {
        outerPieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry entry, Highlight highlight) {
                if(listener != null) {
                    int index = outerEntries.indexOf(entry);
                    listener.onSliceClicked(index);
                }
            }
            @Override
            public void onNothingSelected() {
                if(listener != null) {
                    listener.onNothingSelected();
                }
            }
        });
    }

    private ArrayList<PieEntry> getPieEntries(ArrayList<Float> innerValues) {
        ArrayList<PieEntry> innerEntries = new ArrayList<>();
        for (Float value : innerValues) {
            innerEntries.add(new PieEntry(value));
        }
        return innerEntries;
    }

    private PieDataSet getPieDataSet(ArrayList<PieEntry> pieEntries) {
        PieDataSet dataSet = new PieDataSet(pieEntries, "");
        dataSet.setDrawValues(false);
        return dataSet;
    }

    private PieChart getPieChart(Context context, float holeRadius) {
        PieChart pieChart = new PieChart(context);
        setChartDefaults(pieChart);
        pieChart.setHoleRadius(holeRadius);
        return pieChart;
    }

    private void setChartDefaults(PieChart pieChart) {
        pieChart.setRotationEnabled(false);
        pieChart.getDescription().setEnabled(false);
        pieChart.getLegend().setEnabled(false);
        pieChart.setHoleColor(android.R.color.transparent);
    }

    private void setPieChartParams(PieChart pieChart, float widthPercent) {
        LayoutParams pieChartParams = (LayoutParams) pieChart.getLayoutParams();
        pieChartParams.addRule(PercentRelativeLayout.CENTER_IN_PARENT, PercentRelativeLayout.TRUE);
        pieChartInfo = pieChartParams.getPercentLayoutInfo();
        pieChartInfo.widthPercent = widthPercent;
    }

    private void setColors(PieDataSet dataSet, TypedArray innerColors) {
        ArrayList<Integer> colors = new ArrayList<>();
        for (int i = 0; i < innerColors.length(); i++) {
            colors.add(innerColors.getColor(i, 0));
        }
        dataSet.setColors(colors);
    }

    private void setColors(PieDataSet dataSet, ArrayList<Integer> colors) {
        for (int i = 0; i < colors.size(); i++) {
            colors.set(i, ContextCompat.getColor(getContext(), colors.get(i)));
        }
        dataSet.setColors(colors);
    }

    private void setScaledValues(int widthPx) {
        float widthDp = convertPixelsToDp(widthPx, getContext());
        scaleElements(widthDp);
        scaleInnerPieChart(widthPx);
    }

    private void scaleElements(float widthDp) {
        setSelectionShift(widthDp * SELECTION_SHIFT_SIZE);
        setCenterValueSize(widthDp * CENTER_VALUE_SIZE);
        setCenterLabelSize(widthDp * CENTER_LABEL_SIZE);
        setCenterTextOffset(widthDp * CENTER_TEXT_OFFSET);
        setSpliceSpace(widthDp * SPLICE_SPACE_SIZE);
    }

    private void scaleInnerPieChart(int widthPx) {
        pieChartInfo.widthPercent = getInnerWidthPercent(widthPx);
        resizeHandler.post(new Runnable() {
            @Override
            public void run() {
                innerPieChart.requestLayout();
            }
        });
    }

    private void setSelectionShift(float selectionShift) {
        outerDataSet.setSelectionShift(selectionShift);
        innerDataSet.setSelectionShift(0);
    }

    private void setCenterValueSize(float value) {
        outerPieChart.setCenterTextSize(value);
    }

    private void setCenterLabelSize(float labelSize) {
        innerPieChart.setCenterTextSize(labelSize);
    }

    private float getInnerWidthPercent(int widthPx) {
        float selectionShiftPx = outerDataSet.getSelectionShift() * 2.f;
        float outerWidthPercent = OUTER_PERCENT - (selectionShiftPx / widthPx);
        return INNER_PERCENT * outerWidthPercent;
    }

    private void setCenterTextOffset(float textOffsetDp) {
        outerPieChart.setCenterTextOffset(0, textOffsetDp);
        innerPieChart.setCenterTextOffset(0, -textOffsetDp);
    }

    private void setSpliceSpace(float spliceSpaceDp) {
        outerDataSet.setSliceSpace(spliceSpaceDp);
        innerDataSet.setSliceSpace(spliceSpaceDp);
    }

    private float convertPixelsToDp(float px, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return px / ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    public interface OnSliceClickListener {
        void onSliceClicked(int slice);
        void onNothingSelected();
    }
}
