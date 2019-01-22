package com.konkr.Activities;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.konkr.Adapters.NotificationsAdapter;
import com.konkr.Models.ExpressionList;
import com.konkr.Models.Notifications;
import com.konkr.R;
import com.konkr.SharedPreferences.SessionManager;
import com.konkr.Utils.AlertDialogUtility;
import com.konkr.Utils.ConnectivityDetector;
import com.konkr.Utils.GlobalData;
import com.konkr.Utils.Headerbar;
import com.konkr.Utils.LogM;
import com.konkr.Utils.MyTextView;
import com.konkr.Webservices.GetJsonWithAndroidNetworkingLib;
import com.konkr.Webservices.OnUpdateListener;
import com.konkr.Webservices.WebField;
import com.konkr.databinding.ActivityNotificationsBinding;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

public class NotificationsActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityNotificationsBinding binding;
    private Context context;
    private Headerbar headerBar;
    private RecyclerView notificationsRecyclerView;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<Notifications.NotificationList> notificationsArrayList;
    private NotificationsAdapter adapter;
    private View snackBarView;
    private MyTextView tvEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView (R.layout.activity_notifications);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_notifications);
        context = NotificationsActivity.this;
        snackBarView = findViewById(android.R.id.content);

        bindView();
        setListener();
        setHeaderBar();
        setLayoutManger();
        getNotificationList();

    }

    private void getNotificationList() {

        if (ConnectivityDetector.isConnectingToInternet(context)) {

            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put(WebField.PARAM_USER_ID, SessionManager.getUserId(context));
                jsonObject.put(WebField.PARAM_ACCESSTOKEN, SessionManager.getAccessToken(context));

                LogM.LogE("Request : Get Expression List : : " + jsonObject.toString());

                new GetJsonWithAndroidNetworkingLib(NotificationsActivity.this, jsonObject, WebField.BASE_URL + WebField.GET_NOTIFICATION_LIST.MODE, 1, new OnUpdateListener() {
                    @Override
                    public void onUpdateComplete(JSONObject jsonObject, boolean isSuccess) {

                        final Notifications notificationData = new Gson().fromJson(String.valueOf(jsonObject), Notifications.class);
                        if (isSuccess) {
                            LogM.LogE("Response : Get Expression List : " + jsonObject.toString());
                            if (notificationData.getNotificationList().size() > 0) {
                                notificationsArrayList = new ArrayList<>();
                                notificationsArrayList.addAll(notificationData.getNotificationList());
                                setNotificationAdapter();
                                tvEmpty.setVisibility(View.GONE);
                                notificationsRecyclerView.setVisibility(View.VISIBLE);
                            } else {
                                tvEmpty.setVisibility(View.VISIBLE);
                                notificationsRecyclerView.setVisibility(View.GONE);
                            }
                        }

                    }
                }).execute();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            AlertDialogUtility.showAlert(context, GlobalData.STR_INETRNET_ALERT_MESSAGE);
        }

    }

    private void setLayoutManger() {
        linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        notificationsRecyclerView.setLayoutManager(linearLayoutManager);
    }

    private void setListener() {
        headerBar.ibtnLeftOne.setOnClickListener(this);

    }

    private void bindView() {
        headerBar = binding.headerBar;
        notificationsRecyclerView = binding.notificationRecyclerView;
        tvEmpty = binding.tvEmpty;
    }

    private void setHeaderBar() {
        headerBar.ibtnLeftOne.setVisibility(View.VISIBLE);
        headerBar.ibtnLeftOne.setImageResource(R.drawable.back);
        headerBar.tvTitle.setVisibility(View.VISIBLE);
        headerBar.tvTitle.setText(R.string.notifications_title);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ibtnLeftOne:
                finish();
                break;
        }
    }

//    private void getNotification() {
//        if (notificationsArrayList == null) {
//
//            notificationsArrayList = new ArrayList<> ();
//            notificationsArrayList.add (new Notifications ((R.drawable.user_profile),  "John Doe Added a new Meal in his Profile","1hr ago"));
//            notificationsArrayList.add (new Notifications ((R.drawable.user_profile),  "Stainly Johnson Doestarts following you","2hr ago"));
//            notificationsArrayList.add (new Notifications ((R.drawable.user_profile),  "Jeff Doe Likes your post","3hr ago"));
//            notificationsArrayList.add (new Notifications ((R.drawable.user_profile),  "Jeff Doe Likes your post","2hr ago"));
//            notificationsArrayList.add (new Notifications ((R.drawable.user_profile),  "Jim Brinholzadded a new photo","06/05/2018 "));
//            notificationsArrayList.add (new Notifications ((R.drawable.user_profile),  "John Doe Added a new Meal in his Profile","08/05/2018"));
//            notificationsArrayList.add (new Notifications ((R.drawable.user_profile),  "John Doe Likes your post","3hr ago"));
//        }
//    }

    private void setNotificationAdapter() {
        adapter = new NotificationsAdapter(context, notificationsArrayList);
        notificationsRecyclerView.setAdapter(adapter);
    }
}
