package com.bookkeeping.myapplication.util;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bookkeeping.myapplication.model.BookModel;
import com.bookkeeping.myapplication.model.MaterialModel;
import com.bookkeeping.myapplication.model.SynthesisModel;
import com.bookkeeping.myapplication.model.TypeModel;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
/**
 * @author : qiuyiyang
 * @date   : 2021/1/6  16:36
 * @desc   :
 */
public class UserDao  {
    public static final String TAG = "SQLite";
    private UserSQLiteOpenHelper helper;

    public UserDao(Context context) {
        int permission = PackageManager.PERMISSION_GRANTED;
        int permission1 = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission1 != permission) {
            return ;
        }
        else {
            helper = new UserSQLiteOpenHelper(context);
        }
    }

    // TODO:data表sql方法
    /**
     * 添加一条记录到账单记录表
     * @param bookModel
     *
     * */
    public long add(BookModel bookModel,String type) {
        TypeModel typeModel = typeFindById(bookModel.getType());
        if (!StringUtil.isEmpty(type)) {
            if (typeModel == null) {
                typeModel = new TypeModel();
                typeModel.setId(bookModel.getType());
            }
            typeModel.setBalance(typeModel.getBalance().add(bookModel.getTrxMoney()));
            update(typeModel, "","");
        }
        ContentValues values = new ContentValues();
        values.put("id", bookModel.getId());
        values.put("creatTime", bookModel.getCreatTime());
        values.put("updateTime",bookModel.getUpdateTime());
        values.put("useTime", bookModel.getUseTime());
        values.put("event",bookModel.getEvent());
        values.put("type",bookModel.getType());
        values.put("remark", bookModel.getRemark());
        values.put("trxMoney",bookModel.getTrxMoney()+"");
        values.put("balance", typeModel.getBalance()+"");
        SQLiteDatabase db = helper.getWritableDatabase();
        long num = db.insert("data",null,values);
        db.close();
        return num;
    }
    /**
     * 修改一条记录
     * @param bookModel
     *
     * */
    public int update(BookModel bookModel) {
        TypeModel typeModel=getType(bookModel);
        BookModel bookModelOld=findById(bookModel.getId());
        TypeModel typeModelOld=getType(bookModelOld);
        ContentValues values = new ContentValues();
        values.put("id", bookModel.getId());
        values.put("event",bookModel.getEvent());
        values.put("creatTime", bookModel.getCreatTime());
        values.put("updateTime",bookModel.getUpdateTime());
        values.put("useTime", bookModel.getUseTime());
        values.put("type",bookModel.getType());
        values.put("remark", bookModel.getRemark());
        values.put("trxMoney",bookModel.getTrxMoney()+"");
        if (typeModel.getId().equals(typeModelOld.getId())){
            typeModel.setBalance(typeModel.getBalance().subtract(bookModelOld.getTrxMoney()).add(bookModel.getTrxMoney()));
            update(typeModel,"","");
        }else {
            typeModelOld.setBalance(typeModelOld.getBalance().subtract(bookModelOld.getTrxMoney()));
            update(typeModelOld, "", "");
            typeModel.setBalance(typeModel.getBalance().add(bookModel.getTrxMoney()));
            update(typeModel, "", "");
        }
        values.put("balance", (typeModel.getBalance()+""));
        SQLiteDatabase db = helper.getWritableDatabase();
        int num = db.update("data",values,"id=?", new String[]{bookModel.getId()});
        db.close();
        return num;
    }


    private TypeModel getType(BookModel bookModel){
        TypeModel typeModel=typeFindById(bookModel.getType());
        if (typeModel==null){
            typeModel=new TypeModel();
            typeModel.setId(bookModel.getType());
            typeModel.setBalance(new BigDecimal("0"));
        }
        return typeModel;
    }
    /**
     * 计算总额
     *
     * */
    public String getSumAll(String sql) {
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT sum(trxMoney) FROM ( SELECT * FROM  data where 1=1 "+sql+") r",null);
        cursor.moveToFirst();
        String sum=new BigDecimal(cursor.getDouble(0)).setScale(2,BigDecimal.ROUND_HALF_UP)+"";
        cursor.close();
        db.close();
        return sum;
    }

    /**
     * 计算总额bySql
     * */
    public String getSumBySql(String sql) {
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT sum(trxMoney) FROM ( SELECT * FROM  data where 1=1 "+sql+") r",null);
        Log.i(TAG, "SQL语句: ————getSumBySql——"+"SELECT sum(trxMoney) FROM ( SELECT * FROM  data where 1=1 and "+sql+") r");
        cursor.moveToFirst();
        String sum=new BigDecimal(cursor.getDouble(0)).setScale(2,BigDecimal.ROUND_HALF_UP)+"";
        cursor.close();
        db.close();
        return sum;
    }

    /**
     * 找出所有记录信息
     * */
    public List<BookModel> findAll(String sql) {
        SQLiteDatabase db = helper.getWritableDatabase();
        List<BookModel> list = new ArrayList<BookModel>();
        Cursor cursor = db.rawQuery("select * from data where 1=1 "+sql,null);
        Log.i(TAG, "SQL语句: ————findAllSql——: "+"select * from data where 1=1 "+sql);
        while(cursor.moveToNext()) {
            String id = cursor.getString(cursor.getColumnIndex("id"));
            long creatTime = cursor.getLong(cursor.getColumnIndex("creatTime"));
            long updateTime = cursor.getLong(cursor.getColumnIndex("updateTime"));
            long useTime = cursor.getLong(cursor.getColumnIndex("useTime"));
            String event = cursor.getString(cursor.getColumnIndex("event"));
            String type = cursor.getString(cursor.getColumnIndex("type"));
            String remark = cursor.getString(cursor.getColumnIndex("remark"));
            BigDecimal balance =new BigDecimal( cursor.getDouble(cursor.getColumnIndex("balance"))+"").setScale(2,BigDecimal.ROUND_HALF_UP);
            BigDecimal trxMoney =new BigDecimal( cursor.getDouble(cursor.getColumnIndex("trxMoney"))+"").setScale(2,BigDecimal.ROUND_HALF_UP);
            BookModel dataInfo = new BookModel();
            dataInfo.setId(id);
            dataInfo.setCreatTime(creatTime);
            dataInfo.setUpdateTime(updateTime);
            dataInfo.setUseTime(useTime);
            dataInfo.setEvent(event);
            dataInfo.setType(type);
            dataInfo.setBalance(balance+"");
            dataInfo.setRemark(remark);
            dataInfo.setTrxMoney(trxMoney);
            list.add(dataInfo);
        }
        cursor.close();
        db.close();
        return list;
    }
    /**
     * 找出所有符合条件的信息
     * */
    public int getCountBySql(String sql) {
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from data where 1=1 "+sql,null);
        Log.i(TAG, "SQL语句: ————getCountBySql——"+"select * from data where 1=1 and "+sql);
        int num= cursor.getCount();
        cursor.close();
        db.close();
        return num;
    }
    /**
     * 找出所有符合条件的信息
     * */
    public List<BookModel> findBySql(String sql) {
        SQLiteDatabase db = helper.getWritableDatabase();
        List<BookModel> list = new ArrayList<BookModel>();
        Cursor cursor = db.rawQuery("select * from data where 1=1 "+sql,null);
        Log.i(TAG, "SQL语句: ————findBySql——"+"select * from data where 1=1 and "+sql);
        while(cursor.moveToNext()) {
            String id = cursor.getString(cursor.getColumnIndex("id"));
            long creatTime = cursor.getLong(cursor.getColumnIndex("creatTime"));
            long updateTime = cursor.getLong(cursor.getColumnIndex("updateTime"));
            long useTime = cursor.getLong(cursor.getColumnIndex("useTime"));
            String event = cursor.getString(cursor.getColumnIndex("event"));
            String type = cursor.getString(cursor.getColumnIndex("type"));
            String remark = cursor.getString(cursor.getColumnIndex("remark"));
            BigDecimal balance =new BigDecimal( cursor.getDouble(cursor.getColumnIndex("balance"))+"").setScale(2,BigDecimal.ROUND_HALF_UP);
            BigDecimal trxMoney =new BigDecimal( cursor.getDouble(cursor.getColumnIndex("trxMoney"))+"").setScale(2,BigDecimal.ROUND_HALF_UP);
            BookModel dataInfo = new BookModel();
            dataInfo.setId(id);
            dataInfo.setCreatTime(creatTime);
            dataInfo.setUpdateTime(updateTime);
            dataInfo.setUseTime(useTime);
            dataInfo.setEvent(event);
            dataInfo.setType(type);
            dataInfo.setBalance(balance+"");
            dataInfo.setRemark(remark);
            dataInfo.setTrxMoney(trxMoney);
            list.add(dataInfo);
        }
        cursor.close();
        db.close();
        return list;
    }
    /**
     * 通过id 查找用户信息
     * id 用户id
     * */
    public BookModel findById(final String id) {
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor =db.rawQuery("select * from data where id=?",new String[]{id});
        Log.i(TAG, "SQL语句: ————findById——"+"select * from data where id="+id);
        BookModel dataInfo = new BookModel();
        if (cursor.moveToFirst()){
            long creatTime = cursor.getLong(cursor.getColumnIndex("creatTime"));
            long updateTime = cursor.getLong(cursor.getColumnIndex("updateTime"));
            long useTime = cursor.getLong(cursor.getColumnIndex("useTime"));
            String event = cursor.getString(cursor.getColumnIndex("event"));
            String type = cursor.getString(cursor.getColumnIndex("type"));
            String remark = cursor.getString(cursor.getColumnIndex("remark"));
            BigDecimal balance =new BigDecimal( cursor.getDouble(cursor.getColumnIndex("balance"))+"").setScale(2,BigDecimal.ROUND_HALF_UP);
            BigDecimal trxMoney =new BigDecimal( cursor.getDouble(cursor.getColumnIndex("trxMoney"))+"").setScale(2,BigDecimal.ROUND_HALF_UP);
            dataInfo.setId(id);
            dataInfo.setCreatTime(creatTime);
            dataInfo.setUpdateTime(updateTime);
            dataInfo.setUseTime(useTime);
            dataInfo.setEvent(event);
            dataInfo.setBalance(balance+"");
            dataInfo.setType(type);
            dataInfo.setRemark(remark);
            dataInfo.setTrxMoney(trxMoney);
        }
        cursor.close();
        db.close();
        return dataInfo;
    }


    // TODO:type表sql方法
    /**
     * 添加一条数据到类型表
     * @param typeModel
     * */
    public long add(TypeModel typeModel) {
        if (typeFindById(typeModel.getId())!=null){
            return -2;
        }
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", typeModel.getId());
        values.put("balance", typeModel.getBalance().abs()+"");
        long num = db.insert("type",null,values);
        db.close();
        return num;
    }
    /**
     * 修改一条数据
     * @param typeModel
     * @param manual 是否手动修改
     *
     * */
    public long update(TypeModel typeModel,String manual,String type) {
        TypeModel oldTypeModel=typeFindById(typeModel.getId());
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", typeModel.getId());
        values.put("balance", typeModel.getBalance()+"");
        int num = db.update("type",values,"id=?", new String[]{typeModel.getId()});
        db.close();
        if (num>0 && !StringUtil.isEmpty(manual)){
            BookModel bookModel = new BookModel();
            bookModel.setUseTime(Calendar.getInstance().getTime().getTime());
            bookModel.setCreatTime(Calendar.getInstance().getTime().getTime());
            bookModel.setId(CommonUtils.getRandomString());
            bookModel.setRemark(manual+"\n原余额："+oldTypeModel.getBalance()+type);
            bookModel.setType(typeModel.getId());
            bookModel.setEvent(manual);
            bookModel.setTrxMoney(typeModel.getBalance().subtract(oldTypeModel.getBalance()));
            add(bookModel,"");
        }
        return num;
    }

    /**
     * 计算总资产
     *
     * */
    public String getSumAllType() {
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT sum(balance) FROM ( SELECT * FROM type ) r",null);
        Log.i(TAG, "SQL语句: ————getSumAllType——: "+"SELECT sum(balance) FROM ( SELECT * FROM type ) r");
        cursor.moveToFirst();
        String sum=new BigDecimal(cursor.getDouble(0)).setScale(2,BigDecimal.ROUND_HALF_UP)+"";
        cursor.close();
        db.close();
        return sum;
    }

    /**
     * 找出所有类型信息
     * */
    public List<TypeModel> findAllTypeInfo() {
        SQLiteDatabase db = helper.getWritableDatabase();
        List<TypeModel> list = new ArrayList<>();
        Cursor cursor = db.rawQuery("select * from type ",null);
        Log.i(TAG, "SQL语句: ————findAllTypeSql——: "+"select * from type ");
        while(cursor.moveToNext()) {
            String id = cursor.getString(cursor.getColumnIndex("id"));
            BigDecimal balance =new BigDecimal( cursor.getDouble(cursor.getColumnIndex("balance"))+"").setScale(2,BigDecimal.ROUND_HALF_UP);
            TypeModel dataInfo = new TypeModel();
            dataInfo.setId(id);
            dataInfo.setBalance(balance);
            list.add(dataInfo);
        }
        cursor.close();
        db.close();
        return list;
    }

    /**
     * 找出所有类型
     * */
    public List<String> findAllType() {
        SQLiteDatabase db = helper.getWritableDatabase();
        List<String> list = new ArrayList<>();
        Cursor cursor = db.rawQuery("select * from type ",null);
        Log.i(TAG, "SQL语句: ————findAllTypeSql——: "+"select * from type ");
        while(cursor.moveToNext()) {
            String id = cursor.getString(cursor.getColumnIndex("id"));
            list.add(id);
        }
        cursor.close();
        db.close();
        return list;
    }
    /**
     * 通过id 用户信息
     * id 用户id
     * */
    public TypeModel typeFindById(final String id) {
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor =db.rawQuery("select * from type where id=?",new String[]{id});
        Log.i(TAG, "SQL语句: ————findById——"+"select * from type where id="+id);
        TypeModel dataInfo = new TypeModel();
        if (cursor.moveToFirst()){
            String balance = cursor.getString(cursor.getColumnIndex("balance"));
            dataInfo.setId(id);
            dataInfo.setBalance(new BigDecimal(balance));
        }else {
            dataInfo=null;
        }
        cursor.close();
        db.close();
        return dataInfo;
    }


    // TODO:data表sql方法
    /**
     * 添加一条记录到合成表
     *
     * */
    public long add(MaterialModel materialModel) {
        ContentValues values = new ContentValues();
        values.put("id", materialModel.getId());
        values.put("name", materialModel.getName());
        values.put("type", materialModel.getType());
        values.put("sellPrice",materialModel.getSellPrice());
        values.put("vitality",materialModel.getVitality());
        values.put("price", materialModel.getPrice());
        SQLiteDatabase db = helper.getWritableDatabase();
        long num = db.insert("material",null,values);
        db.close();
        return num;
    }
    /**
     * 修改一条记录
     * @param materialModel
     *
     * */
    public int update(MaterialModel materialModel) {
        ContentValues values = new ContentValues();
        values.put("id", materialModel.getId());
        values.put("name", materialModel.getName());
        values.put("type", materialModel.getType());
        values.put("sellPrice",materialModel.getSellPrice());
        values.put("vitality",materialModel.getVitality());
        values.put("price", materialModel.getPrice());
        SQLiteDatabase db = helper.getWritableDatabase();
        int num = db.update("material",values,"id=?", new String[]{materialModel.getId()});
        db.close();
        return num;
    }

    /**
     * 按条件查找
     * */
    public MaterialModel findMaterialSql(String sql) {
        SQLiteDatabase db = helper.getWritableDatabase();
        MaterialModel materialModel = new MaterialModel();
        Cursor cursor = db.rawQuery("select * from material "+sql,null);
        Log.i(TAG, "SQL语句: ————findMaterialSql——: "+"select * from material "+sql);
        if (cursor.moveToFirst()) {
            String id = cursor.getString(cursor.getColumnIndex("id"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String type = cursor.getString(cursor.getColumnIndex("type"));
            int sellPrice = cursor.getInt(cursor.getColumnIndex("sellPrice"));
            int vitality = cursor.getInt(cursor.getColumnIndex("vitality"));
            int price = cursor.getInt(cursor.getColumnIndex("price"));
            materialModel.setId(id);
            materialModel.setName(name);
            materialModel.setType(type);
            materialModel.setSellPrice(sellPrice);
            materialModel.setVitality(vitality);
            materialModel.setPrice(price);
        }
        cursor.close();
        db.close();
        return materialModel;
    }

    /**
     * 按条件查找
     * */
    public List<MaterialModel> findMaterialListSql(String sql) {
        SQLiteDatabase db = helper.getWritableDatabase();
        List<MaterialModel> list=new ArrayList<>();
        Cursor cursor = db.rawQuery("select * from material "+sql,null);
        Log.i(TAG, "SQL语句: ————findMaterialSql——: "+"select * from material"+sql);
        while(cursor.moveToNext()) {
            String id = cursor.getString(cursor.getColumnIndex("id"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String type = cursor.getString(cursor.getColumnIndex("type"));
            int sellPrice = cursor.getInt(cursor.getColumnIndex("sellPrice"));
            int vitality = cursor.getInt(cursor.getColumnIndex("vitality"));
            int price = cursor.getInt(cursor.getColumnIndex("price"));
            MaterialModel materialModel = new MaterialModel();
            materialModel.setId(id);
            materialModel.setName(name);
            materialModel.setType(type);
            materialModel.setSellPrice(sellPrice);
            materialModel.setVitality(vitality);
            materialModel.setPrice(price);
            list.add(materialModel);
        }
        cursor.close();
        db.close();
        return list;
    }
    /**
     * 按条件查找
     * */
    public List<String> findMaterial(String sql) {
        SQLiteDatabase db = helper.getWritableDatabase();
        List<String> list = new ArrayList<>();
        Cursor cursor = db.rawQuery("select * from material "+sql,null);
        Log.i(TAG, "SQL语句: ————findMaterialSql——: "+"select * from material "+sql);
        while(cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex("name"));
            list.add(name);
        }
        cursor.close();
        db.close();
        return list;
    }
    /**
     * 查找物品类型
     * */
    public List<String> findMaterialTypeSql() {
        SQLiteDatabase db = helper.getWritableDatabase();
        List<String> list = new ArrayList<>();
        Cursor cursor = db.rawQuery("select type from material group by type ",null);
        Log.i(TAG, "SQL语句: ————findMaterialTypeSql——: "+"select type from material group by type ");
        while(cursor.moveToNext()) {
            String type = cursor.getString(cursor.getColumnIndex("type"));
            list.add(type);
        }
        cursor.close();
        db.close();
        return list;
    }



    // TODO:data表sql方法
    /**
     * 添加一条记录到合成表
     *
     * */
    public long add(SynthesisModel synthesisModel) {
        ContentValues values = new ContentValues();
        values.put("id", synthesisModel.getId());
        values.put("childID", synthesisModel.getChildID());
        values.put("parentID", synthesisModel.getParentID());
        values.put("number",synthesisModel.getNumber());
        values.put("isSynthesis",synthesisModel.getIsSynthesis());
        SQLiteDatabase db = helper.getWritableDatabase();
        long num = db.insert("synthesis",null,values);
        db.close();
        return num;
    }
    /**
     * 修改一条记录
     * @param synthesisModel
     *
     * */
    public int update(SynthesisModel synthesisModel) {
        ContentValues values = new ContentValues();
        values.put("id", synthesisModel.getId());
        values.put("childID", synthesisModel.getChildID());
        values.put("parentID", synthesisModel.getParentID());
        values.put("number",synthesisModel.getNumber());
        values.put("isSynthesis",synthesisModel.getIsSynthesis());
        SQLiteDatabase db = helper.getWritableDatabase();
        int num = db.update("synthesis",values,"id=?", new String[]{synthesisModel.getId()});
        db.close();
        return num;
    }

    /**
     * 找出所有类型信息
     * */
    public List<SynthesisModel> findSynthesisModelById(String findId) {
        SQLiteDatabase db = helper.getWritableDatabase();
        List<SynthesisModel> list = new ArrayList<>();
        Cursor cursor = db.rawQuery("select * from synthesis where parentID ='"+findId+"' ",null);
        Log.i(TAG, "SQL语句: ————findSynthesisModelById——: "+"select * from synthesis where parentID='"+findId+"' ");
        while(cursor.moveToNext()) {
            String id = cursor.getString(cursor.getColumnIndex("id"));
            String childID = cursor.getString(cursor.getColumnIndex("childID"));
            String parentID = cursor.getString(cursor.getColumnIndex("parentID"));
            int number = cursor.getInt(cursor.getColumnIndex("number"));
            int isSynthesis = cursor.getInt(cursor.getColumnIndex("isSynthesis"));
            SynthesisModel synthesisModel = new SynthesisModel();
            synthesisModel.setId(id);
            synthesisModel.setChildID(childID);
            synthesisModel.setParentID(parentID);
            synthesisModel.setNumber(number);
            synthesisModel.setIsSynthesis(isSynthesis);
            list.add(synthesisModel);
        }
        cursor.close();
        db.close();
        return list;
    }

    /**
     * 搜索合成材料价格、活力
     * */
    public List<MaterialModel> findSynthesisById(String parentId) {
        SQLiteDatabase db = helper.getWritableDatabase();
        List<MaterialModel> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(
                "WITH cte AS (SELECT id,childID,parentID,number,isSynthesis FROM synthesis WHERE parentID = '"+parentId+"'" +
                        " UNION ALL SELECT b.id,b.childID,b.parentID,b.number,b.isSynthesis FROM synthesis b INNER JOIN cte ON cte.childID = b.parentID AND cte.isSynthesis=1 )" +
                        "SELECT a.id ,m.id AS childId, a.number,a.parentID, m.vitality, m.price,m.type,a.isSynthesis FROM cte a INNER JOIN material m ON a.childID = m.id",null);
        while(cursor.moveToNext()) {
            String id = cursor.getString(cursor.getColumnIndex("id"));
            String childId = cursor.getString(cursor.getColumnIndex("childId"));
            String type = cursor.getString(cursor.getColumnIndex("type"));
            String parentID = cursor.getString(cursor.getColumnIndex("parentID"));
            int vitality = cursor.getInt(cursor.getColumnIndex("vitality"));
            int number = cursor.getInt(cursor.getColumnIndex("number"));
            int price = cursor.getInt(cursor.getColumnIndex("price"));
            int isSynthesis = cursor.getInt(cursor.getColumnIndex("isSynthesis"));
            MaterialModel materialModel = new MaterialModel();
            materialModel.setId(id);
            materialModel.setChildId(childId);
            materialModel.setType(type);
            materialModel.setParentID(parentID);
            materialModel.setNumber(number);
            materialModel.setVitality(vitality);
            materialModel.setPrice(price);
            materialModel.setIsSynthesis(isSynthesis);
            list.add(materialModel);
        }
        cursor.close();
        db.close();
        return list;
    }
    // TODO:通用表sql方法
    /**
     * 总条数
     * */
    public int getCount(String name) {
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from "+name,null);
        int count=cursor.getCount();
        cursor.close();
        db.close();
        return count;
    }
    /**
     * 删除一条记录
     * @param id
     * @param name
     *
     * */
    public void delete(String name,String id) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("delete from "+name+" where id=?",new String[]{id});
        db.close();
    }
    /**
     * 删除一条记录,加回余额
     * @param bookModel
     * @param name
     *
     * */
    public void deleteBook(String name,BookModel bookModel) {
        TypeModel typeModel = typeFindById(bookModel.getType());
        if (!StringUtil.isEmpty(bookModel.getType())) {
            if (typeModel == null) {
                typeModel = new TypeModel();
                typeModel.setId(bookModel.getType());
            }
            typeModel.setBalance(typeModel.getBalance().subtract(bookModel.getTrxMoney()));
            update(typeModel, "","");
        }
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("delete from "+name+" where id=?",new String[]{bookModel.getId()});
        db.close();
    }
    public void openAssignFolder(Context context){
        SQLiteDatabase db = helper.getWritableDatabase();
        Log.i(TAG, "openAssignFolder: "+db.getPath());
        File file = new File(db.getPath());
        if(null==file || !file.exists()){
            return;
        }

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Uri uri = Uri.parse("content://com.android.externalstorage.documents/document/primary:bookkeeping");
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("*/*");
                intent.putExtra(DocumentsContract.EXTRA_INITIAL_URI, uri);
                context.startActivity(intent);
            } else {
                intent.setDataAndType(Uri.fromFile(file), "file/*");
            }
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }
}
