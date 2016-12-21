package com.quinn.riven.span;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Parcel;
import android.provider.Browser;
import android.text.style.URLSpan;
import android.util.Log;
import android.view.View;

/**
 * Created by quinn on 12/21/16.
 */

public class RivenURLSpan extends URLSpan {

    public static final String TAG = "RivenURLSpan";

    public RivenURLSpan(String url) {
        super(url);
    }

    public RivenURLSpan(Parcel src) {
        super(src);
    }

    @Override
    public void onClick(View widget) {
        Log.i(TAG, "onClick");
        Uri uri = Uri.parse(getURL());
        Context context = widget.getContext();
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.putExtra(Browser.EXTRA_APPLICATION_ID, context.getPackageName());
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Log.w("URLSpan", "Actvity was not found for intent, " + intent.toString());
        }
    }
}
