package com.inscripts.ins_armman.muw.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * @author Vivek & Juilee  Created on 14/8/2019
 */

public class RequestFormModel extends UserDetails {
    @SerializedName("hash")
    private String hash;

    public void setHash(String hash) {
        this.hash = hash;
    }
}
