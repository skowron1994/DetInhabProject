package com.detectinhabitants.detinhab;

import android.app.Activity;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HabitantHandler extends AsyncTask<String, String, HabitantModel> {

    BufferedReader reader;
    HttpURLConnection connect;
    HabitantModel model;
    String data;
    Activity activity;

    HabitantHandler(Activity activity){
        this.activity = activity;
    }

    @Override
    protected HabitantModel doInBackground(String... strings) {

        try {
            URL url = new URL("http://detinhabapi.aspnet.pl/api/habitiant/"+strings[0]);
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
            model.setHabName(object.getString("Name"));
            model.setHabSurname(object.getString("Surname"));
            model.setRoomNumber(object.getInt("RoomNumber"));
            model.setHabStatus(object.getInt("Status"));
            model.setHabConsuelor(object.getString("ConsuelorName"));
            model.setConsContact(object.getInt("ConsuelorContact"));
            model.setMaxReturnTime(object.getString("MaxTime"));
            model.setLastGuest(object.getString("LastGuest"));
            model.setHabAge(object.getInt("Age"));
            //płeć
            model.setAdnotations(object.getInt("Sex"));
            return model;

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
    protected void onPostExecute(HabitantModel s) {
        super.onPostExecute(s);
        ManageChosenActivity.habName.setText(s.getHabName());
        ManageChosenActivity.habSurname.setText(s.getHabSurname());
        ManageChosenActivity.habRoom.setText(String.valueOf(s.getRoomNumber()));
        ManageChosenActivity.habCounsuelor.setText(s.getHabConsuelor());
        ManageChosenActivity.habContact.setText(String.valueOf(s.getConsContact()));
        ManageChosenActivity.habAge.setText(String.valueOf(s.getHabAge()));
        ManageChosenActivity.habReturnTime.setText(s.getMaxReturnTime());
        ManageChosenActivity.habLastGuest.setText(s.getLastGuest());
        if(s.getAdnotations()==0){
            ManageChosenActivity.habSex.setText("Kobieta");
        }
        else{
            ManageChosenActivity.habSex.setText("Mężczyzna");
        }
        ManageChosenActivity.habModel = s;
        if(ManageChosenActivity.habModel.getHabStatus()==1){
            ManageChosenActivity.currentStatus.setText("W pokoju");
        }
        else if(ManageChosenActivity.habModel.getHabStatus()==2){
            ManageChosenActivity.currentStatus.setText("Poza placówką");
        }
        else if(ManageChosenActivity.habModel.getHabStatus()==3){
            ManageChosenActivity.currentStatus.setText("Wyjechał na weekend");
        }
        else if(ManageChosenActivity.habModel.getHabStatus()==4){
            ManageChosenActivity.currentStatus.setText("Na zajęciach pozalekcyjnych");
        }
        else if(ManageChosenActivity.habModel.getHabStatus()==5){
            ManageChosenActivity.currentStatus.setText("Spóźnia się");
        }
    }
}
