package com.inscripts.ins_armman.muw.data.model.download_registrationed_data;

import com.google.gson.annotations.SerializedName;
import com.inscripts.ins_armman.muw.data.model.syncing.beneficiaries;

import java.util.ArrayList;

/**
 * @author Vivek & Juilee  Created on 14/8/2019
 */

public class RestoreAllRegistration {
    @SerializedName("total")
    private int total;
    @SerializedName("data")
    private ArrayList<RegisteredData> registrationData;

    public int getTotal() {
        return total;
    }

    public ArrayList<RegisteredData> getAllRegistrationData() {
        return registrationData;
    }
}
