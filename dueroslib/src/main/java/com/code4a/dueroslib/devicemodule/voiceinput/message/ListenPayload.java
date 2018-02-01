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
package com.code4a.dueroslib.devicemodule.voiceinput.message;

import com.code4a.dueroslib.framework.message.Payload;

/**
 * Listen指令对应的payload结构
 * <p>
 * Created by wuruisheng on 2017/6/1.
 */
public class ListenPayload extends Payload {
    private long timeoutInMilliseconds;

    public void setTimeoutInMilliseconds(long timeoutInMilliseconds) {
        this.timeoutInMilliseconds = timeoutInMilliseconds;
    }

    public long getTimeoutInMilliseconds() {
        return timeoutInMilliseconds;
    }
}