package com.inscripts.ins_armman.muw.settingActivity;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.inscripts.ins_armman.muw.R;
import com.inscripts.ins_armman.muw.data.model.Form;
import com.inscripts.ins_armman.muw.data.model.RequestFormModel;
import com.inscripts.ins_armman.muw.data.model.UserDetails;
import com.inscripts.ins_armman.muw.data.model.download_registrationed_data.RegisteredData;
import com.inscripts.ins_armman.muw.data.model.restoredata.BeneficiariesList;
import com.inscripts.ins_armman.muw.data.model.restoredata.RestoreDataRequest;
import com.inscripts.ins_armman.muw.data.model.restoredata.VisitsList;
import com.inscripts.ins_armman.muw.data.model.syncing.QuestionAnswer;
import com.inscripts.ins_armman.muw.data.model.syncing.beneficiaries;
import com.inscripts.ins_armman.muw.data.retrofit.RemoteDataSource;
import com.inscripts.ins_armman.muw.data.service.CheckUpdateService;
import com.inscripts.ins_armman.muw.data.service.FetchAllRegisteredData;
import com.inscripts.ins_armman.muw.data.service.FormDownloadService;
import com.inscripts.ins_armman.muw.data.service.RestoreRegistrationService;
import com.inscripts.ins_armman.muw.data.service.RestoreVisitsService;
import com.inscripts.ins_armman.muw.database.DatabaseContract;
import com.inscripts.ins_armman.muw.database.GenericCursorLoader;
import com.inscripts.ins_armman.muw.database.LocalDataSource;
import com.inscripts.ins_armman.muw.utility.Constants;
import com.inscripts.ins_armman.muw.utility.utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

import static com.inscripts.ins_armman.muw.database.DatabaseContract.FilledFormStatusTable;
import static com.inscripts.ins_armman.muw.database.DatabaseContract.FormDetailsTable;
import static com.inscripts.ins_armman.muw.database.DatabaseContract.RegistrationTable;
import static com.inscripts.ins_armman.muw.utility.Constants.DEFAULT_HASH;
import static com.inscripts.ins_armman.muw.utility.Constants.HASH_ITEM_FORM;
import static com.inscripts.ins_armman.muw.utility.Constants.TOTAL_FORM_COUNT;
import static com.inscripts.ins_armman.muw.utility.utility.getDatabase;

/**
 * @author Vivek & Juilee  Created on 14/8/2019
 */

public class SettingInteractor implements ISettingInteractor, LoaderManager.LoaderCallbacks<Cursor> {

    private Context mContext;
    private ISettingPresenter.OnQueryFinished mOnQueryFinished;
    private SettingPresenter mSettingsPresenter;

    public SettingInteractor(Context mContext, SettingPresenter mSettingsPresenter, ISettingPresenter.OnQueryFinished mOnQueryFinished) {
        this.mContext = mContext;
        this.mSettingsPresenter = mSettingsPresenter;
        this.mOnQueryFinished = mOnQueryFinished;
    }

    @Override
    public void changeLocale(Context context, String language) {
        utility.setApplicationLocale(context, language);
    }

    @Override
    public void deleteLoginDetails() {
        getDatabase().delete(DatabaseContract.LoginTable.TABLE_NAME, null, null);
    }

    @Override
    public void saveFormData(JSONObject formJsonObject) {
        new SaveFormAsyncTask().execute(formJsonObject);
    }

    @Override
    public void downloadForms(RequestFormModel requestFormModel, OnFormDownloadFinished onFormDownloadFinished) {
        RemoteDataSource remoteDataSource = RemoteDataSource.getInstance();
        FormDownloadService formDownloadService = remoteDataSource.downloadFormService();
        formDownloadService.downloadForms(requestFormModel, onFormDownloadFinished, mContext);
    }

    @Override
    public String getHash(String type) {
        Cursor cursor = getDatabase().rawQuery("SELECT * FROM "
                + DatabaseContract.HashTable.TABLE_NAME
                + " WHERE "
                + DatabaseContract.HashTable.COLUMN_ITEM
                + " = ? ", new String[]{type});

        return cursor.moveToFirst() ? cursor.getString(cursor.getColumnIndex(DatabaseContract.HashTable.COLUMN_HASH)) : DEFAULT_HASH;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle bundle) {

        SQLiteDatabase sqLiteDatabase = getDatabase();
        String queryType = bundle.getString(Constants.QUERY_TYPE);
        if (queryType != null && queryType.equalsIgnoreCase(LocalDataSource.QueryType.RAW.name())) {
            return new GenericCursorLoader(mContext, sqLiteDatabase, bundle, LocalDataSource.QueryType.RAW);
        } else {
            return new GenericCursorLoader(mContext, sqLiteDatabase, bundle, LocalDataSource.QueryType.FUNCTION);
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mOnQueryFinished.onSuccess(data, loader.getId());
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void addOrUpdateFormHash(String item, String hash) {

        ContentValues values = new ContentValues();
        values.put(DatabaseContract.HashTable.COLUMN_HASH, hash);
        values.put(DatabaseContract.HashTable.COLUMN_ITEM, item);

        Cursor cursor = utility.getDatabase().rawQuery("SELECT * FROM "
                + DatabaseContract.HashTable.TABLE_NAME
                + " WHERE "
                + DatabaseContract.HashTable.COLUMN_ITEM + " = ? ", new String[]{item});

        if (cursor.moveToFirst()) {
            utility.getDatabase().update(DatabaseContract.HashTable.TABLE_NAME
                    , values
                    , DatabaseContract.HashTable.COLUMN_ITEM + " = ? "
                    , new String[]{item});
        } else {
            utility.getDatabase().insert(DatabaseContract.HashTable.TABLE_NAME, null, values);
        }
    }

    @Override
    public void fetchLoginDetails(int id) {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.QUERY_ARGS_TABLE_NAME, DatabaseContract.LoginTable.TABLE_NAME);
        bundle.putStringArray(Constants.QUERY_ARGS_PROJECTION, null);
        bundle.putString(Constants.QUERY_ARGS_SELECTION, null);
        bundle.putStringArray(Constants.QUERY_ARGS_SELECTION_ARGS, null);
        ((AppCompatActivity) mContext).getSupportLoaderManager().restartLoader(id, bundle, this);
    }

    @Override
    public void fetchFormJsonHash(int id) {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.QUERY_ARGS_TABLE_NAME, DatabaseContract.HashTable.TABLE_NAME);
        bundle.putStringArray(Constants.QUERY_ARGS_PROJECTION, null);
        bundle.putString(Constants.QUERY_ARGS_SELECTION, null);
        bundle.putStringArray(Constants.QUERY_ARGS_SELECTION_ARGS, null);
        ((AppCompatActivity) mContext).getSupportLoaderManager().restartLoader(id, bundle, this);
    }

