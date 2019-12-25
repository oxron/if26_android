package fr.utt.if26t.minimalist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import fr.utt.if26t.minimalist.adapter.ListAdapter;
import fr.utt.if26t.minimalist.contract.MinimalistContract;
import fr.utt.if26t.minimalist.datahelper.MenuListDBHelper;

public class MenuList extends AppCompatActivity {
    private EditText mEditTextName;
    private SQLiteDatabase mDatabase;
    private ListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MenuListDBHelper dbHelper = new MenuListDBHelper(this);
        mDatabase = dbHelper.getWritableDatabase();

        RecyclerView recyclerView =  findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ListAdapter(this, getAllItems());
        recyclerView.setAdapter(mAdapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                removeList((long) viewHolder.itemView.getTag());
            }
        }).attachToRecyclerView(recyclerView);


        mEditTextName = findViewById(R.id.editTextNameList);

        Button buttonAdd = findViewById(R.id.newListButton);

        buttonAdd.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                addList();
            }
        });

        mAdapter.setOnItemClickListener(new ListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                getItemActivity(position);
            }

        });
    }

    private void getItemActivity(long id) {
        Intent myIntent = new Intent(getBaseContext(), Items.class);
        myIntent.putExtra("MY_KEY", (int) id);
        startActivity(myIntent);
    }

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
    }

    private void removeList(long id) {
        Toast toastNotDeleted = Toast.makeText(getApplicationContext(), "La liste ne peut pas être supprimée", Toast.LENGTH_SHORT);
        Toast toastDeleted = Toast.makeText(getApplicationContext(), "La liste a bien été supprimée", Toast.LENGTH_SHORT);

        switch ((int) id) {
            case 1 :
                toastNotDeleted.show();
                mAdapter.swapCursor(getAllItems());
                break;
            case 2 :
                toastNotDeleted.show();
                mAdapter.swapCursor(getAllItems());
                break;
            case 3 :
                toastNotDeleted.show();
                mAdapter.swapCursor(getAllItems());
                break;
            default :
                mDatabase.delete(MinimalistContract.ListEntry.TABLE_NAME,
                        MinimalistContract.ListEntry._ID + "=" + id, null);
                toastDeleted.show();
                mAdapter.swapCursor(getAllItems());
        }

    }

    private Cursor getAllItems() {
        return mDatabase.query(MinimalistContract.ListEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null);
    }
}
