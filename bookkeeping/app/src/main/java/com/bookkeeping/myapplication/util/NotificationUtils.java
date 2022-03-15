package com.bookkeeping.myapplication.util;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Build;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.bookkeeping.myapplication.R;
import com.bookkeeping.myapplication.activity.NewActivity;
import com.bookkeeping.myapplication.model.BookModel;

import java.util.Random;

/**
 * @author : qiuyiyang
 * @date : 2021/1/11  16:56
 * @desc :
 */
public class NotificationUtils extends ContextWrapper {

    private NotificationManager manager;
    public static final String id = "channel_1";
    public static final String name = "channel_name_1";

    public NotificationUtils(Context context) {
        super(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createNotificationChannel() {
        NotificationChannel channel = new NotificationChannel(id, name, NotificationManager.IMPORTANCE_LOW);
        getManager().createNotificationChannel(channel);
    }

    private NotificationManager getManager() {
        if (manager == null) {
            manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        }
        return manager;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Notification.Builder getChannelNotification(String title, String content) {
        return new Notification.Builder(getApplicationContext(), id)
                .setContentTitle(title)
                .setContentText(content)
                .setSmallIcon(R.drawable.ic_launch)
                .setAutoCancel(true);
    }

    public NotificationCompat.Builder getNotification_25(String title, String content) {
        return new NotificationCompat.Builder(getApplicationContext())
                .setContentTitle(title)
                .setContentText(content)
                .setSmallIcon(R.drawable.ic_launch)
                .setAutoCancel(true);
    }
    public void sendNotification(String title, String content, BookModel bookModel) {
        Notification notification;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel();
             notification = getChannelNotification(title, content).build();
        } else {
             notification = getNotification_25(title, content).build();
            notification.flags = Notification.FLAG_AUTO_CANCEL;
        }
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        Intent notificationIntent =new Intent(this, NewActivity.class);
        notificationIntent.putExtra("type", "edit");
        notificationIntent.putExtra("bookModel", bookModel);
        int requestCode = new Random().nextInt();
        PendingIntent contentItent = PendingIntent.getActivity(this, requestCode, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        notification.contentIntent=contentItent;
        if (CommonUtils.isFastDoubleClick2(15)) {
            return;
        }
        getManager().notify(requestCode, notification);
    }
    public Notification getNotification(String title, String content) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel();
            Notification notification = getChannelNotification(title, content).build();
            notification.flags = Notification.FLAG_FOREGROUND_SERVICE;
            return  notification;
        } else {
            Notification notification = getNotification_25(title, content).build();
            notification.flags = Notification.FLAG_FOREGROUND_SERVICE;
            return notification;
        }
    }
}
