package com.detectinhabitants.detinhab;



import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;


public class SearchActivity extends AppCompatActivity {

    public static ListView lvInhabitants;
    private int n = 0;
    public static SearchView etSearchInhab;
    public static List<HabitantModel> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        getSupportActionBar().hide();
        etSearchInhab = (SearchView) findViewById(R.id.etSearchInhab);
        lvInhabitants = (ListView) findViewById(R.id.lvInhabitants);
        etSearchInhab.setSelected(false);
        new LoadHabitants(SearchActivity.this).execute();

        etSearchInhab.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                new FilterHandler(getApplicationContext(), (ArrayList<HabitantModel>) list);
                return false;
            }
        });


        lvInhabitants.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                while (n <= position) {
                    if (position == n) {
                        Intent logger = new Intent(SearchActivity.this, ManageChosenActivity.class);
                        logger.putExtra("id", n+1);
                        startActivity(logger);

                    }
                    n++;
                }
                n = 0;
            }
        });
    }

}
