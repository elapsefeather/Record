package feather.record.Data;

/**
 * Created by feather on 2017/6/26.
 */

public class EnterInfo {

    String item, date, name, option, money, note;

    public EnterInfo(String item, String date, String name, String option, String money, String note) {

        this.item = item;
        this.date = date;
        this.name = name;
        this.option = option;
        this.money = money;
        this.note = note;

    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
