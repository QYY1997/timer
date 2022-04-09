package com.timer.com;

import android.graphics.Color;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.timer.com.bean.EnRollModel;
import com.timer.com.util.StorageCustomerInfoUtil;
import com.timer.com.util.StringUtil;

import java.util.List;

/**
 * @author : qiuyiyang
 * @date : 2022/2/3  22:22
 * @desc :
 */
public class EnRollListAdapter extends BaseQuickAdapter<EnRollModel, BaseViewHolder> {

    public EnRollListAdapter(List<EnRollModel> list) {
        super(R.layout.item_roll_list, list);
    }

    @Override
    protected void convert(BaseViewHolder helper, EnRollModel item) {
        helper.setText(R.id.tv_num,item.getRoad()+":");
        helper.setText(R.id.tv_name,item.getStudentName());
        helper.setText(R.id.tv_time,item.getRecord());
        if (StringUtil.isEmpty(item.getRecord())){
            helper.setText(R.id.tv_time,"暂无");
        }
        helper.setGone(R.id.tv_name, StorageCustomerInfoUtil.getBooleanInfo("nameSwitch",mContext,false));
    }
}