package com.konkr.Adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.konkr.R;

import java.util.ArrayList;

/**
 * Created by Android on 3/7/2018.
 */

public class ImagePagerAdapter extends PagerAdapter {
    Context context;
    LayoutInflater layoutInflater;

    ArrayList<String> arrayList;

    public ImagePagerAdapter(Context context, ArrayList<String> arrayList) {
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.arrayList = arrayList;
    }


    @Override
    public int getCount() {
        if (arrayList != null) {
            return arrayList.size();
        }
        return 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = layoutInflater.inflate(R.layout.activity_image_viewpager, container, false);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.viewPagerItem_image1);
//
//        Picasso.with(context).load(arrayList.get(position))
//                .placeholder(R.drawable.image_uploading)
//                .error(R.drawable.image_not_found).into(imageView);

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}
