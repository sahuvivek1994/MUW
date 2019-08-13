package com.inscripts.ins_armman.muw.login;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.inscripts.ins_armman.muw.R;
import com.inscripts.ins_armman.muw.mainMenu.MainActivity;
import com.inscripts.ins_armman.muw.utility.utility;

/**
 * Login process will be follow and store data from api
 *
 * @author Aniket & Vivek  Created on 15/8/2018
 */

public class Login extends AppCompatActivity implements ILoginview {

    LoginPresenter loginPresenter;
    View progressOverlay;
    private EditText mEditTextUserName;
    private EditText mEditTextPassword;
    private TextInputLayout mTextInputLayoutUsername;
    private TextInputLayout mTextInputLayoutPassword;
    private Button mButtonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);

        String applicationLanguage = utility.getLanguagePreferance(getApplicationContext());
        if (applicationLanguage.isEmpty()) {
            utility.setApplicationLocale(getApplicationContext(), "en");
        } else {
            utility.setApplicationLocale(getApplicationContext(), applicationLanguage);
        }

        loginPresenter = new LoginPresenter();
        loginPresenter.attachView(this);
        init();
    }

    public void init() {
        mEditTextUserName = (EditText) findViewById(R.id.edittext_username);
        mEditTextPassword = (EditText) findViewById(R.id.edittext_pass);
        mTextInputLayoutUsername = (TextInputLayout) findViewById(R.id.textinputlayout_username);
        mTextInputLayoutPassword = (TextInputLayout) findViewById(R.id.textinputlayout_password);
        mButtonLogin = (Button) findViewById(R.id.button_login);
        progressOverlay = findViewById(R.id.progress_overlay);


        mEditTextPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_DONE)
                    mButtonLogin.performClick();
                return false;
            }
        });

        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = mEditTextUserName.getText().toString().trim();
                String password = mEditTextPassword.getText().toString().trim();

                loginPresenter.validateCredentials(userName, password);
            }
        });
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void setUsernameError() {
        mTextInputLayoutUsername.setError(getString(R.string.enter_username));
    }

    @Override
    public void setPasswordError() {
        mTextInputLayoutPassword.setError(getString(R.string.enter_password));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loginPresenter.detch();
    }

    @Override
    public void resetErrorMsg() {
        mTextInputLayoutPassword.setError(null);
        mTextInputLayoutUsername.setError(null);
    }

    @Override
    public void showDialog(String title, String message) {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(getContext(), android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(getContext());
        }
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    @Override
    public void showProgressBar() {
        utility.animateView(progressOverlay, View.VISIBLE, 0.4f, 200);
    }

    @Override
    public void hideProgressBar() {
        utility.animateView(progressOverlay, View.GONE, 0.4f, 200);
    }

    @Override
    public void openHomeActivity() {
        startActivity(new Intent(Login.this, MainActivity.class));
        finish();
    }

    @Override
    public void setAuthenticationFailedError() {
        mEditTextPassword.setText("");
        mTextInputLayoutPassword.setError(getString(R.string.authentication_error_msg));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && getContext() != null) {
            if (loginPresenter.checkPermissions())
                loginPresenter.checkIfUserAlreadyLoggedIn();
        } else
            loginPresenter.checkIfUserAlreadyLoggedIn();
    }
}
