package com.inscripts.ins_armman.muw.data.service;

import android.content.Context;

import com.inscripts.ins_armman.muw.R;
import com.inscripts.ins_armman.muw.data.model.restoredata.RestoreDataRequest;
import com.inscripts.ins_armman.muw.data.model.restoredata.RestoreRegistration;
import com.inscripts.ins_armman.muw.settingActivity.ISettingInteractor;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author Aniket & Vivek  Created on 4/9/2018
 */

public class RestoreRegistrationService {
    private RestoreRegistrationServiceAPI mServiceAPI;

    public RestoreRegistrationService(RestoreRegistrationServiceAPI mServiceAPI) {
        this.mServiceAPI = mServiceAPI;
    }

    public void downloadRegistrationData(final Context context, RestoreDataRequest request, final ISettingInteractor.OnRegistrationsDownloadFinished downloadFinished) {
        Call<RestoreRegistration> call = mServiceAPI.restoreRegistrationData(request);
        call.enqueue(new Callback<RestoreRegistration>() {
            @Override
            public void onResponse(Call<RestoreRegistration> call, Response<RestoreRegistration> response) {

                if (response.code() == 200) {
                    downloadFinished.onSuccessRegistrationsDownloading(response.body());
                } else {
                    try {
                        downloadFinished.onFailure(response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<RestoreRegistration> call, Throwable t) {
                downloadFinished.onFailure(context.getString(R.string.oops_some_thing_happened_wrong));
            }
        });
    }
}
