package com.inscripts.ins_armman.npdsf.mainMenu;

import android.os.AsyncTask;

/**
 * @author Aniket & Vivek  Created on 15/8/2018
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
