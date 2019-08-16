package com.inscripts.ins_armman.muw.data.model;

import com.google.gson.annotations.SerializedName;
import com.inscripts.ins_armman.muw.data.model.syncing.beneficiaries;

import java.util.ArrayList;

import static com.inscripts.ins_armman.muw.utility.Constants.BENEFICIARIES;

/**
 * @author Vivek & Juilee  Created on 14/8/2019
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
