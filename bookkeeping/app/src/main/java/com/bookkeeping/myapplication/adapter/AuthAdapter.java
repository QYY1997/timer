package com.bookkeeping.myapplication.adapter;

import androidx.annotation.Nullable;

import android.graphics.Color;
import android.widget.ImageView;
import android.widget.TextView;

import com.bookkeeping.myapplication.R;
import com.bookkeeping.myapplication.model.bilibili.StaffModel;
import com.bookkeeping.myapplication.model.bilibili.VideoModel;
import com.bookkeeping.myapplication.util.GlideUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import java.util.List;

/**
 * @author: qiuyiyang
 * @description:
 * @date: 2019/4/4
 */
public class AuthAdapter extends BaseQuickAdapter<StaffModel, BaseViewHolder> {

    public AuthAdapter(@Nullable List<StaffModel> data) {
        super(R.layout.item_video, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, StaffModel item) {
        GlideUtils.loadAvatar(mContext,item.getFace(),(ImageView)helper.getView(R.id.iv_head));
        helper.setText(R.id.tv_title,item.getTitle());
        TextView name= helper.getView(R.id.tv_name);
        name.setText(item.getName());
        if (item.getVip().getType()==1||item.getVip().getType()==2){
            name.setTextColor(Color.RED);
            name.getPaint().setFakeBoldText(true);
        }
        else {
            name.setTextColor(Color.GRAY);
            name.getPaint().setFakeBoldText(false);
        }
    }
}
