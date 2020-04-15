package com.inscripts.ins_armman.muw.completedFormList;

import android.content.Context;
import android.database.Cursor;

import com.inscripts.ins_armman.muw.database.DBHelper;

public class CompletedFormsListInteractor implements ICompletedFormsListInteractor {
    Context mContext;
    DBHelper db;

    public CompletedFormsListInteractor(Context mContext) {
        this.mContext = mContext;
        this.db = new DBHelper(mContext);
    }

    @Override
    public Cursor getCompleteFormList(String motherId) {
        return db.getFormsList(motherId);
    }

    @Override
    public Cursor getChildNo(String unique_mother_id) {
        return db.getChildIdFromMotherId(unique_mother_id);
    }
}
