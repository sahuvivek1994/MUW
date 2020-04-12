package com.inscripts.ins_armman.muw.settingActivity;

import android.content.Context;

import com.inscripts.ins_armman.muw.data.model.RequestFormModel;
import com.inscripts.ins_armman.muw.data.model.UpdateModel;
import com.inscripts.ins_armman.muw.data.model.UserDetails;
import com.inscripts.ins_armman.muw.data.model.download_registrationed_data.RegisteredData;
import com.inscripts.ins_armman.muw.data.model.download_registrationed_data.RestoreAllRegistration;
import com.inscripts.ins_armman.muw.data.model.restoredata.BeneficiariesList;
import com.inscripts.ins_armman.muw.data.model.restoredata.RestoreDataRequest;
import com.inscripts.ins_armman.muw.data.model.restoredata.RestoreRegistration;
import com.inscripts.ins_armman.muw.data.model.restoredata.RestoreVisits;
import com.inscripts.ins_armman.muw.data.model.syncing.beneficiaries;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * @author Vivek & Juilee  Created on 14/8/2019
 */

public interface ISettingInteractor {

    void changeLocale(Context context, String language);

    void deleteLoginDetails();

    void downloadAndSaveApk(String apkLink);

    void downloadRegistrationData(RestoreDataRequest request, OnRegistrationsDownloadFinished downloadFinished);

    void downloadVisitsData(RestoreDataRequest request, OnVisitsDownloadFinished downloadFinished);

    void saveFormData(JSONObject formJsonObject);

    void downloadForms(RequestFormModel requestFormModel, OnFormDownloadFinished onFormDownloadFinished);

    String getHash(String type);

    void addOrUpdateFormHash(String item, String hash);

    void fetchLoginDetails(int id);

    void fetchFormJsonHash(int id);

    void checkReleaseUpdate(onCheckUpdateFinished onCheckUpdateFinished);

    void saveDownloadedData(ArrayList<beneficiaries> listRegistrations, ArrayList<BeneficiariesList> listVisits);

    interface OnFormDownloadFinished {
        void onSuccessFormDownloading(JSONObject jsonObject, String hash);

        void onFailure(String message);
    }

    interface onCheckUpdateFinished {
        void onUpdateCheckSuccess(UpdateModel updateModel);

        void onFailure(String message);
    }

    interface OnRegistrationsDownloadFinished {
        void onSuccessRegistrationsDownloading(RestoreRegistration registration);

        void onFailure(String message);
    }

    interface OnVisitsDownloadFinished {
        void onSuccessVisitsDownloading(RestoreVisits visits);
        void onFailure(String message);
    }

    void downloadAllRegistrationData(UserDetails request, OndownloadAllRegistrationData downloadData);

    interface OndownloadAllRegistrationData
    {
        void onSuccessDownloadData(RestoreAllRegistration data);
        void onFailure(String message);
    }

    void saveAllRegisteredData(ArrayList<RegisteredData> listRegistrations);
}
