package com.inscripts.ins_armman.muw.data.service;

import android.content.Context;

import com.inscripts.ins_armman.muw.R;
import com.inscripts.ins_armman.muw.data.model.syncing.FormDetails;
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

public class SyncFormService {
    private SyncFormServiceApi mFormServiceApi;

    public SyncFormService(SyncFormServiceApi mFormServiceApi) {
        this.mFormServiceApi = mFormServiceApi;
    }

    public void syncForms(FormDetails formDetails, final IMainInteractor.OnFormSync onFormSync, final Context context) {
        Call<ResponseBody> responseBodyCall = mFormServiceApi.syncFormDetails(formDetails);
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
                    onFormSync.onSuccessfullySyncForm(jsonObject);
                } catch (IOException e) {
                    e.printStackTrace();
                    onFormSync.onFailure(context.getString(R.string.input_output_error_occured));
                } catch (JSONException e) {
                    e.printStackTrace();
                    onFormSync.onFailure(context.getString(R.string.invalid_data_frm_server));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                onFormSync.onFailure(context.getString(R.string.oops_some_thing_happened_wrong));
            }
        });
    }
}
