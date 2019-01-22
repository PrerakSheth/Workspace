package com.konkr.Activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.konkr.Adapters.MyPlayListAdapter;
import com.konkr.Models.Data;
import com.konkr.Models.MusicAndVideo;
import com.konkr.Models.SpotifyModel;
import com.konkr.R;
import com.konkr.SharedPreferences.SessionManager;
import com.konkr.Utils.FastScroller;
import com.konkr.Utils.GlobalData;
import com.konkr.Utils.Headerbar;
import com.konkr.Utils.LogM;
import com.konkr.databinding.ActivityMusicListBinding;
import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationRequest;
import com.spotify.sdk.android.authentication.AuthenticationResponse;
import com.spotify.sdk.android.player.Config;
import com.spotify.sdk.android.player.ConnectionStateCallback;
import com.spotify.sdk.android.player.Error;
import com.spotify.sdk.android.player.Metadata;
import com.spotify.sdk.android.player.PlaybackState;
import com.spotify.sdk.android.player.Player;
import com.spotify.sdk.android.player.PlayerEvent;
import com.spotify.sdk.android.player.Spotify;
import com.spotify.sdk.android.player.SpotifyPlayer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MusicListActivity extends AppCompatActivity implements View.OnClickListener, SpotifyPlayer.NotificationCallback, ConnectionStateCallback {
    private ActivityMusicListBinding binding;
    private MusicListActivity context;
    private Headerbar headerBar;
    ArrayList<SpotifyModel.Item> playList = new ArrayList<>();
    private ArrayList<MusicAndVideo> musicList = new ArrayList<>();
    private ListView list;
    private FastScroller scroller;
    private TextView section;
    private ArrayList<Data> items;
    private MyPlayListAdapter adapter;
    private int prePosition;
    private ArrayList<Data.MyPlayList> alSong = new ArrayList<>();
    private String title;
    private String playUri;

    private static final String CLIENT_ID = "3dee1604f35748c9bfa31b94b59a450e";
    private static final String REDIRECT_URI = "simpleapp://callback";
    private static final String TEST_SONG_URI = "spotify:track:645nDKBFLVTHoRhThmBh0R";
    private static final int REQUEST_CODE = 42424;
    private SpotifyPlayer mPlayer;
    private PlaybackState mCurrentPlaybackState;
    private Metadata mMetadata;
    private final Player.OperationCallback mOperationCallback = new Player.OperationCallback() {
        @Override
        public void onSuccess() {
            LogM.LogV("Position::: " + mPlayer.getPlaybackState().positionMs);
        }

        @Override
        public void onError(Error error) {
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_music_list);
        context = MusicListActivity.this;
        getIntentData();
        bindViews();
        setHeaderBar();
        setListener();
        items = initData();
        adapter = new MyPlayListAdapter(context, items);
        list.setAdapter(adapter);
        openLoginWindow();

    }

    private ArrayList<Data> initData() {
        LogM.LogE("Size=" + musicList.size());
        for (int i = 0; i < musicList.size(); i++) {
            alSong.add(new Data.MyPlayList(musicList.get(i).getSongName(), musicList.get(i).getArtisName(), musicList.get(i).getImage(), musicList.get(i).getUri(), musicList.get(i).getDuration_ms()));
            LogM.LogE("All song Uri ==>" + musicList.get(i).getUri());
        }


        Collections.sort(alSong, new Comparator<Data.MyPlayList>() {
            @Override
            public int compare(Data.MyPlayList o1, Data.MyPlayList o2) {
                return o1.getSongName().compareTo(o2.getSongName());
            }
        });

        for (int i = 0; i < alSong.size(); i++) {
            LogM.LogE("You have done sorthing===>" + alSong.get(i).getUri());
        }


        ArrayList<Data> list = new ArrayList<>();
        for (int i = 0; i < FastScroller.b.length; i++) {
            boolean flag = false;
            for (int j = 0; j < alSong.size(); j++) {
                if (alSong.get(j).getSongName().toUpperCase().charAt(0) == FastScroller.b[i].charAt(0)) {
                    flag = true;
                    Log.v("log", "" + alSong.get(j) + " && " + FastScroller.b[i].charAt(0));
                    Data.MyPlayList playlist = new Data.MyPlayList(alSong.get(j).getSongName(), alSong.get(j).getArtisName(),
                            alSong.get(j).getImage(), alSong.get(j).getUri(), alSong.get(j).getDuration_ms());
                    list.add(new Data(FastScroller.b[i], playlist, j == 0));
                } else {
                    if (flag == true) {
                        break;
                    }
                }
            }
        }
        return list;
    }

    private void getIntentData() {
        if (getIntent().getExtras() != null) {
            if (getIntent().getStringExtra(GlobalData.FROM) != null && getIntent().getStringExtra(GlobalData.FROM).equalsIgnoreCase("MyPlayList")) {
                if (getIntent().getParcelableArrayListExtra("SongList") != null) {
                    musicList = getIntent().getParcelableArrayListExtra("SongList");
                    title = getIntent().getStringExtra(GlobalData.PLAY_LIST_NAME);


                }
            }
        }
    }

    private void setListener() {
        headerBar.ibtnLeftOne.setOnClickListener(this);

        scroller.setOnTouchingLetterChangedListener(s ->
                new Handler(Looper.getMainLooper()).post(() -> {
                    if (s.equals(FastScroller.HEART)) {
                        prePosition = 0;
                        list.setSelectionFromTop(prePosition, 0);
                    } else {
                        int p = adapter.getPositionForSection(s);
                        prePosition = p == 0 ? prePosition : p;
                        list.setSelectionFromTop(prePosition, 0);
                    }
                }));
    }

    private void setHeaderBar() {

        headerBar.ibtnLeftOne.setVisibility(View.VISIBLE);
        headerBar.ibtnLeftOne.setImageResource(R.drawable.back);
        headerBar.tvTitle.setVisibility(View.VISIBLE);

        int n = title.length();
        if (n > 15) {
            headerBar.tvTitle.setText(title.substring(0, 15) + "...");
        } else {
            headerBar.tvTitle.setText(title);
        }

        //   headerBar.tvTitle.setText(title);

    }

    private void bindViews() {
        headerBar = binding.headerBar;
        list = binding.list;
        section = binding.sectionTitle;
        scroller = binding.scroller;
        scroller.setTextView(section);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ibtnLeftOne:
                alSong.clear();
                musicList.clear();
                onBackPressed();
                break;
        }
    }

    public void paySong(String uri, int pos) {
        playUri = uri;
        LogM.LogE("uri==>" + playUri);
        mPlayer.playUri(mOperationCallback, playUri, 0, 0);
//        int s = (int) mPlayer.getPlaybackState().positionMs;
//        Log.d("ssssss", "" + s);
//        for (int i = 0; i < 10000; i++) {
//            LogM.LogE("timer==>" + mPlayer.getPlaybackState().positionMs);
//
//        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mPlayer != null) {
            mPlayer.addNotificationCallback(MusicListActivity.this);
            mPlayer.addConnectionStateCallback(MusicListActivity.this);
        }
    }

    public void pausTheSong() {

        if (mCurrentPlaybackState != null && mCurrentPlaybackState.isPlaying) {
            mPlayer.pause(mOperationCallback);
            LogM.LogE("position on pause==>" + mPlayer.getPlaybackState().positionMs);
        } else {
            mPlayer.resume(mOperationCallback);
        }
    }

    private void openLoginWindow() {
        final AuthenticationRequest request = new AuthenticationRequest.Builder(CLIENT_ID, AuthenticationResponse.Type.TOKEN, REDIRECT_URI)
                .setScopes(new String[]{"user-read-private", "playlist-read", "playlist-read-private", "streaming"})
                .build();

        AuthenticationClient.openLoginActivity(this, REQUEST_CODE, request);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == REQUEST_CODE) {
            AuthenticationResponse response = AuthenticationClient.getResponse(resultCode, intent);

            LogM.LogE("Status==>: " + response.getCode());
            LogM.LogE("Token==>: " + response.getAccessToken());
            switch (response.getType()) {
                // Response was successful and contains auth token
                case TOKEN:
                    //  openLoginWindow();
                    onAuthenticationComplete(response);
                    break;

                case ERROR:
                    break;

                case CODE:
                    LogM.LogE("CODEEEE==>: " + response.getCode());
                    break;

                default:

            }
        }
    }

    private void onAuthenticationComplete(AuthenticationResponse authResponse) {

        if (mPlayer == null) {
            SessionManager.setSpotifyToken(getApplicationContext(), authResponse.getAccessToken());
            Config playerConfig = new Config(getApplicationContext(), authResponse.getAccessToken(), CLIENT_ID);
            mPlayer = Spotify.getPlayer(playerConfig, this, new SpotifyPlayer.InitializationObserver() {
                @Override
                public void onInitialized(SpotifyPlayer player) {
                    mPlayer.addNotificationCallback(context);
                    mPlayer.addConnectionStateCallback(context);
                    // Trigger UI refresh
                }

                @Override
                public void onError(Throwable error) {

                }
            });
        } else {
            mPlayer.login(authResponse.getAccessToken());
        }
    }

    @Override
    public void onLoggedIn() {


    }

    @Override
    public void onLoggedOut() {


    }

    public void onLoginFailed(Error error) {

    }

    @Override
    public void onTemporaryError() {

    }

    @Override
    public void onConnectionMessage(final String message) {

    }

    @Override
    protected void onPause() {
        super.onPause();

        if (mPlayer != null) {
            mPlayer.removeNotificationCallback(context);
            mPlayer.removeConnectionStateCallback(context);
        }
    }

    @Override
    protected void onDestroy() {
        Spotify.destroyPlayer(this);
        super.onDestroy();
    }

    @Override
    public void onPlaybackEvent(PlayerEvent event) {
        mCurrentPlaybackState = mPlayer.getPlaybackState();
        LogM.LogV("onPlaybackEvent Position::: " + mCurrentPlaybackState.positionMs);
    }

    @Override
    public void onPlaybackError(Error error) {
    }

}
