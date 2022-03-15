package com.bookkeeping.myapplication.adapter;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONObject;
import com.bookkeeping.myapplication.R;
import com.bookkeeping.myapplication.activity.BilBiliActivity;
import com.bookkeeping.myapplication.activity.ImageActivity;
import com.bookkeeping.myapplication.activity.PersonalDynamicActivity;
import com.bookkeeping.myapplication.activity.X5WebViewActivity;
import com.bookkeeping.myapplication.model.bilibili.AttachCardModel;
import com.bookkeeping.myapplication.model.bilibili.CardModel;
import com.bookkeeping.myapplication.model.bilibili.DynamicActModel;
import com.bookkeeping.myapplication.model.bilibili.DynamicColumnModel;
import com.bookkeeping.myapplication.model.bilibili.DynamicDramaModel;
import com.bookkeeping.myapplication.model.bilibili.DynamicLiveCloseModel;
import com.bookkeeping.myapplication.model.bilibili.DynamicLiveingModel;
import com.bookkeeping.myapplication.model.bilibili.DynamicMusicModel;
import com.bookkeeping.myapplication.model.bilibili.DynamicPicturesModel;
import com.bookkeeping.myapplication.model.bilibili.DynamicTurnModel;
import com.bookkeeping.myapplication.model.bilibili.DynamicVideoModel;
import com.bookkeeping.myapplication.model.bilibili.ExtendModel;
import com.bookkeeping.myapplication.model.bilibili.ReserveAttachCardModel;
import com.bookkeeping.myapplication.util.CommonUtils;
import com.bookkeeping.myapplication.util.GlideUtils;
import com.bookkeeping.myapplication.util.StringUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author : qiuyiyang
 * @date : 2021/7/16  16:41
 * @desc :
 */
public class DynamicAdapter extends BaseQuickAdapter<CardModel, DynamicAdapter.MyViewHolder> {

    private ImageAdapter imageAdapter;

    public DynamicAdapter(@Nullable List<CardModel> data) {
        super(R.layout.item_cards, data);
    }

