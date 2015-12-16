package com.puc.pos.barconta;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBAdapter {
    public static final String KEY_ROWID = "_id";
    public static final String KEY_NAME = "name";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_SENHA = "senha";
    public static final String KEY_VALOR = "valor";
    private static final String TAG = "DBAdapter";
    
    private static final String DATABASE_NAME = "MyDB";
    private static final String DATABASE_TABLE_CONTACTS = "contacts";
    private static final String DATABASE_TABLE_ITEM = "item";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE =
        "create table contacts (_id integer primary key autoincrement, "
        + "name text not null, email text not null, senha text not null)";

    private static final String DATABASE_CREATE_ITEM =
        "create table item (_id integer primary key autoincrement, "
        + "name text not null, valor text not null)";
        
    private final Context context;    

    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    public DBAdapter(Context ctx) 
    {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
     }
        
    private static class DatabaseHelper extends SQLiteOpenHelper 
    {
        DatabaseHelper(Context context) 
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) 
        {
        	try {
        		db.execSQL(DATABASE_CREATE);
                db.execSQL(DATABASE_CREATE_ITEM);
        	} catch (SQLException e) {
        		e.printStackTrace();
        	}
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
        {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS contacts");
            db.execSQL("DROP TABLE IF EXISTS item");
            onCreate(db);
        }
    }    

    //---opens the database---
    public DBAdapter open() throws SQLException
    {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    //---init the database---
    public DBAdapter init() throws SQLException
    {
        db = DBHelper.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS contacts");
        db.execSQL("DROP TABLE IF EXISTS item");
        db.execSQL(DATABASE_CREATE);
        db.execSQL(DATABASE_CREATE_ITEM);
        return this;
    }

    //---closes the database---    
    public void close() 
    {
        DBHelper.close();
    }
    
    //---insert a contact into the database---
    public long insertContact(String name, String email, String senha)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_NAME, name);
        initialValues.put(KEY_EMAIL, email);
        initialValues.put(KEY_SENHA, senha);
        return db.insert(DATABASE_TABLE_CONTACTS, null, initialValues);
    }

    //---deletes a particular contact---
    public boolean deleteContact(long rowId) 
    {
        return db.delete(DATABASE_TABLE_CONTACTS, KEY_ROWID + "=" + rowId, null) > 0;
    }

    //---deletes all contacts---
    public boolean deleteAllContacts()
    {
        return db.delete(DATABASE_TABLE_CONTACTS, null, null) > 0;
    }

    //---retrieves all the contacts---
    public Cursor getAllContacts() 
    {
        return db.query(DATABASE_TABLE_CONTACTS, new String[] {KEY_ROWID, KEY_NAME,
                KEY_EMAIL, KEY_SENHA}, null, null, null, null, null);
    }

    //---retrieves a particular contact---
    public Cursor getContact(long rowId) throws SQLException 
    {
        Cursor mCursor =
                db.query(true, DATABASE_TABLE_CONTACTS, new String[] {KEY_ROWID,
                KEY_NAME, KEY_EMAIL, KEY_SENHA}, KEY_ROWID + "=" + rowId, null,
                null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    //---retrieves a particular contact---
    public Cursor getContact(String email) throws SQLException
    {
        Cursor mCursor =
                db.query(true, DATABASE_TABLE_CONTACTS, new String[] {KEY_ROWID,
                                KEY_NAME, KEY_EMAIL, KEY_SENHA}, KEY_EMAIL + "=" + email, null,
                        null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    //---updates a contact---
    public boolean updateContact(long rowId, String name, String email, String senha)
    {
        ContentValues args = new ContentValues();
        args.put(KEY_NAME, name);
        args.put(KEY_EMAIL, email);
        args.put(KEY_SENHA, senha);
        return db.update(DATABASE_TABLE_CONTACTS, args, KEY_ROWID + "=" + rowId, null) > 0;
    }

    //---insert a item into the database---
    public long insertItem(String name, String valor)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_NAME, name);
        initialValues.put(KEY_VALOR, valor);
        return db.insert(DATABASE_TABLE_ITEM, null, initialValues);
    }

    //---retrieves all the item---
    public Cursor getAllItem()
    {
        return db.query(DATABASE_TABLE_ITEM, new String[]{KEY_ROWID, KEY_NAME,
                KEY_VALOR}, null, null, null, null, null);
    }
}
