package com.konkr.Activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.konkr.Adapters.HorizontalVideoPhotoAdapter;
import com.konkr.Interfaces.OnRecyclerViewItemClickListener;
import com.konkr.Models.MiTrainingAddWorkout;
import com.konkr.Models.MiTrainingCategory;
import com.konkr.Models.MiTrainingExercise;
import com.konkr.R;
import com.konkr.SharedPreferences.SessionManager;
import com.konkr.Utils.AlertDialogUtility;
import com.konkr.Utils.BaseActivity;
import com.konkr.Utils.ConnectivityDetector;
import com.konkr.Utils.FileHandeling;
import com.konkr.Utils.GlobalData;
import com.konkr.Utils.GlobalMethods;
import com.konkr.Utils.Headerbar;
import com.konkr.Utils.KeyboardUtility;
import com.konkr.Utils.LogM;
import com.konkr.Utils.MyClass;
import com.konkr.Utils.MyEditText;
import com.konkr.Utils.MyTextView;
import com.konkr.Utils.PermissionsHelper;
import com.konkr.Webservices.GetJsonWithAndroidNetworkingLib;
import com.konkr.Webservices.OnUpdateListener;
import com.konkr.Webservices.WebField;
import com.konkr.databinding.ActivityMiTrainingBinding;
import com.google.gson.Gson;
import com.vincent.videocompressor.VideoCompress;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import static com.konkr.Activities.MiSupplimentActivity.getImageContentUri;

public class MiTrainingActivity extends BaseActivity implements View.OnClickListener, OnRecyclerViewItemClickListener, AdapterView.OnItemClickListener {

    private static final int MEDIA_TYPE_VIDEO = 200;
    private ProgressDialog progressDialog;

    private View snackBarView;
    private ActivityMiTrainingBinding binding;
    private Headerbar headerBar;
    private ImageView ivTrainingVideoPhoto;
    private RecyclerView rvVideoPhoto;
    private MyEditText etWorkoutName;
    private AutoCompleteTextView etExercise;
    private MyTextView tvWorkoutCategory, tvHours, tvMinute;
    private Spinner spinnerWorkoutCategory, spinnerExercise, spinnerHours, spinnerMinute;

    private String[] hours, minutes, categories, exercises;
    private ArrayAdapter<String> hoursAdapter;
    private ArrayAdapter<String> minutesAdapter;
    private ArrayAdapter<String> categoryAdapter;
    private ArrayAdapter<String> exerciseAdapter;
    private ArrayList<MiTrainingCategory.WorkoutCategoriesBean> alCategory = new ArrayList<>();
    private ArrayList<MiTrainingExercise.ExcercisesBean> alExercise = new ArrayList<>();
    private ArrayList<MiTrainingAddWorkout> alWorkoutImagesAndVideos = new ArrayList<>();

    private HorizontalVideoPhotoAdapter adapter;
    private int categoryId, exercisesId;
    private String workoutName = "", workoutCategory = "", exercisesName = "";
    private int hour = 0, minute = 0;
    private int isSubscribed;

    private Uri uriMealPic;
    private Uri mImageUri;
    private File filepath, cropfilepath;
    private static final int PIC_CROP = 156;
    private String strProPicBase64 = "";

