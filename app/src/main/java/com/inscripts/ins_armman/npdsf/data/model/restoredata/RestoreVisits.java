package com.inscripts.ins_armman.npdsf.data.model.restoredata;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * @author Aniket & Vivek  Created on 4/9/2018
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
