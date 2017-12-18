package com.detectinhabitants.detinhab;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.preference.PreferenceActivity;
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


public class AddRoomHandler extends AsyncTask<String,String,String> {

    private int response;
    private String stResponse, stWait;
    private Activity activity;



    public AddRoomHandler(Activity activity){
        this.activity = activity;
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
            URL url = new URL("http://detinhabapi.aspnet.pl/api/CreateRoom");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("newRoomData", params[0]);
            response = connection.getResponseCode();

            if(response==200){
                stResponse = "Dodano nowy pokój do bazy.";
            }
            else{
                stResponse = connection.getHeaderField("message");
            }
            return stResponse;

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

        Toast.makeText(activity.getApplicationContext(),result,Toast.LENGTH_LONG).show();
    }
}