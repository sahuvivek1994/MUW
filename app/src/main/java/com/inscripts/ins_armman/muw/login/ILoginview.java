package com.inscripts.ins_armman.muw.login;


import com.inscripts.ins_armman.muw.utility.IMvpView;

/**
 * @author Vivek & Juilee  Created on 14/8/2019
 */

public interface ILoginview extends IMvpView {

    void setUsernameError();

    void setPasswordError();

    void resetErrorMsg();

    void showDialog(String title, String message);

    void showProgressBar();

    void hideProgressBar();

    void openHomeActivity();

    void setAuthenticationFailedError();
}
