package com.timer.com;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.ImageFormat;
import android.graphics.Point;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.timer.com.util.Gutil;
import com.timer.com.util.StorageCustomerInfoUtil;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author : qiuyiyang
 * @date : 2022/1/18  15:01
 * @desc :
 */
public class MainActivity extends BaseActivity implements SurfaceHolder.Callback {

    private static final String TAG = "MainActivity";
    private static int mOrientation = 0;
    private static int mCameraID = Camera.CameraInfo.CAMERA_FACING_BACK;
    @BindView(R.id.surfaceview)
    SurfaceView surfaceview;
    @BindView(R.id.tv_delay_time)
    TextView tvDelayTime;
    @BindView(R.id.tv_video_time)
    TextView tvVideoTime;
    @BindView(R.id.record_controller)
    TextView recordController;
    private SurfaceHolder mSurfaceHolder;
    private Camera mCamera;
    private boolean havePermission = false;
    private MediaRecorder mMediaRecorder;
    private boolean start = false;
    private int videoTime;
    private long time;
    private final static int MAX_FRAME_RATE = 240;
    private int shootingDelay;
    private CamcorderProfile mCamcorderProfile;

    private String srcPath =Environment.getExternalStorageDirectory().getPath() + "/ffmpeg/video/" + System.currentTimeMillis() + ".mp4";
    private Handler mHandlers = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    if (shootingDelay <= 0) {
                        tvDelayTime.setVisibility(View.GONE);
                        initMediaRecord();
                    } else {
                        tvDelayTime.setVisibility(View.VISIBLE);
                        tvDelayTime.setText(new BigDecimal(shootingDelay + "").divide(new BigDecimal("1000")).setScale(1, BigDecimal.ROUND_HALF_UP).toString());
                        shootingDelay = shootingDelay - 100;
                        sendEmptyMessageDelayed(1, 100);
                    }
                    break;
                case 2:
                    tvVideoTime.setVisibility(View.VISIBLE);
                    tvVideoTime.setText(Gutil.parseTimeMillis(videoTime));
                    videoTime = videoTime + 100;
                    sendEmptyMessageDelayed(2, 100);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public int initLayout() {
        return R.layout.act_video;
    }

