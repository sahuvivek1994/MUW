package com.inscripts.ins_armman.muw.incompleteForm;

import com.inscripts.ins_armman.muw.utility.IBasePresenter;

/**
 * @author Vivek & Juilee  Created on 14/8/2019
 */

public interface IncompletePresenter<v> extends IBasePresenter<v> {


    public void getListInCompleteForm();

    public void getUniqueIdFormId(String uniqueId);

}
