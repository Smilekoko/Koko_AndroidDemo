package com.koko.www.androiddemo.component.service;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

public class One_Service extends Service {
    private String TAG = "One_Service";

    public One_Service() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "onCreate():内部自动调用,创建服务,只会被调用一次");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand():内部自动调用,开始服务/调用次数=startservice()调用次数");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "onDestroy():内部自动调用,销毁服务");
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.e(TAG, "onBind():内部自动调用,绑定服务");
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.e(TAG, "onUnbind():内部自动调用,解绑服务");
        return super.onUnbind(intent);
    }
//------------------------------------------------------------------------

    @Override
    public ComponentName startService(Intent service) {
        Log.e(TAG, "startService():手动调用,启动服务");
        return super.startService(service);
    }

    @Override
    public boolean stopService(Intent name) {
        Log.e(TAG, "stopService():手动调用,关闭服务");
        return super.stopService(name);
    }

    @Override
    public boolean bindService(Intent service, ServiceConnection conn, int flags) {
        Log.e(TAG, "bindService():手动调用,绑定服务");
        return super.bindService(service, conn, flags);
    }

    @Override
    public void unbindService(ServiceConnection conn) {
        Log.e(TAG, "unbindService():手动调用,解绑服务");
        super.unbindService(conn);
    }
}
