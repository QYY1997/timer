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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bookkeeping.myapplication.R;
import com.bookkeeping.myapplication.model.TypeModel;
import com.bookkeeping.myapplication.util.CommonUtils;

import java.math.BigDecimal;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author: qiuyiyang
 * @description:
 * @date: 2019/7/10
 */
public class TypeDialog extends DialogFragment {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_type)
    EditText etType;
    @BindView(R.id.et_balance)
    EditText etBalance;
    @BindView(R.id.tv_cancel)
    TextView tvCancel;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.ll_cancel)
    LinearLayout llCancel;
    Unbinder unbinder;
    private Activity mActivity;
    private TypeModel typeModel;

    private OnClickListener onClickListener = null;

    public static TypeDialog getInstance(TypeModel typeModel) {
        TypeDialog dialog = new TypeDialog();
        Bundle bundle = new Bundle();
        bundle.putSerializable("typeModel", typeModel);
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
            typeModel = (TypeModel) getArguments().getSerializable("typeModel");
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
        unbinder.unbind();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_type, container);
        unbinder = ButterKnife.bind(this, view);
        initData();
        return view;
    }

    private void initData() {
        etType.setFilters(CommonUtils.getInputFilters(20));
        if (typeModel!=null){
            tvTitle.setText("????????????");
            etBalance.setText(typeModel.getBalance()+"");
            etType.setText(typeModel.getId());
        }
        else {
            typeModel=new TypeModel();
            tvTitle.setText("????????????");
        }
    }

    @OnClick({R.id.tv_cancel, R.id.tv_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel:
                dismiss();
                break;
            case R.id.tv_submit:
                typeModel.setBalance(new BigDecimal(etBalance.getText().toString().trim()));
                typeModel.setId(etType.getText().toString().trim());
                if (onClickListener != null) {
                    onClickListener.onClick(typeModel);
                }
                dismiss();
                break;
        }
    }

    public interface OnClickListener {
        void onClick(TypeModel typeModel);
    }
}
