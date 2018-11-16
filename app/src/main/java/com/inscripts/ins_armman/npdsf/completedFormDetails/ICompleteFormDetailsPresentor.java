package com.inscripts.ins_armman.npdsf.completedFormDetails;

import com.inscripts.ins_armman.npdsf.utility.IBasePresenter;

public interface ICompleteFormDetailsPresentor<V> extends IBasePresenter<V> {
    void displayFIlledForm(String unique_id, int form_id);

}
