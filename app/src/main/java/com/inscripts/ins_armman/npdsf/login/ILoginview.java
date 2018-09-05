package com.inscripts.ins_armman.npdsf.login;


import com.inscripts.ins_armman.npdsf.utility.IMvpView;

/**
 * @author Aniket & Vivek  Created on 15/8/2018
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
