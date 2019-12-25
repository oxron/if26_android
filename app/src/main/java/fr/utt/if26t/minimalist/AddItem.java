package fr.utt.if26t.minimalist;

import android.content.ContentValues;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import fr.utt.if26t.minimalist.contract.MinimalistContract;

public class AddItem extends AppCompatActivity {
    private EditText mNom, mNote;
    private CheckBox mImportant, mFavoris;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
    }

/*
    private void addList() {
        if (mEditTextName.getText().toString().trim().length() == 0 ) {
            return;
        }

        String name = mEditTextName.getText().toString();
        ContentValues cv = new ContentValues();
        cv.put(MinimalistContract.ListEntry.COLUMN_NAME, name);
        //SI je veux rajouter un élément à une colomne je le fait là

        mDatabase.insert(MinimalistContract.ListEntry.TABLE_NAME, null, cv);
        mAdapter.swapCursor(getAllItems());

        mEditTextName.getText().clear();
    } */
}
