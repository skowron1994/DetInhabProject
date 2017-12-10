package com.detectinhabitants.detinhab;



import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;


public class SearchActivity extends AppCompatActivity {

    public static ListView lvInhabitants;
    private int length, n = 0;
    private Button btnBack;
    private EditText etSearchInhab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        getSupportActionBar().hide();
        etSearchInhab = (EditText) findViewById(R.id.etSearchInhab);
        btnBack = (Button) findViewById(R.id.btnBack);
        lvInhabitants = (ListView) findViewById(R.id.lvInhabitants);
        etSearchInhab.setSelected(false);
        new LoadHabitants(SearchActivity.this).execute();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
