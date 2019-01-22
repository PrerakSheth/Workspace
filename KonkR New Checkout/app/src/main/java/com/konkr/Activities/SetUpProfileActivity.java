package com.konkr.Activities;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.os.ParcelFileDescriptor;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.AppCompatSpinner;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.facebook.cache.common.SimpleCacheKey;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.google.gson.Gson;
import com.konkr.Models.AddExtraPhoto;
import com.konkr.Models.IntroductoryVideo;
import com.konkr.Models.SetUpProfile;
import com.konkr.Models.SuggestedTrainingGoals;
import com.konkr.Models.UserDetails;
import com.konkr.R;
import com.konkr.SharedPreferences.SessionManager;
import com.konkr.Utils.AlertDialogUtility;
import com.konkr.Utils.BaseActivity;
import com.konkr.Utils.ConnectivityDetector;
import com.konkr.Utils.DateTime;
import com.konkr.Utils.DecimalDigitsInputFilter;
import com.konkr.Utils.FileHandeling;
import com.konkr.Utils.GlobalData;
import com.konkr.Utils.GlobalMethods;
import com.konkr.Utils.KeyboardUtility;
import com.konkr.Utils.LogM;
import com.konkr.Utils.MyClass;
import com.konkr.Utils.MyEditText;
import com.konkr.Utils.MyTextView;
import com.konkr.Utils.PermissionsHelper;
import com.konkr.Webservices.GetJsonWithAndroidNetworkingLib;
import com.konkr.Webservices.OnUpdateListener;
import com.konkr.Webservices.WebField;
import com.konkr.databinding.ActivitySetUpProfileBinding;
import com.vincent.videocompressor.VideoCompress;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Timer;
import java.util.TimerTask;

import mabbas007.tagsedittext.TagsEditText;

public class SetUpProfileActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private ActivitySetUpProfileBinding binding;
    private View snackBarView;
    private SimpleDraweeView ivUserProfile;
    private ImageView ivPhotoOne;
    private ImageView ivPhotoTwo;
    private ImageView ivPhotoThree;
    private ImageView ivPhotoFour;
    private ImageView ivPhotoFive;
    private ImageView ivPhotoSix;
    private ImageView ivPhotoSeven;
    private ImageView ivPhotoEight;
    private ImageView ivPhotoNine;
    private SimpleDraweeView ivIntroductoryVideo;
    private ImageButton ibEdit;
    private ConstraintLayout clVideoView;

    private MyEditText etFirstName;
    private MyEditText etLastName;
    // private MyEditText etNickname;
    private MyTextView tvDateOfBirth;
    private MyTextView tvGender;
    private AppCompatSpinner spinerGender;
    private MyTextView tvBodyType;
    private TagsEditText etCurrentTrainingGoal;

    private ImageView ivEctomorph;
    private ImageView ivMesomorph;
    private ImageView ivEndomorph;

    private MyEditText etWeight;
    private MyEditText etHeight;
    private MyEditText etBodyFat;

    private MyEditText etBio;
    private MyTextView tvTwoHundredCharacter;

    private MyEditText etFacebook;
    private MyEditText etInstagram;
    private MyEditText etSnapchat;
    private MyEditText etTwitter;
    private MyEditText etYouTube;
    private MyEditText etLinkedin;

    private TextView tvUpdateProfile, tvAddIntroductoryVideo;
    private int isSubscribed = 0;

    private int day, month, year, maxyear;
    private Calendar calendar;
    private Activity context;

    private String deviceToken;

    private String firtsName;
    private String lastName;
    private String nickName;
    private String dateOfBirth;
    private String gender;
    private String currentTrainingGoal;
    private String bodyType = "Mesomorph";
    private String weight;
    private String height;
    private String bodyFat;
    private String bio;
    private String facebook;
    private String instagram;
    private String snapchat;
    private String twitter;
    private String youtube;
    private String linkedin;

    private ArrayAdapter<String> genderArrayAdapter;
    private String[] genderType;
    private int strTypeOfGender;

    private final int MY_PERMISSIONS_REQUEST_CAMERA = 108;
    private static final int SELECT_GALLERY_PIC = 101;
    private static final int SELECT_CAMERA_PIC = 99;
    private static final int PIC_CROP = 152;

    private String imageClick;
    private int photoPosition = 0;
    String photoUrl;
    String photoUrlOne = "";
    String photoUrlTwo = "";
    String photoUrlThree = "";
    String photoUrlFour = "";
    String photoUrlFive = "";
    String photoUrlSix = "";
    String photoUrlSeven = "";
    String photoUrlEight = "";
    String photoUrlNine = "";
    private String introductoryVideoURL = "";

    private boolean photoOneClick = false;
    private boolean photoTwoClick = false;
    private boolean photoThreeClick = false;
    private boolean photoFourClick = false;
    private boolean photoFiveClick = false;
    private boolean photoSixClick = false;
    private boolean photoSevenClick = false;
    private boolean photoEightClick = false;
    private boolean photoNineClick = false;

    private static final int FULL_IMAGE = 106;

    String type = "";
    private ScrollView scrollView;

    private String fbFirstName;
    private String fbLastName;
    private String fbProfilePic;

    private MyTextView tvMiMeals;
    private MyTextView tvMiTraining;
    private MyTextView tvMiSupps;

    private File filepath, cropfilepath;
    private FrameLayout frame;

    private ConstraintLayout clMis;
    private int isFromProfile;
    private ImageView ivBack;
    private MyTextView tvSetUpProfile;
    StringBuffer sb;

    private String strProPicBase64 = "";

    UserDetails userDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_set_up_profile);
        context = SetUpProfileActivity.this;
        snackBarView = findViewById(android.R.id.content);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        bindViews();
        scrollView.setVisibility(View.VISIBLE);
        setListener();
        getGenderArray();
        setTextWatcher();
        if (getIntent() != null) {
            if (getIntent().getStringExtra(GlobalData.FB_FIRST_NAME) != null && getIntent().getStringExtra(GlobalData.FB_LAST_NAME) != null
                    && getIntent().getStringExtra(GlobalData.FB_PROFILE_PIC) != null) {
                fbFirstName = getIntent().getStringExtra(GlobalData.FB_FIRST_NAME);
                fbLastName = getIntent().getStringExtra(GlobalData.FB_LAST_NAME);
                fbProfilePic = getIntent().getStringExtra(GlobalData.FB_PROFILE_PIC);
                etFirstName.setText(fbFirstName);
                etLastName.setText(fbLastName);
                ivUserProfile.setImageURI(Uri.parse(fbProfilePic));
                LogM.LogE("fbProfilePic-->" + fbProfilePic);
                strProPicBase64 = com.konkr.Utils.Base64.converToBase64(getBitmapFromURL(fbProfilePic), ivUserProfile);
            }
        }

        isFromProfile = getIntent().getIntExtra(GlobalData.IS_FROM_PROFILE, 0);
        if (isFromProfile == 1) {
            clMis.setVisibility(View.GONE);
            ivBack.setVisibility(View.VISIBLE);
            ivBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    KeyboardUtility.HideKeyboard(context, view);
                    onBackPressed();
                }
            });
            tvSetUpProfile.setText(getString(R.string.title_edit_profile));
            if (!ConnectivityDetector.isConnectingToInternet(SetUpProfileActivity.this)) {
                AlertDialogUtility.showInternetAlert(SetUpProfileActivity.this);
                return;
            }
