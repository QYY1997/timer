package com.timer.com;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Range;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.timer.com.bean.EnRollModel;
import com.timer.com.bean.ProgressEvent;
import com.timer.com.bean.ResultModel;
import com.timer.com.bean.ScoreListModel;
import com.timer.com.bean.VideoInfo;
import com.timer.com.jni.FFmpegCmd;
import com.timer.com.util.Gutil;
import com.timer.com.util.MediaTool;
import com.timer.com.util.OkClient;
import com.timer.com.util.StorageCustomerInfoUtil;
import com.timer.com.util.StringUtil;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TranscodeActivity extends BaseActivity {
    private final static int CODE_REQUEST_WRITE_EXTERNAL = 0x100;
    private final static int SETTING = 0x001;
    private final static int PICK_VIDEO_REQUEST = 0x002;
    private final static int REQUEST_CAMERA2 = 0x003;
    private final static int CHOOSE = 0x004;
    private final static int LOCALLIST = 0x005;
    private static final String TAG = "TranscodeActivityTimer";
    @BindView(R.id.tv_left)
    TextView tvLeft;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right2)
    TextView tvRight2;
    @BindView(R.id.tv_right)
    TextView tvRight;
//    @BindView(R.id.iv_cover)
//    ImageView ivCover;
    @BindView(R.id.tv_last_page)
    TextView tvLastPage;
    @BindView(R.id.tv_next_page)
    TextView tvNextPage;
    @BindView(R.id.ll_page)
    LinearLayout llPage;
    @BindView(R.id.tv_last_paragraph)
    TextView tvLastParagraph;
    @BindView(R.id.tv_next_paragraph)
    TextView tvNextParagraph;
    @BindView(R.id.ll_paragraph)
    LinearLayout llParagraph;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.rv_list2)
    RecyclerView rvList2;
    @BindView(R.id.rl_img)
    RelativeLayout rlImg;
    @BindView(R.id.seek_bar)
    SeekBar seekBar;
    @BindView(R.id.tv_choose)
    TextView tvChoose;
    @BindView(R.id.tv_start)
    TextView tvStart;
    @BindView(R.id.tv_before_n)
    TextView tvBeforeN;
    @BindView(R.id.tv_before)
    TextView tvBefore;
    @BindView(R.id.tv_previous)
    TextView tvPrevious;
    @BindView(R.id.tv_play)
    TextView tvPlay;
    @BindView(R.id.tv_next)
    TextView tvNext;
    @BindView(R.id.tv_after)
    TextView tvAfter;
    @BindView(R.id.tv_after_n)
    TextView tvAfterN;
    @BindView(R.id.tv_end)
    TextView tvEnd;
    @BindView(R.id.tv_recording)
    TextView tvRecording;
    @BindView(R.id.iv_start_flag)
    ImageView ivStartFlag;
    @BindView(R.id.surfaceview)
    SurfaceView surfaceview;
    @BindView(R.id.tv_now_paragraph)
    TextView tvNowParagraph;
    @BindView(R.id.ll_red_line)
    LinearLayout llRedLine;

    private String mVideoPath;
//    private String path;
//    private List<String> paths = new ArrayList<>();
//    private List<VideoInfo> videos = new ArrayList<>();
    private List<EnRollModel> enRollModelList1 = new ArrayList<>();
    private List<EnRollModel> enRollModelList2 = new ArrayList<>();
    private VideoInfo mInfo;
