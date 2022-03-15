package com.timer.com;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @author : qiuyiyang
 * @date : 2022/2/3  22:22
 * @desc :
 */
public class LocalListAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public LocalListAdapter(List<String> list) {
        super(R.layout.item_local_list, list);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_text,item);
        helper.addOnClickListener(R.id.tv_delete);
    }
}