//            callGetUserDetails();
        } else {
            etCurrentTrainingGoal.addTextChangedListener(mTextWatcher);
        }

        callGetUserDetails(0);

        etBio.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                tvTwoHundredCharacter.setText(200 - s.toString().length() + "");
            }
        });

        etBio.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (v.getId() == R.id.etBio) {
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                    switch (event.getAction() & MotionEvent.ACTION_MASK) {
                        case MotionEvent.ACTION_UP:
                            v.getParent().requestDisallowInterceptTouchEvent(false);
                            break;
                    }
                }
                return false;
            }
        });

        etCurrentTrainingGoal.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    etCurrentTrainingGoal.dismissDropDown();
                    callGetSuggestedTrainingGoals(etCurrentTrainingGoal.getText().toString().trim());
                    return true;
                }
                return false;
            }
        });
    }

    private String current = "";

    private void setTextWatcher() {
        try {
            etFacebook.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (!s.toString().equals(current)) {
                        etFacebook.removeTextChangedListener(this);

                        String mString = s.toString().replace("https://", "");
                        if (mString.length() <= 0) {
                            etFacebook.setText("");
                            etFacebook.addTextChangedListener(this);
                            return;
                        }
                        String formatted = "https://" + mString;
                        current = formatted;
                        etFacebook.setText(formatted);
                        etFacebook.setSelection(formatted.length());

                        etFacebook.addTextChangedListener(this);
                    }
                }
            });

            etInstagram.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (!s.toString().equals(current)) {
                        etInstagram.removeTextChangedListener(this);

                        String mString = s.toString().replace("https://", "");
                        if (mString.length() <= 0) {
                            etInstagram.setText("");
                            etInstagram.addTextChangedListener(this);
                            return;
                        }
                        String formatted = "https://" + mString;
                        current = formatted;
                        etInstagram.setText(formatted);
                        etInstagram.setSelection(formatted.length());

                        etInstagram.addTextChangedListener(this);
                    }
                }
            });

            etSnapchat.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (!s.toString().equals(current)) {
                        etSnapchat.removeTextChangedListener(this);

                        String mString = s.toString().replace("https://", "");
                        if (mString.length() <= 0) {
                            etSnapchat.setText("");
                            etSnapchat.addTextChangedListener(this);
                            return;
                        }
                        String formatted = "https://" + mString;
                        current = formatted;
                        etSnapchat.setText(formatted);
                        etSnapchat.setSelection(formatted.length());

                        etSnapchat.addTextChangedListener(this);
                    }
                }
            });

            etTwitter.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (!s.toString().equals(current)) {
                        etTwitter.removeTextChangedListener(this);

                        String mString = s.toString().replace("https://", "");
                        if (mString.length() <= 0) {
                            etTwitter.setText("");
                            etTwitter.addTextChangedListener(this);
                            return;
                        }
                        String formatted = "https://" + mString;
                        current = formatted;
                        etTwitter.setText(formatted);
                        etTwitter.setSelection(formatted.length());

                        etTwitter.addTextChangedListener(this);
                    }
                }
            });

            etYouTube.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (!s.toString().equals(current)) {
                        etYouTube.removeTextChangedListener(this);

                        String mString = s.toString().replace("https://", "");
                        if (mString.length() <= 0) {
                            etYouTube.setText("");
                            etYouTube.addTextChangedListener(this);
                            return;
                        }
                        String formatted = "https://" + mString;
                        current = formatted;
                        etYouTube.setText(formatted);
                        etYouTube.setSelection(formatted.length());

                        etYouTube.addTextChangedListener(this);
                    }
                }
            });

            etLinkedin.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (!s.toString().equals(current)) {
                        etLinkedin.removeTextChangedListener(this);

                        String mString = s.toString().replace("https://", "");
                        if (mString.length() <= 0) {
                            etLinkedin.setText("");
                            etLinkedin.addTextChangedListener(this);
                            return;
                        }
                        String formatted = "https://" + mString;
                        current = formatted;
                        etLinkedin.setText(formatted);
                        etLinkedin.setSelection(formatted.length());

                        etLinkedin.addTextChangedListener(this);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


        etWeight.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!etWeight.getText().toString().isEmpty()) {
                    etWeight.setSelection(etWeight.getText().length() - 3);
                }

            }
        });


        etWeight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//edtWeight.setText(""+charSequence.toString()+"kg");
                Log.e("newchar", "" + charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals(current)) {
                    etWeight.removeTextChangedListener(this);


                    String mString = s.toString().replaceAll("[ KG]", "");
                    if (mString.length() <= 0) {
                        etWeight.setText("");
                        etWeight.addTextChangedListener(this);
                        return;
                    }

                    String formatted = mString + " KG";
                    current = formatted;
                    etWeight.setText(formatted);
                    etWeight.setSelection(formatted.length() - 3);
                    etWeight.addTextChangedListener(this);
                }

            }
        });
        //---------------------------

        etHeight.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!etHeight.getText().toString().isEmpty()) {
                    etHeight.setSelection(etHeight.getText().length() - 3);
                }

            }
        });


        etHeight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//etHeight.setText(""+charSequence.toString()+"kg");
                Log.e("newchar", "" + charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals(current)) {
                    etHeight.removeTextChangedListener(this);


                    String mString = s.toString().replaceAll("[ CM]", "");
                    if (mString.length() <= 0) {
                        etHeight.setText("");
                        etHeight.addTextChangedListener(this);
                        return;
                    }

                    String formatted = mString + " CM";
                    current = formatted;
                    etHeight.setText(formatted);
                    etHeight.setSelection(formatted.length() - 3);
                    etHeight.addTextChangedListener(this);
                }

            }
        });


        //////////////////////


        etBodyFat.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!etBodyFat.getText().toString().isEmpty()) {
                    etBodyFat.setSelection(etBodyFat.getText().length() - 3);
                }

            }
        });


        etBodyFat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//etBodyFat.setText(""+charSequence.toString()+"kg");
                Log.e("newchar", "" + charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals(current)) {
                    etBodyFat.removeTextChangedListener(this);


                    String mString = s.toString().replaceAll("[ %]", "");
                    if (mString.length() <= 0) {
                        etBodyFat.setText("");
                        etBodyFat.addTextChangedListener(this);
                        return;
                    }

                    String formatted = mString + " %";
                    current = formatted;
                    etBodyFat.setText(formatted);
                    etBodyFat.setSelection(formatted.length() - 2);
                    etBodyFat.addTextChangedListener(this);
                }

            }
        });


    }

    private TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (timer != null)
                timer.cancel();
        }

        @Override
        public void afterTextChanged(Editable s) {
            try {
                if (s.toString().trim().length() > 0) {
                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            if (flag) {
                                Looper.prepare();
                                String[] alStr = s.toString().trim().split(" ");
                                callGetSuggestedTrainingGoals(alStr[alStr.length - 1]);
                                Looper.loop();
                            }
                        }
                    }, DELAY);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    private void bindViews() {
        scrollView = binding.scrollView;
        ivUserProfile = binding.ivUserProfile;
        ivPhotoOne = binding.ivPhotoOne;
        ivPhotoTwo = binding.ivPhotoTwo;
        ivPhotoThree = binding.ivPhotoThree;
        ivPhotoFour = binding.ivPhotoFour;
        ivPhotoFive = binding.ivPhotoFive;
        ivPhotoSix = binding.ivPhotoSix;
        ivPhotoSeven = binding.ivPhotoSeven;
        ivPhotoEight = binding.ivPhotoEight;
        ivPhotoNine = binding.ivPhotoNine;
        ivIntroductoryVideo = binding.ivIntroductoryVideo;
        ibEdit = binding.ibEdit;
        clVideoView = binding.clVideoView;

        etFirstName = binding.etFirstName;
        etLastName = binding.etLastName;
        //  etNickname = binding.etNickname;
        tvDateOfBirth = binding.tvDateOfBirth;
        tvGender = binding.tvGender;
        spinerGender = binding.spinGender;
        tvBodyType = binding.tvBodyType;
        etCurrentTrainingGoal = binding.etCurrentTrainingGoal;
        etCurrentTrainingGoal.setThreshold(1);

        ivEctomorph = binding.ivEctomorph;
        ivMesomorph = binding.ivMesomorph;
        ivEndomorph = binding.ivEndomorph;

        etWeight = binding.etWeight;
        etHeight = binding.etHeight;
        etBodyFat = binding.etBodyFat;
        etBio = binding.etBio;
        tvTwoHundredCharacter = binding.tvTwoHundredCharacter;

        etFacebook = binding.etFacebook;
        etInstagram = binding.etInstagram;
        etSnapchat = binding.etSnapchat;
        etTwitter = binding.etTwitter;
        etYouTube = binding.etYouTube;
        etLinkedin = binding.etLinkedin;

        tvUpdateProfile = binding.tvUpdateProfile;
        tvAddIntroductoryVideo = binding.tvAddIntroductoryVideo;

        tvMiMeals = binding.tvMiMeals;
        tvMiTraining = binding.tvMiTraining;
        tvMiSupps = binding.tvMiSupps;
        frame = binding.frame;
        scrollView = binding.scrollView;

        clMis = binding.clMis;
        ivBack = binding.ivBack;
        tvSetUpProfile = binding.tvSetUpProfile;

        setTextLimit();
    }

    private void setTextLimit() {
        etHeight.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(3, 2, Double.POSITIVE_INFINITY)});
        etWeight.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(3, 2, Double.POSITIVE_INFINITY)});
        etBodyFat.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(2, 2, Double.POSITIVE_INFINITY)});
    }

    public void setListener() {
        tvUpdateProfile.setOnClickListener(this);
        tvAddIntroductoryVideo.setOnClickListener(this);
        tvDateOfBirth.setOnClickListener(this);
        tvGender.setOnClickListener(this);
        ivEndomorph.setOnClickListener(this);
        ivMesomorph.setOnClickListener(this);
        ivEctomorph.setOnClickListener(this);
        ivUserProfile.setOnClickListener(this);
        ivPhotoOne.setOnClickListener(this);
        tvMiMeals.setOnClickListener(this);
        tvMiTraining.setOnClickListener(this);
        tvMiSupps.setOnClickListener(this);
        etCurrentTrainingGoal.setOnItemClickListener(this);
        ivIntroductoryVideo.setOnClickListener(this);
        ibEdit.setOnClickListener(this);
//        ivPhotoTwo.setOnClickListener(this);
//        ivPhotoThree.setOnClickListener(this);
//        ivPhotoFour.setOnClickListener(this);
//        ivPhotoFive.setOnClickListener(this);
//        ivPhotoSix.setOnClickListener(this);
//        ivPhotoSeven.setOnClickListener(this);
//        ivPhotoEight.setOnClickListener(this);
//        ivPhotoNine.setOnClickListener(this);
    }

    private boolean flag = true;
    private Timer timer = new Timer();
    private final long DELAY = 100;

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        String item = parent.getItemAtPosition(position).toString();
        flag = false;
