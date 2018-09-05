package com.inscripts.ins_armman.npdsf.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.inscripts.ins_armman.npdsf.data.model.IncompleteFiledForm;
import com.inscripts.ins_armman.npdsf.utility.utility;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.inscripts.ins_armman.npdsf.database.DatabaseContract.DATABASE_NAME;
import static com.inscripts.ins_armman.npdsf.database.DatabaseContract.DATABASE_VERSION;
import static com.inscripts.ins_armman.npdsf.database.DatabaseContract.DB_LOCATION;
import static com.inscripts.ins_armman.npdsf.database.DatabaseContract.DependentQuestionsTable;
import static com.inscripts.ins_armman.npdsf.database.DatabaseContract.FilledFormStatusTable;
import static com.inscripts.ins_armman.npdsf.database.DatabaseContract.FormDetailsTable;
import static com.inscripts.ins_armman.npdsf.database.DatabaseContract.HashTable;
import static com.inscripts.ins_armman.npdsf.database.DatabaseContract.LoginTable;
import static com.inscripts.ins_armman.npdsf.database.DatabaseContract.MainQuestionsTable;
import static com.inscripts.ins_armman.npdsf.database.DatabaseContract.QuestionAnswerTable;
import static com.inscripts.ins_armman.npdsf.database.DatabaseContract.QuestionOptionsTable;
import static com.inscripts.ins_armman.npdsf.database.DatabaseContract.RegistrationTable;
import static com.inscripts.ins_armman.npdsf.database.DatabaseContract.ValidationsTable;
import static com.inscripts.ins_armman.npdsf.database.DatabaseContract.currentFormStatus;

/**
 * This class is used to create and update local database
 *
 * @author Aniket & Vivek  Created on 15/8/2018
 */

public class DBHelper extends SQLiteOpenHelper {
    private static final String TAG = DBHelper.class.getSimpleName();


    public DBHelper(Context context) {
        super(context, DB_LOCATION
                + File.separator + DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(LoginTable.CREATE_TABLE);
        db.execSQL(FormDetailsTable.CREATE_TABLE);
        db.execSQL(RegistrationTable.CREATE_TABLE);
        db.execSQL(MainQuestionsTable.CREATE_TABLE);
        db.execSQL(HashTable.CREATE_TABLE);
        db.execSQL(QuestionOptionsTable.CREATE_TABLE);
        db.execSQL(DependentQuestionsTable.CREATE_TABLE);
        db.execSQL(QuestionAnswerTable.CREATE_TABLE);
        db.execSQL(ValidationsTable.CREATE_TABLE);
        db.execSQL(FilledFormStatusTable.CREATE_TABLE);
        db.execSQL(currentFormStatus.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public List<IncompleteFiledForm> getIncompleteFormListList() {
        List<IncompleteFiledForm> list = new ArrayList<>();


        Cursor cursor = utility.getDatabase().rawQuery("SELECT * FROM \n" +
                "\t(SELECT current.unique_id,current.form_id,reg.name FROM current_form_status AS current LEFT JOIN registration AS reg on current.unique_id = reg.unique_id)\n" +
                "\tWHERE form_id != 5", null);

        while (cursor.moveToNext()) {
            list.add(new IncompleteFiledForm(cursor.getString(cursor.getColumnIndex("unique_id")), cursor.getString(cursor.getColumnIndex("name")),
                    cursor.getString(cursor.getColumnIndex("form_id"))));
        }
        return list;
    }


    public String fetchCount() {
        String status = null;

        String query = "SELECT COUNT(*) AS remaining FROM filled_forms_status WHERE form_sync_status = 0 AND form_completion_status = 1";

        Cursor cursor = utility.getDatabase().rawQuery(query, null);

        if (cursor.moveToFirst() && cursor != null) {
            status = cursor.getString(cursor.getColumnIndex("remaining"));
            cursor.close();
        } else {
            status = "0";
        }
        return status;
    }

    public Cursor fetchUserDetails() {
        String query = "SELECT name,phone_no FROM " + LoginTable.TABLE_NAME;
        Cursor cursor = utility.getDatabase().rawQuery(query, null);
        return cursor;
    }

}
