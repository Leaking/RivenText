package com.quinn.riven.span;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.text.style.ImageSpan;
import android.util.Log;

import com.quinn.riven.R;


/**
 * Created by quinn on 11/30/16.
 */

public class CheckBoxSpan extends ImageSpan {

    public static final String TAG = "CheckBoxSpan";

    private final static int SELECT_STATE_RSID = R.drawable.check_box;
    private final static int UNSELECT_STATE_RSID = R.drawable.check_box_outline;
    private boolean checked = true;

    private Context mContext;
    private int mResourceId;


    public CheckBoxSpan(Context context, Bitmap b) {
        super(context, b);
        this.mContext = context;
    }

    public CheckBoxSpan(Context context, @DrawableRes int resourceId) {
        super(context, resourceId);
        this.mContext = context;
        this.mResourceId = SELECT_STATE_RSID;
    }

    public CheckBoxSpan(Context context, boolean check) {
        super(context, check ? SELECT_STATE_RSID : UNSELECT_STATE_RSID);
        this.mResourceId = check ? SELECT_STATE_RSID : UNSELECT_STATE_RSID;
        this.checked = check;
        this.mContext = context;
    }



    public void toggle() {
        if(checked) {
            mResourceId = UNSELECT_STATE_RSID;
        } else {
            mResourceId = SELECT_STATE_RSID;
        }
        checked = !checked;

    }

    public boolean isChecked() {
        return checked;
    }

    @Override
    public Drawable getDrawable() {
        Log.i(TAG,  "getDrawable mResourceId = " + mResourceId);
        Drawable drawable = null;
        try {
            drawable = mContext.getDrawable(mResourceId);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                    drawable.getIntrinsicHeight());
        } catch (Exception e) {
            Log.e("sms", "Unable to find resource: " + mResourceId);
        }

        return drawable;
    }
}
