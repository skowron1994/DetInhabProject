package com.detectinhabitants.detinhab;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
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


//klasa przeznaczona do obsługi logowania
public class LoginHandler extends AsyncTask<String,String,String> {


    private String data;
    private int response;
    private String stError, stSuccess, stUnexpected, stWait;
    private Activity activity;



    public LoginHandler(Activity activity){
        this.activity = activity;
        stError = "Podaj prawidłowe dane.";
        stSuccess = "Pomyślnie zalogowano!";
        stUnexpected = "Wystąpił nieoczekiwany błąd. Spróbuj ponownie.";
        stWait = "Proszę czekać...";
    }

    public LoginHandler() {

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

            //buffer do zapisania odpowiedzi z api
            StringBuffer buffer = new StringBuffer();
            String line = "";

            //wypełnianie buffera danymi
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }

            data = buffer.toString();


            //obsługa logowania i utworzenia nowej aktywności
            if (data!=null){

                //tworzenie JsonObjectu i wypełnianie go pobranymi danymi (potrzebne do obsługi paru rzeczy,
                //jak m.in wyświetlenie imienia użytkownika przy zalogowaniu )
                //userContext użyty, aby mieć globalny dostęp do użytkownika np. do wykorzystania w powyższych
                JSONObject dataObject = new JSONObject(data);
                AppHelper.UserContext = new UserModel();
                AppHelper.UserContext.setUsrID(dataObject.getInt("UniqueID"));
                AppHelper.UserContext.setUsrLogin(dataObject.getString("Login"));
                AppHelper.UserContext.setUsrPassword(dataObject.getString("Password"));
                AppHelper.UserContext.setUsrName(dataObject.getString("Name"));
                AppHelper.UserContext.setUsrSurname(dataObject.getString("Surname"));
                AppHelper.UserContext.setUsrMail(dataObject.getString("Mail"));
                AppHelper.UserContext.setUsPermission(dataObject.getInt("PermissionFlag"));

                Intent logger = new Intent(activity, MenuActivity.class);
                activity.startActivity(logger);
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