package com.z.my_gradle_test_project01;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.nio.ByteBuffer;

import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;

public class MainActivity extends FlutterActivity {

    private static final String METHOD_CHANNEL = "nativeMethodChannel";

    private static final String methodName_openMyTestActivity = "openMyTestActivity";
    private static final String methodName_share = "share";

    MethodChannel methodChannel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    protected void onStart() {
        super.onStart();

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
}
