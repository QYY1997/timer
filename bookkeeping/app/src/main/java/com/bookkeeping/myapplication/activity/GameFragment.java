package com.bookkeeping.myapplication.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bookkeeping.myapplication.R;
import com.bookkeeping.myapplication.adapter.GameAdapter;
import com.bookkeeping.myapplication.adapter.GameRecyclerViewAdapter;
import com.bookkeeping.myapplication.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author : qiuyiyang
 * @date : 2021/10/29  15:58
 * @desc :
 */
public class GameFragment extends BaseFragment {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_input)
    EditText etInput;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.rv_list_head)
    RecyclerView rvListHead;
    @BindView(R.id.nsv)
    HorizontalScrollView nsv;
    @BindView(R.id.rv_list_title)
    RecyclerView rvListTitle;
    @BindView(R.id.tv_item)
    TextView tvItem;

    private GameRecyclerViewAdapter recyclerViewAdapter;
    private GameAdapter gameAdapter;
    private GameAdapter gameTitleAdapter;
    private List<List<String>> mList = new ArrayList<>();
    private List<String> mListHead = new ArrayList<>();
    private List<String> mListTitle = new ArrayList<>();

    @Override
    public int initLayout() {
        return R.layout.act_game;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void initData(View v) {
        ivBack.setVisibility(View.GONE);
        tvTitle.setText("点杀泰坦");
        recyclerViewAdapter = new GameRecyclerViewAdapter(mList);
        rvList.setLayoutManager(new LinearLayoutManager(context));
        recyclerViewAdapter.bindToRecyclerView(rvList);
        rvList.setNestedScrollingEnabled(false);
        gameAdapter = new GameAdapter(mListHead);
        gameAdapter.bindToRecyclerView(rvListHead);
        gameTitleAdapter = new GameAdapter(mListTitle);
        gameTitleAdapter.bindToRecyclerView(rvListTitle);
        rvListTitle.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
        rvListTitle.setNestedScrollingEnabled(false);
        rvListHead.setLayoutManager(new LinearLayoutManager(context));
        rvListHead.setNestedScrollingEnabled(false);
        getPasteString();
        rvListTitle.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (RecyclerView.SCROLL_STATE_IDLE != recyclerView.getScrollState()) {
                    rvList.scrollBy(dx, dy);
                }
            }
        });

        rvList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (RecyclerView.SCROLL_STATE_IDLE != recyclerView.getScrollState()) {
                    rvListHead.scrollBy(dx, dy);
                    rvListTitle.scrollBy(dx, dy);
                }
            }
        });
        rvListHead.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (RecyclerView.SCROLL_STATE_IDLE != recyclerView.getScrollState()) {
                    rvList.scrollBy(dx, dy);
                }
            }
        });
    }


    private void getPasteString() {
        try {
            context.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clipData = clipboard.getPrimaryClip();
                    if (clipData != null && clipData.getItemCount() > 0) {
                        CharSequence text = clipData.getItemAt(0).getText();
                        getData(text.toString());
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.tv_submit)
    public void onViewClicked() {
        getData(etInput.getText().toString().trim());
    }

    private void getData(String str) {
        String[] split = str.split("\\n");
        for (int i = 0; i < split.length; i++) {
            String[] strings = split[i].split(",");
            List<String> list = new ArrayList<>();
            for (int j = 1; j < strings.length; j++) {
                list.add(strings[j]);
            }
            mListHead.add(strings[0]);
            mList.add(list);
        }
        mListTitle = mList.get(0);
        mList.remove(0);
        gameTitleAdapter.setNewData(mListTitle);
        recyclerViewAdapter.setNewData(mList);
        gameAdapter.setNewData(mListHead);
        tvItem.setText(mListHead.get(0));
        mListHead.remove(0);
    }
}
