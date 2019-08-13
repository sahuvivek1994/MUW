package com.inscripts.ins_armman.muw.completedFormList;

import com.inscripts.ins_armman.muw.utility.IBasePresenter;

public interface ICompletedFormsListPresentor<V> extends IBasePresenter<V> {

    void getCompleteFormList(String unique_mother_id);
}
