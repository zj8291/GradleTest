package com.z.my_gradle_test_project01.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.z.my_gradle_test_project01.R;

/**
 * 专门用于处理外部intent的activity
 */
public class MyShareIntentHandleActivity extends AppCompatActivity {

    private static final String TAG = "MyShareIntentHandleActivity";

    TextView textView_shareIntentContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_my_share_intent_handle);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        textView_shareIntentContent = findViewById(R.id.textView_shareIntentContent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        if (Intent.ACTION_SEND.equals(intent.getAction()) && intent.getType() != null) {
            if (intent.getType().equals("text/plain")) {
                String stringExtra = intent.getStringExtra(Intent.EXTRA_TEXT);
                String shareContent = getString(R.string.shareContent, stringExtra);
                textView_shareIntentContent.setText(shareContent);
            }
        } else {
            textView_shareIntentContent.setText(R.string.noneShareContent);
        }
    }
}