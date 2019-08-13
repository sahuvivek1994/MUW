package com.inscripts.ins_armman.muw.utility;

/**
 * This interface is used for attachView and detch function to follow mvp structure
 *
 * @author Aniket & Vivek  Created on 15/8/2018
 */

public interface IBasePresenter<V> {

    void attachView(V view);

    void detch();

}
