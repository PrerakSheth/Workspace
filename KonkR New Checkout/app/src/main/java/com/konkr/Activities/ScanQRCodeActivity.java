package com.konkr.Activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.konkr.R;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanQRCodeActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private ZXingScannerView mScannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_qrcode);
        QrScanner();
    }

    public void QrScanner() {


        mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view
        setContentView(mScannerView);

        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();         // Start camera

    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }

    @Override
    public void handleResult(Result rawResult) {
        // Do something with the result here

        Log.e("handler", rawResult.getText()); // Prints scan results
        Log.e("handler", rawResult.getBarcodeFormat().toString()); // Prints the scan format (qrcode)

        // show the scanner result into dialog box.
//        AlertDialogUtility.showAlert(ScanActivity.this,rawResult.getText());
//        AlertDialogUtility.showToast(ScanActivity.this,"Scaning code get result successfully.");
//        finish();

        Intent data = new Intent();
        String text = rawResult.getText();
        //---set the data to pass back---
        data.setData(Uri.parse(text));
        Log.e("Uri.parse(text)", "---" + Uri.parse(text));
        Log.e("data", "---" + data);
        setResult(RESULT_OK, data);

        finish();


    }
}
