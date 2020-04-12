package com.inscripts.ins_armman.muw.data.service;

import com.inscripts.ins_armman.muw.data.model.UserDetails;
import com.inscripts.ins_armman.muw.data.model.download_registrationed_data.RegisteredData;
import com.inscripts.ins_armman.muw.data.model.download_registrationed_data.RestoreAllRegistration;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

import static com.inscripts.ins_armman.muw.data.Url.SYNC_ALL_REGISTERED_DATA;

public interface FetchAllRegisteredDataAPI {

    @Headers({
            "Content-Type: application/json"
    })
@POST(SYNC_ALL_REGISTERED_DATA)
    Call<RestoreAllRegistration> registeredData(@Body UserDetails userDetails);

}
