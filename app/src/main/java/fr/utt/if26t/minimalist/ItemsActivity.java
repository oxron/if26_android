package fr.utt.if26t.minimalist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import fr.utt.if26t.minimalist.adapter.ItemAdapter;
import fr.utt.if26t.minimalist.contract.MinimalistContract;
import fr.utt.if26t.minimalist.datahelper.MenuListDBHelper;
import fr.utt.if26t.minimalist.model.ItemModel;

public class ItemsActivity extends AppCompatActivity {
    private TextView mTextView;
    private int intListId;
    private SQLiteDatabase mDatabase;
    private ItemAdapter mAdapter;
    private ArrayList<ItemModel> dataItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);

        intListId = getIntent().getExtras().getInt("KEY_LIST");

        MenuListDBHelper dbHelper = new MenuListDBHelper(this);
        mDatabase = dbHelper.getWritableDatabase();

        RecyclerView recyclerView =  findViewById(R.id.recyclerView2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        getDataItems();

        mAdapter = new ItemAdapter(this, this.dataItems);
        recyclerView.setAdapter(mAdapter);

        mTextView = findViewById(R.id.positionTextView);
        mTextView.setText(String.valueOf(intListId));

        FloatingActionButton mButtonAdd  = findViewById(R.id.buttonAdd);
        mButtonAdd.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                addItem(intListId);
            }
        });

        Log.d("couucou", dataItems.toString());



        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                removeItem((long) viewHolder.itemView.getTag());
            }
        }).attachToRecyclerView(recyclerView);
    }

    public void addItem(int id) {
        Intent myIntent = new Intent(getBaseContext(), AddItem.class);
        myIntent.putExtra("KEY_LIST", id);
        startActivity(myIntent);
    }

    private void removeItem(long id) {
                mDatabase.delete(MinimalistContract.ItemEntry.TABLE_NAME,
                        MinimalistContract.ItemEntry._ID + "=" + id, null);
                getDataItems();
                mAdapter.swapDataItems(dataItems);
    }

    private Cursor getItems() {
        switch (this.intListId) {
            case 1:
                return mDatabase.query(MinimalistContract.ItemEntry.TABLE_NAME,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);
            case 2:
                return mDatabase.query(MinimalistContract.ItemEntry.TABLE_NAME,
                        null,
                        MinimalistContract.ItemEntry.COLUMN_IMPORTANT + " = " + 1,
                        null,
                        null,
                        null,
                        null);
            case 3:
                return mDatabase.query(MinimalistContract.ItemEntry.TABLE_NAME,
                        null,
                        MinimalistContract.ItemEntry.COLUMN_PLANED + " = " + 1,
                        null,
                        null,
                        null,
                        null);
            default:
                return mDatabase.query(MinimalistContract.ItemEntry.TABLE_NAME,
                        null,
                        MinimalistContract.ItemEntry.COLUMN_LIST + " = " + this.intListId,
                        null,
                        null,
                        null,
                        null);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        //replaces the default 'Back' button action
        if(keyCode==KeyEvent.KEYCODE_BACK)
        {
            //do whatever you want the 'Back' button to do
            //as an example the 'Back' button is set to start a new Activity named 'NewActivity'
            this.startActivity(new Intent(ItemsActivity.this, MenuListActivity.class));
        }
        return true;
    }

    public void getDataItems() {
        Cursor listItems = getItems();
        this.dataItems= new ArrayList<>();

        for(listItems.moveToFirst(); !listItems.isAfterLast(); listItems.moveToNext()) {
            //dataList.add(test.getString(test.getColumnIndex(MinimalistContract.ListEntry.COLUMN_NAME)));
            ItemModel temp = new ItemModel(
                    listItems.getLong(listItems.getColumnIndex(MinimalistContract.ItemEntry._ID)),
                    listItems.getString(listItems.getColumnIndex(MinimalistContract.ItemEntry.COLUMN_NAME)),
                    listItems.getString(listItems.getColumnIndex(MinimalistContract.ItemEntry.COLUMN_NOTE)),
                    listItems.getInt(listItems.getColumnIndex(MinimalistContract.ItemEntry.COLUMN_IMPORTANT)),
                    listItems.getInt(listItems.getColumnIndex(MinimalistContract.ItemEntry.COLUMN_PLANED)),
                    listItems.getString(listItems.getColumnIndex(MinimalistContract.ItemEntry.COLUMN_DATE)),
                    listItems.getInt(listItems.getColumnIndex(MinimalistContract.ItemEntry.COLUMN_DONE)),
                    listItems.getInt(listItems.getColumnIndex(MinimalistContract.ItemEntry.COLUMN_LIST)));
            dataItems.add(temp);
        }
    }
}

