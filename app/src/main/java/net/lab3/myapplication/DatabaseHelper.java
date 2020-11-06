/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.ContentValues
 *  android.content.Context
 *  android.database.Cursor
 *  android.database.sqlite.SQLiteDatabase
 *  android.database.sqlite.SQLiteDatabase$CursorFactory
 *  android.database.sqlite.SQLiteOpenHelper
 *  android.graphics.Bitmap
 *  android.graphics.Bitmap$CompressFormat
 *  java.io.ByteArrayOutputStream
 *  java.io.OutputStream
 *  java.io.PrintStream
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.lang.System
 *  java.nio.charset.Charset
 *  java.nio.charset.StandardCharsets
 *  java.text.ParseException
 *  java.text.SimpleDateFormat
 *  java.util.ArrayList
 *  java.util.Date
 *  java.util.List
 */
package net.lab3.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import net.lab3.myapplication.ItemsModel;

public class DatabaseHelper
extends SQLiteOpenHelper {
    public static final String COL_1 = "ID";
    public static final String COL_2 = "Title";
    public static final String COL_3 = "Description";
    public static final String COL_4 = "Priority";
    public static final String COL_5 = "Picture";
    public static final String COL_6 = "Date";
    public static final String DATABASE_NAME = "Note7.db";
    public static final String TABLE_NAME = "student_table";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, (OutputStream)byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public Integer deleteData(int n) {
        SQLiteDatabase sQLiteDatabase = this.getWritableDatabase();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ID = ");
        stringBuilder.append(n);
        return sQLiteDatabase.delete(TABLE_NAME, stringBuilder.toString(), null);
    }

    public List<ItemsModel> getAllCotacts() throws ParseException {
        ArrayList arrayList = new ArrayList();
        Cursor cursor = this.getReadableDatabase().rawQuery("select * from student_table", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            if (cursor.getBlob(4) != null) {
                ItemsModel itemsModel = new ItemsModel(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3), cursor.getBlob(4), cursor.getString(5));
                arrayList.add((Object)itemsModel);
            } else {
                ItemsModel itemsModel = new ItemsModel(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3), cursor.getBlob(4), cursor.getString(5));
                arrayList.add((Object)itemsModel);
            }
            cursor.moveToNext();
        }
        return arrayList;
    }

    public Cursor getAllData() {
        Cursor cursor = this.getWritableDatabase().rawQuery("select * from student_table", null);
        cursor.moveToFirst();
        return cursor;
    }

    public boolean insertData(String string2, String string3, int n, byte[] arrby) {
        SQLiteDatabase sQLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, string2);
        contentValues.put(COL_3, string3);
        contentValues.put(COL_4, Integer.valueOf((int)n));
        contentValues.put(COL_5, arrby);
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        System.out.println(simpleDateFormat.format(date));
        contentValues.put(COL_6, String.valueOf((Object)simpleDateFormat.format(date)));
        return sQLiteDatabase.insert(TABLE_NAME, null, contentValues) != -1L;
    }

    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("create table student_table (ID INTEGER PRIMARY KEY AUTOINCREMENT,Title TEXT,Description TEXT,Priority INTEGER, Picture BLOB, Date text)");
    }

    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int n, int n2) {
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS student_table");
        this.onCreate(sQLiteDatabase);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public boolean updateData(int n, String string2, String string3, int n2, byte[] arrby) {
        SQLiteDatabase sQLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, string2);
        contentValues.put(COL_3, string3);
        contentValues.put(COL_4, Integer.valueOf((int)n2));
        contentValues.put(COL_6, new String(arrby, StandardCharsets.UTF_8));
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ID = ");
        stringBuilder.append(n);
        sQLiteDatabase.update(TABLE_NAME, contentValues, stringBuilder.toString(), null);
        return true;
    }

    public boolean updateDataWithout(int n, String string2, String string3, int n2) {
        SQLiteDatabase sQLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, string2);
        contentValues.put(COL_3, string3);
        contentValues.put(COL_4, Integer.valueOf((int)n2));
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ID = ");
        stringBuilder.append(n);
        sQLiteDatabase.update(TABLE_NAME, contentValues, stringBuilder.toString(), null);
        return true;
    }
}

