package com.code4a.dueroslib;

import android.app.Application;

import com.code4a.dueroslib.androidsystemimpl.webview.BaseWebView;
import com.code4a.dueroslib.oauth.exception.TokenInvalidException;
import com.code4a.dueroslib.systeminterface.IWebView;
import com.code4a.dueroslib.util.LogUtil;
import com.code4a.dueroslib.wakeup.WakeUpSuccessCallback;

/**
 * Created by jiang on 2018/2/1.
 */

public abstract class IDuerosPlatform {

    final String TAG = IDuerosPlatform.this.getClass().getSimpleName();

    protected DuerosConfig duerosConfig;

    protected IDuerosPlatform(Application application, String clientId, String clientSecert) {
        DuerosConfig.init(application, clientId, clientSecert);
        duerosConfig = DuerosConfig.getInstance();
    }

    public boolean uninitDcsFramework() {
        return duerosConfig.uninitFramework();
    }

    public boolean initDcsFramework() {
        try {
            if (duerosConfig.initDcsFramework()) {
                createCustomDeviceModules();
            }
            return true;
        } catch (TokenInvalidException e) {
            LogUtil.e(TAG, "token invalid exception!");
            oauthDuerosPlatform();
        }
        return false;
    }

    public void stopAudioRecord() {
        duerosConfig.stopAudioRecord();
    }

    public void startAudioRecord() {
        duerosConfig.startAudioRecord();
    }

    public void stopWakeUp() {
        duerosConfig.stopWakeUp();
    }

    public void startWakeUp() {
        duerosConfig.startWakeUp();
    }

    public void addOnRecordingListener(DuerosConfig.OnRecordingListener listener) {
        duerosConfig.addOnRecordingListener(listener);
    }

    public void setWakeUpSuccessCallback(WakeUpSuccessCallback wakeUpSuccessCallback) {
        duerosConfig.addWakeUpSuccessCallback(wakeUpSuccessCallback);
    }

    public void setWebView(BaseWebView webView) {
        duerosConfig.setWebView(webView);
    }

    public abstract <T extends DuerosCustomTaskCallback> void addDuerosCustomTaskCallback(T customTaskCallback);

    public abstract void createCustomDeviceModules();

    public abstract void oauthDuerosPlatform();

    public void changeRecording() {
        try {
            duerosConfig.changeRecord();
        } catch (TokenInvalidException e) {
            LogUtil.e(TAG, e.getMessage());
            oauthDuerosPlatform();
        }
    }

    public IWebView getWebView() {
        return duerosConfig.getWebView();
    }

}
