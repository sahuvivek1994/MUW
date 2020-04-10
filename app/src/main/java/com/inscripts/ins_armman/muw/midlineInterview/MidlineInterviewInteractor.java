package com.inscripts.ins_armman.muw.midlineInterview;

import android.content.Context;
import android.database.Cursor;

import com.inscripts.ins_armman.muw.database.DBHelper;

public class MidlineInterviewInteractor implements IMidlineInterviewInteractor {
    private Context mContext;
    private DBHelper dbHelper;

    public MidlineInterviewInteractor(Context mContext) {
        this.mContext = mContext;
        dbHelper = new DBHelper(mContext);
    }

    @Override
    public Cursor fetchAllParticipants() {
        return  dbHelper.fetchAllParticipants();
        //return dbHelper.getcompleteFormListList();
    }
}
