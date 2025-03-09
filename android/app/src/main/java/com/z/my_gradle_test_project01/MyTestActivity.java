package com.z.my_gradle_test_project01;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.z.my_gradle_test_project01.service.MyTestService;

/**
 * activity生命周期
 * <br/>
 * <img height="320" width="240" src="https://developer.android.com/guide/components/images/activity_lifecycle.png?hl=zh-cn"/>
 */
public class MyTestActivity extends AppCompatActivity {

    private static final String TAG = "MyTestActivity";

    TextView textView_1;
    TextView textView_2;
    TextView textView_3;

    /**
     * onCreate方法 Activity 首次创建时调用
     * - 初始化 Activity，例如设置布局、绑定数据等
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: 执行");
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_my_test);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        textView_1 = findViewById(R.id.textView_1);
        textView_2 = findViewById(R.id.textView_2);
        textView_3 = findViewById(R.id.textView_3);
    }

    /**
     * Activity 即将可见时调用，准备进入可互动状态
     * - 准备 Activity 的 UI 和数据，使其对用户可见
     */
    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: 执行");
        ///检查是否存在分享内容
        //获取Intent
        Intent intent = getIntent();
        if (Intent.ACTION_SEND.equals(intent.getAction()) && intent.getType() != null) {
            if (intent.getType().equals("text/plain")) {
                String stringExtra = intent.getStringExtra(Intent.EXTRA_TEXT);
                String shareContent = getString(R.string.shareContent, stringExtra);
                textView_2.setText(shareContent);
            }
        } else {
            textView_2.setText(R.string.noneShareContent);
        }
        // 获取参数
        Bundle extras = intent.getExtras();
        int clickNumber = extras.getInt("clickNumber");
        String clickTimes = getString(R.string.clickTimes, clickNumber);
        textView_1.setText(clickTimes);

        Intent bindServiceIntent = new Intent(MyTestActivity.this, MyTestService.class);
        ServiceConnection serviceConnection = new ServiceConnection() {
            //Service绑定成功的回调
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                ((MyTestService.MyTestServiceBinder) service).myTestService.count.addListener(new MyTestService.MyTestServiceLiveData.OnDataChangedListener<Integer>() {
                    @Override
                    public void onDataChanged(Integer data) {
                        String serviceCount = getString(R.string.serviceCount, data);
                        ///注意，视图只能由UI线程更新！！！
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                textView_3.setText(serviceCount);
                            }
                        });
                    }
                });
            }

            //Service解绑成功的回调
            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };
        bindService(bindServiceIntent, serviceConnection, BIND_AUTO_CREATE);
    }

    /**
     * Activity 失去焦点或部分被遮挡时调用
     * - 保存临时数据或释放资源，例如暂停动画或注销监听器
     */
    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: 执行");
    }


    /**
     * Activity 开始与用户交互时调用
     * - 恢复 Activity 的状态，例如启动动画或注册监听器
     */
    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: 执行");
    }

    /**
     * Activity 完全不可见时调用
     * - 释放不必要的资源或停止后台任务
     */
    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: 执行");
    }

    /**
     * Activity 被销毁时调用
     * - 释放所有资源，确保 Activity 完全终止
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: 执行");
    }

    /**
     * 当前activity调用onStop后又重新启动
     */
    @Override
    protected void onRestart() {
        super.onRestart();
    }
}