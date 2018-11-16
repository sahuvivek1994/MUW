package com.inscripts.ins_armman.npdsf.completedFormList;

import com.inscripts.ins_armman.npdsf.utility.IBasePresenter;

public interface ICompletedFormsListPresentor<V> extends IBasePresenter<V> {

    void getCompleteFormList(String unique_mother_id);
}
