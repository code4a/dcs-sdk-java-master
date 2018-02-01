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
package com.code4a.dueroslib.http;


import com.code4a.dueroslib.framework.message.DcsRequestBody;
import com.code4a.dueroslib.framework.message.DcsStreamRequestBody;
import com.code4a.dueroslib.http.callback.DcsCallback;

/**
 * 网络请求工具接口
 * <p>
 * Created by zhangyan42@baidu.com on 2017/6/1.
 */
public interface HttpRequestInterface {
    /**
     * 异步Post EventString请求
     *
     * @param requestBody 请求信息体
     * @param dcsCallback 结果回调接口
     */
    void doPostEventStringAsync(DcsRequestBody requestBody, DcsCallback dcsCallback);

    /**
     * 异步PostMultipart请求
     *
     * @param requestBody       请求信息体
     * @param streamRequestBody 请求信息体stream
     * @param dcsCallback       结果回调接口
     */
    void doPostEventMultipartAsync(DcsRequestBody requestBody,
                                   DcsStreamRequestBody streamRequestBody,
                                   DcsCallback dcsCallback);

    /**
     * 异步Get Directives请求
     *
     * @param dcsCallback 结果回调接口
     */
    void doGetDirectivesAsync(DcsCallback dcsCallback);

    /**
     * 异步Get Ping请求
     *
     * @param requestBody 请求信息体
     * @param dcsCallback 结果回调接口
     */
    void doGetPingAsync(DcsRequestBody requestBody, DcsCallback dcsCallback);

    /**
     * 取消请求
     *
     * @param requestTag 请求标识
     */
    void cancelRequest(Object requestTag);
}