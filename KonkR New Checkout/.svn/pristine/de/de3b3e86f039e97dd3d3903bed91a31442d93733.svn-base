package com.konkr.Fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.konkr.Activities.CardListActivity;
import com.konkr.Activities.ChangePasswordActivity;
import com.konkr.Activities.DonationsActivity;
import com.konkr.Activities.NotificationsActivity;
import com.konkr.Activities.PartnersActivity;
import com.konkr.Activities.PremiumMemberShipActivity;
import com.konkr.Activities.ShareYourExperiencesActivity;
import com.konkr.Activities.SignInActivity;
import com.konkr.Activities.SocialSharingActivity;
import com.konkr.Activities.TermsConditionActivity;
import com.konkr.Activities.VerificationBadgeActivity;
import com.konkr.Models.SignIn;
import com.konkr.R;
import com.konkr.SharedPreferences.SessionManager;
import com.konkr.Utils.AlertDialogUtility;
import com.konkr.Utils.GlobalData;
import com.konkr.Utils.LogM;
import com.konkr.Utils.MyTextView;
import com.konkr.Webservices.GetJsonWithAndroidNetworkingLib;
import com.konkr.Webservices.OnUpdateListener;
import com.konkr.Webservices.WebField;
import com.konkr.databinding.FragmentSettingsBinding;
import com.google.gson.Gson;
import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationRequest;
import com.spotify.sdk.android.authentication.AuthenticationResponse;

