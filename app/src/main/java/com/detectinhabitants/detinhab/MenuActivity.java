package com.detectinhabitants.detinhab;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;



public class MenuActivity extends AppCompatActivity {

    @Override
    public void onBackPressed() {

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        getSupportActionBar().hide();



        TextView tvWelcome = (TextView) findViewById(R.id.tvWelcome);
        Button btnInhManag = (Button) findViewById(R.id.btnInhManag);
        Button btnGstManag = (Button) findViewById(R.id.btnGstManag);
        Button btnLogout = (Button) findViewById(R.id.btnLogout);
        Button btnSettings = (Button) findViewById(R.id.btnSettings);
        Button btnReports = (Button) findViewById(R.id.btnReport);

        String welcome = AppHelper.UserContext.getUsrName();
        tvWelcome.setText("Witaj "+ welcome +"!");

        btnInhManag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inhabitant = new Intent(MenuActivity.this, SearchActivity.class);
                startActivity(inhabitant);

            }
        });

        btnGstManag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent guest = new Intent(MenuActivity.this, SearchGuestActivity.class);
                startActivity(guest);
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent settings = new Intent(MenuActivity.this, SettingsActivity.class);
                startActivity(settings);
            }
        });

        btnReports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent reports = new Intent(MenuActivity.this, ReportActivity.class);
                startActivity(reports);
            }
        });


    }
}

