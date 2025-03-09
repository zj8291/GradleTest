package com.z.my_gradle_test_project01.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.z.my_gradle_test_project01.MyExecutor;
import com.z.my_gradle_test_project01.model.MyServiceLiveData;


/**
 * Service一样是一个应用程序组件,和activity一样，需要在Manifest文件中使用<service><service/>标签进行声明
 * service的启动方式有两种
 * - startService():生命周期 onCreate()->onStartCommand()->[任务]->直到stopSelf()或者stopService()方法执行->onDestroy()
 * - bindService(): 生命周期 onCreate()->onBind()->（所有组件解绑后）->onUnbind()->onDestroy()
 * <p>
 * startService()方式启动的服务适合做一些后台任务，如音乐播放、下载等，即使启动它的组件销毁了，它也可以继续运行，直到操作系统将其销毁
 * bindService()方式启动的服务，其生命周期与绑定的控件相关，适合做一些与UI实时交互、即时通信相关的任务
 * 注意，Service同样运行在主线程（UI线程）中，因此，若Service需要执行一些阻塞类型的任务时（如网络请求等），需要另起一个线程来完成
 * 否则会导致系统弹出弹窗提示应用程序无响应，同时注意，更新UI的操作只能在UI线程中完成
 */
public class MyTestService extends Service {
    private static final String TAG = "MyTestService";

    public MyServiceLiveData<Integer> count = new MyServiceLiveData<>(0);

    public MyTestService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: Service");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: Service");
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: Service");
        return new MyTestServiceBinder(null, this);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind: Service");
        return super.onUnbind(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: Service");
        //耗时操作不可放在UI线程上
        MyExecutor.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    Log.d(TAG, "onStartCommand: " + count.changeDataWithResult(count.data + 1).data);
                }
            }
        });
        return super.onStartCommand(intent, flags, startId);
    }


    /**
     * MyTestService的service绑定器，用于在bind成功之后向外部传回service对象,以获取service对象中的状态
     */
    public static class MyTestServiceBinder extends Binder {
        public MyTestService myTestService;

        public MyTestServiceBinder(@Nullable String descriptor, MyTestService myTestService) {
            super();
            this.myTestService = myTestService;
        }
    }

}