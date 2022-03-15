package com.bookkeeping.myapplication.base;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import androidx.fragment.app.FragmentActivity;
import androidx.core.content.ContextCompat;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import com.bookkeeping.myapplication.R;
import com.bookkeeping.myapplication.view.HintDialog;

import butterknife.ButterKnife;

public abstract class BaseActivity extends FragmentActivity {
    public Activity context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        context = this;
        setContentView(initLayout());
        ButterKnife.bind(this);
        initData();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setWindowStatusBarColor(this, R.color.transparent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public abstract int initLayout();

    public abstract void initData();

    public void hint(String content) {
        HintDialog hintDialog = HintDialog.getInstance(content);
        getSupportFragmentManager().beginTransaction().add(hintDialog, "tag").commitAllowingStateLoss();
    }
    public void setWindowStatusBarColor(Activity activity, int colorResId) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                Window window = activity.getWindow();
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(ContextCompat.getColor(activity, colorResId));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