import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SettingsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private FragmentSettingsBinding binding;
    private Activity context;
    private MyTextView tvSetupCard;
    private MyTextView tvVerificationBadge;
    private MyTextView tvPremiumMembership;
    private MyTextView tvPartners;
    private MyTextView tvNotifications;
    private MyTextView tvHowTheAppWorks;
    private MyTextView tvChangePassword;
    private MyTextView tvShareUsYourExperince;
    private MyTextView tvSocialSharing;
    private MyTextView tvDonations;
    private MyTextView tvTearmsAndCondition;
    private MyTextView tvLogout;
    private MyTextView tvSwitchAccount;

    private ImageView ivChangePassword;
    private View view7;
    private ImageView ivSwitch;
    private View view12;
    ;


    private static final String CLIENT_ID = "3dee1604f35748c9bfa31b94b59a450e";

    // TODO: Replace with your redirect URI
    private static final String REDIRECT_URI = "simpleapp://callback";
    private static final int REQUEST_CODE = 1337;

    public SettingsFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_settings, container, false);
        View view = binding.getRoot();
        context = getActivity();

        bindViews();
        setListner();
        setVisibilityOfChangePassword();
        setVisibilityOfSwitchSpotifyAccount();

        return view;
    }

    private void setVisibilityOfSwitchSpotifyAccount() {

//        if (SessionManager.getSpotifyToken(context) != null && !SessionManager.getSpotifyToken(context).equalsIgnoreCase("")) {
//            tvSwitchAccount.setVisibility(View.VISIBLE);
//            ivSwitch.setVisibility(View.VISIBLE);
//            view12.setVisibility(View.VISIBLE);
//        } else {
        tvSwitchAccount.setVisibility(View.GONE);
        ivSwitch.setVisibility(View.GONE);
        view12.setVisibility(View.GONE);
//        }
    }

    private void setVisibilityOfChangePassword() {
        if (SessionManager.getLoginType(context) == 1) {
            tvChangePassword.setVisibility(View.GONE);
            ivChangePassword.setVisibility(View.GONE);
            view7.setVisibility(View.GONE);
        } else {
            tvChangePassword.setVisibility(View.VISIBLE);
            ivChangePassword.setVisibility(View.VISIBLE);
            view7.setVisibility(View.VISIBLE);
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view) {
        try {
            Intent intent = null;
            switch (view.getId()) {
                case R.id.tvSetupCard:
                    intent = new Intent(context, CardListActivity.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra(GlobalData.IS_FROM, GlobalData.SETTING);
                    startActivity(intent);
                    LogM.LogE("setup cliecke");
                    //      startActivity(new Intent (getActivity (), SearchUserActivity.class));
                    break;
                case R.id.tvVerificationBadge:
                    intent = new Intent(context, VerificationBadgeActivity.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    break;
                case R.id.tvPremiumMembership:
                    intent = new Intent(context, PremiumMemberShipActivity.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    break;
                case R.id.tvPartners:
                    intent = new Intent(context, PartnersActivity.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    break;
                case R.id.tvNotifications:
                    intent = new Intent(context, NotificationsActivity.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    break;
                case R.id.tvHowTheAppWorks:
                    // for time being i am putting this redirect :For testing
//                    intent = new Intent(context, CommentsActivity.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//                    startActivity(intent);
                    Toast.makeText(context, "Working", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.tvChangePassword:
                    intent = new Intent(context, ChangePasswordActivity.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    break;
                case R.id.tvShareUsYourExperince:
                    intent = new Intent(context, ShareYourExperiencesActivity.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    break;
                case R.id.tvSocialSharing:
                    intent = new Intent(context, SocialSharingActivity.class);
                    // intent = new Intent(context, PostExpressionActivity.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    break;
                case R.id.tvDonations:
                    intent = new Intent(context, DonationsActivity.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    break;
                case R.id.tvTearmsAndCondition:
                    intent = new Intent(context, TermsConditionActivity.class);
                    // intent = new Intent(context, ConnectSpotify.class);
                    startActivity(intent);
                    break;
                case R.id.tvSwitchAccount:
                    //   intent = new Intent(context, TermsConditionActivity.class);
//                    intent = new Intent(context, ConnectSpotify.class);
//                    startActivity(intent);
//                    LogM.LogE("You have to Synch Account===>");
                    showConfirmationDialog();
                    break;
                case R.id.tvLogout:
//                    intent = new Intent(context, SignIn.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//                    startActivity(intent);
//                    SessionManager.clearCredential (getActivity ());
//                    getActivity ().finish();

                    new AlertDialog.Builder(getActivity())
                            .setMessage(getResources().getString(R.string.tv_logout))
                            .setCancelable(false)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    callLogOutApi();
                                }
                            })
                            .setNegativeButton("Cancel", null)
                            .show();


                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void bindViews() {
        tvSetupCard = binding.tvSetupCard;
        tvVerificationBadge = binding.tvVerificationBadge;
        tvPremiumMembership = binding.tvPremiumMembership;
        tvPartners = binding.tvPartners;
        tvNotifications = binding.tvNotifications;
        tvHowTheAppWorks = binding.tvHowTheAppWorks;
        tvChangePassword = binding.tvChangePassword;
        tvShareUsYourExperince = binding.tvShareUsYourExperince;
        tvSocialSharing = binding.tvSocialSharing;
        tvDonations = binding.tvDonations;
        tvTearmsAndCondition = binding.tvTearmsAndCondition;
        tvSwitchAccount = binding.tvSwitchAccount;
        tvLogout = binding.tvLogout;
        ivChangePassword = binding.ivChangePassword;
        view7 = binding.view7;
        view12 = binding.view12;
        ivSwitch = binding.ivSwitch;

    }

    private void setListner() {
        tvSetupCard.setOnClickListener(this);
        tvVerificationBadge.setOnClickListener(this);
        tvPremiumMembership.setOnClickListener(this);
        tvPartners.setOnClickListener(this);
        tvNotifications.setOnClickListener(this);
        tvHowTheAppWorks.setOnClickListener(this);
        tvChangePassword.setOnClickListener(this);
        tvShareUsYourExperince.setOnClickListener(this);
        tvSocialSharing.setOnClickListener(this);
        tvDonations.setOnClickListener(this);
        tvTearmsAndCondition.setOnClickListener(this);
        tvSwitchAccount.setOnClickListener(this);
        tvLogout.setOnClickListener(this);
    }

    private void callLogOutApi() {
        try {

            JSONObject jsonObject = new JSONObject();
            jsonObject.put(WebField.PARAM_USER_ID, SessionManager.getUserId(getActivity()));
            jsonObject.put(WebField.PARAM_ACCESSTOKEN, SessionManager.getAccessToken(getActivity()));


            LogM.LogE("Request : LogOut : " + jsonObject.toString());

            new GetJsonWithAndroidNetworkingLib(context, jsonObject, WebField.BASE_URL + WebField.LOG_OUT.MODE, 1, new OnUpdateListener() {
                @Override
                public void onUpdateComplete(JSONObject jsonObject, boolean isSuccess) {
                    LogM.LogE("Response : LogOut : " + jsonObject.toString());
                    SignIn user = new Gson().fromJson(String.valueOf(jsonObject), SignIn.class);
                    if (isSuccess) {
                        if (user.getStatus() == 1) {
                            SessionManager.clearCredential(getActivity());
                            Intent intent = new Intent(context, SignInActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            getActivity().finish();
                            return;
                        }
                    } else {
                        AlertDialogUtility.showSnakeBar(user.getMessage(), tvLogout, context);
                    }

                }
            }).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void showConfirmationDialog() {

        AlertDialogUtility.showConfirmAlert(getActivity(), getResources().getString(R.string.switch_account), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                LogM.LogE("Token Before clear==>" + SessionManager.getSpotifyToken(getActivity()));
                LogM.LogE(" spitifyId Before clear==>" + SessionManager.getSpotifyUserId(getActivity()));
                SessionManager.clearSpotifyToken(getActivity());
                SessionManager.clearSpotifyUserId(getActivity());

                LogM.LogE("Token After clear========>" + SessionManager.getSpotifyToken(getActivity()));
                LogM.LogE(" spitifyId  after clear==>" + SessionManager.getSpotifyUserId(getActivity()));
//                int numDays=10;
                // AuthenticationClient.clearCookies(getApplication());
//                LogM.LogE("Token After clear==>"+SessionManager.getSpotifyToken(getActivity()));
//                LogM.LogE( String.format("Starting cache prune, deleting files older than %d days", numDays));
//                int numDeletedFiles = GlobalMethods.clearCacheFolder(context.getCacheDir(), numDays);
//                LogM.LogE( String.format("Cache pruning completed, %d files deleted", numDeletedFiles));

                // new Intent(startActivity(getActivity(), SpotifyLogout.class));

//                Intent intent= new Intent(getActivity(),SpotifyLogout.class);
//                startActivity(intent);

//                AuthenticationRequest.Builder builder =
//                        new AuthenticationRequest.Builder(CLIENT_ID, AuthenticationResponse.Type.TOKEN, REDIRECT_URI);
//
//                builder.setScopes(new String[]{"streaming"});
//                builder.setShowDialog(true);
//                AuthenticationRequest request = builder.build();
//
//                AuthenticationClient.openLoginInBrowser(getActivity(), request);
/*
                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                CustomTabsIntent customTabsIntent = builder.build();
                customTabsIntent.launchUrl(context, Uri.parse("https://accounts.spotify.com"));*/

    /*            AuthenticationRequest.Builder builder = new AuthenticationRequest.Builder(CLIENT_ID, AuthenticationResponse.Type.TOKEN, REDIRECT_URI)
                        .setShowDialog(true);
                builder.setScopes(new String[]{"user-read-private", "playlist-read", "playlist-read-private", "streaming"});
                AuthenticationRequest request = builder.build();
                AuthenticationClient.openLoginInBrowser(getActivity(), request);*/
                AuthenticationRequest.Builder builder =
                        new AuthenticationRequest.Builder(CLIENT_ID, AuthenticationResponse.Type.TOKEN, REDIRECT_URI);

                builder.setScopes(new String[]{"streaming"});
                builder.setShowDialog(true);
                AuthenticationRequest request = builder.build();
                AuthenticationClient.openLoginInBrowser(getActivity(), request);
            }
        });

    }
}
