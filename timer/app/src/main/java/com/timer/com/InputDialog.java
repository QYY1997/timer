package com.timer.com;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.timer.com.bean.HintModel;
import com.timer.com.util.StringUtil;

import java.math.BigDecimal;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author: lilingfei
 * @description:
 * @date: 2019/7/10
 */
public class InputDialog extends DialogFragment {

    public Dialog loadingDialog;
    Unbinder unbinder;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.ll_cancel)
    LinearLayout llCancel;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.et_value)
    EditText etValue;
    @BindView(R.id.tv_cancel)
    TextView tvCancel;
    private HintModel model;
    private Activity mActivity;

    private OnClickListener onClickListener = null;

    public static InputDialog getInstance(HintModel content) {
        InputDialog dialog = new InputDialog();
        Bundle bundle = new Bundle();
        bundle.putSerializable("model", content);
        dialog.setArguments(bundle);
        return dialog;
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.custom_Dialog);
        mActivity = getActivity();
        Bundle bundle = getArguments();
        if (bundle != null) {
            model = (HintModel) getArguments().getSerializable("model");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null && dialog.getWindow() != null) {
            dialog.getWindow().setLayout((int) (getScreenWidth(mActivity) * 0.7), ViewGroup.LayoutParams.WRAP_CONTENT);
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
        View view = inflater.inflate(R.layout.dialog_auth, container);
        unbinder = ButterKnife.bind(this, view);
        initData();
        return view;
    }

    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
        int screenHeight = dm.heightPixels;
        return screenWidth;
    }

    private void initData() {
        tvContent.setText(model.getContent());
        tvTitle.setText(StringUtil.isEmpty(model.getTitle()) ? "温馨提示" : model.getTitle());
        if (!model.isNum()) {
            etValue.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL);
        }
    }

    @OnClick({R.id.tv_cancel, R.id.tv_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel:
                dismiss();
                break;
            case R.id.tv_submit:
                if (onClickListener != null) {
                    onClickListener.onClick(new BigDecimal(etValue.getText().toString().trim()));
                }
                dismiss();
                break;
        }
    }

    public interface OnClickListener {
        void onClick(BigDecimal value);
    }
}
