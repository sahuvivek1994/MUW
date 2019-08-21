package com.inscripts.ins_armman.muw.data.model.restoredata;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * @author Vivek & Juilee  Created on 14/8/2019
 */

public class RestoreVisits {
    @SerializedName("total")
    private int total;
    @SerializedName("data")
    private ArrayList<BeneficiariesList> beneficiariesLists;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public ArrayList<BeneficiariesList> getBeneficiariesLists() {
        return beneficiariesLists;
    }

    public void setBeneficiariesLists(ArrayList<BeneficiariesList> beneficiariesLists) {
        this.beneficiariesLists = beneficiariesLists;
    }

    @Override
    public String toString() {
        return "RestoreVisits{" +
                "total=" + total +
                ", beneficiariesLists=" + beneficiariesLists +
                '}';
    }
}
