package com.bookkeeping.myapplication.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bookkeeping.myapplication.MyApplication;
import com.bookkeeping.myapplication.R;
import com.bookkeeping.myapplication.base.BaseActivity;
import com.bookkeeping.myapplication.model.TypeModel;
import com.bookkeeping.myapplication.util.CommonUtils;
import com.bookkeeping.myapplication.util.StorageCustomerInfo02Util;
import com.bookkeeping.myapplication.util.StringUtil;
import com.bookkeeping.myapplication.util.UserDao;
import com.lzy.okgo.cookie.store.SPCookieStore;

import java.math.BigDecimal;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author : qiuyiyang
 * @date : 2021/1/4  15:14
 * @desc :
 */
public class HomeNewActivity extends BaseActivity {
    @BindView(R.id.tv_main)
    TextView tvMain;
    @BindView(R.id.tv_bill)
    TextView tvBill;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.tv_mine)
    TextView tvMine;
    @BindView(R.id.cl_container)
    ConstraintLayout clContainer;
    @BindView(R.id.drawer)
    DrawerLayout drawer;
    @BindView(R.id.sw_automatic)
    Switch swAutomatic;
    @BindView(R.id.fl_content)
    FrameLayout flContent;
    @BindView(R.id.ll_main)
    LinearLayout llMain;
    @BindView(R.id.ll_bill)
    LinearLayout llBill;
    @BindView(R.id.ll_type)
    LinearLayout llType;
    @BindView(R.id.ll_mine)
    LinearLayout llMine;
    @BindView(R.id.ll_tab)
    LinearLayout llTab;
    @BindView(R.id.iv_avatar)
    ImageView ivAvatar;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.iv_level)
    ImageView ivLevel;
    @BindView(R.id.sw_automatic_1)
    Switch swAutomatic1;
    @BindView(R.id.ll_switch_1)
    LinearLayout llSwitch1;
    @BindView(R.id.ll_switch)
    LinearLayout llSwitch;
    @BindView(R.id.ll_notice)
    LinearLayout llNotice;
    @BindView(R.id.ll_starting)
    LinearLayout llStarting;
    @BindView(R.id.ll_database)
    LinearLayout llDatabase;
    @BindView(R.id.tv_exit)
    TextView tvExit;
    private FragmentManager fm;
    private MainFragment mainFragment = null;
    private ListFragment listFragment = null;