//        etCurrentTrainingGoal.setText(item);
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                flag = true;
            }
        }, DELAY * 2);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvUpdateProfile:
                if (isValid()) {
                    callUpdateProfile();
                }
                break;

            case R.id.tvAddIntroductoryVideo:
                spliteCount = 0;
                totalSplite = 1;
                if (!ConnectivityDetector.isConnectingToInternet(SetUpProfileActivity.this)) {
                    AlertDialogUtility.showInternetAlert(SetUpProfileActivity.this);
                    return;
                }
                PermissionsHelper.permissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO}).checkPermissions(SetUpProfileActivity.this, new PermissionsHelper.OnPermissionResult() {
                    @Override
                    public void onGranted() {
                        if (isSubscribed == 1) {
                            GlobalData.CAMERA_ID = 0;
                            Intent intent = new Intent(SetUpProfileActivity.this, CustomCameraActivity.class);
                            startActivityForResult(intent, GlobalData.CAPTURE_VIDEO);
                        } else {
                            Intent intent = new Intent(SetUpProfileActivity.this, PremiumMemberShipActivity.class);
                            startActivityForResult(intent, GlobalData.PREMIUM_MEMBER);
                        }
                    }

                    @Override
                    public void notGranted() {
                    }
                });
                break;

            case R.id.ivIntroductoryVideo:
                if (introductoryVideoURL.trim().length() > 0) {
                    Intent intentVideo = new Intent(getApplicationContext(), VideoActivity.class);
                    intentVideo.putExtra(GlobalData.IS_FROM,GlobalData.SETUP_PROFILE);
                    intentVideo.putExtra("mediaType", 3);
                    intentVideo.putExtra("videoUrl", introductoryVideoURL);
                    startActivity(intentVideo);
                }
                break;

            case R.id.ibEdit:
                isEdit = true;
                tvAddIntroductoryVideo.performClick();
                break;

            case R.id.tvDateOfBirth:
                KeyboardUtility.HideKeyboard(context, tvDateOfBirth);

                if (tvDateOfBirth.getText().toString().length() > 0) {
                    String selecetedDate = tvDateOfBirth.getText().toString();
                    String[] dateParts = selecetedDate.split("/");
                    year = Integer.parseInt(dateParts[2]);
                    day = Integer.parseInt(dateParts[0]);
                    month = Integer.parseInt(dateParts[1]);
                    openDatepicker(true);
                } else {
                    openDatepicker(false);
                }
                break;

            case R.id.ivUserProfile:
                imageClick = GlobalData.PHOTOS_USER_PROFILE;
                checkPermission(getResources().getString(R.string.title_choose_profile_picture));
                break;

            case R.id.ivEctomorph:
                if (type.equalsIgnoreCase(genderType[1])) {
                    ivEctomorph.setBackground(getResources().getDrawable(R.drawable.ectomorph_selected));
                    ivMesomorph.setBackground(getResources().getDrawable(R.drawable.mesomorph));
                    ivEndomorph.setBackground(getResources().getDrawable(R.drawable.endomorph));
                    bodyType = getResources().getString(R.string.Ectomorph);
                } else if (type.equalsIgnoreCase(genderType[2])) {
                    ivEctomorph.setBackground(getResources().getDrawable(R.drawable.female_ectomorph_selected));
                    ivMesomorph.setBackground(getResources().getDrawable(R.drawable.female_mesomorph));
                    ivEndomorph.setBackground(getResources().getDrawable(R.drawable.female_endomorph));
                    bodyType = getResources().getString(R.string.Female_Ectomorph);
                }
                break;

            case R.id.ivMesomorph:
                if (type.equalsIgnoreCase(genderType[1])) {
                    ivEctomorph.setBackground(getResources().getDrawable(R.drawable.ectomorph));
                    ivMesomorph.setBackground(getResources().getDrawable(R.drawable.mesomorph_selected));
                    ivEndomorph.setBackground(getResources().getDrawable(R.drawable.endomorph));
                    bodyType = getResources().getString(R.string.Mesomorph);
                } else {
                    ivEctomorph.setBackground(getResources().getDrawable(R.drawable.female_ectomorph));
                    ivMesomorph.setBackground(getResources().getDrawable(R.drawable.female_mesomorph_selected));
                    ivEndomorph.setBackground(getResources().getDrawable(R.drawable.female_endomorph));
                    bodyType = getResources().getString(R.string.Female_Mesomorph);
                }
                break;

            case R.id.ivEndomorph:
                if (type.equalsIgnoreCase(genderType[1])) {
                    ivEctomorph.setBackground(getResources().getDrawable(R.drawable.ectomorph));
                    ivMesomorph.setBackground(getResources().getDrawable(R.drawable.mesomorph));
                    ivEndomorph.setBackground(getResources().getDrawable(R.drawable.endomorph_selected));
                    bodyType = getResources().getString(R.string.Endomorph);
                } else {
                    ivEctomorph.setBackground(getResources().getDrawable(R.drawable.female_ectomorph));
                    ivMesomorph.setBackground(getResources().getDrawable(R.drawable.female_mesomorph));
                    ivEndomorph.setBackground(getResources().getDrawable(R.drawable.female_endomorph_selected));
                    bodyType = getResources().getString(R.string.Female_Endomorph);
                }
                break;

            case R.id.tvGender:
                KeyboardUtility.HideKeyboard(this, tvGender);
                tvGender.requestFocus();
                if (genderType != null) {
                    spinerGender.performClick();
                }
                break;

            case R.id.ivPhotoOne:
                if (photoOneClick) {
                    ivPhotoOne.setOnClickListener(this);
                    moveToFullScren(photoUrlOne, 1);
                } else {
                    imageClick = GlobalData.PHOTOS1;
                    checkPermission(getResources().getString(R.string.title_add_additional_picture));
                }
                break;

            case R.id.ivPhotoTwo:
                if (photoTwoClick) {
                    ivPhotoTwo.setOnClickListener(this);
                    moveToFullScren(photoUrlTwo, 2);
                } else {
                    imageClick = GlobalData.PHOTOS2;
                    checkPermission(getResources().getString(R.string.title_add_additional_picture));
                }
                break;

            case R.id.ivPhotoThree:
                if (photoThreeClick) {
                    ivPhotoThree.setOnClickListener(this);
                    moveToFullScren(photoUrlThree, 3);
                } else {
                    imageClick = GlobalData.PHOTOS3;
                    checkPermission(getResources().getString(R.string.title_add_additional_picture));
                }
                break;

            case R.id.ivPhotoFour:
                if (photoFourClick) {
                    ivPhotoFour.setOnClickListener(this);
                    moveToFullScren(photoUrlFour, 4);
                } else {
                    imageClick = GlobalData.PHOTOS4;
                    checkPermission(getResources().getString(R.string.title_add_additional_picture));
                }
                break;

            case R.id.ivPhotoFive:
                if (photoFiveClick) {
                    ivPhotoFive.setOnClickListener(this);
                    moveToFullScren(photoUrlFive, 5);
                } else {
                    imageClick = GlobalData.PHOTOS5;
                    checkPermission(getResources().getString(R.string.title_add_additional_picture));
                }
                break;

            case R.id.ivPhotoSix:
                if (photoSixClick) {
                    ivPhotoSix.setOnClickListener(this);
                    moveToFullScren(photoUrlSix, 6);
                } else {
                    imageClick = GlobalData.PHOTOS6;
                    checkPermission(getResources().getString(R.string.title_add_additional_picture));
                }
                break;

            case R.id.ivPhotoSeven:
                if (photoSevenClick) {
                    ivPhotoSeven.setOnClickListener(this);
                    moveToFullScren(photoUrlSeven, 7);
                } else {
                    imageClick = GlobalData.PHOTOS7;
                    checkPermission(getResources().getString(R.string.title_add_additional_picture));
                }
                break;

            case R.id.ivPhotoEight:
                if (photoEightClick) {
                    ivPhotoEight.setOnClickListener(this);
                    moveToFullScren(photoUrlEight, 8);
                } else {
                    imageClick = GlobalData.PHOTOS8;
                    checkPermission(getResources().getString(R.string.title_add_additional_picture));
                }
                break;

            case R.id.ivPhotoNine:
                if (photoNineClick) {
                    ivPhotoNine.setOnClickListener(this);
                    moveToFullScren(photoUrlNine, 9);
                } else {
                    imageClick = GlobalData.PHOTOS9;
                    checkPermission(getResources().getString(R.string.title_add_additional_picture));
                }
                break;

            case R.id.tvMiMeals:
                Intent intentMealAddDetails = new Intent(context, MealDetailsActivity.class);
                intentMealAddDetails.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentMealAddDetails);
                break;

            case R.id.tvMiTraining:
                Intent intentMiTraining = new Intent(context, MiTrainingProfileActivity.class);
                intentMiTraining.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentMiTraining);
                break;

            case R.id.tvMiSupps:
                Intent intent = new Intent(context, MiSupplimentProfileActivity.class);
                intent.putExtra(GlobalData.OTHER_USER_ID, 0);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
        }
    }

    public void replaceFragment(Fragment fragment) {
        frame.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.GONE);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment);
        fragmentTransaction.addToBackStack(fragment.toString());
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();
    }

    private void moveToFullScren(String url, int position) {
        int mediaIdPosition = position - 1;
        Intent intent = new Intent(context, FullScreenActivity.class);
        intent.putExtra(GlobalData.PHOTO_POSITION, position);
        intent.putExtra(GlobalData.PHOTO_URL, url);
        intent.putExtra(GlobalData.MEDIA_ID, userDetails.getUserDetails().getImageArray().get(mediaIdPosition).getMediaId());
//        for (int i = 0; i < userDetails.getUserDetails().getImageArray().size(); i++) {
//            if (mediaIdPosition == i) {
//                intent.putExtra(GlobalData.MEDIA_ID, userDetails.getUserDetails().getImageArray().get(i).getMediaId());
//            }
//        }

        startActivityForResult(intent, FULL_IMAGE);
    }

    private boolean isValid() {
        firtsName = etFirstName.getText().toString();
        lastName = etLastName.getText().toString();
        // nickName = etNickname.getText().toString();

        dateOfBirth = tvDateOfBirth.getText().toString();
//        gender = spinerGender.getSelectedItem().toString();
        gender = tvGender.getText().toString();
        currentTrainingGoal = etCurrentTrainingGoal.getText().toString();
        weight = etWeight.getText().toString();
        height = etHeight.getText().toString();
        bodyFat = etBodyFat.getText().toString();
        bio = etBio.getText().toString();
        facebook = etFacebook.getText().toString();
        instagram = etInstagram.getText().toString();
        snapchat = etSnapchat.getText().toString();
        twitter = etTwitter.getText().toString();
        youtube = etYouTube.getText().toString();
        linkedin = etLinkedin.getText().toString();

        if (firtsName.isEmpty()) {
            scrollView.fullScroll(ScrollView.FOCUS_UP);
            scrollView.smoothScrollTo(0, 0);
            etFirstName.requestFocus();
            AlertDialogUtility.showSnakeBar(getString(R.string.sp_error_first_name), etFirstName, this);
            return false;
        } else if (lastName.isEmpty()) {
            scrollView.fullScroll(ScrollView.FOCUS_UP);
            etLastName.requestFocus();
            AlertDialogUtility.showSnakeBar(getString(R.string.sp_error_last_name), etLastName, this);
            return false;
        }
//        } else if (nickName.isEmpty()) {
//            scrollView.fullScroll(ScrollView.FOCUS_UP);
//            etNickname.requestFocus();
//            AlertDialogUtility.showSnakeBar(getString(R.string.sp_error_nick_name), etNickname, this);
//            return false;
//        }
        else if (dateOfBirth.isEmpty()) {
            tvDateOfBirth.requestFocus();
            scrollView.fullScroll(ScrollView.FOCUS_UP);
            AlertDialogUtility.showSnakeBar(getString(R.string.sp_error_date_of_birth), tvDateOfBirth, this);
            return false;
        } else if (gender.equalsIgnoreCase(getResources().getString(R.string.sp_gender))) {
            spinerGender.requestFocus();
            scrollView.fullScroll(ScrollView.FOCUS_UP);
            AlertDialogUtility.showSnakeBar(getString(R.string.sp_error_gender), spinerGender, this);
            return false;
//        } else if (currentTrainingGoal.isEmpty()) {
//            etCurrentTrainingGoal.requestFocus();
//            scrollView.fullScroll(ScrollView.FOCUS_UP);
//            AlertDialogUtility.showSnakeBar(getString(R.string.sp_error_current_training_goal), etCurrentTrainingGoal, this);
//            return false;
//        } else if (bodyType.isEmpty()) {
//            AlertDialogUtility.showSnakeBar(getString(R.string.sp_error_body_type), tvBodyType, this);
//            return false;
//        } else if (weight.isEmpty()) {
//            etWeight.requestFocus();
//            AlertDialogUtility.showSnakeBar(getString(R.string.sp_error_weight), etWeight, this);
//            return false;
//        } else if (height.isEmpty()) {
//            etHeight.requestFocus();
//            AlertDialogUtility.showSnakeBar(getString(R.string.sp_error_height), etHeight, this);
//            return false;
//        } else if (bodyFat.isEmpty()) {
//            etBodyFat.requestFocus();
//            AlertDialogUtility.showSnakeBar(getString(R.string.sp_error_fat), etBodyFat, this);
//            return false;
//        } else if (bio.isEmpty()) {
//            etBio.requestFocus();
//            AlertDialogUtility.showSnakeBar(getString(R.string.sp_error_bio), etBio, this);
//            return false;
        } else if (facebook.length() > 0 && !GlobalMethods.isValidUrl(facebook)) {
            etFacebook.requestFocus();
            AlertDialogUtility.showSnakeBar(getString(R.string.sp_error_facebook_valid_url), etFacebook, this);
            return false;
        } else if (instagram.length() > 0 && !GlobalMethods.isValidUrl(instagram)) {
            etInstagram.requestFocus();
            AlertDialogUtility.showSnakeBar(getString(R.string.sp_error_insta_valid_url), etInstagram, this);
            return false;
        } else if (snapchat.length() > 0 && !GlobalMethods.isValidUrl(snapchat)) {
            etSnapchat.requestFocus();
            AlertDialogUtility.showSnakeBar(getString(R.string.sp_error_snap_valid_url), etSnapchat, this);
            return false;
        } else if (twitter.length() > 0 && !GlobalMethods.isValidUrl(twitter)) {
            etTwitter.requestFocus();
            AlertDialogUtility.showSnakeBar(getString(R.string.sp_error_twit_valid_url), etTwitter, this);
            return false;
        } else if (youtube.length() > 0 && !GlobalMethods.isValidUrl(youtube)) {
            etYouTube.requestFocus();
            AlertDialogUtility.showSnakeBar(getString(R.string.sp_error_youtube_valid_url), etYouTube, this);
            return false;
        } else if (linkedin.length() > 0 && !GlobalMethods.isValidUrl(linkedin)) {
            etLinkedin.requestFocus();
            AlertDialogUtility.showSnakeBar(getString(R.string.sp_error_linked_valid_url), etLinkedin, this);
            return false;
        }
        return true;
    }

    private void callGetUserDetails(final int isSubscribedCheck) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(WebField.GET_USER_DETAILS.USER_ID, SessionManager.getUserId(SetUpProfileActivity.this));
            jsonObject.put(WebField.GET_USER_DETAILS.ACCESS_TOKEN, SessionManager.getAccessToken(SetUpProfileActivity.this));
            jsonObject.put(WebField.GET_USER_DETAILS.OTHER_USER_ID, 0);

            LogM.LogE("Request : GetUserDetails : " + jsonObject.toString());
            new GetJsonWithAndroidNetworkingLib(context, jsonObject, WebField.BASE_URL + WebField.GET_USER_DETAILS.MODE, 1, new OnUpdateListener() {
                @Override
                public void onUpdateComplete(JSONObject jsonObject, boolean isSuccess) {
                    LogM.LogE("Response : GetUserDetails : " + jsonObject.toString());
                    if (isSuccess) {
                        userDetails = new Gson().fromJson(String.valueOf(jsonObject), UserDetails.class);
                        if (isSubscribedCheck == 1) {
                            isSubscribed = userDetails.getUserDetails().getIsSubscribed();
                            return;
                        }
                        setData(userDetails);
                    }
                }
            }).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setData(UserDetails userDetails) {
        try {
            isSubscribed = userDetails.getUserDetails().getIsSubscribed();
            SessionManager.setSubscribed(SetUpProfileActivity.this, isSubscribed);
            introductoryVideoURL = userDetails.getUserDetails().getVideoThumb().trim();
            if (introductoryVideoURL.length() > 0) {
                tvAddIntroductoryVideo.setVisibility(View.GONE);
                clVideoView.setVisibility(View.VISIBLE);
                ivIntroductoryVideo.setImageURI(Uri.parse(userDetails.getUserDetails().getVideoThumbImg().toString()));
            } else {
                tvAddIntroductoryVideo.setVisibility(View.VISIBLE);
                clVideoView.setVisibility(View.GONE);
            }
            ivUserProfile.setImageURI(Uri.parse(userDetails.getUserDetails().getProfilePic()));
            etFirstName.setText(userDetails.getUserDetails().getFirstName());
            etLastName.setText(userDetails.getUserDetails().getLastName());
            // etNickname.setText(userDetails.getUserDetails().getNickName());
            tvDateOfBirth.setText(userDetails.getUserDetails().getDob());

            if (userDetails.getUserDetails().getGender() < 2) {
                type = spinerGender.getItemAtPosition(userDetails.getUserDetails().getGender() + 1).toString();
                tvGender.setText(type);

                if (type.equalsIgnoreCase(genderType[1])) {
                    if (userDetails.getUserDetails().getBodyType().equalsIgnoreCase(getResources().getString(R.string.Ectomorph))) {
                        ivEctomorph.performClick();
                    } else if (userDetails.getUserDetails().getBodyType().equalsIgnoreCase(getResources().getString(R.string.Mesomorph))) {
                        ivMesomorph.performClick();
                    } else if (userDetails.getUserDetails().getBodyType().equalsIgnoreCase(getResources().getString(R.string.Endomorph))) {
                        ivEndomorph.performClick();
                    }
                } else if (type.equalsIgnoreCase(genderType[2])) {
                    if (userDetails.getUserDetails().getBodyType().equalsIgnoreCase(getResources().getString(R.string.Female_Ectomorph))) {
                        ivEctomorph.performClick();
                    } else if (userDetails.getUserDetails().getBodyType().equalsIgnoreCase(getResources().getString(R.string.Female_Mesomorph))) {
                        ivMesomorph.performClick();
                    } else if (userDetails.getUserDetails().getBodyType().equalsIgnoreCase(getResources().getString(R.string.Female_Endomorph))) {
                        ivEndomorph.performClick();
                    }
                }
            }
            for (int i = 0; i < userDetails.getUserDetails().getCurrentTrainingGoals().size(); i++) {
                etCurrentTrainingGoal.append(userDetails.getUserDetails().getCurrentTrainingGoals().get(i) + " ");
            }

            String wt = "", ht = "", fat = "";
            if (userDetails.getUserDetails().getWeight().contains(".00")) {
                wt = userDetails.getUserDetails().getWeight().substring(0, userDetails.getUserDetails().getWeight().indexOf("."));
                etWeight.setText(wt /*+ GlobalData.KG */);
            } else {
                if (!userDetails.getUserDetails().getWeight().equals("")) {
                    etWeight.setText(userDetails.getUserDetails().getWeight() /*+ GlobalData.KG*/);
                }
            }

            if (userDetails.getUserDetails().getHeight().contains(".00")) {
                ht = userDetails.getUserDetails().getHeight().substring(0, userDetails.getUserDetails().getHeight().indexOf("."));
                etHeight.setText(ht/* + GlobalData.CM*/);
            } else {
                if (!userDetails.getUserDetails().getHeight().equals("")) {
                    etHeight.setText(userDetails.getUserDetails().getHeight() /*+ GlobalData.CM*/);
                }
            }

            if (userDetails.getUserDetails().getBodyFat().contains(".00")) {
                fat = userDetails.getUserDetails().getBodyFat().substring(0, userDetails.getUserDetails().getBodyFat().indexOf("."));
                etBodyFat.setText(fat /*+ GlobalData.PERCENT*/);
            } else {
                if (!userDetails.getUserDetails().getBodyFat().equals("")) {
                    etBodyFat.setText(userDetails.getUserDetails().getBodyFat() + GlobalData.PERCENT);
                }
            }

            etBio.setText(userDetails.getUserDetails().getBio());
            etFacebook.setText(userDetails.getUserDetails().getSocialLinks().getFB());
            etInstagram.setText(userDetails.getUserDetails().getSocialLinks().getInstagram());
            etSnapchat.setText(userDetails.getUserDetails().getSocialLinks().getSnapchat());
            etTwitter.setText(userDetails.getUserDetails().getSocialLinks().getTwitter());
            etYouTube.setText(userDetails.getUserDetails().getSocialLinks().getYoutube());
            etLinkedin.setText(userDetails.getUserDetails().getSocialLinks().getLinkedIn());

            int position = 0;
            for (int i = 0; i < 9; i++) {
                if (i < userDetails.getUserDetails().getImageArray().size()) {
                    if (i == 0) {

                        Glide.with(this)
                                .load(userDetails.getUserDetails().getImageArray().get(i).getImage())
                                .apply(new RequestOptions()
                                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                                        .skipMemoryCache(true))
                                .into(ivPhotoOne);

                      //  Glide.with(this).load(userDetails.getUserDetails().getImageArray().get(i).getImage()).into(ivPhotoOne);
                      //  ivPhotoOne.setImageURI(userDetails.getUserDetails().getImageArray().get(i).getImage());
                        position = 1;
                        photoOneClick = true;
                        photoUrlOne = userDetails.getUserDetails().getImageArray().get(i).getImage();
                    }
                    if (i == 1) {
                        Glide.with(this)
                                .load(userDetails.getUserDetails().getImageArray().get(i).getImage())
                                .apply(new RequestOptions()
                                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                                        .skipMemoryCache(true))
                                .into(ivPhotoTwo);
                       // Glide.with(this).load(userDetails.getUserDetails().getImageArray().get(i).getImage()).into(ivPhotoTwo);
                        //ivPhotoTwo.setImageURI(userDetails.getUserDetails().getImageArray().get(i).getImage());
                        position = 2;
                        photoTwoClick = true;
                        photoUrlTwo = userDetails.getUserDetails().getImageArray().get(i).getImage();
                        ivPhotoTwo.setOnClickListener(this);
                    }
                    if (i == 2) {
                        Glide.with(this)
                                .load(userDetails.getUserDetails().getImageArray().get(i).getImage())
                                .apply(new RequestOptions()
                                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                                        .skipMemoryCache(true))
                                .into(ivPhotoThree);
                       // Glide.with(this).load(userDetails.getUserDetails().getImageArray().get(i).getImage()).into(ivPhotoThree);

                       // ivPhotoThree.setImageURI(userDetails.getUserDetails().getImageArray().get(i).getImage());
                        position = 3;
                        photoThreeClick = true;
                        photoUrlThree = userDetails.getUserDetails().getImageArray().get(i).getImage();
                        ivPhotoThree.setOnClickListener(this);
                    }
                    if (i == 3) {
                        Glide.with(this).load(userDetails.getUserDetails().getImageArray().get(i).getImage())
                                .apply(new RequestOptions()
                                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                                        .skipMemoryCache(true)).into(ivPhotoFour);

//                        ivPhotoFour.setImageURI(userDetails.getUserDetails().getImageArray().get(i).getImage());
                        position = 4;
                        photoFourClick = true;
                        photoUrlFour = userDetails.getUserDetails().getImageArray().get(i).getImage();
                        ivPhotoFour.setOnClickListener(this);
                    }
                    if (i == 4) {
                        Glide.with(this).load(userDetails.getUserDetails().getImageArray().get(i).getImage())
                                .apply(new RequestOptions()
                                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                                        .skipMemoryCache(true)).into(ivPhotoFive);
//                        ivPhotoFive.setImageURI(userDetails.getUserDetails().getImageArray().get(i).getImage());
                        position = 5;
                        photoFiveClick = true;
                        photoUrlFive = userDetails.getUserDetails().getImageArray().get(i).getImage();
                        ivPhotoFive.setOnClickListener(this);
                    }
                    if (i == 5) {
                        Glide.with(this).load(userDetails.getUserDetails().getImageArray().get(i).getImage())
                                .apply(new RequestOptions()
                                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                                        .skipMemoryCache(true)).into(ivPhotoSix);
//                        ivPhotoSix.setImageURI(userDetails.getUserDetails().getImageArray().get(i).getImage());
                        position = 6;
                        photoSixClick = true;
                        photoUrlSix = userDetails.getUserDetails().getImageArray().get(i).getImage();
                        ivPhotoSix.setOnClickListener(this);
                    }
                    if (i == 6) {
                        Glide.with(this).load(userDetails.getUserDetails().getImageArray().get(i).getImage())
                                .apply(new RequestOptions()
                                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                                        .skipMemoryCache(true)).into(ivPhotoSeven);
//                        ivPhotoSeven.setImageURI(userDetails.getUserDetails().getImageArray().get(i).getImage());
                        position = 7;
                        photoSevenClick = true;
                        photoUrlSeven = userDetails.getUserDetails().getImageArray().get(i).getImage();
                        ivPhotoSeven.setOnClickListener(this);
                    }
                    if (i == 7) {
                        Glide.with(this).load(userDetails.getUserDetails().getImageArray().get(i).getImage())
                                .apply(new RequestOptions()
                                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                                        .skipMemoryCache(true)).into(ivPhotoEight);
//                        ivPhotoEight.setImageURI(userDetails.getUserDetails().getImageArray().get(i).getImage());
                        position = 8;
                        photoEightClick = true;
                        photoUrlEight = userDetails.getUserDetails().getImageArray().get(i).getImage();
                        ivPhotoEight.setOnClickListener(this);
                    }
                    if (i == 8) {
                        Glide.with(this).load(userDetails.getUserDetails().getImageArray().get(i).getImage())
                                .apply(new RequestOptions()
                                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                                        .skipMemoryCache(true)).into(ivPhotoNine);
                        //ivPhotoNine.setImageURI(userDetails.getUserDetails().getImageArray().get(i).getImage());
                        position = 9;
                        photoNineClick = true;
                        photoUrlNine = userDetails.getUserDetails().getImageArray().get(i).getImage();
                        ivPhotoNine.setOnClickListener(this);
                    }
                    if (position != 0) {
                        setBackGroundForImages(position);
                    }
                }
            }

//            if (userDetails.getUserDetails().getImageArray() != null) {
//                int position = 0;
//                if (userDetails.getUserDetails().getImageArray().getPhoto1().trim().length() > 0) {
//                    ivPhotoOne.setImageURI(userDetails.getUserDetails().getImageArray().getPhoto1());
//                    position = 1;
//                    photoOneClick = true;
//                    photoUrlOne = userDetails.getUserDetails().getImageArray().getPhoto1();
//                }
//                if (userDetails.getUserDetails().getImageArray().getPhoto2().trim().length() > 0) {
//                    ivPhotoTwo.setImageURI(userDetails.getUserDetails().getImageArray().getPhoto2());
//                    position = 2;
//                    photoTwoClick = true;
//                    photoUrlTwo = userDetails.getUserDetails().getImageArray().getPhoto2();
//                    ivPhotoTwo.setOnClickListener(this);
//                }
//                if (userDetails.getUserDetails().getImageArray().getPhoto3().trim().length() > 0) {
//                    ivPhotoThree.setImageURI(userDetails.getUserDetails().getImageArray().getPhoto3());
//                    position = 3;
//                    photoThreeClick = true;
//                    photoUrlThree = userDetails.getUserDetails().getImageArray().getPhoto3();
//                    ivPhotoThree.setOnClickListener(this);
//                }
//                if (userDetails.getUserDetails().getImageArray().getPhoto4().trim().length() > 0) {
//                    ivPhotoFour.setImageURI(userDetails.getUserDetails().getImageArray().getPhoto4());
//                    position = 4;
//                    photoFourClick = true;
//                    photoUrlFour = userDetails.getUserDetails().getImageArray().getPhoto4();
//                    ivPhotoFour.setOnClickListener(this);
//                }
//                if (userDetails.getUserDetails().getImageArray().getPhoto5().trim().length() > 0) {
//                    ivPhotoFive.setImageURI(userDetails.getUserDetails().getImageArray().getPhoto5());
//                    position = 5;
//                    photoFiveClick = true;
//                    photoUrlFive = userDetails.getUserDetails().getImageArray().getPhoto5();
//                    ivPhotoFive.setOnClickListener(this);
//                }
//                if (userDetails.getUserDetails().getImageArray().getPhoto6().trim().length() > 0) {
//                    ivPhotoSix.setImageURI(userDetails.getUserDetails().getImageArray().getPhoto6());
//                    position = 6;
//                    photoSixClick = true;
//                    photoUrlSix = userDetails.getUserDetails().getImageArray().getPhoto6();
//                    ivPhotoSix.setOnClickListener(this);
//                }
//                if (userDetails.getUserDetails().getImageArray().getPhoto7().trim().length() > 0) {
//                    ivPhotoSeven.setImageURI(userDetails.getUserDetails().getImageArray().getPhoto7());
//                    position = 7;
//                    photoSevenClick = true;
//                    photoUrlSeven = userDetails.getUserDetails().getImageArray().getPhoto7();
//                    ivPhotoSeven.setOnClickListener(this);
//                }
//                if (userDetails.getUserDetails().getImageArray().getPhoto8().trim().length() > 0) {
//                    ivPhotoEight.setImageURI(userDetails.getUserDetails().getImageArray().getPhoto8());
//                    position = 8;
//                    photoEightClick = true;
//                    photoUrlEight = userDetails.getUserDetails().getImageArray().getPhoto8();
//                    ivPhotoEight.setOnClickListener(this);
//                }
//                if (userDetails.getUserDetails().getImageArray().getPhoto9().trim().length() > 0) {
//                    ivPhotoNine.setImageURI(userDetails.getUserDetails().getImageArray().getPhoto9());
//                    position = 9;
//                    photoNineClick = true;
//                    photoUrlNine = userDetails.getUserDetails().getImageArray().getPhoto9();
//                    ivPhotoNine.setOnClickListener(this);
//                }
//                if (position != 0) {
//                    setBackGroundForImages(position);
//                }
//            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            etCurrentTrainingGoal.addTextChangedListener(mTextWatcher);
        }
    }

    private void callUpdateProfile() {
        weight = weight.replaceAll("[^0-9]", "");
        height = height.replaceAll("[^0-9]", "");
        bodyFat = bodyFat.replaceAll("[^0-9]", "");

        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(WebField.SET_UP_PROFILE.PARAM_USERID, SessionManager.getUserId(SetUpProfileActivity.this));
            jsonObject.put(WebField.SET_UP_PROFILE.PARAM_FIRSTNAME, firtsName);
            jsonObject.put(WebField.SET_UP_PROFILE.PARAM_LASTNAME, lastName);
            // jsonObject.put(WebField.SET_UP_PROFILE.PARAM_NICKNAME, nickName);
            jsonObject.put(WebField.SET_UP_PROFILE.PARAM_ACCESSTOKEN, SessionManager.getAccessToken(SetUpProfileActivity.this));
            jsonObject.put(WebField.SET_UP_PROFILE.PARAM_DOB, dateOfBirth);
            jsonObject.put(WebField.SET_UP_PROFILE.PARAM_GENDER, gender.equalsIgnoreCase(getResources().getString(R.string.sp_male)) ? 0 : 1);

            String[] alStrGoal = currentTrainingGoal.split(" ");
            sb = new StringBuffer();
            for (int i = 0; i < alStrGoal.length; i++) {
                if (i == alStrGoal.length - 1) {
                    sb.append(alStrGoal[i]);
                } else {
                    sb.append(alStrGoal[i] + ", ");
                }
            }
            jsonObject.put(WebField.SET_UP_PROFILE.PARAM_CURRENTTRAININGGOALS, sb.toString());
            jsonObject.put(WebField.SET_UP_PROFILE.PARAM_BODYTYPE, bodyType);
            jsonObject.put(WebField.SET_UP_PROFILE.PARAM_WEIGHT, weight);
            jsonObject.put(WebField.SET_UP_PROFILE.PARAM_HEIGHT, height);
            jsonObject.put(WebField.SET_UP_PROFILE.PARAM_BODYFAT, bodyFat);
            jsonObject.put(WebField.SET_UP_PROFILE.PARAM_BIO, bio);

            JSONObject jsonObjectSocialLinks = new JSONObject();

            jsonObjectSocialLinks.put(WebField.SET_UP_PROFILE.PARAM_FB, etFacebook.getText().toString());
            jsonObjectSocialLinks.put(WebField.SET_UP_PROFILE.PARAM_INSTAGRAM, etInstagram.getText().toString());
            jsonObjectSocialLinks.put(WebField.SET_UP_PROFILE.PARAM_SNAPCHAT, etSnapchat.getText().toString());
            jsonObjectSocialLinks.put(WebField.SET_UP_PROFILE.PARAM_TWITTER, etTwitter.getText().toString());
            jsonObjectSocialLinks.put(WebField.SET_UP_PROFILE.PARAM_YOUTUBE, etYouTube.getText().toString());
            jsonObjectSocialLinks.put(WebField.SET_UP_PROFILE.PARAM_LINKEDIN, etLinkedin.getText().toString());

            jsonObject.put(WebField.SET_UP_PROFILE.PARAM_SOCIALLINKS, jsonObjectSocialLinks);
            jsonObject.put(WebField.SET_UP_PROFILE.PARAM_PROFILEPIC, strProPicBase64);

            LogM.LogE("Request : Setup Profile : " + jsonObject.toString());

            new GetJsonWithAndroidNetworkingLib(context, jsonObject, WebField.BASE_URL + WebField.SET_UP_PROFILE.MODE, 1, new OnUpdateListener() {
                @Override
                public void onUpdateComplete(JSONObject jsonObject, boolean isSuccess) {
                    LogM.LogE("Response : Setup Profile : " + jsonObject.toString());
                    SetUpProfile user = new Gson().fromJson(String.valueOf(jsonObject), SetUpProfile.class);
                    if (isSuccess) {
                        SessionManager.saveSetUpProfile(context, user);
                       // SessionManager.saveFirstName(context,firtsName);
                        LogM.LogE("you have saved----"+SessionManager.getFirstName(context));

                        if (SessionManager.getSpotifyToken(context) != null && !SessionManager.getSpotifyToken(context).equalsIgnoreCase("")) {
                            Intent intent = new Intent(context, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        } else {
                            if (SessionManager.getSpotifySkip(context)) {
                                Intent intent = new Intent(context, MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            } else {
                                Intent intent = new Intent(context, ConnectSpotify.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            }

                        }
                    }
                }
            }).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showDatePickerDialog(DatePickerDialog.OnDateSetListener myDateListener) {
        try {
            calendar = Calendar.getInstance();
            year = calendar.get(Calendar.YEAR);
            maxyear = year - 13;
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dpd = new DatePickerDialog(context, myDateListener, year, month, day);
//            dpd.getDatePicker().setMaxDate(calendar.getTimeInMillis());
//            dpd.getDatePicker().setMinDate(year);
            dpd.getDatePicker().setMinDate(maxyear);
            dpd.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private DatePickerDialog.OnDateSetListener fromDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
            if (selectedYear > maxyear) {
//                Toast.makeText(context, "You are mistaken", Toast.LENGTH_SHORT).show();
            } else {
                year = selectedYear;
                month = selectedMonth;
                day = selectedDay;
            }

//            String age = getAge(year, month, day);

            tvDateOfBirth.setText(DateTime.getDateFromString(new StringBuilder().append(month + 1).append("/").append(day).append("/").append(year).toString()));
//            tvDateOfBirth.setText(age);
        }
    };

    private void openDatepicker(boolean isSelected) {
        int mYear, mMonth, mDay, maxYear;
        final Calendar cal = Calendar.getInstance();
        if (isSelected) {
            mYear = year;
            mMonth = month - 1;
            mDay = day;
            maxYear = year;
        } else {
            mYear = cal.get(Calendar.YEAR);
            mMonth = cal.get(Calendar.MONTH);
            mDay = cal.get(Calendar.DAY_OF_MONTH);
            maxYear = mYear - 13;
        }

        DatePickerDialog dpd = new DatePickerDialog(SetUpProfileActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        Calendar minAdultAge = new GregorianCalendar();
                        minAdultAge.add(Calendar.YEAR, -90);

                        int dayofmonth = monthOfYear + 1;
//                        tvDOB.setText(dayOfMonth + "/" + dayofmonth + "/" + year);
                        tvDateOfBirth.setText(DateTime.getDateFromString(dayOfMonth + "-" + dayofmonth + "-" + year));
//                        tvDateOfBirth.setText(DateTime.getDateFromString(dayofmonth + "-" + dayOfMonth + "-" + year));
//                        tvDateOfBirth.setText(DateTime.getDateFromString(new StringBuilder().append(month + 1).append("/").append(day).append("/").append(year).toString()));
                    }
                }, mYear, mMonth, mDay);
//        Calendar c = Calendar.getInstance();
//        c.set(maxYear, mMonth, mDay);
        Calendar caa = Calendar.getInstance();
        caa.add(Calendar.YEAR, -13);
        dpd.getDatePicker().setMaxDate(caa.getTimeInMillis());
        dpd.show();
    }

    private String getAge(int year, int month, int day) {
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(year, month, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }

        Integer ageInt = new Integer(age);
        String ageS = ageInt.toString();

        return ageS;
    }

    private void getGenderArray() {
        genderType = getResources().getStringArray(R.array.typeOfGender);
        type = genderType[1];
        genderArrayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, genderType) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    // Disable the first item from Spinner
                    // First item will be use for hint
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
                    // Set the hint text color gray
                    tv.setTextColor(getResources().getColor(R.color.hint_text_color));
                } else {
                    tv.setTextColor(getResources().getColor(R.color.hint_color));
                }
                return view;
            }
        };

        genderArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinerGender.setAdapter(genderArrayAdapter);
        spinerGender.setOnItemSelectedListener(genderListner);
    }

    private AdapterView.OnItemSelectedListener genderListner = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            try {
                if (position > 0) {
                    type = spinerGender.getItemAtPosition(position).toString();
                    tvGender.setText(type);
                    tvGender.setTextColor(getResources().getColor(R.color.hint_color));
                    LogM.LogE(" onItemSelected: genderType: " + type);
                    if (type.equalsIgnoreCase(genderType[1])) {
                        strTypeOfGender = 0;
                        ivEctomorph.setBackground(getResources().getDrawable(R.drawable.ectomorph));
                        ivMesomorph.setBackground(getResources().getDrawable(R.drawable.mesomorph_selected));
                        ivEndomorph.setBackground(getResources().getDrawable(R.drawable.endomorph));
                    } else if (type.equalsIgnoreCase(genderType[2])) {
                        strTypeOfGender = 1;
                        ivEctomorph.setBackground(getResources().getDrawable(R.drawable.female_ectomorph));
                        ivMesomorph.setBackground(getResources().getDrawable(R.drawable.female_mesomorph_selected));
                        ivEndomorph.setBackground(getResources().getDrawable(R.drawable.female_endomorph));
                    }
                } else {
                    tvGender.setTextColor(getResources().getColor(R.color.hint_text_color));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    };

    private void checkPermission(String title) {
        try {
            PermissionsHelper.permissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA}).checkPermissions(SetUpProfileActivity.this, new PermissionsHelper.OnPermissionResult() {

                @Override
                public void onGranted() {
                    showPicProfileDialog(title);
                }

                @Override
                public void notGranted() {
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showPicProfileDialog(String title) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(SetUpProfileActivity.this)
                    .setTitle(getResources().getString(R.string.app_name)).setMessage(title);

            builder.setNegativeButton("Gallery", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
//                    Intent intent = new Intent();
//                    intent.setType("image/*");
//                    intent.setAction(Intent.ACTION_GET_CONTENT);
//                    startActivityForResult(Intent.createChooser(intent,
//                            "Select Picture"), SELECT_GALLERY_PIC);
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                    mImageUri = Uri.fromFile(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "myFile.jpg"));
                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    if (android.os.Build.VERSION.SDK_INT > 10) {
                        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                    }
                    startActivityForResult(intent, SELECT_GALLERY_PIC);
                }
            });

            builder.setPositiveButton("Camera", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    openCamera();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openCamera() {
//        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//        startActivityForResult(cameraIntent, SELECT_CAMERA_PIC);

        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        mImageUri = Uri.fromFile(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "myFile.jpg"));
        Intent cameraIntent = new Intent();
        cameraIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);
        startActivityForResult(cameraIntent, SELECT_CAMERA_PIC);
    }

    private void compressVideo(final String input) {
        try {
            if (!ConnectivityDetector.isConnectingToInternet(SetUpProfileActivity.this)) {
                AlertDialogUtility.showInternetAlert(SetUpProfileActivity.this);
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
                    progressDialog = new ProgressDialog(SetUpProfileActivity.this);
                    progressDialog.setCancelable(false);
                    progressDialog.setMessage(getString(R.string.please_wait));
                    progressDialog.setTitle("");
                    progressDialog.show();
                }

                @Override
                public void onSuccess() {
                    file = new File(outPath);
                    spliteFile(file);
                    uploadIntroductoryVideo();
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

    private String base64Thumb = "";
    private int spliteCount;
    private ArrayList<String> fileNameArr;
    private FileHandeling fileHandeling;
    private int totalSplite = 1;

    private void spliteFile(File f) {
        try {
            Bitmap bitmap = GlobalMethods.getThumbFromVideo(f.getAbsolutePath());
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

    private Uri mImageUri;
    private Bitmap bitmap = null;
    private String strNineImageBase64 = "";
    private String result = "";
    public Uri selectedUri = null;
    private final String strCompressedVideo = "/Video/CompressedVideo";
    private ProgressDialog progressDialog;
    public File file;
    private int count = 0;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GlobalData.PREMIUM_MEMBER) {
            callGetUserDetails(1);
            return;
        }
        if (resultCode == SetUpProfileActivity.this.RESULT_OK) {
            if (requestCode == GlobalData.CAPTURE_VIDEO && data != null) {
                try {
                    result = data.getStringExtra("result");
                    selectedUri = Uri.parse(result);
                    compressVideo(result);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return;
            } else if (requestCode == SELECT_GALLERY_PIC) {
                mImageUri = data.getData();
                try {
                    bitmap = getBitmapFromUri(mImageUri);
                } catch (Exception e) {
                    e.printStackTrace();
                }

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
            } else if (requestCode == SELECT_CAMERA_PIC) {
                if (mImageUri != null) {
                    performCrop(mImageUri);
                }
            } else if (requestCode == PIC_CROP && resultCode == RESULT_OK) {
                try {
                    if (data != null) {
                        if (imageClick.equalsIgnoreCase(GlobalData.PHOTOS_USER_PROFILE)) {
                            Bitmap thePic = getBitmapFromFile(cropfilepath);
                            if (thePic != null) {
                                ImagePipeline imagePipeline = Fresco.getImagePipeline();
                                imagePipeline.evictFromCache(getImageUri(thePic));
                                imagePipeline.clearCaches();
                                ivUserProfile.setImageURI(mImageUri);
                                strProPicBase64 = converToBase64(thePic, ivUserProfile);
                            }
                        } else if (imageClick.equalsIgnoreCase(GlobalData.PHOTOS1)) {
                            Bitmap thePic = getBitmapFromFile(cropfilepath);
                            if (thePic != null) {
//                                ImagePipeline imagePipeline = Fresco.getImagePipeline();
////                                imagePipeline.evictFromCache(getImageUri(thePic));
////                                imagePipeline.clearCaches();
////                                ivPhotoOne.setImageURI(mImageUri);
                                Glide.with(this)
                                        .load(mImageUri)
                                        .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true))
                                        .into(ivPhotoOne);

                              //  Glide.with(this).load(mImageUri).into(ivPhotoOne);
                                strNineImageBase64 = converToBase64(thePic, ivPhotoOne);
                                callAddExtraPhoto(1);
                            }
                        } else if (imageClick.equalsIgnoreCase(GlobalData.PHOTOS2)) {
                            Bitmap thePic = getBitmapFromFile(cropfilepath);
                            if (thePic != null) {
//                                ImagePipeline imagePipeline = Fresco.getImagePipeline();
//                                imagePipeline.evictFromCache(getImageUri(thePic));
//                                imagePipeline.clearCaches();
//                                ivPhotoTwo.setImageURI(mImageUri);
                                Glide.with(this)
                                        .load(mImageUri)
                                       .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true))
                                        .into(ivPhotoTwo);
                               // Glide.with(this).load(mImageUri).into(ivPhotoTwo);
                                strNineImageBase64 = converToBase64(thePic, ivPhotoTwo);
                                callAddExtraPhoto(2);
                            }
                        } else if (imageClick.equalsIgnoreCase(GlobalData.PHOTOS3)) {
                            Bitmap thePic = getBitmapFromFile(cropfilepath);
                            if (thePic != null) {
//                                ImagePipeline imagePipeline = Fresco.getImagePipeline();
//                                imagePipeline.evictFromCache(getImageUri(thePic));
//                                imagePipeline.clearCaches();
//                                ivPhotoThree.setImageURI(mImageUri);
                                Glide.with(this)
                                        .load(mImageUri)
                                       .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true))
                                        .into(ivPhotoThree);
                              //  Glide.with(this).load(mImageUri).into(ivPhotoThree);
                                strNineImageBase64 = converToBase64(thePic, ivPhotoThree);
                                callAddExtraPhoto(3);
                            }
                        } else if (imageClick.equalsIgnoreCase(GlobalData.PHOTOS4)) {
                            Bitmap thePic = getBitmapFromFile(cropfilepath);
                            if (thePic != null) {
//                                ImagePipeline imagePipeline = Fresco.getImagePipeline();
//                                imagePipeline.evictFromCache(getImageUri(thePic));
//                                imagePipeline.clearCaches();
//                                ivPhotoFour.setImageURI(mImageUri);

                                Glide.with(this)
                                        .load(mImageUri)
                                       .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true))
                                        .into(ivPhotoFour);
                               // Glide.with(this).load(mImageUri).into(ivPhotoFour);
                                strNineImageBase64 = converToBase64(thePic, ivPhotoFour);
                                callAddExtraPhoto(4);
                            }
                        } else if (imageClick.equalsIgnoreCase(GlobalData.PHOTOS5)) {
                            Bitmap thePic = getBitmapFromFile(cropfilepath);
                            if (thePic != null) {
//                                ImagePipeline imagePipeline = Fresco.getImagePipeline();
//                                imagePipeline.evictFromCache(getImageUri(thePic));
//                                imagePipeline.clearCaches();
//                                ivPhotoFive.setImageURI(mImageUri);
                                Glide.with(this).load(mImageUri).
                                 apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true))
                                        .into(ivPhotoFive);
                                strNineImageBase64 = converToBase64(thePic, ivPhotoFive);
                                callAddExtraPhoto(5);
                            }
                        } else if (imageClick.equalsIgnoreCase(GlobalData.PHOTOS6)) {
                            Bitmap thePic = getBitmapFromFile(cropfilepath);
                            if (thePic != null) {
//                                ImagePipeline imagePipeline = Fresco.getImagePipeline();
//                                imagePipeline.evictFromCache(getImageUri(thePic));
//                                imagePipeline.clearCaches();
//                                ivPhotoSix.setImageURI(mImageUri);
                                Glide.with(this).load(mImageUri).
                                        apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)).
                                        into(ivPhotoSix);
                                strNineImageBase64 = converToBase64(thePic, ivPhotoSix);
                                callAddExtraPhoto(6);
                            }
                        } else if (imageClick.equalsIgnoreCase(GlobalData.PHOTOS7)) {
                            Bitmap thePic = getBitmapFromFile(cropfilepath);
                            if (thePic != null) {
//                                ImagePipeline imagePipeline = Fresco.getImagePipeline();
//                                imagePipeline.evictFromCache(getImageUri(thePic));
//                                imagePipeline.clearCaches();
//                                ivPhotoSeven.setImageURI(mImageUri);
                                Glide.with(this).load(mImageUri). apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)).
                                        into(ivPhotoSeven);
                                strNineImageBase64 = converToBase64(thePic, ivPhotoSeven);
                                callAddExtraPhoto(7);
                            }
                        } else if (imageClick.equalsIgnoreCase(GlobalData.PHOTOS8)) {
                            Bitmap thePic = getBitmapFromFile(cropfilepath);
                            if (thePic != null) {
//                                ImagePipeline imagePipeline = Fresco.getImagePipeline();
//                                imagePipeline.evictFromCache(getImageUri(thePic));
//                                imagePipeline.clearCaches();
//                                ivPhotoEight.setImageURI(mImageUri);
                                Glide.with(this).load(mImageUri).
                                        apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)).
                                        into(ivPhotoEight);
                                strNineImageBase64 = converToBase64(thePic, ivPhotoEight);
                                callAddExtraPhoto(8);
                            }
                        } else if (imageClick.equalsIgnoreCase(GlobalData.PHOTOS9)) {
                            Bitmap thePic = getBitmapFromFile(cropfilepath);
                            if (thePic != null) {
//                                ImagePipeline imagePipeline = Fresco.getImagePipeline();
//                                imagePipeline.evictFromCache(getImageUri(thePic));
//                                imagePipeline.clearCaches();
//                                ivPhotoNine.setImageURI(mImageUri);
                                Glide.with(this).load(mImageUri).
                                        apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)).into(ivPhotoNine);
                                strNineImageBase64 = converToBase64(thePic, ivPhotoNine);
                                callAddExtraPhoto(9);
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == FULL_IMAGE && resultCode == RESULT_OK && data != null) {


                String photoURL = data.getStringExtra(GlobalData.PHOTO_URL);
                int position = data.getIntExtra(GlobalData.PHOTO_POSITION, 0);



                if (data.getStringExtra(GlobalData.FROM) != null) {
                    if (data.getStringExtra(GlobalData.FROM).equalsIgnoreCase(GlobalData.MAKE_PROFILE_PIC)) {
                        try {
                            ImagePipeline imagePipeline = Fresco.getImagePipeline();
                            imagePipeline.evictFromCache(Uri.parse(photoURL));
                            imagePipeline.clearCaches();
                            ivUserProfile.setImageURI(Uri.parse(photoURL));
                            strProPicBase64 = com.konkr.Utils.Base64.converToBase64(getBitmapFromURL(photoURL), ivUserProfile);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else if (data.getStringExtra(GlobalData.FROM).equalsIgnoreCase(GlobalData.PHOTO_POSITION)) {


                        switch (position) {
                            case 1:
                                // ivPhotoOne.setImageDrawable(null);

                                Glide.with(this).load(Uri.parse(photoURL)).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)).into(ivPhotoOne);
//                                ivPhotoOne.setImageURI(Uri.parse(photoURL));
                                break;
                            case 2:
                                Glide.with(this).load(Uri.parse(photoURL)).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)).into(ivPhotoTwo);
                               // ivPhotoTwo.setImageURI(Uri.parse(photoURL));
                                break;
                            case 3:
                                Glide.with(this).load(Uri.parse(photoURL)).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)).into(ivPhotoThree);
                                //ivPhotoThree.setImageURI(Uri.parse(photoURL));
                                break;
                            case 4:
                                Glide.with(this).load(Uri.parse(photoURL)).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)).into(ivPhotoFour);
