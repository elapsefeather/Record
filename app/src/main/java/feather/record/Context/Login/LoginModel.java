package feather.record.Context.Login;

import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import feather.record.Data.UserInfo;
import feather.record.Other.API;

/**
 * Created by lycodes on 2017/8/9.
 */

public class LoginModel {

    LoginPresenter loginPresenter;
    //    firebase database
    DatabaseReference myRef;
    ArrayList<UserInfo> userlist = new ArrayList<>();

    public LoginModel(LoginPresenter loginPresenter) {
        this.loginPresenter = loginPresenter;
    }

    public void getUser(String account) {
        Log.i("user", "  account = " + account);

        myRef = FirebaseDatabase.getInstance().getReferenceFromUrl(API.firebase_data).child("User").child(account);
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                String account = "", name = "", password = "";
                try {
                    account = dataSnapshot.child("account").getValue().toString();
                    name = dataSnapshot.child("name").getValue().toString();
                    password = dataSnapshot.child("password").getValue().toString();

                } catch (Exception e) {
                }
                UserInfo user = new UserInfo(account, name, password);
                userlist.add(user);
                Log.i("user", "  account = " + account + "  name = " + name + "  password = " + password);

                loginPresenter.loginCheck();
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
