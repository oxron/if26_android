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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;

import fr.utt.if26t.minimalist.adapter.ListAdapter;
import fr.utt.if26t.minimalist.contract.MinimalistContract;
import fr.utt.if26t.minimalist.datahelper.MenuListDBHelper;

public class MenuList extends AppCompatActivity implements AddListDialog.AddListDialogListener {
    private SQLiteDatabase mDatabase;
    private ListAdapter mAdapter;

    private ArrayList<ListItemModel> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MenuListDBHelper dbHelper = new MenuListDBHelper(this);
        mDatabase = dbHelper.getWritableDatabase();

        RecyclerView recyclerView =  findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        getDataList();

        Log.d("DEBUG", "onCreate: " + this.dataList);

        mAdapter = new ListAdapter(this, getAllItems(),this.dataList);
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

        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View v) {
                                       openDialog();
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
        myIntent.putExtra("KEY_LIST", (int) id);
        startActivity(myIntent);
    }

    public void openDialog() {
        AddListDialog dialog = new AddListDialog();
        dialog.show(getSupportFragmentManager(), "Ajouter une liste");
    }

    private void removeList(long id) {
        Toast toastNotDeleted = Toast.makeText(getApplicationContext(), "La liste ne peut pas être supprimée", Toast.LENGTH_SHORT);
        Toast toastDeleted = Toast.makeText(getApplicationContext(), "La liste a bien été supprimée", Toast.LENGTH_SHORT);

        switch ((int) id) {
            case 1 :
            case 2 :
            case 3 :
                toastNotDeleted.show();
                getDataList();
                mAdapter.swapCursor(dataList);
                break;
            default :
                mDatabase.delete(MinimalistContract.ListEntry.TABLE_NAME,
                        MinimalistContract.ListEntry._ID + "=" + id, null);
                toastDeleted.show();
                getDataList();
                System.out.println(this.dataList);
                mAdapter.swapCursor(dataList);
                mAdapter.notifyDataSetChanged();
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

    @Override
    public void applyTexts(String nameList) {
        if (nameList.trim().length() == 0 ) {
            return;
        }

        ContentValues cv = new ContentValues();
        cv.put(MinimalistContract.ListEntry.COLUMN_NAME, nameList);

        mDatabase.insert(MinimalistContract.ListEntry.TABLE_NAME, null, cv);
        getDataList();
        mAdapter.swapCursor(dataList);
    }

    public void getDataList() {
        Cursor test = getAllItems();

        this.dataList= new ArrayList<>();

        for(test.moveToFirst(); !test.isAfterLast(); test.moveToNext()) {
            //dataList.add(test.getString(test.getColumnIndex(MinimalistContract.ListEntry.COLUMN_NAME)));
            ListItemModel temp = new ListItemModel(
                    test.getLong(test.getColumnIndex(MinimalistContract.ListEntry._ID)),
                    test.getString(test.getColumnIndex(MinimalistContract.ListEntry.COLUMN_NAME)));

            dataList.add(temp);
        }

    }

}
