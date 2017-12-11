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
    HabitantModel model;
    String data;
    Activity activity;
    String response;

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
            URL url = new URL("http://detinhabapi.aspnet.pl/api/UpdateStatus/");
            connect = (HttpURLConnection)url.openConnection();
            connect.connect();
            connect.setRequestMethod("GET");
            InputStream stream = connect.getInputStream();
            reader = new BufferedReader(new InputStreamReader(stream));

            StringBuffer buffer = new StringBuffer();

            String line="";

            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }

            data = buffer.toString();
            model = new HabitantModel();
            JSONObject object = new JSONObject(data);
            model.setHabStatus(object.getInt("Status"));

            connect.setRequestMethod("POST");
            connect.setRequestProperty("OriginalStatus", String.valueOf(model.getHabStatus()));
            connect.setRequestProperty("CurrentStatus", String.valueOf(strings[0]));
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
        } catch (JSONException e) {
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
