package com.inscripts.ins_armman.muw.mainMenu;

import com.inscripts.ins_armman.muw.utility.IMvpView;

/**
 * @author Vivek & Juilee  Created on 14/8/2019
 */

public interface IMainView extends IMvpView {

    void setUnsentFormsCount(int count);

    void showProgressBar(String label);

    void hideProgressBar();

    void showSnackBar(String message);
}