//    private String basePathPic;
//    private String basePathVideo;
    private int frameRate;
    private long lastClickTime;
    private Thread payThread;
    private int delayTime;
    private int start, startPage;
    private boolean autoStart;
    private boolean yun;
    private int addTime = 0;
    private int videoPage = 1, videoCount, time;
    private int screenWidth;
    private EnRollListAdapter enRollListAdapter1;
    private EnRollListAdapter enRollListAdapter2;
    private MediaPlayer mPlayer;
    private ProgressDialog progressDialog;
    private int scorePage = 0;
    private String demandId;
    private boolean last, first;
    private long videoTime;
    private long startTime;
    private int group,road;
    private Integer deviceLevel;

    Comparator<EnRollModel> comparator = new Comparator<EnRollModel>() {
        @Override
        public int compare(EnRollModel o1, EnRollModel o2) {
            if (Integer.parseInt(o1.getRoad().replace("-", "")) > Integer.parseInt(o2.getRoad().replace("-", ""))) {
                return 1;
            } else {
                return -1;
            }
        }
    };

    private View.OnTouchListener movingEventListener = new View.OnTouchListener() {
        int lastX, x;//按下的位置
        int moveX;//移动的位置
        float nowX;

        @SuppressLint("ClickableViewAccessibility")
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    //todo 获取按下位置的像素坐标
                    lastX = (int) event.getRawX();
                    x = (int) event.getRawX();
                    break;
                case MotionEvent.ACTION_MOVE:
                    //设置移动的的位置
                    moveX = (int) event.getRawX();
                    //设置移动距离不超出屏幕
                    moveX = Math.min(moveX, screenWidth);
                    moveX = Math.max(moveX, 0);
                    //设置最终位置，不超出屏幕范围
                    nowX = llRedLine.getX() + (moveX - lastX);
                    llRedLine.setX(nowX < 0 ? 0 : (nowX > screenWidth - v.getWidth() ? screenWidth - v.getWidth() : nowX));
                    //记录最后移动位置
                    lastX = moveX;
                    break;
                case MotionEvent.ACTION_UP:
                    //检测移动的距离，如果很微小可以认为是点击事件
                    if (Math.abs(event.getRawX() - this.x) < 10) {
                        try {
                            Field field = View.class.getDeclaredField("mListenerInfo");
                            field.setAccessible(true);
                            Object object = field.get(v);
                            field = object.getClass().getDeclaredField("mOnClickListener");
                            field.setAccessible(true);
                            object = field.get(object);
                            if (object instanceof View.OnClickListener) {
                                ((View.OnClickListener) object).onClick(v);
                            }
                        } catch (Exception e) {
                        }
                    }
                    break;
            }
            return true;
        }
    };

    @SuppressLint("HandlerLeak")
    private Handler mHandlers = new Handler() {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
//                case 1:
//                    File file = new File((String) msg.obj);
//                    if (file != null) {
//                        play(false);
//                        ivCover.setVisibility(View.VISIBLE);
//                        Glide.with(context).load(file).asBitmap().into(new SimpleTarget<Bitmap>() {
//                            @Override
//                            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
//                                ivCover.setImageBitmap(resource);
//                            }
//                        });
//                    }
//                    loadingDialog.dismiss();
//                    payThread.interrupt();
//                    break;
                case 2:
                    if (mPlayer.isPlaying()) {
                        if (mPlayer.getCurrentPosition()-addTime>=time){
                            mPlayer.pause();
                            mPlayer.seekTo(addTime,MediaPlayer.SEEK_CLOSEST);
                        }
                        seekBar.setProgress(mPlayer.getCurrentPosition()-addTime);
                        mHandlers.sendEmptyMessageDelayed(2, 100);
                    } else {
                        mHandlers.removeMessages(2);
                    }
                    break;
//                case 3:
////                    MyTask myTask = new MyTask();
////                    myTask.execute();
//                    surfaceview.setVisibility(View.VISIBLE);
//                    break;
//                case 4:
//                    setVideo(paths.get(0));
//                    break;
                case 5:
                    setVideo();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public int initLayout() {
        return R.layout.activity_transcode;
    }

    @Override
    public void initData() {
//        basePathPic = "/ffmpeg/pic";
//        basePathVideo = "/ffmpeg/videoCut";
//        deleteDir(basePathPic);
//        deleteDir(basePathVideo);
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setText("参数设置");
        tvLeft.setText("组别设置");
        enRollListAdapter1 = new EnRollListAdapter(enRollModelList1);
        enRollListAdapter2 = new EnRollListAdapter(enRollModelList2);
        rvList2.setLayoutManager(new LinearLayoutManager(context));
        rvList.setLayoutManager(new LinearLayoutManager(context));
        enRollListAdapter2.bindToRecyclerView(rvList2);
        enRollListAdapter1.bindToRecyclerView(rvList);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        screenWidth = dm.widthPixels;
        llRedLine.setVisibility(StorageCustomerInfoUtil.getBooleanInfo("redLine", context, true) ? View.VISIBLE : View.GONE);
        llRedLine.setOnTouchListener(movingEventListener);
//        try {
//            basePathPic = isExistDir(basePathPic);
//            basePathVideo = isExistDir(basePathVideo);
//            isExistDir("/ffmpeg/video");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        checkPermission();
        try {
            isHardwareSupported();
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onResume() {
        super.onResume();
        llRedLine.setVisibility(StorageCustomerInfoUtil.getBooleanInfo("redLine", context, true) ? View.VISIBLE : View.GONE);
        delayTime = StorageCustomerInfoUtil.getIntInfo(context, "delayTime", 0);
        videoCount = StorageCustomerInfoUtil.getIntInfo(context, "cut", 10);
        autoStart = StorageCustomerInfoUtil.getBooleanInfo("autoStart", context, true);
        yun = StorageCustomerInfoUtil.getBooleanInfo("switchYun", context, true);
        progressDialog = ProgressDialog.getInstance(videoCount);
        enRollListAdapter1.notifyDataSetChanged();
        enRollListAdapter2.notifyDataSetChanged();
        if (mPlayer!=null) {
            mPlayer.reset();
        }
//        surfaceview.setVisibility(View.GONE);
//        surfaceview.setVisibility(View.VISIBLE);
//        if (mPlayer!=null) {
//            mPlayer.seekTo(addTime + seekBar.getProgress(), MediaPlayer.SEEK_CLOSEST);
//            mPlayer.start();
//            mPlayer.pause();
//        }
    }


//    @RequiresApi(api = Build.VERSION_CODES.O)
//    @Override
//    protected void onRestart() {
//        super.onRestart();
//        if (mPlayer==null) {
//            setMediaPlay(new File( StorageCustomerInfoUtil.getInfo("videoPath",context)));
//        }
//        mPlayer.seekTo(addTime + seekBar.getProgress(), MediaPlayer.SEEK_CLOSEST);
//        mPlayer.start();
//        mPlayer.pause();
//    }

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) {
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.RECORD_AUDIO}, CODE_REQUEST_WRITE_EXTERNAL);
        }
    }

    private void isHardwareSupported() throws CameraAccessException {
        CameraManager cameraManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
        CameraCharacteristics characteristics = cameraManager.getCameraCharacteristics("0");
        deviceLevel = characteristics.get(CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL);
        if (deviceLevel == null) {
            Log.i(TAG, "can not get INFO_SUPPORTED_HARDWARE_LEVEL");
            return;
        }
        Range<Integer> range1 = characteristics.get(CameraCharacteristics.SENSOR_INFO_SENSITIVITY_RANGE);
        //获取快门范围
        Range<Long> range2 = characteristics.get(CameraCharacteristics.SENSOR_INFO_EXPOSURE_TIME_RANGE);
        StreamConfigurationMap fpsMap = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
        Range<Integer>[] range3 = characteristics.get(CameraCharacteristics.CONTROL_AE_AVAILABLE_TARGET_FPS_RANGES);
        Range<Integer> range4 = characteristics.get(CameraCharacteristics.CONTROL_AE_COMPENSATION_RANGE);


        if (range4!= null) {
            int minExposure = range4.getLower();
            int maxExposure = range4.getUpper();
            Log.i(TAG, "亮度范围：" + minExposure + "-" + maxExposure);
            StorageCustomerInfoUtil.putInfo(context, "light", maxExposure);
        }
        if (range1 != null) {
            int max1 = range1.getUpper();//华为P9最大值为3500
            int min1 = range1.getLower();//华为P9最小值为100
            Log.i(TAG, "ISO范围：" + min1 + "-" + max1);
            StorageCustomerInfoUtil.putInfo(context, "iso", max1);
        }
        if (range2 != null) {
            Long max2 = range2.getUpper();//华为P9最大值1s
            Long min2 = range2.getLower();//华为p9最小值100ns
            Log.i(TAG, "快门范围：" + min2 + "-" + max2);
            StorageCustomerInfoUtil.putInfo(context, "KMmax", max2);
            StorageCustomerInfoUtil.putInfo(context, "KMmin", min2);
        }
        if (fpsMap != null) {
            for (Range<Integer> fpsRange : fpsMap.getHighSpeedVideoFpsRanges()) {
                Log.d(TAG, "SYNC_MAX_LATENCY_PER_FRAME_CONTROL: " + fpsRange.toString());
            }
        }
        if (range3 != null) {
            Log.d(TAG, "SYNC_MAX_LATENCY_PER_FRAME_CONTROL: " + Arrays.toString(range3));
        }
        switch (deviceLevel) {
            case CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL_FULL:
                Log.i("TAG", "hardware supported level:LEVEL_FULL");
                break;
            case CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL_LEGACY:
                Log.i("TAG", "hardware supported level:LEVEL_LEGACY");
                break;
            case CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL_3:
                Log.i("TAG", "hardware supported level:LEVEL_3");
                break;
            case CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL_LIMITED:
                Log.i("TAG", "hardware supported level:LEVEL_LIMITED");
                break;
            case CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL_EXTERNAL:
                Log.i("TAG", "hardware supported level:LEVEL_EXTERNA");
                break;
        }
        List<CaptureRequest.Key<?>> keyList = characteristics.getAvailableCaptureRequestKeys();
        for (CaptureRequest.Key<?> key : keyList) {
            if (key.equals(CaptureRequest.LENS_FOCUS_DISTANCE)) {
                Log.i("TAG", "支持调节焦距");
            }
            if (key.equals(CaptureRequest.CONTROL_AF_MODE)) {
                Log.i("TAG", "支持自动调节焦距");
            }
            if (key.equals(CaptureRequest.SENSOR_SENSITIVITY)) {
                Log.i("TAG", "支持调节ISO");
            }
            if (key.equals(CaptureRequest.CONTROL_AE_MODE)) {
                Log.i("TAG", "支持自动曝光");
            }
            if (key.equals(CaptureRequest.SENSOR_EXPOSURE_TIME)) {
                Log.i("TAG", "支持调节快门时间");
            }
            if (key.equals(CaptureRequest.CONTROL_AE_TARGET_FPS_RANGE)) {
                Log.i("TAG", "支持调节帧率");
            }
            if (key.equals(CaptureRequest.REQUEST_AVAILABLE_CAPABILITIES_CONSTRAINED_HIGH_SPEED_VIDEO)) {
                Log.i("TAG", "支持高帧率");
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == PICK_VIDEO_REQUEST && null != data) {
                Uri selectedVideo = data.getData();
                String[] filePathColumn = {MediaStore.Video.Media.DATA};
                Cursor cursor = getContentResolver().query(selectedVideo, filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                mVideoPath = cursor.getString(columnIndex);
                mHandlers.sendEmptyMessage(5);
                cursor.close();
            } else if (requestCode == REQUEST_CAMERA2 || requestCode == SETTING || requestCode == LOCALLIST) {
                mVideoPath = data.getStringExtra("path");
                mHandlers.sendEmptyMessage(5);
                videoTime=data.getLongExtra("time",0);
                if (startTime!=0&&videoTime!=0&&yun){
                    delayTime=(int)(videoTime-startTime);
                }
            } else if (requestCode == CHOOSE) {
                demandId = data.getStringExtra("id");
                startTime=data.getLongExtra("startTime",0);
                group=data.getIntExtra("group",0);
                road=data.getIntExtra("road",0);
                if (videoTime!=0&&startTime!=0&&yun){
                    delayTime=(int)(videoTime-startTime);
                }
                loadData2(true);
            }
        }
    }

    private void loadData2(boolean left) {
        HttpParams map = new HttpParams();
        map.put("demandId", demandId);
        map.put("page", scorePage);
        map.put("size", 10);
        OkClient.getInstance().post("/api/findScorePage", map, new OkClient.EntityCallBack<ResultModel>(context, ResultModel.class) {

            @Override
            public void onError(Response<ResultModel> response) {
                super.onError(response);
            }

            @Override
            public void onSuccess(Response<ResultModel> response) {
                super.onSuccess(response);
                if (response.body() != null) {
                    ResultModel resultModel = response.body();
                    if (resultModel.getData().getRetCode() == 0) {
                        ScoreListModel scoreListModel = JSONObject.parseObject(resultModel.getData().getData(), ScoreListModel.class);
                        last = scoreListModel.isLast();
                        first = scoreListModel.isFirst();
                        Map<String, EnRollModel> map1=new HashMap<>();
                        for (EnRollModel enRollModel:scoreListModel.getContent()){
                            map1.put(enRollModel.getRoad(),enRollModel);
                        }

                        if (left) {
                            enRollModelList1.clear();
                            for (int i=0;i<(last?road:10);i++){
                                String roads=group+"-"+(i+1);
                                if (map1.containsKey(roads)){
                                    enRollModelList1.add(map1.get(roads));
                                }else {
                                    EnRollModel rollModel = new EnRollModel();
                                    rollModel.setRoad(group + "-" + (i + 1));
                                    enRollModelList1.add(rollModel);
                                }
                            }
                            enRollListAdapter1.setNewData(enRollModelList1);
                            enRollListAdapter1.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                    if (!StringUtil.isEmpty(tvTitle.getText().toString()) && !tvTitle.getText().toString().equals("00:00.000")) {
                                        submit(position, left);
                                    }
                                }
                            });
                            if (!last) {
                                loadData2(false);
                            } else {
                                enRollModelList2 = new ArrayList<>();
                                enRollListAdapter2.setNewData(enRollModelList2);
                            }
                        } else {
                            enRollModelList2.clear();
                            for (int i=enRollModelList1.size();i<(last?road:20);i++){
                                String roads=group+"-"+(i+1);
                                if (map1.containsKey(roads)){
                                    enRollModelList2.add(map1.get(roads));
                                }else {
                                    EnRollModel rollModel = new EnRollModel();
                                    rollModel.setRoad(group + "-" + (i + 1));
                                    enRollModelList2.add(rollModel);
                                }
                            }
                            enRollListAdapter2.setNewData(enRollModelList2);
                            enRollListAdapter2.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                    submit(position, left);
                                }
                            });
                        }
                    }
                }
            }
        });
    }

    private String getRandomString() {
        StringBuffer buffer = new StringBuffer(
                "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ");
        StringBuffer sb = new StringBuffer();
        Random r = new Random();
        int range = buffer.length();
        for (int i = 0; i < 20; i++) {
            sb.append(buffer.charAt(r.nextInt(range)));
        }
        return sb.toString();
    }

