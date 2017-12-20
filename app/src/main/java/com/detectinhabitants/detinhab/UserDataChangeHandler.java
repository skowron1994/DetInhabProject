package com.detectinhabitants.detinhab;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class UserDataChangeHandler extends AsyncTask<String, String, String> {

    BufferedReader reader;
    HttpURLConnection connect;
    int response;
    String data, stWait;
    Activity activity;

    UserDataChangeHandler(Activity activity){
        this.activity = activity;
        stWait = "Proszę czekać...";
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Toast.makeText(activity.getApplicationContext(),stWait,Toast.LENGTH_SHORT).show();
    }

    @Override
    protected String doInBackground(String... strings) {

        try {
            URL url = new URL("http://detinhabapi.aspnet.pl/api/UpdateUser/");
            connect = (HttpURLConnection)url.openConnection();
            connect.setRequestMethod("POST");
            connect.setRequestProperty("userId", String.valueOf(AppHelper.UserContext.getUsrID()));
            connect.setRequestProperty("userName", strings[0]);
            connect.setRequestProperty("userSurname", strings[1]);
            connect.setRequestProperty("userMail", strings[2]);
            connect.connect();
            //data = connect.getHeaderField("message");
            response = connect.getResponseCode();

            if(response==200){
                data = "Poszło";
            }
            else{
                data = "nie poszło";
            }
            return data;

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
        Toast.makeText(activity.getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }
}
