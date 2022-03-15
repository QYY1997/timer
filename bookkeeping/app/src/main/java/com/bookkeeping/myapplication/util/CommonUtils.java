package com.bookkeeping.myapplication.util;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Selection;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.format.DateUtils;
import android.text.style.ImageSpan;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bookkeeping.myapplication.R;
import com.bookkeeping.myapplication.model.bilibili.EmoteModel;
import com.bookkeeping.myapplication.view.ShadowHelper;
import com.google.gson.Gson;
import com.lsjwzh.widget.materialloadingprogressbar.CircleProgressBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Cipher;


/**
 * @author hkrt 常用的工具类
 */
public class CommonUtils {

    public static final String TAG = "CommonUtils.log :";
    private static boolean checkNetState = true;
    private static long lastClickTime = 0;
    private static int id;
    private static BitmapDrawable drawable;

    public static String getTime() {
        DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        String time = df.format(new Date());
        return time;
    }

    public static String getTime(String format) {
        DateFormat df = new SimpleDateFormat(format);
        String time = df.format(new Date());
        return time;
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }


    /**
     * 非法字符、表情过滤器
     * @param length 设置文本最长字符数
     * @return
     */
    public static InputFilter[] getInputFilters(int length) {
        InputFilter[] inputFilters=new InputFilter[]{
                //文本-非法字符过滤器
                new InputFilter() {
                    //正则表达式过滤表情
                    Pattern emoji = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
                            Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
                    @Override
                    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                        Matcher emojiMatcher = emoji.matcher(source);
                        //匹配到表情符号后返回""
                        if (emojiMatcher.find() ) {
                            return "";
                        }
                        return null;
                    }
                },
                new InputFilter() {
                    @Override
                    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                        for (int i = start; i < end; i++) {
                            //逐字检测
                            int type = Character.getType(source.charAt(i));
                            //过滤掉轮换字符和特殊字符
                            if (type == Character.SURROGATE || type == Character.OTHER_SYMBOL) {
                                return "";
                            }
                        }
                        return null;
                    }
                },
                new InputFilter.LengthFilter(length)
        };
        return inputFilters;
    }
    /**
     * 复制文件夹及其中的文件
     *
     * @param oldPath String 原文件夹路径 如：data/user/0/com.test/files
     * @param newPath String 复制后的路径 如：data/user/0/com.test/cache
     * @return <code>true</code> if and only if the directory and files were copied;
     * <code>false</code> otherwise
     */
    public static boolean copyFolder(String oldPath, String newPath) {
        try {
            File newFile = new File(newPath);
            if (!newFile.exists()) {
                if (!newFile.mkdirs()) {
                    Log.e("--Method--", "copyFolder: cannot create directory.");
                    return false;
                }
            }
            File oldFile = new File(oldPath);
            String[] files = oldFile.list();
            File temp;
            for (String file : files) {
                if (oldPath.endsWith(File.separator)) {
                    temp = new File(oldPath + file);
                } else {
                    temp = new File(oldPath + File.separator + file);
                }
                if (temp.isDirectory()) {   //如果是子文件夹
                    copyFolder(oldPath + "/" + file, newPath + "/" + file);
                } else if (!temp.exists()) {
                    Log.e("--Method--", "copyFolder:  oldFile not exist.");
                    return false;
                } else if (!temp.isFile()) {
                    Log.e("--Method--", "copyFolder:  oldFile not file.");
                    return false;
                } else if (!temp.canRead()) {
                    Log.e("--Method--", "copyFolder:  oldFile cannot read.");
                    return false;
                } else {
                    FileInputStream fileInputStream = new FileInputStream(temp);
                    FileOutputStream fileOutputStream = new FileOutputStream(newPath + "/" + temp.getName());
                    byte[] buffer = new byte[1024];
                    int byteRead;
                    while ((byteRead = fileInputStream.read(buffer)) != -1) {
                        fileOutputStream.write(buffer, 0, byteRead);
                    }
                    fileInputStream.close();
                    fileOutputStream.flush();
                    fileOutputStream.close();
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    /** 删除目录及目录下的文件
     * @param filePath 要删除的目录的文件路径
     * @return 目录删除成功返回true，否则返回false
     */
    public static boolean deleteDirectory(String filePath) {
        // 如果dir不以文件分隔符结尾，自动添加文件分隔符
        if (!filePath.endsWith(File.separator)) {
            filePath = filePath + File.separator;
        }
        File dirFile = new File(filePath);
        // 如果dir对应的文件不存在，或者不是一个目录，则退出
        if ((!dirFile.exists()) || (!dirFile.isDirectory())) {
            Log.e("--Method--", "删除目录失败：" + filePath + "不存在！");
            return false;
        }
        boolean flag = true;
        // 删除文件夹中的所有文件包括子目录
        File[] files = dirFile.listFiles();
        for (File file : files) {
            // 删除子文件
            if (file.isFile()) {
                flag = deleteSingleFile(file.getAbsolutePath());
                if (!flag) {
                    break;
                }
            }
            // 删除子目录
            else if (file.isDirectory()) {
                flag = deleteDirectory(file
                        .getAbsolutePath());
                if (!flag) {
                    break;
                }
            }
        }
        if (!flag) {
            Log.e("--Method--", "删除目录失败！");
            return false;
        }
        // 删除当前目录
        if (dirFile.delete()) {
            Log.e("--Method--", "Copy_Delete.deleteDirectory: 删除目录" + filePath + "成功！");
            return true;
        } else {
            Log.e("--Method--", "删除目录：" + filePath + "失败！");
            return false;
        }
    }
    /** 删除单个文件
     * @param filePath$Name 要删除的文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteSingleFile(String filePath$Name) {
        File file = new File(filePath$Name);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                Log.e("--Method--", "Copy_Delete.deleteSingleFile: 删除单个文件" + filePath$Name + "成功！");
                return true;
            } else {
                Log.e("--Method--", "删除单个文件" + filePath$Name + "失败！");
                return false;
            }
        } else {
            Log.e("--Method--", "删除单个文件失败：" + filePath$Name + "不存在！");
            return false;
        }
    }

    public static boolean hasFile(String filePath){
        File file = new File(filePath);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists()) {
            return true;
        }
        return false;
    }
    public static String getDirPath(){
        //TODO 这里返回存放db的文件夹的绝对路径
        return   Environment.getExternalStorageDirectory().getPath() + "/bookkeeping";
    }
    public static String getVideoPath(){
        //TODO 这里返回存放db的文件夹的绝对路径
        return   Environment.getExternalStorageDirectory().getPath() + "/bookkeeping/video/";
    }
    /**
     * 设置事件不可连续点击
     *
     * @return
     */
    public static boolean isFastDoubleClick(int viewId,int times) {
        if (id==viewId) {
            long time = System.currentTimeMillis();
            long timeD = time - lastClickTime;
            Log.e(TAG, "timeD==" + timeD);
            if (0 < timeD && timeD < times) {
                return true;
            }
            lastClickTime = time;
        }
        return false;
    }
    /**
     * 设置验证码不可连续点击
     *
     * @return
     */
    public static boolean isFastDoubleClick2(int viewId) {
        if (id==viewId){
            long time = System.currentTimeMillis();
            long timeD = time - lastClickTime;
            Log.e(TAG, "timeD==" + timeD);
            if (0 < timeD && timeD < 500) {
                return true;
            }
            lastClickTime = time;
            return false;
        }
        return false;
    }

    /*
     *unicode解码
     */
    public static String decode(String unicodeStr) {
        if (unicodeStr == null) {
            return null;
        }
        StringBuffer retBuf = new StringBuffer();
        int maxLoop = unicodeStr.length();
        for (int i = 0; i < maxLoop; i++) {
            if (unicodeStr.charAt(i) == '\\') {
                if ((i < maxLoop - 5) && ((unicodeStr.charAt(i + 1) == 'u') || (unicodeStr.charAt(i + 1) == 'U'))) {
                    try {
                        retBuf.append((char) Integer.parseInt(unicodeStr.substring(i + 2, i + 6), 16));
                        i += 5;
                    } catch (NumberFormatException localNumberFormatException) {
                        retBuf.append(unicodeStr.charAt(i));
                    }
                }
                else {
                    retBuf.append(unicodeStr.charAt(i));
                }
            } else {
                retBuf.append(unicodeStr.charAt(i));
            }
        }
        return retBuf.toString();
    }
    public static int strToInt(String value) {
        int i = 0;
        try {

            i = Integer.parseInt(value);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return i;
    }

    /**
     * @param d   今天
     * @param day 几天前
     * @return
     */
    public static Date getDateBefore(Date d, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
        return now.getTime();
    }

    public static String getTime(Date date, String format) {
        DateFormat df = new SimpleDateFormat(format);
        String time = df.format(date);
        return time;
    }

    public static String getYearTime() {
        DateFormat df = new SimpleDateFormat("yyMMddHHmmss");
        String time = df.format(new Date());
        return time;
    }

    public static String getMonthTime() {
        DateFormat df = new SimpleDateFormat("MMddHHmmss");
        String time = df.format(new Date());
        return time;
    }

    public static String getFormatTime(String date) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String time = df.format(new Date(date));
        return time;
    }

    public static String getFormatTime_(String date, String format) {
        DateFormat df = new SimpleDateFormat(format);
        DateFormat df_ = new SimpleDateFormat(format);
        Date date_ = null;
        try {
            date_ = df.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String time = df_.format(date_);
        return time;
    }

    public static boolean isTaday(String time) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        boolean isTaday = false;
        try {
            date = format.parse(time);
            long dateTime = date.getTime();
            isTaday = DateUtils.isToday(dateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return isTaday;

    }

    /**
     * 判断身份证号 中国的身份证为15位或18位
     *
     * @param idCardNum
     * @return
     */
    public static boolean isIdCard(String idCardNum) {

        Pattern p = Pattern
                .compile("(\\d{15}$)|(^\\d{18}$)|(^\\d{17}(\\d|X|x)$)");
        Matcher m = p.matcher(idCardNum);
        Log.v("isIdCard", m.matches() + "");
        return m.matches();
    }

    //验证银行卡号
    public static boolean checkBankCard(String cardId) {
        String reges = "^\\d{16,19}$";
        Pattern pattern = Pattern.compile(reges);
        Matcher matcher = pattern.matcher(cardId);
        if (matcher.matches()) {
            return true;
        }
        return false;
    }


    /**
     * @param time       如：20130809121308
     * @param timeFormat 日期格式，如：yyyyMMddHHmmss,yyyy-MM-dd HH:mm:ss
     * @return
     */

    public static long getTimeLong(String time, String timeFormat) {

        SimpleDateFormat format = new SimpleDateFormat(timeFormat);
        Date date = null;
        long dayTime = 0;
        try {
            date = format.parse(time);
            dayTime = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return dayTime;

    }

    /**
     * @param context
     * @return
     */

    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager
                    .getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }


    public static boolean isWifiConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mWiFiNetworkInfo = mConnectivityManager
                    .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (mWiFiNetworkInfo != null) {
                return mWiFiNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    public static boolean isMobileConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mMobileNetworkInfo = mConnectivityManager
                    .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (mMobileNetworkInfo != null) {
                return mMobileNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * 检查网络状态
     *
     * @param context
     */
    public static boolean checkTheNetState(Context context) {
        int netStatus = CommonUtils.getConnectedType(context);
        if (netStatus == -1) {
            Toast.makeText(context, "请检查手机网络", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public static int getConnectedType(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager
                    .getActiveNetworkInfo();
            if (mNetworkInfo != null && mNetworkInfo.isAvailable()) {
                return mNetworkInfo.getType();
            }
        }
        return -1;
    }

    /**
     * 把字符串的中间某几位位用“*”号代替
     *
     * @param str 要代替的字符串
     * @param n   代替的位数
     * @return
     */

    public static String replaceSubString(String str, int n) {
        String sub = "";
        try {
            if (str == "") {
                return "";
            }
            if (str == null) {
                return "";
            }
            if (str.length() < 16) {
                return str;
            }
            Log.i(TAG, "str old==" + str);
            String strs = str.substring(13);
            sub = str.substring(0, str.length() - n);
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < 4; i++) {
                sb = sb.append("*");
            }
            sub += sb.toString();
            sub += strs;
            Log.i(TAG, "str new==" + sub);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sub;
    }

    public static String replaceSubStringNew(String str) {

        String sub = "";
        try {
            if (str == "") {
                return "";
            }
            if (str == null) {
                return "";
            }
            if (str.length() < 16) {
                return str;
            }
            sub = str.substring(0, 6) + "******"
                    + str.substring(str.length() - 4);
            Log.i(TAG, "str new==" + sub);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sub;
    }

    public static String toHexString(String s) {

        String str = "";
        for (int i = 0; i < s.length(); i++) {
            int ch = (int) s.charAt(i);
            String s4 = Integer.toHexString(ch);
            str = str + s4;
        }
        return str;
    }

    public static String byteToString(byte b) {
        byte high, low;
        byte maskHigh = (byte) 0xf0;
        byte maskLow = 0x0f;
        high = (byte) ((b & maskHigh) >> 4);
        low = (byte) (b & maskLow);
        StringBuffer buf = new StringBuffer();
        buf.append(findHex(high));
        buf.append(findHex(low));
        return buf.toString();
    }

    private static char findHex(byte b) {
        int t = new Byte(b).intValue();
        t = t < 0 ? t + 16 : t;
        if ((0 <= t) && (t <= 9)) {
            return (char) (t + '0');
        }
        return (char) (t - 10 + 'A');
    }

    public static String moneyToZeroFormat(String money) {

        String money_ = null;
        if (money.contains(".")) {

            String[] moneyStr = money.split("\\.");
            Log.i(TAG, "moneyStr length=" + moneyStr.length);
            String centMoney = moneyStr[1];
            if (centMoney.length() < 2) {
                centMoney = centMoney + "0";
            }
            money_ = moneyStr[0] + centMoney;
        } else {
            money_ = money + "00";
        }

        int len = money_.length();

        int left = 12 - len;
        StringBuffer str = new StringBuffer();
        for (int i = 0; i < left; i++) {
            str.append("0");
        }

        String s = str + money_;
        return s;
    }

    /**
     * 16进制格式的字符串转成16进制byte 44 --> byte 0x44
     *
     * @param hexString
     * @return
     */
    public static byte[] HexStringToByteArray(String hexString) {//
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        if (hexString.length() == 1 || hexString.length() % 2 != 0) {
            hexString = "0" + hexString;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    /**
     * 对double数据进行取精度.
     * <p>
     * For example: <br>
     * double value = 100.345678; <br>
     * double ret = round(value,4,BigDecimal.ROUND_HALF_UP); <br>
     * ret为100.3457 <br>
     *
     * @param value        double数据.
     * @param scale        精度位数(保留的小数位数).
     * @param roundingMode 精度取值方式.
     * @return 精度计算后的数据.
     */
    public static double round(double value, int scale, int roundingMode) {
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(scale, roundingMode);
        double d = bd.doubleValue();
        bd = null;
        return d;
    }

    /**
     * 返回当前程序版本名 如1.2.0
     */
    public static String getAppVersionName(Context context) {
        String versionName = "";
        try {
            // ---get the package info---
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return versionName;
    }

    /**
     * 获取当前程序的版本号 如 14
     *
     * @param context
     * @return 版本code
     */
    public static int getAppVersionCode(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }

    /**
     * 判断是否是手机号的正则表达式
     *
     * @param context
     * @param phone
     * @return
     */
    public static boolean isPhone(Context context, String phone) {
        String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
        if (phone.length() != 11) {
           Toast.makeText(context, "手机号应为11位数", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(phone);
            boolean isMatch = m.matches();
            Log.e(TAG, "isMatch:" + isMatch);
            if (!isMatch) {
                Toast.makeText(context, "请填入正确的手机号", Toast.LENGTH_SHORT).show();
            }
            return isMatch;
        }
    }

    public static boolean isCorrectPhone(Context context, String phone) {
        if (phone.length() != 11) {
            Toast.makeText(context, "手机号应为11位数", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            if (!phone.startsWith("1")) {
                Toast.makeText(context, "请填入正确的手机号", Toast.LENGTH_SHORT).show();
                return false;
            }
            return true;
        }
    }


    /**
     * 保留小数点后2位
     *
     * @param s
     * @return
     */
    @SuppressLint("NewApi")
    public static String format(String s) {
        if (s == null || s.length() < 1) {
            return "";
        }
        if (s.contains(",")) {
            s = s.replace(",", "");
        }
        NumberFormat formater = null;
        double num = Double.parseDouble(s);
        Log.i(TAG, "num==" + num);
        formater = new DecimalFormat("#,##0.00");

        formater.setRoundingMode(RoundingMode.DOWN);
        String moneyVal = formater.format(num);

        // if(Long.valueOf(moneyVal)<1){
        //
        // moneyVal = "0" + moneyVal;
        //
        // }
        return moneyVal;

    }

    /**
     * 保留小数点后2位
     * 如果为空则返回0.00
     *
     * @param s
     * @return
     */
    @SuppressLint("NewApi")
    public static String format00(String s) {
        if (s.contains(",")) {
            s = s.replace(",", "");
        }
        if (s == null || s.length() < 1) {
            return "0.00";
        }
        NumberFormat formater = null;
        double num = Double.parseDouble(s);
        Log.i(TAG,"num==" + num);
        formater = new DecimalFormat("#,##0.00");

        formater.setRoundingMode(RoundingMode.DOWN);
        String moneyVal = formater.format(num);

        // if(Long.valueOf(moneyVal)<1){
        //
        // moneyVal = "0" + moneyVal;
        //
        // }
        return TextUtils.isEmpty(moneyVal) ? "0.00" : moneyVal;

    }

    /**
     * 将12位数值的数据转换成###,##0.00格式显示
     *
     * @param s
     * @return
     */
    @SuppressLint("NewApi")
    public static String format02(String s) {
        if (s.contains(",")) {
            s = s.replace(",", "");
        }
        if (s == null || s.length() < 1) {
            return "";
        }
        NumberFormat formater = null;
        double num = Double.parseDouble(s) / 100;
        Log.i(TAG, "num==" + num);
        formater = new DecimalFormat("###,##0.00");

        formater.setRoundingMode(RoundingMode.DOWN);
        String moneyVal = formater.format(num);

        // if(Long.valueOf(moneyVal)<1){
        //
        // moneyVal = "0" + moneyVal;
        //
        // }
        return moneyVal;

    }

    /**
     * 保留小数后1位
     *
     * @param
     * @return
     */
    public static String formatNum(String str) {
        if (str == null || str.length() < 1) {
            return "";
        }
        NumberFormat formater = null;
        double num = Double.parseDouble(str);
        formater = new DecimalFormat("#,##0.0");

        String moneyVal = formater.format(num);
        return moneyVal;

    }


    /**
     * 保留小数后0位
     *
     * @param
     * @return
     */
    @SuppressLint("NewApi")
    public static String formatNum_(String str) {
        if (str == null || str.length() < 1) {
            return "";
        }
        NumberFormat formater = null;
        double num = Double.parseDouble(str);
        formater = new DecimalFormat("#,##0");

        formater.setRoundingMode(RoundingMode.DOWN);
        String moneyVal = formater.format(num);

        return moneyVal;

    }

    public static String convertStringToHex(String str) {
        char[] chars = str.toCharArray();
        StringBuffer hex = new StringBuffer();
        for (int i = 0; i < chars.length; i++) {
            if ((int) chars[i]<48) {
                hex.append("%" + Integer.toHexString((int) chars[i]));
            }
            else {
                hex.append(chars[i]);
            }
        }
        return hex.toString();
    }

    /**
     * 字符串转为URL对象
     * @param url url字符串
     * @return url对象
     */
    private static URL stringToURL(String url){
        if(url==null || url.length() == 0 || !url.contains("://")){
            return null;
        }
        try {
            StringBuilder sbUrl = new StringBuilder("http");
            sbUrl.append(url.substring(url.indexOf("://")));
            URL mUrl = new URL(sbUrl.toString());
            return mUrl;
        }catch (Exception ex){
            return null;
        }
    }
    /**
     * 获得解析后的URL参数
     * @param url url对象
     * @return URL参数map集合
     */
    public static Map<String, String> getUrlParams(String url){
        final Map<String, String> query_pairs = new LinkedHashMap<String, String>();
        URL mUrl = stringToURL(url);
        if(mUrl == null)
        {
            return query_pairs;
        }
        try {
            String query = mUrl.getQuery();
            if(query==null){
                return query_pairs;
            }
            //判断是否包含url=,如果是url=后面的内容不用解析
            if(query.contains("url=")){
                int index = query.indexOf("url=");
                String urlValue = query.substring(index + 4);
                query_pairs.put("url", URLDecoder.decode(urlValue, "UTF-8"));
                query = query.substring(0, index);
            }
            //除url之外的参数进行解析
            if(query.length()>0) {
                final String[] pairs = query.split("&");
                for (String pair : pairs) {
                    final int idx = pair.indexOf("=");
                    //如果等号存在且不在字符串两端，取出key、value
                    if (idx > 0 && idx < pair.length() - 1) {
                        final String key = URLDecoder.decode(pair.substring(0, idx), "UTF-8");
                        final String value = URLDecoder.decode(pair.substring(idx + 1), "UTF-8");
                        query_pairs.put(key, value);
                    }
                }
            }
        }catch (Exception ex){
        }
        return query_pairs;
    }


    /**
     * 用公钥对字符串进行加密
     *
     * @param data 原文
     */
    public static byte[] encryptByPublicKey(byte[] data, byte[] publicKey) throws Exception {
        // 得到公钥
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKey);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        PublicKey keyPublic = kf.generatePublic(keySpec);
        // 加密数据
        Cipher cp = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cp.init(Cipher.ENCRYPT_MODE, keyPublic);
        return cp.doFinal(data);
    }

    /**
     * @params str 要加密的字符串
     */
    public static String rsaEncode(String str,String publicKey) {
        publicKey="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDjb4V7EidX/ym28t2ybo0U6t0n6p4ej8VjqKHg100va6jkNbNTrLQqMCQCAYtXMXXp2Fwkk6WR+12N9zknLjf+C9sx/+l48mjUU8RqahiFD1XT/u2e0m2EN029OhCgkHx3Fc/KlFSIbak93EH/XlYis0w+Xl69GV6klzgxW6d2xQIDAQAB";
        String outStr = "";
        try {
            // base64编码的公钥
            byte[] decoded = Base64.decode(publicKey, Base64.DEFAULT);
            RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
            // RSA加密
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, pubKey);
            outStr = Base64.encodeToString(cipher.doFinal(str.getBytes("UTF-8")), Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return outStr;
    }
    /**
     * 保留小数后3位
     *
     * @param
     * @return
     */
    @SuppressLint("NewApi")
    public static String formatNumThree(String str) {
        if (str == null || str.length() < 1) {
            return "";
        }
        NumberFormat formater = null;
        double num = Double.parseDouble(str);
        formater = new DecimalFormat("#,##0.00");

        formater.setRoundingMode(RoundingMode.DOWN);
        String moneyVal = formater.format(num);

        return moneyVal;

    }

    /**
     * 显示短卡号
     * 如前6后4 1122334455667788——112233******7788
     *
     * @param str
     * @return
     */
    @SuppressLint("NewApi")
    public static String translateShortNumber(String str, int start, int end) {
        if (str.length() < start + end) return str;
        String middle = str.substring(start, str.length() - end);
        StringBuffer middle_new = new StringBuffer();
        for (int i = 0; i < middle.length(); i++) {
            middle_new.append("*");
        }
        String startStr = str.substring(0, start);
        String endStr = str.substring(start);
        String s = startStr + endStr.replace(middle, middle_new);
        return s;
    }

    public static boolean containStr(String str) {

        int count = 0;
        String regEx = "[\\u4e00-\\u9fa5]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        while (m.find()) {
            for (int i = 0; i <= m.groupCount(); i++) {
                count = count + 1;
            }
        }
        return count > 0;
    }

    /**
     * toast
     */
    public static void mMakeTextToast(Context context, String text,
                                      boolean isLong) {

        if (isLong == true) {
            Toast.makeText(context, text, Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
        }
    }
    public  static void jumpStartInterface(Context context) {
        String mobileType=Build.MANUFACTURER;
        Intent intent = new Intent();
        try {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Log.e("HLQ_Struggle", "******************当前手机型号为：" + mobileType);
            ComponentName componentName = null;
            if (mobileType.equals("Xiaomi")) { // 红米Note4
                componentName = new ComponentName("com.miui.securitycenter", "com.miui.permcenter.autostart.AutoStartManagementActivity");
            } else if (mobileType.equals("Letv")) { // 乐视2
                intent.setAction("com.letv.android.permissionautoboot");
            } else if (mobileType.equals("samsung")) { // 三星Note5
                //componentName = new ComponentName("com.samsung.android.sm_cn", "com.samsung.android.sm.ui.ram.AutoRunActivity");
                //componentName = ComponentName.unflattenFromString("com.samsung.android.sm/.ui.ram.RamActivity");// Permission Denial not exported from uid 1000，不允许被其他程序调用
                componentName = ComponentName.unflattenFromString("com.samsung.android.sm/.app.dashboard.SmartManagerDashBoardActivity");
            } else if (mobileType.equals("HUAWEI")) { // 华为
                //componentName = new ComponentName("com.huawei.systemmanager", "com.huawei.systemmanager.optimize.process.ProtectActivity");//锁屏清理
                componentName = ComponentName.unflattenFromString("com.huawei.systemmanager/.startupmgr.ui.StartupNormalAppListActivity");//跳自启动管理
                //SettingOverlayView.show(context);
            } else if (mobileType.equals("vivo")) { // VIVO
                componentName = ComponentName.unflattenFromString("com.iqoo.secure/.safeguard.PurviewTabActivity");
            } else if (mobileType.equals("Meizu")) { //魅族
                //componentName = ComponentName.unflattenFromString("com.meizu.safe/.permission.PermissionMainActivity");//跳转到手机管家
                componentName = ComponentName.unflattenFromString("com.meizu.safe/.permission.SmartBGActivity");//跳转到后台管理页面
            } else if (mobileType.equals("OPPO")) { // OPPO R8205
                componentName = ComponentName.unflattenFromString("com.oppo.safe/.permission.startup.StartupAppListActivity");
            } else if (mobileType.equals("ulong")) { // 360手机
                componentName = new ComponentName("com.yulong.android.coolsafe", ".ui.activity.autorun.AutoRunListActivity");
            } else {
                // 将用户引导到系统设置页面
                if (Build.VERSION.SDK_INT >= 9) {
                    Log.e("HLQ_Struggle", "APPLICATION_DETAILS_SETTINGS");
                    intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                    intent.setData(Uri.fromParts("package", context.getPackageName(), null));
                } else if (Build.VERSION.SDK_INT <= 8) {
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
                    intent.putExtra("com.android.settings.ApplicationPkgName", context.getPackageName());
                }
            }
            StorageCustomerInfo02Util.putInfo(context,"autoStart","1");
            intent.setComponent(componentName);
            context.startActivity(intent);
        } catch (Exception e) {//抛出异常就直接打开设置页面
            Log.e("HLQ_Struggle", e.getLocalizedMessage());
            intent = new Intent(Settings.ACTION_SETTINGS);
            context.startActivity(intent);
        }
    }
    /**
     * toast
     */
    public static void mMakeResIdToast(Context context, int resId,
                                       boolean isLong) {

        if (isLong == true) {
            Toast.makeText(context, resId, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, resId, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * @param s
     * @return
     */
    public static String formatTo12Zero(String s) {//89.90
        if (s == null || s.length() < 1 || s.length() > 12) {
            return "";
        }
        if (s.contains(",")) {
            s = s.replace(",", "");
        }
        s = s.replace(".", "");
        int len = s.length();
        int len_ = 12 - len;
        StringBuffer moneyNum = new StringBuffer();
        for (int i = 0; i < len_; i++) {

            moneyNum.append("0");

        }

        moneyNum.append(s);
        return moneyNum.toString();

    }

    /**
     * @param s
     * @return
     */
    public static String formatTo8Zero(String s) {
        if (s == null || s.length() < 1 || s.length() > 12) {
            return "";
        }
        s = s.replace(".", "");
        int len = s.length();
        int len_ = 8 - len;
        StringBuffer moneyNum = new StringBuffer();
        for (int i = 0; i < len_; i++) {

            moneyNum.append("0");

        }

        moneyNum.append(s);
        return moneyNum.toString();

    }

    /**
     * @param s
     * @return
     */
    public static String formatTo3Zero(String s) {
        if (s == null || s.length() < 1 || s.length() > 12) {
            return "";
        }
        s = s.replace(".", "");
        int len = s.length();
        int len_ = 3 - len;
        StringBuffer moneyNum = new StringBuffer();
        for (int i = 0; i < len_; i++) {

            moneyNum.append("0");

        }

        moneyNum.append(s);
        return moneyNum.toString();

    }

    /**
     * @param s
     * @return
     */
    public static Long formatMoneyToFen(String s) {
        if (s == null || s.length() < 1) {
            return Long.valueOf(0);
        }
        s = s.replace(".", "");
        s = s.replace(",", "");
        return Long.valueOf(s);
    }

    /**
     * @param s
     * @return
     * @auther
     */
    public static int formatMoneyToFen2(String s) {
        if (s == null || s.length() < 1) {
            return Integer.valueOf(0);
        }
        s = s.replace(".", "");
        s = s.replace(",", "");
        return Integer.valueOf(s);
    }

    public static String formatDate(String str) {
        SimpleDateFormat sf1 = new SimpleDateFormat("yyyyMMddHHmmss");
        SimpleDateFormat sf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sfstr = "";
        try {
            sfstr = sf2.format(sf1.parse(str));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return sfstr;
    }

    // 把yyyymmdd转成yyyy-MM-dd格式

    public static String getDateFromFormat(String str, String format) {
        SimpleDateFormat sf1 = new SimpleDateFormat("yyyyMMddHHmmss");
        SimpleDateFormat sf2 = new SimpleDateFormat(format);
        String sfstr = "";
        try {
            sfstr = sf2.format(sf1.parse(str));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return sfstr;
    }

    // 把yyyymmdd转成指定格式例如yyyy-MM-dd

    public static String getDateFromFormat_(String str, String oldFormat, String newFormat) {
        SimpleDateFormat sf1 = new SimpleDateFormat(oldFormat);
        SimpleDateFormat sf2 = new SimpleDateFormat(newFormat);
        String sfstr = "";
        try {
            sfstr = sf2.format(sf1.parse(str));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return sfstr;
    }

    public static void bankCardNumAddSpace(final TextView creditCardNum) {

        creditCardNum.addTextChangedListener(new TextWatcher() {
            int beforeTextLength = 0;
            int onTextLength = 0;
            boolean isChanged = false;

            int location = 0;// 记录光标的位置
            int konggeNumberB = 0;
            private char[] tempChar;
            private StringBuffer buffer = new StringBuffer();

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                beforeTextLength = s.length();
                if (buffer.length() > 0) {
                    buffer.delete(0, buffer.length());
                }
                konggeNumberB = 0;
                for (int i = 0; i < s.length(); i++) {
                    if (s.charAt(i) == ' ') {
                        konggeNumberB++;
                    }
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                onTextLength = s.length();
                buffer.append(s.toString());
                if (onTextLength == beforeTextLength || onTextLength <= 3
                        || isChanged) {
                    isChanged = false;
                    return;
                }
                isChanged = true;
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (isChanged) {
                    location = creditCardNum.getSelectionEnd();
                    int index = 0;
                    while (index < buffer.length()) {
                        if (buffer.charAt(index) == ' ') {
                            buffer.deleteCharAt(index);
                        } else {
                            index++;
                        }
                    }

                    index = 0;
                    int konggeNumberC = 0;
                    while (index < buffer.length()) {
                        if ((index == 4 || index == 9 || index == 14 || index == 19)) {
                            buffer.insert(index, ' ');
                            konggeNumberC++;
                        }
                        index++;
                    }

                    if (konggeNumberC > konggeNumberB) {
                        location += (konggeNumberC - konggeNumberB);
                    }

                    tempChar = new char[buffer.length()];
                    buffer.getChars(0, buffer.length(), tempChar, 0);
                    String str = buffer.toString();
                    if (location > str.length()) {
                        location = str.length();
                    } else if (location < 0) {
                        location = 0;
                    }

                    creditCardNum.setText(str);
                    Editable etable = (Editable) creditCardNum.getText();
                    Selection.setSelection(etable, location);
                    isChanged = false;
                }
            }
        });
    }

    public static void phoneNumAddSpace(final TextView creditCardNum) {

        creditCardNum.addTextChangedListener(new TextWatcher() {
            int beforeTextLength = 0;
            int onTextLength = 0;
            boolean isChanged = false;

            int location = 0;// 记录光标的位置
            int konggeNumberB = 0;
            private char[] tempChar;
            private StringBuffer buffer = new StringBuffer();

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                beforeTextLength = s.length();
                if (buffer.length() > 0) {
                    buffer.delete(0, buffer.length());
                }
                konggeNumberB = 0;
                for (int i = 0; i < s.length(); i++) {
                    if (s.charAt(i) == ' ') {
                        konggeNumberB++;
                    }
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                onTextLength = s.length();
                buffer.append(s.toString());
                if (onTextLength == beforeTextLength || onTextLength <= 3
                        || isChanged) {
                    isChanged = false;
                    return;
                }
                isChanged = true;
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (isChanged) {
                    location = creditCardNum.getSelectionEnd();
                    int index = 0;
                    while (index < buffer.length()) {
                        if (buffer.charAt(index) == ' ') {
                            buffer.deleteCharAt(index);
                        } else {
                            index++;
                        }
                    }

                    index = 0;
                    int konggeNumberC = 0;
                    while (index < buffer.length()) {
                        if ((index == 3 || index == 8)) {
                            buffer.insert(index, ' ');
                            konggeNumberC++;
                        }
                        index++;
                    }

                    if (konggeNumberC > konggeNumberB) {
                        location += (konggeNumberC - konggeNumberB);
                    }

                    tempChar = new char[buffer.length()];
                    buffer.getChars(0, buffer.length(), tempChar, 0);
                    String str = buffer.toString();
                    if (location > str.length()) {
                        location = str.length();
                    } else if (location < 0) {
                        location = 0;
                    }

                    creditCardNum.setText(str);
                    Editable etable = (Editable) creditCardNum.getText();
                    Selection.setSelection(etable, location);
                    isChanged = false;
                }
            }
        });
    }

    public static Double div(Double v1, Double v2, int scale) {

        if (scale < 0) {

            throw new IllegalArgumentException(

                    "The scale must be a positive integer or zero");

        }

        BigDecimal b1 = new BigDecimal(v1.toString());

        BigDecimal b2 = new BigDecimal(v2.toString());

        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();

    }

    /**
     * 把中文转成Unicode码
     *
     * @param str
     * @return
     */
    public static String chinaToUnicode(String str) {
        String result = "";
        for (int i = 0; i < str.length(); i++) {
            int chr1 = str.charAt(i);
            if (chr1 >= 19968 && chr1 <= 171941) {//汉字范围 \u4e00-\u9fa5 (中文)
                result += "\\u" + Integer.toHexString(chr1);
            } else {
                result += str.charAt(i);
            }
        }
        return result;
    }


    public static int getPage (int count,int size){
       return new BigDecimal(count).divide(new BigDecimal(size),0,BigDecimal.ROUND_UP).intValue();
    }

    /**
     * 处理数字
     *
     * @param str
     * @return
     */
    public static String getNumber(int str,String defaults) {
        String result = "";
        if (str!=0) {
            if (str < 10000) {
                return result + str;
            } else {
                result = new BigDecimal(str + "").divide(new BigDecimal("10000"), 1, BigDecimal.ROUND_HALF_UP) + "万";
            }
            return result;
        }
        else {
            return defaults;
        }
    }


    /**
     * 处理数字
     *
     * @param str
     * @return
     */
    public static String getNumber2(int str,String defaults) {
        String result = "";
        if (str!=0) {
            if (str < 100000) {
                return result + str;
            } else {
                result = new BigDecimal(str + "").divide(new BigDecimal("10000"), 0, BigDecimal.ROUND_HALF_UP) + "万";
            }
            return result;
        }
        else {
            return defaults;
        }
    }

    public static void setMaxEcplise(final TextView mTextView, final int maxLines, final String content) {
        ViewTreeObserver observer = mTextView.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mTextView.setText(content);
                if (mTextView.getLineCount() > maxLines) {
                    int lineEndIndex = mTextView.getLayout().getLineEnd(maxLines - 1);
                    //下面这句代码中：我在项目中用数字3发现效果不好，改成1了

                    String text = content.subSequence(0, lineEndIndex - 3) + "...";
                    mTextView.setText(text);
                } else {
                    removeGlobalOnLayoutListener(mTextView.getViewTreeObserver(), this);
                }
            }
        });
    }


    @SuppressWarnings("deprecation")
    @SuppressLint("NewApi")
    private static void removeGlobalOnLayoutListener(ViewTreeObserver obs, ViewTreeObserver.OnGlobalLayoutListener listener) {
        if (obs == null) {
            return;
        }
        if (Build.VERSION.SDK_INT < 16) {
            obs.removeGlobalOnLayoutListener(listener);
        } else {
            obs.removeOnGlobalLayoutListener(listener);
        }
    }
    /**
     * 处理数字
     *
     * @param str
     * @return
     */
    public static String getNumber(int str) {
        String result = "";
        if (str < 10000) {
            return result + str;
        } else {
            result = new BigDecimal(str + "").divide(new BigDecimal("10000"), 1, BigDecimal.ROUND_HALF_UP) + "万";
        }
        return result;
    }

    /**
     * 得到自定义的progressDialog
     *
     * @param context
     * @param msg
     * @return
     */
    public static Dialog createLoadingDialog(Context context, String msg, boolean isCancel) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.loading_dialog, null);
        LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);
        // main.xml中的ImageView
        CircleProgressBar img = (CircleProgressBar) v.findViewById(R.id.img);
        TextView tipTextView = (TextView) v.findViewById(R.id.tipTextView);
        img.setColorSchemeResources(R.color.black);

        tipTextView.setText(msg);

        Dialog loadingDialog = new Dialog(context, R.style.transparentBackDialog);
        loadingDialog.setCancelable(isCancel);
        loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT,
                LinearLayout.LayoutParams.FILL_PARENT));
        return loadingDialog;
    }

    public static void loadImageVideo(Context context, String url, ImageView imageView){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                URL imageurl;
                try {
                    imageurl = new URL(url);
                    HttpURLConnection conn = (HttpURLConnection) imageurl.openConnection();
                    conn.setDoInput(true);
                    conn.connect();
                    InputStream is = conn.getInputStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                    is.close();
                    final int width =bitmap.getWidth();
                    final int height =bitmap.getHeight();
                    if (width>height) {
                        Resources resources =context.getResources();
                        BitmapDrawable drawable = new BitmapDrawable(resources, bitmap);
                        imageView.setImageDrawable(drawable);
                    }else {
                        imageView.setImageBitmap(Bitmap.createBitmap(bitmap, 0, (height-(width/16*10))/2, width, width/16*10));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        try {
            thread.start();
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static void loadImage(Context context, String url, ImageView imageView){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                URL imageurl;
                try {
                    imageurl = new URL(url);
                    HttpURLConnection conn = (HttpURLConnection) imageurl.openConnection();
                    conn.setDoInput(true);
                    conn.connect();
                    InputStream is = conn.getInputStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                    is.close();
                    Resources resources =context.getResources();
                    BitmapDrawable drawable = new BitmapDrawable(resources, bitmap);
                    imageView.setImageDrawable(drawable);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        try {
            thread.start();
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static void loadAvatar(Context context, String url, ImageView imageView){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                URL imageurl;
                try {
                    imageurl = new URL(url);
                    HttpURLConnection conn = (HttpURLConnection) imageurl.openConnection();
                    conn.setDoInput(true);
                    conn.connect();
                    InputStream is = conn.getInputStream();
                    Bitmap bitmap = CommonUtils.circleBitmap(BitmapFactory.decodeStream(is));
                    is.close();
                    Resources resources =context.getResources();
                    BitmapDrawable drawable = new BitmapDrawable(resources, bitmap);
                    imageView.setImageDrawable(drawable);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        try {
            thread.start();
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 按宽度缩放图片
     *
     * @param bmp  需要缩放的图片源
     * @param newW 需要缩放成的图片宽度
     *
     * @return 缩放后的图片
     */
    public static Bitmap zoom(@NonNull Bitmap bmp, int newW) {

        // 获得图片的宽高
        int width = bmp.getWidth();
        int height = bmp.getHeight();

        // 计算缩放比例
        float scale = ((float) newW) / width;

        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);

        // 得到新的图片
        Bitmap newbm = Bitmap.createBitmap(bmp, 0, 0, width, height, matrix, true);
        return newbm;
    }


    public static Bitmap circleBitmap(Bitmap source) {
        //获取Bitmap的宽度
        int width = source.getWidth();
        //以Bitmap的宽度值作为新的bitmap的宽高值。
        Bitmap bitmap = Bitmap.createBitmap(width, width, Bitmap.Config.ARGB_8888);
        //以此bitmap为基准，创建一个画布
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        //在画布上画一个圆
        canvas.drawCircle(width / 2, width / 2, width / 2, paint);

        //设置图片相交情况下的处理方式
        //setXfermode：设置当绘制的图像出现相交情况时候的处理方式的,它包含的常用模式有：
        //PorterDuff.Mode.SRC_IN 取两层图像交集部分,只显示上层图像
        //PorterDuff.Mode.DST_IN 取两层图像交集部分,只显示下层图像
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        //在画布上绘制bitmap
        canvas.drawBitmap(source, 0, 0, paint);

        return bitmap;

    }

    //实现图片的压缩处理
    //设置宽高必须使用浮点型，否则导致压缩的比例：0
    public static Bitmap zoom(Bitmap source,float width ,float height){

        Matrix matrix = new Matrix();
        //图片的压缩处理
        matrix.postScale(width / source.getWidth(),height / source.getHeight());

        Bitmap bitmap = Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, false);
        return bitmap;
    }


    /**
     * 判断是否是全是中文
     *
     * @param str
     * @return
     */
    public static boolean isChina(String str) {
        String result = "";
        int isChinese = 0;
        int noChinese = 0;
        for (int i = 0; i < str.length(); i++) {
            int chr1 = str.charAt(i);
            if (chr1 >= 19968 && chr1 <= 171941) {//汉字范围 \u4e00-\u9fa5 (中文)
                isChinese++;
            } else {
                noChinese++;
            }
        }
        return noChinese <= 0;
    }

    public static boolean isChinese(String s) {
        Pattern pa = Pattern.compile("^[\u4e00-\u9fa5]*$ ");
        Matcher m = pa.matcher(s);
        return m.matches();     //true为全部是汉字，否则是false

    }

    public static String Md5(String plainText) {
        StringBuffer buf = new StringBuffer();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();

            int i;


            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0) {
                    i += 256;
                }
                if (i < 16) {
                    buf.append("0");
                }
                buf.append(Integer.toHexString(i));
            }

            Log.i(TAG, "result: " + buf.toString());//32位的加密

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return buf.toString();
    }

    /**
     * 字节数组转换十六进制
     *
     * @param bts
     * @return
     */
    public static String bytes2Hex(byte[] bts) {
        String des = "";
        String tmp = null;
        if (null != bts) {
            for (int i = 0; i < bts.length; i++) {
                tmp = (Integer.toHexString(bts[i] & 0xFF));
                if (tmp.length() == 1) {
                    des += "0";
                }
                des += tmp;
            }
        }
        return des;
    }

    public static String getTimeStr(long time){
        //时间
        if (System.currentTimeMillis()- time*1000 < 2000) {
            return "刚刚";
        }
        else if (System.currentTimeMillis() / 1000 - time < 60) {
            return (System.currentTimeMillis() / 1000 - time) + "秒前";
        } else if ((System.currentTimeMillis() / 60000 - time / 60) < 60) {
            return (System.currentTimeMillis() / 60000 - time / 60) + "分钟前";
        } else if ((System.currentTimeMillis() / 3600000 - time / 3600) < 24) {
            return (System.currentTimeMillis() / 3600000 - time/ 3600) + "小时前";
        } else {
            return DateUtil.formatDateToHM(time* 1000);
        }
    }

    public static void setReply(String message, TextView content, List<EmoteModel> emoteList){
        //评论内容
        content.setText(message);
        SpannableString spanStr = new SpannableString(message);
        if (emoteList != null && emoteList.size() > 0) {
            for (EmoteModel emoteBean : emoteList) {
                drawable = new BitmapDrawable();
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        URL imageurl;
                        try {
                            imageurl = new URL(emoteBean.getUrl());
                            HttpURLConnection conn = (HttpURLConnection) imageurl.openConnection();
                            conn.setDoInput(true);
                            conn.connect();
                            InputStream is = conn.getInputStream();
                            Bitmap bitmap = BitmapFactory.decodeStream(is);
                            is.close();
                            Bitmap zoom = CommonUtils.zoom(bitmap, 48);
                            Resources resources = content.getContext().getResources();
                            drawable = new BitmapDrawable(resources, zoom);
                            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                try {
                    thread.start();
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                int start = message.indexOf(emoteBean.getText());
                while (start != -1) {
                    spanStr.setSpan(new ImageSpan(drawable), start, start + emoteBean.getText().length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                    start = message.indexOf(emoteBean.getText(), start + emoteBean.getText().length());
                    if (start == -1) {
                        content.setText(spanStr);
                        break;
                    }
                    continue;
                }
            }
        }
    }





    /**
     * @param s
     * @return
     */
    public static String formatTo6Zero(String s) {
        if (s == null || s.length() < 1 || s.length() > 6) {
            return "";
        }
        int len = s.length();
        int len_ = 6 - len;
        StringBuffer moneyNum = new StringBuffer();
        for (int i = 0; i < len_; i++) {

            moneyNum.append("0");

        }
        moneyNum.append(s);

        return moneyNum.toString();

    }

    /**
     * 调节系统音量为最大
     *
     * @param mContext
     */
    public static void adjustAudioMax(Context mContext) {
//	如何获取声音管理器：
        AudioManager audioManager = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
        //最大音量
        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_SYSTEM);
//当前音量
        int currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_SYSTEM);

//	A、设置声音模式
//声音模式
        audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
//静音模式
//	audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
//震动模式
//	audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);

//	B、调整声音大小
//减少声音音量
//	audioManager.adjustVolume(AudioManager.ADJUST_LOWER,  0);
//调大声音音量
        for (int i = 0; i < maxVolume - currentVolume; i++) {
            audioManager.adjustVolume(AudioManager.ADJUST_RAISE, 0);
        }

//	（当传入的第一个参数为 AudioManager.ADJUST_LOWER 时，可将音量调小一个单位，传入AudioManager.ADJUST_RAISE 时，则可以将音量调大一个单位。）
    }

    public static float dip2pxf(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (dpValue * scale + 0.5f);
    }
    public static int dip2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dpValue * scale + 0.5f);
    }
    /**
     * 获取缓存路径
     *
     * @param context
     * @param uniqueName 文件夹名称
     * @return 缓存路径的文件夹对象
     */
    public static File getDiskCacheDir(Context context, String uniqueName) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();// /sdcard/Android/data/<application package>/cache
        } else {
            cachePath = context.getCacheDir().getPath();// /data/data/<application package>/cache
        }
        File cacheFile = new File(cachePath + File.separator + uniqueName);
        if (!cacheFile.exists()) {
            cacheFile.mkdirs();
        }
        return cacheFile;
    }
    public static String getRandomString() {
        StringBuffer buffer = new StringBuffer(
                "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ");
        StringBuffer sb = new StringBuffer();
        Random r = new Random();
        int range = buffer.length();
        for (int i = 0; i < 20; i++) {
            sb.append(buffer.charAt(r.nextInt(range)));
        }
        return sb.toString();
    }
    public static BigDecimal formatNewWithScale(String money, int scale) {
        if (StringUtil.isEmpty(money)) {
            return new BigDecimal("0").setScale(scale, BigDecimal.ROUND_DOWN);
        }
        BigDecimal bigDecimal = new BigDecimal(money);
        return bigDecimal.setScale(scale, BigDecimal.ROUND_DOWN);
    }

    public static String formatNewFen(String money) {
        BigDecimal bigDecimal = new BigDecimal(money);
        return bigDecimal.setScale(2, BigDecimal.ROUND_DOWN)
                .multiply(new BigDecimal("100")).toString();
    }

    public static void setShadowColor(int bgColor, int shadowColor, View view) {
        ShadowHelper.getInstance()
                .setBgColor(bgColor)
                .setShadowColor(shadowColor)
                .setShadowRadius(10)
                .setShapeRadius(dp2px(view.getContext(), 5))
                .setOffsetX(4)
                .setOffsetY(4)
                .into(view);
    }

    /**
     * 根据dip转化像素
     *
     * @param context
     * @param dpValue dip值
     * @return
     */
    public static int dp2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static void setShadowColor(int bgColor, int shadowColor, int shapeRadius, View view) {
        ShadowHelper.getInstance()
                .setBgColor(bgColor)
                .setShadowColor(shadowColor)
                .setShadowRadius(10)
                .setShapeRadius(shapeRadius)
                .setOffsetX(4)
                .setOffsetY(4)
                .into(view);
    }


    private void setIsClose(AlertDialog dialog, boolean isClose) {

        try {

            Field field = dialog.getClass().getSuperclass()
                    .getDeclaredField("mShowing");
            field.setAccessible(true);
            field.set(dialog, false);

        } catch (Exception e) {

            e.printStackTrace();
        }

    }

}
