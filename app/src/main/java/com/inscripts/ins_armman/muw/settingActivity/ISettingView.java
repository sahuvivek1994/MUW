package com.inscripts.ins_armman.muw.settingActivity;

import com.inscripts.ins_armman.muw.utility.IMvpView;


/**
 * @author Aniket & Vivek  Created on 21/8/2018
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
