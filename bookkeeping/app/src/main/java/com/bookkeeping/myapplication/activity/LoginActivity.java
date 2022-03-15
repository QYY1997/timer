package com.bookkeeping.myapplication.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.bookkeeping.myapplication.R;
import com.bookkeeping.myapplication.base.BaseActivity;
import com.bookkeeping.myapplication.model.bilibili.GT3ResultModel;
import com.bookkeeping.myapplication.model.bilibili.KeyModel;
import com.bookkeeping.myapplication.model.bilibili.LoginModel;
import com.bookkeeping.myapplication.model.bilibili.ResponseModel;
import com.bookkeeping.myapplication.model.bilibili.VerificationCodeModel;
import com.bookkeeping.myapplication.util.CommonUtils;
import com.bookkeeping.myapplication.util.StorageCustomerInfo02Util;
import com.bookkeeping.myapplication.util.StringUtil;
import com.bookkeeping.myapplication.util.okgo.OkClient;
import com.geetest.sdk.GT3ConfigBean;
import com.geetest.sdk.GT3ErrorBean;
import com.geetest.sdk.GT3GeetestUtils;
import com.geetest.sdk.GT3Listener;
import com.geetest.sdk.views.GT3GeetestButton;
import com.lzy.okgo.cookie.store.SPCookieStore;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;

import org.json.JSONException;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Cookie;

/**
 * @author : qiuyiyang
 * @date : 2021/7/14  17:49
 * @desc :
 */
