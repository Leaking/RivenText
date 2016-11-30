package com.tencent.richeditor;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.BulletSpan;
import android.text.style.ClickableSpan;
import android.text.style.ImageSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.tencent.richeditor.span.CheckBoxSpan;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    private EditText editText;
    private Button format;
    private int start;
    private int end;
    private ImageButton boldBtn;
    private ImageButton italicBtn;
    private ImageButton underlineBtn;
    private ImageButton strikethroughBtn;
    private ImageButton imageBtn;
    private ImageButton checkBoxBtn;
    private ImageButton colorBtn;
    private ImageButton quoteBtn;
    private ImageButton clearBtn;
    private ImageButton bulletBtn;
    private ImageButton numericBtn;
    private ImageButton linkBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = (EditText) findViewById(R.id.edittext);

        boldBtn = (ImageButton) findViewById(R.id.bold);
        italicBtn = (ImageButton) findViewById(R.id.italic);
        underlineBtn = (ImageButton) findViewById(R.id.underline);
        strikethroughBtn = (ImageButton) findViewById(R.id.strikethrough);
        imageBtn = (ImageButton) findViewById(R.id.photo);
        colorBtn = (ImageButton) findViewById(R.id.color);
        quoteBtn = (ImageButton) findViewById(R.id.quote);
        clearBtn = (ImageButton) findViewById(R.id.clear);
        bulletBtn = (ImageButton) findViewById(R.id.bullet);
        numericBtn = (ImageButton) findViewById(R.id.number);
        linkBtn = (ImageButton) findViewById(R.id.link);
        checkBoxBtn = (ImageButton) findViewById(R.id.checkbox);

        boldBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateSelectArea();
                bold();
            }
        });
        italicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateSelectArea();
                italic();
            }
        });
        underlineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateSelectArea();
                underline();
            }
        });
        strikethroughBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateSelectArea();
                strikethrough();
            }
        });
        checkBoxBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateSelectArea();
                addCheckBox(start, true);
            }
        });
        imageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateSelectArea();
                addPhoto();
            }
        });
        colorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateSelectArea();
                color();
            }
        });
        quoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateSelectArea();
                quote();
            }
        });
        bulletBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateSelectArea();
                bullet();
            }
        });
        numericBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateSelectArea();
                number();
            }
        });
        linkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateSelectArea();
                link();
            }
        });
        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateSelectArea();
                clear();
            }
        });




    }

    private void bold() {
        editText.getEditableText().setSpan(new StyleSpan(Typeface.BOLD), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    private void italic() {
        editText.getEditableText().setSpan(new StyleSpan(Typeface.ITALIC), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    private void underline() {
        editText.getEditableText().setSpan(new UnderlineSpan(), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    private void strikethrough() {
        editText.getEditableText().setSpan(new StrikethroughSpan(), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    private void addPhoto() {
        final SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder("image");
        final ImageSpan span = new ImageSpan(this, R.mipmap.ic_launcher);
        spannableStringBuilder.setSpan(span, 0, spannableStringBuilder.length() , Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableStringBuilder.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Log.i(TAG, "onClick  Photo");
                Toast.makeText(getApplicationContext(), "onClick  Photo", Toast.LENGTH_SHORT).show();
            }
        }, 0, spannableStringBuilder.length() - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        editText.setMovementMethod(LinkMovementMethod.getInstance());
        editText.getEditableText().insert(start, spannableStringBuilder);
    }

    private void addCheckBox(int start, boolean check) {
        final SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder("checkboxspan");
        final CheckBoxSpan span = new CheckBoxSpan(this, check);
        spannableStringBuilder.setSpan(span, 0, spannableStringBuilder.length() , Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableStringBuilder.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Log.i(TAG, "onClick  Checkbox span start = " + spannableStringBuilder.getSpanStart(span));
                CheckBoxSpan[] checkBoxSpens = editText.getEditableText().getSpans(0, editText.getEditableText().length(), CheckBoxSpan.class);
                if(checkBoxSpens != null && checkBoxSpens.length > 0) {
                    for(CheckBoxSpan checkBoxSpan : checkBoxSpens) {
                        // 直接Span.getStart拿到的值不是最新的
                        int spanStart = editText.getEditableText().getSpanStart(checkBoxSpan);
                        int spanEnd = editText.getEditableText().getSpanEnd(checkBoxSpan);
                        Log.i(TAG, "checkBoxSpan start " +  spanStart + " end = " + editText.getEditableText().getSpanEnd(checkBoxSpan)
                         + " focus = " + editText.getSelectionStart());
                        if(span == checkBoxSpan) {
                            Log.i(TAG, "You clcik me");
                            editText.getEditableText().removeSpan(span);
                            editText.getEditableText().replace(spanStart,spanEnd, "");
                            addCheckBox(spanStart, !span.isChecked());
                            break;
                        }
                    }
                }
                Toast.makeText(getApplicationContext(), "onClick  Checkbox", Toast.LENGTH_SHORT).show();
            }
        }, 0, spannableStringBuilder.length() - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        editText.setMovementMethod(LinkMovementMethod.getInstance());
        editText.getEditableText().insert(start, spannableStringBuilder);
    }


    private void color() {

    }

    private void quote() {

    }
    private void bullet() {
        editText.getEditableText().setSpan(new BulletSpan(), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    private void number() {

    }

    private void link() {

    }

    private void clear() {

    }

    /**
     * {@link Html#fromHtml(String)} 自动将html 转化为span
     */
    private void htmlToSpan() {
        Spanned s = Html.fromHtml("<font color=\"red\">Red Char</font>");
        editText.setText(s);
    }

    private void updateSelectArea() {
        start = editText.getSelectionStart();
        end = editText.getSelectionEnd();
        Log.i(TAG, "Select start = " + start + " end = " + end);
    }


}
