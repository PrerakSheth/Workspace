package com.konkr.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.konkr.Activities.AdvertismentDetailsActivity;
import com.konkr.Models.Advertisement;
import com.konkr.R;
import com.konkr.SharedPreferences.SessionManager;
import com.konkr.Utils.GlobalData;
import com.konkr.Webservices.WebField;

import java.util.ArrayList;


public class AdCardAdapter extends PagerAdapter {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Advertisement.AdvertiseListBean> alAdCards;
    int from;

    public AdCardAdapter(Context context, ArrayList<Advertisement.AdvertiseListBean> alAdCards, int from) {
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.alAdCards = alAdCards;
        this.from = from;
    }

    @Override
    public int getCount() {
        return alAdCards.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((ConstraintLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View itemView = inflater.inflate(R.layout.raw_adcard, container, false);

//         Declare Variables
        SimpleDraweeView ivAdCardSearch;
        SimpleDraweeView ivAdCardPartner;

        ivAdCardSearch = (SimpleDraweeView) itemView.findViewById(R.id.ivAdCardSearch);
        ivAdCardPartner = (SimpleDraweeView) itemView.findViewById(R.id.ivAdCardPartner);

        if (from == 1) {
            ivAdCardSearch.setVisibility(View.VISIBLE);
            ivAdCardPartner.setVisibility(View.GONE);
            ivAdCardSearch.setImageURI(Uri.parse(alAdCards.get(position).getLogo()));
        }
        if (from == 0) {
            ivAdCardPartner.setVisibility(View.VISIBLE);
            ivAdCardSearch.setVisibility(View.GONE);
            ivAdCardPartner.setImageURI(Uri.parse(alAdCards.get(position).getLogo()));
        }

        ((ViewPager) container).addView(itemView);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AdvertismentDetailsActivity.class);
                if (from == 1) {
                    intent.putExtra(GlobalData.FROM, GlobalData.SEARCH_ADVERTISEMENT);
                }
                if (from == 0) {
                    intent.putExtra(GlobalData.FROM, GlobalData.PARTNER_ADVERTISEMENT);
                }
                intent.putExtra(GlobalData.ADVERTISEMENT, alAdCards.get(position));
                context.startActivity(intent);
            }
        });

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        ((ViewPager) container).removeView((LinearLayout) object);
        container.removeView((ConstraintLayout) object);
    }
}
