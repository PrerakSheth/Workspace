package com.konkr.Activities;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

import com.konkr.R;
import com.konkr.Utils.Headerbar;

public class DonateUsActivity extends Activity implements View.OnClickListener {
    private Headerbar headerBar;
    private WebView webViewDonateUs;
    private String strHtmlText, strMyString;
    private Button btnDonateUs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate_us);
        initView();
        setHeaderBar();
        setListener();
        setWebView();
    }

    private void setWebView() {
        try {
            strHtmlText = " %s ";
            strMyString = getResources().getString(R.string.lorem_ipsum);
            webViewDonateUs.loadData(String.format(strHtmlText, strMyString), "text/html", "utf-8");
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }


    private void initView() {
        try {
            headerBar = findViewById(R.id.headerBar);
            webViewDonateUs = findViewById(R.id.webViewDonateUs);
            btnDonateUs = findViewById(R.id.btnDonateUs);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setHeaderBar() {
        try {
            headerBar.ibtnLeftOne.setVisibility(View.VISIBLE);
            headerBar.ibtnLeftOne.setImageResource(R.drawable.left_arrow);
            headerBar.tvTitle.setVisibility(View.VISIBLE);
            headerBar.tvTitle.setText(R.string.donate_us);
//            String txtJustify = "<html><body style=\"text-align:justify\"> %s </body></Html>";
//            webviewInfo.loadDataWithBaseURL(null, htmlContentInStringFormat, "text/html", "utf-8", null);
//            webviewCherish.loadData(String.format(txtJustify, htmlContentInStringFormat), "text/html", "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setListener() {
        try {
            headerBar.ibtnLeftOne.setOnClickListener(this);
            btnDonateUs.setOnClickListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()) {
                case R.id.ibtnLeftOne:
                    finish();
                    break;

                case R.id.btnDonateUs:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
