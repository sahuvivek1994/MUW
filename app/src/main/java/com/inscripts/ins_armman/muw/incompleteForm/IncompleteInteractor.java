package com.inscripts.ins_armman.muw.incompleteForm;

import android.database.Cursor;

/**
 * @author Vivek & Juilee  Created on 14/8/2019
 */

public interface IncompleteInteractor {

    Cursor fetchListIncompleteForm();

    Cursor fetchUniqueIdChildId(String unique);

    Cursor checkChildFilledForm(String unique);
}
