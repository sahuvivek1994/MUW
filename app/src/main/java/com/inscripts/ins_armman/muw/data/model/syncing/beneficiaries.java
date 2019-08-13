package com.inscripts.ins_armman.muw.data.model.syncing;

import com.google.gson.annotations.SerializedName;
import com.inscripts.ins_armman.muw.database.DatabaseContract.RegistrationTable;

/**
 * @author Aniket & Vivek  Created on 4/9/2018
 */

public class beneficiaries {

    @SerializedName(RegistrationTable.COLUMN_UNIQUE_ID)
    private String uniqueId;
    @SerializedName(RegistrationTable.COLUMN_FIRST_NAME)
    private String Name;
    @SerializedName(RegistrationTable.COLUMN_ADDRESS)
    private String address;
    @SerializedName(RegistrationTable.COLUMN_MOBILE_NO)
    private String mobNo;
    @SerializedName(RegistrationTable.COLUMN_EDUCATION)
    private String education;
    @SerializedName(RegistrationTable.COLUMN_DOB)
    private String age;
    @SerializedName(RegistrationTable.COLUMN_CREATED_ON)
    private String createdOn;
    @SerializedName(RegistrationTable.COLUMN_MOTHER_ID)
    private String motherId;
    @SerializedName(RegistrationTable.COLUMN_GENDER)
    private String gender;


    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String firstName) {
        this.Name = firstName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobNo() {
        return mobNo;
    }

    public void setMobNo(String mobNo) {
        this.mobNo = mobNo;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String dob) {
        this.age = dob;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getMotherId() {
        return motherId;
    }

    public void setMotherId(String motherId) {
        this.motherId = motherId;
    }

    @Override
    public String toString() {
        return "beneficiaries{" +
                "uniqueId='" + uniqueId + '\'' +
                ", Name='" + Name + '\'' +
                ", address='" + address + '\'' +
                ", mobNo='" + mobNo + '\'' +
                ", education='" + education + '\'' +
                ", age='" + age + '\'' +
                ", createdOn='" + createdOn + '\'' +
                '}';
    }
}
