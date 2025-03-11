package com.z.my_gradle_test_project01.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.z.my_gradle_test_project01.MyExecutor;
import com.z.my_gradle_test_project01.activity.MyForegroundServiceActivity;
import com.z.my_gradle_test_project01.activity.MyTestActivity;
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
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: Service");
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind: Service");
        return super.onUnbind(intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: Service");
        ////高版本sdk需要手动请求通知权限，才会显示通知
        //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        //    requestNotificationPermission();
        //}
        createNotificationChannel();
        Intent intent = new Intent(this, MyForegroundServiceActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.baseline_3g_mobiledata_24)
                .setContentTitle("天翼3G 快人一步")
                .setContentText("setContentText")
                .setContentIntent(pendingIntent)
                .build();
        startForeground(new Random().nextInt(), notification);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: Service");
        return super.onStartCommand(intent, flags, startId);
    }

    private void requestNotificationPermission() {
        try {
            // 创建一个广播意图，通知应用需要请求通知权限
            Intent intent = new Intent("android.settings.APP_NOTIFICATION_SETTINGS");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("android.provider.extra.APP_PACKAGE", getPackageName());
            startActivity(intent);
            Log.d(TAG, "请求通知权限");
        } catch (Exception e) {
            Log.e(TAG, "请求通知权限失败", e);
        }
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
        Log.d(TAG, "onBind: Service");
        return new MyTestForgroundServiceBinder(null, myServiceLiveData);
    }


    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is not in the Support Library.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name_foreground);
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