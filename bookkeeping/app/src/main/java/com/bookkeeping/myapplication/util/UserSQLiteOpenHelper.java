package com.bookkeeping.myapplication.util;

import android.Manifest;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * @author : qiuyiyang
 * @date   : 2021/1/6  16:32
 * @desc   :
 */
public class UserSQLiteOpenHelper extends SQLiteOpenHelper {

    /**
     * 数据库的构造方法  用来定义数据库的名称  数据库查询的结果集 数据库的版本
     * **/
    public UserSQLiteOpenHelper (Context context) {
        super(new CustomPathDatabaseContext(context,CommonUtils.getDirPath()), "data.db", null, 4);
    }
    /**
     * 数据库第一次被创建的时候调用的方法
     *  db被创建的数据库
     * **/
    @Override
    public void onCreate(SQLiteDatabase db) {
        //初始化数据库的表结构
        db.execSQL("create table data (id varchar(20) primary key ," +
                "useTime Long," +
                "creatTime Long," +
                "updateTime Long," +
                "type varchar(20)," +
                "remark varchar(255)," +
                "trxMoney decimal(10,2)DEFAULT 0," +
                "balance decimal(10,2) DEFAULT 0,"+
                "event varchar(20))");
        db.execSQL("create table type (id varchar(20) primary key ," +
                "balance decimal(10,2) DEFAULT 0)");
        db.execSQL("create table synthesis (id varchar(20) primary key ," +
                "childID varchar(20)," +
                "parentID varchar(20)," +
                "isSynthesis int DEFAULT 0," +
                "number int DEFAULT 0)");
        db.execSQL("create table material (id varchar(20) primary key ," +
                "name varchar(20)," +
                "type varchar(20)," +
                "sellPrice int DEFAULT 0," +
                "vitality int DEFAULT 0,"+
                "price int DEFAULT 0)");
    }

    /**
     * 当数据库的版本号发生变化的时候(增加的时候) 调用
     * */
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i2) {
        if (i2==4) {
            dropDb(db,"model");
            db.execSQL("create table synthesis (id varchar(20) primary key ," +
                    "childID varchar(20)," +
                    "parentID varchar(20)," +
                    "isSynthesis int DEFAULT 0," +
                    "number int DEFAULT 0)");
            db.execSQL("create table material (id varchar(20) primary key ," +
                    "name varchar(20)," +
                    "type varchar(20)," +
                    "sellPrice int DEFAULT 0," +
                    "vitality int DEFAULT 0,"+
                    "price int DEFAULT 0)");
        }
    }

    static void dropDb(SQLiteDatabase db,String name) {
        Cursor cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type ='table' AND name != 'sqlite_sequence'", null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                if (cursor.getString(0).equals(name)) {
                    db.execSQL("DROP TABLE " + cursor.getString(0));
                }
            }
        }
        if (cursor != null) {
            cursor.close();
        }
    }

    static class  CustomPathDatabaseContext extends ContextWrapper {

        private String mDirPath;

        public CustomPathDatabaseContext(Context base, String dirPath) {
            super(base);
            this.mDirPath = dirPath;
        }

        @Override
        public File getDatabasePath(String name) {
            File result = new File(mDirPath + File.separator + name);


            if (!result.getParentFile().exists()) {
                result.getParentFile().mkdirs();
            }
            return result;
        }

        @Override
        public SQLiteDatabase openOrCreateDatabase(String name, int mode, SQLiteDatabase.CursorFactory factory) {
            return SQLiteDatabase.openOrCreateDatabase(getDatabasePath(name), factory);
        }

        @Override
        public SQLiteDatabase openOrCreateDatabase(String name, int mode, SQLiteDatabase.CursorFactory factory, DatabaseErrorHandler errorHandler) {
            return SQLiteDatabase.openOrCreateDatabase(getDatabasePath(name).getAbsolutePath(), factory, errorHandler);
        }
    }
}

