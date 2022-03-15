package com.bookkeeping.myapplication.adapter;

import android.graphics.Color;
import androidx.annotation.Nullable;
import com.bookkeeping.myapplication.R;
import com.bookkeeping.myapplication.model.BookModel;
import com.bookkeeping.myapplication.util.DateUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author: qiuyiyang
 * @description:
 * @date: 2019/4/4
 */
public class ListAdapter extends BaseQuickAdapter<BookModel, BaseViewHolder> {

    public ListAdapter(@Nullable List<BookModel> data) {
        super(R.layout.item_list, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BookModel item) {
        helper.setText(R.id.tv_time, DateUtil.formateDateTOYMD(item.getUseTime()));
        helper.setText(R.id.tv_event,item.getEvent());
        helper.setText(R.id.tv_trx_money,"￥"+item.getTrxMoney());
        helper.setTextColor(R.id.tv_trx_money,item.getTrxMoney().compareTo(new BigDecimal("0"))==1?Color.GREEN:Color.RED);
        helper.setText(R.id.tv_type,item.getType());
        helper.setText(R.id.tv_detail, "删除");
        helper.setBackgroundColor(R.id.tv_detail,Color.RED);
        helper.addOnClickListener(R.id.tv_detail);
    }
}
