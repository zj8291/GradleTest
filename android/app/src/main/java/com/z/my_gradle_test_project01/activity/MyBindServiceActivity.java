package com.z.my_gradle_test_project01.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
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
import com.z.my_gradle_test_project01.service.MyTestService;


/**
 * 进入activity后绑定后台服务
 */
public class MyBindServiceActivity extends AppCompatActivity {

    boolean isBound = false;

    ServiceConnection serviceConnection = new ServiceConnection() {
        //Service绑定成功的回调
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            isBound = true;
            ((MyTestService.MyTestServiceBinder) service).myTestService.count.addListener(new MyServiceLiveData.OnDataChangedListener<Integer>() {
                @Override
                public void onDataChanged(Integer data) {
                    String serviceCount = getString(R.string.serviceCount, data);
                    ///注意，视图只能由UI线程更新！！！
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textView_Content.setText(serviceCount);
                        }
                    });
                }
            });
        }

        //Service解绑成功的回调
        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBound = false;
            String serviceNotBind = getString(R.string.serviceNotBind);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    textView_status.setText(serviceNotBind);
                }
            });
        }
    };

    TextView textView_status;
    TextView textView_Content;
    Button button_bind;
    Button button_stop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_my_bind_service);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        textView_status = findViewById(R.id.textView_bindService_status);
        textView_Content = findViewById(R.id.textView_bindService_text);
        button_bind = findViewById(R.id.button_bindService_bind);
        button_stop = findViewById(R.id.button_bindService_stop);
        button_bind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bindService();
            }
        });
        button_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        textView_Content.setText(getString(R.string.serviceNotBind));
    }

    void stopService() {
        try {
            unbindService(serviceConnection);
        } catch (IllegalArgumentException e) {
            Toast.makeText(this, "服务未绑定", Toast.LENGTH_SHORT).show();
        }
    }

    void bindService() {
        Intent intent = new Intent(MyBindServiceActivity.this, MyTestService.class);
        bindService(intent, serviceConnection, BIND_AUTO_CREATE);
    }
}