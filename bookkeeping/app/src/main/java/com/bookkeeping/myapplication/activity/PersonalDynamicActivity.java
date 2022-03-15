package com.bookkeeping.myapplication.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONObject;
import com.bookkeeping.myapplication.R;
import com.bookkeeping.myapplication.adapter.DynamicAdapter;
import com.bookkeeping.myapplication.base.BaseActivity;
import com.bookkeeping.myapplication.model.bilibili.CardModel;
import com.bookkeeping.myapplication.model.bilibili.CoinModel;
import com.bookkeeping.myapplication.model.bilibili.DynamicListModel;
import com.bookkeeping.myapplication.model.bilibili.DynamicSpaceModel;
import com.bookkeeping.myapplication.model.bilibili.ResponseModel;
import com.bookkeeping.myapplication.model.bilibili.UserInfoModel;
import com.bookkeeping.myapplication.util.CommonUtils;
import com.bookkeeping.myapplication.util.StorageCustomerInfo02Util;
import com.bookkeeping.myapplication.util.StringUtil;
import com.bookkeeping.myapplication.util.okgo.OkClient;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.android.material.tabs.TabLayout;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author : qiuyiyang
 * @date : 2021/8/10  15:52
 * @desc :
 */
public class PersonalDynamicActivity extends BaseActivity {
    @BindView(R.id.iv_head)
    ImageView ivHead;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_attention)
    TextView tvAttention;
    @BindView(R.id.tv_fans)
    TextView tvFans;
    @BindView(R.id.tv_dynamic)
    TextView tvDynamic;
    @BindView(R.id.tl_tab)
    TabLayout tlTab;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.srl_refresh)
    SmartRefreshLayout srlRefresh;
    @BindView(R.id.tv_coin)
    TextView tvCoin;
    @BindView(R.id.tv_coin_hint)
    TextView tvCoinHint;
    @BindView(R.id.ll_attention)
    LinearLayout llAttention;
    private List<CardModel> mList = new ArrayList<>();
    private DynamicAdapter dynamicAdapter;
    private String uid, self;
    private String next_offset;
    private int type = 0;

    @Override
    public int initLayout() {
        return R.layout.frag_mine;
    }

    @Override
    public void initData() {
        rvList.setNestedScrollingEnabled(false);
        self = StorageCustomerInfo02Util.getInfo("DedeUserID", context);
        uid = getIntent().getStringExtra("uid");
        tvCoin.setVisibility(View.GONE);
        tvCoinHint.setVisibility(View.GONE);
        srlRefresh.setRefreshHeader(new ClassicsHeader(context));
        srlRefresh.setRefreshFooter(new ClassicsFooter(context));
        srlRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mList.clear();
                dynamicAdapter.setNewData(mList);
                refreshlayout.resetNoMoreData();
                loadData();
                loadCard();
                loadDynamic();
            }
        });
        srlRefresh.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                if (!StringUtil.isEmpty(next_offset)) {
                    loadDataNext();
                } else {
                    refreshlayout.finishLoadmoreWithNoMoreData();
                }
            }
        });
        tlTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                type = tab.getPosition();
                srlRefresh.autoRefresh();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        rvList.setLayoutManager(new LinearLayoutManager(context));
        dynamicAdapter = new DynamicAdapter(mList);
        dynamicAdapter.bindToRecyclerView(rvList);
        dynamicAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (mList.get(position).getDesc().getType() == 8) {
                    startActivity(new Intent(context, BilBiliActivity.class).putExtra("bv", mList.get(position).getDesc().getBvid()));
                }
                if (mList.get(position).getDesc().getType() == 64) {
                    startActivity(new Intent(context, X5WebViewActivity.class).putExtra("url", (String) view.getTag()));
                }
                if (mList.get(position).getDesc().getType() == 256) {
                    startActivity(new Intent(context, X5WebViewActivity.class).putExtra("url", (String) view.getTag()));
                }
                if (mList.get(position).getDesc().getType() == 4308 || mList.get(position).getDesc().getType() == 4200) {
                    startActivity(new Intent(context, X5WebViewActivity.class).putExtra("url", (String) view.getTag()));
                }
            }
        });
        srlRefresh.autoRefresh();
    }

    private void loadDynamic() {
        HttpParams httpParams = new HttpParams();
        httpParams.put("uids", uid);
        OkClient.getInstance().get("https://api.vc.bilibili.com/dynamic_svr/v1/dynamic_svr/space_num_ex", httpParams, new OkClient.EntityCallBack<ResponseModel>(context, ResponseModel.class) {
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
                    DynamicSpaceModel dynamicSpaceModel = JSONObject.parseObject(model.getData(), DynamicSpaceModel.class);
                    tvDynamic.setText(dynamicSpaceModel.getItems().get(0).getNum() + "");
                }
            }
        });
    }

    private void loadCard() {
        HttpParams httpParams = new HttpParams();
        httpParams.put("mid", uid);
        httpParams.put("photo", true);
        OkClient.getInstance().get("https://api.bilibili.com/x/web-interface/card", httpParams, new OkClient.EntityCallBack<ResponseModel>(context, ResponseModel.class) {
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
                    UserInfoModel userInfoModel = JSONObject.parseObject(model.getData(), UserInfoModel.class);
                    tvFans.setText(CommonUtils.getNumber(userInfoModel.getCard().getFans()));
                    tvAttention.setText(userInfoModel.getCard().getAttention() + "");
                    tvName.setText(userInfoModel.getCard().getName());
                    Glide.with(context).load(userInfoModel.getCard().getFace()).asBitmap().into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            ivHead.setImageBitmap(resource);
                        }
                    });
                }
            }
        });
    }

    private void loadData() {
        HttpParams httpParams = new HttpParams();
        httpParams.put("visitor_uid", self);
        httpParams.put("host_uid", uid);
        httpParams.put("offset_dynamic_id", "0");
        httpParams.put("platform", "web");
        httpParams.put("need_top", "1");
        OkClient.getInstance().get("https://api.vc.bilibili.com/dynamic_svr/v1/dynamic_svr/space_history", httpParams, new OkClient.EntityCallBack<ResponseModel>(context, ResponseModel.class) {
            @Override
            public void onError(Response<ResponseModel> response) {
                super.onError(response);
                srlRefresh.finishRefresh();
            }

            @Override
            public void onSuccess(Response<ResponseModel> response) {
                super.onSuccess(response);
                srlRefresh.finishRefresh();
                ResponseModel model = response.body();
                if (model == null) {
                    return;
                }
                if (model.getCode() == 0) {
                    DynamicListModel dynamicListModel = JSONObject.parseObject(model.getData(), DynamicListModel.class);
                    setAdapter(dynamicListModel.getCards());
                    next_offset = dynamicListModel.getHistory_offset();
                }
            }
        });
    }

    private void loadDataNext() {
        HttpParams httpParams = new HttpParams();
        httpParams.put("visitor_uid", self);
        httpParams.put("host_uid", uid);
        httpParams.put("platform", "web");
        httpParams.put("need_top", "1");
        httpParams.put("offset_dynamic_id", next_offset);
        OkClient.getInstance().get("https://api.vc.bilibili.com/dynamic_svr/v1/dynamic_svr/space_history", httpParams, new OkClient.EntityCallBack<ResponseModel>(context, ResponseModel.class) {
            @Override
            public void onError(Response<ResponseModel> response) {
                super.onError(response);
                srlRefresh.finishLoadmore();
            }

            @Override
            public void onSuccess(Response<ResponseModel> response) {
                super.onSuccess(response);
                srlRefresh.finishLoadmore();
                ResponseModel model = response.body();
                if (model == null) {
                    return;
                }
                if (model.getCode() == 0) {
                    DynamicListModel dynamicListModel = JSONObject.parseObject(model.getData(), DynamicListModel.class);
                    setAdapter(dynamicListModel.getCards());
                    next_offset = dynamicListModel.getNext_offset();
                }
            }
        });
    }

    @OnClick(R.id.ll_attention)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_attention:
                startActivity(new Intent(context, AttentionListActivity.class).putExtra("uid", uid));
                break;
        }
    }

    private void setAdapter(List<CardModel> list) {
        switch (type) {
            case 1:
                for (CardModel cardModel : list) {
                    if (cardModel.getDesc().getType() == 8) {
                        mList.add(cardModel);
                    }
                }
                break;
            case 2:
                for (CardModel cardModel : list) {
                    if (cardModel.getDesc().getType() == 1 || cardModel.getDesc().getType() == 2 || cardModel.getDesc().getType() == 4) {
                        mList.add(cardModel);
                    }
                }
                break;
            case 3:
                for (CardModel cardModel : list) {
                    if (cardModel.getDesc().getType() == 512) {
                        mList.add(cardModel);
                    }
                }
                break;
            case 4:
                for (CardModel cardModel : list) {
                    if (cardModel.getDesc().getType() == 64) {
                        mList.add(cardModel);
                    }
                }
                break;
            case 5:
                for (CardModel cardModel : list) {
                    if (cardModel.getDesc().getType() == 256) {
                        mList.add(cardModel);
                    }
                }
                break;
            default:
                mList.addAll(list);
                break;
        }
        dynamicAdapter.setNewData(mList);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
