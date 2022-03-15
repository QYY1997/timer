package com.bookkeeping.myapplication.adapter;

import androidx.annotation.Nullable;

import com.bookkeeping.myapplication.MyApplication;
import com.bookkeeping.myapplication.R;
import com.bookkeeping.myapplication.model.MaterialModel;
import com.bookkeeping.myapplication.model.SynthesisModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author: qiuyiyang
 * @description:
 * @date: 2019/4/4
 */
public class NewMaterialAdapter extends BaseQuickAdapter<SynthesisModel, BaseViewHolder> {

    public NewMaterialAdapter(@Nullable List<SynthesisModel> data) {
        super(R.layout.item_new_material, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SynthesisModel item) {
        MaterialModel materialModel= MyApplication.getUserDao().findMaterialSql(" where id='"+item.getChildID()+"'");
        helper.setText(R.id.tv_name,materialModel.getName());
        helper.setText(R.id.tv_type,materialModel.getType());
        helper.setText(R.id.tv_vitality,materialModel.getVitality()==0?"":materialModel.getVitality()+"");
        helper.setText(R.id.tv_number,item.getNumber()+"");
        helper.setText(R.id.tv_synthesis,item.getIsSynthesis()==1?"自合":"材料");
        helper.setBackgroundColor(R.id.tv_synthesis,mContext.getResources().getColor(item.getIsSynthesis()==1?R.color.red:R.color.background));
        helper.setText(R.id.tv_operation,"删除");
    }
}
