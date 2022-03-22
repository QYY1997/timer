package com.bookkeeping.myapplication.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.alibaba.fastjson.JSONObject;
import com.bookkeeping.myapplication.MyApplication;
import com.bookkeeping.myapplication.R;
import com.bookkeeping.myapplication.activity.HomeNewActivity;
import com.bookkeeping.myapplication.model.bilibili.CodeModel;
import com.bookkeeping.myapplication.model.bilibili.GT3ResultModel;
import com.bookkeeping.myapplication.model.bilibili.LoginModel;
import com.bookkeeping.myapplication.model.bilibili.ResponseModel;
import com.bookkeeping.myapplication.model.bilibili.VerificationCodeModel;
import com.bookkeeping.myapplication.util.StorageCustomerInfo02Util;
import com.bookkeeping.myapplication.util.okgo.OkClient;
import com.geetest.sdk.GT3ConfigBean;
import com.geetest.sdk.GT3ErrorBean;
import com.geetest.sdk.GT3GeetestUtils;
import com.geetest.sdk.GT3Listener;
import com.lzy.okgo.cookie.store.SPCookieStore;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;

import org.json.JSONException;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Cookie;

/**
 * @author: qiuyiyang
 * @description:
 * @date: 2019/7/10
 */
public class CodeDialog extends DialogFragment {

    Unbinder unbinder;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.tv_code)
    TextView tvCode;
    @BindView(R.id.tv_next)
    TextView tvNext;
    @BindView(R.id.ll_submit)
    LinearLayout llSubmit;

    private Activity context;
    private String token, id,source;
    private GT3ConfigBean gt3ConfigBean;
    private GT3GeetestUtils gt3GeetestUtils;
    private CodeModel codeModel;
    private org.json.JSONObject jsonObject = new org.json.JSONObject();

    public static CodeDialog getInstance(String token, String id,String source) {
        CodeDialog dialog = new CodeDialog();
        Bundle bundle = new Bundle();
        bundle.putString("token", token);
        bundle.putString("id", id);
        bundle.putString("source", source);
        dialog.setArguments(bundle);
        return dialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.custom_Dialog);
        context = getActivity();
        Bundle bundle = getArguments();
        if (bundle != null) {
            token = getArguments().getString("token");
            id = getArguments().getString("id");
            source = getArguments().getString("source");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
        int screenHeight = dm.heightPixels;
        if (dialog != null && dialog.getWindow() != null) {
            dialog.getWindow().setLayout((int) (screenWidth * 0.7), ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_code, container);
        unbinder = ButterKnife.bind(this, view);
        loadData();
        return view;
    }


    @OnClick({R.id.tv_code, R.id.tv_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_next:
                login();
                break;
            case R.id.tv_code:
                initGT3();
                break;
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
                sendCode(resultModel);
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


    private void login() {
        HttpParams httpParams = new HttpParams();
        httpParams.put("type", "17");
        httpParams.put("code", etCode.getText().toString());
        httpParams.put("tmp_code", token);
        httpParams.put("request_id", id);
        OkClient.getInstance().post("https://api.bilibili.com/x/safecenter/tel/verify", httpParams, true,new OkClient.EntityCallBack<ResponseModel>(context, ResponseModel.class) {
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
                    LoginModel loginModel = JSONObject.parseObject(model.getData(), LoginModel.class);
                    if (loginModel.getStatus() == 0) {
                        getCode(loginModel.getCode());
                    }
                }
                else {
                    Toast.makeText(context,model.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void sendCode(GT3ResultModel resultModel) {
        HttpParams httpParams = new HttpParams();
        httpParams.put("type", "17");
        httpParams.put("tmp_code", token);
        httpParams.put("captcha_key", codeModel.getResult().getKey());
        httpParams.put("captcha_type","5");
        httpParams.put("challenge", resultModel.getGeetest_challenge());
        httpParams.put("validate", resultModel.getGeetest_validate());
        httpParams.put("seccode", resultModel.getGeetest_seccode());
        OkClient.getInstance().post("https://api.bilibili.com/x/safecenter/sms/send", httpParams, true,new OkClient.EntityCallBack<ResponseModel>(context, ResponseModel.class) {
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
                if (model.getCode() != 0) {
                    Toast.makeText(context,model.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void loadData() {
        HttpParams httpParams = new HttpParams();
        httpParams.put("plat", "5");
        OkClient.getInstance().get("https://passport.bilibili.com/web/captcha/combine", httpParams, new OkClient.EntityCallBack<ResponseModel>(context, ResponseModel.class) {
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
                    codeModel = JSONObject.parseObject(model.getData(), CodeModel.class);
                    try {
                        jsonObject.put("success", "1");
                        jsonObject.put("key",  codeModel.getResult().getKey());
                        jsonObject.put("challenge", codeModel.getResult().getChallenge());
                        jsonObject.put("gt", codeModel.getResult().getGt());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void getCode(String code) {
        HttpParams httpParams = new HttpParams();
        httpParams.put("code", code);
        OkClient.getInstance().get("https://passport.bilibili.com/web/sso/exchange_cookie", httpParams,new OkClient.EntityCallBack<ResponseModel>(context, ResponseModel.class) {
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
                    LoginModel loginModel = JSONObject.parseObject(model.getData(), LoginModel.class);
                    if (loginModel.getStatus() == 0) {
                        StorageCustomerInfo02Util.putInfo(context, "automaticLogin", true);
                        String cookStr="";
                        List<String> cookies=response.headers().values("set-cookie");
                       for (int i=0;i<cookies.size();i++){
                            String session = cookies.get(i);
                            if (!TextUtils.isEmpty(session)) {
                                int position = session.indexOf(";");
                                if (position <  session.length() && position >= 0) {
                                    String cook=session.substring(0,position);
                                    if (cook.contains("=")) {
                                        String[] value = cook.split("=");
                                        StorageCustomerInfo02Util.putInfo(context, value[0], value[1]);
                                    }
                                    cookStr += session.substring(0,position)+";";
                                }
                            }
                        }
                        StorageCustomerInfo02Util.putInfo(context,"cookies",cookStr);
                        Log.i("TAG", "onSuccess: "+cookStr);
                        startActivity(new Intent(context, HomeNewActivity.class));
                    }
                }
                else {
                    Toast.makeText(context,model.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
