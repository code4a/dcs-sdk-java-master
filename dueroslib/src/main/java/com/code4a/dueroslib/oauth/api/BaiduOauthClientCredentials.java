/*
 * Copyright (c) 2017 Baidu, Inc. All Rights Reserved.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.code4a.dueroslib.oauth.api;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.CookieSyncManager;

import com.baidu.dcs.okhttp3.Call;
import com.baidu.dcs.okhttp3.Request;
import com.baidu.dcs.okhttp3.Response;
import com.code4a.dueroslib.http.callback.DcsCallback;
import com.code4a.dueroslib.http.callback.ResponseCallback;
import com.code4a.dueroslib.util.CommonUtil;
import com.code4a.dueroslib.util.LogUtil;
import com.code4a.dueroslib.util.ObjectMapperUtil;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 封装了oauth2授权，我们采用的是百度Oauth的Client Credentials的方式
 * 该方式的地址：http://developer.baidu.com/wiki/index.php?title=docs/oauth/client
 * <p>
 * Created by zhangyan42@baidu.com on 2017/5/24.
 * TODO: 百度Oauth2授权方式完善
 */
public class BaiduOauthClientCredentials implements Parcelable, BaiduOauth {
    private static final String LOG_TAG = "BaiduOauth";
    // 百度Oauth授权回调需要在DUEROS开放平台的控制平台
    // 应用编辑-->>OAUTH CONFIG URL的链接地址-->>授权回调页-->>安全设置-->>授权回调页
    // 需要注意
    private static final String OAUTHORIZE_URL = "https://openapi.baidu.com/oauth/2.0/token";

    private static final String[] DEFAULT_PERMISSIONS = {"public"};
    private static final String KEY_CLIENT_ID = "clientId";
    private static final String KEY_CLIENT_SECRET = "clientSecret";
    // 应用注册的api key信息
    private String cliendId;
    private String cliendSecret;
    private AccessTokenManager accessTokenManager;

    /**
     * 使用应用的基本信息构建Baidu对象
     *
     * @param clientId 应用注册的api key信息
     * @param context  当前应用的上下文环境
     */
    public BaiduOauthClientCredentials(String clientId, String cliendSecret, Context context) {
        if (TextUtils.isEmpty(clientId) || TextUtils.isEmpty(cliendSecret)) {
            throw new IllegalArgumentException("apiKey信息必须提供！");
        }
        this.cliendId = clientId;
        this.cliendSecret = cliendSecret;
        init(context);
    }

    /**
     * 使用Parcel流构建Baidu对象
     *
     * @param in Parcel流信息
     */
    public BaiduOauthClientCredentials(Parcel in) {
        Bundle bundle = Bundle.CREATOR.createFromParcel(in);
        this.cliendId = bundle.getString(KEY_CLIENT_ID);
        this.cliendSecret = bundle.getString(KEY_CLIENT_SECRET);
        this.accessTokenManager = AccessTokenManager.CREATOR.createFromParcel(in);
    }

