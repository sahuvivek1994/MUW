package com.inscripts.ins_armman.npdsf.settingActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.inscripts.ins_armman.npdsf.R;
import com.inscripts.ins_armman.npdsf.mainMenu.MainActivity;

/**
 * Setting this class is used to deal with setting fragment is used for easy code and less coding is required then normal coding standard
 *
 * @author Aniket & Vivek  Created on 21/8/2018
 */

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        getFragmentManager().beginTransaction().replace(R.id.frame_settings_menu, new MainPreferenceFragment()).commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(Settings.this,MainActivity.class));
    }
}


