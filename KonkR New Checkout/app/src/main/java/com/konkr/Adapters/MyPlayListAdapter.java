package com.konkr.Adapters;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.konkr.Activities.MusicListActivity;
import com.konkr.Models.Data;
import com.konkr.R;
import com.konkr.Utils.MyTextView;

import java.util.ArrayList;

/**
 * Created by Android on 6/27/2018.
 */

public class MyPlayListAdapter extends BaseAdapter {

    Context context;
    ArrayList<Data> items;

    public MyPlayListAdapter(Context context, ArrayList<Data> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.row_my_playlist, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //viewHolder.index.setText(getItem(position).index);
        viewHolder.ivSongPhoto.setImageURI(items.get(position).getPerson().getImage());
        viewHolder.tvSongTitle.setText(items.get(position).getPerson().getSongName());
        long durationMin = items.get(position).getPerson().getDuration_ms() / (1000 * 60);
        long durationSec = items.get(position).getPerson().getDuration_ms() % (1000 * 60);
        viewHolder.tvEstTime.setText("" + durationMin + ":" + ("" + durationSec).substring(0, 2));
        viewHolder.tvSingerName.setText(items.get(position).getPerson().getArtisName());
        viewHolder.tvSongTitle.setSelected(true);
        viewHolder.tvSingerName.setSelected(true);

//        viewHolder.ivSongPhoto.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//            }
//        });

        if (items.get(position).getPerson().isPlaying()) {
            viewHolder.ivPlayPause.setImageResource(R.drawable.pause);
            viewHolder.clSong.setBackground(context.getResources().getDrawable(R.color.verification_tab_text_color));

        } else {
            viewHolder.ivPlayPause.setImageResource(R.drawable.play);
            viewHolder.clSong.setBackground(context.getResources().getDrawable(R.color.white));
        }

        viewHolder.ivPlayPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!items.get(position).getPerson().isPlaying()) {
                    ((MusicListActivity) context).paySong(items.get(position).getPerson().getUri(), position);
                    for (int i = 0; i < items.size(); i++) {
                        if (i == position) {
                            items.get(position).getPerson().setPlaying(true);
                        } else {
                            items.get(i).getPerson().setPlaying(false);
                        }
                    }

                    notifyDataSetChanged();

                } else if (items.get(position).getPerson().isPlaying()) {
                    ((MusicListActivity) context).pausTheSong();

                    for (int i = 0; i < items.size(); i++) {

                        if (i == position) {
                            items.get(position).getPerson().setPlaying(false);
                        } else {
                            items.get(i).getPerson().setPlaying(false);
                        }
                    }

                    notifyDataSetChanged();
                }
            }
        });

        // viewHolder.tvEstTime.setText (items.get(position).getPerson ().getDuration ());
        //etItem (position).person.getPersonName ()
        //viewHolder.index.setVisibility(getItem(position).showIndex ? View.VISIBLE : View.GONE);
        return convertView;
    }

    public int getPositionForSection(String s) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).index.equals(s)) return i;
        }
        return 0;
    }

    class ViewHolder {
        MyTextView tvSongTitle, tvSingerName, tvEstTime, tvAbsType;
        SimpleDraweeView ivSongPhoto;
        private TextView content;
        ImageView ivPlayPause;
        ConstraintLayout clSong;

        public ViewHolder(View view) {
            this.tvSongTitle = (MyTextView) view.findViewById(R.id.tvSongTitle);
            this.ivSongPhoto = (SimpleDraweeView) view.findViewById(R.id.ivSongPhoto);
            this.tvSingerName = (MyTextView) view.findViewById(R.id.tvSingerName);
            this.tvEstTime = (MyTextView) view.findViewById(R.id.tvEstTime);
            this.ivPlayPause = (ImageView) view.findViewById(R.id.ivPlayPause);
            this.clSong = (ConstraintLayout) view.findViewById(R.id.clSong);
        }
    }
}