    @Override
    public void initData() {
        shootingDelay = StorageCustomerInfoUtil.getIntInfo(this, "shootingDelay", 0);
        if (!StorageCustomerInfoUtil.getBooleanInfo("daley", this, true)) {
            shootingDelay = 0;
        }
        // Android 6.0相机动态权限检查,省略了
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) {
            havePermission = true;
            mSurfaceHolder = surfaceview.getHolder();
            mSurfaceHolder.addCallback(this);
        } else {
            havePermission = false;
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO}, 100);
        }
        recordController.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!start) {
                    mHandlers.sendEmptyMessage(1);
                } else if (mMediaRecorder != null) {
                    mMediaRecorder.stop();
                    mMediaRecorder.release();
                    mHandlers.removeMessages(2);
                    mCamera.lock();
                    mCamera.release();
                    mCamera=null;
//                    setResult(RESULT_OK, new Intent().putExtra("path", srcPath).putExtra("time", time));
//                    finish();
                }
            }
        });
        mCamcorderProfile = CamcorderProfile.get(Camera.CameraInfo.CAMERA_FACING_BACK, CamcorderProfile.QUALITY_HIGH);
        if (CamcorderProfile.hasProfile(CamcorderProfile.QUALITY_1080P)) {
            mCamcorderProfile = CamcorderProfile.get(CamcorderProfile.QUALITY_1080P);
            Log.i(TAG, "setUpMediaRecorder: support QUALITY_1080P ");
        } else if (CamcorderProfile.hasProfile(CamcorderProfile.QUALITY_HIGH)) {
            mCamcorderProfile = CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH);
            Log.i(TAG, "setUpMediaRecorder: support QUALITY_HIGH ");
        } else if (CamcorderProfile.hasProfile(CamcorderProfile.QUALITY_720P)) {
            mCamcorderProfile = CamcorderProfile.get(CamcorderProfile.QUALITY_720P);
            Log.i(TAG, "setUpMediaRecorder: support QUALITY_720P");
        } else if (CamcorderProfile.hasProfile(CamcorderProfile.QUALITY_480P)) {
            mCamcorderProfile = CamcorderProfile.get(CamcorderProfile.QUALITY_480P);
            Log.i(TAG, "setUpMediaRecorder: support QUALITY_480P");
        } else if (CamcorderProfile.hasProfile(CamcorderProfile.QUALITY_LOW)) {
            mCamcorderProfile = CamcorderProfile.get(CamcorderProfile.QUALITY_LOW);
            Log.i(TAG, "setUpMediaRecorder: support QUALITY_LOW ");
        }
        mMediaRecorder = new MediaRecorder();
        mMediaRecorder.reset();
        mMediaRecorder.setCamera(mCamera);
        mMediaRecorder.setOrientationHint(mOrientation);
        mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.SURFACE);
        mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        mMediaRecorder.setAudioEncoder(mCamcorderProfile.audioCodec);
        mMediaRecorder.setVideoEncoder(mCamcorderProfile.videoCodec);
        mMediaRecorder.setVideoSize(mCamcorderProfile.videoFrameWidth, mCamcorderProfile.videoFrameHeight);
        mMediaRecorder.setVideoFrameRate(mCamcorderProfile.videoFrameRate);
        mMediaRecorder.setVideoEncodingBitRate(mCamcorderProfile.videoBitRate);
        mMediaRecorder.setAudioEncodingBitRate(mCamcorderProfile.audioBitRate);
        mMediaRecorder.setAudioChannels(mCamcorderProfile.audioChannels);
        mMediaRecorder.setAudioSamplingRate(mCamcorderProfile.audioSampleRate);
        mMediaRecorder.setOutputFile(srcPath);
        mMediaRecorder.setPreviewDisplay(surfaceview.getHolder().getSurface());
    }


    public void initMediaRecord() {
        mCamera.unlock();
        recordController.animate().scaleX(0.8f).scaleY(0.8f).setDuration(500).start();
        recordController.setTextColor(Color.RED);
        recordController.setBackgroundResource(R.drawable.small_video_shoot_stop);
        recordController.setText("停止");
        //创建录音文件
        File mRecorderFile = new File(srcPath);
        try {
            if (!mRecorderFile.getParentFile().exists()) {
                mRecorderFile.getParentFile().mkdirs();
            }
            mRecorderFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //开始录音
        try {
            mMediaRecorder.prepare();
            mMediaRecorder.start();
            start = true;
            mHandlers.sendEmptyMessage(2);
            time = System.currentTimeMillis();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void releaseCamera() {
        if (mCamera != null) {
            mCamera.release();
            mCamera = null;
        }
    }

    private void initCamera() {
        if (mCamera != null) {
            releaseCamera();
        }
        mCamera = Camera.open(mCameraID);
        if (mCamera != null) {
            try {
                mCamera.setPreviewDisplay(mSurfaceHolder);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Camera.Parameters parameters = mCamera.getParameters();
            parameters.setRecordingHint(true);
            {
                //设置获取数据
                parameters.setPreviewFormat(ImageFormat.NV21);
                //parameters.setPreviewFormat(ImageFormat.YUV_420_888);
                //通过setPreviewCallback方法监听预览的回调：
                mCamera.setPreviewCallback(new Camera.PreviewCallback() {
                    @Override
                    public void onPreviewFrame(byte[] bytes, Camera camera) {
                        //这里面的Bytes的数据就是NV21格式的数据,或者YUV_420_888的数据
                    }
                });
            }

            if (mCameraID == Camera.CameraInfo.CAMERA_FACING_BACK) {
                parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);
            }
            mCamera.setParameters(parameters);
            calculateCameraPreviewOrientation(this);
            mCamera.setDisplayOrientation(mOrientation);
            Camera.Size tempSize;
            tempSize = setPreviewSize(mCamera, mCamcorderProfile.videoFrameWidth, mCamcorderProfile.videoFrameHeight);
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) surfaceview.getLayoutParams();
            if (mOrientation==90||mOrientation==270){
                layoutParams.width = tempSize.height;
                layoutParams.height = tempSize.width;
            }else {
                layoutParams.width = tempSize.width;
                layoutParams.height = tempSize.height;
            }
            surfaceview.setLayoutParams(layoutParams);
            mCamera.startPreview();
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        //当SurfaceView变化时也需要做相应操作，这里未做相应操作
        if (havePermission) {
            initCamera();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseCamera();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (mCamera!=null) {
            mCamera.stopPreview();
        }
    }

    private Camera.Size setPreviewSize(Camera camera, int expectWidth, int expectHeight) {
        Camera.Parameters parameters = camera.getParameters();
        Point point = new Point(expectWidth, expectHeight);
        Camera.Size size = findProperSize(point, parameters.getSupportedPreviewSizes());
        parameters.setPreviewSize(size.width, size.height);
        camera.setParameters(parameters);
        return size;
    }

    /**
     * 找出最合适的尺寸，规则如下：
     * 1.将尺寸按比例分组，找出比例最接近屏幕比例的尺寸组
     * 2.在比例最接近的尺寸组中找出最接近屏幕尺寸且大于屏幕尺寸的尺寸
     * 3.如果没有找到，则忽略2中第二个条件再找一遍，应该是最合适的尺寸了
     */
    public static Camera.Size findProperSize(Point surfaceSize, List<Camera.Size> sizeList) {
        if (surfaceSize.x <= 0 || surfaceSize.y <= 0 || sizeList == null) {
            return null;
        }

        int surfaceWidth = surfaceSize.x;
        int surfaceHeight = surfaceSize.y;

        Camera.Size bestSize = null;
        int diff = Integer.MAX_VALUE;
        for (Camera.Size size : sizeList) {
            if (size.width==surfaceWidth&&size.height==surfaceHeight){
                return size;
            }
            int newDiff = Math.abs(size.width - surfaceWidth) + Math.abs(size.height - surfaceHeight);
            if ((surfaceHeight* size.width)==(size.height* surfaceWidth)) {
                if (newDiff < diff) {
                    bestSize = size;
                    diff = newDiff;
                }
            }
        }
        return bestSize;
    }

    /**
     * 排序
     *
     * @param list
     */
    private static void sortList(List<Camera.Size> list) {
        Collections.sort(list, new Comparator<Camera.Size>() {
            @Override
            public int compare(Camera.Size pre, Camera.Size after) {
                if (pre.width > after.width) {
                    return 1;
                } else if (pre.width < after.width) {
                    return -1;
                }
                return 0;
            }
        });
    }

    /**
     * 设置预览角度，setDisplayOrientation本身只能改变预览的角度
     * previewFrameCallback以及拍摄出来的照片是不会发生改变的，拍摄出来的照片角度依旧不正常的
     * 拍摄的照片需要自行处理
     * 这里Nexus5X的相机简直没法吐槽，后置摄像头倒置了，切换摄像头之后就出现问题了。
     *
     * @param activity
     */
    public static int calculateCameraPreviewOrientation(Activity activity) {
        Camera.CameraInfo info = new Camera.CameraInfo();
        Camera.getCameraInfo(mCameraID, info);
        int rotation = activity.getWindowManager().getDefaultDisplay()
                .getRotation();
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0:
                degrees = 0;
                break;
            case Surface.ROTATION_90:
                degrees = 90;
                break;
            case Surface.ROTATION_180:
                degrees = 180;
                break;
            case Surface.ROTATION_270:
                degrees = 270;
                break;
        }

        int result;
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360;
        } else {
            result = (info.orientation - degrees + 360) % 360;
        }
        mOrientation = result;
        return result;
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (havePermission && mCamera != null) {
            mCamera.stopPreview();
            mHandlers.removeMessages(2);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}