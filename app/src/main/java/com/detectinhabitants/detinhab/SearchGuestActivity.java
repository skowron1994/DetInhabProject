package com.detectinhabitants.detinhab;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class SearchGuestActivity extends AppCompatActivity {
    private String[] guests;
    private ArrayList<String> guList;
    private ArrayAdapter<String> adapter;
    private ListView lvGuests;
    private int length, n=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search2);
        getSupportActionBar().hide();
        EditText etSearchGst = (EditText) findViewById(R.id.etSearchGst);
        Button btnBack_2 = (Button) findViewById(R.id.btnBack_2);
        lvGuests = (ListView)findViewById(R.id.lvGuests);
        initList();
        etSearchGst.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                length = s.toString().length();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().equals("")){
                    initList();
                }
                else{
                    searchPhrase(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().length() < length){
                    initList();
                    for(String item:guests){
                        if(!item.toLowerCase().contains(s.toString().toLowerCase())){
                            guList.remove(item);
                            if((!s.toString().isEmpty()) && (guList.isEmpty())){

                            }
                            else{

                            }
                        }
                    }
                }
            }
        });


        btnBack_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        lvGuests.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                while(n<=position){
                    if(position==n){
                        Intent logger = new Intent(SearchGuestActivity.this,ManageGuestActivity.class);
                        startActivityForResult(logger,0);
                    }
                    n++;
                }
            }
        });
    }

    private void searchPhrase(String s) {
        for(String item:guests){
            if(!item.toLowerCase().contains(s.toString().toLowerCase())){
                guList.remove(item);
                if((!s.toString().isEmpty()) && (guList.isEmpty())){

                }
                else{

                }
            }
        }

        adapter.notifyDataSetChanged();
    }

    private void initList() {
        guests = new String[]{"G1","G2","G3","G4"};
        guList = new ArrayList<>(Arrays.asList(guests));
        /*adapter = new ArrayAdapter<String>(this, R.layout.guests, R.id.tvGuests, guList);
        lvGuests.setAdapter(adapter);*/

    }
}

