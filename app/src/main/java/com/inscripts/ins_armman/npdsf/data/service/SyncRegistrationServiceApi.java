package com.inscripts.ins_armman.npdsf.data.service;


import com.inscripts.ins_armman.npdsf.data.model.SyncRegistrationDetails;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

import static com.inscripts.ins_armman.npdsf.data.Url.SYNC_REGISTRATION_DATA;

/**
 * @author Aniket & Vivek  Created on 4/9/2018
 */

public interface SyncRegistrationServiceApi {
    @Headers({
            "Content-Type: application/json"
    })
    @POST(SYNC_REGISTRATION_DATA)
    Call<ResponseBody> SyncRegistrationDetails(@Body SyncRegistrationDetails registrationDetails);
}
