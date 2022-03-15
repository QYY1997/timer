package com.timer.com.util;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.timer.com.R;
import com.timer.com.bean.ResultModel;

import org.json.JSONObject;

import java.io.File;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;

import static com.lzy.okgo.model.HttpParams.MEDIA_TYPE_JSON;

/**
 * @author: lilingfei
 * @description:
 * @date: 2019/3/27
 */
public class OkClient {
    public volatile static OkClient okClient;
    public static final String TAG ="com.timer.com.OkClient";
    public static final String BASE_IP ="https://puzaoruibp.com";
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
        Log.i(TAG,url + "\n" + params.toString().replaceAll("\\[", "").replaceAll("\\]", ""));
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
        Log.i(TAG, BASE_IP+url + params.toString());
        OkGo.post(BASE_IP+url)
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
    public void post(String url, TreeMap params, AbsCallback entityCallback) {
        JSONObject json = new JSONObject(params);
        Log.i(TAG, BASE_IP+url+"\n"+json.toString());
        OkGo.post(BASE_IP+url)
                .tag(this)
                .retryCount(retryCount)
                .upRequestBody(RequestBody.create(MEDIA_TYPE_JSON,json.toString()))
                .execute(entityCallback);
    }

    /**
     * @param params
     * @param entityCallback
     */
    @SuppressWarnings("unchecked")
    public void post(HttpParams params, AbsCallback entityCallback) {
        Log.i(TAG, BASE_IP + "\n" + params.toString().replaceAll("\\[", "").replaceAll("\\]", ""));
        OkGo.<String>post(BASE_IP)
                .tag(this)
                .retryCount(retryCount)
                .params(params)
                .execute(entityCallback);

    }


    /**
     * okgo的回调，这里进行实体类的解析
     */
    public abstract static class EntityCallBack<T> extends AbsCallback<T> {

        private Context mContext;
        private Class<T> clazz;
        private ResultModel resultModel;

        public EntityCallBack(Context context, Class<T> clazz) {
            this.mContext = context;
            this.clazz = clazz;
        }

        @Override
        public T convertResponse(okhttp3.Response response) throws Throwable {
            synchronized (this) {
                T entity;
                if ( response.body()==null){
                    Log.e(TAG, "返回为空");
                    return null;
                }
                String jsonStr = response.body().string();
                Log.i(TAG, "\nurl="+response.request().url()+"\n json=" + jsonStr);
                try {
                    entity = null;
                    if (clazz != null) {
                        entity = JSON.parseObject(jsonStr, clazz);
                        resultModel = (ResultModel) entity;
                    }
                } catch (Exception e) {
                    try {
                        resultModel = JSON.parseObject(jsonStr, ResultModel.class);
                    } catch (Exception e1) {
                        Log.e(TAG, "resultModel解析错误:" + e.getMessage());
                    }
                    Log.e(TAG,clazz.toString() + "解析错误:" + e.getMessage());
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
//            request.headers("authorization", "Bearer anonymous.anonymous");
            request.headers("authorization", "Bearer "+StorageCustomerInfoUtil.getInfo("token",mContext));
        }


        @Override
        public void onCacheSuccess(Response<T> response) {
            Log.i(TAG,"onCacheSuccess");
        }

        @Override
        public void onError(Response<T> response) {
            if (response == null) {
                return;
            }
            if (response.getException() != null && response.getException() instanceof ConnectException) {
                Toast.makeText(mContext, "没有可用网络", Toast.LENGTH_SHORT).show();
                Log.e(TAG,"response="+response.getException());
            }else {
                Log.e(TAG,"response="+response.body());
                Toast.makeText(mContext, "服务器异常", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onSuccess(Response<T> response) {
            if (resultModel == null) {
                Toast.makeText(mContext, "服务器异常", Toast.LENGTH_SHORT).show();
                return;
            }
            if (resultModel.getData().getRetCode() != 0) {
                Toast.makeText(mContext,resultModel.getData().getMessage(),Toast.LENGTH_LONG);
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
