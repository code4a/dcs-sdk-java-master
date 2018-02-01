package com.code4a.dueroslib;

import android.app.Application;

import com.code4a.dueroslib.devicemodule.cmccgateway.SmartGatewayDeviceModule;
import com.code4a.dueroslib.oauth.api.OauthClientCredentialsInfo;
import com.code4a.dueroslib.oauth.api.OauthRequest;
import com.code4a.dueroslib.util.LogUtil;

/**
 * Created by jiang on 2018/2/1.
 */

public final class DuerosGatewayManager extends IDuerosPlatform {

    static final String TAG = DuerosGatewayManager.class.getSimpleName();

    GatewayControlListener gatewayControlListener;

    protected DuerosGatewayManager(Application application, String clientId, String clientSecert) {
        super(application, clientId, clientSecert);
    }

    @Override
    public void createCustomDeviceModules() {
        duerosConfig.getDeviceModuleFactory().createSmartGatewayDeviceModule();
        duerosConfig.getDeviceModuleFactory().getSmartGatewayDeviceModule()
                .addControlDeviceListener(new SmartGatewayDeviceModule.ControlDeviceListener() {
                    @Override
                    public void controlDevice(SmartGatewayDeviceModule.Command command, String deviceName, String type) {
                        LogUtil.e(TAG, "controlDevice : " + deviceName + type);
                        if (gatewayControlListener != null)
                            gatewayControlListener.controlDevice(command, deviceName, type);
                    }
                });
    }

    @Override
    public void oauthDuerosPlatform(final boolean isInitFramework) {
        duerosConfig.clientCredentialsOauth(new OauthRequest.OauthCallback<OauthClientCredentialsInfo>() {
            @Override
            public void onSuccess(OauthClientCredentialsInfo info) {
                LogUtil.w(TAG, "client credentials success");
                if (isInitFramework) {
                    initDcsFramework();
                }
            }

            @Override
            public void onFailure(String string) {
                LogUtil.w(TAG, "client credentials failed! " + string);
                throw new RuntimeException(string);
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
        this.gatewayControlListener = (GatewayControlListener) gatewayControlListener;
    }

    public interface GatewayControlListener extends DuerosCustomTaskCallback {

        void controlDevice(SmartGatewayDeviceModule.Command command, String deviceName, String type);
    }
}
