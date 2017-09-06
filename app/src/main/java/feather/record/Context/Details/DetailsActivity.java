package feather.record.Context.Details;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.TextView;

import feather.record.Context.MainActivity;
import feather.record.R;

public class DetailsActivity extends AppCompatActivity {

    Spinner spinner_year, spinner_month;
    ExpandableListView exlistview;
    TextView nodata;

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

        YY = MainActivity.yy;
        MM = MainActivity.mm;

        nodata = (TextView) findViewById(R.id.details_nodata);

        detailsPresenter = new DetailsPresenter(this);

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

        exlistview = (ExpandableListView) findViewById(R.id.details_exlistview);
        exlistview.setAdapter(detailsPresenter.detilsAdapter);

    }
     public void setSpinnerSelection(){

         //        預設 year
         spinner_year.setSelection(2);
         //        預設 month
         for(int i = 0;i<detailsPresenter.detilsModel.month.size();i++)
         {
             if(MM.equals(detailsPresenter.detilsModel.month.get(i))){
                 spinner_month.setSelection(i);
             }
         }
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
                setSpinnerSelection();//spinner預設

                progressStop();
            }
        }.execute(null, null, null);
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
