package com.konkr.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;
import com.konkr.Activities.ConnectSpotify;
import com.konkr.Activities.MainActivity;
import com.konkr.Activities.MiTrainingProfileActivity;
import com.konkr.Activities.MusicListActivity;
import com.konkr.Activities.ProfileActivity;
import com.konkr.Adapters.PlayListAdapter;
import com.konkr.Interfaces.OnRecyclerViewItemClickListener;
import com.konkr.Models.Data;
import com.konkr.Models.MusicAndVideo;
import com.konkr.Models.MusicTracks;
import com.konkr.Models.SpotifyModel;
import com.konkr.R;
import com.konkr.SharedPreferences.SessionManager;
import com.konkr.Utils.AlertDialogUtility;
import com.konkr.Utils.App;
import com.konkr.Utils.ConnectivityDetector;
import com.konkr.Utils.GlobalData;
import com.konkr.Utils.LogM;
import com.konkr.Utils.MyProgressDialog;
import com.konkr.Utils.MyTextView;
import com.konkr.databinding.FragmentMyPlaylistBinding;
import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationRequest;
import com.spotify.sdk.android.authentication.AuthenticationResponse;
import com.spotify.sdk.android.player.ConnectionStateCallback;
import com.spotify.sdk.android.player.Error;
import com.spotify.sdk.android.player.PlayerEvent;
import com.spotify.sdk.android.player.SpotifyPlayer;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyPlayListFragment extends Fragment implements OnRecyclerViewItemClickListener, SpotifyPlayer.NotificationCallback, ConnectionStateCallback, View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "spotifyId";
    private static final String ARG_PARAM4 = "isFrom";
    private static final String ARG_PARAM5 = "otherUserId";
    private static final String ARG_PARAM6 = "name";
    private String isFrom = "";
    int otherUserId;
    String spotifyId;
    String userName;

    private ArrayList<SpotifyModel.Item> playList = new ArrayList<>();
    private int isFromProfile;
    private FragmentMyPlaylistBinding binding;
    private PlayListAdapter adapter;
    private RecyclerView rvPlayList;
    private MyTextView tvEmpty, tvLoginSpotify;
    private String otherUserSpotifyId;
    ArrayList<MusicAndVideo> musicList = new ArrayList<>();
    private ArrayList<Data.MyPlayList> alNames = new ArrayList<>();
    // TODO: Replace with your client ID
    private static final String CLIENT_ID = "3dee1604f35748c9bfa31b94b59a450e";

    // TODO: Replace with your redirect URI
    private static final String REDIRECT_URI = "simpleapp://callback";
    Context context;
    int userId;
