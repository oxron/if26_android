package fr.utt.if26.if26_android;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "database.db";

    public static final String TABLE_NAME = "taches";
    public static final String KEY = "id";
    public static final String COLUMN_TACHE = "tache";
    public static final String COLUMN_LISTE = "liste";

    public static final int DATABASE_VERSION = 1;

    //Création de la base de données
    private static final String DATABASE_CREATE = "create table "
            + TABLE_NAME +  "(" + KEY + " integer primary key autoincrement, "
            + COLUMN_TACHE + "  text not null, "
            + COLUMN_LISTE + " text);";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(), "Upgrading database from version " + oldVersion + " to "
                + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
