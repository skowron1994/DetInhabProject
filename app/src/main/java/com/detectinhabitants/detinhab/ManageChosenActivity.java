package com.detectinhabitants.detinhab;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ManageChosenActivity extends AppCompatActivity {

    private static TextView habName;
    private static TextView habSurname;
    private TextView habAge;
    private static TextView habRoom;
    private TextView habCounsuelor;
    private TextView habContact;
    private TextView habReturnTime;
    private TextView habLastGuest;
    private TextView habStatus;
    private TextView habAdnotations;
    private Button btnSave, btnBack;
    private Spinner statusListSpinner;
    String idChosen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.on_click_hbt);
        getSupportActionBar().hide();
        habName = (TextView) findViewById(R.id.tvHabName);
        habSurname = (TextView) findViewById(R.id.tvHabSurname);
        habAge = (TextView) findViewById(R.id.tvAge);
        habRoom = (TextView) findViewById(R.id.tvRoomNumber);
        habCounsuelor = (TextView) findViewById(R.id.tvCounsuelor);
        habContact = (TextView) findViewById(R.id.tvCounContact);
        habReturnTime = (TextView) findViewById(R.id.tvMaxReturnTime);
        habLastGuest = (TextView) findViewById(R.id.tvLastGuest);
        statusListSpinner = (Spinner)findViewById(R.id.spStatusList);
        Bundle intent = getIntent().getExtras();
        idChosen = String.valueOf(intent.getInt("id"));
        new JsonTask2().execute(idChosen);

        //list of statuses, controlled in listener
        String[] statusList = {"W pokoju", "Opuścił placówkę", "Wyjechał na weekend", "Na zajęciach pozalekcyjnych", "Spóźnia się"};
        ArrayAdapter<String> status = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, statusList);
        statusListSpinner.setAdapter(status);


        //controller for items chosen from spinner of statuses - on click request gonna be sent to api and then actions performed in the app
        statusListSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int id, long position) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {


            }
        });







    }


    public class JsonTask2 extends AsyncTask<String, String, HabitantModel> {

        BufferedReader reader;
        HttpURLConnection connect;
        HabitantModel model;
        String data, name, surname, room;

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
                String hehexd = "hehexd";
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
            habName.setText(s.getHabName());
            habSurname.setText(s.getHabSurname());
            habRoom.setText(String.valueOf(s.getRoomNumber()));

        }
    }
}

