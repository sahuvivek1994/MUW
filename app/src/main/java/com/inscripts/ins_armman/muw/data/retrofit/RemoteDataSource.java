package com.inscripts.ins_armman.muw.data.retrofit;


import com.inscripts.ins_armman.muw.data.service.AuthService;
import com.inscripts.ins_armman.muw.data.service.CheckUpdateApi;
import com.inscripts.ins_armman.muw.data.service.CheckUpdateService;
import com.inscripts.ins_armman.muw.data.service.FormDownloadService;
import com.inscripts.ins_armman.muw.data.service.FormDownloadServiceAPI;
import com.inscripts.ins_armman.muw.data.service.LoginServiceAPI;
import com.inscripts.ins_armman.muw.data.service.RestoreRegistrationService;
import com.inscripts.ins_armman.muw.data.service.RestoreRegistrationServiceAPI;
import com.inscripts.ins_armman.muw.data.service.RestoreVisitsService;
import com.inscripts.ins_armman.muw.data.service.RestoreVisitsServiceAPI;
import com.inscripts.ins_armman.muw.data.service.SyncFormService;
import com.inscripts.ins_armman.muw.data.service.SyncFormServiceApi;
import com.inscripts.ins_armman.muw.data.service.SyncRegistrationService;
import com.inscripts.ins_armman.muw.data.service.SyncRegistrationServiceApi;

import retrofit2.Retrofit;


/**
 * @author Vivek & Juilee  Created on 14/8/2019
 */

public class RemoteDataSource {
    private static RemoteDataSource mRemoteDataSource;
    private Retrofit mRestClient;

    private RemoteDataSource(Retrofit restClient) {
        mRestClient = restClient;
    }

    public static RemoteDataSource getInstance() {
        if (mRemoteDataSource == null) {
            mRemoteDataSource = new RemoteDataSource(RestClient.getClient());
        }
        return mRemoteDataSource;
    }

    public <T> T createApiService(Class<T> apiInterface) {
        return mRestClient.create(apiInterface);
    }

    public AuthService getAuthService() {
        return new AuthService(createApiService(LoginServiceAPI.class));
    }

    public FormDownloadService downloadFormService() {
        return new FormDownloadService(createApiService(FormDownloadServiceAPI.class));
    }

    public SyncFormService syncFormService() {
        return new SyncFormService(createApiService(SyncFormServiceApi.class));
    }

    public SyncRegistrationService syncRegistrationService() {
        return new SyncRegistrationService(createApiService(SyncRegistrationServiceApi.class));
    }

    public CheckUpdateService getCheckUpdateService() {
        return new CheckUpdateService(createApiService(CheckUpdateApi.class));
    }

    public RestoreRegistrationService restoreRegistrationService() {
        return new RestoreRegistrationService(createApiService(RestoreRegistrationServiceAPI.class));
    }

    public RestoreVisitsService restoreVisitsService() {
        return new RestoreVisitsService(createApiService(RestoreVisitsServiceAPI.class));
    }

  /*

    public HelpManualDownloadService helpManualDownloadService() {
        return new HelpManualDownloadService(createApiService(HelpManualDownloadServiceApi.class));
    }

    public SyncReferralService syncReferralService() {
        return new SyncReferralService(createApiService(SyncReferralServiceApi.class));
    }

    public SendChildGrowthDataService sendChildGrowthDataService() {
        return new SendChildGrowthDataService(createApiService(SendChildGrowthDataAPI.class));
    }

    public RestoreReferralService restoreReferralService() {
        return new RestoreReferralService(createApiService(RestoreReferralServiceAPI.class));
    }

    public SyncUpdatePhotoService syncUpdatePhotoService() {
        return new SyncUpdatePhotoService(createApiService(SyncUpdatePhotoServiceApi.class));
    }

    public RestoreChildGrowthService restoreChildGrowthService() {
        return new RestoreChildGrowthService(createApiService(RestoreChildGrowthServiceAPI.class));
    }*/
}
