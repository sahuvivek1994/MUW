package com.inscripts.ins_armman.muw.userProfile;

import android.database.Cursor;

import com.inscripts.ins_armman.muw.data.model.syncing.beneficiaries;

public class userProfilePresenter implements IuserProfilePresenter<IuserProfileView> {

    IuserProfileView iuserProfileView;
    IuserProfileInteractor iuserProfileInteractor;

    @Override
    public void attachView(IuserProfileView iuserProfileView1) {
        this.iuserProfileView = iuserProfileView1;
        iuserProfileInteractor = new userProfileInteractor(iuserProfileView1.getContext());
    }

    @Override
    public void detch() {
        iuserProfileView = null;
    }

    @Override
    public beneficiaries fetchUserDetails() {
        beneficiaries details = new beneficiaries();
        Cursor cursor = iuserProfileInteractor.fetchUserDetails();
        if (cursor.moveToFirst() && cursor != null) {
            details.setMobNo(cursor.getString(cursor.getColumnIndex("phone_no")));
            details.setName(cursor.getString(cursor.getColumnIndex("name")));
            cursor.close();
        } else {
            details.setName("ERROR");
            details.setMobNo("error");
        }
        return details;
    }
}
