package fr.utt.if26.if26_android;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

public class TacheDataSource {
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;

    private String[] allColumns = { MySQLiteHelper.KEY,
            MySQLiteHelper.COLUMN_TACHE, MySQLiteHelper.COLUMN_LISTE };

    public TacheDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Taches createTache(String tache) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_TACHE, tache);
        long insertId = database.insert(MySQLiteHelper.TABLE_NAME, null,
                values);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_NAME,
                allColumns, MySQLiteHelper.KEY + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Taches newTache = cursorToTache(cursor);
        cursor.close();
        return newTache;
    }

    public void deleteTache(Taches taches) {
        long id = taches.getId();
        System.out.println("Comment deleted with id: " + id);
        database.delete(MySQLiteHelper.TABLE_NAME, MySQLiteHelper.KEY
                + " = " + id, null);
    }

    public List<Taches> getAllTaches() {
        List<Taches> taches = new ArrayList<Taches>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_NAME,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Taches tache = cursorToTache(cursor);
            taches.add(tache);
            cursor.moveToNext();
        }
        // assurez-vous de la fermeture du curseur
        cursor.close();
        return taches;
    }

    private Taches cursorToTache(Cursor cursor) {
        Taches taches = new Taches();
        taches.setId(cursor.getInt(0));
        taches.setTache(cursor.getString(1));
        return taches;
    }
}
