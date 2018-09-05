package com.inscripts.ins_armman.npdsf.database;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.inscripts.ins_armman.npdsf.utility.Constants;

/**
 * @author Aniket & Vivek  Created on 4/9/2018
 */

public class GenericCursorLoader extends BaseCursorLoader {
    private final LocalDataSource.QueryType mQueryType;
    private SQLiteDatabase mSQLiteDatabase;
    private Bundle mBundle;

    public GenericCursorLoader(Context context, SQLiteDatabase sQLiteDatabase, Bundle bundle, LocalDataSource.QueryType queryType) {
        super(context);
        this.mSQLiteDatabase = sQLiteDatabase;
        this.mBundle = bundle;
        this.mQueryType = queryType;
    }

    @Override
    public Cursor loadInBackground() {
        LocalDataSource localDataSource = new LocalDataSource(mSQLiteDatabase);
        Cursor cursor = null;

        switch (mQueryType) {
            case FUNCTION:
                cursor = readFromFunctionQuery(localDataSource);
                break;
            case RAW:
                cursor = localDataSource.rawQuery(mBundle.getString(Constants.RAW_QUERY), null);
                break;
            default:
                cursor = readFromFunctionQuery(localDataSource);
                break;
        }


        return cursor;
    }

    private Cursor readFromFunctionQuery(LocalDataSource flwLocalDataSource) {
        Cursor cursor = flwLocalDataSource.read(
                mBundle.getBoolean(Constants.QUERY_ARGS_DISTINCT),
                mBundle.getString(Constants.QUERY_ARGS_TABLE_NAME),
                mBundle.getStringArray(Constants.QUERY_ARGS_PROJECTION),
                mBundle.getString(Constants.QUERY_ARGS_SELECTION),
                mBundle.getStringArray(Constants.QUERY_ARGS_SELECTION_ARGS),
                mBundle.getString(Constants.QUERY_ARGS_GROUP_BY),
                null,
                mBundle.getString(Constants.QUERY_ARGS_ORDER_BY),
                mBundle.getString(Constants.QUERY_ARGS_LIMIT)
        );
        return cursor;
    }
}
