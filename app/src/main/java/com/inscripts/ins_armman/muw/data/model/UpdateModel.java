package com.inscripts.ins_armman.muw.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * @author Aniket & Vivek  Created on 4/9/2018
 */

public class UpdateModel {
    @SerializedName("status")
    private boolean status;
    @SerializedName("data")
    private Data data;

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data {
        @SerializedName("id")
        private String id;
        @SerializedName("name")
        private String name;
        @SerializedName("versionName")
        private String versionName;
        @SerializedName("versionCode")
        private String versionCode;
        @SerializedName("description")
        private String description;
        @SerializedName("link")
        private String link;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getVersionName() {
            return versionName;
        }

        public void setVersionName(String versionName) {
            this.versionName = versionName;
        }

        public String getVersionCode() {
            return versionCode;
        }

        public void setVersionCode(String versionCode) {
            this.versionCode = versionCode;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }
    }
}
