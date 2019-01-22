package com.konkr.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.konkr.Activities.ProfileActivity;
import com.konkr.Models.CommonMessageStatus;
import com.konkr.Models.FollowersList;
import com.konkr.Models.TagUsers;
import com.konkr.R;
import com.konkr.SharedPreferences.SessionManager;
import com.konkr.Utils.GlobalData;
import com.konkr.Utils.LogM;
import com.konkr.Utils.MyTextView;
import com.konkr.Webservices.GetJsonWithAndroidNetworkingLib;
import com.konkr.Webservices.OnUpdateListener;
import com.konkr.Webservices.WebField;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android on 6/19/2018.
 */

public class TagUserAdapter extends BaseAdapter implements SpinnerAdapter {
    private Activity context;
    private ArrayList<TagUsers.UserDataBean> alTagUsers;

    public TagUserAdapter(Activity context, ArrayList<TagUsers.UserDataBean> alTagUsers) {
        this.alTagUsers = alTagUsers;
        this.context = context;
    }


    @Override
    public int getCount() {
        return alTagUsers.size();
    }

    @Override
    public Object getItem(int position) {
        return alTagUsers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View view, ViewGroup viewgroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View convertView = inflater.inflate(R.layout.row_tag_user, viewgroup, false);

        TextView textViewName = convertView.findViewById(R.id.tvName);
        SimpleDraweeView imageViewFlag = convertView.findViewById(R.id.ivUserPhoto);
        textViewName.setText(alTagUsers.get(position).getName());
        imageViewFlag.setImageURI(Uri.parse(alTagUsers.get(position).getProfilePic()));
        return convertView;
            /*TextView txt = new TextView(activity);
            txt.setGravity(Gravity.CENTER_VERTICAL);
           // txt.setPadding(12, 12, 12, 12);
            txt.setTextSize(activity.getResources().getDimension(R.dimen._8ssp));
            txt.setSingleLine();
            txt.setEllipsize(TextUtils.TruncateAt.END);

            txt.setSelected(true);
            txt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.down, 0);
            txt.setText(alFt.get(i).getTimezone());
            txt.setCompoundDrawablePadding(5);
            txt.setTextColor(activity.getResources().getColor(R.color.txtColor));
            Typeface Gotham_Book = Fonts.Gotham_Book(activity);
            txt.setTypeface(Gotham_Book);
            return txt;*/
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.row_tag_user, parent, false);

        TextView textViewName = convertView.findViewById(R.id.tvName);
        SimpleDraweeView imageViewFlag = convertView.findViewById(R.id.ivUserPhoto);
        textViewName.setText(alTagUsers.get(position).getName());
        imageViewFlag.setImageURI(Uri.parse(alTagUsers.get(position).getProfilePic()));
        return row;
            /*TextView txt = new TextView(activity);
            //txt.setPadding(12, 12, 12, 12);
            txt.setTextSize(activity.getResources().getDimension(R.dimen._8ssp));
            txt.setSingleLine();
            txt.setGravity(Gravity.CENTER_VERTICAL);
            txt.setText(alFt.get(position).getTimezone());
            txt.setTextColor(activity.getResources().getColor(R.color.txtColor));*/

    }
}
