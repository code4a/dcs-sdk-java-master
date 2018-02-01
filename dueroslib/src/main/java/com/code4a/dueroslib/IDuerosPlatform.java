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
        oauthDuerosPlatform(true);
    }

    public void uninitDcsFramework() {
        duerosConfig.uninitFramework();
    }

    public void initDcsFramework() {
        duerosConfig.initDcsFramework();
        createCustomDeviceModules();
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

    public void startWakeUp() {
        duerosConfig.startWakeUp();
    }

    public void stopWakeUp() {
        duerosConfig.stopWakeUp();
    }

    public abstract void addDuerosCustomTaskCallback(DuerosCustomTaskCallback customTaskCallback);

    public abstract void createCustomDeviceModules();

    public abstract void oauthDuerosPlatform(boolean isInitFramework);

    public void changeRecording() {
        try {
            duerosConfig.changeRecord();
        } catch (TokenInvalidException e) {
            LogUtil.e(TAG, e.getMessage());
            oauthDuerosPlatform(false);
        }
    }

    public IWebView getWebView() {
        return duerosConfig.getWebView();
    }

}
