package com.timer.com;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.timer.com.bean.EnRollModel;
import com.timer.com.bean.SignUpModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : qiuyiyang
 * @date : 2022/2/3  22:22
 * @desc :
 */
public class SignUpOutListAdapter extends BaseQuickAdapter<SignUpModel, BaseViewHolder> {

    private SignUpListAdapter signUpListAdapter;

    public SignUpOutListAdapter(List<SignUpModel> list) {
        super(R.layout.item_choose_list, list);
    }

    @Override
    protected void convert(BaseViewHolder helper, SignUpModel item) {
        helper.setText(R.id.tv_text,helper.getAdapterPosition());
        signUpListAdapter = new SignUpListAdapter(item.getList());
        RecyclerView rvList =(RecyclerView) helper.getView(R.id.rv_list);
        rvList.setLayoutManager(new LinearLayoutManager(mContext));
        signUpListAdapter.bindToRecyclerView(rvList);
    }
}