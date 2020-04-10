package com.inscripts.ins_armman.muw.data.model.download_registrationed_data;

import com.google.gson.annotations.SerializedName;

public class RegisteredData {

    @SerializedName("unique_id")
    String unique_id;
    @SerializedName("user_id")
    String user_id;
    @SerializedName("name")
    String name;


    public String getUnique_id() {
        return unique_id;
    }

    public void setUnique_id(String unique_id) {
        this.unique_id = unique_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "AllRegisteredData{" +
                "uniqueId='" + unique_id + '\'' +
                ", name ='" + name + '\'' +
                ", userId=" + user_id +
                '}';
    }
}
