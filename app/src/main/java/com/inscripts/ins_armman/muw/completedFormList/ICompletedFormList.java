package com.inscripts.ins_armman.muw.completedFormList;

import android.content.Context;

import com.inscripts.ins_armman.muw.data.model.CompleteFormQnA;

import java.util.ArrayList;

public interface ICompletedFormList {
    Context getContext();
    void getData(ArrayList<CompleteFormQnA> formDetails, ArrayList<CompleteFormQnA> childNo);

}
