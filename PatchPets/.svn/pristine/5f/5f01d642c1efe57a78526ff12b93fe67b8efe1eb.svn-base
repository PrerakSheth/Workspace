package com.patchpets.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.patchpets.R;
import com.patchpets.interfaces.OnHomeMenuClick;
import com.patchpets.model.User;
import com.patchpets.utils.CircleTransform;
import com.patchpets.utils.Constants;
import com.patchpets.utils.MyApp;

public class DrawerLayoutAdapter extends RecyclerView.Adapter {

    private Context context;
    private User user;
    private String[] drawerTitle;
    private OnHomeMenuClick mListener;

    public DrawerLayoutAdapter(Context c, User user, String[] drawerTitle, OnHomeMenuClick mListener) {
        this.context = c;
        this.user = user;
        this.drawerTitle = drawerTitle;
        this.mListener = mListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        try {
            View view;
            switch (viewType) {
                case 0:
                    view = LayoutInflater.from(context).inflate(R.layout.drawer_profile, viewGroup, false);
                    return new ProfileHolder(view);
                default:
                    view = LayoutInflater.from(context).inflate(R.layout.drawer_menu, viewGroup, false);
                    return new MenuHolder(view);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, final int position) {
        try {
            switch (viewHolder.getItemViewType()) {
                case 0:
                    if (user.getUserType() == Constants.BUSINESS_USER) {
                        ((ProfileHolder) viewHolder).tvUserName.setText(user.getBusinessName());
                    } else if (user.getUserType() == Constants.DOG_OWNER) {
                        ((ProfileHolder) viewHolder).tvUserName.setText(user.getFirstName() + " " + user.getLastName());
                    }

                    ((ProfileHolder) viewHolder).tvUserName.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mListener.homeMenuClick(position, ((ProfileHolder) viewHolder).tvUserName);
                        }
                    });
                    ((ProfileHolder) viewHolder).ibEdit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mListener.homeMenuClick(position, ((ProfileHolder) viewHolder).ibEdit);
                        }
                    });
                    ((ProfileHolder) viewHolder).ivProfile.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mListener.homeMenuClick(position, ((ProfileHolder) viewHolder).tvUserName);
                        }
                    });

                    MyApp.picasso
                            .load(user.getProfilePic())
                            .placeholder(R.drawable.profile)
                            .error(R.drawable.profile)
                            .fit().centerCrop()
                            .transform(new CircleTransform())
                            .into(((ProfileHolder) viewHolder).ivProfile);
                    break;

                default:
                    ((MenuHolder) viewHolder).tvMenuItem.setText(drawerTitle[position]);
                    ((MenuHolder) viewHolder).tvMenuItem.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mListener.homeMenuClick(position, ((MenuHolder) viewHolder).tvMenuItem);
                        }
                    });
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return drawerTitle.length;
    }

    public static class ProfileHolder extends RecyclerView.ViewHolder {
        TextView tvUserName;
        ImageButton ibEdit;
        ImageView ivProfile;

        public ProfileHolder(View itemView) {
            super(itemView);
            this.ibEdit = itemView.findViewById(R.id.ibEdit);
            this.tvUserName = itemView.findViewById(R.id.tvUserName);
            this.ivProfile = itemView.findViewById(R.id.ivProfile);
        }
    }

    public static class MenuHolder extends RecyclerView.ViewHolder {
        TextView tvMenuItem;

        public MenuHolder(View itemView) {
            super(itemView);
            this.tvMenuItem = itemView.findViewById(R.id.tvMenuItem);
        }
    }
}
