package com.timer.com;

import android.app.Dialog;
import android.content.Context;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Range;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @作者 chenlanxin
 * @创建日期 2019/2/27 10:47
 * @功能 公告
 **/
public class FPSSetDialog extends DialogFragment {
    Unbinder unbinder;
    @BindView(R.id.rl_30)
    RelativeLayout rl30;
    @BindView(R.id.rl_120)
    RelativeLayout rl120;
    @BindView(R.id.rl_240)
    RelativeLayout rl240;
    @BindView(R.id.rl_cancel)
    RelativeLayout rlCancel;
    private OnClickListener on30ClickListener = null;
    private OnClickListener on120ClickListener = null;
    private OnClickListener on240ClickListener = null;

    public void setOn30ClickListener(OnClickListener on30ClickListener) {
        this.on30ClickListener = on30ClickListener;
    }

    public void setOn120ClickListener(OnClickListener on120ClickListener) {
        this.on120ClickListener = on120ClickListener;
    }

    public void setOn240ClickListener(OnClickListener on240ClickListener) {
        this.on240ClickListener = on240ClickListener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.custom_Dialog);
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            Window window = getDialog().getWindow();
            //设置弹出位置
            window.setGravity(Gravity.BOTTOM);
            //设置弹出动画
            window.setWindowAnimations(R.style.ActionSheetDialogAnimation);
            //设置对话框大小
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_fps, container);
        unbinder = ButterKnife.bind(this, view);
        initData();
        return view;
    }

    private void initData() {
        try {
            CameraManager cameraManager = (CameraManager) getActivity().getSystemService(Context.CAMERA_SERVICE);
            CameraCharacteristics characteristics = cameraManager.getCameraCharacteristics("0");
            StreamConfigurationMap fpsMap = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
            Range<Integer>[] fpsRange = fpsMap.getHighSpeedVideoFpsRanges();
            for (int i = 0; i < fpsRange.length; i++) {
                Range<Integer> mFps = fpsRange[i];
                int max = mFps.getUpper();
                int min = mFps.getLower();
                if (max == min && max == 120) {
                    rl120.setVisibility(View.VISIBLE);
                }
                if (max == min && max == 240) {
                    rl240.setVisibility(View.VISIBLE);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @OnClick({R.id.rl_30, R.id.rl_120, R.id.rl_240, R.id.rl_cancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_30:
                dismiss();
                if (on30ClickListener != null) {
                    on30ClickListener.onClick();
                }
                break;
            case R.id.rl_120:
                dismiss();
                if (on120ClickListener != null) {
                    on120ClickListener.onClick();
                }
                break;
            case R.id.rl_240:
                dismiss();
                if (on240ClickListener != null) {
                    on240ClickListener.onClick();
                }
                break;
            case R.id.rl_cancel:
                dismiss();
                break;
        }
    }

    public interface OnClickListener {
        void onClick();
    }
}
