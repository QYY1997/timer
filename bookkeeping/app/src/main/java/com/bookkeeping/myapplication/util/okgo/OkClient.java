package com.bookkeeping.myapplication.util.okgo;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bookkeeping.myapplication.model.bilibili.ResponseModel;
import com.bookkeeping.myapplication.util.StorageCustomerInfo02Util;
import com.bookkeeping.myapplication.util.StringUtil;
import com.bookkeeping.myapplication.util.okHttp.OkHttp3Util;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import java.io.File;
import java.net.ConnectException;
import java.util.ArrayList;
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
public class OkClient {
    private static final String TAG = "OkClient";
    public  static OkClient okClient;
    //请求超时的重试次数 默认超时不重连
    private int retryCount = 0;

    private OkClient() {
    }

    /**
     * 获取实例
     *
     * @return
     */
    public static OkClient getInstance() {
        if (okClient == null) {
            synchronized (OkClient.class) {
                if (okClient == null) {
                    okClient = new OkClient();
                }
            }
        }
        return okClient;
    }

    /**
     * 获取文件HttpParams对象
     *
     * @return
     */
    public static Params getParamsInstance() {
        return Params.getInstance();
    }

    /**
     * 取消所有请求
     */
    public static void CancelAll() {
        OkGo.getInstance().cancelAll();
    }

