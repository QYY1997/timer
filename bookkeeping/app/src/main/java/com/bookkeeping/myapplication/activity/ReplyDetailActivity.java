package com.bookkeeping.myapplication.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONObject;
import com.bookkeeping.myapplication.R;
import com.bookkeeping.myapplication.adapter.ReplyAdapter;
import com.bookkeeping.myapplication.base.BaseActivity;
import com.bookkeeping.myapplication.model.bilibili.RepliesModel;
import com.bookkeeping.myapplication.model.bilibili.ReplyModel;
import com.bookkeeping.myapplication.model.bilibili.ResponseModel;
import com.bookkeeping.myapplication.util.CommonUtils;
import com.bookkeeping.myapplication.util.okgo.OkClient;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author : qiuyiyang
 * @date : 2021/7/6  16:47
 * @desc :
 */
public class ReplyDetailActivity extends BaseActivity {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    private int pageIndex = 1, count;
    private ReplyAdapter replyAdapter;
    private List<RepliesModel> repliesList = new ArrayList<>();
    private Dialog loadingDialog;

    @Override
    public int initLayout() {
        return R.layout.act_recyclerview;
    }

    @Override
    public void initData() {
        loadingDialog = CommonUtils.createLoadingDialog(context, "数据加载中……", false);
        tvTitle.setText("评论详情");
        rvList.setLayoutManager(new LinearLayoutManager(context));
        replyAdapter = new ReplyAdapter(repliesList);
        replyAdapter.setType("detail");
        replyAdapter.bindToRecyclerView(rvList);
        loadReplies();
        replyAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                if (count > 0 && count > pageIndex) {
                    pageIndex++;
                    loadReplies();
                } else {
                    replyAdapter.loadMoreEnd(true);
                }
            }
        },rvList);
    }

    private void loadReplies() {
//        loadingDialog.show();
        HttpParams httpParams = new HttpParams();
        httpParams.put("pn", pageIndex);
        httpParams.put("type", "1");
        httpParams.put("ps", "20");
        httpParams.put("root", getIntent().getStringExtra("id"));
        httpParams.put("oid", getIntent().getStringExtra("aid"));
        httpParams.put("sort", "2");
        OkClient.getInstance().get("https://api.bilibili.com/x/v2/reply/reply", httpParams, new OkClient.EntityCallBack<ResponseModel>(context, ResponseModel.class) {
            @Override
            public void onError(Response<ResponseModel> response) {
                super.onError(response);
                Log.i("TAG", "onError: https://api.bilibili.com/x/v2/reply/reply  error");
            }

            @Override
            public void onSuccess(Response<ResponseModel> response) {
                super.onSuccess(response);
                ResponseModel model = response.body();
                if (model == null) {
                    return;
                }
                if (model.getCode() == 0) {
                    ReplyModel replyModel = JSONObject.parseObject(model.getData(), ReplyModel.class);
                    count = CommonUtils.getPage(replyModel.getPage().getCount(), replyModel.getPage().getSize());
                    if (pageIndex==1) {
                        repliesList.add(replyModel.getRoot());
                    }
                    repliesList.addAll(replyModel.getReplies());
                    replyAdapter.setNewData(repliesList);
                    loadingDialog.dismiss();
                }
            }
        });
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }
}
