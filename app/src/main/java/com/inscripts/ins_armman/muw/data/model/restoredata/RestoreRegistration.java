package com.inscripts.ins_armman.muw.data.model.restoredata;

import com.google.gson.annotations.SerializedName;
import com.inscripts.ins_armman.muw.data.model.syncing.beneficiaries;

import java.util.ArrayList;

/**
 * @author Aniket & Vivek  Created on 4/9/2018
 */

public class RestoreRegistration {
    @SerializedName("total")
    private int total;
    @SerializedName("data")
    private ArrayList<beneficiaries> registrationData;

    public int getTotal() {
        return total;
    }

    public ArrayList<beneficiaries> getRegistrationData() {
        return registrationData;
    }
}
