package com.inscripts.ins_armman.muw.data.service;

import com.inscripts.ins_armman.muw.data.model.syncing.FormDetails;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

import static com.inscripts.ins_armman.muw.data.Url.SYNC_FORM_DATA;

/**
 * @author Vivek & Juilee  Created on 14/8/2019
 */

public interface SyncFormServiceApi {
    @Headers({
            "Content-Type: application/json"
    })
    @POST(SYNC_FORM_DATA)
    Call<ResponseBody> syncFormDetails(@Body FormDetails formDetails);
}
