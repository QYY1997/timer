package com.bookkeeping.myapplication.util.okgo;

import android.content.Context;
import android.widget.Toast;

import com.lzy.okgo.callback.Callback;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author: qiuyiyang
 * @description:
 * @date: 2019/3/27
 */
public abstract class NormalCallback extends StringCallback {
    //密钥
    private Context mContext;

    public NormalCallback(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void onStart(Request request) {
        super.onStart(request);
        Sign(request.getParams());
    }

    private void Sign(HttpParams params) {
//        params.put("0", "0700");
        Map<String, Object> map = new HashMap<>();
        for (Map.Entry<String, List<String>> entry : params.urlParamsMap.entrySet()) {
            map.put(entry.getKey(), entry.getValue().get(0));
        }
        Map<String, Object> resultMap = sortMapByKey(map);
        String sign = "";
        for (Map.Entry<String, Object> entry : resultMap.entrySet()) {
            if ("".equals(sign)) {
                sign = entry.getValue() + "";
            } else {
                sign += entry.getValue();
            }
        }
    }

    /***
     * 进行排序
     * @param map
     * @return
     */
    private static Map<String, Object> sortMapByKey(Map<String, Object> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }

        Map<String, Object> sortMap = new TreeMap<String, Object>(new Comparator<String>() {
            @Override
            public int compare(String lhs, String rhs) {
                // 这里改为小写进行比较
                Integer l = Integer.parseInt(lhs);
                Integer r = Integer.parseInt(rhs);
                return l.compareTo(r);
            }
        });
        sortMap.putAll(map);
        return sortMap;
    }
}
