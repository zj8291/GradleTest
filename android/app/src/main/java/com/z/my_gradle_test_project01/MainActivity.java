package com.z.my_gradle_test_project01;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PersistableBundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.z.my_gradle_test_project01.activity.MyBindServiceActivity;
import com.z.my_gradle_test_project01.activity.MyCustomViewForegroundServiceActivity;
import com.z.my_gradle_test_project01.activity.MyForegroundServiceActivity;
import com.z.my_gradle_test_project01.activity.MyStartServiceActivity;
import com.z.my_gradle_test_project01.activity.MyTestActivity;
import com.z.my_gradle_test_project01.model.MyServiceLiveData;
import com.z.my_gradle_test_project01.service.MyTestForegroundService;
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
    private static final String methodName_bindForegroundService = "bindForegroundService";
    private static final String methodName_startCustomViewForegroundService =
            "startCustomViewForegroundService";

    private static final int REQUEST_NOTIFICATION_PERMISSION = 1001;

    MethodChannel methodChannel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        Log.d(TAG, "onCreate: 执行");
    }

    ///请求权限结果的回调
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_NOTIFICATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 权限已授予，可以启动服务
                Toast.makeText(this, "通知权限请求成功", Toast.LENGTH_SHORT).show();
            } else {
                // 权限被拒绝，提示用户
                Toast.makeText(this, "需要通知权限才能正常运行服务", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: 执行");
        // 请求通知权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) !=
                    PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.POST_NOTIFICATIONS},
                        REQUEST_NOTIFICATION_PERMISSION);
            }
        }
    }

    @Override
    public void configureFlutterEngine(@NonNull FlutterEngine flutterEngine) {
        super.configureFlutterEngine(flutterEngine);
        methodChannel = new MethodChannel(flutterEngine.getDartExecutor().getBinaryMessenger(), METHOD_CHANNEL);
        // 设置方法调用的handler
        methodChannel.setMethodCallHandler(new MethodChannel.MethodCallHandler() {
            @Override
            public void onMethodCall(@NonNull MethodCall call, @NonNull MethodChannel.Result result) {
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
                    Intent intent = new Intent(MainActivity.this, MyStartServiceActivity.class);
                    startActivity(intent);
                } else if (call.method.equals(methodName_bindService)) {
                    Intent intent = new Intent(MainActivity.this, MyBindServiceActivity.class);
                    startActivity(intent);
                } else if (call.method.equals(methodName_bindForegroundService)) {
                    Intent intent = new Intent(MainActivity.this, MyForegroundServiceActivity.class);
                    startActivity(intent);
                } else if (call.method.equals(methodName_startCustomViewForegroundService)) {
                    Intent intent = new Intent(MainActivity.this, MyCustomViewForegroundServiceActivity.class);
                    startActivity(intent);
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
