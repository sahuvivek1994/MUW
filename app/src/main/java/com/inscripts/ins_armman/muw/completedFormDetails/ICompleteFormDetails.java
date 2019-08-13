package com.inscripts.ins_armman.muw.completedFormDetails;

import android.content.Context;

import com.inscripts.ins_armman.muw.data.model.CompleteFormQnA;

import java.util.ArrayList;

public interface ICompleteFormDetails {
Context getContext();
void getFormdetails(ArrayList<CompleteFormQnA> formDetails);
}
