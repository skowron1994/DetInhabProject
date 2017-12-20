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

public class LoadUserData extends AsyncTask<String,String,UserModel> {


    private String data;
    private Activity activity;



    public LoadUserData(Activity activity){
        this.activity = activity;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
    @Override
    protected UserModel doInBackground(String... params) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        try {

            URL url = new URL("http://detinhabapi.aspnet.pl/api/login/");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("login", params[0]);
            connection.setRequestProperty("password", params[1]);
            InputStream stream = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(stream));

            StringBuffer buffer = new StringBuffer();
            String line = "";

            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }

            data = buffer.toString();


            if (data!=null){

                JSONObject dataObject = new JSONObject(data);
                AppHelper.UserContext = new UserModel();
                AppHelper.UserContext.setUsrID(dataObject.getInt("UniqueID"));
                AppHelper.UserContext.setUsrLogin(dataObject.getString("Login"));
                AppHelper.UserContext.setUsrPassword(dataObject.getString("Password"));
                AppHelper.UserContext.setUsrName(dataObject.getString("Name"));
                AppHelper.UserContext.setUsrSurname(dataObject.getString("Surname"));
                AppHelper.UserContext.setUsrMail(dataObject.getString("Mail"));
                AppHelper.UserContext.setUsPermission(dataObject.getInt("PermissionFlag"));

            }

            return AppHelper.UserContext;

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
    protected void onPostExecute(UserModel result) {
        super.onPostExecute(result);
        SettingsActivity.tvFirstName.setText(result.getUsrName());
        SettingsActivity.tvUserLastName.setText(result.getUsrSurname());
        SettingsActivity.tvUserEmail.setText(result.getUsrMail());
        SettingsActivity.tvUserLogin.setText(result.getUsrLogin());
    }
}