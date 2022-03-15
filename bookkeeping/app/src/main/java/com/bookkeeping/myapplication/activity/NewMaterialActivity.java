package com.bookkeeping.myapplication.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bookkeeping.myapplication.MyApplication;
import com.bookkeeping.myapplication.R;
import com.bookkeeping.myapplication.adapter.NewMaterialAdapter;
import com.bookkeeping.myapplication.base.BaseActivity;
import com.bookkeeping.myapplication.model.MaterialModel;
import com.bookkeeping.myapplication.model.SynthesisModel;
import com.bookkeeping.myapplication.util.CommonUtils;
import com.bookkeeping.myapplication.util.StringUtil;
import com.bookkeeping.myapplication.util.UserDao;
import com.bookkeeping.myapplication.view.HintDialog;
import com.bookkeeping.myapplication.view.SynthesisDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author : qiuyiyang
 * @date : 2021/1/5  15:14
 * @desc :
 */
public class NewMaterialActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.sp_type)
    Spinner spType;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_price)
    EditText etPrice;
    @BindView(R.id.et_sell_price)
    EditText etSellPrice;
    @BindView(R.id.et_vitality)
    EditText etVitality;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.ll_synthesis)
    LinearLayout llSynthesis;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.tv_delete)
    TextView tvDelete;
    @BindView(R.id.tv_synthesis)
    TextView tvSynthesis;
    @BindView(R.id.tv_add)
    TextView tvAdd;
    private List<String> typeList = new ArrayList<>();
    private ArrayAdapter<String> mAdapter;
    private MaterialModel materialModel;
    private UserDao userDao;
    private String type;
    private List<SynthesisModel> mList = new ArrayList<>();
    private NewMaterialAdapter newMaterialAdapter;

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
        return R.layout.act_new_material;
    }

    @Override
    public void initData() {
        userDao = MyApplication.getUserDao();
        etName.setFilters(CommonUtils.getInputFilters(20));
        type = getIntent().getStringExtra("type");
        newMaterialAdapter = new NewMaterialAdapter(mList);
        rvList.setLayoutManager(new LinearLayoutManager(context));
        newMaterialAdapter.bindToRecyclerView(rvList);
        newMaterialAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                final HintDialog hintDialog = HintDialog.getInstance("确定删除此项吗？");
                hintDialog.setOnClickListener(new HintDialog.OnClickListener() {
                    @Override
                    public void onClick() {
                        userDao.delete("synthesis", mList.get(position).getId());
                        mList.remove(position);
                        refreshData();
                    }
                });
                hintDialog.show(getSupportFragmentManager(), "");
            }
        });
        if (type.equals("update")) {
            tvTitle.setText("修改信息");
            materialModel =userDao.findMaterialSql(" where name='" + getIntent().getStringExtra("detail") + "'");
            etName.setText(materialModel.getName());
            etVitality.setText(materialModel.getVitality() + "");
            etPrice.setText(materialModel.getPrice() + "");
            etSellPrice.setText(materialModel.getSellPrice() + "");
            mList = userDao.findSynthesisModelById(materialModel.getId());
            tvDelete.setVisibility(View.VISIBLE);
            refreshData();
        } else {
            tvTitle.setText("新建物品");
            materialModel = new MaterialModel();
            materialModel.setId(CommonUtils.getRandomString());
        }
        initSpinner();
    }

    private void refreshData() {
        newMaterialAdapter.setNewData(new ArrayList<>());
        newMaterialAdapter.setNewData(mList);
    }

    private void initSpinner() {
        typeList.add("请选择类型");
        typeList.add("新手千货");
        typeList.add("主城千货");
        typeList.add("边境千货");
        typeList.add("江陵千货");
        typeList.add("军团商店");
        typeList.add("时价材料");
        typeList.add("普通合成");
        typeList.add("庖丁合成");
        typeList.add("镶工合成");
        typeList.add("工匠合成");
        typeList.add("玉石匠合成");
        typeList.add("制符师合成");
        mAdapter = new ArrayAdapter<>(context, R.layout.spinner_layout, typeList);
        mAdapter.setDropDownViewResource(R.layout.spiner_drop_down_style);
        spType.setAdapter(mAdapter);
        spType.setDropDownVerticalOffset(60);
        spType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    materialModel.setType(typeList.get(position));
                } else {
                    materialModel.setType("");
                }
                llSynthesis.setVisibility(materialModel.getType().contains("合成") ? View.VISIBLE : View.GONE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        if (type.equals("update") && typeList.lastIndexOf(materialModel.getType()) > 0) {
            spType.setSelection(typeList.lastIndexOf(materialModel.getType()));
        }
    }

    private void submit() {
        materialModel.setName(etName.getText().toString().trim());
        if (!StringUtil.isEmpty(etPrice.getText().toString())) {
            materialModel.setPrice(Integer.parseInt(etPrice.getText().toString()));
        }
        if (materialModel.getType().contains("合成")) {
            materialModel.setVitality(Integer.parseInt(etVitality.getText().toString()));
            if (!StringUtil.isEmpty(etSellPrice.getText().toString())) {
                materialModel.setSellPrice(Integer.parseInt(etSellPrice.getText().toString()));
            }else {
                materialModel.setSellPrice(0);
            }
        }
        if (type.equals("new")) {
            userDao.add(materialModel);
        } else if (type.equals("update")) {
            userDao.update(materialModel);
        }
        finish();
    }

    @OnClick({R.id.iv_back, R.id.tv_submit, R.id.tv_add, R.id.tv_delete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_delete:
                final HintDialog deleteDialog = HintDialog.getInstance("确定删除吗？");
                deleteDialog.setOnClickListener(new HintDialog.OnClickListener() {
                    @Override
                    public void onClick() {
                       if (mList!=null&&mList.size()>0){
                           for (SynthesisModel synthesisModel:mList){
                               userDao.delete("synthesis",synthesisModel.getId());
                           }
                           userDao.delete("material",materialModel.getId());
                           finish();
                       }
                    }
                });
                deleteDialog.show(getSupportFragmentManager(), "");
                break;
            case R.id.tv_add:
                SynthesisDialog synthesisDialog = new SynthesisDialog();
                synthesisDialog.setOnClickListener(new SynthesisDialog.OnClickListener() {
                    @Override
                    public void onClick(SynthesisModel synthesisModel) {
                        synthesisModel.setParentID(materialModel.getId());
                        if (materialModel.getId().equals(synthesisModel.getChildID())) {
                            Toast.makeText(context, "不得添加自己为合成材料！", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        userDao.add(synthesisModel);
                        mList.add(synthesisModel);
                        refreshData();
                    }
                });
                synthesisDialog.show(getSupportFragmentManager(), "");
                break;
            case R.id.tv_submit:
                if (StringUtil.isEmpty(materialModel.getType())) {
                    Toast.makeText(context, "请选择类型！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (StringUtil.isEmpty(etName.getText().toString().trim())) {
                    Toast.makeText(context, "请输入物品名称！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (materialModel.getType().contains("合成")) {
                    if (StringUtil.isEmpty(etName.getText().toString().trim())) {
                        Toast.makeText(context, "请输入所需活力！", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (mList == null || mList.size() == 0) {
                        Toast.makeText(context, "请添加合成材料！", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (StringUtil.isEmpty(etPrice.getText().toString().trim())) {
                        Toast.makeText(context, "请输入金额！", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                if (!StringUtil.isEmpty(userDao.findMaterialSql(" where name='" + materialModel.getName() + "'").getId()) && type.equals("new")) {
                    Toast.makeText(context, "该物品已存在！", Toast.LENGTH_SHORT).show();
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
        }
    }
}
