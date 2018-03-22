package com.code4a.dueroslib;

import android.app.Application;

import com.code4a.dueroslib.devicemodule.cmccgateway.SmartGatewayDeviceModule;
import com.code4a.dueroslib.oauth.api.OauthClientCredentialsInfo;
import com.code4a.dueroslib.oauth.api.OauthRequest;
import com.code4a.dueroslib.oauth.exception.OAuthFailureException;
import com.code4a.dueroslib.util.LogUtil;

/**
 * Created by jiang on 2018/2/1.
 */

public final class DuerosGatewayManager extends IDuerosPlatform {

    static final String TAG = DuerosGatewayManager.class.getSimpleName();

    protected DuerosGatewayManager(Application application, String clientId, String clientSecert) {
        super(application, clientId, clientSecert);
    }

    @Override
    public <T extends DuerosCustomTaskCallback> void addDuerosCustomTaskCallback(T customTaskCallback) {
        duerosConfig.addDuerosCustomTaskCallback(customTaskCallback);
    }

    @Override
    public void createCustomDeviceModules() {
        duerosConfig.createGatewayDeviceModule();
    }

    @Override
    public void oauthDuerosPlatform() throws OAuthFailureException {
        LogUtil.w(TAG, " --- oauthDuerosPlatform --- ");
        duerosConfig.clientCredentialsOauth(new OauthRequest.OauthCallback<OauthClientCredentialsInfo>() {
            @Override
            public void onSuccess(OauthClientCredentialsInfo info) {
                LogUtil.w(TAG, "client credentials success");
            }

            @Override
            public void onFailure(String string) {
                LogUtil.e(TAG, "client credentials failed! " + string);
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

    public interface GatewayControlListener extends DuerosCustomTaskCallback {

        void controlDevice(SmartGatewayDeviceModule.Command command, String deviceName, String type);
    }
}
