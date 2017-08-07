package com.github.mikephil.charting.renderer;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.charts.SplitChart;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.data.SplitData;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ISplitDataSet;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;

public class SplitChartRenderer extends DataRenderer {

    private SplitChart mSplitChart;
    private Paint mLeftPaint;
    private Paint mRightPaint;
    private Paint mLabelPaint;
    private final float textSpacer;


    public SplitChartRenderer(SplitChart splitChart, ChartAnimator mAnimator, ViewPortHandler mViewPortHandler) {
        super(mAnimator, mViewPortHandler);
        this.mSplitChart = splitChart;

        mLeftPaint = new Paint();
        mLeftPaint.setColor(Color.GREEN);

        mRightPaint = new Paint();
        mRightPaint.setColor(Color.RED);

        mLabelPaint = new Paint();
        mLabelPaint.setColor(Color.BLACK);
        textSpacer = Utils.convertDpToPixel(8);
    }

    @Override
    public void initBuffers() {

    }

    @Override
    public void drawData(Canvas c) {
        float width = mViewPortHandler.getChartWidth();
        float height = mViewPortHandler.getChartHeight();

        SplitData splitData = mSplitChart.getData();

        if (splitData != null) {
            final ISplitDataSet set = splitData.getDataSet();

            if (set.isVisible() && set.getEntryCount() > 0) {
                drawDataSet(c, set, width, height);
            }
        }
    }

    private void drawDataSet(Canvas canvas, ISplitDataSet set, float width, float height) {
        final float lineThickness = getLineThickness(set);

        float phaseX = mAnimator.getPhaseX();
        float leftX = mViewPortHandler.contentLeft();
        float y = height - (lineThickness / 2);
        final float fraction = mSplitChart.getDrawFraction();

        float leftStopX = width * fraction * phaseX;

        float right = width - leftStopX;
        float rightStopX = leftStopX + (right - (right * phaseX));

        mLeftPaint.setColor(set.getColor(0));
        mRightPaint.setColor(set.getColor(1));

        mLeftPaint.setStrokeWidth(lineThickness);
        mRightPaint.setStrokeWidth(lineThickness);

        canvas.save();
        canvas.drawLine(leftX, y, leftStopX, y, mLeftPaint);
        canvas.drawLine(width, y, rightStopX, y, mRightPaint);
        canvas.restore();
    }

    private float getLineThickness(ISplitDataSet set) {
        int thicknessDp = set.getLineThickness();
        if (thicknessDp > 20)
            thicknessDp = 20;
        if (thicknessDp < 1)
            thicknessDp = 1;

        return Utils.convertDpToPixel(thicknessDp);
    }

    @Override
    public void drawValues(Canvas canvas) {

        final SplitData data = mSplitChart.getData();

        if (data == null) {
            return;
        }

        final ISplitDataSet dataSet = data.getDataSet();

        float width = mViewPortHandler.getChartWidth();
        float height = mViewPortHandler.getChartHeight();

        float leftX = mViewPortHandler.contentLeft();
        float y = height - getLineThickness(dataSet) - textSpacer;
        float rightX = width;


        final IValueFormatter valueFormatter = dataSet.getValueFormatter();
        final boolean multiline = dataSet.isMultiLine();

        final Typeface valueTypeFace = dataSet.getValueTypeface();
        final Typeface labelTypeFace = dataSet.getLabelTypeface();

        final float valueTextSize = dataSet.getValueTextSize();
        final float labelTextSize = dataSet.getLabelTextSize();

        final PieEntry leftEntry = dataSet.getEntryForIndex(0);
        final PieEntry rightEntry = dataSet.getEntryForIndex(1);
        final String formattedLeftValue = valueFormatter.getFormattedValue(leftEntry.getValue(), leftEntry, 0, mViewPortHandler);
        final String formattedRightValue = valueFormatter.getFormattedValue(rightEntry.getValue(), rightEntry, 0, mViewPortHandler);

        final int valueTextColor = dataSet.getValueTextColor();
        final int labelTextColor = dataSet.getLabelTextColor();

        setLabelPaintProperties(labelTypeFace, labelTextSize, labelTextColor);
        setValuePaintProperties(valueTypeFace, valueTextSize, valueTextColor);

        final int leftLabelWidth = Utils.calcTextWidth(mValuePaint, leftEntry.getLabel());


        final int valueHeight = Utils.calcTextHeight(mValuePaint, formattedLeftValue);
        final int rightValueWidth = Utils.calcTextWidth(mValuePaint, formattedRightValue);
        final float fullValueHeight = valueHeight + textSpacer;
        final float textHorizontalOffset = dataSet.getTextHorizontalOffset();

        leftX += textHorizontalOffset;
        rightX -= textHorizontalOffset;
        if (multiline) {
            y -= fullValueHeight;
        } else {
            rightX -= rightValueWidth + textSpacer;
        }

        mLabelPaint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText(leftEntry.getLabel(), leftX, y, mLabelPaint);

        mLabelPaint.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText(rightEntry.getLabel(), rightX, y, mLabelPaint);

        if (multiline) {
            y += fullValueHeight;
        } else {
            leftX += leftLabelWidth + textSpacer;
            rightX = width;
        }


        mValuePaint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText(formattedLeftValue, leftX, y, mValuePaint);

        mValuePaint.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText(formattedRightValue, rightX, y, mValuePaint);

    }

    public float getLabelHeightOffset(ISplitDataSet dataSet) {
        final IValueFormatter valueFormatter = dataSet.getValueFormatter();
        final boolean multiline = dataSet.isMultiLine();
        final Typeface valueTypeFace = dataSet.getValueTypeface();
        final Typeface labelTypeFace = dataSet.getLabelTypeface();

        final float valueTextSize = dataSet.getValueTextSize();
        final float labelTextSize = dataSet.getLabelTextSize();

        final PieEntry leftEntry = dataSet.getEntryForIndex(0);
        final String formattedLeftValue = valueFormatter.getFormattedValue(leftEntry.getValue(), leftEntry, 0, mViewPortHandler);

        final int valueTextColor = dataSet.getValueTextColor();
        final int labelTextColor = dataSet.getLabelTextColor();

        setLabelPaintProperties(labelTypeFace, labelTextSize, labelTextColor);
        setValuePaintProperties(valueTypeFace, valueTextSize, valueTextColor);

        final int valueHeight = Utils.calcTextHeight(mValuePaint, formattedLeftValue);
        final int labelHeight = Utils.calcTextHeight(mLabelPaint, leftEntry.getLabel());
        final float lineThickness = getLineThickness(dataSet);

        if (multiline) {
            return (valueHeight + labelHeight + 2 * textSpacer) + lineThickness;
        } else {
            return Math.max(valueHeight, labelHeight) + textSpacer + lineThickness;
        }

    }

    private void setValuePaintProperties(Typeface labelTypeFace, float labelTextSize, int labelTextColor) {
        mValuePaint.setTypeface(labelTypeFace);
        mValuePaint.setColor(labelTextColor);
        mValuePaint.setTextSize(labelTextSize);
    }

    private void setLabelPaintProperties(Typeface labelTypeFace, float labelTextSize, int labelTextColor) {
        mLabelPaint.setTypeface(labelTypeFace);
        mLabelPaint.setColor(labelTextColor);
        mLabelPaint.setTextSize(labelTextSize);
    }

    @Override
    public void drawExtras(Canvas c) {

    }

    @Override
    public void drawHighlighted(Canvas c, Highlight[] indices) {

    }
}
