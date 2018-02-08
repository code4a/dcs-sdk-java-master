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
    boolean initSuccess = false;

    protected IDuerosPlatform(Application application, String clientId, String clientSecert) {
        DuerosConfig.init(application, clientId, clientSecert);
        duerosConfig = DuerosConfig.getInstance();
        initSuccess = false;
        oauthDuerosPlatform(true);
    }

    public void uninitDcsFramework() {
        initSuccess = false;
        duerosConfig.uninitFramework();
    }

    public void initDcsFramework() {
        duerosConfig.initDcsFramework();
        initSuccess = true;
        createCustomDeviceModules();
    }

    public void addOnRecordingListener(DuerosConfig.OnRecordingListener listener) {
        if (initSuccess) {
            duerosConfig.addOnRecordingListener(listener);
        } else {
            LogUtil.e(TAG, "addOnRecordingListener uninit");
        }
    }

    public void setWakeUpSuccessCallback(WakeUpSuccessCallback wakeUpSuccessCallback) {
        if (!initSuccess) {
            LogUtil.e(TAG, "setWakeUpSuccessCallback uninit");
        }
        duerosConfig.addWakeUpSuccessCallback(wakeUpSuccessCallback);
    }

    public void setWebView(BaseWebView webView) {
        if (initSuccess) {
            duerosConfig.setWebView(webView);
        } else {
            LogUtil.e(TAG, "setWebView uninit");
        }
    }

    public void startWakeUp() {
        if (initSuccess) {
            duerosConfig.startWakeUp();
        } else {
            LogUtil.e(TAG, "startWakeUp uninit");
        }
    }

    public void stopWakeUp() {
        if (initSuccess) {
            duerosConfig.stopWakeUp();
        } else {
            LogUtil.e(TAG, "stopWakeUp uninit");
        }
    }

    public abstract void addDuerosCustomTaskCallback(DuerosCustomTaskCallback customTaskCallback);

    public abstract void createCustomDeviceModules();

    public abstract void oauthDuerosPlatform(boolean isInitFramework);

    public void changeRecording() {
        if (initSuccess) {
            try {
                duerosConfig.changeRecord();
            } catch (TokenInvalidException e) {
                LogUtil.e(TAG, e.getMessage());
                oauthDuerosPlatform(false);
            }
        }
    }

    public IWebView getWebView() {
        return duerosConfig.getWebView();
    }

}
