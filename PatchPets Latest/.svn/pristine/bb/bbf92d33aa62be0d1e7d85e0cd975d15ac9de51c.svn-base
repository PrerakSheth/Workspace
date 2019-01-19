package com.patchpets.Activitys;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.patchpets.Adapters.NotificationListAdapter;
import com.patchpets.R;
import com.patchpets.databinding.ActivityNotificationListBinding;
import com.patchpets.model.Notification;
import com.patchpets.utils.HeaderBar;

import java.util.ArrayList;

public class NotificationListActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityNotificationListBinding binding;
    private HeaderBar headerBar;
    private TextView tvEmpty;
    private RecyclerView rvNotificationList;
    private NotificationListAdapter notificationListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_notification_list);

        bindViews();
        setHeaderBar();
        setListener();
        setAdapter();
    }

    private void bindViews() {
        headerBar = binding.headerBar;
        rvNotificationList = binding.rvNotificationList;
        tvEmpty = binding.tvEmpty;
    }

    private void setHeaderBar() {
        headerBar.ibLeft.setVisibility(View.VISIBLE);
        headerBar.ibLeft.setImageResource(R.drawable.back);
        headerBar.tvTitle.setVisibility(View.VISIBLE);
        headerBar.tvTitle.setText(R.string.label_notification);
    }

    private void setListener() {
        headerBar.ibLeft.setOnClickListener(this);
    }

    private void setAdapter() {
        ArrayList<Notification> cardDetails = new ArrayList();
        Notification notification = new Notification();
        notification.setNotificationMsg(getResources().getString(R.string.johne_doe) + " Messaged you");
        notification.setNotificationTime("1 hrs ago");
        cardDetails.add(notification);

        Notification notificationTwo = new Notification();
        notificationTwo.setNotificationMsg(getResources().getString(R.string.johne_doe) + " unfav your photo");
        notificationTwo.setNotificationTime("2 hrs ago");
        cardDetails.add(notificationTwo);

        Notification notificationThree = new Notification();
        notificationThree.setNotificationMsg(getResources().getString(R.string.johne_doe) + " " + "Messaged you");
        notificationThree.setNotificationTime("3 hrs ago");
        cardDetails.add(notificationThree);

        notificationListAdapter = new NotificationListAdapter(this, cardDetails);
        rvNotificationList.setLayoutManager(new LinearLayoutManager(this));
        rvNotificationList.setAdapter(notificationListAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibLeft:
                onBackPressed();
                break;
        }
    }
}
