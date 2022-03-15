package com.bookkeeping.myapplication.service;

import android.app.Notification;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import androidx.annotation.RequiresApi;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import android.util.Log;

import com.bookkeeping.myapplication.MyApplication;
import com.bookkeeping.myapplication.model.BookModel;
import com.bookkeeping.myapplication.util.CommonUtils;
import com.bookkeeping.myapplication.util.Constant;
import com.bookkeeping.myapplication.util.NotificationUtils;
import com.bookkeeping.myapplication.util.StorageCustomerInfo02Util;
import com.bookkeeping.myapplication.util.UserDao;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author : qiuyiyang
 * @date : 2021/1/11  16:56
 * @desc :
 */
@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
public class HelperNotificationListenerService extends NotificationListenerService {

    private static final String TAG = "通知监听服务";
    private BookModel bookModel = new BookModel();
    private UserDao userDao;

    @Override
    public void onCreate() {
        super.onCreate();
        userDao= MyApplication.getUserDao();
        Log.i(TAG, "onCreate: 初始化");
        NotificationUtils notificationUtils = new NotificationUtils(this);
        startForeground(0,notificationUtils.getNotification("收款监听", "服务正在运行..."));
    }

    @Override
    public IBinder onBind(Intent intent) {
        return super.onBind(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onListenerConnected() {
        sendBroadcast(Constant.BROADCAST_TYPE_NEW_BILL, "服务正在运行...");
    }

    @Override
    public void onListenerDisconnected() {
        sendBroadcast(Constant.BROADCAST_TYPE_DISCONNECTED_SERVICE, "监听服务已经断开...");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        Notification notification = sbn.getNotification();
        String pkg = sbn.getPackageName();
        if (notification == null) {
            return;
        }
        Bundle extras = notification.extras;
        if (extras == null) {
            return;
        }
        if(!StorageCustomerInfo02Util.getBooleanInfo("notification",getApplicationContext(),true)){
            return;
        }
        final String title = getNotificationTitle(extras);
        final String content = getNotificationContent(extras);
        final String date = getNotificationTime(notification);
        printNotify(notification.when,date, title, content);
        if (Constant.LISTENING_TARGET_PKG1.equals(pkg)&&getNotificationTitle(extras).contains("交易提醒")) {
            final String money = findMoney(content);
            if (CommonUtils.isFastDoubleClick(1,1000)){
                return;
            }
            postMoney(notification.when,title, content, money, "支付宝");
        }
        else if (Constant.LISTENING_TARGET_PKG2.equals(pkg)&&getNotificationTitle(extras).equals("微信支付")&&getNotificationContent(extras).equals("微信支付凭证")) {
            if (CommonUtils.isFastDoubleClick(2,1000)){
                return;
            }
            postMoney(notification.when,title, content, "0","微信");
        }
    }

    private void postMoney(long when, final String title, final String content, final String money,final String type) {
        bookModel.setUseTime(when);
        bookModel.setCreatTime(when);
        bookModel.setId(CommonUtils.getRandomString());
        bookModel.setRemark(content);
        bookModel.setType(type);
        bookModel.setEvent(title);
        bookModel.setTrxMoney(new BigDecimal(money).multiply(new BigDecimal("-1")));
        userDao.add(bookModel,"new");
        sendBroadcast(Constant.BROADCAST_TYPE_NEW_BILL, "收到新的订单");
        NotificationUtils notificationUtils = new NotificationUtils(this);
        notificationUtils.sendNotification("账小计", "你有新的支付记录，点击补填信息",bookModel);
    }

    /**
     * 从通知内容中提取出金额
     * @param content
     * @return
     */
    private String findMoney(String content) {
        String pattern = "(\\d+\\.\\d{2})元";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(content);
        if (m.find()) {
            return m.group(1);
        }
        return "0";
    }

    private void sendBroadcast(int type, String msg) {
        Intent intent = new Intent(getPackageName());
        intent.putExtra("type", type);
        intent.putExtra("msg", msg);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        Log.d(TAG, "onNotificationRemoved");
    }

    private String getNotificationTime(Notification notification) {
        long when = notification.when;
        Date date = new Date(when);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String time = format.format(date);
        return time;
    }

    private String getNotificationTitle(Bundle extras) {
        return extras.getString(Notification.EXTRA_TITLE, "");
    }

    private String getNotificationContent(Bundle extras) {
        return extras.getString(Notification.EXTRA_TEXT, "");
    }

    private void printNotify(long when,String time, String title, String content) {
        Log.d(TAG, "when="+when);
        Log.d(TAG, "time="+time);
        Log.d(TAG, "title="+title);
        Log.d(TAG, "content="+content);
    }
}
