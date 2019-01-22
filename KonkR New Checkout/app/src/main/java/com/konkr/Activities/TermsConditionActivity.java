package com.konkr.Activities;

import android.app.Activity;
import android.content.res.Resources;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.konkr.R;
import com.konkr.Utils.Headerbar;
import com.konkr.Utils.MyTextView;
import com.konkr.databinding.ActivityTermConditionBinding;

public class TermsConditionActivity extends Activity implements View.OnClickListener {
    private Headerbar headerBar;
    //    private WebView webView;
    private String strHtmlText, strMyString;
    private MyTextView tvData;
    private ActivityTermConditionBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_term_condition);
        bindViews();
        setHeaderBar();
        setListener();
        setWebView();
    }

    private void setWebView() {
        try {
            strHtmlText = " %s ";
            strMyString = getResources().getString(R.string.lorem_ipsum);
            tvData.setText(strMyString);
//            webView.loadData(String.format(strHtmlText, strMyString), "text/html", "utf-8");
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setListener() {
        try {
            headerBar.ibtnLeftOne.setOnClickListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setHeaderBar() {
        try {
            headerBar.ibtnLeftOne.setVisibility(View.VISIBLE);
            headerBar.ibtnLeftOne.setImageResource(R.drawable.back);
            headerBar.tvTitle.setVisibility(View.VISIBLE);
            headerBar.tvTitle.setText(R.string.title_terms_condition);
//            String txtJustify = "<html><body style=\"text-align:justify\"> %s </body></Html>";
//            webviewInfo.loadDataWithBaseURL(null, htmlContentInStringFormat, "text/html", "utf-8", null);
//            webviewCherish.loadData(String.format(txtJustify, htmlContentInStringFormat), "text/html", "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void bindViews() {
        try {
//            webView = findViewById(R.id.webView);
            headerBar = binding.headerBar;
            tvData = binding.tvData;
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
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
