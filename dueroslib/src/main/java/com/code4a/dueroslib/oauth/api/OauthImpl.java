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

import android.text.TextUtils;
import com.code4a.dueroslib.DuerosConfig;

/**
 * 用户认证接口
 * <p>
 * Created by zhangyan42@baidu.com on 2017/6/8.
 */
public class OauthImpl implements IOauth {
    @Override
    public String getAccessToken() {
        return OauthPreferenceUtil.getAccessToken(DuerosConfig.getApplication());
    }

    @Override
    public void authorize() {

    }

    @Override
    public boolean isSessionValid() {
        String accessToken = getAccessToken();
        long createTime = OauthPreferenceUtil.getCreateTime(DuerosConfig.getApplication());
        long expires = OauthPreferenceUtil.getExpires(DuerosConfig.getApplication()) + createTime;
        return !TextUtils.isEmpty(accessToken) && expires != 0 && System.currentTimeMillis() < expires;
    }
}
