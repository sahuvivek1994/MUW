package com.inscripts.ins_armman.muw.mainMenu;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.inscripts.ins_armman.muw.R;
import com.inscripts.ins_armman.muw.login.Login;


public class SplashScreen extends AppCompatActivity {
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

    handler=new Handler();
    handler.postDelayed(new Runnable() {
        @Override
        public void run() {
            Intent intent=new Intent(SplashScreen.this, Login.class);
            startActivity(intent);
            finish();
        }
    },1000);

}
}
