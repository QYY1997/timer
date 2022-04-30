package com.timer.com;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.timer.com.bean.HintModel;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author : qiuyiyang
 * @date : 2022/2/3  22:09
 * @desc :
 */
public class LocalListActivity extends BaseActivity {

    @BindView(R.id.tv_left)
    TextView tvLeft;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right2)
    TextView tvRight2;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.rv_list)
    RecyclerView rvList;

    private LocalListAdapter localListAdapter;
    private List<String> mList=new ArrayList<>();


    @Override
    public int initLayout() {
        return R.layout.act_local_list;
    }

    @Override
    public void initData() {
        tvTitle.setText("本地记录");
        localListAdapter = new LocalListAdapter(mList);
        rvList.setLayoutManager(new LinearLayoutManager(context));
        localListAdapter.bindToRecyclerView(rvList);
        localListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                setResult(RESULT_OK,new Intent().putExtra("path",context.getExternalFilesDir(Environment.DIRECTORY_DCIM)+"/"+mList.get(position)));
                finish();
            }
        });
        localListAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                File file =new File(context.getExternalFilesDir(Environment.DIRECTORY_DCIM)+"/"+mList.get(position));
                if (file != null && file.exists()&&file.isFile()) {
                    HintModel hintModel=new HintModel();
                    hintModel.setContent("确定要删除这条记录吗？");
                    HintDialog hintDialog =HintDialog.getInstance(hintModel);
                    hintDialog.setOnClickListener(new HintDialog.OnClickListener() {
                        @Override
                        public void onClick() {
                            file.delete();
                            refreshList();
                        }
                    });
                    hintDialog.show(getSupportFragmentManager(),"");
                }
            }
        });
        refreshList();
    }

    private void refreshList() {
        mList.clear();
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File dir = context.getExternalFilesDir(Environment.DIRECTORY_DCIM);
            if (dir == null || !dir.exists() || !dir.isDirectory() || dir.listFiles() == null) {
                return;
            }
            for (int i=0;i<dir.listFiles().length;i++) {
                if (dir.listFiles()[i].isFile()&&getFileSize(dir.listFiles()[i])>0) {
                    mList.add(dir.listFiles()[i].getName());
                }
                if (i==dir.listFiles().length-1){
                    localListAdapter.setNewData(mList);
                }
            }
            localListAdapter.setNewData(mList);
        }
    }
    private long getFileSize(File file) {
        long size = 0;
        try {
            if (file.exists()) {
                FileInputStream fis = null;
                fis = new FileInputStream(file);
                size = fis.available();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return size;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.tv_left)
    public void onViewClicked() {
        finish();
    }
}
