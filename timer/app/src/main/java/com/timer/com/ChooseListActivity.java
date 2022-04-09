package com.timer.com;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.timer.com.bean.ResultModel;
import com.timer.com.bean.SportModel;
import com.timer.com.util.OkClient;
import com.timer.com.util.StorageCustomerInfoUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author : qiuyiyang
 * @date : 2022/2/4  2:31
 * @desc :
 */
public class ChooseListActivity extends BaseActivity {
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

    private ChooseListAdapter localListAdapter;
    private List<String> mList1 = new ArrayList<>();
    private List<String> mList2 = new ArrayList<>();
    private List<String> mList3 = new ArrayList<>();
    private List<SportModel> mSportModelList = new ArrayList<>();
    private int times=1;
    private String id;
    private int roadNum=0;
    private long startTimes;

    @Override
    public int initLayout() {
        return R.layout.act_local_list;
    }

    @Override
    public void initData() {
        tvTitle.setText("选择比赛");
        tvRight.setVisibility(View.VISIBLE);
        tvRight2.setVisibility(View.VISIBLE);
        localListAdapter = new ChooseListAdapter(mList1);
        rvList.setLayoutManager(new LinearLayoutManager(context));
        localListAdapter.bindToRecyclerView(rvList);
        localListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (times==1) {
                    loadData2(mList1.get(position));
                }else if (times==2){
                    id=mSportModelList.get(position).getId();
                    startTimes=mSportModelList.get(position).getValidityBegin();
                    roadNum=mSportModelList.get(position).getRoadNum();
                    mList3.clear();
                    for (int i=0;i<mSportModelList.get(position).getGroupNum();i++){
                        mList3.add((i+1)+"");
                    }
                    localListAdapter.setNewData(mList3);
                    times=3;
                }else {
                    setResult(RESULT_OK,new Intent().putExtra("id",id)
                            .putExtra("startTime",startTimes)
                            .putExtra("group",position+1)
                    .putExtra("road",roadNum));
                    finish();
                }
            }
        });
        loadData1();
    }

    private void loadData1() {
        loadingDialog.show();
        HttpParams map=new HttpParams();
        map.put("school", StorageCustomerInfoUtil.getInfo("school",context));
        map.put("authorization",StorageCustomerInfoUtil.getInfo("token",context));
        OkClient.getInstance().post("/api/getSportTypeList", map, new OkClient.EntityCallBack<ResultModel>(context, ResultModel.class) {

            @Override
            public void onError(Response<ResultModel> response) {
                super.onError(response);
                loadingDialog.dismiss();
            }

            @Override
            public void onSuccess(Response<ResultModel> response) {
                super.onSuccess(response);
                loadingDialog.dismiss();
                if (response.body()!=null) {
                    ResultModel resultModel = response.body();
                    if (resultModel.getData().getRetCode() == 0) {
                        tvLeft.setVisibility(View.GONE);
                        mList1 = JSONArray.parseArray(resultModel.getData().getData(), String.class);
                        localListAdapter.setNewData(mList1);
                        times=1;
                    }
                }
            }
        });
    }


    private void loadData2(String type) {
        HttpParams map=new HttpParams();
        map.put("type", type);
        OkClient.getInstance().post("/api/getSportNameList", map, new OkClient.EntityCallBack<ResultModel>(context, ResultModel.class) {

            @Override
            public void onError(Response<ResultModel> response) {
                super.onError(response);
            }

            @Override
            public void onSuccess(Response<ResultModel> response) {
                super.onSuccess(response);
                if (response.body()!=null) {
                    ResultModel resultModel = response.body();
                    if (resultModel.getData().getRetCode() == 0) {
                        mList2.clear();
                        tvLeft.setVisibility(View.VISIBLE);
                        tvRight.setVisibility(View.VISIBLE);
                        mSportModelList = JSONArray.parseArray(resultModel.getData().getData(), SportModel.class);
                        for (int i = 0; i < mSportModelList.size(); i++) {
                            mList2.add(mSportModelList.get(i).getTitle());
                        }
                        localListAdapter.setNewData(mList2);
                        times=2;
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

    @Override
    protected void onResume() {
        super.onResume();
        loadData1();
    }

    @OnClick({R.id.tv_left, R.id.tv_right, R.id.tv_right2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_left:
                if (times==3){

                }else if ((times==2)){
                    loadData1();
                }else {
                    finish();
                }
                break;
            case R.id.tv_right:
                startActivity(new Intent(context,NewActivity.class));
                break;
            case R.id.tv_right2:
                finish();
                break;
        }
    }
}
