package fr.utt.if26t.minimalist;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;

import fr.utt.if26t.minimalist.contract.MinimalistContract;
import fr.utt.if26t.minimalist.datahelper.MenuListDBHelper;
import fr.utt.if26t.minimalist.model.ItemModel;

public class ModifyItem extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private Integer mIdItem;
    private SQLiteDatabase mDatabase;
    private ArrayList<ItemModel> dataOfItem;
    private EditText mNom, mNote;
    private CheckBox mImportant, mFavoris, mDone;
    private Integer mIdList;
    private TextView dateText, dateText2;
    private Switch switchPlanifie;
    private Integer important, done, planifie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_item);
        mIdItem = getIntent().getExtras().getInt("KEY_LIST");
        mIdList = getIntent().getExtras().getInt("CURRENT_LIST_ID");

        MenuListDBHelper dbHelper = new MenuListDBHelper(this);
        mDatabase = dbHelper.getWritableDatabase();

        getDataOfItem();

        mNom = findViewById(R.id.editTextNameItem);
        mNote = findViewById(R.id.editTextNote);
        mImportant = findViewById(R.id.checkBoxImportant);
        mDone = findViewById(R.id.checkBoxDone);
        dateText = findViewById(R.id.dateTextView);
        dateText2 = findViewById(R.id.dateTextView2);
        switchPlanifie = findViewById(R.id.switch1);
        final Button showCalendar = findViewById(R.id.showCalendar);
        Button buttonModify = findViewById(R.id.buttonModifyItem);

        mNom.setText(dataOfItem.get(0).getName());
        mNote.setText(dataOfItem.get(0).getNote());
        if (dataOfItem.get(0).getImportant() ==  1) {
            mImportant.setChecked(true);
        }
        if (dataOfItem.get(0).getDone() ==  1) {
            mDone.setChecked(true);
        }
        if (dataOfItem.get(0).getPlanifie() == 1){
            switchPlanifie.setChecked(true);
            dateText.setVisibility(View.VISIBLE);
            dateText2.setVisibility(View.VISIBLE);
            showCalendar.setVisibility(View.VISIBLE);
        }

        dateText.setText(dataOfItem.get(0).getDate());

        buttonModify.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                updateItem();
            }
        });


        showCalendar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                showCalendarDialog();
            }
        });

        switchPlanifie.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    dateText.setVisibility(View.VISIBLE);
                    dateText2.setVisibility(View.VISIBLE);
                    showCalendar.setVisibility(View.VISIBLE);
                    planifie = 1;
                } else {
                    dateText.setVisibility(View.INVISIBLE);
                    dateText2.setVisibility(View.INVISIBLE);
                    showCalendar.setVisibility(View.INVISIBLE);
                    planifie = 0;
                }
            }
        });
    }

    private Cursor getDataItem() {
                return mDatabase.query(MinimalistContract.ItemEntry.TABLE_NAME,
                        null,
                        MinimalistContract.ItemEntry._ID + " =  " + mIdItem,
                        null,
                        null,
                        null,
                        null);
    }

    public void getDataOfItem() {
        Cursor listItems = getDataItem();
        this.dataOfItem = new ArrayList<ItemModel>();

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
            dataOfItem.add(temp);
        }
    }

    public void updateItem()  {
        if (mNom.getText().toString().trim().length() == 0 ) {
            Toast toastNoName = Toast.makeText(getApplicationContext(), "Vous devez donner un nom à la liste", Toast.LENGTH_SHORT);
            toastNoName.show();
            return;
        }

        String name = mNom.getText().toString();
        String note = mNote.getText().toString();
        String date = dateText.getText().toString();

        if (mImportant.isChecked()) {
            important = 1;
        } else {
            important = 0;
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
        cv.put(MinimalistContract.ItemEntry.COLUMN_PLANED, planifie);
        cv.put(MinimalistContract.ItemEntry.COLUMN_DATE, date);
        cv.put(MinimalistContract.ItemEntry.COLUMN_DONE, done);
        cv.put(MinimalistContract.ItemEntry.COLUMN_LIST, mIdList);

        mDatabase.update(MinimalistContract.ItemEntry.TABLE_NAME, cv, MinimalistContract.ItemEntry._ID + " = " + mIdItem, null);

        Intent myIntent = new Intent(getBaseContext(), ItemsActivity.class);
        myIntent.putExtra("KEY_LIST", (int) mIdList);
        startActivity(myIntent);
    }

    private void showCalendarDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String date = dayOfMonth + "/" + month + 1 + "/" + year;
        dateText.setText(date);
    }
}
