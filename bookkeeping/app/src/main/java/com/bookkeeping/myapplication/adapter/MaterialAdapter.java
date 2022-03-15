package com.bookkeeping.myapplication.adapter;

import android.util.Log;

import androidx.annotation.Nullable;

import com.bookkeeping.myapplication.R;
import com.bookkeeping.myapplication.model.MaterialModel;
import com.bookkeeping.myapplication.util.CommonUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author: qiuyiyang
 * @description:
 * @date: 2019/4/4
 */
public class MaterialAdapter extends BaseQuickAdapter<MaterialModel, BaseViewHolder> {

    public MaterialAdapter(@Nullable List<MaterialModel> data) {
        super(R.layout.item_material, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MaterialModel item) {
        helper.setText(R.id.tv_name,item.getName());
        helper.setText(R.id.tv_type, item.getType());
        helper.setText(R.id.tv_vitality, CommonUtils.getNumber2(item.getVitality(),""));
        helper.setText(R.id.tv_purchase_price,CommonUtils.getNumber2(item.getPrice(),""));
        helper.setText(R.id.tv_sell_price,CommonUtils.getNumber2(item.getSellPrice(),""));
        helper.setText(R.id.tv_profit,CommonUtils.getNumber2(item.getProfit().intValue(),""));
        helper.setText(R.id.tv_cost_performance,item.getCostPerformance().compareTo(new BigDecimal("0"))==0?"":item.getCostPerformance().toString());
    }
}
