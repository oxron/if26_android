package fr.utt.if26t.minimalist.datahelper;

import android.content.ClipData;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.jar.Attributes;

import fr.utt.if26t.minimalist.contract.MinimalistContract.*;

import static fr.utt.if26t.minimalist.contract.MinimalistContract.ListEntry.TABLE_NAME;


public class MenuListDBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "minimalist.db";
    public static final int DATABASE_VERSION = 8;

    public MenuListDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQL_CREATE_MENULIST_TABLE = "CREATE TABLE "
                + ListEntry.TABLE_NAME +  " (" +
                ListEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ListEntry.COLUMN_NAME + " TEXT NOT NULL" +
                ");";

        final String SQL_CREATE_ITEMLIST_TABLE = "CREATE TABLE "
                + ItemEntry.TABLE_NAME +  " (" +
                ItemEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ItemEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                ItemEntry.COLUMN_NOTE + " TEXT, " +
                ItemEntry.COLUMN_IMPORTANT + " INTEGER, " +
                ItemEntry.COLUMN_FAVORIS + " INTEGER," +
                ItemEntry.COLUMN_DONE + " INTEGER "+
                ");";

        db.execSQL(SQL_CREATE_MENULIST_TABLE);
        db.execSQL(SQL_CREATE_ITEMLIST_TABLE);
        db.execSQL("insert into table_list(name) values('Prévu');");
        db.execSQL("insert into table_list(name) values('Important');");
        db.execSQL("insert into table_list(name) values('Favoris');");

        db.execSQL("insert into table_item(name, important, done) values('Favoris', 0, 0);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MenuListDBHelper.class.getName(), "Upgrading database from version " + oldVersion + " to "
                + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + ListEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ItemEntry.TABLE_NAME);
        onCreate(db);
    }
}
