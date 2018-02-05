package com.code4a.dueroslib.http.callback;

import com.baidu.dcs.okhttp3.Call;
import com.baidu.dcs.okhttp3.Request;

/**
 * Created by jiang on 2018/2/5.
 */

public class DirectCallback<T> {

    /**
     * UI线程
     *
     * @param request 请求
     * @param id      请求id
     */
    public void onBefore(Request request, int id) {
    }

    /**
     * UI线程
     *
     * @param id 请求id
     */
    public void onAfter(int id) {
    }

    public void onFailure(int id, Call call, String message) {

    }

    public void onSuccess(T t, int id) {

    }
}
