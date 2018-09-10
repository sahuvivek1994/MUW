package com.inscripts.ins_armman.npdsf.incompleteForm;

import android.content.Context;
import android.database.Cursor;

import com.inscripts.ins_armman.npdsf.data.model.IncompleteFiledForm;
import com.inscripts.ins_armman.npdsf.database.DBHelper;

import java.util.List;

/**
 * @author Aniket & Vivek  Created on 4/9/2018
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
}
