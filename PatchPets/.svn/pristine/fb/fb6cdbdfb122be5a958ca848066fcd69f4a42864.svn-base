package com.patchpets.Activitys;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;

import com.patchpets.Adapters.MessagesAdapter;
import com.patchpets.R;
import com.patchpets.databinding.ActivityMessagesBinding;
import com.patchpets.interfaces.OnItemClickListener;
import com.patchpets.utils.HeaderBar;

public class MessagesActivity extends AppCompatActivity implements View.OnClickListener, OnItemClickListener {

    private ActivityMessagesBinding binding;
    private HeaderBar headerBar;
    private RecyclerView rvGroup;
    private ImageButton ibCreateGroup;
    private MessagesAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_messages);
        bindViews();
        setListener();
        mAdapter = new MessagesAdapter(MessagesActivity.this, this);
        rvGroup.setLayoutManager(new LinearLayoutManager(this));
        rvGroup.setAdapter(mAdapter);
    }

    private void bindViews() {
        headerBar = binding.headerBar;
        rvGroup = binding.rvGroup;
        ibCreateGroup = binding.ibCreateGroup;
        headerBar.tvTitle.setText(getResources().getString(R.string.messages));
        headerBar.ibLeft.setImageDrawable(getResources().getDrawable(R.drawable.back));
    }

    private void setListener() {
        headerBar.ibLeft.setOnClickListener(this);
        headerBar.ibRight.setOnClickListener(this);
        ibCreateGroup.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        try {
            switch (view.getId()) {
                case R.id.ibLeft:
                    onBackPressed();
                    break;

                case R.id.ibCreateGroup:
                    Intent intent = new Intent(MessagesActivity.this, CreateGroupActivity.class);
                    startActivity(intent);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClicked(int position) {
        Intent intent = new Intent(MessagesActivity.this, ChatScreenActivity.class);
        startActivity(intent);
    }
}
