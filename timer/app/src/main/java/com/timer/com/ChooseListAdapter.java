package com.timer.com;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @author : qiuyiyang
 * @date : 2022/2/3  22:22
 * @desc :
 */
public class ChooseListAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public ChooseListAdapter(List<String> list) {
        super(R.layout.item_choose_list, list);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_text,item);
    }
}