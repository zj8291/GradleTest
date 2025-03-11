package com.z.my_gradle_test_project01.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.z.my_gradle_test_project01.R;
import com.z.my_gradle_test_project01.service.MyCustomViewService;

public class MyCustomViewForegroundServiceActivity extends AppCompatActivity {

    Button button_start;
    Button button_stop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_my_custom_view_foreground_service);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        button_start = findViewById(R.id.button_custom_start);
        button_stop = findViewById(R.id.button_custom_stop);
        button_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService();
            }
        });
        button_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endService();
            }
        });
    }

    void startService() {
        Intent intent = new Intent(MyCustomViewForegroundServiceActivity.this, MyCustomViewService.class);
        startService(intent);
    }

    void endService() {
        Intent intent = new Intent(MyCustomViewForegroundServiceActivity.this, MyCustomViewService.class);
        stopService(intent);
    }
}