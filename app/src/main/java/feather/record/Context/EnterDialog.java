package feather.record.Context;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import feather.record.R;

public class EnterDialog extends Activity implements View.OnClickListener {

    //元件
    TextView date;
    EditText money, note, nameed;
    Button check;
    Spinner option;
    String name = "",optionst, type = "", DB_id = "";
    String notest, moneyst;
    //日期
    String yy = "", mm = "", dd = "", setdate = "";
    int yi, di, mi, change = 0;

    //spinner
    int sp;
    String[] earn_list = {"開箱", "增資"};
    String[] cost_list = {"進貨", "台租"};
    String[] use_list = new String[]{};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_enter);

        Intent intent = this.getIntent();
        type = intent.getStringExtra("type");
//                  0 = 新增，1 = 更新
        change = intent.getIntExtra("change", 0);

        setType();
        ReadUserInfo();
        init();
        if (change == 1) {
            DB_id = intent.getStringExtra("id");
            yy=intent.getStringExtra("year");
            mm=intent.getStringExtra("month");
            dd=intent.getStringExtra("day");
//            name=intent.getStringExtra("name");
            optionst=intent.getStringExtra("option");
            moneyst=intent.getStringExtra("money");
            notest=intent.getStringExtra("note");
            setInfo();
        }
    }

    public void setInfo() {

        yi = Integer.parseInt(yy);
        mi = Integer.parseInt(mm) - 1;
        di = Integer.parseInt(dd);

        for (int i = 0; i <use_list.length; i++) {
            if (option.equals(use_list[i])) {
                option.setSelection(i, false);
                Log.i("spinner", "spinner_year.setSelection =  " + i);
                break;
            }
        }

        setdate = yy + "-" + mm + "-" + dd;
        date.setText(setdate);

        money.setText(moneyst);
        note.setText(notest);

    }

    public void setType() {
        switch (type) {
            case "cost":
                use_list = cost_list;
                break;
            case "earn":
                use_list = earn_list;
                break;
        }
    }

    public void init() {

        yy = MainActivity.yy;
        mm = MainActivity.mm;
        dd = MainActivity.dd;

        yi = Integer.parseInt(yy);
        mi = Integer.parseInt(mm) - 1;
        di = Integer.parseInt(dd);

        date = (TextView) findViewById(R.id.dialog_earn_date);
        date.setText(MainActivity.today);
        date.setOnClickListener(this);

        money = (EditText) findViewById(R.id.dialog_earn_money);
        nameed = (EditText) findViewById(R.id.dialog_earn_name);
        nameed.setText(name);
        nameed.setEnabled(false);
        note = (EditText) findViewById(R.id.dialog_earn_note);

        check = (Button) findViewById(R.id.dialog_earn_check);
        check.setOnClickListener(this);

        option = (Spinner) findViewById(R.id.dialog_earn_option_sp);
        option.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.i("ShowoverActivity", "type " + position);
                if (sp != position) {
                    sp = position;
                    Log.i("change", "sp = " + sp);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayAdapter<String> spadapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, use_list);
        spadapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        option.setAdapter(spadapter);

    }

    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int month, int day) {
            yy = String.valueOf(year);
            mm = String.valueOf(month + 1);
            dd = String.valueOf(day);

            if (month < 9) {
                mm = "0" + mm;
            }
            if (day < 10) {
                dd = "0" + dd;
            }
            setdate = yy + "-" + mm + "-" + dd;
            date.setText(setdate);
        }
    };

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {

            case R.id.dialog_earn_date:

                new DatePickerDialog(this, d, yi, mi, di).show();

                break;
            case R.id.dialog_earn_check:

                moneyst = money.getText().toString().trim();
                notest = note.getText().toString().trim();
                if (!moneyst.equals("")) {
                    Log.i("enter", "yy = " + yy);
                    Log.i("enter", "mm = " + mm);
                    Log.i("enter", "dd = " + dd);
                    Log.i("enter", "moneyst = " + moneyst);
                    switch (change) {
                        case 0:
//                                  新增
                            Log.i("enter", "new ");
                            MainActivity.helper.Add(type, yy, mm, dd, name, use_list[sp], moneyst, notest);
                            break;
                        case 1:
//                                  更新
                            Log.i("enter", "update ");
                            MainActivity.helper.details_update(DB_id, type, yy, mm, dd, name, use_list[sp], moneyst, notest);
                            break;
                    }
                    finish();
                    break;
                }
        }
    }

    private void ReadUserInfo() {

        //預設關閉
        SharedPreferences sharedata = this.getSharedPreferences("userInfo", 0);
        name = sharedata.getString("name", "");
        Log.d("setup", "read name = " + name);
    }
}
