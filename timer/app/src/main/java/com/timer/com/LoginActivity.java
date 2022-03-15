package com.timer.com;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.timer.com.bean.ResultModel;
import com.timer.com.util.OkClient;
import com.timer.com.util.StorageCustomerInfoUtil;
import com.timer.com.util.StringUtil;

import java.util.TreeMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author : qiuyiyang
 * @date : 2022/2/11  16:24
 * @desc :
 */
public class LoginActivity extends BaseActivity {
    @BindView(R.id.et_school)
    EditText etSchool;
    @BindView(R.id.et_user)
    EditText etUser;
    @BindView(R.id.et_pass)
    EditText etPass;
    @BindView(R.id.tv_login)
    TextView tvLogin;

    private String school,user,pass;
    @Override
    public int initLayout() {
        return R.layout.act_login;
    }

    @Override
    public void initData() {
        school=StorageCustomerInfoUtil.getInfo("school",context);
        user=StorageCustomerInfoUtil.getInfo("user",context);
        pass=StorageCustomerInfoUtil.getInfo("pass",context);
        if (!StringUtil.isEmpty(school)&&!StringUtil.isEmpty(user)&&!StringUtil.isEmpty(pass)){
            etSchool.setText(school);
            etUser.setText(user);
            etPass.setText(pass);
            login();
        }
    }

    private void login() {
        HttpParams httpParams=new HttpParams();
        httpParams.put("school", school);
        httpParams.put("name", user);
        httpParams.put("passWord", pass);
        OkClient.getInstance().post("/api/login", httpParams, new OkClient.EntityCallBack<ResultModel>(context, ResultModel.class) {
            @Override
            public void onStart(Request<ResultModel, ? extends Request> request) {
                super.onStart(request);
                request.headers("authorization", "Bearer anonymous.anonymous");
            }

            @Override
            public void onError(Response<ResultModel> response) {
                super.onError(response);
            }

            @Override
            public void onSuccess(Response<ResultModel> response) {
                super.onSuccess(response);
                if (response.body()!=null) {
                    ResultModel resultModel = response.body();
                    if (resultModel.isStatus()&&resultModel.getData().getRetCode()== 0) {
                        JSONObject jsonObject = JSONObject.parseObject(resultModel.getData().getData(), JSONObject.class);
                        Log.i("TAG", "onSuccess: "+jsonObject.toJSONString());
                        Log.i("TAG", "onSuccess: "+jsonObject.getString("data"));
                        StorageCustomerInfoUtil.putInfo(context, "token", jsonObject.getString("data"));
                        StorageCustomerInfoUtil.putInfo(context, "userId", jsonObject.getString("userId"));
                        startActivity(new Intent(context,TranscodeActivity.class));
                        finish();
                    }else {
                        Toast.makeText(context,resultModel.getData().getMessage(),Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.tv_login)
    public void onViewClicked(View view) {
        hideKeyboard(view);
        school=etSchool.getText().toString().trim();
        user = etUser.getText().toString().trim();
        pass = etPass.getText().toString().trim();
        if (StringUtil.isEmpty(school)) {
            Toast.makeText(context, "请输入学校", Toast.LENGTH_LONG);
            return;
        }
        if (StringUtil.isEmpty(user)) {
            Toast.makeText(context, "请输入账号",  Toast.LENGTH_LONG);
            return;
        }
        if (StringUtil.isEmpty(pass)) {
            Toast.makeText(context, "请输入密码",  Toast.LENGTH_LONG);
            return;
        }
        StorageCustomerInfoUtil.putInfo(context,"school",school);
        StorageCustomerInfoUtil.putInfo(context,"user",user);
        StorageCustomerInfoUtil.putInfo(context,"pass",pass);
        login();
    }

    private static void hideKeyboard(View view){
        InputMethodManager imm = (InputMethodManager) view.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
    }
}
