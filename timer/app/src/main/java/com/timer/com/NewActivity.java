package com.timer.com;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.timer.com.bean.ResultModel;
import com.timer.com.util.OkClient;
import com.timer.com.util.StorageCustomerInfoUtil;
import com.timer.com.util.StringUtil;

import java.util.Locale;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author : qiuyiyang
 * @date : 2022/2/4  2:01
 * @desc :
 */
public class NewActivity extends BaseActivity {
    @BindView(R.id.tv_left)
    TextView tvLeft;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right2)
    TextView tvRight2;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.et_project)
    EditText etProject;
    @BindView(R.id.et_grade)
    EditText etGrade;
    @BindView(R.id.tv_subtract_group)
    TextView tvSubtractGroup;
    @BindView(R.id.et_group)
    EditText etGroup;
    @BindView(R.id.tv_add_group)
    TextView tvAddGroup;
    @BindView(R.id.tv_subtract_number)
    TextView tvSubtractNumber;
    @BindView(R.id.et_number)
    EditText etNumber;
    @BindView(R.id.tv_add_number)
    TextView tvAddNumber;
    @BindView(R.id.tv_save)
    TextView tvSave;
    @BindView(R.id.tv_cancel)
    TextView tvCancel;
    @BindView(R.id.et_race)
    EditText etRace;

    private String raceName;
    private String projectName;
    private String grade;
    private String roadNum;
    private String groupNum;


    @Override
    public int initLayout() {
        return R.layout.act_new;
    }

    @Override
    public void initData() {
        tvTitle.setText("新增项目");
        tvLeft.setText("选择比赛");
    }

    private void submit() {
        loadingDialog.show();
        HttpParams map=new HttpParams();
        map.put("raceName", raceName);
        map.put("projectName",projectName);
        map.put("grade", grade);
        map.put("roadNum", roadNum);
        map.put("groupNum", groupNum);
        map.put("userId", StorageCustomerInfoUtil.getInfo("userId",context));
        OkClient.getInstance().post("/demand/save", map, new OkClient.EntityCallBack<ResultModel>(context, ResultModel.class) {

            @Override
            public void onError(Response<ResultModel> response) {
                super.onError(response);
                loadingDialog.dismiss();
            }

            @Override
            public void onSuccess(Response<ResultModel> response) {
                super.onSuccess(response);
                loadingDialog.dismiss();
                if (response.body() != null) {
                    ResultModel resultModel = response.body();
                    if (resultModel.getData().getRetCode() == 0) {
                        Toast.makeText(context, "新建项目成功", Toast.LENGTH_SHORT).show();
                        finish();
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

    @OnClick({R.id.tv_left, R.id.tv_subtract_group, R.id.tv_add_group, R.id.tv_subtract_number, R.id.tv_add_number, R.id.tv_save, R.id.tv_cancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel:
            case R.id.tv_left:
                finish();
                break;
            case R.id.tv_subtract_group:
                if ((Integer.parseInt(etGroup.getText().toString()) > 1)) {
                    etGroup.setText((Integer.parseInt(etGroup.getText().toString()) - 1) + "");
                }
                break;
            case R.id.tv_add_group:
                etGroup.setText((Integer.parseInt(etGroup.getText().toString()) + 1) + "");
                break;
            case R.id.tv_subtract_number:
                if ((Integer.parseInt(etNumber.getText().toString()) > 1)) {
                    etNumber.setText((Integer.parseInt(etNumber.getText().toString()) - 1) + "");
                }
                break;
            case R.id.tv_add_number:
                etNumber.setText((Integer.parseInt(etNumber.getText().toString()) + 1) + "");
                break;
            case R.id.tv_save:
                hideKeyboard(view);
                raceName = etRace.getText().toString().trim();
                projectName = etProject.getText().toString().trim();
                grade = etGrade.getText().toString().trim();
                groupNum = etGroup.getText().toString().trim();
                roadNum = etNumber.getText().toString().trim();
                if (StringUtil.isEmpty(raceName)) {
                    Toast.makeText(context, "请输入比赛名称", Toast.LENGTH_LONG).show();
                    return;
                }
                if (StringUtil.isEmpty(projectName)) {
                    Toast.makeText(context, "请输入项目名称", Toast.LENGTH_LONG).show();
                    return;
                }
                if (StringUtil.isEmpty(grade)) {
                    Toast.makeText(context, "请输入年级", Toast.LENGTH_LONG).show();
                    return;
                }
                if (Integer.parseInt(groupNum)<1) {
                    Toast.makeText(context, "请输入正确的组数", Toast.LENGTH_LONG).show();
                    return;
                }
                if (Integer.parseInt(roadNum)<1) {
                    Toast.makeText(context, "请输入正确的道数", Toast.LENGTH_LONG).show();
                    return;
                }
                submit();
                break;
        }
    }

    private static void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}
