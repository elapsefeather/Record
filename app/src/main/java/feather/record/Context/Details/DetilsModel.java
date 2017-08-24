package feather.record.Context.Details;

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

        for (int i = Integer.parseInt(year_item)-2; i <= Integer.parseInt(year_item)+2; i++) {
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
        Log.i("model", "y(i) = " + year_item);
        Log.i("model", "m(i) = " + month_item);
        MainActivity.myRef = FirebaseDatabase.getInstance().getReferenceFromUrl(API.firebase_data)
                .child("Info").child(year_item).child(month_item);

        MainActivity.myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                Object i = dataSnapshot.getValue();
                String item = dataSnapshot.child("item").getValue().toString();
                String date = dataSnapshot.child("date").getValue().toString();
                String name = dataSnapshot.child("name").getValue().toString();
                String option = dataSnapshot.child("option").getValue().toString();
                String money = dataSnapshot.child("money").getValue().toString();
                String note = dataSnapshot.child("note").getValue().toString();

                EnterInfo info = new EnterInfo(item, date, name, option, money, note);
                list.add(info);
                Log.i("info", " " + item + " " + date + " " + name + " " + option + " " + money + " " + note);
                detailsPresenter.notifyData();//新資料更新
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

}
