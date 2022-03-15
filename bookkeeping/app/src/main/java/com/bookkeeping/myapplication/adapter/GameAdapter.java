package com.bookkeeping.myapplication.adapter;

import androidx.annotation.Nullable;

import com.bookkeeping.myapplication.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @author: qiuyiyang
 * @description:
 * @date: 2019/4/4
 */
public class GameAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public GameAdapter(@Nullable List<String> data) {
        super(R.layout.item_item_game, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
       helper.setText(R.id.tv_item,item);
    }
}
