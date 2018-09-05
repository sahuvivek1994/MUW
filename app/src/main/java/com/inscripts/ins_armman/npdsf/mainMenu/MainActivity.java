package com.inscripts.ins_armman.npdsf.mainMenu;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.inscripts.ins_armman.npdsf.R;
import com.inscripts.ins_armman.npdsf.incompleteForm.IncompleteForm;
import com.inscripts.ins_armman.npdsf.registration.EnrollmentQuestions;
import com.inscripts.ins_armman.npdsf.settingActivity.Settings;
import com.inscripts.ins_armman.npdsf.userProfile.userProfile;
import com.inscripts.ins_armman.npdsf.utility.utility;

/**
 * Main screen of the project which contain different menu of different functionality
 * @author Aniket & Vivek  Created on 15/8/2018
 */

public class MainActivity extends AppCompatActivity implements IMainView, View.OnClickListener {
    MainPresenter mainPresenter;
    Context ctx = this;
    private LayerDrawable mSyncDrawable;
    private CardView registration;
    private CardView incompleteForm;
    private CardView completeForm;
    private CardView userProfile;
    private AlertDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainPresenter = new MainPresenter();
        mainPresenter.attachView(this);
        init();
    }

    public void init() {
        registration = (CardView) findViewById(R.id.card1);
        registration.setOnClickListener(this);
        incompleteForm = (CardView) findViewById(R.id.card2);
        incompleteForm.setOnClickListener(this);
        completeForm = (CardView) findViewById(R.id.card3);
        completeForm.setOnClickListener(this);
        userProfile = (CardView) findViewById(R.id.card4);
        userProfile.setOnClickListener(this);
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


            case R.id.card1:
                Intent intent1 = new Intent(MainActivity.this, EnrollmentQuestions.class);
                startActivity(intent1);
                finish();
                break;

            case R.id.card2:
                Intent intent2 = new Intent(MainActivity.this, IncompleteForm.class);
                startActivity(intent2);
//                Intent intent2 = new Intent(MainActivity.this, displayForm.class);
//                intent2.putExtra(UNIQUE_ID, "1535524404388.10");
//                intent2.putExtra(FORM_ID, "5");
//                startActivity(intent2);
//                finish();
                break;

            case R.id.card3:
                Toast.makeText(this, "In Progress", Toast.LENGTH_SHORT).show();
                //  Intent intent = new Intent(MainActivity.this, HouseholdActivity.class);
                //  startActivity(intent);

                break;

            case R.id.card4:
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