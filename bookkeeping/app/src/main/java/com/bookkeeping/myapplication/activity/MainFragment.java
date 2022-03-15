package com.bookkeeping.myapplication.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bookkeeping.myapplication.MyApplication;
import com.bookkeeping.myapplication.R;
import com.bookkeeping.myapplication.adapter.MaterialAdapter;
import com.bookkeeping.myapplication.base.BaseFragment;
import com.bookkeeping.myapplication.model.MaterialModel;
import com.bookkeeping.myapplication.util.StorageCustomerInfo02Util;
import com.bookkeeping.myapplication.util.StringUtil;
import com.bookkeeping.myapplication.util.UserDao;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.pilot.common.utils.PinyinUtils;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.bookkeeping.myapplication.util.UserDao.TAG;

/**
 * @author : qiuyiyang
 * @date : 2021/1/22  17:49
 * @desc :
 */
public class MainFragment extends BaseFragment implements TextWatcher {

    Unbinder unbinder;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_jl)
    EditText etJl;
    @BindView(R.id.et_bj)
    EditText etBj;
    @BindView(R.id.et_zc)
    EditText etZc;
    @BindView(R.id.et_xs)
    EditText etXs;
    @BindView(R.id.tv_change)
    TextView tvChange;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.tv_only_synthesis)
    TextView tvOnlySynthesis;
    @BindView(R.id.tv_add)
    TextView tvAdd;
    @BindView(R.id.et_search)
    EditText etSearch;

    private MaterialAdapter materialAdapter;
    private UserDao userDao;
    private BigDecimal xsfee, zcfee, bjfee, jlfee;
    private List<MaterialModel> mList = new ArrayList<>();
    private List<MaterialModel> list = new ArrayList<>();
    private Map<String, MaterialModel> map;
    private boolean synthesis = true;
    private String sql = " where type like '%合成%' ";
    Comparator<MaterialModel> comparator = new Comparator<MaterialModel>() {
        @Override
        public int compare(MaterialModel o1, MaterialModel o2) {
            if (o1.getCostPerformance().intValue()!=0&&o2.getCostPerformance().intValue()==0){
                return -1;
            } else if (o1.getCostPerformance().intValue()>o2.getCostPerformance().intValue()) {
                return -1;
            } else if (o1.getCostPerformance().intValue()<o2.getCostPerformance().intValue()) {
                return 1;
            } else {
                if (o1.getVitality()>o2.getVitality()) {
                    return -1;
                } else if (o1.getVitality()<o2.getVitality()) {
                    return 1;
                } else {
                    if (o1.getPrice()>o2.getPrice()) {
                        return -1;
                    } else if (o1.getPrice()<o2.getPrice()) {
                        return 1;
                    } else {
                        return 0;
                    }
                }
            }
        }
    };

    @Override
    public int initLayout() {
        return R.layout.frag_main;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void initData(View v) {
        tvTitle.setText("计算成本");

        ivBack.setVisibility(View.GONE);
        userDao = MyApplication.getUserDao();
        xsfee = new BigDecimal(StorageCustomerInfo02Util.getIntInfo(context, "xsfee", 0));
        zcfee = new BigDecimal(StorageCustomerInfo02Util.getIntInfo(context, "zcfee", 0));
        bjfee = new BigDecimal(StorageCustomerInfo02Util.getIntInfo(context, "bjfee", 0));
        jlfee = new BigDecimal(StorageCustomerInfo02Util.getIntInfo(context, "jlfee", 0));
        etXs.setText(xsfee.toString());
        etZc.setText(zcfee.toString());
        etBj.setText(bjfee.toString());
        etJl.setText(jlfee.toString());
        etXs.addTextChangedListener(this);
        etZc.addTextChangedListener(this);
        etBj.addTextChangedListener(this);
        etJl.addTextChangedListener(this);
        materialAdapter = new MaterialAdapter(list);
        rvList.setLayoutManager(new LinearLayoutManager(context));
        materialAdapter.bindToRecyclerView(rvList);
        materialAdapter.setEmptyView(R.layout.layout_empty, rvList);
        materialAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(context, NewMaterialActivity.class).putExtra("detail", list.get(position).getName()).putExtra("type", "update"));
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
               search(s.toString().trim());
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshData();
    }

    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    private void search(String s){
        list.clear();
        if (!StringUtil.isEmpty(s)) {
            for (int i = 0; i < mList.size(); i++) {
                boolean chinese = true;
                char[] cTemp = s.toCharArray();
                String chars = mList.get(i).getName();
                String[] first = PinyinUtils.converterToFirstSpell(chars).split(",");
                boolean contain = true;
                for (int k = 0; k < s.length(); k++) {
                    try {
                        chinese = String.valueOf(cTemp[k]).getBytes(StandardCharsets.UTF_8).length > 1;
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
                    if (!contain) {
                        continue;
                    }
                }
                if (contain && !list.contains(mList.get(i))) {
                    list.add(mList.get(i));
                }
            }
        }
        else {
            list.addAll(mList);
        }
        Collections.sort(list, comparator);
        materialAdapter.setNewData(list);
    }
    private void loadData() {
        mList= userDao.findMaterialListSql(sql);
        for (MaterialModel materialModel : mList) {
            map = new HashMap<String, MaterialModel>();
            BigDecimal price = new BigDecimal("0"), vitality = new BigDecimal(materialModel.getVitality());
            if (materialModel.getType().contains("合成")) {
                List<MaterialModel> list = userDao.findSynthesisById(materialModel.getId());
                for (MaterialModel model : list) {
                    map.put(model.getChildId(), model);
                }
                for (MaterialModel model : list) {
                    if (model.getIsSynthesis() == 1) {
                        vitality = vitality.add(new BigDecimal(model.getVitality()).multiply(calculation(model)));
                    } else {
                        price = price.add(getMoney(model.getType(), model.getPrice()).multiply(calculation(model)));
                    }
                }
                materialModel.setPrice(price.intValue());
                materialModel.setVitality(vitality.intValue());
                if (materialModel.getSellPrice() != 0 && materialModel.getPrice() != 0) {
                    materialModel.setProfit(new BigDecimal(materialModel.getSellPrice()).subtract(new BigDecimal(materialModel.getPrice())));
                    materialModel.setCostPerformance(materialModel.getProfit().divide(new BigDecimal(materialModel.getVitality()), 2, BigDecimal.ROUND_HALF_UP));
                }
            } else {
                materialModel.setPrice(getMoney(materialModel.getType(), materialModel.getPrice()).intValue());
            }
        }
        search(etSearch.getText().toString());
    }

    private BigDecimal calculation(MaterialModel materialModel) {
        BigDecimal number = new BigDecimal(materialModel.getNumber());
        if (map.containsKey(materialModel.getParentID())) {
            return number.multiply(calculation( map.get(materialModel.getParentID())));
        }
        return number;
    }

    private BigDecimal getMoney(String type, int money) {
        BigDecimal fee;
        switch (type) {
            case "新手千货":
                fee = xsfee.max(zcfee);
                break;
            case "主城千货":
                fee = zcfee;
                break;
            case "边境千货":
                fee = bjfee;
                break;
            case "江陵千货":
                fee = jlfee;
                break;
            default:
                fee = new BigDecimal("0");
                break;
        }
        return new BigDecimal(money).multiply(new BigDecimal("100").subtract(fee)).divide(new BigDecimal("100"), 0, BigDecimal.ROUND_UP);
    }

    private void refreshData() {
        mList.clear();
        if (jlfee.compareTo(new BigDecimal("10")) > 0 || bjfee.compareTo(new BigDecimal("10")) > 0
                || zcfee.compareTo(new BigDecimal("10")) > 0 || xsfee.compareTo(new BigDecimal("10")) > 0) {
            Toast.makeText(context, "折扣不得大于10%", Toast.LENGTH_SHORT).show();
        } else {
            StorageCustomerInfo02Util.putInfo(context, "jlfee", jlfee.intValue());
            StorageCustomerInfo02Util.putInfo(context, "bjfee", bjfee.intValue());
            StorageCustomerInfo02Util.putInfo(context, "zcfee", zcfee.intValue());
            StorageCustomerInfo02Util.putInfo(context, "xsfee", xsfee.intValue());
            loadData();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        String temp = s.toString();
        if (StringUtil.isEmpty(temp)) {
            s.clear();
            s.append("0");
            return;
        }
        if (temp.startsWith("0") && temp.length() > 1) {
            s.delete(0, 1);
        }
        if (new BigDecimal(temp).compareTo(new BigDecimal("10")) > 0) {
            s.clear();
            s.append("10");
        }
    }

    @OnClick({R.id.tv_change, R.id.tv_add, R.id.tv_only_synthesis})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_only_synthesis:
                synthesis = !synthesis;
                tvOnlySynthesis.setBackgroundResource(synthesis ? R.drawable.submit : R.drawable.shape_soild_red_corner_5);
                sql = " where " + (synthesis ? "" : "not") + " type like '%合成%' ";
                tvOnlySynthesis.setText(synthesis?"合成品":"材料");
                refreshData();
                break;
            case R.id.tv_change:
                if (!StringUtil.isEmpty(etJl.getText().toString())) {
                    jlfee = new BigDecimal(etJl.getText().toString());
                }
                if (!StringUtil.isEmpty(etBj.getText().toString())) {
                    bjfee = new BigDecimal(etBj.getText().toString());
                }
                if (!StringUtil.isEmpty(etZc.getText().toString())) {
                    zcfee = new BigDecimal(etZc.getText().toString());
                }
                if (!StringUtil.isEmpty(etXs.getText().toString())) {
                    xsfee = new BigDecimal(etXs.getText().toString());
                }
                refreshData();
                break;
            case R.id.tv_add:
                startActivity(new Intent(context, NewMaterialActivity.class).putExtra("type", "new"));
                break;
        }
    }
}
