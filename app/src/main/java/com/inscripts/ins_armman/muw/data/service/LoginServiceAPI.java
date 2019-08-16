package com.inscripts.ins_armman.muw.data.service;

import com.inscripts.ins_armman.muw.data.model.UserDetails;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

import static com.inscripts.ins_armman.muw.data.Url.AUTHENTICATE;

/**
 * @author Vivek & Juilee  Created on 14/8/2019
 */

public interface LoginServiceAPI {

    @Headers({
            "Content-Type: application/json"
    })
    @POST(AUTHENTICATE)
    Call<ResponseBody> getAuthentication(@Body UserDetails userDetails);

}
