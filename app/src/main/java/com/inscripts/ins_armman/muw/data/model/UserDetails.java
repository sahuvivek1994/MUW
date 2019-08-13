package com.inscripts.ins_armman.muw.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * This is model class of UserDetails used to create json and pass parameter in retrofit api request
 *
 * @author Aniket & Vivek  Created on 4/9/2018
 */

public class UserDetails {
    @SerializedName("username")
    private String userName;
    @SerializedName("password")
    private String password;
    @SerializedName("imei")
    private ArrayList<String> imei;
    @SerializedName("showdata")
    private String showdata;

    public void setShowdata(String showdata) {
        this.showdata = showdata;
    }

    public void setImei(ArrayList<String> imei) {
        this.imei = imei;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserDetails{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", imei=" + imei +
                ", showdata='" + showdata + '\'' +
                '}';
    }

}
