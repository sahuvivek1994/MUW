package com.inscripts.ins_armman.npdsf.data.service;

import com.inscripts.ins_armman.npdsf.data.model.UserDetails;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

import static com.inscripts.ins_armman.npdsf.data.Url.AUTHENTICATE;

/**
 * @author Aniket & Vivek  Created on 16/8/2018
 */

public interface LoginServiceAPI {

    @Headers({
            "Content-Type: application/json"
    })
    @POST(AUTHENTICATE)
    Call<ResponseBody> getAuthentication(@Body UserDetails userDetails);

}
