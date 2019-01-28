package com.patchpets.Adapters;

import android.annotation.SuppressLint;
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
import com.patchpets.model.DogDetails;
import com.patchpets.utils.Constants;
import com.patchpets.utils.MyApp;

import java.util.ArrayList;

public class DogsGridImageAdapter extends RecyclerView.Adapter<DogsGridImageAdapter.MyViewHolder> {

    private Context context;
    private OnItemClickListener mListener;
    private String className;
    private ArrayList<DogDetails> alDogs;

    public DogsGridImageAdapter(Context c, OnItemClickListener mListener, ArrayList<DogDetails> alDogs) {
        this.context = c;
        this.mListener = mListener;
        this.alDogs = alDogs;
        className = context.getClass().getSimpleName();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_rv_data_dog_image, parent, false);
        return new MyViewHolder(view);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        try {
            switch (className) {
                case Constants.FavouritesDogActivity:
                    if (alDogs.get(position).getIsUserActive() == 1) {
                        holder.ibDogStatus.setImageDrawable(context.getResources().getDrawable(R.drawable.home_paw_green));
                    } else {
                        holder.ibDogStatus.setImageDrawable(context.getResources().getDrawable(R.drawable.home_paw_grey));
                    }
                    holder.tvUser.setText(alDogs.get(position).getDogName());
                    holder.tvLocation.setText(alDogs.get(position).getLocation());
                    holder.tvDistance.setVisibility(View.GONE);
                    break;
                default:
                    holder.ibDogStatus.setVisibility(View.GONE);
                    holder.tvDistance.setVisibility(View.GONE);
                    holder.tvUser.setVisibility(View.GONE);
                    holder.tvLocation.setVisibility(View.GONE);
                    break;
            }

            MyApp.picasso.invalidate(alDogs.get(position).getDogProfilePic());
            MyApp.picasso
                    .load(alDogs.get(position).getDogProfilePic())
                    .placeholder(R.drawable.place_holder)
                    .error(R.drawable.place_holder)
                    .fit().centerCrop()
//                    .transform(new RoundedCornersTransformation(20, 0))
                    .into(holder.imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return alDogs.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ConstraintLayout clItem;
        ImageView imageView;
        ImageButton ibDogStatus;
        TextView tvDistance, tvUser, tvLocation;

        public MyViewHolder(View itemView) {
            super(itemView);
            clItem = itemView.findViewById(R.id.clItem);
            imageView = itemView.findViewById(R.id.imageView);
            ibDogStatus = itemView.findViewById(R.id.ibDogStatus);
            tvDistance = itemView.findViewById(R.id.tvDistance);
            tvUser = itemView.findViewById(R.id.tvUser);
            tvLocation = itemView.findViewById(R.id.tvLocation);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListener.onItemClicked(getAdapterPosition());
        }
    }
}
