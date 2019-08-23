package com.inscripts.ins_armman.muw.data.service;

import com.inscripts.ins_armman.muw.data.model.restoredata.RestoreDataRequest;
import com.inscripts.ins_armman.muw.data.model.restoredata.RestoreVisits;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

import static com.inscripts.ins_armman.muw.data.Url.GET_VISITS;

/**
 * @author Vivek & Juilee  Created on 14/8/2019
 */

public interface RestoreVisitsServiceAPI {
    @Headers({
            "Content-Type: application/json"
    })
    @POST(GET_VISITS)
    Call<RestoreVisits> restoreRegistrationData(@Body RestoreDataRequest request);
}
