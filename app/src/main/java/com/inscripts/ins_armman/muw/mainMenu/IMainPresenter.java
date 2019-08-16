package com.inscripts.ins_armman.muw.mainMenu;

import android.database.Cursor;

import com.inscripts.ins_armman.muw.utility.IBasePresenter;

/**
 * @author Vivek & Juilee  Created on 14/8/2019
 */

public interface IMainPresenter<V> extends IBasePresenter<V> {

    void fetchUnsentFormsCount();

    void fetchRegistrationData();

    void onFetchedRegistrationData(Cursor cursor);

    void syncUnsentForms();

    interface OnQueryFinished {

        void onSuccess(Cursor cursor, int id);

        void onSuccess();

        void onFailure();
    }

    interface OnResetTaskCompleted {

        void onResetCompleted();
    }
}
