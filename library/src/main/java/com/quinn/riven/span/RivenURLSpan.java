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
import android.widget.EditText;

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
        EditText editText = (EditText)widget;
        //选中链接的开头和结尾默认触发了点击事件，此处屏蔽掉
        int spanEnd = editText.getEditableText().getSpanEnd(this);
        int spanStart = editText.getEditableText().getSpanStart(this);
        int selectEnd = editText.getSelectionEnd();
        int selectStart = editText.getSelectionStart();
        Log.i(TAG, "spanStart = " + spanStart + " spanEnd = " + spanEnd + " selectStart = " + selectStart + " selectEnd = " + selectEnd);
        if(spanEnd == selectEnd) {
            return;
        }
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
