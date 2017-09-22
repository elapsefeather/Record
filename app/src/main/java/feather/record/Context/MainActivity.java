package feather.record.Context;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

import feather.record.Context.Chart.ChartActivity;
import feather.record.Context.Details.DetailsActivity;
import feather.record.Context.Login.LoginActivity;
import feather.record.Other.API;
import feather.record.Other.DB_Helper;
import feather.record.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    LinearLayout earn, cost, details, chart, logout;

    //    SQLite
    public static DB_Helper helper;

    //    firebase database
    public static DatabaseReference myRef;

    //日期
    public static String today = "", yy = "", mm = "", dd = "";
    int yi, di, mi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        helper = new DB_Helper(this, "expense.db", null, 1);

        getdate();
        init();
    }

    public void init() {

        earn = (LinearLayout) findViewById(R.id.main_earn);
        earn.setOnClickListener(this);

        cost = (LinearLayout) findViewById(R.id.main_cost);
        cost.setOnClickListener(this);

        details = (LinearLayout) findViewById(R.id.main_details);
        details.setOnClickListener(this);

        chart = (LinearLayout) findViewById(R.id.main_chart);
        chart.setOnClickListener(this);

        logout = (LinearLayout) findViewById(R.id.main_logout);
        logout.setOnClickListener(this);

        //連接資料庫
        myRef = FirebaseDatabase.getInstance().getReferenceFromUrl(API.firebase_data);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {

            case R.id.main_earn:

                Intent earn_dialog = new Intent(this, EnterDialog.class);
                earn_dialog.putExtra("type", "earn");
                startActivity(earn_dialog);

                break;
            case R.id.main_cost:

                Intent cost_dialog = new Intent(this, EnterDialog.class);
                cost_dialog.putExtra("type", "cost");
                startActivity(cost_dialog);

                break;
            case R.id.main_details:

                Intent details = new Intent(this, DetailsActivity.class);
                startActivity(details);

                break;
            case R.id.main_chart:
                Intent chart = new Intent(this, ChartActivity.class);
                startActivity(chart);
                break;
            case R.id.main_logout:

                WriteAccountSetting();

                Intent login = new Intent(this, LoginActivity.class);
                startActivity(login);
                finish();

                break;
        }
    }

    public void getdate() {
        // 取得目前日期
        Calendar c = Calendar.getInstance();
        yi = c.get(Calendar.YEAR);
        mi = c.get(Calendar.MONTH);
        di = c.get(Calendar.DAY_OF_MONTH);

        yy = String.valueOf(yi);
        mm = String.valueOf(mi + 1);
        dd = String.valueOf(di);

        if (mm.length() < 2) {
            mm = "0" + mm;
        }
        if (dd.length() < 2) {
            dd = "0" + dd;
        }

        today = yy + "-" + mm + "-" + dd;
    }

    private void WriteAccountSetting() {
        SharedPreferences.Editor sharedata = getSharedPreferences("AccountSetting", 0).edit();
        sharedata.putBoolean("auto", false);//取消自動登入
        sharedata.commit();
    }
}
