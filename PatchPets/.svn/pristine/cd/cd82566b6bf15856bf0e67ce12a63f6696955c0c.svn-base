package com.patchpets.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatCheckBox;
import android.util.AttributeSet;

public class MyCheckBox extends AppCompatCheckBox {

    public MyCheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);

        String str = attrs.getAttributeValue("http://schemas.android.com/apk/res-auto", "font");

        String fontName = "";
        try {
            fontName = context.getString(Integer.parseInt(str.substring(1).trim()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        setFont(context, fontName);
    }

    public void setFont(Context context, String fontName) {
        Typeface font = Typeface.createFromAsset(context.getAssets(), fontName);
        setTypeface(font);
    }

    public void resizeAndSetText(String text, int maxLength) {
        if (text.length() > maxLength) {
            text = text.substring(0, maxLength) + "...";
        }
        this.setText(text);
    }
}