//    private  Activity  cntx;

    public MyPlayListFragment() {
    }


    public static MyPlayListFragment newInstance(int isFromProfile, ArrayList<SpotifyModel.Item> playList, String otherUserSpotifyId, String isFrom, int otherUserId, String userName) {
        MyPlayListFragment fragment = new MyPlayListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, isFromProfile);
        args.putParcelableArrayList(ARG_PARAM2, playList);
        args.putString(ARG_PARAM3, otherUserSpotifyId);
        args.putString(ARG_PARAM4, isFrom);
        args.putInt(ARG_PARAM5, otherUserId);
        args.putString(ARG_PARAM6, userName);
        fragment.setArguments(args);


        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        LogM.LogE("On Attached is called=======>" + context);
    }

    private void setClickAbleSpan(int otherUserId) {
        LogM.LogE("otherUserId click");
        String text;
        LogM.LogE("SAVED SESSION NAME===>" + SessionManager.getFirstName(getActivity()));
        ;
        if (userName.equals(SessionManager.getFirstName(getActivity()))) {
            text = "To check your playlist, please login to Spotify";
        } else if (SessionManager.getFirstName(getActivity()).isEmpty() || SessionManager.getFirstName(getActivity()).equals(null)) {
            text = "To check your playlist, please login to Spotify";
        } else {
            text = "To check " + userName + "'s " + "playlist, please login to Spotify";
        }

        SpannableString spanString = new SpannableString(text);
        Matcher matcher = Pattern.compile("login to Spotify").matcher(spanString);

        while (matcher.find()) {
            spanString.setSpan(new ForegroundColorSpan(Color.parseColor("#00FF00")), matcher.start(), matcher.end(), 0); //to highlight word havgin '@'
            final String tag = matcher.group(0);
            ClickableSpan clickableSpan = new ClickableSpan() {
                @Override
                public void onClick(View textView) {
                    Log.e("click", "clicked Login " + tag);
                    Intent intent = new Intent(getActivity(), ConnectSpotify.class);
                    intent.putExtra(GlobalData.FROM, isFrom);
                    intent.putExtra(GlobalData.SPOTIFY_ID, spotifyId);
                    intent.putExtra(GlobalData.OTHER_USER_ID, otherUserId);
                    startActivity(intent);

                }

                @Override
                public void updateDrawState(TextPaint ds) {
                    ds.setColor(Color.parseColor("#00FF00"));    // you can use custom color
                    ds.setUnderlineText(false);    // this remove the underline

                }
            };
            spanString.setSpan(clickableSpan, matcher.start(), matcher.end(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        tvLoginSpotify.setText(spanString);
        tvLoginSpotify.setMovementMethod(LinkMovementMethod.getInstance());

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LogM.LogE("Oncreate playlist fragment====>+");
        if (getArguments() != null) {
            isFromProfile = getArguments().getInt(ARG_PARAM1, 0);
            playList = getArguments().getParcelableArrayList(ARG_PARAM2);
            try {
                otherUserSpotifyId = getArguments().getString((ARG_PARAM3));
            } catch (Exception e) {

            }

            isFrom = getArguments().getString((ARG_PARAM4));
            userId = getArguments().getInt(GlobalData.USER_Id);
            userName = getArguments().getString(ARG_PARAM6);
            otherUserId = getArguments().getInt(ARG_PARAM5);
            LogM.LogE("PLAY_LIST UserId====>" + userId);
            LogM.LogE("UserName====>" + userName);
            LogM.LogE("Other USerIDDD====>" + otherUserId);
            LogM.LogE("PLAY_LIST isFrom====>" + isFrom);
            LogM.LogE(" PLAY_LIST otherUserSpotifyId====>" + otherUserSpotifyId);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_playlist, container, false);
        View view = binding.getRoot();
        rvPlayList = binding.rvPlayList;
        tvEmpty = binding.tvEmpty;
        tvLoginSpotify = binding.tvLoginSpotify;


        // setListner();
//        context = getActivity();


        adapter = new PlayListAdapter(getActivity(), playList, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvPlayList.setLayoutManager(mLayoutManager);
        rvPlayList.setAdapter(adapter);
        if (isFromProfile == 1) {
            ProfileActivity.showHideTraining(0);
        }
        LogM.LogE("sssSpotifyId===>" + SessionManager.getSpotifyUserId(getActivity()));
        if (SessionManager.getSpotifyUserId(getActivity()).isEmpty()) {
            tvLoginSpotify.setVisibility(View.VISIBLE);
        } else {
            tvLoginSpotify.setVisibility(View.GONE);
        }

        setClickAbleSpan(otherUserId);
        return view;
    }

//    private void setListner() {
//        tvLoginSpotify.setOnClickListener(this);
//    }

    @Override
    public void onItemClickListener(View view, int position) {
        if (playList.get(position).getTracks().getTotal() > 0) {
            callGetSongList(position);
        }
    }

    private void callGetSongList(int position) {
        MyProgressDialog.showProgressDialog(getActivity());
        if (ConnectivityDetector.isConnectingToInternet(getActivity())) {
            try {
                AndroidNetworking.get("https://api.spotify.com/v1/playlists/" + playList.get(position).getId() + "/tracks")
                        .addHeaders("Authorization", "Bearer " + SessionManager.getSpotifyToken(getActivity()))
                        .setTag(this)
                        .setPriority(Priority.LOW)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                MyProgressDialog.hideProgressDialog();
                                MusicTracks musicTracks = new Gson().fromJson(String.valueOf(response), MusicTracks.class);

                                musicList.clear();

                                for (int i = 0; i < musicTracks.getItems().size(); i++) {


                                    String artistName = "";
                                    for (int j = 0; j < musicTracks.getItems().get(i).getTrack().getAlbum().getArtists().size(); j++) {
                                        if (artistName.equalsIgnoreCase("")) {
                                            artistName = musicTracks.getItems().get(i).getTrack().getAlbum().getArtists().get(j).getName();
                                        } else {
                                            artistName = artistName + ", " + musicTracks.getItems().get(i).getTrack().getAlbum().getArtists().get(j).getName();
                                        }
                                    }
                                    LogM.LogE("Song Play URI====>" + musicTracks.getItems().get(i).getTrack().getUri());
                                    musicList.add(new MusicAndVideo(
                                            musicTracks.getItems().get(i).getTrack().getName(),
                                            artistName,
                                            musicTracks.getItems().get(i).getTrack().getAlbum().getImages().get(0).getUrl(), musicTracks.getItems().get(i).getTrack().getUri(), musicTracks.getItems().get(i).getTrack().getDuration_ms()));
                                }


                                Intent intent = new Intent(getActivity(), MusicListActivity.class);
                                intent.putExtra(GlobalData.FROM, "MyPlayList");
                                intent.putExtra(GlobalData.PLAY_LIST_NAME, playList.get(position).getName());
                                intent.putExtra("SongList", musicList);
                                startActivity(intent);

                            }

                            @Override
                            public void onError(ANError error) {
                                // handle error
                            }
                        });


            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            AlertDialogUtility.showAlert(getActivity(), GlobalData.STR_INETRNET_ALERT_MESSAGE);
        }


    }


    public void updatePlayList(String spotifyId) {
        // SessionManager.setSpotifyToken(getActivity(), "BQDtKdcyoDFzUFtCoHq5cpWd1qwuMqrC37lnvbEQJTJqA_rAOjc6P47IPepNVZ0bvCmjiuY9YSmr7Y012sO6LuS6ZFYOx2yqp6RBqZMSCvwVE889WomOW6Ei7NhRxtwiEuMQd5XGu3qBB4g4qHdRQlMJ1s_wP2eRqSffXPkkLQ78k7grA6YaHwBYMpaVE8-nkHhveSMwIXae4A");
//        context= getActivity();

        LogM.LogE("spotifyId Before CallingAPI==>" + spotifyId);
        LogM.LogE("context CallingAPI==>" + getActivity());

        if (spotifyId.isEmpty()) {
            // callGetUserPlayList((SessionManager.getSpotifyUserId(getActivity())));
            tvEmpty.setVisibility(View.VISIBLE);
        } else if (spotifyId.equals("NA")) {
            tvEmpty.setVisibility(View.VISIBLE);
        }
        //callGetUserPlayList(SessionManager.getSpotifyUserId(getActivity()));}
        else if (spotifyId.equalsIgnoreCase(SessionManager.getSpotifyUserId(App.getInstance().getApplicationContext()))) {
            //callGetPlayList();
            callGetUserPlayList((SessionManager.getSpotifyUserId(App.getInstance().getApplicationContext())));
        } else {
            callGetUserPlayList(spotifyId);
        }


    }

    public void passOtheruserId(int otherUserId) {
        otherUserId = otherUserId;
    }

    private void callGetUserPlayList(String spotifyId) {

        try {
            if (ConnectivityDetector.isConnectingToInternet(App.getInstance().getApplicationContext())) {

                try {
                    LogM.LogE("request==>" + "https://api.spotify.com/v1/users/" + spotifyId + "/playlists");
                    AndroidNetworking.get("https://api.spotify.com/v1/users/" + spotifyId + "/playlists")
                            .addHeaders("Authorization", "Bearer " + SessionManager.getSpotifyToken(App.getInstance().getApplicationContext()))
                            //                        .addQueryParameter("limit","30")
                            //                        .addQueryParameter("offset","100")
                            .setTag(this)
                            .setPriority(Priority.LOW)
                            .build()
                            .getAsJSONObject(new JSONObjectRequestListener() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    // do anything with response
                                    tvLoginSpotify.setVisibility(View.GONE);
                                    SpotifyModel spotify = new Gson().fromJson(String.valueOf(response), SpotifyModel.class);
                                    LogM.LogE("Response : Get PlayListS " + spotify.getItems());
                                    LogM.LogE("Response : ALL PLAY LIST " + spotify.getItems().size());
                                    playList.addAll(spotify.getItems());
                                    LogM.LogE("After Adding all lists " + playList.size());
                                    playList.clear();
                                    playList.addAll(spotify.getItems());
                                    adapter.notifyDataSetChanged();


                                    if (playList.size() > 0) {
                                        tvEmpty.setVisibility(View.GONE);
                                    } else {
                                        tvEmpty.setVisibility(View.VISIBLE);
                                    }
                                    if (SessionManager.getSpotifyUserId(context).isEmpty()) {
                                        tvLoginSpotify.setVisibility(View.VISIBLE);
                                    } else {
                                        tvLoginSpotify.setVisibility(View.GONE);
                                    }

                                }

                                @Override
                                public void onError(ANError error) {
                                    // handle error

                                }
                            });


                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else {
                AlertDialogUtility.showAlert(App.getInstance().getApplicationContext(), GlobalData.STR_INETRNET_ALERT_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public void onResume() {
        super.onResume();
        LogM.LogE("OnResume Fragment is called");
    }

    private void callGetPlayList() {

        if (ConnectivityDetector.isConnectingToInternet(getActivity())) {

            try {////https://api.spotify.com/v1/users/{user_id}/playlists
                LogM.LogE("List spotifi Req==>" + "https://api.spotify.com/v1/me/playlists" + SessionManager.getSpotifyToken(getActivity()));
                AndroidNetworking.get("https://api.spotify.com/v1/me/playlists")
                        .addHeaders("Authorization", "Bearer " + SessionManager.getSpotifyToken(getActivity()))
                        .setTag(this)
                        .setPriority(Priority.LOW)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                // do anything with response
                                SpotifyModel spotify = new Gson().fromJson(String.valueOf(response), SpotifyModel.class);
                                LogM.LogE("Response : Get PlayListS " + spotify.getItems());
                                LogM.LogE("Response : ALL PLAY LIST " + spotify.getItems().size());
                                playList.clear();
                                playList.addAll(spotify.getItems());
                                adapter.notifyDataSetChanged();
                                LogM.LogE("After Adding all lists " + playList.size());

                            }

                            @Override
                            public void onError(ANError error) {
                                // handle error
                                LogM.LogE("error======>" + error.getErrorCode());
                                if (error.getErrorCode() == 401) {
                                    AuthenticationRequest.Builder builder = new AuthenticationRequest.Builder(CLIENT_ID, AuthenticationResponse.Type.TOKEN, REDIRECT_URI);
                                    builder.setScopes(new String[]{"user-read-private", "playlist-read", "playlist-read-private", "streaming"});
                                    AuthenticationRequest request = builder.build();
                                    AuthenticationClient.openLoginActivity(getActivity(), GlobalData.REQUEST_CODE, request);
                                }


                            }
                        });


            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            AlertDialogUtility.showAlert(getActivity(), GlobalData.STR_INETRNET_ALERT_MESSAGE);
        }
    }

    @Override
    public void onLoggedIn() {

    }

    @Override
    public void onLoggedOut() {

    }

    @Override
    public void onLoginFailed(Error error) {

    }

    @Override
    public void onTemporaryError() {

    }

    @Override
    public void onConnectionMessage(String s) {

    }

    @Override
    public void onPlaybackEvent(PlayerEvent playerEvent) {

    }

    @Override
    public void onPlaybackError(Error error) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvLoginSpotify:
                LogM.LogE("value of from" + isFrom);
//                Intent intent = new Intent(getActivity(), ConnectSpotify.class);
//

//                startActivity(intent);
                break;
        }
    }


    //    private ArrayList<Data> initData() {
//        LogM.LogE("Size===xxxxxxxxxx"+musicList.size());
//        for (int i = 0; i < musicList.size(); i++) {
//            alNames.add(new Data.MyPlayList(musicList.get(i).getSongName(),musicList.get(i).getArtisName(),musicList.get(i).getImage()));
//
//        }
//
//        Collections.sort(alNames, new Comparator<Data.MyPlayList>() {
//            @Override
//            public int compare(Data.MyPlayList o1, Data.MyPlayList o2) {
//                return o1.getSongName().compareTo(o2.getSongName());
//            }
//        });
//
//       for(int i=0;i<alNames.size();i++){
//           LogM.LogE("You have done sorthing===>"+alNames.get(i).getSongName());
//       }
//
//
//        ArrayList<Data> list = new ArrayList<>();
//        for (int i = 0; i < FastScroller.b.length; i++) {
//            boolean flag = false;
//            for (int j = 0; j < alNames.size(); j++) {
//                if (alNames.get(j).getSongName().toUpperCase().charAt(0) == FastScroller.b[i].charAt(0)) {
//                    flag = true;
//                    Log.v("log", "" + alNames.get(j) + " && " + FastScroller.b[i].charAt(0));
//                    Data.MyPlayList playlist = new Data.MyPlayList(alNames.get(j).getSongName(), alNames.get(j).getArtisName(),
//                            alNames.get(j).getImage());
//                    list.add(new Data(FastScroller.b[i], playlist, j == 0));
//                } else {
//                    if (flag == true) {
//                        break;
//                    }
//                }
//            }
//        }
//       return list;
//    }
}

// if (isFromProfile == 1 &&alNames.size() > GlobalData.PROFILE_MI_SIZE) {
//         for (int p = 0; p < GlobalData.PROFILE_MI_SIZE; p++) {
//        list.add(new Data(FastScroller.b[i], playlist, j == 0));
//        ((ProfileActivity)getActivity()).showHideTraining(1);
//        }
//        } else {
//        list.add(new Data(FastScroller.b[i], playlist, j == 0));
//        }
