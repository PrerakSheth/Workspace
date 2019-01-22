package com.patchpets.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.patchpets.R;

import java.util.ArrayList;

public class AdvertisementAdapter extends PagerAdapter {

    private final ArrayList<Integer> alImages = new ArrayList<>();
    private int[] mResources;
    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public AdvertisementAdapter(Context mContext, int[] mResources) {
        this.mContext = mContext;
        this.mResources = mResources;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mResources.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == ((ConstraintLayout) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.item_advertisement_banner, container, false);
        ImageView ivAdBanner = itemView.findViewById(R.id.ivAdBanner);
        ivAdBanner.setImageResource(mResources[position]);
        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ConstraintLayout) object);
    }
}
