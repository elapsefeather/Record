package feather.record.Data;

/**
 * Created by lycodes on 2017/8/9.
 */

public class UserInfo {

    String account, name, password;

    public UserInfo(String account, String name, String password) {
        this.account = account;
        this.name = name;
        this.password = password;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAccount() {
        return this.account;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return this.password;
    }

}
