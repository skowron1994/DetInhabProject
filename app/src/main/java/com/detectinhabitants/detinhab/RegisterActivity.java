package com.detectinhabitants.detinhab;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    public EditText etRegLogin, etRegPassword, etReRegPassword, etRegEmail, etRegFirstName, etRegLastName, etRegToken;
    public Button btnRegister;

    public String STR_Required = "Nie wypełniono wszystkich pól!";
    public String STR_PasswordNotTheSame = "Hasło zostało niepoprawnie powtórzone.";

    public String STR_LoginLength = "Login powinien składać się z minimum 6 znaków.";
    public String STR_PasswordLength = "Hasło powinno mieć długośc minimum 8 znaków.";
    public String STR_EmailValidate = "Niepoprawny adres email.";
    public String STR_FirstnameLength = "Imię powinno mieć długość minimum 3 znaków.";
    public String STR_SecondnameLength = "Nazwisko powinno mieć długość minimum 3 znaków.";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();

        etRegLogin = findViewById(R.id.etLoginReg);
        etRegEmail = findViewById(R.id.etMailReg);
        etRegPassword = findViewById(R.id.etPasswordReg);
        etReRegPassword = findViewById(R.id.etRePasswordReg);
        etRegFirstName = findViewById(R.id.etFirstNameReg);
        etRegLastName = findViewById(R.id.etLastNameReg);
        etRegToken = findViewById(R.id.etTokenReg);
        btnRegister = findViewById(R.id.btnRegisterReg);


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String login, email, password, repassword, firstname, lastname, token;
                login = etRegLogin.getText().toString();
                email = etRegEmail.getText().toString();
                password = etRegPassword.getText().toString();
                repassword = etReRegPassword.getText().toString();
                firstname = etRegFirstName.getText().toString();
                lastname = etRegLastName.getText().toString();
                token = etRegToken.getText().toString();


                if(login != null &&  email !=null && password != null && repassword !=null &&
                        firstname != null && lastname != null && token != null)
                {
                    if(login.length() < 6)
                    {
                        Toast.makeText(RegisterActivity.this.getApplicationContext(),STR_LoginLength,Toast.LENGTH_LONG).show();
                        return;
                    }
                    if(email.length() < 5 || !email.contains("@"))
                    {
                        Toast.makeText(RegisterActivity.this.getApplicationContext(),STR_EmailValidate,Toast.LENGTH_LONG).show();
                        return;
                    }
                    if(password.length() < 8)
                    {
                        Toast.makeText(RegisterActivity.this.getApplicationContext(),STR_PasswordLength,Toast.LENGTH_LONG).show();
                        return;
                    }
                    if(firstname.length() < 3)
                    {
                        Toast.makeText(RegisterActivity.this.getApplicationContext(),STR_FirstnameLength,Toast.LENGTH_LONG).show();
                        return;
                    }
                    if(lastname.length() < 3)
                    {
                        Toast.makeText(RegisterActivity.this.getApplicationContext(),STR_SecondnameLength,Toast.LENGTH_LONG).show();
                        return;
                    }

                    if(!password.equals(repassword))
                    {
                        Toast.makeText(RegisterActivity.this.getApplicationContext(),STR_PasswordNotTheSame,Toast.LENGTH_LONG).show();
                        return;
                    }


                    RegisterHandler register = new RegisterHandler(RegisterActivity.this);
                         register.execute(etRegLogin.getText().toString(),etRegEmail.getText().toString(),
                        etRegPassword.getText().toString(),etRegFirstName.getText().toString(),
                        etRegLastName.getText().toString(),etRegToken.getText().toString());

                }
                else
                {
                    Toast.makeText(RegisterActivity.this.getApplicationContext(),STR_Required,Toast.LENGTH_LONG).show();
                    return;
                }

            }
        });
    }
}
