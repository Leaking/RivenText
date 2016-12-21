package com.quinn.sample.view;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.quinn.sample.R;

/**
 * Created by quinn on 11/29/16.
 */

public class SelectableImageButton extends android.support.v7.widget.AppCompatImageButton {

    private boolean selected = false;

    public SelectableImageButton(Context context) {
        super(context);
    }

    public SelectableImageButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SelectableImageButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public boolean isCheck() {
        return selected;
    }

    public void setCheck(boolean check) {
        selected = check;
        if(selected) {
            this.setBackgroundColor(getColorWrapper(getContext(), R.color.colorAccent));
        } else {
            this.setBackgroundColor(Color.TRANSPARENT);
        }
    }

    private void toggle() {
        selected = !selected;
        if(selected) {
            this.setBackgroundColor(getColorWrapper(getContext(), R.color.colorAccent));
        } else {
            this.setBackgroundColor(Color.TRANSPARENT);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    private int getColorWrapper(Context context, int id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return context.getColor(id);
        } else {
            return context.getResources().getColor(id);
        }
    }
}
