package com.inscripts.ins_armman.npdsf.data.service;

import com.inscripts.ins_armman.npdsf.data.model.restoredata.RestoreDataRequest;
import com.inscripts.ins_armman.npdsf.data.model.restoredata.RestoreVisits;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

import static com.inscripts.ins_armman.npdsf.data.Url.GET_VISITS;

/**
 * @author Aniket & Vivek  Created on 4/9/2018
 */

public interface RestoreVisitsServiceAPI {
    @Headers({
            "Content-Type: application/json"
    })
    @POST(GET_VISITS)
    Call<RestoreVisits> restoreRegistrationData(@Body RestoreDataRequest request);
}
