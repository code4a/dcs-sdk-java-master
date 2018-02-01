package com.code4a.duerossimple;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.code4a.dueroslib.DuerosGatewayManager;
import com.code4a.dueroslib.DuerosPlatformManager;
import com.code4a.dueroslib.devicemodule.cmccgateway.SmartGatewayDeviceModule;

/**
 * Created by jiang on 2018/2/1.
 */

public class SimpleService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        DuerosPlatformManager.addDuerosCustomTaskCallback(new DuerosGatewayManager.GatewayControlListener() {
            @Override
            public void controlDevice(SmartGatewayDeviceModule.Command command, String deviceName, String type) {
                switch (command) {
                    case ADD:
                        showShortToast("添加" + deviceName);
                        break;
                    case REMOVE:
                        showShortToast("删除" + deviceName);
                        break;
                    case BIND:
                        showShortToast("绑定" + deviceName);
                        break;
                    case UNBIND:
                        showShortToast("解绑" + deviceName);
                        break;
                }
            }
        });
        return super.onStartCommand(intent, flags, startId);
    }

    private void showShortToast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }
}
