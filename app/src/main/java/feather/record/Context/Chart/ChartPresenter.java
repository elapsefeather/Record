package feather.record.Context.Chart;

import android.graphics.Color;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import feather.record.Data.ChartInfo;

/**
 * Created by feather on 2017/9/18.
 */

public class ChartPresenter {

    ChartActivity chartActivity;
    ChartModel chartModel;
    ArrayList<Entry> cost_entry, earn_entry = new ArrayList<>();
    ArrayList<BarEntry> cost_barentry, earn_barentry = new ArrayList<>();
    public ArrayAdapter<String> yearAdapter;

    public ChartPresenter(ChartActivity chartActivity) {
        this.chartActivity = chartActivity;
        this.chartModel = new ChartModel(this);

        yearAdapter = new ArrayAdapter<String>(chartActivity, android.R.layout.simple_spinner_item, chartModel.year);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
    }

    public void getData() {
        chartModel.getData();
    }

    public void setBarChartData() {
        Log.i("notify", "setBarChartData " );
        //        一定要加在這　不然不能執行
        earn_barentry = new ArrayList<>();
        cost_barentry = new ArrayList<>();

        Log.i("chart_select", "add size = " +  earn_barentry.size());
        for (int i = 0; i < 12; i++) {
            ChartInfo costinfo = chartModel.cost_list.get(i);
            cost_barentry.add(new BarEntry(Float.parseFloat(costinfo.getMoney()), i));

            ChartInfo earninfo = chartModel.earn_list.get(i);
            earn_barentry.add(new BarEntry(Float.parseFloat(earninfo.getMoney()), i));
        }
        Log.i("chart_select", "add size = " +  earn_barentry.size());
    }

    public void setLineChartData() {
//        一定要加在這　不然不能執行
        earn_entry = new ArrayList<>();
        cost_entry = new ArrayList<>();

        for (int i = 0; i < 12; i++) {
            ChartInfo costinfo = chartModel.cost_list.get(i);
            Entry e = new Entry(Float.parseFloat(costinfo.getMoney()), i);
            Log.i("chart_select", "e = " + e);
            cost_entry.add(e);

            ChartInfo earninfo = chartModel.earn_list.get(i);
            earn_entry.add(new Entry(Float.parseFloat(earninfo.getMoney()), i));
        }
    }

    public void getYear() {
        chartModel.getyear();
    }

    public void notifyYear() {
        yearAdapter.notifyDataSetChanged();
    }
}
