package com.patchpets.Activitys;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.zxing.Result;
import com.patchpets.R;
import com.patchpets.databinding.ActivityScanQrCodeBinding;
import com.patchpets.utils.HeaderBar;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanQrCodeActivity extends AppCompatActivity implements View.OnClickListener, ZXingScannerView.ResultHandler {

    private HeaderBar headerBar;
    private ActivityScanQrCodeBinding binding;
    private static final int PERMISSION_REQUEST_CODE = 200;

    private ZXingScannerView mScannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_scan_qr_code);
        bindViews();
        setListener();
        if (!checkPermission()) {
            requestPermission();
        }
        QrScanner();

    }

    private void bindViews() {
        headerBar = binding.headerBar;
        mScannerView = binding.ivQrCode;
        headerBar.tvTitle.setText(getResources().getString(R.string.scan_qr_code));
        headerBar.ibLeft.setImageDrawable(getResources().getDrawable(R.drawable.back));
    }

    private void setListener() {
        headerBar.ibLeft.setOnClickListener(this);
    }

    public void onClick(View view) {
        try {
            switch (view.getId()) {
                case R.id.ibLeft:
                    onBackPressed();
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void QrScanner() {
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
    }

    @Override
    protected void onResume() {
        super.onResume();
        mScannerView.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    protected void onDestroy() {
        mScannerView.stopCamera();
        super.onDestroy();
    }

    @Override
    public void handleResult(Result rawResult) {
        Intent data = new Intent();
        String text = rawResult.getText();
        data.setData(Uri.parse(text));
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }

    private boolean checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        return true;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                requestPermission();
                            }
                        }
                    }
                }
                break;
        }
    }
}
