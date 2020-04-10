package com.inscripts.ins_armman.muw.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.inscripts.ins_armman.muw.utility.utility;

import java.io.File;

import static com.inscripts.ins_armman.muw.database.DatabaseContract.DATABASE_NAME;
import static com.inscripts.ins_armman.muw.database.DatabaseContract.DATABASE_VERSION;
import static com.inscripts.ins_armman.muw.database.DatabaseContract.DB_LOCATION;
import static com.inscripts.ins_armman.muw.database.DatabaseContract.DependentQuestionsTable;
import static com.inscripts.ins_armman.muw.database.DatabaseContract.FilledFormStatusTable;
import static com.inscripts.ins_armman.muw.database.DatabaseContract.FormDetailsTable;
import static com.inscripts.ins_armman.muw.database.DatabaseContract.HashTable;
import static com.inscripts.ins_armman.muw.database.DatabaseContract.LoginTable;
import static com.inscripts.ins_armman.muw.database.DatabaseContract.MainQuestionsTable;
import static com.inscripts.ins_armman.muw.database.DatabaseContract.QuestionAnswerTable;
import static com.inscripts.ins_armman.muw.database.DatabaseContract.QuestionOptionsTable;
import static com.inscripts.ins_armman.muw.database.DatabaseContract.RegistrationTable;
import static com.inscripts.ins_armman.muw.database.DatabaseContract.ValidationsTable;
import static com.inscripts.ins_armman.muw.database.DatabaseContract.AllregistrationDetail;

/**
 * This class is used to create and update local database
 *
 * @author Vivek & Juilee  Created on 14/8/2019
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
        db.execSQL(AllregistrationDetail.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            upgradeVersion2(db);
        }
        if (oldVersion < 3) {
            upgradeVersion3(db);
        }
    }

    private void upgradeVersion2(SQLiteDatabase db) {
        db.execSQL("DROP TABLE " + DatabaseContract.CurrentFormStatus.TABLE_NAME);
    }

    private void upgradeVersion3(SQLiteDatabase db)
    {
        db.execSQL(AllregistrationDetail.CREATE_TABLE);
    }

    public Cursor getIncompleteFormListList() {

     /*String str= "select c.unique_id,c.form_id,r.name,c.form_completion_status " +
             "from filled_forms_status as c join registration as r  on c.unique_id=r.unique_id " +
             "and form_id>=2 and form_id<=10 and form_completion_status=0 " +
             "group by c.unique_id ";
        return utility.getDatabase().rawQuery(str,null);
     */return utility.getDatabase().rawQuery("SELECT * FROM " +
                "(SELECT current.unique_id,current.form_id,reg.name, current.form_completion_status " +
                "FROM filled_forms_status AS current " +
                " JOIN registration AS reg on current.unique_id = reg.unique_id "+
                " AND current.unique_id NOT IN (SELECT unique_id FROM filled_forms_status WHERE form_id = 10 AND form_completion_status = 1))" +
                " GROUP BY unique_id", null);
    }


    public String fetchCount() {
        String status = null;

        String query = "SELECT COUNT(*) AS remaining FROM filled_forms_status WHERE form_sync_status = 0 AND form_completion_status = 1";

        Cursor cursor = utility.getDatabase().rawQuery(query, null);

        if (cursor != null && cursor.moveToFirst()) {
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

    public Cursor getcompleteFormListList() {

        return utility.getDatabase().rawQuery("SELECT name,unique_id from registration WHERE unique_id IN (SELECT unique_id FROM filled_forms_status WHERE form_id = 10 and form_completion_status = 1 )", null);
    }

    public Cursor getChildIdFromMotherId(String motherId) {
        return utility.getDatabase().rawQuery("SELECT name,unique_id FROM " + RegistrationTable.TABLE_NAME + " WHERE mother_id ='" + motherId + "'", null);
    }

    public Cursor getuniqueIdFormId(String uniqueId) {
        return utility.getDatabase().rawQuery("SELECT max(form_id) as form_id FROM " + FilledFormStatusTable.TABLE_NAME + " WHERE unique_id = '" + uniqueId + "' AND form_completion_status = 1", null);
    }
    /*public Cursor getCompleteFormDetails(String unique_id, int form_id) {
        return utility.getDatabase().rawQuery("SELECT main_questions.question_label," +
                "question_options.option_label,question_answers.unique_id " +
                "FROM question_answers" +
                " JOIN main_questions" +
                " ON question_answers.question_keyword=main_questions.keyword " +
                "JOIN question_options" +
                " ON question_answers.answer_keyword=question_options.keyword " +
                "WHERE question_answers.unique_id='"+unique_id+"' " +
                "and question_answers.form_id="+form_id,null);
    }*/

    /**
     *form 6 contains question label but does'nt contain answer label so only
     main_questions and question_answers involved in query and not question_option
     * @param unique_id=child unique_id
     * @param form_id=child form_id
     * @return
     */
    public Cursor getForm6Details(String unique_id,int form_id){
        return utility.getDatabase().rawQuery("SELECT main_questions.question_label,question_answers.answer_keyword,question_answers.unique_id " +
                "FROM question_answers" +
                " JOIN main_questions ON question_answers.question_keyword=main_questions.keyword " +
                "WHERE question_answers.unique_id='"+unique_id+"' and question_answers.form_id="+form_id,null);
    }
    public Cursor getFormsList(){
        return utility.getDatabase().rawQuery("select visit_name,form_id from form_details group by(form_id) order by cast(form_id as int) asc",null);
    }
    public Cursor getIncompleteFormList(String unique_id){
        return utility.getDatabase().rawQuery("select filled_forms_status.form_id,visit_name" +
                " from filled_forms_status join form_details on" +
                " filled_forms_status.form_id=form_details.form_id" +
                " where unique_id='"+unique_id+"' and form_completion_status=1" +
                " group by(filled_forms_status.form_id)",null);
    }
    public Cursor getCompleteFormDetails(String unique_id, int Form_id)
    {
        return utility.getDatabase().rawQuery( "select r.unique_id ,mq.question_label,qo.option_label,qa.answer_keyword,qa.question_keyword" +
                " from question_answers as qa" +
                " left join registration as r on r.unique_id=qa.unique_id" +
                " left join main_questions as mq on mq.keyword = qa.question_keyword" +
                " left join question_options as qo on qo.keyword = qa.answer_keyword" +
                " where qa.unique_id='"+unique_id+"'" +
                " and qa.form_id="+Form_id+" group by(qa.question_keyword)",null);
    }
}