//    private void createId(int position, boolean left) {
//        loadingDialog.show();
//        HttpParams map = new HttpParams();
//        map.put("demandId", demandId);
//        map.put("road", left ? enRollModelList1.get(position).getRoad() : enRollModelList2.get(position).getRoad());
//        map.put("cls", "");
////        map.put("id", getRandomString());
//        map.put("studentName", "");
//        OkClient.getInstance().post("/api/save", map, new OkClient.EntityCallBack<ResultModel>(context, ResultModel.class) {
//
//            @Override
//            public void onError(Response<ResultModel> response) {
//                super.onError(response);
//                loadingDialog.dismiss();
//            }
//
//            @Override
//            public void onSuccess(Response<ResultModel> response) {
//                super.onSuccess(response);
//                loadingDialog.dismiss();
//                if (response.body() != null) {
//                    ResultModel resultModel = response.body();
//                    if (resultModel.getData().getRetCode() == 0) {
//                        Toast.makeText(context, "报名成功", Toast.LENGTH_SHORT).show();
//                        submit(position,left);
//                    }
//                }
//            }
//        });
//    }
    private void submit(int position, boolean left) {
        loadingDialog.show();
        HttpParams map = new HttpParams();
        map.put("enrollId", left ? enRollModelList1.get(position).getId() : enRollModelList2.get(position).getId());
        map.put("record", tvTitle.getText().toString());
        OkClient.getInstance().post("/api/saveScore", map, new OkClient.EntityCallBack<ResultModel>(context, ResultModel.class) {

            @Override
            public void onError(Response<ResultModel> response) {
                super.onError(response);
                loadingDialog.dismiss();
            }

            @Override
            public void onSuccess(Response<ResultModel> response) {
                super.onSuccess(response);
                loadingDialog.dismiss();
                if (response.body() != null) {
                    ResultModel resultModel = response.body();
                    if (resultModel.getData().getRetCode() == 0) {
                        Toast.makeText(context, "上传成绩成功", Toast.LENGTH_SHORT).show();
                        if (left) {
                            enRollModelList1.get(position).setRecord(tvTitle.getText().toString());
                            enRollListAdapter1.setNewData(enRollModelList1);
                        } else {
                            enRollModelList2.get(position).setRecord(tvTitle.getText().toString());
                            enRollListAdapter2.setNewData(enRollModelList2);
                        }
                    }
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setVideo() {
//        this.path = path;
//        deleteDir(basePathVideo);
        start = 0;
        startPage = 0;
        addTime = 0;
        videoPage = 1;
        time = 0;
        addTime=0;
        File file = new File(mVideoPath);
        if (file != null) {
            mInfo = FFmpegCmd.getVideoInfo(mVideoPath);
            if (videoCount>1) {
                time = (int) mInfo.duration / videoCount;
            }
            frameRate = 1000 / mInfo.fps;
            setMediaPlay(file);
            tvNowParagraph.setText("当前：" + videoPage + "/" + videoCount);
            tvNowParagraph.setVisibility(View.VISIBLE);
//            ivCover.setVisibility(View.VISIBLE);
//            Bitmap videoFrame = MediaTool.getVideoFrame(mVideoPath, 200000);
//            ivCover.setImageBitmap(videoFrame);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setMediaPlay(File file){
        if (mPlayer != null) {
            mPlayer.reset();
        }
        mPlayer = MediaPlayer.create(context, FileProvider.getUriForFile(context,"com.timer.com.fileProvider",file));
        mPlayer.setScreenOnWhilePlaying(true);
        mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                seekBar.setMax(time);
                seekBar.setProgress(0);
            }
        });

        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onCompletion(MediaPlayer mp) {
               setMediaPlay(file);
            }
        });
        surfaceview.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                mPlayer.setDisplay(holder);
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvTitle.setText(Gutil.parseTimeMillis(addTime + progress + delayTime));
//                    ivCover.setImageBitmap(MediaTool.getVideoFrame(path, progress*1000));
                if (!mPlayer.isPlaying()){
                    surfaceview.setVisibility(View.VISIBLE);
                    mPlayer.seekTo(addTime + progress,MediaPlayer.SEEK_CLOSEST);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                mPlayer.pause();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
//                    getPic();
            }
        });
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) surfaceview.getLayoutParams();
        layoutParams.width =(mInfo.rotation==90||mInfo.rotation==270)? mInfo.height:mInfo.width;
        layoutParams.height =(mInfo.rotation==90||mInfo.rotation==270)?mInfo.width:mInfo.height;
        surfaceview.setLayoutParams(layoutParams);
        mPlayer.seekTo(addTime,MediaPlayer.SEEK_CLOSEST);
        mPlayer.start();
        mPlayer.pause();
        seekBar.setProgress(0);
        surfaceview.setVisibility(View.GONE);
        surfaceview.setVisibility(View.VISIBLE);
