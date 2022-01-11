package com.unal.directoryenterprise;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.telephony.mbms.StreamingServiceInfo;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "dbCompanies.db";
    public static final String CONTACTS_TABLE_NAME = "companies";
    public static final String CONTACTS_COLUMN_ID = "id";
    public static final String CONTACTS_COLUMN_NAME = "name";
    public static final String CONTACTS_COLUMN_URL = "url";
    public static final String CONTACTS_COLUMN_EMAIL = "email";
    public static final String CONTACTS_COLUMN_CLASIFICATION= "clasification";
    public static final String CONTACTS_COLUMN_PRODUCTS_SERVICES= "product";
    public static final String CONTACTS_COLUMN_PHONE = "phone";

    DBHelper(Context context){
        super(context,DATABASE_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table companies " +
                        "(id integer primary key, name text,url text,phone text, email text,product text,clasification text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS companies");
        onCreate(db);
    }

    public boolean insertCompany (String name, String url, String phone, String email, String clasification,String product) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("phone", phone);
        contentValues.put("email", email);
        contentValues.put("url", url);
        contentValues.put("clasification", clasification);
        contentValues.put("product", product);
        db.insert("companies", null, contentValues);
        return true;
    }
    @SuppressLint("Range")
    public ArrayList<String[]>  getData(String name,String clasification) {

        ArrayList<String[]> array_list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res;
        //Cursor res =  db.rawQuery( "select * from companies where name=? and clasification=?", new String[]{name,clasification} );
        if (name=="") {
           res =  db.rawQuery( "select * from companies where (name LIKE '%"+ name + "%')", null);
        }else if (clasification=="") {
            res =  db.rawQuery( "select * from companies where (clasification LIKE '%"+ clasification + "%')", null);
        }else{
            res =  db.rawQuery( "select * from companies where (name LIKE '%"+ name + "%') AND (clasification LIKE '%"+ clasification + "%')", null);
        }

        res.moveToFirst();

        while(res.isAfterLast() == false){
            String[] rowQuery=new String[7];
            rowQuery[0]=res.getString(res.getColumnIndex(CONTACTS_COLUMN_ID));
            rowQuery[1]=res.getString(res.getColumnIndex(CONTACTS_COLUMN_NAME));
            rowQuery[2]=res.getString(res.getColumnIndex(CONTACTS_COLUMN_URL));
            rowQuery[3]=res.getString(res.getColumnIndex(CONTACTS_COLUMN_PHONE));
            rowQuery[4]=res.getString(res.getColumnIndex(CONTACTS_COLUMN_EMAIL));
            rowQuery[5]=res.getString(res.getColumnIndex(CONTACTS_COLUMN_PRODUCTS_SERVICES));
            rowQuery[6]=res.getString(res.getColumnIndex(CONTACTS_COLUMN_CLASIFICATION));
            array_list.add(rowQuery);
            res.moveToNext();
        }
        return array_list;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, CONTACTS_TABLE_NAME);
        return numRows;
    }

    public boolean updateCompany (Integer id, String name, String url, String phone, String email, String clasification,String product) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("phone", phone);
        contentValues.put("email", email);
        contentValues.put("url", url);
        contentValues.put("clasification", clasification);
        contentValues.put("product", product);
        db.update("companies", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public Integer deleteCompany(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("companies",
                "id = ? ",
                new String[] { Integer.toString(id) });
    }


    @SuppressLint("Range")
    public ArrayList<String[]> getAllCompanies() {
        ArrayList<String[]> array_list = new ArrayList<>();
        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from companies", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            String[] rowQuery=new String[7];
            rowQuery[0]=res.getString(res.getColumnIndex(CONTACTS_COLUMN_ID));
            rowQuery[1]=res.getString(res.getColumnIndex(CONTACTS_COLUMN_NAME));
            rowQuery[2]=res.getString(res.getColumnIndex(CONTACTS_COLUMN_URL));
            rowQuery[3]=res.getString(res.getColumnIndex(CONTACTS_COLUMN_PHONE));
            rowQuery[4]=res.getString(res.getColumnIndex(CONTACTS_COLUMN_EMAIL));
            rowQuery[5]=res.getString(res.getColumnIndex(CONTACTS_COLUMN_PRODUCTS_SERVICES));
            rowQuery[6]=res.getString(res.getColumnIndex(CONTACTS_COLUMN_CLASIFICATION));
            array_list.add(rowQuery);
            res.moveToNext();
        }
        return array_list;
    }
}
