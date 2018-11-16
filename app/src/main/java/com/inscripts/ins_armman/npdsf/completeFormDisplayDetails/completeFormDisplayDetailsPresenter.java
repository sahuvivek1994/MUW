package com.inscripts.ins_armman.npdsf.completeFormDisplayDetails;

public class completeFormDisplayDetailsPresenter implements IcompleteFormDisplayDetailsPresenter<completeFormDisplayDetails> {

    IcompleteFormDisplayDetailsView icompleteFormDisplayDetailsView;
    completeFormDisplayDetailsInteractor completeFormDisplayDetailsInteractor;

    @Override
    public void attachView(completeFormDisplayDetails view) {
        this.icompleteFormDisplayDetailsView = view;
        completeFormDisplayDetailsInteractor = new completeFormDisplayDetailsInteractor(icompleteFormDisplayDetailsView.getcontext());
    }

    @Override
    public void detch() {
        icompleteFormDisplayDetailsView = null;
    }
}
