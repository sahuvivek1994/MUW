package com.inscripts.ins_armman.muw.login;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.inscripts.ins_armman.muw.data.model.UserDetails;
import com.inscripts.ins_armman.muw.data.retrofit.RemoteDataSource;
import com.inscripts.ins_armman.muw.data.service.AuthService;
import com.inscripts.ins_armman.muw.database.DBHelper;
import com.inscripts.ins_armman.muw.database.DatabaseContract;
import com.inscripts.ins_armman.muw.utility.utility;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Aniket & Vivek  Created on 15/8/2018
 */

public class LoginInteractor implements ILoginInteractor {

    private DBHelper dbHelper;
    private Context mcontext;

    LoginInteractor(Context context) {
        this.mcontext = context;
    }

    @Override
    public void login(UserDetails userDetails, OnLoginFinished onLoginFinished, Context context) {
        RemoteDataSource remoteDataSource = RemoteDataSource.getInstance();
        AuthService authService = remoteDataSource.getAuthService();
        authService.getAuthentication(userDetails, onLoginFinished, context);
    }

    @Override
    public void saveUserDetails(String username, String password, JSONObject jsonObject) throws JSONException {
        if (jsonObject.has("data")) {

            deleteUserDetails();

            JSONObject jsonObjectUser = jsonObject.getJSONObject("data");

            ContentValues values = new ContentValues();

            values.put(DatabaseContract.LoginTable.COLUMN_USER_ID, jsonObjectUser.optString("id"));
            values.put(DatabaseContract.LoginTable.COLUMN_NAME, jsonObjectUser.optString("name"));
            values.put(DatabaseContract.LoginTable.COLUMN_USERNAME, username);
            values.put(DatabaseContract.LoginTable.COLUMN_PASSWORD, password);
            values.put(DatabaseContract.LoginTable.COLUMN_PHONE_NO, jsonObjectUser.optString("phone_no"));

            utility.getDatabase().insert(DatabaseContract.LoginTable.TABLE_NAME, null, values);
        }
    }

    @Override
    public void deleteUserDetails() {
        utility.getDatabase().delete(DatabaseContract.LoginTable.TABLE_NAME, null, null);
    }

    @Override
    public boolean userAlreadyLoggedIn() {
        Cursor cursor = utility.getDatabase().rawQuery("SELECT * FROM "
                + DatabaseContract.LoginTable.TABLE_NAME, null);
        return cursor.moveToFirst();
    }
}
