package com.konkr.Activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;
import com.konkr.Models.CardList;
import com.konkr.Models.MusicAndVideo;
import com.konkr.Models.MusicTracks;
import com.konkr.Models.SpotifyModel;
import com.konkr.Models.SpotifyUserDetails;
import com.konkr.R;
import com.konkr.SharedPreferences.SessionManager;
import com.konkr.Utils.AlertDialogUtility;
import com.konkr.Utils.ConnectivityDetector;
import com.konkr.Utils.GlobalData;
import com.konkr.Utils.Headerbar;
import com.konkr.Utils.LogM;
import com.konkr.Utils.MyTextView;
import com.konkr.Webservices.GetJsonWithAndroidNetworkingLib;
import com.konkr.Webservices.OnUpdateListener;
import com.konkr.Webservices.WebField;
import com.konkr.databinding.ActivityConnectSpotifyBinding;
import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationRequest;
import com.spotify.sdk.android.authentication.AuthenticationResponse;
import com.spotify.sdk.android.player.Config;
import com.spotify.sdk.android.player.ConnectionStateCallback;
import com.spotify.sdk.android.player.Error;
import com.spotify.sdk.android.player.Player;
import com.spotify.sdk.android.player.PlayerEvent;
import com.spotify.sdk.android.player.SpotifyPlayer;

import org.json.JSONObject;

import java.util.ArrayList;

public class ConnectSpotify extends AppCompatActivity implements View.OnClickListener, SpotifyPlayer.NotificationCallback, ConnectionStateCallback {
    ActivityConnectSpotifyBinding binding;
    ConnectSpotify context;
    Headerbar headerBar;
    MyTextView tvtagline;
    ImageView ivConnectBtn;
    ImageView ivSkiptBtn;
    String isFrom="";
    private MyTextView tvSkip;
    ArrayList<MusicAndVideo> musicList = new ArrayList<>();
    ArrayList<SpotifyModel.Item> playList = new ArrayList<>();
    private View snackBarView;

    // TODO: Replace with your client ID
    private static final String CLIENT_ID = "3dee1604f35748c9bfa31b94b59a450e";

    // TODO: Replace with your redirect URI
    private static final String REDIRECT_URI = "simpleapp://callback";
    private static final int REQUEST_CODE = 1337;
    private Player mPlayer;

