package com.bookkeeping.myapplication.activity;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.Toast;
import com.bookkeeping.myapplication.R;
import com.bookkeeping.myapplication.base.BaseActivity;
import com.bookkeeping.myapplication.util.PermissionUtil;
import com.bookkeeping.myapplication.util.StorageCustomerInfo02Util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author : qiuyiyang
 * @date   : 2021/1/4  14:10
 * @desc   :
 */
public class LaunchActivity extends BaseActivity implements Animation.AnimationListener {

    final ScaleAnimation animation = new ScaleAnimation(1.0f, 1.0f, 1.0f, 1.0f,
            Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 1.0f);
    @BindView(R.id.iv_launch)
    ImageView ivLaunch;

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        goLoginActivity();
    }

    /**
     * 进入登录页
     */
    private void goLoginActivity() {
        getNotification();
//        String string = Settings.Secure.getString(getContentResolver(), "enabled_notification_listeners");
//        if (!string.contains(getPackageName())) {
//            Toast.makeText(context,"请赋予应用获取系统的通知权，否则无法使用",Toast.LENGTH_SHORT).show();
//            startActivity(new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS")
//                    .putExtra("app_package", context.getPackageName())
//            .putExtra("app_uid", context.getApplicationInfo().uid));;
//        }else {
//            Intent intent = new Intent();
//            intent.setClass(LaunchActivity.this, HomeNewActivity.class);
//            startActivity(intent);
//            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
//            finish();
//        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    private void  getNotification(){
        boolean enabled = isNotificationEnabled(LaunchActivity.this);

        if (!enabled) {
            /**
             * 跳到通知栏设置界面
             * @param context
             */
            Intent localIntent = new Intent();
            //直接跳转到应用通知设置的代码：
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                localIntent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
                localIntent.putExtra("app_package", context.getPackageName());
                localIntent.putExtra("app_uid", context.getApplicationInfo().uid);
            } else if (android.os.Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
                localIntent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                localIntent.addCategory(Intent.CATEGORY_DEFAULT);
                localIntent.setData(Uri.parse("package:" + context.getPackageName()));
            } else {
                //4.4以下没有从app跳转到应用通知设置页面的Action，可考虑跳转到应用详情页面,
                localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                if (Build.VERSION.SDK_INT >= 9) {
                    localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                    localIntent.setData(Uri.fromParts("package", context.getPackageName(), null));
                } else if (Build.VERSION.SDK_INT <= 8) {
                    localIntent.setAction(Intent.ACTION_VIEW);
                    localIntent.setClassName("com.android.settings", "com.android.setting.InstalledAppDetails");
                    localIntent.putExtra("com.android.settings.ApplicationPkgName", context.getPackageName());
                }
            }
            context.startActivity(localIntent);
        }
        else {
            Intent intent = new Intent();
            if (StorageCustomerInfo02Util.getBooleanInfo("automaticLogin",context,false)){
                intent.setClass(LaunchActivity.this, HomeNewActivity.class);
            }else {
                intent.setClass(LaunchActivity.this, LoginActivity.class);
            }
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
            finish();
        }
    }

    /**
     * 获取通知权限
     * @param context
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private boolean isNotificationEnabled(Context context) {

        String CHECK_OP_NO_THROW = "checkOpNoThrow";
        String OP_POST_NOTIFICATION = "OP_POST_NOTIFICATION";

        AppOpsManager mAppOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
        ApplicationInfo appInfo = context.getApplicationInfo();
        String pkg = context.getApplicationContext().getPackageName();
        int uid = appInfo.uid;

        Class appOpsClass = null;
        try {
            appOpsClass = Class.forName(AppOpsManager.class.getName());
            Method checkOpNoThrowMethod = appOpsClass.getMethod(CHECK_OP_NO_THROW, Integer.TYPE, Integer.TYPE,
                    String.class);
            Field opPostNotificationValue = appOpsClass.getDeclaredField(OP_POST_NOTIFICATION);

            int value = (Integer) opPostNotificationValue.get(Integer.class);
            return ((Integer) checkOpNoThrowMethod.invoke(mAppOps, value, uid, pkg) == AppOpsManager.MODE_ALLOWED);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PermissionUtil.ALL:
                int i = 0;
                for (i = 0; i < grantResults.length; i++) {
                        boolean isTip = ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i]);
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            if (isTip) {
                                //表明用户没有彻底禁止弹出权限请求
                                Toast.makeText(context, "请赋予应用该权限", Toast.LENGTH_SHORT);
                                PermissionUtil.ALL(context);
                            } else {//表明用户已经彻底禁止弹出权限请求
                                if (context != null && !context.isFinishing()) {
                                    new AlertDialog.Builder(context)
                                            .setMessage("请赋予应用权限,否则可能会导致未知错误,赋予权限之后,请重新打开应用！")
                                            .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    openSetting(context);
                                                }
                                            }).show();
                                }
                            }
                            return;
                        }
                    }
                int length = permissions.length;
                if (i == length) {
                    initData();
                }
                break;
            default:
                break;
        }
    }

    /**
     * 打开设置
     */
    public void openSetting(Context mContext) {
        Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.setData(Uri.parse("package:" + mContext.getPackageName()));
        mContext.startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        if((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
            return;
        }
    }

    @Override
    public int initLayout() {
        return R.layout.activity_launch;
    }

    @Override
    public void initData() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            showImage();
        } else {
            if (PermissionUtil.ALL(context)) {
             showImage();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        showImage();
    }

    private void showImage() {
        ivLaunch.setImageResource(R.drawable.launch);
        animation.setDuration(600);
        ivLaunch.startAnimation(animation);
        animation.setAnimationListener(this);
    }
}