package com.inscripts.ins_armman.muw.incompleteForm;

import android.content.Context;
import android.database.Cursor;

import com.inscripts.ins_armman.muw.database.DBHelper;

/**
 * @author Vivek & Juilee  Created on 14/8/2019
 */

public class IncompleteFormInteractor implements IncompleteInteractor {

    private Context mContext;
    private DBHelper dbHelper;

    IncompleteFormInteractor(Context context) {
        this.mContext = context;
        dbHelper = new DBHelper(context);
    }

    @Override
    public Cursor fetchListIncompleteForm() {
        return dbHelper.getIncompleteFormListList();
    }

    @Override
    public Cursor fetchUniqueIdChildId(String unique) {
        return dbHelper.getChildIdFromMotherId(unique);
    }

    @Override
    public Cursor checkChildFilledForm(String unique) {
        return dbHelper.getuniqueIdFormId(unique);
    }
}
