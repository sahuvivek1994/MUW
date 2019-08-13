package com.inscripts.ins_armman.muw.data.service;

import android.content.Context;

import com.inscripts.ins_armman.muw.R;
import com.inscripts.ins_armman.muw.data.model.SyncRegistrationDetails;
import com.inscripts.ins_armman.muw.mainMenu.IMainInteractor;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author Aniket & Vivek  Created on 4/9/2018
 */

public class SyncRegistrationService {

    private SyncRegistrationServiceApi serviceApi;

    public SyncRegistrationService(SyncRegistrationServiceApi serviceApi) {
        this.serviceApi = serviceApi;
    }

    public void syncRegistrationDetails(SyncRegistrationDetails registrationDetails, final IMainInteractor.OnDataSync onDataSync, final Context context) {
        Call<ResponseBody> responseBodyCall = serviceApi.SyncRegistrationDetails(registrationDetails);
        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String loginJsonResponse = null;
                    if (response.body() != null) {
                        loginJsonResponse = response.body().string();
                    } else if (response.errorBody() != null) {
                        loginJsonResponse = response.errorBody().string();
                    }
                    JSONObject jsonObject = new JSONObject(loginJsonResponse);
                    onDataSync.onSuccessfullySyncRegData(jsonObject);
                } catch (IOException e) {
                    e.printStackTrace();
                    onDataSync.onFailure(context.getString(R.string.input_output_error_occured));
                } catch (JSONException e) {
                    e.printStackTrace();
                    onDataSync.onFailure(context.getString(R.string.invalid_data_frm_server));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                onDataSync.onFailure(context.getString(R.string.oops_some_thing_happened_wrong));
            }
        });
    }
}
