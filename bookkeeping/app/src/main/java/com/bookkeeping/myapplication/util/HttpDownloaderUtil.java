package com.bookkeeping.myapplication.util;

import android.content.res.XmlResourceParser;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static com.pilot.common.utils.PackageUtils.TAG;

/**
 * @author : qiuyiyang
 * @date : 2021/4/9  11:40
 * @desc :
 */
public class HttpDownloaderUtil {


    private int FILESIZE = 1024 * 1024;

    private URL url = null;


    /**
     * 在SD卡上创建文件
     * @param fileName
     * @return
     * @throws IOException
     */
    public File createSDFile(String fileName) throws IOException{
        File file = new File( fileName);
        file.createNewFile();
        return file;
    }

    /**
     * 在SD卡上创建目录
     * @param dirName
     * @return
     */
    public File createSDDir(String dirName){
        File dir = new File(dirName);
        dir.mkdir();
        return dir;
    }

    /**
     * 判断SD卡上的文件夹是否存在
     * @param fileName
     * @return
     */
    public boolean isFileExist(String fileName){
        File file = new File( fileName);
        return file.exists();
    }

    /**
     * 将一个InputStream里面的数据写入到SD卡中
     * @param path
     * @param fileName
     * @param input
     * @return
     */
    public File write2SDFromInput(String path,String fileName,InputStream input){
        File file = null;
        OutputStream output = null;
        try {
            createSDDir(path);
            file = createSDFile(path + fileName);
            output = new FileOutputStream(file);

            byte[] buffer = new byte[1024];
            int length;
            while((length=(input.read(buffer))) >0){
                output.write(buffer,0,length);
            }
            output.flush();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally{
            try {
                if (output!=null) {
                    output.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }


    /**
     * 根据URL下载文件,前提是这个文件当中的内容是文本,函数的返回值就是文本当中的内容
     * 1.创建一个URL对象
     * 2.通过URL对象,创建一个HttpURLConnection对象
     * 3.得到InputStream
     * 4.从InputStream当中读取数据
     * @param urlStr
     * @return
     */
    public String download(String urlStr){
        StringBuffer sb = new StringBuffer();
        String line = null;
        BufferedReader buffer = null;
        try {
            url = new URL(urlStr);
            HttpURLConnection urlConn = (HttpURLConnection)url.openConnection();
            buffer = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
            while( (line = buffer.readLine()) != null){
                sb.append(line);
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (buffer!=null){
                try {
                    buffer.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return sb.toString();
    }

    /**
     *
     * @param urlStr
     * @param path
     * @param fileName
     * @return
     *      -1:文件下载出错
     *       0:文件下载成功
     *       1:文件已经存在
     */
    public int downFile(String urlStr, String path, String fileName){
        InputStream inputStream = null;
        try {
            if(isFileExist(path + fileName)){
                Log.i(TAG, "downFile: 文件已经存在");
                return 1;
            } else {
                inputStream = getInputStreamFromURL(urlStr);
                File resultFile = write2SDFromInput(path, fileName, inputStream);
                if(resultFile == null){
                    Log.i(TAG, "downFile: 文件下载出错");
                    return -1;
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
        finally {
            if (inputStream!=null){
                try {
                    inputStream.close();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
            }
        }
        Log.i(TAG, "downFile: 文件下载成功"+path+"/"+fileName);
        return 0;
    }

    /**
     * 根据URL得到输入流
     * @param urlStr
     * @return
     */
    public InputStream getInputStreamFromURL(String urlStr) {
        HttpURLConnection urlConn = null;
        InputStream inputStream = null;
        try {
            url = new URL(urlStr);
            urlConn = (HttpURLConnection)url.openConnection();
            inputStream = urlConn.getInputStream();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return inputStream;
    }
}
