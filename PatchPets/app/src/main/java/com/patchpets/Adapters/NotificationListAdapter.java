package com.patchpets.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.patchpets.R;
import com.patchpets.model.Notification;
import com.patchpets.utils.CircleTransform;
import com.patchpets.utils.MyApp;
import com.patchpets.utils.TypefaceSpan;

import java.util.ArrayList;

public class NotificationListAdapter extends RecyclerView.Adapter<NotificationListAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Notification> alNotification;

    public NotificationListAdapter(Context context, ArrayList<Notification> alNotification) {
        this.context = context;
        this.alNotification = alNotification;
    }

    @Override
    public NotificationListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_notification, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        //  final Notification contact = alNotification.get(position);
        String str;
        if (position % 2 == 0) {
            str = context.getResources().getString(R.string.johne_doe) + " " + "Messaged you";
        } else {
            str = context.getResources().getString(R.string.johne_doe) + " " + "unfav your photo";
        }
        SpannableString spanString = new SpannableString(str);
        spanString.setSpan(new TypefaceSpan(context, context.getResources().getString(R.string.font_opensans_bold)), 0, 8, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spanString.setSpan(new AbsoluteSizeSpan(50), 0, 8, 0);
        holder.tvNotificationMsg.setText(spanString);

        //  holder.tvNotificationMsg.setText(contact.getNotificationMsg());
        //       holder.tvNotificationTime.setText(contact.getNotificationTime());
        holder.tvNotificationTime.setText(position + 1 + " hrs ago");

        MyApp.picasso.load("https://images-na.ssl-images-amazon.com/images/I/51ltYuAUfXL._SY355_.jpg").transform(new CircleTransform()).into(holder.ivNotification);

//        Picasso.with(context).load("https://images-eu.ssl-images-amazon.com/images/I/611YFIz8h+L._AC_SS350_.jpg").transform(new CircleTransform()).into(holder.ivNotification);

//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                itemClickListener.onItemClick(view, position);
//
//            }
//        });
//
//        holder.cardNumber.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                itemClickListener.onCardNumberClick(view, position);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        // return alNotification.size();
        return 11;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView tvNotificationMsg, tvNotificationTime;
        ImageView ivNotification;

        public ViewHolder(View v) {
            super(v);

            tvNotificationMsg = v.findViewById(R.id.tvNotificationMsg);
            tvNotificationTime = v.findViewById(R.id.tvNotificationTime);
            ivNotification = v.findViewById(R.id.ivNotification);
        }
    }
}
