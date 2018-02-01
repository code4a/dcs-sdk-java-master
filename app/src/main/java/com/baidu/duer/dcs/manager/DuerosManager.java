package com.baidu.duer.dcs.manager;

import android.app.Application;
import android.text.TextUtils;

import com.baidu.duer.dcs.androidapp.DcsSampleApplication;
import com.baidu.duer.dcs.androidsystemimpl.PlatformFactoryImpl;
import com.baidu.duer.dcs.framework.DcsFramework;
import com.baidu.duer.dcs.framework.DeviceModuleFactory;
import com.baidu.duer.dcs.http.HttpConfig;
import com.baidu.duer.dcs.oauth.api.BaiduOauthClientCredentials;
import com.baidu.duer.dcs.oauth.api.IOauth;
import com.baidu.duer.dcs.oauth.api.OauthClientCredentialsInfo;
import com.baidu.duer.dcs.oauth.api.OauthPreferenceUtil;
import com.baidu.duer.dcs.oauth.api.OauthRequest;
import com.baidu.duer.dcs.systeminterface.IPlatformFactory;
import com.baidu.duer.dcs.util.LogUtil;

/**
 * Created by jiang on 2018/1/31.
 */

public class DuerosManager {

    private static final String CLIENT_ID = "dL33GvH6W91LVSirqu8BoR9KzyUefZDl";
    private static final String CLIENT_SECRET = "LRW0tO85FNBWyyh19roOXIktND2IX97f";

    static DuerosManager mInstance;
    private DcsFramework dcsFramework;
    private DeviceModuleFactory deviceModuleFactory;
    private IPlatformFactory platformFactory;

    public static void init(Application context) {
        mInstance = new DuerosManager(context);
    }

    public static DuerosManager getInstance() {
        if (mInstance == null) throw new RuntimeException("please call init at first");
        return mInstance;
    }

    private DuerosManager(Application context){
        doAuth(context);
        platformFactory = new PlatformFactoryImpl(context);
        dcsFramework = new DcsFramework(platformFactory);
        deviceModuleFactory = dcsFramework.getDeviceModuleFactory();

        deviceModuleFactory.createVoiceOutputDeviceModule();
        deviceModuleFactory.createVoiceInputDeviceModule();
    }

    void doAuth(Application context) {
        BaiduOauthClientCredentials baiduOauthClientCredentials = new BaiduOauthClientCredentials(CLIENT_ID, CLIENT_SECRET, context);
        baiduOauthClientCredentials.authorize(context, new OauthRequest.OauthCallback<OauthClientCredentialsInfo>() {
            @Override
            public void onSuccess(OauthClientCredentialsInfo info) {
                LogUtil.e("DoAuth", "auth success");
            }

            @Override
            public void onFailure(String string) {
                throw new RuntimeException("Dueros auth failure!");
            }

            @Override
            public void onStart() {
                LogUtil.v("DoAuth", "onStart");
            }

            @Override
            public void onEnd() {
                LogUtil.v("DoAuth", "onEnd");
            }
        });
    }

    void checkAuth(final Application context) {
        IOauth baiduOauth = new IOauth() {
            @Override
            public String getAccessToken() {
                return OauthPreferenceUtil.getAccessToken(context);
            }

            @Override
            public void authorize() {
                doAuth(context);
            }

            @Override
            public boolean isSessionValid() {
                String accessToken = getAccessToken();
                long createTime = OauthPreferenceUtil.getCreateTime(context);
                long expires = OauthPreferenceUtil.getExpires(DcsSampleApplication.getInstance()) + createTime;
                return !TextUtils.isEmpty(accessToken) && expires != 0 && System.currentTimeMillis() < expires;
            }
        };
        HttpConfig.setAccessToken(baiduOauth.getAccessToken());
    }
}
