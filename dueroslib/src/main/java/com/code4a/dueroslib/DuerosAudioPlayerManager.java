package com.code4a.dueroslib;

import android.app.Application;

import com.code4a.dueroslib.oauth.api.OauthClientCredentialsInfo;
import com.code4a.dueroslib.oauth.api.OauthRequest;
import com.code4a.dueroslib.oauth.exception.OAuthFailureException;
import com.code4a.dueroslib.systeminterface.IMediaPlayer;
import com.code4a.dueroslib.util.LogUtil;

/**
 * Created by jiang on 2018/2/1.
 */

public final class DuerosAudioPlayerManager extends IDuerosPlatform {

    static final String TAG = DuerosAudioPlayerManager.class.getSimpleName();

    AudioPlayerControlListener audioPlayerControlListener;

    protected DuerosAudioPlayerManager(Application application, String clientId, String clientSecert) {
        super(application, clientId, clientSecert);
    }

    @Override
    public void createCustomDeviceModules() {
        duerosConfig.getDeviceModuleFactory().createAudioPlayerDeviceModule();
        duerosConfig.getDeviceModuleFactory().getAudioPlayerDeviceModule().addAudioPlayListener(
                new IMediaPlayer.SimpleMediaPlayerListener() {
                    @Override
                    public void onPaused() {
                        super.onPaused();
                        //                        pauseOrPlayButton.setText(getResources().getString(R.string.audio_paused));
                        //                        isPause = true;
                    }

                    @Override
                    public void onPlaying() {
                        super.onPlaying();
                        //                        pauseOrPlayButton.setText(getResources().getString(R.string.audio_playing));
                        //                        isPause = false;
                    }

                    @Override
                    public void onCompletion() {
                        super.onCompletion();
                        //                        pauseOrPlayButton.setText(getResources().getString(R.string.audio_default));
                        //                        isPause = false;
                    }

                    @Override
                    public void onStopped() {
                        super.onStopped();
                        //                        pauseOrPlayButton.setText(getResources().getString(R.string.audio_default));
                        //                        isPause = true;
                    }
                });
        duerosConfig.getDeviceModuleFactory().createPlaybackControllerDeviceModule();
    }

    @Override
    public void oauthDuerosPlatform() throws OAuthFailureException {
        duerosConfig.clientCredentialsOauth(new OauthRequest.OauthCallback<OauthClientCredentialsInfo>() {
            @Override
            public void onSuccess(OauthClientCredentialsInfo info) {
                LogUtil.w(TAG, "client credentials success");
            }

            @Override
            public void onFailure(String string) {
                LogUtil.w(TAG, "client credentials failed! " + string);
                throw new OAuthFailureException(string);
            }

            @Override
            public void onStart() {
                LogUtil.w(TAG, "client credentials start");
            }

            @Override
            public void onEnd() {
                LogUtil.w(TAG, "client credentials end");
            }
        });
    }

    @Override
    public void addDuerosCustomTaskCallback(DuerosCustomTaskCallback gatewayControlListener) {
        this.audioPlayerControlListener = (AudioPlayerControlListener) gatewayControlListener;
    }

    public interface AudioPlayerControlListener extends DuerosCustomTaskCallback {

        void controlDevice();
    }
}
