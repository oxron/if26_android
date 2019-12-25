package fr.utt.if26t.minimalist;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import fr.utt.if26t.minimalist.adapter.ListAdapter;
import fr.utt.if26t.minimalist.contract.MinimalistContract;
import fr.utt.if26t.minimalist.datahelper.MenuListDBHelper;

public class AddItem extends AppCompatActivity {
    private EditText mNom, mNote;
    private CheckBox mImportant, mFavoris, mDone;
    private SQLiteDatabase mDatabase;
    private ListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        MenuListDBHelper dbHelper = new MenuListDBHelper(this);
        mDatabase = dbHelper.getWritableDatabase();

        mNom = findViewById(R.id.editTextNameItem);
        mNote = findViewById(R.id.editTextNote);
        mImportant = findViewById(R.id.checkBoxImportant);
        mFavoris = findViewById(R.id.checkBoxFavoris);
        mDone = findViewById(R.id.checkBoxDone);

        Button buttonAdd = findViewById(R.id.buttonAddItem);

        buttonAdd.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                addList();
            }
        });


    }


    private void addList() {
        if (mNom.getText().toString().trim().length() == 0 ) {
            Toast toastNoName = Toast.makeText(getApplicationContext(), "Vous devez donner un nom à la liste", Toast.LENGTH_SHORT);
            toastNoName.show();
            return;
        }

        String name = mNom.getText().toString();
        String note = mNote.getText().toString();
        Integer important, favoris, done;

        if (mImportant.isChecked()) {
            important = 1;
        } else {
            important = 0;
        }

        if (mFavoris.isChecked()) {
            favoris = 1;
        } else {
            favoris = 0;
        }

        if (mDone.isChecked()) {
            done = 1;
        } else {
            done = 0;
        }


        ContentValues cv = new ContentValues();
        cv.put(MinimalistContract.ItemEntry.COLUMN_NAME, name);
        cv.put(MinimalistContract.ItemEntry.COLUMN_NOTE, note);
        cv.put(MinimalistContract.ItemEntry.COLUMN_IMPORTANT, important);
        cv.put(MinimalistContract.ItemEntry.COLUMN_FAVORIS, favoris);
        cv.put(MinimalistContract.ItemEntry.COLUMN_DONE, done);

        mDatabase.insert(MinimalistContract.ItemEntry.TABLE_NAME, null, cv);

        Intent myIntent = new Intent(getBaseContext(), Items.class);
        startActivity(myIntent);
    }
}
