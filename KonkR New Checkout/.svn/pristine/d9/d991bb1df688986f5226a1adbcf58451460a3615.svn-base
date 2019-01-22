package com.konkr.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.konkr.Models.Notifications;
import com.konkr.R;
import com.konkr.Utils.GlobalData;
import com.konkr.Utils.LogM;
import com.konkr.Utils.MyTextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Android on 6/19/2018.
 */

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.ViewHolder> {
    Context context;
    ArrayList<Notifications.NotificationList> notificationsArrayList;

    public NotificationsAdapter(Context context, ArrayList<Notifications.NotificationList> notificationsArrayList) {
        this.context = context;
        this.notificationsArrayList = notificationsArrayList;
    }


    @NonNull
    @Override
    public NotificationsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_notifications, parent, false);
        return new NotificationsAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationsAdapter.ViewHolder holder, final int position) {
        final Notifications.NotificationList comList = notificationsArrayList.get(position);
        if (comList.getProfilePic().equalsIgnoreCase("")) {
            holder.ivUserPhoto.setImageResource(R.drawable.user_profile);
        } else {
            holder.ivUserPhoto.setImageURI(comList.getProfilePic());
        }
        holder.tvNotificationMsg.setText(comList.getMessage());

        if (comList.getCreatedDate().contains(" ")) {
            String wt = comList.getCreatedDate().substring(0, comList.getCreatedDate().indexOf(" "));
            holder.tvNotificationTime.setText(wt);
        }

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
//            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date mDate = sdf.parse(comList.getCreatedDate());
            long timeInMilliseconds = mDate.getTime();
            LogM.LogV(comList.getCreatedDate() + " && " + mDate + "Date in milli :: " + timeInMilliseconds);

            long seconds = (System.currentTimeMillis() - timeInMilliseconds) / 1000;
            String strAgo = "";
            LogM.LogV(seconds + " && " + System.currentTimeMillis() + " && " + timeInMilliseconds);
            if (seconds < 0) {
                strAgo = comList.getCreatedDate().split(" ")[0];
            } else if (seconds == 0) {
                strAgo = "Just now";
            } else if (seconds < 60) {
                strAgo = seconds + " sec ago";
            } else if (seconds < 120) {
                strAgo = "1 min ago";
            } else if (seconds < 3600) {
                strAgo = ((int) (seconds / 60)) + " mins ago";
            } else if (seconds == 3600) {
                strAgo = "1 hour ago";
            } else if (seconds <= (60 * 60 * 24)) {
                strAgo = (seconds / (60 * 60)) + " hours ago";
            } else if (seconds > (60 * 60 * 24) && seconds < (60 * 60 * 24 * 2)) {
                strAgo = "yesterday";
            } else {
                strAgo = comList.getCreatedDate().split(" ")[0];
            }
            holder.tvNotificationTime.setText(strAgo);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

//    public static String getTotalSecond(String dateStart, String dateStop) {
//        String strAgo = "";
//        int total = 0;
//        // String dateStart = "2018-07-18 15:14:40";
//        //HH converts hour in 24 hours format (0-23), day calculation
//        SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy hh:mm:ss a");
//
//        Date d1 = null;
//        Date d2 = null;
//
//        try {
//            d1 = format.parse(dateStart);
//            d2 = format.parse(dateStop);
//            System.out.println(format.format(d1));
//            System.out.println(format.format(d2));
//            //in milliseconds
//            long diff = d2.getTime() - d1.getTime();
//
//            long diffSeconds = diff / 1000 % 60;
//            long diffMinutes = diff / (60 * 1000) % 60;
//            long diffHours = diff / (60 * 60 * 1000) % 24;
//            long diffDays = diff / (24 * 60 * 60 * 1000);
//
//            System.out.print(diffDays + " days, ");
//            System.out.print(diffHours + " hours, ");
//            System.out.print(diffMinutes + " minutes, ");
//            System.out.print(diffSeconds + " seconds.");
//
//            long day = diffDays * 86400;
//            long hours = diffHours * 3600;
//            long min = diffMinutes * 60;
//
//            total = (int) (day + hours + min + diffSeconds);
//
//            if(total < 60) {
//                strAgo = "Few sec ago";
//            } else if(total < 120) {
//                strAgo = "1 min ago";
//            } else if(total < 3600) {
//                strAgo = ((int)(total/60)) + " mins ago";
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return strAgo;
//    }


    @Override
    public int getItemCount() {
        return notificationsArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView ivUserPhoto;
        MyTextView tvNotificationMsg, tvNotificationTime;

        public ViewHolder(View itemView) {
            super(itemView);
            ivUserPhoto = itemView.findViewById(R.id.ivUserPhoto);
            tvNotificationMsg = itemView.findViewById(R.id.tvNotificationMsg);
            tvNotificationTime = itemView.findViewById(R.id.tvNotificationTime);

        }

    }
}
