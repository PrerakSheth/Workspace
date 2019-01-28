package com.patchpets.Activitys;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.provider.ContactsContract;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.patchpets.R;
import com.patchpets.databinding.ActivityViaSmsBinding;
import com.patchpets.utils.AlertDialogUtility;
import com.patchpets.utils.Constants;
import com.patchpets.utils.HeaderBar;

public class ViaSMSActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityViaSmsBinding binding;
    private HeaderBar headerBar;
    private ConstraintLayout clNext;
    private EditText etWriteMessage;
    private String strMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_via_sms);

        bindViews();
        setHeaderBar();
        setListener();
    }

    private void bindViews() {
        try {
            headerBar = binding.headerBar;
            clNext = binding.clNext;
            etWriteMessage = binding.etWriteMessage;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setHeaderBar() {
        try {
            headerBar.ibLeft.setVisibility(View.VISIBLE);
            headerBar.ibLeft.setImageResource(R.drawable.back);
            headerBar.tvTitle.setVisibility(View.VISIBLE);
            headerBar.tvTitle.setText(R.string.via_sms);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setListener() {
        clNext.setOnClickListener(this);
        headerBar.ibLeft.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibLeft:
                onBackPressed();
                break;

            case R.id.clNext:
                strMessage = etWriteMessage.getText().toString().trim();

                if (strMessage.length() == 0) {
                    etWriteMessage.requestFocus();
                    AlertDialogUtility.showSnakeBar(getString(R.string.er_write_your_message), etWriteMessage, this);
                } else {
                    Intent intent = new Intent(ViaSMSActivity.this, ContactListActivity.class);
                    intent.putExtra(Constants.WRITE_MESSAGE, strMessage);
                    startActivity(intent);
                }
                break;
        }
    }
}
