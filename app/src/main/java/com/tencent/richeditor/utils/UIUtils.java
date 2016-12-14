package com.tencent.richeditor.utils;

import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by quinn on 12/1/16.
 */

public class UIUtils {

    public static int screen_width = -1;
    public static int screen_height = -1;

    public static int getColorWrapper(Context context, int id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return context.getColor(id);
        } else {
            return context.getResources().getColor(id);
        }
    }


    public static int getScreenWidth(Context context) {
        if(screen_width == -1) {
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            screen_width = size.x;
        }
        return screen_width;
    }

    public static int getScreenHeight(Context context) {
        if(screen_height == -1) {
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            screen_height = size.x;
        }
        return screen_height;
    }

    public static float getScreenRatio(Context context) {
        float width = getScreenWidth(context);
        float height = getScreenHeight(context);
        return width / height;
    }
}
