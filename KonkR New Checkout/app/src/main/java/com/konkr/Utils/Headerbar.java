package com.konkr.Utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.konkr.R;


public class Headerbar extends RelativeLayout {

    public TextView tvTitle;
    public ImageButton ibtnLeftOne, ibtnLeftTwo, ibtnRightOne, ibtnRightTwo;
    private Context context;
    public TextView tvRight;

    public Headerbar(Context context) {
        super(context);
        this.context = context;
        initView();
    }

    public Headerbar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        try {
            initView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void initView() {
        try {
            View view = LayoutInflater.from(context).inflate(R.layout.headerbar, this, true);
            tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            ibtnLeftOne = (ImageButton) findViewById(R.id.ibtnLeftOne);
            ibtnLeftTwo = (ImageButton) findViewById(R.id.ibtnLeftTwo);
            ibtnRightOne = (ImageButton) findViewById(R.id.ibtnRightOne);
            ibtnRightTwo = (ImageButton) findViewById(R.id.ibtnRightTwo);
            tvRight = (TextView) findViewById(R.id.tvRight);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
