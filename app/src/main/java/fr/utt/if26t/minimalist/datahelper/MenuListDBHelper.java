package fr.utt.if26t.minimalist.datahelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.jar.Attributes;

import fr.utt.if26t.minimalist.contract.MinimalistContract.*;

import static fr.utt.if26t.minimalist.contract.MinimalistContract.ListEntry.TABLE_NAME;


public class MenuListDBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "minimalist.db";
    public static final int DATABASE_VERSION = 4;

    public MenuListDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQL_CREATE_MENULIST_TABLE = "CREATE TABLE "
                + TABLE_NAME +  " (" +
                ListEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ListEntry.COLUMN_NAME + " TEXT NOT NULL" +
                ");";

        db.execSQL(SQL_CREATE_MENULIST_TABLE);
        db.execSQL("insert into table_list(name) values('Prévu');");
        db.execSQL("insert into table_list(name) values('Important');");
        db.execSQL("insert into table_list(name) values('Favoris');");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MenuListDBHelper.class.getName(), "Upgrading database from version " + oldVersion + " to "
                + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + ListEntry.TABLE_NAME);
        onCreate(db);
    }
}
