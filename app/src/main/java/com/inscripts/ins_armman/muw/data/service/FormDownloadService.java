package com.inscripts.ins_armman.muw.data.service;

import android.content.Context;

import com.inscripts.ins_armman.muw.R;
import com.inscripts.ins_armman.muw.data.model.RequestFormModel;
import com.inscripts.ins_armman.muw.settingActivity.ISettingInteractor;
import com.inscripts.ins_armman.muw.utility.utility;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author Vivek & Juilee  Created on 14/8/2019
 */

public class FormDownloadService {

    private FormDownloadServiceAPI formDownloadServiceAPI;

    public FormDownloadService(FormDownloadServiceAPI formDownloadServiceAPI) {
        this.formDownloadServiceAPI = formDownloadServiceAPI;
    }

    public void downloadForms(RequestFormModel requestFormModel, final ISettingInteractor.OnFormDownloadFinished onFormDownloadFinished, final Context context) {
        if (requestFormModel != null) {
            Call<ResponseBody> responseBodyCall = formDownloadServiceAPI.downloadFormJson(requestFormModel);
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
                        JSONObject loginJsonObject = new JSONObject(loginJsonResponse);
                        onFormDownloadFinished.onSuccessFormDownloading(loginJsonObject, utility.mdFive(loginJsonResponse));
                    } catch (IOException e) {
                        e.printStackTrace();
                        onFormDownloadFinished.onFailure(context.getString(R.string.input_output_error_occured));
                    } catch (JSONException e) {
                        e.printStackTrace();
                        onFormDownloadFinished.onFailure(context.getString(R.string.invalid_data_frm_server));
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    onFormDownloadFinished.onFailure(context.getString(R.string.oops_some_thing_happened_wrong));
                }
            });
        }
    }
}
