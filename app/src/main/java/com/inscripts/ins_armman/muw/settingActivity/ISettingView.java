package com.inscripts.ins_armman.muw.settingActivity;

import com.inscripts.ins_armman.muw.utility.IMvpView;


/**
 * @author Vivek & Juilee  Created on 14/8/2019
 */

public interface ISettingView extends IMvpView {

    void showProgressBar(String label);

    void hideProgressBar();

    void showDialog(String title, String message);

    void updateAvailable(final String url);

    void showApkDownloadProgress();

    void updateApkDownloadProgress(int progress);

    void dismissApkDownloadProgress();

    void showSnackBar(String message);

}
