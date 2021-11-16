package com.IsmailUsman_i180516_i180634;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyDBHelper extends SQLiteOpenHelper {

    public static final String DBNAME = "updatedChatData4.db";

    public MyDBHelper(Context context) {
        super(context, "updatedChatData4.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        MyDB.execSQL(" create table users( email TEXT primary key , password TEXT , confirm_password TEXT) ");
        try{
            MyDB.execSQL(" create table profile( email TEXT primary key , firstName TEXT, lastName TEXT, gender TEXT, bio TEXT, profileUriStr STRING) ");
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

        try{
            MyDB.execSQL(" create table messages( senderEmail TEXT primary key , receiverEmail TEXT, mssgType TEXT, mssgImg TEXT, mssgImgUriStr STRING) ");
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB , int i, int i1) {

        MyDB.execSQL("drop table if exists users");
    }

    public boolean insertData ( String email , String password , String confirm_password){

            SQLiteDatabase MyDB = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();

            contentValues.put("email", email);
            contentValues.put("password", password);
            contentValues.put("confirm_password", confirm_password);

//        ContentValues contentValues2 = new ContentValues();
//
//        contentValues2.put("email", "Random Input");
//        contentValues2.put("firstName", "Ismail");
//        contentValues2.put("lastName", "Sb");
//        contentValues2.put("bio", "Ismail");
//        contentValues2.put("profileUriStr", "Moron");

        long result = MyDB.insert("users", null, contentValues);
//        long result2 = MyDB.insert("profile", null, contentValues2);

            if (result == -1)
                return false;

            else
                return true;
    }

    // Insert Data of the Profile of the Contact
    public boolean insertProfileData ( String email , String firstname , String lastname , String gender , String bio , String profileUriStr ){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("email", email);
        contentValues.put("firstname", firstname);
        contentValues.put("lastname", lastname);
        contentValues.put("gender", gender);
        contentValues.put("bio", bio);
        contentValues.put("profileUriStr", profileUriStr);


        long result = MyDB.insert("profile", null, contentValues);

        if (result == -1)
            return false;

        else
            return true;
    }

    public boolean checkemail( String email){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("select * from users where email=?" , new String[] {email}) ;

        if(cursor.getCount()>0)
            return false ;

        else
            return true ;
    }

    public boolean checkemailpassword( String email , String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("select * from users where email=? and password=?" , new String[] {email,password}) ;

        if(cursor.getCount()>0)
        {
            return true ;
        }

        else
            return false ;
    }

    @SuppressLint("Range")
    public String fetchingquerry()
    {
        SQLiteDatabase MyDB = this.getReadableDatabase();
        String s = "" ;
        Cursor cursor = MyDB.rawQuery("select * from users" , null) ;

        cursor.moveToFirst() ;

        while ( !cursor.isAfterLast()){

            if(cursor.getString(cursor.getColumnIndex("email")) != null){
                s+= cursor.getString(cursor.getColumnIndex("email")) ;
                s+="\n";
            }
            cursor.moveToNext();
        }

            cursor.close();
            return s ;
    }
}


