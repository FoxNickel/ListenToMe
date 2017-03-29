package cn.foxnickel.listentome;

import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;

public class DataAnalysis extends AppCompatActivity {

    private LineChart mLineChart;
    private BarChart mBarChart;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_analysis);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//显示返回上级的箭头
        getSupportActionBar().setDisplayShowTitleEnabled(false);//将actionbar原有的标题去掉
        /*返回上级*/
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        initLineChart();
        initBarChart();
    }

    private void initBarChart() {
        mBarChart = (BarChart) findViewById(R.id.bar_chart);

        /*1.chart格式设置*/
        mBarChart.setDrawGridBackground(false);//无背景网格
        mBarChart.setDrawBorders(false);//无边框

        //图表描述
        Description description = new Description();
        description.setText("近一周学习时间图（分钟/天）");//描述内容
        description.setTextColor(0xff000000);//描述字体颜色
        description.setTextSize(16f);//描述字体大小
        description.setTextAlign(Paint.Align.LEFT);//文字左对齐
        description.setPosition(100, 100);//设置图表描述
        mBarChart.setDescription(description);

        mBarChart.setTouchEnabled(false);//可触摸
        mBarChart.setDragEnabled(true);//可拖动
        mBarChart.setScaleEnabled(true);//可放缩

        /*2.获取坐标轴并进行设置*/
        //获取和设置X轴
        XAxis xAxis = mBarChart.getXAxis();//获取X轴
        xAxis.setEnabled(true);//设置显示X轴
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//X轴位置
        xAxis.setAxisLineWidth(2);//设置X轴宽度
        xAxis.setDrawGridLines(false);//无网格
        xAxis.setDrawAxisLine(true);//显示X轴
        /*X轴数据*/
        final String[] xValues = {"3.25", "3.26", "3.27", "3.28", "3.29", "3.30", "3.31"};
        /*给X轴设置数据*/
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return xValues[(int) value];
            }

            @Override
            public int getDecimalDigits() {
                return 0;
            }
        });
        xAxis.setDrawLabels(true);

        //获取并设置Y轴
        YAxis leftYAxis = mBarChart.getAxisLeft();//获取左侧Y轴
        YAxis rightYAxis = mBarChart.getAxisRight();//获取右侧Y轴
        rightYAxis.setEnabled(false);//禁止显示右侧Y轴
        leftYAxis.setAxisLineWidth(2);
        leftYAxis.setDrawGridLines(false);
        /*leftYAxis.setStartAtZero(true);//设置从零开始显示*/

        /*3.添加数据*/
        ArrayList<BarEntry> entries1 = new ArrayList<>();//Entry就是折线图上的点
        entries1.add(new BarEntry(0, 20));
        entries1.add(new BarEntry(1, 35));
        entries1.add(new BarEntry(2, 10));
        entries1.add(new BarEntry(3, 40));
        entries1.add(new BarEntry(4, 22));
        entries1.add(new BarEntry(5, 44));
        entries1.add(new BarEntry(6, 5));

        BarDataSet barDataSet = new BarDataSet(entries1, "使用时间");

        BarData barData = new BarData(barDataSet);

        mBarChart.setData(barData);
        mBarChart.invalidate();//刷新显示
    }

    private void initLineChart() {
        mLineChart = (LineChart) findViewById(R.id.line_chart);
        /*1.chart格式设置*/
        mLineChart.setDrawGridBackground(false);//无背景网格
        mLineChart.setDrawBorders(false);//无边框

        //图表描述
        Description description = new Description();
        description.setText("近一周学习情况（平均分/天）");//描述内容
        description.setTextColor(0xff000000);//描述字体颜色
        description.setTextSize(16f);//描述字体大小
        description.setTextAlign(Paint.Align.LEFT);//文字左对齐
        description.setPosition(100, 100);//设置图表描述
        mLineChart.setDescription(description);

        mLineChart.setTouchEnabled(false);//可触摸
        mLineChart.setDragEnabled(true);//可拖动
        mLineChart.setScaleEnabled(true);//可放缩

        /*2.获取坐标轴并进行设置*/
        //获取和设置X轴
        XAxis xAxis = mLineChart.getXAxis();//获取X轴
        xAxis.setEnabled(true);//设置显示X轴
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//X轴位置
        xAxis.setAxisLineWidth(2);//设置X轴宽度
        xAxis.setDrawGridLines(false);//无网格
        xAxis.setDrawAxisLine(true);//显示X轴
        /*X轴数据*/
        final String[] xValues = {"3.25", "3.26", "3.27", "3.28", "3.29", "3.30", "3.31"};
        /*给X轴设置数据*/
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return xValues[(int) value];
            }

            @Override
            public int getDecimalDigits() {
                return 0;
            }
        });

        //获取并设置Y轴
        YAxis leftYAxis = mLineChart.getAxisLeft();//获取左侧Y轴
        YAxis rightYAxis = mLineChart.getAxisRight();//获取右侧Y轴
        rightYAxis.setEnabled(false);//禁止显示右侧Y轴
        leftYAxis.setAxisLineWidth(2);
        leftYAxis.setDrawGridLines(false);
        /*leftYAxis.setStartAtZero(true);//设置从零开始显示*/

        /*3.添加数据*/
        ArrayList<Entry> entries1 = new ArrayList<>();//Entry就是折线图上的点
        entries1.add(new Entry(0, 85));
        entries1.add(new Entry(1, 88));
        entries1.add(new Entry(2, 75));
        entries1.add(new Entry(3, 69));
        entries1.add(new Entry(4, 95));
        entries1.add(new Entry(5, 77));
        entries1.add(new Entry(6, 0));

        ArrayList<Entry> entries2 = new ArrayList<>();
        entries2.add(new Entry(0, 75));
        entries2.add(new Entry(1, 88));
        entries2.add(new Entry(2, 55));
        entries2.add(new Entry(3, 79));
        entries2.add(new Entry(4, 85));
        entries2.add(new Entry(5, 97));
        entries2.add(new Entry(6, 0));

        /*LineDataSet是点的集合，连成线*/
        LineDataSet lineDataSet1 = new LineDataSet(entries1, "听力");
        lineDataSet1.setColor(0xff3f51b5);
        LineDataSet lineDataSet2 = new LineDataSet(entries2, "口语");
        lineDataSet2.setColor(0xffff4081);

        /*线条的集合（可以添加多条线）*/
        List<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(lineDataSet1);
        dataSets.add(lineDataSet2);

        /*要给Chart设置的数据（将dataSets作为数据对象）*/
        LineData lineData = new LineData(dataSets);

        mLineChart.setData(lineData);//设置数据
        mLineChart.invalidate();//刷新显示
    }

}