    private boolean upload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_mi_training);
        snackBarView = findViewById(android.R.id.content);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        bindViews();
        setListener();
        setSpinnerData();
        setTextWatcher();

        adapter = new HorizontalVideoPhotoAdapter(MiTrainingActivity.this, alWorkoutImagesAndVideos, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(MiTrainingActivity.this, LinearLayoutManager.HORIZONTAL, false);
        rvVideoPhoto.setLayoutManager(mLayoutManager);
        rvVideoPhoto.setAdapter(adapter);
        rvVideoPhoto.setVisibility(View.GONE);

        callGetWorkoutCategory();

        etExercise.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    etExercise.dismissDropDown(); // this line will close drop down  on click of search
                    if (tvWorkoutCategory.getText().toString().trim().length() > 0) {
                        callGetExerciseList(etExercise.getText().toString().trim());
                    } else {
//                        etExercise.setText("");
                        KeyboardUtility.HideKeyboard(getApplicationContext(), etExercise);
                        AlertDialogUtility.showSnakeBar(getResources().getString(R.string.workout_category_empty), snackBarView, MiTrainingActivity.this);
                        etExercise.setText("");
                    }
                    return true;
                }
                return false;
            }
        });
    }

    private void bindViews() {
        try {
            headerBar = binding.headerBar;
            headerBar.tvTitle.setVisibility(View.VISIBLE);
            headerBar.tvTitle.setText(R.string.mi_training);
            headerBar.tvRight.setVisibility(View.VISIBLE);
            headerBar.tvRight.setText(getResources().getString(R.string.done));
            headerBar.ibtnLeftOne.setVisibility(View.VISIBLE);
            headerBar.ibtnLeftOne.setImageResource(R.drawable.back);

            ivTrainingVideoPhoto = binding.ivTrainingVideoPhoto;
            rvVideoPhoto = binding.rvVideoPhoto;
            etWorkoutName = binding.etWorkoutName;
            tvWorkoutCategory = binding.tvWorkoutCategory;
            etExercise = binding.etExercise;
//            etExercise.setEnabled(false);
            tvHours = binding.tvHours;
            tvMinute = binding.tvMinute;
            spinnerWorkoutCategory = binding.spinnerWorkoutCategory;
            spinnerExercise = binding.spinnerExercise;
            spinnerHours = binding.spinnerHours;
            spinnerMinute = binding.spinnerMinute;

            Typeface font = Typeface.createFromAsset(getAssets(), "Fonts/Poppins-Regular_1.ttf");
            etExercise.setTypeface(font);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setListener() {
        try {
            headerBar.tvRight.setOnClickListener(this);
            headerBar.ibtnLeftOne.setOnClickListener(this);
            ivTrainingVideoPhoto.setOnClickListener(this);
            tvWorkoutCategory.setOnClickListener(this);
            tvHours.setOnClickListener(this);
            tvMinute.setOnClickListener(this);
            etExercise.setOnItemClickListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setSpinnerData() {
        try {
            hours = getResources().getStringArray(R.array.hours);
            minutes = getResources().getStringArray(R.array.minutes);

            hoursAdapter = new ArrayAdapter<String>(MiTrainingActivity.this, android.R.layout.simple_spinner_dropdown_item, hours) {
                @Override
                public boolean isEnabled(int position) {
                    if (position == 0) {
                        return false;
                    } else {
                        return true;
                    }
                }

                @Override
                public View getDropDownView(int position, View convertView, ViewGroup parent) {
                    View view = super.getDropDownView(position, convertView, parent);
                    TextView tv = (TextView) view;
                    if (position == 0) {
                        tv.setTextColor(Color.GRAY);
                    } else {
                        tv.setTextColor(Color.BLACK);
                    }
                    return view;
                }
            };
            hoursAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerHours.setAdapter(hoursAdapter);
            spinnerHours.setOnItemSelectedListener(hoursListener);

            minutesAdapter = new ArrayAdapter<String>(MiTrainingActivity.this, android.R.layout.simple_spinner_dropdown_item, minutes) {
                @Override
                public boolean isEnabled(int position) {
                    if (position == 0) {
                        return false;
                    } else {
                        return true;
                    }
                }

                @Override
                public View getDropDownView(int position, View convertView, ViewGroup parent) {
                    View view = super.getDropDownView(position, convertView, parent);
                    TextView tv = (TextView) view;
                    if (position == 0) {
                        tv.setTextColor(Color.GRAY);
                    } else {
                        tv.setTextColor(Color.BLACK);
                    }
                    return view;
                }
            };
            minutesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerMinute.setAdapter(minutesAdapter);
            spinnerMinute.setOnItemSelectedListener(minutesListener);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private AdapterView.OnItemSelectedListener hoursListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            try {
                if (position > 0) {
                    tvHours.setText(hours[position]);
                    hour = Integer.parseInt(hours[position]);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    };

    private AdapterView.OnItemSelectedListener minutesListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            try {
                if (position > 0) {
                    tvMinute.setText(minutes[position]);
                    minute = Integer.parseInt(minutes[position]);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    };

    private void setTextWatcher() {
        try {
            etExercise.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (timer != null)
                        timer.cancel();
                }

                @Override
                public void afterTextChanged(final Editable s) {
                    try {
                        if (s.toString().trim().length() > 0) {
                            timer = new Timer();
                            timer.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    if (flag) {
                                        if (tvWorkoutCategory.getText().toString().trim().length() > 0) {
                                            Looper.prepare();
                                            callGetExerciseList(s.toString().trim());
                                            Looper.loop();
                                        } else {
//                                            etExercise.setText("");
                                            KeyboardUtility.HideKeyboard(getApplicationContext(), etExercise);
                                            AlertDialogUtility.showSnakeBar(getResources().getString(R.string.workout_category_empty), snackBarView, MiTrainingActivity.this);
                                            etExercise.setText("");
                                        }
                                    }
                                }

                            }, DELAY);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isValidData() {
        boolean isValid = false;

        if (etWorkoutName.getText().toString().trim().length() > 0) {
            isValid = true;
            workoutName = etWorkoutName.getText().toString().trim();
            KeyboardUtility.HideKeyboard(getApplicationContext(), etWorkoutName);
        } else {
            etWorkoutName.requestFocus();
            AlertDialogUtility.showSnakeBar(getResources().getString(R.string.workout_name_empty), snackBarView, MiTrainingActivity.this);
            KeyboardUtility.HideKeyboard(getApplicationContext(), etWorkoutName);
            return false;
        }

        if (tvWorkoutCategory.getText().toString().trim().length() > 0) {
            isValid = true;
            workoutCategory = tvWorkoutCategory.getText().toString().trim();
        } else {
            tvWorkoutCategory.requestFocus();
            AlertDialogUtility.showSnakeBar(getResources().getString(R.string.workout_category_empty), snackBarView, MiTrainingActivity.this);
            return false;
        }

        if (tvWorkoutCategory.getText().toString().trim().length() > 0) {
            if (etExercise.getText().toString().trim().length() > 0) {

                isValid = true;
                exercisesName = etExercise.getText().toString().trim();
            } else {
                etExercise.requestFocus();
                AlertDialogUtility.showSnakeBar(getResources().getString(R.string.exercises_name_empty), snackBarView, MiTrainingActivity.this);
                return false;
            }
        } else {
            tvWorkoutCategory.requestFocus();
            AlertDialogUtility.showSnakeBar(getResources().getString(R.string.workout_category_empty), snackBarView, MiTrainingActivity.this);
            return false;
        }
        if (tvMinute.getText().toString().isEmpty()
                || tvHours.getText().toString().isEmpty()
                || tvMinute.getText().toString().trim().contains("Minute")
                || tvHours.getText().toString().trim().contains("Hour")
                || (Integer.parseInt(tvMinute.getText().toString().trim()) + Integer.parseInt(tvHours.getText().toString()) < 1)) {
            tvMinute.requestFocus();
            AlertDialogUtility.showSnakeBar(getResources().getString(R.string.exercises_time_empty), snackBarView, MiTrainingActivity.this);
            return false;
        } else {
            isValid = true;
        }

//        if (tvMinute.getText().toString().trim().contains("Minute")
//                || tvMinute.getText().toString().isEmpty()
//                || tvMinute.getText().toString().trim().equalsIgnoreCase("00")//00
//                || tvHours.getText().toString().trim().contains("Hour")
//                || tvHours.getText().toString().isEmpty()
//                || tvHours.getText().toString().trim().equalsIgnoreCase("00")) {
//            tvMinute.requestFocus();
//            AlertDialogUtility.showSnakeBar(getResources().getString(R.string.exercises_time_empty), snackBarView, MiTrainingActivity.this);
//            return false;
//        } else {
//            isValid = true;
//        }

        return isValid;
    }

    @Override
    public void onBackPressed() {
        callRemoveWorkoutMedia();
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
//        callRemoveWorkoutMedia();
        super.onPause();
    }

    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()) {
                case R.id.ibtnLeftOne:
                    KeyboardUtility.HideKeyboard(MiTrainingActivity.this, v);
                    onBackPressed();
                    break;

                case R.id.tvRight:
                    if (!ConnectivityDetector.isConnectingToInternet(MiTrainingActivity.this)) {
                        AlertDialogUtility.showInternetAlert(MiTrainingActivity.this);
                        return;
                    }
                    if (isValidData()) {
                        callAddWorkout();
                    }
                    break;

                case R.id.ivTrainingVideoPhoto:
                    itemId = 0;
                    spliteCount = 0;
                    totalSplite = 1;
                    if (!ConnectivityDetector.isConnectingToInternet(MiTrainingActivity.this)) {
                        AlertDialogUtility.showInternetAlert(MiTrainingActivity.this);
                        return;
                    }
                    PermissionsHelper.permissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO}).checkPermissions(MiTrainingActivity.this, new PermissionsHelper.OnPermissionResult() {

                        @Override
                        public void onGranted() {
                            openAlertForVideoPhoto();
                        }

                        @Override
                        public void notGranted() {
                        }
                    });
                    break;

                case R.id.tvWorkoutCategory:
                    if (categoryAdapter != null) {
                        spinnerWorkoutCategory.performClick();
                        return;
                    }
                    categoryAdapter = new ArrayAdapter<String>(MiTrainingActivity.this, R.layout.row_spineer, R.id.txtVwSpinner, categories);
                    categoryAdapter.setDropDownViewResource(R.layout.row_spineer);
                    spinnerWorkoutCategory.setAdapter(categoryAdapter);
                    spinnerWorkoutCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            tvWorkoutCategory.setText(alCategory.get(position).getWorkoutCategoryName());
                            categoryId = alCategory.get(position).getWorkoutCategoryId();
                            etExercise.setEnabled(true);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            spinnerWorkoutCategory.performClick();
                        }
                    }, 100);
                    break;

                case R.id.tvHours:
                    spinnerHours.performClick();
                    break;

                case R.id.tvMinute:
                    spinnerMinute.performClick();
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClickListener(View view, int position) {
        try {
            MiTrainingAddWorkout workout = alWorkoutImagesAndVideos.get(position);
            if (workout.getMediaType() == GlobalData.MEDIA_TYPE_IMAGE) {
                Intent intent = new Intent(getApplicationContext(), VideoActivity.class);
                intent.putExtra(GlobalData.IS_FROM, GlobalData.TRAINING_MEDIA);
                intent.putExtra("mediaType", workout.getMediaType());
                intent.putExtra("imageUrl", workout.getImageURL());
                startActivity(intent);
            } else if (workout.getMediaType() == GlobalData.MEDIA_TYPE_VIDEO) {
                Intent intent = new Intent(getApplicationContext(), VideoActivity.class);
                intent.putExtra(GlobalData.IS_FROM, GlobalData.TRAINING_MEDIA);
                intent.putExtra("mediaType", workout.getMediaType());
                intent.putExtra("videoUrl", workout.getVideoURL());
                intent.putExtra("videoThumb", workout.getVideoThumbImage());
                startActivity(intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean flag = true;
    private Timer timer = new Timer();
    private final long DELAY = 1000;

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
        flag = false;
        etExercise.setText(item);
        exercisesId = alExercise.get(position).getExcerciseId();
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                flag = true;
            }
        }, DELAY * 2);
    }

    private void openAlertForVideoPhoto() {
        final CharSequence[] items = {getString(R.string.photo), getString(R.string.video)};

        AlertDialog.Builder builder = new AlertDialog.Builder(MiTrainingActivity.this);
        builder.setTitle(getString(R.string.training_video_photo));
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals(getString(R.string.photo))) {
//                    openAlertForCameraGallery(getString(R.string.photo));
                    openAlertForGalleryCamera(getString(R.string.photo));
                } else if (items[item].equals(getString(R.string.video))) {
//                    openAlertForGalleryCamera(getString(R.string.video));
                    if (isSubscribed == 1) {
                        openAlertForGalleryCamera(getString(R.string.video));
                    } else {
                        Intent intent = new Intent(MiTrainingActivity.this, PremiumMemberShipActivity.class);
                        startActivityForResult(intent, GlobalData.PREMIUM_MEMBER);
                    }
                }
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private void openAlertForCameraGallery(final String isPhotoOrVideo) {
        final CharSequence[] items = {getString(R.string.camera), getString(R.string.gallery)};

        AlertDialog.Builder builder = new AlertDialog.Builder(MiTrainingActivity.this);
        builder.setTitle(getString(R.string.training_video_photo));
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals(getString(R.string.camera))) {
                    if (isPhotoOrVideo.equalsIgnoreCase(getString(R.string.photo))) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent, GlobalData.CAPTURE_PHOTO);
                    } else if (isPhotoOrVideo.equalsIgnoreCase(getString(R.string.video))) {
//                        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
//                        captureVideoURI = FileProvider.getUriForFile(MiTrainingActivity.this, getPackageName() + ".com.fitinc.provider", getOutputMediaFile(MEDIA_TYPE_VIDEO));
//                        intent.putExtra(MediaStore.EXTRA_OUTPUT, captureVideoURI);
//                        long maxVideoSize = 10 * 1024 * 1024; // 10 MB
//                        intent.putExtra(MediaStore.EXTRA_SIZE_LIMIT, maxVideoSize);
//                        intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 30);
                        GlobalData.CAMERA_ID = 0;
                        Intent intent = new Intent(MiTrainingActivity.this, CustomCameraActivity.class);
                        startActivityForResult(intent, GlobalData.CAPTURE_VIDEO);
                    }
                } else if (items[item].equals(getString(R.string.gallery))) {
                    if (isPhotoOrVideo.equalsIgnoreCase(getString(R.string.photo))) {
                        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(galleryIntent, GlobalData.PICK_PHOTO);
                    } else if (isPhotoOrVideo.equalsIgnoreCase(getString(R.string.video))) {
                        Intent intent = new Intent();
                        intent.setTypeAndNormalize("video/mp4");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        intent.addCategory(Intent.CATEGORY_OPENABLE);
                        startActivityForResult(Intent.createChooser(intent, getResources().getString(R.string.select_video)), GlobalData.TRIM_VIDEO);
                    }
                }
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private void openAlertForGalleryCamera(final String isPhotoOrVideo) {
        try {
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(MiTrainingActivity.this)
                    .setTitle(getResources().getString(R.string.app_name))
                    .setMessage(isPhotoOrVideo.equalsIgnoreCase(getString(R.string.photo)) ? (getString(R.string.training_photo)) : (getString(R.string.training_video)));

            builder.setNegativeButton(getString(R.string.gallery), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    if (isPhotoOrVideo.equalsIgnoreCase(getString(R.string.photo))) {
//                        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                        startActivityForResult(galleryIntent, GlobalData.PICK_PHOTO);
                        try {
                            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                            uriMealPic = Uri.fromFile(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "myFile.jpg"));
                            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            intent.setType("image/*");
                            if (android.os.Build.VERSION.SDK_INT > 10) {
                                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                            }
                            startActivityForResult(intent, GlobalData.PICK_PHOTO);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else if (isPhotoOrVideo.equalsIgnoreCase(getString(R.string.video))) {
                        Intent intent = new Intent();
                        intent.setTypeAndNormalize("video/mp4");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        intent.addCategory(Intent.CATEGORY_OPENABLE);
                        startActivityForResult(Intent.createChooser(intent, getResources().getString(R.string.select_video)), GlobalData.TRIM_VIDEO);
                    }
                }
            });

            builder.setPositiveButton(getString(R.string.camera), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    if (isPhotoOrVideo.equalsIgnoreCase(getString(R.string.photo))) {
//                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                        startActivityForResult(intent, GlobalData.CAPTURE_PHOTO);

//                        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//                        startActivityForResult(cameraIntent, GlobalData.CAPTURE_PHOTO);
                        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                        mImageUri = Uri.fromFile(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "myFile.jpg"));
                        Intent cameraIntent = new Intent();
                        cameraIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);
                        startActivityForResult(cameraIntent, GlobalData.CAPTURE_PHOTO);
                    } else if (isPhotoOrVideo.equalsIgnoreCase(getString(R.string.video))) {
//                        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
//                        captureVideoURI = FileProvider.getUriForFile(MiTrainingActivity.this, getPackageName() + ".com.fitinc.provider", getOutputMediaFile(MEDIA_TYPE_VIDEO));
//                        intent.putExtra(MediaStore.EXTRA_OUTPUT, captureVideoURI);
//                        long maxVideoSize = 10 * 1024 * 1024; // 10 MB
//                        intent.putExtra(MediaStore.EXTRA_SIZE_LIMIT, maxVideoSize);
//                        intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 30);
                        GlobalData.CAMERA_ID = 0;
                        Intent intent = new Intent(MiTrainingActivity.this, CustomCameraActivity.class);
                        startActivityForResult(intent, GlobalData.CAPTURE_VIDEO);
                    }
                }
            });
            android.app.AlertDialog dialog = builder.create();
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Uri selectedUri = null;
    private String result = "";
    private final String strCompressedVideo = "/Video/CompressedVideo";
    private int count = 0;
    public File file;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == GlobalData.PREMIUM_MEMBER) {
                isSubscribed = SessionManager.getSubscribed(MiTrainingActivity.this);
                return;
            }
