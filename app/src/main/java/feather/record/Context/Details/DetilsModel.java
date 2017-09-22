package feather.record.Context.Details;

import android.content.ContentValues;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import feather.record.Context.MainActivity;
import feather.record.Data.EnterInfo;
import feather.record.Other.API;

/**
 * Created by feather on 2017/6/26.
 */

public class DetilsModel {

    DetailsPresenter detailsPresenter;
    public ArrayList<EnterInfo> list = new ArrayList<>();
    public ArrayList<String> year = new ArrayList<>();
    public ArrayList<String> month = new ArrayList<>();
    String year_item = "", month_item = "";

    public DetilsModel(DetailsPresenter detailsPresenter) {
        this.detailsPresenter = detailsPresenter;
        year_item = detailsPresenter.detilsActivity.YY;
        month_item = detailsPresenter.detilsActivity.MM;
    }

    public void getYear() {

        for (int i = Integer.parseInt(year_item) - 2; i <= Integer.parseInt(year_item) + 2; i++) {
            year.add("" + i);
        }
        for (int i = 1; i <= 12; i++) {
            if (i < 10) {
                month.add("0" + i);
            } else {
                month.add("" + i);
            }
        }
    }

    public void getData() {

        list.clear();
        Log.i("getData", "year_item = " + year_item + " month_item = " + month_item);
        list = MainActivity.helper.Details_Select(year_item, month_item);
        Log.i("getData", "list = " + list);
    }

    public void detils_delect(int i) {
        //  用項目獲取 id 刪除項目
        String id = String.valueOf(list.get(i).getId());
//        進行資料庫刪除動作
        MainActivity.helper.details_delect(id);
    }

    public void detils_update(int i, ContentValues values) {
        String id = String.valueOf(list.get(i).getId());
        MainActivity.helper.details_update(id, values);
    }

}
