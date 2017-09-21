package feather.record.Context.Chart;

import android.util.Log;

import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import feather.record.Context.MainActivity;
import feather.record.Data.ChartInfo;
import feather.record.Data.EnterInfo;

/**
 * Created by feather on 2017/9/18.
 */

public class ChartModel {

    ChartPresenter chartPresenter;
    ArrayList<ChartInfo> earn_list, cost_list = new ArrayList<>();
    ArrayList<String> label_list = new ArrayList<>();
    public ArrayList<String> year = new ArrayList<>();
    String year_item = "";

    public ChartModel(ChartPresenter chartPresenter) {
        this.chartPresenter = chartPresenter;

        year_item = chartPresenter.chartActivity.YY;
    }

    public void getyear() {
        for (int i = Integer.parseInt(year_item) - 2; i <= Integer.parseInt(year_item) + 2; i++) {
            year.add("" + i);
        }
        chartPresenter.notifyYear();
    }

    public void getData() {
        earn_list = new ArrayList<>();
        cost_list = new ArrayList<>();
        label_list = new ArrayList<>();
        earn_list.clear();
        cost_list.clear();
        label_list.clear();

        Log.i("chart_select", "clear size = " +  earn_list.size());
        Log.i("chart_select", "year_item = " +  year_item);
        for (int i = 1; i <= 12; i++) {
            Log.i("chart_select", "i = " + i);
            if (i < 10) {
                earn_list.add(MainActivity.helper.chart_select("earn", "0" + i, year_item));
                cost_list.add(MainActivity.helper.chart_select("cost", "0" + i, year_item));
                label_list.add("0" + i + "月");
            } else {
                earn_list.add(MainActivity.helper.chart_select("earn", "" + i, year_item));
                cost_list.add(MainActivity.helper.chart_select("cost", "" + i, year_item));
                label_list.add("" + i + "月");
            }
        }
        Log.i("chart_select", "add size = " +  earn_list.size());
        chartPresenter.setBarChartData();
//        chartPresenter.setLineChartData();
    }

}
