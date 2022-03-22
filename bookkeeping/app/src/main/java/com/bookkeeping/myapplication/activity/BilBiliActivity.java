package com.bookkeeping.myapplication.activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONObject;
import com.bookkeeping.myapplication.R;
import com.bookkeeping.myapplication.adapter.AuthAdapter;
import com.bookkeeping.myapplication.adapter.ReplyAdapter;
import com.bookkeeping.myapplication.base.BaseActivity;
import com.bookkeeping.myapplication.model.bilibili.FavLikeCoinModel;
import com.bookkeeping.myapplication.model.bilibili.RepliesModel;
import com.bookkeeping.myapplication.model.bilibili.ReplyModel;
import com.bookkeeping.myapplication.model.bilibili.ResponseModel;
import com.bookkeeping.myapplication.model.bilibili.StaffModel;
import com.bookkeeping.myapplication.model.bilibili.StatModel;
import com.bookkeeping.myapplication.model.bilibili.VideoModel;
import com.bookkeeping.myapplication.model.bilibili.VideoUrlModel;
import com.bookkeeping.myapplication.util.CommonUtils;
import com.bookkeeping.myapplication.util.DateUtil;
import com.bookkeeping.myapplication.util.GlideUtils;
import com.bookkeeping.myapplication.util.StorageCustomerInfo02Util;
import com.bookkeeping.myapplication.util.StringUtil;
import com.bookkeeping.myapplication.util.okHttp.OkHttp3Util;
import com.bookkeeping.myapplication.util.okgo.OkClient;
import com.bookkeeping.myapplication.view.DanmakuVideoPlayer;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack;
import com.shuyu.gsyvideoplayer.listener.LockClickListener;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.Inflater;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okio.BufferedSink;
import okio.Okio;
import okio.Sink;

/**
 * @author : qiuyiyang
 * @date : 2021/3/29  14:25
 * @desc :
 */
public class BilBiliActivity extends BaseActivity {