    private String spotifyOtherUserId = "";
    private int otherUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_connect_spotify);
        context = ConnectSpotify.this;

        bindView();
        setData();
        setListener();
        getIntentData();
    }

    private void getIntentData() {
        if (getIntent().getExtras() != null) {
            isFrom=getIntent().getStringExtra(GlobalData.FROM);
            LogM.LogE("comingFrom:::=>"+isFrom);
            if (getIntent().getStringExtra(GlobalData.FROM) != null && getIntent().getStringExtra(GlobalData.FROM).equalsIgnoreCase(GlobalData.MI_TRAINING)) {
                headerBar.ibtnLeftOne.setVisibility(View.VISIBLE);
                headerBar.ibtnLeftOne.setImageResource(R.drawable.back);
                ivSkiptBtn.setVisibility(View.GONE);
                tvSkip.setVisibility(View.GONE);
                ivConnectBtn.performClick();
                //<-------------->Connect to spotify------------------------------------->
            }
            if (getIntent().getStringExtra(GlobalData.FROM) != null && getIntent().getStringExtra(GlobalData.FROM).equalsIgnoreCase(GlobalData.PROFILE_ACTIVITY)) {

                spotifyOtherUserId = getIntent().getStringExtra(GlobalData.SPOTIFY_ID);
                otherUserId = getIntent().getIntExtra(GlobalData.OTHER_USER_ID, 0);

                LogM.LogE("Songlist of spotifyId       ===>" + spotifyOtherUserId);
                LogM.LogE("Other User Id connect screen ===>" + otherUserId);

                headerBar.ibtnLeftOne.setVisibility(View.VISIBLE);
                headerBar.ibtnLeftOne.setImageResource(R.drawable.back);
                ivSkiptBtn.setVisibility(View.GONE);
                tvSkip.setVisibility(View.GONE);
                ivConnectBtn.performClick();
                //<-------------->Connect to spotify------------------------------------->
            }
            if (getIntent().getStringExtra(GlobalData.FROM) != null && getIntent().getStringExtra(GlobalData.FROM).equalsIgnoreCase(GlobalData.SETUP_TRAINING)) {
                headerBar.ibtnLeftOne.setVisibility(View.VISIBLE);
                headerBar.ibtnLeftOne.setImageResource(R.drawable.back);
                ivSkiptBtn.setVisibility(View.GONE);
                tvSkip.setVisibility(View.GONE);
                ivConnectBtn.performClick();
                //<-------------->Connect to spotify------------------------------------->
            }
        }
    }

    private void setData() {
        String first = "Sync your";
        String next = " " + "<font color='#11FB20'>Spotify</font>";
        String last = " " + "Music \n With";
        tvtagline.setText(Html.fromHtml(first + next + last));
        // tvtagline.setText(Html.fromHtml(replacedWith));
    }


    private void setListener() {
        headerBar.tvTitle.setVisibility(View.VISIBLE);
        //  headerBar.ibtnLeftOne.setVisibility(View.VISIBLE);
        headerBar.tvTitle.setText(R.string.playlist);
        headerBar.ibtnLeftOne.setOnClickListener(this);
        headerBar.ibtnRightTwo.setOnClickListener(this);
        ivConnectBtn.setOnClickListener(this);
        ivSkiptBtn.setOnClickListener(this);
        headerBar.ibtnLeftOne.setOnClickListener(this);
    }

    private void bindView() {
        headerBar = binding.headerBar;
        tvtagline = binding.tvtagLine;
        ivConnectBtn = binding.ivConnectBtn;
        ivSkiptBtn = binding.ivSkiptBtn;
        tvSkip = binding.tvSkip;

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivConnectBtn:

                //   The only thing that's different is we added the 5 lines below.
                AuthenticationRequest.Builder builder = new AuthenticationRequest.Builder(CLIENT_ID, AuthenticationResponse.Type.TOKEN, REDIRECT_URI);
                builder.setScopes(new String[]{"user-read-private", "playlist-read", "playlist-read-private", "streaming"});
                AuthenticationRequest request = builder.build();
                AuthenticationClient.openLoginActivity(this, REQUEST_CODE, request);

                LogM.LogE("Token clear==>" + SessionManager.getSpotifyToken(getApplicationContext()));

//                AuthenticationRequest.Builder builder =
//                        new AuthenticationRequest.Builder(CLIENT_ID, AuthenticationResponse.Type.TOKEN, REDIRECT_URI);
//
//                builder.setScopes(new String[]{"streaming"});
//                builder.setShowDialog(true);
//                AuthenticationRequest request = builder.build();
//
//                AuthenticationClient.openLoginInBrowser(this, request);

                break;
            case R.id.ivSkiptBtn:
                SessionManager.isSpotifySkip(context, true);
                Intent intent = new Intent(context, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                break;

            case R.id.ibtnLeftOne:
                finish();
                break;
        }
    }
//<------------------------------------------------------spotify--------------------------------------------->


