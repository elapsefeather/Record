package feather.record.Context.Chart;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.annotation.ColorInt;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import feather.record.Context.MainActivity;
import feather.record.Data.ChartInfo;
import feather.record.R;

public class ChartActivity extends AppCompatActivity {

    ChartPresenter chartPresenter;
    ProgressDialog progressDialog;
    LineChart lineChart;
    BarChart barChart;
    BarData data = new BarData();
    ArrayList<IBarDataSet> dataSets;
    Spinner spinner_year;
    int yeartest = 0;
    String YY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        init();

        getFirst();
    }

    public void init() {
        chartPresenter = new ChartPresenter(this);

        chartPresenter.chartModel.year_item = MainActivity.yy;
        YY = MainActivity.yy;

        spinner_year = (Spinner) findViewById(R.id.chart_sp_year);
        spinner_year.setAdapter(chartPresenter.yearAdapter);
        spinner_year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.i("chartactivity", "type = " + position);
                if (yeartest != 0) {
//                  初始化不執行
                    Log.i("chartactivity", "year_item  = " + chartPresenter.chartModel.year_item);
                    Log.i("chartactivity", "year =  " + chartPresenter.chartModel.year.get(position));
                    if (!chartPresenter.chartModel.year_item.equals(chartPresenter.chartModel.year.get(position))) {
                        chartPresenter.chartModel.year_item = chartPresenter.chartModel.year.get(position);
                        getData();
                    }
                }
                yeartest++;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        barChart = (BarChart) findViewById(R.id.chart_barchart);
        barChart.setDescription("");
        barChart.animateXY(2000, 2000);
        barChart.setData(data);
        XAxis bar_xAxis = barChart.getXAxis();
        bar_xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        YAxis bar_leftAxis = barChart.getAxisLeft();
        bar_leftAxis.setStartAtZero(true);//  從0開始
        YAxis bar_rightAxis = barChart.getAxisRight();
        bar_rightAxis.setEnabled(false);//隱藏右邊
//    lineChart = (LineChart) findViewById(R.id.chart_linechart);
//        lineChart.setDescription("");
//        lineChart.animateXY(2000, 2000);
//        lineChart.getAxisRight().setEnabled(false); //隐藏Y轴右边轴线，此时标签数字也隐藏
//        XAxis xl = lineChart.getXAxis();
//        xl.setPosition(XAxis.XAxisPosition.BOTTOM);//设置X轴的位置TOP, BOTTOM, BOTH_SIDED, TOP_INSIDE, BOTTOM_INSIDE

    }

    public void setSelection() {

        for (int i = 0; i < chartPresenter.chartModel.year.size(); i++) {
            if (YY.equals(chartPresenter.chartModel.year.get(i))) {
                spinner_year.setSelection(i, false);
                chartPresenter.chartModel.year_item = YY;
                Log.i("spinner", "spinner_year.setSelection =  " + i);
                break;
            }
        }
    }

    public void getFirst() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressStart();
                barChart.clear();
            }

            @Override
            protected Void doInBackground(Void... params) {
                chartPresenter.getYear();
                chartPresenter.getData();
                return null;
            }

            protected void onPostExecute(Void i) {
                super.onPostExecute(i);
                setSelection();
                setBarChart();
                progressStop();
            }
        }.execute(null, null, null);
    }

    public void getData() {

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressStart();
                barChart.clear();
                dataSets.removeAll(chartPresenter.earn_barentry);
                dataSets.removeAll(chartPresenter.cost_barentry);
            }

            @Override
            protected Void doInBackground(Void... params) {
                chartPresenter.getData();
                return null;
            }

            protected void onPostExecute(Void i) {
                super.onPostExecute(i);
//                setLineChart();
                setBarChart();
                progressStop();
            }
        }.execute(null, null, null);
    }

    public void setBarChart() {

        Log.i("notify", "setBarChart ");
        BarDataSet earn_barset = new BarDataSet(chartPresenter.earn_barentry, "earn");
        earn_barset.setColor(Color.rgb(0, 155, 0));
        BarDataSet cost_barset = new BarDataSet(chartPresenter.cost_barentry, "cost");
        cost_barset.setColor(Color.rgb(155, 0, 0));

        dataSets = new ArrayList<>();
        dataSets.add(earn_barset);
        dataSets.add(cost_barset);
        data = new BarData(chartPresenter.chartModel.label_list, dataSets);
        barChart.setData(data);
        barChart.notifyDataSetChanged();
        barChart.invalidate();
    }

    public void setLineChart() {
        ArrayList<ILineDataSet> line = new ArrayList<>();

        LineDataSet dataset = new LineDataSet(chartPresenter.earn_entry, "earn");
        dataset.setDrawFilled(false); // 線下顏色
        dataset.setLineWidth(5f); // 线宽
        dataset.setCircleSize(5f);// 显示的圆形大小
        dataset.setCircleColor(Color.WHITE);// 圆形的颜色
        dataset.setHighLightColor(Color.GREEN); // 高亮的线的颜色

        LineDataSet dataset1 = new LineDataSet(chartPresenter.cost_entry, "cost");
        dataset1.setDrawFilled(false); // 線下顏色
        dataset1.setLineWidth(5f); // 线宽
        dataset1.setCircleSize(5f);// 显示的圆形大小
        dataset1.setCircleColor(Color.WHITE);// 圆形的颜色
        dataset1.setHighLightColor(Color.RED); // 高亮的线的颜色

        line.add(dataset);
        line.add(dataset1);

        LineData data = new LineData(chartPresenter.chartModel.label_list, line);
        lineChart.setData(data);
    }

    public void progressStart() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("請稍候");
        progressDialog.show();
    }

    public void progressStop() {
        if (progressDialog != null) {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }
    }
}