//    private GameFragment gameFragment = null;
    private MyFragment myFragment = null;
    private VideoFragment videoFragment = null;
    private int defaultColor = 0xFF000000;
    private long firstime;
    private boolean automatic,notification;
    private UserDao userDao;

    @Override
    public int initLayout() {
        return R.layout.activity_home;
    }

    @Override
    public void initData() {
        fm = getSupportFragmentManager();
        showFrag(2);
        automatic = StorageCustomerInfo02Util.getBooleanInfo("automatic", context, true);
        swAutomatic.setChecked(automatic);
        swAutomatic.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                StorageCustomerInfo02Util.putInfo(context, "automatic", isChecked);
                automatic = isChecked;
                Toast.makeText(context, "互转录入已" + (isChecked ? "开启" : "关闭"), Toast.LENGTH_SHORT).show();
            }
        });
        notification = StorageCustomerInfo02Util.getBooleanInfo("notification", context, true);
        swAutomatic1.setChecked(notification);
        swAutomatic1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                StorageCustomerInfo02Util.putInfo(context, "notification", isChecked);
                notification = isChecked;
                Toast.makeText(context, "自动获取已" + (isChecked ? "开启" : "关闭"), Toast.LENGTH_SHORT).show();
            }
        });

        drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
                clContainer.setTranslationX(drawerView.getMeasuredWidth() * (slideOffset));
            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
        initType();
    }

    private void initType(){
        userDao=new UserDao(MyApplication.applicationContext);
        if (userDao.getCount("type")==0) {
            TypeModel typeModelWX = new TypeModel();
            typeModelWX.setBalance(new BigDecimal("0"));
            typeModelWX.setId("微信");
            TypeModel typeModelAliPay = new TypeModel();
            typeModelAliPay.setBalance(new BigDecimal("0"));
            typeModelAliPay.setId("支付宝");
            userDao.add(typeModelAliPay);
            userDao.add(typeModelWX);
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            /** 设置双击退出 */
            long secondtime = System.currentTimeMillis();
            if (secondtime - firstime > 3000) {
                Toast.makeText(this, "再按一次返回键退出", Toast.LENGTH_SHORT).show();
                firstime = System.currentTimeMillis();
                return true;
            } else {
                System.exit(0);
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private void showExitDialog(final Activity activity) {
        final Button confirmBt, cancleBt;
        final Dialog mydialog = new Dialog(activity, R.style.MyProgressDialog);
        mydialog.setContentView(R.layout.dialog_exit);
        mydialog.setCanceledOnTouchOutside(false);
        confirmBt = (Button) mydialog.findViewById(R.id.bt_cancelPlan);
        cancleBt = (Button) mydialog.findViewById(R.id.bt_suspendCancel);
        confirmBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CommonUtils.isFastDoubleClick2(confirmBt.getId())) {
                    return;
                }
                mydialog.dismiss();
                StorageCustomerInfo02Util.putInfo(context,"automaticLogin",false);
                StorageCustomerInfo02Util.removeKey("DedeUserID", context);
                new SPCookieStore(context).removeAllCookie();
                startActivity(new Intent(context,LoginActivity.class));
                finish();
            }
        });
        cancleBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mydialog.dismiss();
            }

        });
        mydialog.show();
    }

    @OnClick({R.id.ll_switch, R.id.ll_notice, R.id.ll_starting, R.id.ll_database,R.id.tv_exit
            , R.id.ll_main, R.id.ll_bill, R.id.ll_type, R.id.ll_mine})
    public void onViewClicked(View view) {
        drawer.closeDrawers();
        switch (view.getId()) {
            case R.id.ll_switch:
                // TODO:自动录入开关
                swAutomatic.performClick();
                break;
            case R.id.ll_notice:
                // TODO:获取通知权
                startActivity(new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"));
                break;
            case R.id.ll_starting:
                // TODO:设为自启动
                CommonUtils.jumpStartInterface(context);
                break;
            case R.id.ll_database:
                // TODO:数据库位置
                MyApplication.getUserDao().openAssignFolder(context);
                break;
            case R.id.tv_exit:
                // TODO: 2020/5/4 退出登录
                showExitDialog(context);
                break;
            case R.id.ll_main:
                showFrag(1);
                break;
            case R.id.ll_bill:
                showFrag(2);
                break;
            case R.id.ll_type:
                showFrag(3);
                break;
            case R.id.ll_mine:
                showFrag(4);
                break;
        }
    }

    private void showFrag(int i) {
        FragmentTransaction transaction = fm.beginTransaction();
        tvMine.setTextColor(ContextCompat.getColor(context, R.color.text_color));
        tvBill.setTextColor(ContextCompat.getColor(context, R.color.text_color));
        tvType.setTextColor(ContextCompat.getColor(context, R.color.text_color));
        tvMain.setTextColor(ContextCompat.getColor(context, R.color.text_color));
        if (mainFragment != null) {
            transaction.hide(mainFragment);
        }
//        if (gameFragment != null) {
//            transaction.hide(gameFragment);
//        }
        if (videoFragment != null) {
            transaction.hide(videoFragment);
        }

        if (myFragment != null) {
            transaction.hide(myFragment);
        }
        if (listFragment != null) {
            transaction.hide(listFragment);
        }
        switch (i) {
            case 1:
                if (mainFragment == null) {
                    mainFragment = new MainFragment();
                    transaction.add(R.id.fl_content, mainFragment);
                } else {
                    transaction.show(mainFragment);
                }
                tvMain.setTextColor(defaultColor);
                break;
            case 2:
                if (listFragment == null) {
                    listFragment = new ListFragment();
                    transaction.add(R.id.fl_content, listFragment);
                } else {
                    transaction.show(listFragment);
                }
                tvBill.setTextColor(defaultColor);
                break;
            case 3:
                if (videoFragment == null) {
                    videoFragment = new VideoFragment();
                    transaction.add(R.id.fl_content, videoFragment);
                } else {
                    transaction.show(videoFragment);
                }
                tvType.setTextColor(defaultColor);
                break;
            case 4:
                if (myFragment == null) {
                    myFragment =new MyFragment();
                    transaction.add(R.id.fl_content, myFragment);
                } else {
                    transaction.show(myFragment);
                }
                tvMine.setTextColor(defaultColor);
                break;
            default:
                break;
        }
        transaction.commit();
    }

    @SuppressLint("WrongConstant")
    public void open() {
        drawer.openDrawer(Gravity.START);
    }

    public void show(int i) {
        showFrag(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
