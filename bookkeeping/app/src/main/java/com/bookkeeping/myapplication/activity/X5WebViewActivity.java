package com.bookkeeping.myapplication.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.bookkeeping.myapplication.R;
import com.bookkeeping.myapplication.base.BaseActivity;
import com.bookkeeping.myapplication.util.CommonUtils;
import com.bookkeeping.myapplication.util.PermissionUtil;
import com.bookkeeping.myapplication.view.X5WebChomeClient;
import com.bookkeeping.myapplication.view.X5WebView;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.tencent.smtt.sdk.DownloadListener;
import com.tencent.smtt.sdk.URLUtil;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author: lilingfei
 * @description:
 * @date: 2019/3/30
 */
public class X5WebViewActivity extends BaseActivity implements X5WebChomeClient.OpenFileChooserCallBack {
    public ValueCallback<Uri[]> mUploadMsgForAndroid5;
    String url;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.progressBar1)
    ProgressBar progressBar1;
    @BindView(R.id.webview)
    X5WebView webview;
    private ValueCallback<Uri> mUploadMsg;
    private String bankId, arcCode;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {

            if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
                if (data != null && requestCode == 100) {
                    ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                        mUploadMsg.onReceiveValue(Uri.fromFile(new File(images.get(0).path)));
                    } else {
                        mUploadMsgForAndroid5.onReceiveValue(new Uri[]{Uri.fromFile(new File(images.get(0).path))});
                    }
                } else {
                    Toast.makeText(context, "没有数据", Toast.LENGTH_SHORT).show();
                }
            }
        }
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
                            Toast.makeText(context, "请赋予应用该权限", Toast.LENGTH_LONG).show();
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
                    Intent imgIntent = new Intent(context, ImageGridActivity.class);
                    startActivityForResult(imgIntent,100);
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

    private void checkMobilePermission(String mobile) {
        // 拨打电话
        if (!PermissionUtil.CALL_PHONE(context)) {
            return;
        }
        showCallServerDialog(mobile);
    }

    private void showCallServerDialog(final String phone) {
        Button confirmBt, cancleBt;
        final Dialog mydialog = new Dialog(context, R.style.MyProgressDialog);
        mydialog.setContentView(R.layout.callserver_dialog);
        mydialog.setCanceledOnTouchOutside(false);
        TextView phonenum = mydialog.findViewById(R.id.phoneNum);
        phonenum.setText(phone);
        TextView title = mydialog.findViewById(R.id.tv_dialogTitle);
        title.setText("拨打电话");
        confirmBt = mydialog.findViewById(R.id.bt_cancelPlan);
        cancleBt = mydialog.findViewById(R.id.bt_suspendCancel);
        confirmBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CommonUtils.isFastDoubleClick(v.getId(),1000)) {
                    return;
                }
                mydialog.dismiss();
                Intent phoneIntent = new Intent(
                        "android.intent.action.CALL", Uri.parse("tel:"
                        + phone));
                startActivity(phoneIntent);
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        bankId = getIntent().getStringExtra("bankId");
        arcCode = getIntent().getStringExtra("arcCode");
    }

    @Override
    public int initLayout() {
        return R.layout.activity_web_view;
    }

    @Override
    public void initData() {
        String title = getIntent().getStringExtra("title");
        url = getIntent().getStringExtra("url");
        tvTitle.setText(title);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CloseSpeaker();
                setResult(RESULT_OK);
                finish();
            }
        });
        WebSettings settings = webview.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setAllowFileAccess(true);
        settings.setAppCacheEnabled(true);
        settings.setSupportZoom(false);
        settings.setUseWideViewPort(false);

        if (Patterns.WEB_URL.matcher(url).matches() || URLUtil.isValidUrl(url)) {
            webview.loadUrl(url);
        }
        else{
            webview.loadDataWithBaseURL(null, url, "text/html", "utf-8", null);
        }
        //设置Web视图
        webview.setWebViewClient(new HelloWebViewClient());
        webview.setWebChromeClient(new X5WebChomeClient(this) {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                // TODO 自动生成的方法存根

                if (newProgress == 100) {
                    progressBar1.setVisibility(View.GONE);//加载完网页进度条消失
                } else {
                    progressBar1.setVisibility(View.VISIBLE);//开始加载网页时显示进度条
                    progressBar1.setProgress(newProgress);//设置进度值
                }

            }
        });
        webview.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String s, String s1, String s2, String s3, long l) {
                openBrowser((FragmentActivity) context, s);
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            CloseSpeaker();
        }
        return super.onKeyDown(keyCode, event);
    }

    public void CloseSpeaker() {

        try {
            AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            int currVolume = audioManager.getStreamVolume(AudioManager.STREAM_VOICE_CALL);
            if (audioManager != null) {
                if (audioManager.isSpeakerphoneOn()) {
                    audioManager.setSpeakerphoneOn(false);
                    audioManager.setStreamVolume(AudioManager.STREAM_VOICE_CALL, currVolume,
                            AudioManager.STREAM_VOICE_CALL);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void openBrowser(FragmentActivity fragmentActivity, String url) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));

        // 注意此处的判断intent.resolveActivity()可以返回显示该Intent的Activity对应的组件名
        // 官方解释 : Name of the component implementing an activity that can display the intent
        if (intent.resolveActivity(fragmentActivity.getPackageManager()) != null) {
            ComponentName componentName = intent.resolveActivity(fragmentActivity.getPackageManager());
            // 打印Log   ComponentName到底是什么
            fragmentActivity.startActivity(Intent.createChooser(intent, "请选择浏览器"));
        } else {
            Toast.makeText(fragmentActivity, "请下载浏览器", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void openFileChooserCallBack(ValueCallback<Uri> uploadMsg, String acceptType) {

    }

    @Override
    public boolean openFileChooserCallBackAndroid5(WebView webView, ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams) {
        return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


    //Web视图
    private class HelloWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.startsWith("tel:")) {
                try {
                    String mobile = url.substring(url.lastIndexOf(":") + 1);
                    checkMobilePermission(mobile);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            } else {
                return super.shouldOverrideUrlLoading(view, url);
            }
        }
    }
}
