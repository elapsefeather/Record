package feather.record.Context.Details;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import feather.record.Context.EnterDialog;
import feather.record.Context.MainActivity;
import feather.record.Data.EnterInfo;
import feather.record.R;

public class DetailsActivity extends AppCompatActivity {

    Spinner spinner_year, spinner_month;
    ExpandableListView exlistview;
    TextView nodata;
    ArrayList<String> edit_item;

    DetailsPresenter detailsPresenter;
    ProgressDialog progressDialog;
    String YY, MM;
    int yeartest = 0, monthtest = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        init();
        getfirstdata();

    }

    public void init() {
        detailsPresenter = new DetailsPresenter(this);
        detailsPresenter.detilsModel.year_item = MainActivity.yy;
        detailsPresenter.detilsModel.month_item = MainActivity.mm;
        YY = MainActivity.yy;
        MM = MainActivity.mm;

        nodata = (TextView) findViewById(R.id.details_nodata);

        spinner_year = (Spinner) findViewById(R.id.details_spinner_year);
        spinner_year.setAdapter(detailsPresenter.yearAdapter);
        spinner_year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.i("ShowoverActivity", "type " + position);
                if (yeartest != 0) {
//                  初始化不執行
                    if (detailsPresenter.detilsModel.year_item != detailsPresenter.detilsModel.year.get(position)) {
                        detailsPresenter.detilsModel.year_item = detailsPresenter.detilsModel.year.get(position);
                        Log.i("change", "year_item = " + detailsPresenter.detilsModel.year_item);
                        detailsPresenter.getData();
                        detailsPresenter.notifyData();
                    }
                }
                yeartest++;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner_month = (Spinner) findViewById(R.id.details_spinner_month);
        spinner_month.setAdapter(detailsPresenter.monthAdapter);
        spinner_month.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.i("ShowoverActivity", "type " + position);
                if (monthtest != 0) {
//                  初始化不執行
                    if (detailsPresenter.detilsModel.month_item != detailsPresenter.detilsModel.month.get(position)) {
                        detailsPresenter.detilsModel.month_item = detailsPresenter.detilsModel.month.get(position);
                        Log.i("change", "month_item = " + detailsPresenter.detilsModel.month_item);
                        detailsPresenter.getData();
                        detailsPresenter.notifyData();
                    }
                }
                monthtest++;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        edit_item = new ArrayList<>();
        edit_item.add(getString(R.string.edit_item_update));
        edit_item.add(getString(R.string.edit_item_delect));

        exlistview = (ExpandableListView) findViewById(R.id.details_exlistview);
        exlistview.setAdapter(detailsPresenter.detilsAdapter);
        exlistview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int i, long id) {
//                長按項目 修改、
                new AlertDialog.Builder(DetailsActivity.this)
                        .setItems(edit_item.toArray(new String[edit_item.size()]), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String name = edit_item.get(which);
                                switch (which) {
                                    case 0:
//                                                                      修改
                                        update_item(i);
                                        break;
                                    case 1:
//                                                                      刪除
                                        delete_item(i);
                                        break;
                                }
                            }
                        })
                        .show();
                return false;
            }
        });
    }

    public void update_item(int i) {
        EnterInfo info = detailsPresenter.detilsModel.list.get(i);
        Intent change = new Intent(this, EnterDialog.class);
        change.putExtra("id", info.getId());
        Log.i("enter", "id = " + info.getId());
        change.putExtra("change", 1);
        change.putExtra("type", info.getItem());
        change.putExtra("year", info.getYear());
        change.putExtra("month", info.getMonth());
        change.putExtra("day", info.getDay());
        change.putExtra("name", info.getName());
        change.putExtra("option", info.getOption());
        change.putExtra("money", info.getMoney());
        change.putExtra("note", info.getNote());
        startActivity(change);

    }

    public void delete_item(final int i) {

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                Log.i("notify", "onPreExecute");
                progressStart();
//                              進行刪除
                detailsPresenter.detils_delete(i);
            }

            @Override
            protected Void doInBackground(Void... params) {
                Log.i("notify", "doInBackground");
//                              重新撈取資料
                detailsPresenter.getData();
                return null;
            }

            protected void onPostExecute(Void i) {
                super.onPostExecute(i);
                Log.i("notify", "onPostExecute");
//                              刷新資料
                detailsPresenter.notifyData();
                progressStop();
            }
        }.execute(null, null, null);
    }

    public void getfirstdata() {

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                Log.i("notify", "onPreExecute");
                progressStart();
            }

            @Override
            protected Void doInBackground(Void... params) {
                Log.i("notify", "doInBackground");
                detailsPresenter.getYear();
                detailsPresenter.getData();

                return null;
            }

            protected void onPostExecute(Void i) {
                super.onPostExecute(i);
                Log.i("notify", "onPostExecute");
                detailsPresenter.notifyfirst();
                detailsPresenter.notifyData();
                setSelction();
                progressStop();
            }
        }.execute(null, null, null);
    }

    public void setSelction() {
        //        預設 year
        for (int i = 0; i < detailsPresenter.detilsModel.year.size(); i++) {
            if (YY.equals(detailsPresenter.detilsModel.year.get(i))) {
                spinner_year.setSelection(i, false);
                detailsPresenter.detilsModel.year_item = YY;
                Log.i("spinner", "spinner_year.setSelection =  " + i);
                break;
            }
        }
        //        預設 month
        for (int i = 0; i < detailsPresenter.detilsModel.month.size(); i++) {
            if (MM.equals(detailsPresenter.detilsModel.month.get(i))) {
                spinner_month.setSelection(i, false);
                detailsPresenter.detilsModel.month_item = MM;
                Log.i("spinner", "spinner_month.setSelection =  " + i);
                break;
            }
        }
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
    @Override
    public void onResume() {
        super.onResume();
        detailsPresenter.getData();
        detailsPresenter.notifyData();
        Log.i("details", "onResume");
    }

}
