package com.inscripts.ins_armman.muw.incompleteForm;

import android.content.Context;

import com.inscripts.ins_armman.muw.data.model.IncompleteFiledForm;

import java.util.List;

/**
 * @author Vivek & Juilee  Created on 14/8/2019
 */
public interface IncompleteView {

    void setAdapter(List<IncompleteFiledForm> mWomenList);

    Context getContext();

    void openActivity(String uniqueId, int form_id, String noOfChild, String childCounter);
}
