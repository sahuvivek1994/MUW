package com.inscripts.ins_armman.muw.completedFormDetails;

import android.database.Cursor;

public interface ICompleteFormDetailsInteractor {
Cursor displayFormDetails(String id, int form_id);
Cursor displayForm6Details(String id, int form_id);


}
