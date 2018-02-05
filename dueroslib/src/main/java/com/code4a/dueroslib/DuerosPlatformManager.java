package com.code4a.dueroslib;

import android.app.Application;

import com.code4a.dueroslib.androidsystemimpl.webview.BaseWebView;
import com.code4a.dueroslib.systeminterface.IWebView;
import com.code4a.dueroslib.wakeup.WakeUpSuccessCallback;

/**
 * Created by jiang on 2018/2/1.
 */

public final class DuerosPlatformManager {

    static final String TAG = DuerosPlatformManager.class.getSimpleName();

    private static IDuerosPlatform iDuerosPlatform;

    public static void initDuerosPlatformManager(IDuerosPlatform iDuerosPlatform) {
        DuerosPlatformManager.iDuerosPlatform = iDuerosPlatform;
    }

    public static DuerosGatewayManager createDuerosGatewayManager(Application application, String clientId, String clientSecert) {
        return new DuerosGatewayManager(application, clientId, clientSecert);
    }

    public static void addWakeUpSuccessCallback(WakeUpSuccessCallback wakeUpSuccessCallback) {
        if (iDuerosPlatform != null)
            iDuerosPlatform.setWakeUpSuccessCallback(wakeUpSuccessCallback);
    }

    public static void addOnRecordingListener(DuerosConfig.OnRecordingListener listener) {
        if (iDuerosPlatform != null) iDuerosPlatform.addOnRecordingListener(listener);
    }

    public static void addDuerosCustomTaskCallback(DuerosCustomTaskCallback duerosCustomTaskCallback) {
        if (iDuerosPlatform != null)
            iDuerosPlatform.addDuerosCustomTaskCallback(duerosCustomTaskCallback);
    }

    public static void setWebView(BaseWebView webView) {
        if (iDuerosPlatform != null) iDuerosPlatform.setWebView(webView);
    }


    public static void startWakeUp() {
        if (iDuerosPlatform != null) iDuerosPlatform.startWakeUp();
    }

    public static void stopWakeUp() {
        if (iDuerosPlatform != null) iDuerosPlatform.stopWakeUp();
    }

    public static void uninitDcsFramework() {
        if (iDuerosPlatform != null) iDuerosPlatform.uninitDcsFramework();
    }

    public static void changeRecording() {
        if (iDuerosPlatform != null) iDuerosPlatform.changeRecording();
    }

    public static IWebView getWebView() {
        return iDuerosPlatform == null ? null : iDuerosPlatform.getWebView();
    }
}
