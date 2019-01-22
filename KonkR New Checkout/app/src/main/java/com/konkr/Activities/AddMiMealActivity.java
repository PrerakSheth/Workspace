package com.konkr.Activities;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.constraint.ConstraintLayout;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.google.android.gms.common.util.ArrayUtils;
import com.konkr.Models.Meals;
import com.konkr.Models.MiMealModel;
import com.konkr.Models.MyMeals;
import com.konkr.Models.SearchMeal;
import com.konkr.Models.SelectDeselectExpression;
import com.konkr.R;
import com.konkr.SharedPreferences.SessionManager;
import com.konkr.Utils.AlertDialogUtility;
import com.konkr.Utils.Base64;
import com.konkr.Utils.BaseActivity;
import com.konkr.Utils.ConnectivityDetector;
import com.konkr.Utils.GlobalData;
import com.konkr.Utils.GlobalMethods;
import com.konkr.Utils.Headerbar;
import com.konkr.Utils.KeyboardUtility;
import com.konkr.Utils.LogM;
import com.konkr.Utils.MyEditText;
import com.konkr.Utils.MyTextView;
import com.konkr.Utils.PermissionsHelper;
import com.konkr.Webservices.GetJsonWithAndroidNetworkingLib;
import com.konkr.Webservices.OnUpdateListener;
import com.konkr.Webservices.WebField;
import com.konkr.databinding.ActivityAddMiMealBinding;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import static com.konkr.Activities.MiSupplimentActivity.getImageContentUri;

public class AddMiMealActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener {

    private ActivityAddMiMealBinding binding;
    private SimpleDraweeView ivMealPhoto;
    private Headerbar headerBar;
    private MyTextView tvMealCategory, tvAddMore;
    private Spinner spinnerMeal;
    private LinearLayout linearLayout;
    private Activity context;
    private View snackBarView;
    private boolean flag = true;
    private ImageView ivPhoto;
    private AutoCompleteTextView etMealName;
    private MyEditText etQuantity, etUnit;
    private MyTextView tvMealName;
    private Timer timer = new Timer();
    private final long DELAY = 1000; // in ms
    private ArrayList<String> spinnerList = new ArrayList<>();
    int selectedItemPosition;
    private ArrayAdapter adapter;
    private String imageUrl = "";
    int OtherUserId;

    private boolean displayOrEdit;
    private ArrayList<SearchMeal.MealSearchNewDataBean> mealSearchNewDataBeanArrayList = new ArrayList<>();
    private ArrayAdapter<SearchMeal.MealSearchNewDataBean> autoAdapter;

    private Uri mImageUri;
    private File filepath, cropfilepath;
    private String strProPicBase64 = "";
    private static final int PHOTO_PICK = 243, PHOTO_CLICK = 891, PIC_CROP = 152;
    private ArrayList<MiMealModel> alMiMeal = new ArrayList<>();
    private ArrayList<AutoCompleteTextView> alTvFoodName = new ArrayList<>();
    private ArrayList<MyEditText> alEtQuantity = new ArrayList<>();
    private ArrayList<MyEditText> alEtUnit = new ArrayList<>();
    private ArrayList<MyTextView> alTvMealName = new ArrayList<>();


    private int counter = 0;
    private String action;
    private int mealType;
    private int[] mArray;
    private ArrayList<Integer> mealIdArrayList = new ArrayList<>();
    private int mealCount;
    private int count = 1;

    private ArrayList<Meals.Meal> alMeal;
    private MyEditText etOtherMealType;

