/*
 * Copyright 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.timer.com;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraConstrainedHighSpeedCaptureSession;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.params.MeteringRectangle;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Range;
import android.util.Size;
import android.util.SparseIntArray;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.timer.com.util.DateUtil;
import com.timer.com.util.Gutil;
import com.timer.com.util.StorageCustomerInfoUtil;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.support.v4.math.MathUtils.clamp;

public class Camera2VideoActivity extends BaseActivity
        implements View.OnClickListener, ActivityCompat.OnRequestPermissionsResultCallback {

    private static final int SENSOR_ORIENTATION_DEFAULT_DEGREES = 90;
    private static final int SENSOR_ORIENTATION_INVERSE_DEGREES = 270;
    private static final SparseIntArray DEFAULT_ORIENTATIONS = new SparseIntArray();
    private static final SparseIntArray INVERSE_ORIENTATIONS = new SparseIntArray();

    private static final String TAG = "Camera2VideoFragment";
    private static final int REQUEST_VIDEO_PERMISSIONS = 1;
    private static final String FRAGMENT_DIALOG = "dialog";

    private static final String[] VIDEO_PERMISSIONS = {
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO,
    };

    static {
        DEFAULT_ORIENTATIONS.append(Surface.ROTATION_0, 90);
        DEFAULT_ORIENTATIONS.append(Surface.ROTATION_90, 0);
        DEFAULT_ORIENTATIONS.append(Surface.ROTATION_180, 270);
        DEFAULT_ORIENTATIONS.append(Surface.ROTATION_270, 180);
    }

    static {
        INVERSE_ORIENTATIONS.append(Surface.ROTATION_0, 270);
        INVERSE_ORIENTATIONS.append(Surface.ROTATION_90, 180);
        INVERSE_ORIENTATIONS.append(Surface.ROTATION_180, 90);
        INVERSE_ORIENTATIONS.append(Surface.ROTATION_270, 0);
    }

    @BindView(R.id.mTextureView)
    AutoFitTextureView mTextureView;
    @BindView(R.id.tv_delay_time)
    TextView tvDelayTime;
    @BindView(R.id.record_controller)
    TextView recordController;
    @BindView(R.id.tv_video_time)
    TextView tvVideoTime;

    /**
     * Button to record video
     */

    /**
     * A reference to the opened {@link CameraDevice}.
     */
    private CameraDevice mCameraDevice;

    /**
     * A reference to the current {@link CameraCaptureSession} for
     * preview.
     */
    private CameraCaptureSession mPreviewSession;
    private CameraConstrainedHighSpeedCaptureSession mHighSpeedPreviewSession;

    /**
     * {@link TextureView.SurfaceTextureListener} handles several lifecycle events on a
     * {@link TextureView}.
     */
    private TextureView.SurfaceTextureListener mSurfaceTextureListener
            = new TextureView.SurfaceTextureListener() {

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture,
                                              int width, int height) {
            openCamera(width, height);
        }

        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture,
                                                int width, int height) {
            configureTransform(width, height);
        }

        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
            return true;
        }

        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
        }

    };

    /**
     * The {@link Size} of camera preview.
     */
    private Size mPreviewSize;

    /**
     * The {@link Size} of video recording.
     */
    private Size mVideoSize;

    /**
     * MediaRecorder
     */
    private MediaRecorder mMediaRecorder;

    /**
     * Whether the app is recording video now
     */
    private boolean mIsRecordingVideo;

    /**
     * An additional thread for running tasks that shouldn't block the UI.
     */
    private HandlerThread mBackgroundThread;

    /**
     * A {@link Handler} for running tasks in the background.
     */
    private Handler mBackgroundHandler;

    /**
     * A {@link Semaphore} to prevent the app from exiting before closing the camera.
     */
    private Semaphore mCameraOpenCloseLock = new Semaphore(1);

    /**
     * {@link CameraDevice.StateCallback} is called when {@link CameraDevice} changes its status.
     */
    private CameraDevice.StateCallback mStateCallback = new CameraDevice.StateCallback() {

        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void onOpened(@NonNull CameraDevice cameraDevice) {
            mCameraDevice = cameraDevice;
            startPreview();
            mCameraOpenCloseLock.release();
            if (null != mTextureView) {
                configureTransform(mTextureView.getWidth(), mTextureView.getHeight());
                mTextureView.setKeepScreenOn(true);
                mTextureView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        // ???????????????view???????????????
                        double x = event.getX(), y = event.getY(), tmp;
                        // ?????????????????????????????????????????????,???????????????????????????
                        int realPreviewWidth = mPreviewSize.getWidth(), realPreviewHeight = mPreviewSize.getHeight();
                        if (SENSOR_ORIENTATION_DEFAULT_DEGREES== mSensorOrientation || SENSOR_ORIENTATION_INVERSE_DEGREES== mSensorOrientation) {
                            realPreviewWidth = mPreviewSize.getHeight();
                            realPreviewHeight = mPreviewSize.getWidth();
                        }
                        // ???????????????????????????????????????view???????????????,?????????????????????
                        double imgScale = 1.0, verticalOffset = 0, horizontalOffset = 0;
                        if (realPreviewHeight * mTextureView.getWidth() > realPreviewWidth * mTextureView.getHeight()) {
                            imgScale = mTextureView.getWidth() * 1.0 / realPreviewWidth;
                            verticalOffset = (realPreviewHeight - mTextureView.getWidth() / imgScale) / 2;
                        } else {
                            imgScale = mTextureView.getHeight() * 1.0 / realPreviewHeight;
                            horizontalOffset = (realPreviewWidth - mTextureView.getWidth() / imgScale) / 2;
                        }
// ?????????????????????????????????????????????
                        x = x / imgScale + horizontalOffset;
                        y = y / imgScale + verticalOffset;
                        if (SENSOR_ORIENTATION_DEFAULT_DEGREES== mSensorOrientation) {
                            tmp = x;
                            x = y;
                            y = mPreviewSize.getHeight() - tmp;
                        } else if (SENSOR_ORIENTATION_INVERSE_DEGREES== mSensorOrientation) {
                            tmp = x;
                            x = mPreviewSize.getWidth() - y;
                            y = tmp;
                        }
                        //app????????????????????????????????????(crop region)????????????????????????????????????????????????,??????????????????app?????????????????????????????????????????????????????????,?????????????????????:
                        // ?????????????????????????????????????????????????????????,????????????
                        Rect cropRegion = mPreviewBuilder.get(CaptureRequest.SCALER_CROP_REGION);
                        if (null == cropRegion) {
                            Log.e(TAG, "can't get crop region");
                            return false;
                        }
                        int cropWidth = cropRegion.width(), cropHeight = cropRegion.height();
                        if (mPreviewSize.getHeight() * cropWidth > mPreviewSize.getWidth() * cropHeight) {
                            imgScale = cropHeight * 1.0 / mPreviewSize.getHeight();
                            verticalOffset = 0;
                            horizontalOffset = (cropWidth - imgScale * mPreviewSize.getWidth()) / 2;
                        } else {
                            imgScale = cropWidth * 1.0 / mPreviewSize.getWidth();
                            horizontalOffset = 0;
                            verticalOffset = (cropHeight - imgScale * mPreviewSize.getHeight()) / 2;
                        }
                        //????????????????????????app?????????????????????,???????????????????????????????????????:
                        // ???????????????????????????????????????,???????????????????????????????????????
                        x = x * imgScale + horizontalOffset + cropRegion.left;
                        y = y * imgScale + verticalOffset + cropRegion.top;
                        //????????????????????????????????????0.1????????????????????????:
                        double tapAreaRatio = 0.1;
                        Rect rect = new Rect();
                        rect.left = clamp((int) (x - tapAreaRatio / 2 * cropRegion.width()), 0, cropRegion.width());
                        rect.right = clamp((int) (x + tapAreaRatio / 2 * cropRegion.width()), 0, cropRegion.width());
                        rect.top = clamp((int) (y - tapAreaRatio / 2 * cropRegion.height()), 0, cropRegion.height());
                        rect.bottom = clamp((int) (y + tapAreaRatio / 2 * cropRegion.height()), 0, cropRegion.height());

                        mPreviewBuilder.set(CaptureRequest.CONTROL_AF_REGIONS, new MeteringRectangle[]{new MeteringRectangle(rect, 1000)});
                        mPreviewBuilder.set(CaptureRequest.CONTROL_AE_REGIONS, new MeteringRectangle[]{new MeteringRectangle(rect, 1000)});
                        mPreviewBuilder.set(CaptureRequest.CONTROL_AF_MODE, CaptureRequest.CONTROL_AF_MODE_AUTO);
                        mPreviewBuilder.set(CaptureRequest.CONTROL_AF_TRIGGER, CameraMetadata.CONTROL_AF_TRIGGER_START);
                        mPreviewBuilder.set(CaptureRequest.CONTROL_AE_PRECAPTURE_TRIGGER, CameraMetadata.CONTROL_AE_PRECAPTURE_TRIGGER_START);
                        state = true;

                        updatePreview();
                        return false;
                    }
                });
            }
        }

        @Override
        public void onDisconnected(@NonNull CameraDevice cameraDevice) {
            mCameraOpenCloseLock.release();
            cameraDevice.close();
            mCameraDevice = null;
        }

        @Override
        public void onError(@NonNull CameraDevice cameraDevice, int error) {
            mCameraOpenCloseLock.release();
            cameraDevice.close();
            mCameraDevice = null;
        }

    };
    private CamcorderProfile mCamcorderProfile;
    private Integer mSensorOrientation;
    private int shootingDelay;
    private int videoTime;
    private Range<Integer> fps;
    private long time;
    private boolean state = true;
    private int rotation;
    private String mNextVideoAbsolutePath;
    private CaptureRequest.Builder mPreviewBuilder;

    /**
     * In this sample, we choose a video size with 3x4 aspect ratio. Also, we don't use sizes
     * larger than 1080p, since MediaRecorder cannot handle such a high-resolution video.
     *
     * @param choices The list of available sizes
     * @return The video size
     */
    private Size chooseVideoSize(Size[] choices) {
        int deviceWidth, deviceHeigh;
        if (rotation == Surface.ROTATION_0 || rotation== Surface.ROTATION_180) {
            DisplayMetrics displayMetrics = getResources().getDisplayMetrics(); //???????????????????????????????????????,?????????????????????????????????
            deviceWidth = displayMetrics.heightPixels; //??????????????????
            deviceHeigh = displayMetrics.widthPixels; //??????????????????
        } else {
            DisplayMetrics displayMetrics = getResources().getDisplayMetrics(); //???????????????????????????????????????,?????????????????????????????????
            deviceWidth = displayMetrics.widthPixels; //??????????????????
            deviceHeigh = displayMetrics.heightPixels; //??????????????????
        }
        for (Size size : choices) {
            if (size.getWidth() <= deviceWidth && size.getHeight() <= deviceHeigh) {
                return size;
            }
        }
        Log.e(TAG, "Couldn't find any suitable video size");
        return choices[choices.length - 1];
    }

    /**
     * Given {@code choices} of {@code Size}s supported by a camera, chooses the smallest one whose
     * width and height are at least as large as the respective requested values, and whose aspect
     * ratio matches with the specified value.
     *
     * @param choices     The list of sizes that the camera supports for the intended output class
     * @param width       The minimum desired width
     * @param height      The minimum desired height
     * @param aspectRatio The aspect ratio
     * @return The optimal {@code Size}, or an arbitrary one if none were big enough
     */
    private static Size chooseOptimalSize(Size[] choices, int width, int height, Size aspectRatio) {
        // Collect the supported resolutions that are at least as big as the preview Surface
        List<Size> bigEnough = new ArrayList<>();
        int w = aspectRatio.getWidth();
        int h = aspectRatio.getHeight();
        for (Size option : choices) {
            if (option.getHeight() == option.getWidth() * h / w &&
                    option.getWidth() >= width && option.getHeight() >= height) {
                bigEnough.add(option);
            }
        }

        // Pick the smallest of those, assuming we found any
        if (bigEnough.size() > 0) {
            Log.i(TAG, "chooseVideoSize: " + Collections.min(bigEnough, new CompareSizesByArea()).getHeight() + "*" + Collections.min(bigEnough, new CompareSizesByArea()).getWidth());
            return Collections.min(bigEnough, new CompareSizesByArea());
        } else {
            Log.e(TAG, "Couldn't find any suitable preview size");
            return choices[0];
        }
    }

    @Override
    public int initLayout() {
        return R.layout.fragment_camera2_video;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void initData() {
        shootingDelay = StorageCustomerInfoUtil.getIntInfo(context, "shootingDelay", 0);
        if (!StorageCustomerInfoUtil.getBooleanInfo("daley", context, true)) {
            shootingDelay = 0;
        }
        recordController.setOnClickListener(this::onClick);
        mCamcorderProfile = CamcorderProfile.get(Camera.CameraInfo.CAMERA_FACING_BACK, CamcorderProfile.QUALITY_HIGH);
        if ( StorageCustomerInfoUtil.getIntInfo(context, "fps", 30)>30) {
            if (CamcorderProfile.hasProfile(CamcorderProfile.QUALITY_HIGH_SPEED_1080P)) {
                Log.i(TAG, "setUpMediaRecorder: support QUALITY_HIGH_SPEED_1080P");
                mCamcorderProfile = CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH_SPEED_1080P);
            } else if (CamcorderProfile.hasProfile(CamcorderProfile.QUALITY_HIGH_SPEED_HIGH)) {
                mCamcorderProfile = CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH_SPEED_HIGH);
                Log.i(TAG, "setUpMediaRecorder: support QUALITY_HIGH_SPEED_HIGH ");
            } else if (CamcorderProfile.hasProfile(CamcorderProfile.QUALITY_HIGH_SPEED_720P)) {
                mCamcorderProfile = CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH_SPEED_720P);
                Log.i(TAG, "setUpMediaRecorder: support QUALITY_HIGH_SPEED_720P");
            } else if (CamcorderProfile.hasProfile(CamcorderProfile.QUALITY_HIGH_SPEED_LOW)) {
                mCamcorderProfile = CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH_SPEED_LOW);
                Log.i(TAG, "setUpMediaRecorder: support QUALITY_HIGH_SPEED_LOW");
            } else if (CamcorderProfile.hasProfile(CamcorderProfile.QUALITY_HIGH_SPEED_480P)) {
                mCamcorderProfile = CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH_SPEED_480P);
                Log.i(TAG, "setUpMediaRecorder: support QUALITY_HIGH_SPEED_480P ");
            } else if (CamcorderProfile.hasProfile(CamcorderProfile.QUALITY_1080P)) {
                mCamcorderProfile = CamcorderProfile.get(CamcorderProfile.QUALITY_1080P);
                Log.i(TAG, "setUpMediaRecorder: support 1080 ");
            }
        }else {
            if (CamcorderProfile.hasProfile(CamcorderProfile.QUALITY_1080P)) {
                Log.i(TAG, "setUpMediaRecorder: support QUALITY_1080P");
                mCamcorderProfile = CamcorderProfile.get(CamcorderProfile.QUALITY_1080P);
            } else if (CamcorderProfile.hasProfile(CamcorderProfile.QUALITY_HIGH)) {
                mCamcorderProfile = CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH);
                Log.i(TAG, "setUpMediaRecorder: support QUALITY_HIGH ");
            } else if (CamcorderProfile.hasProfile(CamcorderProfile.QUALITY_720P)) {
                mCamcorderProfile = CamcorderProfile.get(CamcorderProfile.QUALITY_720P);
                Log.i(TAG, "setUpMediaRecorder: support QUALITY_720P");
            } else if (CamcorderProfile.hasProfile(CamcorderProfile.QUALITY_LOW)) {
                mCamcorderProfile = CamcorderProfile.get(CamcorderProfile.QUALITY_LOW);
                Log.i(TAG, "setUpMediaRecorder: support QUALITY_LOW");
            } else if (CamcorderProfile.hasProfile(CamcorderProfile.QUALITY_480P)) {
                mCamcorderProfile = CamcorderProfile.get(CamcorderProfile.QUALITY_480P);
                Log.i(TAG, "setUpMediaRecorder: support QUALITY_480P ");
            }
        }
    }

    @Override
    public void onPause() {
        closeCamera();
        stopBackgroundThread();
        super.onPause();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.record_controller:
                Log.i(TAG, "onClick:?????????????????? "+ DateUtil.formatDateToHMS2(System.currentTimeMillis()) );
                if (mIsRecordingVideo) {
                    stopRecordingVideo();
                } else {
                    mHandlers.sendEmptyMessage(1);
                }
                break;
        }
    }

    private Handler mHandlers = new Handler() {
        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    if (shootingDelay <= 0) {
                        tvDelayTime.setVisibility(View.GONE);
                        Camera2VideoActivity.this.runOnUiThread(new Runnable() {
                            @RequiresApi(api = Build.VERSION_CODES.M)
                            @Override
                            public void run() {
                                // UI
                                mMediaRecorder.start();
                                Log.i(TAG, "onClick:???????????? "+ DateUtil.formatDateToHMS2(System.currentTimeMillis()) );
                                time = System.currentTimeMillis();
                                mHandlers.sendEmptyMessage(2);
                                mIsRecordingVideo = true;
//                recordController.animate().scaleX(0.8f).scaleY(0.8f).setDuration(500).start();
                                recordController.setTextColor(Color.RED);
                                recordController.setBackgroundResource(R.drawable.small_video_shoot_stop);
                                recordController.setText("??????");
                                // Start recording
                            }
                        });
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

    /**
     * Starts a background thread and its {@link Handler}.
     */
    private void startBackgroundThread() {
        mBackgroundThread = new HandlerThread("CameraBackground");
        mBackgroundThread.start();
        mBackgroundHandler = new Handler(mBackgroundThread.getLooper());
    }

    /**
     * Stops the background thread and its {@link Handler}.
     */
    private void stopBackgroundThread() {
        mBackgroundThread.quitSafely();
        try {
            mBackgroundThread.join();
            mBackgroundThread = null;
            mBackgroundHandler = null;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets whether you should show UI with rationale for requesting permissions.
     *
     * @param permissions The permissions your app wants to request.
     * @return Whether you can show permission rationale UI.
     */
    private boolean shouldShowRequestPermissionRationale(String[] permissions) {
        for (String permission : permissions) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Requests permissions needed for recording video.
     */
    private void requestVideoPermissions() {
        if (shouldShowRequestPermissionRationale(VIDEO_PERMISSIONS)) {
            new ConfirmationDialog().show(getFragmentManager(), FRAGMENT_DIALOG);
        } else {
            ActivityCompat.requestPermissions(this, VIDEO_PERMISSIONS, REQUEST_VIDEO_PERMISSIONS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult");
        if (requestCode == REQUEST_VIDEO_PERMISSIONS) {
            if (grantResults.length == VIDEO_PERMISSIONS.length) {
                for (int result : grantResults) {
                    if (result != PackageManager.PERMISSION_GRANTED) {
                        ErrorDialog.newInstance("permission_request")
                                .show(getFragmentManager(), FRAGMENT_DIALOG);
                        break;
                    }
                }
            } else {
                ErrorDialog.newInstance("permission_request")
                        .show(getFragmentManager(), FRAGMENT_DIALOG);
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private boolean hasPermissionsGranted(String[] permissions) {
        for (String permission : permissions) {
            if (ActivityCompat.checkSelfPermission(this, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
//            case KeyEvent.KEYCODE_BACK:
//                ExitDialog exitDialog=new ExitDialog();
//                exitDialog.setOnClickListener(new ExitDialog.OnClickListener() {
//                    @Override
//                    public void onClick() {
//                        if (mMediaRecorder!=null) {
//                            mMediaRecorder.release();
//                            mHandlers.removeMessages(2);
//                            File file = new File(mNextVideoAbsolutePath);
//                            Log.i(TAG, "onClick: "+file.getAbsolutePath());
//                            if (file != null) {
//                                file.delete();
//                            }
//                        }
//                        finish();
//                    }
//                });
//                exitDialog.show(getSupportFragmentManager(),"");
//                break;
            case KeyEvent.KEYCODE_VOLUME_UP:
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                if (mIsRecordingVideo) {
                    stopRecordingVideo();
                } else {
                    mHandlers.sendEmptyMessage(1);
                }
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onResume() {
        super.onResume();
        startBackgroundThread();
        mPreviewSize = mVideoSize = new Size(mCamcorderProfile.videoFrameWidth, mCamcorderProfile.videoFrameHeight);
        rotation =this.getWindowManager().getDefaultDisplay().getRotation();
        if (Surface.ROTATION_90 == rotation || Surface.ROTATION_270 == rotation) {
            mTextureView.setAspectRatio(mPreviewSize.getWidth(), mPreviewSize.getHeight());
        } else {
            mTextureView.setAspectRatio(mPreviewSize.getHeight(), mPreviewSize.getWidth());
        }
        configureTransform(mTextureView.getWidth(), mTextureView.getHeight());
        mMediaRecorder = new MediaRecorder();
        if (mTextureView.isAvailable()) {
            openCamera(mTextureView.getWidth(), mTextureView.getHeight());
        } else {
            mTextureView.setSurfaceTextureListener(mSurfaceTextureListener);
        }
    }

    /**
     * Tries to open a {@link CameraDevice}. The result is listened by `mStateCallback`.
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressWarnings("MissingPermission")
    private void openCamera(int width, int height) {
        if (!hasPermissionsGranted(VIDEO_PERMISSIONS)) {
            requestVideoPermissions();
            return;
        }
        CameraManager manager = (CameraManager) this.getSystemService(Context.CAMERA_SERVICE);
        try {
            if (!mCameraOpenCloseLock.tryAcquire(2500, TimeUnit.MILLISECONDS)) {
                throw new RuntimeException("Time out waiting to lock camera opening.");
            }
            String cameraId = manager.getCameraIdList()[0];

            // Choose the sizes for camera preview and video recording
            CameraCharacteristics characteristics = manager.getCameraCharacteristics(cameraId);
            StreamConfigurationMap map = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
            mSensorOrientation = characteristics.get(CameraCharacteristics.SENSOR_ORIENTATION);
            int fpsNum = StorageCustomerInfoUtil.getIntInfo(context, "fps", 30);
            StreamConfigurationMap fpsMap = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
            Range<Integer>[] fpsRange = fpsNum > 30 ? fpsMap.getHighSpeedVideoFpsRanges() : characteristics.get(CameraCharacteristics.CONTROL_AE_AVAILABLE_TARGET_FPS_RANGES);
            for (int i = 0; i < fpsRange.length; i++) {
                Range<Integer> mFps = fpsRange[i];
                int max = mFps.getUpper();
                int min = mFps.getLower();
                if (max == min && max == fpsNum) {
                    fps = mFps;
                }
            }
            if (map == null) {
                throw new RuntimeException("Cannot get available preview/video sizes");
            }
//            mVideoSize = chooseVideoSize(map.getOutputSizes(MediaRecorder.class));
//            mPreviewSize = chooseOptimalSize(map.getOutputSizes(SurfaceTexture.class),
//                    width, height, mVideoSize);
            manager.openCamera(cameraId, mStateCallback, null);
        } catch (CameraAccessException e) {
            Toast.makeText(this, "Cannot access the camera.", Toast.LENGTH_SHORT).show();
            finish();
        } catch (NullPointerException e) {
            // Currently an NPE is thrown when the Camera2API is used but not supported on the
            // device this code runs.
            ErrorDialog.newInstance("gR.string.camera_error").show(getFragmentManager(), FRAGMENT_DIALOG);
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException("Interrupted while trying to lock camera opening.");
        }
    }

    private void closeCamera() {
        try {
            mCameraOpenCloseLock.acquire();
            closePreviewSession();
            if (null != mCameraDevice) {
                mCameraDevice.close();
                mCameraDevice = null;
            }
            if (null != mMediaRecorder) {
                mMediaRecorder.release();
                mMediaRecorder = null;
            }
        } catch (InterruptedException e) {
            throw new RuntimeException("Interrupted while trying to lock camera closing.");
        } finally {
            mCameraOpenCloseLock.release();
        }
    }

    /**
     * Update the camera preview. {@link #startPreview()} needs to be called in advance.
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void updatePreview() {
        if (null == mCameraDevice) {
            return;
        }
        try {
            setUpCaptureRequestBuilder(mPreviewBuilder);
            HandlerThread thread = new HandlerThread("CameraPreview");
            thread.start();
            if (fps.getLower() > 30) {
                List<CaptureRequest> mPreviewBuilderBurst = mHighSpeedPreviewSession.createHighSpeedRequestList(mPreviewBuilder.build());
                mHighSpeedPreviewSession.setRepeatingBurst(mPreviewBuilderBurst, mCaptureCallback, mBackgroundHandler);
            } else {
                mPreviewSession.setRepeatingRequest(mPreviewBuilder.build(), mCaptureCallback, mBackgroundHandler);
            }
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private void setUpCaptureRequestBuilder(CaptureRequest.Builder builder) {
        builder.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO);
    }

    /**
     * Configures the necessary {@link Matrix} transformation to `mTextureView`.
     * This method should not to be called until the camera preview size is determined in
     * openCamera, or until the size of `mTextureView` is fixed.
     *
     * @param viewWidth  The width of `mTextureView`
     * @param viewHeight The height of `mTextureView`
     */
    private void configureTransform(int viewWidth, int viewHeight) {
        if (null == mTextureView || null == mPreviewSize) {
            return;
        }
        Matrix matrix = new Matrix();
        RectF viewRect = new RectF(0, 0, viewWidth, viewHeight);
        RectF bufferRect = new RectF(0, 0, mPreviewSize.getHeight(), mPreviewSize.getWidth());
        float centerX = viewRect.centerX();
        float centerY = viewRect.centerY();
        if (Surface.ROTATION_90 == rotation || Surface.ROTATION_270 == rotation) {
            bufferRect.offset(centerX - bufferRect.centerX(), centerY - bufferRect.centerY());
            matrix.setRectToRect(viewRect, bufferRect, Matrix.ScaleToFit.FILL);
            float scale = Math.max(
                    (float) viewHeight / mPreviewSize.getHeight(),
                    (float) viewWidth / mPreviewSize.getWidth());
            matrix.postScale(scale, scale, centerX, centerY);
            matrix.postRotate(90 * (rotation - 2), centerX, centerY);
        }
        mTextureView.setTransform(matrix);
    }

    private void setUpMediaRecorder() throws IOException {
        if (mNextVideoAbsolutePath != null) {
            return;
        }
        mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.SURFACE);
        mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        mNextVideoAbsolutePath = getVideoFilePath();
        mMediaRecorder.setOutputFile(mNextVideoAbsolutePath);
//        if (fps.getLower() > 30) {
            mMediaRecorder.setAudioEncoder(mCamcorderProfile.audioCodec);
            mMediaRecorder.setVideoEncoder(mCamcorderProfile.videoCodec);
            mMediaRecorder.setVideoSize(mCamcorderProfile.videoFrameWidth, mCamcorderProfile.videoFrameHeight);
            mMediaRecorder.setVideoFrameRate(mCamcorderProfile.videoFrameRate);
            mMediaRecorder.setVideoEncodingBitRate(mCamcorderProfile.videoBitRate);
            mMediaRecorder.setAudioEncodingBitRate(mCamcorderProfile.audioBitRate);
            mMediaRecorder.setAudioChannels(mCamcorderProfile.audioChannels);
            mMediaRecorder.setAudioSamplingRate(mCamcorderProfile.audioSampleRate);
//            mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);//???????????????????????????????????????????????????????????????app?????????????????????????????????????????????AAC
//            mMediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);//???????????????????????????????????????????????????????????????app?????????????????????????????????????????????H264
//            mMediaRecorder.setVideoEncodingBitRate(16 * 24 * mVideoSize.getWidth() * mVideoSize.getHeight());//??????????????? ????????? 1*????????? ??? 10*????????? ???????????????????????????????????????????????????????????????????????????
//            mMediaRecorder.setVideoFrameRate(mCamcorderProfile.videoFrameRate);//???????????? ?????? 30????????? ??????????????????????????????????????????????????????????????????????????????????????????????????????????????????30?????????
//            mMediaRecorder.setAudioSamplingRate(44100);
//            mMediaRecorder.setVideoSize(mVideoSize.getWidth(), mVideoSize.getHeight());
//        } else {
//            mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);//???????????????????????????????????????????????????????????????app?????????????????????????????????????????????AAC
//            mMediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);//???????????????????????????????????????????????????????????????app?????????????????????????????????????????????H264
//            mMediaRecorder.setVideoEncodingBitRate(16 * 24 * mVideoSize.getWidth() * mVideoSize.getHeight());//??????????????? ????????? 1*????????? ??? 10*????????? ???????????????????????????????????????????????????????????????????????????
//            mMediaRecorder.setVideoFrameRate(30);//???????????? ?????? 30????????? ??????????????????????????????????????????????????????????????????????????????????????????????????????????????????30?????????
//            mMediaRecorder.setAudioSamplingRate(44100);
//            mMediaRecorder.setVideoSize(mVideoSize.getWidth(), mVideoSize.getHeight());
//        }
        switch (mSensorOrientation) {
            case SENSOR_ORIENTATION_DEFAULT_DEGREES:
                mMediaRecorder.setOrientationHint(DEFAULT_ORIENTATIONS.get(rotation));
                break;
            case SENSOR_ORIENTATION_INVERSE_DEGREES:
                mMediaRecorder.setOrientationHint(INVERSE_ORIENTATIONS.get(rotation));
                break;
        }
        mMediaRecorder.prepare();

    }

    private String getVideoFilePath() {
        return context.getExternalFilesDir(Environment.DIRECTORY_DCIM) + "/" + DateUtil.formatDateToHMS2(System.currentTimeMillis()) + ".mp4";
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void startPreview() {
        if (null == mCameraDevice || !mTextureView.isAvailable() || null == mPreviewSize) {
            return;
        }
        try {
            closePreviewSession();
            SurfaceTexture texture = mTextureView.getSurfaceTexture();
            assert texture != null;
            texture.setDefaultBufferSize(mPreviewSize.getWidth(), mPreviewSize.getHeight());
            Long km = StorageCustomerInfoUtil.getLongInfo(context, "km", (long) 0);
            long min = StorageCustomerInfoUtil.getLongInfo(context, "KMmin", (long) 1000 * 1000);
            long max = StorageCustomerInfoUtil.getLongInfo(context, "KMmax", (long) 10 * 1000 * 1000);
            if (km > 0) {
                km = Math.max(Math.min(max, km), min);

            }
            mPreviewBuilder = mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_RECORD);
            if (km>0){
                mPreviewBuilder.set(CaptureRequest.SENSOR_EXPOSURE_TIME, km);
            }
            mPreviewBuilder.set(CaptureRequest.CONTROL_AF_MODE, CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_VIDEO);
            mPreviewBuilder.set(CaptureRequest.CONTROL_AE_MODE, CaptureRequest.CONTROL_AE_MODE_ON);
            mPreviewBuilder.set(CaptureRequest.CONTROL_AE_TARGET_FPS_RANGE, fps);
            List<Surface> surfaces = new ArrayList<>();

            // Set up Surface for the camera preview
            Surface previewSurface = new Surface(texture);
            surfaces.add(previewSurface);
            mPreviewBuilder.addTarget(previewSurface);

            // Start a capture session
            // Once the session starts, we can update the UI and start recording
            if (fps.getLower() > 30) {
                mCameraDevice.createConstrainedHighSpeedCaptureSession(surfaces,
                        new CameraCaptureSession.StateCallback() {
                            @Override
                            public void onConfigured(@NonNull CameraCaptureSession session) {
                                mHighSpeedPreviewSession = (CameraConstrainedHighSpeedCaptureSession) session;
                                updatePreview();
                            }

                            @Override
                            public void onConfigureFailed(@NonNull CameraCaptureSession session) {
                                Toast.makeText(Camera2VideoActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                            }
                        }, mBackgroundHandler);
            } else {
                mCameraDevice.createCaptureSession(surfaces, new CameraCaptureSession.StateCallback() {
                    @Override
                    public void onConfigured(@NonNull CameraCaptureSession cameraCaptureSession) {
                        mPreviewSession = cameraCaptureSession;
                        updatePreview();
                    }

                    @Override
                    public void onConfigureFailed(@NonNull CameraCaptureSession cameraCaptureSession) {
                        Toast.makeText(Camera2VideoActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                    }
                }, mBackgroundHandler);
            }
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private void closePreviewSession() {
        if (mPreviewSession != null) {
            mPreviewSession.close();
            mPreviewSession = null;
        }
        if (mHighSpeedPreviewSession != null) {
            mHighSpeedPreviewSession.close();
            mHighSpeedPreviewSession = null;
        }
    }

    private CameraCaptureSession.CaptureCallback mCaptureCallback
            = new CameraCaptureSession.CaptureCallback() {

        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void onCaptureCompleted(@NonNull CameraCaptureSession session, @NonNull CaptureRequest request, @NonNull TotalCaptureResult result) {
            super.onCaptureCompleted(session, request, result);
            if (state) {
                if (null == mCameraDevice || !mTextureView.isAvailable() || null == mPreviewSize) {
                    return;
                }
                try {
                    closePreviewSession();
                    setUpMediaRecorder();
                    SurfaceTexture texture = mTextureView.getSurfaceTexture();
                    assert texture != null;
                    texture.setDefaultBufferSize(mPreviewSize.getWidth(), mPreviewSize.getHeight());
                    Long km = StorageCustomerInfoUtil.getLongInfo(context, "km", (long) 0);
                    long min = StorageCustomerInfoUtil.getLongInfo(context, "KMmin", (long) 1000 * 1000);
                    long max = StorageCustomerInfoUtil.getLongInfo(context, "KMmax", (long) 10 * 1000 * 1000);
                    if (km > 0) {
                        km = Math.max(Math.min(max, km), min);
                    }
                    mPreviewBuilder = mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_RECORD);
//                    mPreviewBuilder.set(CaptureRequest.CONTROL_AF_MODE, result.get(CaptureResult.CONTROL_AF_MODE));
                    mPreviewBuilder.set(CaptureRequest.CONTROL_AF_TRIGGER, CameraMetadata.CONTROL_AF_TRIGGER_IDLE);
                    mPreviewBuilder.set(CaptureRequest.CONTROL_AE_PRECAPTURE_TRIGGER, CameraMetadata.CONTROL_AE_PRECAPTURE_TRIGGER_IDLE);
                    if (km == 0) {
                        mPreviewBuilder.set(CaptureRequest.CONTROL_AE_MODE, CaptureRequest.CONTROL_AE_MODE_ON);
                    } else {
                        mPreviewBuilder.set(CaptureRequest.CONTROL_AE_MODE, CaptureRequest.CONTROL_AE_MODE_OFF);
                        mPreviewBuilder.set(CaptureRequest.LENS_FOCUS_DISTANCE, result.get(CaptureResult.LENS_FOCUS_DISTANCE));
                        mPreviewBuilder.set(CaptureRequest.CONTROL_AE_EXPOSURE_COMPENSATION, result.get(CaptureResult.CONTROL_AE_EXPOSURE_COMPENSATION));
                        mPreviewBuilder.set(CaptureRequest.SENSOR_SENSITIVITY, result.get(CaptureResult.SENSOR_SENSITIVITY));
                        mPreviewBuilder.set(CaptureRequest.SENSOR_EXPOSURE_TIME, result.get(CaptureResult.SENSOR_EXPOSURE_TIME));
                        mPreviewBuilder.set(CaptureRequest.SENSOR_FRAME_DURATION, result.get(CaptureResult.SENSOR_FRAME_DURATION));
                    }
                    mPreviewBuilder.set(CaptureRequest.CONTROL_AE_TARGET_FPS_RANGE, fps);
                    List<Surface> surfaces = new ArrayList<>();

                    // Set up Surface for the camera preview
                    Surface previewSurface = new Surface(texture);
                    surfaces.add(previewSurface);
                    mPreviewBuilder.addTarget(previewSurface);

                    Surface recorderSurface = mMediaRecorder.getSurface();
                    surfaces.add(recorderSurface);
                    mPreviewBuilder.addTarget(recorderSurface);
                    // Start a capture session
                    // Once the session starts, we can update the UI and start recording
                    if (fps.getLower() > 30) {
                        mCameraDevice.createConstrainedHighSpeedCaptureSession(surfaces,
                                new CameraCaptureSession.StateCallback() {

                                    @Override
                                    public void onConfigured(@NonNull CameraCaptureSession session) {
                                        mHighSpeedPreviewSession = (CameraConstrainedHighSpeedCaptureSession) session;
                                        updatePreview();
                                    }

                                    @Override
                                    public void onConfigureFailed(@NonNull CameraCaptureSession session) {
                                        Toast.makeText(Camera2VideoActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                                    }
                                }, mBackgroundHandler);
                    } else {
                        mCameraDevice.createCaptureSession(surfaces, new CameraCaptureSession.StateCallback() {
                            @Override
                            public void onConfigured(@NonNull CameraCaptureSession cameraCaptureSession) {
                                mPreviewSession = cameraCaptureSession;
                                updatePreview();
                            }

                            @Override
                            public void onConfigureFailed(@NonNull CameraCaptureSession cameraCaptureSession) {
                                Toast.makeText(Camera2VideoActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                            }
                        }, mBackgroundHandler);
                    }
                } catch (CameraAccessException | IOException e) {
                    e.printStackTrace();
                }
                state = false;
            }
        }
    };

    private void stopRecordingVideo() {
        // UI
        mIsRecordingVideo = false;
        // Stop recording
        mMediaRecorder.stop();
        mMediaRecorder.release();
        mHandlers.removeMessages(2);
        File file = new File(mNextVideoAbsolutePath);
        String newFile = context.getExternalFilesDir(Environment.DIRECTORY_DCIM) + "/" + DateUtil.formatDateToHMS2(time) + ".mp4";
        if (file != null) {
            file.renameTo(new File(newFile));
        }
        setResult(RESULT_OK, new Intent().putExtra("path", newFile).putExtra("time", time));
        finish();
//        startPreview();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mMediaRecorder!=null) {
            mMediaRecorder.release();
            mHandlers.removeMessages(2);
            if (mIsRecordingVideo) {
                File file = new File(mNextVideoAbsolutePath);
                if (file != null) {
                    file.delete();
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    /**
     * Compares two {@code Size}s based on their areas.
     */
    static class CompareSizesByArea implements Comparator<Size> {

        @Override
        public int compare(Size lhs, Size rhs) {
            // We cast here to ensure the multiplications won't overflow
            return Long.signum((long) lhs.getWidth() * lhs.getHeight() -
                    (long) rhs.getWidth() * rhs.getHeight());
        }

    }

    public static class ErrorDialog extends DialogFragment {

        private static final String ARG_MESSAGE = "message";

        public static ErrorDialog newInstance(String message) {
            ErrorDialog dialog = new ErrorDialog();
            Bundle args = new Bundle();
            args.putString(ARG_MESSAGE, message);
            dialog.setArguments(args);
            return dialog;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Activity activity = getActivity();
            return new AlertDialog.Builder(activity)
                    .setMessage(getArguments().getString(ARG_MESSAGE))
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            activity.finish();
                        }
                    })
                    .create();
        }

    }

    @SuppressLint("ValidFragment")
    public class ConfirmationDialog extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            return new AlertDialog.Builder(getActivity())
                    .setMessage("R.string.permission_request")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(Camera2VideoActivity.this, VIDEO_PERMISSIONS,
                                    REQUEST_VIDEO_PERMISSIONS);
                        }
                    })
                    .setNegativeButton(android.R.string.cancel,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Camera2VideoActivity.this.finish();
                                }
                            })
                    .create();
        }
    }
}
