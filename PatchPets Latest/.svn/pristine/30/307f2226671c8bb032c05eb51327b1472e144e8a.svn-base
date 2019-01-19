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

public class DogParksAdapter extends RecyclerView.Adapter<DogParksAdapter.MyViewHolder> {

    private Context context;
    private OnItemClickListener mListener;

    public DogParksAdapter(Context c, OnItemClickListener mListener) {
        this.context = c;
        this.mListener = mListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_rv_dog_parks, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        try {
            if (position % 3 == 0) {
                holder.tvDogParkName.setText("Kroll Gardens");
            } else if (position % 3 == 1) {
                holder.tvDogParkName.setText("Colmslie Recreation Reserve");
            } else {
                holder.tvDogParkName.setText("Downfall Creek");
            }
            holder.tvDogParkService.setText("Dog off leash park");
            if (position % 3 == 0) {
                holder.tvDogParkLocation.setText("Kroll Gardens, Clontarf");
            } else if (position % 3 == 1) {
                holder.tvDogParkLocation.setText("Colmslie Recreation Reserve");
            } else {
                holder.tvDogParkLocation.setText("Downfall Creek, Clontarf");
            }
            holder.tvDogParkDistance.setText("2.4 Km");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return 20;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ConstraintLayout layoutMain;
        ImageView ivDogParkImage;
        TextView tvDogParkName, tvDogParkService, tvDogParkLocation, tvDogParkDistance;

        public MyViewHolder(View itemView) {
            super(itemView);
            ivDogParkImage = itemView.findViewById(R.id.ivDogParkImage);
            tvDogParkName = itemView.findViewById(R.id.tvDogParkName);
            tvDogParkService = itemView.findViewById(R.id.tvDogParkService);
            tvDogParkLocation = itemView.findViewById(R.id.tvDogParkLocation);
            tvDogParkDistance = itemView.findViewById(R.id.tvDogParkDistance);
            layoutMain = itemView.findViewById(R.id.layoutMain);
            layoutMain.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListener.onItemClicked(getAdapterPosition());
        }
    }
}
