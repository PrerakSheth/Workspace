package com.patchpets.utils;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.patchpets.R;

public class HeaderBar extends ConstraintLayout {

    public TextView tvTitle;
    public ImageButton ibLeft, ibRight, ibSwitchBtn, ibFilter;
    private Context context;

    public HeaderBar(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public HeaderBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        try {
            init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void init() {
        try {
            View view = LayoutInflater.from(context).inflate(R.layout.header_bar, this, true);
            tvTitle = view.findViewById(R.id.tvTitle);
            ibLeft = findViewById(R.id.ibLeft);
            ibRight = findViewById(R.id.ibRight);
            ibSwitchBtn = findViewById(R.id.ibSwitchBtn);
            ibFilter = findViewById(R.id.ibFilter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