//                                ivPhotoFour.setImageURI(Uri.parse(photoURL));
                                break;
                            case 5:
                                Glide.with(this).load(Uri.parse(photoURL)).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)).into(ivPhotoFive);
//                                ivPhotoFive.setImageURI(Uri.parse(photoURL));
                                break;
                            case 6:
                                Glide.with(this).load(Uri.parse(photoURL)).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)).into(ivPhotoSix);
//                                ivPhotoSix.setImageURI(Uri.parse(photoURL));
                                break;
                            case 7:
                                Glide.with(this).load(Uri.parse(photoURL)).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)).into(ivPhotoSeven);
//                                ivPhotoSeven.setImageURI(Uri.parse(photoURL));
                                break;
                            case 8:
                                Glide.with(this).load(Uri.parse(photoURL)).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)).into(ivPhotoEight);
//                                ivPhotoEight.setImageURI(Uri.parse(photoURL));
                                break;
                            case 9:
                                Glide.with(this).load(Uri.parse(photoURL)).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)).into(ivPhotoNine);
//                                ivPhotoNine.setImageURI(Uri.parse(photoURL));
                                break;
                        }
                    }
                }
            }
        }
    }

    public static Bitmap getBitmapFromURL(String src) {
        Bitmap myBitmap = null;
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            myBitmap = BitmapFactory.decodeStream(input);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return myBitmap;
        }
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

    private void callAddExtraPhoto(int position) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(WebField.ADD_EXTRA_PHOTO.PARAM_USERID, SessionManager.getUserId(SetUpProfileActivity.this));
            jsonObject.put(WebField.ADD_EXTRA_PHOTO.PARAM_ACCESSTOKEN, SessionManager.getAccessToken(SetUpProfileActivity.this));
            jsonObject.put(WebField.ADD_EXTRA_PHOTO.PARAM_PHOTO_POSITION, position);
            jsonObject.put(WebField.ADD_EXTRA_PHOTO.PARAM_PROFILE_ID, 0);
            jsonObject.put(WebField.ADD_EXTRA_PHOTO.PARAM_MEDIA_ID, 0);
            jsonObject.put(WebField.ADD_EXTRA_PHOTO.PARAM_BASE64, strNineImageBase64);

            LogM.LogE("Request : AddExtraPhoto : " + jsonObject.toString());

            new GetJsonWithAndroidNetworkingLib(context, jsonObject, WebField.BASE_URL + WebField.ADD_EXTRA_PHOTO.MODE, 1, new OnUpdateListener() {
                @Override
                public void onUpdateComplete(JSONObject jsonObject, boolean isSuccess) {
                    if (isSuccess) {
                        LogM.LogE("Response : AddExtraPhoto : " + jsonObject.toString());

                        AddExtraPhoto addExtraPhoto = new Gson().fromJson(String.valueOf(jsonObject), AddExtraPhoto.class);
                        photoUrl = addExtraPhoto.getImageArray().get(0).getImage();
                        LogM.LogE("PHOTO URL " + photoUrl + "----" + imageClick);

// userDetails.getUserDetails().getImageArray().add(new UserDetails.UserDetailsBean.ImageArrayBean("","","","",""));
                        if (imageClick.equalsIgnoreCase(GlobalData.PHOTOS1)) {
                          //  ivPhotoOne.setImageURI(Uri.parse(photoUrl));
                            Glide.with(context).load(Uri.parse(photoUrl)).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)).into(ivPhotoOne);
                            photoUrlOne = photoUrl;
                        } else if (imageClick.equalsIgnoreCase(GlobalData.PHOTOS2)) {
//                            ivPhotoTwo.setImageURI(Uri.parse(photoUrl));
                            Glide.with(context).load(Uri.parse(photoUrl)).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)).into(ivPhotoTwo);
                            photoUrlTwo = photoUrl;
                        } else if (imageClick.equalsIgnoreCase(GlobalData.PHOTOS3)) {
//                            ivPhotoThree.setImageURI(Uri.parse(photoUrl));
                            Glide.with(context).load(Uri.parse(photoUrl)).
                                    apply(new RequestOptions()
                                            .diskCacheStrategy(DiskCacheStrategy.NONE).
                                                    skipMemoryCache(true)).into(ivPhotoThree);
                            photoUrlThree = photoUrl;
                        } else if (imageClick.equalsIgnoreCase(GlobalData.PHOTOS4)) {
//                            ivPhotoFour.setImageURI(Uri.parse(photoUrl));
                            Glide.with(context).load(Uri.parse(photoUrl)).
                                    apply(new RequestOptions()
                                            .diskCacheStrategy(DiskCacheStrategy.NONE).
                                                    skipMemoryCache(true)).into(ivPhotoFour);
                            photoUrlFour = photoUrl;
                        } else if (imageClick.equalsIgnoreCase(GlobalData.PHOTOS5)) {
//                            ivPhotoFive.setImageURI(Uri.parse(photoUrl));
                            Glide.with(context).load(Uri.parse(photoUrl)).
                                    apply(new RequestOptions()
                                            .diskCacheStrategy(DiskCacheStrategy.NONE).
                                                    skipMemoryCache(true)).into(ivPhotoFive);
                            photoUrlFive = photoUrl;
                        } else if (imageClick.equalsIgnoreCase(GlobalData.PHOTOS6)) {
//                            ivPhotoSix.setImageURI(Uri.parse(photoUrl));
                            Glide.with(context).load(Uri.parse(photoUrl)).
                                    apply(new RequestOptions()
                                            .diskCacheStrategy(DiskCacheStrategy.NONE).
                                                    skipMemoryCache(true)).into(ivPhotoSix);
                            photoUrlSix = photoUrl;
                        } else if (imageClick.equalsIgnoreCase(GlobalData.PHOTOS7)) {
//                            ivPhotoSeven.setImageURI(Uri.parse(photoUrl));
                            Glide.with(context).load(Uri.parse(photoUrl)).
                                    apply(new RequestOptions()
                                            .diskCacheStrategy(DiskCacheStrategy.NONE).
                                                    skipMemoryCache(true)).into(ivPhotoSeven);
                            photoUrlSeven = photoUrl;
                        } else if (imageClick.equalsIgnoreCase(GlobalData.PHOTOS8)) {
//                            ivPhotoEight.setImageURI(Uri.parse(photoUrl));
                            Glide.with(context).load(Uri.parse(photoUrl)).
                                    apply(new RequestOptions()
                                            .diskCacheStrategy(DiskCacheStrategy.NONE).
                                                    skipMemoryCache(true)).into(ivPhotoEight);
                            photoUrlEight = photoUrl;
                        } else if (imageClick.equalsIgnoreCase(GlobalData.PHOTOS9)) {
//                            ivPhotoNine.setImageURI(Uri.parse(photoUrl));
                            Glide.with(context).load(Uri.parse(photoUrl)).
                                    apply(new RequestOptions()
                                            .diskCacheStrategy(DiskCacheStrategy.NONE).
                                                    skipMemoryCache(true)).into(ivPhotoNine);
                            photoUrlNine = photoUrl;
                        }

                        photoPosition = addExtraPhoto.getPhotoPosition();
                        setBackGroundForImages(photoPosition);
                       //callGetUserDetails(0);
                        callGetUserDetailsSecondTime(0);
                    }
                }
            }).execute();
        } catch (Exception e) {
            e.printStackTrace();
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
            cropIntent.putExtra("outputX", 1024);
            cropIntent.putExtra("outputY", 1024);
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
        } catch (ActivityNotFoundException anfe) {
            // display an error message
            String errorMessage = "Whoops - your device doesn't support the crop action!";
            Toast toast = Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private String converToBase64(Bitmap bitmap, ImageView ivImage) {
        String base64 = "";
//        String strFilePathForFresco = "file://" + cropfilepath.getAbsolutePath();
        ivImage.setImageURI(getImageContentUri(context, cropfilepath));

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
        byte[] dataTest = bos.toByteArray();
        base64 = com.konkr.Utils.Base64.encodeBytes(dataTest);
        Log.d("strBase64 ", " strBase64 ========== " + base64);
        return base64;
    }

    public static Uri getImageContentUri(Context context, File imageFile) {
        String filePath = imageFile.getAbsolutePath();
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID},
                MediaStore.Images.Media.DATA + "=? ",
                new String[]{filePath}, null);
        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor
                    .getColumnIndex(MediaStore.MediaColumns._ID));
            cursor.close();
            return Uri.withAppendedPath(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "" + id);
        } else {
            if (imageFile.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);
                return context.getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }

    public Uri getImageUri(Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor =
                context.getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }

    public static String encodeTobase64(Bitmap image) {
        Bitmap immagex = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immagex.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);

        LogM.LogE(imageEncoded);
        return imageEncoded;
    }

    private void setBackGroundForImages(int photoPosition) {
        switch (photoPosition) {
            case 1:
                ivPhotoTwo.setImageDrawable(getResources().getDrawable(R.drawable.add_new_photo_setup));
               // ivPhotoTwo.getHierarchy().setPlaceholderImage(getResources().getDrawable(R.drawable.add_new_photo_setup));
                //ivPhotoTwo.getHierarchy().setFailureImage(getResources().getDrawable(R.drawable.add_new_photo_setup));
              //  ivPhotoTwo.getHierarchy().setRetryImage(getResources().getDrawable(R.drawable.add_new_photo_setup));
                photoOneClick = true;
                ivPhotoTwo.setOnClickListener(this);
                break;

            case 2:
                ivPhotoThree.setImageDrawable(getResources().getDrawable(R.drawable.add_new_photo_setup));
//                ivPhotoThree.getHierarchy().setPlaceholderImage(getResources().getDrawable(R.drawable.add_new_photo_setup));
//                ivPhotoThree.getHierarchy().setFailureImage(getResources().getDrawable(R.drawable.add_new_photo_setup));
//                ivPhotoThree.getHierarchy().setRetryImage(getResources().getDrawable(R.drawable.add_new_photo_setup));
                photoTwoClick = true;
                ivPhotoThree.setOnClickListener(this);
                break;

            case 3:
                ivPhotoFour.setImageDrawable(getResources().getDrawable(R.drawable.add_new_photo_setup));
//                ivPhotoFour.getHierarchy().setPlaceholderImage(getResources().getDrawable(R.drawable.add_new_photo_setup));
//                ivPhotoFour.getHierarchy().setFailureImage(getResources().getDrawable(R.drawable.add_new_photo_setup));
//                ivPhotoFour.getHierarchy().setRetryImage(getResources().getDrawable(R.drawable.add_new_photo_setup));
                photoThreeClick = true;
                ivPhotoFour.setOnClickListener(this);
                break;

            case 4:
                ivPhotoFive.setImageDrawable(getResources().getDrawable(R.drawable.add_new_photo_setup));
//                ivPhotoFive.getHierarchy().setPlaceholderImage(getResources().getDrawable(R.drawable.add_new_photo_setup));
//                ivPhotoFive.getHierarchy().setFailureImage(getResources().getDrawable(R.drawable.add_new_photo_setup));
//                ivPhotoFive.getHierarchy().setRetryImage(getResources().getDrawable(R.drawable.add_new_photo_setup));
                photoFourClick = true;
                ivPhotoFive.setOnClickListener(this);
                break;

            case 5:
                ivPhotoSix.setImageDrawable(getResources().getDrawable(R.drawable.add_new_photo_setup));
//                ivPhotoSix.getHierarchy().setPlaceholderImage(getResources().getDrawable(R.drawable.add_new_photo_setup));
//                ivPhotoSix.getHierarchy().setFailureImage(getResources().getDrawable(R.drawable.add_new_photo_setup));
//                ivPhotoSix.getHierarchy().setRetryImage(getResources().getDrawable(R.drawable.add_new_photo_setup));
                photoFiveClick = true;
                ivPhotoSix.setOnClickListener(this);
                break;

            case 6:
                ivPhotoSeven.setImageDrawable(getResources().getDrawable(R.drawable.add_new_photo_setup));
//                ivPhotoSeven.getHierarchy().setPlaceholderImage(getResources().getDrawable(R.drawable.add_new_photo_setup));
//                ivPhotoSeven.getHierarchy().setFailureImage(getResources().getDrawable(R.drawable.add_new_photo_setup));
//                ivPhotoSeven.getHierarchy().setRetryImage(getResources().getDrawable(R.drawable.add_new_photo_setup));
                photoSixClick = true;
                ivPhotoSeven.setOnClickListener(this);
                break;

            case 7:
                ivPhotoEight.setImageDrawable(getResources().getDrawable(R.drawable.add_new_photo_setup));
//                ivPhotoEight.getHierarchy().setPlaceholderImage(getResources().getDrawable(R.drawable.add_new_photo_setup));
//                ivPhotoEight.getHierarchy().setFailureImage(getResources().getDrawable(R.drawable.add_new_photo_setup));
//                ivPhotoEight.getHierarchy().setRetryImage(getResources().getDrawable(R.drawable.add_new_photo_setup));
                photoSevenClick = true;
                ivPhotoEight.setOnClickListener(this);
                break;

            case 8:
                ivPhotoNine.setImageDrawable(getResources().getDrawable(R.drawable.add_new_photo_setup));
//                ivPhotoNine.getHierarchy().setPlaceholderImage(getResources().getDrawable(R.drawable.add_new_photo_setup));
//                ivPhotoNine.getHierarchy().setFailureImage(getResources().getDrawable(R.drawable.add_new_photo_setup));
//                ivPhotoNine.getHierarchy().setRetryImage(getResources().getDrawable(R.drawable.add_new_photo_setup));
                photoEightClick = true;
                ivPhotoNine.setOnClickListener(this);
                break;

            case 9:
                photoNineClick = true;
                break;
        }
    }

    private ArrayList<SuggestedTrainingGoals.SuggestedTrainingGoalsBean> alTrainingGoals = new ArrayList<>();
    private String[] trainingGoals;
    private ArrayAdapter<String> exerciseAdapter;

    private void callGetSuggestedTrainingGoals(String searchBy) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(WebField.GET_SUGGESTED_TRAINING_GOALS.USER_ID, SessionManager.getUserId(SetUpProfileActivity.this));
            jsonObject.put(WebField.GET_SUGGESTED_TRAINING_GOALS.ACCESS_TOKEN, SessionManager.getAccessToken(SetUpProfileActivity.this));
            jsonObject.put(WebField.GET_SUGGESTED_TRAINING_GOALS.SEARCHED_TRAINING_GOALS, searchBy);
            LogM.LogE("Request : GetSuggestedTrainingGoals : " + jsonObject.toString());

            new GetJsonWithAndroidNetworkingLib(SetUpProfileActivity.this, jsonObject, WebField.BASE_URL + WebField.GET_SUGGESTED_TRAINING_GOALS.MODE, 0, new OnUpdateListener() {
                @Override
                public void onUpdateComplete(JSONObject jsonObject, boolean isSuccess) {
                    LogM.LogE("Response : GetSuggestedTrainingGoals : " + jsonObject.toString());
                    SuggestedTrainingGoals mSuggestedTrainingGoals = new Gson().fromJson(String.valueOf(jsonObject), SuggestedTrainingGoals.class);
                    if (isSuccess) {
                        alTrainingGoals.clear();
                        alTrainingGoals.addAll(mSuggestedTrainingGoals.getSuggestedTrainingGoals());
                        trainingGoals = new String[alTrainingGoals.size()];
                        int index = 0;
                        for (SuggestedTrainingGoals.SuggestedTrainingGoalsBean value : alTrainingGoals) {
                            trainingGoals[index] = value.getGoalName();
                            index++;
                        }

                        exerciseAdapter = new ArrayAdapter<String>(SetUpProfileActivity.this, android.R.layout.simple_dropdown_item_1line, trainingGoals);
                        exerciseAdapter.setNotifyOnChange(true);
                        etCurrentTrainingGoal.setAdapter(exerciseAdapter);
                        etCurrentTrainingGoal.showDropDown();
                    } else {
                        AlertDialogUtility.showSnakeBar(mSuggestedTrainingGoals.getMessage(), snackBarView, SetUpProfileActivity.this);
                    }
                }
            }).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String base64 = "";
    private MyClass myClass = new MyClass();
    private boolean isEdit = false;

    private String getBase64(int index) {
        String base64_ = null;
        try {
            File f = new File(myClass.makeDir(SetUpProfileActivity.this), fileNameArr.get(index));
            base64_ = fileHandeling.getBase64FromByteArray(fileHandeling.convertFileToByteArray(f));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return base64_;
    }

    private void uploadIntroductoryVideo() {
        try {
            if (!ConnectivityDetector.isConnectingToInternet(SetUpProfileActivity.this)) {
                AlertDialogUtility.showInternetAlert(SetUpProfileActivity.this);
                return;
            }
            if (progressDialog == null) {
                progressDialog = new ProgressDialog(SetUpProfileActivity.this);
                progressDialog.setCancelable(false);
                progressDialog.setMessage(getString(R.string.please_wait));
                progressDialog.setTitle("");
                progressDialog.show();
            }

            if (spliteCount < totalSplite) {
                base64 = getBase64(spliteCount);
            }

            JSONObject jsonObject = new JSONObject();
            jsonObject.put(WebField.UPLOAD_USER_VIDEO.USER_ID, SessionManager.getUserId(SetUpProfileActivity.this));
            jsonObject.put(WebField.UPLOAD_USER_VIDEO.ACCESS_TOKEN, SessionManager.getAccessToken(SetUpProfileActivity.this));
            if (spliteCount == totalSplite - 1) {
                jsonObject.put(WebField.UPLOAD_USER_VIDEO.IS_COMPLETED, 1);
            } else {
                jsonObject.put(WebField.UPLOAD_USER_VIDEO.IS_COMPLETED, 0);
            }
            jsonObject.put(WebField.UPLOAD_USER_VIDEO.IS_EDIT, isEdit ? 1 : 0);
            jsonObject.put(WebField.UPLOAD_USER_VIDEO.ITEM_THUMB, base64Thumb);
            jsonObject.put(WebField.UPLOAD_USER_VIDEO.WORKOUT_SOURCE_DATA, base64);

            LogM.LogE("Request : UploadUserVideo : " + jsonObject.toString());

            new GetJsonWithAndroidNetworkingLib(SetUpProfileActivity.this, jsonObject, WebField.BASE_URL + WebField.UPLOAD_USER_VIDEO.MODE, 0, new OnUpdateListener() {
                @Override
                public void onUpdateComplete(JSONObject jsonObject, boolean isSuccess) {
                    LogM.LogE("Response : UploadUserVideo : " + jsonObject.toString());
                    isEdit = false;
                    IntroductoryVideo mIntroductoryVideo = new Gson().fromJson(String.valueOf(jsonObject), IntroductoryVideo.class);
                    if (isSuccess) {
                        spliteCount++;
                        if (spliteCount < totalSplite) {
                            uploadIntroductoryVideo();
                        } else {
                            introductoryVideoURL = mIntroductoryVideo.getVideoURL();
                            if (introductoryVideoURL.length() > 0) {
                                tvAddIntroductoryVideo.setVisibility(View.GONE);
                                clVideoView.setVisibility(View.VISIBLE);
                                Uri mUri = Uri.parse(mIntroductoryVideo.getVideoThumbImage().toString());
                                Fresco.getImagePipeline().evictFromMemoryCache(mUri);
                                Fresco.getImagePipelineFactory().getMainBufferedDiskCache().remove(new SimpleCacheKey(mUri.toString()));
                                Fresco.getImagePipelineFactory().getSmallImageFileCache().remove(new SimpleCacheKey(mUri.toString()));
                                ivIntroductoryVideo.setImageURI(mUri);
                            } else {
                                tvAddIntroductoryVideo.setVisibility(View.VISIBLE);
                                clVideoView.setVisibility(View.GONE);
                            }
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
                        AlertDialogUtility.showSnakeBar(mIntroductoryVideo.getMessage(), snackBarView, SetUpProfileActivity.this);
                    }
                }
            }).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private void callGetUserDetailsSecondTime(final int isSubscribedCheck) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(WebField.GET_USER_DETAILS.USER_ID, SessionManager.getUserId(SetUpProfileActivity.this));
            jsonObject.put(WebField.GET_USER_DETAILS.ACCESS_TOKEN, SessionManager.getAccessToken(SetUpProfileActivity.this));
            jsonObject.put(WebField.GET_USER_DETAILS.OTHER_USER_ID, 0);

            LogM.LogE("Request : GetUserDetails callGetUserDetailsSecondTime : " + jsonObject.toString());
            new GetJsonWithAndroidNetworkingLib(context, jsonObject, WebField.BASE_URL + WebField.GET_USER_DETAILS.MODE, 0, new OnUpdateListener() {
                @Override
                public void onUpdateComplete(JSONObject jsonObject, boolean isSuccess) {
                    LogM.LogE("Response : GetUserDetails callGetUserDetailsSecondTime: " + jsonObject.toString());
                    if (isSuccess) {
                        userDetails = new Gson().fromJson(String.valueOf(jsonObject), UserDetails.class);
                        if (isSubscribedCheck == 1) {
                            isSubscribed = userDetails.getUserDetails().getIsSubscribed();
                            return;
                        }
                        SetImagesOnly(userDetails);
                    }
                }
            }).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void SetImagesOnly(UserDetails userDetails) {

        int position = 0;
        for (int i = 0; i < 9; i++) {
            if (i < userDetails.getUserDetails().getImageArray().size()) {
                if (i == 0) {

                    Glide.with(this)
                            .load(userDetails.getUserDetails().getImageArray().get(i).getImage())
                            .apply(new RequestOptions()
                                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                                    .skipMemoryCache(true))
                            .into(ivPhotoOne);

                    //  Glide.with(this).load(userDetails.getUserDetails().getImageArray().get(i).getImage()).into(ivPhotoOne);
                    //  ivPhotoOne.setImageURI(userDetails.getUserDetails().getImageArray().get(i).getImage());
                    position = 1;
                    photoOneClick = true;
                    photoUrlOne = userDetails.getUserDetails().getImageArray().get(i).getImage();
                }
                if (i == 1) {
                    Glide.with(this)
                            .load(userDetails.getUserDetails().getImageArray().get(i).getImage())
                            .apply(new RequestOptions()
                                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                                    .skipMemoryCache(true))
                            .into(ivPhotoTwo);
                    // Glide.with(this).load(userDetails.getUserDetails().getImageArray().get(i).getImage()).into(ivPhotoTwo);
                    //ivPhotoTwo.setImageURI(userDetails.getUserDetails().getImageArray().get(i).getImage());
                    position = 2;
                    photoTwoClick = true;
                    photoUrlTwo = userDetails.getUserDetails().getImageArray().get(i).getImage();
                    ivPhotoTwo.setOnClickListener(this);
                }
                if (i == 2) {
                    Glide.with(this)
                            .load(userDetails.getUserDetails().getImageArray().get(i).getImage())
                            .apply(new RequestOptions()
                                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                                    .skipMemoryCache(true))
                            .into(ivPhotoThree);
                    // Glide.with(this).load(userDetails.getUserDetails().getImageArray().get(i).getImage()).into(ivPhotoThree);

                    // ivPhotoThree.setImageURI(userDetails.getUserDetails().getImageArray().get(i).getImage());
                    position = 3;
                    photoThreeClick = true;
                    photoUrlThree = userDetails.getUserDetails().getImageArray().get(i).getImage();
                    ivPhotoThree.setOnClickListener(this);
                }
                if (i == 3) {
                    Glide.with(this).load(userDetails.getUserDetails().getImageArray().get(i).getImage())
                            .apply(new RequestOptions()
                                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                                    .skipMemoryCache(true)).into(ivPhotoFour);

//                        ivPhotoFour.setImageURI(userDetails.getUserDetails().getImageArray().get(i).getImage());
                    position = 4;
                    photoFourClick = true;
                    photoUrlFour = userDetails.getUserDetails().getImageArray().get(i).getImage();
                    ivPhotoFour.setOnClickListener(this);
                }
                if (i == 4) {
                    Glide.with(this).load(userDetails.getUserDetails().getImageArray().get(i).getImage())
                            .apply(new RequestOptions()
                                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                                    .skipMemoryCache(true)).into(ivPhotoFive);
//                        ivPhotoFive.setImageURI(userDetails.getUserDetails().getImageArray().get(i).getImage());
                    position = 5;
                    photoFiveClick = true;
                    photoUrlFive = userDetails.getUserDetails().getImageArray().get(i).getImage();
                    ivPhotoFive.setOnClickListener(this);
                }
                if (i == 5) {
                    Glide.with(this).load(userDetails.getUserDetails().getImageArray().get(i).getImage())
                            .apply(new RequestOptions()
                                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                                    .skipMemoryCache(true)).into(ivPhotoSix);
//                        ivPhotoSix.setImageURI(userDetails.getUserDetails().getImageArray().get(i).getImage());
                    position = 6;
                    photoSixClick = true;
                    photoUrlSix = userDetails.getUserDetails().getImageArray().get(i).getImage();
                    ivPhotoSix.setOnClickListener(this);
                }
                if (i == 6) {
                    Glide.with(this).load(userDetails.getUserDetails().getImageArray().get(i).getImage())
                            .apply(new RequestOptions()
                                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                                    .skipMemoryCache(true)).into(ivPhotoSeven);
//                        ivPhotoSeven.setImageURI(userDetails.getUserDetails().getImageArray().get(i).getImage());
                    position = 7;
                    photoSevenClick = true;
                    photoUrlSeven = userDetails.getUserDetails().getImageArray().get(i).getImage();
                    ivPhotoSeven.setOnClickListener(this);
                }
                if (i == 7) {
                    Glide.with(this).load(userDetails.getUserDetails().getImageArray().get(i).getImage())
                            .apply(new RequestOptions()
                                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                                    .skipMemoryCache(true)).into(ivPhotoEight);
//                        ivPhotoEight.setImageURI(userDetails.getUserDetails().getImageArray().get(i).getImage());
                    position = 8;
                    photoEightClick = true;
                    photoUrlEight = userDetails.getUserDetails().getImageArray().get(i).getImage();
                    ivPhotoEight.setOnClickListener(this);
                }
                if (i == 8) {
                    Glide.with(this).load(userDetails.getUserDetails().getImageArray().get(i).getImage())
                            .apply(new RequestOptions()
                                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                                    .skipMemoryCache(true)).into(ivPhotoNine);
                    //ivPhotoNine.setImageURI(userDetails.getUserDetails().getImageArray().get(i).getImage());
                    position = 9;
                    photoNineClick = true;
                    photoUrlNine = userDetails.getUserDetails().getImageArray().get(i).getImage();
                    ivPhotoNine.setOnClickListener(this);
                }
                if (position != 0) {
                    setBackGroundForImages(position);
                }
            }
        }

    }
}