    void saveFormDetails(Form form) {
        ContentValues values = new ContentValues();
        values.put(FormDetailsTable.COLUMN_FORM_ID, form.getFormId());
        values.put(FormDetailsTable.COLUMN_VISIT_NAME, form.getVisitName());
        values.put(DatabaseContract.FormDetailsTable.COLUMN_ORDER_ID, form.getOrderId());

        utility.getDatabase().insert(DatabaseContract.FormDetailsTable.TABLE_NAME, null, values);
    }

    @Override
    public void checkReleaseUpdate(onCheckUpdateFinished onCheckUpdateFinished) {
        RemoteDataSource remoteDataSource = RemoteDataSource.getInstance();
        CheckUpdateService checkUpdateService = remoteDataSource.getCheckUpdateService();
        checkUpdateService.getUpdateData(onCheckUpdateFinished, mContext);
    }

    @Override
    public void downloadAndSaveApk(String apkLink) {
        String destination = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/Download/";
        String fileName = "MUW.apk";
        destination += fileName;
        final Uri uri = Uri.parse("file://" + destination);

        //Delete update file if exists
        File file = new File(destination);
        if (file.exists())
            file.delete();
        //file.delete() - test this, I think sometimes it doesnt work


        //set downloadmanager
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(apkLink));
        request.setDescription(mContext.getString(R.string.apk_download_request_text));
        request.setTitle(mContext.getString(R.string.app_name));

        //set destination
        request.setDestinationUri(uri);

        // get download service and enqueue file
        final DownloadManager manager = (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);
        final long downloadId = manager.enqueue(request);

