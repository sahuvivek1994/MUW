package com.inscripts.ins_armman.muw.data.service;

import com.inscripts.ins_armman.muw.data.model.UpdateModel;

import retrofit2.Call;
import retrofit2.http.GET;

import static com.inscripts.ins_armman.muw.data.Url.RELEASE;

/**
 * @author Vivek & Juilee  Created on 14/8/2019
 */

public interface CheckUpdateApi {
    @GET(RELEASE)
    Call<UpdateModel> getUpdateData();
}
