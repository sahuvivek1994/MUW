package com.inscripts.ins_armman.npdsf.completedFormList;

import android.database.Cursor;

public interface ICompletedFormsListInteractor {
    Cursor getCompleteFormList();
    Cursor getChildNo(String unique_mother_id);
}
