package com.z.my_gradle_test_project01;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PersistableBundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.z.my_gradle_test_project01.service.MyTestService;


import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;

public class MainActivity extends FlutterActivity {
    private static final String TAG = "MyTestMainActivity";

    private static final String METHOD_CHANNEL = "nativeMethodChannel";

    private static final String methodName_openMyTestActivity = "openMyTestActivity";
    private static final String methodName_share = "share";
    private static final String methodName_startService = "startService";
    private static final String methodName_bindService = "bindService";

    MethodChannel methodChannel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        Log.d(TAG, "onCreate: 执行");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: 执行");
    }

    @Override
    public void configureFlutterEngine(@NonNull FlutterEngine flutterEngine) {
        super.configureFlutterEngine(flutterEngine);
        methodChannel = new MethodChannel(flutterEngine.getDartExecutor().getBinaryMessenger(), METHOD_CHANNEL);
        // 设置方法调用的handler
        methodChannel.setMethodCallHandler(new MethodChannel.MethodCallHandler() {
            @Override
            public void onMethodCall(@NonNull MethodCall call, @NonNull MethodChannel.Result result) {
                // TODO:可以写Flutter调用原生方法的回调
                if (call.method.equals(methodName_openMyTestActivity)) {
                    Integer clickNumber = (Integer) call.argument("clickNumber");
                    if (clickNumber == null) {
                        result.error("-1", "参数错误", null);
                        return;
                    }
                    // 启动一个Activity（显式Intent启动Activity，显式Intent指明哪个具体的组件）
                    Intent intent = new Intent(MainActivity.this, MyTestActivity.class);
                    // Bundle用于存储键值对参数
                    Bundle bundle = new Bundle();
                    bundle.putInt("clickNumber", clickNumber);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    result.success(null);
                } else if (call.method.equals(methodName_share)) {
                    String shareContent = call.argument("shareContent");
                    // 分享内容 （隐式Intent，不指明需要哪个具体的组件，由操作系统决定哪个组件）
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_TEXT, shareContent);
                    startActivity(intent);
                    result.success(null);
                } else if (call.method.equals(methodName_startService)) {
                    //使用startService方法启动Service，当MainActivity执行onDestroy方法后，startService依然可以继续执行其中的任务
                    Intent intent = new Intent(MainActivity.this, MyTestService.class);
                    startService(intent);
                } else if (call.method.equals(methodName_bindService)) {
                    Intent intent = new Intent(MainActivity.this, MyTestService.class);
                    ServiceConnection serviceConnection = new ServiceConnection() {
                        //Service绑定成功的回调
                        @Override
                        public void onServiceConnected(ComponentName name, IBinder service) {
                            ((MyTestService.MyTestServiceBinder) service).myTestService.count.addListener(new MyTestService.MyTestServiceLiveData.OnDataChangedListener<Integer>() {
                                @Override
                                public void onDataChanged(Integer data) {

                                }
                            });
                        }

                        //Service解绑成功的回调
                        @Override
                        public void onServiceDisconnected(ComponentName name) {

                        }
                    };
                    bindService(intent, serviceConnection, BIND_AUTO_CREATE);
                } else {
                    try {
                        result.notImplemented();
                    } catch (RuntimeException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: 执行");
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: 执行");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: 执行");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: 执行");
    }
}
