package com.bookkeeping.myapplication.adapter;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bookkeeping.myapplication.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @author: qiuyiyang
 * @description:
 * @date: 2019/4/4
 */
public class GameRecyclerViewAdapter extends BaseQuickAdapter<List<String> , BaseViewHolder> {

    public GameRecyclerViewAdapter(@Nullable List<List<String> > data) {
        super(R.layout.item_game, data);
    }

    @Override
    protected void convert(BaseViewHolder helper,List<String>  item) {
        RecyclerView recyclerView=(RecyclerView) helper.getView(R.id.rv_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext,RecyclerView.HORIZONTAL,false));
        GameAdapter adapter=new GameAdapter(item);
        recyclerView.setAdapter(adapter);
        adapter.bindToRecyclerView(recyclerView);
        recyclerView.setNestedScrollingEnabled(false);
    }
}
