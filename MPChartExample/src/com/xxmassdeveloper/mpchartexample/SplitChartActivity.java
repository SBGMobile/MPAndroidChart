
package com.xxmassdeveloper.mpchartexample;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.WindowManager;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.SplitChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.data.SplitData;
import com.github.mikephil.charting.data.SplitDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.xxmassdeveloper.mpchartexample.notimportant.DemoBase;

import java.util.ArrayList;

public class SplitChartActivity extends DemoBase {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splitchart);


        SplitData splitData1 = createSplitData(2, 100);
        configureSplitChart(splitData1, (SplitChart) findViewById(R.id.chart1));

        SplitData splitData2 = createSplitData(2, 100);
        SplitDataSet splitDataSet = (SplitDataSet) splitData2.getDataSet();
        splitDataSet.setMultiline(true);
        splitDataSet.setTextHorizontalOffset(50);
        configureSplitChart(splitData2, (SplitChart) findViewById(R.id.chart2));


    }

    private void configureSplitChart(SplitData splitData, SplitChart splitChart) {
        splitChart.setDescription(new Description());

        splitChart.setDragDecelerationFrictionCoef(0.95f);

        splitChart.setHighlightPerTapEnabled(true);


        splitChart.setData(splitData);

        // undo all highlights
        splitChart.highlightValues(null);

        splitChart.invalidate();

        splitChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
    }


    private SplitData createSplitData(int count, float range) {

        float mult = range;

        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        for (int i = 0; i < count; i++) {
            float value = (float) ((Math.random() * mult) + mult / 5);
            if (i == 1) {
                value *= -1;
            }
            final PieEntry pieEntry = new PieEntry(value, mParties[i % mParties.length]);
            entries.add(pieEntry);
        }

        SplitDataSet dataSet = new SplitDataSet(entries, "Election Results");
        dataSet.setLineThickness(3);
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

        return data;
    }


}
