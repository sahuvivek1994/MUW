package com.inscripts.ins_armman.muw.completedFormList;

import android.database.Cursor;

public interface ICompletedFormsListInteractor {
    Cursor getCompleteFormList();
    Cursor getChildNo(String unique_mother_id);
}
