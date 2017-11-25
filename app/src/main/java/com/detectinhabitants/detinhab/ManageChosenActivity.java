package com.detectinhabitants.detinhab;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ManageChosenActivity extends AppCompatActivity {

    private TextView habName, habSurname, habAge, habRoom, habCounsuelor, habContact, habReturnTime, habLastGuest, habStatus, habAdnotations;
    private String shabName, shabSurname;
    private Button btnChange;
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
        btnChange = (Button) findViewById(R.id.btnChange);

        /*TextView habName = (TextView)findViewById(R.id.tvAge);
        TextView habName = (TextView)findViewById(R.id.tvAge);*/

        Bundle info = new Bundle();
        shabName = info.getString("");

        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               new JsonTask().execute();
            }
        });



    }


    public class JsonTask extends AsyncTask<String, String, String> {

        String data;

        @Override
        protected String doInBackground(String... params) {
            BufferedReader reader = null;
            HttpURLConnection connect = null;

            try {
                URL url = new URL("http://detinhabapi.aspnet.pl/api/UpdateUser");
                connect = (HttpURLConnection) url.openConnection();
                connect.setRequestMethod("POST");
                connect.setRequestProperty("UserContext", "{" +
                        "\"UniqueID\": \"5\"," +
                        "\"Name\": \"Ryszard\"," +
                        "\"Surname\": \"xxxC\"," +
                        "\"Login\": \"admin\"," +
                        "\"Password\": \"admin\"," +
                        "\"Mail\": \"piotrsiemieniuk@outlook.com\"," +
                        "\"PermissionFlag\": 5" +
                        "}");
                connect.connect();
                int response = connect.getResponseCode();

               /* InputStream stream = connect.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                String line = "";
                StringBuffer buffer = new StringBuffer();

                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                data = buffer.toString();
                JSONArray habArray = new JSONArray(data);


                List habList = new ArrayList<HabitantModel>();
                for (int i = 0; i < habArray.length(); i++) {

                    HabitantModel model = new HabitantModel();

                    JSONObject arrObject = habArray.getJSONObject(i);

                    model.setHabName(arrObject.getString("Name"));
                    model.setHabSurname(arrObject.getString("Surname"));
                    model.setRoomNumber(arrObject.getInt("RoomNumber"));
                    habList.add(model);
                }*/


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } /*catch (JSONException e) {
                e.printStackTrace();
            }*/ finally {
                if (connect != null) {
                    connect.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

        }
    }
}

