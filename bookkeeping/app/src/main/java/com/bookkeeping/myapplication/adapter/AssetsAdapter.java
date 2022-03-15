package com.bookkeeping.myapplication.adapter;

import androidx.annotation.Nullable;
import com.bookkeeping.myapplication.R;
import com.bookkeeping.myapplication.model.TypeModel;
import com.bookkeeping.myapplication.util.StringUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pilot.common.utils.PinyinUtils;

import java.util.Arrays;
import java.util.List;

import static android.text.Html.fromHtml;

/**
 * @author: qiuyiyang
 * @description:
 * @date: 2019/4/4
 */
public class AssetsAdapter extends BaseQuickAdapter<TypeModel, BaseViewHolder> {
    private String str="";
    public AssetsAdapter(@Nullable List<TypeModel> data) {
        super(R.layout.item_assets, data);
    }

    public void setStr(String str) {
        this.str = str;
    }

    @Override
    protected void convert(BaseViewHolder helper, TypeModel item) {
        helper.setText(R.id.tv_name,item.getId());
        helper.setText(R.id.tv_balance,"￥ "+item.getBalance()+"");
        switch (helper.getAdapterPosition()%3){
            case 0:
                helper.setBackgroundRes(R.id.ll_assets,R.drawable.shape_solid_red_corner_10);
                break;
            case 1:
                helper.setBackgroundRes(R.id.ll_assets,R.drawable.shape_solid_blue_corner_10);
                break;
            case 2:
                helper.setBackgroundRes(R.id.ll_assets,R.drawable.shape_solid_green_corner_10);
                break;
        }
        if (!StringUtil.isEmpty(str)) {
            String chars=str;
            String content = "<html>";
            for (int i=0;i<item.getId().length();i++){
                String word=item.getId().substring(i,i+1);
                String[] strings= PinyinUtils.converterToFirstSpell(word).split(",");
                String[] string= PinyinUtils.converterToSpell(word).split(",");
                List<String> lists= Arrays.asList(strings);
                List<String> list =Arrays.asList(string);
                boolean flag=false;
                for (int k=list.size()-1;k>=0;k--){
                    int index=(chars).toLowerCase().lastIndexOf(list.get(k));
                    if (index>0){
                        flag=true;
                        chars=chars.replaceFirst(chars.substring(index,index+list.get(k).length()),"");
                        break;
                    }
                }
                if (!flag) {
                    for (int j = chars.length() - 1; j >= 0; j--) {
                        String check = chars.substring(j, j + 1);
                        if (word.equals(check) || word.equals(check.toUpperCase()) || lists.contains(check.toLowerCase())) {
                            flag = true;
                            chars = chars.replaceFirst(check, " ");
                            break;
                        }
                    }
                }

                if (!flag){
                    content += "<font color=\"#FFFFFF\">" +  item.getId().substring(i,i+1)   + "</font>";
                }
                else {
                    content +=  "<font color=\"#fbd100\">" +  item.getId().substring(i,i+1)  + "</font>" ;
                }
            }
            content += "</html>";
            helper.setText(R.id.tv_name, fromHtml(content));
        }
    }
}
