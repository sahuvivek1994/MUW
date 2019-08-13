package com.inscripts.ins_armman.muw.incompleteFormList;

import com.inscripts.ins_armman.muw.utility.IBasePresenter;

public interface IIncompleteFormListPresentor<V> extends IBasePresenter<V> {

    void getCompleteFormList(String unique_mother_id);

}
