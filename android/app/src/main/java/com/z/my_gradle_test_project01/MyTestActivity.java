package com.z.my_gradle_test_project01;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MyTestActivity extends AppCompatActivity {

    TextView textView_1;
    TextView textView_2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_my_test);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        textView_2 = findViewById(R.id.textView_2);
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

        textView_1 = findViewById(R.id.textView_1);
        // 获取参数
        Bundle extras = intent.getExtras();
        int clickNumber = extras.getInt("clickNumber");
        String clickTimes = getString(R.string.clickTimes, clickNumber);
        textView_1.setText(clickTimes);
    }
}