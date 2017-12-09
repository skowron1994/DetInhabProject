package com.detectinhabitants.detinhab;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
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

public class ManageChosenActivity extends AppCompatActivity {

    private static TextView habName, habSurname, habRoom, habCounsuelor, habContact, habReturnTime, habLastGuest, habSex, habAge;
    private TextView currentStatus;
    private ImageButton statusChange;
    private ListView lvStatusChange;
    HabitantModel habModel;
    String idChosen;
    private int n;
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
        habSex = (TextView) findViewById(R.id.tvAdnotations);
        currentStatus = (TextView)findViewById(R.id.tvCurrentStatus);
        statusChange = (ImageButton)findViewById(R.id.fbChangeStatus);
        lvStatusChange = (ListView)findViewById(R.id.lvStatusList);
        Bundle intent = getIntent().getExtras();
        idChosen = String.valueOf(intent.getInt("id"));
        new JsonTask2().execute(idChosen);
        lvStatusChange.setVisibility(View.INVISIBLE);

        statusChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lvStatusChange.setVisibility(View.VISIBLE);
            }
        });


        //list of statuses, controlled in listener
        String[] statusList = {"Wybierz status:", "W pokoju", "Opuścił placówkę", "Wyjechał na weekend", "Na zajęciach pozalekcyjnych", "Spóźnia się"};
        ArrayAdapter<String> status = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, statusList);
        lvStatusChange.setAdapter(status);
        lvStatusChange.setSelection(0);

        lvStatusChange.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                while (n <= position) {
                    if (position == n) {

                        switch((int)position){

                            case 1:
                                habModel.setHabStatus(1);
                                Toast.makeText(getApplicationContext(),"Status został zmieniony!",Toast.LENGTH_SHORT).show();
                                changeStatus();
                                break;
                            case 2:
                                habModel.setHabStatus(2);
                                Toast.makeText(getApplicationContext(),"Status został zmieniony!",Toast.LENGTH_SHORT).show();
                                changeStatus();
                                break;
                            case 3:
                                habModel.setHabStatus(3);
                                Toast.makeText(getApplicationContext(),"Status został zmieniony!",Toast.LENGTH_SHORT).show();
                                changeStatus();
                                break;
                            case 4:
                                habModel.setHabStatus(4);
                                Toast.makeText(getApplicationContext(),"Status został zmieniony!",Toast.LENGTH_SHORT).show();
                                changeStatus();
                                break;
                            case 5:
                                habModel.setHabStatus(5);
                                Toast.makeText(getApplicationContext(),"Status został zmieniony!",Toast.LENGTH_SHORT).show();
                                changeStatus();
                                break;
                        }
                        lvStatusChange.setVisibility(View.INVISIBLE);
                    }
                    n++;
                }
                n = 0;

            }
        });


    }

    private void changeStatus(){
        if(habModel.getHabStatus()==1){
            currentStatus.setText("W pokoju");
        }
        else if(habModel.getHabStatus()==2){
            currentStatus.setText("Poza placówką");
        }
        else if(habModel.getHabStatus()==3){
            currentStatus.setText("Wyjechał na weekend");
        }
        else if(habModel.getHabStatus()==4){
            currentStatus.setText("Na zajęciach pozalekcyjnych");
        }
        else if(habModel.getHabStatus()==5){
            currentStatus.setText("Spóźnia się");
        }

    }


    public class JsonTask2 extends AsyncTask<String, String, HabitantModel> {

        BufferedReader reader;
        HttpURLConnection connect;
        HabitantModel model;
        String data;

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
            habName.setText(s.getHabName());
            habSurname.setText(s.getHabSurname());
            habRoom.setText(String.valueOf(s.getRoomNumber()));
            habCounsuelor.setText(s.getHabConsuelor());
            habContact.setText(String.valueOf(s.getConsContact()));
            habAge.setText(String.valueOf(s.getHabAge()));
            habReturnTime.setText(s.getMaxReturnTime());
            habLastGuest.setText(s.getLastGuest());
            if(s.getAdnotations()==0){
                habSex.setText("Kobieta");
            }
            else{
                habSex.setText("Mężczyzna");
            }
            habModel = s;
            if(habModel.getHabStatus()==1){
                currentStatus.setText("W pokoju");
            }
            else if(habModel.getHabStatus()==2){
                currentStatus.setText("Poza placówką");
            }
            else if(habModel.getHabStatus()==3){
                currentStatus.setText("Wyjechał na weekend");
            }
            else if(habModel.getHabStatus()==4){
                currentStatus.setText("Na zajęciach pozalekcyjnych");
            }
            else if(habModel.getHabStatus()==5){
                currentStatus.setText("Spóźnia się");
            }
        }
    }
}

