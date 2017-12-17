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

/**
 * Created by Piotr on 17.12.2017.
 */

public class RegisterHandler extends AsyncTask<String,String,String> {

    private String data;
    private int response;
    private String stError, stSuccess, stUnexpected, stWait;
    private Activity activity;


    public RegisterHandler(Activity activity){
        this.activity = activity;
        stError = "Podano niewłaściwe dane.";
        stSuccess = "Pomyślnie zarejestrowano!";
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
            URL url = new URL("http://detinhabapi.aspnet.pl/api/createuser");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("login", params[0]);
            connection.setRequestProperty("mail", params[1]);
            connection.setRequestProperty("password", params[2]);
            connection.setRequestProperty("firstname", params[3]);
            connection.setRequestProperty("lastname", params[4]);
            connection.setRequestProperty("token", params[5]);
            response = connection.getResponseCode();

            if(response == 200)
                data = "true";
            else
            {
                data = null;
            }

            return data;

        } catch (MalformedURLException e) {
            e.printStackTrace();

        } catch (IOException e) {
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
