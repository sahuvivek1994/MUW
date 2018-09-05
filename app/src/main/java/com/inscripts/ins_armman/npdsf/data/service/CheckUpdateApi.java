package com.inscripts.ins_armman.npdsf.data.service;

import com.inscripts.ins_armman.npdsf.data.model.UpdateModel;

import retrofit2.Call;
import retrofit2.http.GET;

import static com.inscripts.ins_armman.npdsf.data.Url.RELEASE;

/**
 * @author Aniket & Vivek  Created on 4/9/2018
 */

public interface CheckUpdateApi {
    @GET(RELEASE)
    Call<UpdateModel> getUpdateData();
}
