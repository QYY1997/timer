package com.bookkeeping.myapplication;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Process;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.bookkeeping.myapplication.activity.ListFragment;
import com.bookkeeping.myapplication.model.BookModel;
import com.bookkeeping.myapplication.model.TypeModel;
import com.bookkeeping.myapplication.service.HelperNotificationListenerService;
import com.bookkeeping.myapplication.util.CommonUtils;
import com.bookkeeping.myapplication.util.Constant;
import com.bookkeeping.myapplication.util.PermissionUtil;
import com.bookkeeping.myapplication.util.StorageCustomerInfo02Util;
import com.bookkeeping.myapplication.util.UserDao;
import com.bookkeeping.myapplication.view.GlidePickImageLoader;
import com.lzy.imagepicker.ImagePicker;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author qiuyiyang
 */
public class MyApplication extends Application implements Application.ActivityLifecycleCallbacks {

    public static Context applicationContext;
    private static MyApplication instance;
    private static  UserDao userDao;
    private ServiceBroadcastReceiver serviceBroadcastReceiver = new ServiceBroadcastReceiver();

    public static MyApplication getInstance() {
        if (null == instance) {
            instance = new MyApplication();
        }
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = getApplicationContext();
        userDao=new UserDao(MyApplication.applicationContext);
        registerActivityLifecycleCallbacks(this);
        initImagePicker();
//        IntentFilter intentFilter = new IntentFilter(applicationContext.getPackageName());
//        LocalBroadcastManager.getInstance(applicationContext).registerReceiver(serviceBroadcastReceiver, intentFilter);
//        ensureCollectorRunning();
    }

    private void initImagePicker() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlidePickImageLoader());
        imagePicker.setShowCamera(true);
        imagePicker.setCrop(false);
        imagePicker.setSelectLimit(1);
    }

    public void ensureCollectorRunning() {
        ComponentName collectorComponent = new ComponentName(applicationContext, HelperNotificationListenerService.class);
        ActivityManager manager = (ActivityManager) applicationContext.getSystemService(Context.ACTIVITY_SERVICE);
        boolean collectorRunning = false;
        List<ActivityManager.RunningServiceInfo> runningServices = manager.getRunningServices(Integer.MAX_VALUE);
        if (runningServices == null) {
            return;
        }
        for (ActivityManager.RunningServiceInfo service : runningServices) {
            if (service.service.equals(collectorComponent)) {
                if (service.pid == Process.myPid()) {
                    collectorRunning = true;
                }
            }
        }
        if (collectorRunning) {
            return;
        }
        toggleNotificationListenerService(collectorComponent);
    }

    private void toggleNotificationListenerService(ComponentName collectorComponent) {
        PackageManager pm = applicationContext.getPackageManager();
        pm.setComponentEnabledSetting(collectorComponent,PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
    }

    public static UserDao getUserDao() {
        return userDao;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {
        Log.i("Activity_Show", "Activity = " + activity.getLocalClassName());
    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        LocalBroadcastManager.getInstance(applicationContext).unregisterReceiver(serviceBroadcastReceiver);
    }

    public class ServiceBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

        }
    }
}
