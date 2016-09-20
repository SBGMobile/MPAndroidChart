
package com.xxmassdeveloper.mpchartexample;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.WindowManager;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.SplitChart;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.data.SplitData;
import com.github.mikephil.charting.data.SplitDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.xxmassdeveloper.mpchartexample.notimportant.DemoBase;

import java.util.ArrayList;

public class SplitChartActivity extends DemoBase {

    private SplitChart mChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splitchart);


        mChart = (SplitChart) findViewById(R.id.chart1);
        mChart.setDescription("");
//        mChart.setExtraOffsets(5, 10, 5, 5);

        mChart.setDragDecelerationFrictionCoef(0.95f);

        mChart.setHighlightPerTapEnabled(true);

        mChart.setLineThickness(3);

        setData(2, 100);

        mChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        // mChart.spin(2000, 0, 360);



    }


    private void setData(int count, float range) {

        float mult = range;

        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        for (int i = 0; i < count; i++) {
            entries.add(new PieEntry((float) ((Math.random() * mult) + mult / 5), mParties[i % mParties.length]));
        }

        SplitDataSet dataSet = new SplitDataSet(entries, "Election Results");

        dataSet.setLabelTextColor(Color.BLACK);
        Typeface typeFace = Typeface.create("Roboto", Typeface.BOLD);
        dataSet.setLabelTypeFace(typeFace);

        dataSet.setValueTextColor(Color.BLACK);
        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.MATERIAL_COLORS)
            colors.add(c);


        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);



        SplitData data = new SplitData(dataSet);
        data.setMultiline(false);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setLabelTextSize(11f);
        data.setValueTextColor(Color.GRAY);
        data.setValueTypeface(mTfLight);
        mChart.setData(data);

        // undo all highlights
        mChart.highlightValues(null);

        mChart.invalidate();
    }


}
