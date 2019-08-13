package com.inscripts.ins_armman.muw.completedFormDetails;

import com.inscripts.ins_armman.muw.utility.IBasePresenter;

public interface ICompleteFormDetailsPresentor<V> extends IBasePresenter<V> {
    void displayFIlledForm(String unique_id, int form_id);

}
