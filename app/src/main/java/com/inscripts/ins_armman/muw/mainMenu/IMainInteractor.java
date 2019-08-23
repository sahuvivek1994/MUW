package com.inscripts.ins_armman.muw.mainMenu;

import android.database.Cursor;

import com.inscripts.ins_armman.muw.data.model.SyncRegistrationDetails;
import com.inscripts.ins_armman.muw.data.model.syncing.FormDetails;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * @author Vivek & Juilee  Created on 14/8/2019
 */

public interface IMainInteractor {

    Integer fetchUnsentFormsCount();
    ArrayList<Integer> getCounts();
    void resetFailureStatus();

    void fetchRegistrationDetails(int id);

    Cursor checkUnsentForms();

    Cursor fetchFormData(String referenceId);

    void sendForms(FormDetails formDetails, IMainInteractor.OnFormSync onFormSync);

    int fetchUnregisteredChildCount(String motherId);

    void updateFormFailureStatus(String uniqueId, String formId, String errorMsg);

    void sendRegistrationBasicDetails(SyncRegistrationDetails registrationDetails, IMainInteractor.OnDataSync onDataSync);

    void updateFormSyncStatus(String uniqueId, String formId);

    void updateRegistrationSyncStatus(JSONArray jsonArray);

    void fetchLoginDetails(int id);

    interface OnFormSync {
        void onSuccessfullySyncForm(JSONObject jsonObject);

        void onFailure(String message);
    }

    interface OnDataSync {
        void onSuccessfullySyncRegData(JSONObject jsonObjectResponse);

        void onFailure(String message);
    }
}
