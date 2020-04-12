package com.inscripts.ins_armman.muw.data.service;

import android.content.Context;

import com.inscripts.ins_armman.muw.R;
import com.inscripts.ins_armman.muw.data.model.UserDetails;
import com.inscripts.ins_armman.muw.data.model.download_registrationed_data.RegisteredData;
import com.inscripts.ins_armman.muw.data.model.download_registrationed_data.RestoreAllRegistration;
import com.inscripts.ins_armman.muw.settingActivity.ISettingInteractor;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FetchAllRegisteredData {
    private FetchAllRegisteredDataAPI mServiceAPI;

    public FetchAllRegisteredData(FetchAllRegisteredDataAPI mServiceAPI) {
        this.mServiceAPI = mServiceAPI;
    }

    public void downloadVisitsData(final Context context, UserDetails request, final ISettingInteractor.OndownloadAllRegistrationData downloadFinished) {
        Call<RestoreAllRegistration> call = mServiceAPI.registeredData(request);
        call.enqueue(new Callback<RestoreAllRegistration>() {
            @Override
            public void onResponse(Call<RestoreAllRegistration> call, Response<RestoreAllRegistration> response) {
                if (response.code() == 200) {
                    downloadFinished.onSuccessDownloadData(response.body());
                } else {
                    try {
                        downloadFinished.onFailure(response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<RestoreAllRegistration> call, Throwable t) {
                downloadFinished.onFailure(context.getString(R.string.oops_some_thing_happened_wrong));
            }
        });
    }
}
