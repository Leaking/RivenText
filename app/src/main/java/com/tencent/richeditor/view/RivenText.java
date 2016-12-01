package com.tencent.richeditor.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ImageSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.tencent.richeditor.span.CheckBoxSpan;
import com.tencent.richeditor.span.ColorCricleBulletSpan;

/**
 * Created by quinn on 11/30/16.
 */

public class RivenText extends android.support.v7.widget.AppCompatEditText implements FormatInterface {

    public static final String TAG = "RivenText";

    private SelectChangeListener selectChangeListener;

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
        Log.i(TAG, "bold start = " + start + " end = " + end);
        StyleSpan boldSpan = new StyleSpan(Typeface.BOLD);
        if(format) {
            getEditableText().setSpan(boldSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        } else {
            //此处可能会拿到可start和end交叉的span，如果直接remove掉，则会影响选中区域外的格式，所以需要补全区域外的格式
            StyleSpan[] spans = getEditableText().getSpans(start, end, StyleSpan.class);
            for (StyleSpan span : spans) {
                //先拿出start和end的值，如果remove之后再拿值，则拿到是-1
                int spanStart = getEditableText().getSpanStart(span);
                int spanEnd = getEditableText().getSpanEnd(span);
                if (span.getStyle() == Typeface.BOLD) {
                    getEditableText().removeSpan(span);
                }
                if(spanStart < start) {
                    bold(spanStart, start, true);
                }
                if(spanEnd > end) {
                    bold(end, spanEnd, true);
                }
            }
        }
        onSelectionChanged(start, end);
    }

    @Override
    public boolean cantainBold(int start, int end) {
        StyleSpan[] spans = getEditableText().getSpans(start, end, StyleSpan.class);
        if(spans == null) {
            Log.i(TAG, "cantainBold return null");
            return false;
        } else {
            Log.i(TAG, "cantainBold lenght = " + spans.length);
        }
        for (StyleSpan span : spans) {
            Log.i(TAG, "containBold style = " + span.getStyle());
            if (span.getStyle() == Typeface.BOLD) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void italic(int start, int end, boolean format) {
        StyleSpan boldSpan = new StyleSpan(Typeface.ITALIC);
        if(format) {
            getEditableText().setSpan(boldSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        } else {
            StyleSpan[] spans = getEditableText().getSpans(start, end, StyleSpan.class);
            for (StyleSpan span : spans) {
                int spanStart = getEditableText().getSpanStart(span);
                int spanEnd = getEditableText().getSpanEnd(span);
                if (span.getStyle() == Typeface.ITALIC) {
                    getEditableText().removeSpan(span);
                }
                if(spanStart < start) {
                    italic(spanStart, start, true);
                }
                if(spanEnd > end) {
                    italic(end, spanEnd, true);
                }
            }
        }
        onSelectionChanged(start, end);
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
        if(format) {
            getEditableText().setSpan(new UnderlineSpan(), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        } else {
            UnderlineSpan[] spans = getEditableText().getSpans(start, end, UnderlineSpan.class);
            if(spans != null && spans.length > 0) {
                for (UnderlineSpan span : spans) {
                    int spanStart = getEditableText().getSpanStart(span);
                    int spanEnd = getEditableText().getSpanEnd(span);
                    getEditableText().removeSpan(span);
                    if(spanStart < start) {
                        underLine(spanStart, start, true);
                    }
                    if(spanEnd > end) {
                        underLine(end, spanEnd, true);
                    }
                }
            }
        }
        onSelectionChanged(start, end);
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
        if(format) {
            getEditableText().setSpan(new StrikethroughSpan(), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        } else {
            StrikethroughSpan[] spans = getEditableText().getSpans(start, end, StrikethroughSpan.class);
            if(spans != null && spans.length > 0) {
                for (StrikethroughSpan span : spans) {
                    int spanStart = getEditableText().getSpanStart(span);
                    int spanEnd = getEditableText().getSpanEnd(span);
                    getEditableText().removeSpan(span);
                    if(spanStart < start) {
                        strikeThrough(spanStart, start, true);
                    }
                    if(spanEnd > end) {
                        strikeThrough(end, spanEnd, true);
                    }
                }
            }
        }
        onSelectionChanged(start, end);
    }

    @Override
    public boolean containStrikeThrough(int start, int end) {
        StrikethroughSpan[] spans = getEditableText().getSpans(start, end, StrikethroughSpan.class);
        if(spans != null && spans.length > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void addCheckBox(int start, boolean check) {
        final SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder("checkboxspan");
        final CheckBoxSpan span = new CheckBoxSpan(this.getContext(), check);
        spannableStringBuilder.setSpan(span, 0, spannableStringBuilder.length() , Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableStringBuilder.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Log.i(TAG, "onClick  Checkbox span start = " + spannableStringBuilder.getSpanStart(span));
                CheckBoxSpan[] checkBoxSpens = getEditableText().getSpans(0, getEditableText().length(), CheckBoxSpan.class);
                if(checkBoxSpens != null && checkBoxSpens.length > 0) {
                    for(CheckBoxSpan checkBoxSpan : checkBoxSpens) {
                        // 直接Span.getStart拿到的值不是最新的
                        int spanStart = getEditableText().getSpanStart(checkBoxSpan);
                        int spanEnd = getEditableText().getSpanEnd(checkBoxSpan);
                        if(span == checkBoxSpan) {
                            Log.i(TAG, "You click me");
                            getEditableText().removeSpan(span);
                            getEditableText().replace(spanStart,spanEnd, "");
                            addCheckBox(spanStart, !span.isChecked());
                            break;
                        }
                    }
                }
                Toast.makeText(RivenText.this.getContext(), "onClick  Checkbox", Toast.LENGTH_SHORT).show();
            }
        }, 0, spannableStringBuilder.length() - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        setMovementMethod(LinkMovementMethod.getInstance());
        getEditableText().insert(start, spannableStringBuilder);
    }

    @Override
    public void addPhoto(int start, Bitmap bitmap) {
        final SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder("imageSpan");
        ImageSpan imageSpan = new ImageSpan(getContext(), bitmap);
        spannableStringBuilder.setSpan(imageSpan, 0, spannableStringBuilder.length() , Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        setMovementMethod(LinkMovementMethod.getInstance());
        getEditableText().insert(start, spannableStringBuilder);
    }

    @Override
    public void bullet(int start, int end, boolean format) {
        getEditableText().setSpan(new ColorCricleBulletSpan(), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    @Override
    public boolean containBullet(int start, int end) {
        ColorCricleBulletSpan[] spans = getEditableText().getSpans(start, end, ColorCricleBulletSpan.class);
        if(spans != null && spans.length > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void number(int start, int end, boolean format) {

    }

    @Override
    public boolean containNumber(int start, int end) {
        return false;
    }


    @Override
    protected void onSelectionChanged(int selStart, int selEnd) {
        super.onSelectionChanged(selStart, selEnd);
        Log.i(TAG, "onSelectionChanged start = " + selStart + " end = " + selEnd);
        if(selectChangeListener != null) {
            selectChangeListener.select(selStart, selEnd);
        }
    }

    public interface SelectChangeListener {
        public void select(int start, int end);
    }

    public void setSelectChangeListener(SelectChangeListener selectChangeListener) {
        this.selectChangeListener = selectChangeListener;
    }



}