        new Thread(new Runnable() {

            @Override
            public void run() {

                boolean downloading = true;

                while (downloading) {

                    DownloadManager.Query q = new DownloadManager.Query();
                    q.setFilterById(downloadId);

                    Cursor cursor = manager.query(q);
                    cursor.moveToFirst();
                    int bytes_downloaded = cursor.getInt(cursor
                            .getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                    int bytes_total = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));

                    if (cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)) == DownloadManager.STATUS_SUCCESSFUL) {
                        downloading = false;

                    }

                    double dl_progress = bytes_downloaded * 100 / bytes_total;
                    int progressInt = (int) dl_progress;
                    Log.i("download_apk", "progress " + progressInt);
                    mSettingsPresenter.setApkDownloadProgress(progressInt);
                    cursor.close();
                }

            }
        }).start();
        //set BroadcastReceiver to install app when .apk is downloaded
        BroadcastReceiver onComplete = new BroadcastReceiver() {
            public void onReceive(Context ctxt, Intent intent) {
                mSettingsPresenter.onApkDownloaded();
                Intent install = new Intent(Intent.ACTION_VIEW);
                install.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                install.setDataAndType(uri,
                        manager.getMimeTypeForDownloadedFile(downloadId));
                mContext.startActivity(install);

                mContext.unregisterReceiver(this);
                //finish();
            }

        };

        //register receiver for when .apk download is compete
        mContext.registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    @Override
    public void downloadRegistrationData(RestoreDataRequest request, OnRegistrationsDownloadFinished downloadFinished) {
        RemoteDataSource remoteDataSource = RemoteDataSource.getInstance();
        RestoreRegistrationService service = remoteDataSource.restoreRegistrationService();
        service.downloadRegistrationData(mContext, request, downloadFinished);
    }

    @Override
    public void downloadVisitsData(RestoreDataRequest request, OnVisitsDownloadFinished downloadFinished) {
        RemoteDataSource remoteDataSource = RemoteDataSource.getInstance();
        RestoreVisitsService service = remoteDataSource.restoreVisitsService();
        service.downloadVisitsData(mContext, request, downloadFinished);
    }

    @Override
    public void saveDownloadedData(ArrayList<beneficiaries> listRegistrations, ArrayList<BeneficiariesList> listVisits) {

        new SaveRestoredDataAsyncTask(listRegistrations, listVisits).execute();
    }

    class SaveFormAsyncTask extends AsyncTask<JSONObject, Integer, Void> {

        ProgressBar progressBar;
        int visit_from_week, visit_to_week;
        String displayCondition;
        String orderno, depend1, question_keyword, ques_ans_type, messages, calculations, ques_lang, option_id, option_language, option_messages, dependant_ques_messages, attr16, attr17;
        String vali_req, dependant, custom_vali_cond, cust_lang, range_min, range_max, range_lang, length_min, length_max, length_lang, avoid_repetition;
        int Form_id, quesid;
        String option_keyword, visit_name, setid;
        private AlertDialog mProgressDialog;
        private int mProgress;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View dialogView = inflater.inflate(R.layout.progress_dialog_layout, null);
            TextView textView = dialogView.findViewById(R.id.textView_label);
            progressBar = dialogView.findViewById(R.id.progressBar);
            textView.setText(R.string.saving_forms);
            progressBar.setIndeterminate(false);
            progressBar.setMax(TOTAL_FORM_COUNT);
            AlertDialog.Builder mAlertDialogBuilder = new AlertDialog.Builder(mContext);
            mAlertDialogBuilder.setView(dialogView);
            mAlertDialogBuilder.setCancelable(false);
            mProgressDialog = mAlertDialogBuilder.create();
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(JSONObject... jsonObjects) {

            utility.getDatabase().beginTransaction();

            deleteOldFormData();

            JSONObject jsonObject = jsonObjects[0];
            try {
//                dbhelper.deleteSynchronizationTables();

                JSONArray formArray = jsonObject.optJSONArray("forms");
                int length = formArray.length();

                for (int i = 0; i < length; i++) {

                    JSONObject formkeys = formArray.getJSONObject(i);

                    String visitNameString = formkeys.optString("details");

                    visit_name = visitNameString;
//
//                    if (visitNameString.contains("details")) {
//                        JSONObject visitNameObject = new JSONObject(visitNameString);
//                        visit_name = visitNameObject.optString("details");
//                    }

                    Form_id = formkeys.optInt("form_id");
//                    visit_from_week = formkeys.optInt("from_weeks");
//                    visit_to_week = formkeys.optInt("to_weeks");
                    String orderId = formkeys.optString("orderby");

                    saveFormDetails(new Form(Form_id, visit_name, orderId));
//                    dbhelper.insertanswer(Form_id, trimester_name, visit_name, visit_from_week, visit_to_week, Form_id);

                    JSONArray questionArray = formkeys.optJSONArray("question");
                    int qstnLength = questionArray.length();

                    for (int j = 0; j < qstnLength; j++) {

                        JSONObject main_question_keys = questionArray.getJSONObject(j);

                        quesid = main_question_keys.optInt("id");

                        setid = main_question_keys.optString("set_id");
                        question_keyword = main_question_keys.optString("keyword");
                        orderno = main_question_keys.optString("order_no");
                        ques_ans_type = main_question_keys.optString("answer_type");
                        ques_lang = main_question_keys.optString("languages");

                        if (main_question_keys.optString("messages") != null && main_question_keys.optString("messages").length() > 0) {
                            messages = main_question_keys.optString("messages");
                        } else {
                            messages = "";
                        }

                        if (main_question_keys.optString("calculations") != null && main_question_keys.optString("calculations").length() > 0) {
                            calculations = main_question_keys.optString("calculations");
                        } else {
                            calculations = "";
                        }

                        if (main_question_keys.optString("validation") != null && main_question_keys.optString("validation").length() > 0) {
                            JSONObject validationobject = main_question_keys.getJSONObject("validation");

                            if (validationobject.optString("required") != null && validationobject.optString("required").length() > 0) {
                                vali_req = validationobject.optString("required");
                            } else {
                                vali_req = "";
                            }

                            if (validationobject.optString("avoid_repetition") != null && validationobject.optString("avoid_repetition").length() > 0) {
                                avoid_repetition = validationobject.optString("avoid_repetition");
                            } else
                                avoid_repetition = "";

                            if (validationobject.optString("custom") != null && validationobject.optString("custom").length() > 0) {
                                JSONObject customobject = validationobject.getJSONObject("custom");
                                custom_vali_cond = customobject.optString("validation_condition");
                                cust_lang = customobject.optString("languages");
                            } else {
                                custom_vali_cond = "";
                                cust_lang = "";
                            }

                            if (validationobject.optString("range") != null && validationobject.optString("range").length() > 0) {
                                JSONObject rangeobject = validationobject.getJSONObject("range");
//
                                range_min = rangeobject.optString("min");
                                range_max = rangeobject.optString("max");
                                range_lang = rangeobject.optString("languages");
                            } else {
                                range_min = "";
                                range_max = "";
                                range_lang = "";
                            }

                            if (validationobject.optString("length") != null && validationobject.optString("length").length() > 0) {

                                JSONObject lengthobject = validationobject.getJSONObject("length");
//
                                length_min = lengthobject.optString("min");
                                length_max = lengthobject.optString("max");
                                length_lang = lengthobject.optString("languages");
                            } else {
                                length_min = "";
                                length_max = "";
                                length_lang = "";
                            }

                            if (validationobject.optString("display_condition") != null && validationobject.optString("display_condition").length() > 0) {
                                displayCondition = validationobject.optString("display_condition");
                            } else {
                                displayCondition = "";
                            }

//                            dbhelper.insertquesValidation(setid, quesid, vali_req, custom_vali_cond, cust_lang, range_min, range_max, range_lang, length_min, length_max, length_lang, displayCondition, avoid_repetition);
                            saveValidationData(Form_id, quesid, vali_req, custom_vali_cond, cust_lang, range_min, range_max, range_lang
                                    , length_min, length_max, length_lang, displayCondition, avoid_repetition);
                        }

                        if (main_question_keys.optJSONArray("options") != null) {
                                JSONArray main_question_optionArray = main_question_keys.optJSONArray("options");

                            for (int k = 0; k < main_question_optionArray.length(); k++) {

                                JSONObject main_ques_options_key = main_question_optionArray.getJSONObject(k);

                                recursiveDependantCheck(main_ques_options_key, main_question_keys.getInt("orientation"));
                            }
                        }
//                        dbhelper.insertquestions(Form_id, quesid, question_keyword, orderno, ques_ans_type, setid, ques_lang, messages, calculations, main_question_keys.getInt("orientation"));
                        saveMainQuestions(Form_id, quesid, question_keyword, ques_ans_type, ques_lang, messages, calculations, main_question_keys.getInt("orientation"));
                    }

                    publishProgress(++mProgress);
                }

                addOrUpdateFormHash(HASH_ITEM_FORM, jsonObject.optString("hash"));

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                utility.getDatabase().setTransactionSuccessful();
                utility.getDatabase().endTransaction();
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mProgressDialog.dismiss();
        }

        void deleteOldFormData() {
            utility.getDatabase().execSQL("DROP TABLE IF EXISTS " + DatabaseContract.DependentQuestionsTable.TABLE_NAME);
            utility.getDatabase().execSQL("DROP TABLE IF EXISTS " + DatabaseContract.FormDetailsTable.TABLE_NAME);
            utility.getDatabase().execSQL("DROP TABLE IF EXISTS " + DatabaseContract.MainQuestionsTable.TABLE_NAME);
            utility.getDatabase().execSQL("DROP TABLE IF EXISTS " + DatabaseContract.QuestionOptionsTable.TABLE_NAME);
            utility.getDatabase().execSQL("DROP TABLE IF EXISTS " + DatabaseContract.ValidationsTable.TABLE_NAME);

            utility.getDatabase().execSQL(DatabaseContract.FormDetailsTable.CREATE_TABLE);
            utility.getDatabase().execSQL(DatabaseContract.MainQuestionsTable.CREATE_TABLE);
            utility.getDatabase().execSQL(DatabaseContract.DependentQuestionsTable.CREATE_TABLE);
            utility.getDatabase().execSQL(DatabaseContract.QuestionOptionsTable.CREATE_TABLE);
            utility.getDatabase().execSQL(DatabaseContract.ValidationsTable.CREATE_TABLE);
        }

      /*  void saveFormDetails(Form form) {
            ContentValues values = new ContentValues();
            values.put(FormDetailsTable.COLUMN_FORM_ID, form.getFormId());
            values.put(FormDetailsTable.COLUMN_VISIT_NAME, form.getVisitName());
            values.put(FormDetailsTable.COLUMN_FROM_WEEKS, form.getFromDays());
            values.put(FormDetailsTable.COLUMN_TO_WEEKS, form.getToDays());
            values.put(FormDetailsTable.COLUMN_ORDER_ID, form.getOrderId());

            Utility.getDatabase().insert(DatabaseContract.FormDetailsTable.TABLE_NAME, null, values);
        }*/

        void saveValidationData(int formId, int questionId, String compulsoryQstn, String custValCondition
                , String custValMessage, String minRange, String maxRange, String rangeMessage
                , String minLength, String maxLength, String LengthMessage, String displayCondition
                , String avoidRepetition) {

            ContentValues values = new ContentValues();
            values.put(DatabaseContract.ValidationsTable.COLUMN_FORM_ID, formId);
            values.put(DatabaseContract.ValidationsTable.COLUMN_QUESTION_ID, questionId);
            values.put(DatabaseContract.ValidationsTable.COLUMN_COMPULSORY_QUESTIONS, compulsoryQstn);
            values.put(DatabaseContract.ValidationsTable.COLUMN_CUSTOM_VALIDATION_CON, custValCondition);
            values.put(DatabaseContract.ValidationsTable.COLUMN_CUSTOM_VALIDATION_LANG, custValMessage);
            values.put(DatabaseContract.ValidationsTable.COLUMN_MIN_RANGE, minRange);
            values.put(DatabaseContract.ValidationsTable.COLUMN_MAX_RANGE, maxRange);
            values.put(DatabaseContract.ValidationsTable.COLUMN_RANGE_ERROR_MESSAGE, rangeMessage);
            values.put(DatabaseContract.ValidationsTable.COLUMN_MIN_LENGTH, minLength);
            values.put(DatabaseContract.ValidationsTable.COLUMN_MAX_LENGTH, maxLength);
            values.put(DatabaseContract.ValidationsTable.COLUMN_LENGTH_ERROR_MESSAGE, LengthMessage);
            values.put(DatabaseContract.ValidationsTable.COLUMN_DISPLAY_CONDITION, displayCondition);
            values.put(DatabaseContract.ValidationsTable.COLUMN_AVOID_REPETETIONS, avoidRepetition);

            utility.getDatabase().insertWithOnConflict(DatabaseContract.ValidationsTable.TABLE_NAME,
                    null,
                    values, SQLiteDatabase.CONFLICT_IGNORE);
        }

        void saveMainQuestions(int formId, int questionId, String keyword, String questionType, String questionLabel
                , String messages, String calculations, int orientation) {

            ContentValues values = new ContentValues();
            values.put(DatabaseContract.MainQuestionsTable.COLUMN_FORM_ID, formId);
            values.put(DatabaseContract.MainQuestionsTable.COLUMN_QUESTION_ID, questionId);
            values.put(DatabaseContract.MainQuestionsTable.COLUMN_KEYWORD, keyword);
            values.put(DatabaseContract.MainQuestionsTable.COLUMN_QUESTION_TYPE, questionType);
            values.put(DatabaseContract.MainQuestionsTable.COLUMN_QUESTION_LABEL, questionLabel);
            values.put(DatabaseContract.MainQuestionsTable.COLUMN_MESSAGES, messages);
            values.put(DatabaseContract.MainQuestionsTable.COLUMN_CALCULATION, calculations);
            values.put(DatabaseContract.MainQuestionsTable.COLUMN_ORIENTATION, orientation);

            utility.getDatabase().insert(DatabaseContract.MainQuestionsTable.TABLE_NAME, null, values);
        }

        void saveDependentQuestions(String optionKeyword, int questionId, int formId, String keyword, String questionType, String questionLabel
                , String messages, String validation, int orientation) {

            ContentValues values = new ContentValues();
            values.put(DatabaseContract.DependentQuestionsTable.COLUMN_MAIN_QUESTION_OPTION_KEYWORD, optionKeyword);
            values.put(DatabaseContract.DependentQuestionsTable.COLUMN_QUESTION_ID, questionId);
            values.put(DatabaseContract.DependentQuestionsTable.COLUMN_FORM_ID, formId);
            values.put(DatabaseContract.DependentQuestionsTable.COLUMN_KEYWORD, keyword);
            values.put(DatabaseContract.DependentQuestionsTable.COLUMN_QUESTION_TYPE, questionType);
            values.put(DatabaseContract.DependentQuestionsTable.COLUMN_QUESTION_LABEL, questionLabel);
            values.put(DatabaseContract.DependentQuestionsTable.COLUMN_MESSAGES, messages);
            values.put(DatabaseContract.DependentQuestionsTable.COLUMN_VALIDATIONS, validation);
            values.put(DatabaseContract.DependentQuestionsTable.COLUMN_ORIENTATION, orientation);

            utility.getDatabase().insert(DatabaseContract.DependentQuestionsTable.TABLE_NAME, null, values);
        }

        void saveQuestionOptions(int formId, int questionId, String keyword, String optionLabel, String messages) {

            ContentValues values = new ContentValues();

            values.put(DatabaseContract.QuestionOptionsTable.COLUMN_FORM_ID, formId);
            values.put(DatabaseContract.QuestionOptionsTable.COLUMN_QUESTION_ID, questionId);
            values.put(DatabaseContract.QuestionOptionsTable.COLUMN_KEYWORD, keyword);
            values.put(DatabaseContract.QuestionOptionsTable.COLUMN_OPTION_LABEL, optionLabel);
            values.put(DatabaseContract.QuestionOptionsTable.COLUMN_MESSAGES, messages);

            utility.getDatabase().insert(DatabaseContract.QuestionOptionsTable.TABLE_NAME, null, values);
        }

        public void recursiveDependantCheck(JSONObject main_ques_options_key, int orientation) throws JSONException {


            option_id = main_ques_options_key.optString("id");

            if (main_ques_options_key.optString("validation") != null && main_ques_options_key.optString("validation").length() > 0) {
                JSONObject validationobject = main_ques_options_key.getJSONObject("validation");

                if (validationobject.optString("required") != null && validationobject.optString("required").length() > 0) {
                    vali_req = validationobject.optString("required");
                } else {
                    vali_req = "";
                }

                if (validationobject.optString("avoid_repetition") != null && validationobject.optString("avoid_repetition").length() > 0) {
                    avoid_repetition = validationobject.optString("avoid_repetition");
                } else
                    avoid_repetition = "";

                if (validationobject.optString("custom") != null && validationobject.optString("custom").length() > 0) {
                    JSONObject customobject = validationobject.getJSONObject("custom");
                    custom_vali_cond = customobject.optString("validation_condition");
                    cust_lang = customobject.optString("languages");
                } else {
                    custom_vali_cond = "";
                    cust_lang = "";
                }

                if (validationobject.optString("range") != null && validationobject.optString("range").length() > 0) {
                    JSONObject rangeobject = validationobject.getJSONObject("range");
//
                    range_min = rangeobject.optString("min");
                    range_max = rangeobject.optString("max");
                    range_lang = rangeobject.optString("languages");
                } else {
                    range_min = "";
                    range_max = "";
                    range_lang = "";
                }

                if (validationobject.optString("length") != null && validationobject.optString("length").length() > 0) {

                    JSONObject lengthobject = validationobject.getJSONObject("length");
//
                    length_min = lengthobject.optString("min");
                    length_max = lengthobject.optString("max");
                    length_lang = lengthobject.optString("languages");
                } else {
                    length_min = "";
                    length_max = "";
                    length_lang = "";
                }

                if (validationobject.optString("display_condition") != null && validationobject.optString("display_condition").length() > 0) {
                    displayCondition = validationobject.optString("display_condition");
                } else {
                    displayCondition = "";
                }

//                            dbhelper.insertquesValidation(setid, quesid, vali_req, custom_vali_cond, cust_lang, range_min, range_max, range_lang, length_min, length_max, length_lang, displayCondition, avoid_repetition);
                saveValidationData(Form_id, quesid, vali_req, custom_vali_cond, cust_lang, range_min, range_max, range_lang
                        , length_min, length_max, length_lang, displayCondition, avoid_repetition);
            }



            option_keyword = main_ques_options_key.optString("keyword");

            depend1 = main_ques_options_key.optString("dependents");

            if (main_ques_options_key.optString("messages") != null && main_ques_options_key.optString("messages").length() > 0) {

                option_messages = main_ques_options_key.optString("messages");


            } else {
                option_messages = "";
            }

            if (main_ques_options_key.optString("dependents") != null && main_ques_options_key.optString("dependents").length() > 0) {
                dependant = "true";
            } else {
                dependant = "false";
            }

            option_language = main_ques_options_key.optString("languages");

            if (option_language != null && option_language.length() > 0) {
                JSONObject object1 = null;
                try {
                    object1 = main_ques_options_key.getJSONObject("languages");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                attr16 = object1 != null ? object1.optString("en") : null;

                attr17 = object1 != null ? object1.optString("mr") : null;

            }

            if (main_ques_options_key.optString("action") != null && main_ques_options_key.optString("action").length() > 0) {

                JSONObject main_ques_action = main_ques_options_key.getJSONObject("action");

                if (main_ques_action.optJSONArray("question") != null) {
                    JSONArray jsonArray4 = main_ques_action.optJSONArray("question");

                    for (int p = 0; p < jsonArray4.length(); p++) {
                            JSONObject dependant_ques_key = jsonArray4.getJSONObject(p);

                        JSONObject depandant_ques_lang = dependant_ques_key.getJSONObject("languages");

                        if (dependant_ques_key.optString("messages") != null && dependant_ques_key.optString("messages").length() > 0) {
                            dependant_ques_messages = dependant_ques_key.optString("messages");
                        } else {
                            dependant_ques_messages = "";
                        }

                        if (dependant_ques_key.optJSONArray("options") != null && dependant_ques_key.optJSONArray("options").length() > 0) {
                            JSONArray jsonArray5 = dependant_ques_key.optJSONArray("options");

                            for (int l = 0; l < jsonArray5.length(); l++) {
                                JSONObject depandant_ques_option_key = jsonArray5.getJSONObject(l);

                                if (depandant_ques_option_key.optString("messages") != null && depandant_ques_option_key.optString("messages").length() > 0) {
                                    dependant_ques_messages = depandant_ques_option_key.optString("messages").toString();
                                } else {
                                    dependant_ques_messages = "";
                                }

//                                dbhelper.insertDependantQuestion(Form_id, setid, dependant_ques_key.optString("id"), depandant_ques_option_key.optString("keyword"), "", depandant_ques_option_key.optString("languages"), "", dependant_ques_messages);
                                saveQuestionOptions(Form_id, dependant_ques_key.optInt("id"), depandant_ques_option_key.optString("keyword"), depandant_ques_option_key.optString("languages"), dependant_ques_messages);

                                if (depandant_ques_option_key.optString("action") != null && depandant_ques_option_key.optString("action").length() > 0) {
                                    JSONObject depandant_ques_action_dependant_key = depandant_ques_option_key.getJSONObject("action");

                                    String option_keyword = depandant_ques_option_key.optString("keyword");

                                    actionDependantQuestions(depandant_ques_action_dependant_key, option_keyword, orientation);
                                }

                            }
                        }

//                        dbhelper.insertDependantquestionlist(option_keyword, dependant_ques_key.optString("id"),Form_id,dependant_ques_key.optString("set_id"), dependant_ques_key.optString("keyword"), dependant_ques_key.optString("answer_type"), ""+depandant_ques_lang,dependant_ques_messages,dependant_ques_key.optString("validation"),dependant_ques_key.optString("order_no"),orientation);
                        saveDependentQuestions(option_keyword, dependant_ques_key.optInt("id"), Form_id, dependant_ques_key.optString("keyword")
                                , dependant_ques_key.optString("answer_type"), "" + depandant_ques_lang, dependant_ques_messages
                                , dependant_ques_key.optString("validation"), orientation);
                    }
                }
            }

//            dbhelper.insertDependantQuestion(Form_id,setid, quesid, option_keyword, "", option_language,"",option_messages);
            saveQuestionOptions(Form_id, quesid, option_keyword, option_language, option_messages);

        }

        public void actionDependantQuestions(JSONObject depandant_ques_action_dependant_key, String optionKeyword, int orientation) throws JSONException {

            if (depandant_ques_action_dependant_key.optJSONArray("question") != null) {
                JSONArray jsonArray4 = depandant_ques_action_dependant_key.optJSONArray("question");

                for (int p = 0; p < jsonArray4.length(); p++) {
                    JSONObject dependant_ques_key = jsonArray4.getJSONObject(p);

                    JSONObject depandant_ques_lang = dependant_ques_key.getJSONObject("languages");

                    if (dependant_ques_key.optString("messages") != null && dependant_ques_key.optString("messages").length() > 0) {
                        dependant_ques_messages = dependant_ques_key.optString("messages");
                    } else {
                        dependant_ques_messages = "";
                    }


                    if (dependant_ques_key.optJSONArray("options") != null && dependant_ques_key.optJSONArray("options").length() > 0) {
                        JSONArray jsonArray5 = dependant_ques_key.optJSONArray("options");

                        for (int l = 0; l < jsonArray5.length(); l++) {
                            JSONObject dependant_ques_option_key = jsonArray5.getJSONObject(l);


                            if (dependant_ques_option_key.optString("messages") != null && dependant_ques_option_key.optString("messages").length() > 0) {
                                dependant_ques_messages = dependant_ques_option_key.optString("messages").toString();
                            } else {
                                dependant_ques_messages = "";
                            }

//                            dbhelper.insertDependantQuestion(Form_id, setid, dependant_ques_key.optString("id"), dependant_ques_option_key.optString("keyword"), "", dependant_ques_option_key.optString("languages"), "", dependant_ques_messages);

                            saveQuestionOptions(Form_id, dependant_ques_key.optInt("id"), dependant_ques_option_key.optString("keyword"),
                                    dependant_ques_option_key.optString("languages"), dependant_ques_messages);

                            if (dependant_ques_option_key.optString("action").toString() != null && dependant_ques_option_key.optString("action").toString().length() > 0)

                            {
                                JSONObject recursive_action_dependant_key = dependant_ques_option_key.getJSONObject("action");
                                String option_keyword = dependant_ques_option_key.optString("keyword");
                                actionDependantQuestions(recursive_action_dependant_key, option_keyword, orientation);
                            }
                        }
                    }

//                    dbhelper.insertDependantquestionlist(optionKeyword, dependant_ques_key.optString("id"),Form_id,dependant_ques_key.optString("set_id"), dependant_ques_key.optString("keyword")
//                            , dependant_ques_key.optString("answer_type"), ""+depandant_ques_lang,dependant_ques_messages
//                            ,dependant_ques_key.optString("validation"),dependant_ques_key.optString("order_no"),orientation);

                    saveDependentQuestions(optionKeyword, dependant_ques_key.optInt("id"), Form_id, dependant_ques_key.optString("keyword")
                            , dependant_ques_key.optString("answer_type"), "" + depandant_ques_lang, dependant_ques_messages
                            , dependant_ques_key.optString("validation"), orientation);
                }
            }
        }

    }

    class SaveRestoredDataAsyncTask extends AsyncTask<ArrayList<Object>, Integer, Void> {

        ProgressBar progressBar;
        ArrayList<beneficiaries> listRegistrations;
        ArrayList<BeneficiariesList> listVisits;
        private AlertDialog mProgressDialog;
        private int mProgress;

        SaveRestoredDataAsyncTask(ArrayList<beneficiaries> listRegistrations, ArrayList<BeneficiariesList> listVisits) {
            this.listRegistrations = listRegistrations;
            this.listVisits = listVisits;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View dialogView = inflater.inflate(R.layout.progress_dialog_layout, null);
            TextView textView = dialogView.findViewById(R.id.textView_label);
            progressBar = dialogView.findViewById(R.id.progressBar);
            textView.setText(R.string.saving_forms);
            progressBar.setIndeterminate(false);
            progressBar.setMax(listRegistrations.size() + listVisits.size());
            AlertDialog.Builder mAlertDialogBuilder = new AlertDialog.Builder(mContext);
            mAlertDialogBuilder.setView(dialogView);
            mAlertDialogBuilder.setCancelable(false);
            mProgressDialog = mAlertDialogBuilder.create();
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(ArrayList<Object>[] arrayLists) {

            deleteOldData();

            utility.getDatabase().beginTransaction();

            for (beneficiaries details :
                    listRegistrations) {
                saveRegistrations(details);
                publishProgress(++mProgress);
            }

            for (BeneficiariesList data :
                    listVisits) {
                saveVisits(data);
                publishProgress(++mProgress);
            }

            utility.getDatabase().setTransactionSuccessful();
            utility.getDatabase().endTransaction();

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mProgressDialog.dismiss();
        }

        void saveRegistrations(beneficiaries data) {
            ContentValues values = new ContentValues();
            values.put(RegistrationTable.COLUMN_UNIQUE_ID, data.getUniqueId());
            values.put(RegistrationTable.COLUMN_FIRST_NAME, data.getName());
            values.put(RegistrationTable.COLUMN_MOBILE_NO, data.getMobNo());
            values.put(RegistrationTable.COLUMN_ADDRESS, data.getAddress());
            values.put(RegistrationTable.COLUMN_DOB, data.getAge());
            values.put(RegistrationTable.COLUMN_EDUCATION, data.getEducation());
            values.put(RegistrationTable.COLUMN_MOTHER_ID, data.getMotherId());
            values.put(RegistrationTable.COLUMN_GENDER, data.getGender());
            values.put(RegistrationTable.COLUMN_REGISTRATION_STATUS, 1);
            values.put(RegistrationTable.COLUMN_SYNC_STATUS, 1);
            values.put(RegistrationTable.COLUMN_CREATED_ON, data.getCreatedOn());

            utility.getDatabase().insert(DatabaseContract.RegistrationTable.TABLE_NAME, null, values);
        }

        void saveVisits(BeneficiariesList data) {

            for (VisitsList list :
                    data.getVisitsList()) {
                ContentValues values = new ContentValues();
                values.put(DatabaseContract.FilledFormStatusTable.COLUMN_UNIQUE_ID, data.getUniqueId());
                values.put(DatabaseContract.FilledFormStatusTable.COLUMN_FORM_ID, list.getFormId());
                values.put(DatabaseContract.FilledFormStatusTable.COLUMN_FORM_COMPLETION_STATUS, 1);
                values.put(DatabaseContract.FilledFormStatusTable.COLUMN_FORM_SYNC_STATUS, 1);
                values.put(DatabaseContract.FilledFormStatusTable.COLUMN_CREATED_ON, list.getCreatedOn());

                int referenceId = (int) utility.getDatabase().insert(FilledFormStatusTable.TABLE_NAME, null, values);

                for (QuestionAnswer questionAnswer :
                        list.getQuestionAnswers()) {
                    saveQuestionAnswers(referenceId, data.getUniqueId(), list.getFormId(), questionAnswer);
                }

            }
        }

        void saveQuestionAnswers(int referenceId, String uniqueId, int formId, QuestionAnswer questionAnswer) {
            ContentValues values = new ContentValues();
            values.put(DatabaseContract.QuestionAnswerTable.COLUMN_REFERENCE_ID, referenceId);
            values.put(DatabaseContract.QuestionAnswerTable.COLUMN_UNIQUE_ID, uniqueId);
            values.put(DatabaseContract.QuestionAnswerTable.COLUMN_FORM_ID, formId);
            values.put(DatabaseContract.QuestionAnswerTable.COLUMN_QUESTION_KEYWORD, questionAnswer.getKeyword());
            values.put(DatabaseContract.QuestionAnswerTable.COLUMN_ANSWER_KEYWORD, questionAnswer.getAnswer());
            values.put(DatabaseContract.QuestionAnswerTable.COLUMN_CREATED_ON, questionAnswer.getCreatedOn());
            utility.getDatabase().insert(DatabaseContract.QuestionAnswerTable.TABLE_NAME, null, values);
        }

        void deleteOldData() {
            utility.getDatabase().execSQL("DROP TABLE IF EXISTS " + DatabaseContract.RegistrationTable.TABLE_NAME);
            utility.getDatabase().execSQL("DROP TABLE IF EXISTS " + DatabaseContract.FilledFormStatusTable.TABLE_NAME);
            utility.getDatabase().execSQL("DROP TABLE IF EXISTS " + DatabaseContract.QuestionAnswerTable.TABLE_NAME);

            utility.getDatabase().execSQL(DatabaseContract.RegistrationTable.CREATE_TABLE);
            utility.getDatabase().execSQL(DatabaseContract.FilledFormStatusTable.CREATE_TABLE);
            utility.getDatabase().execSQL(DatabaseContract.QuestionAnswerTable.CREATE_TABLE);
        }
    }

    @Override
    public void downloadAllRegistrationData(UserDetails request, OndownloadAllRegistrationData downloadData) {
        RemoteDataSource remoteDataSource = RemoteDataSource.getInstance();
        FetchAllRegisteredData service = remoteDataSource.fetchAllRegisteredData();
        service.downloadVisitsData(mContext, request, downloadData);
    }

    @Override
    public void saveAllRegisteredData(ArrayList<RegisteredData> listRegistrations) {
        new SaveAllRegistrationData(listRegistrations).execute();
    }

    class SaveAllRegistrationData extends AsyncTask<ArrayList<Object>, Integer, Void>
    {
        ProgressBar progressBar;
        ArrayList<RegisteredData> listRegistrations;
        private AlertDialog mProgressDialog;
        private int mProgress;

        public SaveAllRegistrationData(ArrayList<RegisteredData> listRegistration) {
            this.listRegistrations = listRegistration;
        }

        @Override
        protected void onPreExecute() {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View dialogView = inflater.inflate(R.layout.progress_dialog_layout, null);
            TextView textView = dialogView.findViewById(R.id.textView_label);
            progressBar = dialogView.findViewById(R.id.progressBar);
            textView.setText(R.string.saving_forms);
            progressBar.setIndeterminate(false);
            AlertDialog.Builder mAlertDialogBuilder = new AlertDialog.Builder(mContext);
            mAlertDialogBuilder.setView(dialogView);
            mAlertDialogBuilder.setCancelable(false);
            mProgressDialog = mAlertDialogBuilder.create();
            mProgressDialog.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            mProgressDialog.dismiss();
        }

        @Override
        protected Void doInBackground(ArrayList<Object>... arrayLists) {
            deleteOldRecords();
            utility.getDatabase().beginTransaction();
            for (RegisteredData details :
                    listRegistrations) {
                saveAllRegistrated(details);
                publishProgress(++mProgress);
            }
            utility.getDatabase().setTransactionSuccessful();
            utility.getDatabase().endTransaction();
            return null;
        }
    }

    void deleteOldRecords() {
        utility.getDatabase().execSQL("DROP TABLE IF EXISTS " + DatabaseContract.AllregistrationDetail.TABLE_NAME);
        utility.getDatabase().execSQL(DatabaseContract.AllregistrationDetail.CREATE_TABLE);
    }
    void saveAllRegistrated(RegisteredData data) {
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.AllregistrationDetail.COLUMN_UNIQUE_ID, data.getUnique_id());
        values.put(DatabaseContract.AllregistrationDetail.COLUMN_USER_ID, data.getUser_id());
        values.put(DatabaseContract.AllregistrationDetail.COLUMN_FORM_REGISTRATION_NAME, data.getName());
        utility.getDatabase().insert(DatabaseContract.AllregistrationDetail.TABLE_NAME, null, values);
    }
}
