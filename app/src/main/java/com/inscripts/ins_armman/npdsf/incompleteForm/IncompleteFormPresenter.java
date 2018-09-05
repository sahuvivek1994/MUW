package com.inscripts.ins_armman.npdsf.incompleteForm;

import com.inscripts.ins_armman.npdsf.data.model.IncompleteFiledForm;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Aniket & Vivek  Created on 4/9/2018
 */
public class IncompleteFormPresenter implements IncompletePresenter<IncompleteView> {

    IncompleteView incompleteView;
    IncompleteFormInteractor incompleteFormInteractor;

    @Override
    public void attachView(IncompleteView incompl) {
        this.incompleteView = incompl;
        incompleteFormInteractor = new IncompleteFormInteractor(incompleteView.getContext());
    }

    @Override
    public void detch() {
        incompleteView = null;
    }

    @Override
    public void GetListIncompleteForm() {
        List<IncompleteFiledForm> womenList = new ArrayList<>();
        womenList = incompleteFormInteractor.FetchListIncompleteForm();
        incompleteView.setAdapter(womenList);
    }
}
