package com.patchpets.Adapters;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.patchpets.R;
import com.patchpets.utils.MyApp;

import java.util.ArrayList;

public class SlidingImagePagerAdapter extends PagerAdapter {

    private ArrayList<String> alImages;
    private LayoutInflater inflater;
    private Context context;

    public SlidingImagePagerAdapter(Context context, ArrayList<String> alImages) {
        this.context = context;
        this.alImages = alImages;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return alImages.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View imageLayout = inflater.inflate(R.layout.item_rv_data_dog_view_pager_item, view, false);
        assert imageLayout != null;
        final ImageView imageView = imageLayout.findViewById(R.id.ivItems);
        MyApp.picasso.invalidate(alImages.get(position));
        MyApp.picasso
                .load(alImages.get(position))
                .fit().centerCrop()
                .into(imageView);
        view.addView(imageLayout, 0);
        return imageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }
}
