package com.konkr.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.konkr.Activities.ProfileActivity;
import com.konkr.Models.CommonMessageStatus;
import com.konkr.Models.FollowingList;
import com.konkr.R;
import com.konkr.SharedPreferences.SessionManager;
import com.konkr.Utils.GlobalData;
import com.konkr.Utils.LogM;
import com.konkr.Utils.MyTextView;
import com.konkr.Webservices.GetJsonWithAndroidNetworkingLib;
import com.konkr.Webservices.OnUpdateListener;
import com.konkr.Webservices.WebField;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Android on 6/19/2018.
 */

public class FollowingListAdapter extends RecyclerView.Adapter<FollowingListAdapter.ViewHolder> {
    private Activity context;
    private ArrayList<FollowingList.FollowingListBean> userSearchListBeans;

    public FollowingListAdapter(Activity context, ArrayList<FollowingList.FollowingListBean> userSearchListBeans) {
        this.context = context;
        this.userSearchListBeans = userSearchListBeans;
    }

    @NonNull
    @Override
    public FollowingListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_following_list, parent, false);
        return new FollowingListAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FollowingListAdapter.ViewHolder holder, final int position) {
        int pos = position;
        final FollowingList.FollowingListBean userList = userSearchListBeans.get(position);
        holder.ivUserPhoto.setImageURI(Uri.parse(userList.getProfilePic()));
        holder.tvFirstName.setText(userList.getFirstName() + " " + userList.getLastName());
        int followUnfollow = userList.getFollowUnfollow();
        if(userList.getUserId()==SessionManager.getUserId(context)){
            holder.tvFollowUnFollow.setVisibility(View.GONE);
        }else{
            holder.tvFollowUnFollow.setVisibility(View.VISIBLE);
        }
        if (followUnfollow == 0) {
            holder.tvFollowUnFollow.setText(context.getResources().getString(R.string.follow));
            holder.tvFollowUnFollow.setTextColor(context.getResources().getColor(R.color.white));
            holder.tvFollowUnFollow.setBackground(context.getResources().getDrawable(R.drawable.originalfollow_bg));
        } else if (followUnfollow == 1) {
            holder.tvFollowUnFollow.setText(context.getResources().getString(R.string.following));
            holder.tvFollowUnFollow.setTextColor(context.getResources().getColor(R.color.menu_text_color));
            holder.tvFollowUnFollow.setBackground(context.getResources().getDrawable(R.drawable.following_bg_one));
        }
        String badge = userList.getBadge();
        if (badge.equalsIgnoreCase("1")) {
            holder.ibBadge.setVisibility(View.GONE);
        } else if (badge.equalsIgnoreCase("2")) {
            holder.ibBadge.setVisibility(View.VISIBLE);
            holder.ibBadge.setBackground(context.getResources().getDrawable(R.drawable.celebrity));
        } else if (badge.equalsIgnoreCase("3")) {
            holder.ibBadge.setVisibility(View.VISIBLE);
            holder.ibBadge.setBackground(context.getResources().getDrawable(R.drawable.inflencer));
        } else if (badge.equalsIgnoreCase("4")) {
            holder.ibBadge.setVisibility(View.VISIBLE);
            holder.ibBadge.setBackground(context.getResources().getDrawable(R.drawable.sponsor));
        }
        holder.tvFollowUnFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (followUnfollow == 0) {
                    callFollowUnFollowAPI(1, userList.getUserId(), pos);
                } else if (followUnfollow == 1) {
                    callFollowUnFollowAPI(0, userList.getUserId(), pos);
                }
            }
        });

        holder.ivUserPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProfileActivity.class);
                intent.putExtra(GlobalData.OTHER_USER_ID, userSearchListBeans.get(pos).getUserId());
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        holder.tvFirstName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProfileActivity.class);
                intent.putExtra(GlobalData.OTHER_USER_ID, userSearchListBeans.get(pos).getUserId());
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    private void callFollowUnFollowAPI(int folloUnfollow, int userId, int position) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(WebField.PARAM_USER_ID, SessionManager.getUserId(context));
            jsonObject.put(WebField.PARAM_ACCESSTOKEN, SessionManager.getAccessToken(context));
            jsonObject.put(WebField.FOLLOW_UNFOLLOW.PARAM_FOLLOWORUNFOLLOW, folloUnfollow);
            jsonObject.put(WebField.FOLLOW_UNFOLLOW.PARAM_OTHERUSERID, userId);

            LogM.LogE("Request : followunfollow : " + jsonObject.toString());

            new GetJsonWithAndroidNetworkingLib(context, jsonObject, WebField.BASE_URL + WebField.FOLLOW_UNFOLLOW.MODE, 1, new OnUpdateListener() {
                @Override
                public void onUpdateComplete(JSONObject jsonObject, boolean isSuccess) {
                    LogM.LogE("Response : followunfollow  : " + jsonObject.toString());
//                    CommonMessageStatus user = new Gson().fromJson(String.valueOf(jsonObject), CommonMessageStatus.class);
                    if (isSuccess) {
                        CommonMessageStatus user = new Gson().fromJson(String.valueOf(jsonObject), CommonMessageStatus.class);
                        if (userSearchListBeans.get(position).getFollowUnfollow() == 0) {
                            userSearchListBeans.get(position).setFollowUnfollow(1);
                        } else {
                            userSearchListBeans.get(position).setFollowUnfollow(0);
                        }
                        notifyItemChanged(position);
//                        notify();
                    }
                }
            }).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return userSearchListBeans.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private SimpleDraweeView ivUserPhoto;
        private MyTextView tvFirstName;
        private MyTextView tvLastName;
        private MyTextView tvFollowUnFollow;
        private ImageView ibBadge;

        public ViewHolder(View itemView) {
            super(itemView);
            ivUserPhoto = itemView.findViewById(R.id.ivUserPhoto);
            tvFirstName = itemView.findViewById(R.id.tvFirstName);
            tvLastName = itemView.findViewById(R.id.tvLastName);
            tvFollowUnFollow = itemView.findViewById(R.id.tvFollowUnFollow);
            ibBadge = itemView.findViewById(R.id.ibBadge);

        }

    }
}
