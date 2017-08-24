package feather.record.Context;

import android.app.Activity;
import android.app.DatePickerDialog;
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

import feather.record.Data.EnterInfo;
import feather.record.R;

public class CostDialog extends Activity implements View.OnClickListener {

    //元件
    TextView date;
    EditText money, note, nameed;
    Button check;
    Spinner option;
    String name = "";

    //日期
    String yy = "", mm = "", dd = "", setdate = "";
    int yi, di, mi;

    //spinner
    int sp;
    String[] option_list = {"進貨", "預支"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_cost);
        ReadUserInfo();
        init();

    }

    public void init() {
        date = (TextView) findViewById(R.id.dialog_cost_date);
        date.setText(MainActivity.today);
        date.setOnClickListener(this);

        money = (EditText) findViewById(R.id.dialog_cost_money);
        nameed = (EditText) findViewById(R.id.dialog_cost_name);
        nameed.setText(name);
        nameed.setEnabled(false);
        note = (EditText) findViewById(R.id.dialog_cost_note);

        check = (Button) findViewById(R.id.dialog_cost_check);
        check.setOnClickListener(this);

        option = (Spinner) findViewById(R.id.dialog_cost_option_sp);
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
        ArrayAdapter<String> spadapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, option_list);
        spadapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        option.setAdapter(spadapter);

    }

    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int month,
                              int day) {
            yy = String.valueOf(year);
            mm = String.valueOf(month + 1);
            dd = String.valueOf(day);

            if (mm.length() < 2) {
                mm = "0" + mm;
            }
            if (dd.length() < 2) {
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

            case R.id.dialog_cost_date:

                new DatePickerDialog(this, d, yi, mi, di).show();

                break;
            case R.id.dialog_cost_check:

                String datest = date.getText().toString().trim();
                String moneyst = money.getText().toString().trim();
                String notest = note.getText().toString().trim();
                if (!moneyst.equals("")) {
                    EnterInfo enter = new EnterInfo("cost", datest, name, option_list[sp], "-" + moneyst, notest);
                    MainActivity.myRef.child("Info").child(yy).child(mm).push().setValue(enter);
                }
                finish();

                break;
        }
    }

    private void ReadUserInfo() {

        //預設關閉
        SharedPreferences sharedata = this.getSharedPreferences("userInfo", 0);
        name = sharedata.getString("name", "");
        Log.d("setup", "read name = " + name);
    }

}
