package com.bookkeeping.myapplication.adapter;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bookkeeping.myapplication.R;
import com.bookkeeping.myapplication.model.bilibili.EmoteModel;
import com.bookkeeping.myapplication.model.bilibili.RepliesModel;
import com.bookkeeping.myapplication.util.CommonUtils;
import com.bookkeeping.myapplication.util.DateUtil;
import com.bookkeeping.myapplication.util.GlideUtils;
import com.bookkeeping.myapplication.util.StringUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author: qiuyiyang
 * @description:
 * @date: 2019/4/4
 */
public class ReplyAdapter extends BaseQuickAdapter<RepliesModel, BaseViewHolder> {

    private List<EmoteModel> emoteList;
    private String message;
    private BitmapDrawable drawable;
    private String type;
    private boolean top=false;

    public void setTop(boolean top) {
        this.top = top;
    }

    public void setType(String type) {
        this.type = (type == null ? "" : type);
    }

    public ReplyAdapter(@Nullable List<RepliesModel> data) {
        super(R.layout.item_reply, data);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void convert(final BaseViewHolder helper, RepliesModel item) {
        helper.setGone(R.id.v_line,type.equals("out"));

        //是否置顶
        if (helper.getAdapterPosition()==0) {
            helper.setGone(R.id.tv_top, top);
        }

        //是否是评论详情
        if (type.equals("normal")&&item.getRcount()>0){
            helper.setGone(R.id.tv_details,true);
            helper.setText(R.id.tv_details,"共"+item.getRcount()+"条回复");
        }else {
            helper.setGone(R.id.tv_details,false);
        }

        RecyclerView rvList = helper.getView(R.id.rv_list);
        rvList.setFocusable(false);
        rvList.setNestedScrollingEnabled(false);
        if (item.getReplies()!=null&&item.getReplies().size()>0&&type.equals("normal")) {
            rvList.setVisibility(View.VISIBLE);
            ReplyAdapter replyAdapter;
            rvList.setLayoutManager(new LinearLayoutManager(mContext));
            replyAdapter = new ReplyAdapter(item.getReplies());
            replyAdapter.setType("out");
            replyAdapter.bindToRecyclerView(rvList);
        }
        else {
            rvList.setVisibility(View.GONE);
        }

        if (type.equals("detail")&&helper.getAdapterPosition()>0) {
            helper.setGone(R.id.ll_detail,true);
        }
        else {
            helper.setGone(R.id.ll_detail,false);
        }

        //头像框
        if (!StringUtil.isEmpty(item.getMember().getPendant().getImage())) {
            helper.setVisible(R.id.iv_pendant, true);
            GlideUtils.loadImage(mContext, item.getMember().getPendant().getImage(), (ImageView) helper.getView(R.id.iv_pendant));
        } else {
            helper.setGone(R.id.iv_pendant, false);
        }

        //徽章
        if (!StringUtil.isEmpty(item.getMember().getNameplate().getImage_small())) {
            GlideUtils.loadAvatar(mContext, item.getMember().getNameplate().getImage_small(), (ImageView) helper.getView(R.id.iv_badge));
        } else {
            helper.setGone(R.id.iv_badge, false);
        }


        //card table
        TextView tab= helper.getView(R.id.tv_up_like);
        if (item.getCard_label()!=null&&item.getCard_label().size()>0) {
            tab.setText(item.getCard_label().get(0).getText_content());
            tab.setVisibility(View.VISIBLE);
        }
        else {
            tab.setVisibility(View.GONE);
        }

        //头像
        if (!StringUtil.isEmpty(item.getMember().getAvatar())) {
            GlideUtils.loadAvatar(mContext, item.getMember().getAvatar(), (ImageView) helper.getView(R.id.iv_head));
        }

        //粉丝牌
        if (item.getMember().getFans_detail()!=null){
            helper.setText(R.id.tv_namePlate_reply,item.getMember().getFans_detail().getMedal_name());
            helper.setText(R.id.tv_level_reply,item.getMember().getFans_detail().getLevel()+"");
            helper.setGone(R.id.iv_pendant_reply,true);
            switch (item.getMember().getFans_detail().getGuard_level()){
                case "0":
                    helper.setGone(R.id.iv_pendant_reply,false);
                    break;
                case "1":
                    helper.setImageResource(R.id.iv_pendant_reply,R.drawable.zongdu);
                    break;
                case "2":
                    helper.setImageResource(R.id.iv_pendant_reply,R.drawable.tidu);
                    break;
                case "3":
                    helper.setImageResource(R.id.iv_pendant_reply,R.drawable.navigation);
                    break;
            }
        }
        else {
            helper.setGone(R.id.iv_pendant_reply,false);
            helper.setGone(R.id.ll_name_plate,false);
        }

        //昵称
        TextView name = helper.getView(R.id.tv_name);
        name.setText(item.getMember().getUname());
        if (item.getMember().getVip().getVipType() == 1 || item.getMember().getVip().getVipType() == 2) {
            name.setTextColor(Color.RED);
        } else {
            name.setTextColor(Color.GRAY);
        }
        //等级
        helper.setText(R.id.tv_level, "Lv" + item.getMember().getLevel_info().getCurrent_level());

        //点赞数
        helper.setText(R.id.tv_like, item.getLike() + "");
        if (item.getAction()==1) {
            helper.setImageResource(R.id.iv_like, R.drawable.like);
        } else {
            helper.setImageResource(R.id.iv_like, R.drawable.unlike);
        }
        //等级颜色
        switch (item.getMember().getLevel_info().getCurrent_level()) {
            case 0:
                helper.setTextColor(R.id.tv_level, mContext.getColor(R.color.gray_light));
                break;
            case 1:
                helper.setTextColor(R.id.tv_level, mContext.getColor(R.color.gray_888));
                break;
            case 2:
                helper.setTextColor(R.id.tv_level, mContext.getColor(R.color.green));
                break;
            case 3:
                helper.setTextColor(R.id.tv_level, mContext.getColor(R.color.bg_blue));
                break;
            case 4:
                helper.setTextColor(R.id.tv_level, mContext.getColor(R.color.orange_fb8d1c));
                break;
            case 5:
                helper.setTextColor(R.id.tv_level, mContext.getColor(R.color.red_e84230));
                break;
            case 6:
                helper.setTextColor(R.id.tv_level, mContext.getColor(R.color.red));
                break;
        }

        //时间
        if (System.currentTimeMillis() / 1000 - item.getCtime() < 60) {
            helper.setText(R.id.tv_create_time, (System.currentTimeMillis() / 1000 - item.getCtime()) + "秒前");
        } else if ((System.currentTimeMillis() / 60000 - item.getCtime() / 60) < 60) {
            helper.setText(R.id.tv_create_time, (System.currentTimeMillis() / 60000 - item.getCtime() / 60) + "分钟前");
        } else if ((System.currentTimeMillis() / 3600000 - item.getCtime() / 3600) < 24) {
            helper.setText(R.id.tv_create_time, (System.currentTimeMillis() / 3600000 - item.getCtime() / 3600) + "小时前");
        } else {
            helper.setText(R.id.tv_create_time, DateUtil.formatDateToHM(item.getCtime() * 1000));
        }

        //使用平台
        switch (item.getContent().getPlat()) {
            case 1:
                helper.setText(R.id.tv_from, "来自网页端");
                break;
            case 2:
                if (StringUtil.isEmpty(item.getContent().getDevice())) {
                    helper.setText(R.id.tv_from, "来自安卓客户端");
                } else if (item.getContent().getDevice().equals("pad")) {
                    helper.setText(R.id.tv_from, "来自Pad客户端");
                }
                break;
            case 3:
                if (item.getContent().getDevice().equals("phone")) {
                    helper.setText(R.id.tv_from, "来自IOS客户端");
                } else {
                    helper.setText(R.id.tv_from, "来自IPad客户端");
                }
                break;
        }

        //评论内容
        message = item.getContent().getMessage();
        helper.setText(R.id.tv_content, message);
        if (!StringUtil.isEmpty(item.getContent().getEmote())) {
            emoteList = new ArrayList<>();
            try {
                JSONObject jsonObject = new JSONObject(item.getContent().getEmote());
                //动态获取key值
                Iterator<String> iterator = jsonObject.keys();
                while (iterator.hasNext()) {
                    String key = iterator.next();
                    Gson gson = new Gson();
                    EmoteModel emote = gson.fromJson(jsonObject.getJSONObject(key).toString(), EmoteModel.class);
                    emoteList.add(emote);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            TextView content = (TextView) helper.getView(R.id.tv_content);
            SpannableString spanStr = new SpannableString(message);

            if (emoteList != null && emoteList.size() > 0) {
                for (EmoteModel emoteBean : emoteList) {
                    drawable = new BitmapDrawable();
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            URL imageurl;
                            try {
                                imageurl = new URL(emoteBean.getUrl());
                                HttpURLConnection conn = (HttpURLConnection) imageurl.openConnection();
                                conn.setDoInput(true);
                                conn.connect();
                                InputStream is = conn.getInputStream();
                                Bitmap bitmap = BitmapFactory.decodeStream(is);
                                is.close();
                                Bitmap zoom = CommonUtils.zoom(bitmap, 48);
                                Resources resources = content.getContext().getResources();
                                drawable = new BitmapDrawable(resources, zoom);
                                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    try {
                        thread.start();
                        thread.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    int start = message.indexOf(emoteBean.getText());
                    while (start != -1) {
                        spanStr.setSpan(new ImageSpan(drawable), start, start + emoteBean.getText().length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                        start = message.indexOf(emoteBean.getText(), start + emoteBean.getText().length());
                        if (start == -1) {
                            content.setText(spanStr);
                            break;
                        }
                        continue;
                    }
                }
            }
        }
    }
}
