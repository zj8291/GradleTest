package com.z.my_gradle_test_project01.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.pm.ServiceInfo;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.z.my_gradle_test_project01.MyExecutor;
import com.z.my_gradle_test_project01.MyTestActivity;
import com.z.my_gradle_test_project01.R;
import com.z.my_gradle_test_project01.model.MyServiceLiveData;

import java.util.Random;

public class MyTestForegroundService extends Service {

    private static final String TAG = "MyTestForegroundService";
    public static final String CHANNEL_ID = "MyForegroundServiceChannel";

    MyServiceLiveData<Integer> myServiceLiveData = new MyServiceLiveData<>(0);

    public MyTestForegroundService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        MyExecutor.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1000);
                        Log.d(TAG, "run: " + myServiceLiveData.data);
                        myServiceLiveData.changeData(myServiceLiveData.data + 1);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        createNotificationChannel();
        Intent intent = new Intent(this, MyTestActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.baseline_3g_mobiledata_24)
                .setContentTitle("天翼3G 快人一步")
                .setContentText("setContentText")
                .setContentIntent(pendingIntent)
                .build();
        startForeground(new Random().nextInt(), notification);
    }

    public static class MyTestForgroundServiceBinder extends Binder {
        public MyServiceLiveData<Integer> myServiceLiveData;

        public MyTestForgroundServiceBinder(@Nullable String descriptor, MyServiceLiveData<Integer> myServiceLiveData) {
            this.myServiceLiveData = myServiceLiveData;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MyTestForgroundServiceBinder(null, myServiceLiveData);
    }


    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is not in the Support Library.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system. You can't change the importance
            // or other notification behaviors after this.
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

}