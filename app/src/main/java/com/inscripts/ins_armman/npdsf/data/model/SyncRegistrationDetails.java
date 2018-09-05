package com.inscripts.ins_armman.npdsf.data.model;

import com.google.gson.annotations.SerializedName;
import com.inscripts.ins_armman.npdsf.data.model.syncing.beneficiaries;

import java.util.ArrayList;

import static com.inscripts.ins_armman.npdsf.utility.Constants.BENEFICIARIES;

/**
 * @author Aniket & Vivek  Created on 4/9/2018
 */

public class SyncRegistrationDetails extends UserDetails {
    @SerializedName(BENEFICIARIES)
    private ArrayList<beneficiaries> regData;

    public void setRegData(ArrayList<beneficiaries> regData) {
        this.regData = regData;
    }

    @Override
    public String toString() {
        return "SyncRegistrationDetails{" +
                "regData=" + regData +
                '}';
    }
}
