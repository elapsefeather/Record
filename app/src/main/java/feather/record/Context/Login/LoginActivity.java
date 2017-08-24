package feather.record.Context.Login;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import feather.record.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText accounted, passworded;
    Button login_btn;
    LoginPresenter loginPresenter = new LoginPresenter(this);
    CheckBox remember, auto;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();

    }

    public void init() {
        progressDialog = new ProgressDialog(this);

        accounted = (EditText) findViewById(R.id.login_account);
        passworded = (EditText) findViewById(R.id.login_password);

        remember = (CheckBox) findViewById(R.id.login_remember);
        remember.setOnClickListener(this);
        auto = (CheckBox) findViewById(R.id.login_auto);
        auto.setOnClickListener(this);
        loginPresenter.setAccountSetting();

        login_btn = (Button) findViewById(R.id.login_Btn);
        login_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.login_Btn:

                loginPresenter.getUser();

                break;
            case R.id.login_remember:
//                如果不記住  不能自動登入
                if (!remember.isChecked()) {
                    auto.setChecked(false);
                }
                break;
            case R.id.login_auto:
//                如果要自動登入  一定要記住
                if (auto.isChecked()) {
                    remember.setChecked(true);
                }
                break;
        }
    }
}
