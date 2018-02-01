package com.baidu.duer.dcs.oauth.api;

import com.baidu.dcs.okhttp3.Call;
import com.baidu.dcs.okhttp3.Response;
import com.baidu.duer.dcs.http.DcsHttpManager;
import com.baidu.duer.dcs.http.callback.DcsCallback;
import com.baidu.duer.dcs.util.ObjectMapperUtil;

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
     * @param dcsCallback 请求结果回调
     */
    public static void oauthClientCredentials(String url, Map<String, String> params, DcsCallback dcsCallback) {
        DcsHttpManager.post()
                .url(url)
                .tag(AUTH_CLIENT_CREDENTIALS_TAG)
                .params(params)
                .build()
                .connTimeOut(DEFAULT_MILLISECONDS)
                .readTimeOut(DEFAULT_MILLISECONDS)
                .execute(dcsCallback);
    }

    public interface OauthCallback<T>{

        void onSuccess(T info);

        void onFailure(String string);

        void onStart();

        void onEnd();
    }
}