    // -----------expression ------------------------------------------------
    private ConstraintLayout clExpression, rowCom;
    boolean isAlreadyLiked;
    boolean isExpressVisible;
    private MyTextView tvLikeCount;
    private MyTextView tvCommentCount;
    private SimpleDraweeView ivLike, ivComment, ivGoal, ivAdmiring, ivInspiring;
    private int goal, admiring, inspiring, expressionCount, commentsCount;
    private int homeFeedId;
//------------------------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_mi_meal);
        context = AddMiMealActivity.this;
        snackBarView = findViewById(android.R.id.content);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        bindViews();
        getIntentData();
        setheaderBar();
        setListener();
        setSpinnerData();
        if (mArray != null) {
            ivLike.setVisibility(View.GONE);
            ivComment.setVisibility(View.GONE);
            tvCommentCount.setVisibility(View.GONE);
            tvLikeCount.setVisibility(View.GONE);
            addView();
        } else {
            setViewsForEditing();
        }


    }

    private void setViewsForEditing() {
        if (alMeal != null && alMeal.size() > 0) {
            for (int i = 0; i < alMeal.size(); i++) {
                addAndSetViews();
                etMealName.setText(alMeal.get(i).getFoodName());
                tvMealName.setText(alMeal.get(i).getFoodName());
                etQuantity.setText("" + alMeal.get(i).getQuantity());
                etUnit.setText("" + alMeal.get(i).getUnit());
                mealIdArrayList.add(alMeal.get(i).getMealId());

                etMealName.setVisibility(View.GONE);
                tvMealName.setVisibility(View.VISIBLE);
                tvMealName.setSelected(true);
            }

            counter++;

            int mealType = alMeal.get(0).getMealType();

            DisableClicksandEditing(alTvFoodName, alEtQuantity, alEtUnit, false);

            if (mealType == 1) {
                tvMealCategory.setText(GlobalData.BREAKFAST);
            } else if (mealType == 2) {
                tvMealCategory.setText(GlobalData.LUNCH);
            } else if (mealType == 3) {
                tvMealCategory.setText(GlobalData.SNACK);
            } else if (mealType == 4) {
                tvMealCategory.setText(GlobalData.DINNER);
            } else if (mealType == 5) {
                tvMealCategory.setText(GlobalData.OTHER);
                etOtherMealType.setText(alMeal.get(0).getMealName());
            }
        }
    }

    private void DisableClicksandEditing(ArrayList<AutoCompleteTextView> alTvFoodName, ArrayList<MyEditText> alEtQuantity, ArrayList<MyEditText> alEtUnit, boolean edit) {


        if (!edit) {
            // we are disabling edit mode here and click events
            etOtherMealType.setEnabled(false);
            tvMealCategory.setClickable(false);
            tvMealCategory.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
            // ivMealPhoto.setClickable(false);
            tvAddMore.setVisibility(View.GONE);

            if ((SessionManager.getIsProfileSetup(AddMiMealActivity.this)).equalsIgnoreCase("0")) {
                clExpression.setVisibility(View.GONE);
            } else {
                clExpression.setVisibility(View.VISIBLE);
            }

            for (int i = 0; i < alTvFoodName.size(); i++) {

                alTvFoodName.get(i).setEnabled(false);
                alEtQuantity.get(i).setEnabled(false);
                alEtUnit.get(i).setEnabled(false);
            }
        } else {

            etOtherMealType.setEnabled(true);
            tvMealCategory.setClickable(true);
            //  ivMealPhoto.setClickable(true);
            tvMealCategory.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.down_arrow, 0);

            tvAddMore.setVisibility(View.VISIBLE);
            LogM.LogE("size of its" + alTvFoodName.size() + "" + alTvMealName.size());
            for (int i = 0; i < alTvFoodName.size(); i++) {
                alTvFoodName.get(i).setEnabled(true);
                alEtQuantity.get(i).setEnabled(true);
                alEtUnit.get(i).setEnabled(true);

                alTvMealName.get(i).setVisibility(View.GONE);
                alTvFoodName.get(i).setVisibility(View.VISIBLE);
            }

        }


    }


    private void getIntentData() {

        if (getIntent().getExtras() != null) {

            OtherUserId = getIntent().getIntExtra(GlobalData.OTHER_USER_ID, 0);
            goal = getIntent().getIntExtra(GlobalData.GOAL, 0);
            admiring = getIntent().getIntExtra(GlobalData.ADMIRING, 0);
            inspiring = getIntent().getIntExtra(GlobalData.OTHER_USER_ID, 0);

            LogM.LogE("OTHER USR ID==>" + OtherUserId);


            if (getIntent().getStringExtra(GlobalData.AddMeal) != null) {
                action = "add";
            } else {
                action = "edit";
            }

            mealCount = getIntent().getIntExtra("noOfMeal", 0);
            LogM.LogE("number of meal==>" + mealCount);
            mArray = getIntent().getIntArrayExtra("myArray");
            alMeal = getIntent().getExtras().getParcelableArrayList(GlobalData.MEAL);


            if (alMeal != null) {
                expressionCount = alMeal.get(0).getExpressionCount();
                commentsCount = alMeal.get(0).getCommentCount();
                homeFeedId = Integer.parseInt(alMeal.get(0).getHomeFeedId());
                goal = alMeal.get(0).getIs_goals();
                inspiring = alMeal.get(0).getIs_inspiring();
                admiring = alMeal.get(0).getIs_admiring();

                tvCommentCount.setText("" + commentsCount);
                tvLikeCount.setText("" + expressionCount);
                isAlreadyLiked = true;
                if (goal > 0) {
                    ivLike.setImageResource(R.drawable.goal_green);
                } else if (inspiring > 0) {
                    ivLike.setImageResource(R.drawable.inspiration_green);
                } else if (admiring > 0) {
                    ivLike.setImageResource(R.drawable.admiring_green);
                } else {
                    isAlreadyLiked = false;
                    ivLike.setImageResource(R.drawable.like);
                }
                LogM.LogE("OTHER USR ID==>" + homeFeedId);

                for (int i = 0; i < alMeal.size(); i++) {
                    if (!alMeal.get(i).getMealPhoto().equals("")) {
                        ImagePipeline imagePipeline = Fresco.getImagePipeline();
                        imagePipeline.evictFromCache(Uri.parse(imageUrl));
                        imagePipeline.clearCaches();
                        imageUrl = alMeal.get(i).getMealPhoto();
                        break;
                    }
                }

                ivMealPhoto.setImageURI(imageUrl);
            }

            LogM.LogE("expressionCount==>" + expressionCount);
            LogM.LogE("Comments Count==>" + commentsCount);
            LogM.LogE("HOME Feed ID==>" + homeFeedId);
            // below lines doe nothing except showing logs and checking values
            if (mArray != null) {
                LogM.LogE("size of added array==>" + mArray.length);
                {
                    for (int i = 0; i < mArray.length; i++) {
                        LogM.LogE("check==> " + "   " + i + "  " + mArray[i]);
                    }
                    LogM.LogE("0 Position====>" + mArray[0]);
                    LogM.LogE("1 Position====>" + mArray[1]);
                    LogM.LogE("2 Position====>" + mArray[2]);
                    LogM.LogE("3 Position====>" + mArray[3]);
                }

            }
        }
    }

    private void setSpinnerData() {
        if (mArray != null) {
            if (mArray[0] < 1) {
                spinnerList.add(GlobalData.BREAKFAST);
            }
            if (mArray[1] < 1) {
                spinnerList.add(GlobalData.LUNCH);
            }
            if (mArray[2] < 1) {
                spinnerList.add(GlobalData.SNACK);
            }
            if (mArray[3] < 1) {
                spinnerList.add(GlobalData.DINNER);
            }
            spinnerList.add(GlobalData.OTHER);
        } else {
            int mealType = alMeal.get(0).getMealType();

            if (mealType == 1) {
                spinnerList.add(GlobalData.BREAKFAST);
            } else if (mealType == 2) {
                spinnerList.add(GlobalData.LUNCH);
            } else if (mealType == 3) {
                spinnerList.add(GlobalData.SNACK);
            } else if (mealType == 4) {
                spinnerList.add(GlobalData.DINNER);
            } else if (mealType == 5) {
                spinnerList.add(GlobalData.OTHER);
            }
        }


        adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, spinnerList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMeal.setAdapter(adapter);
    }

    private void setListener() {
        tvAddMore.setOnClickListener(this);
        headerBar.ibtnLeftOne.setOnClickListener(this);
        tvMealCategory.setOnClickListener(this);
        spinnerMeal.setOnItemSelectedListener(this);
        ivMealPhoto.setOnClickListener(this);
        headerBar.tvRight.setOnClickListener(this);

        ivComment.setOnClickListener(this);
        ivLike.setOnClickListener(this);
        ivGoal.setOnClickListener(this);
        ivInspiring.setOnClickListener(this);
        ivAdmiring.setOnClickListener(this);
        tvLikeCount.setOnClickListener(this);
    }

    private void setheaderBar() {
        headerBar.ibtnLeftOne.setVisibility(View.VISIBLE);
        headerBar.ibtnLeftOne.setImageResource(R.drawable.back);
        headerBar.tvRight.setVisibility(View.VISIBLE);

        if (OtherUserId == 0) {
            headerBar.tvRight.setVisibility(View.VISIBLE);
        } else if (OtherUserId != (SessionManager.getUserId(context))) {
            headerBar.tvRight.setVisibility(View.GONE);
        }


        //
        if (mArray != null) {
            headerBar.tvRight.setText(getResources().getString(R.string.done));
            displayOrEdit = true;
        } else {
            headerBar.tvRight.setText(getResources().getString(R.string.edit));
        }
        headerBar.tvTitle.setVisibility(View.VISIBLE);
        headerBar.tvTitle.setText(R.string.mi_meal_title);
    }

    private void bindViews() {
        headerBar = binding.headerBar;
        tvMealCategory = binding.tvMealCategory;
        spinnerMeal = binding.spinnerMeal;
        linearLayout = binding.parentlayout;
        tvAddMore = binding.tvAddMore;
        ivMealPhoto = binding.ivMealPhoto;
        ivPhoto = binding.ivPhoto;
        etOtherMealType = binding.etOtherMealType;
        rowCom = binding.rowCom;
        clExpression = binding.clExpression;
        ivComment = binding.ivComment;
        ivLike = binding.ivLike;
        ivGoal = binding.ivGoal;
        ivAdmiring = binding.ivAdmiring;
        ivInspiring = binding.ivInspiring;
        tvLikeCount = binding.tvLikeCount;
        tvCommentCount = binding.tvCommentCount;

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.ibtnLeftOne:
                KeyboardUtility.HideKeyboard(context, tvMealCategory);
                setResult(RESULT_CANCELED);
                finish();
                break;

            case R.id.tvAddMore:
                addMoreMeal();
                break;

            case R.id.tvMealCategory:
                spinnerMeal.performClick();
                break;

            case R.id.ivMealPhoto:

                if (!displayOrEdit && !TextUtils.isEmpty(imageUrl)) {
                    Intent intent = new Intent(context, FullScreenProfileImages.class);
                    intent.putExtra(GlobalData.FROM, GlobalData.ADD_MEAL_SCREEN);
                    intent.putExtra(GlobalData.IMAGE_URL, imageUrl);
                    startActivity(intent);
                } else if (displayOrEdit) {
                    PermissionsHelper.permissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.CAMERA}).checkPermissions(AddMiMealActivity.this, new PermissionsHelper.OnPermissionResult() {

                        @Override
                        public void onGranted() {
                            openAlertForPhoto();
                        }

                        @Override
                        public void notGranted() {

                        }
                    });
                }
                break;

            case R.id.tvRight:
                LogM.LogE(" are we editing==>" + displayOrEdit);
                if (displayOrEdit) {
//                    headerBar.tvRight.setText(getResources().getString(R.string.edit));
                    validation();
                } else {
                    displayOrEdit = true;
                    headerBar.tvRight.setText(getResources().getString(R.string.done));
                    //tvMealCategory.setCompoundDrawablesWithIntrinsicBounds(0, 0,  0, 0);
                    DisableClicksandEditing(alTvFoodName, alEtQuantity, alEtUnit, displayOrEdit);
                }

                break;

            case R.id.ivLike:
                if (!isAlreadyLiked) {
                    if (isExpressVisible) {
                        rowCom.setVisibility(View.GONE);
                        isExpressVisible = false;
                    } else {
                        rowCom.setVisibility(View.VISIBLE);
                        isExpressVisible = true;
                    }
                } else {

//---------------------------below code for dislike-----------------------------------------------------------------------
                    if (goal > 0) {
                        callAddexpressionUserMedia("" + GlobalData.EXPRESSION_GOAL, 1, 1);
                    }
                    ;
                    if (inspiring > 0) {
                        callAddexpressionUserMedia("" + GlobalData.EXPRESSION_INSPIRING, 2, 1);
                    }
                    ;
                    if (admiring > 0) {
                        callAddexpressionUserMedia("" + GlobalData.EXPRESSION_ADMIRING, 3, 1);
                    }
//                    ;
                }

                break;
            case R.id.ivGoal:
                callAddexpressionUserMedia("" + GlobalData.EXPRESSION_GOAL, 1, 0);
                break;
            case R.id.ivInspiring:
                callAddexpressionUserMedia("" + GlobalData.EXPRESSION_INSPIRING, 2, 0);
                break;
            case R.id.ivAdmiring:
                callAddexpressionUserMedia("" + GlobalData.EXPRESSION_ADMIRING, 3, 0);
                break;
            case R.id.ivComment:
                Intent intent = new Intent(context, CommentsActivity.class);
                intent.putExtra(GlobalData.HOME_FEED_ID, String.valueOf(homeFeedId));
                startActivityForResult(intent, GlobalData.MEAL_DETAIL_REQ);
                break;

            case R.id.tvLikeCount:
                try {
                    if (Integer.parseInt(tvLikeCount.getText().toString().trim()) > 0) {
                        Intent pIntent = new Intent(context, PostExpressionActivity.class);
                        pIntent.putExtra(GlobalData.IS_FROM, GlobalData.DETAILS);
                        pIntent.putExtra(GlobalData.HOME_FEED_ID, alMeal.get(0).getHomeFeedId());
//                        if (alMeal.get(0).getIs_goals() == 1) {
//                            pIntent.putExtra(GlobalData.EXPRESSION, GlobalData.EXPRESSION_GOAL);
//                        } else if (alMeal.get(0).getIs_inspiring() == 1) {
//                            pIntent.putExtra(GlobalData.EXPRESSION, GlobalData.EXPRESSION_INSPIRING);
//                        } else if (alMeal.get(0).getIs_admiring() == 1) {
//                            pIntent.putExtra(GlobalData.EXPRESSION, GlobalData.EXPRESSION_ADMIRING);
//                        }

                        if (goal == 1) {
                            pIntent.putExtra(GlobalData.EXPRESSION, GlobalData.EXPRESSION_GOAL);
                        } else if (inspiring == 1) {
                            pIntent.putExtra(GlobalData.EXPRESSION, GlobalData.EXPRESSION_INSPIRING);
                        } else if (admiring == 1) {
                            pIntent.putExtra(GlobalData.EXPRESSION, GlobalData.EXPRESSION_ADMIRING);
                        }
                        startActivityForResult(pIntent, 132);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            default:
                rowCom.setVisibility(View.GONE);
                isExpressVisible = false;

        }
    }

    private void validation() {
        int intTotalMeals = alTvFoodName.size() - 1;
        String strFoodName = alTvFoodName.get(intTotalMeals).getText().toString().trim();
        String strQuantity = alEtQuantity.get(intTotalMeals).getText().toString().trim();
        String strUnit = alEtUnit.get(intTotalMeals).getText().toString().trim();
        LogM.LogE("Food name ===>" + strFoodName.length());
        LogM.LogE("Size of  alTvFoodName, alEtQuantity,alEtUnit ===>" + alTvFoodName.size() + ",,==>" + alTvFoodName.size() + ",==>" + alEtUnit.size());

        String mealcat = tvMealCategory.getText().toString();

        if (mealcat.equalsIgnoreCase(GlobalData.BREAKFAST)) {
            mealType = 1;
        } else if (mealcat.equalsIgnoreCase(GlobalData.LUNCH)) {
            mealType = 2;
        } else if (mealcat.equalsIgnoreCase(GlobalData.SNACK)) {
            mealType = 3;
        } else if (mealcat.equalsIgnoreCase(GlobalData.DINNER)) {
            mealType = 4;
        } else if (mealcat.equalsIgnoreCase(GlobalData.OTHER)) {
            mealType = 5;
        }

        LogM.LogE("Meal Type====>" + mealType);

        if (etOtherMealType.getVisibility() == View.VISIBLE && etOtherMealType.getText().toString().length() == 0) {

            AlertDialogUtility.showSnakeBar(getResources().getString(R.string.add_title), snackBarView, context);
        } else {
            if (alTvFoodName.size() == 1) {
                if (strFoodName.length() == 0) {
                    AlertDialogUtility.showSnakeBar(getResources().getString(R.string.add_meal_name), snackBarView, context);
                } else if (strQuantity.length() == 0) {
                    AlertDialogUtility.showSnakeBar(getResources().getString(R.string.add_meal_quantity), snackBarView, context);
                } else if (strProPicBase64.equalsIgnoreCase(null)) {
                    AlertDialogUtility.showSnakeBar("Please add unit", snackBarView, context);
                } else {
                    alMiMeal.add(new MiMealModel(strFoodName, strQuantity, strUnit, "", mealIdArrayList.size() > 0 ? mealIdArrayList.get(0) : 0, etOtherMealType.getText().toString().trim()));

                    callAddMealAPI();

                }
            } else {

                if (strFoodName.length() == 0 && strQuantity.length() == 0 && strUnit.length() == 0) {
                    alMiMeal.clear();
                    for (int i = 0; i < alTvFoodName.size(); i++) {
                        if (alTvFoodName.get(i).getText().toString().trim().length() > 0) {
                            alMiMeal.add(new MiMealModel(alTvFoodName.get(i).getText().toString().trim(),
                                    alEtQuantity.get(i).getText().toString().trim(),
                                    alEtUnit.get(i).getText().toString().trim(),
                                    "",
                                    mealIdArrayList.size() > 0 ? mealIdArrayList.get(i) : 0, etOtherMealType.getText().toString().trim()));
                        }
                    }
                    // alMiMeal.add(new MiMealModel(0,strFoodName, strQuantity, strUnit, ""));
                    callAddMealAPI();
                } else if (strFoodName.length() == 0) {
                    AlertDialogUtility.showSnakeBar(getResources().getString(R.string.add_meal_name), snackBarView, context);
                } else if (strQuantity.length() == 0) {
                    AlertDialogUtility.showSnakeBar(getResources().getString(R.string.add_meal_quantity), snackBarView, context);
                }
//            else if (strUnit.length() == 0) {
//                AlertDialogUtility.showSnakeBar(getResources().getString(R.string.add_meal_quantity), snackBarView, context);
//            }
                else if (strProPicBase64.equalsIgnoreCase(null)) {
                    AlertDialogUtility.showSnakeBar("Please add unit", snackBarView, context);
                } else {
                    alMiMeal.clear();
                    for (int i = 0; i < alTvFoodName.size(); i++) {
                        alMiMeal.add(new MiMealModel(alTvFoodName.get(i).getText().toString().trim(),
                                alEtQuantity.get(i).getText().toString().trim(),
                                alEtUnit.get(i).getText().toString().trim(),
                                "",
                                mealIdArrayList.size() > 0 ? mealIdArrayList.get(i) : 0, etOtherMealType.getText().toString().trim()));
                    }

                    callAddMealAPI();

                }

            }
        }


    }

    private void addMoreMeal() {
        int intTotalMeals = alTvFoodName.size() - 1;
        String strFoodName = alTvFoodName.get(intTotalMeals).getText().toString().trim();
        String strQuantity = alEtQuantity.get(intTotalMeals).getText().toString().trim();
        String strUnit = alEtUnit.get(intTotalMeals).getText().toString().trim();
        LogM.LogE("Food name ===>" + strFoodName.length());
        if (strFoodName.length() == 0) {
            AlertDialogUtility.showSnakeBar(getResources().getString(R.string.add_meal_name), snackBarView, context);
        } else if (strQuantity.length() == 0) {
            AlertDialogUtility.showSnakeBar(getResources().getString(R.string.add_meal_quantity), snackBarView, context);
        } else {
            alMiMeal.add(new MiMealModel(strFoodName, strQuantity, strUnit, "", mealIdArrayList.size() > 0 ? mealIdArrayList.get(intTotalMeals) : 0, etOtherMealType.getText().toString()));
            addView();

            count++;

        }
    }

    private void addView() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.row_meal_details, null);
        rowView.setTag(linearLayout.getChildCount());

        etMealName = rowView.findViewById(R.id.etRowMealName);
        etQuantity = rowView.findViewById(R.id.etQuantity);
        etUnit = rowView.findViewById(R.id.etUnit);
        tvMealName = rowView.findViewById(R.id.tvMealName);
        // font style

        Typeface font = Typeface.createFromAsset(getAssets(), "Fonts/MavenPro-Regular.ttf");
        etMealName.setTypeface(font);
        etQuantity.setTypeface(font);
        etUnit.setTypeface(font);
        tvMealName.setTypeface(font);


        etMealName.setTag(linearLayout.getChildCount());
        tvMealName.setTag(linearLayout.getChildCount());
        etQuantity.setTag(linearLayout.getChildCount());
        etUnit.setTag(linearLayout.getChildCount());
        tvMealName.setSelected(true);
        alTvFoodName.add(etMealName);
        alTvMealName.add(tvMealName);

        alEtQuantity.add(etQuantity);
        alEtUnit.add(etUnit);
        mealIdArrayList.add(0);
        etMealName.setDropDownWidth(getResources().getDisplayMetrics().widthPixels);
        etQuantity.setInputType(InputType.TYPE_CLASS_NUMBER);
        final Timer[] timer = {new Timer()};
        final long DELAY = 1000; // in ms

        etMealName.setOnItemClickListener(this);
        autoAdapter = new ArrayAdapter<SearchMeal.MealSearchNewDataBean>(context, android.R.layout.simple_dropdown_item_1line, mealSearchNewDataBeanArrayList);
        autoAdapter.setNotifyOnChange(true);


        etMealName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (timer[0] != null)
                    timer[0].cancel();
            }

            @Override
            public void afterTextChanged(Editable editable) {

                LogM.LogV(editable.toString());
                LogM.LogV((etMealName.getTag().toString()));
                etMealName.getTag();


                try {
                    if (editable.toString().trim().length() > 0) {
                        LogM.LogV("Before timer Searched Suppliment--------- " + editable.toString().trim());
                        timer[0] = new Timer();
                        timer[0].schedule(new TimerTask() {
                            @Override
                            public void run() {
                                LogM.LogV("run--------- " + editable.toString().trim());
                                if (flag) {

                                    Looper.prepare();
                                    LogM.LogV("After timer Searched Suppliment--------- " + editable.toString().trim());
                                    callSearchMealApi(editable.toString().trim());
                                    Looper.loop();
                                }
                            }

                        }, DELAY);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });
        linearLayout.addView(rowView, linearLayout.getChildCount() - 1);


        etUnit.setImeOptions(EditorInfo.IME_ACTION_DONE);

        if (etUnit.getText().toString().isEmpty()) {
            etQuantity.setSingleLine(true);
        } else {
            etQuantity.setSingleLine(true);
            etQuantity.setImeOptions(EditorInfo.IME_ACTION_DONE);
        }


    }

    private void callAddMealAPI() {
        try {
            JSONArray jsonArray = new JSONArray();
            for (int i = 0; i < alMiMeal.size(); i++) {
                JSONObject jObject = new JSONObject();
                MiMealModel modelMiMeal = alMiMeal.get(i);
                jObject.put(WebField.ADD_MEAL.PARAM_FOODNAME, modelMiMeal.getFoodName());
                jObject.put(WebField.ADD_MEAL.PARAM_QUANTITY, modelMiMeal.getQuantiy());
                jObject.put(WebField.ADD_MEAL.PARAM_UNIT, modelMiMeal.getUnit());
                jObject.put(WebField.ADD_MEAL.PARAM_RESOURCEID, modelMiMeal.getResourceId());
                jObject.put(WebField.ADD_MEAL.PARAM_MEAL_ID, modelMiMeal.getMealId());
                jObject.put(WebField.ADD_MEAL.PARAM_MEAL_NAME, modelMiMeal.getMealName());
                jsonArray.put(jObject);
            }

            JSONObject jsonRequest = new JSONObject();
            jsonRequest.put(WebField.PARAM_USER_ID, SessionManager.getUserId(context));
            jsonRequest.put(WebField.PARAM_ACCESSTOKEN, SessionManager.getAccessToken(context));
            jsonRequest.put(WebField.ADD_MEAL.PARAM_MEALPHOTO, strProPicBase64);
            jsonRequest.put(WebField.ADD_MEAL.PARAM_MEALTYPE, mealType);
            jsonRequest.put(WebField.ADD_MEAL.PARAM_MEALS, jsonArray);
            jsonRequest.put(WebField.ADD_MEAL.PARAM_ACTION, action);
            jsonRequest.put(WebField.ADD_MEAL.PARAM_HOMEFEED_ID, homeFeedId);
            LogM.LogE("Request : AddMeal : " + jsonRequest.toString());
            new GetJsonWithAndroidNetworkingLib(context, jsonRequest, WebField.BASE_URL + WebField.ADD_MEAL.MODE, 1, new OnUpdateListener() {
                @Override
                public void onUpdateComplete(JSONObject jsonObject, boolean isSuccess) {
                    if (isSuccess) {
                        LogM.LogE("Response : AddMeal : " + jsonObject.toString());
                        KeyboardUtility.HideKeyboard(context, etMealName);
                        try {
                            final String msg = jsonObject.get(GlobalData.MESSAGE).toString();
                            alMiMeal.clear();
                            setResult(RESULT_OK);
                            finish();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                    }
                }
            }).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openAlertForPhoto() {

        try {
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(AddMiMealActivity.this)
                    .setTitle(getResources().getString(R.string.app_name)).setMessage("Choose Meal Photo");


            builder.setNegativeButton("Gallery", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    pickSupplimentPicFromGallary();
                    LogM.LogE("you have clicked camera");
                }
            });

            builder.setPositiveButton("Camera", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    pickSupplimentPicFromCamera();
                }
            });
            android.app.AlertDialog dialog = builder.create();
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void pickSupplimentPicFromCamera() {

        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        mImageUri = Uri.fromFile(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "myFile.jpg"));
        Intent cameraIntent = new Intent();
        cameraIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);
        startActivityForResult(cameraIntent, PHOTO_CLICK);
    }


    private void pickSupplimentPicFromGallary() {

        try {
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            if (android.os.Build.VERSION.SDK_INT > 10) {
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
            }
            startActivityForResult(intent, PHOTO_PICK);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        GlobalMethods.checkPicturesFolder();
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GlobalData.MEAL_DETAIL_REQ && resultCode == RESULT_OK && data != null) {
            data.getIntExtra(GlobalData.LATEST_COUNT, 0);
            LogM.LogE("Updated count" + data.getIntExtra(GlobalData.LATEST_COUNT, 0));
            if (Integer.parseInt(tvCommentCount.getText().toString().trim()) < data.getIntExtra(GlobalData.LATEST_COUNT, 0))
                tvCommentCount.setText("" + data.getIntExtra(GlobalData.LATEST_COUNT, 0));
        }
        if (requestCode == PHOTO_PICK && resultCode == RESULT_OK && data != null) {
//
            ivPhoto.setVisibility(View.GONE);

            mImageUri = data.getData();

            if (mImageUri.toString().contains("content://com.google.android.apps.photos.content/0/")) {
                filepath = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/sent_" + System.currentTimeMillis() + ".png");
            } else if (mImageUri.toString().contains("content://com.google.android.apps.photos.content/1/")) {
                filepath = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/sent_" + System.currentTimeMillis() + ".mp4");
            }
            if (filepath != null) {
                mImageUri = getImageContentUri(context, filepath);
                if (mImageUri != null) {
                    performCrop(mImageUri);
                } else {
                    AlertDialogUtility.showAlert(context, "Please Select correct image");
                }
            } else {

                if (mImageUri != null) {
                    performCrop(mImageUri);
                } else {
                    AlertDialogUtility.showAlert(context, "Please Select correct image");
                }
            }


        } else if (requestCode == PHOTO_CLICK && resultCode == RESULT_OK) {
            ivPhoto.setVisibility(View.GONE);

            if (mImageUri != null) {
                performCrop(mImageUri);
            }


        } else if (requestCode == PIC_CROP && resultCode == RESULT_OK) {
            try {
                if (data != null) {
                    Bitmap thePic = getBitmapFromFile(cropfilepath);
                    if (thePic != null) {
                        converToBase64(thePic);
//
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            ivPhoto.setVisibility(View.VISIBLE);

        }

    }

    private void converToBase64(Bitmap thePic) {

        ivMealPhoto.setImageURI(getImageContentUri(context, cropfilepath));

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        thePic.compress(Bitmap.CompressFormat.PNG, 100, bos);
        byte[] dataTest = bos.toByteArray();
        strProPicBase64 = Base64.encodeBytes(dataTest);
        LogM.LogV("base64 =====>" + strProPicBase64);
        Log.d("strBase64 ", " strBase64 ========== " + strProPicBase64);
    }


    private Bitmap getBitmapFromFile(File photoPath) {
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            return BitmapFactory.decodeFile(photoPath.getAbsolutePath(), options);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    private void performCrop(Uri UriCamera) {

        // take care of exceptions
        try {
            // call the standard crop action intent (the user device may not
            // support it)
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            // indicate image type and Uri

            cropIntent.setDataAndType(UriCamera, "image/*");
            // set crop properties
            cropIntent.putExtra("crop", "true");
            // indicate aspect of desired crop
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            // indicate output X and Y
            cropIntent.putExtra("outputX", 256);
            cropIntent.putExtra("outputY", 256);
            // retrieve data on return
//			cropIntent.putExtra("return-data", false);
            cropfilepath = new File(
                    Environment
                            .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                            + "/sent_"
                            + System.currentTimeMillis()
                            + ".jpg");

            cropIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(cropfilepath));
            cropIntent.putExtra("output", Uri.fromFile(cropfilepath));
            cropIntent.putExtra("return-data", false);
            // start the activity - we handle returning in onActivityResult
            startActivityForResult(cropIntent, PIC_CROP);
        }
        // respond to users whose devices do not support the crop action
        catch (ActivityNotFoundException anfe) {
            // display an error message
            String errorMessage = "Whoops - your device doesn't support the crop action!";
            Toast toast = Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT);
            toast.show();
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String pos = adapterView.getItemAtPosition(i).toString();

        if (pos.equalsIgnoreCase(GlobalData.OTHER)) {
            etOtherMealType.setVisibility(View.VISIBLE);
        } else {
            etOtherMealType.setVisibility(View.GONE);
        }

        selectedItemPosition = i;
        LogM.LogV("You have Clicked");
        tvMealCategory.setText(pos);

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    private void callSearchMealApi(String trim) {
        LogM.LogE("MealName filed" + trim);

        KeyboardUtility.HideKeyboard(context, headerBar.ibtnLeftOne);

        if (ConnectivityDetector.isConnectingToInternet(context)) {

            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put(WebField.PARAM_USER_ID, SessionManager.getUserId(context));
                jsonObject.put(WebField.PARAM_ACCESSTOKEN, SessionManager.getAccessToken(context));
                jsonObject.put(WebField.SEARCH_MEAL.FOOD_NAME, trim);

                LogM.LogE("Request : SEARCH Meal : " + jsonObject.toString());
                new GetJsonWithAndroidNetworkingLib(context, jsonObject, WebField.BASE_URL + WebField.SEARCH_MEAL.MODE, 1, new OnUpdateListener() {
                    @Override
                    public void onUpdateComplete(JSONObject jsonObject, boolean isSuccess) {
                        final SearchMeal searchMealList = new Gson().fromJson(String.valueOf(jsonObject), SearchMeal.class);
                        if (isSuccess) {
                            LogM.LogE("Response : SEARCH Meal : " + jsonObject.toString());

                            mealSearchNewDataBeanArrayList.clear();
                            mealSearchNewDataBeanArrayList.addAll(searchMealList.getMealSearchNewData());
                            autoAdapter = new ArrayAdapter<SearchMeal.MealSearchNewDataBean>(context, android.R.layout.simple_dropdown_item_1line, mealSearchNewDataBeanArrayList);
                            etMealName.setAdapter(autoAdapter);
                            etMealName.showDropDown();


                        } else {
                            AlertDialogUtility.showSnakeBar(searchMealList.getMessage(), snackBarView, context);
                        }

                    }
                }).execute();


            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            AlertDialogUtility.showAlert(context, GlobalData.STR_INETRNET_ALERT_MESSAGE);
        }

    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        flag = false;

        etUnit.setText(mealSearchNewDataBeanArrayList.get(i).getServing_uom());

        if (etUnit.getText().toString().isEmpty()) {
            etQuantity.setSingleLine(true);
        } else {

            etQuantity.setSingleLine(true);
            etQuantity.setImeOptions(EditorInfo.IME_ACTION_DONE);
        }


        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                flag = true;
            }

        }, DELAY * 2);
    }


    private void addAndSetViews() {

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.row_meal_details, null);
        rowView.setTag(linearLayout.getChildCount());

        etMealName = rowView.findViewById(R.id.etRowMealName);
        tvMealName = rowView.findViewById(R.id.tvMealName);
        etQuantity = rowView.findViewById(R.id.etQuantity);
        etUnit = rowView.findViewById(R.id.etUnit);
// font style
        Typeface font = Typeface.createFromAsset(getAssets(), "Fonts/MavenPro-Regular.ttf");
        etMealName.setTypeface(font);
        etQuantity.setTypeface(font);
        etUnit.setTypeface(font);
        tvMealName.setTypeface(font);


        tvMealName.setLines(1);
        tvMealName.setHorizontallyScrolling(true);
        tvMealName.isFocusableInTouchMode();
        tvMealName.isFocusable();
        tvMealName.setSelected(true);
        tvMealName.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        tvMealName.setMarqueeRepeatLimit(-1);

        etMealName.setTag(linearLayout.getChildCount());
        etQuantity.setTag(linearLayout.getChildCount());
        etUnit.setTag(linearLayout.getChildCount());

        alTvFoodName.add(etMealName);
        alTvMealName.add(tvMealName);
        alEtQuantity.add(etQuantity);
        alEtUnit.add(etUnit);
        etMealName.setDropDownWidth(getResources().getDisplayMetrics().widthPixels);
        etQuantity.setInputType(InputType.TYPE_CLASS_NUMBER);
        final Timer[] timer = {new Timer()};
        final long DELAY = 1000; // in ms
        tvMealName.setSelected(true);


        etMealName.setOnItemClickListener(this);
        autoAdapter = new ArrayAdapter<SearchMeal.MealSearchNewDataBean>(context, android.R.layout.simple_dropdown_item_1line, mealSearchNewDataBeanArrayList);
        autoAdapter.setNotifyOnChange(true);

        etMealName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (timer[0] != null)
                    timer[0].cancel();
            }

            @Override
            public void afterTextChanged(Editable editable) {

                LogM.LogV(editable.toString());
                LogM.LogV((etMealName.getTag().toString()));
                etMealName.getTag();

                if (counter > 0) {


                    try {
                        if (editable.toString().trim().length() > 0) {
                            LogM.LogV("Before timer Searched Suppliment--------- " + editable.toString().trim());
                            timer[0] = new Timer();
                            timer[0].schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    LogM.LogV("run--------- " + editable.toString().trim());
                                    if (flag) {

                                        Looper.prepare();
                                        LogM.LogV("After timer Searched Suppliment--------- " + editable.toString().trim());
                                        callSearchMealApi(editable.toString().trim());
                                        Looper.loop();
                                    }
                                }

                            }, DELAY);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        });
        linearLayout.addView(rowView, linearLayout.getChildCount() - 1);


        etUnit.setImeOptions(EditorInfo.IME_ACTION_DONE);

        if (etUnit.getText().toString().isEmpty()) {
            etQuantity.setSingleLine(true);
        } else {
            etQuantity.setSingleLine(true);
            etQuantity.setImeOptions(EditorInfo.IME_ACTION_DONE);
        }


    }

    private void callAddexpressionUserMedia(String expression, int selected, int deselect) {
        try {
            if (!ConnectivityDetector.isConnectingToInternet(context)) {
                AlertDialogUtility.showInternetAlert(context);
                return;
            }
            JSONObject jsonObject = new JSONObject();

            jsonObject.put(WebField.PARAM_USER_ID, SessionManager.getUserId(context));
            jsonObject.put(WebField.PARAM_ACCESSTOKEN, SessionManager.getAccessToken(context));
            jsonObject.put(WebField.PARAM_HOMEFEEDID, homeFeedId);
            jsonObject.put(WebField.GIVE_EXPRESSION_ON_FEED.PARAM_EXPRESSION, expression);


            LogM.LogE("Request : Media Expression : " + jsonObject.toString());
            new GetJsonWithAndroidNetworkingLib(context, jsonObject, WebField.BASE_URL + WebField.GIVE_EXPRESSION_ON_FEED.MODE, 1, new OnUpdateListener() {
                @Override
                public void onUpdateComplete(JSONObject jsonObject, boolean isSuccess) {
                    LogM.LogE("Response : Media Expression: " + jsonObject.toString());
                    if (isSuccess) {
                        SelectDeselectExpression expression = new Gson().fromJson(String.valueOf(jsonObject), SelectDeselectExpression.class);
                        tvLikeCount.setText("" + expression.getExpressionCount());
                        goal = expression.getIs_goals();
                        inspiring = expression.getIs_inspiring();
                        admiring = expression.getIs_admiring();

//                        rowCom.setVisibility(View.GONE);
//                        isAlreadyLiked = true;
//                        if (selected == 1 && deselect == 0) {
//                            ivLike.setImageResource(R.drawable.goal_green);
//                        } else if (selected == 2 && deselect == 0) {
//                            ivLike.setImageResource(R.drawable.inspiration_green);
//                        } else if (selected == 3 && deselect == 0) {
//                            ivLike.setImageResource(R.drawable.admiring_green);
//                        } else {
//                            isAlreadyLiked = false;
//                            ivLike.setImageResource(R.drawable.like);
//                        }

                        rowCom.setVisibility(View.GONE);
                        isAlreadyLiked = true;
                        if (expression.getStatus() == 2) {
                            if (expression.getIs_goals() == GlobalData.EXPRESSION_GOAL) {
                                isAlreadyLiked = false;
                                ivLike.setImageResource(R.drawable.like);
                            } else if (expression.getIs_inspiring() == GlobalData.EXPRESSION_INSPIRING) {
                                isAlreadyLiked = false;
                                ivLike.setImageResource(R.drawable.like);
                            } else if (expression.getIs_admiring() == GlobalData.EXPRESSION_ADMIRING) {
                                isAlreadyLiked = false;
                                ivLike.setImageResource(R.drawable.like);
                            }
                        } else {
                            if (goal == 1) {
                                goal = 1;
                                ivLike.setImageResource(R.drawable.goal_green);
                            } else if (inspiring == 2) {
                                inspiring = 1;
                                ivLike.setImageResource(R.drawable.inspiration_green);
                            } else if (admiring == 3) {
                                admiring = 1;
                                ivLike.setImageResource(R.drawable.admiring_green);
                            } else {
                                isAlreadyLiked = false;
                                ivLike.setImageResource(R.drawable.like);
                            }
                        }
                    }
                }
            }).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
