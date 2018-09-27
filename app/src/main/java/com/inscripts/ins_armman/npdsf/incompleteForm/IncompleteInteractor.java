package com.inscripts.ins_armman.npdsf.incompleteForm;

import android.database.Cursor;

/**
 * @author Aniket & Vivek  Created on 4/9/2018
 */

public interface IncompleteInteractor {

    Cursor fetchListIncompleteForm();

    Cursor fetchUniqueIdChildId(String unique);

    Cursor checkChildFilledForm(String unique);
}
