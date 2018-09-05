package com.inscripts.ins_armman.npdsf.data.model.syncing;

import com.google.gson.annotations.SerializedName;
import com.inscripts.ins_armman.npdsf.data.model.UserDetails;

import java.util.ArrayList;

import static com.inscripts.ins_armman.npdsf.utility.Constants.DATA;
import static com.inscripts.ins_armman.npdsf.utility.Constants.FORM_ID;
import static com.inscripts.ins_armman.npdsf.utility.Constants.UNIQUE_ID;

/**
 * @author Aniket & Vivek  Created on 4/9/2018
 */

public class FormDetails extends UserDetails {
    @SerializedName(UNIQUE_ID)
    private String uniqueId;
    @SerializedName(FORM_ID)
    private String formId;
    @SerializedName(DATA)
    private ArrayList<QuestionAnswer> data;

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public void setData(ArrayList<QuestionAnswer> data) {
        this.data = data;
    }

}
