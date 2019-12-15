package fr.utt.if26.if26_android;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;
import java.util.Random;

public class MainActivity extends ListActivity {
    private TacheDataSource datasource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        datasource = new TacheDataSource(this);
        datasource.open();

        List<Taches> values = datasource.getAllTaches();

        // utilisez SimpleCursorAdapter pour afficher les
        // éléments dans une ListView
        ArrayAdapter<Taches> adapter = new ArrayAdapter<Taches>(this,
                android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);
    }

        // Sera appelée par l'attribut onClick
        // des boutons déclarés dans main.xml
        public void onClick(View view) {
            @SuppressWarnings("unchecked")
            ArrayAdapter<Taches> adapter = (ArrayAdapter<Taches>) getListAdapter();
            Taches tache = null;
            switch (view.getId()) {
                case R.id.button:
                    String[] comments = new String[] { "Cool", "Very nice", "Hate it" };


                    int nextInt = new Random().nextInt(3);
                    // enregistrer le nouveau commentaire dans la base de données
                    tache = datasource.createTache(comments[nextInt]);
                    adapter.add(tache);
                    break;
                case R.id.button2:
                    if (getListAdapter().getCount() > 0) {
                        tache = (Taches) getListAdapter().getItem(0);
                        datasource.deleteTache(tache);
                        adapter.remove(tache);
                    }
                    break;
            }
            adapter.notifyDataSetChanged();
        }

        @Override
        protected void onResume() {
            datasource.open();
            super.onResume();
        }

        @Override
        protected void onPause() {
            datasource.close();
            super.onPause();
        }

    }

        //String[] textArray = {"One", "Two", "Three", "Four"};

        /*setContentView(linearLayout);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        for( int i = 0; i < textArray.length; i++ )
        {
            EditText textView = new EditText(this);
            textView.setText(textArray[i]);
            linearLayout.addView(textView);
        } */




