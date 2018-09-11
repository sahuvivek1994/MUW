package com.inscripts.ins_armman.npdsf.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.inscripts.ins_armman.npdsf.utility.utility;

import java.io.File;

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
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2){
            upgradeVersion2(db);
        }
    }

    private void upgradeVersion2(SQLiteDatabase db) {
        db.execSQL("DROP TABLE " + DatabaseContract.CurrentFormStatus.TABLE_NAME);
    }

    public Cursor getIncompleteFormListList() {

        return utility.getDatabase().rawQuery("SELECT * FROM \n" +
                "\t(SELECT current.unique_id,current.form_id,reg.name, current.form_completion_status \n" +
                "\tFROM filled_forms_status AS current \n" +
                " JOIN registration AS reg on current.unique_id = reg.unique_id AND (reg.mother_id is null OR reg.mother_id = '') \n" +
                " AND current.unique_id NOT IN (SELECT unique_id FROM filled_forms_status WHERE form_id = 10 AND form_completion_status = 1))\n" +
                " GROUP BY unique_id", null);
    }


    public String fetchCount() {
        String status = null;

        String query = "SELECT COUNT(*) AS remaining FROM filled_forms_status WHERE form_sync_status = 0 AND form_completion_status = 1";

        Cursor cursor = utility.getDatabase().rawQuery(query, null);

        if ( cursor != null && cursor.moveToFirst()) {
            status = cursor.getString(cursor.getColumnIndex("remaining"));
            cursor.close();
        } else {
            status = "0";
        }
        return status;
    }

    public Cursor fetchUserDetails() {
        String query = "SELECT name,phone_no FROM " + LoginTable.TABLE_NAME;
        return utility.getDatabase().rawQuery(query, null);
    }

}
