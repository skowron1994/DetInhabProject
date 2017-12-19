package com.detectinhabitants.detinhab;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class SettingsActivity extends AppCompatActivity {

    private LinearLayout dlgChangeData, dlgChangePass;
    private EditText etUserName, etUserSurname, etUserMail, etNewPass, etRetypePass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportActionBar().hide();
        Button btnUser = findViewById(R.id.btnInhManag);
        Button btnRoom = findViewById(R.id.btnGstManag);
        Button btnChangeData = findViewById(R.id.btnChangeData);
        Button btnChangePass = findViewById(R.id.btnChangePass);
        etUserName = findViewById(R.id.dlgEtFirstName);
        etUserSurname = findViewById(R.id.dlgEtLastName);
        etUserMail = findViewById(R.id.dlgEtMail);
        etNewPass = findViewById(R.id.dlgEtNewPass);
        etRetypePass = findViewById(R.id.dlgEtRePass);
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

        btnChangeData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dlgChangeData.getVisibility() == View.VISIBLE){
                    clearChangeDataDialogControl();
                    dlgChangeData.setVisibility(View.INVISIBLE);
                }
                else
                    dlgChangeData.setVisibility(View.VISIBLE);
            }
        });

        btnChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dlgChangePass.getVisibility() == View.VISIBLE){
                    clearChangePassDialogControl();
                    dlgChangePass.setVisibility(View.INVISIBLE);
                }
                else
                    dlgChangePass.setVisibility(View.VISIBLE);
            }
        });
    }

    private void clearChangeDataDialogControl() {
        etUserName.setText("");
        etUserSurname.setText("");
        etUserMail.setText("");
    }

    private void clearChangePassDialogControl() {
        etNewPass.setText("");
        etRetypePass.setText("");
    }
}
