package com.inscripts.ins_armman.muw.utility;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.DisplayMetrics;
import android.view.View;

import com.inscripts.ins_armman.muw.R;
import com.inscripts.ins_armman.muw.database.DatabaseContract;
import com.inscripts.ins_armman.muw.database.DatabaseManager;

import java.io.ByteArrayOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import static android.provider.MediaStore.Video.VideoColumns.LANGUAGE;
import static com.inscripts.ins_armman.muw.utility.Constants.UNIQUE_MEMBER_ID_SEPERATOR;

/**
 * This class is used for storing shared Preference language,check internet connect and many other things also
 *
 * @author Vivek & Juilee  Created on 14/8/2019
 */

public class utility {

    public static final String COMMAN_PREF_NAME = "CommonPrefs";


    public static String getLanguagePreferance(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(COMMAN_PREF_NAME, Activity.MODE_PRIVATE);
        String language = prefs.getString(LANGUAGE, "");
        return language;
    }

    /**
     * change the locale for the app.
     *
     * @param context
     * @param locale
     */
    public static void setApplicationLocale(Context context, String locale) {
        try {
            setLocaleInPreference(locale, context);

            Resources res = context.getApplicationContext().getResources();
            // Change locale settings in the app.
            DisplayMetrics dm = res.getDisplayMetrics();
            android.content.res.Configuration conf = res.getConfiguration();
            String[] localeArray = locale.split("_");
            if (localeArray.length > 1) {
                conf.locale = new Locale(localeArray[0], localeArray[1]);
            } else {
                conf.locale = new Locale(locale);
            }
            res.updateConfiguration(conf, dm);
        } catch (Exception ex) {
        }
    }

    private static void setLocaleInPreference(String locale, Context context) {
        SharedPreferences prefs = context.getSharedPreferences(COMMAN_PREF_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(LANGUAGE, locale);
        editor.commit();
    }

    /**
     * @param view         View to animate
     * @param toVisibility Visibility at the end of animation
     * @param toAlpha      Alpha at the end of animation
     * @param duration     Animation duration in ms
     */
    public static void animateView(final View view, final int toVisibility, float toAlpha, int duration) {
        boolean show = toVisibility == View.VISIBLE;
        if (show) {
            view.setAlpha(0);
        }
        view.setVisibility(View.VISIBLE);
        view.animate()
                .setDuration(duration)
                .alpha(show ? toAlpha : 0)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        view.setVisibility(toVisibility);
                    }
                });
    }

    public static String getCurrentDateTime() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(new Date());
    }


    public static boolean hasInternetConnectivity(Context context) {
        boolean rc = false;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            try {
                NetworkInfo activeNetworkInfo = cm.getActiveNetworkInfo();
                if (activeNetworkInfo != null && activeNetworkInfo.isAvailable() && activeNetworkInfo.isConnected()) {
                    rc = true;
                }
            } catch (Exception e) {
            }
        }
        return rc;
    }

    public static ArrayList<String> getDeviceImeiNumber(Context context) {
        TelephonyInfo telephonyInfo = TelephonyInfo.getInstance(context);
        ArrayList<String> imeiArray = new ArrayList<>();
//        imeiArray.add("867375022176455");
        if (telephonyInfo.isDualSIM()) {
            imeiArray.add(telephonyInfo.getImsiSIM1());
            imeiArray.add(telephonyInfo.getImsiSIM2());
        } else {
            imeiArray.add(telephonyInfo.getImsiSIM1());
        }
        return imeiArray;
    }

    public static void setBadgeCount(Context context, LayerDrawable icon, int count) {

        BadgeDrawable badge;

        // Reuse drawable if possible
        Drawable reuse = icon.findDrawableByLayerId(
                R.id.ic_badge);
        if (reuse != null && reuse instanceof BadgeDrawable) {
            badge = (BadgeDrawable) reuse;
        } else {
            badge = new BadgeDrawable(context);
        }

        badge.setCount(count);
        icon.mutate();
        icon.setDrawableByLayerId(R.id.ic_badge, badge);
    }

    public static SQLiteDatabase getDatabase() {
        return DatabaseManager.getInstance().openDatabase();
    }


    /**
     * This is the method which generates the Hash.
     *
     * @param s = JsonResponse
     * @return hash in string format
     */
    public static String mdFive(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String generateUniqueId() {
        return System.currentTimeMillis() + UNIQUE_MEMBER_ID_SEPERATOR + getUserId();
    }

    public static String getUserId() {
        Cursor cursor = getDatabase().rawQuery("SELECT " + DatabaseContract.LoginTable.COLUMN_USER_ID
                + " FROM " + DatabaseContract.LoginTable.TABLE_NAME, null);

        if (cursor.moveToFirst())
            return cursor.getString(cursor.getColumnIndex(DatabaseContract.LoginTable.COLUMN_USER_ID));
        else return "0";
    }

    public static byte[] getImageByteArray(Bitmap bitmap) {
        final int COMPRESSION_QUALITY = 100;
        ByteArrayOutputStream byteArrayBitmapStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, COMPRESSION_QUALITY,
                byteArrayBitmapStream);
        return byteArrayBitmapStream.toByteArray();
    }

    public static String getAppVersionName(Context context) {
        PackageInfo pInfo = null;
        try {
            pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return pInfo.versionName;
    }

}
