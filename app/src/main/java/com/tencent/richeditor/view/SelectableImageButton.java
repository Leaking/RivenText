package com.tencent.richeditor.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.tencent.richeditor.R;

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

    private boolean isCheck() {
        return selected;
    }

    private void toggle() {
        selected = !selected;
        if(selected) {
            this.setBackgroundColor(getContext().getColor(R.color.colorAccent));
        } else {
            this.setBackgroundColor(Color.TRANSPARENT);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_UP) {
            toggle();
        }

        return super.onTouchEvent(event);

    }
}
