package com.inscripts.ins_armman.muw.mainMenu;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.inscripts.ins_armman.muw.R;
import com.inscripts.ins_armman.muw.completedForm.completedForm;
import com.inscripts.ins_armman.muw.incompleteForm.IncompleteForm;
import com.inscripts.ins_armman.muw.registration.EnrollmentQuestions;
import com.inscripts.ins_armman.muw.settingActivity.Settings;
import com.inscripts.ins_armman.muw.userProfile.userProfile;
import com.inscripts.ins_armman.muw.utility.utility;

import java.util.ArrayList;

/**
 * Main screen of the project which contain different menu of different functionality
 * @author Aniket & Vivek  Created on 15/8/2018
 */

public class MainActivity extends AppCompatActivity implements IMainView, View.OnClickListener {
    MainPresenter mainPresenter;
    Context ctx = this;
    TextView textTotalReg, textTotalIncomplete;
    int complete=0,incomplete=0;
    ArrayList<Integer> totalCounts=new ArrayList<>();
    private LayerDrawable mSyncDrawable;
    private ConstraintLayout registration;
    private ConstraintLayout incompleteForm;
    private ConstraintLayout completeForm;
    private ConstraintLayout userProfile;
    private AlertDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainPresenter = new MainPresenter();
        mainPresenter.attachView(this);
        totalCounts= mainPresenter.getCounts();
        complete=totalCounts.get(0);
        incomplete=totalCounts.get(1);
        init();
    }

    public void init() {
        registration = (ConstraintLayout) findViewById(R.id.layout1);
        registration.setOnClickListener(this);
        incompleteForm = (ConstraintLayout) findViewById(R.id.layout2);
        incompleteForm.setOnClickListener(this);
        completeForm = (ConstraintLayout) findViewById(R.id.layout3);
        completeForm.setOnClickListener(this);
        userProfile = (ConstraintLayout) findViewById(R.id.layout4);
        userProfile.setOnClickListener(this);
        textTotalIncomplete=findViewById(R.id.textIncompleteCount);
        textTotalReg=findViewById(R.id.textcompleteCount);
        textTotalIncomplete.setText(String.valueOf(incomplete));
        textTotalReg.setText(String.valueOf(complete));

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainPresenter.detch();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_activity, menu);
        MenuItem item = menu.findItem(R.id.action_sync);
        mSyncDrawable = (LayerDrawable) item.getIcon();
        utility.setBadgeCount(this, mSyncDrawable, 0);
        mainPresenter.fetchUnsentFormsCount();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                startActivity(new Intent(MainActivity.this, Settings.class));
                finish();
                return true;
            case R.id.action_sync:
                // mainPresenter.fetchTempRegistration();
                mainPresenter.fetchRegistrationData();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.layout1:
                Intent intent1 = new Intent(MainActivity.this, EnrollmentQuestions.class);
                startActivity(intent1);
                finish();
                break;

            case R.id.layout2:
                Intent intent2 = new Intent(MainActivity.this, IncompleteForm.class);
                startActivity(intent2);
                break;

            case R.id.layout3:
                 Intent intent3 = new Intent(MainActivity.this, completedForm.class);
                  startActivity(intent3);
                  break;

            case R.id.layout4:
                Intent intent = new Intent(MainActivity.this, userProfile.class);
                startActivity(intent);
                break;


        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        mainPresenter.fetchUnsentFormsCount();

    }


    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void setUnsentFormsCount(int count) {
        if (mSyncDrawable != null) utility.setBadgeCount(this, mSyncDrawable, count);
    }

    @Override
    public void showProgressBar(String label) {
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.progress_dialog_layout, null);
        TextView textView = dialogView.findViewById(R.id.textView_label);
        textView.setText(label);
        AlertDialog.Builder mAlertDialogBuilder = new AlertDialog.Builder(this);
        mAlertDialogBuilder.setView(dialogView);
        mAlertDialogBuilder.setCancelable(false);
        mProgressDialog = mAlertDialogBuilder.create();
        mProgressDialog.show();
    }

    @Override
    public void hideProgressBar() {
        {
            if (mProgressDialog != null) mProgressDialog.dismiss();
        }
    }

    @Override
    public void showSnackBar(String message) {
        Snackbar snackbar = Snackbar
                .make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

    @Override
    public void onBackPressed() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(ctx);
        builder
                .setTitle(MainActivity.this.getString(R.string.back_form))
                .setMessage(MainActivity.this.getString(R.string.back_form_message))
                .setIcon(R.mipmap.ic_exitalert)
                .setPositiveButton(MainActivity.this.getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton(MainActivity.this.getString(R.string.no), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //finish();

                    }
                }).show();

    }
}