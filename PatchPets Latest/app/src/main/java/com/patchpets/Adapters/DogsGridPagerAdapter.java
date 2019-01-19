package com.patchpets.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.patchpets.R;
import com.patchpets.interfaces.OnItemClickListener;
import com.patchpets.model.DogDetails;
import com.patchpets.utils.ClickableViewPager;
import com.patchpets.utils.Constants;

import java.util.ArrayList;
import java.util.Calendar;

public class DogsGridPagerAdapter extends RecyclerView.Adapter<DogsGridPagerAdapter.MyViewHolder> {

    private Context context;
    private OnItemClickListener mListener;
    private ArrayList<DogDetails> alDogs;
    private int dragThreshold = 10, downX = 0, downY = 0;

    public DogsGridPagerAdapter(Context c, OnItemClickListener mListener, ArrayList<DogDetails> alDogs) {
        this.context = c;
        this.mListener = mListener;
        this.alDogs = alDogs;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_rv_data_dog_pager, parent, false);
        return new MyViewHolder(view);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        try {
            ArrayList<String> alString = new ArrayList<>();
            alString.add(alDogs.get(position).getDogProfilePic());
            alString.addAll(alDogs.get(position).getDogPics());

            SlidingImagePagerAdapter viewPagerAdapter = new SlidingImagePagerAdapter(context, alString);
            holder.viewPager.setAdapter(viewPagerAdapter);
            holder.viewPager.setOnTouchListener(new View.OnTouchListener() {
                private long startClickTime;

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        startClickTime = Calendar.getInstance().getTimeInMillis();
                        downX = (int) event.getRawX();
                        downY = (int) event.getRawY();
                    } else if (event.getAction() == MotionEvent.ACTION_UP) {
                        long clickDuration = Calendar.getInstance().getTimeInMillis() - startClickTime;
                        if (clickDuration < Constants.MAX_CLICK_DURATION) {
                            mListener.onItemClicked(position);
                        }
                    } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                        int distanceX = Math.abs((int) event.getRawX() - downX);
                        int distanceY = Math.abs((int) event.getRawY() - downY);

                        if (distanceY > distanceX && distanceY > dragThreshold) {
                            v.getParent().requestDisallowInterceptTouchEvent(false);
                            return true;
                        }
                    }
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                    return false;
                }
            });

            if (alDogs.get(position).getIsUserActive() == 1) {
                holder.ibDogPark.setImageDrawable(context.getResources().getDrawable(R.drawable.home_paw_green));
            } else {
                holder.ibDogPark.setImageDrawable(context.getResources().getDrawable(R.drawable.home_paw_grey));
            }
            holder.tvUser.setText(alDogs.get(position).getDogName());
            holder.tvLocation.setText(alDogs.get(position).getLocation());
            holder.tvDistance.setText(alDogs.get(position).getDistance() == null ? "" : alDogs.get(position).getDistance() + "Km");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return alDogs.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout clItem;
        ClickableViewPager viewPager;
        ImageButton ibDogPark;
        TextView tvDistance, tvUser, tvLocation;

        public MyViewHolder(View itemView) {
            super(itemView);
            clItem = itemView.findViewById(R.id.clItem);
            viewPager = itemView.findViewById(R.id.viewPager);
            ibDogPark = itemView.findViewById(R.id.ibDogPark);
            tvDistance = itemView.findViewById(R.id.tvDistance);
            tvUser = itemView.findViewById(R.id.tvUser);
            tvLocation = itemView.findViewById(R.id.tvLocation);
        }
    }
}
