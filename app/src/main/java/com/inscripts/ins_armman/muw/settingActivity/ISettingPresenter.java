package com.inscripts.ins_armman.muw.settingActivity;

import android.content.Context;
import android.database.Cursor;

import com.inscripts.ins_armman.muw.utility.IBasePresenter;


/**
 * @author Vivek & Juilee  Created on 14/8/2019
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
