package com.patchpets.Activitys;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.patchpets.Adapters.PeopleAdapter;
import com.patchpets.R;
import com.patchpets.databinding.ActivityCreateGroupBinding;
import com.patchpets.interfaces.OnDeleteClick;
import com.patchpets.utils.CircleTransform;
import com.patchpets.utils.Constants;
import com.patchpets.utils.HeaderBar;
import com.patchpets.utils.MyApp;

import java.util.ArrayList;
import java.util.List;

public class CreateGroupActivity extends AppCompatActivity implements View.OnClickListener, OnDeleteClick {

    private ActivityCreateGroupBinding binding;
    private HeaderBar headerBar;
    private ConstraintLayout ClAddPeople;
    private PeopleAdapter mAdapter;
    private RecyclerView rvPepole;
    private TextView tvTitle;
    private EditText etGroupName;
    private View viewGroupnameShadow;
    private List<String> peopleList = new ArrayList<>();
    private boolean check = true;
    private ImageView ivProfile;
    private Animation animation;
    private View viewMovable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_group);
        bindViews();
        setListener();

        mAdapter = new PeopleAdapter(this, peopleList, this);
        rvPepole.setLayoutManager(new LinearLayoutManager(this));
        rvPepole.setAdapter(mAdapter);
        tvTitle.setVisibility(View.INVISIBLE);
        viewGroupnameShadow.setVisibility(View.VISIBLE);
        etGroupName.setVisibility(View.VISIBLE);

        if (getIntent().getBooleanExtra(Constants.FROM, false)) {
            setData();
            tvTitle.setVisibility(View.VISIBLE);
            viewGroupnameShadow.setVisibility(View.INVISIBLE);
            etGroupName.setVisibility(View.INVISIBLE);
            headerBar.tvTitle.setText("QLD Dog Group");
            mAdapter.notifyDataSetChanged();
        }

        MyApp.picasso.load("https://images-na.ssl-images-amazon.com/images/I/51ltYuAUfXL._SY355_.jpg").transform(new CircleTransform()).into(ivProfile);
    }

    public void setData() {
        for (int i = 0; i < 20; i++) {
            peopleList.add("User " + i);
        }
    }

    private void bindViews() {
        headerBar = binding.headerBar;
        ClAddPeople = binding.ClAddPeople;
        rvPepole = binding.rvPepole;
        tvTitle = binding.tvTitles;
        etGroupName = binding.etGroupName;
        viewGroupnameShadow = binding.viewGroupnameShadow;
        ivProfile = binding.ivProfile;
        headerBar.tvTitle.setText(getResources().getString(R.string.create_group));
        headerBar.ibLeft.setImageDrawable(getResources().getDrawable(R.drawable.back));
    }

    private void setListener() {
        headerBar.ibLeft.setOnClickListener(this);
        headerBar.ibRight.setOnClickListener(this);
        ClAddPeople.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        try {
            switch (view.getId()) {
                case R.id.ibLeft:
                    onBackPressed();
                    break;

                case R.id.ClAddPeople:
                    final Dialog dialog = new Dialog(this, R.style.FullScreenDialogStyle);
                    dialog.setContentView(R.layout.dialog_add_people);

                    TextView tvFromDirectory = dialog.findViewById(R.id.tvFromDirectory);
                    TextView tvThroughQr = dialog.findViewById(R.id.tvThroughQr);
                    tvFromDirectory.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(CreateGroupActivity.this, ContactListActivity.class);
                            startActivity(intent);
                            dialog.dismiss();
                        }
                    });
                    tvThroughQr.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getApplicationContext(), ScanQrCodeActivity.class);
                            startActivityForResult(intent, 102);
                            dialog.dismiss();
                        }
                    });
                    dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    dialog.show();
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDeleteClick(final int position) {
        try {
            final Dialog dialog = new Dialog(this, R.style.FullScreenDialogStyle);
            dialog.setContentView(R.layout.dialog_delete_people);
            Button btnSubmit = dialog.findViewById(R.id.btn_submit);
            viewMovable = dialog.findViewById(R.id.viewMovable);
            final TextView tvYes = dialog.findViewById(R.id.tvYes);
            final TextView tvNo = dialog.findViewById(R.id.tvNo);
            tvYes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    tvYes.setTextSize(30);
                    tvNo.setTextSize(16);
                    tvYes.setTextColor(getResources().getColor(R.color.colorPrimary));
                    tvNo.setTextColor(getResources().getColor(R.color.email_text));
                    if (!check) {
                        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.right_to_left);
                        viewMovable.startAnimation(animation);
                    }
                    check = true;
                }
            });

            tvNo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    tvNo.setTextSize(30);
                    tvYes.setTextSize(16);
                    tvNo.setTextColor(getResources().getColor(R.color.colorPrimary));
                    tvYes.setTextColor(getResources().getColor(R.color.email_text));
                    if (check) {
                        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.left_to_right);
                        viewMovable.startAnimation(animation);
                    }
                    check = false;
                }
            });

            tvNo.performClick();

            btnSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (check == true) {
                        peopleList.remove(position);
                        mAdapter.notifyDataSetChanged();
                        if (peopleList.size() <= 0) {
                            tvTitle.setVisibility(View.GONE);
                            viewGroupnameShadow.setVisibility(View.VISIBLE);
                            etGroupName.setVisibility(View.VISIBLE);
                        }
                    }
                    dialog.dismiss();
                }
            });
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 102) {
                setData();
                tvTitle.setVisibility(View.VISIBLE);
                viewGroupnameShadow.setVisibility(View.INVISIBLE);
                etGroupName.setVisibility(View.INVISIBLE);
                headerBar.tvTitle.setText("QLD Dog Group");
                mAdapter.notifyDataSetChanged();
            }
        }
    }
}
