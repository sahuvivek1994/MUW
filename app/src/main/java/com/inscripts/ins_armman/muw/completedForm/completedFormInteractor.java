package com.inscripts.ins_armman.muw.completedForm;

import android.content.Context;
import android.database.Cursor;

import com.inscripts.ins_armman.muw.database.DBHelper;

public class completedFormInteractor implements IcompletedInteractor {

    private Context mContext;
    private DBHelper dbHelper;

    completedFormInteractor(Context context) {
        this.mContext = context;
        dbHelper = new DBHelper(context);
    }


    @Override
    public Cursor fetchListcompleteForm() {
        return dbHelper.getcompleteFormListList();
    }
}