    private static void Sign(HttpParams params) {
        Map<String, Object> map = new HashMap<>();
        for (Map.Entry<String, List<String>> entry : params.urlParamsMap.entrySet()) {
            map.put(entry.getKey(), entry.getValue().get(0));
        }
//        Map<String, Object> resultMap = sortMapByKey(map);
        String sign = "";
        for (Map.Entry<String, Object> entry : map.entrySet()) {
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

    /**
     * 设置超时重连次数
     *
     * @param retryCount
     * @return
     */
    public OkClient setRetryCount(int retryCount) {
        this.retryCount = retryCount;
        return this;
    }

    /**
     * get请求
     *
     * @param url            get请求地址
     * @param entityCallback 实体类回调
     */
    public void get(String url, AbsCallback entityCallback) {
        OkGo.get(url)
                .tag(this)
                .retryCount(retryCount)
                .execute(entityCallback);
    }

    /**
     * get请求
     *
     * @param url            get请求地址
     * @param params         可以使用OkMap，就不用考虑转化为String的麻烦，int boolean float都可以
     * @param entityCallback 实体类的接口回调，解析的时候要注意，这里使用的是FastJson
     */
    public void get(String url, HttpParams params, AbsCallback entityCallback) {
        Log.i(TAG, url + "\n" + params.toString().replaceAll("\\[", "").replaceAll("\\]", ""));
        OkGo.get(url)
                .tag(this)
                .retryCount(retryCount)
                .params(params)
                .execute(entityCallback);
    }

    /**
     * post请求
     *
     * @param url            post请求地址
     * @param params         可以使用OkMap，就不用考虑转化为String的麻烦，int boolean float都可以
     * @param entityCallback 实体类的接口回调，解析的时候要注意，这里使用的是FastJson
     */
    public void post(String url, HttpParams params, AbsCallback entityCallback) {
        Log.i(TAG, url + "\n" + params.toString().replaceAll("\\[", "").replaceAll("\\]", ""));
        OkGo.post(url)
                .tag(this)
                .retryCount(retryCount)
                .params(params)
                .client(OkHttp3Util.getInstance())
                .execute(entityCallback);
    }

    public void post(String url, HttpParams params,boolean t, AbsCallback entityCallback) {
        Log.i(TAG, url + "\n" + params.toString().replaceAll("\\[", "").replaceAll("\\]", ""));
        OkGo.post(url)
                .tag(this)
                .retryCount(retryCount)
                .params(params)
                .client(OkHttp3Util.getInstance())
                .execute(entityCallback);
    }

//    /**
//     * @param params
//     * @param entityCallback
//     */
//    public void post(HttpParams params, AbsCallback entityCallback) {
//        Log.i(TAG, Constant.REQUEST_API + "\n" + params.toString().replaceAll("\\[", "").replaceAll("\\]", ""));
//        OkGo.<String>post(Constant.REQUEST_API)
//                .tag(this)
//                .retryCount(retryCount)
//                .params(params)
//                .execute(entityCallback);
//
//    }


    public abstract static class EntityCallBack<T> extends AbsCallback<T> {

        private Context mContext;
        private Class<T> clazz;
        private ResponseModel responseModel;

        public EntityCallBack(Context context, Class<T> clazz) {
            this.mContext = context;
            this.clazz = clazz;
        }

        @Override
        public T convertResponse(okhttp3.Response response) throws Throwable {
            synchronized (this) {
                T entity;
                String jsonStr = response.body().string();
                Log.i(TAG, "json=" + jsonStr);
                try {
                    entity = null;
                    if (clazz != null) {
                        entity = JSON.parseObject(jsonStr, clazz);
                        responseModel = (ResponseModel) entity;
                    }
                } catch (Exception e) {
                    try {
                        responseModel = JSON.parseObject(jsonStr, ResponseModel.class);
                    } catch (Exception e1) {
                        Log.e(TAG, "BaseEntity解析错误:" + e.getMessage());
                    }
                    Log.e(TAG, clazz.toString() + "解析错误:" + e.getMessage());
                    return null;
                } finally {
                    response.close();
                }
                return entity;
            }
        }

        @Override
        public void onStart(Request<T, ? extends Request> request) {
            super.onStart(request);
            request.headers("Cookie",StorageCustomerInfo02Util.getInfo("cookies",mContext));
            request.headers("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
            Sign(request.getParams());
        }


        @Override
        public void onCacheSuccess(Response<T> response) {
            Log.i(TAG, "onCacheSuccess");
        }

        @Override
        public void onError(Response<T> response) {
            if (response == null) {
                return;
            }
            if (response.getException() != null && response.getException() instanceof ConnectException) {
                Toast.makeText(mContext,"没有可用的网络！", Toast.LENGTH_SHORT).show();
                Log.i(TAG, "response="+response.getException());
            }else {
                Log.e(TAG,"response="+response.body());
                Toast.makeText(mContext, "服务器异常", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onSuccess(Response<T> response) {
            if (responseModel == null) {
                Toast.makeText(mContext, "服务器异常", Toast.LENGTH_SHORT).show();
                return;
            }
        }
    }

    /**
     * 这里设置参数 HttpParams
     */
    public static class Params {

        private static HttpParams params;

        public static Params getInstance() {
            params = new HttpParams();
            return new Params();
        }

        public HttpParams getParams() {
            return params;
        }

        public Params put(String key, String value) {
            params.put(key, value);
            return this;
        }

        public Params put(String key, int value) {
            params.put(key, value);
            return this;
        }

        public Params put(String key, long value) {
            params.put(key, value);
            return this;
        }

        public Params put(String key, Float value) {
            params.put(key, value);
            return this;
        }

        public Params put(String key, double value) {
            params.put(key, value);
            return this;
        }

        public Params put(String key, char value) {
            params.put(key, value);
            return this;
        }

        public Params put(String key, File file) {
            params.put(key, file);
            return this;
        }

        public Params put(String key, List<String> filePathList) {
            if (filePathList == null || filePathList.size() == 0) {
                return this;
            }
            for (String filePath : filePathList) {
                if (!TextUtils.isEmpty(filePath)) {
                    File file = new File(filePath);
                    params.put(key, file);
                }
            }
            return this;
        }

        public Params put(String key, ArrayList<File> fileList) {
            if (fileList == null || fileList.size() == 0) {
                return this;
            }
            for (File file : fileList) {
                if (file != null) {
                    params.put(key, file);
                }
            }
            return this;
        }
    }
}
