package com.quinn.riven.span;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Parcel;
import android.support.annotation.ColorInt;
import android.text.Layout;
import android.text.style.QuoteSpan;

/**
 * Created by quinn on 12/21/16.
 */

public class ColorQuoteSpan extends QuoteSpan{
    private static final int STRIPE_WIDTH = 8;
    private static final int GAP_WIDTH = 15;

    private final int mColor;

    public ColorQuoteSpan() {
        super();
        mColor = 0xff0000ff;
    }

    public ColorQuoteSpan(@ColorInt int color) {
        super();
        mColor = color;
    }

    public ColorQuoteSpan(Parcel src) {
        mColor = src.readInt();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        writeToParcelInternal(dest, flags);
    }

    /** @hide */
    public void writeToParcelInternal(Parcel dest, int flags) {
        dest.writeInt(mColor);
    }

    @ColorInt
    public int getColor() {
        return mColor;
    }

    public int getLeadingMargin(boolean first) {
        return STRIPE_WIDTH + GAP_WIDTH;
    }

    public void drawLeadingMargin(Canvas c, Paint p, int x, int dir,
                                  int top, int baseline, int bottom,
                                  CharSequence text, int start, int end,
                                  boolean first, Layout layout) {
        Paint.Style style = p.getStyle();
        int color = p.getColor();

        p.setStyle(Paint.Style.FILL);
        p.setColor(mColor);

        c.drawRect(x, top, x + dir * STRIPE_WIDTH, bottom, p);

        p.setStyle(style);
        p.setColor(color);
    }
}
