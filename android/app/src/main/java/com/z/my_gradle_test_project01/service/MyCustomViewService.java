package com.z.my_gradle_test_project01.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;

import com.z.my_gradle_test_project01.R;
import com.z.my_gradle_test_project01.model.MyServiceLiveData;

import java.util.Random;

public class MyCustomViewService extends Service {
    private static final String TAG = "MyCustomViewService";
    private static final String CHANNEL_ID = "MyCustomViewServiceChannel";

    public MyCustomViewService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //创建通知channel
        createNotificationChannel();
        //构建notification
        //定义一个Notification对象
        Notification notification = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, MyCustomViewService.class), PendingIntent.FLAG_MUTABLE);

            RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.notification_remote_view);
            remoteViews.setTextViewText(R.id.textView_title, "这是歌曲的标题");
            remoteViews.setImageViewResource(R.id.imageView_media, R.drawable.notification_img);

            RemoteViews remoteBigViews = new RemoteViews(getPackageName(), R.layout.notification_remote_view_big);
            remoteBigViews.setTextViewText(R.id.textView_title_big, "这是歌曲的标题");
            remoteBigViews.setTextViewText(R.id.textView_subtitle_big, "这是歌曲的子标题");
            remoteBigViews.setImageViewResource(R.id.imageView_media_big, R.drawable.notification_img);

            notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(R.drawable.baseline_4g_plus_mobiledata_24)
                    .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                    .setCustomContentView(remoteViews)
                    .setCustomBigContentView(remoteBigViews)
                    .setContentIntent(pendingIntent)
                    .setOngoing(true)
                    .build();
        }
        //提升为前台服务
        startForeground(new Random().nextInt(), notification);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is not in the Support Library.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name_custom_view_foreground);
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