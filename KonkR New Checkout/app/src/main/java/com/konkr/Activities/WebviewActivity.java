package com.konkr.Activities;

import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.konkr.R;
import com.konkr.Utils.GlobalData;
import com.konkr.databinding.ActivityWebviewBinding;

public class WebviewActivity extends AppCompatActivity {

    ActivityWebviewBinding binding;
    private WebView webView;
    String url;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_webview);

        webView = binding.webview;
        progressBar = binding.progressBar;

        if (getIntent() != null) {
            if (getIntent().getStringExtra(GlobalData.URL) != null) {
                url = getIntent().getStringExtra(GlobalData.URL);
                Log.e("url", "" + url);
//                setContentView(webView);
//                webView.setWebViewClient(new WebViewClient());
//                webView.loadUrl(url);
            }
        }

//        webView.getSettings().setJavaScriptEnabled(true);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(final WebView view, final String url) {
                return false;
            }

            @Override
            public void onPageStarted(final WebView view, final String url, final Bitmap favicon) {
                progressBar.setVisibility(View.VISIBLE);
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(final WebView view, final String url) {
                progressBar.setVisibility(View.GONE);
                super.onPageFinished(view, url);
            }
        });
//        webView.loadUrl("https://" + url);
        webView.loadUrl(url);

//        // find the WebView by name in the main.xml of step 2
////        browser = (WebView) findViewById(R.id.WebView);
//
//        // Enable javascript
//        webView.getSettings().setJavaScriptEnabled(true);
//
//        // Set WebView client
//        webView.setWebChromeClient(new WebChromeClient());
//
//        webView.setWebViewClient(new WebViewClient() {
//
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                view.loadUrl(url);
//                return false;
//            }
//        });
//        // Load the webpage
////        webView.loadUrl(url);

    }
}
