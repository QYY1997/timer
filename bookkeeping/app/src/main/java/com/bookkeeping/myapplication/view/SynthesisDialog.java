package com.bookkeeping.myapplication.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.bookkeeping.myapplication.MyApplication;
import com.bookkeeping.myapplication.R;
import com.bookkeeping.myapplication.model.MaterialModel;
import com.bookkeeping.myapplication.model.SynthesisModel;
import com.bookkeeping.myapplication.util.CommonUtils;
import com.bookkeeping.myapplication.util.StorageCustomerInfo02Util;
import com.bookkeeping.myapplication.util.StringUtil;
import com.bookkeeping.myapplication.util.UserDao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author: qiuyiyang
 * @description:
 * @date: 2019/7/10
 */
public class SynthesisDialog extends DialogFragment {
    Unbinder unbinder;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.sp_type)
    Spinner spType;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.sp_name)
    Spinner spName;
    @BindView(R.id.et_number)
    EditText etNumber;
    @BindView(R.id.tv_is_synthesis)
    TextView tvIsSynthesis;
    @BindView(R.id.tv_cancel)
    TextView tvCancel;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.ll_cancel)
    LinearLayout llCancel;


    private List<String> mListType = new ArrayList<>();
    private List<String> mListName = new ArrayList<>();
    private Activity mActivity;
    private SynthesisModel synthesisModel=new SynthesisModel();
    private ArrayAdapter<String> mAdapterOut, mAdapterIn;
    private OnClickListener onClickListener = null;
    private boolean isSynthesis=false;
    private UserDao userDao;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.custom_Dialog);
        mActivity = getActivity();
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
        View view = inflater.inflate(R.layout.dialog_synthesis, container);
        unbinder = ButterKnife.bind(this, view);
        initData();
        return view;
    }

    private void initData() {
        userDao = MyApplication.getUserDao();
        mListName.add("请选择材料");
        initSpinnerType();
    }

    private void initSpinnerType() {
        mListType.add("请选择类型");
        mListType.addAll(userDao.findMaterialTypeSql());
        mAdapterOut = new ArrayAdapter<>(mActivity, R.layout.spinner_layout, mListType);
        mAdapterOut.setDropDownViewResource(R.layout.spiner_drop_down_style);
        spType.setAdapter(mAdapterOut);
        spType.setDropDownVerticalOffset(60);
        spType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position!=0){
                    String type = mListType.get(position);
                    mListName.addAll(userDao.findMaterial(" where type='" + type + "'"));
                    tvIsSynthesis.setVisibility(type.contains("合成")?View.VISIBLE:View.GONE);
                    initSpinnerName();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void initSpinnerName() {
        mAdapterIn = new ArrayAdapter<>(mActivity, R.layout.spinner_layout, mListName);
        mAdapterIn.setDropDownViewResource(R.layout.spiner_drop_down_style);
        spName.setAdapter(mAdapterIn);
        spName.setDropDownVerticalOffset(60);
        spName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position!=0){
                    MaterialModel materialModel = userDao.findMaterialSql(" where name='"+mListName.get(position)+"'");
                    synthesisModel.setChildID(materialModel.getId());
                    synthesisModel.setId(CommonUtils.getRandomString());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @OnClick({R.id.tv_cancel, R.id.tv_submit, R.id.tv_is_synthesis})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel:
                dismiss();
                break;
            case R.id.tv_is_synthesis:
                isSynthesis=!isSynthesis;
                tvIsSynthesis.setText(isSynthesis?"自合":"材料");
                tvIsSynthesis.setBackgroundResource(isSynthesis?R.drawable.shape_soild_red_corner_5:R.drawable.submit);
                break;
            case R.id.tv_submit:
                if (spType.getSelectedItemPosition()==0) {
                    Toast.makeText(mActivity, "请选择类型！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (spName.getSelectedItemPosition()==0) {
                    Toast.makeText(mActivity, "请选择材料！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (StringUtil.isEmpty(etNumber.getText().toString().trim())) {
                    Toast.makeText(mActivity, "数量不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if ("0".equals(etNumber.getText().toString().trim())) {
                    Toast.makeText(mActivity, "请输入正确的数量", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (onClickListener != null) {
                    synthesisModel.setNumber(Integer.parseInt(etNumber.getText().toString()));
                    synthesisModel.setIsSynthesis(isSynthesis?1:0);
                    onClickListener.onClick(synthesisModel);
                }
                dismiss();
                break;
        }
    }

    public interface OnClickListener {
        void onClick(SynthesisModel synthesisModel);
    }
}
