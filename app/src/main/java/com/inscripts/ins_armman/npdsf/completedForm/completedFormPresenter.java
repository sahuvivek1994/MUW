package com.inscripts.ins_armman.npdsf.completedForm;

import android.database.Cursor;

import com.inscripts.ins_armman.npdsf.data.model.completeFiledForm;

import java.util.ArrayList;
import java.util.List;

public class completedFormPresenter implements IcompletedPresenter<completedForm> {

    IcompletedFormView icompletedFormView;
    completedFormInteractor completedFormInteractor;

    @Override
    public void attachView(completedForm completedForm) {
        this.icompletedFormView = completedForm;
        completedFormInteractor = new completedFormInteractor(completedForm.getContext());
    }

    @Override
    public void detch() {
        icompletedFormView = null;
    }

    @Override
    public void getListCompleteForm() {
        List<completeFiledForm> womenList = new ArrayList<>();
        Cursor cursor = completedFormInteractor.fetchListcompleteForm();
        if (cursor != null && cursor.moveToFirst())
            do {
                womenList.add(new completeFiledForm(cursor.getString(cursor.getColumnIndex("name")),cursor.getString(cursor.getColumnIndex("unique_id"))));
            } while (cursor.moveToNext());

        icompletedFormView.setAdapter(womenList);
    }


}
