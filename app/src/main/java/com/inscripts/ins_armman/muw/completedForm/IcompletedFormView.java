package com.inscripts.ins_armman.muw.completedForm;

import android.content.Context;

import com.inscripts.ins_armman.muw.data.model.completeFiledForm;

import java.util.List;

public interface IcompletedFormView {

    void setAdapter(List<completeFiledForm> mWomenList);

    Context getContext();
}
