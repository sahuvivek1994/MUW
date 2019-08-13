package com.inscripts.ins_armman.muw.userProfile;

import android.content.Context;
import android.database.Cursor;

import com.inscripts.ins_armman.muw.database.DBHelper;

public class userProfileInteractor implements IuserProfileInteractor {

    private DBHelper dbHelper;
    private Context mcontext;

    public userProfileInteractor(Context context) {
        this.mcontext = context;
        dbHelper = new DBHelper(context);
    }

    @Override
    public Cursor fetchUserDetails() {
        return dbHelper.fetchUserDetails();
    }
}
