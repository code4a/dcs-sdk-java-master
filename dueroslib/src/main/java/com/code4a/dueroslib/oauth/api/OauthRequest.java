package com.code4a.dueroslib.oauth.api;

import com.code4a.dueroslib.http.DcsHttpManager;
import com.code4a.dueroslib.http.callback.DcsCallback;
import com.code4a.dueroslib.http.callback.DirectCallback;

import java.util.Map;

/**
 * Created by jiang on 2018/1/29.
 */

public class OauthRequest {

    public static final long DEFAULT_MILLISECONDS = 20 * 1000L;

    public static final String AUTH_CLIENT_CREDENTIALS_TAG = "auth_client_credentials_tag";

    /**
     * Client Credentials授权
     *
     * @param url         授权地址
     * @param params      get请求参数
     * @param directCallback 请求结果回调
     */
    public static void oauthClientCredentials(String url, Map<String, String> params, DirectCallback<OauthClientCredentialsInfo> directCallback) {
        DcsHttpManager.post()
                .url(url)
                .tag(AUTH_CLIENT_CREDENTIALS_TAG)
                .params(params)
                .build()
                .connTimeOut(DEFAULT_MILLISECONDS)
                .readTimeOut(DEFAULT_MILLISECONDS)
                .execute(OauthClientCredentialsInfo.class, directCallback);
    }

    public interface OauthCallback<T> extends BaiduOauthListener{

        void onSuccess(T info);

        void onFailure(String string);

        void onStart();

        void onEnd();
    }
}
