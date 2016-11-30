package com.tencent.richeditor.view;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.AttributeSet;

/**
 * Created by quinn on 11/30/16.
 */

public class RivenText extends android.support.v7.widget.AppCompatEditText implements FormatInterface {


    public RivenText(Context context) {
        super(context);
    }

    public RivenText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RivenText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public void bold(int start, int end, boolean format) {
        StyleSpan boldSpan = new StyleSpan(Typeface.BOLD);
        if(format) {
            getEditableText().setSpan(boldSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        } else {
            StyleSpan[] spans = getEditableText().getSpans(start, end, StyleSpan.class);
            for (StyleSpan span : spans) {
                if (span.getStyle() == Typeface.BOLD) {
                    getEditableText().removeSpan(span);
                }
            }
        }
    }

    @Override
    public boolean cantainBold(int start, int end) {
        StyleSpan[] spans = getEditableText().getSpans(start, end, StyleSpan.class);
        for (StyleSpan span : spans) {
            if (span.getStyle() == Typeface.BOLD) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void italic(int start, int end, boolean format) {

    }

    @Override
    public boolean containItalic(int start, int end) {
        StyleSpan[] spans = getEditableText().getSpans(start, end, StyleSpan.class);
        for (StyleSpan span : spans) {
            if (span.getStyle() == Typeface.ITALIC) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void underLine(int start, int end, boolean format) {

    }

    @Override
    public boolean containUnderLine(int start, int end) {
        UnderlineSpan[] spans = getEditableText().getSpans(start, end, UnderlineSpan.class);
        if(spans != null && spans.length > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void strikeThrough(int start, int end, boolean format) {

    }

    @Override
    public boolean containStrikeThrough(int start, int end) {

    }

    @Override
    public void addCheckBox() {

    }
}
