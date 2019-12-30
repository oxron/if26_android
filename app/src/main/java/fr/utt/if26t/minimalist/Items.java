package fr.utt.if26t.minimalist;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

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

        intValue = getIntent().getExtras().getInt("KEY_LIST");

        mTextView = findViewById(R.id.positionTextView);
        mTextView.setText(String.valueOf(intValue));

        FloatingActionButton mButtonAdd  = findViewById(R.id.buttonAdd);
        mButtonAdd.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                addItem(intValue);
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

    public void addItem(int id) {
        Intent myIntent = new Intent(getBaseContext(), AddItem.class);
        myIntent.putExtra("KEY_LIST", id);
        startActivity(myIntent);
    }

    private void removeItem(long id) {
                mDatabase.delete(MinimalistContract.ItemEntry.TABLE_NAME,
                        MinimalistContract.ListEntry._ID + "=" + id, null);
                mAdapter.swapCursor(getAllItems());
    }

    private Cursor getAllItems() {
        String[] columns = {MinimalistContract.ItemEntry.COLUMN_NAME, MinimalistContract.ItemEntry.COLUMN_LIST};

        return mDatabase.query(MinimalistContract.ItemEntry.TABLE_NAME,
                 null,
                MinimalistContract.ItemEntry.COLUMN_LIST + " = " + intValue,
                null,
                null,
                null,
                null);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        //replaces the default 'Back' button action
        if(keyCode==KeyEvent.KEYCODE_BACK)
        {
            //do whatever you want the 'Back' button to do
            //as an example the 'Back' button is set to start a new Activity named 'NewActivity'
            this.startActivity(new Intent(Items.this,MenuList.class));
        }
        return true;
    }
}
