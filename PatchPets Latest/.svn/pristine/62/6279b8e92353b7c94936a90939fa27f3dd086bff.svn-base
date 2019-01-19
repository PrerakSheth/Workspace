package com.patchpets.Activitys;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.patchpets.R;
import com.patchpets.databinding.ActivityHowTheAppWorksBinding;
import com.patchpets.utils.HeaderBar;

public class HowTheAppWorksActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityHowTheAppWorksBinding binding;
//    private ViewPager vwPageIntroduction;
    private HeaderBar headerBar;
//    private PageIndicatorView pageIndicatorView;

    int[] mResources = {
            R.drawable.camera,
            R.drawable.add_dog_age_arrow,
            R.drawable.search,
            R.drawable.next
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_how_the_app_works);

        bindViews();
        setListner();
        setHeaderBar();

//        CustomPagerAdapter customPagerAdapter = new CustomPagerAdapter(HowTheAppWorksActivity.this);
//        vwPageIntroduction.setAdapter(customPagerAdapter);
//        pageIndicatorView.setViewPager(vwPageIntroduction);
    }

    private void setListner() {
        headerBar.ibLeft.setOnClickListener(this);
    }

    private void bindViews() {
        try {
            headerBar = binding.headerBar;
//            vwPageIntroduction = binding.vwPageIntroduction;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setHeaderBar() {
        try {
            headerBar.ibLeft.setVisibility(View.VISIBLE);
            headerBar.ibLeft.setImageResource(R.drawable.back);
            headerBar.tvTitle.setVisibility(View.VISIBLE);
            headerBar.tvTitle.setText(R.string.how_app_title);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibLeft:
                onBackPressed();
                break;
        }
    }

    class CustomPagerAdapter extends PagerAdapter {

        Context mContext;
        LayoutInflater mLayoutInflater;

        public CustomPagerAdapter(Context context) {
            mContext = context;
            mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return mResources.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((ConstraintLayout) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View itemView = mLayoutInflater.inflate(R.layout.row_introduction, container, false);

            ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);
            imageView.setImageResource(mResources[position]);
            container.addView(itemView);

            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((ConstraintLayout) object);
        }
    }
}