public class LoginActivity extends BaseActivity {

    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_pass)
    EditText etPass;
    @BindView(R.id.iv_txt_clear)
    ImageView ivTxtClear;
    @BindView(R.id.iv_pass_show)
    ImageView ivPassShow;
    @BindView(R.id.cb_login)
    CheckBox cbLogin;

    private GT3ConfigBean gt3ConfigBean;
    private GT3GeetestUtils gt3GeetestUtils;
    private boolean isPassclose;
    private boolean login=true;
    private VerificationCodeModel vcModel;
    private org.json.JSONObject jsonObject = new org.json.JSONObject();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (gt3GeetestUtils != null) {
            gt3GeetestUtils.destory();
        }
    }

    @Override
    public int initLayout() {
        return R.layout.activity_new_login;
    }

    @Override
    public void initData() {
        if (!StringUtil.isEmpty(StorageCustomerInfo02Util.getInfo("phone",context))){
            etPhone.setText(StorageCustomerInfo02Util.getInfo("phone",context));
        }
        if (!StringUtil.isEmpty(StorageCustomerInfo02Util.getInfo("pass",context))){
            etPass.setText(StorageCustomerInfo02Util.getInfo("pass",context));
        }
        loadData();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // TODO 横竖屏切换
        if (gt3GeetestUtils != null) {
            gt3GeetestUtils.changeDialogLayout();
        }
    }

    @SuppressLint("WrongConstant")
    private void initGT3() {
        gt3GeetestUtils = new GT3GeetestUtils(context);
        // 配置bean文件，也可在oncreate初始化
        gt3ConfigBean = new GT3ConfigBean();
// 设置验证模式，1：bind，2：unbind
        gt3ConfigBean.setPattern(1);
// 设置点击灰色区域是否消失，默认不消息
        gt3ConfigBean.setCanceledOnTouchOutside(false);
// 设置语言，如果为null则使用系统默认语言
        gt3ConfigBean.setLang(null);
// 设置加载webview超时时间，单位毫秒，默认10000，仅且webview加载静态文件超时，不包括之前的http请求
        gt3ConfigBean.setTimeout(10000);
// 设置webview请求超时(用户点选或滑动完成，前端请求后端接口)，单位毫秒，默认10000
        gt3ConfigBean.setWebviewTimeout(10000);
// 设置回调监听
        gt3ConfigBean.setListener(new GT3Listener() {
            @Override
            public void onReceiveCaptchaCode(int i) {

            }

            @Override
            public void onDialogReady(String s) {
                super.onDialogReady(s);
            }

            @Override
            public void onDialogResult(String s) {
                super.onDialogResult(s);
                gt3GeetestUtils.dismissGeetestDialog();
                GT3ResultModel resultModel = JSONObject.parseObject(s,GT3ResultModel.class);
                loadKey(resultModel);
                gt3GeetestUtils.destory();
                gt3ConfigBean=null;
            }

            @Override
            public void onStatistics(String s) {

            }

            @Override
            public void onClosed(int i) {
            }

            @Override
            public void onSuccess(String s) {
                gt3GeetestUtils.dismissGeetestDialog();
            }

            @Override
            public void onFailed(GT3ErrorBean gt3ErrorBean) {
                gt3GeetestUtils.dismissGeetestDialog();
            }

            @Override
            public void onButtonClick() {
                gt3ConfigBean.setApi1Json(jsonObject);
                // 继续验证
                gt3GeetestUtils.getGeetest();
            }
        });
        gt3GeetestUtils.init(gt3ConfigBean);

        gt3GeetestUtils.startCustomFlow();
    }

    private void loadData() {
        HttpParams httpParams = new HttpParams();
        httpParams.put("source", "main_web");
        OkClient.getInstance().get("https://passport.bilibili.com/x/passport-login/captcha", httpParams, new OkClient.EntityCallBack<ResponseModel>(context, ResponseModel.class) {
            @Override
            public void onError(Response<ResponseModel> response) {
                loadData();
                super.onError(response);
            }

            @Override
            public void onSuccess(Response<ResponseModel> response) {
                super.onSuccess(response);
                ResponseModel model = response.body();
                if (model == null) {
                    loadData();
                    return;
                }
                if (model.getCode() == 0) {
                    vcModel = JSONObject.parseObject(model.getData(), VerificationCodeModel.class);
                    try {
                        jsonObject.put("success", "1");
                        jsonObject.put("challenge", vcModel.getGeetest().getChallenge());
                        jsonObject.put("gt", vcModel.getGeetest().getGt());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    loadData();
                }
            }
        });
    }

    private void loadKey(GT3ResultModel resultModel) {
        HttpParams httpParams = new HttpParams();
        OkClient.getInstance().get("https://passport.bilibili.com/x/passport-login/web/key", httpParams, new OkClient.EntityCallBack<ResponseModel>(context, ResponseModel.class) {
            @Override
            public void onError(Response<ResponseModel> response) {
                super.onError(response);
            }

            @Override
            public void onSuccess(Response<ResponseModel> response) {
                super.onSuccess(response);
                ResponseModel model = response.body();
                if (model == null) {
                    return;
                }
                if (model.getCode() == 0) {
                    KeyModel keyModel = JSONObject.parseObject(model.getData(), KeyModel.class);
                    login(etPhone.getText().toString(), CommonUtils.rsaEncode(keyModel.getHash()+etPass.getText().toString(), keyModel.getKey()), resultModel);
                }
            }
        });
    }

    private void login(String phone, String pass, GT3ResultModel resultModel) {
        HttpParams httpParams = new HttpParams();
        httpParams.put("source", "main_web");
        httpParams.put("username", phone);
        httpParams.put("password", pass);
        httpParams.put("keep", true);
        httpParams.put("token", vcModel.getToken());
        httpParams.put("challenge", resultModel.getGeetest_challenge());
        httpParams.put("validate", resultModel.getGeetest_validate());
        httpParams.put("seccode", resultModel.getGeetest_seccode());
        OkClient.getInstance().post("https://passport.bilibili.com/x/passport-login/web/login", httpParams, true,new OkClient.EntityCallBack<ResponseModel>(context, ResponseModel.class) {
            @Override
            public void onError(Response<ResponseModel> response) {
                super.onError(response);
                loadData();
            }

            @Override
            public void onSuccess(Response<ResponseModel> response) {
                super.onSuccess(response);
                ResponseModel model = response.body();
                if (model == null) {
                    loadData();
                    return;
                }
                if (model.getCode() == 0) {
                    LoginModel loginModel = JSONObject.parseObject(model.getData(), LoginModel.class);
                    if (loginModel.getStatus() == 0) {
                        StorageCustomerInfo02Util.putInfo(context, "automaticLogin", login);
                        List<Cookie> list=new SPCookieStore(context).getAllCookie();
                        String cookies="";
                        for(Cookie cookie :list) {
                            StorageCustomerInfo02Util.putInfo(context,cookie.name(),cookie.value());
                            cookies+=cookie.name()+"="+cookie.value()+";";
                        }
                        StorageCustomerInfo02Util.putInfo(context,"cookies",cookies);
                        startActivity(new Intent(context, HomeNewActivity.class));
                    }
                }
                else {
                    Toast.makeText(context,model.getMessage(),Toast.LENGTH_LONG).show();
                    loadData();
                }
            }
        });
    }

    @SuppressLint("WrongConstant")
    @OnClick({R.id.iv_txt_clear, R.id.iv_pass_show, R.id.tv_login, R.id.cb_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_txt_clear:
                etPhone.setText("");
                break;
            case R.id.iv_pass_show:
                String password1 = etPass.getText().toString().trim();
                if (StringUtil.isEmpty(password1)) {
                    return;
                }
                if (isPassclose) {
                    isPassclose = false;
                    ivPassShow.setImageResource(R.drawable.pass_show);
                    etPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    isPassclose = true;
                    ivPassShow.setImageResource(R.drawable.pass_close);
                    etPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                etPass.setSelection(password1.length());
                break;
            case R.id.tv_login:
                if (!StringUtil.isEmpty(etPhone.getText().toString())&&!StringUtil.isEmpty(etPass.getText().toString())) {
                    initGT3();
                    StorageCustomerInfo02Util.putInfo(context,"phone",etPhone.getText().toString());
                    StorageCustomerInfo02Util.putInfo(context,"pass",etPass.getText().toString());
                }
                else {
                    if (StringUtil.isEmpty(etPhone.getText().toString())) {
                        Toast.makeText(context, "请输入手机号",500).show();
                    }
                    else if (StringUtil.isEmpty(etPhone.getText().toString())){
                        Toast.makeText(context, "请输入密码",500).show();
                    }
                }
                break;
            case R.id.cb_login:
                login=cbLogin.isChecked();
                break;
        }
    }
}
