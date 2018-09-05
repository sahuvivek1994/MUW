package com.inscripts.ins_armman.npdsf.settingActivity;

import android.content.Context;
import android.database.Cursor;

import com.inscripts.ins_armman.npdsf.utility.IBasePresenter;


/**
 * @author Aniket & Vivek  Created on 21/8/2018
 */

public interface ISettingPresenter<V> extends IBasePresenter<V> {
    void changeLanguage(Context context, String language);

    void downloadForms();

    void logout();

    void checkUpdate();

    void setApkDownloadProgress(int progress);

    void downloadApk(String apkLink);

    void onApkDownloaded();

    void restoreData();

    void restoreVisits(int pageNumber);

    void resetDataMemberValues();

    void restoreRegistrations(int pageNumber);

    interface OnQueryFinished {

        void onSuccess(Cursor cursor, int id);

        void onSuccess();

        void onFailure();
    }
}
