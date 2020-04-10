package com.inscripts.ins_armman.muw.midlineInterview;

import android.content.Context;


import com.inscripts.ins_armman.muw.data.model.completeFiledForm;

import java.util.List;

public interface IMidlineInterviewView {
    void setAdapter(List<completeFiledForm> mWomenList);

    Context getContext();
}
