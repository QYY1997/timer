package com.timer.com.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;


/**
 * 存储商户信息里面展示的内容
 *
 * @author lzx
 */
public class StorageCustomerInfoUtil {

    public static void putInfo(Context context, String key, String value) {
        SharedPreferences sps = context.getSharedPreferences("customerInfo", Context.MODE_PRIVATE);
        Editor editor = sps.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static void putInfo(Context context, String key, boolean value) {
        SharedPreferences sps = context.getSharedPreferences("customerInfo", Context.MODE_PRIVATE);
        Editor editor = sps.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static void putInfo(Context context, String key, int value) {
        SharedPreferences sps = context.getSharedPreferences("customerInfo", Context.MODE_PRIVATE);
        Editor editor = sps.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public static int getIntInfo(Context context, String key, int value) {
        SharedPreferences sps = context.getSharedPreferences("customerInfo", Context.MODE_PRIVATE);
        return sps.getInt(key, value);

    }
    public static void putInfo(Context context, String key, Long value) {
        SharedPreferences sps = context.getSharedPreferences("customerInfo", Context.MODE_PRIVATE);
        Editor editor = sps.edit();
        editor.putLong(key, value);
        editor.commit();
    }

    public static Long getLongInfo(Context context, String key, Long value) {
        SharedPreferences sps = context.getSharedPreferences("customerInfo", Context.MODE_PRIVATE);
        return sps.getLong(key, value);

    }
    public static boolean getBooleanInfo(String key, Context context,boolean def) {
        SharedPreferences sps = context.getSharedPreferences("customerInfo", Context.MODE_PRIVATE);
        boolean value = sps.getBoolean(key, def);
        return value;
    }

    public static String getInfo(String key, Context context) {
        SharedPreferences sps = context.getSharedPreferences("customerInfo", Context.MODE_PRIVATE);
        String value = sps.getString(key, "");
        return value;
    }

    /**
     * @param key
     * @param context
     * @return
     */
    public static boolean removeKey(String key, Context context) {
        SharedPreferences sp = context.getSharedPreferences("customerInfo", Context.MODE_PRIVATE);
        Editor editor = sp.edit();
        editor.remove(key);
        editor.commit();
        return true;
    }

    public static boolean clearKey(Context context) {
        SharedPreferences sp = context.getSharedPreferences("customerInfo", Context.MODE_PRIVATE);
        Editor editor = sp.edit();
        editor.clear();
        editor.commit();
        return true;
    }
}
