package com.quinn.sample;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.quinn.riven.RivenText;
import com.quinn.sample.view.SelectableImageButton;

import java.io.FileNotFoundException;
import java.io.IOException;

import static com.quinn.sample.R.id.photo;


public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    public static final int CHOOSE_PICTURE = 100;

    private RivenText rivenText;
    private Button format;
    private int start;
    private int end;
    private SelectableImageButton boldBtn;
    private SelectableImageButton italicBtn;
    private SelectableImageButton underlineBtn;
    private SelectableImageButton strikethroughBtn;
    private ImageButton imageBtn;
    private ImageButton checkBoxBtn;
    private ImageButton colorBtn;
    private SelectableImageButton quoteBtn;
    private ImageButton clearBtn;
    private SelectableImageButton bulletBtn;
    private ImageButton numericBtn;
    private ImageButton linkBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rivenText = (RivenText) findViewById(R.id.edittext);

        boldBtn = (SelectableImageButton) findViewById(R.id.bold);
        italicBtn = (SelectableImageButton) findViewById(R.id.italic);
        underlineBtn = (SelectableImageButton) findViewById(R.id.underline);
        strikethroughBtn = (SelectableImageButton) findViewById(R.id.strikethrough);
        imageBtn = (ImageButton) findViewById(photo);
        colorBtn = (ImageButton) findViewById(R.id.color);
        quoteBtn = (SelectableImageButton) findViewById(R.id.quote);
        clearBtn = (ImageButton) findViewById(R.id.clear);
        bulletBtn = (SelectableImageButton) findViewById(R.id.bullet);
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

        rivenText.setSelectChangeListener(new RivenText.SelectChangeListener() {
            @Override
            public void select(int start, int end) {
                updateIconState(start, end);
            }
        });



    }

    private void bold() {
        rivenText.bold(start, end, !boldBtn.isCheck());
    }

    private void italic() {
        rivenText.italic(start, end, !italicBtn.isCheck());
    }

    private void underline() {
        rivenText.underLine(start, end, !underlineBtn.isCheck());
    }

    private void strikethrough() {
        rivenText.strikeThrough(start, end, !strikethroughBtn.isCheck());
    }

    private void addPhoto() {
        Intent openAlbumIntent = new Intent(Intent.ACTION_GET_CONTENT);
        openAlbumIntent.setType("image/*");
        startActivityForResult(openAlbumIntent, CHOOSE_PICTURE);
    }

    private void addCheckBox(int start, boolean check) {
        rivenText.addCheckBox(start, check);
    }


    private void color() {

    }

    private void quote() {
        rivenText.quote(start, end, !quoteBtn.isCheck());

    }
    private void bullet() {
        rivenText.bullet(start, end, !bulletBtn.isCheck());
    }

    private void number() {

    }

    private void link() {

    }

    private void clear() {

    }

    private void updateIconState(int start, int end) {
        Log.i(TAG, "updateIconState start = " + start + " end = " + end);
        boldBtn.setCheck(rivenText.cantainBold(start, end));
        italicBtn.setCheck(rivenText.containItalic(start, end));
        underlineBtn.setCheck(rivenText.containUnderLine(start, end));
        strikethroughBtn.setCheck(rivenText.containStrikeThrough(start, end));
        bulletBtn.setCheck(rivenText.containBullet(start, end));
        quoteBtn.setCheck(rivenText.containQuote(start, end));
    }

    /**
     * {@link Html#fromHtml(String)} 自动将html 转化为span
     */
    private void htmlToSpan() {
        Spanned s = Html.fromHtml("<font color=\"red\">Red Char</font>");
        rivenText.setText(s);
    }

    private void updateSelectArea() {
        start = rivenText.getSelectionStart();
        end = rivenText.getSelectionEnd();
        Log.i(TAG, "Select start = " + start + " end = " + end);
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        if (resultCode != RESULT_OK)
            return;
        switch (requestCode) {
            case CHOOSE_PICTURE:
                Uri uri = data.getData();
                try {
                    Bitmap bitmap;
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),
                            uri);
                    rivenText.addPhoto(rivenText.getSelectionStart(), bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                break;
            default:
                break;
        }

    }

}
