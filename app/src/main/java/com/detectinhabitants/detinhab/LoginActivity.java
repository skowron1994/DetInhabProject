package com.detectinhabitants.detinhab;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class LoginActivity extends AppCompatActivity {

    public static EditText etLogin, etPassword;
    public static TextView tvRegister;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        etLogin = findViewById(R.id.etLogin);
        etPassword = findViewById(R.id.etPassword);
        Button btnLogin = findViewById(R.id.btnLogin);
        TextView tvPassForgotten = findViewById(R.id.tvPassForgotten);
        tvRegister = findViewById(R.id.tvRegister);

        // Tylko na czas aplikacji w trybie debug
        etLogin.setText("admin");
        etPassword.setText("admin");

        //logowanie
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LoginHandler login = new LoginHandler(LoginActivity.this);
                login.execute(etLogin.getText().toString(),etPassword.getText().toString());

            }
        });


        //przypomnienie hasła
        tvPassForgotten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(),"Prace w toku, prosimy o cierpliwość.",Toast.LENGTH_SHORT).show();

            }
        });

        tvRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                Intent register = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(register);
            }
        });
    }
    }

