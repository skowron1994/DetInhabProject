package com.detectinhabitants.detinhab;

import android.app.Activity;
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

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        etLogin = (EditText) findViewById(R.id.etLogin);
        etPassword = (EditText) findViewById(R.id.etPassword);
        Button btnLogin = (Button) findViewById(R.id.btnLogin);
        TextView tvPassForgotten = (TextView) findViewById(R.id.tvPassForgotten);

        etLogin.setText("admin");
        etPassword.setText("admin");

        //logowanie
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ConnectionProcesses login = new ConnectionProcesses(LoginActivity.this);
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
    }

    /*public class ConnectionProcesses extends AsyncTask<String,String,String> {


        private String data;
        private int response;
        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            try {

                //connecting the api
                URL url = new URL("http://detinhabapi.aspnet.pl/api/login/");
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("login", params[0]);
                connection.setRequestProperty("password", params[1]);
                response = connection.getResponseCode();
                message = connection.getHeaderField("message").toString();
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
                AppHelper.UserContext = new UserModel();
                AppHelper.UserContext.setUsrID(dataObject.getInt("UniqueID"));
                AppHelper.UserContext.setUsrLogin(dataObject.getString("Login"));
                AppHelper.UserContext.setUsrPassword(dataObject.getString("Password"));
                AppHelper.UserContext.setUsrName(dataObject.getString("Name"));
                AppHelper.UserContext.setUsrSurname(dataObject.getString("Surname"));
                AppHelper.UserContext.setUsrMail(dataObject.getString("Mail"));
                AppHelper.UserContext.setUsPermission(dataObject.getInt("PermissionFlag"));


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
            if(response == 200){
                checker = true;

            }
            else if (response == 400){
                checker = false;

            }

        }

    }*/


    public class ConnectionProcesses extends AsyncTask<String,String,String> {


        private String data;
        private int response;
        private String stError, stSuccess, stUnexpected, stWait;
        private Activity activity;



        public ConnectionProcesses(Activity activity){
            this.activity = activity;
            stError = "Podaj prawidłowe dane.";
            stSuccess = "Pomyślnie zalogowano!";
            stUnexpected = "Wystąpił nieoczekiwany błąd. Spróbuj ponownie.";
            stWait = "Proszę czekać...";
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(activity.getApplicationContext(),stWait,Toast.LENGTH_SHORT).show();
        }
        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            try {

                //połączenie z api
                URL url = new URL("http://detinhabapi.aspnet.pl/api/login/");
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("login", params[0]);
                connection.setRequestProperty("password", params[1]);
                response = connection.getResponseCode();
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


                //starting an activity
                if (data!=null){

                    //making a json object from loaded data + parsing it to a UserModel
                    JSONObject dataObject = new JSONObject(data);
                    AppHelper.UserContext = new UserModel();
                    AppHelper.UserContext.setUsrID(dataObject.getInt("UniqueID"));
                    AppHelper.UserContext.setUsrLogin(dataObject.getString("Login"));
                    AppHelper.UserContext.setUsrPassword(dataObject.getString("Password"));
                    AppHelper.UserContext.setUsrName(dataObject.getString("Name"));
                    AppHelper.UserContext.setUsrSurname(dataObject.getString("Surname"));
                    AppHelper.UserContext.setUsrMail(dataObject.getString("Mail"));
                    AppHelper.UserContext.setUsPermission(dataObject.getInt("PermissionFlag"));

                    Intent logger = new Intent(LoginActivity.this, MenuActivity.class);
                    startActivity(logger);
                }

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
            if (data != null) {
                Toast.makeText(activity.getApplicationContext(),stSuccess,Toast.LENGTH_LONG).show();
            }
            else if(data==null){
                Toast.makeText(activity.getApplicationContext(),stError,Toast.LENGTH_SHORT).show();
            }
            else{

                Toast.makeText(activity.getApplicationContext(),stUnexpected,Toast.LENGTH_SHORT).show();
            }

            }
        }

    }