//        mHandlers.sendEmptyMessageDelayed(3,500);

    }

    public boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < 100) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

//    private void getPic() {
//        if (isFastDoubleClick()) {
//            return;
//        }
//        Runnable payRunnable = new Runnable() {
//            @Override
//            public void run() {
//                deleteDir(basePathPic);
//                String PathPic = basePathPic + "/screenShot" + System.currentTimeMillis() + ".jpg";
//                String size = "";
//                if (mInfo.rotation == 0 || mInfo.rotation == 180) {
//                    size = mInfo.width + "x" + mInfo.height;
//                } else {
//                    size = mInfo.height + "x" + mInfo.width;
//                }
//                FFmpegCmd.screenShot(mVideoPath,
//                        Gutil.parseTimeMillis(seekBar.getProgress() ==
//                                seekBar.getMax() ? seekBar.getMax() - frameRate : seekBar.getProgress()),
//                        size, mInfo.fps + "", PathPic);
//                File file = new File(PathPic);
//                if (file != null) {
//                    Message msg = new Message();
//                    msg.what = 1;
//                    msg.obj = PathPic;
//                    mHandlers.sendMessage(msg);
//                }
//            }
//        };
//        payThread = new Thread(payRunnable);
//        payThread.start();
//    }

    public static void deleteDir(String path) {
        // 下载位置
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File downloadFile = new File(Environment.getExternalStorageDirectory(), path);
            deleteDirWihtFile(downloadFile);
        }
    }

    public static void deleteDirWihtFile(File dir) {
        if (dir == null || !dir.exists() || !dir.isDirectory() || dir.listFiles() == null) {
            return;
        }
        for (File file : dir.listFiles()) {
            if (file.isFile()) {
                file.delete(); // 删除所有文件
            } else if (file.isDirectory()) {
                deleteDirWihtFile(file); // 递规的方式删除文件夹
            }
        }
    }

    public static String isExistDir(String saveDir) throws IOException {
        // 下载位置
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File downloadFile = new File(Environment.getExternalStorageDirectory(), saveDir);
            if (!downloadFile.mkdirs()) {
                downloadFile.createNewFile();
            }
            String savePath = downloadFile.getAbsolutePath();
            return savePath;
        }
        return null;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @OnClick({R.id.tv_left, R.id.tv_right, R.id.tv_last_page, R.id.tv_next_page, R.id.tv_last_paragraph,
            R.id.tv_next_paragraph, R.id.tv_choose, R.id.tv_start, R.id.tv_before_n, R.id.tv_before,R.id.tv_sign,
            R.id.tv_previous, R.id.tv_play, R.id.tv_next, R.id.tv_after, R.id.tv_after_n, R.id.tv_recording})
    public void onViewClicked(View view) {
        if (isFastDoubleClick()){
            return;
        }
        switch (view.getId()) {
            case R.id.tv_left:
                startActivityForResult(new Intent(context, ChooseListActivity.class), CHOOSE);
                break;
            case R.id.tv_right:
                startActivityForResult(new Intent(context, SettingActivity.class), SETTING);
                break;
            case R.id.tv_sign:
                if (!StringUtil.isEmpty(demandId)) {
                    startActivity(new Intent(context, SignUpActivity.class).putExtra("demandId", demandId));
                }
                break;
            case R.id.tv_last_page:
                if (!first && scorePage != 0) {
                    scorePage--;
                    loadData2(true);
                }
                break;
            case R.id.tv_next_page:
                if (!last) {
                    scorePage++;
                    loadData2(true);
                }
                break;
            case R.id.tv_last_paragraph:
                if (videoPage == 1) {
                    return;
                }
                addTime -= time;
                addTime = Math.max(addTime, 0);
                tvTitle.setText(Gutil.parseTimeMillis(addTime + delayTime));
                seekBar.setProgress(0);
                mPlayer.seekTo(addTime,MediaPlayer.SEEK_CLOSEST);
//                ivCover.setVisibility(View.GONE);
                surfaceview.setVisibility(View.VISIBLE);
//                setVideo(paths.get(videoPage - 2));
                videoPage--;
                if (startPage == videoPage) {
                    ivStartFlag.setVisibility(View.VISIBLE);
                    ivStartFlag.setX((start * seekBar.getWidth()) / seekBar.getMax() + seekBar.getX());
                    ivStartFlag.invalidate();
                } else {
                    ivStartFlag.setVisibility(View.GONE);
                }
                tvNowParagraph.setText("当前：" + videoPage + "/" + videoCount);
                break;
            case R.id.tv_next_paragraph:
                if (videoPage == videoCount) {
                    return;
                }
                addTime += time;
                tvTitle.setText(Gutil.parseTimeMillis(addTime + delayTime));
                seekBar.setProgress(0);
                mPlayer.seekTo(addTime,MediaPlayer.SEEK_CLOSEST);
//                ivCover.setVisibility(View.GONE);
                surfaceview.setVisibility(View.VISIBLE);
//                setVideo(paths.get(videoPage));
                videoPage++;
                if (startPage == videoPage) {
                    ivStartFlag.setVisibility(View.VISIBLE);
                    ivStartFlag.setX((start * seekBar.getWidth()) / seekBar.getMax() + seekBar.getX());
                    ivStartFlag.invalidate();
                } else {
                    ivStartFlag.setVisibility(View.GONE);
                }
                tvNowParagraph.setText("当前：" + videoPage + "/" + videoCount);
                break;
            case R.id.tv_choose:
                NavigationDialog dialog = new NavigationDialog();
                dialog.setOnLocalClickListener(new NavigationDialog.OnClickListener() {
                    @Override
                    public void onClick() {
                        startActivityForResult(new Intent(context, LocalListActivity.class), LOCALLIST);
                    }
                });
                dialog.setOnPhotoClickListener(new NavigationDialog.OnClickListener() {
                    @Override
                    public void onClick() {
                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent, PICK_VIDEO_REQUEST);
                    }
                });
                dialog.show(getSupportFragmentManager(), "");
                break;
            case R.id.tv_start:
                if (seekBar != null && !autoStart) {
                    startPage = videoPage;
                    start = seekBar.getProgress();
                    delayTime=-start-addTime;
                    tvTitle.setText(Gutil.parseTimeMillis(addTime + start + delayTime));
                    ivStartFlag.setVisibility(View.VISIBLE);
                    ivStartFlag.setX((seekBar.getProgress() * seekBar.getWidth()) / seekBar.getMax() + seekBar.getX() + 2);
                    ivStartFlag.invalidate();
                }
                break;
            case R.id.tv_before_n:
                seekBar.incrementProgressBy(-StorageCustomerInfoUtil.getIntInfo(context, "fpsJump", 1) * frameRate);
//                getPic();
                break;
            case R.id.tv_before:
                seekBar.incrementProgressBy(-30 * frameRate);
//                getPic();
                break;
            case R.id.tv_previous:
                seekBar.incrementProgressBy(-frameRate);
//                getPic();
                break;
            case R.id.tv_play:
                if (mPlayer == null) {
                 return;
                }
                surfaceview.setVisibility(View.VISIBLE);
//                ivCover.setVisibility(View.GONE);
                mPlayer.start();
                mHandlers.sendEmptyMessageDelayed(2, 100);
//                mPlayer.start();
                break;
            case R.id.tv_next:
                seekBar.incrementProgressBy(frameRate);
//                getPic();
                break;
            case R.id.tv_after:
                seekBar.incrementProgressBy(30 * frameRate);
//                getPic();
                break;
            case R.id.tv_after_n:
                seekBar.incrementProgressBy(StorageCustomerInfoUtil.getIntInfo(context, "fpsJump", 1) * frameRate);
//                getPic();
                break;
            case R.id.tv_recording:
//                if (deviceLevel==CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL_LEGACY){
//                    startActivityForResult(new Intent(this, MainActivity.class), REQUEST_CAMERA2);
//                }else {
                    startActivityForResult(new Intent(this, Camera2VideoActivity.class), REQUEST_CAMERA2);
//                }
                break;
        }
    }

