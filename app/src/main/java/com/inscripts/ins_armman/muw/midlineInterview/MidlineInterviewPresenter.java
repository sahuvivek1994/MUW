package com.inscripts.ins_armman.muw.midlineInterview;

import android.database.Cursor;

import com.inscripts.ins_armman.muw.data.model.completeFiledForm;

import java.util.ArrayList;
import java.util.List;

public class MidlineInterviewPresenter implements IMidlineInterviewPresenter<MidlineInterviewActivity>  {

    IMidlineInterviewView view;
    MidlineInterviewInteractor interactor;

    @Override
    public void attachView(MidlineInterviewActivity view) {
        this.view = view;
        interactor = new MidlineInterviewInteractor(view.getContext());
    }

    @Override
    public void detch() {
        view= null;
    }

    @Override
    public void getAllParticipants() {
        List<completeFiledForm> womenList = new ArrayList<>();
        Cursor cursor = interactor.fetchAllParticipants();
        if (cursor != null && cursor.moveToFirst())
            do {
                womenList.add(new completeFiledForm(cursor.getString(cursor.getColumnIndex("name")),cursor.getString(cursor.getColumnIndex("unique_id"))));
            } while (cursor.moveToNext());

        view.setAdapter(womenList);
    }
}
