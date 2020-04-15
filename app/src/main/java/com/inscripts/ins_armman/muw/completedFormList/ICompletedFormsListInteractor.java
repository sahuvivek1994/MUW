package com.inscripts.ins_armman.muw.completedFormList;

import android.database.Cursor;

public interface ICompletedFormsListInteractor {
    Cursor getCompleteFormList(String motherId);
    Cursor getChildNo(String unique_mother_id);
}
