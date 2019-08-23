package com.inscripts.ins_armman.muw.data.model.restoredata;

import com.google.gson.annotations.SerializedName;
import com.inscripts.ins_armman.muw.data.model.UserDetails;

/**
 * @author Vivek & Juilee  Created on 14/8/2019
 */

public class RestoreDataRequest extends UserDetails {
    @SerializedName("limit")
    private int limit;

    @SerializedName("pg")
    private int pageNumber;

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }
}
