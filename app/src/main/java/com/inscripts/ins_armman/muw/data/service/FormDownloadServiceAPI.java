package com.inscripts.ins_armman.muw.data.service;


import com.inscripts.ins_armman.muw.data.model.RequestFormModel;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

import static com.inscripts.ins_armman.muw.data.Url.DOWNLOAD_FORMS;

/**
 * @author Vivek & Juilee  Created on 14/8/2019
 */

public interface FormDownloadServiceAPI {
    @Headers({
            "Content-Type: application/json"
    })
    @POST(DOWNLOAD_FORMS)
    Call<ResponseBody> downloadFormJson(@Body RequestFormModel requestFormModel);
}
