/**
 * Copyright 2016 Google Inc. All Rights Reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.patchpets.notifications;

import android.app.ActivityManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.patchpets.utils.LogM;

import java.util.List;

public class MyFCMService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
//    private LoadTables lt;
    private static final String NOTIFICATION_GROUP_ID = "com.patchpets";
    private static final String NOTIFICATION_CHANNEL_ID = "my_notification_channel";

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        LogM.e("Token : " + s);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
//        Log.e(TAG, "From: " + remoteMessage.getFrom());
        sendNotification(remoteMessage);
    }

    NotificationManager notificationManager;

    private void sendNotification(RemoteMessage messageBody) {
        try {
//            notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//            int JOB_ID = 0, SITTER_ID = 0, completedJobsWithSitter = 0;
//            int JOB_DETAIL_ID = 0, SENDER_ID = 0, RECEIVER_ID = 0, senderId = 0, receiverId = 0;
//            String FULL_NAME = "", LAST_NAME = "", PROFILE_PIC = "", ROOM_URL = "";
////            Log.e("FCM Message body : ", messageBody.getData().toString());
//            int notificationType = Integer.parseInt(messageBody.getData().get(Keys.notification_type));
//
//            try {
//                if (messageBody.getData().containsKey(Keys.job_id))
//                    JOB_ID = Integer.parseInt(messageBody.getData().get(Keys.job_id));
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            if (messageBody.getData().containsKey(Keys.totalCompletedJobs))
//                completedJobsWithSitter = Integer.parseInt(messageBody.getData().get(Keys.totalCompletedJobs));
//
//            if (messageBody.getData().containsKey(Keys.job_detail_id))
//                JOB_DETAIL_ID = Integer.parseInt(messageBody.getData().get(Keys.job_detail_id));
//
//            if (messageBody.getData().containsKey(Keys.receiverID))
//                RECEIVER_ID = Integer.parseInt(messageBody.getData().get(Keys.receiverID));
//
//            if (messageBody.getData().containsKey(Keys.senderID))
//                SENDER_ID = Integer.parseInt(messageBody.getData().get(Keys.senderID));
//
//            if (messageBody.getData().containsKey(Keys.fullname))
//                FULL_NAME = messageBody.getData().get(Keys.fullname);
//
//            if (messageBody.getData().containsKey(Keys.sitter_fullname))
//                FULL_NAME = messageBody.getData().get(Keys.sitter_fullname);
//
//            if (messageBody.getData().containsKey(Keys.user_fullname))
//                FULL_NAME = messageBody.getData().get(Keys.user_fullname);
//
//            if (messageBody.getData().containsKey(Keys.profile_pic))
//                PROFILE_PIC = messageBody.getData().get(Keys.profile_pic);
//
//            if (messageBody.getData().containsKey(Keys.sitter_profile))
//                PROFILE_PIC = messageBody.getData().get(Keys.sitter_profile);
//
//            if (messageBody.getData().containsKey(Keys.user_profile))
//                PROFILE_PIC = messageBody.getData().get(Keys.user_profile);
//
//            if (messageBody.getData().containsKey(Keys.url))
//                ROOM_URL = messageBody.getData().get(Keys.url);
//
//            if (messageBody.getData().containsKey(Keys.lastname))
//                LAST_NAME = messageBody.getData().get(Keys.lastname);
//
//            if (messageBody.getData().containsKey(Keys.sitter_lastname))
//                LAST_NAME = messageBody.getData().get(Keys.sitter_lastname);
//
//            if (messageBody.getData().containsKey(Keys.user_lastname))
//                LAST_NAME = messageBody.getData().get(Keys.user_lastname);
//
//            if (messageBody.getData().containsKey(Keys.sitter_id))
//                SITTER_ID = Integer.parseInt(messageBody.getData().get(Keys.sitter_id));
//
//            if (messageBody.getData().containsKey(Keys.sender_id))
//                senderId = Integer.parseInt(messageBody.getData().get(Keys.sender_id));
//
//            if (messageBody.getData().containsKey(Keys.receiver_id))
//                receiverId = Integer.parseInt(messageBody.getData().get(Keys.receiver_id));
//
//            Intent intent = null;
//
//            // Timber.e("feedidinnoti %s",""+feedid);
//            // intent.putExtra(Keys.badgecount,badgecount);
//
////            lt=new LoadTables(MummaCoApplication.getInstance());
////            lt.LoadAllTables(MummaCoApplication.getInstance());
////
////            datauser=new ArrayList<>();
////            datauser= LoadTables.getData(Keys.Tbl_User);
////            if(datauser.size()>0)
////            for(int i=0;i<datauser.size();i++)
////            {
////                String[] str=datauser.get(i);
////
////                if(LoadTables.getUsertype()==1)
////                    intent = new Intent(MummaCoApplication.getInstance(), ParentDashboard.class);
////                else
////                    intent = new Intent(MummaCoApplication.getInstance(), SitterDashboard.class);
////
////            }
////            else
//
//            lt = new LoadTables(MummaCoApplication.getInstance());
//            lt.LoadAllTables(MummaCoApplication.getInstance());
//            if (lt.getCount(Keys.Tbl_User) > 0) {
//                if (messageBody.getData().containsKey(Keys.totalUnreadNotification)) {
//                    SessionManager sm = new SessionManager(getApplicationContext());
//                    sm.setBadgeCount(Integer.parseInt(messageBody.getData().get(Keys.totalUnreadNotification)));
//                }
//
//                if (notificationType == 1) {
//                    notificationManager.cancel(notificationType);
//                    intent = new Intent(MummaCoApplication.getInstance(), SitterSideJobDetails.class)
//                            .putExtra(Keys.job_id, JOB_ID)
//                            .putExtra(Keys.is_direct_job, 1)
//                            .putExtra(Keys.job_detail_id, JOB_DETAIL_ID);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                } else if (notificationType == 2) {
//                    notificationManager.cancel(notificationType);
//                    intent = new Intent(MummaCoApplication.getInstance(), ParentDashboard.class)
//                            .putExtra(Keys.selected_tab, 5);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                } else if (notificationType == 4) {
//                    notificationManager.cancel(notificationType);
//                    intent = new Intent(MummaCoApplication.getInstance(), TipSitterActivity.class)
//                            .putExtra(Keys.job_id, JOB_ID)
//                            .putExtra(Keys.job_detail_id, JOB_DETAIL_ID)
//                            .putExtra(Keys.profile_pic, PROFILE_PIC)
//                            .putExtra(Keys.name, FULL_NAME + " " + LAST_NAME)
//                            .putExtra(Keys.totalCompletedJobs, completedJobsWithSitter)
//                            .putExtra(Keys.user_id, SITTER_ID);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    if (appRunning() == 1) {
//                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        MummaCoApplication.getInstance().startActivity(intent);
//                    }
//                } else if (notificationType == 5) {
//                    notificationManager.cancel(notificationType);
//                    intent = new Intent(MummaCoApplication.getInstance(), SitterDashboard.class)
//                            .putExtra(Keys.selected_tab, 4);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                } else if (notificationType == 6) {
//                    notificationManager.cancel(notificationType);
//                    intent = new Intent(MummaCoApplication.getInstance(), SitterDashboard.class)
//                            .putExtra(Keys.selected_tab, 4);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                } else if (notificationType == 7) {
//                    notificationManager.cancel(notificationType);
//                    intent = new Intent(MummaCoApplication.getInstance(), SitterDashboard.class)
//                            .putExtra(Keys.selected_tab, 1);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    if (appRunning() == 1) {
//                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        MummaCoApplication.getInstance().startActivity(intent);
//                    }
//                } else if (notificationType == 8) {
//                    notificationManager.cancel(notificationType);
//                    intent = new Intent(MummaCoApplication.getInstance(), ChatActivity.class)
//                            .putExtra(Keys.receiverID, senderId)
//                            .putExtra(Keys.senderID, receiverId)
//                            .putExtra(Keys.job_id, JOB_ID)
////                            .putExtra(Keys.senderID, Integer.parseInt(LoadTables.getUserid()))
//                            .putExtra(Keys.job_detail_id, JOB_DETAIL_ID)
//                            .putExtra(Keys.fullname, FULL_NAME + " " + LAST_NAME)
//                            .putExtra(Keys.profile_pic, PROFILE_PIC);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                } else if (notificationType == 9) {
//                    notificationManager.cancel(notificationType);
//                    intent = new Intent(MummaCoApplication.getInstance(), VideoCallActivity.class)
//                            .putExtra(Keys.receiverID, receiverId)
//                            .putExtra(Keys.senderID, senderId)
//                            .putExtra(Keys.room_url, ROOM_URL)
//                            .putExtra(Keys.fullname, FULL_NAME + " " + LAST_NAME)
//                            .putExtra(Keys.job_id, JOB_ID)
//                            .putExtra(Keys.job_detail_id, JOB_DETAIL_ID)
//                            .putExtra("isFromNotification", 1)
//                            .putExtra(Keys.is_receive, 1)
//                            .putExtra(Keys.profile_pic, PROFILE_PIC);
//                    if (appRunning() == 1) {
//                        MummaCoApplication.getInstance().startActivity(intent);
//                    }
//                } else if (notificationType == 10) {
//                    notificationManager.cancel(notificationType);
//                    intent = new Intent(MummaCoApplication.getInstance(), SitterDashboard.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    if (appRunning() == 1) {
//                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        MummaCoApplication.getInstance().startActivity(intent);
//                    }
//                } else if (notificationType == 26 || notificationType == 25) { // Reject call
//                    notificationManager.cancel(notificationType);
//                    intent = new Intent("REJECT_CALL_LISTENER");
//                    intent.putExtra("notification_type", notificationType);
//                    LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
//                } else if (notificationType == 28) {
//                    notificationManager.cancel(notificationType);
//                    if (LoadTables.getUsertype() == 1) {
//                        intent = new Intent(MummaCoApplication.getInstance(), ParentDashboard.class)
//                                .putExtra(Keys.selected_tab, 9);
//                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    } else {
//                        intent = new Intent(MummaCoApplication.getInstance(), SitterDashboard.class)
//                                .putExtra(Keys.selected_tab, 12);
//                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    }
//                } else if (notificationType == 29) {
//                    notificationManager.cancel(notificationType);
//                    intent = new Intent(MummaCoApplication.getInstance(), InActiveJobDetails.class)
//                            .putExtra(Keys.job_id, JOB_ID)
//                            .putExtra(Keys.job_detail_id, JOB_DETAIL_ID)
//                            .putExtra(Keys.my_booking, 1)
//                            .putExtra(Keys.is_past, 1)
//                            .putExtra(Keys.chat_counts, 0)
//                            .putExtra(Keys.is_confirmed_job, 1)
//                            .putExtra(Keys.is_active, 0);
//                } else {
//                    intent = new Intent(MummaCoApplication.getInstance(), Login.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                }
//            } else {
//                intent = new Intent(MummaCoApplication.getInstance(), Login.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            }
//
//            PendingIntent pendingIntent = PendingIntent.getActivity(this,
//                    notificationType, intent, PendingIntent.FLAG_ONE_SHOT);
//
//            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//
//            NotificationCompat.Builder notificationBuilder;
//            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
//                notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
//                        .setSmallIcon(R.drawable.ic_noti)
//                        .setColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary))
//                        .setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.mipmap.ic_launcher_round))
//                        .setContentTitle(getResources().getString(R.string.app_name))
//                        .setContentText(messageBody.getData().get(Keys.message))
//                        .setAutoCancel(true)
//                        .setSound(defaultSoundUri)
//                        .setStyle(new NotificationCompat.BigTextStyle().bigText(messageBody.getData().get(Keys.message)))
//                        .setGroup(NOTIFICATION_GROUP_ID)
//                        .setGroupSummary(true)
//                        .setContentIntent(pendingIntent);
//            } else {
//                notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
//                        .setSmallIcon(R.drawable.ic_noti)
//                        .setColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary))
//                        .setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.mipmap.ic_launcher_round))
//                        .setContentTitle(getResources().getString(R.string.app_name))
//                        .setContentText(messageBody.getData().get(Keys.message))
//                        .setAutoCancel(true)
//                        .setSound(defaultSoundUri)
//                        .setContentIntent(pendingIntent);
//            }
//
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "My Notifications", NotificationManager.IMPORTANCE_DEFAULT);
//
//                // Configure the notification channel.
//                notificationChannel.setDescription("Channel description");
//                notificationChannel.enableLights(true);
//                notificationChannel.setLightColor(Color.RED);
//                notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
//                notificationChannel.enableVibration(true);
//                notificationManager.createNotificationChannel(notificationChannel);
//            }
//
//            notificationManager.notify(notificationType, notificationBuilder.build());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int appRunning() {
        ActivityManager activityManager = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> procInfos = activityManager.getRunningAppProcesses();
        for (int i = 0; i < procInfos.size(); i++) {
            if (procInfos.get(i).processName.equals(getPackageName())) {
                return 1;
            }
        }
        return 0;
    }
}
