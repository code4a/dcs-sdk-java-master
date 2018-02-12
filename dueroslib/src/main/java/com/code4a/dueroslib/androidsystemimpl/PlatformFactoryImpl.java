/*
 * Copyright (c) 2017 Baidu, Inc. All Rights Reserved.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.code4a.dueroslib.androidsystemimpl;

import android.content.Context;
import android.os.Looper;

import com.code4a.dueroslib.androidsystemimpl.alert.AlertsFileDataStoreImpl;
import com.code4a.dueroslib.androidsystemimpl.audioinput.AudioVoiceInputImpl;
import com.code4a.dueroslib.androidsystemimpl.playbackcontroller.IPlaybackControllerImpl;
import com.code4a.dueroslib.androidsystemimpl.player.AudioTrackPlayerImpl;
import com.code4a.dueroslib.androidsystemimpl.player.MediaPlayerImpl;
import com.code4a.dueroslib.androidsystemimpl.wakeup.WakeUpImpl;
import com.code4a.dueroslib.systeminterface.IAlertsDataStore;
import com.code4a.dueroslib.systeminterface.IAudioInput;
import com.code4a.dueroslib.systeminterface.IAudioRecord;
import com.code4a.dueroslib.systeminterface.IHandler;
import com.code4a.dueroslib.systeminterface.IMediaPlayer;
import com.code4a.dueroslib.systeminterface.IPlatformFactory;
import com.code4a.dueroslib.systeminterface.IPlaybackController;
import com.code4a.dueroslib.systeminterface.IWakeUp;
import com.code4a.dueroslib.systeminterface.IWebView;

import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by wuruisheng on 2017/6/7.
 */
public class PlatformFactoryImpl implements IPlatformFactory {
    private IHandler mainHandler;
    private IAudioInput voiceInput;
    private IWebView webView;
    private IPlaybackController playback;
    private Context context;
    private IAudioRecord audioRecord;
    private LinkedBlockingDeque<byte[]> linkedBlockingDeque = new LinkedBlockingDeque<>();

    public PlatformFactoryImpl(Context context) {
        this.context = context.getApplicationContext();
    }

    @Override
    public IHandler createHandler() {
        return new HandlerImpl();
    }

    @Override
    public IHandler getMainHandler() {
        if (mainHandler == null) {
            mainHandler = new HandlerImpl(Looper.getMainLooper());
        }

        return mainHandler;
    }

    @Override
    public IAudioRecord getAudioRecord() {
        if (audioRecord == null) {
            audioRecord = new AudioRecordThread(linkedBlockingDeque);
        }
        return audioRecord;
    }

    @Override
    public void resetAudioRecord() {
        if (audioRecord != null) {
            audioRecord = null;
        }
    }

    @Override
    public IWakeUp getWakeUp() {
        return new WakeUpImpl(context, linkedBlockingDeque);
    }

    @Override
    public IAudioInput getVoiceInput() {
        if (voiceInput == null) {
            voiceInput = new AudioVoiceInputImpl(linkedBlockingDeque);
        }

        return voiceInput;
    }

    @Override
    public IMediaPlayer createMediaPlayer() {
        return new MediaPlayerImpl();
    }

    @Override
    public IMediaPlayer createAudioTrackPlayer() {
        return new AudioTrackPlayerImpl();
    }

    public IAlertsDataStore createAlertsDataStore() {
        return new AlertsFileDataStoreImpl();
    }

    @Override
    public IWebView getWebView() {
        return webView;
    }

    @Override
    public IPlaybackController getPlayback() {
        if (playback == null) {
            playback = new IPlaybackControllerImpl();
        }

        return playback;
    }

    public void setWebView(IWebView webView) {
        this.webView = webView;
    }
}