//    protected void onNewIntent(Intent intent) {
//        super.onNewIntent(intent);
//
//        Uri uri = intent.getData();
//        if (uri != null) {
//            AuthenticationResponse response = AuthenticationResponse.fromUri(uri);
//
//            switch (response.getType()) {
//                // Response was successful and contains auth token
//                case TOKEN:
//                    // Handle successful response
//                    LogM.LogE("TOKEN==>" + response.getAccessToken());
//                    LogM.LogE("CODE==>" + response.getCode());
//                    break;
//
//                // Auth flow returned an error
//                case ERROR:
//                    // Handle error response
//                    LogM.LogE("Error");
//                    LogM.LogE("TOKEN==>" + response.getAccessToken());
//                    LogM.LogE("CODE==>" + response.getCode());
//                    break;
//
//                // Most likely auth flow was cancelled
//                default:
//                    // Handle other cases
//                    LogM.LogE("Defalut");
//                    LogM.LogE("TOKEN==>" + response.getAccessToken());
//                    LogM.LogE("CODE==>" + response.getCode());
//            }
//        }
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Check if result comes from the correct activity
        // The next 19 lines of the code are what you need to copy & paste! :)
        if (requestCode == REQUEST_CODE) {
            AuthenticationResponse response = AuthenticationClient.getResponse(resultCode, data);
            LogM.LogE("CODE==>" + response.getCode());
            if (response.getType() == AuthenticationResponse.Type.TOKEN) {
                Config playerConfig = new Config(this, response.getAccessToken(), CLIENT_ID);

                LogM.LogE("TOKEN==>" + response.getAccessToken());
                LogM.LogE("CODE==>" + response.getCode());
                SessionManager.setSpotifyToken(context, response.getAccessToken());
//                getSpotyCode();

                getSpotifyUserId();
                // callGetPlayList();
//                Spotify.getPlayer(playerConfig, this, new SpotifyPlayer.InitializationObserver() {
//                    @Override
//                    public void onInitialized(SpotifyPlayer spotifyPlayer) {
//                        mPlayer = spotifyPlayer;
//                        mPlayer.addConnectionStateCallback(ConnectSpotify.this);
//                        mPlayer.addNotificationCallback(ConnectSpotify.this);
//
//
//                        LogM.LogE("contextName==>"+ mPlayer.getMetadata().contextName);
//                        LogM.LogE("contextUri==>"+ mPlayer.getMetadata().contextUri);
//                        LogM.LogE("currentTrack==>"+ mPlayer.getMetadata().currentTrack);
//                        LogM.LogE("nextTrack==>"+ mPlayer.getMetadata().nextTrack);
//                        LogM.LogE("describeContents==>"+ mPlayer.getMetadata().describeContents());
//                    }
//
//                    @Override
//                    public void onError(Throwable throwable) {
//                        Log.e("MainActivity", "Could not initialize player: " + throwable.getMessage());
//                    }
//                });
            } else if (response.getType() == AuthenticationResponse.Type.TOKEN) {
                LogM.LogE("FINAL TOKEN==>" + response.getAccessToken());
            }
        }


    }

    private void getSpotifyUserId() {

        if (ConnectivityDetector.isConnectingToInternet(context)) {

            try {

                AndroidNetworking.get("https://api.spotify.com/v1/me")
                        .addHeaders("Authorization", "Bearer " + SessionManager.getSpotifyToken(context))
//                        .addQueryParameter("limit","30")
//                        .addQueryParameter("offset","100")
                        .setTag(this)
                        .setPriority(Priority.LOW)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                // do anything with response
                                LogM.LogE("print==>" + response.toString());
//                                SpotifyModel spotify = new Gson().fromJson(String.valueOf(response), SpotifyModel.class);
//                                LogM.LogE("Response : Get PlayListS " + spotify.getItems());
//                                LogM.LogE("Response : ALL PLAY LIST " + spotify.getItems().size());
//                                playList.addAll(spotify.getItems());
                                SpotifyUserDetails spotifyUserDetails = new Gson().fromJson(String.valueOf(response), SpotifyUserDetails.class);
                                LogM.LogE("spotifyUserId==>" + spotifyUserDetails.getId());
                                SessionManager.setSpotifyUserId(context, spotifyUserDetails.getId());

                                calladdSpotifyUser();
                                // callGetPlayList();

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
            AlertDialogUtility.showAlert(ConnectSpotify.this, GlobalData.STR_INETRNET_ALERT_MESSAGE);
        }
    }

    private void calladdSpotifyUser() {

        try {

            JSONObject jsonObject = new JSONObject();

            jsonObject.put(WebField.ADDSPOTIFYUSER.USER_ID, SessionManager.getUserId(context));
            LogM.LogE("add final userid " + SessionManager.getSpotifyUserId(context));

            jsonObject.put(WebField.ADDSPOTIFYUSER.SPOTIFY_USER_ID, SessionManager.getSpotifyUserId(context));
            jsonObject.put(WebField.PARAM_ACCESSTOKEN, SessionManager.getAccessToken(context));

            LogM.LogE("Request : ADD Spotify USERSDETAILS : " + jsonObject.toString());

            new GetJsonWithAndroidNetworkingLib(context, jsonObject, WebField.BASE_URL + WebField.ADDSPOTIFYUSER.MODE, 1, new OnUpdateListener() {
                @Override
                public void onUpdateComplete(JSONObject jsonObject, boolean isSuccess) {
                    LogM.LogE("Response : ADD Spotify USERSDETAILS : " + jsonObject.toString());
                    final CardList cardList = new Gson().fromJson(String.valueOf(jsonObject), CardList.class);
                    if (isSuccess) {

//                        CardList dd= new CardList();
//                        dd.setCardList (jsonObject.getString (GlobalData.USER_ID));
//
                        callGetPlayList();
                    }

                }
            }).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getSpotyCode() {
        AuthenticationRequest.Builder builder = new AuthenticationRequest.Builder(CLIENT_ID, AuthenticationResponse.Type.CODE, REDIRECT_URI);
        builder.setScopes(new String[]{"user-read-private", "playlist-read", "playlist-read-private", "streaming"});
        AuthenticationRequest request = builder.build();
        AuthenticationClient.openLoginActivity(this, REQUEST_CODE, request);
    }

    private void callGetPlayList() {

        if (ConnectivityDetector.isConnectingToInternet(context)) {

            try {

//                AndroidNetworking.get("https://api.spotify.com/v1/me/playlists")
//                        .addHeaders("Authorization", "Bearer " + SessionManager.getSpotifyToken(context))
                AndroidNetworking.get("https://api.spotify.com/v1/users/" + SessionManager.getSpotifyUserId(context) + "/playlists")
                        .addHeaders("Authorization", "Bearer " + SessionManager.getSpotifyToken(context))
//                        .addQueryParameter("limit","30")
//                        .addQueryParameter("offset","100")
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
                                playList.addAll(spotify.getItems());

                                LogM.LogE("After Adding all lists " + playList.size());

                                //  callGetSongList(spotify.getId(),PlaylistAccessToken);
//                                for (int i = 0; i < spotify.getItems().size(); i++) {
//                                    LogM.LogE("Response : NAME " + spotify.getItems().get(i).getName());
//                                    LogM.LogE("Response : PlayList ID " + spotify.getItems().get(i).getId());
//                                    if(i == spotify.getItems().size() - 1) {
//                                        callGetSongList(spotify.getItems().get(i).getId(), playlistAccessToken, true);
//                                    } else {
//                                        callGetSongList(spotify.getItems().get(i).getId(), playlistAccessToken, false);
//                                    }
//                                }

                                if (getIntent().getStringExtra(GlobalData.FROM) != null && isFrom.equalsIgnoreCase(GlobalData.PROFILE_ACTIVITY)) {
                                    Intent intent = new Intent(context, ProfileActivity.class);
                                    intent.putExtra(GlobalData.FROM, GlobalData.CONNECTSPOTIFY);
                                    intent.putExtra(GlobalData.OTHER_USER_ID,otherUserId);
                                    intent.putExtra("playList", playList);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
//                                    finish();
                                } else if (getIntent().getStringExtra(GlobalData.FROM) != null && getIntent().getStringExtra(GlobalData.FROM).equalsIgnoreCase(GlobalData.SETUP_TRAINING)) {
                                    Intent intent = new Intent(context, MiTrainingProfileActivity.class);
                                    intent.putExtra(GlobalData.FROM, GlobalData.CONNECTSPOTIFY);
                                    intent.putExtra("playList", playList);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                              //      setResult(RESULT_OK, intent);
                                    startActivity(intent);
                                    finish();
                                } else {     // coming from Training fragment
                                    Intent intent = new Intent(context, MainActivity.class);
                                    intent.putExtra(GlobalData.FROM, "ConnectSpotify");
                                    intent.putExtra("playList", playList);
                                    startActivity(intent);
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
            AlertDialogUtility.showAlert(ConnectSpotify.this, GlobalData.STR_INETRNET_ALERT_MESSAGE);
        }


    }

    private void callGetSongList(String playListId, String playlistAccessToken, boolean isLastPost) {

        if (ConnectivityDetector.isConnectingToInternet(context)) {
            try {
                AndroidNetworking.get("https://api.spotify.com/v1/playlists/" + playListId + "/tracks")
                        .addHeaders("Authorization", "Bearer " + playlistAccessToken)
                        .setTag(this)
                        .setPriority(Priority.LOW)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                MusicTracks musicTracks = new Gson().fromJson(String.valueOf(response), MusicTracks.class);
                                LogM.LogE("Response : Tacks in a playlist " + musicTracks.getItems());
                                LogM.LogE("Size of  " + musicTracks.getItems().size());


                                for (int i = 0; i < musicTracks.getItems().size(); i++) {
                                    // LogM.LogE(" : find " + musicTracks.getItems().get(i).getName().toString());
                                    LogM.LogE(" : Song Name " + musicTracks.getItems().get(i).getTrack().getName());
                                    LogM.LogE(" : Album Name " + musicTracks.getItems().get(i).getTrack().getAlbum().getName());
                                    LogM.LogE(" : Artist Name " + musicTracks.getItems().get(i).getTrack().getAlbum().getArtists().get(0).getName());

                                    String artistName = "";
                                    for (int j = 0; j < musicTracks.getItems().get(i).getTrack().getAlbum().getArtists().size(); j++) {
                                        if (artistName.equalsIgnoreCase("")) {
                                            artistName = musicTracks.getItems().get(i).getTrack().getAlbum().getArtists().get(j).getName();
                                        } else {
                                            artistName = artistName + "," + musicTracks.getItems().get(i).getTrack().getAlbum().getArtists().get(j).getName();
                                        }
                                    }
                                    LogM.LogE(" : Images Name " + musicTracks.getItems().get(i).getTrack().getAlbum().getImages().get(0).getUrl());
                                    musicList.add(new MusicAndVideo(
                                            musicTracks.getItems().get(i).getTrack().getName(),
                                            artistName,
                                            musicTracks.getItems().get(i).getTrack().getAlbum().getImages().get(0).getUrl(), musicTracks.getItems().get(i).getTrack().getUri(), musicTracks.getItems().get(i).getTrack().getDuration_ms()));
                                }
                                if (isLastPost) {
                                    LogM.LogE("Size of final list " + musicList.size());
                                    Intent intent = new Intent(context, MainActivity.class);
                                    intent.putExtra(GlobalData.FROM, "ConnectSpotify");
                                    intent.putExtra("SongList", musicList);
                                    startActivity(intent);
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
            AlertDialogUtility.showAlert(ConnectSpotify.this, GlobalData.STR_INETRNET_ALERT_MESSAGE);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onPlaybackEvent(PlayerEvent playerEvent) {
        LogM.LogE("Playback event received: " + playerEvent.name());
        switch (playerEvent) {
            // Handle event type as necessary
            default:
                break;
        }
    }

    @Override
    public void onPlaybackError(Error error) {
        LogM.LogE("Playback error received: " + error.name());
        switch (error) {
            // Handle error type as necessary
            default:
                break;
        }
    }

    @Override
    public void onLoggedIn() {
        LogM.LogE("User logged in");

    }

    @Override
    public void onLoggedOut() {
        LogM.LogE("User logged out");
    }

    @Override
    public void onLoginFailed(Error var1) {
        LogM.LogE("Login failed");
        Toast.makeText(context, "Logged in failed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTemporaryError() {
        LogM.LogE("Temporary error occurred");
    }

    @Override
    public void onConnectionMessage(String message) {
        LogM.LogE("Received connection message:===> " + message.toString());
    }
}
