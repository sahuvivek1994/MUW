package com.inscripts.ins_armman.npdsf.incompleteFormList;

import android.database.Cursor;

public interface IIncompleteFormListInteractor {
    Cursor getCompleteFormList(String unique_id);
    Cursor getChildNo(String unique_mother_id);
}