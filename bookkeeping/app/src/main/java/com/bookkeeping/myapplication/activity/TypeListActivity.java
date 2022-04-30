package com.bookkeeping.myapplication.activity;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bookkeeping.myapplication.MyApplication;
import com.bookkeeping.myapplication.R;
import com.bookkeeping.myapplication.adapter.AssetsAdapter;
import com.bookkeeping.myapplication.base.BaseActivity;
import com.bookkeeping.myapplication.base.BaseFragment;
import com.bookkeeping.myapplication.model.TypeModel;
import com.bookkeeping.myapplication.util.CommonUtils;
import com.bookkeeping.myapplication.util.StringUtil;
import com.bookkeeping.myapplication.view.HintDialog;
import com.bookkeeping.myapplication.view.TransferDialog;
import com.bookkeeping.myapplication.view.TypeDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.pilot.common.utils.PinyinUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author : qiuyiyang
 * @date : 2021/1/14  17:35
 * @desc :
 */
public class TypeListActivity extends BaseActivity {

    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.iv_et_clear)
    ImageView ivEtClear;
    @BindView(R.id.rl_search)
    RelativeLayout rlSearch;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.tv_sum_balance)
    TextView tvSumBalance;
    Unbinder unbinder;
    private AssetsAdapter assetsAdapter;
    private List<TypeModel> mList = new ArrayList<>();
    private List<TypeModel> list = new ArrayList<>();
    private String str = "";
    private boolean isSearch = false;


    @Override
    public int initLayout() {
        return R.layout.act_type_list;
    }

    @Override
    public void initData() {
        mList = MyApplication.getUserDao().findAllTypeInfo();
        tvSumBalance.setText("￥ " + MyApplication.getUserDao().getSumAllType());
        assetsAdapter = new AssetsAdapter(mList);
        rvList.setLayoutManager(new LinearLayoutManager(context));
        assetsAdapter.bindToRecyclerView(rvList);
        assetsAdapter.setEmptyView(R.layout.layout_empty, rvList);
        assetsAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(final BaseQuickAdapter adapter, View view, int position) {
                TypeDialog typeDialog = TypeDialog.getInstance(mList.get(position));
                typeDialog.setOnClickListener(new TypeDialog.OnClickListener() {
                    @Override
                    public void onClick(TypeModel typeModel) {
                        MyApplication.getUserDao().update(typeModel, "", "");
                        refresh();
                    }
                });
                typeDialog.show(getSupportFragmentManager(), "");
            }
        });
        assetsAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, final int position) {
                HintDialog hintDialog = HintDialog.getInstance("确定删除此类型吗？");
                hintDialog.setOnClickListener(new HintDialog.OnClickListener() {
                    @Override
                    public void onClick() {
                        MyApplication.getUserDao().delete("type", isSearch ? list.get(position).getId() : mList.get(position).getId());
                        refresh();
                    }
                });
                hintDialog.show(getSupportFragmentManager(), "");
                return true;
            }
        });
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                list.clear();
                str = s.toString().trim();
                assetsAdapter.setStr(str);
                if (!StringUtil.isEmpty(s.toString())) {
                    ivEtClear.setVisibility(View.VISIBLE);
                    for (int i = 0; i < mList.size(); i++) {
                        boolean chinese = true;
                        boolean quanping = false;
                        char[] cTemp = str.toCharArray();
                        String chars = mList.get(i).getId();
                        String[] first = PinyinUtils.converterToFirstSpell(chars).split(",");
                        String[] firsts = PinyinUtils.converterToSpell(chars).split(",");
                        for (String qp : firsts) {
                            if (qp.startsWith(str.toLowerCase())) {
                                quanping = true;
                            }
                        }
                        if (quanping) {
                            list.add(mList.get(i));
                            continue;
                        }
                        boolean contain = true;
                        for (int k = 0; k < str.length(); k++) {
                            try {
                                chinese = String.valueOf(cTemp[k]).getBytes("UTF-8").length > 1;
                            } catch (Exception e) {
                                e.printStackTrace();
                                chinese = false;
                            }
                            if (chinese) {
                                contain = contain && chars.contains(cTemp[k] + "");
                                chars = chars.replaceFirst(cTemp[k] + "", " ");
                            } else {
                                boolean f = false;
                                for (int j = 0; j < first.length; j++) {
                                    f = f || first[j].contains(cTemp[k] + "") || first[j].toUpperCase().contains(cTemp[k] + "") || chars.contains((cTemp[k] + "").toUpperCase());
                                    first[j] = first[j].replaceFirst((cTemp[k] + "").toLowerCase(), " ");
                                }
                                chars = chars.replaceFirst((cTemp[k] + "").toUpperCase(), " ");
                                contain = contain && f;
                            }
                        }
                        if (contain && !list.contains(mList.get(i))) {
                            list.add(mList.get(i));
                        }
                    }
                    assetsAdapter.setNewData(list);
                    isSearch = true;
                } else {
                    list.clear();
                    isSearch = false;
                    ivEtClear.setVisibility(View.GONE);
                    assetsAdapter.setNewData(mList);
                }
            }
        });
    }

    private void refresh() {
        mList = MyApplication.getUserDao().findAllTypeInfo();
        assetsAdapter.setNewData(mList);
        tvSumBalance.setText("￥ " + MyApplication.getUserDao().getSumAllType());
        etSearch.setText("");
    }

    @OnClick({R.id.iv_et_clear,R.id.tv_new_type,R.id.tv_transfer})
    public void onViewClicked(View view) {
        if (CommonUtils.isFastDoubleClick2(view.getId())) {
            return;
        }
        switch (view.getId()) {
            case R.id.iv_et_clear:
                etSearch.setText("");
                ivEtClear.setVisibility(View.GONE);
                break;
            case R.id.tv_new_type:
                TypeDialog typeDialog = TypeDialog.getInstance(null);
                typeDialog.setOnClickListener(new TypeDialog.OnClickListener() {
                    @Override
                    public void onClick(TypeModel typeModel) {
                        if (MyApplication.getUserDao().add(typeModel) == -2) {
                            final HintDialog hintDialog = HintDialog.getInstance("此类型已存在，请更改类型名称后再试");
                            hintDialog.show(getSupportFragmentManager(), "");
                        } else {
                            refresh();
                        }

                    }
                });
                typeDialog.show(getSupportFragmentManager(), "");
                break;
            case R.id.tv_transfer:
                // TODO:名下互转
                TransferDialog transferDialog = TransferDialog.getInstance();
                transferDialog.setOnClickListener(new TransferDialog.OnClickListener() {
                    @Override
                    public void onClick() {
                        refresh();
                    }
                });
                transferDialog.show(getSupportFragmentManager(), "");
                break;
        }
    }
}
