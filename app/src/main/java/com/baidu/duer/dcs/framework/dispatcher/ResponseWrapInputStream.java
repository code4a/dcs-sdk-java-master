/*
 * *
 * Copyright (c) 2017 Baidu, Inc. All Rights Reserved.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.baidu.duer.dcs.framework.dispatcher;

import android.util.Log;

import com.baidu.duer.dcs.util.FileUtil;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * get net time
 * <p>
 * Created by guxiuzhong@baidu.com on 2017/8/1.
 */
public class ResponseWrapInputStream extends BufferedInputStream {
    private static final int BUFFER_SIZE = 8192;

    public ResponseWrapInputStream(InputStream in) {
        super(in, BUFFER_SIZE);
    }

    @Override
    public synchronized int read(byte[] b, int off, int len) throws IOException {
        int ret = super.read(b, off, len);
        return ret;
    }
}