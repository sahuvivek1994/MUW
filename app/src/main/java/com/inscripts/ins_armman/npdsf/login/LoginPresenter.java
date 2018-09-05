package com.inscripts.ins_armman.npdsf.login;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.inscripts.ins_armman.npdsf.R;
import com.inscripts.ins_armman.npdsf.data.model.UserDetails;
import com.inscripts.ins_armman.npdsf.database.DBHelper;
import com.inscripts.ins_armman.npdsf.database.DatabaseManager;
import com.inscripts.ins_armman.npdsf.utility.utility;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.inscripts.ins_armman.npdsf.utility.Constants.AUTHENTICATION_FAILED;

/**
 * @author Aniket & Vivek  Created on 15/8/2018
 */

public class LoginPresenter implements ILoginPresenter<ILoginview>, LoginInteractor.OnLoginFinished {

    ILoginview iLoginview;
    ILoginInteractor iLoginInteractor;
    private UserDetails mUserDetails;
    private Boolean checkLoginStatus;
    private String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.INTERNET, Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.CAMERA};

    @Override
    public void attachView(ILoginview view) {
        this.iLoginview = view;
        iLoginInteractor = new LoginInteractor(iLoginview.getContext());

        if (checkPermissions()) {
            initializeDBHelper();
        }
    }

    @Override
    public void detch() {
        iLoginview = null;
    }

    @Override
    public void initializeDBHelper() {
        DBHelper dbHelper = new DBHelper(iLoginview.getContext().getApplicationContext());
        DatabaseManager.initializeInstance(dbHelper);
        DatabaseManager.getInstance().openDatabase();
    }

    @Override
    public boolean checkPermissions() {
        List<String> listPermissionsNeeded = new ArrayList<>();

        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(iLoginview.getContext(), permission) != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(permission);
            }
        }

        if (!listPermissionsNeeded.isEmpty()) {
            getPermissions(listPermissionsNeeded);
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void getPermissions(List<String> listPermissionsNeeded) {
        ActivityCompat.requestPermissions((Activity) iLoginview.getContext(), listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 1);
    }

    @Override
    public void validateCredentials(String username, String password) {
        iLoginview.resetErrorMsg();
        if (username.isEmpty()) {
            iLoginview.setUsernameError();
        } else if (password.isEmpty()) {
            iLoginview.setPasswordError();
        } else {
            loginUser(username, password);
        }
    }

    @Override
    public void loginUser(String username, String password) {
        if (utility.hasInternetConnectivity(iLoginview.getContext())) {
            iLoginview.showProgressBar();
            createRequestBody(username, password);
            iLoginInteractor.login(mUserDetails, this, iLoginview.getContext());
        } else {
            String title = iLoginview.getContext().getString(R.string.no_internet);
            String message = iLoginview.getContext().getString(R.string.no_internet_connection);
            iLoginview.showDialog(title, message);
        }
    }

    @Override
    public void createRequestBody(String username, String password) {
        mUserDetails = new UserDetails();
        mUserDetails.setUserName(username);
        mUserDetails.setPassword(password);
        mUserDetails.setImei(utility.getDeviceImeiNumber(iLoginview.getContext()));
        mUserDetails.setShowdata("true");
    }

    @Override
    public void onSuccess(JSONObject jsonObject) throws JSONException {

        if (jsonObject.has("response")) {
            switch (jsonObject.optString("response")) {
                case "AUTHENTICATION_SUCCESS":
                    iLoginInteractor.saveUserDetails(mUserDetails.getUserName(), mUserDetails.getPassword(), jsonObject);
                    iLoginview.hideProgressBar();
                    iLoginview.openHomeActivity();
                    break;
                case AUTHENTICATION_FAILED:
                    iLoginview.hideProgressBar();
                    iLoginview.showDialog(iLoginview.getContext().getString(R.string.error), iLoginview.getContext().getString(R.string.authentication_failed));
                    iLoginview.setAuthenticationFailedError();
                    break;
                default:
                    iLoginview.hideProgressBar();
                    iLoginview.showDialog(iLoginview.getContext().getString(R.string.error), iLoginview.getContext().getString(R.string.response_not_found));
                    break;
            }
        } else {
            iLoginview.hideProgressBar();
            iLoginview.showDialog(iLoginview.getContext().getString(R.string.error), iLoginview.getContext().getString(R.string.response_not_found));
        }
    }

    @Override
    public void onFailure(String message) {
        iLoginview.hideProgressBar();
        iLoginview.showDialog(iLoginview.getContext().getString(R.string.error), message);
    }

    @Override
    public void checkIfUserAlreadyLoggedIn() {
        try {
            if (iLoginInteractor.userAlreadyLoggedIn()) {
                iLoginview.openHomeActivity();
            }
        } catch (IllegalStateException e) {
            checkPermissions();
            initializeDBHelper();
            checkIfUserAlreadyLoggedIn();
        }
    }
}
