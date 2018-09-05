package com.inscripts.ins_armman.npdsf.userProfile;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.inscripts.ins_armman.npdsf.R;
import com.inscripts.ins_armman.npdsf.data.model.syncing.beneficiaries;

/**
 * @author Aniket & Vivek  Created on 21/8/2018
 */

public class userProfile extends AppCompatActivity implements IuserProfileView {

    TextView tname, tnumber;
    userProfilePresenter userProfilePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        setTitle("User Profile");
        userProfilePresenter = new userProfilePresenter();
        userProfilePresenter.attachView(this);
        init();
        setUserDetails();
    }

    public void init() {
        tname = findViewById(R.id.textview_userName);
        tnumber = findViewById(R.id.textView_contactNumber);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        userProfilePresenter.detch();
    }

    @Override
    public void setUserDetails() {
        beneficiaries details = userProfilePresenter.fetchUserDetails();
        tname.setText(details.getName());
        tnumber.setText("Contact no : " + details.getMobNo());
    }

}
