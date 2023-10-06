package com.example.tasky;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "ContactsDB";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_CONTACT = "contacts";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_FAME = "fame";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(" CREATE TABLE " + TABLE_CONTACT + " (" +
                KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_NAME + " TEXT, " +
                KEY_FAME + " TEXT "+

                ")");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACT);
        onCreate(db);
    }

    public void addContact(String name,String fame) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name);
        values.put(KEY_FAME, fame);
        db.insert(TABLE_CONTACT, null, values);
    }
    public ArrayList<contactmodel1> fetchContact(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CONTACT ,null );
        ArrayList<contactmodel1> arrContacts = new ArrayList<>();

        while (cursor.moveToNext()){
            contactmodel1 model = new contactmodel1();
            model.id = cursor.getInt(0);
            model.name = cursor.getString(1);
            model.fame = cursor.getString(2);
            arrContacts.add(model); // Add the model to the ArrayList
        }


        cursor.close(); // Close the cursor after you're done with it
        return arrContacts;
    }
    public void updateContact(contactmodel1 cotactModel1){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(KEY_NAME,cotactModel1.name);
        database.update(TABLE_CONTACT,cv,KEY_ID+" = " + cotactModel1.id,null);

    }
    public void deleteContact(int id){
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(TABLE_CONTACT , KEY_ID +" = ?",new String[]{String.valueOf(id)});

    }

}