    /**
     * 初始化accesTokenManager等信息
     *
     * @param context 当前执行的上下文环境
     */
    public void init(Context context) {
        if (context.checkCallingOrSelfPermission(Manifest.permission.ACCESS_NETWORK_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            Log.w(LOG_TAG, "App miss permission android.permission.ACCESS_NETWORK_STATE! "
                    + "Some mobile's WebView don't display page!");
        }
        this.accessTokenManager = new AccessTokenManager(context);
        this.accessTokenManager.initToken();
    }

    /**
     * 获取token信息，该方法使用默认的用户权限
     *
     * @param activity 用来校验权限的activity
     * @param listener Dialog回调接口如Activity跳转
     * @param configs
     */
    @Override
    public void authorize(Application activity, BaiduOauthListener listener, boolean ...configs) {
        this.authorize(activity, null, (OauthRequest.OauthCallback<OauthClientCredentialsInfo>)listener);
    }

    /**
     * 根据相应的permissions信息，获取token信息
     *
     * @param activity    用来校验权限的activity
     * @param permissions 需要获得的授权权限信息
     * @param listener    回调的listener接口，如Activity跳转等
     */
    private void authorize(Application activity, String[] permissions, final OauthRequest.OauthCallback<OauthClientCredentialsInfo> listener) {
        if (this.isSessionValid()) {
            listener.onSuccess(null);
            return;
        }

        // 使用匿名的BaiduDialogListener对listener进行了包装，并进行一些存储token信息和当前登录用户的逻辑，
        // 外部传进来的listener信息不需要在进行存储相关的逻辑
        this.authorize(activity, permissions,
                new ResponseCallback() {

                    @Override
                    public void onBefore(Request request, int id) {
                        listener.onStart();
                    }

                    @Override
                    public Response parseNetworkResponse(Response response, int id) throws Exception {
                        if (super.validateResponse(response, id)) {
                            try {
                                String json = response.body().string();
                                Log.e("parseNetworkResponse", json);
                                OauthClientCredentialsInfo info = ObjectMapperUtil.instance().getObjectReader(OauthClientCredentialsInfo.class).readValue(json);
                                // 存储相应的token信息
                                if(info == null){
                                    throw new RuntimeException("Token信息解析失败！");
                                }
                                Log.e("parseInfo", info.toString());
                                getAccessTokenManager().storeToken(info);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        return super.parseNetworkResponse(response, id);
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        listener.onFailure(e.getMessage());
                    }

                    @Override
                    public void onResponse(Response response, int id) {
                        if (validateResponse(response, id)) {
                            listener.onSuccess(null);
                        } else {
                            listener.onFailure(response.toString());
                        }
                    }

                    @Override
                    public void onAfter(int id) {
                        listener.onEnd();
                    }
                }, "client_credentials");
    }

    /**
     * 通过Client Credentials授权 获取access token
     *
     * @param application    用来校验权限的activity
     * @param permissions 需要请求的环境
     * @param dcsCallback 结果回调接口
     * @param grantType   授权请求的类型
     */
    private void authorize(Application application, String[] permissions, final DcsCallback dcsCallback, String grantType) {
        CookieSyncManager.createInstance(application);
        Map<String, String> params = new LinkedHashMap<>();
        params.put("client_id", this.cliendId);
        params.put("client_secret", this.cliendSecret);
        params.put("grant_type", grantType);
        if (permissions == null) {
            permissions = DEFAULT_PERMISSIONS;
        }
        if (permissions != null && permissions.length > 0) {
            String scope = TextUtils.join(" ", permissions);
            params.put("scope", scope);
        }
        LogUtil.d(LOG_TAG, "url:" + OAUTHORIZE_URL);
        if (application.checkCallingOrSelfPermission(Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {
            CommonUtil.showAlert(application, "没有权限", "应用需要访问互联网的权限");
        } else {
            OauthRequest.oauthClientCredentials(OAUTHORIZE_URL, params, dcsCallback);
        }
    }

    /**
     * 将清除存储的token信息
     */
    @Override
    public void clearAccessToken() {
        if (this.accessTokenManager != null) {
            this.accessTokenManager.clearToken();
            this.accessTokenManager = null;
        }
    }

    /**
     * 判断token信息是否有效。
     *
     * @return boolean true/false
     */
    @Override
    public boolean isSessionValid() {
        return this.accessTokenManager.isSessionValid();
    }

    /**
     * 获取AccessTokenManager对象
     *
     * @return accessTokenManager对象
     */
    @Override
    public AccessTokenManager getAccessTokenManager() {
        return this.accessTokenManager;
    }

    /**
     * 获取AccessToken信息
     *
     * @return accessToken信息
     */
    @Override
    public String getAccessToken() {
        return this.accessTokenManager.getAccessToken();
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.os.Parcelable#describeContents()
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.os.Parcelable#writeToParcel(android.os.Parcel, int)
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_CLIENT_ID, this.cliendId);
        bundle.putString(KEY_CLIENT_SECRET, this.cliendSecret);
        bundle.writeToParcel(dest, flags);
        this.accessTokenManager.writeToParcel(dest, flags);
    }

    public static final Creator<BaiduOauthClientCredentials> CREATOR = new Creator<BaiduOauthClientCredentials>() {
        public BaiduOauthClientCredentials createFromParcel(Parcel in) {
            return new BaiduOauthClientCredentials(in);
        }

        public BaiduOauthClientCredentials[] newArray(int size) {
            return new BaiduOauthClientCredentials[size];
        }
    };
}