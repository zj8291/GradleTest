package com.z.my_gradle_test_project01.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.z.my_gradle_test_project01.MainActivity;
import com.z.my_gradle_test_project01.R;
import com.z.my_gradle_test_project01.model.MyServiceLiveData;
import com.z.my_gradle_test_project01.service.MyTestForegroundService;

public class MyForegroundServiceActivity extends AppCompatActivity {

    TextView textView_status;
    TextView textView_Content;
    Button button_start;
    Button button_stop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_my_foreground_service);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        textView_status = findViewById(R.id.textView_foregroundService_status);
        textView_Content = findViewById(R.id.textView_foregroundService_count);
        button_start = findViewById(R.id.button_foregroundService_start);
        button_stop = findViewById(R.id.button_foregroundService_stop);
        button_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startForegroundService();
            }
        });
        button_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopForegroundService();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        textView_Content.setText(getString(R.string.serviceNotStart));
    }

    void stopForegroundService() {
        Intent intent = new Intent(MyForegroundServiceActivity.this, MyTestForegroundService.class);
        boolean result = stopService(intent);
        if (result) {
            textView_status.setText("前台服务已停止");
        } else {
            textView_status.setText("前台服务停止失败或服务未运行");
        }
    }

    void startForegroundService() {
        textView_Content.setText(getString(R.string.serviceStart));
        ///前台服务
        Intent intent = new Intent(MyForegroundServiceActivity.this, MyTestForegroundService.class);
        startService(intent);
    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        // 确保在Activity销毁时解绑服务
//        if (isBound && serviceConnection != null) {
//            unbindService(serviceConnection);
//            isBound = false;
//        }
//    }
}