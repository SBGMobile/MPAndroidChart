package com.github.mikephil.charting.charts;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.github.mikephil.charting.data.SplitData;
import com.github.mikephil.charting.interfaces.datasets.ISplitDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.renderer.SplitChartRenderer;
import com.github.mikephil.charting.utils.Utils;

public class SplitChart extends Chart<SplitData>{

    private float mLineThickness = 5f;

    private float mDrawFraction;

    public SplitChart(Context context) {
        super(context);
    }

    public SplitChart(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SplitChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void init() {
        super.init();

        mRenderer = new SplitChartRenderer(this, mAnimator, mViewPortHandler);
        mChartTouchListener = new ChartTouchListener(this) {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        };
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mRenderer.drawData(canvas);

        mRenderer.drawValues(canvas);
    }

    @Override
    public void notifyDataSetChanged() {
        if (mData == null)
            return;

        calculateFractions();
        calculateOffsets();
        requestLayout();
    }

    @Override
    protected void calculateOffsets() {


        float offsetLeft = 0f, offsetRight = 0f, offsetTop = 0f, offsetBottom = 0f;

        if (mData != null) {
            offsetTop += ((SplitChartRenderer) getRenderer()).getLabelHeightOffset(mData.getDataSet()) + getLineThickness();
        }

        mViewPortHandler.restrainViewPort(offsetLeft, offsetTop, offsetRight, offsetBottom);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int height = getMeasuredHeight();
        int width = getMeasuredWidth();


        if (mData != null && getLayoutParams().height == LayoutParams.WRAP_CONTENT) {
            height = (int) Math.ceil(((SplitChartRenderer) getRenderer()).getLabelHeightOffset(mData.getDataSet()) + getLineThickness());
            height += getPaddingBottom() + getPaddingTop();
        }
        setMeasuredDimension(width, height|MeasureSpec.EXACTLY);

    }

    @Override
    protected void calcMinMax() {
        calculateFractions();
    }

    @Override
    public float getYChartMin() {
        return 0;
    }

    @Override
    public float getYChartMax() {
        return 0;
    }

    @Override
    public int getMaxVisibleCount() {
        return 0;
    }

    public void setLineThickness(int thicknessDp) {
        if (thicknessDp > 20)
            thicknessDp = 20;
        if (thicknessDp < 1)
            thicknessDp = 1;

        mLineThickness = Utils.convertDpToPixel(thicknessDp);
    }

    public float getLineThickness() {
        return mLineThickness;
    }

    private void calculateFractions() {
        float yValueSum = mData.getYValueSum();
        final ISplitDataSet dataSet = mData.getDataSet();
        mDrawFraction = Math.abs(dataSet.getEntryForIndex(0).getValue() / yValueSum);
    }


    public float getDrawFraction() {
        return mDrawFraction;
    }
}
