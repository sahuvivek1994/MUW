package com.inscripts.ins_armman.npdsf.completeFormDisplayDetails;


import com.inscripts.ins_armman.npdsf.utility.IBasePresenter;

public interface IcompleteFormDisplayDetailsPresenter<v> extends IBasePresenter<v> {

    @Override
    void attachView(v view);

    @Override
    void detch();

}
