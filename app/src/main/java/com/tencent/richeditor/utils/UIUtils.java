package com.tencent.richeditor.utils;

import android.content.Context;
import android.os.Build;

/**
 * Created by quinn on 12/1/16.
 */

public class UIUtils {

    public static int getColorWrapper(Context context, int id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return context.getColor(id);
        } else {
            //noinspection deprecation
            return context.getResources().getColor(id);
        }
    }

}
