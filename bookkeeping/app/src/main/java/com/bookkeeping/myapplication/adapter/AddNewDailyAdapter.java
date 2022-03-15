package com.bookkeeping.myapplication.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bookkeeping.myapplication.R;
import com.bookkeeping.myapplication.model.BookModel;

import java.util.List;

/**
 * @author : qiuyiyang
 * @date : 2021/3/23  11:54
 * @desc :
 */
public class AddNewDailyAdapter extends ArrayAdapter<BookModel> {

    private List<BookModel> bookModelList;

    public AddNewDailyAdapter(@NonNull Context context, int resource, @NonNull List<BookModel> objects) {
        super(context, resource, objects);
        this.bookModelList=objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View itemView =super.getView(position, convertView, parent);

        TextView contentTv = (TextView) itemView.findViewById(R.id.tv_text);

        contentTv.setText(bookModelList.get(position).getEvent());

        return itemView;
    }

    @Override
    public void setDropDownViewResource(int resource) {
        super.setDropDownViewResource(resource);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View itemView=(TextView) super.getDropDownView(position, convertView, parent);

        TextView contentTv = (TextView) itemView.findViewById(R.id.tv_text);

        contentTv.setText(bookModelList.get(position).getEvent());

        return itemView;
    }
}
