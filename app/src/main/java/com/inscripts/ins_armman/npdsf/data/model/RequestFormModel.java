package com.inscripts.ins_armman.npdsf.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * @author Aniket & Vivek  Created on 4/9/2018
 */

public class RequestFormModel extends UserDetails {
    @SerializedName("hash")
    private String hash;

    public void setHash(String hash) {
        this.hash = hash;
    }
}
