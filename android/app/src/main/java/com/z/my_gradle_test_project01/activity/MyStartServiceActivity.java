package com.z.my_gradle_test_project01.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.z.my_gradle_test_project01.R;
import com.z.my_gradle_test_project01.service.MyTestService;

/**
 * 进入activity后启动后台服务
 */
public class MyStartServiceActivity extends AppCompatActivity {
    private static final String TAG = "MyStartServiceActivity";

    TextView textView_text;
    Button button_start;
    Button button_stop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_my_start_service);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        textView_text = findViewById(R.id.textView_startService_text);
        button_start = findViewById(R.id.button_startService_start);
        button_stop = findViewById(R.id.button_startService_stop);
    }

    @Override
    protected void onStart() {
        super.onStart();
        textView_text.setText("服务未启动");
        button_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartService();
            }
        });
        button_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService();
            }
        });
    }

    void stopService() {
        Intent intent = new Intent(MyStartServiceActivity.this, MyTestService.class);
        // 使用stopService方法停止服务
        boolean result = stopService(intent);
        if (result) {
            textView_text.setText("服务已停止");
        } else {
            textView_text.setText("服务停止失败或服务未运行");
        }
    }

    void StartService() {
        Intent intent = new Intent(MyStartServiceActivity.this, MyTestService.class);
        ///使用startService的方式启动Service
        startService(intent);
        textView_text.setText("服务开启");
    }
}