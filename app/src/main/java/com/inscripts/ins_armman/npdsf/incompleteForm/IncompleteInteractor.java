package com.inscripts.ins_armman.npdsf.incompleteForm;

import android.database.Cursor;

import com.inscripts.ins_armman.npdsf.data.model.IncompleteFiledForm;

import java.util.List;

/**
 * @author Aniket & Vivek  Created on 4/9/2018
 */

public interface IncompleteInteractor {

    Cursor fetchListIncompleteForm();

}
