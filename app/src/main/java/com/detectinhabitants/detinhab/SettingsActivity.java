package com.detectinhabitants.detinhab;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class SettingsActivity extends AppCompatActivity {

    private LinearLayout dlgChangeData, dlgChangePass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportActionBar().hide();
        Button btnUser = findViewById(R.id.btnInhManag);
        Button btnRoom = findViewById(R.id.btnGstManag);
        dlgChangeData = findViewById(R.id.dlgChangeData);
        dlgChangePass = findViewById(R.id.dlgChangePass);
        dlgChangeData.setVisibility(View.INVISIBLE);
        dlgChangePass.setVisibility(View.INVISIBLE);

        btnUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingsActivity.this, AddHabitantActivity.class);
                startActivity(intent);
            }
        });

        btnRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SettingsActivity.this, AddRoomActivity.class);
                startActivity(i);
            }
        });
    }
}
