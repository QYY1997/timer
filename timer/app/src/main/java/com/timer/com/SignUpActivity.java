package com.timer.com;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.timer.com.bean.EnRollModel;
import com.timer.com.bean.ResultModel;
import com.timer.com.util.OkClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author : qiuyiyang
 * @date : 2022/2/14  3:45
 * @desc :
 */
public class SignUpActivity extends BaseActivity {

    @BindView(R.id.tv_left)
    TextView tvLeft;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right2)
    TextView tvRight2;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.tv_num)
    TextView tvNum;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_cls)
    TextView tvCls;
    @BindView(R.id.tv_sign_up_time)
    TextView tvSignUpTime;
    @BindView(R.id.tv_status)
    TextView tvStatus;

    private SignUpListAdapter signUpListAdapter;
    private List<EnRollModel> enRollModelList = new ArrayList<>();

    @Override
    public int initLayout() {
        return R.layout.act_sign_up_list;
    }

    @Override
    public void initData() {
        tvTitle.setText("报名情况");
        signUpListAdapter = new SignUpListAdapter(enRollModelList);
        rvList.setLayoutManager(new LinearLayoutManager(context));
        signUpListAdapter.bindToRecyclerView(rvList);
        tvNum.setText("序号");
        tvName.setText("姓名");
        tvCls.setText("年级");
        tvSignUpTime.setText("报名时间");
        tvStatus.setText("报名情况");
        loadData1();
    }

    private void loadData1() {
        loadingDialog.show();
        HttpParams map = new HttpParams();
        map.put("demandId", getIntent().getStringExtra("demandId"));
        OkClient.getInstance().post("/api/getEnRollList", map, new OkClient.EntityCallBack<ResultModel>(context, ResultModel.class) {

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
                        try {
                            JSONArray jsonArray = new JSONArray(resultModel.getData().getData());
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONArray jsonArray1 = jsonArray.getJSONArray(i);
                                for (int j = 0; j < jsonArray1.length(); j++) {
                                    JSONObject jsonObject = jsonArray1.getJSONObject(j);
                                    Log.i("TAG", "onSuccess: " + jsonObject.toString());
                                    EnRollModel enRollModel = new EnRollModel();
                                    if (jsonObject.has("cls")) {
                                        enRollModel.setCls(jsonObject.getString("cls"));
                                    }
                                    if (jsonObject.has("dateCreated")) {
                                        enRollModel.setDateCreated(jsonObject.getLong("dateCreated"));
                                    }
                                    if (jsonObject.has("id")) {
                                        enRollModel.setId(jsonObject.getString("id"));
                                    }
                                    if (jsonObject.has("road")) {
                                        enRollModel.setRoad(jsonObject.getString("road"));
                                    }
                                    if (jsonObject.has("studentName")) {
                                        enRollModel.setStudentName(jsonObject.getString("studentName"));
                                    }
                                    if (jsonObject.has("editAble")) {
                                        enRollModel.setEditAble(jsonObject.getInt("editAble"));
                                    }
                                    enRollModelList.add(enRollModel);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        signUpListAdapter.setNewData(enRollModelList);
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

    @OnClick({R.id.tv_left})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_left:
                finish();
                break;
        }
    }
}