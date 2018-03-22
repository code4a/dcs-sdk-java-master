package com.code4a.dueroslib;

import android.app.Application;

import com.code4a.dueroslib.androidsystemimpl.webview.BaseWebView;
import com.code4a.dueroslib.oauth.exception.OAuthFailureException;
import com.code4a.dueroslib.systeminterface.IWebView;
import com.code4a.dueroslib.wakeup.WakeUpSuccessCallback;

/**
 * Created by jiang on 2018/2/1.
 * <p>
 * 使用步骤：
 * 1.需要创建IDuerosPlatform的实现类，DuerosGatewayManager/DuerosAudioPlayerManager/或者继承IDuerosPlatform的类，或者匿名实现类等。
 * 目前实现类只是做了一些数据的初始化，赋值的操作
 * 2.接着需要调用initDuerosPlatformManager方法传递IDuerosPlatform的实现类，进行赋值，接着在此方法内，做了平台的oatuh授权。获取token进行下一步的操作
 * 3.然后需要调用initDcsFramework进行框架的初始化，自定义模块的初始化
 * 4.最后可以添加一些监听器，唤醒成功回调监听，录音状态回调监听，自定义模块的状态回调监听，以及可以设置webview，查看语音的答复内容
 */

public final class DuerosPlatformManager {

    static final String TAG = DuerosPlatformManager.class.getSimpleName();

    private static IDuerosPlatform iDuerosPlatform;

    public static void initDuerosPlatformManager(IDuerosPlatform iDuerosPlatform) {
        DuerosPlatformManager.iDuerosPlatform = iDuerosPlatform;
        try {
            DuerosPlatformManager.iDuerosPlatform.oauthDuerosPlatform();
        } catch (OAuthFailureException e) {
            e.printStackTrace();
        }
    }

    public static DuerosGatewayManager createDuerosGatewayManager(Application application, String clientId, String clientSecert) {
        return new DuerosGatewayManager(application, clientId, clientSecert);
    }

    public static boolean initDcsFramework() {
        if (iDuerosPlatform != null) {
            return iDuerosPlatform.initDcsFramework();
        }
        return false;
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

    public static void stopAudioRecord() {
        if (iDuerosPlatform != null) iDuerosPlatform.stopAudioRecord();
    }

    public static void startAudioRecord() {
        if (iDuerosPlatform != null) iDuerosPlatform.startAudioRecord();
    }

    public static void stopWakeUp() {
        if (iDuerosPlatform != null) iDuerosPlatform.stopWakeUp();
    }

    public static void startWakeUp() {
        if (iDuerosPlatform != null) iDuerosPlatform.startWakeUp();
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
