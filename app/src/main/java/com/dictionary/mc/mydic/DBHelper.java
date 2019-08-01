package com.dictionary.mc.mydic;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * Created by vmang on 6/9/19.
 */

public class DBHelper extends SQLiteOpenHelper {

    // Global variable
    private Context mContext;

    public static final String DATABASE_NAME = "MY_DIC_TEST.db";
    public static final int DATABASE_VERSION = 1;


    public String DATABASE_LOCATION = "";
    public String DATABASE_FULL_PATH = "";

    private final String TBL_ENG_CHIN = "eng_chin";
    private final String TBL_BOOKMARK = "bookmark";


    private final String COL_KEY = "key";
    private final String COL_VALUE = "value";

    public SQLiteDatabase mDB;

    public DBHelper(Context context){
    //       super(context, name: "");
        super(context,DATABASE_NAME, null, DATABASE_VERSION);


        DATABASE_LOCATION = "data/data"+mContext.getPackageName()+"/database";
        DATABASE_FULL_PATH = DATABASE_LOCATION + DATABASE_NAME;

        if (!isExistingDB()){
            try {
                extractAssetToDatabaseDirectory(DATABASE_NAME);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        mDB = SQLiteDatabase.openOrCreateDatabase(DATABASE_FULL_PATH, null);


    }

    boolean isExistingDB(){

        File file = new File(DATABASE_FULL_PATH);
        return file.exists();
    }

    public void extractAssetToDatabaseDirectory(String filename)
            throws IOException{ //copy the database
        int length;
        InputStream sourceDatabase = this.mContext.getAssets().open(filename);
        File destinationPath = new File (DATABASE_FULL_PATH);
        OutputStream destination = new FileOutputStream(destinationPath);

        byte [] buffer = new byte[4096];
        while ((length = sourceDatabase.read(buffer))>0){
            destination.write(buffer, 0, length);

        }
        sourceDatabase.close();
        destination.flush();
        destination.close();

    }



    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }

    public ArrayList<String> getWord(int dicType){
        String tableName = getTableName(dicType);
        String q = "SELECT * FROM" + tableName;
        mDB.rawQuery(q, null);


        Cursor result = mDB.rawQuery(q, null);

        ArrayList<String> source = new ArrayList<>();
        while (result.moveToNext()){

            source.add(result.getString(result.getColumnIndex(COL_KEY)));

        }

        return source;
    }

    public Word getWord(String key, int dicType){
        String tableName = getTableName(dicType);
        String q = "SELECT * FROM" +tableName+ "WHERE upper ([key]) = upper(?)";
        //mDB.rawQuery(q, null);

        //Cursor result = mDB.rawQuery(q, null);
        Cursor result = mDB.rawQuery(q, new String[]{key});

        Word word = new Word();
        while (result.moveToNext()){

            word.key = (result.getString(result.getColumnIndex(COL_KEY)));
            word.key = (result.getString(result.getColumnIndex(COL_VALUE)));
        }
        return word;

    }

    public void addBookmark(Word word){

       try{
           //String q = "INSERT INTO bookmark(["+COL_KEY+"],[value]) VALUES('A', '123');"
           String q = "INSERT INTO bookmark(["+COL_KEY+"], ["+COL_VALUE+"]) VALUES (?, ?');";
           mDB.execSQL(q,new Object[] {word.key, word.value});
       } catch (SQLException ex){
       }
    }

    public void removeBookmark(Word word){
        try{
            String q = "DELETE FROM bookmark WHERE uppper (["+COL_KEY+"]) = upper(?) AND ["+COL_VALUE+"])= ?;";
            mDB.execSQL(q,new Object[] {word.key, word.value});
        } catch (SQLException ex){

        }
    }

    // #5 get word from bookmark
    public ArrayList<String> getALLWordFromBookmark(String key){

        String q = "SELECT * FROM bookmark ORDER BY [date] DESC;";
        mDB.rawQuery(q, null);
        Cursor result = mDB.rawQuery(q, null);

        ArrayList<String> source = new ArrayList<>();
        while (result.moveToNext()){
            source.add(result.getString(result.getColumnIndex(COL_KEY)));
        }
        return source;

    }
    /*
    // #5 get word from bookmark
    public ArrayList<String> getWordFromBookmark(String key){
        String q = "SELECT * FROM bookmark WHERE upper([key]) = upper(?)";
        Cursor result = mDB.rawQuery(q, null);

        Word word = new Word();
        ArrayList<String> source = new ArrayList<>();
        while (result.moveToNext()) {
            source.add(result.getString(result.getColumnIndex(COL_KEY)));
        }
        return source;
    } */

    // #6 is word mark
    public boolean isWordMark(Word word){
        String q = "SELECT * FROM bookmark WHERE upper([key]) = upper(?) AND [value] =?";
        Cursor result = mDB.rawQuery(q, new String[] {word.key, word.value});
        return result.getCount() > 0;

    }

    // #7
    public Word getWordFromBookmark(String key){
        String q = "SELECT * FROM bookmark WHERE upper([key]) = upper(?)";
        Cursor result = mDB.rawQuery(q, null);
        Word word = new Word();
        while (result.moveToNext()) {
            word.key = result.getString(result.getColumnIndex(COL_KEY));
            word.value = result.getString(result.getColumnIndex(COL_VALUE));
        }
        return word;

    }

    public String getTableName (int dicType){
        String tableName = "";
        if (dicType == R.id.action_eng_chin){

            tableName = TBL_ENG_CHIN;
        }
        return tableName;

    }



}
