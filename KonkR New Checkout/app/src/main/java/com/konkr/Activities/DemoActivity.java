package com.konkr.Activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.konkr.R;
import com.konkr.SharedPreferences.SessionManager;
import com.konkr.Utils.LogM;
import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationRequest;
import com.spotify.sdk.android.authentication.AuthenticationResponse;
import com.spotify.sdk.android.player.Config;
import com.spotify.sdk.android.player.ConnectionStateCallback;
import com.spotify.sdk.android.player.Connectivity;
import com.spotify.sdk.android.player.Error;
import com.spotify.sdk.android.player.Metadata;
import com.spotify.sdk.android.player.PlaybackBitrate;
import com.spotify.sdk.android.player.PlaybackState;
import com.spotify.sdk.android.player.Player;
import com.spotify.sdk.android.player.PlayerEvent;
import com.spotify.sdk.android.player.Spotify;
import com.spotify.sdk.android.player.SpotifyPlayer;

public class DemoActivity extends AppCompatActivity implements
        SpotifyPlayer.NotificationCallback, ConnectionStateCallback {
//    private static final String CLIENT_ID = "3dee1604f35748c9bfa31b94b59a450e";
//
//    // TODO: Replace with your redirect URI
//    private static final String REDIRECT_URI = "simpleapp://callback";
//    private static final int REQUEST_CODE = 1147;
//    private Player mPlayer;

    @SuppressWarnings("SpellCheckingInspection")
    private static final String CLIENT_ID = "3dee1604f35748c9bfa31b94b59a450e";
    @SuppressWarnings("SpellCheckingInspection")
    private static final String REDIRECT_URI = "simpleapp://callback";


    @SuppressWarnings("SpellCheckingInspection")
    private static final String TEST_SONG_URI = "spotify:track:645nDKBFLVTHoRhThmBh0R";
    /**
     * Request code that will be passed together with authentication result to the onAuthenticationResult
     */
    private static final int REQUEST_CODE = 1337;

    /**
     * UI controls which may only be enabled after the player has been initialized,
     * (or effectively, after the user has logged in).
     */
    private static final int[] REQUIRES_INITIALIZED_STATE = {
            R.id.play_track_button,
            R.id.pause_button,
    };

    /**
     * UI controls which should only be enabled if the player is actively playing.
     */
    public static final String TAG = "SpotifySdkDemo";


    private SpotifyPlayer mPlayer;

    private PlaybackState mCurrentPlaybackState;
    private final Player.OperationCallback mOperationCallback = new Player.OperationCallback() {
        @Override
        public void onSuccess() {

        }

        @Override
        public void onError(Error error) {

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_spoty_music);
        updateView();
        openLoginWindow();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mPlayer != null) {
            mPlayer.addNotificationCallback(DemoActivity.this);
            mPlayer.addConnectionStateCallback(DemoActivity.this);
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
                    mPlayer.addNotificationCallback(DemoActivity.this);
                    mPlayer.addConnectionStateCallback(DemoActivity.this);
                    // Trigger UI refresh
                    updateView();
                }

                @Override
                public void onError(Throwable error) {

                }
            });
        } else {
            mPlayer.login(authResponse.getAccessToken());
        }
    }

    //  _   _ ___   _____                 _
    // | | | |_ _| | ____|_   _____ _ __ | |_ ___
    // | | | || |  |  _| \ \ / / _ \ '_ \| __/ __|
    // | |_| || |  | |___ \ V /  __/ | | | |_\__ \
    //  \___/|___| |_____| \_/ \___|_| |_|\__|___/
    //

    private void updateView() {
        boolean loggedIn = true;

        for (int id : REQUIRES_INITIALIZED_STATE) {
            findViewById(id).setEnabled(loggedIn);
        }


    }

    private boolean isLoggedIn() {
        return true;
    }


    public void onPlayButtonClicked(View view) {

        String uri;
        switch (view.getId()) {
            case R.id.play_track_button:
                uri = TEST_SONG_URI;
                break;

            default:
                throw new IllegalArgumentException("View ID does not have an associated URI to play");
        }

        //      logStatus("Starting playback for " + uri);
        mPlayer.playUri(mOperationCallback, uri, 0, 0);
    }

    public void onPauseButtonClicked(View view) {
        if (mCurrentPlaybackState != null && mCurrentPlaybackState.isPlaying) {
            mPlayer.pause(mOperationCallback);
        } else {
            mPlayer.resume(mOperationCallback);
        }
    }


    @Override
    public void onLoggedIn() {

        updateView();
    }

    @Override
    public void onLoggedOut() {

        updateView();
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
            mPlayer.removeNotificationCallback(DemoActivity.this);
            mPlayer.removeConnectionStateCallback(DemoActivity.this);
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
        //    mMetadata = mPlayer.getMetadata();
        updateView();
    }

    @Override
    public void onPlaybackError(Error error) {
    }
}