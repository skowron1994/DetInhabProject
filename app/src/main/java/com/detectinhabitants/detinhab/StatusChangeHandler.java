package com.detectinhabitants.detinhab;

import android.app.Activity;
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


public class StatusChangeHandler extends AsyncTask<Integer, Integer, String> {

    BufferedReader reader;
    HttpURLConnection connect;
    Activity activity;
    String response="";

    StatusChangeHandler(Activity activity){
        this.activity = activity;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Toast.makeText(activity.getApplicationContext(),"Wysyłam informacje. Proszę czekać...",Toast.LENGTH_SHORT).show();
    }


    @Override
    protected String doInBackground(Integer... strings) {



        try {
            URL url = new URL("http://detinhabapi.aspnet.pl/api/updatestatus/");
            connect = (HttpURLConnection)url.openConnection();
            connect.setRequestMethod("POST");
            connect.setRequestProperty("habitiantid", String.valueOf(strings[2]));
            connect.setRequestProperty("OriginalStatus:", String.valueOf(strings[0]));
            connect.setRequestProperty("CurrentStatus:", String.valueOf(strings[1]));
            connect.connect();
            if(connect.getResponseCode()==200){
                response = "Pomyślnie zmieniono ztatus.";
            }
            else {
                response = "Nie udało się zmienić statusu. Jest w tej chwili edytowany przez innego użytkownika";
            }
            return response;


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            if(connect!=null){
                connect.disconnect();
            }

            if(reader!=null){
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
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Toast.makeText(activity.getApplicationContext(),s,Toast.LENGTH_LONG).show();
    }
}