//            else if (requestCode == GlobalData.CAPTURE_PHOTO && data != null) {
//                try {
//                    Bitmap bmProfilePic = null;
//                    bmProfilePic = (Bitmap) data.getExtras().get("data");
//                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//                    bmProfilePic.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
//                    File destination = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".jpg");
//                    FileOutputStream fo;
//                    try {
//                        destination.createNewFile();
//                        fo = new FileOutputStream(destination);
//                        fo.write(bytes.toByteArray());
//                        fo.close();
//                    } catch (FileNotFoundException e) {
//                        e.printStackTrace();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
////                    spliteFile(destination);
//                    base64 = getBase64String(bmProfilePic);
//                    callAddWorkoutMedia(2);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                return;
//            }

            if (requestCode == GlobalData.CAPTURE_VIDEO && data != null) {
                try {
                    result = data.getStringExtra("result");
                    selectedUri = Uri.parse(result);
                    compressVideo(result);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return;
            }


//            if (requestCode == GlobalData.PICK_PHOTO && data != null) {
//                try {
//                    selectedUri = data.getData();
//                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedUri);
//                    base64 = getBase64String(bitmap);
//                    callAddWorkoutMedia(2);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                return;
//            }

            if (requestCode == GlobalData.PICK_PHOTO && resultCode == RESULT_OK && data != null) {
                mImageUri = data.getData();
//            Log.e("Image captured", "Image captured");
//                ivPhoto.setVisibility(View.GONE);
                // tvAddPhoto.setVisibility(View.GONE);

                mImageUri = data.getData();

                if (mImageUri.toString().contains("content://com.google.android.apps.photos.content/0/")) {
                    filepath = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/sent_" + System.currentTimeMillis() + ".png");
                } else if (mImageUri.toString().contains("content://com.google.android.apps.photos.content/1/")) {
                    filepath = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/sent_" + System.currentTimeMillis() + ".mp4");
                }
                if (filepath != null) {
                    mImageUri = getImageContentUri(getApplicationContext(), filepath);
                    if (mImageUri != null) {
                        performCrop(mImageUri);
                    } else {
                        AlertDialogUtility.showAlert(getApplicationContext(), "Please Select correct image");
                    }
                } else {
                    // Log.e("path", "" + filePath);
                    if (mImageUri != null) {
                        performCrop(mImageUri);
                    } else {
                        AlertDialogUtility.showAlert(getApplicationContext(), "Please Select correct image");
                    }
                }
            } else if (requestCode == GlobalData.CAPTURE_PHOTO && resultCode == RESULT_OK) {
//                ivPhoto.setVisibility(View.GONE);
                // tvAddPhoto.setVisibility(View.GONE);
//            Log.e("Video captured", "Video captured");
//                Bitmap photo = (Bitmap) data.getExtras().get("data");
//                mImageUri = getImageUri(photo);
//                performCrop(mImageUri);

                if (mImageUri != null) {
                    performCrop(mImageUri);
                }
            } else if (requestCode == PIC_CROP && resultCode == RESULT_OK) {
                try {
                    if (data != null) {
                        Bitmap thePic = getBitmapFromFile(cropfilepath);
                        if (thePic != null) {
                            converToBase64(thePic);
                            callAddWorkoutMedia(2, 0);
//                        GlobalData.loginViaFacebook = false;  // it is used to save new selected photon from device into base64.
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
//                ivPhoto.setVisibility(View.VISIBLE);
                //  tvAddPhoto.setVisibility(View.VISIBLE);
            }

            if (requestCode == GlobalData.TRIM_VIDEO && data != null) {
                try {
                    selectedUri = data.getData();
                    if (selectedUri != null) {
                        startTrimActivity(selectedUri);
                    } else {
                        Toast.makeText(getApplicationContext(), R.string.toast_cannot_retrieve_selected_video, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return;
            }

            if (requestCode == GlobalData.PICK_VIDEO && data != null) {
                try {
                    result = data.getStringExtra("result");
                    selectedUri = Uri.parse(result);
                    compressVideo(result);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void converToBase64(Bitmap thePic) {

//        String strFilePathForFresco = "file://" + cropfilepath.getAbsolutePath();
//        ivMealPhoto.setImageURI(getImageContentUri(getApplicationContext(), cropfilepath));

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        thePic.compress(Bitmap.CompressFormat.PNG, 100, bos);
        byte[] dataTest = bos.toByteArray();
        strProPicBase64 = com.konkr.Utils.Base64.encodeBytes(dataTest);
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

    public Uri getImageUri(Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(getApplicationContext().getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
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
            Toast toast = Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT);
            toast.show();
        }

    }

    private void startTrimActivity(Uri uri) {
        try {
            if (life.knowledge4.videotrimmer.utils.FileUtils.getPath(MiTrainingActivity.this, uri) == null) {
                Toast.makeText(MiTrainingActivity.this, "We can't get this video. Please try to choose another video.", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(MiTrainingActivity.this, VideoTrimmerActivity.class);
                intent.putExtra(GlobalData.EXTRA_VIDEO_PATH, life.knowledge4.videotrimmer.utils.FileUtils.getPath(MiTrainingActivity.this, uri));
                startActivityForResult(intent, GlobalData.PICK_VIDEO);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void compressVideo(final String input) {
        try {
            if (!ConnectivityDetector.isConnectingToInternet(MiTrainingActivity.this)) {
                AlertDialogUtility.showInternetAlert(MiTrainingActivity.this);
                return;
            }
            File compressedVideoFolder = new File(Environment.getExternalStorageDirectory().toString() + "/" + GlobalData.APP_NAME + strCompressedVideo);
            if (!compressedVideoFolder.exists()) {
                compressedVideoFolder.mkdir();
            }

            final String outPath = Environment.getExternalStorageDirectory().toString() + "/" + GlobalData.APP_NAME + strCompressedVideo + "/out" + count + GlobalData.VIDEO_FORMATE;

            VideoCompress.compressVideoMedium(input, outPath, new VideoCompress.CompressListener() {

                @Override
                public void onStart() {
                    progressDialog = new ProgressDialog(MiTrainingActivity.this);
                    progressDialog.setCancelable(false);
                    progressDialog.setMessage(getString(R.string.please_wait));
                    progressDialog.setTitle("");
                    progressDialog.show();
                }

                @Override
                public void onSuccess() {
                    file = new File(outPath);
                    spliteFile(file);
                    callAddWorkoutMedia(3, 0);
                }

                @Override
                public void onFail() {
                }

                @Override
                public void onProgress(float percent) {
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private MyClass myClass = new MyClass();

    private String getBase64(int index) {
        String base64_ = null;
        try {
            File f = new File(myClass.makeDir(MiTrainingActivity.this), fileNameArr.get(index));
            base64_ = fileHandeling.getBase64FromByteArray(fileHandeling.convertFileToByteArray(f));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return base64_;
    }

    private String base64Thumb = "";
    private int spliteCount;
    private ArrayList<String> fileNameArr;
    private FileHandeling fileHandeling;
    private int totalSplite = 1;

    private void spliteFile(File f) {
        try {
            Bitmap bitmap = GlobalMethods.getThumbFromVideoWorkout(f.getAbsolutePath());
            base64Thumb = getBase64String(bitmap);
            spliteCount = 0;
            fileNameArr = new ArrayList<>();
            fileHandeling = new FileHandeling(getApplicationContext());
            fileNameArr = fileHandeling.splitFileTobase64(f);
            totalSplite = fileNameArr.size();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getBase64String(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String base64String = Base64.encodeToString(imageBytes, Base64.NO_WRAP);
        return base64String;
    }

    private void callGetWorkoutCategory() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(WebField.GET_WORKOUT_CATEGORY.USER_ID, SessionManager.getUserId(MiTrainingActivity.this));
            jsonObject.put(WebField.GET_WORKOUT_CATEGORY.ACCESS_TOKEN, SessionManager.getAccessToken(MiTrainingActivity.this));
            LogM.LogE("Request : GetWorkoutCategory : " + jsonObject.toString());

            new GetJsonWithAndroidNetworkingLib(MiTrainingActivity.this, jsonObject, WebField.BASE_URL + WebField.GET_WORKOUT_CATEGORY.MODE, 1, new OnUpdateListener() {
                @Override
                public void onUpdateComplete(JSONObject jsonObject, boolean isSuccess) {
                    LogM.LogE("Response : GetWorkoutCategory : " + jsonObject.toString());
                    MiTrainingCategory mTrainingCategory = new Gson().fromJson(String.valueOf(jsonObject), MiTrainingCategory.class);
                    if (isSuccess) {
                        alCategory.clear();
                        isSubscribed = mTrainingCategory.getIsSubscribed();
                        SessionManager.setSubscribed(MiTrainingActivity.this, isSubscribed);
                        alCategory.addAll(mTrainingCategory.getWorkoutCategories());
                        categories = new String[alCategory.size()];
                        int index = 0;
                        upload = true;
                        for (MiTrainingCategory.WorkoutCategoriesBean value : alCategory) {
                            categories[index] = value.getWorkoutCategoryName();
                            index++;
                        }
                    } else {
                        AlertDialogUtility.showSnakeBar(mTrainingCategory.getMessage(), snackBarView, MiTrainingActivity.this);
                    }
                }
            }).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void callGetExerciseList(String searchBy) {
        try {
            KeyboardUtility.HideKeyboard(this, etExercise);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(WebField.GET_EXCERCISE_LIST.USER_ID, SessionManager.getUserId(MiTrainingActivity.this));
            jsonObject.put(WebField.GET_EXCERCISE_LIST.ACCESS_TOKEN, SessionManager.getAccessToken(MiTrainingActivity.this));
            jsonObject.put(WebField.GET_EXCERCISE_LIST.WORKOUT_CATEGORY_ID, categoryId);
            jsonObject.put(WebField.GET_EXCERCISE_LIST.SEARCH_BY, searchBy);
            LogM.LogE("Request : GetExerciseList : " + jsonObject.toString());

            new GetJsonWithAndroidNetworkingLib(MiTrainingActivity.this, jsonObject, WebField.BASE_URL + WebField.GET_EXCERCISE_LIST.MODE, 1, new OnUpdateListener() {
                @Override
                public void onUpdateComplete(JSONObject jsonObject, boolean isSuccess) {
                    LogM.LogE("Response : GetExerciseList : " + jsonObject.toString());
                    MiTrainingExercise mTrainingExercise = new Gson().fromJson(String.valueOf(jsonObject), MiTrainingExercise.class);
                    if (isSuccess) {
                        alExercise.clear();
                        alExercise.addAll(mTrainingExercise.getExcercises());
                        exercises = new String[alExercise.size()];
                        int index = 0;
                        for (MiTrainingExercise.ExcercisesBean value : alExercise) {
                            exercises[index] = value.getExcerciseName();
                            index++;
                        }

                        exerciseAdapter = new ArrayAdapter<String>(MiTrainingActivity.this, android.R.layout.simple_dropdown_item_1line, exercises);
                        exerciseAdapter.setNotifyOnChange(true);
                        etExercise.setAdapter(exerciseAdapter);
                        etExercise.showDropDown();
                    } else {
                        AlertDialogUtility.showSnakeBar(mTrainingExercise.getMessage(), snackBarView, MiTrainingActivity.this);
                    }
                }
            }).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String base64 = "";
    private int itemId = 0;

    private void callAddWorkoutMedia(int mediaType, int itemId) { //mediaType 2=Image, 3=Video
        try {
            if (!ConnectivityDetector.isConnectingToInternet(MiTrainingActivity.this)) {
                AlertDialogUtility.showInternetAlert(MiTrainingActivity.this);
                return;
            }
            if (progressDialog == null) {
                progressDialog = new ProgressDialog(MiTrainingActivity.this);
                progressDialog.setCancelable(false);
                progressDialog.setMessage(getString(R.string.please_wait));
                progressDialog.setTitle("");
                progressDialog.show();
            }

            if (mediaType == 3) {
                if (spliteCount < totalSplite) {
                    base64 = getBase64(spliteCount);
                }
            }

            JSONObject jsonObject = new JSONObject();
            jsonObject.put(WebField.ADD_WORKOUT_MEDIA.USER_ID, SessionManager.getUserId(MiTrainingActivity.this));
            jsonObject.put(WebField.ADD_WORKOUT_MEDIA.ACCESS_TOKEN, SessionManager.getAccessToken(MiTrainingActivity.this));
            jsonObject.put(WebField.ADD_WORKOUT_MEDIA.ITEM_ID, itemId);
            jsonObject.put(WebField.ADD_WORKOUT_MEDIA.MEDIA_TYPE, mediaType);
            if (spliteCount == totalSplite - 1) {
                jsonObject.put(WebField.ADD_WORKOUT_MEDIA.IS_COMPLETED, 1);
            } else {
                jsonObject.put(WebField.ADD_WORKOUT_MEDIA.IS_COMPLETED, 0);
            }
            if (mediaType == 3) {
                jsonObject.put(WebField.ADD_WORKOUT_MEDIA.ITEM_THUMB, base64Thumb);
                jsonObject.put(WebField.ADD_WORKOUT_MEDIA.WORKOUT_SOURCE_DATA, base64);
            } else {
                jsonObject.put(WebField.ADD_WORKOUT_MEDIA.WORKOUT_SOURCE_DATA, strProPicBase64);
//                jsonObject.put(WebField.ADD_WORKOUT_MEDIA.WORKOUT_SOURCE_DATA, base64);
            }

            LogM.LogE("Request : AddWorkoutMedia : " + jsonObject.toString());

            new GetJsonWithAndroidNetworkingLib(MiTrainingActivity.this, jsonObject, WebField.BASE_URL + WebField.ADD_WORKOUT_MEDIA.MODE, 0, new OnUpdateListener() {
                @Override
                public void onUpdateComplete(JSONObject jsonObject, boolean isSuccess) {
                    LogM.LogE("Response : AddWorkoutMedia : " + jsonObject.toString());
                    MiTrainingAddWorkout mTrainingAddWorkout = new Gson().fromJson(String.valueOf(jsonObject), MiTrainingAddWorkout.class);
                    if (isSuccess) {
                        int itemId = mTrainingAddWorkout.getItemId();
                        spliteCount++;
                        if (mediaType == 3 && spliteCount < totalSplite) {
                            callAddWorkoutMedia(3, itemId);
                        } else {
                            alWorkoutImagesAndVideos.add(mTrainingAddWorkout);
                            if (rvVideoPhoto.getVisibility() == View.GONE) {
                                rvVideoPhoto.setVisibility(View.VISIBLE);
                            }
                            adapter.notifyDataSetChanged();
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                                progressDialog = null;
                            }
                        }
                    } else {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                            progressDialog = null;
                        }
                        AlertDialogUtility.showSnakeBar(mTrainingAddWorkout.getMessage(), snackBarView, MiTrainingActivity.this);
                    }
                }
            }).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void callAddWorkout() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(WebField.ADD_WORKOUT.USER_ID, SessionManager.getUserId(MiTrainingActivity.this));
            jsonObject.put(WebField.ADD_WORKOUT.ACCESS_TOKEN, SessionManager.getAccessToken(MiTrainingActivity.this));
            jsonObject.put(WebField.ADD_WORKOUT.WORKOUT_NAME, workoutName);
            jsonObject.put(WebField.ADD_WORKOUT.WORKOUT_CATEGORY_ID, categoryId);
            jsonObject.put(WebField.ADD_WORKOUT.WORKOUT_CATEGORY_NAME, workoutCategory);
            jsonObject.put(WebField.ADD_WORKOUT.EXCERCISE_ID, exercisesId);
            jsonObject.put(WebField.ADD_WORKOUT.EXCERCISE_NAME, exercisesName);

            JSONObject jObjTime = new JSONObject();
            jObjTime.put(WebField.ADD_WORKOUT.HOUR, hour);
            jObjTime.put(WebField.ADD_WORKOUT.MIN, minute);

            jsonObject.put(WebField.ADD_WORKOUT.WORKOUT_DURATION, jObjTime);
            LogM.LogE("Request : AddWorkout : " + jsonObject.toString());

            new GetJsonWithAndroidNetworkingLib(MiTrainingActivity.this, jsonObject, WebField.BASE_URL + WebField.ADD_WORKOUT.MODE, 1, new OnUpdateListener() {
                @Override
                public void onUpdateComplete(JSONObject jsonObject, boolean isSuccess) {
                    LogM.LogE("Response : AddWorkout : " + jsonObject.toString());
                    if (isSuccess) {
                        onBackPressed();
                    } else {
                        AlertDialogUtility.showSnakeBar("", snackBarView, MiTrainingActivity.this);
                    }
                }
            }).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void callRemoveWorkoutMedia() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(WebField.REMOVE_WORKOUT_MEDIA.USER_ID, SessionManager.getUserId(MiTrainingActivity.this));
            jsonObject.put(WebField.REMOVE_WORKOUT_MEDIA.ACCESS_TOKEN, SessionManager.getAccessToken(MiTrainingActivity.this));
            LogM.LogE("Request : RemoveWorkoutMedia : " + jsonObject.toString());

            new GetJsonWithAndroidNetworkingLib(MiTrainingActivity.this, jsonObject, WebField.BASE_URL + WebField.REMOVE_WORKOUT_MEDIA.MODE, 0, new OnUpdateListener() {
                @Override
                public void onUpdateComplete(JSONObject jsonObject, boolean isSuccess) {
                    LogM.LogE("Response : RemoveWorkoutMedia : " + jsonObject.toString());
                }
            }).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
