package com.bookkeeping.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONObject;
import com.bookkeeping.myapplication.R;
import com.bookkeeping.myapplication.adapter.AttentionListAdapter;
import com.bookkeeping.myapplication.base.BaseActivity;
import com.bookkeeping.myapplication.model.bilibili.CardModel;
import com.bookkeeping.myapplication.model.bilibili.DynamicListModel;
import com.bookkeeping.myapplication.model.bilibili.PersonalListModel;
import com.bookkeeping.myapplication.model.bilibili.PersonalModel;
import com.bookkeeping.myapplication.model.bilibili.ResponseModel;
import com.bookkeeping.myapplication.model.bilibili.UserInfoModel;
import com.bookkeeping.myapplication.util.okgo.OkClient;
import com.bookkeeping.myapplication.view.HintDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author : qiuyiyang
 * @date : 2021/8/10  16:38
 * @desc :
 */
public class AttentionListActivity extends BaseActivity {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rv_list)
    RecyclerView rvList;

    private List<PersonalModel> attentionList = new ArrayList<>();
    private AttentionListAdapter attentionListAdapter;
    private int pageIndex = 1, count;
    private String uid;

    @Override
    public int initLayout() {
        return R.layout.act_recyclerview;
    }

    @Override
    public void initData() {
        uid=getIntent().getStringExtra("uid");
        tvTitle.setText("关注列表");
        rvList.setLayoutManager(new LinearLayoutManager(context));
        attentionListAdapter = new AttentionListAdapter(attentionList);
        attentionListAdapter.bindToRecyclerView(rvList);
        attentionListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(context, PersonalDynamicActivity.class).putExtra("uid", attentionList.get(position).getMid()));
            }
        });
        attentionListAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                if (pageIndex<count) {
                    pageIndex++;
                    loadData();
                }
                else {
                    attentionListAdapter.loadMoreEnd();
                }
            }
        },rvList);
    }

    @Override
    protected void onResume() {
        super.onResume();
        pageIndex=1;
        loadData();
    }

    private void loadData() {
        HttpParams httpParams = new HttpParams();
        httpParams.put("vmid", uid);
        httpParams.put("pn", pageIndex);
        httpParams.put("ps", "20");
        httpParams.put("order", "desc");
        httpParams.put("order_type", "attention");
        httpParams.put("jsonp", "jsonp");
        OkClient.getInstance().get("https://api.bilibili.com/x/relation/followings", httpParams, new OkClient.EntityCallBack<ResponseModel>(context, ResponseModel.class) {
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
                    PersonalListModel personalListModel = JSONObject.parseObject(model.getData(), PersonalListModel.class);
                    count = new BigDecimal(personalListModel.getTotal() + "").divide(new BigDecimal("20"), 0, BigDecimal.ROUND_UP).intValue();
                    attentionList.addAll(personalListModel.getList());
                    attentionListAdapter.setNewData(attentionList);
                }
                else {
                    attentionList.clear();
                    attentionListAdapter.setNewData(attentionList);
                    hint(model.getMessage());
                }
            }
        });
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }
}
