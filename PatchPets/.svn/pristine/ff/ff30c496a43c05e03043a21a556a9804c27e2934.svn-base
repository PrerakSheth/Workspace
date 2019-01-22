package com.patchpets.Adapters;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.patchpets.R;
import com.patchpets.interfaces.OnItemClickListener;
import com.patchpets.utils.CircleTransform;
import com.patchpets.utils.MyApp;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MyViewHolder> {

    private Context context;
    private OnItemClickListener mListener;

    public MessagesAdapter(Context c, OnItemClickListener mListener) {
        this.context = c;
        this.mListener = mListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_messages, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        try {
            holder.tvTitle.setText("QLD Dog Group" + position);
            holder.tvDesc.setText("Jhon : Are you coming guys.." + position);

            holder.tvTime.setText("10:00");
            MyApp.picasso
                    .load("https://images-na.ssl-images-amazon.com/images/I/51ltYuAUfXL._SY355_.jpg")
                    .transform(new CircleTransform())
                    .placeholder(R.drawable.place_holder)
                    .error(R.drawable.place_holder)
                    .into(holder.ivProfile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return 20;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView ivProfile;
        TextView tvTitle, tvDesc, tvTime;
        ConstraintLayout row_main;

        public MyViewHolder(View itemView) {
            super(itemView);
            ivProfile = itemView.findViewById(R.id.ivProfile);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDesc = itemView.findViewById(R.id.tvDesc);
            tvTime = itemView.findViewById(R.id.tvTime);
            row_main = itemView.findViewById(R.id.row_main);
            row_main.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListener.onItemClicked(getAdapterPosition());
        }
    }
}
