package com.patchpets.Adapters;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.patchpets.R;
import com.patchpets.interfaces.OnItemClickListener;
import com.patchpets.utils.LogM;

public class DirectoryAdapter extends RecyclerView.Adapter<DirectoryAdapter.MyViewHolder> {

    private Context context;
    private OnItemClickListener mListener;

    public DirectoryAdapter(Context c, OnItemClickListener mListener) {
        this.context = c;
        this.mListener = mListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_rv_directory, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        try {
            holder.tvBusinessTitle.setText("Business " + position);
            holder.tvBusinessService.setText("Main service 1, service 2, service 3, service 4, service 5, service 6");
            if (position % 3 == 0) {
                holder.tvBusinessLocation.setText("Kroll Gardens, Clontarf");
            } else if (position % 3 == 1) {
                holder.tvBusinessLocation.setText("Colmslie Recreation Reserve");
            } else {
                holder.tvBusinessLocation.setText("Downfall Creek, Clontarf");
            }
            holder.tvBusinessPhoneNumber.setText("+61 12345 67890");
            holder.ibMessage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LogM.e("Message clicked " + position);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return 20;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView ivCompanyLogo;
        ImageButton ibMessage;
        ConstraintLayout layoutMain;
        TextView tvBusinessTitle, tvBusinessService, tvBusinessLocation, tvBusinessPhoneNumber;

        public MyViewHolder(View itemView) {
            super(itemView);
            ivCompanyLogo = itemView.findViewById(R.id.ivCompanyLogo);
            tvBusinessTitle = itemView.findViewById(R.id.tvBusinessTitle);
            tvBusinessService = itemView.findViewById(R.id.tvBusinessService);
            tvBusinessLocation = itemView.findViewById(R.id.tvBusinessLocation);
            tvBusinessPhoneNumber = itemView.findViewById(R.id.tvBusinessPhoneNumber);
            ibMessage = itemView.findViewById(R.id.ibMessage);
            layoutMain = itemView.findViewById(R.id.layoutMain);
            layoutMain.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListener.onItemClicked(getAdapterPosition());
        }
    }
}
