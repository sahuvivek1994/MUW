package com.inscripts.ins_armman.npdsf.data.service;

import android.content.Context;

import com.inscripts.ins_armman.npdsf.R;
import com.inscripts.ins_armman.npdsf.data.model.UpdateModel;
import com.inscripts.ins_armman.npdsf.settingActivity.ISettingInteractor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author Aniket & Vivek  Created on 4/9/2018
 */

public class CheckUpdateService {
    private CheckUpdateApi mCheckUpdateApi;

    public CheckUpdateService(CheckUpdateApi mCheckUpdateApi) {
        this.mCheckUpdateApi = mCheckUpdateApi;
    }

    public void getUpdateData(final ISettingInteractor.onCheckUpdateFinished onCheckUpdateFinished, final Context context) {
        Call<UpdateModel> call = mCheckUpdateApi.getUpdateData();
        call.enqueue(new Callback<UpdateModel>() {
            @Override
            public void onResponse(Call<UpdateModel> call, Response<UpdateModel> response) {
                if (response.code() == 200) {
                    UpdateModel mobileData = response.body();
                    onCheckUpdateFinished.onUpdateCheckSuccess(mobileData);
                } else if (response.code() == 404) {
                    onCheckUpdateFinished.onFailure(context.getString(R.string.invalid_server_url));
                } else
                    onCheckUpdateFinished.onFailure(context.getString(R.string.invalid_data_frm_server));
            }

            @Override
            public void onFailure(Call<UpdateModel> call, Throwable t) {
                onCheckUpdateFinished.onFailure(context.getString(R.string.oops_some_thing_happened_wrong));
            }
        });
    }
}