    @BindView(R.id.detail_player)
    DanmakuVideoPlayer detailPlayer;
    @BindView(R.id.iv_head)
    ImageView ivHead;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_attention)
    TextView tvAttention;
    @BindView(R.id.tv_is_attention)
    TextView tvIsAttention;
    @BindView(R.id.tv_comment)
    TextView tvComment;
    @BindView(R.id.tv_view)
    TextView tvView;
    @BindView(R.id.tv_danmaku)
    TextView tvDanmaku;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.tv_BV)
    TextView tvBV;
    @BindView(R.id.tv_top)
    TextView tvTop;
    @BindView(R.id.tv_introduction)
    TextView tvIntroduction;
    @BindView(R.id.tv_auth_number)
    TextView tvAuthNumber;
    @BindView(R.id.ll_auth)
    LinearLayout llAuth;
    @BindView(R.id.rv_auth_list)
    RecyclerView rvAuthList;
    @BindView(R.id.iv_like)
    ImageView ivLike;
    @BindView(R.id.tv_like)
    TextView tvLike;
    @BindView(R.id.iv_coin)
    ImageView ivCoin;
    @BindView(R.id.tv_coin)
    TextView tvCoin;
    @BindView(R.id.iv_favorites)
    ImageView ivFavorites;
    @BindView(R.id.tv_favorites)
    TextView tvFavorites;
    @BindView(R.id.tv_share)
    TextView tvShare;
    @BindView(R.id.tv_reply)
    TextView tvReply;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.nsv)
    NestedScrollView nsv;
    private int pageIndex = 1, count;
    private AuthAdapter authAdapter;
    private ReplyAdapter replyAdapter;
    private List<StaffModel> authList = new ArrayList<>();
    private List<RepliesModel> repliesList = new ArrayList<>();
    private OrientationUtils orientationUtils;
    private boolean isPlay;
    private boolean isPause;
    private boolean isDestory;
    private File file;
    private String bv;
    private Dialog loadingDialog;
    private VideoModel videoModel = new VideoModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int initLayout() {
        return R.layout.act_comment;
    }

    @Override
    public void initData() {
        rvAuthList.setNestedScrollingEnabled(false);
        rvList.setNestedScrollingEnabled(false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvAuthList.setLayoutManager(linearLayoutManager);
        authAdapter = new AuthAdapter(authList);
        authAdapter.bindToRecyclerView(rvAuthList);
        bv = getIntent().getStringExtra("bv");
        rvList.setLayoutManager(new LinearLayoutManager(context));
        replyAdapter = new ReplyAdapter(repliesList);
        replyAdapter.setType("normal");
        replyAdapter.bindToRecyclerView(rvList);
        detailPlayer.setListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
            }
        });
        loadingDialog = CommonUtils.createLoadingDialog(context, "数据加载中……", false);
        loadingDialog.show();
        loadDate();
        initListener();
    }

    private void initListener() {
        nsv.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                   if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
                    if (count > 0 && count > pageIndex) {
                        pageIndex++;
                        if (!StringUtil.isEmpty(videoModel.getAid())) {
                            loadReplies();
                        }
                    } else {
                        replyAdapter.loadMoreEnd(true);
                    }
                }
            }
        });
        replyAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (repliesList.get(position).getReplies()!=null&&repliesList.get(position).getReplies().size()>0) {
                    startActivity(new Intent(context, ReplyDetailActivity.class)
                            .putExtra("id", repliesList.get(position).getRpid())
                            .putExtra("aid", videoModel.getAid()));
                }
            }
        });
    }

    private void loadDate() {
        HttpParams httpParams = new HttpParams();
        httpParams.put("bvid", bv);
        OkClient.getInstance().get("https://api.bilibili.com/x/web-interface/view", httpParams, new OkClient.EntityCallBack<ResponseModel>(context, ResponseModel.class) {
            @Override
            public void onError(Response<ResponseModel> response) {
                super.onError(response);
                loadingDialog.dismiss();
            }

            @Override
            public void onSuccess(Response<ResponseModel> response) {
                super.onSuccess(response);
                ResponseModel model = response.body();
                if (model == null) {
                    loadingDialog.dismiss();
                    return;
                }
                if (model.getCode() == 0) {
                    videoModel = JSONObject.parseObject(model.getData(), VideoModel.class);
                    detailPlayer.setTitle(videoModel.getTitle());
                    if (!isDestory) {
                        GlideUtils.loadAvatar(context, videoModel.getOwner().getFace(), ivHead);
                    }
                    tvName.setText(videoModel.getOwner().getName());
                    tvComment.setText(videoModel.getDynamic());
                    tvDate.setText(DateUtil.formatDateToMD(videoModel.getPubdate() * 1000));
                    authList = videoModel.getStaff();
                    if (authList != null && authList.size() > 0) {
                        authAdapter.setNewData(authList);
                        tvAuthNumber.setText("（" + authList.size() + "）");
                    } else {
                        llAuth.setVisibility(View.GONE);
                        rvAuthList.setVisibility(View.GONE);
                    }

                    tvView.setText(CommonUtils.getNumber(videoModel.getStat().getView()));
                    tvDanmaku.setText(CommonUtils.getNumber(videoModel.getStat().getDanmaku()));
                    tvBV.setText(bv);
                    tvIntroduction.setText(videoModel.getDesc());

                    tvLike.setText(CommonUtils.getNumber(videoModel.getStat().getLike(), "点赞"));
                    tvCoin.setText(CommonUtils.getNumber(videoModel.getStat().getCoin(), "投币"));
                    tvFavorites.setText(CommonUtils.getNumber(videoModel.getStat().getFavorite(), "收藏"));
                    tvShare.setText(CommonUtils.getNumber(videoModel.getStat().getShare(), "分享"));
                    tvReply.setText("评论区 " + CommonUtils.getNumber(videoModel.getStat().getReply(), "") + "条");
                    if (videoModel.getStat().getHis_rank() != 0) {
                        tvTop.setText("最高Top" + videoModel.getStat().getHis_rank());
                        tvTop.setVisibility(View.VISIBLE);
                    } else {
                        tvTop.setVisibility(View.GONE);
                    }

                    loadFavLikeCoin();
                    getStat(videoModel.getOwner().getMid());
                    loadReplies();
                    getVideoUrl();
                    loadingDialog.dismiss();
                }
            }
        });
    }

    private void loadCover(ImageView imageView, String url) {
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageResource(R.drawable.launch);
        Glide.with(context).load(url).into(imageView);
    }


    private GSYVideoPlayer getCurPlay() {
        if (detailPlayer.getFullWindowPlayer() != null) {
            return detailPlayer.getFullWindowPlayer();
        }
        return detailPlayer;
    }

    /**
     * orientationUtils 和  detailPlayer.onConfigurationChanged 方法是用于触发屏幕旋转的
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //如果旋转了就全屏
        if (isPlay && !isPause) {
            detailPlayer.onConfigurationChanged(this, newConfig, orientationUtils, true, true);
        }
    }

    @Override
    public void onBackPressed() {

        if (orientationUtils != null) {
            orientationUtils.backToProtVideo();
        }

        if (GSYVideoManager.backFromWindowFull(this)) {
            return;
        }
        super.onBackPressed();
    }


    @Override
    protected void onPause() {
        getCurPlay().onVideoPause();
        super.onPause();
        isPause = true;
    }

    @Override
    protected void onResume() {
        getCurPlay().onVideoResume();
        super.onResume();
        isPause = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isPlay) {
            getCurPlay().release();
        }
        //GSYPreViewManager.instance().releaseMediaPlayer();
        if (orientationUtils != null) {
            orientationUtils.releaseListener();
        }
        isDestory = true;
    }

    private void loadFavLikeCoin() {
        HttpParams httpParams = new HttpParams();
        httpParams.put("bvid", videoModel.getBvid());
        httpParams.put("oid", videoModel.getAid());
        OkClient.getInstance().get("https://api.bilibili.com/x/web-interface/archive/relation", httpParams, new OkClient.EntityCallBack<ResponseModel>(context, ResponseModel.class) {
            @Override
            public void onError(Response<ResponseModel> response) {
                super.onError(response);
            }

            @Override
            public void onSuccess(Response<ResponseModel> response) {
                super.onSuccess(response);
                ResponseModel model = response.body();
                if (model == null) {
                    return;
                }
                if (model.getCode() == 0) {

                    FavLikeCoinModel favLikeCoinModel = JSONObject.parseObject(model.getData(), FavLikeCoinModel.class);
                    if (favLikeCoinModel.isAttention()) {
                        tvIsAttention.setText("已关注");
                        tvIsAttention.setTextColor(getResources().getColor(R.color.text_color));
                        tvIsAttention.setBackgroundResource(R.drawable.shape_solid_gray_corner_2);
                    } else {
                        tvIsAttention.setText("关注");
                        tvIsAttention.setTextColor(getResources().getColor(R.color.pink));
                        tvIsAttention.setBackgroundResource(R.drawable.shape_solid_pink_corner_2);
                    }
                    if (favLikeCoinModel.isLike()) {
                        ivLike.setImageResource(R.drawable.like);
                        tvLike.setTextColor(getResources().getColor(R.color.background));
                    } else {
                        ivLike.setImageResource(R.drawable.unlike);
                        tvLike.setTextColor(getResources().getColor(R.color.text_color));
                    }
                    if (favLikeCoinModel.getCoin() > 0) {
                        ivCoin.setImageResource(R.drawable.coin);
                        tvCoin.setTextColor(getResources().getColor(R.color.background));
                    } else {
                        ivCoin.setImageResource(R.drawable.uncoin);
                        tvCoin.setTextColor(getResources().getColor(R.color.text_color));
                    }
                    if (favLikeCoinModel.isFavorite()) {
                        ivFavorites.setImageResource(R.drawable.favorites);
                        tvFavorites.setTextColor(getResources().getColor(R.color.background));
                    } else {
                        ivFavorites.setImageResource(R.drawable.unfavorites);
                        tvFavorites.setTextColor(getResources().getColor(R.color.text_color));
                    }

                }
            }
        });
    }

    private void getStat(String mid) {
        HttpParams httpParams = new HttpParams();
        httpParams.put("vmid", mid);
        OkClient.getInstance().get("https://api.bilibili.com/x/relation/stat", httpParams, new OkClient.EntityCallBack<ResponseModel>(context, ResponseModel.class) {
            @Override
            public void onError(Response<ResponseModel> response) {
                super.onError(response);
            }

            @Override
            public void onSuccess(Response<ResponseModel> response) {
                super.onSuccess(response);
                ResponseModel model = response.body();
                if (model == null) {
                    return;
                }
                if (model.getCode() == 0) {
                    StatModel statModel = JSONObject.parseObject(model.getData(), StatModel.class);
                    tvAttention.setText(CommonUtils.getNumber(statModel.getFollower()) + "粉丝");
                }
            }
        });
    }

    private void getVideoUrl() {
        HttpParams httpParams = new HttpParams();
        httpParams.put("bvid", videoModel.getBvid());
        httpParams.put("cid", videoModel.getCid());
        httpParams.put("qn", "64");
        OkClient.getInstance().get("https://api.bilibili.com/x/player/playurl", httpParams, new OkClient.EntityCallBack<ResponseModel>(context, ResponseModel.class) {
            @Override
            public void onError(Response<ResponseModel> response) {
                super.onError(response);
            }

            @Override
            public void onSuccess(Response<ResponseModel> response) {
                super.onSuccess(response);
                ResponseModel model = response.body();
                if (model == null) {
                    return;
                }
                if (model.getCode() == 0) {
                    if (!isDestory) {
                        VideoUrlModel videoUrlModel = JSONObject.parseObject(model.getData().replace("null", "\"\""), VideoUrlModel.class);
                        setVideoPlay(CommonUtils.decode(videoUrlModel.getDurl().get(0).getUrl()));
                    }
                }
            }
        });
    }

    private void loadReplies() {
        loadingDialog.show();
        HttpParams httpParams = new HttpParams();
        httpParams.put("bvid", videoModel.getBvid());
        httpParams.put("pn", pageIndex);
        httpParams.put("type", "1");
        httpParams.put("oid", videoModel.getAid());
        httpParams.put("sort", "1");
        OkClient.getInstance().get("http://api.bilibili.com/x/v2/reply", httpParams, new OkClient.EntityCallBack<ResponseModel>(context, ResponseModel.class) {
            @Override
            public void onError(Response<ResponseModel> response) {
                loadingDialog.dismiss();
                super.onError(response);
            }

            @Override
            public void onSuccess(Response<ResponseModel> response) {
                loadingDialog.dismiss();
                super.onSuccess(response);
                ResponseModel model = response.body();
                if (model == null) {
                    return;
                }
                if (model.getCode() == 0) {
                    ReplyModel replyModel = JSONObject.parseObject(model.getData(), ReplyModel.class);
                    count = CommonUtils.getPage(replyModel.getPage().getCount(),replyModel.getPage().getSize());
                    if (pageIndex==1&&replyModel.getUpper().getTop()!=null){
                        repliesList.add(replyModel.getUpper().getTop());
                        replyAdapter.setTop(true);
                    }
                    repliesList.addAll(replyModel.getReplies());
                    replyAdapter.setNewData(repliesList);
                }
            }
        });
    }


    public void getDanmaku(String cid) {
        final Request request = new Request.Builder()
                .get()
                .url("https://api.bilibili.com/x/v1/dm/list.so?oid=" + cid)
                .build();
        Call call = OkHttp3Util.getInstance().newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                Sink sink = null;
                BufferedSink bufferedSink = null;
                try {
                    File file = new File(CommonUtils.getVideoPath(), cid + ".xml");
                    if (!file.exists()) {
                        file.createNewFile();
                    }
                    sink = Okio.sink(file);//打开目标文件路径的sink
                    byte[] decompressBytes = decompress(response.body().bytes());//调用解压函数进行解压，返回包含解压后数据的byte数组
                    bufferedSink = Okio.buffer(sink);
                    bufferedSink.write(decompressBytes);//将解压后数据写入文件（sink）中
                    bufferedSink.close();
                    if (!isDestory) {
                        ((DanmakuVideoPlayer) detailPlayer.getCurrentPlayer()).setDanmaKuStream(file);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (bufferedSink != null) {
                        bufferedSink.close();
                    }
                }
            }
        });
    }

    public static byte[] decompress(byte[] data) {
        byte[] output;

        Inflater decompresser = new Inflater(true);
        decompresser.reset();
        decompresser.setInput(data);

        ByteArrayOutputStream o = new ByteArrayOutputStream(data.length);
        try {
            byte[] buf = new byte[1024];
            while (!decompresser.finished()) {
                int i = decompresser.inflate(buf);
                o.write(buf, 0, i);
            }
            output = o.toByteArray();
        } catch (Exception e) {
            output = data;
            e.printStackTrace();
        } finally {
            try {
                o.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        decompresser.end();
        return output;
    }

    private void setVideoPlay(String url) {
        //必须在setUp之前设置
        detailPlayer.setShrinkImageRes(R.drawable.custom_shrink);
        detailPlayer.setEnlargeImageRes(R.drawable.custom_enlarge);
        //增加封面
        ImageView imageView = new ImageView(this);
        loadCover(imageView, videoModel.getPic());
        detailPlayer.setThumbImageView(imageView);
        detailPlayer.setDanmaKuShow(false);
        detailPlayer.getBackButton().setVisibility(View.VISIBLE);

        file = new File(CommonUtils.getVideoPath());
        Map<String, String> mapHeadData = new HashMap<>();
        mapHeadData.put("Referer", "https://www.bilibili.com/video/" + videoModel.getBvid());
        mapHeadData.put("method", "GET");
        mapHeadData.put("Sec-Fetch-Mode", "cors");
        mapHeadData.put("Accept", "text/html,application/xhtml+xml,application/xml;video/x-flv;q=0.9,image/webp,*/*;q=0.8");
        mapHeadData.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/89.0.4389.90 Safari/537.36");
        detailPlayer.setUp(url, false, file, mapHeadData, videoModel.getTitle());

        //外部辅助的旋转，帮助全屏
        orientationUtils = new OrientationUtils(this, detailPlayer);
        //初始化不打开外部的旋转
        orientationUtils.setEnable(false);

        detailPlayer.setIsTouchWiget(true);
        //关闭自动旋转
        detailPlayer.setRotateViewAuto(false);
        detailPlayer.setLockLand(false);
        detailPlayer.setShowFullAnimation(false);
        detailPlayer.setNeedLockFull(true);
        detailPlayer.setReleaseWhenLossAudio(false);

        //detailPlayer.setOpenPreView(true);
        detailPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //直接横屏
                orientationUtils.resolveByClick();

                //第一个true是否需要隐藏actionbar，第二个true是否需要隐藏statusbar
                detailPlayer.startWindowFullscreen(context, false, false);
            }
        });

        detailPlayer.setVideoAllCallBack(new GSYSampleCallBack() {
            @Override
            public void onPrepared(String url, Object... objects) {
                super.onPrepared(url, objects);
                //开始播放了才能旋转和全屏
                orientationUtils.setEnable(detailPlayer.isRotateWithSystem());
                isPlay = true;
                getDanmaku(videoModel.getCid());
                detailPlayer.setDanmaKuShow(true);
            }

            @Override
            public void onAutoComplete(String url, Object... objects) {
                super.onAutoComplete(url, objects);
            }

            @Override
            public void onClickStartError(String url, Object... objects) {
                super.onClickStartError(url, objects);
            }

            @Override
            public void onQuitFullscreen(String url, Object... objects) {
                super.onQuitFullscreen(url, objects);
                nsv.scrollTo(0, 0);
            }
        });

        detailPlayer.setLockClickListener(new LockClickListener() {
            @Override
            public void onClick(View view, boolean lock) {
                if (orientationUtils != null) {
                    //配合下方的onConfigurationChanged
                    orientationUtils.setEnable(!lock);
                }
            }
        });
    }
}
