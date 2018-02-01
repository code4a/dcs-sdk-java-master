package com.code4a.dueroslib.oauth.api;

import android.app.Application;

/**
 * Created by jiang on 2018/2/1.
 */

public interface BaiduOauth {

    /**
     * 获取token信息
     * @param application 上下文对象
     * @param listener 鉴权结果监听
     * @param configs 鉴权需要的参数
     */
    void authorize(Application application, BaiduOauthListener listener, boolean... configs);

    /**
     * 判断token信息是否有效。
     *
     * @return boolean true/false
     */
    boolean isSessionValid();

    /**
     * 获取AccessTokenManager对象
     *
     * @return accessTokenManager对象
     */
    AccessTokenManager getAccessTokenManager();

    /**
     * 获取AccessToken信息
     *
     * @return accessToken信息
     */
    String getAccessToken();

    /**
     * 将清除存储的token信息
     */
    void clearAccessToken();
}
