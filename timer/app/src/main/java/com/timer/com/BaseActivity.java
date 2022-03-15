package com.timer.com;

import android.app.Activity;
import android.app.Dialog;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.timer.com.util.ViewUtils;

import butterknife.ButterKnife;

public abstract class BaseActivity extends FragmentActivity {
    public Dialog loadingDialog;
    public Dialog loadingDialogCanCancel;
    public Activity context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        context = this;
        loadingDialog = ViewUtils.createLoadingDialog(context,"视频加载中", false);
        loadingDialogCanCancel=ViewUtils.createLoadingDialog(context,"视频加载中",true);
        //设置布局
        setContentView(initLayout());
        ButterKnife.bind(this);
        //设置数据
        initData();
        //全局设置竖屏
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//        ActivityManager.getInstance().add(this);
        setWindowStatusBarColor(this, R.color.transparent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
        if (loadingDialogCanCancel != null && loadingDialogCanCancel.isShowing()) {
            loadingDialogCanCancel.dismiss();
        }
    }

    /**
     * 设置布局
     *
     * @return
     */
    public abstract int initLayout();

    /**
     * 设置数据
     */
    public abstract void initData();

    public void setWindowStatusBarColor(Activity activity, int colorResId) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                Window window = activity.getWindow();
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(ContextCompat.getColor(activity, colorResId));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
