package com.timer.com;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.timer.com.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @作者 chenlanxin
 * @创建日期 2019/2/27 10:47
 * @功能 公告
 **/
public class NavigationDialog extends DialogFragment {

    Unbinder unbinder;
    private OnClickListener onLocalClickListener = null;
    private OnClickListener onPhotoClickListener = null;

    public static NavigationDialog getInstance() {
        NavigationDialog dialog = new NavigationDialog();
        return dialog;
    }

    public void setOnLocalClickListener(OnClickListener onLocalClickListener) {
        this.onLocalClickListener = onLocalClickListener;
    }

    public void setOnPhotoClickListener(OnClickListener onPhotoClickListener) {
        this.onPhotoClickListener = onPhotoClickListener;
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
        View view = inflater.inflate(R.layout.dialog_map, container);
        unbinder = ButterKnife.bind(this, view);
        initData();
        return view;
    }

    private void initData() {

    }

    @OnClick({R.id.rl_local, R.id.rl_photo, R.id.rl_cancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_local:
                dismiss();
                if (onLocalClickListener != null) {
                    onLocalClickListener.onClick();
                }
                break;
            case R.id.rl_photo:
                dismiss();
                if (onPhotoClickListener != null) {
                    onPhotoClickListener.onClick();
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
