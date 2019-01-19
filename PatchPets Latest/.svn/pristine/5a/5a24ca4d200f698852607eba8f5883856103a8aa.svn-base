package com.patchpets.Adapters;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.patchpets.R;
import com.patchpets.model.ModelFilter;
import com.patchpets.utils.Constants;

import java.util.List;

public class FilterDetailAdapter extends RecyclerView.Adapter<FilterDetailAdapter.MyViewHolder> {

    List<ModelFilter> mydata;
    String type;
    private int lastCheckedPosition = -1;

    private static int lastCheckedPos = 0;

    public FilterDetailAdapter(List<ModelFilter> mydata, String type) {
        this.mydata = mydata;
        this.type = type;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_filter_detail, viewGroup, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int i) {
        myViewHolder.title.setText(mydata.get(i).getTitle());

        if (type.equalsIgnoreCase(Constants.GENDER)) {
            myViewHolder.chk.setChecked(i == lastCheckedPosition);
        }

        if (mydata.get(i).isIschecked()) {
            myViewHolder.chk.setChecked(true);
        }

        myViewHolder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type.equalsIgnoreCase(Constants.GENDER)) {
                    lastCheckedPosition = i;
                    if (myViewHolder.chk.isChecked()) {
                        myViewHolder.chk.setChecked(false);
                    } else {
                        myViewHolder.chk.setChecked(true);
                    }
                } else {
                    if (myViewHolder.chk.isChecked()) {
                        myViewHolder.chk.setChecked(false);
                    } else {
                        myViewHolder.chk.setChecked(true);
                    }
                }
            }
        });

        myViewHolder.chk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, final boolean b) {
                if (type.equalsIgnoreCase(Constants.GENDER)) {
                    lastCheckedPosition = i;
                    cleardatas();
                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {
                            mydata.get(i).setIschecked(b);
                            notifyDataSetChanged();
                        }
                    });
                } else {
                    mydata.get(i).setIschecked(b);
                }
            }
        });
    }

    void cleardatas() {
        for (int i = 0; i < mydata.size(); i++) {
            mydata.get(i).setIschecked(false);
        }
    }

    @Override
    public int getItemCount() {
        return mydata.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        CheckBox chk;
        ConstraintLayout layoutMain;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_title);
            chk = itemView.findViewById(R.id.check);
            layoutMain = itemView.findViewById(R.id.layoutMain);
        }
    }
}
