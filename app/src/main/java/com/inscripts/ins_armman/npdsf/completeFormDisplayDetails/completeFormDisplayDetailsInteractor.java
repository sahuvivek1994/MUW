package com.inscripts.ins_armman.npdsf.completeFormDisplayDetails;

import android.content.Context;
import android.database.Cursor;

import com.inscripts.ins_armman.npdsf.database.DBHelper;

public class completeFormDisplayDetailsInteractor implements IcompleteFormDisplayDetailsInteractor {


    private Context mContext;
    private DBHelper dbHelper;

    completeFormDisplayDetailsInteractor(Context context)
    {
        this.mContext = context;
        dbHelper = new DBHelper(context);
    }

    @Override
    public Cursor fetchListcompleteFormDiplayDetails() {
        return dbHelper.getcompleteFormListList();
    }
}
