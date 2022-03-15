package com.bookkeeping.myapplication.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.bookkeeping.myapplication.MyApplication;
import com.bookkeeping.myapplication.R;
import com.bookkeeping.myapplication.base.BaseActivity;
import com.bookkeeping.myapplication.model.BookModel;
import com.bookkeeping.myapplication.util.CommonUtils;
import com.bookkeeping.myapplication.util.DateUtil;
import com.bookkeeping.myapplication.util.StringUtil;
import com.bookkeeping.myapplication.util.UserDao;
import com.bookkeeping.myapplication.view.HintDialog;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author : qiuyiyang
 * @date : 2021/1/5  15:14
 * @desc :
 */
public class NewActivity extends BaseActivity {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.et_trx_money)
    EditText etTrxMoney;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.sp_type)
    Spinner spType;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.et_remark)
    EditText etRemark;
    @BindView(R.id.et_event)
    EditText etEvent;
    @BindView(R.id.tv_create_time)
    TextView tvCreateTime;
    @BindView(R.id.tv_update_time)
    TextView tvUpdateTime;
    @BindView(R.id.tv_delete)
    TextView tvDelete;
    @BindView(R.id.tv_type_pay)
    TextView tvTypePay;
    @BindView(R.id.tv_balance)
    TextView tvBalance;
    @BindView(R.id.ll_balance)
    LinearLayout llBalance;
    @BindView(R.id.ll_use_time)
    LinearLayout llUseTime;
    private List<String> mList = new ArrayList<>();
    private ArrayAdapter<String> mAdapter;
    private BookModel bookModel;
    private String type;
    private UserDao userDao;
    private TimePickerView pvTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public int initLayout() {
        return R.layout.act_new;
    }

    @Override
    public void initData() {
        userDao = MyApplication.getUserDao();
        initSpinner();
        etEvent.setFilters(CommonUtils.getInputFilters(20));
        etRemark.setFilters(CommonUtils.getInputFilters(500));
        type = getIntent().getStringExtra("type");
        bookModel = (BookModel) getIntent().getSerializableExtra("bookModel");
        if (type.equals("new")) {
            tvTitle.setText("添加记录");
            tvCreateTime.setVisibility(View.GONE);
            tvUpdateTime.setVisibility(View.GONE);
            tvDelete.setVisibility(View.GONE);
            llBalance.setVisibility(View.GONE);
            bookModel=new BookModel();
        } else if (type.equals("edit") && bookModel != null) {
            tvTitle.setText("修改记录");
            spType.setSelection(mList.lastIndexOf(bookModel.getType()));
            tvTime.setText(DateUtil.formateDateTOYMD(bookModel.getUseTime()));
            etEvent.setText(bookModel.getEvent());
            etRemark.setText(bookModel.getRemark());
            etTrxMoney.setText(bookModel.getTrxMoney().abs() + "");
            tvTypePay.setBackgroundResource(bookModel.getTrxMoney().compareTo(new BigDecimal("0")) <= 0 ? R.drawable.shape_soild_red_corner_5 : R.drawable.submit);
            tvTypePay.setText(bookModel.getTrxMoney().compareTo(new BigDecimal("0")) <= 0 ? "支出" : "收入");
            tvCreateTime.setVisibility(View.VISIBLE);
            tvSubmit.setText("修改");
            tvCreateTime.setText("创建于" + DateUtil.formatDateToHMS(bookModel.getCreatTime()));
            if (bookModel.getUpdateTime() != 0) {
                tvUpdateTime.setVisibility(View.VISIBLE);
                tvUpdateTime.setText("最后修改于" + DateUtil.formatDateToHMS(bookModel.getUpdateTime()));
            }
            tvDelete.setVisibility(View.VISIBLE);
            llBalance.setVisibility(View.VISIBLE);
            tvBalance.setText(bookModel.getBalance());
        }
    }

    private void initSpinner() {
        mList.add("请选择类型");
        mList.addAll(userDao.findAllType());
        mAdapter = new ArrayAdapter<>(context, R.layout.spinner_layout, mList);
        mAdapter.setDropDownViewResource(R.layout.spiner_drop_down_style);
        spType.setAdapter(mAdapter);
        spType.setDropDownVerticalOffset(60);
        spType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    bookModel.setType(mList.get(position));
                } else {
                    bookModel.setType("");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @OnClick({R.id.iv_back, R.id.tv_time, R.id.tv_submit, R.id.tv_delete, R.id.tv_type_pay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_type_pay:
                boolean types = tvTypePay.getText().toString().trim().equals("支出");
                tvTypePay.setBackgroundResource(types ? R.drawable.submit : R.drawable.shape_soild_red_corner_5);
                tvTypePay.setText(types ? "收入" : "支出");
                break;
            case R.id.tv_time:
                if (CommonUtils.isFastDoubleClick2(view.getId())) {
                    return;
                }
                InputMethodManager inputManger = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManger.hideSoftInputFromWindow(getWindow().peekDecorView().getWindowToken(), 0);
                showTimePickerDialog();
                break;
            case R.id.tv_submit:
                if (CommonUtils.isFastDoubleClick2(view.getId())) {
                    return;
                }
                if (StringUtil.isEmpty(etEvent.getText().toString().trim())) {
                    Toast.makeText(context, "事件不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (StringUtil.isEmpty(etTrxMoney.getText().toString().trim())) {
                    Toast.makeText(context, "金额不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (StringUtil.isEmpty(bookModel.getType())) {
                    Toast.makeText(context, "请选择支付类型", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (StringUtil.isEmpty(tvTime.getText().toString().trim()) && !type.equals("model")) {
                    Toast.makeText(context, "请选择时间", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (".".equals(etTrxMoney.getText().toString().trim())) {
                    Toast.makeText(context, "请输入正确的金额", Toast.LENGTH_SHORT).show();
                    return;
                }
                final HintDialog hintDialog = HintDialog.getInstance("确定提交吗？");
                hintDialog.setOnClickListener(new HintDialog.OnClickListener() {
                    @Override
                    public void onClick() {
                        submit();
                    }
                });
                hintDialog.show(getSupportFragmentManager(), "");
                break;
            case R.id.tv_delete:
                if (CommonUtils.isFastDoubleClick2(view.getId())) {
                    return;
                }
                if (type.equals("edit")) {
                    HintDialog hintDialogs = HintDialog.getInstance("删除后是否将金额加回余额？");
                    hintDialogs.setOnClickListener(new HintDialog.OnClickListener() {
                        @Override
                        public void onClick() {
                            userDao.deleteBook("data", bookModel);
                            finish();
                        }
                    });
                    hintDialogs.setOnCancelClickListener(new HintDialog.OnClickListener() {
                        @Override
                        public void onClick() {
                            userDao.delete("data", bookModel.getId());
                            finish();
                        }
                    });
                    hintDialogs.show(getSupportFragmentManager(), "");
                }
                break;
        }
    }

    private void submit() {
        bookModel.setEvent(etEvent.getText().toString().trim());
        bookModel.setRemark(etRemark.getText().toString().trim());
        bookModel.setTrxMoney(new BigDecimal(etTrxMoney.getText().toString().trim()).multiply(new BigDecimal(tvTypePay.getText().toString().trim().equals("支出") ? "-1" : "1")).setScale(2, BigDecimal.ROUND_HALF_UP));
        if (type.equals("new")) {
            bookModel.setId(CommonUtils.getRandomString());
            bookModel.setCreatTime(Calendar.getInstance().getTime().getTime());
            userDao.add(bookModel, "new");
        } else if (type.equals("edit")){
            bookModel.setUpdateTime(Calendar.getInstance().getTime().getTime());
            userDao.update(bookModel);
        }
        finish();
    }

    private void showTimePickerDialog() {
        pvTime = new TimePickerBuilder(context, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                tvTime.setText(DateUtil.formateDateTOYMD(calendar.getTime()));
                bookModel.setUseTime(calendar.getTime().getTime());
            }
        }).setCancelColor(Color.parseColor("#808080"))
                .setSubmitColor(Color.parseColor("#ff4949"))
                .setType(new boolean[]{true, true, true, false, false, false})
                .isDialog(false)
                .build();
        pvTime.show();
    }
}
