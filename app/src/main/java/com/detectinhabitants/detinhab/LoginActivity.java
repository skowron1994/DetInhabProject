package com.detectinhabitants.detinhab;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class LoginActivity extends AppCompatActivity {

    public static EditText etLogin, etPassword;
    private String wait = "Proszę czekać...";
    private String error = "Niepoprawne dane logowania. Spróbuj ponownie.";
    private String success = "Pomyślnie zalogowano.";
    public static boolean checker= false;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        etLogin = (EditText) findViewById(R.id.etLogin);
        etPassword = (EditText) findViewById(R.id.etPassword);
        Button btnLogin = (Button) findViewById(R.id.btnLogin);
        TextView tvPassForgotten = (TextView) findViewById(R.id.tvPassForgotten);

        //login
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ConnectionProcesses login = new ConnectionProcesses();
                login.execute(etLogin.getText().toString(),etPassword.getText().toString());
                Toast.makeText(getApplicationContext(),wait,Toast.LENGTH_SHORT).show();
                if(checker == false){
                    Toast.makeText(getApplicationContext(),error,Toast.LENGTH_SHORT).show();
                }
                else if (checker == true){
                    Toast.makeText(getApplicationContext(),success,Toast.LENGTH_SHORT).show();
                    Intent logger = new Intent(LoginActivity.this, MenuActivity.class);
                    startActivity(logger);

                }

            }
        });



        //password recovery
        tvPassForgotten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(),"Prace w toku, prosimy o cierpliwość.",Toast.LENGTH_SHORT).show();

            }
        });
    }

    public class ConnectionProcesses extends AsyncTask<String,String,String> {


        private String data;

        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            try {
                //connecting the api
                URL url = new URL("http://detinhabapi.aspnet.pl/api/user/" + params[0] + "/" + params[1]);
                connection = (HttpURLConnection) url.openConnection();
                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));

                //buffer to save the json data
                StringBuffer buffer = new StringBuffer();
                String line = "";

                //filling the buffer with data
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                data = buffer.toString();

                //making a json object from loaded data + parsing it to a UserModel
                JSONObject dataObject = new JSONObject(data);
                UserModel account = new UserModel();
                account.setUsrID(dataObject.getInt("UniqueID"));
                account.setUsrLogin(dataObject.getString("Login"));
                account.setUsrPassword(dataObject.getString("Password"));
                account.setUsrName(dataObject.getString("Name"));
                account.setUsrSurname(dataObject.getString("Surname"));
                account.setUsrMail(dataObject.getString("Mail"));
                account.setUsPermission(dataObject.getInt("PermissionFlag"));

            return data;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if(data!=null){
                checker = true;
            }

        }

    }


}
