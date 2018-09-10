package com.inscripts.ins_armman.npdsf.incompleteForm;

import android.database.Cursor;

import com.inscripts.ins_armman.npdsf.data.model.IncompleteFiledForm;
import com.inscripts.ins_armman.npdsf.database.DatabaseContract.FilledFormStatusTable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Aniket & Vivek  Created on 4/9/2018
 */
public class IncompleteFormPresenter implements IncompletePresenter<IncompleteView> {

    IncompleteView incompleteView;
    IncompleteFormInteractor incompleteFormInteractor;

    @Override
    public void attachView(IncompleteView incompl) {
        this.incompleteView = incompl;
        incompleteFormInteractor = new IncompleteFormInteractor(incompleteView.getContext());
    }

    @Override
    public void detch() {
        incompleteView = null;
    }

    @Override
    public void getListInCompleteForm() {
        List<IncompleteFiledForm> womenList = new ArrayList<>();
        Cursor cursor = incompleteFormInteractor.fetchListIncompleteForm();
        if (cursor != null && cursor.moveToFirst())
            do {
                womenList.add(new IncompleteFiledForm(cursor.getString(cursor.getColumnIndex("unique_id")),
                        cursor.getString(cursor.getColumnIndex("name")),
                        cursor.getString(cursor.getColumnIndex("form_id")),
                        cursor.getInt(cursor.getColumnIndex(FilledFormStatusTable.COLUMN_FORM_COMPLETION_STATUS))));
            }while (cursor.moveToNext()) ;

        incompleteView.setAdapter(womenList);
    }
}
