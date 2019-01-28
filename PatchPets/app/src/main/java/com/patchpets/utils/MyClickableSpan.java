package com.patchpets.utils;

import android.content.Context;
import android.content.Intent;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

import com.patchpets.Activitys.SignInActivity;
import com.patchpets.Activitys.SignUpActivity;

public class MyClickableSpan extends ClickableSpan {

    private int spanPosition;    // span position
    private String clickedOn;
    private Context context;

    public MyClickableSpan(Context context, int spanPosition, String ClickedOn) {
        this.context = context;
        this.spanPosition = spanPosition;
        this.clickedOn = ClickedOn;
    }

    @Override
    public void onClick(View widget) {
        Intent intent = null;
        switch (clickedOn) {
            case Constants.SIGN_UP:
                intent = new Intent(context, SignUpActivity.class);
                context.startActivity(intent);
                break;
            case Constants.SIGN_IN:
                intent = new Intent(context, SignInActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                break;
        }
    }

    public void updateDrawState(TextPaint ds) {// override updateDrawState
        ds.setUnderlineText(false); // set to false to remove underline
    }
}
