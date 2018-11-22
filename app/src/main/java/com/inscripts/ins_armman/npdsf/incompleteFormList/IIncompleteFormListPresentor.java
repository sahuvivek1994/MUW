package com.inscripts.ins_armman.npdsf.incompleteFormList;

import com.inscripts.ins_armman.npdsf.utility.IBasePresenter;

public interface IIncompleteFormListPresentor<V> extends IBasePresenter<V> {

    void getCompleteFormList(String unique_mother_id);

}
