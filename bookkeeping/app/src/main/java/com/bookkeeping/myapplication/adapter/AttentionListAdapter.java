package com.bookkeeping.myapplication.adapter;

import android.graphics.Color;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bookkeeping.myapplication.R;
import com.bookkeeping.myapplication.model.bilibili.PersonalModel;
import com.bookkeeping.myapplication.model.bilibili.UserInfoModel;
import com.bookkeeping.myapplication.util.CommonUtils;
import com.bookkeeping.myapplication.util.GlideUtils;
import com.bookkeeping.myapplication.util.StringUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @author: qiuyiyang
 * @description:
 * @date: 2019/4/4
 */
public class AttentionListAdapter extends BaseQuickAdapter<PersonalModel, BaseViewHolder> {

    public AttentionListAdapter(@Nullable List<PersonalModel> data) {
        super(R.layout.item_attention_list, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PersonalModel item) {
        helper.setText(R.id.tv_name,item.getUname());
        helper.setText(R.id.tv_tag,item.getOfficial_verify().getDesc());
        helper.setText(R.id.tv_sign,item.getSign());
        helper.setGone(R.id.tv_tag, !StringUtil.isEmpty(item.getOfficial_verify().getDesc()));
        helper.setGone(R.id.tv_sign, !StringUtil.isEmpty(item.getSign()));
        if (item.getVip().getVipType() == 1 || item.getVip().getVipType() == 2) {
            helper.setTextColor(R.id.tv_name,Color.RED);
        } else {
            helper.setTextColor(R.id.tv_name,Color.BLACK);
        }
        GlideUtils.loadAvatar(mContext,item.getFace(),(ImageView)helper.getView(R.id.iv_head));
    }
}
