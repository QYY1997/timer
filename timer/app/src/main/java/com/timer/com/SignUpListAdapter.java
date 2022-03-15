package com.timer.com;

import android.graphics.Color;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.timer.com.bean.EnRollModel;
import com.timer.com.util.DateUtil;

import java.util.List;

/**
 * @author : qiuyiyang
 * @date : 2022/2/3  22:22
 * @desc :
 */
public class SignUpListAdapter extends BaseQuickAdapter<EnRollModel, BaseViewHolder> {

    public SignUpListAdapter(List<EnRollModel> list) {
        super(R.layout.item_sign_up_list, list);
    }

    @Override
    protected void convert(BaseViewHolder helper, EnRollModel item) {
        helper.setText(R.id.tv_num,item.getRoad()+":");
        helper.setText(R.id.tv_name,item.getStudentName());
        helper.setText(R.id.tv_cls,item.getCls());
        if (item.getDateCreated()>0) {
            helper.setText(R.id.tv_sign_up_time, DateUtil.formatDateToHMS(item.getDateCreated()));
            helper.setText(R.id.tv_status, "已报名");
            helper.setTextColor(R.id.tv_status, Color.RED);
        }else {
            helper.setText(R.id.tv_sign_up_time, "");
            helper.setText(R.id.tv_status, "未报名");
            helper.setTextColor(R.id.tv_status, Color.BLACK);
        }
        helper.setText(R.id.tv_name,item.getStudentName());
    }
}