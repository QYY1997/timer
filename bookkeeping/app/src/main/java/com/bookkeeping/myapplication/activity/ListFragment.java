package com.bookkeeping.myapplication.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.bookkeeping.myapplication.MyApplication;
import com.bookkeeping.myapplication.R;
import com.bookkeeping.myapplication.adapter.ListAdapter;
import com.bookkeeping.myapplication.base.BaseFragment;
import com.bookkeeping.myapplication.model.BookModel;
import com.bookkeeping.myapplication.util.CommonUtils;
import com.bookkeeping.myapplication.util.DateUtil;
import com.bookkeeping.myapplication.util.SingleOptionsPicker;
import com.bookkeeping.myapplication.util.StringUtil;
import com.bookkeeping.myapplication.util.UserDao;
import com.bookkeeping.myapplication.view.HintDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author: qiuyiyang
 * @date: 2021/1/26
 */
public class ListFragment extends BaseFragment {
    @BindView(R.id.tv_year)
    TextView tvYear;
    @BindView(R.id.tv_month)
    TextView tvMonth;
    @BindView(R.id.tv_day)
    TextView tvDay;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.tv_new)
    TextView tvNew;
    @BindView(R.id.tv_first)
    TextView tvFirst;
    @BindView(R.id.tv_before)
    TextView tvBefore;
    @BindViews({R.id.tv_1, R.id.tv_2, R.id.tv_3, R.id.tv_4, R.id.tv_5})
    List<TextView> textViews;
    @BindView(R.id.tv_after)
    TextView tvAfter;
    @BindView(R.id.tv_last)
    TextView tvLast;
    @BindView(R.id.tv_go)
    TextView tvGo;
    @BindView(R.id.et_page)
    EditText etPage;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_trx_money)
    TextView tvTrxMoney;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.tv_detail)
    TextView tvDetail;
    @BindView(R.id.tv_pay)
    TextView tvPay;
    @BindView(R.id.tv_get)
    TextView tvGet;
    @BindView(R.id.tv_all)
    TextView tvAll;
    @BindView(R.id.tv_sum_all_pay)
    TextView tvSumAllPay;
    @BindView(R.id.tv_sum_month_pay)
    TextView tvSumMonthPay;
    @BindView(R.id.tv_sum_pay)
    TextView tvSumPay;
    @BindView(R.id.tv_sum_all_get)
    TextView tvSumAllGet;
    @BindView(R.id.tv_sum_month_get)
    TextView tvSumMonthGet;
    @BindView(R.id.tv_sum_get)
    TextView tvSumGet;
    @BindView(R.id.tv_manual)
    TextView tvManual;
    @BindView(R.id.tv_automatic)
    TextView tvAutomatic;
    Unbinder unbinder;
    @BindView(R.id.ll_list)
    LinearLayout llList;
    @BindView(R.id.iv_menu)
    ImageView ivMenu;
    @BindView(R.id.ll_time)
    LinearLayout llTime;
    @BindView(R.id.ll_screen)
    LinearLayout llScreen;
    @BindView(R.id.ll_field)
    LinearLayout llField;
    @BindView(R.id.ll_sum)
    LinearLayout llSum;
    @BindView(R.id.ll_page)
    LinearLayout llPage;
    private ListAdapter mListAdapter;
    private List<BookModel> mList = new ArrayList<>();
    private List<String> TrxList = new ArrayList<>();
    private List<String> TypeList = new ArrayList<>();
    private int pageIndex = 1;
    private int count;
    private int year, month, dayNum;
    private int year2, month2, dayNum2;
    private long date1, date2;
    private String sqlm = " ORDER BY useTime DESC ", sqlt = "";
    private String pay = "";
    private String manual = "";
    private UserDao userDao;

    @Override
    public int initLayout() {
        return R.layout.act_main;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void initData(View v) {
        tvTitle.setText("账单");
        userDao = MyApplication.getUserDao();
        mListAdapter = new ListAdapter(mList);
        rvList.setLayoutManager(new LinearLayoutManager(context));
        mListAdapter.bindToRecyclerView(rvList);
        mListAdapter.setEmptyView(R.layout.layout_empty, rvList);
        TrxList.add("取消排序");
        TrxList.add("升序");
        TrxList.add("降序");
        TypeList.add("取消筛选");
        TypeList.addAll(userDao.findAllType());
        initListener();
    }


    private void initListener() {
        mListAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, final int position) {
                if (view.getId() == R.id.tv_detail) {
                    HintDialog hintDialogs = HintDialog.getInstance("删除后是否将金额加回余额?");
                    hintDialogs.setOnClickListener(new HintDialog.OnClickListener() {
                        @Override
                        public void onClick() {
                            userDao.deleteBook("data", mList.get(position));
                            refreshData();
                        }
                    });
                    hintDialogs.setOnCancelClickListener(new HintDialog.OnClickListener() {
                        @Override
                        public void onClick() {
                            userDao.delete("data", mList.get(position).getId());
                            refreshData();
                        }
                    });
                    hintDialogs.show(getChildFragmentManager(), "");
                }
            }
        });
        mListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    startActivity(new Intent(context, NewActivity.class).putExtra("type", "edit").putExtra("bookModel", mList.get(position)));
            }
        });
    }

    /**
     * 重新刷新数据到首页
     */
    public void refreshNew() {
        Calendar calendar = Calendar.getInstance();
        tvSumAllGet.setText(userDao.getSumAll(" and trxMoney > 0 "));
        tvSumAllPay.setText(userDao.getSumAll(" and trxMoney < 0 "));
        tvSumMonthGet.setText(userDao.getSumBySql(" and useTime > " + DateUtil.parseYMDToLong(calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-01")
                + " and useTime < " + DateUtil.parseYMDToLong(calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 2) + "-01") + " and trxMoney > 0 "));
        tvSumMonthPay.setText(userDao.getSumBySql(" and useTime > " + DateUtil.parseYMDToLong(calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-01")
                + " and useTime < " + DateUtil.parseYMDToLong(calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 2) + "-01") + " and trxMoney < 0 "));
        pageIndex = 1;
        etPage.setText("");
        refreshData();
    }

    /**
     * 刷新页面
     */
    private void refreshData() {
        if (year == 0) {
            month = month2 = 0;
            dayNum = dayNum2 = 0;
        } else if (month == 0) {
            year2 = year + 1;
            month2 = 0;
            dayNum2 = 0;
        } else if (dayNum == 0) {
            year2 = year;
            month2 = month + 1;
           dayNum2 = 0;
        } else {
            year2 = year;
            month2 = month;
            dayNum2 = dayNum + 1;
        }
        loadData();
    }


    private void loadData() {
        mList.clear();
        date1 = DateUtil.parseYMDToLong(year + "-" + (month==0?"01":month) + "-" + (dayNum==0?"01":dayNum));
        date2 = DateUtil.parseYMDToLong(year2 + "-" + (month2==0?"01":month2) + "-" + (dayNum2==0?"01":dayNum2));
        if (year == 0) {
            mList = userDao.findAll(sqlt + pay + manual + sqlm + " limit " + (pageIndex * 10 - 10) + ",10");
            tvSumGet.setText(userDao.getSumAll(sqlt + pay + manual + " and trxMoney > 0 "));
            tvSumPay.setText(userDao.getSumAll(sqlt + pay + manual + " and trxMoney < 0 "));
            count = (new BigDecimal(userDao.getCountBySql(sqlt + pay + manual + sqlm)).divide(new BigDecimal(10), BigDecimal.ROUND_UP)).intValue();
        } else {
            mList = userDao.findBySql(" and useTime > " + date1 + " and useTime < " + date2 + sqlt + pay + manual + sqlm + "  limit " + (pageIndex * 10 - 10) + ",10");
            tvSumGet.setText(userDao.getSumBySql(" and useTime > " + date1 + " and useTime < " + date2 + sqlt + manual + pay + " and trxMoney > 0 ") + "");
            tvSumPay.setText(userDao.getSumBySql(" and useTime > " + date1 + " and useTime < " + date2 + sqlt + manual + pay + " and trxMoney < 0 ") + "");
            count = (new BigDecimal(userDao.getCountBySql(" and useTime > " + date1 + " and useTime < " + date2 + sqlt + pay + manual + sqlm)).divide(new BigDecimal(10), BigDecimal.ROUND_UP)).intValue();
        }
        mListAdapter.setNewData(mList);
        rvList.scrollToPosition(0);
        choose();
    }

    private void choose() {
        int postion;
        if (count < 1) {
            count = 1;
        }
        if (count < 6) {
            postion = pageIndex - 1;
        } else {
            if (pageIndex == 1) {
                postion = 0;
            } else if (pageIndex == 2) {
                postion = 1;
            } else if (pageIndex == count - 1) {
                postion = 3;
            } else if (pageIndex == count) {
                postion = 4;
            } else {
                postion = 2;
            }
        }
        for (int i = 0; i < 5; i++) {
            if (i == postion) {
                textViews.get(i).setVisibility(View.VISIBLE);
                textViews.get(i).setTextColor(Color.WHITE);
                textViews.get(i).setText(pageIndex + "");
                textViews.get(i).setBackgroundResource(R.drawable.shape_soli_bule_corner_5);
                textViews.get(i).setTag("choose");
            } else if ((i - postion + pageIndex) > count) {
                textViews.get(i).setVisibility(View.GONE);
            } else {
                textViews.get(i).setVisibility(View.VISIBLE);
                textViews.get(i).setText((i - postion + pageIndex) + "");
                textViews.get(i).setBackgroundResource(R.drawable.text);
                textViews.get(i).setTextColor(Color.BLACK);
                textViews.get(i).setTag("");
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.iv_menu, R.id.tv_year, R.id.tv_month, R.id.tv_day, R.id.tv_new, R.id.tv_first, R.id.tv_all,R.id.tv_balance
            , R.id.tv_before, R.id.tv_1, R.id.tv_2, R.id.tv_3, R.id.tv_4, R.id.tv_5, R.id.tv_after, R.id.tv_last, R.id.tv_go
            , R.id.tv_trx_money, R.id.tv_type,  R.id.tv_pay, R.id.tv_get, R.id.tv_manual, R.id.tv_automatic})
    public void onViewClicked(View view) {
        if (CommonUtils.isFastDoubleClick2(view.getId())) {
            return;
        }
        switch (view.getId()) {
            case R.id.iv_menu:
                HomeNewActivity activity = (HomeNewActivity) getActivity();
                activity.open();
                break;
            case R.id.tv_manual:
                if (!" and event <> '名下互转' and event <> '手动修改'".equals(manual)) {
                    tvManual.setBackgroundResource(R.drawable.shape_soild_red_corner_5);
                    tvAutomatic.setBackgroundResource(R.drawable.submit);
                    manual = " and event <> '名下互转' and event <> '手动修改'";
                } else {
                    tvManual.setBackgroundResource(R.drawable.submit);
                    tvAutomatic.setBackgroundResource(R.drawable.submit);
                    manual = "";
                }
                refreshData();
                break;
            case R.id.tv_automatic:
                if (!" and (event = '名下互转' or event = '手动修改')".equals(manual)) {
                    tvAutomatic.setBackgroundResource(R.drawable.shape_soild_red_corner_5);
                    tvManual.setBackgroundResource(R.drawable.submit);
                    manual = " and (event = '名下互转' or event = '手动修改')";
                } else {
                    tvAutomatic.setBackgroundResource(R.drawable.submit);
                    tvManual.setBackgroundResource(R.drawable.submit);
                    manual = "";
                }
                refreshData();
                break;
            case R.id.tv_pay:
                if (!" and trxMoney <= 0".equals(pay)) {
                    tvPay.setBackgroundResource(R.drawable.shape_soild_red_corner_5);
                    tvGet.setBackgroundResource(R.drawable.submit);
                    pay = " and trxMoney <= 0";
                } else {
                    tvPay.setBackgroundResource(R.drawable.submit);
                    pay = "";
                }
                refreshData();
                break;
            case R.id.tv_get:
                if (!" and trxMoney > 0".equals(pay)) {
                    tvGet.setBackgroundResource(R.drawable.shape_soild_red_corner_5);
                    tvPay.setBackgroundResource(R.drawable.submit);
                    pay = " and trxMoney > 0";
                } else {
                    tvGet.setBackgroundResource(R.drawable.submit);
                    pay = "";
                }
                refreshData();
                break;
            case R.id.tv_new:
                startActivity(new Intent(context, NewActivity.class).putExtra("type", "new"));
                break;
            case R.id.tv_balance:
                startActivity(new Intent(context, TypeListActivity.class));
                break;
            case R.id.tv_trx_money:
                new SingleOptionsPicker(context, 0, TrxList, new SingleOptionsPicker.OnPickerOptionsClickListener() {
                    @Override
                    public void onOptionsSelect(int options1, int options2, int options3, View view) {
                        switch (options1) {
                            case 0:
                                sqlm = " ORDER BY useTime DESC ";
                                tvTrxMoney.setText("金额");
                                break;
                            case 1:
                                sqlm = " ORDER BY trxMoney ";
                                tvTrxMoney.setText("升序");
                                break;
                            case 2:
                                sqlm = " ORDER BY trxMoney DESC ";
                                tvTrxMoney.setText("降序");
                                break;
                        }
                        refreshData();
                    }
                }).show();
                break;
            case R.id.tv_type:
                new SingleOptionsPicker(context, 0, TypeList, new SingleOptionsPicker.OnPickerOptionsClickListener() {
                    @Override
                    public void onOptionsSelect(int options1, int options2, int options3, View view) {
                        if (options1 == 0) {
                            tvType.setText("类型");
                            sqlt = "";
                        } else {
                            tvType.setText(TypeList.get(options1));
                            sqlt = " and type ='" + TypeList.get(options1) + "' ";
                        }
                        refreshData();
                    }
                }).show();
                break;
            case R.id.tv_all:
                tvYear.setText("所有年");
                tvMonth.setText("所有月");
                tvDay.setText("所有日");
                tvTrxMoney.setText("金额");
                tvType.setText("类型");
                tvPay.setBackgroundResource(R.drawable.submit);
                tvGet.setBackgroundResource(R.drawable.submit);
                tvAutomatic.setBackgroundResource(R.drawable.submit);
                tvManual.setBackgroundResource(R.drawable.submit);
                year = 0;
                month = 0;
                dayNum = 0;
                pageIndex = 1;
                sqlm = " ORDER BY useTime DESC ";
                pay = "";
                manual = "";
                sqlt = "";
                refreshData();
                break;
            case R.id.tv_1:
            case R.id.tv_2:
            case R.id.tv_3:
            case R.id.tv_4:
            case R.id.tv_5:
                TextView textView = (TextView) view;
                if (Integer.parseInt(textView.getText().toString()) != pageIndex) {
                    pageIndex = Integer.parseInt(textView.getText().toString());
                }
                refreshData();
                break;
            case R.id.tv_first:
                if (pageIndex != 1) {
                    pageIndex = 1;
                    refreshData();
                }
                break;
            case R.id.tv_before:
                if (pageIndex != 1) {
                    pageIndex--;
                    refreshData();
                }
                break;
            case R.id.tv_after:
                if (pageIndex != count) {
                    pageIndex++;
                    refreshData();
                }
                break;
            case R.id.tv_last:
                if (pageIndex != count) {
                    pageIndex = count;
                    refreshData();
                }
                break;
            case R.id.tv_go:
                if (StringUtil.isEmpty(etPage.getText().toString().trim())) {
                    Toast.makeText(context, "页码为空", Toast.LENGTH_SHORT).show();
                } else {
                    int page = Integer.parseInt(etPage.getText().toString().trim());
                    if (page > 0 && page <= count) {
                        if (page == pageIndex) {
                            Toast.makeText(context, "已经是第" + page + "页", Toast.LENGTH_SHORT).show();
                        } else {
                            pageIndex = page;
                            refreshData();
                        }
                    } else {
                        Toast.makeText(context, "无效页码", Toast.LENGTH_SHORT).show();
                    }
                }
                etPage.setText("");
                etPage.clearFocus();
                break;
            case R.id.tv_year:
                showTimePickerDialog();
                break;
            case R.id.tv_month:
                List<String> list1 = new ArrayList<>();
                for (int i = 0; i <= 12; i++) {
                    if (i == 0) {
                        list1.add("所有月");
                    } else if (year != 0) {
                        list1.add((i < 10 ? "0" + i : i + "") + "月");
                    }
                }
                new SingleOptionsPicker(context, tvMonth.getText().toString(), list1, new SingleOptionsPicker.OnPickerOptionsClickListener() {
                    @Override
                    public void onOptionsSelect(int options1, int options2, int options3, View view) {
                        if (options1 == 0) {
                            tvMonth.setText("所有月");
                            tvDay.setText("所有日");
                            month = 0;
                            dayNum = 0;
                        } else {
                            tvMonth.setText(options1 + "月");
                            month = options1;
                        }
                        refreshData();
                    }
                }).show();
                break;
            case R.id.tv_day:
                List<String> list2 = new ArrayList<>();
                for (int i = 0; i <= 31; i++) {
                    if (i == 0) {
                        list2.add("所有日");
                    } else if (month != 0) {
                        if (year % 4 == 0 && month == 2 && i == 29) {
                            list2.add(i + "日");
                            continue;
                        }
                        if (month == 2 && i > 28) {
                            continue;
                        }
                        if (i == 31) {
                            if (month == 2 || month == 4 || month == 6 || month == 9 || month == 11) {
                                continue;
                            }
                        }
                        list2.add((i < 10 ? "0" + i : i + "") + "日");
                    }
                }
                new SingleOptionsPicker(context, tvMonth.getText().toString(), list2, new SingleOptionsPicker.OnPickerOptionsClickListener() {
                    @Override
                    public void onOptionsSelect(int options1, int options2, int options3, View view) {
                        if (options1 == 0) {
                            tvDay.setText("所有日");
                            dayNum = 0;
                        } else {
                            tvDay.setText(options1 + "日");
                            dayNum = options1;
                        }
                        refreshData();
                    }
                }).show();
                break;
            default:
                break;
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        refreshNew();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            refreshNew();
        }
    }

    /**
     * 显示日期选择 7.0 7.1显示年月日
     */
    private void showTimePickerDialog() {
        TimePickerView pvTime = new TimePickerBuilder(context, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                year = calendar.get(Calendar.YEAR);
                tvYear.setText(String.format("%d年", year));
                refreshData();
            }
        })
                .setType(new boolean[]{true, false, false, false, false, false})
                .isDialog(false)
                .setCancelColor(Color.parseColor("#808080"))
                .setSubmitColor(Color.parseColor("#ff4949"))
                .build();
        pvTime.show();
    }
}
