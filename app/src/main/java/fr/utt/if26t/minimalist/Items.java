package fr.utt.if26t.minimalist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Items extends AppCompatActivity {
    private TextView mTextView;
    private int intValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);

        intValue = getIntent().getExtras().getInt("MY_KEY");


        mTextView = findViewById(R.id.positionTextView);
        mTextView.setText(String.valueOf(intValue));
    }
}
