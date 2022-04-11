package com.hamid.template.utils;

import android.content.Context;
import android.util.AttributeSet;



/**
 * Created by mala on 22-08-2016.
 */

public class TextViewRegular extends androidx.appcompat.widget.AppCompatTextView {

    public TextViewRegular(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public TextViewRegular(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TextViewRegular(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {

    }
}
