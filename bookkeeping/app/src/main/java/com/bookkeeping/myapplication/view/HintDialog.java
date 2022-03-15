package com.bookkeeping.myapplication.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.bookkeeping.myapplication.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author: qiuyiyang
 * @description:
 * @date: 2019/7/10
 */
public class HintDialog extends DialogFragment {

    public Dialog loadingDialog;
    Unbinder unbinder;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.ll_cancel)
    LinearLayout llCancel;
    @BindView(R.id.tv_know)
    TextView tvKnow;
    @BindView(R.id.ll_submit)
    LinearLayout llSubmit;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    private Activity mActivity;
    private String content;

    private OnClickListener onClickListener = null;

    private OnClickListener onCancelClickListener = null;

    public static HintDialog getInstance(String content) {
        HintDialog dialog = new HintDialog();
        Bundle bundle = new Bundle();
        bundle.putString("content", content);
        dialog.setArguments(bundle);
        return dialog;
    }
    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public void setOnCancelClickListener(OnClickListener onCancelClickListener) {
        this.onCancelClickListener = onCancelClickListener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.custom_Dialog);
        mActivity = getActivity();
        Bundle bundle = getArguments();
        if (bundle != null) {
            content =  getArguments().getString("content");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        WindowManager wm = (WindowManager) mActivity.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
        int screenHeight = dm.heightPixels;
        if (dialog != null && dialog.getWindow() != null) {
            dialog.getWindow().setLayout((int) (screenWidth * 0.7), ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
        unbinder.unbind();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_hint, container);
        unbinder = ButterKnife.bind(this, view);
        initData();
        return view;
    }

    private void initData() {
        tvContent.setText(content);
    }

    @OnClick({R.id.tv_cancel, R.id.tv_submit,R.id.tv_know})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel:
                if (onCancelClickListener != null) {
                    onCancelClickListener.onClick();
                }
                dismiss();
                break;
            case R.id.tv_know:
            case R.id.tv_submit:
                if (onClickListener != null) {
                    onClickListener.onClick();
                }
                dismiss();
                break;
        }
    }
    public interface OnClickListener {
        void onClick();
    }
}