    @Override
    protected void convert(MyViewHolder helper, CardModel item) {

        helper.rvList.setVisibility(View.GONE);
        helper.llColumn.setVisibility(View.GONE);
        helper.llMusic.setVisibility(View.GONE);
        helper.llVideo.setVisibility(View.GONE);
        helper.llTurn.setVisibility(View.GONE);
        helper.llCard.setVisibility(View.GONE);
        helper.llHint.setVisibility(View.GONE);
        helper.tvContent.setVisibility(View.VISIBLE);
        helper.rvList.setNestedScrollingEnabled(false);
        helper.rvListTurn.setNestedScrollingEnabled(false);

        //点赞数
        helper.tvLike.setText(CommonUtils.getNumber(item.getDesc().getLike(), "点赞"));

        //评论数
        helper.tvReply.setText(CommonUtils.getNumber(item.getDesc().getComment(), "评论"));

        //转发数
        helper.tvTurn.setText(CommonUtils.getNumber(item.getDesc().getRepost(), "转发"));

        ExtendModel extendModel = JSONObject.parseObject(item.getExtend_json().replace("\"\":", "\"s\":"), ExtendModel.class);
        if (extendModel.getDispute() != null && !StringUtil.isEmpty(extendModel.getDispute().getContent())) {
            helper.llHint.setVisibility(View.VISIBLE);
            helper.tvHint.setText(extendModel.getDispute().getContent());
        } else {
            helper.llHint.setVisibility(View.GONE);
        }


        //时间
        helper.tvCreateTime.setText(CommonUtils.getTimeStr(item.getDesc().getTimestamp()));

        if (item.getDesc().getUser_profile() != null) {
            //昵称
            if (!StringUtil.isEmpty(item.getDesc().getUser_profile().getInfo().getUname())) {
                helper.tvName.setText(item.getDesc().getUser_profile().getInfo().getUname());
            }
            if (item.getDesc().getUser_profile().getVip().getVipType() == 1 || item.getDesc().getUser_profile().getVip().getVipType() == 2) {
                helper.tvName.setTextColor(Color.RED);
            } else {
                helper.tvName.setTextColor(Color.GRAY);
            }
            //头像
            if (!StringUtil.isEmpty(item.getDesc().getUser_profile().getInfo().getFace())) {
                GlideUtils.loadAvatar(mContext,item.getDesc().getUser_profile().getInfo().getFace(),helper.ivHead);
            }

            //头像框
            if (!StringUtil.isEmpty(item.getDesc().getUser_profile().getPendant().getImage())) {
                helper.ivPendant.setVisibility(View.VISIBLE);
                GlideUtils.loadImage(mContext, item.getDesc().getUser_profile().getPendant().getImage(), helper.ivPendant);
            } else {
                helper.ivPendant.setVisibility(View.GONE);
            }
        }

        //赞了
        if (item.getDisplay().getLike_info() != null && item.getDisplay().getLike_info().getLike_users() != null && item.getDisplay().getLike_info().getLike_users().size() > 0) {
            String content = "";
            for (int i = 0; i < item.getDisplay().getLike_info().getLike_users().size(); i++) {
                if (i > 0) {
                    content += "、";
                }
                content += item.getDisplay().getLike_info().getLike_users().get(i).getUname();
            }
            helper.tvUpLike.setVisibility(View.VISIBLE);
            helper.tvUpLike.setText(content + "赞了");
        } else {
            helper.tvUpLike.setVisibility(View.GONE);
        }
        helper.ivHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, PersonalDynamicActivity.class)
                        .putExtra("uid",item.getDesc().getUser_profile().getInfo().getUid()));
            }
        });
        if (item.getDisplay().getAdd_on_card_info() != null && item.getDisplay().getAdd_on_card_info().size() > 0) {
            switch (item.getDisplay().getAdd_on_card_info().get(0).getAdd_on_card_show_type()) {
                case 2:
                    if (item.getDisplay().getAdd_on_card_info() != null && item.getDisplay().getAdd_on_card_info().get(0).getAttach_card() != null) {
                        helper.llCard.setVisibility(View.VISIBLE);
                    }
                    AttachCardModel attachCardModel = item.getDisplay().getAdd_on_card_info().get(0).getAttach_card();
                    helper.tvButton.setTextColor(Color.WHITE);
                    helper.tvButton.setBackgroundResource(R.drawable.shape_solid_background_corner_2);
                    GlideUtils.loadImage(mContext, attachCardModel.getCover_url(), helper.ivCard);
                    helper.tvCardTitle.setText(attachCardModel.getTitle());
                    helper.tvButton.setText(attachCardModel.getButton().getJump_style().getText());
                    helper.tvButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mContext.startActivity(new Intent(mContext, X5WebViewActivity.class)
                                    .putExtra("title", attachCardModel.getTitle())
                                    .putExtra("url", attachCardModel.getJump_url()));
                        }
                    });
                    helper.ivCard.setVisibility(View.VISIBLE);
                    helper.tvCardContent.setText(attachCardModel.getDesc_first());
                    helper.tvCardContent.setVisibility(StringUtil.isEmpty(attachCardModel.getDesc_first()) ? View.GONE : View.VISIBLE);
                    helper.tvCardContentSecond.setText(attachCardModel.getDesc_second());
                    helper.tvCardContentSecond.setVisibility(StringUtil.isEmpty(attachCardModel.getDesc_second()) ? View.GONE : View.VISIBLE);
                    break;
                case 6:
                    if (item.getDisplay().getAdd_on_card_info() != null && item.getDisplay().getAdd_on_card_info().get(0).getReserve_attach_card() != null) {
                        helper.llCard.setVisibility(View.VISIBLE);
                    }
                    ReserveAttachCardModel reserveAttachCardModel = item.getDisplay().getAdd_on_card_info().get(0).getReserve_attach_card();
                    helper.ivCard.setVisibility(View.GONE);
                    helper.tvCardTitle.setText(reserveAttachCardModel.getTitle());
                    switch (reserveAttachCardModel.getReserve_button().getStatus()) {
                        case 1:
                            helper.tvButton.setText(reserveAttachCardModel.getReserve_button().getUncheck().getText());
                            helper.tvButton.setTextColor(Color.WHITE);
                            helper.tvButton.setBackgroundResource(R.drawable.shape_solid_background_corner_2);
                            break;
                        case 2:
                            helper.tvButton.setText(reserveAttachCardModel.getReserve_button().getCheck().getText());
                            helper.tvButton.setTextColor(mContext.getResources().getColor(R.color.gray_888));
                            helper.tvButton.setBackgroundResource(R.drawable.shape_solid_gray_light__corner_2);
                            break;
                        default:
                            helper.tvButton.setVisibility(View.GONE);
                            break;
                    }
                    helper.tvCardContent.setText(reserveAttachCardModel.getDesc_first().getText() + "  " + reserveAttachCardModel.getDesc_second());
                    helper.tvCardContent.setVisibility(View.VISIBLE);
                    helper.tvCardContentSecond.setText("");
                    helper.tvCardContentSecond.setVisibility(View.GONE);
                    if (reserveAttachCardModel.getReserve_lottery() != null) {
                        helper.tvCardContentDraw.setText(reserveAttachCardModel.getReserve_lottery().getText());
                        GlideUtils.loadImage(mContext, reserveAttachCardModel.getReserve_lottery().getIcon(), helper.ivDraw);
                        helper.llDraw.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mContext.startActivity(new Intent(mContext,X5WebViewActivity.class).putExtra("url",reserveAttachCardModel.getReserve_lottery().getJump_url()).putExtra("title",reserveAttachCardModel.getReserve_lottery().getText()));
                            }
                        });
                    }
                    else {
                        helper.llDraw.setVisibility(View.GONE);
                    }
                    break;
            }
        }


        switch (item.getDesc().getType()) {
            case 1:
                helper.llTurn.setVisibility(View.VISIBLE);
                helper.rvListTurn.setVisibility(View.GONE);
                helper.llColumnTurn.setVisibility(View.GONE);
                helper.llMusicTurn.setVisibility(View.GONE);
                helper.llCardTurn.setVisibility(View.GONE);
                helper.llVideoTurn.setVisibility(View.GONE);
                helper.tvContentTurn.setVisibility(View.VISIBLE);
                helper.llHintTurn.setVisibility(View.GONE);

                DynamicTurnModel dynamicTurnModel = JSONObject.parseObject(item.getCard(), DynamicTurnModel.class);
                CardModel items = new CardModel();
                items.setDesc(item.getDesc().getOrigin());
                items.getDesc().setUser_profile(dynamicTurnModel.getOrigin_user());
                items.setCard(dynamicTurnModel.getOrigin());
                items.setDisplay(item.getDisplay().getOrigin());

                //头像
                if (!StringUtil.isEmpty(items.getDesc().getUser_profile().getInfo().getFace())) {
                    GlideUtils.loadAvatar(mContext, items.getDesc().getUser_profile().getInfo().getFace(), helper.ivHeadTurn);
                }
                //昵称
                if (!StringUtil.isEmpty(items.getDesc().getUser_profile().getInfo().getUname())) {
                    helper.tvNameTurn.setText(items.getDesc().getUser_profile().getInfo().getUname());
                }
                if (items.getDesc().getUser_profile().getVip().getVipType() == 1 || items.getDesc().getUser_profile().getVip().getVipType() == 2) {
                    helper.tvNameTurn.setTextColor(Color.RED);
                } else {
                    helper.tvNameTurn.setTextColor(Color.GRAY);
                }
                //文本内容
                if (item.getDisplay().getEmoji_info() != null) {
                    CommonUtils.setReply(dynamicTurnModel.getItem().getContent(), helper.tvContent, item.getDisplay().getEmoji_info().getEmoji_details());
                } else {
                    helper.tvContent.setVisibility(StringUtil.isEmpty(dynamicTurnModel.getItem().getContent()) ? View.GONE : View.VISIBLE);
                    helper.tvContent.setText(dynamicTurnModel.getItem().getContent());
                }
                ExtendModel extendModelTurn = JSONObject.parseObject(item.getExtend_json(), ExtendModel.class);
                if (extendModelTurn.getDispute() != null && !StringUtil.isEmpty(extendModelTurn.getDispute().getContent())) {
                    helper.llHintTurn.setVisibility(View.VISIBLE);
                    helper.tvHintTurn.setText(extendModelTurn.getDispute().getContent());
                } else {
                    helper.llHintTurn.setVisibility(View.GONE);
                }
                helper.ivHeadTurn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mContext.startActivity(new Intent(mContext, PersonalDynamicActivity.class)
                                .putExtra("uid",items.getDesc().getUser_profile().getInfo().getUid()));
                    }
                });
                if (items.getDisplay().getAdd_on_card_info() != null && items.getDisplay().getAdd_on_card_info().size() > 0) {
                    switch (items.getDisplay().getAdd_on_card_info().get(0).getAdd_on_card_show_type()) {
                        case 2:
                            if (items.getDisplay().getAdd_on_card_info() != null && items.getDisplay().getAdd_on_card_info().get(0).getAttach_card() != null) {
                                helper.llCardTurn.setVisibility(View.VISIBLE);
                            }
                            AttachCardModel attachCardModel = items.getDisplay().getAdd_on_card_info().get(0).getAttach_card();
                            helper.tvButtonTurn.setTextColor(Color.WHITE);
                            helper.tvButtonTurn.setBackgroundResource(R.drawable.shape_solid_background_corner_2);
                            GlideUtils.loadImage(mContext, attachCardModel.getCover_url(), helper.ivCardTurn);
                            helper.tvCardTitleTurn.setText(attachCardModel.getTitle());
                            helper.tvButtonTurn.setText(attachCardModel.getButton().getJump_style().getText());
                            helper.tvButtonTurn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mContext.startActivity(new Intent(mContext, X5WebViewActivity.class)
                                            .putExtra("title", attachCardModel.getTitle())
                                            .putExtra("url", attachCardModel.getJump_url()));
                                }
                            });
                            helper.ivCardTurn.setVisibility(View.VISIBLE);
                            helper.tvCardContentTurn.setText(attachCardModel.getDesc_first());
                            helper.tvCardContentTurn.setVisibility(StringUtil.isEmpty(attachCardModel.getDesc_first()) ? View.GONE : View.VISIBLE);
                            helper.tvCardContentSecondTurn.setText(attachCardModel.getDesc_second());
                            helper.tvCardContentSecondTurn.setVisibility(StringUtil.isEmpty(attachCardModel.getDesc_second()) ? View.GONE : View.VISIBLE);
                            break;
                        case 6:
                            if (items.getDisplay().getAdd_on_card_info() != null && items.getDisplay().getAdd_on_card_info().get(0).getReserve_attach_card() != null) {
                                helper.llCardTurn.setVisibility(View.VISIBLE);
                            }
                            ReserveAttachCardModel reserveAttachCardModel = items.getDisplay().getAdd_on_card_info().get(0).getReserve_attach_card();
                            helper.ivCardTurn.setVisibility(View.GONE);
                            helper.tvCardTitleTurn.setText(reserveAttachCardModel.getTitle());
                            switch (reserveAttachCardModel.getReserve_button().getStatus()) {
                                case 1:
                                    helper.tvButtonTurn.setText(reserveAttachCardModel.getReserve_button().getUncheck().getText());
                                    helper.tvButtonTurn.setTextColor(Color.WHITE);
                                    helper.tvButtonTurn.setBackgroundResource(R.drawable.shape_solid_background_corner_2);
                                    break;
                                case 2:
                                    helper.tvButtonTurn.setText(reserveAttachCardModel.getReserve_button().getCheck().getText());
                                    helper.tvButtonTurn.setTextColor(mContext.getResources().getColor(R.color.gray_888));
                                    helper.tvButtonTurn.setBackgroundResource(R.drawable.shape_solid_gray_light__corner_2);
                                    break;
                                default:
                                    helper.tvButtonTurn.setVisibility(View.GONE);
                                    break;
                            }
                            helper.tvCardContentTurn.setText(reserveAttachCardModel.getDesc_first().getText() + "  " + reserveAttachCardModel.getDesc_second());
                            helper.tvCardContentTurn.setVisibility(View.VISIBLE);
                            helper.tvCardContentSecondTurn.setText("");
                            helper.tvCardContentSecondTurn.setVisibility(View.GONE);
                            if (reserveAttachCardModel.getReserve_lottery() != null) {
                                helper.tvCardContentDrawTurn.setText(reserveAttachCardModel.getReserve_lottery().getText());
                                GlideUtils.loadImage(mContext, reserveAttachCardModel.getReserve_lottery().getIcon(), helper.ivDrawTurn);
                                helper.llDrawTurn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        mContext.startActivity(new Intent(mContext,X5WebViewActivity.class).putExtra("url",reserveAttachCardModel.getReserve_lottery().getJump_url()).putExtra("title",reserveAttachCardModel.getReserve_lottery().getText()));
                                    }
                                });
                            }
                            else {
                                helper.llDrawTurn.setVisibility(View.GONE);
                            }
                            break;
                    }
                }

                switch (item.getDesc().getOrig_type()) {
                    case 2:
                        DynamicPicturesModel dynamicPicturesModel = JSONObject.parseObject(items.getCard(), DynamicPicturesModel.class);
                        helper.rvListTurn.setVisibility(View.VISIBLE);
                        //图片
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, dynamicPicturesModel.getItem().getPictures_count() != 4 ? 3 : 2);
                        helper.rvListTurn.setLayoutManager(gridLayoutManager);
                        imageAdapter = new ImageAdapter(dynamicPicturesModel.getItem().getPictures());
                        imageAdapter.bindToRecyclerView(helper.rvListTurn);
                        imageAdapter.setOnItemClickListener(new OnItemClickListener() {
                            @Override
                            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                mContext.startActivity(new Intent(mContext, ImageActivity.class)
                                        .putExtra("title", items.getDesc().getUser_profile().getInfo().getUname())
                                        .putExtra("url", dynamicPicturesModel.getItem().getPictures().get(position).getImg_src()));
                            }
                        });

                        //文本内容
                        if (items.getDisplay().getEmoji_info() != null) {
                            CommonUtils.setReply(dynamicPicturesModel.getItem().getDescription(), helper.tvContentTurn, items.getDisplay().getEmoji_info().getEmoji_details());
                        } else {
                            helper.tvContentTurn.setText(StringUtil.isEmpty(dynamicPicturesModel.getItem().getDescription()) ? "分享图片" : dynamicPicturesModel.getItem().getDescription());
                        }
                        break;
                    case 4:
                        DynamicTurnModel dynamicTurnModelWord = JSONObject.parseObject(items.getCard(), DynamicTurnModel.class);

                        //文本内容
                        if (items.getDisplay().getEmoji_info() != null) {
                            CommonUtils.setReply(dynamicTurnModelWord.getItem().getContent(), helper.tvContentTurn, items.getDisplay().getEmoji_info().getEmoji_details());
                        } else {
                            helper.tvContentTurn.setVisibility(StringUtil.isEmpty(dynamicTurnModelWord.getItem().getContent()) ? View.GONE : View.VISIBLE);
                            helper.tvContentTurn.setText(dynamicTurnModelWord.getItem().getContent());
                        }
                        break;
                    case 8:
                        DynamicVideoModel dynamicVideoModel = JSONObject.parseObject(items.getCard(), DynamicVideoModel.class);
                        helper.llVideoTurn.setVisibility(View.VISIBLE);
                        helper.llViewTurn.setVisibility(View.VISIBLE);
                        helper.tvLiveTurn.setVisibility(View.GONE);
                        //文本内容
                        if (items.getDisplay().getEmoji_info() != null) {
                            CommonUtils.setReply(dynamicVideoModel.getDynamic(), helper.tvContentTurn, items.getDisplay().getEmoji_info().getEmoji_details());
                        } else {
                            helper.tvContentTurn.setVisibility(StringUtil.isEmpty(dynamicVideoModel.getDynamic()) ? View.GONE : View.VISIBLE);
                            helper.tvContentTurn.setText(dynamicVideoModel.getDynamic());
                        }
                        //评论数
                        helper.tvReply.setText(CommonUtils.getNumber(dynamicVideoModel.getStat().getReply(), "评论"));
                        //封面
                        if (!StringUtil.isEmpty(dynamicVideoModel.getPic())) {
                            CommonUtils.loadImageVideo(mContext, dynamicVideoModel.getPic(), helper.ivImageTurn);
                        }
                        //标题
                        if (!StringUtil.isEmpty(dynamicVideoModel.getTitle())) {
                            helper.tvTitleTurn.setText(dynamicVideoModel.getTitle());
                        }
                        else {
                            helper.tvTitleTurn.setText("");
                        }
                        //简介
                        if (!StringUtil.isEmpty(dynamicVideoModel.getDesc())) {
                            helper.tvIntroductionTurn.setText(dynamicVideoModel.getDesc());
                        }
                        else {
                            helper.tvIntroductionTurn.setText("");
                        }
                        //播放量
                        helper.tvViewTurn.setText(CommonUtils.getNumber(dynamicVideoModel.getStat().getView()));
                        //弹幕数
                        helper.tvDanmakuTurn.setText(CommonUtils.getNumber(dynamicVideoModel.getStat().getDanmaku()));

                        helper.llTurn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mContext.startActivity(new Intent(mContext, BilBiliActivity.class).putExtra("bv", items.getDesc().getBvid()));
                            }
                        });
                        break;
                    case 64:
                        DynamicColumnModel dynamicColumnModel = JSONObject.parseObject(items.getCard(), DynamicColumnModel.class);
                        helper.llColumnTurn.setVisibility(View.VISIBLE);
                        helper.tvContentTurn.setVisibility(View.GONE);
                        if (!StringUtil.isEmpty(dynamicColumnModel.getImage_urls().get(0))) {
                            helper.ivImageColumnTurn.setVisibility(View.VISIBLE);
                            GlideUtils.loadImage(mContext, dynamicColumnModel.getImage_urls().get(0), helper.ivImageColumnTurn);
                        } else {
                            helper.ivImageColumnTurn.setVisibility(View.GONE);
                        }
                        helper.tvTitleColumnTurn.setText(dynamicColumnModel.getTitle());
                        helper.tvIntroductionColumnTurn.setText(dynamicColumnModel.getSummary());
                        helper.llTurn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mContext.startActivity(new Intent(mContext, X5WebViewActivity.class)
                                        .putExtra("title", dynamicColumnModel.getTitle())
                                        .putExtra("url", "https://www.bilibili.com/read/cv" + dynamicColumnModel.getId()));
                            }
                        });
                        helper.setTag(R.id.ll_dynamic, "https://www.bilibili.com/read/cv" + dynamicColumnModel.getId());
                        break;
                    case 256:
                        DynamicMusicModel dynamicMusicModel = JSONObject.parseObject(items.getCard(), DynamicMusicModel.class);
                        helper.llMusicTurn.setVisibility(View.VISIBLE);
                        //文本内容
                        if (items.getDisplay().getEmoji_info() != null) {
                            CommonUtils.setReply(dynamicMusicModel.getIntro(), helper.tvContentTurn, items.getDisplay().getEmoji_info().getEmoji_details());
                        } else {
                            helper.tvContentTurn.setText(StringUtil.isEmpty(dynamicMusicModel.getIntro()) ? "投稿音乐" : dynamicMusicModel.getIntro());
                        }
                        //封面
                        if (!StringUtil.isEmpty(dynamicMusicModel.getCover())) {
                            GlideUtils.loadImage(mContext, dynamicMusicModel.getCover(), helper.ivImageMusicTurn);
                        }
                        helper.tvTitleMusicTurn.setText(dynamicMusicModel.getTitle());
                        helper.tvIntroductionMusicTurn.setText(dynamicMusicModel.getTypeInfo());
                        helper.llTurn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mContext.startActivity(new Intent(mContext, X5WebViewActivity.class)
                                        .putExtra("title", dynamicMusicModel.getTitle())
                                        .putExtra("url", dynamicMusicModel.getSchema()));
                            }
                        });
                        break;
                    case 512:
                        DynamicDramaModel dynamicDramaModel = JSONObject.parseObject(items.getCard(), DynamicDramaModel.class);
                        helper.llVideoTurn.setVisibility(View.VISIBLE);
                        helper.llViewTurn.setVisibility(View.VISIBLE);
                        helper.tvLiveTurn.setVisibility(View.GONE);
                        helper.tvContentTurn.setVisibility(View.GONE);
                        //头像
                        if (dynamicDramaModel.getApiSeasonInfo() != null && !StringUtil.isEmpty(dynamicDramaModel.getApiSeasonInfo().getCover())) {
                            GlideUtils.loadAvatar(mContext, dynamicDramaModel.getApiSeasonInfo().getCover(), helper.ivHeadTurn);
                        }
                        //昵称
                        if (dynamicDramaModel.getApiSeasonInfo() != null && !StringUtil.isEmpty(dynamicDramaModel.getApiSeasonInfo().getTitle())) {
                            helper.tvNameTurn.setText(dynamicDramaModel.getApiSeasonInfo().getType_name() + ": " + dynamicDramaModel.getApiSeasonInfo().getTitle());
                        }
                        //封面
                        if (!StringUtil.isEmpty(dynamicDramaModel.getCover())) {
                            CommonUtils.loadImageVideo(mContext, dynamicDramaModel.getCover(), helper.ivImageTurn);
                        }
                        //标题
                        if (!StringUtil.isEmpty(dynamicDramaModel.getNew_desc())) {
                            helper.tvTitleTurn.setText(dynamicDramaModel.getNew_desc());
                        }
                        helper.tvIntroductionTurn.setText("");
                        //播放量
                        helper.tvViewTurn.setText(CommonUtils.getNumber(dynamicDramaModel.getPlay_count()));
                        //弹幕数
                        helper.tvDanmakuTurn.setText(CommonUtils.getNumber(dynamicDramaModel.getBullet_count()));
                        helper.llTurn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mContext.startActivity(new Intent(mContext, X5WebViewActivity.class)
                                        .putExtra("title", dynamicDramaModel.getApiSeasonInfo().getTitle())
                                        .putExtra("url", dynamicDramaModel.getUrl()));
                            }
                        });
                        break;
                    case 2048:
                        DynamicActModel dynamicActModel = JSONObject.parseObject(items.getCard(), DynamicActModel.class);
                        helper.llMusicTurn.setVisibility(View.VISIBLE);
                        //文本内容
                        if (items.getDisplay().getEmoji_info() != null) {
                            CommonUtils.setReply(dynamicActModel.getVest().getContent(), helper.tvContentTurn, items.getDisplay().getEmoji_info().getEmoji_details());
                        } else {
                            helper.tvContentTurn.setText(StringUtil.isEmpty(dynamicActModel.getVest().getContent()) ? "参与活动" : dynamicActModel.getVest().getContent());
                        }
                        //封面
                        if (!StringUtil.isEmpty(dynamicActModel.getSketch().getCover_url())) {
                            GlideUtils.loadImage(mContext, dynamicActModel.getSketch().getCover_url(), helper.ivImageTurn);
                        }
                        helper.tvTitleMusicTurn.setText(dynamicActModel.getSketch().getTitle());
                        helper.tvIntroductionMusicTurn.setText(dynamicActModel.getSketch().getDesc_text());
                        helper.llTurn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mContext.startActivity(new Intent(mContext, X5WebViewActivity.class)
                                        .putExtra("title", dynamicActModel.getSketch().getTitle())
                                        .putExtra("url", dynamicActModel.getSketch().getTarget_url()));
                            }
                        });
                        break;
                    case 4200:
                        DynamicLiveCloseModel dynamicLiveCloseModel = JSONObject.parseObject(items.getCard(), DynamicLiveCloseModel.class);
                        helper.llVideoTurn.setVisibility(View.VISIBLE);
                        helper.llViewTurn.setVisibility(View.GONE);
                        helper.tvLiveTurn.setVisibility(View.VISIBLE);
                        helper.tvContentTurn.setVisibility(View.GONE);
                        //封面
                        if (!StringUtil.isEmpty(dynamicLiveCloseModel.getCover())) {
                            CommonUtils.loadImageVideo(mContext, dynamicLiveCloseModel.getCover(), helper.ivImageTurn);
                        }
                        if (dynamicLiveCloseModel.getLive_status() == 1) {
                            helper.tvLiveTurn.setText(dynamicLiveCloseModel.getArea_v2_name() + " · " + dynamicLiveCloseModel.getOnline() + "人气");
                        } else {
                            helper.tvLiveTurn.setText(dynamicLiveCloseModel.getArea_v2_name() + " 直播结束");
                        }
                        helper.tvIntroductionTurn.setText("");
                        helper.tvTitleTurn.setText(dynamicLiveCloseModel.getTitle());
                        helper.llTurn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mContext.startActivity(new Intent(mContext, X5WebViewActivity.class)
                                        .putExtra("title", dynamicLiveCloseModel.getTitle())
                                        .putExtra("url", dynamicLiveCloseModel.getLink()));
                            }
                        });
                        break;
                    case 4308:
                        DynamicLiveingModel dynamicLiveingModel = JSONObject.parseObject(items.getCard(), DynamicLiveingModel.class);
                        helper.llVideoTurn.setVisibility(View.VISIBLE);
                        helper.llViewTurn.setVisibility(View.GONE);
                        helper.tvLiveTurn.setVisibility(View.VISIBLE);
                        helper.tvContentTurn.setVisibility(View.GONE);
                        //封面
                        if (dynamicLiveingModel.getLive_play_info() != null && !StringUtil.isEmpty(dynamicLiveingModel.getLive_play_info().getCover())) {
                            CommonUtils.loadImageVideo(mContext, dynamicLiveingModel.getLive_play_info().getCover(), helper.ivImageTurn);
                        }
                        if (dynamicLiveingModel.getLive_play_info().getLive_status() == 1) {
                            helper.tvLiveTurn.setText(dynamicLiveingModel.getLive_play_info().getArea_name() + " · " + dynamicLiveingModel.getLive_play_info().getOnline() + "人气");
                        } else {
                            helper.tvLiveTurn.setText(dynamicLiveingModel.getLive_play_info().getArea_name());
                        }
                        helper.tvIntroductionTurn.setText("");
                        helper.tvTitleTurn.setText(dynamicLiveingModel.getLive_play_info().getTitle());
                        helper.llTurn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mContext.startActivity(new Intent(mContext, X5WebViewActivity.class)
                                        .putExtra("title", dynamicLiveingModel.getLive_play_info().getTitle())
                                        .putExtra("url", dynamicLiveingModel.getLive_play_info().getLink()));
                            }
                        });
                        break;
                }
                break;
            case 2:
                DynamicPicturesModel dynamicPicturesModel = JSONObject.parseObject(item.getCard(), DynamicPicturesModel.class);
                //图片
                helper.rvList.setVisibility(View.VISIBLE);
                GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, dynamicPicturesModel.getItem().getPictures_count() != 4 ? 3 : 2);
                helper.rvList.setLayoutManager(gridLayoutManager);
                imageAdapter = new ImageAdapter(dynamicPicturesModel.getItem().getPictures());
                imageAdapter.bindToRecyclerView(helper.rvList);
                imageAdapter.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        mContext.startActivity(new Intent(mContext, ImageActivity.class)
                                .putExtra("title", item.getDesc().getUser_profile().getInfo().getUname())
                                .putExtra("url", dynamicPicturesModel.getItem().getPictures().get(position).getImg_src()));
                    }
                });

                //文本内容
                if (item.getDisplay().getEmoji_info() != null) {
                    CommonUtils.setReply(dynamicPicturesModel.getItem().getDescription(), helper.tvContent, item.getDisplay().getEmoji_info().getEmoji_details());
                } else {
                    helper.tvContent.setText(StringUtil.isEmpty(dynamicPicturesModel.getItem().getDescription()) ? "分享图片" : dynamicPicturesModel.getItem().getDescription());
                }
                break;
            case 4:
                DynamicTurnModel dynamicTurnModelWord = JSONObject.parseObject(item.getCard(), DynamicTurnModel.class);

                //文本内容
                if (item.getDisplay().getEmoji_info() != null) {
                    CommonUtils.setReply(dynamicTurnModelWord.getItem().getContent(), helper.tvContent, item.getDisplay().getEmoji_info().getEmoji_details());
                } else {
                    helper.tvContent.setVisibility(StringUtil.isEmpty(dynamicTurnModelWord.getItem().getContent()) ? View.GONE : View.VISIBLE);
                    helper.tvContent.setText(dynamicTurnModelWord.getItem().getContent());
                }
                break;
            case 8:
                DynamicVideoModel dynamicVideoModel = JSONObject.parseObject(item.getCard(), DynamicVideoModel.class);
                helper.llVideo.setVisibility(View.VISIBLE);
                helper.llView.setVisibility(View.VISIBLE);
                helper.tvLive.setVisibility(View.GONE);
                //文本内容
                if (item.getDisplay().getEmoji_info() != null) {
                    CommonUtils.setReply(dynamicVideoModel.getDynamic(), helper.tvContent, item.getDisplay().getEmoji_info().getEmoji_details());
                } else {
                    helper.tvContent.setVisibility(StringUtil.isEmpty(dynamicVideoModel.getDynamic()) ? View.GONE : View.VISIBLE);
                    helper.tvContent.setText(dynamicVideoModel.getDynamic());
                }

                //评论
                helper.tvReply.setText(CommonUtils.getNumber(dynamicVideoModel.getStat().getReply(), "评论"));

                //封面
                if (!StringUtil.isEmpty(dynamicVideoModel.getPic())) {
                    CommonUtils.loadImageVideo(mContext, dynamicVideoModel.getPic(), helper.ivImage);
                }
                //标题
                if (!StringUtil.isEmpty(dynamicVideoModel.getTitle())) {
                    helper.tvTitle.setText(dynamicVideoModel.getTitle());
                }

                else {
                    helper.tvTitle.setText("");
                }

                //简介
                if (!StringUtil.isEmpty(dynamicVideoModel.getTitle())) {
                    helper.tvIntroduction.setText(dynamicVideoModel.getDesc());
                }
                else {
                    helper.tvIntroduction.setText("");
                }
                //播放量
                helper.tvView.setText(CommonUtils.getNumber(dynamicVideoModel.getStat().getView()));
                //弹幕数
                helper.tvDanmaku.setText(CommonUtils.getNumber(dynamicVideoModel.getStat().getDanmaku()));
                helper.llDynamic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mContext.startActivity(new Intent(mContext, BilBiliActivity.class).putExtra("bv", item.getDesc().getBvid()));
                    }
                });
                break;
            case 64:
                DynamicColumnModel dynamicColumnModel = JSONObject.parseObject(item.getCard(), DynamicColumnModel.class);
                helper.llColumn.setVisibility(View.VISIBLE);
                helper.tvContent.setVisibility(View.GONE);

                if (!StringUtil.isEmpty(dynamicColumnModel.getImage_urls().get(0))) {
                    helper.ivImageColumn.setVisibility(View.VISIBLE);
                    GlideUtils.loadImage(mContext, dynamicColumnModel.getImage_urls().get(0), helper.ivImageColumn);
                } else {
                    helper.ivImageColumn.setVisibility(View.GONE);
                }
                helper.tvTitleColumn.setText(dynamicColumnModel.getTitle());
                helper.tvIntroductionColumn.setText( dynamicColumnModel.getSummary());
                helper.llDynamic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mContext.startActivity(new Intent(mContext, X5WebViewActivity.class)
                                .putExtra("title", dynamicColumnModel.getTitle())
                                .putExtra("url", "https://www.bilibili.com/read/cv" + dynamicColumnModel.getId()));
                    }
                });

                break;
            case 256:
                DynamicMusicModel dynamicMusicModel = JSONObject.parseObject(item.getCard(), DynamicMusicModel.class);

                helper.llMusic.setVisibility(View.VISIBLE);
                //文本内容
                if (item.getDisplay().getEmoji_info() != null) {
                    CommonUtils.setReply(dynamicMusicModel.getIntro(), helper.tvContent, item.getDisplay().getEmoji_info().getEmoji_details());
                } else {
                    helper.tvContent.setVisibility(StringUtil.isEmpty(dynamicMusicModel.getIntro()) ? View.GONE : View.VISIBLE);
                    helper.tvContent.setText(StringUtil.isEmpty(dynamicMusicModel.getIntro()) ? "投稿音乐" : dynamicMusicModel.getIntro());
                }

                //封面
                if (!StringUtil.isEmpty(dynamicMusicModel.getCover())) {
                    GlideUtils.loadImage(mContext, dynamicMusicModel.getCover(), helper.ivImageMusic);
                }
                helper.tvTitleMusic.setText(dynamicMusicModel.getTitle());
                helper.tvIntroductionMusic.setText(dynamicMusicModel.getTypeInfo());
                helper.llDynamic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mContext.startActivity(new Intent(mContext, X5WebViewActivity.class)
                                .putExtra("title", dynamicMusicModel.getTitle())
                                .putExtra("url", dynamicMusicModel.getSchema()));
                    }
                });
                break;
            case 512:
                DynamicDramaModel dynamicDramaModel = JSONObject.parseObject(item.getCard(), DynamicDramaModel.class);
                helper.llVideo.setVisibility(View.VISIBLE);
                helper.llView.setVisibility(View.VISIBLE);
                helper.tvLive.setVisibility(View.GONE);
                helper.tvContent.setVisibility(View.GONE);
                //头像
                if (dynamicDramaModel.getApiSeasonInfo() != null && !StringUtil.isEmpty(dynamicDramaModel.getApiSeasonInfo().getCover())) {
                    GlideUtils.loadAvatar(mContext, dynamicDramaModel.getApiSeasonInfo().getCover(), helper.ivHead);
                }
                //昵称
                if (dynamicDramaModel.getApiSeasonInfo() != null && !StringUtil.isEmpty(dynamicDramaModel.getApiSeasonInfo().getTitle())) {
                    helper.tvName.setText(dynamicDramaModel.getApiSeasonInfo().getType_name() + ": " + dynamicDramaModel.getApiSeasonInfo().getTitle());
                }
                //封面
                if (!StringUtil.isEmpty(dynamicDramaModel.getCover())) {
                    CommonUtils.loadImageVideo(mContext, dynamicDramaModel.getCover(), helper.ivImage);
                }
                //标题
                if (!StringUtil.isEmpty(dynamicDramaModel.getNew_desc())) {
                    helper.tvTitle.setText(dynamicDramaModel.getNew_desc());
                }
                helper.tvIntroduction.setText("");
                //评论
                helper.tvReply.setText(CommonUtils.getNumber(dynamicDramaModel.getReply_count(), "评论"));
                //播放量
                helper.tvView.setText(CommonUtils.getNumber(dynamicDramaModel.getPlay_count()));
                //弹幕数
                helper.tvDanmaku.setText(CommonUtils.getNumber(dynamicDramaModel.getBullet_count()));
                helper.llDynamic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mContext.startActivity(new Intent(mContext, X5WebViewActivity.class)
                                .putExtra("title", dynamicDramaModel.getApiSeasonInfo().getTitle())
                                .putExtra("url", dynamicDramaModel.getUrl()));
                    }
                });
                break;
            case 2048:
                DynamicActModel dynamicActModel = JSONObject.parseObject(item.getCard(), DynamicActModel.class);
                helper.llMusic.setVisibility(View.VISIBLE);
                //文本内容
                if (item.getDisplay().getEmoji_info() != null) {
                    CommonUtils.setReply(dynamicActModel.getVest().getContent(), helper.tvContent, item.getDisplay().getEmoji_info().getEmoji_details());
                } else {
                    helper.tvContent.setText(StringUtil.isEmpty(dynamicActModel.getVest().getContent()) ? "参与活动" : dynamicActModel.getVest().getContent());
                }
                //封面
                if (!StringUtil.isEmpty(dynamicActModel.getSketch().getCover_url())) {
                    GlideUtils.loadImage(mContext, dynamicActModel.getSketch().getCover_url(), helper.ivImage);
                }
                helper.tvTitleMusic.setText(dynamicActModel.getSketch().getTitle());
                helper.tvIntroductionMusic.setText(dynamicActModel.getSketch().getDesc_text());
                helper.llDynamic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mContext.startActivity(new Intent(mContext, X5WebViewActivity.class)
                                .putExtra("title", dynamicActModel.getSketch().getTitle())
                                .putExtra("url", dynamicActModel.getSketch().getTarget_url()));
                    }
                });
                break;
            case 4200:
                DynamicLiveCloseModel dynamicLiveCloseModel = JSONObject.parseObject(item.getCard(), DynamicLiveCloseModel.class);
                helper.llVideo.setVisibility(View.VISIBLE);
                helper.llView.setVisibility(View.GONE);
                helper.tvLive.setVisibility(View.VISIBLE);
                helper.tvContent.setVisibility(View.GONE);
                //封面
                if (!StringUtil.isEmpty(dynamicLiveCloseModel.getCover())) {
                    CommonUtils.loadImageVideo(mContext, dynamicLiveCloseModel.getCover(), helper.ivImage);
                }
                if (dynamicLiveCloseModel.getLive_status() == 1) {
                    helper.tvLive.setText(dynamicLiveCloseModel.getArea_v2_name() + " · " + dynamicLiveCloseModel.getOnline() + "人气");
                } else {
                    helper.tvLive.setText(dynamicLiveCloseModel.getArea_v2_name() + " 直播结束");
                }
                helper.tvIntroduction.setText("");
                helper.tvTitle.setText(dynamicLiveCloseModel.getTitle());

                helper.llDynamic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mContext.startActivity(new Intent(mContext, X5WebViewActivity.class)
                                .putExtra("title", dynamicLiveCloseModel.getTitle())
                                .putExtra("url", dynamicLiveCloseModel.getLink()));
                    }
                });
            case 4308:
                DynamicLiveingModel dynamicLiveingModel = JSONObject.parseObject(item.getCard(), DynamicLiveingModel.class);
                helper.llVideo.setVisibility(View.VISIBLE);
                helper.llView.setVisibility(View.GONE);
                helper.tvLive.setVisibility(View.VISIBLE);
                helper.tvContent.setVisibility(View.GONE);
                //封面
                if (dynamicLiveingModel.getLive_play_info() != null && !StringUtil.isEmpty(dynamicLiveingModel.getLive_play_info().getCover())) {
                    CommonUtils.loadImageVideo(mContext, dynamicLiveingModel.getLive_play_info().getCover(), helper.ivImage);
                }
                if (dynamicLiveingModel.getLive_play_info().getLive_status() == 1) {
                    helper.tvLive.setText(dynamicLiveingModel.getLive_play_info().getArea_name() + " · " + dynamicLiveingModel.getLive_play_info().getOnline() + "人气");
                } else {
                    helper.tvLive.setText(dynamicLiveingModel.getLive_play_info().getArea_name());
                }
                helper.tvIntroduction.setText("");
                helper.tvTitle.setText(dynamicLiveingModel.getLive_play_info().getTitle());
                helper.llDynamic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mContext.startActivity(new Intent(mContext, X5WebViewActivity.class)
                                .putExtra("title", dynamicLiveingModel.getLive_play_info().getTitle())
                                .putExtra("url", dynamicLiveingModel.getLive_play_info().getLink()));
                    }
                });
                break;
        }
    }

    class MyViewHolder extends BaseViewHolder {

        @BindView(R.id.iv_head)
        ImageView ivHead;
        @BindView(R.id.iv_pendant)
        ImageView ivPendant;
        @BindView(R.id.iv_identity)
        ImageView ivIdentity;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_create_time)
        TextView tvCreateTime;
        @BindView(R.id.tv_hint)
        TextView tvHint;
        @BindView(R.id.ll_hint)
        LinearLayout llHint;
        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.rv_list)
        RecyclerView rvList;
        @BindView(R.id.iv_image_column)
        ImageView ivImageColumn;
        @BindView(R.id.tv_title_column)
        TextView tvTitleColumn;
        @BindView(R.id.tv_introduction_column)
        TextView tvIntroductionColumn;
        @BindView(R.id.ll_column)
        LinearLayout llColumn;
        @BindView(R.id.iv_image)
        ImageView ivImage;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_introduction)
        TextView tvIntroduction;
        @BindView(R.id.tv_view)
        TextView tvView;
        @BindView(R.id.tv_danmaku)
        TextView tvDanmaku;
        @BindView(R.id.ll_view)
        LinearLayout llView;
        @BindView(R.id.tv_live)
        TextView tvLive;
        @BindView(R.id.ll_video)
        LinearLayout llVideo;
        @BindView(R.id.iv_image_music)
        ImageView ivImageMusic;
        @BindView(R.id.tv_title_music)
        TextView tvTitleMusic;
        @BindView(R.id.tv_introduction_music)
        TextView tvIntroductionMusic;
        @BindView(R.id.ll_music)
        LinearLayout llMusic;
        @BindView(R.id.iv_head_turn)
        ImageView ivHeadTurn;
        @BindView(R.id.tv_name_turn)
        TextView tvNameTurn;
        @BindView(R.id.tv_hint_turn)
        TextView tvHintTurn;
        @BindView(R.id.ll_hint_turn)
        LinearLayout llHintTurn;
        @BindView(R.id.tv_content_turn)
        TextView tvContentTurn;
        @BindView(R.id.rv_list_turn)
        RecyclerView rvListTurn;
        @BindView(R.id.iv_image_column_turn)
        ImageView ivImageColumnTurn;
        @BindView(R.id.tv_title_column_turn)
        TextView tvTitleColumnTurn;
        @BindView(R.id.tv_introduction_column_turn)
        TextView tvIntroductionColumnTurn;
        @BindView(R.id.ll_column_turn)
        LinearLayout llColumnTurn;
        @BindView(R.id.iv_image_turn)
        ImageView ivImageTurn;
        @BindView(R.id.tv_title_turn)
        TextView tvTitleTurn;
        @BindView(R.id.tv_introduction_turn)
        TextView tvIntroductionTurn;
        @BindView(R.id.tv_view_turn)
        TextView tvViewTurn;
        @BindView(R.id.tv_danmaku_turn)
        TextView tvDanmakuTurn;
        @BindView(R.id.ll_view_turn)
        LinearLayout llViewTurn;
        @BindView(R.id.tv_live_turn)
        TextView tvLiveTurn;
        @BindView(R.id.ll_video_turn)
        LinearLayout llVideoTurn;
        @BindView(R.id.iv_image_music_turn)
        ImageView ivImageMusicTurn;
        @BindView(R.id.tv_title_music_turn)
        TextView tvTitleMusicTurn;
        @BindView(R.id.tv_introduction_music_turn)
        TextView tvIntroductionMusicTurn;
        @BindView(R.id.ll_music_turn)
        LinearLayout llMusicTurn;
        @BindView(R.id.iv_card_turn)
        ImageView ivCardTurn;
        @BindView(R.id.tv_card_title_turn)
        TextView tvCardTitleTurn;
        @BindView(R.id.tv_card_content_turn)
        TextView tvCardContentTurn;
        @BindView(R.id.tv_card_content_second_turn)
        TextView tvCardContentSecondTurn;
        @BindView(R.id.iv_draw_turn)
        ImageView ivDrawTurn;
        @BindView(R.id.tv_card_content_draw_turn)
        TextView tvCardContentDrawTurn;
        @BindView(R.id.ll_draw_turn)
        LinearLayout llDrawTurn;
        @BindView(R.id.tv_button_turn)
        TextView tvButtonTurn;
        @BindView(R.id.ll_card_turn)
        LinearLayout llCardTurn;
        @BindView(R.id.ll_turn)
        LinearLayout llTurn;
        @BindView(R.id.iv_card)
        ImageView ivCard;
        @BindView(R.id.tv_card_title)
        TextView tvCardTitle;
        @BindView(R.id.tv_card_content)
        TextView tvCardContent;
        @BindView(R.id.tv_card_content_second)
        TextView tvCardContentSecond;
        @BindView(R.id.iv_draw)
        ImageView ivDraw;
        @BindView(R.id.tv_card_content_draw)
        TextView tvCardContentDraw;
        @BindView(R.id.ll_draw)
        LinearLayout llDraw;
        @BindView(R.id.tv_button)
        TextView tvButton;
        @BindView(R.id.ll_card)
        LinearLayout llCard;
        @BindView(R.id.iv_turn)
        ImageView ivTurn;
        @BindView(R.id.tv_turn)
        TextView tvTurn;
        @BindView(R.id.iv_reply)
        ImageView ivReply;
        @BindView(R.id.tv_reply)
        TextView tvReply;
        @BindView(R.id.iv_like)
        ImageView ivLike;
        @BindView(R.id.tv_like)
        TextView tvLike;
        @BindView(R.id.tv_up_like)
        TextView tvUpLike;
        @BindView(R.id.ll_dynamic)
        LinearLayout llDynamic;
        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
