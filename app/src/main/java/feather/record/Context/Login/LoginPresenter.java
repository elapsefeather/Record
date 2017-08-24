package feather.record.Context.Login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import feather.record.Context.MainActivity;
import feather.record.Data.UserInfo;

/**
 * Created by lycodes on 2017/8/9.
 */

public class LoginPresenter {

    public LoginActivity loginActivity;
    public LoginModel loginModel;
    Boolean remember = false, auto = false;
    UserInfo user;
    String account = "", password = "";

    int check = 0;//０＝無此帳號，１＝密碼錯誤，２＝登入成功

    public LoginPresenter(LoginActivity loginActivity) {
        this.loginActivity = loginActivity;
        this.loginModel = new LoginModel(this);
    }

    public void getUser() {
        loginActivity.progressDialog = ProgressDialog.show(loginActivity, "", "Loading...");

        account=loginActivity.accounted.getText().toString().trim();
        password=loginActivity.passworded.getText().toString().trim();

        loginModel.getUser(account);

    }

    public void loginCheck() {

        check = 0;//初始化

        for (int i = 0; i < loginModel.userlist.size(); i++) {
            user = loginModel.userlist.get(i);
            Log.i("user", "loginCheck  account = " + user.getAccount() + "  name = " + user.getName() + "  password = " + user.getPassword());
            Log.i("user", "loginCheck enter  account = " + account + "  password = " + password);

            if (user.getAccount().equals(account)) {
                check = 1;
                if (user.getPassword().equals(password)) {
                    check = 2;
                    WriteUserInfo(user.getAccount(), user.getName(), user.getPassword());//寫入使用者資訊
                    WriteAccountSetting(); //寫入帳密設定
                }
                break;
            }
        }
        Log.i("user","check = "+ check);
        switch (check) {
            case 0:
//                        無此帳號
                Toast.makeText(loginActivity, "無此帳號", Toast.LENGTH_LONG).show();
                break;
            case 1:
//                        密碼錯誤
                Toast.makeText(loginActivity, "密碼錯誤", Toast.LENGTH_LONG).show();
                break;
            case 2:
                //正確

                Toast.makeText(loginActivity, "登入成功", Toast.LENGTH_LONG).show();

                Intent login = new Intent(loginActivity, MainActivity.class);
                loginActivity.startActivity(login);
                loginActivity.finish();

                break;
        }
        loginActivity.progressDialog.dismiss();
    }

    public void setAccountSetting() {

        ReadAccountSetting();
        loginActivity.remember.setChecked(remember);
        loginActivity.auto.setChecked(auto);

        if (remember) {
            ReadUserInfo();
            loginActivity.accounted.setText(account);
            loginActivity.passworded.setText(password);
        }

        if (auto) {
            getUser();
            Log.i("account set", "autocheck");
        }

    }

    private void ReadAccountSetting() {

        //預設關閉
        SharedPreferences sharedata = loginActivity.getSharedPreferences("AccountSetting", 0);
        remember = sharedata.getBoolean("remember", false);
        auto = sharedata.getBoolean("auto", false);

    }

    private void WriteAccountSetting() {
        SharedPreferences.Editor sharedata = loginActivity.getSharedPreferences("AccountSetting", 0).edit();
        sharedata.putBoolean("remember", loginActivity.remember.isChecked());
        sharedata.putBoolean("auto", loginActivity.auto.isChecked());
        sharedata.commit();
    }

    private void ReadUserInfo() {

        //預設關閉
        SharedPreferences sharedata = loginActivity.getSharedPreferences("userInfo", 0);
        account = sharedata.getString("account", "");
        password = sharedata.getString("password", "");
    }

    private void WriteUserInfo(String account, String name, String password) {

        /*
        Context.MODE_PRIVATE    =  0        私有数据
        Context.MODE_APPEND    =  32768     模式会检查文件是否存在，存在就往文件追加内容，否则就创建新文件
        Context.MODE_WORLD_READABLE =  1    可以被其他应用读取
        Context.MODE_WORLD_WRITEABLE =  2   可以被其他应用写入
        */
        SharedPreferences.Editor sharedata = loginActivity.getSharedPreferences("userInfo", 0).edit();
        sharedata.putString("account", account);
        sharedata.putString("password", password);
        sharedata.putString("name", name);
        sharedata.commit();

    }
}