//    class MyTask extends AsyncTask<String, Integer, String> {
//
//        @Override
//        protected void onPostExecute(String result) {
//            //最终结果的显示
//            if (videoCount!=1) {
//                progressDialog.dismiss();
//            }
////            Toast.makeText(context,result,Toast.LENGTH_SHORT).show();
//        }
//
//        @Override
//        protected void onPreExecute() {
//            //开始前的准备工作
//        }
//
//        @Override
//        protected void onProgressUpdate(Integer... values) {
//            //显示进度
//            if (values[0] == 1) {
//                progressDialog.show(getSupportFragmentManager(), "");
//            }
//            EventBus.getDefault().post(new ProgressEvent(values[0]));
//        }
//
//        @SuppressLint("WrongThread")
//        @Override
//        protected String doInBackground(String... strings) {
//            deleteDir(basePathVideo);
//            paths.clear();
//            start = 0;
//            end = 0;
//            startPage = 0;
//            endPage = 0;
//            addTime = 0;
//            videoPage = 1;
//            time = 0;
//            path = "";
//            File file = new File(mVideoPath);
//            if (file != null) {
//                mInfo = FFmpegCmd.getVideoInfo(mVideoPath);
//                if (videoCount > 1&&mInfo.duration>videoCount*1000) {
//                    time = (int) mInfo.duration / videoCount;
//                    int sumTime=0;
//                    for (i = 0; i < videoCount; i++) {
//                        publishProgress(i + 1);
//                        try{
//                            FFmpegCmd.cutVideo(mVideoPath, Gutil.parseTime(sumTime),
//                                    Gutil.parseTime((i+1)*time-sumTime),
//                                    basePathVideo + "/videoCut" + i + ".mp4");
//                        }catch (Exception e){
//                            e.printStackTrace();
//                        }
//                        paths.add(basePathVideo + "/videoCut" + i + ".mp4");
//                        VideoInfo newVideo=FFmpegCmd.getVideoInfo(paths.get(i));
//                        videos.add(newVideo);
//                        sumTime+=newVideo.duration;
//                    }
//                }
//                if (paths != null && paths.size() > 0) {
//                    videoPage = 1;
//                    seekBar.setProgress(0);
//                    file = new File(paths.get(0));
//                    if (file != null) {
//                        mHandlers.sendEmptyMessage(4);
//                    } else {
//                        mHandlers.sendEmptyMessage(5);
//                    }
//                } else {
//                    videoPage = 1;
//                    videoCount = 1;
//                    time = (int) mInfo.duration;
//                    mHandlers.sendEmptyMessage(5);
//                    paths = new ArrayList<>();
//                    paths.add(mVideoPath);
//                }
//            }
//            onPostExecute("");
//            return null;
//        }
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}

