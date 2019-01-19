package com.patchpets.Activitys;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.patchpets.Adapters.FilterDetailAdapter;
import com.patchpets.R;
import com.patchpets.databinding.ActivityFilterDetailBinding;
import com.patchpets.model.ModelFilter;
import com.patchpets.utils.Constants;
import com.patchpets.utils.HeaderBar;
import com.skyfishjy.library.RippleBackground;

import java.util.ArrayList;
import java.util.List;

public class FilterDetailActivity extends AppCompatActivity implements View.OnClickListener {
    RecyclerView rvfilterDetail;
    List<ModelFilter> list1 = new ArrayList<>();
    String type;
    ImageView ivcheck;
    ArrayList<String> data;
    //    public String[] bredddata = {"Affenpinscher", "Afghan Hound", "Airedale Terrier", "Akita", "Alaskan Klee Kai", "Alaskan Malamute", "American Bulldog", "Akita"};
    public String[] ageddata = {"<1", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25"};
    //    public String[] genderdata = {"Both", "Female", "Male"};
//    public String[] statusdata = {"Sexed", "Desexed"};
    public String[] distancedata = {"<1 km", "1 - 5 km", "5 - 10 km", "10 - 15 km", "15 - 20 km", "> 20 km"};
    //    public String[] sizedata = {"Toy", "Small", "Medium", "Large", "Extra Large"};
    private HeaderBar headerBar;
    private ActivityFilterDetailBinding binding;
    RippleBackground rippleBackground;
    LinearLayout viewhalfcircle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_filter_detail);
        type = getIntent().getExtras().getString(Constants.TYPE);

        bindViews();
        rippleBackground = (RippleBackground) findViewById(R.id.content);
        rippleBackground.startRippleAnimation();

        setData();
        setListener();
        Display display = getWindowManager().getDefaultDisplay();
        int width = display.getWidth();
        TextView Views = new TextView(this);
        Views.setLayoutParams(new FrameLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, width / 2));
        viewhalfcircle.addView(Views);

    }

    @Override
    protected void onResume() {
        super.onResume();
        rippleBackground.startRippleAnimation();
    }

    private void bindViews() {
        headerBar = binding.headerBar;
        headerBar.tvTitle.setText(type);
        headerBar.ibLeft.setImageDrawable(getResources().getDrawable(R.drawable.back));
        rvfilterDetail = binding.rvfilterDetail;
        ivcheck = binding.ivcheck;
        viewhalfcircle = binding.viewhalfcircle;
    }

    private void setListener() {
        headerBar.ibLeft.setOnClickListener(this);
        headerBar.ibRight.setOnClickListener(this);
        ivcheck.setOnClickListener(this);
    }

    public void setData() {

        data = getIntent().getStringArrayListExtra(Constants.LIST_DATA);


        if (type.equalsIgnoreCase(Constants.BREEDS)) {
            addDatas(getResources().getStringArray(R.array.breeds));
            // addBreedData();
        } else if (type.equalsIgnoreCase(Constants.AGE)) {
            addDatas(ageddata);
            //addAgeData();
        } else if (type.equalsIgnoreCase(Constants.GENDER)) {
            addDatas(getResources().getStringArray(R.array.gender));
            // addGenderData();
        } else if (type.equalsIgnoreCase(Constants.STATUS)) {
            addDatas(getResources().getStringArray(R.array.status));
            // addStatusData();
        } else if (type.equalsIgnoreCase(Constants.DISTANCE)) {
            addDatas(distancedata);
            // addDistanceData();
        } else if (type.equalsIgnoreCase(Constants.SIZE)) {
            addDatas(getResources().getStringArray(R.array.sizeOfDog));
            // addSizeData();
        }

        FilterDetailAdapter ra = new FilterDetailAdapter(list1, type);
        rvfilterDetail.setAdapter(ra);
        rvfilterDetail.setLayoutManager(new LinearLayoutManager(this));
    }

    public void addDatas(String[] mydata) {
        for (int i = 0; i < mydata.length; i++) {
            ModelFilter modelFilter = new ModelFilter();
            modelFilter.setTitle(mydata[i]);
            if (data.contains(mydata[i])) {
                modelFilter.setIschecked(true);
            }
            list1.add(modelFilter);
        }
    }

    /* public void addGenderData() {
         for (int i = 0; i < genderdata.length; i++) {
             ModelFilter modelFilter = new ModelFilter();
             modelFilter.setTitle(genderdata[i]);
             if (data.contains(genderdata[i])) {
                 modelFilter.setIschecked(true);
             }
             list1.add(modelFilter);
         }
     }

     public void addStatusData() {
         for (int i = 0; i < statusdata.length; i++) {
             ModelFilter modelFilter = new ModelFilter();
             modelFilter.setTitle(statusdata[i]);
             if (data.contains(statusdata[i])) {
                 modelFilter.setIschecked(true);
             }
             list1.add(modelFilter);
         }
     }

     public void addDistanceData() {
         for (int i = 0; i < distancedata.length; i++) {
             ModelFilter modelFilter = new ModelFilter();
             modelFilter.setTitle(distancedata[i]);
             if (data.contains(distancedata[i])) {
                 modelFilter.setIschecked(true);
             }
             list1.add(modelFilter);
         }
     }

     public void addSizeData() {
         for (int i = 0; i < sizedata.length; i++) {
             ModelFilter modelFilter = new ModelFilter();
             modelFilter.setTitle(sizedata[i]);
             if (data.contains(sizedata[i])) {
                 modelFilter.setIschecked(true);
             }
             list1.add(modelFilter);
         }
     }


     public void addAgeData() {
         for (int i = 0; i < ageddata.length; i++) {
             ModelFilter modelFilter = new ModelFilter();
             modelFilter.setTitle(ageddata[i]);
             if (data.contains(ageddata[i])) {
                 modelFilter.setIschecked(true);
             }
             list1.add(modelFilter);
         }
     }

     public void addBreedData() {
         for (int i = 0; i < bredddata.length; i++) {
             ModelFilter modelFilter = new ModelFilter();
             modelFilter.setTitle(bredddata[i]);
             if (data.contains(bredddata[i])) {
                 modelFilter.setIschecked(true);
             }
             list1.add(modelFilter);
         }

     }
 */
    public void senddata() {
        Intent intent = new Intent();
        intent.putExtra(Constants.TYPE, type);
        data.clear();
        for (int i = 0; i < list1.size(); i++) {
            if (list1.get(i).isIschecked()) {
                if (!data.contains(list1.get(i).getTitle())) {
                    data.add(list1.get(i).getTitle());
                }
            }
        }
        intent.putStringArrayListExtra(Constants.LIST_DATA, data);
        setResult(RESULT_OK, intent);
        finish();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibLeft:
                finish();
                break;
            case R.id.ivcheck:
                senddata();
                break;
        }
    }
}
