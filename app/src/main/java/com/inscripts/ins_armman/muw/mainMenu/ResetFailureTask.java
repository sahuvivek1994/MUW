package com.inscripts.ins_armman.muw.mainMenu;

import android.os.AsyncTask;

/**
 * @author Vivek & Juilee  Created on 14/8/2019
 */

public class ResetFailureTask extends AsyncTask<Void, Void, Void> {

    private MainPresenter.OnResetTaskCompleted mOnResetTaskCompleted;
    private MainInteractor mHomeActivityInteractor;

    public ResetFailureTask(MainPresenter.OnResetTaskCompleted onResetTaskCompleted, MainInteractor mainInteractor) {
        mOnResetTaskCompleted = onResetTaskCompleted;
        mHomeActivityInteractor = mainInteractor;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        mHomeActivityInteractor.resetFailureStatus();
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        mOnResetTaskCompleted.onResetCompleted();
    }
}
