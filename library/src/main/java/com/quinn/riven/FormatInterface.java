package com.quinn.riven;

import android.graphics.Bitmap;

/**
 * Created by quinn on 11/30/16.
 */

public interface FormatInterface {

    public void bold(int start, int end, boolean format);

    public boolean cantainBold(int start, int end);

    public void italic(int start, int end, boolean format);

    public boolean containItalic(int start, int end);

    public void underLine(int start, int end, boolean format);

    public boolean containUnderLine(int start, int end);

    public void strikeThrough(int start, int end, boolean format);

    public boolean containStrikeThrough(int start, int end);

    public void foreColor(int color, int start, int end, boolean format);

    public boolean containForeColor(int start, int end);

    public void addCheckBox(int start, boolean check);

    public void addPhoto(int start, Bitmap bitmap);

    public void bullet(int start, int end, boolean format);

    public void clearBullet(int start, int end);

    public boolean containBullet(int start, int end);

    public void number(int start, int end, boolean format);

    public boolean containNumber(int start, int end);

    public void quote(int start, int end, boolean format);

    public void clearQuote(int start, int end);

    public boolean containQuote(int start, int end);

    public void link(String url, int start);



}
