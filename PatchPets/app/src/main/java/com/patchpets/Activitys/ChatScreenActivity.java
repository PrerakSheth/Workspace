package com.patchpets.Activitys;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.patchpets.Adapters.ChatAdapter;
import com.patchpets.R;
import com.patchpets.databinding.ActivityChatScreenBinding;
import com.patchpets.utils.Constants;
import com.patchpets.utils.HeaderBar;

public class ChatScreenActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityChatScreenBinding binding;
    private HeaderBar headerBar;
    private RecyclerView rvChat;
    private ChatAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_chat_screen);
        bindViews();
        setListener();
        mAdapter = new ChatAdapter(this);
        rvChat.setLayoutManager(new LinearLayoutManager(this));
        rvChat.setAdapter(mAdapter);
    }

    private void bindViews() {
        headerBar = binding.headerBar;
        rvChat = binding.rvChat;
        headerBar.tvTitle.setText("Group / UserName");
        headerBar.ibLeft.setImageDrawable(getResources().getDrawable(R.drawable.back));
    }

    private void setListener() {
        headerBar.ibLeft.setOnClickListener(this);
        headerBar.tvTitle.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        try {
            switch (view.getId()) {
                case R.id.ibLeft:
                    onBackPressed();
                    break;

                case R.id.tvTitle:
                    Intent iExploreGroup = new Intent(ChatScreenActivity.this, CreateGroupActivity.class);
                    iExploreGroup.putExtra(Constants.FROM, true);
                    startActivity(iExploreGroup);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
