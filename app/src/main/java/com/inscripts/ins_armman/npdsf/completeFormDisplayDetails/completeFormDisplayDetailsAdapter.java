package com.inscripts.ins_armman.npdsf.completeFormDisplayDetails;

import android.content.Context;
import com.inscripts.ins_armman.npdsf.data.model.completeFiledForm;

import java.util.List;


public class completeFormDisplayDetailsAdapter{

    private Context mContext;
    private List<completeFiledForm> mWomenList;
    private com.inscripts.ins_armman.npdsf.completedForm.completedFormAdapter.OnItemClickListener mOnItemClickListener;

    public completeFormDisplayDetailsAdapter(Context mContext, List<completeFiledForm> womenList, com.inscripts.ins_armman.npdsf.completedForm.completedFormAdapter.OnItemClickListener mOnItemClickListener) {
        this.mContext = mContext;
        this.mWomenList = womenList;
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public void swapDataList(List<completeFiledForm> womenList) {
        this.mWomenList = womenList;
    }

}
