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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bookkeeping.myapplication.MyApplication;
import com.bookkeeping.myapplication.R;
import com.bookkeeping.myapplication.model.TypeModel;
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
public class TransferDialog extends DialogFragment {
    Unbinder unbinder;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_type_out)
    TextView tvTypeOut;
    @BindView(R.id.sp_type_out)
    Spinner spTypeOut;
    @BindView(R.id.tv_type_in)
    TextView tvTypeIn;
    @BindView(R.id.sp_type_in)
    Spinner spTypeIn;
    @BindView(R.id.et_trx_money)
    EditText etTrxMoney;
    @BindView(R.id.tv_cancel)
    TextView tvCancel;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.ll_cancel)
    LinearLayout llCancel;

    private List<String> mList = new ArrayList<>();
    private Activity mActivity;
    private TypeModel typeModelOut, typeModelIn;
    private ArrayAdapter<String> mAdapterOut,mAdapterIn;
    private OnClickListener onClickListener = null;
    private UserDao userDao;

    public static TransferDialog getInstance() {
        TransferDialog dialog = new TransferDialog();
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
        View view = inflater.inflate(R.layout.dialog_transfer, container);
        unbinder = ButterKnife.bind(this, view);
        initData();
        return view;
    }

    private void initData() {
        typeModelIn=new TypeModel();
        typeModelOut=new TypeModel();
        userDao=MyApplication.getUserDao();
        mList.add("请选择类型");
        mList.addAll(userDao.findAllType());
        initSpinnerIn();
        initSpinnerOut();
    }


    private void initSpinnerOut() {
        mAdapterOut = new ArrayAdapter<>(mActivity, R.layout.spinner_layout, mList);
        mAdapterOut.setDropDownViewResource(R.layout.spiner_drop_down_style);
        spTypeOut.setAdapter(mAdapterOut);
        spTypeOut.setDropDownVerticalOffset(60);
        spTypeOut.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                typeModelOut.setId(mList.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void initSpinnerIn() {
        mAdapterIn = new ArrayAdapter<>(mActivity, R.layout.spinner_layout, mList);
        mAdapterIn.setDropDownViewResource(R.layout.spiner_drop_down_style);
        spTypeIn.setAdapter(mAdapterIn);
        spTypeIn.setDropDownVerticalOffset(60);
        spTypeIn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                typeModelIn.setId(mList.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @OnClick({R.id.tv_cancel, R.id.tv_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel:
                dismiss();
                break;
            case R.id.tv_submit:
                if (StringUtil.isEmpty(typeModelOut.getId())||typeModelOut.getId().equals("请选择类型")){
                    Toast.makeText(mActivity, "请选择转出类型！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (StringUtil.isEmpty(typeModelIn.getId())||typeModelIn.getId().equals("请选择类型")){
                    Toast.makeText(mActivity, "请选择转入类型！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (typeModelOut.getId().equals(typeModelIn.getId())){
                    Toast.makeText(mActivity, "转出类型不能与传入类型相同", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (StringUtil.isEmpty(etTrxMoney.getText().toString().trim())) {
                    Toast.makeText(mActivity, "金额不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (".".equals(etTrxMoney.getText().toString().trim())) {
                    Toast.makeText(mActivity, "请输入正确的金额", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (userDao.typeFindById(typeModelOut.getId()).getBalance().subtract(new BigDecimal(etTrxMoney.getText().toString().trim())).compareTo(new BigDecimal("0"))<0) {
                    Toast.makeText(mActivity, "转出金额不足！", Toast.LENGTH_SHORT).show();
                    return;
                }
                typeModelOut.setBalance(userDao.typeFindById(typeModelOut.getId()).getBalance().subtract(new BigDecimal(etTrxMoney.getText().toString().trim()).abs().setScale(2, BigDecimal.ROUND_HALF_UP)));
                typeModelIn.setBalance(userDao.typeFindById(typeModelIn.getId()).getBalance().add(new BigDecimal(etTrxMoney.getText().toString().trim()).abs().setScale(2, BigDecimal.ROUND_HALF_UP)));
                userDao.update(typeModelOut, StorageCustomerInfo02Util.getBooleanInfo("automatic",mActivity,true)?"名下互转":"","\n转入方："+typeModelIn.getId());
                userDao.update(typeModelIn,StorageCustomerInfo02Util.getBooleanInfo("automatic",mActivity,true)?"名下互转":"","\n转出方："+typeModelOut.getId());
                if (typeModelOut.getId().equals("微信")||typeModelOut.getId().equals("支付宝")){
                    if (typeModelIn.getId().contains("银行")) {
                        typeModelOut.setBalance(userDao.typeFindById(typeModelOut.getId()).getBalance().subtract(new BigDecimal(etTrxMoney.getText().toString().trim()).abs().multiply(new BigDecimal("0.001")).setScale(2, BigDecimal.ROUND_UP)));
                        userDao.update(typeModelOut, "提现手续费", "\n转入方：" + typeModelIn.getId());
                    }
                }
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
