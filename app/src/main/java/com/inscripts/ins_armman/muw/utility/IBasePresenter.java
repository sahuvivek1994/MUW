package com.inscripts.ins_armman.muw.utility;

/**
 * This interface is used for attachView and detch function to follow mvp structure
 *
 * @author Vivek & Juilee  Created on 14/8/2019
 */

public interface IBasePresenter<V> {

    void attachView(V view);

    void detch();

}
