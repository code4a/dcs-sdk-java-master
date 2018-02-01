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
package com.code4a.dueroslib.devicemodule.speakcontroller;

import com.code4a.dueroslib.devicemodule.speakcontroller.message.AdjustVolumePayload;
import com.code4a.dueroslib.devicemodule.speakcontroller.message.MuteChangedPayload;
import com.code4a.dueroslib.devicemodule.speakcontroller.message.SetMutePayload;
import com.code4a.dueroslib.devicemodule.speakcontroller.message.SetVolumePayload;
import com.code4a.dueroslib.devicemodule.speakcontroller.message.VolumeStatePayload;
import com.code4a.dueroslib.devicemodule.system.HandleDirectiveException;
import com.code4a.dueroslib.devicemodule.system.HandleDirectiveException.ExceptionType;
import com.code4a.dueroslib.framework.BaseDeviceModule;
import com.code4a.dueroslib.framework.BaseMultiChannelMediaPlayer;
import com.code4a.dueroslib.framework.IMessageSender;
import com.code4a.dueroslib.framework.message.ClientContext;
import com.code4a.dueroslib.framework.message.Directive;
import com.code4a.dueroslib.framework.message.Event;
import com.code4a.dueroslib.framework.message.Header;
import com.code4a.dueroslib.framework.message.MessageIdHeader;
import com.code4a.dueroslib.framework.message.Payload;

/**
 * SpeakerController模块处理服务下发的SetVolume、AdjustVolume、SetMute指令，发送VolumeChanged、MuteChanged事件，
 * 以及维护自身的端状态
 * <p>
 * Created by wuruisheng on 2017/5/31.
 */
public class SpeakerControllerDeviceModule extends BaseDeviceModule {
    private final BaseMultiChannelMediaPlayer.ISpeakerController speakerController;

    public SpeakerControllerDeviceModule(BaseMultiChannelMediaPlayer.ISpeakerController speakerController,
                                         IMessageSender messageSender) {
        super(ApiConstants.NAMESPACE, messageSender);
        this.speakerController = speakerController;
    }

    @Override
    public ClientContext clientContext() {
        String namespace = getNameSpace();
        String name = ApiConstants.Events.VolumeState.NAME;
        Header header = new Header(namespace, name);
        long volume = (long) (speakerController.getVolume() * 100.0F);
        boolean mute = speakerController.getMute();
        VolumeStatePayload payload = new VolumeStatePayload(volume, mute);
        return new ClientContext(header, payload);
    }

    @Override
    public void handleDirective(Directive directive) throws HandleDirectiveException {
        Header header = directive.header;
        String name = header.getName();
        Payload payload = directive.getPayload();
        if (name.equals(ApiConstants.Directives.AdjustVolume.NAME)) {
            if (payload instanceof AdjustVolumePayload) {
                AdjustVolumePayload adjustVolumePayload = (AdjustVolumePayload) directive.getPayload();
                float increment = (float) adjustVolumePayload.getVolume() / 100.0F;
                float volume = speakerController.getVolume() + increment;
                volume = Math.min(1.0F, Math.max(volume, -1.0F));
                setVolume(volume);
            }
        } else if (name.equals(ApiConstants.Directives.SetVolume.NAME)) {
            if (payload instanceof SetVolumePayload) {
                SetVolumePayload setVolumePayload = (SetVolumePayload) payload;
                float volume = (float) setVolumePayload.getVolume() / 100.0F;
                this.setVolume(volume);
            }
        } else if (name.equals(ApiConstants.Directives.SetMute.NAME)) {
            if (payload instanceof SetMutePayload) {
                SetMutePayload setMutePayload = (SetMutePayload) payload;
                setMute(setMutePayload.getMute());
            }
        } else {
            String message = "SpeakerController cannot handle the directive";
            throw new HandleDirectiveException(ExceptionType.UNSUPPORTED_OPERATION, message);
        }
    }

    @Override
    public void release() {

    }

    private void setVolume(float volume) {
        speakerController.setVolume(volume);
        messageSender.sendEvent(volumeChangedEvent());
    }

    private void setMute(boolean mute) {
        speakerController.setMute(mute);
        messageSender.sendEvent(muteChangedEvent());
    }

    private Event volumeChangedEvent() {
        String nameSpace = getNameSpace();
        String name = ApiConstants.Events.VolumeChanged.NAME;
        MessageIdHeader header = new MessageIdHeader(nameSpace, name);
        VolumeStatePayload payload = new VolumeStatePayload(getVolume(), isMuted());
        return new Event(header, payload);
    }

    private Event muteChangedEvent() {
        String nameSpace = getNameSpace();
        String name = ApiConstants.Events.MuteChanged.NAME;
        MessageIdHeader header = new MessageIdHeader(nameSpace, name);
        MuteChangedPayload payload = new MuteChangedPayload(getVolume(), isMuted());
        return new Event(header, payload);
    }

    private long getVolume() {
        return (long) (speakerController.getVolume() * 100.0F);
    }

    private boolean isMuted() {
        return speakerController.getMute();
    }
}
