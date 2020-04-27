package com.inscripts.ins_armman.muw.utility;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.Log;

import com.inscripts.ins_armman.muw.R;

/**
 * BadgeDrawable is used to draw of display the notification of remaining to sync entry
 *
 * @author Vivek & Juilee  Created on 14/8/2019
 */

public class BadgeDrawable extends Drawable {

    private float mTextSize;
    private Paint mBadgePaint;
    private Paint mTextPaint;
    private Rect mTxtRect = new Rect();

    private String mCount = "";
    private boolean mWillDraw = false;
    int screen_width,screen_height;
    Resources res;
    Context mContext;
    float width,height,radius,centerX,centerY;

    public BadgeDrawable(Context context) {
        mTextSize = context.getResources().getDimension(R.dimen.badge_text_size);
        mContext = context;
        mBadgePaint = new Paint();
        mBadgePaint.setColor(Color.RED);
        mBadgePaint.setAntiAlias(true);
        mBadgePaint.setStyle(Paint.Style.FILL);

        mTextPaint = new Paint();
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setTypeface(Typeface.DEFAULT_BOLD);
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    public void draw(Canvas canvas) {
        if (!mWillDraw) {
            return;
        }
        Rect bounds = getBounds();
//		float width = bounds.right - bounds.left;
//		float height = bounds.bottom - bounds.top;
        res = mContext.getApplicationContext().getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        screen_width = dm.widthPixels;
        screen_height = dm.heightPixels;
        Log.d("ScreenSize","Height" + screen_height + " width " + screen_width);
        int w = 600;
        int h = 976;
        if (screen_width == 600 && screen_height == 976) {
            //this if is for old tabs
            //this is blue circle size
            width = (float) 45.5;
            height = (float) 45.5;
            radius = ((Math.min(width, height) / 2) - 1) / 2;
            //this is blue icon position
            centerX = (float) 25.5;
            centerY = (float) 6.5;
        }
        else{
            //this else is for the new tabs
            //this is blue icon size
            width = (float) 90;
            height = (float) 90;
            radius = (Math.min(width, height) / 2) / 2;
            //this is blue icon position
            centerX = (float) 65;
            centerY = (float) 20;
        }
        //Log.d(TAG,"Width : " + width + " Height : " +height);

        // Position the badge in the top-right quadrant of the icon.
//		float radius = ((Math.min(width, height) / 2) - 1) / 2;
//		float centerX = width - radius - 1;
//		float centerY = radius + 1;

       /* float radius = ((Math.min(width, height) / 2) - 1) / 2;
        float centerX = (float) 25.5;
        float centerY = (float) 6.5;*/

        //Log.d(TAG,"Radius : " + radius + " centerX : " + centerX + " centerY : " +centerY);

        // Draw badge circle.
        canvas.drawCircle(centerX, centerY, radius, mBadgePaint);

        // Draw badge textViewCount text inside the circle.
        mTextPaint.getTextBounds(mCount, 0, mCount.length(), mTxtRect);
        float textHeight = mTxtRect.bottom - mTxtRect.top;
        float textY = centerY + (textHeight / 2f);

        /**
         * following if else is to adjust the count position in the red circle
         */
        if(screen_height == 976 && screen_width == 600){
            canvas.drawText(mCount, 25f, 11.2f, mTextPaint);
        }
        else
            canvas.drawText(mCount, 65f, 32f, mTextPaint);

        float x = centerX - 0.0003f;

        Log.d("FRO", "textHeight : " + textHeight + " centerX : " + x + " centerY : " + textY);

    }

    /*
    Sets the textViewCount (i.e notifications) to display.
     */
    public void setCount(int count) {
        mCount = Integer.toString(count);

        // Only draw a badge if there are notifications.
        mWillDraw = count > 0;
        invalidateSelf();
    }

    @Override
    public void setAlpha(int alpha) {
        // do nothing
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        // do nothing
    }

    @Override
    public int getOpacity() {
        return PixelFormat.UNKNOWN;
    }
}