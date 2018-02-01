package com.code4a.duerossimple;

import android.app.Application;

import com.code4a.dueroslib.DuerosGatewayManager;
import com.code4a.dueroslib.DuerosPlatformManager;

/**
 * Created by jiang on 2018/2/1.
 */

public class SimpleApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        DuerosGatewayManager duerosGatewayManager = DuerosPlatformManager.createDuerosGatewayManager(this, "dL33GvH6W91LVSirqu8BoR9KzyUefZDl", "LRW0tO85FNBWyyh19roOXIktND2IX97f");
        DuerosPlatformManager.initDuerosPlatformManager(duerosGatewayManager);
    }
}
