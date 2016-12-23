package com.quinn.riven;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.text.Layout;
import android.text.Selection;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.quinn.riven.span.CheckBoxSpan;
import com.quinn.riven.span.ColorCricleBulletSpan;
import com.quinn.riven.span.ColorQuoteSpan;
import com.quinn.riven.span.RivenURLSpan;
import com.quinn.riven.utils.UIUtils;

import java.util.Arrays;

/**
 * Created by quinn on 11/30/16.
 */

public class RivenText extends android.support.v7.widget.AppCompatEditText implements FormatInterface {

    public static final String TAG = "RivenText";

    private static final String LINE_FEED = "\n";

    private static final String EMPTY_SPACE = " ";

    private int lastLineCount = 1;

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
        if(start == end) {
            return false;
        }
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
        if(start == end) {
            return false;
        }
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
        if(start == end) {
            return false;
        }
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
        if(start == end) {
            return false;
        }
        StrikethroughSpan[] spans = getEditableText().getSpans(start, end, StrikethroughSpan.class);
        if(spans != null && spans.length > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void foreColor(int color, int start, int end, boolean format) {
        if(format) {
            getEditableText().setSpan(new ForegroundColorSpan(color), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        } else {
            ForegroundColorSpan[] spans = getEditableText().getSpans(start, end, ForegroundColorSpan.class);
            if(spans != null && spans.length > 0) {
                for (ForegroundColorSpan span : spans) {
                    int spanStart = getEditableText().getSpanStart(span);
                    int spanEnd = getEditableText().getSpanEnd(span);
                    getEditableText().removeSpan(span);
                    if(spanStart < start) {
                        foreColor(color, spanStart, start, true);
                    }
                    if(spanEnd > end) {
                        foreColor(color, end, spanEnd, true);
                    }
                }
            }
        }
        onSelectionChanged(start, end);
    }

    @Override
    public boolean containForeColor(int start, int end) {
        if(start == end) {
            return false;
        }
        ForegroundColorSpan[] spans = getEditableText().getSpans(start, end, ForegroundColorSpan.class);
        if(spans != null && spans.length > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void addCheckBox(int start, boolean check) {
        final SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder("        ");
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
        bitmap = resizeBitmap(bitmap);
        final SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder("imageSpan");
        ImageSpan imageSpan = new ImageSpan(getContext(), bitmap);
        spannableStringBuilder.setSpan(imageSpan, 0, spannableStringBuilder.length() , Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        setMovementMethod(LinkMovementMethod.getInstance());
        //为了优化光标控制的体验，前后插入一个空行
        insertLineFeed();
        getEditableText().insert(start + 1, spannableStringBuilder);
        insertLineFeed();
    }

    @Override
    public void bullet(int start, int end, boolean format) {
        if(format) {
            if(containQuote(start, end)) {
                clearQuote(start, end);
            }
            String content = content();
            Log.i(TAG, "Try to bullet start = " + start + " end = " + end);
            int nextLineFeedOfStart = content.indexOf(this.LINE_FEED, start);
            int nextLineFeedOfEnd = content.indexOf(this.LINE_FEED, end);
            Log.i(TAG, "nextLineFeedOfStart = " + nextLineFeedOfStart + " nextLineFeedOfEnd = " + nextLineFeedOfEnd);
            //设置当前行
            if (nextLineFeedOfStart == nextLineFeedOfEnd) {
                Log.i(TAG, "Bullet the same line");
                int lineFeedBeforeStart = findLineStart(start);
                int lineFeedAfterEnd = findNextLineFeed(end);
                start = lineFeedBeforeStart;
                end = lineFeedAfterEnd;
                Log.i(TAG, "Line start = " + start + " end = " + end);
            } else {
                Log.i(TAG, "Bullet splited line");
                bullet(start, nextLineFeedOfStart - 1, true);
                bullet(nextLineFeedOfStart + 1, end, true);
                return;
            }
            getEditableText().setSpan(new ColorCricleBulletSpan(), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        } else {
            if(start == end && start == getEditableText().length()) {
                start = start - 1;
                end = end - 1;
            }
            clearBullet(start, end);
        }
        onSelectionChanged(start, end);
    }

    @Override
    public void clearBullet(int start, int end) {
        Log.i(TAG, "Try to clear bullet start = " + start + " end = " + end);
        start = findLineStart(start);
        ColorCricleBulletSpan[] spans = getEditableText().getSpans(start, end, ColorCricleBulletSpan.class);
        for(ColorCricleBulletSpan span: spans) {
            int spanStart = getEditableText().getSpanStart(span);
            int spanEnd = getEditableText().getSpanEnd(span);
            Log.i(TAG, "spanStart = " + spanStart + " spanEnd = " + spanEnd);
            getEditableText().removeSpan(span);
        }
    }


    @Override
    public boolean containBullet(int start, int end) {
        start = findLineStart(start);
        if(start == end && end == getEditableText().length()) {
            end = end - 1;
        }
        ColorCricleBulletSpan[] spans = getEditableText().getSpans(start, end, ColorCricleBulletSpan.class);
        if(spans != null && spans.length > 0) {
            Log.i(TAG, "containBullet true");
            return true;
        } else {
            Log.i(TAG, "containBullet false");
            return false;
        }
    }

    @Override
    public void number(int start, int end, boolean format) {
        Log.i(TAG, "getCurrentCursorLine = " + getCurrentCursorLine(this));
    }

    @Override
    public boolean containNumber(int start, int end) {
        return false;
    }

    @Override
    public void quote(int start, int end, boolean format) {
        if(format) {
            if(containBullet(start, end)) {
                clearBullet(start, end);
            }
            String content = content();
            Log.i(TAG, "Try to quote start = " + start + " end = " + end);
            int nextLineFeedOfStart = content.indexOf(this.LINE_FEED, start);
            int nextLineFeedOfEnd = content.indexOf(this.LINE_FEED, end);
            Log.i(TAG, "nextLineFeedOfStart = " + nextLineFeedOfStart + " nextLineFeedOfEnd = " + nextLineFeedOfEnd);
            //设置当前行
            if (nextLineFeedOfStart == nextLineFeedOfEnd) {
                Log.i(TAG, "quote the same line");
                int lineFeedBeforeStart = findLineStart(start);
                int lineFeedAfterEnd = findNextLineFeed(end);
                start = lineFeedBeforeStart;
                end = lineFeedAfterEnd;
                Log.i(TAG, "Line start = " + start + " end = " + end);
            } else {
                Log.i(TAG, "quote splited line");
                quote(start, nextLineFeedOfStart - 1, true);
                quote(nextLineFeedOfStart + 1, end, true);
                return;
            }
            getEditableText().setSpan(new ColorQuoteSpan(UIUtils.getColorWrapper(getContext(), R.color.colorPrimary)), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        } else {
            if(start == end && start == getEditableText().length()) {
                start = start - 1;
                end = end - 1;
            }
            clearQuote(start, end);
        }
        onSelectionChanged(start, end);
    }

    @Override
    public void clearQuote(int start, int end) {
        Log.i(TAG, "Try to clear quote start = " + start + " end = " + end);
        start = findLineStart(start);
        ColorQuoteSpan[] spans = getEditableText().getSpans(start, end, ColorQuoteSpan.class);
        for(ColorQuoteSpan span: spans) {
            int spanStart = getEditableText().getSpanStart(span);
            int spanEnd = getEditableText().getSpanEnd(span);
            Log.i(TAG, "spanStart = " + spanStart + " spanEnd = " + spanEnd);
            getEditableText().removeSpan(span);
        }
    }

    @Override
    public boolean containQuote(int start, int end) {
        start = findLineStart(start);
        if(start == end && end == getEditableText().length()) {
            end = end - 1;
        }
        ColorQuoteSpan[] spans = getEditableText().getSpans(start, end, ColorQuoteSpan.class);
        if(spans != null && spans.length > 0) {
            Log.i(TAG, "containQuote true");
            return true;
        } else {
            Log.i(TAG, "containQuote false");
            return false;
        }
    }

    @Override
    public void link(String url, int start) {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(url);
        RivenURLSpan urlSpan = new RivenURLSpan(url);
        spannableStringBuilder.setSpan(urlSpan, 0, spannableStringBuilder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        setMovementMethod(LinkMovementMethod.getInstance());
        getEditableText().insert(start, spannableStringBuilder);
        insertEmptySpace();
    }

    @Override
    protected void onSelectionChanged(int selStart, int selEnd) {
        super.onSelectionChanged(selStart, selEnd);
        Log.i(TAG, "onSelectionChanged start = " + selStart + " end = " + selEnd + " lenght = " + getEditableText().toString().length());
        if(selectChangeListener != null && selStart >= 0 && selEnd >= 0) {
            selectChangeListener.select(selStart, selEnd);
        }
    }

    public interface SelectChangeListener {
        public void select(int start, int end);
    }

    public void setSelectChangeListener(SelectChangeListener selectChangeListener) {
        this.selectChangeListener = selectChangeListener;
    }

    private Bitmap resizeBitmap(Bitmap bitmap) {
        float bmpRatio = (float)bitmap.getWidth() / (float)bitmap.getHeight();
        int screenWidth = UIUtils.getScreenWidth(getContext());
        if(bitmap.getWidth() > screenWidth) {
            int scaledWidth  = screenWidth;
            int scaledHeight = (int)((float)screenWidth / bmpRatio);
            bitmap = Bitmap.createScaledBitmap(bitmap, scaledWidth , scaledHeight, false);
        }
        return bitmap;
    }

    private String content() {
        return getEditableText().toString();
    }

    private void printColorCricleBulletSpan() {
        ColorCricleBulletSpan[] spans = getEditableText().getSpans(0, getEditableText().length(), ColorCricleBulletSpan.class);
        for(ColorCricleBulletSpan span: spans) {
            int start = getEditableText().getSpanStart(span);
            int end = getEditableText().getSpanEnd(span);
            Log.i(TAG, "start = " + start + " end = " + end);
        }
        Log.i(TAG, "split = " + Arrays.toString(getEditableText().toString().split(LINE_FEED)));
        int length = getEditableText().toString().length();
        Log.i(TAG, "lenght = " + length);
        Log.i(TAG, "last one = " + getEditableText().toString().substring(length));
    }


    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        Log.i(TAG, "start = " + start + " lengthBefore = " + lengthBefore + " lengthAfter = " + lengthAfter);
//        if(containBullet(start + lengthAfter, start + lengthAfter + 1)) {
//            boolean isLineStart = start == 0 || charBefore(start).equals(LINE_FEED);
//            Log.i(TAG, "isLineStart = " + isLineStart);
//            if(isLineStart && !last().equals(EMPTY_SPACE)) {
//                clearBullet(start + lengthAfter, start + lengthAfter);
//                bullet(start, start, true);
//                return;
//            }
//        }
        int currLineCount = getLineCount();
        Log.i(TAG, "onTextChanged line currlinecount = " + currLineCount + " lastLineCount = " + lastLineCount + " start = " + start);
        if(currLineCount > lastLineCount) {
            int lastLineStart = findLineStart(start);
            if(containBullet(lastLineStart, lastLineStart)) {
                if(!lastInput().equals(LINE_FEED)) {
                    resetCurrBullet(start);
                } else {
                    //回车换行自动补上列表节点
//                    Log.i(TAG, "Input line feed");
//                    insertEmptySpaceInSelection();
//                    bullet(getSelectionEnd(), getSelectionEnd(), true);
//                    setSelection(getSelectionEnd() - 1);
                }
            }
            if(containQuote(lastLineStart, lastLineStart)) {
                resetCurrQuote(start);
            }
        } else {

        }
        lastLineCount = currLineCount;
    }



    private void resetCurrBullet(int where) {
        Log.i(TAG, "resetCurrBullet");
        int lineStart = findLineStart(where);
        clearBullet(lineStart, lineStart);
        bullet(lineStart, lineStart, true);
    }

    private void resetCurrQuote(int where) {
        Log.i(TAG, "resetCurrBullet");
        int lineStart = findLineStart(where);
        clearQuote(lineStart, lineStart);
        quote(lineStart, lineStart, true);
    }

    private int findLineStart(int where) {
        Log.i(TAG, "findLineStart " + where);
        String partContent = content().substring(0, where);
        if(TextUtils.isEmpty(partContent)) {
            return 0;
        }
        int index = partContent.lastIndexOf(LINE_FEED);
        index = index + 1;
        Log.i(TAG, "findLineStart index = " + index);
        return index;
    }

    private int findNextLineFeed(int where) {
        int length = content().length();
        if(length == 0) {
            return 0;
        }
        int index = content().substring(where).indexOf(LINE_FEED);
        if (index == -1) {
            index = content().length();
        } else {
            index += where;
        }
        return index;
    }

    private String last(){
        if(content().isEmpty()) {
            return null;
        } else {
            return content().substring(content().length() - 1);
        }
    }

    private String lastInput(){
        if(content().isEmpty()) {
            return null;
        } else {
            return content().charAt(getSelectionEnd() - 1) + "";
        }
    }

    private String charBefore(int index) {
        return content().charAt(index - 1) + "";
    }

    private void insertLineFeed() {
        getEditableText().insert(content().length(), LINE_FEED);
    }

    private void insertEmptySpace() {
        getEditableText().insert(content().length(), EMPTY_SPACE);
    }

    private void insertEmptySpaceInSelection() {
        getEditableText().insert(getSelectionEnd(), EMPTY_SPACE);
    }

    private int getCurrentCursorLine(EditText editText)
    {
        int selectionStart = Selection.getSelectionStart(editText.getText());
        Layout layout = editText.getLayout();

        if (!(selectionStart == -1)) {
            return layout.getLineForOffset(selectionStart);
        }

        return -1;
    }
}
