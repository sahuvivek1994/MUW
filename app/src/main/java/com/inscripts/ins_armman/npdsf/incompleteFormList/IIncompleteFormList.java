package com.inscripts.ins_armman.npdsf.incompleteFormList;

import android.content.Context;

import com.inscripts.ins_armman.npdsf.data.model.CompleteFormQnA;

import java.util.ArrayList;

public interface IIncompleteFormList {
    Context getContext();
    void getData(ArrayList<CompleteFormQnA> formDetails, ArrayList<CompleteFormQnA> childNo);

}
