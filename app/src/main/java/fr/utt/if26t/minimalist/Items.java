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
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import fr.utt.if26t.minimalist.adapter.ItemAdapter;
import fr.utt.if26t.minimalist.adapter.ListAdapter;
import fr.utt.if26t.minimalist.contract.MinimalistContract;
import fr.utt.if26t.minimalist.datahelper.MenuListDBHelper;

public class Items extends AppCompatActivity {
    private TextView mTextView;
    private int intValue;
    private SQLiteDatabase mDatabase;
    private ItemAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);

        intValue = getIntent().getExtras().getInt("MY_KEY");

        mTextView = findViewById(R.id.positionTextView);
        mTextView.setText(String.valueOf(intValue));

        Button mButtonAdd  = findViewById(R.id.buttonAdd);
        mButtonAdd.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                addItem();
            }
        });


        MenuListDBHelper dbHelper = new MenuListDBHelper(this);
        mDatabase = dbHelper.getWritableDatabase();

        RecyclerView recyclerView =  findViewById(R.id.recyclerView2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ItemAdapter(this, getAllItems());
        recyclerView.setAdapter(mAdapter);

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

    public void addItem() {
        Intent myIntent = new Intent(getBaseContext(), AddItem.class);
        startActivity(myIntent);
    }

    private void removeItem(long id) {
                mDatabase.delete(MinimalistContract.ItemEntry.TABLE_NAME,
                        MinimalistContract.ListEntry._ID + "=" + id, null);
                mAdapter.swapCursor(getAllItems());
    }

    private Cursor getAllItems() {
        return mDatabase.query(MinimalistContract.ItemEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null);
    }
}
